package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Manager;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ManagerDTO extends UserDTO{

    private String matricule;
    private String phoneNumber;
    private String department;

    public ManagerDTO(Manager manager){
        super(
          manager.getId(), manager.getFirstName(), manager.getLastName(), manager.getEmail(),
          manager.getRole().toString()
        );
        this.matricule = manager.getMatricule();
        this.phoneNumber = manager.getPhoneNumber();
        this.department = manager.getDepartment().toString();
    }

    public Manager toEntity() {
        return Manager.builder()
            .id(this.getId())
            .firstName(this.getFirstName())
            .lastName(this.getLastName())
            .email(this.getEmail())
            .matricule(this.getMatricule())
            .phoneNumber(this.getPhoneNumber())
            .department(Department.valueOf(this.getDepartment()))
            .build();
    }
}
