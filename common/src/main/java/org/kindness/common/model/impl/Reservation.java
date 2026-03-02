package org.kindness.common.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Reservation {
    private Long id;
    private Long userId;
    private Long tableId;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean isDeleted;

    public Reservation(Long userId, Long tableId, LocalDateTime start, LocalDateTime end) {
        this.userId = userId;
        this.tableId = tableId;
        this.reservationStart = start;
        this.reservationEnd = end;
        this.dateCreated = LocalDateTime.now();
        this.isDeleted = false;
    }
}
