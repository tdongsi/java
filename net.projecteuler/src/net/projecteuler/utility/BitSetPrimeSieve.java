/**
 * 
 */
package net.projecteuler.utility;

/**
 * Implements PrimeSieve interface using BitSet as underlying data structure.
 * Performance is better or similar to boolean array, which is not checked in.
 * Populating the prime sieve using Eratosthenes's sive algorithm.
 * 
 * @author tdongsi
 *
 */
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BitSetPrimeSieve implements PrimeSieve
{
	private static Logger logger = LoggerFactory.getLogger(BitSetPrimeSieve.class);
	
	// the sieve for odd numbers
	// Using boolean array gives similar performance with no obvious benefits.
    BitSet isMultiple; 
    private int squareRootOfBound;
	private int bound;
	
	/**
	 * List of primes
	 */
	protected List<Integer> list;

    /**
     * Construct the sieve of primes, using BitSet as underlying data structure
     * 
     * @param bound: all primes are less than or equal to this number
     */
    public BitSetPrimeSieve(int bound)
    {
    	this.bound = bound;
		squareRootOfBound = (int) Math.sqrt(bound);
        isMultiple = new BitSet(bound / 2);

        final int indexToStop = squareRootOfBound / 2;
        for (int i = 1; i <= indexToStop; i++)
        {
            if (!isMultiple.get(i))
            {
                final int startIndex = 2 * i * (i + 1);
                // Start the loop from p^2 = 4i^2 + 4i + 1 has the index 2i(i + 1)
                // Proceed with the number step 2p, has the index step of p = 2*i + 1
                for (int j = startIndex; j < isMultiple.size(); j += 2 * i + 1)
                {
                    isMultiple.set(j);
                }
            }
        }
    }
    
    public BitSetPrimeSieve()
    {
    	// If the bound is not specified, use 100
    	this(100);
    }

    public List<Integer> getList()
    {
        if (list != null)
            return list;

        List<Integer> l = new ArrayList<Integer>();
        l.add(2);
        
        // iterate over all clear bits in the bit set
        for (int i = isMultiple.nextClearBit(1); i >= 0 && i < bound / 2; i = isMultiple.nextClearBit(i+1) ) 
        {
        	// find the prime from the index i
        	// prime = 2*i + 1;
        	l.add( 2*i + 1 );
        }

        this.list = l;

        return list;
    }
    
    public boolean isPrime(int n)
    {
        if (n <= 1)
            return false;
        if (n == 2)
            return true;
        else if (n % 2 == 0)
            return false;
        else if (n > bound)
            throw new IllegalArgumentException( "Input " + n + " is larger than the bound : " + bound);
        else
            return !isMultiple.get((n - 1) / 2);
    }
    
    /**
	 * @param number the number to be factored
	 * @return a table with prime numbers as keys and theirs powers as their corresponding values
	 */
	public Map<Integer,Integer> getPrimeFactors( int number )
	{
		Map<Integer,Integer> primeFactors = new TreeMap<Integer,Integer>();
		// Quick return if input number is prime.
		if ( isPrime(number) )
		{
			primeFactors.put(number, 1);
			return primeFactors;
		}
		
		List<Integer> primes = this.getList();
		int maxPrime = primes.get(primes.size()-1);
		logger.trace( "maxPrime: {} number: {}", maxPrime, number );
		
		// NOTE: possible number overflow by maxPrime*maxPrime
		if ( (long) number > (long)maxPrime*maxPrime )
		{
			throw new IllegalArgumentException("Unable to factor this number. The number is too large.");
		} else if ( number < 1 )
		{
			throw new IllegalArgumentException("Number must be greater than 1.");
		} else if ( number == 1)
		{
			// return empty map
			return primeFactors;
		}
		
		long currNum = number;
		
		while (currNum > 1 )
		{
			for ( int prime: primes )
			{
				int count = 0;
				while ( currNum % prime == 0 )
				{
					currNum /= prime;
					count++;
				}
				
				if ( count > 0 )
				{
					primeFactors.put(prime, count);
				}
				
			}
		}
		
		return primeFactors;
	}
	
	
	/**
	 * Compute the integer from the given factorization.
	 * The factorization that represent the integer is a table 
	 * that map the primes and their corresponding exponents 
	 * 
	 * @param factorization the map that represents the prime factorization.
	 * @return the integer
	 */
	public int valueFromPrimeFactors(Map<Integer,Integer> factorization)
	{
		int product = 1;
		for (Map.Entry<Integer, Integer> entry : factorization.entrySet() )
		{
			int prime = entry.getKey();
			int exponent = entry.getValue();
			for (int i = 0; i < exponent; i++) {
				product *= prime;
			}
		}
		return product;
	}

	/**
	 * Return the cycle length of repeating decimals of 1/n where n is the given prime number.
	 * @param prime
	 * @return
	 */
	public int primeReciprocalLength(int prime) {
		if ( prime > bound )
		{
			throw new IllegalArgumentException("The number is too large");
		}
		if ( !isPrime(prime) )
		{
			throw new IllegalArgumentException("Number must be a prime.");
		}
		
		if ( prime == 2 || prime == 5 )
		{
			return 0;
		}
		
		// From Little Fermat's Theorem, the cycle length must be a divisor of this p-1
		int number = prime - 1;
		// For all divisors, find the lowest divisor n that is 10^n - 1 are not divisible by p
		int minDivisor = number;
				
		// Find all the proper divisors of this num
		for ( int j=1; j*j <= number; j++)
		{
			if ( number % j == 0 )
			{
				// remainder = (10^divisor - 1) % prime;
				int remainder = NumberUtility.remainderDivideNineSeries(j, prime);
				if ( remainder == 0 )
				{
					// As j is increasing, the first found j is the lowest divisor
					minDivisor = j;
					break;
				}

				// Find the correspondent divisor
				int div = number/j;
				if ( div != j && div != number )
				{
					// remainder = (10^divisor - 1) % prime;
					remainder = NumberUtility.remainderDivideNineSeries(div, prime);
					if ( remainder == 0 )
					{
						if ( div < minDivisor )
						{
							minDivisor = div;
						}
					}
				}
			}
		}
		
		return minDivisor;
	}
	
}
