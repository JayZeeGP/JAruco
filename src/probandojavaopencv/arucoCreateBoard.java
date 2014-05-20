/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import wrapper.*;

/**
 *
 * @author jayzeegp
 */
public class arucoCreateBoard {
    
 public static void main(String[] args) throws Exception {
     System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
     try{
            if(args.length<3){
                System.out.println("Usage: X:Y boardImage.png boardConfiguration.yml [pixSize] [Type(0: panel,1: chessboard, 2: frame)] [interMarkerDistance(0,1)]");
                System.exit(-1);
            }

            int XSize, YSize;
            String pattern = "\\d+:\\d+";
            if(args[0].matches(pattern)&&args[0].split(":").length!=2){
                System.out.println("Incorrect X:Y specification");
                System.exit(-1);
            }

            XSize = Integer.parseInt(args[0].split(":")[0]);
            YSize = Integer.parseInt(args[0].split(":")[1]);

            System.out.println("XSize="+XSize);
            System.out.println("YSize="+YSize);
            
            int pixSize=100;
            float interMarkerDistance=0.2f;
            boolean isChessBoard=false;
            int typeBoard=0;
            
            if(args.length>=4) pixSize = Integer.parseInt(args[3]);
            if(args.length>=5) typeBoard = Integer.parseInt(args[4]);
            if(args.length>=6) interMarkerDistance = Integer.parseInt(args[5]);

            System.out.println("pixSize="+pixSize);
            System.out.println("typeBoard="+typeBoard);
            System.out.println("interMarkerDistance="+interMarkerDistance);
            
            BoardConfiguration BInfo = new BoardConfiguration();
            FiducidalMarkers fm = new FiducidalMarkers();
            Mat BoardImage = new Mat();

            if (typeBoard==0)
                BoardImage = fm.createBoardImage(new Size(XSize,YSize), pixSize,(int) (pixSize*0.2),BInfo);
            else if (typeBoard==1)
                BoardImage = fm.createBoardImage_ChessBoard(new Size(XSize,YSize), pixSize,BInfo);
            else if (typeBoard==2)
                BoardImage = fm.createBoardImage_Frame(new Size(XSize,YSize), pixSize,(int) (pixSize*0.2),BInfo);
            else {
                System.out.println("Incorrect board type");
                System.exit(-1);
            }
            
            Highgui.imwrite(args[1], BoardImage);
            BInfo.saveToFile(args[2]);

     }catch(Exception e){
         System.out.println("Exception: " + e.getMessage());
     }
 }

     static{
         System.loadLibrary("WrapperCPP");
    }
}

