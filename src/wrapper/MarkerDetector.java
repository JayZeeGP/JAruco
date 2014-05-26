/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;

/**
 *
 * @author jayzeegp
 */
public class MarkerDetector {
    private native void Jdetect(long input, ArrayList <Marker> Marker, long camMatrix, long distCoeff, float markerSizeMeters, boolean SetYPerperdicular);
    private native void Jdetect(long input, ArrayList <Marker> detectedMarkers, CameraParameters cam,float markerSizeMeters, boolean setYPerpendicular);
    private native void JMarkerDetector();
    private native int JgetDesiredSpeed();
    private native void JsetDesiredSpeed(int val);
    private native void JsetThresholdMethod(ThresholdMethods m);
    private native ThresholdMethods JgetThresholdMethod();
    public native void JsetThresholdParams(double param1, double param2);
    public native void JgetThresholdedImage(long imgAddr);
    public native void JsetCornerRefinementMethod(CornerRefinementMethod method);
    public native CornerRefinementMethod JgetCornerRefinementMethod();
    public native void JsetMinMaxSize(float min, float max);
    public native void JenableErosion(boolean enable);
    public native void JpyrDown(int level);
    public native void Jthreshold(int method, long greyAddr, long thresImgAddr, double param, double param2);
    public native boolean Jwarp(Mat in, Mat out, double wdith, double height, ArrayList <Point> points);


    
    private static MarkerDetector inst;
    private long nativeHandle;
    
    public MarkerDetector(){
        JMarkerDetector();
    }

    public void detect(Mat img, ArrayList<Marker> markers, CameraParameters camParam, float markerSizeMeters, boolean setYPerpendicular) {
        Jdetect(img.getNativeObjAddr(), markers, camParam, markerSizeMeters, setYPerpendicular);
    }
    
    public void detect(Mat img, ArrayList<Marker> markers, CameraParameters camParam, float markerSizeMeters) {
        Jdetect(img.getNativeObjAddr(), markers, camParam, markerSizeMeters, true);
    }
 
    public void detect(Mat img, ArrayList<Marker> markers, CameraParameters camParam) {
        Jdetect(img.getNativeObjAddr(), markers, camParam, -1, true);
    }
              
    public void detect(Mat input, ArrayList<Marker> detectedMarkers){
        Jdetect(input.getNativeObjAddr(), detectedMarkers, new Mat().getNativeObjAddr(), new Mat().getNativeObjAddr(), -1, true);
    }
    
    public void detect(Mat input, ArrayList<Marker> detectedMarkers, Mat camMatrix){
        Jdetect(input.getNativeObjAddr(), detectedMarkers, camMatrix.getNativeObjAddr(), new Mat().getNativeObjAddr(), -1, true);
    }
    
    public void detect(Mat input, ArrayList<Marker> detectedMarkers, Mat camMatrix, Mat distCoeff){
        Jdetect(input.getNativeObjAddr(), detectedMarkers, camMatrix.getNativeObjAddr(), distCoeff.getNativeObjAddr(), -1, true);
    }
    
    public void detect(Mat input, ArrayList<Marker> detectedMarkers, Mat camMatrix, Mat distCoeff, float markerSizeMeters){
        Jdetect(input.getNativeObjAddr(), detectedMarkers, camMatrix.getNativeObjAddr(), distCoeff.getNativeObjAddr(), markerSizeMeters, true);
    }
    
     public void detect(Mat input, ArrayList<Marker> detectedMarkers, Mat camMatrix, Mat distCoeff, float markerSizeMeters, boolean setYPerpendicular){
        Jdetect(input.getNativeObjAddr(), detectedMarkers, camMatrix.getNativeObjAddr(), distCoeff.getNativeObjAddr(), markerSizeMeters, setYPerpendicular);
    }


    
    public enum ThresholdMethods{FIXED_THRES,ADPT_THRES,CANNY};
    public enum CornerRefinementMethod {NONE,HARRIS,SUBPIX,LINES};

    public void setCornerRefinementMethod(CornerRefinementMethod method) {
        JsetCornerRefinementMethod(method);
    }
    
    public CornerRefinementMethod getCornerRefinementMethod(){
        return JgetCornerRefinementMethod();
    }
    
    public void setThresholdMethod (ThresholdMethods m){
        JsetThresholdMethod(m);
    }
    
    public ThresholdMethods getThresholdMethod(){
        return ThresholdMethods.FIXED_THRES;
    }
    
    public void setThresholdParams(double param1, double param2){
        JsetThresholdParams(param1, param2);
    }
    
    public void getThresholdParams(double param1, double param2){
        
    }
    
    public Mat getThresholdedImage(){
        Mat img = new Mat();
        JgetThresholdedImage(img.getNativeObjAddr());
        return img;
    }
    
    public void setDesiredSpeed(int val){
        JsetDesiredSpeed(val);
    }
    
    public int getDesiredSpeed(){
        return JgetDesiredSpeed();
    }
    
    public void setMinMaxSize(float min, float max) throws Exception{
        JsetMinMaxSize(min,max);
    }
    
    public void setMinMaxSize(float min) throws Exception{
        setMinMaxSize(min,0.5f);
    }
    
    public void setMinMaxSize()throws Exception{
        setMinMaxSize(0.03f,0.5f);
    }
    
    public void getMinMaxSize(float min, float max){
        
    }
    
    public void enableErosion(boolean enable){
        JenableErosion(enable);
    }
    

    
    public void setMarkerDetectorFunction(){
        //ESTA DIFICIL IOH
    }
    
    public void pyrDown(int level){
        JpyrDown(level);
    }
    
    public void threshold(int method, Mat grey, Mat thresImg,double param,double param2)throws Exception{
            Jthreshold(method, grey.getNativeObjAddr(), thresImg.getNativeObjAddr(), param, param2);
    }
    
    public void threshold(int method, Mat grey, Mat thresImg,double param)throws Exception{
            threshold(method, grey, thresImg, param, -1);
    }
    
    public void threshold(int method, Mat grey, Mat thresImg)throws Exception{
            threshold(method, grey, thresImg, -1, -1);
    }
    
    public boolean warp(Mat in, Mat out, Size size, ArrayList <Point> points) throws Exception{
        return Jwarp(in, out, size.width, size.height, points);
    }
    
    //public void refineCandidateLines(MarkerCandidate candidate){
        //JrefineCandidateLines(candidate);
    //}
    
    

}
