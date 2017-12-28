---
layout: post
title: "Date and Time in Java 7 vs Java 8"
date: 2016-12-27 18:50:15 -0800
comments: true
categories: 
- Java
---

This post discuss how you handle date and time data in Java 8 and compare it with Java 7.

<!--more-->

### Date and Time in Java 8

`java.time` is the new package in Java 8 for handling date and time.
It is heavily based on `joda-time` package which is the de facto standard Java package for handling date & time before Java 8.
This hopefully will put an end to the messy Date & Time classes in `java.util` package.

``` java java.time demos
// Instant
Instant now = Instant.now();
System.out.printf("Instant.now(): %s%n", now);

// Duration
Instant then = now.plus(1, ChronoUnit.DAYS);
Duration elapsed = Duration.between(now, then);
System.out.printf("Duration: %s%n", elapsed);
System.out.printf("Duration in hours: %d hours %n", elapsed.toHours());

then = now.plus(1, ChronoUnit.DAYS)
    .plus(1, ChronoUnit.HALF_DAYS)
    .plus(1, ChronoUnit.HOURS)
    .plus(10, ChronoUnit.MINUTES)
    .plusSeconds(10);
System.out.printf("Then: %s %n", then);

// LocalDate
LocalDate localNow = LocalDate.now();
System.out.printf("LocalDate.now(): %s%n", localNow);

// Factory method
LocalDate firstDayOfSpring = LocalDate.of(2017, Month.MARCH, 20);
System.out.printf("First day of spring %s falls on %s%n", firstDayOfSpring, firstDayOfSpring.getDayOfWeek());

// Time zones
Set<String> allTimeZones = ZoneId.getAvailableZoneIds();
System.out.printf("Total number of time zones: %d%n", allTimeZones.size());
allTimeZones.stream().filter(name -> name.contains("America")).forEach(System.out::println);

// ZonedDateTime
ZonedDateTime missing = ZonedDateTime.of(
    LocalDate.of(2016, Month.MARCH, 13),
    LocalTime.of(2, 30),
    ZoneId.of("America/New_York"));
System.out.println(missing);

// DateTimeFormatter
DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG)
    .withLocale(Locale.FRANCE);
System.out.println(formatter.format(missing));

// From Javadoc
LocalDate customerBirthday = firstDayOfSpring;
LocalDate today = LocalDate.now();
if (customerBirthday.equals(today)) {
LocalDate specialOfferExpiryDate = today.plusWeeks(2).with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
// customer.sendBirthdaySpecialOffer(specialOfferExpiryDate);
}
```

### Compared with Java 7

Handling date time data in Java 8 seems more elegant although not obvious.
As an example, the code for solving the same date time problem ([Project Euler number 19](https://projecteuler.net/problem=19)) in Java 7 and 8 are shown below:

``` java Solving Project Euler 19 in Java 8
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
Calendar calendar = new GregorianCalendar();

// From year 1901 to 2000.
for ( int year=1901; year <= 2000; year++ )
{
    // From month January to December
    for ( int month = 0; month < 12; month++ )
    {
        // Set to the first day of the month
        calendar.set(year, month, 1);
        
        // If the date is Sunday
        if ( calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY )
        {
            count++;
        }
    }
}
```

As you can see, the code is pretty much similar.

Java 7 is awekward.

Traps and Pitfalls
There are a few minor traps in the Calendar class that you should be aware of to avoid unnecessary headaches. I'll cover those in the following sections.

The Month Trap
The MONTH field of the Calendar class does not go from 1 to 12 like they do when we write dates otherwise. Instead the months run from 0 to 11, where 0 is January and 11 is December. This can cause a bit of errors and subsequent debugging if you are not aware of this.

The Day of Week Trap
The day of week runs from 1 to 7 as you might expect, but sunday, not monday is the first day of the week. That means that 1 = sunday, 2 = monday, ..., 7 = saturday. This has also caused me minor annoyances from time to time.

### Reference

* [Java 7 examples](http://tutorials.jenkov.com/java-date-time/java-util-calendar.html)