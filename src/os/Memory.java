package os;

import java.util.ArrayList;
public class Memory {
	static int base=0;//������ʼ��ַ
	static int space;//����ռ��С
	static ArrayList<Integer>memory=new ArrayList<Integer>();//����δ�ַ�����
	public Memory(int space)
	{
		this.space=space;
		for(int i=0;i<space;i++) {
			memory.add(space);
		}
	}
	//�����̷����ڴ�
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
	//����ִ���꣬�ͷ�����ռ�õ��ڴ�ռ�,���ϲ����з���
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
	//��ʾ�����״̬
	public static StringBuffer show(ArrayList<Integer>memory)
	{
		StringBuffer s=new StringBuffer();
		for(int i=0;i<space;i++) {
			s.append(memory.get(i)+" ");
		}
		return s;
	}
}
	

