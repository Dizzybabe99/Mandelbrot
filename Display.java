import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 11.02.2018
  * @author 
  */

public class Display extends JFrame {
  // Anfang Attribute
  public JLabel label = new JLabel();
  public Container cp;
  // Ende Attribute
  
  public Display(int width, int height) { 
    // Frame-Initialisierung
    super();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    int frameWidth = width; 
    int frameHeight = height;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Display");
    setResizable(true);
    cp = getContentPane();
    cp.setLayout(null);
    cp.setPreferredSize(new Dimension(frameWidth, frameHeight));
    // Anfang Komponenten
    
    label.setBounds(0, 0, width, height);
    cp.add(label);
    // Ende Komponenten
    
    //setVisible(true);
  } // end of public Display
  
  // Anfang Methoden
  public void changeImg(String newImgPath) {
    try {
      label.setIcon(new ImageIcon(ImageIO.read(new File(newImgPath))));
    } catch(Exception e) {
      System.out.println(e);
    } // end of try
    
  }
  // Ende Methoden
} // end of class Display
