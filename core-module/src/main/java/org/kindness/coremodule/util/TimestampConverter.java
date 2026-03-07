package org.kindness.coremodule.util;

import com.google.protobuf.Timestamp;

import java.time.*;

public class TimestampConverter {
    // Source - https://stackoverflow.com/a/52651480
    // Posted by Basil Bourque, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-03-03, License - CC BY-SA 4.0
    public static LocalDateTime convert(Timestamp ts){
        Instant instant =  Instant.ofEpochSecond( ts.getSeconds() , ts.getNanos() ) ;
        ZoneId z = ZoneId.of("UTC");
        ZonedDateTime zdt = instant.atZone( z ) ;
        return zdt.toLocalDateTime();
    }

    public static Timestamp fromLocalTime(LocalTime localTime) {
        LocalDateTime ldt = localTime.atDate(LocalDate.now());

        return Timestamp.newBuilder()
                .setSeconds(ldt.toEpochSecond(ZoneOffset.UTC))
                .setNanos(ldt.getNano())
                .build();
    }
}
