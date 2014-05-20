/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import java.util.ArrayList;

/**
 *
 * @author jayzeegp
 */
public class BoardConfiguration {
    private native void JBoardConfiguration();
    private native void JBoardConfiguration(BoardConfiguration T);
    private native void JBoardConfiguration(long addr);
    
    private native void JsaveToFile(String sfile);
    private native void JreadFromFile(String sfile);
    private native boolean JisExpressedInMeters();
    private native boolean JisExpressedInPixels();
    private native int JgetIndexOfMarkerId(int id);
    private native void JgetIdList(ArrayList <Integer> ids, boolean append);
    
    private long nativeHandle;
    
    public BoardConfiguration(){
        JBoardConfiguration();
    }
    
    public BoardConfiguration(BoardConfiguration T){
        JBoardConfiguration(T);
    }
    
    public BoardConfiguration(long addr){
        JBoardConfiguration(addr);
    }
    
    public void saveToFile(String sfile) throws Exception{
        JsaveToFile(sfile);
    }
    
    public void readFromFile(String sfile){
        JreadFromFile(sfile);
    }
    
    public boolean isExpressedInMeters(){
        return JisExpressedInMeters();
    }
    
    public boolean isExpressedInPixels(){
        return JisExpressedInPixels();
    }
    
    int getIndexOfMarkerId(int id){
        return JgetIndexOfMarkerId(id);
    }
    
    //    const MarkerInfo& getMarkerInfo(int id)const throw (cv::Exception);

    void getIdList(ArrayList <Integer> ids, boolean append){
        JgetIdList(ids, append);
    }
       
    void getIdList(ArrayList <Integer> ids){
        getIdList(ids, true);
    }
}
