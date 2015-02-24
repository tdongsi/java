package my.practice.math;

import java.util.Scanner;

/**
 * Test code for practice Eclipse debugging.
 * http://agile.csc.ncsu.edu/SEMaterials/tutorials/eclipse-debugger/
 * 
 * @author cdongsi
 *
 */
public class Fibonacci {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int n = 0;
		do {
			System.out.print("Enter a number (n>2): ");
			n = scanner.nextInt();
		} while (n < 2);

		printArray(getFiboArray(n));
	}

	private static int[] getFiboArray(int n) {
		int[] f = new int[n];
		f[0] = 1;
		f[1] = 1;
		for (int i = 2; i < n; i++)
			f[i] = f[i - 1] + f[i - 2];
		return f;
	}

	private static void printArray(int[] fiboArray) {
		for (int i = 0; i < fiboArray.length; i++) {
			System.out.printf("%4d", fiboArray[i]);
		}
	}
}