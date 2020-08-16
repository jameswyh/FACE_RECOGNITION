package FaceRecognition;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.*;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import DataBase.EmployeeDataBase;
import FaceRecognition.FaceRecognition;
import Model.EigenFaceCore;
import View.ResultMessage;

public class FaceDetection {
	private int facecount = 0;
	private double framecount = 0;
	private String storePath = null;
	private String faceshotPath = null;
	private CascadeClassifier faceDetector = new CascadeClassifier();
	private OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	CanvasFrame canvas = new CanvasFrame("Face Recognition");//新建一个窗口
	OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);
	
	private CascadeClassifier eyeDetector = new CascadeClassifier();
	
	private int blinkcount = 0;
	private int eyeframecount = 0;
	private int eyeflag = 0;//0睁眼，1闭眼
	
	private int fdcount = 0;//人脸检测计数
	
	private String Result = null;	
	private String faceid = null;
	
	private boolean resultflag = false;
	
	private EigenFaceCore eigenFaceCore = null;
	
	ResultMessage rs = new ResultMessage();
	
	Mat mat = new Mat();
	Mat mat1 = new Mat();
	Mat mat2 = new Mat();
	Mat mat3 = new Mat();
	Mat mat4 = new Mat();
	Mat resizemat = new Mat();
    Mat tmp_img = new Mat();
    Mat gray_img = new Mat();
    Mat roi_img = null;
    RectVector faces = new RectVector();
    RectVector eyes = new RectVector();
    
    int fcount = 0;
    int count = 0;
    long begintime;
    long endtime;
    
    AudioClip audioClip;
//    File soundin = new File("sound\\initial.wav");
    File soundst = new File("sound\\start.wav");
    File soundb1 = new File("sound\\blink1.wav");
    File soundb2 = new File("sound\\blink2.wav");
    File soundf = new File("sound\\far.wav");
    File soundn = new File("sound\\near.wav");
    
    long soundtime = 0;
    int distflag = 0;
    
	//视频捕捉
	public void videoCapture(String storePath, EigenFaceCore eigenFaceCore)
	{
		this.storePath = storePath;
		this.eigenFaceCore = eigenFaceCore;

		new Thread(new Runnable() {
            @Override
            public void run() {
            	try
                {
                	//初始化人脸检测模型
            		initDetector();
            		System.out.println("INIT DETECTOR");
  
                    grabber.start();   //开始获取摄像头数据
                                    
                    canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    canvas.setAlwaysOnTop(true);

            		try {
            			audioClip = Applet.newAudioClip(soundst.toURL());
            			audioClip.play();
            		} catch (MalformedURLException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                    count = 0;
                    begintime = System.currentTimeMillis();
                    while(count < 180 && !resultflag){//180帧约6s
                        if(!canvas.isDisplayable()){//窗口是否关闭
                        	faceDetector.close();
                            grabber.stop();
                            grabber.close();
//                            System.exit(2);
                            break;
                        }
//                        canvas.showImage(grabber.grab());
                        count++;
                        canvas.showImage(faceDetection(grabber.grab()));
                        fcount++;
                        endtime = System.currentTimeMillis();
                        if ((endtime-begintime)/1000>=1)
                        {
                        	System.out.println("FPS:"+ fcount);
                        	begintime = System.currentTimeMillis();
                        	fcount = 0;
                        }
                    }	
//                    faceDetector.close();
                    
                    if(resultflag){
                   		try {
                			audioClip = Applet.newAudioClip(soundb1.toURL());
                			audioClip.play();
                		} catch (MalformedURLException e) {
                			// TODO Auto-generated catch block
                			e.printStackTrace();
                		}
                    }
                    
                    count = 0;
                    begintime = System.currentTimeMillis();
                    while(count < 180 && blinkcount<3 && resultflag){//180帧约6s
                    	if(!canvas.isDisplayable()){//窗口是否关闭
                        	eyeDetector.close();
                            grabber.stop();
                            grabber.close();
//                            System.exit(2);
                            break;
                        } 
                    	
//                    soundtime = 0;
                    canvas.showImage(eyeDetection(grabber.grab()));
                    count++;
                    fcount++;
                    endtime = System.currentTimeMillis();
                    if ((endtime-begintime)/1000>=1)
                    {
                    	System.out.println("FPS:"+ fcount);
                    	begintime = System.currentTimeMillis();
                    	fcount = 0;
                    }
                    }
                    
                    if(blinkcount<3)
                    	Result = "UNKNOWN";
                    rs.showUI(Result,faceshotPath);
                    eyeDetector.close();
                    grabber.stop();
                    grabber.close();
                    canvas.dispose();
                }
                catch(Exception e)
                {
                	System.err.println("GRAB EXCEPTION");
                }
            }
        }).start();
		
	}

	//初始化人脸检测器、人眼检测器
	private void initDetector() throws UnsupportedEncodingException
	{
		faceDetector.load("C://Program Files//opencv//build//etc//haarcascades//haarcascade_frontalface_default.xml");
		eyeDetector.load("C://Program Files//opencv//build//etc//haarcascades//haarcascade_eye_tree_eyeglasses.xml");
	}
	
	
	//人脸检测
	private Frame faceDetection(Frame frame)
	{
		opencv_core.flip(converter.convertToMat(frame), mat, 1);
		resize(mat, mat1, new Size(320,240), 0, 0, INTER_LINEAR);//缩小尺寸
		cvtColor(mat1, mat2, COLOR_RGB2GRAY);//灰度化
//		equalizeHist(mat2, mat3);//直方图均衡化
//		bilateralFilter(mat3,mat4,-1,8,8);//高斯双边模糊
		
		faces.clear();
		
		faceDetector.detectMultiScale(mat2, faces);

		if(!faces.empty())
		{
			//矩形圈出人脸
			Rect r = faces.get(0);
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            
//            System.out.println(w);
//            System.out.println(h);
            
            if(w>160&&(System.currentTimeMillis()-soundtime)>2000){
            	soundtime = System.currentTimeMillis();
            	distflag = 1;
            	try {
         			audioClip = Applet.newAudioClip(soundn.toURL());
         			audioClip.play();
         		} catch (MalformedURLException e) {
         			// TODO Auto-generated catch block
         			e.printStackTrace();
         		}
            }
//            else if(w<50&&(System.currentTimeMillis()-soundtime)>2000){
//            	soundtime = System.currentTimeMillis();
//            	distflag = 1;
//            	try {
//         			audioClip = Applet.newAudioClip(soundf.toURL());
//         			audioClip.play();
//         		} catch (MalformedURLException e) {
//         			// TODO Auto-generated catch block
//         			e.printStackTrace();
//         		}
//            }
            else if(w<=160){
//            	soundtime = 0;
            	distflag = 0;
            }
        	
            
            rectangle(mat, new Point(x*2, y*2), new Point((x + w)*2, (y + h)*2), Scalar.GREEN, 1, CV_AA, 0);  
            
            fdcount++;
            if(fdcount>=60&&distflag==0)
            {
            	fdcount=0;
//        		equalizeHist(mat2, mat3);//直方图均衡化
//        		bilateralFilter(mat2,mat3,-1,8,8);//高斯双边模糊
            	roi_img = new Mat(mat2,r);
        		equalizeHist(roi_img, mat3);//直方图均衡化
        		bilateralFilter(mat3,mat4,6,15,15);//高斯双边模糊
                faceid = FaceRecognition.ShowResult(mat4, eigenFaceCore);//人脸识别
                Result = faceid;
                if (!Result.equals("UNKNOWN")){
    	    		resultflag = true;
                    storeFace(mat4);
                }
            }

//	            putText(mat1, name, new Point(x, y), CV_FONT_VECTOR0, 1, Scalar.GREEN, 1, 0,false); 
            
//            resize(mat2, mat3, new Size(640,480), 0, 0, INTER_LINEAR);
            
			frame = converter.convert(mat);
			return frame;
		}	
//		resize(mat2, mat3, new Size(640,480), 0, 0, INTER_LINEAR);
		frame = converter.convert(mat);
		Result = "UNKNOWN";
		System.out.println("no face");
		
		
		fdcount = 0;
		return frame;
	}
	
	//眨眼检测
	private Frame eyeDetection(Frame frame)
	{
		opencv_core.flip(converter.convertToMat(frame), mat, 1);
		resize(mat, mat1, new Size(320,240), 0, 0, INTER_LINEAR);//缩小尺寸
		cvtColor(mat1, mat2, COLOR_RGB2GRAY);//灰度化
//		equalizeHist(mat2, mat3);//直方图均衡化
		
		faces.clear();
		eyes.clear();
		
		faceDetector.detectMultiScale(mat2, faces);
		eyeDetector.detectMultiScale(mat1, eyes);
		
//		putText(mat1, "Please Blink Your Eyes!", new Point(100, 100), CV_FONT_VECTOR0, 1, Scalar.GREEN, 1, 0,false);
		
		if(!eyes.empty()&&eyes.size()>=2&&!faces.empty())
		{
			eyeflag = 0;
			eyeframecount = 0;
			//矩形圈出人眼
			for(int i=0; i<2; i++)
			{
				Rect r = eyes.get(i);
	            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
	            
				Rect r1 = faces.get(0);
	            int x1 = r1.x(), y1 = r1.y(), w1 = r1.width(), h1 = r1.height();
	            
	            if(w1>160&&(System.currentTimeMillis()-soundtime)>2000){
	            	soundtime = System.currentTimeMillis();
	            	distflag = 1;
	            	try {
	         			audioClip = Applet.newAudioClip(soundn.toURL());
	         			audioClip.play();
	         		} catch (MalformedURLException e) {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		}
	            }
//	            else if(w1<100&&(System.currentTimeMillis()-soundtime)>2000){
//	            	soundtime = System.currentTimeMillis();
//	            	distflag = 1;
//	            	try {
//	         			audioClip = Applet.newAudioClip(soundf.toURL());
//	         			audioClip.play();
//	         		} catch (MalformedURLException e) {
//	         			// TODO Auto-generated catch block
//	         			e.printStackTrace();
//	         		}
//	            }
	            else if(w1<=160){
//	            	soundtime = 0;
	            	distflag = 0;
	            }
	            	
	            
	            rectangle(mat, new Point(x*2, y*2), new Point((x + w)*2, (y + h)*2), Scalar.BLUE, 1, CV_AA, 0);
			}
			frame = converter.convert(mat);
			return frame;
		}

		if(faces.empty()||distflag==1||eyeframecount>=10){
			blinkcount=0;
			eyeframecount=0;
		}
		else{
			eyeframecount++;
			if(eyeframecount>=2&&eyeflag == 0)
			{
				blinkcount++;
				eyeflag = 1;
				
				if(blinkcount<3)
				{
	         		try {
	         			audioClip = Applet.newAudioClip(soundb2.toURL());
	         			audioClip.play();
	         		} catch (MalformedURLException e) {
	         			// TODO Auto-generated catch block
	         			e.printStackTrace();
	         		}
				}
			}
		}
		
		System.out.println("Blink!"+ blinkcount);

		frame = converter.convert(mat);
		
		return frame;
	}

	private void storeFace(Mat mat)
	{
		faceshotPath = storePath+"//"+(facecount)+".jpg";
		File file = new File(faceshotPath);
        while(file.exists()){
        	this.facecount++;
        	faceshotPath = storePath+"//"+(facecount)+".jpg";
        	file = new File(faceshotPath);
        }
        
        resize(mat, resizemat, new Size(100,100), 0, 0, INTER_LINEAR);
		imwrite(faceshotPath, resizemat);
		System.out.println(faceshotPath+" SAVED");
		this.facecount++;
	}
}

////保存截图
//private void storeImg(Mat mat)
//{
//	String screenshotPath = storePath+"//"+(count++)+".png";
//	imwrite(screenshotPath, mat);
//}
//
