package com.rainyalley.practice.newFeatureIn8;

import java.time.*;

/**
 * java8 日期时间处理API
 */
public class DateTimeAPI {
    public static void main(String[] args){
        //日期时间都有，有时区
        Clock clock = Clock.systemUTC();
        System.out.println(clock.instant());
        System.out.println(clock.millis());

        //只有日期没有时间
        LocalDate localDate = LocalDate.now();
        LocalDate localDateFromClock = LocalDate.now(clock);
        System.out.println(localDate);
        System.out.println(localDateFromClock);

        //只有时间没有日期
        LocalTime localTime = LocalTime.now();
        LocalTime localTimeFromClock = LocalTime.now(clock);
        System.out.println(localTime);
        System.out.println(localTimeFromClock);

        //日期时间都有，没有时区
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDateTime localDateTimeFromClock = LocalDateTime.now(clock);
        System.out.println(localDateTime);
        System.out.println(localDateTimeFromClock);

        //指定时区，只有ZonedDateTime，没有ZoneDate与ZoneTime
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeFromClock = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeFromZone = ZonedDateTime.now(ZoneId.of( "America/Los_Angeles" ));
        System.out.println(zonedDateTime);
        System.out.println(zonedDateTimeFromClock);
        System.out.println(zonedDateTimeFromZone);

        //计算时间差
        LocalDateTime from = LocalDateTime.of( 2014, Month.APRIL, 16, 0, 0, 0 );
        LocalDateTime to = LocalDateTime.of( 2015, Month.APRIL, 16, 23, 59, 59 );
        Duration duration = Duration.between( from, to );
        System.out.println( "Duration in days: " + duration.toDays() );
        System.out.println( "Duration in hours: " + duration.toHours() );
    }
}
