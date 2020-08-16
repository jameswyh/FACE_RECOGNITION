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

import FaceRecognition.FaceDetection;
import FaceRecognition.PreRecognition;
import Model.EigenFaceCore;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.awt.event.ActionEvent;


public class Menu extends JFrame implements ActionListener{
	
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
	 
	 
	
	int firsttime = 0;
	
	File soundwl = new File("sound\\welcome.wav");
//	File soundin = new File("sound\\initial.wav");
	AudioClip audioClip;
	
	public void showUI() {
		
		jf.setTitle("人脸识别门禁管理系统");
		jf.setSize(500,235);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		
		background = new ImageIcon("img\\background.jpg");// 背景图片
		JLabel label = new JLabel(background);
		label.setBounds(0, 0, background.getIconWidth(),
		background.getIconHeight());
		imagePanel = (JPanel) jf.getContentPane();
		imagePanel.setOpaque(false);
		imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT,50,120));
		jf.getLayeredPane().setLayout(null);
		jf.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		
//		FlowLayout fl=new FlowLayout(FlowLayout.CENTER,50,10);
//		jf.setLayout(fl);//流式布局
			
		Dimension dim=new Dimension(100, 40);//按钮的大小	
		
	
		button1=new JButton("人脸识别");
		button1.setPreferredSize(dim);
		imagePanel.add(button1);
//		jf.add(button1);
		
		
		button2=new JButton("管理中心");
		button2.setPreferredSize(dim);
		imagePanel.add(button2);
//		jf.add(button2);
		
		jf.setVisible(true);
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		
		try {
		    audioClip = Applet.newAudioClip(soundwl.toURL());
			audioClip.play();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void actionPerformed(ActionEvent event) {
		Object source=event.getSource();
//		jf.dispose();
		if(source==button1) {
//			if(firsttime == 0)
//			{
//				try {
//		 			audioClip = Applet.newAudioClip(soundin.toURL());
//		 			audioClip.play();
//		 		} catch (MalformedURLException e) {
//		 			// TODO Auto-generated catch block
//		 			e.printStackTrace();
//		 		}
//				firsttime = 1;
//			}
	        Mat matVec = PreRecognition.CreateDatabase(TRAIN_PATH,PreRecognition.foldercount());
			EigenFaceCore eigenFaceCore = PreRecognition.eigenFaceCore(matVec);
			FaceDetection fd = new FaceDetection();
			fd.videoCapture(RECORD_PATH, eigenFaceCore);
			}
		else if(source==button2) {
			AdminLogin al = new AdminLogin();
			al.showUI();
			}
		}
	}

