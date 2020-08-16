package View;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DataBase.AdminDataBase;

public class AdminLogin extends JFrame implements ActionListener{
	
	private Button button;
	private JFrame jf=new JFrame();
	
	JTextField textname=new JTextField();
	JPasswordField textpw=new JPasswordField();
	
	Dimension dim=new Dimension(55, 50);	
	Dimension dim1=new Dimension(250, 30);
	Dimension dim2=new Dimension(100, 40);
	
	AdminDataBase adb = new AdminDataBase();
	
	public void showUI() {
		
		jf.setTitle("登录管理员账号");
		jf.setSize(340,200);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
 
		java.awt.FlowLayout fl=new java.awt.FlowLayout(FlowLayout.CENTER,5,5);
		jf.setLayout(fl);//流式布局
		
		//设置格式大小		

		
	
		JLabel labname=new JLabel();
		labname.setText("用户名：");
		labname.setPreferredSize(dim);
		jf.add(labname);
		
		
		textname.setPreferredSize(dim1);
		jf.add(textname);
		
		JLabel labpw=new JLabel();
		labpw.setText("密码：");
		labpw.setPreferredSize(dim);
		jf.add(labpw);
		
		
		textpw.setPreferredSize(dim1);
		jf.add(textpw);
		
		button=new Button("登录");
		button.setPreferredSize(dim2);
		jf.add(button);
		
		jf.setVisible(true);
		
		//对当前窗体添加监听方法
	
		button.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source=event.getSource();
		if(source==button) {
			if(adb.AdminLogin(textname.getText(),textpw.getText())){
				AdminMenu am = new AdminMenu();
				am.showUI();
				jf.dispose();
			}
			else{
				JFrame jf=new JFrame();
				jf.setTitle("登录失败");
				jf.setSize(340,100);
				jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				jf.setLocationRelativeTo(null);
				jf.setResizable(false);
				
				java.awt.FlowLayout fl=new java.awt.FlowLayout(FlowLayout.CENTER,5,5);
				jf.setLayout(fl);//流式布局
				
				JLabel labwarning=new JLabel();
				labwarning.setText("登录失败，请重新登录！");
				jf.add(labwarning);
				java.awt.Dimension dim=new java.awt.Dimension(300, 50);	
				labwarning.setPreferredSize(dim);
					
				jf.setVisible(true);	
			}
			}
		}
	}