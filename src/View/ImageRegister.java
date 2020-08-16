package View;

import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.*;


import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

import Util.ImageFileFilter;


public class ImageRegister extends JFrame implements ActionListener,MouseListener{
	
	
	String storePath = "cache\\";
	String Path = null;
	int shotcount = 0;
	JButton btn = null;
    JTextField textField = null;
    JPanel panelup = new JPanel();
    JPanel panelleft = new JPanel();
    JPanel panelright = new JPanel();
    
    JLabel jlimage = new JLabel();
    JScrollPane jsp = new JScrollPane();
    
    String workid;
    String name;
    String faceid;
    
    Mat mat = new Mat();
    Mat mat1 = new Mat();
    Mat mat2 = new Mat();
    Mat mat3 = new Mat();
    Mat mat4 = new Mat();

	private CascadeClassifier faceDetector = new CascadeClassifier();
	
	
	public void showui(String faceid, String workid, String name){
		
		this.workid = workid;
		this.name = name;
		this.faceid = faceid;
		
		this.setTitle("图片录入");
		this.setSize(600,600);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
//		this.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		
		JLabel label = new JLabel("请选择图片文件：");
        textField = new JTextField(30);
        btn = new JButton("浏览");
        
        btn.addActionListener(this);
        panelup.add(label);
        panelup.add(textField);
        panelup.add(btn);
        
        
        
        jlimage.setPreferredSize(new Dimension(450,450));
        
        
        
        panelleft.add(jlimage);
        panelright.setLayout(new FlowLayout(FlowLayout.LEFT,0,5));
        
        this.add(panelup,BorderLayout.NORTH);
        this.add(panelleft,BorderLayout.CENTER);
        this.add(jsp,BorderLayout.EAST);
        
	}
	
	private void initDetector() throws UnsupportedEncodingException
	{
		faceDetector.load("C://Program Files//opencv//build//etc//haarcascades//haarcascade_frontalface_alt2.xml");
//		eyeDetector.load("C://Program Files//opencv//build//etc//haarcascades//haarcascade_eye_tree_eyeglasses.xml");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 JFileChooser chooser = new JFileChooser("C://Users//james_000//Desktop//testphoto");
		 chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	     ImageFileFilter Filter = new ImageFileFilter();
	     chooser.addChoosableFileFilter(Filter);
	     chooser.setFileFilter(Filter);
	     if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	 panelright.removeAll();
	 		try {
				initDetector();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	         File file = chooser.getSelectedFile();
	         textField.setText(file.getAbsoluteFile().toString());
	         
	         
			 mat = imread(textField.getText());
			 RectVector faces = new RectVector();
			 
			 double height = mat.arrayHeight();
		     double width = mat.arrayWidth();
		     double ra;
		     Mat resizemat = new Mat();
		     if(height<width){
		    	 ra = 400/width;
		    	 System.out.println(width);
		    	 System.out.println(450/width);
		    	 org.bytedeco.javacpp.opencv_imgproc.resize(mat, resizemat, new Size(400,(int)(height*ra)), 0, 0, INTER_LINEAR);
		     }
		     else{
		    	 ra = 400/height;
		    	 org.bytedeco.javacpp.opencv_imgproc.resize(mat, resizemat, new Size((int)(width*ra),400), 0, 0, INTER_LINEAR);
		     }
			 
			 cvtColor(resizemat, mat1, COLOR_RGB2GRAY);//灰度化
//			 
			 faceDetector.detectMultiScale(mat1, faces);
			 JLabel jl[] = new JLabel[(int)faces.size()];
			 if(!faces.empty())
				{
					for(int i=0; i<faces.size(); i++)
					{
						Rect r = faces.get(i);
			            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
			            rectangle(resizemat, new Point(x, y), new Point(x + w, y + h), Scalar.GREEN, 1, CV_AA, 0);
		            	Mat roi_img = new Mat(mat1,r);
			            Mat resizemat1 = new Mat();
			            org.bytedeco.javacpp.opencv_imgproc.resize(roi_img, resizemat1, new Size(100,100), 0, 0, INTER_LINEAR);
			            equalizeHist(resizemat1, mat2);//直方图均衡化
			            bilateralFilter(mat2,mat3,7,15,15);
			            storeImg(mat3);
			            jl[i] = new JLabel(new ImageIcon(Path));
			            jl[i].setName(String.valueOf(shotcount));
			            jl[i].setSize(100, 100);
			            jl[i].addMouseListener(this);
			            panelright.add(jl[i]);
			            roi_img.close();
					}
				} 
			 storeImg(resizemat);
			 ImageIcon icon = new ImageIcon(Path);
		     Image image = icon.getImage();
//		     double height = image.getHeight(icon.getImageObserver());
//		     double width = image.getWidth(icon.getImageObserver());
//		     double r;
//		     Image resizeimage = null;
//		     if(height<width){
//		    	 r = 400/width;
//		    	 System.out.println(width);
//		    	 System.out.println(450/width);
//		    	 resizeimage =  image.getScaledInstance(400,(int)(height*r),Image.SCALE_FAST);
//		     }
//		     else{
//		    	 r = 400/height;
//		    	 resizeimage =  image.getScaledInstance((int)(width*r),400,Image.SCALE_FAST);
//		     }
			 jlimage.setIcon(new ImageIcon(image));
			 jlimage.setText("检测到"+faces.size()+"张人脸，请在右侧选择需录入的人脸");
			 jlimage.setHorizontalAlignment(JLabel.CENTER);
			 jlimage.setVerticalTextPosition(JLabel.BOTTOM);
			 jlimage.setHorizontalTextPosition(JLabel.CENTER);
			 panelright.setPreferredSize(new Dimension(100,(int)faces.size()*150));
			 jsp.setViewportView(panelright);
			 jsp.setPreferredSize(new Dimension(120,400));
			 
	     }

	}
	private void storeImg(Mat mat)
	{
		Path = storePath+(shotcount)+".jpg";
		File file = new File(Path);
        while(file.exists()){
        	this.shotcount++;
        	Path = storePath+(shotcount)+".jpg";
        	file = new File(Path);
        }
		imwrite(Path, mat);
		System.out.println(Path+" SAVED");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getComponent().getName());
		if(e.getComponent().getName()!=null){
            ImageConfirm ic = new ImageConfirm();
            ic.imshow(Integer.parseInt(e.getComponent().getName()),name,workid,faceid); 
    	}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}



