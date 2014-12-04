package my.vip.applets.impedance;

import java.text.DecimalFormat;

class Complex {
	private double re, im;
	private double mag, arg;

	public Complex() {
		re = 0.0;
		im = 0.0;
		mag = 0.0;
		arg = 0.0;
	}

	public Complex(double real) {
		re = real;
		im = 0.0;
		toPolar();
	}

	public Complex(double real, double imaginary) {
		re = real;
		im = imaginary;
		toPolar();
	}

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

	public void setRe(double real) {
		re = real;
		toPolar();
	}

	public void setIm(double imaginary) {
		im = imaginary;
		toPolar();
	}

	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	public double getMag() {
		return mag;
	}

	public double getArg() {
		return arg * 180 / Math.PI;
	}

	public String toString() {
		DecimalFormat twoDigits = new DecimalFormat("0.0");

		return " " + twoDigits.format(re) + " + j" + twoDigits.format(im);
	}

	static public Complex add(Complex num1, Complex num2) {
		double real = num1.getRe() + num2.getRe();
		double imaginary = num1.getIm() + num2.getIm();
		return new Complex(real, imaginary);
	}

	static public Complex multiply(Complex num, double a) {
		double real = a * num.getRe();
		double imaginary = a * num.getIm();
		return new Complex(real, imaginary);
	}
}
