/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.highgui.VideoCapture;

import wrapper.*;

/**
 *
 * @author jayzeegp
 */
public class arucoTest {
    String TheInputVideo = new String();
    String TheIntrinsicFile = new String();
    float TheMarkerSize=-1.0f;
    int ThePyrDownLevel = 0;
    MarkerDetector MDetector = new MarkerDetector();
    VideoCapture TheVideoCapturer = new VideoCapture();
    ArrayList <Marker> TheMarkers;
    Mat TheInputImage = new Mat();
    Mat TheInputImageCopy = new Mat();
    CameraParameters TheCameraParameters = new CameraParameters();
    //void cvTackBarEvents(int pos,void*);
    //boolean readCameraParameters(string TheIntrinsicFile,CameraParameters &CP,Size size);
    double AvrgTimeFirst=0;
    double AvrgTimeSecond=0;
    double ThresParam1,ThresParam2;
    int iThresParam1,iThresParam2;
    int waitTime=0;
     
 
public boolean readArguments (String[] args)
{
    if (args.length<1) {
        System.out.println("Invalid number of arguments");
        System.out.println("Usage: (in.avi|live) [intrinsics.yml] [size]");
        return false;
    }
    TheInputVideo=args[0];
    if (args.length>=2)
        TheIntrinsicFile=args[1];
    if (args.length>=3)
        TheMarkerSize=Float.parseFloat(args[2]);

    if (args.length==2)
        System.out.println("NOTE: You need makersize to see 3d info!!!!");
    return true;
}

 public static void main(String[] args) {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     arucoTest inst = new arucoTest();
     
     try{
         
         if(inst.readArguments(args)==false)
             System.exit(0); 
        
         if(inst.TheInputVideo.equals("live")){
             inst.TheVideoCapturer.open(0);
             inst.waitTime = 10;
         }else{
             inst.TheVideoCapturer.open(Integer.parseInt(inst.TheInputVideo));
         }
         
         //Time for cam to initialize
         Thread.sleep(1000);
         
         if (!inst.TheVideoCapturer.isOpened()) {
            System.out.println("Could not open video");
            System.exit(-1);
         }
         
         //read first image to get the dimensions
         //TheVideoCapturer>>TheInputImage; Sustituido por las 2 líneas que siguen
         inst.TheVideoCapturer.grab();
         inst.TheVideoCapturer.retrieve(inst.TheInputImage);
         
        //read camera parameters if passed
        if (!inst.TheIntrinsicFile.equals("")) {
            inst.TheCameraParameters.readFromXMLFile(inst.TheIntrinsicFile);
            inst.TheCameraParameters.resize(inst.TheInputImage.size());
        }
        //Configure other parameters
        if (inst.ThePyrDownLevel>0)
            inst.MDetector.pyrDown(inst.ThePyrDownLevel);
        
        inst.MDetector.getThresholdParams(inst.ThresParam1, inst.ThresParam2);
        inst.MDetector.setCornerRefinementMethod(MarkerDetector.CornerRefinementMethod.LINES);
        inst.iThresParam1= (int) inst.ThresParam1;
        inst.iThresParam2= (int) inst.ThresParam2;
        
        char key=0;
        int index=0;
        //capture until press ESC or until the end of the video
        while ( key!=27 && inst.TheVideoCapturer.grab())
        {
            inst.TheVideoCapturer.retrieve( inst.TheInputImage);
            //copy image

            index++; //number of images captured
            double tick = (double)Core.getTickCount();//for checking the speed
            //Detection of markers in the image passed
            inst.MDetector.detect(inst.TheInputImage,inst.TheMarkers,inst.TheCameraParameters,inst.TheMarkerSize);
            //chekc the speed by calculating the mean speed of all iterations
            inst.AvrgTimeFirst+=((double)Core.getTickCount()-tick)/Core.getTickFrequency();
            inst.AvrgTimeSecond++;
            System.out.println("Time detection="+1000*inst.AvrgTimeFirst/inst.AvrgTimeSecond+" milliseconds");

            //print marker info and draw the markers in image
            inst.TheInputImage.copyTo(inst.TheInputImageCopy);
            for (int i=0;i<inst.TheMarkers.size();i++) {
                System.out.println(inst.TheMarkers.get(i));
                inst.TheMarkers.get(i).draw(inst.TheInputImageCopy, new Scalar(0,0,255),1);
            }
            
            //print other rectangles that contains no valid markers
       /**     for (unsigned int i=0;i<MDetector.getCandidates().size();i++) {
                aruco::Marker m( MDetector.getCandidates()[i],999);
                m.draw(TheInputImageCopy,cv::Scalar(255,0,0));
            }*/



            //draw a 3d cube in each marker if there is 3d info
         //   if (  TheCameraParameters.isValid())
           //     for (unsigned int i=0;i<TheMarkers.size();i++) {
             //       CvDrawingUtils::draw3dCube(TheInputImageCopy,TheMarkers[i],TheCameraParameters);
               //     CvDrawingUtils::draw3dAxis(TheInputImageCopy,TheMarkers[i],TheCameraParameters);
               // }
            //DONE! Easy, right?
            
            //show input with augmented information and  the thresholded image
            show("in", inst.TheInputImageCopy);
            show("thres", inst.MDetector.getThresholdedImage());

            //key=Highgui.waitKey(inst.waitTime);//wait for key to be pressed
        }
     }catch(Exception e){
         System.out.println("Exception: " + e.getMessage());
     }
    }

     static{
         System.loadLibrary("WrapperCPP");
    }
     
static void show(String windowName, Mat img){
         MatOfByte matOfByte = new MatOfByte();
     
         Highgui.imencode(".jpg", img, matOfByte); 

     byte[] byteArray = matOfByte.toArray();
     BufferedImage bufImage = null;
   
     try {
         InputStream in = new ByteArrayInputStream(byteArray);
         bufImage = ImageIO.read(in);
     } catch (Exception e) {
                  System.out.println("Exception: " + e.getMessage());
     }
    JLabel picLabel = new JLabel(new ImageIcon(bufImage));
    JFrame frame = new JFrame(windowName);
    frame.add(picLabel);
    frame.pack();
    frame.setVisible(true);
     }
}

