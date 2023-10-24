package com.yyYiran.flickerbuddies.service;

import com.yyYiran.flickerbuddies.model.*;
import com.yyYiran.flickerbuddies.repo.TokenRepo;
import com.yyYiran.flickerbuddies.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    // User can register when it doesn't exist in DB and has valid inputs
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        // TODO: save this user with its token in DB
        userRepo.save(user);
        String token = jwtService.generateToken(user);
        saveUserToken(user, token);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }

    private void saveUserToken(User user, String token) {
        Token savedToken = Token.builder()
                .user(user)
                .expired(false)
                .revoked(false)
                .token(token)
                .build();
        tokenRepo.save(savedToken);
    }

    private boolean updateUserToken(User user, String token){
        Token tokenToUpdate = tokenRepo.findTokenByUser(user.getId());
        if (tokenToUpdate == null){
            return false;
        }
        tokenToUpdate.setToken(token);
        tokenRepo.save(tokenToUpdate);
        return true;
    }



    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        TODO: allow authentication if this user's token have not expired
//        System.err.println("request.getUsername(): " + request.getUsername());
//        System.err.println(SecurityContextHolder.getContext().getAuthentication());
//        System.err.println("vs\n" + new UsernamePasswordAuthenticationToken(request.getUsername(),
//                request.getPassword()
//        ));
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(request.getUsername(),
//                        request.getPassword()
//                ).
//        );

        User user = userRepo.findUserByName(request.getUsername());

//        System.err.println("user" + user);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword()))
            return null;

        String token = jwtService.generateToken(user);
        // var refreshToken = jwtService.generateRefreshToken(user);
        //    revokeAllUserTokens(user);
        if (!updateUserToken(user, token)){
            System.err.println("Can't find this user's token");
            return null;
        }
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }
}
