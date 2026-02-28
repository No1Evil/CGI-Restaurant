package org.kindness.api.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    public Author(String firstName, String lastName, LocalDate birthDate){
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }
}
