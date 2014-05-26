/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;

import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import wrapper.*;

/**
 *
 * @author jayzeegp
 */
public class arucoSimpleBoard {
    public static void main(String args[]){
        try{
            if(args.length<2) {
                System.out.println("Usage: image  boardConfig.yml [cameraParams.yml] [markerSize]  [outImage]");
                System.exit(0);
            }
            
            CameraParameters CamParam = new CameraParameters();
            MarkerDetector MDetector = new MarkerDetector();
            ArrayList <Marker> Markers = new ArrayList();
            float MarkerSize=-1;
            BoardConfiguration TheBoardConfig = new BoardConfiguration();
            BoardDetector TheBoardDetector = new BoardDetector();
            Board TheBoardDetected = new Board();
            Mat InImage=Highgui.imread(args[0]);
            TheBoardConfig.readFromFile(args[1]);
            if (args.length>=3) {
                CamParam.readFromXMLFile(args[2]);
                //resizes the parameters to fit the size of the input image
                CamParam.resize( InImage.size());
            }     
            
            if (args.length>=4) 
                MarkerSize=Float.parseFloat(args[3]);
            
            cv::namedWindow("in",1);
            MDetector.detect(InImage,Markers);//detect markers without computing R and T information
            //Detection of the board
            float probDetect=TheBoardDetector.detect( Markers, TheBoardConfig,TheBoardDetected, CamParam,MarkerSize);
            //for each marker, draw info and its boundaries in the image
            for(int i=0;i<Markers.size();i++){
                System.out.println(Markers.get(i));
                Markers.get(i).draw(InImage,new Scalar(0,0,255),2);
            }
            
            //draw a 3d cube in each marker if there is 3d info
            if (  CamParam.isValid()){
                for(unsigned int i=0;i<Markers.size();i++){
                CvDrawingUtils::draw3dCube(InImage,Markers[i],CamParam);
                CvDrawingUtils::draw3dAxis(InImage,Markers[i],CamParam);
            }
                CvDrawingUtils::draw3dAxis(InImage,TheBoardDetected,CamParam);
                cout<<TheBoardDetected.Rvec<<" "<<TheBoardDetected.Tvec<<endl;
            }
            //draw board axis            
		//show input with augmented information
		cv::imshow("in",InImage);
		cv::waitKey(0);//wait for key to be pressed
		if(argc>=6) cv::imwrite(argv[5],InImage);
		
	}catch(std::exception &ex)

	{
		cout<<"Exception :"<<ex.what()<<endl;
	}

}
}
