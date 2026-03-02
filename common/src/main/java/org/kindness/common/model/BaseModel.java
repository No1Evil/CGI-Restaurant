package org.kindness.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseModel {
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateUpdated;
    protected boolean isDeleted;
}
