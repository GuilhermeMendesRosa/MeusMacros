package br.com.roselabs.meus_macros.data;

import br.com.roselabs.meus_macros.entities.User;
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

    public ShowUserDTO(User userDetails) {
        this.email = userDetails.getEmail();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
    }
}
