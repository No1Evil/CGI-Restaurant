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

    public User(String firstName, String secondName, String email){
        this(null, firstName, secondName, email);
    }
}
