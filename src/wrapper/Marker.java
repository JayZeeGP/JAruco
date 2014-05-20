/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
/**
 *
 * @author jayzeegp
 */
public class Marker {
    //Constructors
    private native void JMarker();
    private native void JMarker(Marker M);
    private native void JMarker(long addr);
    
    //Functions
    private native boolean JisValid();
    private native void Jdraw(long in, double[] color, int lineWidth, boolean writeId);
    private native void JglGetModelViewMatrix(double [] modelview_matrix);
    private native float JgetArea();
    private native float JgetPerimeter();
    private native Point JgetCenter();
    private native void JcalculateExtrinsics(float markerSize, CameraParameters CP,boolean setYPerpendicular);
    private native void JcalculateExtrinsics(float markerSize, long cameraMatrix, long distCoeff,boolean setYPerpendicular);
    private native void JOgreGetPoseParameters(double[] position, double[] orientation);
    
    private static Marker inst;
    private long nativeHandle;
    
    public Marker(){
       JMarker();
    }
    
    public Marker(Marker M){
        JMarker(M);
    }
    
    public Marker(long addr){
        JMarker(addr);
    }
    
    public void setNativeHandle(long addr){
        nativeHandle = addr;
    }
    
    public long getNativeHandle(){
        return nativeHandle;
    }
    
    public boolean isValid(){
        return JisValid();
    }
    
    public void draw(Mat img, Scalar color, int lineWidth, boolean writeId){
        Jdraw(img.getNativeObjAddr(), color.val, lineWidth, writeId);
    }
    
    public void draw(Mat img, Scalar color, int lineWidth){
        Jdraw(img.getNativeObjAddr(), color.val, lineWidth, true);
    }
    
    public void draw(Mat img, Scalar color){
        Jdraw(img.getNativeObjAddr(), color.val, 1, true);
    }
    
    //I supposed the parameters don't change
    public void calculateExtrinsics(float markerSize, CameraParameters CP, boolean setYPerpendicular) throws Exception{
        JcalculateExtrinsics(markerSize, CP, setYPerpendicular);
    }
    
    //I supposed the parameters don't change    
    public void calculateExtrinsics(float markerSizeMeters, Mat cameraMatrix, Mat distCoeff, boolean setYPerpendicular) throws Exception{
        JcalculateExtrinsics(markerSizeMeters, cameraMatrix.getNativeObjAddr(), distCoeff.getNativeObjAddr(), setYPerpendicular);
    }
    
    public void glGetModelViewMatrix(double modelview_matrix[]) throws Exception{
        JglGetModelViewMatrix(modelview_matrix);
    }
    
    public void OgreGetPoseParameters(double position[], double orientation[]) throws Exception{
        JOgreGetPoseParameters(position, orientation);
    }
    
    public Point getCenter(){
        return JgetCenter();
    }

    public float getPerimeter(){
        return JgetPerimeter();
    }
    
    public float getArea(){
        return JgetArea();
    }    
    
    
}
