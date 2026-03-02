package org.kindness.common.dto;

import java.time.LocalDateTime;

public record ReservationRequest(
        Long userId,
        Long tableId,
        LocalDateTime start,
        LocalDateTime end
) {}
