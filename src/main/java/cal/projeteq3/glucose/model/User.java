package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public UserDTO toDTO() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(this.getUserID());
        userDTO.setLastName(this.getLastName());
        userDTO.setFirstName(this.getFirstName());
        userDTO.setEmail(this.getEmail());
        return userDTO;
    }
}
