package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.model.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    public UserDTO(String firstName, String lastName, String role){
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
    }
}
