package FaceRecognition;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.*;

import Model.EigenFaceCore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FaceRecognition {
	static{System.loadLibrary( "opencv_java341" );}
//	public static String TEST_PATH="tsd\\";
	public static String TRAIN_PATH="TrainDatabase\\";
	public static String DOT_JPG=".jpg";
	public static String THUMBS="Thumbs.db";
	static File folder = new File(TRAIN_PATH);
	
	
		
	public static String ShowResult(org.bytedeco.javacpp.opencv_core.Mat mat, EigenFaceCore eigenFaceCore){
		org.opencv.core.Mat gray_img = new org.opencv.core.Mat(mat.address());
		Mat img_re = new Mat();
		Imgproc.resize(gray_img, img_re, new Size(100,100), 0, 0, Imgproc.INTER_LINEAR);
		
		String recognitionResult = Recognition(img_re, eigenFaceCore);
		System.out.println(recognitionResult);
	
		if(!recognitionResult.equals("UNKNOWN")){
			int count = 0;
			int foldercount = PreRecognition.foldercount();
			List<String> fileList = null;
			for (int i = 0; i < foldercount; i++) {
				if(new File(TRAIN_PATH+"s"+Integer.toString(i)).exists()){
					fileList = PreRecognition.getFileList(TRAIN_PATH+"s"+Integer.toString(i)+"\\");
					for (int j = 0; j < fileList.size(); j++) {
						count++;	
						if (String.valueOf(count).equals(recognitionResult)){
							recognitionResult = "s"+Integer.toString(i);
							return recognitionResult;
						}
					}
				}
				else{
					foldercount++;
				}
				
			}
		}
		return recognitionResult;
	} 
   
   /**
    * 识别函数
    * @return
    */
   public static String Recognition(Mat testImg,EigenFaceCore eigenFaceCore){
	  Mat projectedImages=new Mat();
	  int trainNumber=eigenFaceCore.getEigenFaces().cols();
	  List<Mat> listMatVec=new ArrayList<Mat>();
	  for (int i = 0; i < trainNumber; i++) {
		//Projection of centered images into facespace
		Mat tempMat=new Mat();
		Core.gemm(eigenFaceCore.getEigenFaces().t(),eigenFaceCore.getImgVecCenter().col(i), 1, new Mat(), 0, tempMat); 
		listMatVec.add(tempMat);
	  }
	  Core.hconcat(listMatVec, projectedImages);
	  /**Extracting the PCA features from test image**/
	  Size testImgSize = testImg.size();
	 
	  Mat testImgMat = testImg.t().reshape(0, (int)(testImgSize.width*testImgSize.height));
	  testImgMat.convertTo(testImgMat, CvType.CV_64FC1);//转换成double型进行运算
	  //Centered test image
	  Mat differenceTestImgMat = new Mat();
	  Core.subtract(testImgMat, eigenFaceCore.getMean(), differenceTestImgMat);
	  //Test image feature vector
	  Mat projectedTestImage = new Mat();
	  Core.gemm(eigenFaceCore.getEigenFaces().t(),differenceTestImgMat, 1, new Mat(), 0,projectedTestImage);
	  
	  /**Calculating Euclidean distances **/
	  
	  Mat eucDist=new Mat();
	  eucDist.create(trainNumber,1,CvType.CV_64FC1);
	  for (int i = 0; i < trainNumber; i++) {
		Mat trainProCol=new Mat();
		trainProCol=projectedImages.col(i);
//		System.out.println(projectedImages.dump());
		Mat temp=new Mat();
		Core.subtract(projectedTestImage, trainProCol, temp);
		double distence = Math.pow(Core.norm(temp), 2);
		System.out.println(distence);
		eucDist.put(i, 0, distence);
	}
	  //求最小值的
	  
	  double minDis = Core.minMaxLoc(eucDist).minVal;
	  double minLoc = Core.minMaxLoc(eucDist).minLoc.y;
	  
	  System.out.println("minDis:"+minDis+",minLoc:"+minLoc);
	  
	  String Result = new String();
	  
	  if(minDis>1E15)
		  Result = "UNKNOWN";
	  else
		  Result = Integer.toString((int)minLoc+1);
  
	return Result;
   }
}
