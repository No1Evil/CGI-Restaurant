package org.kindness.api.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Table {
    private Long id;
    private Long zoneId;
    private int capacity;
    private float posX;
    private float posY;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean isDeleted;

    public Table(Long zoneId, int capacity, float posX, float posY) {
        this(null, zoneId, capacity, posX, posY, LocalDateTime.now(), LocalDateTime.now(), false);
    }
}
