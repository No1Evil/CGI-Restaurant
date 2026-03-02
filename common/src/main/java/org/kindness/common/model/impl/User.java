package org.kindness.common.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.kindness.common.model.BaseModel;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public final class User extends BaseModel {
    private Long userId;
    private String firstName;
    private String secondName;
    private String email;
    private String passwordHash;
    private String role;
}
