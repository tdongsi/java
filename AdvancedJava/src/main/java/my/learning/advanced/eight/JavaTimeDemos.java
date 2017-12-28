package my.learning.advanced.eight;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;
import java.util.Set;

/**
 * Created by tdongsi on 12/27/17.
 */
public class JavaTimeDemos {

    public static void demos() {
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

    }

    // Solve the problem 19 of Project Euler
    // https://projecteuler.net/problem=19
    public static void solve() {
        long count = 0;
        for (int year = 1901; year <= 2000; year++) {
            for (Month month: Month.values()) {
                LocalDate date = LocalDate.of(year, month, 1);
                if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    count++;
                }
            }
        }

        System.out.println("Answer: " + count);
    }

    public static void main(String args[]) {
        solve();
    }

}
