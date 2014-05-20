/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import java.util.ArrayList;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;

/**
 *
 * @author jayzeegp
 */
public class FiducidalMarkers {
    private native void JcreateMarkerImage(int id, int size, long matAddr);
    private native int Jdetect(long inAddr, int nRotations);
    private native void JgetMarkerMat(int id, long matAddr);
    private native void JcreateBoardImage(double width, double height, int MarkerSize, int MarkerDistance, BoardConfiguration TInfo, ArrayList <Integer> excludedIds, long matAddr);
    private native void JcreateBoardImage_ChessBoard(double width, double height,int MarkerSize, BoardConfiguration TInfo ,boolean setDataCentered ,ArrayList <Integer> excludedIds, long matAddr);
    private native void JcreateBoardImage_Frame(double width, double height, int MarkerSize,int MarkerDistance,  BoardConfiguration TInfo ,boolean setDataCentered,ArrayList <Integer> excludedIds, long matAddr);

    private long nativeHandle;

    
    public Mat createMarkerImage(int id,int size) throws Exception{
        Mat returnedValue = new Mat(size,size,CvType.CV_8UC1);
        JcreateMarkerImage(id,size, returnedValue.getNativeObjAddr());
        return returnedValue;
    }

    public int detect(Mat in,int nRotations){
        return Jdetect(in.getNativeObjAddr(), nRotations);
    }

    public Mat getMarkerMat(int id) throws Exception{
        Mat marker = new Mat(5,5,CvType.CV_8UC1);
        JgetMarkerMat(id, marker.getNativeObjAddr());
        return marker;
    }

    public Mat createBoardImage(Size gridSize,int MarkerSize,int MarkerDistance,  BoardConfiguration TInfo ,ArrayList <Integer>excludedIds) throws Exception{
        Mat krisu = new Mat();
        JcreateBoardImage(gridSize.width, gridSize.height, MarkerSize, MarkerDistance, TInfo, excludedIds, krisu.getNativeObjAddr());
        return krisu;
    }

    public Mat createBoardImage(Size  gridSize,int MarkerSize,int MarkerDistance,  BoardConfiguration TInfo) throws Exception{
        ArrayList <Integer>excludedIds = new ArrayList <>();
        return createBoardImage(gridSize, MarkerSize, MarkerDistance, TInfo, excludedIds);
    }

    public Mat  createBoardImage_ChessBoard(Size gridSize,int MarkerSize, BoardConfiguration TInfo ,boolean setDataCentered ,ArrayList <Integer> excludedIds) throws Exception{
        Mat returnedValue = new Mat();
        JcreateBoardImage_ChessBoard(gridSize.width, gridSize.height, MarkerSize, TInfo, setDataCentered, excludedIds, returnedValue.getNativeObjAddr());
        return returnedValue;
    }

    public Mat  createBoardImage_ChessBoard(Size gridSize,int MarkerSize, BoardConfiguration TInfo ,boolean setDataCentered) throws Exception{
        ArrayList <Integer>excludedIds = new ArrayList <>();        
        return createBoardImage_ChessBoard(gridSize, MarkerSize, TInfo, setDataCentered, excludedIds);
    }

    public Mat  createBoardImage_ChessBoard(Size gridSize,int MarkerSize, BoardConfiguration TInfo) throws Exception{
        ArrayList <Integer>excludedIds = new ArrayList <>();        
        return createBoardImage_ChessBoard(gridSize, MarkerSize, TInfo, true, excludedIds);
    }

    public Mat  createBoardImage_Frame(Size gridSize,int MarkerSize, int MarkerDistance,  BoardConfiguration TInfo, boolean setDataCentered ,ArrayList <Integer> excludedIds) throws Exception{
        Mat returnedValue = new Mat();
        JcreateBoardImage_Frame(gridSize.width, gridSize.height, MarkerSize, MarkerDistance, TInfo, setDataCentered, excludedIds, returnedValue.getNativeObjAddr());
        return returnedValue;
    }

    public Mat  createBoardImage_Frame(Size gridSize,int MarkerSize,int MarkerDistance,  BoardConfiguration TInfo ,boolean setDataCentered) throws Exception{
        ArrayList <Integer>excludedIds = new ArrayList <>();                
        return createBoardImage_Frame(gridSize, MarkerSize, MarkerDistance, TInfo, setDataCentered, excludedIds);
    }

    public Mat  createBoardImage_Frame(Size gridSize,int MarkerSize,int MarkerDistance,  BoardConfiguration TInfo) throws Exception{
        ArrayList <Integer>excludedIds = new ArrayList <>();                
        return createBoardImage_Frame(gridSize, MarkerSize, MarkerDistance, TInfo, true, excludedIds);
    }

}
