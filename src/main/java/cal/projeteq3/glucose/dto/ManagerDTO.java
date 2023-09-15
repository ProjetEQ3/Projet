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

    public ManagerDTO(Manager manager){
        super(manager.getId(), manager.getFirstName(), manager.getLastName(), manager.getEmail(), manager.getRole().toString());
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
