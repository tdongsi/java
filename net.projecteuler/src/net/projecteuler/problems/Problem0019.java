/**
 * 
 */
package net.projecteuler.problems;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?
 * 
 * @author tdongsi
 *
 */
public class Problem0019 {

	public static void main(String[] args) {
		
		Calendar calendar = new GregorianCalendar();
		
		// Sanity check
		// The number of MONTH is 0-based. March is 2.
		calendar.set(2013, 2, 02);
		System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("Sunday: " + Calendar.SUNDAY );
		
		int count = 0;
		// From year 1901 to 2000.
		for ( int i=1901; i <= 2000; i++ )
		{
			// From month January to December
			for ( int j = 0; j < 12; j++ )
			{
				// Set to the first day of the month
				calendar.set(i, j, 1);
				
				// If the date is Sunday
				if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY )
				{
					count++;
				}
			}
		}
		
		System.out.println("The number of Sundays is " + count );

	}

}
