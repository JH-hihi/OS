package os;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Show extends JFrame implements Runnable {
	static int flag=1;
	JFrame j=new JFrame("���н���");
	JPanel p=new JPanel(null);
	JLabel jr=new JLabel("�������� ");
	JLabel jp=new JLabel("�󱸶���");
	JLabel jb=new JLabel("��������");
	JLabel jh=new JLabel("������� ");
	JLabel j1=new JLabel("�����1");
	JLabel j2=new JLabel("�����2");	
	JLabel jm=new JLabel("�ڴ�");
	JLabel j11=new JLabel("���ս�������ʱ�䡢���ȼ����ڴ��С��ǰ��˳������");
	JButton jb1=new JButton("���");
	JButton jb2=new JButton("���");
	JButton jb3=new JButton("����");
	JButton jb4=new JButton("����");
	static JTextField jt1=new JTextField();//�����1�ı���
	static JTextField jt2=new JTextField();//�����2�ı���
	static JTextField jt11=new JTextField();//��ȡ����
	JTextArea jtr=new JTextArea();//����������ʾ
	JTextArea jtp=new JTextArea();//�󱸶�����ʾ
	JTextArea jtb=new JTextArea();//����������ʾ
	JTextArea jth=new JTextArea();//���������ʾ
	static JTextArea jtm=new JTextArea();//�ڴ���ʾ
	public void run() {
		while(flag>0) {
			if(CPU.ready.size()+CPU.block.size()+CPU.hang.size()>0) {
				jtr.setText(String.valueOf(CPU.show1(CPU.ready)));
				jtp.setText(String.valueOf(CPU.show2(CPU.newp)));
				jtb.setText(String.valueOf(CPU.show1(CPU.block)));
				jth.setText(String.valueOf(CPU.show2(CPU.hang)));
			}
			else {
				jtr.setText("");
				jtp.setText("");
				jth.setText("");
				jtb.setText("");
				jt1.setText("");
				jt2.setText("");
			}
		}
		jtr.setText("");
		jtp.setText("");
		jth.setText("");
		jtb.setText("");
		jt1.setText("");
		jt2.setText("");
	}
    
	public Show() {
		j.setSize(1000,900);
		j.setLocation(500,100);	
		//���ø������λ�úʹ�С
		jr.setBounds(50,0,70,100);
		jp.setBounds(50,100,70,100);
		jb.setBounds(50,200,70,100);;
		jh.setBounds(50,300,70,100);
		j1.setBounds(50,400,70,100);
		j2.setBounds(50,470,70,100);
		j11.setBounds(50,650,500,60);
		jm.setBounds(70,550,70,100);
		//���ð�ť��λ�úʹ�С
		jb1.setBounds(100, 750, 60, 40);
		jb2.setBounds(150, 750,60, 40);
		jb3.setBounds(200, 750, 60, 40);
		jb4.setBounds(250, 750, 60, 40);
		//�ı���λ�úʹ�С
		jtr.setBounds(110,40,400,90);
		jtp.setBounds(110,140,400,90);
		jtb.setBounds(110,240,400,90);
		jth.setBounds(110,340,400,90);
		jt1.setBounds(110,440,400,50);
		jt2.setBounds(110,500,400,50);
		jt11.setBounds(50,700,400,50);
		jtm.setBounds(110,590,860,50);
		//Ϊ��ť����ӡ�����¼�
		jb1.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  String ss=jt11.getText();
		    	  jt11.setText("");
		    	  if(CPU.insert(ss)==false) {
		    		  jt11.setText("�������");
		    	  }
		    	  else {
		    		  jtp.setText(String.valueOf(CPU.show2(CPU.newp)));
		    	  }
		    	  
		    }
		});
		//Ϊ��ť����ҡ�����¼�
		jb2.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  String ss=jt11.getText();
		    	  jt11.setText("");
		    	  if(CPU.move(ss)==false) {
		    		  jt11.setText("�ڴ治��");
		    	  }	
		    	  else jt11.setText("");
		    }
		});
		//Ϊ��ť����������¼�
		jb3.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  String ss=jt11.getText();
		    	  jt11.setText("");
	              CPU.hang(ss);
	              jth.setText(String.valueOf(CPU.show2(CPU.hang)));
		      }
		});
		//Ϊ��ť������������¼�
		jb4.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  flag=0;
		    	  int i=JOptionPane.showConfirmDialog(null, "�˳�ϵͳ��");
		    	  if(i==0) {
		    		  System.exit(0);
		    	  }
		      }
		});
		p.add(jr);
		p.add(jtr);
		p.add(jp);
		p.add(jtp);
		p.add(jb);
		p.add(jtb);
		p.add(jh);
		p.add(jth);
		p.add(j1);
		p.add(jt1);
		p.add(j2);
		p.add(jt2);
		p.add(jb1);
		p.add(jb2);
		p.add(jb3);
		p.add(jb4);
		p.add(j11);
		p.add(jt11);
		p.add(jm);
		p.add(jtm);
	    j.add(p);
		j.setContentPane(p);
		j.setLayout(null);
		j.setVisible(true);  //ʹ����ɼ�
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//���ô���رշ�ʽ	
	}
	  public static void main(String[] args) {  
		  	new Memory(50);	
		  	Show w=new Show();
		  	Thread t=new Thread(w);
		  	t.start();
		  	CPU c = new CPU();
		  	Thread t1=new Thread(c,"op");
		  	Thread t2=new Thread(c,"pro1");
		  	Thread t3=new Thread(c,"pro2");
		  	t1.start();
		  	t2.start();
		  	t3.start();
	  }

}
