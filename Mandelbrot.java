/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 08.02.2018
  * @author 
  */

import java.util.*;
import java.lang.*;
import java.io.File;
import java.util.concurrent.*;

public class Mandelbrot implements Runnable{
  public int imgWidth = 1080;
  public int imgHeight = 1080;
  String savePath = "D:\\Java-Editor\\Mandelbrot_Kopie\\result";
  
  double offsetX=(double)((0.5-0.0750001));
  double offsetY=(double)((0.2+0.0040000290003));
  double scaleE = 0;
  
  public double potenz = 2;
  
  double scale =  (double) 4/imgHeight*(float)(Math.pow(2,-scaleE));
  double startX =  (double) -2-(imgWidth-imgHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
  double startY =  (double) 2-offsetY*imgHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2; 
  boolean centerAt20x = false;
  
  int iteration = 0;
  public int[][] iterationPP = new int[imgHeight][imgWidth];
  public Complex[][] complexPP = new Complex[imgHeight][imgWidth];
  public Complex[][] complexPP0 = new Complex[imgHeight][imgWidth];
  public int[][] imgColorsPP = new int[imgHeight][imgWidth];
  
  boolean breakExecution = false;
  
  ImagePx imgC = new ImagePx();
  //int[] colorCycle = {0xff0000,0xff8000,0xffff00,0x80ff00,0x00ff00,0x00ff80,0x00ffff,0x0080ff,0x0000ff,0x7f00ff,0xff00ff,0xff007f};
  int[] colorCycle = {0x800000,0x8b0000,0xa52a2a,0xb2222,0xdc143c,0xff0000,0xff6347,0xff7f50,0xcd5c5c,0xf08080,0xe9967a,0xfa8072,0xffa07a,0xff4500,0xff8c00,0xffa500,0xffd700,0xb8860b,0xdaa520,0xeee8aa,0xbdb76b,0xf0e68c,0x808000,0xffff00,0x9acd32,0x556b2f,0x6b8e23,0x7cfc00,0x7fff00,0xadff2f,0x006400,0x008000,0x228b22,0x00ff00,0x32cd32,0x90ee90,0x98fb98,0x8fbc8f,0x00fa9a,0x00ff7f,0x2e8b57,0x66cdaa,0x3cb371,0x20b2aa,0x2f4f4f,0x008080,0x008b8b,0x00ffff,0xe0ffff,0x00ced1};
  //int[] colorCycle = {0xf00000,0xf0f000,0x00f000,0x00f0f0,0x0000f0,0xf000f0};
  //int[] colorCycle = {0xffffff};
  //int[] colorCycle = {0xffffff,0xf7f7f7,0xefefef,0xe7e7e7,0xdfdfdf,0xd7d7d7,0xcfcfcf,0xc7c7c7,0xbfbfbf,0xb7b7b7,0xafafaf,0xa7a7a7,0x9f9f9f,0x979797,0x8f8f8f,0x878787,0x7f7f7f,0x777777,0x6f6f6f,0x676767,0x5f5f5f,0x575757,0x4f4f4f,0x474747,0x3f3f3f,0x373737};
  
  
  Complex comp = new Complex();
  
  int totalPix = imgHeight*imgWidth;
  int nMandel;
  
  
  int imageWidth = 4;
  int imageHeight = 4;
  int iterationsNeeded = 0;
  
  int mode = 0;
  Display disp;
  int extraIterations = 50;
  int threads = 4;
  ExecutorService es;
  int stepSize;
  int start = 0;
  int end = 1;
  int stepScale = 100;
  Controller parent;
  
  public static void main(String[] args) {
    new Controller();
  } // end of main
  
  public Mandelbrot(int imgWidth, int imgHeight, String savePath, double offsetX, double offsetY, double scaleE, double potenz, int extraIterations, int threadCount, Display display, int stepSize, int start, int end, int stepScale, int mode, Controller parent) {
    this.imgWidth = imgWidth;
    this.imgHeight = imgHeight;
    this.savePath = savePath;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.scaleE = scaleE;
    this.potenz = potenz;
    this.mode = mode;
    this.extraIterations = extraIterations;
    this.threads = threadCount;
    this.disp = display;
    this.stepSize = stepSize;
    this.start = start;
    this.end = end;
    this.stepScale = (int)Math.pow(10, stepScale);
    this.mode = mode;
    this.parent = parent;
  }
  
  public void run() {
    switch (mode) {
      case  1:
        System.out.println("Hallo"); 
        this.fullIterate();
        break;
      case  2: 
        this.zoom();
        break;
      case  3:
        this.potenzChange();
        break;
      default: 
        this.singleIterate();
    } 
    parent.done(); 
  }
  
  public void singleIterate() {
    
  }
  
  public void fullIterate() {
    System.out.println("Hallo");
    /*
    Getting the minimus amount of Iterations
    The minimus Iterations is when no longer every Pixel
    of the Result would be Part of the MandelbrotSet
    this is done with a lowResolution 16x16 Image
    done on a single Core 
    */
    
    imageWidth = 16;   //initialization of Arrays              
    imageHeight = 16;
    totalPix = imageHeight*imageWidth;
    nMandel = totalPix;
    iterationsNeeded = 0;
    scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));    
    startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
    startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
    init();
    
    iterationsNeeded = 0;
    while ((double)(nMandel*100/totalPix)==100) { 
      iterationsNeeded++;
      iterate();
      System.out.println(totalPix*100/totalPix);
      
      if (breakExecution) {     //manual Breakpoint
        break;
      } // end of if
    } // end of while
    
    System.out.println(iterationsNeeded);
    
    /* 
    Main part creating the image.
    This is done by doing minIterations + additional Iterations.
    To reduce the Time needen this is multithreaded.
    */
    
    imageWidth = imgWidth;    //initialization of Arrays
    imageHeight = imgHeight;
    scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
    startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
    startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
    iteration = 0;
    init();
    
    for (int i = 0; i<this.extraIterations+iterationsNeeded; i++) {
      es = Executors.newCachedThreadPool();  //Making Threads for execution
      for (int j=0; j<threads; j++) {es.execute(new iterateTask(this, j, threads, 1, i));} // end of for
      es.shutdown();
      try {
        while (!es.awaitTermination(500, TimeUnit.MILLISECONDS)) {  //waiting for threads to finish
          if (breakExecution) {
            es.shutdownNow();
          } // end of if
        } // end of while
      }
      catch(Exception e) {}
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      createColors();
      iteration = i;
      createImg(); 
      disp.changeImg(savePath + "\\" + (iteration) + ".jpg");
    } // end of for                               
  }
  
  public void zoom() {
    iteration = 0;
    for (int s = start; s<end+1; s+=stepSize) {
      scaleE = (double)s/stepScale;
      
      //finding #No. of Iterations to use
      imageWidth = 16;
      imageHeight = 16;
      scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
      startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
      startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
      
      iteration = 0;
      
      init();
      System.out.print("---- Scale " + scaleE + " ----  extra Iterations needed: ");
      totalPix = imageHeight*imageWidth;
      nMandel = totalPix;
      iterationsNeeded = 0;
      
      while ((double)(nMandel*100/totalPix)==100) { 
        iterationsNeeded++;
        iterate();
        if (breakExecution) {
          break;
        } // end of if
      } // end of while
      
      System.out.println(iterationsNeeded);
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      //Doing Iterations
      imageWidth = imgWidth;
      imageHeight = imgHeight;
      scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
      startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
      startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
      iteration = 0;
      init();
      
      es = Executors.newCachedThreadPool();
      for (int i=0; i<threads; i++) {es.execute(new iterateTask(this, i, threads,(iterationsNeeded+50)));} // end of for
      es.shutdown();
      try {
        while (!es.awaitTermination(500, TimeUnit.MILLISECONDS)) {
          if (breakExecution) {
            es.shutdownNow();
          } // end of if
        }} // end of while
      catch(Exception e) {}
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      createColors();
      iteration = s/stepSize;
      createImg();
      
      disp.changeImg(savePath + "\\" + (iteration) + ".jpg"); 
    } // end of for
  }
  
  public void potenzChange() {
    for (int p = start; p<end+1; p+=stepSize) {
      potenz = (double)p/stepScale;
      
      imageWidth = 16;
      imageHeight = 16;
      scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
      startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
      startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
      
      iteration = 0;
      
      init();
      System.out.print("---- Scale " + scaleE + " ----  Iterations needed: ");
      totalPix = imageHeight*imageWidth;
      nMandel = totalPix;
      iterationsNeeded = 0;
      
      while ((double)(nMandel*100/totalPix)==100) { 
        iterationsNeeded++;
        iterate();
        if (breakExecution) {
          break;
        } // end of if
      } // end of while
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      imageWidth = imgWidth;
      imageHeight = imgHeight;
      scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
      startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
      startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
      iteration = 0;
      init();
      
      System.out.println("power " + potenz);
      
      es = Executors.newCachedThreadPool();
      for (int i=0; i<threads; i++) {es.execute(new potenzTask(this, i, threads));} // end of for
      es.shutdown();
      try {
        while (!es.awaitTermination(500, TimeUnit.MILLISECONDS)) {
          if (breakExecution) {
            es.shutdownNow();
          } // end of if
        }} // end of while
      catch(Exception e) {}
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      
      iteration = (int)((p/stepSize)); 
      createColors();
      createImg();
      disp.changeImg(savePath + "\\" + (iteration) + ".jpg");
    }
  }
  
  
  public void breakExecution() {
    this.breakExecution = true;
  }  
  
  public void init() {
    for (int i = 0; i<imgHeight; i++) {               //y-Coordinate
      for (int j = 0; j<imgWidth; j++) {              //x-Coordinate
        complexPP[i][j] = new Complex(startX+j*scale,startY-i*scale);
        complexPP0[i][j] = new Complex(startX+j*scale,startY-i*scale);
        iterationPP[i][j] = -1;
        //System.out.println(startX + "+" + (j*scaleX) + "," + startY + "+" + (i*scaleY) + ": " + complexPP[i][j].readable());
      } // end of for
    } // end of for
  }
  
  public void iterate() {
    for (int i = 0; i<imgHeight; i++) {               //y-Coordinate
      for (int j = 0; j<imgWidth; j++) {              //x-Coordinate
        if (iterationPP[i][j]<0) {
          if (complexPP[i][j].abs()>=2) {
            iterationPP[i][j] = iteration;
            nMandel--;
          } else {
            Complex temp = new Complex();
            temp.setAll(complexPP[i][j]);
            //complexPP[i][j].sqr();
            for(int v=1; v<potenz; v++) {
              complexPP[i][j].multiply(temp);
            } // end of for
            //complexPP[i][j].pow(potenz);
            
            complexPP[i][j].add(complexPP0[i][j]);
          }// end of if
        } // end of if
      } // end of for
    } // end of for
    
    iteration++;
  }
  
  public void iteratePotenz() {
    for (int i = 0; i<imgHeight; i++) {               //y-Coordinate
      for (int j = 0; j<imgWidth; j++) {              //x-Coordinate
        if (iterationPP[i][j]<0) {
          if (complexPP[i][j].abs()>=2) {
            iterationPP[i][j] = iteration;
            nMandel--;
          } else {
            Complex temp = new Complex();
            temp.setAll(complexPP[i][j]);
            //complexPP[i][j].sqr();
            complexPP[i][j].pow(potenz);
            complexPP[i][j].add(complexPP0[i][j]);
          }// end of if
        } // end of if
      } // end of for
    } // end of for
    
    iteration++;
  }
  
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
  
  public void createImg(double sc) {
    int dec = (int)(Math.floor(sc*100) - Math.floor(sc)*100);
    int one = (int)(Math.floor(sc));
    imgC.makeImage(imgWidth,imgHeight,imgColorsPP,new File(savePath + "\\Scale_of_" + one + "_" + dec + ".jpg"));
  }


  /*public void sample() {
    Complex a = new Complex(2.5,3);
    Complex b = new Complex(-4,0.142);
    System.out.print("a: " + a.readable() + "\nb: " + b.readable() + "\n");
    System.out.println("Addition:           " + a.add(a,b).readable());
    System.out.println("Subtraction:        " + a.subtract(a,b).readable());
    System.out.println("Multiplication (*2):" + a.multiply(a,2).readable());
    System.out.println("Multiplication:     " + a.multiply(a,b).readable());
    System.out.println("Division (*2):      " + a.devide(a,2).readable());
    System.out.println("Division:           " + a.devide(a,b).readable());
    System.out.println("Absolute:           " + a.abs());
    System.out.println("Squared:            " + a.sqr().readable());
    
    for (double n = 0; n<=1; n+=0.1) {
      a.setAll(new Complex(0.5,n));
      System.out.print("\n" + a.readable() + ": \n");
      
      for (int i = 0; i<100; i++) {
        if (a.abs()==0) {break;} // end of if
        System.out.println(i + ". " + a.sqr().readable());
        if (a.abs()>=2) {break;} // end of if
      } // end of for
    }
  }  */
  
  

  /*public Mandelbrot() {   //Variable Iterations
    //initiating Arrays
    init();
    
    Display disp = new Display(imgWidth, imgHeight);
    Controller control = new Controller(disp.cp, this);
    
    System.out.println("---- Scale " + scaleE + " ----");
    
    for (int cycles = 0; cycles < 2000; cycles+=1) {
      System.out.println("Iteration " + iteration);
      //      System.out.println(complexPP[500][500].readable());
      //      System.out.println(complexPP0[500][500].readable());
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      iterate();
      createColors();
      createImg();
      disp.changeImg(savePath + "\\" + (iteration) + ".jpg");
    } // end of for
    
    System.out.println(savePath + "\\" + (iteration) + ".jpg");
  }   
  
  
  
  public Mandelbrot(boolean b) {      //Variable Zoom
    //initiating Arrays
    savePath = (new SelectPath()).jFileChooser1_openFile().getAbsolutePath();
    Display disp = new Display(imgWidth, imgHeight);
    Controller control = new Controller(disp.cp, this);
    int imageWidth = 4;
    int imageHeight = 4;
    totalPix = imageHeight*imageWidth;
    
    int iterationsNeeded = 0;
    ExecutorService es;
    int threadCount = 8;
    
    iteration = 0;
    for (int s = -100; s<4501; s+=20) {
      scaleE = (double)s/100;
      
      //finding #No. of Iterations to use
      imageWidth = 32;
      imageHeight = 32;
      scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
      startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
      startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
      
      iteration = 0;
      
      init();
      System.out.print("---- Scale " + scaleE + " ----  Iterations needed: ");
      nMandel = totalPix;
      iterationsNeeded = 0;
      
      while ((double)(nMandel*100/totalPix)==100) { 
        iterationsNeeded++;
        iterate();
      } // end of while
      
      System.out.println(iterationsNeeded+50);
      
      //Doing Iterations
      imageWidth = imgWidth;
      imageHeight = imgHeight;
      scale =  (double) 4/(imageHeight*(double)(Math.pow(2,scaleE)));
      startX =  (double) -2-(imageWidth-imageHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
      startY =  (double) 2-offsetY*imageHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
      iteration = 0;
      init();
      
      es = Executors.newCachedThreadPool();
      for (int i=0; i<threadCount; i++) {es.execute(new iterateTask(this, i, threadCount,(iterationsNeeded+50)));} // end of for
      es.shutdown();
      try {
        while (!es.awaitTermination(500, TimeUnit.MILLISECONDS)) {
          if (breakExecution) {
            es.shutdownNow();
          } // end of if
        }} // end of while
      catch(Exception e) {}
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      /*for (int cycles = 0; cycles < 25; cycles++) {
      //System.out.println("Iteration " + iteration);
      //      System.out.println(complexPP[500][500].readable());
      //      System.out.println(complexPP0[500][500].readable());
      iterate();
      if ((double)(nMandel*100/totalPix)>99) {
      cycles--;
      //System.out.println((double)(nMandel*100/totalPix) + "% Part of Mandelbrot");
      } // end of if;
      } // end of for
      System.out.println((double)(nMandel*100/totalPix) + "% Part of Mandelbrot, after: " + iteration + ", " + (iteration-25));
      
      createColors();
      iteration = s/20;
      createImg();
      
      disp.changeImg(savePath + "\\" + (iteration) + ".jpg"); 
    } // end of for
  }*/ 
  
  
  /*public Mandelbrot(int b) {         //Variable Power
    //initiating Arrays
    savePath = (new SelectPath()).jFileChooser1_openFile().getAbsolutePath();
    Display disp = new Display(imgWidth, imgHeight);
    Controller control = new Controller(disp.cp, this);
    //control.setVisible(false);
    
    offsetX=0;
    offsetY=0;
    scaleE =-0.005;
    
    potenz = 2;
    int threadCount = 8;
    
    scale =  (double) 4/imgHeight*(float)(Math.pow(2,-scaleE));
    startX =  (double) -2-(imgWidth-imgHeight)*scale/2+offsetX+(1-(double)(Math.pow(2,-scaleE)))*2;
    startY =  (double) 2-offsetY*imgHeight*scale-offsetY-(1-(double)(Math.pow(2,-scaleE)))*2;
    ExecutorService es;
    //ExecutorService fileSaver = Executors.newCachedThreadPool();
    
    for (int p = 2000; p<6001; p+=50) {
      iteration = 0;
      potenz = (double)p/1000;
      init();
      System.out.println("power " + potenz);
      
      es = Executors.newCachedThreadPool();
      for (int i=0; i<threadCount; i++) {es.execute(new potenzTask(this, i, threadCount));} // end of for
      es.shutdown();
      try {
        while (!es.awaitTermination(500, TimeUnit.MILLISECONDS)) {
          if (breakExecution) {
            es.shutdownNow();
          } // end of if
        }} // end of while
      catch(Exception e) {}
      
      if (breakExecution) {
        breakExecution = false;
        break;
      } // end of if
      
      
      iteration = (int)((p/50)); 
      createColors();
      createImg();
      disp.changeImg(savePath + "\\" + (iteration) + ".jpg");
      //try {Thread.sleep(500);} catch(Exception e) {} 
    } // end of for
  }   */

} // end of class Mandelbrot
