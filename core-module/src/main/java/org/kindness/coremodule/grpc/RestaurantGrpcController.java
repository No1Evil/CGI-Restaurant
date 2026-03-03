package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.restaraunt.*;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class RestaurantGrpcController extends RestaurantServiceGrpc.RestaurantServiceImplBase {
    @Override
    public void getAllRestaurantsData(AllRestaurantsDataRequest request, StreamObserver<AllRestaurantsDataResponse> responseObserver) {
        super.getAllRestaurantsData(request, responseObserver);
    }

    @Override
    public void getRestaurantData(RestaurantDataRequest request, StreamObserver<RestaurantDataResponse> responseObserver) {
        super.getRestaurantData(request, responseObserver);
    }
}
