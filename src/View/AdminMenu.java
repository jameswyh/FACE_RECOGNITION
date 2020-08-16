package View;


import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.opencv.core.Mat;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;



public class AdminMenu extends JFrame implements ActionListener{
	
	public static String TRAIN_PATH="TrainDatabase\\";
	public static String RECORD_PATH="FaceRecord\\";
	public static String DOT_JPG=".jpg";
	public static String THUMBS="Thumbs.db";
	static File folder = new File(TRAIN_PATH);
	
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	

	private JPanel imagePanel;
	private ImageIcon background;
	
	private JPanel btPanel = new JPanel();;
	
	private JFrame jf=new JFrame();
	
	public void showUI() {
		
		jf.setTitle("管理中心");
		jf.setSize(435,235);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
 
//		java.awt.FlowLayout fl=new FlowLayout(FlowLayout.CENTER,20,20);
//		jf.setLayout(fl);//流式布局
		
		background = new ImageIcon("img\\bg2.jpg");// 背景图片
		JLabel label = new JLabel(background);// 把背景图片显示在一个标签里面
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, background.getIconWidth(),
	    background.getIconHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		imagePanel = (JPanel) jf.getContentPane();
		imagePanel.setOpaque(false);
		// 内容窗格默认的布局管理器为BorderLayout
		imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT,5,55));
		
		 
		jf.getLayeredPane().setLayout(null);
		// 把背景图片添加到分层窗格的最底层作为背景
		jf.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
			
		Dimension dim=new Dimension(100, 40);//按钮的大小	
		
		btPanel.setPreferredSize(new Dimension(300, 150));
		btPanel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
		btPanel.setBackground(null);
		btPanel.setOpaque(false);
		
		
		
		button1=new JButton("人脸录入");
		button1.setPreferredSize(dim);
		btPanel.add(button1);
//		jf.add(button1);
		
		button2=new JButton("访客记录");
		button2.setPreferredSize(dim);
		btPanel.add(button2);
//		jf.add(button2);
		
		button3=new JButton("用户管理");
		button3.setPreferredSize(dim);
		btPanel.add(button3);
//		jf.add(button3);
		
		button4=new JButton("人脸库");
		button4.setPreferredSize(dim);
		btPanel.add(button4);
//		jf.add(button4);
		
		imagePanel.add(btPanel);
		
		
		
		jf.setVisible(true);
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source=event.getSource();
//		jf.dispose();
		if(source==button1) {
			RegisterMenu rm = new RegisterMenu();
			rm.showUI();
			}
		else if(source==button2) {
			HistoryRecord hr = new HistoryRecord();
			hr.showUI();
			}
		else if(source==button3) {
			UserManagement um = new UserManagement();
			um.showUI();
			}
		else if(source==button4) {
			FaceGrid fg = new FaceGrid();
			fg.FaceGrid();
		}
	}
}
	
