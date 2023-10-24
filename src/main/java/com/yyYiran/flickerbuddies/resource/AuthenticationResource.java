package com.yyYiran.flickerbuddies.resource;

import com.yyYiran.flickerbuddies.model.AuthenticationRequest;
import com.yyYiran.flickerbuddies.model.AuthenticationResponse;
import com.yyYiran.flickerbuddies.model.RegisterRequest;
import com.yyYiran.flickerbuddies.service.AuthenticationService;
import com.yyYiran.flickerbuddies.service.FlickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class AuthenticationResource {

    private final AuthenticationService service;
    private final FlickService flickService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        if (request == null || request.getUsername() == null || request.getPassword() == null){
            return ResponseEntity.badRequest().build();
        }
        if (request.getUsername().length() < 4){
            return ResponseEntity
                    .badRequest()
                    .body(
                            AuthenticationResponse
                                    .builder()
                                    .message("Username too short.")
                                    .build()
                    );
        }

        if (!isPasswordValid(request.getPassword())){
            return ResponseEntity
                    .badRequest()
                    .body(
                            AuthenticationResponse
                                    .builder()
                                    .message("Password insecure")
                                    .build()
                    );
        }

        // User of this username already exists
        if (flickService.findUserByName(request.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    AuthenticationResponse
                            .builder()
                            .message("User already exist. Please change username")
                            .build()
            );
        }
        System.err.println("New user new life");
        return ResponseEntity.ok(service.register(request));

    }

    private boolean isPasswordValid(String password){
        if (password.length() < 8){
            return false;
        }
        String uppercaseRegex = ".*[A-Z].*";
        String lowercaseRegex = ".*[a-z].*";
        String digitRegex = ".*[0-9].*";
        String specialCharRegex = ".*[@#$%^&*+=!()].*";

        return password.matches(uppercaseRegex)
                && password.matches(lowercaseRegex)
                && password.matches(digitRegex)
                && password.matches(specialCharRegex);
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        AuthenticationResponse response = service.authenticate(request);
        if (response == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(AuthenticationResponse
                            .builder()
                            .message("Failed authentication")
                            .build());
        }
        return ResponseEntity.ok(response);

    }
}
