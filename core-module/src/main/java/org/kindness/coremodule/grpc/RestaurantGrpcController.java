package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.restaraunt.*;
import org.kindness.common.model.impl.Restaurant;
import org.kindness.coremodule.domain.restaurant.RestaurantService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.kindness.common.model.util.TimestampConverter;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public final class RestaurantGrpcController extends RestaurantServiceGrpc.RestaurantServiceImplBase {
    private final RestaurantService restaurantService;

    @Override
    public void getRestaurant(RestaurantRequest request, StreamObserver<RestaurantResponse> observer) {
        var responseBuilder = RestaurantResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            Restaurant restaurant = restaurantService.getRestaurant(request.getRestaurantId());
            responseBuilder.setData(convert(restaurant));
        });
    }

    @Override
    public void getRestaurantCollection(RestaurantCollectionRequest request, StreamObserver<RestaurantCollectionResponse> observer) {
        var responseBuilder = RestaurantCollectionResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            responseBuilder.addAllData(convert(restaurants));
        });
    }

    private static List<RestaurantData> convert(List<Restaurant> restaurants){
        return restaurants.stream().map(RestaurantGrpcController::convert).toList();
    }

    private static RestaurantData convert(Restaurant rs){
        return RestaurantData.newBuilder()
                .setRestaurantId(rs.getId())
                .setName(rs.getName())
                .setAddress(rs.getAddress())
                .setEmail(rs.getEmail())
                .setPhone(rs.getPhone())
                .setOpenTime(TimestampConverter.fromLocalTime(rs.getOpenTime()))
                .setCloseTime(TimestampConverter.fromLocalTime(rs.getCloseTime()))
                .build();
    }
}
