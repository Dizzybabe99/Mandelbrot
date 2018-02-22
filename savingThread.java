/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 17.02.2018
  * @author 
  */
import java.util.*;
import java.lang.*;
import java.io.File;
import java.util.concurrent.*;

public class savingThread implements Runnable{
  
  // Anfang Attribute
  int imgHeight;
  int imgWidth;
  int[][] iterationPP;
  int[] colorCycle;
  String savePath;
  int iteration;
  
  int[][] imgColorsPP;
  ImagePx imgC = new ImagePx();
  // Ende Attribute
  
  public savingThread(int imgHeight, int imgWidth, int[][] iterationPP, int[] colorCycle, String savePath, int iteration) {
    this.imgHeight = imgHeight;
    this.imgWidth = imgWidth;
    this.iterationPP = iterationPP;
    this.colorCycle = colorCycle;
    this.savePath = savePath;
    this.iteration = iteration;
    this.imgColorsPP = null;
    this.iteration = 0;
    imgColorsPP = new int[imgHeight][imgWidth];
  }

  // Anfang Methoden
  public void createColors() {
    int cCycle = 0;
    for (int i = 0; i<imgHeight; i++) {               //y-Coordinate
      for (int j = 0; j<imgWidth; j++) {              //x-Coordinate
        if (iterationPP[i][j] < 0){
          imgColorsPP[i][j] = 0; 
        } else {
          cCycle = iterationPP[i][j]%colorCycle.length;
          imgColorsPP[i][j] = colorCycle[cCycle];
        }// end of if
      } // end of for
    } // end of for
  }
  
  public void createImg() {
    imgC.makeImage(imgWidth,imgHeight,imgColorsPP,new File(savePath + "\\" + iteration + ".jpg"));
  }
  
  public void run() {
    createColors();
    createImg();
  }
  // Ende Methoden
} // end of savingThread
