package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.user.*;
import org.kindness.common.model.impl.User;
import org.kindness.common.model.impl.UserRestaurantPermission;
import org.kindness.coremodule.domain.user.UserService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.springframework.grpc.server.service.GrpcService;

import java.util.Collections;
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
            responseBuilder.setData(convert(user));
        });
    }

    @Override
    public void getUserCollection(UserCollectionRequest request, StreamObserver<UserCollectionResponse> observer) {
        var responseBuilder = UserCollectionResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            var users = userService.getAllUsers();
            responseBuilder.addAllData(convertUsers(users));
        });
    }

    @Override
    public void changeGlobalRole(ChangeGlobalRoleRequest request, StreamObserver<UserResponse> observer) {
        var responseBuilder = UserResponse.newBuilder();
        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            User user = userService.changeGlobalRole(request.getUserId(), request.getRole());
            responseBuilder.setData(convert(user));
        });
    }

    @Override
    public void deleteUser(DeleteUserRequest request, StreamObserver<DeleteResponse> observer) {
        userService.deleteUser(request.getUserId());
        var response = DeleteResponse.newBuilder().setSuccess(true).build();
        observer.onNext(response);
        observer.onCompleted();
    }

    @Override
    public void changeRestaurantRole(ChangeRestaurantRoleRequest request, StreamObserver<UserResponse> observer) {
        var responseBuilder = UserResponse.newBuilder();
        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            User user = userService.changeRestaurantRole(request.getUserId(), request.getRestaurantId(), request.getRole());
            responseBuilder.setData(convert(user));
        });
    }

    private static List<UserData> convertUsers(List<User> users){
        if (users == null) return Collections.emptyList();
        return users.stream().map(UserGrpcController::convert).toList();
    }

    private static UserData convert(User user){
        String role = user.getRole() != null ? user.getRole() : "USER";
        return UserData.newBuilder()
                .setUserId(user.getUserId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setRole(role)
                .addAllPermissions(convertPermissions(user.getPermissions()))
                .build();
    }

    private static List<RestaurantPermission> convertPermissions(List<UserRestaurantPermission> permissions){
        if (permissions == null) return Collections.emptyList();
        return permissions.stream().map(UserGrpcController::convert).toList();
    }

    private static RestaurantPermission convert(UserRestaurantPermission ps){
        return RestaurantPermission.newBuilder()
                .setRestaurantId(ps.getRestaurantId())
                .setRole(ps.getRole())
                .build();
    }
}
