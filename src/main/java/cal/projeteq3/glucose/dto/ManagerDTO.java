package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Manager;
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

    public ManagerDTO(Manager manager){
        super(manager.getUserID(), manager.getFirstName(), manager.getLastName(), manager.getEmail());
        this.matricule = manager.getMatricule();
        this.phoneNumber = manager.getPhoneNumber();
    }

}
