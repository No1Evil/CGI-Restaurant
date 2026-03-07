package org.kindness.coremodule.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.user.AuthenticationServiceGrpc;
import org.kindness.common.grpc.user.LoginValidationResponse;
import org.kindness.common.grpc.user.ValidateLoginRequest;
import org.kindness.common.model.impl.User;
import org.kindness.coremodule.domain.security.JwtTokenProvider;
import org.kindness.coremodule.domain.user.AuthenticationService;
import org.kindness.coremodule.util.GrpcServiceUtil;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public final class AuthenticationGrpcController extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void login(ValidateLoginRequest request, StreamObserver<LoginValidationResponse> observer) {
        var responseBuilder = LoginValidationResponse.newBuilder();

        GrpcServiceUtil.handleRequest(responseBuilder, observer, () -> {
            User user = authenticationService.login(request.getEmail(), request.getPassword());
            String token = jwtTokenProvider.createToken(user.getUserId(), user.getEmail());
            responseBuilder.setToken(token);
            responseBuilder.setUserId(user.getUserId());
        });
    }
}
