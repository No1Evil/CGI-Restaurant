package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.user.RegisterRequest;
import org.kindness.common.grpc.user.RegisterResponse;
import org.kindness.common.grpc.user.RegistrationServiceGrpc;
import org.kindness.coremodule.domain.user.RegistrationService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public final class RegistrationGrpcController extends RegistrationServiceGrpc.RegistrationServiceImplBase {
    private final RegistrationService registrationService;

    @Override
    public void register(RegisterRequest request, StreamObserver<RegisterResponse> observer) {
        var responseBuilder = RegisterResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            String email = request.getEmail();
            String password = request.getPassword();
            String firstName = request.getFirstName();
            String secondName = request.getSecondName();
            registrationService.register(email, password, firstName, secondName);
        });
    }
}