package View;

import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgcodecs.*;

import DataBase.FaceDataBase;

import javax.swing.*;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ImageConfirm extends JFrame implements ActionListener {
	
	private int no;
	
	private JPanel jpface = new JPanel();
	private JPanel jpinfo = new JPanel();
	private JLabel jlface;
    private JLabel jlworkid = new JLabel();
    private JLabel jlname = new JLabel();
    private JLabel jldate = new JLabel();
    private String workid = null;
    private String name = null;
    private String faceid = null;
    private String date = null;
    private String path = null;
    private JFrame jf=new JFrame();
    private JLabel lb=new JLabel();
    
    private JButton btconfirm = new JButton("确认");
    private JButton btcancel = new JButton("取消");
    
    FaceDataBase fdb = new FaceDataBase();
    
    String storePath = "cache\\";

    public void imshow(int no, String name, String workid, String faceid) {
    	
    	this.no = no;
    	this.name = name;
    	this.workid = workid; 
    	this.faceid = faceid;
        this.setTitle("人脸信息确认");
        this.setSize(350,350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
        
        this.setSize(350,300);
		ImageIcon face = new ImageIcon(storePath+(no)+".jpg");
	    Image image = face.getImage();
	    Image bigface =  image.getScaledInstance(200,200,Image.SCALE_FAST);    
	    jlface = new JLabel(new ImageIcon(bigface));			
	    jpface.add(jlface);			
	    this.add(jpface);
					
	    JLabel jl1 = new JLabel("工号:");     
	    jlworkid.setText(workid);			
	    JLabel jl2 = new JLabel("姓名:");					
	    jlname.setText(name);				
     
	    jpinfo.setSize(250,200);				
	    jpinfo.add(jl1);				
	    jpinfo.add(jlworkid);				
	    jpinfo.add(jl2);				
	    jpinfo.add(jlname);								
			
	    btconfirm.addActionListener(this);
	    btcancel.addActionListener(this);
	    
	    
        jpinfo.add(btconfirm);	
        jpinfo.add(btcancel);
        
        
        jpinfo.setLayout(new FlowLayout(FlowLayout.CENTER));				
        this.add(jpinfo);
    }

    public void actionPerformed(ActionEvent event) {
        
    	Object source=event.getSource();
//		jf.dispose();
		if(source==btconfirm) {
			Mat mat = new Mat();
			mat = org.bytedeco.javacpp.opencv_imgcodecs.imread(storePath+(no)+".jpg");
			storeFace(mat);
			this.dispose();
	    	jf.setTitle("录入成功");
			
			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jf.setLocationRelativeTo(null);
			jf.setResizable(false);
			jf.setSize(300,100); 
			jf.setVisible(true);
			
			lb.setText("人脸图像录入成功");
			lb.setPreferredSize(new Dimension(220, 30));
			
			jf.add(lb);
			
			jf.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		}
		
		else if(source==btcancel){
			this.dispose();
		}	
    }
    
    private void storeFace(Mat mat)
	{
    	int facecount = 0;
		File file =new File("TrainDatabase//"+ faceid); 
		//如果文件夹不存在则创建 
		if  (!file.exists() && !file.isDirectory())   
		{    
		    System.out.println("创建新人脸文件夹");
		    file.mkdir(); 
		} else
		{
		    System.out.println("更新已存在人脸文件夹");
		}
		
		String faceshotPath = "TrainDatabase//"+ faceid +"//"+ (facecount)+".jpg";
		File f = new File(faceshotPath);
        while(f.exists()){
        	facecount++;
        	faceshotPath = "TrainDatabase//"+ faceid +"//"+ (facecount)+".jpg";
        	f = new File(faceshotPath);
        }
        org.bytedeco.javacpp.opencv_imgcodecs.imwrite(faceshotPath, mat);
		System.out.println(faceshotPath+" SAVED");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		fdb.InsertFace(workid, name, sdf.format(new Date()), faceshotPath);
		facecount++;
	}
}