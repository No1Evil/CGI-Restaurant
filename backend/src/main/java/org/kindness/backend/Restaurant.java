package org.kindness.backend;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public class Restaurant {
    @Id
    private Long id;
    private String name;
}
