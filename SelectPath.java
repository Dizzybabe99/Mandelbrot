import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.File;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 15.02.2018
  * @author 
  */

public class SelectPath extends JFrame {
  // Anfang Attribute
  private JFileChooser jFileChooser1 = new JFileChooser();
  // Ende Attribute
  
  public SelectPath() { 
    // Frame-Initialisierung
    super();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    int frameWidth = 300; 
    int frameHeight = 300;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("SelectPath");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    
    jFileChooser1.setCurrentDirectory(new File("."));
    jFileChooser1.setDialogTitle("Please select a Folder to save images");
    jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    jFileChooser1.setAcceptAllFileFilterUsed(false);
    //jFileChooser1_openFile();

    
    // Ende Komponenten
    
    setVisible(false);
  } // end of public SelectPath
  
  // Anfang Methoden
  
  
  public File jFileChooser1_openFile() {
    if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      return jFileChooser1.getSelectedFile();
    } else {
      return null;
    }
  }
  
  public File jFileChooser1_openFile(int i) {
    if (jFileChooser1.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      return jFileChooser1.getSelectedFile();
    } else {
      System.exit(0);
      return null;
    }
  }

  // Ende Methoden
} // end of class SelectPath
