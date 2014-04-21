package com.google.codejam.y2008;

import static org.junit.Assert.*;

import org.junit.Test;

public class BadNeighborsTest {

	@Test
	public void testMaxDonations() {
		assertEquals(19, BadNeighbors.maxDonations(new int[]{ 10, 3, 2, 5, 7, 8 }));
		
		assertEquals(15, BadNeighbors.maxDonations(new int[]{ 11, 15 }));
		
		assertEquals(21, BadNeighbors.maxDonations(new int[]{ 7, 7, 7, 7, 7, 7, 7 }));
		
		assertEquals(16, BadNeighbors.maxDonations(new int[]{ 1, 2, 3, 4, 5, 1, 2, 3, 4, 5 }));
		
		assertEquals(2926, BadNeighbors.maxDonations(new int[]
				{ 94, 40, 49, 65, 21, 21, 106, 80, 92, 81, 679, 4, 61,  
				  6, 237, 12, 72, 74, 29, 95, 265, 35, 47, 1, 61, 397,
				  52, 72, 37, 51, 1, 81, 45, 435, 7, 36, 57, 86, 81, 72 }));
	}

}
