package View;


import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DataBase.EmployeeDataBase;
import DataBase.RecordDataBase;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.awt.event.ActionEvent;

import java.text.SimpleDateFormat;


public class ResultMessage extends JFrame {
	
	private JFrame jf=new JFrame();
	private JPanel jp=new JPanel();
	private JLabel jl=new JLabel();
	EmployeeDataBase idb = new EmployeeDataBase();
	RecordDataBase rdb = new RecordDataBase();
	private String name = null;
	private String workid = null;
	private String dept = null;
	private Date date = null;
	private String face = null;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	File sounds = new File("sound\\success.wav");
	File soundf = new File("sound\\failure.wav");
	File soundb = new File("sound\\blacklist.wav");
	AudioClip audioClip;
	

	
	public void showUI(String faceid, String path) {
		
		jf.setTitle("人脸识别结果");
		
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		
		jf.setLayout(new FlowLayout(FlowLayout.CENTER,10,20));
		
		Dimension dim=new Dimension(50, 20);	
		Dimension dim1=new Dimension(120, 20);
		Dimension dim2=new Dimension(180, 20);
		Dimension dim3=new Dimension(250, 20);
		jp.setPreferredSize(new Dimension(200, 125));
		jp.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
		if(!faceid.equals("UNKNOWN")){	
			
			jf.setSize(340,220);
			
			jl.setIcon(new ImageIcon(path));
			jl.setSize(100,100);
			jf.add(jl);

			JLabel labname=new JLabel();
			labname.setText("姓名：");
			labname.setPreferredSize(dim);
			jp.add(labname);
			
			JLabel labname1=new JLabel();
			name = idb.GetName(faceid);
		    labname1.setText(name);
			labname1.setPreferredSize(dim1);
			jp.add(labname1);
			
			JLabel labnum=new JLabel();
			labnum.setText("工号：");
			labnum.setPreferredSize(dim);
			jp.add(labnum);
			
			JLabel labnum1=new JLabel();
			workid = idb.Getworkid(faceid);
			labnum1.setText(workid);
			labnum1.setPreferredSize(dim1);
			jp.add(labnum1);
			
			JLabel labdept=new JLabel();
			labdept.setText("部门：");
			labdept.setPreferredSize(dim);
			jp.add(labdept);
			
			JLabel labdept1=new JLabel();
			dept = idb.GetDept(faceid);
			labdept1.setText(dept);
			labdept1.setPreferredSize(dim1);
			jp.add(labdept1);
			
			JLabel labtime=new JLabel();
			labtime.setText("时间：");
			labtime.setPreferredSize(dim);
			jp.add(labtime);
			
			JLabel labtime1=new JLabel();
			date = new Date();
			labtime1.setText(df.format(date));
			labtime1.setPreferredSize(dim1);
			jp.add(labtime1);
			
			JLabel labresult=new JLabel();
			
			if(idb.GetBlackList(workid).equals("否")){
				labresult.setText("身份认证成功！欢迎光临！");
				labresult.setPreferredSize(dim2);
				rdb.InsertRecord(workid,dept,name,df.format(date).toString(),path,"通过");

				try {
					audioClip = Applet.newAudioClip(sounds.toURL());
					audioClip.play();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				labresult.setText("对不起！您没有权限进入！");
				labresult.setPreferredSize(dim2);
				rdb.InsertRecord(workid,dept,name, df.format(date).toString(), path,"拦截");
				try {
					audioClip = Applet.newAudioClip(soundb.toURL());
					audioClip.play();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			jp.add(labresult);
			jf.add(jp);
			
		}
		else
			jf.setSize(340,100);
	
		JLabel labresult=new JLabel();
		if(faceid.equals("UNKNOWN")){
			labresult.setText("身份认证失败，请重试或联系系统管理员！");
			labresult.setPreferredSize(dim3);
			try {
				audioClip = Applet.newAudioClip(soundf.toURL());
				audioClip.play();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jf.add(labresult);
		}
		
		
 		jf.setVisible(true);
	}
}