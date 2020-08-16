package View;
import org.opencv.core.Mat;

import DataBase.FaceDataBase;
import Model.FaceModel;

import javax.swing.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class ImageViewer extends JFrame implements ActionListener {
	
	private int no;
	
	private JPanel jpface = new JPanel();
	private JPanel jpinfo = new JPanel();
	private JLabel jlface;
    private JLabel jlworkid = new JLabel();
    private JLabel jlname = new JLabel();
    private JLabel jldate = new JLabel();
    private JButton btdelete;
    private JFrame jf=new JFrame();
    private JLabel lb=new JLabel();
    
    private JButton btconfirm = new JButton("确认");
    private JButton btcancel = new JButton("取消");
    
    FaceDataBase fdb = new FaceDataBase();
  	
    public void imshow(int no) {
    	
    	this.no = no;
        this.setTitle("人脸详情");
        this.setSize(350,300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
		
        FaceModel fm = new FaceModel();
        
        fm = fdb.SelectFace(no);
		
		if(fm.getEmname()==null){
			JLabel jl = new JLabel("该人脸已被删除");
			this.setSize(300,80);
			this.add(jl);
		}
		else{
			 this.setSize(350,300);
			 ImageIcon face = new ImageIcon(fm.getFacepath());
		     Image image = face.getImage();
		     Image bigface =  image.getScaledInstance(200,200,Image.SCALE_FAST);    
		     jlface = new JLabel(new ImageIcon(bigface));			
		     jpface.add(jlface);			
		     this.add(jpface);
							
		     JLabel jl1 = new JLabel("工号:");     
		     jlworkid.setText(fm.getEmworkid());			
		     JLabel jl2 = new JLabel("姓名:");					
		     jlname.setText(fm.getEmname());				
		     JLabel jl3 = new JLabel("采集日期:");				
		     jldate.setText(fm.getFacedate());
								
		     jpinfo.setSize(250,200);				
		     jpinfo.add(jl1);				
		     jpinfo.add(jlworkid);				
		     jpinfo.add(jl2);				
		     jpinfo.add(jlname);				
		     jpinfo.add(jl3);				
		     jpinfo.add(jldate);				
		     btdelete = new JButton("删除");				
		     btdelete.addActionListener(this);
			    				
		     jpinfo.add(btdelete);				
		     jpinfo.setLayout(new FlowLayout(FlowLayout.CENTER));				
		     this.add(jpinfo);
					    				
		     btconfirm.addActionListener(this);	
		     btcancel.addActionListener(this);
		}
    }

    public void actionPerformed(ActionEvent event) {
        
    	Object source=event.getSource();
//		jf.dispose();
		if(source==btdelete) {
	    	
	    	jf.setTitle("删除人脸");
			
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setResizable(false);
			jf.setSize(300,130); 
			jf.setVisible(true);
			
			lb.setText("删除后不可恢复，确认要删除吗？");
			lb.setPreferredSize(new Dimension(220, 30));
			
			jf.add(lb);
			jf.add(btconfirm);
			jf.add(btcancel);
			
			jf.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		}
		
		else if(source==btconfirm){
			
			FaceDataBase fd = new FaceDataBase();
			fd.DeleteFace(no);
			
			jf.dispose();
			this.dispose();
			
		}
		else if(source==btcancel){
			jf.dispose();
		}
    	
		}
}