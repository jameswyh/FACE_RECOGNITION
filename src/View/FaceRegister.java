package View;

import static org.bytedeco.javacpp.opencv_imgcodecs.imwrite;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.CV_FONT_VECTOR0;
import static org.bytedeco.javacpp.opencv_imgproc.INTER_LINEAR;
import static org.bytedeco.javacpp.opencv_imgproc.bilateralFilter;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.putText;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;
import static org.bytedeco.javacpp.opencv_imgproc.resize;
import static org.bytedeco.javacpp.opencv_imgproc.equalizeHist;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.RectVector;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import DataBase.FaceDataBase;

public class FaceRegister {
	private int photocount = 0;
	private int facecount = 0;
	private double framecount = 0;
	private String faceid = null;
	private String name = null;
	private String workid = null;
	private CascadeClassifier faceDetector = new CascadeClassifier();
	private OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	
	private CanvasFrame canvas = new CanvasFrame("����¼������...");//�½�һ������
	OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);

	RectVector faces = new RectVector();
	Mat mat = new Mat();
	Mat mat1 = new Mat();
	Mat mat2 = new Mat();
	Mat mat3 = new Mat();
	Mat mat4 = new Mat();
    Mat matbf = new Mat();

    Mat gray_img = new Mat();
    
    AudioClip audioClip;
    File soundst = new File("sound\\start.wav");
    
    FaceDataBase fdb = new FaceDataBase();
	
	public void videoCapture(String faceid, String workid, String name)
	{
		this.faceid = faceid;
		this.name = name;
		this.workid = workid;
		new Thread(new Runnable() {
            @Override
            public void run() {
            	try
                {
                	//��ʼ���������ģ��
            		initDetector();
            		System.out.println("INIT DETECTOR");
            		
                    grabber.start();   //��ʼ��ȡ����ͷ����
                    
                    canvas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    canvas.setAlwaysOnTop(true);
                    
                    try {
            			audioClip = Applet.newAudioClip(soundst.toURL());
            			audioClip.play();
            		} catch (MalformedURLException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
                    while(true){
                        if(!canvas.isDisplayable()){//�����Ƿ�ر�
                        	faceDetector.close();
                            grabber.stop();
//                            System.exit(2);
                            break;
                        } 	
                        canvas.showImage(faceDetection(grabber.grab()));
//                        Thread.sleep(10);
                    }	
                }
                catch(Exception e)
                {
                	System.err.println("GRAB EXCEPTION");
                }
            }
        }).start();
	}

	//��ʼ�����������
	private void initDetector() throws UnsupportedEncodingException
	{
//		//�������ģ��·��
		faceDetector.load("C://Program Files//opencv//build//etc//haarcascades//haarcascade_frontalface_default.xml");
	}
	
	
	//�������
	private Frame faceDetection(Frame frame)
	{
		opencv_core.flip(converter.convertToMat(frame), mat, 1);
		resize(mat, mat1, new Size(320,240), 0, 0, INTER_LINEAR);//��С�ߴ�
		cvtColor(mat1, mat2, COLOR_RGB2GRAY);//�ҶȻ�
//		equalizeHist(mat2, mat3);//ֱ��ͼ���⻯
//		bilateralFilter(mat3,mat4,-1,8,8);
		
		faces.clear();
		faceDetector.detectMultiScale(mat2, faces);
		
		
		if(!faces.empty())
		{
			Rect r = faces.get(0);
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            rectangle(mat, new Point(x*2, y*2), new Point((x + w)*2, (y + h)*2), Scalar.GREEN, 1, CV_AA, 0);  
        	Mat roi_img = new Mat(mat2,r);
            framecount++;
            if(framecount >= 30){
        		equalizeHist(roi_img, mat3);//ֱ��ͼ���⻯
        		bilateralFilter(mat3,mat4,6,15,15);
	            storeFace(mat4);
	            photocount++;
	            framecount = 0;
            }
            if(photocount == 5){
            	try {
					grabber.stop();
					grabber.close();
				} catch (FrameGrabber.Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		    JFrame jf=new JFrame();
    			jf.setTitle("����¼��ɹ�");
    			jf.setSize(340,100);
    			jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    			jf.setLocationRelativeTo(null);
    			jf.setResizable(false);
    			
    			jf.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));
    			
    			JLabel lab=new JLabel();
    			lab.setText("����¼��ɹ���");
    			jf.add(lab);
    			lab.setPreferredSize(new Dimension(300, 50));
    			
         		jf.setVisible(true);
         		canvas.dispose();
            }
            frame = converter.convert(mat);
    		return frame;
		}
		frame = converter.convert(mat);
		System.out.println("no face");
		framecount = 0;
		return frame;
	}
	
	
	private void storeFace(Mat mat)
	{
		File file =new File("TrainDatabase//"+ faceid); 
		//����ļ��в������򴴽� 
		if  (!file.exists() && !file.isDirectory())   
		{    
		    System.out.println("�����������ļ���");
		    file.mkdir(); 
		} else
		{
		    System.out.println("�����Ѵ��������ļ���");
		}
		
		String faceshotPath = "TrainDatabase//"+ faceid +"//"+ (facecount)+".jpg";
		File f = new File(faceshotPath);
        while(f.exists()){
        	this.facecount++;
        	faceshotPath = "TrainDatabase//"+ faceid +"//"+ (facecount)+".jpg";
        	f = new File(faceshotPath);
        }
        Mat resizemat = new Mat();
        resize(mat, resizemat, new Size(100,100), 0, 0, INTER_LINEAR);
        
		imwrite(faceshotPath, resizemat);
		System.out.println(faceshotPath+" SAVED");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		fdb.InsertFace(workid, name, sdf.format(new Date()), faceshotPath);
		this.facecount++;
	}
}
