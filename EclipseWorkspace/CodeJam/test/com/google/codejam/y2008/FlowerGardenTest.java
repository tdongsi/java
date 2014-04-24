package com.google.codejam.y2008;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * http://community.topcoder.com/stat?c=problem_statement&pm=1918&rd=5006
 * 
 * @author tdongsi
 *
 */
public class FlowerGardenTest {

	@Test
	public void test_getOrdering() {
		// 0
		assertArrayEquals( new int[]{ 1,  2,  3,  4,  5 }, 
					FlowerGarden.getOrdering(
					new int[]{5,4,3,2,1}, 
					new int[]{1,1,1,1,1},
					new int[]{365,365,365,365,365}
					)
				);
		
		// 1
		assertArrayEquals( new int[]{ 5,  4,  3,  2,  1 }, 
				FlowerGarden.getOrdering(
				new int[]{5,4,3,2,1}, 
				new int[]{1,5,10,15,20},
				new int[]{4,9,14,19,24}
				)
			);
		
		// 2
		assertArrayEquals( new int[]{ 1,  2,  3,  4,  5 }, 
				FlowerGarden.getOrdering(
				new int[]{5,4,3,2,1}, 
				new int[]{1,5,10,15,20},
				new int[]{5,10,15,20,25}
				)
			);
		
		// 3
		assertArrayEquals( new int[]{ 3,  4,  5,  1,  2 }, 
				FlowerGarden.getOrdering(
				new int[]{5,4,3,2,1}, 
				new int[]{1,5,10,15,20},
				new int[]{5,10,15,20,25}
				)
			);
		
		// 4
		assertArrayEquals( new int[]{ 2,  4,  6,  1,  3,  5 }, 
				FlowerGarden.getOrdering(
				new int[]{1,2,3,4,5,6}, 
				new int[]{1,3,1,3,1,3},
				new int[]{2,4,2,4,2,4}
				)
			);
		
		// 5
		assertArrayEquals( new int[]{ 4,  5,  2,  3 }, 
				FlowerGarden.getOrdering(
				new int[]{3,2,5,4}, 
				new int[]{1,2,11,10},
				new int[]{4,3,12,13}
				)
			);
	}

}
