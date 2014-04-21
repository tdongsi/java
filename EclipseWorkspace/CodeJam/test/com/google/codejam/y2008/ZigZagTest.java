package com.google.codejam.y2008;

import static org.junit.Assert.*;

import org.junit.Test;

public class ZigZagTest {

	@Test
	public void testLongestZigZag() {
		assertEquals(6, ZigZag.longestZigZag(new int[]{ 1, 7, 4, 9, 2, 5 }));
		
		assertEquals(1, ZigZag.longestZigZag(new int[]{44}));
		
		assertEquals(2, ZigZag.longestZigZag(new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9 }));
		
		assertEquals(8, ZigZag.longestZigZag(new int[]{ 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 }));
		
		assertEquals(36, ZigZag.longestZigZag(new int[]
				{ 374, 40, 854, 203, 203, 156, 362, 279, 812, 955, 
				600, 947, 978, 46, 100, 953, 670, 862, 568, 188, 
				67, 669, 810, 704, 52, 861, 49, 640, 370, 908, 
				477, 245, 413, 109, 659, 401, 483, 308, 609, 120, 
				249, 22, 176, 279, 23, 22, 617, 462, 459, 244 }));
	}

}
