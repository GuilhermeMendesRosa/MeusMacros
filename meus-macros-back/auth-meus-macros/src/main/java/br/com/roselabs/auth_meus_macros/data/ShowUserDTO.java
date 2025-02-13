package br.com.roselabs.auth_meus_macros.data;

import br.com.roselabs.auth_meus_macros.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowUserDTO {

    private String email;
    private String firstName;
    private String lastName;

    public ShowUserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
