package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.restaraunt.*;
import org.kindness.common.model.impl.Restaurant;
import org.kindness.coremodule.domain.restaurant.RestaurantService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.kindness.coremodule.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public final class RestaurantGrpcController extends RestaurantServiceGrpc.RestaurantServiceImplBase {
    private final RestaurantService restaurantService;

    @Override
    public void getRestaurantData(RestaurantDataRequest request, StreamObserver<RestaurantDataResponse> observer) {
        var responseBuilder = RestaurantDataResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurantId());
            responseBuilder.mergeFrom(toResponse(restaurant));
        });
    }

    @Override
    public void getAllRestaurantsData(AllRestaurantsDataRequest request, StreamObserver<AllRestaurantsDataResponse> observer) {
        var responseBuilder = AllRestaurantsDataResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            responseBuilder.addAllData(toResponse(restaurants));
        });
    }

    private static List<RestaurantDataResponse> toResponse(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantGrpcController::toResponse).toList();
    }

    private static RestaurantDataResponse toResponse(Restaurant restaurant) {
        return RestaurantDataResponse.newBuilder()
                .setName(restaurant.getName())
                .setAddress(restaurant.getAddress())
                .setPhone(restaurant.getPhone())
                .setEmail(restaurant.getEmail())
                .setOpenTime(TimestampConverter.fromLocalTime(restaurant.getOpenTime()))
                .setCloseTime(TimestampConverter.fromLocalTime(restaurant.getCloseTime()))
                .build();
    }
}
