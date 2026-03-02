package org.kindness.common.dto;

public record TableRequest(
        Long id,
        Long zoneId,
        int capacity,
        float posX,
        float posY
) {}
