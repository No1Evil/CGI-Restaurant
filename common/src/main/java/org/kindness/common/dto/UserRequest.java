package org.kindness.common.dto;

public record UserRequest(
        String firstName,
        String secondName,
        String email
) {}
