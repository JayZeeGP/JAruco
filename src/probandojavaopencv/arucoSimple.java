/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import wrapper.*;

/**
 *
 * @author jayzeegp
 */
public class arucoSimple{     

 public static void main(String[] args) {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    
     try
    {
        if (args.length<1) {
            System.out.println("Usage: (in.jpg|in.avi) [cameraParams.yml] [markerSize] [outImage]");
            System.exit(0);
        }

        CameraParameters CamParam = new CameraParameters();
        MarkerDetector MDetector = new MarkerDetector();
        ArrayList <Marker> Markers;
        Markers = new ArrayList<>();
        float MarkerSize=-1;
        //read the input image
        Mat InImage = new Mat();
        //try opening first as video
        VideoCapture vreader = new VideoCapture(Integer.parseInt(args[0]));
        if (vreader.isOpened()) {
            vreader.grab();
            vreader.retrieve(InImage);
        }
        else {
            InImage=Highgui.imread(args[0]);
        }
        //at this point, we should have the image in InImage
        //if empty, exit
        if (InImage.total()==0) {
            System.out.println("Could not open input");
            System.exit(0);
        }
        
        //read camera parameters if specifed
        if (args.length>=2) {
            CamParam.readFromXMLFile(args[1]);
            //resizes the parameters to fit the size of the input image
            CamParam.resize(InImage.size());
        }
        
        //read marker size if specified
        if (args.length>=3) MarkerSize=Float.parseFloat(args[2]);
        
        //Ok, let's detect
        MDetector.detect(InImage,Markers,CamParam,MarkerSize);
        //for each marker, draw info and its boundaries in the image
        for (int i=0;i<Markers.size();i++) {
            System.out.println(Markers.get(i));
            Markers.get(i).draw(InImage,new Scalar(0,0,255),2);
        }
        //draw a 3d cube in each marker if there is 3d info
        if (CamParam.isValid() && MarkerSize!=-1)
            for (int i=0;i<Markers.size();i++) {
                //CvDrawingUtils::draw3dCube(InImage,Markers[i],CamParam);
            }
        //show input with augmented information
        show("in",InImage);
	//show also the internal image resulting from the threshold operation
        show("thes", MDetector.getThresholdedImage() );
        
        cv::waitKey(0);//wait for key to be pressed


        if (args.length>=4) Highgui.imwrite(args[3],InImage);
    } catch (Exception e){
        System.out.println("Exception: "+e.getMessage());        
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

