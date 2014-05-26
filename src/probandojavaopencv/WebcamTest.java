/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author jayzeegp
 */
public class WebcamTest {
     public static void main(String[] args) {
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

         WebcamTest test = new WebcamTest();
         try {
             test.go();
         } catch (InterruptedException ex) {
             Logger.getLogger(WebcamTest.class.getName()).log(Level.SEVERE, null, ex);
         }
     }

    private void go() throws InterruptedException {
        VideoCapture camera = new VideoCapture(0);
        Thread.sleep(1000);
        if (camera.isOpened())
             System.out.println("Camera is ready!");
        else {
             System.out.println("Camera Error!");
             return;
        }
        Mat newMat = new Mat();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            //e.printStackTrace();
        }

        camera.read(newMat);
        Highgui.imwrite("testfile.jpg", newMat);

        camera.release();
        if (camera.isOpened()) {
            System.out.println("Camera is running!");
        }
        else {
            System.out.println("Camera closed!");

        }
        
    }

}
