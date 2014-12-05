package my.vip.applets.impedance;

import java.text.DecimalFormat;

/**
 * A simple class to represent complex numbers.
 * 
 * @author tdongsi
 *
 */
class Complex {
	private double re, im;
	private double mag, arg;

	/**
	 * Initialize to zero.
	 */
	public Complex() {
		this.re = 0.0;
		this.im = 0.0;
		this.mag = 0.0;
		this.arg = 0.0;
	}

	/**
	 * Initialize to a real number.
	 * 
	 * @param real
	 */
	public Complex(double real) {
		this.re = real;
		this.im = 0.0;
		toPolar();
	}

	/**
	 * Initialize to a complex number.
	 * 
	 * @param real
	 * @param imaginary
	 */
	public Complex(double real, double imaginary) {
		this.re = real;
		this.im = imaginary;
		toPolar();
	}

	/**
	 * Update the polar form of the complex number, 
	 * based on the current values in Cartesian form.
	 */
	private void toPolar() {
		mag = Math.sqrt(re * re + im * im);
		if (re > 0.0) {
			arg = Math.atan(im / re);
		} else if (re < 0.0) {
			arg = Math.PI + Math.atan(im / re);
		} else {
			arg = Math.PI / 2;
		}
	}

	public void setReal(double real) {
		re = real;
		toPolar();
	}

	public void setImaginary(double imaginary) {
		im = imaginary;
		toPolar();
	}

	public double getReal() {
		return re;
	}

	public double getImaginary() {
		return im;
	}

	public double getMagnitude() {
		return mag;
	}

	public String toString() {
		DecimalFormat twoDigits = new DecimalFormat("0.0");

		return twoDigits.format(re) + " + j" + twoDigits.format(im);
	}

	static public Complex add(Complex num1, Complex num2) {
		double real = num1.getReal() + num2.getReal();
		double imaginary = num1.getImaginary() + num2.getImaginary();
		return new Complex(real, imaginary);
	}

	static public Complex multiply(Complex num, double a) {
		double real = a * num.getReal();
		double imaginary = a * num.getImaginary();
		return new Complex(real, imaginary);
	}
}
