package org.kindness.api.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime dateCreated;
    private String role;
    private boolean isDeleted;

    public User(String firstName, String secondName, String email, String passwordHash){
        this(null, firstName, secondName, email, passwordHash,  LocalDateTime.now(),"USER", false);
    }
}
