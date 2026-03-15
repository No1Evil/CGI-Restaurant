package org.kindness.common.model.util;

import com.google.protobuf.Timestamp;

import java.time.*;

public class TimestampConverter {

    public static final ZoneId DEFAULT_ZONE = ZoneId.of("Europe/Tallinn");

    // Source - https://stackoverflow.com/a/52651480
    // Posted by Basil Bourque, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-03-03, License - CC BY-SA 4.0
    public static LocalDateTime toLocalDateTime(Timestamp ts){
        Instant instant =  Instant.ofEpochSecond( ts.getSeconds() , ts.getNanos() ) ;
        ZonedDateTime zdt = instant.atZone(DEFAULT_ZONE) ;
        return zdt.toLocalDateTime();
    }

    public static LocalTime toLocalTime(Timestamp protoTs) {
        if (protoTs == null) return null;
        Instant instant = Instant.ofEpochSecond(protoTs.getSeconds(), protoTs.getNanos());
        return instant.atZone(DEFAULT_ZONE).toLocalTime();
    }

    public static Timestamp fromLocalTime(LocalTime lt) {
        if (lt == null) return null;
        Instant instant = lt.atDate(LocalDate.now(DEFAULT_ZONE))
                .atZone(DEFAULT_ZONE)
                .toInstant();

        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Timestamp fromLocalDateAndTime(LocalDate date, LocalTime time) {
        if (date == null || time == null) return null;
        Instant instant = LocalDateTime.of(date, time)
                .atZone(DEFAULT_ZONE)
                .toInstant();

        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Timestamp fromLocalDateTime(LocalDateTime ldt) {
        if (ldt == null) return null;
        Instant instant = ldt.atZone(DEFAULT_ZONE).toInstant();

        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Timestamp fromInstant(Instant instant) {
        if (instant == null) return null;
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    public static Instant toInstant(Timestamp ts) {
        if (ts == null) return null;
        return Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
    }
}
