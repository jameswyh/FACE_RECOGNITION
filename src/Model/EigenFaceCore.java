package Model;
import org.opencv.core.Mat;


public class EigenFaceCore {
    public Mat getMean() {
		return mean;
	}
	public void setMean(Mat mean) {
		this.mean = mean;
	}
	public Mat getImgVecCenter() {
		return imgVecCenter;
	}
	public void setImgVecCenter(Mat imgVecCenter) {
		this.imgVecCenter = imgVecCenter;
	}
	public Mat getEigenFaces() {
		return eigenFaces;
	}
	public void setEigenFaces(Mat eigenFaces) {
		this.eigenFaces = eigenFaces;
	}
	public Mat mean;//(M*Nx1) Mean of the training database
    public Mat imgVecCenter;//(M*NxP) Matrix of centered image vectors
    public Mat eigenFaces;//(M*Nx(P-1)) Eigen vectors of the covariance matrix of the training database
}
