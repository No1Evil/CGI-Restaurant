package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.user.*;
import org.kindness.common.model.impl.User;
import org.kindness.coremodule.domain.user.UserService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
@RequiredArgsConstructor
public final class UserGrpcController extends UserServiceGrpc.UserServiceImplBase {
    private final UserService userService;

    @Override
    public void getUserData(UserDataRequest request, StreamObserver<UserDataResponse> observer) {
        var responseBuilder = UserDataResponse.newBuilder();
        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            User user = userService.getUserData(request.getUserId());
            responseBuilder.mergeFrom(toResponse(user));
        });
    }

    @Override
    public void getAllUsers(AllUsersDataRequest request, StreamObserver<AllUsersDataResponse> observer) {
        var responseBuilder = AllUsersDataResponse.newBuilder();
        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            var users = userService.getAllUsers();
            responseBuilder.addAllData(toResponse(users));
        });
    }

    private static List<UserDataResponse> toResponse(List<User> users){
        return users.stream().map(UserGrpcController::toResponse).toList();
    }

    private static UserDataResponse toResponse(User user){
        return UserDataResponse.newBuilder()
                .setUserId(user.getUserId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setSecondName(user.getSecondName())
                .setRole(user.getRole())
                .build();
    }
}
