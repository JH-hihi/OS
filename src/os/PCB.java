package os;

public class PCB {
	private int pid;//������
	private int times;//��Ҫ����ʱ��
	private int priority;//���ȼ�
	private int state;//���̵�״̬������Ϊ0������Ϊ1������Ϊ2.��Ϊ3������Ϊ4
	private int size;//�����ڴ��С
	private int address;//�ڴ���ַ
	private int pre;//���̵�ǰ��
	
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
    //���ö�Ӧ������
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
  //��ȡ��Ӧ�ĸ������Ե�ֵ
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
