package org.kindness.api.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Reservation {
    private Long reservationId;
    private Long tableId;
    private Long userId;
    private LocalDateTime reservationTime;
    private LocalDateTime reservationEnd;
}
