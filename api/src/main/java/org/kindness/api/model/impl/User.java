package org.kindness.api.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class User {
    private Long userId;
    private String firstName;
    private String secondName;
    private String email;
    private String passwordHash;
    private String role;

    public User(String firstName, String secondName, String email, String passwordHash){
        this(null, firstName, secondName, email, passwordHash, "USER");
    }
}
