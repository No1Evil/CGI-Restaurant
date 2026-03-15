package org.kindness.webapp.web;

import lombok.RequiredArgsConstructor;
import org.kindness.common.grpc.user.*;
import org.kindness.common.model.impl.User;
import org.kindness.webapp.model.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class AuthenticationController {
    private final AuthenticationServiceGrpc.AuthenticationServiceBlockingStub authService;
    private final UserServiceGrpc.UserServiceBlockingStub userService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        ValidateLoginRequest request = ValidateLoginRequest.newBuilder()
                .setEmail(email)
                .setPassword(password)
                .build();

        LoginValidationResponse response = authService.login(request);

        if (!response.getSuccess()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(response.getMessage());
        }

        long userId = response.getUserId();

        UserResponse dataResponse = userService.getUser(UserRequest.newBuilder().setUserId(userId).build());
        if (!dataResponse.getSuccess()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(dataResponse.getMessage());
        }

        var data = dataResponse.getData();

        User user = User.builder()
                .userId(data.getUserId())
                .firstName(data.getFirstName())
                .lastName(data.getLastName())
                .email(data.getEmail())
                .role(data.getRole()).build();

        return ResponseEntity.ok(new AuthResponse(user, response.getToken()));
    }

    @GetMapping("/auth/me")
    public ResponseEntity<?> getCurrentUser(Principal principal) {
        var userId = principal.getName();
        var data = userService.getUser(UserRequest.newBuilder().setUserId(Long.parseLong(userId)).build());
        return ResponseEntity.ok(data);
    }
}
