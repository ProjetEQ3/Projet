package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.dto.ManagerDTO;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Manager extends User {

    private String matricule;
    private String phoneNumber;

    public ManagerDTO toDTO() {
        return new ManagerDTO(
                this.getUserID(),
                this.getFirstName(),
                this.getLastName(),
                this.getEmail(),
                this.getMatricule(),
                this.getPhoneNumber()
        );
    }

}
