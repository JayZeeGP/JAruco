/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import wrapper.*;

/**
 *
 * @author jayzeegp
 */
public class arucoCreateMarker {
    
 public static void main(String[] args) throws Exception {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     try{
            if(args.length!=3){
                System.out.println("    Usage: <makerid(0:1023)> outfile.jpg sizeInPixels");
                System.exit(-1);
            }
            
            FiducidalMarkers fm = new FiducidalMarkers();
            Mat marker = fm.createMarkerImage(Integer.parseInt(args[0]),Integer.parseInt(args[2]));
            Highgui.imwrite(args[1],marker);
            
     }catch(Exception e){
         System.out.println("Exception: " + e.getMessage());
     }
 }

     static{
         System.loadLibrary("WrapperCPP");
    }
}

