package my.practice.y2016;

import java.util.ArrayList;
import java.util.List;

public class Practice {
	
	public static List<Integer> maximalSubarray(List<Integer> list) {
		if ( list.size() <= 1 ) {
			return new ArrayList<Integer>(list);
		}
		
		int sum = 0;
		List<Integer> cumSum = new ArrayList<Integer>(list.size());
		cumSum.add(0);
		for (int elem : list) {
			sum += elem;
			cumSum.add(sum);
		}
		
		int lowest = Integer.MAX_VALUE;
		int profit = Integer.MIN_VALUE;
		int lowestIdx = -1;
		int profitIdx = -1;
		int lowForProfitIdx = -1;
		for (int i = 0; i < cumSum.size(); i++) {
			int value = cumSum.get(i);
			if (value < lowest) {
				lowest = value;
				lowestIdx = i;
			}
			
			if (value - lowest > profit) {
				profit = value - lowest;
				profitIdx = i;
				lowForProfitIdx = lowestIdx;
			}
		}
		
		return new ArrayList<Integer>(list.subList(lowForProfitIdx, profitIdx));
	}

}
