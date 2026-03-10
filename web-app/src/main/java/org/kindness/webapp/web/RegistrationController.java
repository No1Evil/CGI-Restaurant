package org.kindness.webapp.web;

import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.user.*;
import org.kindness.common.model.impl.User;
import org.kindness.webapp.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class RegistrationController {
    private final RegistrationServiceGrpc.RegistrationServiceBlockingStub registrationService;
    private final UserServiceGrpc.UserServiceBlockingStub userService;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestParam String email,
                                      @RequestParam String password,
                                      @RequestParam String firstName,
                                      @RequestParam String secondName) {

        RegisterRequest request = RegisterRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .setFirstName(firstName)
                .setSecondName(secondName)
                .build();

        RegisterResponse registerResponse = registrationService.register(request);

        if (!registerResponse.getSuccess()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(registerResponse.getMessage());
        }

        var userResponse = userService.getUser(UserRequest.newBuilder()
                .setUserId(registerResponse.getUserId()).build());

        if (!userResponse.getSuccess()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(userResponse.getMessage());
        }

        var data = userResponse.getData();

        var user = User.builder()
                .userId(data.getUserId())
                .firstName(data.getFirstName())
                .secondName(data.getSecondName())
                .email(data.getEmail())
                .globalRole(data.getRole())
                .build();

        return ResponseEntity.ok(new AuthResponse(user, registerResponse.getToken()));
    }
}
