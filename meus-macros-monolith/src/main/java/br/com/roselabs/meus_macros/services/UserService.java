package br.com.roselabs.meus_macros.services;

import br.com.roselabs.meus_macros.data.RegisterRequestRecord;
import br.com.roselabs.meus_macros.data.ShowUserDTO;
import br.com.roselabs.meus_macros.dtos.GoalDTO;
import br.com.roselabs.meus_macros.entities.User;
import br.com.roselabs.meus_macros.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final GoalService goalService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDetails loadUserByUUID(UUID uuid) throws UsernameNotFoundException {
        return this.userRepository.findById(uuid).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ShowUserDTO showUser(UUID uuid) throws UsernameNotFoundException {
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

    public void createDefaultGoal(User user) {
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setCalories(2000);
        goalDTO.setProteinPercentage(25);
        goalDTO.setCarbohydratesPercentage(50);
        goalDTO.setFatPercentage(25);


        this.goalService.createGoal(user.getId(), goalDTO);
    }

}
