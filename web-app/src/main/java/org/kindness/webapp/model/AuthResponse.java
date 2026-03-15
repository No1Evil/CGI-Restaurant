package org.kindness.webapp.model;

import org.kindness.common.model.impl.User;

public record AuthResponse(User user, String token) {
}
