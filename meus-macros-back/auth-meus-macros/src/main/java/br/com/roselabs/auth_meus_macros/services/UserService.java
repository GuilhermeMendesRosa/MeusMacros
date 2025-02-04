package br.com.roselabs.auth_meus_macros.services;

import br.com.roselabs.auth_meus_macros.data.RegisterRequestRecord;
import br.com.roselabs.auth_meus_macros.data.ShowUserDTO;
import br.com.roselabs.auth_meus_macros.entities.User;
import br.com.roselabs.auth_meus_macros.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;  // Lombok vai criar o construtor automaticamente

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

    public RegisterRequestRecord registerUser(RegisterRequestRecord registerRequest) {
        String encodedPassword = passwordEncoder.encode(registerRequest.password());

        User user = new User();
        user.setEmail(registerRequest.email());
        user.setPassword(encodedPassword);
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());

        userRepository.save(user);

        return registerRequest;
    }

}
