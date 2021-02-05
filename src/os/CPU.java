package os;

import java.util.Vector;

public class CPU extends Thread{
	static int TIME = 1;//时间片为1
	static int NUMBER = 3;//道数
	static Vector<PCB>ready=new Vector<PCB>();//就绪队列
	static Vector<PCB>newp=new Vector<PCB>();//后备队列
	static Vector<PCB>block=new Vector<PCB>();//阻塞队列
	static Vector<PCB>hang=new Vector<PCB>();//挂起队列
	static Vector<PCB>finish=new Vector<PCB>();//完成队列
	static int rmutex=1;//就绪队列的锁
	static int bmutex=1;//阻塞队列的锁

	//根据优先级给进程排序
	public static void sort(Vector<PCB>q)
	{
		PCB s;
		for(int i=0;i<q.size()-1;i++)
		{
			for(int j=i+1;j<q.size();j++) {
				if(q.get(i).getpriority()>q.get(j).getpriority()) {
					s=q.get(i);
					q.set(i,q.get(j));
					q.set(j,s);
				}
			}
		}
	}
	//两个处理机调度
	public void run()
	{	
		String name=Thread.currentThread().getName();
		if(name.equals("op")) {
			while(Show.flag>0) {
				try {
					sort(newp);
					dispatch(ready,newp);//作业调度
					Thread.sleep(200);
				}catch(Exception e) {}
			}
		}
		else if(name.equals("pro1")) {
			while(Show.flag>0) {
				try {
					deal();//进程调度
					Thread.sleep(200);
				}catch(Exception e) {}
			}
		}
		else if(name.equals("pro2")) {
			while(Show.flag>0) {				
				try {
					deal();//进程调度
					Thread.sleep(200);
				}catch(Exception e) {}
			}
		}
	}
	//作业调度
	public static void dispatch(Vector<PCB>ready,Vector<PCB>newp)
	{
		if(ready.size()+block.size()<NUMBER&&newp.size()>0){
			if(Memory.enough(newp.get(0))){//分配内存
				newp.get(0).setstate(0);
				ready.add(newp.get(0));
				newp.remove(0);
			}
			else { //内存不满足进程的需求，将进程加到后备队列队尾
				PCB p=newp.get(0);
				newp.remove(0);
				newp.add(p);
			}
		}
	}
	//进程调度
	public static void deal()
	{
		PCB p;
		if(ready.size()>=1&&ready.size()<NUMBER&&newp.size()==0||ready.size()>NUMBER-1) {
			if(rmutex>0) {
				rmutex-=1;
				sort(ready);					
			}
			for(int i=0;i<ready.size();i++) {
				p=ready.get(i);
				if(p.getstate()==1)continue;//进程在另一个处理机执行，跳过该进程		
				else if(p.getpre()>0&&find(p.getpre(),finish)||p.getpre()==0){//进程的前驱已经运行完毕或者进程无前驱，运行该进程
					p.setstate(1);
					if(p.gettimes()>0) {
						p.settimes(p.gettimes()-1);
						if(Thread.currentThread().getName()=="pro1") {
							Show.jt1.setText("进程"+p.getpid()+"正在运行"+" 剩余时间"+p.gettimes()+" 当前优先级"+p.getpriority());
							try {
								Thread.sleep(1000);
							}catch(Exception e) {}
							Show.jt1.setText("");
							p.setpriority(p.getpriority()+1);
						}
						else {
							Show.jt2.setText("进程"+p.getpid()+"正在运行"+" 剩余时间"+p.gettimes()+" 当前优先级"+p.getpriority());
							try {
								Thread.sleep(1000);
							}catch(Exception e) {}	
							Show.jt2.setText("");
							p.setpriority(p.getpriority()+1);
						}
						p.setstate(0);
					}
					if(p.gettimes()<1) {//进程执行完
						finish.add(p);
						ready.remove(p);
						Memory.recover(p);
						wakeup(p.getpid());
					}
				}		
				else if(p.getpre()>0&&find(p.getpre(),finish)==false) {//进程存在前驱且前驱没有执行完
					p.setstate(2);
					if(bmutex>0) {
						bmutex-=1;
						block.add(p);
						ready.remove(p);
						sort(block);
						bmutex+=1;
					}
				}
				rmutex+=1;
				break;
			}	
			}
		
		else if(ready.size()<=0&&newp.size()<=0) {
			try {
				Thread.sleep(200);
			}catch(Exception e) {}
		}
	}
    //将阻塞的后继进程加入就绪队列
	public static void wakeup(int pid)
	{
		for(int i=0;i<block.size();i++) {
			if(block.get(i).getpre()==pid) {
				block.get(i).setstate(0);
				ready.add(block.get(i));
				block.remove(i);
			}
		}
	}
	//寻找进程
	public static boolean find(int pid,Vector<PCB>q)
	{
		for(int i=0;i<q.size();i++) {
			if(pid==q.get(i).getpid())
				return true;
		}
		return false;
	}
	//添加作业
	public static boolean insert(String str)
	{
		String s[]=str.split(" ");
		if(s.length==5) {
			PCB p=new PCB(Integer.valueOf(s[0]),Integer.valueOf(s[1]),Integer.valueOf(s[2]),Integer.valueOf(s[3]),Integer.valueOf(s[4]));
	        newp.add(p);
	        sort(newp);
	        return true;
		}
		return false;

	}
	//挂起进程
	public static void hang(String s)
	{
		PCB p;
		int name=Integer.parseInt(s);
		for(int i=0;i<block.size();i++) {
			if(block.get(i).getpid()==name) {
				p=block.get(i);
				Memory.recover(p);
				p.setstate(4);
				hang.add(p);
				block.remove(p);
				break;
			}
		}
		for(int j=0;j<ready.size();j++){
			if(ready.get(j).getpid()==name) {
				p=ready.get(j);
				Memory.recover(p);
				p.setstate(4);
				hang.add(p);
				ready.remove(p);
				break;
			}
		}
	}
    //解挂进程
	public static boolean move(String s)
	{
		PCB p;
		int name=Integer.parseInt(s);
		for(int i=0;i<hang.size();i++) {
			if(hang.get(i).getpid()==name) {
				p=hang.get(i);
				if(Memory.enough(p)) {
					p.setstate(0);
					ready.add(p);
					sort(ready);
					hang.remove(i);	
					return true;
				}	
				break;
			}
		}
		return false;
	}
	//就绪和阻塞队列显示
	public static StringBuffer show1(Vector<PCB>q)
	{
		StringBuffer s=new StringBuffer();
		PCB p;
        if(q.size()>0) {
    		for(int i=0;i<q.size();i++) {
    			p=q.get(i);
    			s.append("pid:"+p.getpid()+"  时间:"+p.gettimes()+"  优先级:"+p.getpriority()+"  主存地址"+p.getaddress()+"  主存大小:"+p.getsize()+"  前驱:"+p.getpre()+"\r\n");  			
    	    }
    		return s;
        }
        else {
        	s.append("");
        	return s;
        }
	}
	//后备和挂起队列显示
	public static StringBuffer show2(Vector<PCB>q)
	{
		StringBuffer s=new StringBuffer();
		PCB p;
        if(q.size()>0) {
    		for(int i=0;i<q.size();i++) {
    			p=q.get(i);
    			s.append("pid:"+p.getpid()+"  时间:"+p.gettimes()+"  优先级:"+p.getpriority()+" 主存大小:"+p.getsize()+"  前驱:"+p.getpre()+"\r\n");  			
    	    }
    		return s;
        }
        else {
        	s.append("");
        	return s;
        }
	}
}
