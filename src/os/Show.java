package os;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Show extends JFrame implements Runnable {
	static int flag=1;
	JFrame j=new JFrame("运行界面");
	JPanel p=new JPanel(null);
	JLabel jr=new JLabel("就绪队列 ");
	JLabel jp=new JLabel("后备队列");
	JLabel jb=new JLabel("阻塞队列");
	JLabel jh=new JLabel("挂起队列 ");
	JLabel j1=new JLabel("处理机1");
	JLabel j2=new JLabel("处理机2");	
	JLabel jm=new JLabel("内存");
	JLabel j11=new JLabel("按照进程名、时间、优先级、内存大小、前驱顺序输入");
	JButton jb1=new JButton("添加");
	JButton jb2=new JButton("解挂");
	JButton jb3=new JButton("挂起");
	JButton jb4=new JButton("结束");
	static JTextField jt1=new JTextField();//处理机1文本框
	static JTextField jt2=new JTextField();//处理机2文本框
	static JTextField jt11=new JTextField();//获取输入
	JTextArea jtr=new JTextArea();//就绪队列显示
	JTextArea jtp=new JTextArea();//后备队列显示
	JTextArea jtb=new JTextArea();//阻塞队列显示
	JTextArea jth=new JTextArea();//挂起队列显示
	static JTextArea jtm=new JTextArea();//内存显示
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
		//设置各组件的位置和大小
		jr.setBounds(50,0,70,100);
		jp.setBounds(50,100,70,100);
		jb.setBounds(50,200,70,100);;
		jh.setBounds(50,300,70,100);
		j1.setBounds(50,400,70,100);
		j2.setBounds(50,470,70,100);
		j11.setBounds(50,650,500,60);
		jm.setBounds(70,550,70,100);
		//设置按钮的位置和大小
		jb1.setBounds(100, 750, 60, 40);
		jb2.setBounds(150, 750,60, 40);
		jb3.setBounds(200, 750, 60, 40);
		jb4.setBounds(250, 750, 60, 40);
		//文本框位置和大小
		jtr.setBounds(110,40,400,90);
		jtp.setBounds(110,140,400,90);
		jtb.setBounds(110,240,400,90);
		jth.setBounds(110,340,400,90);
		jt1.setBounds(110,440,400,50);
		jt2.setBounds(110,500,400,50);
		jt11.setBounds(50,700,400,50);
		jtm.setBounds(110,590,860,50);
		//为按钮“添加”添加事件
		jb1.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  String ss=jt11.getText();
		    	  jt11.setText("");
		    	  if(CPU.insert(ss)==false) {
		    		  jt11.setText("输入错误");
		    	  }
		    	  else {
		    		  jtp.setText(String.valueOf(CPU.show2(CPU.newp)));
		    	  }
		    	  
		    }
		});
		//为按钮“解挂”添加事件
		jb2.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  String ss=jt11.getText();
		    	  jt11.setText("");
		    	  if(CPU.move(ss)==false) {
		    		  jt11.setText("内存不足");
		    	  }	
		    	  else jt11.setText("");
		    }
		});
		//为按钮“挂起”添加事件
		jb3.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  String ss=jt11.getText();
		    	  jt11.setText("");
	              CPU.hang(ss);
	              jth.setText(String.valueOf(CPU.show2(CPU.hang)));
		      }
		});
		//为按钮“结束”添加事件
		jb4.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		    	  flag=0;
		    	  int i=JOptionPane.showConfirmDialog(null, "退出系统？");
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
		j.setVisible(true);  //使窗体可见
		j.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置窗体关闭方式	
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
