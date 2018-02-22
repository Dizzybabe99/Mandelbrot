/**
  *
  * Beschreibung
  * Allows the use of Complex Numbers in java
  *
  * Operations implemented for Complex, Complex:
  * Addition, Subtraction, Multiplication, Subtraction, Absolute
  *
  * Operation implemetned for Complex, float:
  * Addition, Subtraction, Multiplication, Subtraction
  *
  * Operation implemented for Complex, int (if not already in preveous part):
  * -/-
  *
  * ToDo for 1.0:
  * Potenzieren mit 2
  *
  * ToDo for 1.1:
  * Algebraform -> Polarform
  * Potenzieren mit beliebigen natürlichen Exponenten
  *
  * Todo for 1.2:
  * Potenzieren mit beliebigen komlexen Exponenten
  *
  *
  * @version 0.1 vom 08.02.2018
  * @author  Dennis McGinnis
  */

public class Complex {
  
  // Anfang Attribute
  private double real;
  private double complex;
  private double r;
  private double phi;
  // Ende Attribute
  
  public Complex(double Real, double Complex) {
    this.real = Real;
    this.complex = Complex;
  }
  
  public Complex(double r, double phi, boolean x) {
    this.r = r;
    this.phi = phi;
  }

  public Complex() {
  }

  // Anfang get/set
  public double getReal() {
    return real;
  }

  public void setReal(double real) {
    this.real = real;
  }

  public double getComplex() {
    return complex;
  }
  
  public void setComplex(double complex) {
    this.complex = complex;
  }
    
  public double getR() {
    return r;
  }

  public void setR(double r) {
    this.r = r;
  }

  public double getPhi() {
    return phi;
  }

  public void setPhi(double phi) {
    this.phi = phi;
  }
  
  public void setAll(Complex x) {
    this.setReal(x.getReal());
    this.setR(x.getR());
    this.setPhi(x.getPhi());
    this.setComplex(x.getComplex());
  }
    
  //Ende get/set
  
  //Anfang Methoden

  //Return of Content as printable String
  public String readable(Complex x) {
    String z ="";
    if (x.getReal()<0) {z = z + x.getReal();} else {z = z + " " + x.getReal();}// end of if
    if (x.getComplex()<0) {z = z + x.getComplex()+"i";} else {z = z + "+" + x.getComplex()+"i";} // end of if
    return z;
  }
    
  public String readable() {
    String z ="";
    if (this.getReal()<0) {z = z + this.getReal();} else {z = z + " " + this.getReal();}// end of if
    if (this.getComplex()<0) {z = z + this.getComplex()+"i";} else {z = z + "+" + this.getComplex()+"i";} // end of if
    return z;
  }  
    
    //Addition
  public Complex add(Complex x, Complex y) {
    Complex z = new Complex(x.getReal()+y.getReal(),x.getComplex()+y.getComplex());  
    return z;
  }
    
  public Complex add(Complex x, double y) {
    Complex z = new Complex(x.getReal()+y,x.getComplex());
    return z;
  }
    
  public Complex add(Complex y) {
    Complex z = new Complex(this.getReal()+y.getReal(),this.getComplex()+y.getComplex());
    this.setAll(z);
    return z;
  }
    
  public Complex add(double y) {
    Complex z = new Complex(this.getReal()+y,this.getComplex());
    this.setAll(z);
    return z;
  }
    
    //Subtraction
  public Complex subtract(Complex x, Complex y) {
    Complex z = new Complex(x.getReal()-y.getReal(),x.getComplex()-y.getComplex());
    return z;
  }
    
  public Complex subtract(Complex x, double y) {
    Complex z = new Complex(x.getReal()-y,x.getComplex());
    return z;
  }
    
  public Complex subtract(Complex y) {
    Complex z = new Complex(this.getReal()-y.getReal(),this.getComplex()-y.getComplex());
    this.setAll(z);
    return z;
  }
    
  public Complex subtract(double y) {
    Complex z = new Complex(this.getReal()-y,this.getComplex());
    this.setAll(z);
    return z;
  }
    
    //Multiplikation  
  public Complex multiply(Complex x, double y) {
    Complex z = new Complex(x.getReal()*y,x.getComplex()*y);
    return z;
  }
    
  public Complex multiply(Complex x, Complex y) {
    Complex z = new Complex(x.getReal()*y.getReal()-x.getComplex()*y.getComplex(),x.getReal()*y.getComplex()+x.getComplex()*y.getReal());
    return z;
  }
    
  public Complex multiply(double y) {
    Complex z = new Complex(this.getReal()*y,this.getComplex()*y);
    this.setAll(z);
    return z;
  }
    
  public Complex multiply(Complex y) {
    Complex z = new Complex(this.getReal()*y.getReal()-this.getComplex()*y.getComplex(),this.getReal()*y.getComplex()+this.getComplex()*y.getReal());
    this.setAll(z);
    return z;
  }
    
    //Devision  
  public Complex devide(Complex x, double y) {
    Complex z = new Complex(x.getReal()/y,x.getComplex()/y);
    return z;
  }
    
  public Complex devide(Complex x, Complex y) {
    Complex z = new Complex(
    (x.getReal()*y.getReal()+x.getComplex()*y.getComplex())/(y.getReal()*y.getReal()+y.getComplex()*y.getComplex()),
    (x.getComplex()*y.getReal()-x.getReal()*y.getComplex())/(y.getReal()*y.getReal()+y.getComplex()*y.getComplex()));
    return z;
  }
    
  public Complex devide(double y) {
    Complex z = new Complex(this.getReal()/y,this.getComplex()/y);
    this.setAll(z);
    return z;
  }
    
  public Complex devide(Complex y) {
    Complex z = new Complex(
    (this.getReal()*y.getReal()+this.getComplex()*y.getComplex())/(y.getReal()*y.getReal()+y.getComplex()*y.getComplex()),
    (this.getComplex()*y.getReal()-this.getReal()*y.getComplex())/(y.getReal()*y.getReal()+y.getComplex()*y.getComplex()));
    this.setAll(z);
    return z;
  }
    
  //Absolute  
  public float abs(Complex x) {
    return (float)java.lang.Math.sqrt(x.getReal()*x.getReal()+x.getComplex()*x.getComplex());
  }
    
  public float abs() {
    return (float)java.lang.Math.sqrt(this.getReal()*this.getReal()+this.getComplex()*this.getComplex());
  }
    
  public float abs(float x) {
    return x;
  }
  
  //Exponentials (Squares)
  public Complex sqr(Complex x) {
    Complex z = new Complex(x.getReal()*x.getReal()-x.getComplex()*x.getComplex(), 2*x.getReal()*x.getComplex());
    return z;
  }
                                                                             
  public Complex sqr() {
    Complex z = new Complex(this.getReal()*this.getReal()-this.getComplex()*this.getComplex(), 2*this.getReal()*this.getComplex());
    this.setAll(z);
    return z;
  }
  
  //Potenzen
  
  public void toPolar() {
    this.r = this.abs();
    if (this.complex>=0) {
      this.phi = Math.acos(this.real/this.r);
    } else {
      this.phi = -Math.acos(this.real/this.r);
    }// end of if
  }
  
  public void toAlgebra() {
    this.real = this.r * Math.cos(this.phi);
    this.complex = this.r * Math.sin(this.phi);
  }
  
  public Complex pow(double n) {
    //System.out.println(this.real + " : " + this.complex + " : " + this.r + " : " + this.phi);
    this.toPolar();
    //System.out.println(this.real + " : " + this.complex + " : " + this.r + " : " + this.phi);
    Complex z = new Complex(Math.pow(this.r,n),n*this.phi,true);
    //Complex z = new Complex(Math.pow(this.r,n)*Math.cos(n*this.phi),Math.pow(this.r,n)*Math.sin(n*this.phi));
    this.setAll(z);
    this.toAlgebra();
    //System.out.println(this.real + " : " + this.complex + " : " + this.r + " : " + this.phi);
    return z;
  }
    
    
    // Ende Methoden
} // end of Complex
