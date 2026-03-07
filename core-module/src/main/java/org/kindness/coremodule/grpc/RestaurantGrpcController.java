package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.restaraunt.*;
import org.kindness.common.model.impl.Restaurant;
import org.kindness.coremodule.domain.restaurant.RestaurantService;
import org.kindness.coremodule.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public final class RestaurantGrpcController extends RestaurantServiceGrpc.RestaurantServiceImplBase {
    private RestaurantService restaurantService;

    @Override
    public void getAllRestaurantsData(AllRestaurantsDataRequest request, StreamObserver<AllRestaurantsDataResponse> observer) {
        List<Restaurant> allRestaurants = restaurantService.getAllRestaurants();
        var response = AllRestaurantsDataResponse.newBuilder()
                .addAllData(toResponse(allRestaurants))
                .build();

        observer.onNext(response);
        observer.onCompleted();
    }

    @Override
    public void getRestaurantData(RestaurantDataRequest request, StreamObserver<RestaurantDataResponse> observer) {
        long restaurantId = request.getRestaurantId();
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        if (restaurant == null) {
            observer.onCompleted();
            return;
        }
        RestaurantDataResponse response = toResponse(restaurant);
        observer.onNext(response);
        observer.onCompleted();
    }

    private static List<RestaurantDataResponse> toResponse(List<Restaurant> restaurants){
        return restaurants.stream().map(RestaurantGrpcController::toResponse).toList();
    }

    private static RestaurantDataResponse toResponse(Restaurant restaurant){
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
