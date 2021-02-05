package os;

import java.util.ArrayList;
public class Memory {
	static int base=0;//主存起始地址
	static int space;//主存空间大小
	static ArrayList<Integer>memory=new ArrayList<Integer>();//构造未分分区表
	public Memory(int space)
	{
		this.space=space;
		for(int i=0;i<space;i++) {
			memory.add(space);
		}
	}
	//给进程分配内存
    public static boolean enough(PCB p)
    {
    	int size,tmp;
		for(int i=0;i<space;++i) {
			if(p.getsize() <= memory.get(i)) {		    
				p.setaddress(i); 
				size= p.getsize();
				tmp=memory.get(i);
				for(int j=i;j<i+size;++j) {
					memory.set(j,-1);					
				}
				for(int k=i+size;k<i+tmp;k++) {
					memory.set(k, tmp-size);
				}
				Show.jtm.setText(String.valueOf(show(memory)));
				return true;
			}
		}
		return false;
    }
	//进程执行完，释放其所占用的内存空间,并合并空闲分区
	public static void recover(PCB p)
	{
		int a=p.getaddress()+p.getsize();
		int b=p.getaddress()-1;
		for(;a<space;a++) {
			if(memory.get(a)==-1)break;
		}
		for(;b>=0;b--) {
			if(memory.get(b)==-1)break;
		}
		for(int k=b+1;k<a;k++) {
			memory.set(k, a-b-1);
		}
		Show.jtm.setText(String.valueOf(show(memory)));
	}
	//显示主存的状态
	public static StringBuffer show(ArrayList<Integer>memory)
	{
		StringBuffer s=new StringBuffer();
		for(int i=0;i<space;i++) {
			s.append(memory.get(i)+" ");
		}
		return s;
	}
}
	

