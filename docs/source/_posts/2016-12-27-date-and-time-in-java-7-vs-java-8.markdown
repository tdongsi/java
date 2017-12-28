---
layout: post
title: "Date and Time in Java 7 vs Java 8"
date: 2016-12-27 18:50:15 -0800
comments: true
categories: 
- Java
---

``` java Solving Project Euler in Java 8
long count = 0;
for (int year = 1901; year <= 2000; year++) {
    for (Month month: Month.values()) {
        LocalDate date = LocalDate.of(year, month, 1);
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            count++;
        }
    }
}
```

``` java Solving Proiect Euler 19 in Java 7
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
```

Java 7 is awekward.

Traps and Pitfalls
There are a few minor traps in the Calendar class that you should be aware of to avoid unnecessary headaches. I'll cover those in the following sections.

The Month Trap
The MONTH field of the Calendar class does not go from 1 to 12 like they do when we write dates otherwise. Instead the months run from 0 to 11, where 0 is January and 11 is December. This can cause a bit of errors and subsequent debugging if you are not aware of this.

The Day of Week Trap
The day of week runs from 1 to 7 as you might expect, but sunday, not monday is the first day of the week. That means that 1 = sunday, 2 = monday, ..., 7 = saturday. This has also caused me minor annoyances from time to time.

### Reference

* [Java 7 examples](http://tutorials.jenkov.com/java-date-time/java-util-calendar.html)