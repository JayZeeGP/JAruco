/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;

/**
 *
 * @author jayzeegp
 */
public class CameraParameters {
    private native void JCameraParameters();
    private native void JreadFromFile(String path);
    private native boolean JisValid();
    private native void JsetParams(long cameraMatrix, long distorsionCoeff, double widht, double height);      
    private native void JsaveToFile(String path, boolean inXML);
    private native void JreadFromXMLFile(String filePath);
    private native void Jresize(double width, double height);
    private native void JglGetProjectionMatrix(double orgWidth, double orgHeight, double width, double height, double [] proj_matrix,double gnear,double gfar,boolean invert);
    private native void JOgreGetProjectionMatrix(double orgWidth, double orgHeight, double width, double height, double [] proj_matrix,double gnear,double gfar,boolean invert);
    private native Point JgetCameraLocation(long RvecAddr, long TvecAddr);
    
    private static CameraParameters inst;
    private long nativeHandle;
    
    
    public CameraParameters(){
        JCameraParameters();
    }
    
    public CameraParameters(Mat cameraMatrix, Mat distorsionCoeff, Size size){
        
    }
    
    public CameraParameters(CameraParameters CI){
        
    }
    
    public Point getCameraLocation(Mat Rvec, Mat Tvec){
        return (Point) JgetCameraLocation(Rvec.getNativeObjAddr(), Tvec.getNativeObjAddr());
    }
    
    public void setParams(Mat cameraMatrix, Mat distorsionCoeff, Size size) throws Exception{
        JsetParams(cameraMatrix.getNativeObjAddr(), distorsionCoeff.getNativeObjAddr(), size.width, size.height);        
    }
    
    public boolean isValid(){
        return JisValid();
    }
    
    public void readFromFile(String path) throws Exception{
        JreadFromFile(path);        
    }
    
    public void saveToFile(String path, boolean inXML) throws Exception{
        JsaveToFile(path, inXML);
    }
    
    public void readFromXMLFile(String filePath) throws Exception{
        JreadFromXMLFile(filePath);
    }
    
    public void resize(Size size) throws Exception{
        Jresize(size.width, size.height);        
    }
    
    public void glGetProjectionMatrix(Size orgImgSize, Size size, double proj_matrix[],double gnear,double gfar,boolean invert) throws Exception{
        JglGetProjectionMatrix(orgImgSize.width, orgImgSize.height, size.width, size.height, proj_matrix, gnear, gfar, invert);
    }
    
    public void OgreGetProjectionMatrix(Size orgImgSize, Size size, double proj_matrix[],double gnear,double gfar,boolean invert) throws Exception{
        JOgreGetProjectionMatrix(orgImgSize.width, orgImgSize.height, size.width, size.height, proj_matrix, gnear, gfar, invert);
    }
}
