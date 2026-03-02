package org.kindness.common.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Zone {
    private Long zoneId;
    private String name;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean isDeleted;

    public Zone(String name){
        this(null, name, LocalDateTime.now(), LocalDateTime.now(), false);
    }
}
