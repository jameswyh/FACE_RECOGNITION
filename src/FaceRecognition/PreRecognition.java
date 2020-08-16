package FaceRecognition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import Model.EigenFaceCore;

public class PreRecognition {
	
	static{System.loadLibrary( "opencv_java341" );}
	public static String TRAIN_PATH="TrainDatabase\\";
	public static String DOT_JPG=".jpg";
	public static String THUMBS="Thumbs.db";
	static File folder = new File(TRAIN_PATH);
	
	
	/**
	 * 返回文件夹数量
	 * @return
	 */
	public static int foldercount(){
		int foldercount = 0;
		File[] folderlist = folder.listFiles();
		for (File file : folderlist){
			if(!file.isFile()){
				foldercount++;
			}
		}
		return foldercount;
	}
	
	/**
	 * 图片矩阵向量化
	 * @return
	 */
	public static Mat CreateDatabase(String trainPath, int foldernum) {
		
		Mat matVec=new Mat();
		List<Mat> listMatVec=new ArrayList<Mat>();
		List<String> fileList = null;
		for (int i = 0; i < foldernum; i++) {
			if(new File(trainPath+"s"+Integer.toString(i)).exists()){
				fileList = getFileList(trainPath+"s"+Integer.toString(i)+"\\");
				int j = 0; 
				int count = 0;//人脸文件数
				while(count<fileList.size()){
					File file = new File(trainPath+"s"+Integer.toString(i)+"\\"+Integer.toString(j)+DOT_JPG);
					if(!file.exists())
						j++;
					else{
						Mat imTrainRead = Imgcodecs.imread(trainPath+"s"+Integer.toString(i)+"\\"+Integer.toString(j)+DOT_JPG,0);
						Mat imTrainReadend = new Mat();
						Imgproc.resize(imTrainRead, imTrainReadend, new Size(100,100), 0, 0, Imgproc.INTER_LINEAR);
						imTrainReadend.convertTo(imTrainReadend, CvType.CV_64FC1);//转换成单通道的double型数据
						int row=imTrainReadend.rows();
						int col=imTrainReadend.cols();
						Mat imgVec = imTrainReadend.t().reshape(0,row*col);
						listMatVec.add(imgVec);
						count++;
						j++;
					}
				}
			}
			else{
				foldernum++;
			}
			//Construction of 2D matrix from 1D image vectors

		}
		//将listMat按行合并成一个Mat
		Core.hconcat(listMatVec, matVec);
		return matVec;
	}
	
	/**
	 * 获取指定路径下的所有文件名称
	 * @param path
	 * @return
	 */
	public static List<String> getFileList(String path) {
		 List<String> imgNmVec=new ArrayList<String>();
		 
		 File file = new File(path);
		 File[] fileList = file.listFiles();
		 for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isFile()&&!"..".equals(fileList[i].getName())&&!".".equals(fileList[i].getName())&&!THUMBS.equals(fileList[i].getName())) {
				imgNmVec.add(fileList[i].getName());
			}	
		}
		return imgNmVec;
	}
	
	/**
	 * PCA特征脸算法
	 * @param matVector
	 * @return
	 */
   public static EigenFaceCore eigenFaceCore(Mat matVector){
	   EigenFaceCore eigenFaceCore = new EigenFaceCore();
	   Mat horizontalMean = horizontalMean(matVector);
	   eigenFaceCore.setMean(horizontalMean);//均值
	   int trainNumber=matVector.cols();
	   List<Mat> listMatVec=new ArrayList<Mat>();
	   Mat imgVecCenter=new Mat();
	   //去均值化
	   for (int i = 0; i < trainNumber; i++) {
		   Mat tempMat=new Mat();
		   Mat colRange = matVector.col(i);
		   Core.subtract(colRange, horizontalMean, tempMat);
		   listMatVec.add(tempMat);
	   }
	   //按列合并
	   Core.hconcat(listMatVec, imgVecCenter);
	   eigenFaceCore.setImgVecCenter(imgVecCenter);//图像向量中心
	   /**L=A'*A**/
	   Mat tempMutipil=new Mat();
	   Core.gemm(imgVecCenter.t(), imgVecCenter, 1, new Mat(), 0, tempMutipil);
	   Mat eigenValues=new Mat();//L的特征值
	   Mat eigenVectors=new Mat();//L的特征向量
	    /**求L的特征值和特征向量**/
	   Core.eigen(tempMutipil, eigenValues, eigenVectors);
//	   System.out.println(eigenValues.dump());
	   Mat L_eig_vec=new Mat();
	   List<Mat> listEigMat=new ArrayList<Mat>();
	   for (int i = 0; i < eigenVectors.cols(); i++) {
		   double[] data=new double[2];
		if (eigenValues.get(i,0,data)>1) {
			listEigMat.add(eigenVectors.colRange(i, i+1));
		}
	}
	   	Core.hconcat(listEigMat, L_eig_vec);
	    Mat tempEigMat=new Mat();
		Core.gemm(imgVecCenter, L_eig_vec, 1,new Mat(),0,tempEigMat);//L=A'*A;
		eigenFaceCore.setEigenFaces(tempEigMat);
		return eigenFaceCore;
   } 
   
   /**
    * 矩阵按行求均值
    * @param mat
    * @return 返回行均值向量
    */
   public static Mat horizontalMean(Mat mat){
	   Mat meanVector=new Mat();
	   meanVector.create(mat.rows(),1,CvType.CV_64FC1);
	   //遍历mat矩阵
	   for (int i = 0; i < mat.rows(); i++) {
		   Mat rowRange = mat.rowRange(i,i+1);
		   Scalar mean = Core.mean(rowRange);
		   meanVector.put(i, 0,mean.val[0]);
	}
	   return meanVector;
   }
   
}
