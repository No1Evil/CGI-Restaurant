package org.kindness.api.dto;

import java.time.LocalDateTime;

public record ReservationRequest(
        Long userId,
        Long tableId,
        LocalDateTime start,
        LocalDateTime end
) {}
