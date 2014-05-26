/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package probandojavaopencv;

import org.opencv.core.Mat;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author jayzeegp
 */
public class arucoSelectOptimalMarkers {

    int HammDist_(Mat m1, Mat m2){
        int dist=0;
        for (int y=0;y<5;y++)
            for (int x=0;x<5;x++)
                if (m1.get(y,x)!=m2.get(y,x)) 
                    dist++;
                
        return dist;
}
    
    
Mat rotate(Mat  in)
{
    Mat out = new Mat();
    in.copyTo(out);
    for (int i=0;i<in.rows();i++)
    {
        for (int j=0;j<in.cols();j++)
        {
            out.put(i, j, in.get(in.cols()-j-1,i));
        }
    }
    return out;
}


int HammDist(Mat m1, Mat m2)
{
    Mat mc=m1.clone();
    int minD=Integer.MAX_VALUE;
    for(int i=0;i<4;i++){
      int dist=HammDist_(mc,m2);
      if( dist<minD) minD=dist;
      mc= rotate(mc);
    }
    return minD;

}

int entropy(Mat marker)
{
  
  //the entropy is calcualte for each bin as the number of elements different from it in its sourroundings
  int totalEntropy=0;
    for (int y=0;y<5;y++)
        for (int x=0;x<5;x++){
            int minX=Math.max(x-1,0);
            int maxX=Math.min(x+1,5);
            int minY=Math.max(y-1,0);
            int maxY=Math.min(y+1,5); 
            for(int yy=minY;yy<maxY;yy++)
                for(int xx=minX;xx<maxX;xx++)
                    if (marker.get(y,x)!=marker.get(yy,xx)) 
                        totalEntropy++;
        }
    return totalEntropy;
}

public static void main(String args[])
{
    try {
        if (args.length<3) {

            //You can also use ids 2000-2007 but it is not safe since there are a lot of false positives.
            System.out.println("Usage: nofMarkers outbasename size [minimum_entropy(9,25)]");
            System.exit(-1);
        }
        
      
        //create a vector with all markers
        int minimimEntropy=0;
        if(args.length>=4) 
            minimimEntropy=Integer.parseInt(args[3]);
        ArrayList <Mat> markers = new ArrayList();
        ArrayList <Integer> ventropy = new ArrayList();
        
        for (int i=0;i<1024;i++){
            markers.push_back(aruco::FiducidalMarkers::getMarkerMat(i) );
 	    ventropy.push_back(entropy( aruco::FiducidalMarkers::getMarkerMat(i) ));
	}
	 System.out.println("Calculating distance matrix");
        //create a matrix with all distances
        Mat distances=Mat.zeros(1024,1024, CvType.CV_32SC1);
        for (int i=0;i<1024;i++)
            for (int j=i+1;j<1024;j++)
                distances.at<int>(i,j)=distances.at<int>(j,i)= HammDist (  markers[i],markers[j]);
	System.out.println("done");
	    //
        int nMarkers=Integer.parseInt(args[0]);
        //select the first marker
        ArrayList<Boolean> usedMarkers = new ArrayList(Collections.nCopies(1024, false));
        
 
	
	
        ArrayList <Integer> selectedMarkers = new ArrayList();
        //select the masker with higher entropy first
        int bestEntr=0;
        for(int i=0;i<ventropy.size();i++)
            if (ventropy.get(i)>ventropy.get(bestEntr))
                bestEntr=i;
        
        selectedMarkers.push_back(bestEntr);
        usedMarkers.set(bestEntr,true);
        
        //remove these with low entropy. Not very elegnat. Other day I will improve the algorithm
        //to make it multiobjective
        for(int i=0;i<ventropy.size();i++)
            if (ventropy.get(i)<minimimEntropy) usedMarkers.set(i,true);
        

        System.out.println("Max Entroy in ="+bestEntr+" "+ventropy.get(bestEntr));
        //add new markers according to the distance added to the global
        for (int i=1;i<nMarkers;i++) {
	  int bestMarker=-1;
	  int shorterDist=0;
            //select as new marker the one that maximizes mean distance to
            for (int j=0;j<distances.cols();j++) {
                if (!usedMarkers.get(i)) {
                    int minDist=Integer.MAX_VALUE;
                    for (int k=0;k<selectedMarkers.size();k++)
                        if (distances.get( selectedMarkers.get(k), j)<minDist) 
                            minDist=distances.get( selectedMarkers.get(k), j);
                        //cout<<"j="<<j<<" "<<distSum<<"|"<<flush;
		    if (minDist>shorterDist){ 
		      shorterDist=minDist;
		      bestMarker=j;
		    }
                }
            }
            if (bestMarker!=-1 && shorterDist>1 ){
	      selectedMarkers.push_back(bestMarker);
	      usedMarkers.set(bestMarker,true);
// 	      cout<<"Best id="<<bestMarker<<" dist="<<farthestDist<< endl;
	    }
            else {
                System.out.println("COUDL NOT ADD ANY MARKER");
                System.exit(0);
            }
            }}
//             char c;cin>>c;
        }
        
        sort(selectedMarkers.begin(),selectedMarkers.end());
        for(int i=0;i<selectedMarkers.size();i++){
	  char name[1024];
	  sprintf(name,"%s%d.png",args[1],selectedMarkers[i]);
// 	  cout<<"name="<<name<<endl;
	  cout<<selectedMarkers[i]<<" "<<flush;
	  Mat markerImage=aruco::FiducidalMarkers::createMarkerImage(selectedMarkers[i],atoi(argv[3]));
	  imwrite(name,markerImage);
	}
	cout<<endl;
	//print the minimim distance between any two  elements
	int minDist=Integer.MAX_VALUE;
	for(size_t i=0;i<selectedMarkers.size()-1;i++)
	  for(size_t j=i+1;j<selectedMarkers.size();j++){
// 	    cout<<" d=" << selectedMarkers[i]<<" "<<selectedMarkers[j]<<"->"<<distances.at<int> ( selectedMarkers[i], selectedMarkers[j])<<endl;
	    if (distances.at<int> ( selectedMarkers[i], selectedMarkers[j]) < minDist) minDist=distances.at<int> ( selectedMarkers[i], selectedMarkers[j]);
	    
	  }
	    
	
   System.out.println("Min Dist="+minDist);
}
