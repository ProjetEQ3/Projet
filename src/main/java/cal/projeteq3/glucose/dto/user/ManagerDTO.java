package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.model.user.Manager;
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

    public ManagerDTO(
      Long id, String firstName, String lastName, String email, String role,  String matricule, String phoneNumber
    ){
        super(id, firstName, lastName, email, role);
        this.matricule = matricule;
        this.phoneNumber = phoneNumber;
    }

    public ManagerDTO(Manager manager){
        super(
          manager.getId(), manager.getFirstName(), manager.getLastName(), manager.getEmail(),
          manager.getCredentials().getRole().toString()
        );
        this.matricule = manager.getMatricule();
        this.phoneNumber = manager.getPhoneNumber();
    }

    public Manager toEntity() {
        return Manager.builder()
            .id(this.getId())
            .firstName(this.getFirstName())
            .lastName(this.getLastName())
            .email(this.getEmail())
            .matricule(this.getMatricule())
            .phoneNumber(this.getPhoneNumber())
            .build();
    }
}
