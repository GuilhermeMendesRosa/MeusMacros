package br.com.roselabs.auth_meus_macros.services;

import br.com.roselabs.auth_meus_macros.clients.MacrosCalculatorClient;
import br.com.roselabs.auth_meus_macros.data.GoalDTO;
import br.com.roselabs.auth_meus_macros.data.RegisterRequestRecord;
import br.com.roselabs.auth_meus_macros.data.ShowUserDTO;
import br.com.roselabs.auth_meus_macros.entities.User;
import br.com.roselabs.auth_meus_macros.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MacrosCalculatorClient macrosCalculatorClient;
    private final JWTService jwtService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetails loadUserByUUID(String uuid) throws UsernameNotFoundException {
        return this.userRepository.findById(UUID.fromString(uuid)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ShowUserDTO showUser(String uuid) throws UsernameNotFoundException {
        User user = (User) this.loadUserByUUID(uuid);
        return new ShowUserDTO(user);
    }

    public User registerUser(RegisterRequestRecord registerRequest) {
        String encodedPassword = passwordEncoder.encode(registerRequest.password());

        User user = new User();
        user.setEmail(registerRequest.email());
        user.setPassword(encodedPassword);
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());

        userRepository.save(user);

        return user;
    }

    public void createDefaultGoal(User user, String token) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setCalories(2000);
        goalDTO.setProteinPercentage(25);
        goalDTO.setCarbohydratesPercentage(50);
        goalDTO.setFatPercentage(25);

        GoalDTO generate = this.macrosCalculatorClient.generate(goalDTO);
    }

}
