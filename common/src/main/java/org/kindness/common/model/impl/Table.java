package org.kindness.common.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.kindness.common.model.BaseModel;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public final class Table extends BaseModel {
    private Long id;
    private Long zoneId;
    private Long restaurantId;
    private int capacity;
    private float posX;
    private float posY;
}
