/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

/**
 *
 * @author jayzeegp
 */
public class Board {
    private native void JBoard();
    
    private native void JsaveToFile(String sfile);
    private native void JreadFromFile(String sfile);
    private native void JglGetModelViewMatrix(double modelview_matrix[]);
    private native void JOgreGetPoseParameters(double position[], double orientation[]);
    
    private long nativeHandle;
    
    public Board(){
        JBoard();
    }

    public void glGetModelViewMatrix(double modelview_matrix[])throws Exception{
        JglGetModelViewMatrix(modelview_matrix);
    }

    public void OgreGetPoseParameters(double position[], double orientation[])throws Exception{
        JOgreGetPoseParameters(position, orientation);
    }

    public void saveToFile(String filePath)throws Exception{
        JsaveToFile(filePath);
    }

    public void readFromFile(String filePath)throws Exception{
        JreadFromFile(filePath);
    }

}
