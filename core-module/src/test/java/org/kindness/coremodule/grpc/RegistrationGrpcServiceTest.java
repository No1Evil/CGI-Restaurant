package org.kindness.coremodule.grpc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import io.grpc.stub.StreamObserver;
import org.kindness.common.grpc.user.RegisterRequest;
import org.kindness.common.grpc.user.RegisterResponse;
import org.kindness.common.model.impl.User;
import org.kindness.coremodule.domain.security.JwtTokenProvider;
import org.kindness.coremodule.domain.user.RegistrationService;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
reference: https://yidongnan.github.io/grpc-spring-boot-starter/en/server/testing.html
 */
@ExtendWith(MockitoExtension.class)
public class RegistrationGrpcServiceTest {

    @Mock
    private RegistrationService registrationService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private RegistrationGrpcController grpcController;

    @Mock
    private StreamObserver<RegisterResponse> responseObserver;

    @Test
    public void testRegisterUser(){
        RegisterRequest request = RegisterRequest.newBuilder()
                .setEmail("bruh@gmail.com")
                .setFirstName("bruho")
                .setSecondName("heyo")
                .setPassword("1234")
                .build();

        User mockUser = User.builder().userId(10L).email("bruh@gmail.com").build();
        when(registrationService.register(any(), any(), any(), any())).thenReturn(mockUser);
        when(jwtTokenProvider.createToken(anyLong(), anyString())).thenReturn("mock-token");

        grpcController.register(request, responseObserver);

        ArgumentCaptor<RegisterResponse> responseCaptor = ArgumentCaptor.forClass(RegisterResponse.class);
        verify(responseObserver).onNext(responseCaptor.capture());
        verify(responseObserver).onCompleted();

        RegisterResponse response = responseCaptor.getValue();
        assertTrue("Awaited success=true but got: " + response.getMessage(), response.getSuccess());
        assertEquals(10L, response.getUserId());
        assertEquals("mock-token", response.getToken());
    }

}
