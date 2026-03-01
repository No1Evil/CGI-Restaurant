package org.kindness.api.dto;

public record UserRequest(
        String firstName,
        String secondName,
        String email
) {}
