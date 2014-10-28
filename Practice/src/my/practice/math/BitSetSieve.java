package my.practice.math;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class BitSetSieve implements Sieve {
	
	private BitSet sieve;
	private int sieveSize;
	private int bound;
	
	/**
	 * Create a sieve with prime numbers less than or equal to bound.
	 * 
	 * @param bound: upper bound of prime numbers
	 */
	public BitSetSieve(int bound) {
		this.bound = bound;
		this.sieve = new BitSet(bound/2+1);
		this.sieveSize = sieve.size();
		
		buildSieve();
	}

	private void buildSieve() {
		sieve.clear(); // clear all
		
		sieve.set(0); // 1
		
		int num = 3;
		
		while ( num * num <= bound ) {
			
			for ( int j = num*num; j < bound; j += 2*num) {
				// Since j is always odd. num = 2*idx + 1
				sieve.set(j/2);
			}
			
			num += 2;
			while ( num + 2 <= bound && sieve.get(num/2) ) {
				num += 2;
			}
			
		}
		
	}
	
	@Override
	public boolean isPrime(int number) {
		if (number <= 0 || number > bound) {
			throw new IllegalArgumentException("Out of bound input");
		} else if ( number == 2 ) {
			return true;
		} else if ( number % 2 == 0 ) {
			return false;
		}
		
		return !(sieve.get(number/2));
	}
	
	@Override
	public List<Integer> listOfPrimes() {
		List<Integer> list = new ArrayList<Integer>(sieveSize - sieve.cardinality());
		
		for (int i = sieve.nextClearBit(0); 2*i < bound - 1; i = sieve.nextClearBit(i+1)) {
		     list.add(2*i+1);
		 }
		
		return list;
	}

}
