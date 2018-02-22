import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 10.02.2018
  * @author 
  */                                       

public class ImagePx {
  
  public ImagePx(){}
  
  public int color(int red, int green, int blue) {
    int c = 0; 
    c = c | (red<<16);
    c = c | (green<< 8);
    c = c | blue;
    return c;
  }
  
  public BufferedImage bufferImage(int x, int y, int[][] colors) {
    BufferedImage img = new BufferedImage(x,y,BufferedImage.TYPE_INT_RGB);
    
    //System.out.println("Baking Image");
    for (int i = 0; i<y; i++) {
      for (int j = 0; j<x; j++) {
        img.setRGB(j,i,colors[i][j]);
      } // end of for
    } // end of for
    
    return img;
  }  
  
  public BufferedImage makeImage(int x, int y, int[][] colors, File file){
    BufferedImage img = bufferImage(x,y,colors);
    
    //System.out.println("Saving file");
    try {
      ImageIO.write(img, "jpg", file);
      //System.out.println("DONE!");
    } catch(IOException e) {
      System.out.println(e);
    }// end of try
    
    return img;
  }
  
  public void randomImage(int x, int y) {
    int[][] colors = new int[y][x];
    Random rng = new Random();
    
    System.out.println("Randomizing Pixels");
    for (int i = 0; i<y; i++) {
      for (int j = 0; j<x; j++) {
        colors[i][j] = (rng.nextInt() & 0xffffff) | 0xff000000;
      } // end of for
    } // end of for
    
    makeImage(x,y,colors,new File("D:\\Java-Editor\\ImagePx (util)\\Output.jpg"));
  }

} // end of class ImagePx
