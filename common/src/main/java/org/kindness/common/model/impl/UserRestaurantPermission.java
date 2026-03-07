package org.kindness.common.model.impl;

import lombok.*;
import org.kindness.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserRestaurantPermission extends BaseModel {
    private Long userId;
    private Long restaurantId;
    private String role;
}
