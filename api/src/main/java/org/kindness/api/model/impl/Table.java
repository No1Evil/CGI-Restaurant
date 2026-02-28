package org.kindness.api.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Table {
    private Long tableId;
    private Long zoneId;
    private int capacity;
    private float pos_x;
    private float pos_y;

    public Table(Long zoneId, int capacity, float pos_x, float pos_y){
        this(null, zoneId, capacity, pos_x, pos_y);
    }
}
