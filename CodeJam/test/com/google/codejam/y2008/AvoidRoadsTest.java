package com.google.codejam.y2008;

import static org.junit.Assert.*;

import org.junit.Test;

public class AvoidRoadsTest {

	@Test
	public void test_numWays() {
		assertEquals(252, AvoidRoads.numWays(6,6,new String[]{"0 0 0 1","5 6 6 6"}));
		
		assertEquals(2, AvoidRoads.numWays(1,1,new String[]{}));
		
		assertEquals(6406484391866534976L, AvoidRoads.numWays(35,31,new String[]{}));
		
		assertEquals(0, AvoidRoads.numWays(2,2,new String[]{"0 0 1 0", "1 2 2 2", "1 1 2 1"}));
	}

}
