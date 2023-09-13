package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagerDTO extends UserDTO{

    private String matricule;
    private String phoneNumber;

    public ManagerDTO(Long id, String firstName, String lastName, String email, String matricule, String phoneNumber) {
        super(id, firstName, lastName, email);
        this.matricule = matricule;
        this.phoneNumber = phoneNumber;
    }

}
