package com.aliakseikul.storenew.service.impl.auth;

import com.aliakseikul.storenew.config.service.JwtService;
import com.aliakseikul.storenew.dto.auth.AuthenticationRequest;
import com.aliakseikul.storenew.dto.auth.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserNickname(),
                        request.getUserPassword()
                )
        );
        UserDetails user = userDetailsService.loadUserByUsername(request.getUserNickname());
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
