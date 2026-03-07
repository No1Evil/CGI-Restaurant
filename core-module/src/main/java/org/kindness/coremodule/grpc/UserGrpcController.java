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
    public void getUser(UserRequest request, StreamObserver<UserResponse> observer) {
        var responseBuilder = UserResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            User user = userService.getUserData(request.getUserId());
            responseBuilder.mergeFrom(convert(user));
        });
    }

    @Override
    public void getUserCollection(UserCollectionRequest request, StreamObserver<UserCollectionResponse> observer) {
        var responseBuilder = UserCollectionResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            var users = userService.getAllUsers();
            responseBuilder.addAllData(convert(users));
        });
    }

    private static List<UserData> convert(List<User> users){
        return users.stream().map(UserGrpcController::convert).toList();
    }

    private static UserData convert(User user){
        return UserData.newBuilder()
                .setUserId(user.getUserId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setSecondName(user.getSecondName())
                .setRole(user.getRole())
                .build();
    }
}
