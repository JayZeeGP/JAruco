/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import java.util.ArrayList;
import org.opencv.core.Mat;

/**
 *
 * @author jayzeegp
 */
public class BoardDetector {
    private native void JBoardDetector(boolean setYPerpendicular);
    private native float Jdetect(long imgAddr);
    private native Board JgetDetectedBoard();
    private native MarkerDetector JgetMarkerDetector();
    private native ArrayList<Marker> JgetDetectedMarkers();
    private native void JsetYPerpendicular(boolean enable);
    private native float Jdetect(ArrayList<Marker> detectedMarkers, BoardConfiguration BConf, Board Bdetected, CameraParameters cp, float markerSizeMeters);
    private native float Jdetect(ArrayList<Marker> detectedMarkers, BoardConfiguration BConf, Board Bdetected, long camMatrix, long distCoeff, float markerSizeMeters);
    private static MarkerDetector inst;
    private long nativeHandle;
    
    public BoardDetector(){
        JBoardDetector(true);
    }
    
    public BoardDetector(boolean setYPerpendicular){
        JBoardDetector(setYPerpendicular);
    }
    
    public float detect(Mat im) throws Exception{
        float returnedValue;
        returnedValue = Jdetect(im.getNativeObjAddr());
        return returnedValue;
    }
    
    public Board getDetectedBoard(){
        return JgetDetectedBoard();
    }
    
    public MarkerDetector getMarkerDetector(){
        return JgetMarkerDetector();
    }
    
    public ArrayList <Marker> getDetectedMarkers(){
        return JgetDetectedMarkers();
    }
    
    public float detect(ArrayList<Marker> detectedMarkers,BoardConfiguration BConf, Board Bdetected, Mat camMatrix,Mat distCoeff, float markerSizeMeters) throws Exception{
        return Jdetect(detectedMarkers, BConf, Bdetected, camMatrix.getNativeObjAddr(), distCoeff.getNativeObjAddr(), markerSizeMeters);
    }
    
    public float detect(ArrayList<Marker> detectedMarkers,BoardConfiguration BConf, Board Bdetected, Mat camMatrix,Mat distCoeff) throws Exception{
        return detect(detectedMarkers, BConf, Bdetected, camMatrix, distCoeff, -1);
    }
        
    public float detect(ArrayList<Marker> detectedMarkers,BoardConfiguration BConf, Board Bdetected, Mat camMatrix) throws Exception{
        return detect(detectedMarkers, BConf, Bdetected, camMatrix, new Mat(), -1);
    }   
    
    public float detect(ArrayList<Marker> detectedMarkers,BoardConfiguration BConf, Board Bdetected) throws Exception{
        return detect(detectedMarkers, BConf, Bdetected, new Mat(), new Mat(), -1);
    }
    
    public float detect(ArrayList <Marker> detectedMarkers, BoardConfiguration BConf, Board Bdetected, CameraParameters cp, float markerSizeMeters) throws Exception{
        return Jdetect(detectedMarkers, BConf, Bdetected,cp,markerSizeMeters);
    }
    
    public float detect(ArrayList <Marker> detectedMarkers, BoardConfiguration BConf, Board Bdetected, CameraParameters cp) throws Exception{
        return detect(detectedMarkers, BConf, Bdetected,cp,-1);   
    }
        
    public void setYPerpendicular(boolean enable){
        JsetYPerpendicular(enable);
    }

}
