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
import org.opencv.core.Size;

import wrapper.*;

/**
 *
 * @author jayzeegp
 */
public class ProbandoJavaOpenCV {
    
 public static void main(String[] args) {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

     CameraParameters camParam;
     camParam = new CameraParameters();
     MarkerDetector mDetector;
     mDetector = new MarkerDetector();
     ArrayList <Marker> markers;
     markers = new ArrayList<>();
     BoardDetector bd;
     bd = new BoardDetector(true);
     Board board;
     board = new Board();
     BoardConfiguration boardC;
     boardC = new BoardConfiguration();
     FiducidalMarkers fm = new FiducidalMarkers();
              
     Mat img = Highgui.imread("/home/jayzeegp/NetBeansProjects/ProbandoAruco/img/image-test.png");

     
     mDetector.detect(img, markers, camParam, -1);
     ArrayList <Integer>excludedIds = new ArrayList <>();
     excludedIds.add(23);
     excludedIds.add(12);
     excludedIds.add(45);
     excludedIds.add(11);
     excludedIds.add(44);
     excludedIds.add(22);
     
     
     try{
         //bd.detect(markers, boardC, board, camParam);
         //Mat returned = fm.createBoardImage(new Size(2,3), 50, 5, boardC, excludedIds);
         //Mat returned = fm.createBoardImage_ChessBoard(new Size(2,3), 60, boardC, false, excludedIds);
         Mat returned = fm.createBoardImage_Frame(new Size(2,3), 50, 5, boardC, false, excludedIds);
         MatOfByte matOfByte = new MatOfByte();
     
        Highgui.imencode(".jpg", returned, matOfByte); 

        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        boardC.saveToFile("/home/jayzeegp/NetBeansProjects/ProbandoAruco/board.txt");
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
        }
       JLabel picLabel = new JLabel(new ImageIcon(bufImage));
       JFrame frame = new JFrame("Original");
       frame.add(picLabel);
       frame.pack();
       frame.setVisible(true);
       
     }catch(Exception e){
         System.out.println("Exception: " + e.getMessage());
     }
     
     
     
     for(int i=0;i<markers.size();i++){
         if(markers.get(i).isValid()){
            markers.get(i).draw(img, new Scalar(255,0,0));
         }
     }
    
     
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
    JFrame frame = new JFrame("Original");
    frame.add(picLabel);
    frame.pack();
    frame.setVisible(true);
    Mat resultado;  
    
    
    }

     static{
         System.loadLibrary("WrapperCPP");
    }
}

