package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.dto.user.UserDTO;
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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "password")
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
