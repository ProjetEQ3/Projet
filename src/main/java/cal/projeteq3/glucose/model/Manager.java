package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.dto.ManagerDTO;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends User {

    private String matricule;
    private String phoneNumber;

    public Manager(String firstName, String lastName, String email, String password, String matricule, String phoneNumber) {
        super(firstName, lastName, email, password);
        this.matricule = matricule;
        this.phoneNumber = phoneNumber;
    }
}
