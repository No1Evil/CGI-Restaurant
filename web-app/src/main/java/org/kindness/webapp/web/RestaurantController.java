package org.kindness.webapp.web;

import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.restaraunt.*;
import org.kindness.common.model.impl.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.kindness.common.model.util.TimestampConverter;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class RestaurantController {
    private final RestaurantServiceGrpc.RestaurantServiceBlockingStub restaurantService;
    private final ObjectMapper objectMapper;

    @PostMapping("/get")
    public ResponseEntity<?> getRestaurant(@RequestParam long id){
        RestaurantRequest request = RestaurantRequest.newBuilder()
                .setRestaurantId(id)
                .build();

        RestaurantResponse response = restaurantService.getRestaurant(request);
        if (response.getSuccess()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response.getMessage());
        }

        RestaurantData data = response.getData();

        Restaurant restaurant = mapToRestaurant(data);

        return ResponseEntity.ok(restaurant);
    }

    @PostMapping("/all")
    public ResponseEntity<?> getAllRestaurants() {
        RestaurantCollectionRequest request = RestaurantCollectionRequest.newBuilder().build();

        RestaurantCollectionResponse response = restaurantService.getRestaurantCollection(request);

        if (!response.getSuccess()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response.getMessage());
        }

        List<Restaurant> restaurants = response.getDataList().stream()
                .map(this::mapToRestaurant)
                .toList();

        return ResponseEntity.ok(restaurants);
    }

    private Restaurant mapToRestaurant(RestaurantData data) {
        return Restaurant.builder()
                .id(data.getRestaurantId())
                .email(data.getEmail())
                .address(data.getAddress())
                .name(data.getName())
                .openTime(TimestampConverter.toLocalTime(data.getOpenTime()))
                .closeTime(TimestampConverter.toLocalTime(data.getCloseTime()))
                .phone(data.getPhone())
                .build();
    }
}
