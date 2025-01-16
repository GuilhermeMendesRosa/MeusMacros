package br.com.roselabs.auth_meus_macros.controllers;

import br.com.roselabs.auth_meus_macros.data.LoginRequestRecord;
import br.com.roselabs.auth_meus_macros.services.JWTService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JWTService tokenService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestRecord loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        Authentication authentication = authenticationManager.authenticate(authToken);

        String token = tokenService.generateToken(authentication);

        return ResponseEntity.ok(token);
    }
}
