package br.com.roselabs.auth_meus_macros.controllers;

import br.com.roselabs.auth_meus_macros.data.AuthenticationTokens;
import br.com.roselabs.auth_meus_macros.data.LoginRequestRecord;
import br.com.roselabs.auth_meus_macros.data.RegisterRequestRecord;
import br.com.roselabs.auth_meus_macros.data.ShowUserDTO;
import br.com.roselabs.auth_meus_macros.entities.User;
import br.com.roselabs.auth_meus_macros.services.JWTService;
import br.com.roselabs.auth_meus_macros.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokens> login(@RequestBody LoginRequestRecord loginRequest) {
        AuthenticationTokens tokens = getAuthenticationTokens(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(tokens);
    }

    private AuthenticationTokens getAuthenticationTokens(String email, String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManager.authenticate(authToken);
        String token = jwtService.generateToken(authentication);

        AuthenticationTokens tokens = new AuthenticationTokens(token);
        return tokens;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequestRecord registerRequest) {
        try {
            User user = this.userService.registerUser(registerRequest);
            AuthenticationTokens authenticationTokens = this.getAuthenticationTokens(registerRequest.email(), registerRequest.password());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, authenticationTokens.getToken(), null);
            SecurityContextHolder.getContext().setAuthentication(authToken);

//            this.userService.createDefaultGoal(user, authenticationTokens.getToken());

            rabbitTemplate.convertAndSend("email.ex", "", new ShowUserDTO(user));

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<ShowUserDTO> me(@RequestHeader("Authorization") String token) {
        String userUuid = jwtService.getSubjectFromToken(token.replace("Bearer ", ""));

        ShowUserDTO showUserDTO = this.userService.showUser(userUuid);

        return ResponseEntity.ok(showUserDTO);
    }

}
