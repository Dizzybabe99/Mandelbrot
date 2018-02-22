import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*; 
import java.util.concurrent.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 11.02.2018
  * @author 
  */

public class Controller extends JFrame{
  // Anfang Attribute
  private JScrollPane jScrollPane1;
  private JScrollBar jScrollBar1 = new JScrollBar();
  private JScrollBar jScrollBar2 = new JScrollBar();
  
  Mandelbrot mandel;
  private JButton breakButton = new JButton();
  Display img;
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel savePath = new JLabel();
  String sPath = null;
  private JButton changeSave = new JButton();
  private JLabel jLabel4 = new JLabel();
  private JNumberField offsetX = new JNumberField();
  private JLabel jLabel5 = new JLabel();
  private JNumberField offsetY = new JNumberField();
  private JLabel jLabel6 = new JLabel();
  private JNumberField scaleE = new JNumberField();
  private JLabel jLabel7 = new JLabel();
  private JSpinner potenz = new JSpinner();
  private SpinnerNumberModel potenzModel = new SpinnerNumberModel(2, 1, 100, 1);
  private JLabel jLabel8 = new JLabel();
  private JSpinner extraIterations = new JSpinner();
  private SpinnerNumberModel extraIterationsModel = new SpinnerNumberModel(50, 0, 300, 1);
  private JLabel jLabel9 = new JLabel();
  private JSpinner threads = new JSpinner();
  private SpinnerNumberModel threadsModel = new SpinnerNumberModel(4, 1, 32, 1);
  private JLabel jLabel10 = new JLabel();
  private JLabel jLabel11 = new JLabel();
  private JLabel jLabel12 = new JLabel();
  private JSpinner stepSize = new JSpinner();
  private SpinnerNumberModel stepSizeModel = new SpinnerNumberModel(1, 1, 100, 1);
  private JLabel jLabel13 = new JLabel();
  private JSpinner imgWidth = new JSpinner();
  private SpinnerNumberModel imgWidthModel = new SpinnerNumberModel(1080, 1, 4000, 1);
  private JSpinner imgHeight = new JSpinner();
  private SpinnerNumberModel imgHeightModel = new SpinnerNumberModel(1080, 1, 4000, 1);
  private JSpinner start = new JSpinner();
    private SpinnerNumberModel startModel = new SpinnerNumberModel(0, -999999999, 999999999, 1);
  private JSpinner end = new JSpinner();
    private SpinnerNumberModel endModel = new SpinnerNumberModel(0, -999999999, 999999999, 1);
  private JSpinner stepScale = new JSpinner();
  private SpinnerNumberModel stepScaleModel = new SpinnerNumberModel(0, 0, 10, 1);
  private JComboBox Mode = new JComboBox();
  private DefaultComboBoxModel ModeModel = new DefaultComboBoxModel();
  private JLabel jLabel3 = new JLabel();
  private SelectPath selectPath = new SelectPath();
  private JButton startButton = new JButton();
  ExecutorService es;
  // Ende Attribute
  
  public Controller() {
    // Frame-Initialisierung
    super();
    
    sPath = selectPath.jFileChooser1_openFile(1).toString();
    
    jScrollPane1 = new JScrollPane();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    int frameWidth = 887; 
    int frameHeight = 863;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setTitle("Controller");
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    
    jScrollPane1.setBounds(8, 184, 857, 625);
    cp.add(jScrollPane1);
    
    jScrollBar1.setBounds(0, 840, 841, 17);
    jScrollBar1.setOrientation(Scrollbar.HORIZONTAL);
    //jScrollPane1.setViewportView(jScrollBar1);
    jScrollBar2.setBounds(840, 0, 17, 841);
    jScrollBar2.setOrientation(SwingConstants.VERTICAL);
    //jScrollPane1.setViewportView(jScrollBar2);
    System.out.println(jScrollPane1.getViewportBorderBounds());
    
    jScrollPane1.setHorizontalScrollBar(jScrollBar1);
    //jScrollPane1.setHorizontalScrollBarPolicy(32);
    jScrollPane1.setVerticalScrollBar(jScrollBar2);
    //jScrollPane1.setVerticalScrollBarPolicy(22);
    
    breakButton.setBounds(768, 72, 89, 57);
    breakButton.setText("BREAK");
    breakButton.setMargin(new Insets(2, 2, 2, 2));
    breakButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        breakButton_ActionPerformed(evt);
      }
    });
    breakButton.setEnabled(false);
    cp.add(breakButton);
    jLabel1.setBounds(8, 8, 91, 25);
    jLabel1.setText("Image Width");
    cp.add(jLabel1);
    jLabel2.setBounds(8, 40, 91, 25);
    jLabel2.setText("Image Height");
    cp.add(jLabel2);
    savePath.setBounds(8, 144, 739, 25);
    savePath.setText("Save Path: " + sPath);
    cp.add(savePath);
    changeSave.setBounds(752, 144, 113, 25);
    changeSave.setText("Change save path");
    changeSave.setMargin(new Insets(2, 2, 2, 2));
    changeSave.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        changeSave_ActionPerformed(evt);
      }
    });
    cp.add(changeSave);
    jLabel4.setBounds(184, 8, 83, 25);
    jLabel4.setText("Offset X");
    cp.add(jLabel4);
    offsetX.setBounds(272, 8, 145, 25);
    offsetX.setText("0.4249999");
    offsetX.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        offsetX_ActionPerformed(evt);
      }
    });
    cp.add(offsetX);
    jLabel5.setBounds(184, 40, 83, 25);
    jLabel5.setText("Offset Y");
    cp.add(jLabel5);
    offsetY.setBounds(272, 40, 145, 25);
    offsetY.setText("0.2040000290003");
    offsetY.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        offsetY_ActionPerformed(evt);
      }
    });
    cp.add(offsetY);
    jLabel6.setBounds(424, 8, 83, 25);
    jLabel6.setText("Scale E");
    cp.add(jLabel6);
    scaleE.setBounds(512, 8, 65, 25);
    scaleE.setText("0");
    cp.add(scaleE);
    jLabel7.setBounds(424, 40, 83, 25);
    jLabel7.setText("Potenz");
    cp.add(jLabel7);
    potenz.setBounds(512, 40, 65, 25);
    potenz.setValue(2);
    potenz.setModel(potenzModel);
    cp.add(potenz);
    
    jLabel8.setBounds(584, 8, 91, 25);
    jLabel8.setText("Extra Iterations");
    cp.add(jLabel8);
    extraIterations.setBounds(680, 8, 57, 25);
    extraIterations.setValue(50);
    extraIterations.setModel(extraIterationsModel);
    cp.add(extraIterations);
    
    jLabel9.setBounds(584, 40, 91, 25);
    jLabel9.setText("Threads");
    cp.add(jLabel9);
    threads.setBounds(680, 40, 57, 25);
    threads.setValue(4);
    threads.setModel(threadsModel);
    cp.add(threads);
    
    jLabel10.setBounds(8, 72, 91, 25);
    jLabel10.setText("Start");
    cp.add(jLabel10);
    jLabel11.setBounds(184, 72, 83, 25);
    jLabel11.setText("End");
    cp.add(jLabel11);
    jLabel12.setBounds(424, 72, 83, 25);
    jLabel12.setText("Step size");
    cp.add(jLabel12);
    stepSize.setBounds(512, 72, 65, 25);
    stepSize.setValue(1);
    stepSize.setModel(stepSizeModel);
    cp.add(stepSize);
    
    jLabel13.setBounds(584, 72, 99, 25);
    jLabel13.setText("Step Scale 10^-..");
    cp.add(jLabel13);
    
    imgWidth.setBounds(104, 8, 73, 25);
    imgWidth.setValue(1080);
    imgWidth.setModel(imgWidthModel);
    cp.add(imgWidth);
    
    imgHeight.setBounds(104, 40, 73, 25);
    imgHeight.setValue(1080);
    imgHeight.setModel(imgHeightModel);
    cp.add(imgHeight);
    
    start.setBounds(104, 72, 73, 25);
    start.setValue(0);
    start.setModel(startModel);
    start.setToolTipText("inclusive Startpoint");
    cp.add(start);
    
    end.setBounds(272, 72, 65, 25);
    end.setValue(0);
    end.setModel(endModel);
    end.setToolTipText("inclusive Endpoint (if End%StepSize == 0)");
    cp.add(end);
    
    stepScale.setBounds(680, 72, 57, 25);
    stepScale.setValue(0);
    stepScale.setModel(stepScaleModel);
    cp.add(stepScale);
    
    Mode.setModel(ModeModel);
    Mode.setBounds(104, 104, 305, 25);
    Mode.addItem("Single iterations");
    Mode.addItem("Full iterations");
    Mode.addItem("Zoom");
    Mode.addItem("Potenz change");
    Mode.setSelectedIndex(1);
    cp.add(Mode);
    jLabel3.setBounds(8, 104, 91, 25);
    jLabel3.setText("Mode");
    cp.add(jLabel3);
    startButton.setBounds(768, 8, 89, 57);
    startButton.setText("START");
    startButton.setMargin(new Insets(2, 2, 2, 2));
    startButton.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        startButton_ActionPerformed(evt);
      }
    });
    cp.add(startButton);
    // Ende Komponenten
    
    setVisible(true);
  } // end of public Controller
  
  // Anfang Methoden
  
  /*public static void main(String[] args) {
    new Controller();
  } // end of main */
  
  //onStart do:
  //Display(imgWidth.getValue(), imgHeight.getValue());     
  //jScrollPane1.setViewportView(this.img);
  
  public void breakButton_ActionPerformed(ActionEvent evt) {
    mandel.breakExecution();
  } // end of breakButton_ActionPerformed

  public void changeSave_ActionPerformed(ActionEvent evt) {
    sPath = selectPath.jFileChooser1_openFile().toString();
    savePath.setText("Save Path: " + sPath);
  } // end of changeSave_ActionPerformed

  public void offsetX_ActionPerformed(ActionEvent evt) {
    if (offsetX.getDouble()>1) offsetX.setDouble(1);
    else if (offsetX.getDouble()<-1) offsetX.setDouble(-1);// end of if
  } // end of offsetX_ActionPerformed

  public void offsetY_ActionPerformed(ActionEvent evt) {
    if (offsetY.getDouble()>1) offsetY.setDouble(1);
    else if (offsetY.getDouble()<-1) offsetY.setDouble(-1);// end of if
  } // end of offsetY_ActionPerformed

  public void done() {
    startButton.setEnabled(true);
    breakButton.setEnabled(false);
  }
  
  public void startButton_ActionPerformed(ActionEvent evt) {
    try {
      
      imgWidth.commitEdit(); System.out.println(285);
      imgHeight.commitEdit(); System.out.println(286);
      start.commitEdit();     System.out.println(287);
      end.commitEdit();       System.out.println(288);
      potenz.commitEdit();    System.out.println(289);
      stepSize.commitEdit();  System.out.println(290);
      extraIterations.commitEdit(); System.out.println(291);
      threads.commitEdit();   System.out.println(292);
      stepScale.commitEdit(); System.out.println(293);
      
      img = new Display((int)imgWidth.getValue(), (int)imgHeight.getValue());
      jScrollPane1.setViewportView(this.img.cp);
      
      mandel = new Mandelbrot((int)imgWidth.getValue(),(int)imgHeight.getValue(), sPath, offsetX.getDouble(), offsetY.getDouble(), scaleE.getDouble(), (int)potenz.getValue(), (int)extraIterations.getValue(), (int)threads.getValue(), img, (int)stepSize.getValue(), (int)start.getValue(), (int)end.getValue(), (int)stepScale.getValue(),this.Mode.getSelectedIndex(), this);
      
      System.out.println(this.Mode.getSelectedIndex());
      
      breakButton.setEnabled(true);
      startButton.setEnabled(false);
      es = Executors.newCachedThreadPool();
      es.execute(mandel);
    } catch(Exception e) {
      System.out.println(e);
    }// end of try
  } // end of startButton_ActionPerformed


  // Ende Methoden
} // end of class Controller
