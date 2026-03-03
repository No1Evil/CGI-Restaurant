package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.user.*;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public final class UserGrpcController extends UserServiceGrpc.UserServiceImplBase {
    @Override
    public void login(ValidateLoginRequest request, StreamObserver<LoginValidationResponse> responseObserver) {
        super.login(request, responseObserver);
    }

    @Override
    public void getUserData(UserDataRequest request, StreamObserver<UserDataResponse> responseObserver) {
        super.getUserData(request, responseObserver);
    }

    @Override
    public void getAllUsers(AllUsersDataRequest request, StreamObserver<AllUsersDataResponse> responseObserver) {
        super.getAllUsers(request, responseObserver);
    }
}
