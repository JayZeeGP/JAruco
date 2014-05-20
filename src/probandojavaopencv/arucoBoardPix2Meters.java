/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;

import org.opencv.core.Core;
import wrapper.BoardConfiguration;

/**
 *
 * @author jayzeegp
 */
//This program converts a boardconfiguration file expressed in pixel to another one expressed in meters

public class arucoBoardPix2Meters {
     
 public static void main(String[] args) throws Exception {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     try{
         if(args.length < 3){
             System.out.println("Usage:  in_boardConfiguration.yml markerSize_meters out_boardConfiguration.yml");
             System.exit(-1);
         }
         
      BoardConfiguration BInfo = new BoardConfiguration();
      BInfo.readFromFile(args[0]);
      
      if(BInfo.size()==0){
          System.out.println("Invalid board with no markers");
          System.exit(-1);
      }
         
     }catch(Exception e){
         System.out.println("Exception: " + e.getMessage());
     }
 }

     static{
         System.loadLibrary("WrapperCPP");
    }
}


