package org.kindness.common.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.kindness.common.model.BaseModel;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public final class Reservation extends BaseModel {
    private Long id;
    private Long userId;
    private Long tableId;

    private Instant startsAt;
    private Instant endsAt;
}
