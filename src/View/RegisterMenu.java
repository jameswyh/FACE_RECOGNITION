package View;


import java.applet.Applet;
import java.applet.AudioClip;
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
import java.net.MalformedURLException;
import java.awt.event.ActionEvent;


public class RegisterMenu extends JFrame implements ActionListener{
	
	public static String TRAIN_PATH="TrainDatabase\\";
	public static String RECORD_PATH="FaceRecord\\";
	public static String DOT_JPG=".jpg";
	public static String THUMBS="Thumbs.db";
	static File folder = new File(TRAIN_PATH);
	
	private JButton button1;
	private JButton button2;

	private JFrame jf=new JFrame();
	
	private JPanel imagePanel;
	private ImageIcon background;
	
	private JPanel btPanel = new JPanel();;
	
	public void showUI() {
		
		jf.setTitle("人脸录入");
		jf.setSize(300,235);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		
		background = new ImageIcon("img\\bg3.jpg");// 背景图片
		JLabel label = new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),
	    background.getIconHeight());
		imagePanel = (JPanel) jf.getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT,20,70));
		
		btPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,15));
		btPanel.setPreferredSize(new Dimension(120, 120));
		
		btPanel.setBackground(null);
		btPanel.setOpaque(false);
		 
		jf.getLayeredPane().setLayout(null);
		jf.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		
		
//		jf.setLayout(new FlowLayout(FlowLayout.CENTER,50,10));
			
		Dimension dim=new Dimension(100, 40);//按钮大小	
		
	
		button1=new JButton("摄像头录入");
		button1.setPreferredSize(dim);
		btPanel.add(button1);
		
		
		button2=new JButton("图片录入");
		button2.setPreferredSize(dim);
		btPanel.add(button2);
		
		imagePanel.add(btPanel);
		
		jf.setVisible(true);
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source=event.getSource();
//		jf.dispose();
		if(source==button1) {
			EnterName en = new EnterName();
			en.showUI(1);
			}
		else if(source==button2) {
			EnterName en = new EnterName();
			en.showUI(2);
			}
		}
	}