package org.kindness.common.model.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;

    private LocalTime openTime;
    private LocalTime closeTime;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean isDeleted;

    public Restaurant(String name, String address){
        this(name, address, null, null, null, null);
    }

    public Restaurant(String name, String address, String phone, String email, LocalTime openTime, LocalTime closeTime){
        this(null, name, address, phone,
                email, openTime, closeTime,
                null, null, false);
    }
}
