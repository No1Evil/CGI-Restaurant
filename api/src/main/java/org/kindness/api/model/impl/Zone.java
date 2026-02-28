package org.kindness.api.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Zone {
    private Long zoneId;
    private String name;
    private int capacity;

    public Zone(String name, int capacity){
        this(null, name, capacity);
    }
}
