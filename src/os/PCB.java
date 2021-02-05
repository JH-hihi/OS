package os;

public class PCB {
	private int pid;//进程名
	private int times;//需要运行时间
	private int priority;//优先级
	private int state;//进程的状态，就绪为0，运行为1，阻塞为2.后备为3，挂起为4
	private int size;//所需内存大小
	private int address;//内存起址
	private int pre;//进程的前驱
	
	public PCB() {
		
	}
    public PCB(int pid,int times,int priority,int size,int pre)
    {
    	this.pid=pid;
    	this.times=times;
    	this.priority=priority;
    	this.state=3;
    	this.size=size; 
    	this.pre=pre;
    }
    //设置对应的属性
    void setpid(int p)
    {
    	pid=p;
    }
    void settimes(int t)
    {
    	times=t;
    }
    void setpriority(int pr)
    {
    	priority=pr;
    }
    void setstate(int s)
    {
    	state=s;
    }
    void setsize(int si)
    {
    	size=si;
    }
    void setaddress(int a)
    {
    	address=a;
    }
    void setpre(int p)
    {
    	pre=p;
    }
  //获取对应的各个属性的值
    int getpid()
    {
    	return pid;
    }
    int gettimes()
    {
    	return times;
    }
    int getsize()
    {
    	return size;
    }
    int getpriority()
    {
    	return priority;
    }
    int getpre()
    {
    	return pre;
    }
    int getaddress()
    {
    	return address;
    }
    int getstate()
    {
    	return state;
    }
}
