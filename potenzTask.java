/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 17.02.2018
  * @author 
  */

public class potenzTask implements Runnable{
  
  // Anfang Attribute
  Mandelbrot parent;
  int number;
  int totalThreads;
  int imgHeight;
  int imgWidth;
  int iteration = 0;
  double potenz;
  int maxIterations = 100;
  
  // Ende Attribute
  
  // Anfang Methoden
  public potenzTask(Mandelbrot parent, int number, int totalThreads) {
    this.parent = parent;
    this.number = number;
    this.totalThreads = totalThreads;
    this.imgHeight = parent.imgHeight;
    this.imgWidth = parent.imgWidth;
    this.potenz = parent.potenz;
  }
  public potenzTask(Mandelbrot parent, int number, int totalThreads, int maxIterations) {
    this.parent = parent;
    this.number = number;
    this.maxIterations = maxIterations;
    this.totalThreads = totalThreads;
    this.imgHeight = parent.imgHeight;
    this.imgWidth = parent.imgWidth;
    this.potenz = parent.potenz;
  }
  
  public void run() {
    for (int cycles = 0; cycles < maxIterations; cycles+=1) {
      for (int i = (int)Math.floor((double)((imgHeight/totalThreads)*number)); i<(int)Math.floor((double)((imgHeight/totalThreads)*(number+1))); i++) {               //y-Coordinate
        for (int j = 0; j<imgWidth; j++) {              //x-Coordinate
          if (parent.iterationPP[i][j]<0) {
            if (parent.complexPP[i][j].abs()>=2) {
              parent.iterationPP[i][j] = iteration;
            } else {
              Complex temp = new Complex();
              temp.setAll(parent.complexPP[i][j]);
              //complexPP[i][j].sqr();
              parent.complexPP[i][j].pow(potenz);
              
              parent.complexPP[i][j].add(parent.complexPP0[i][j]);
            }// end of if
          } // end of if
        } // end of for
      } // end of for
      
      iteration++;
    } // end of for
  }
  // Ende Methoden
} // end of potenzTask
