package org.kindness.common.model.util;

import com.google.protobuf.Timestamp;

import java.time.*;

public class TimestampConverter {
    // Source - https://stackoverflow.com/a/52651480
    // Posted by Basil Bourque, modified by community. See post 'Timeline' for change history
    // Retrieved 2026-03-03, License - CC BY-SA 4.0
    public static LocalDateTime toLocalDateTime(Timestamp ts){
        Instant instant =  Instant.ofEpochSecond( ts.getSeconds() , ts.getNanos() ) ;
        ZoneId z = ZoneId.of("UTC");
        ZonedDateTime zdt = instant.atZone( z ) ;
        return zdt.toLocalDateTime();
    }

    public static LocalTime toLocalTime(com.google.protobuf.Timestamp protoTs) {
        if (protoTs == null) return null;

        Instant instant = Instant.ofEpochSecond(protoTs.getSeconds(), protoTs.getNanos());

        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC);

        return ldt.toLocalTime();
    }

    public static Timestamp fromLocalTime(LocalTime localTime) {
        LocalDateTime ldt = localTime.atDate(LocalDate.now());

        return Timestamp.newBuilder()
                .setSeconds(ldt.toEpochSecond(ZoneOffset.UTC))
                .setNanos(ldt.getNano())
                .build();
    }

    public static Timestamp fromLocalDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;

        long seconds = localDateTime.toEpochSecond(ZoneOffset.UTC);
        int nanos = localDateTime.getNano();

        return Timestamp.newBuilder()
                .setSeconds(seconds)
                .setNanos(nanos)
                .build();
    }

    public static java.sql.Timestamp toSqlTimestamp(com.google.protobuf.Timestamp protoTs) {
        if (protoTs == null) return null;

        Instant instant = Instant.ofEpochSecond(protoTs.getSeconds(), protoTs.getNanos());
        return java.sql.Timestamp.from(instant);
    }
}
