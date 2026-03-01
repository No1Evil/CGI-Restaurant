package org.kindness.api.dto;

public record TableRequest(
        Long id,
        Long zoneId,
        int capacity,
        float posX,
        float posY
) {}
