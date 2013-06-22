/**
 * 
 */
package net.projecteuler.utility;

import static org.junit.Assert.*;

import java.util.Map;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author EpipolarGineer
 *
 */
public class NumberFactorerTest {
	
	private static Logger logger = LoggerFactory.getLogger(NumberFactorer.class);

	@Test
	public void test() {
		NumberFactorer factorer = new NumberFactorer(100);
		
		Map<Integer,Integer> primeFactors = factorer.getPrimeFactors(24);
		
		for( Map.Entry<Integer, Integer> entry: primeFactors.entrySet() )
		{
			logger.info("{} : {}", entry.getKey(), entry.getValue() );
		}
		
		assertTrue(primeFactors.containsKey(2));
		assertTrue(primeFactors.containsKey(3));
		assertEquals(primeFactors.get(2).intValue(), 3);
		assertEquals(primeFactors.get(3).intValue(), 1);
	}

}
