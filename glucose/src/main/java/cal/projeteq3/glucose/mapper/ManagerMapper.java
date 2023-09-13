package cal.projeteq3.glucose.mapper;

import cal.projeteq3.glucose.dto.ManagerDTO;
import cal.projeteq3.glucose.model.Manager;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper {

    public ManagerDTO toDTO(Manager manager) {
        ManagerDTO managerDTO = new ManagerDTO();

        managerDTO.setId(manager.getUserID());
        managerDTO.setLastName(manager.getLastName());
        managerDTO.setFirstName(manager.getFirstName());
        managerDTO.setEmail(manager.getEmail());
        managerDTO.setMatricule(manager.getMatricule());
        managerDTO.setPhoneNumber(manager.getPhoneNumber());

        return managerDTO;
    }

    public Manager toEntity(ManagerDTO managerDTO) {
        Manager manager = new Manager();

        manager.setUserID(managerDTO.getId());
        manager.setLastName(managerDTO.getLastName());
        manager.setFirstName(managerDTO.getFirstName());
        manager.setEmail(managerDTO.getEmail());
        manager.setMatricule(managerDTO.getMatricule());
        manager.setPhoneNumber(managerDTO.getPhoneNumber());

        return manager;
    }

}
