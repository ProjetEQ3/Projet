package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.ManagerDTO;
import cal.projeteq3.glucose.mapper.ManagerMapper;
import cal.projeteq3.glucose.model.Manager;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final EmployerRepository employerRepository;
    private final StudentRepository studentRepository;
    private final ManagerMapper managerMapper;


    @Autowired
    public ManagerService(ManagerRepository managerRepository, EmployerRepository employerRepository, StudentRepository studentRepository, ManagerMapper managerMapper) {
        this.managerRepository = managerRepository;
        this.employerRepository = employerRepository;
        this.studentRepository = studentRepository;
        this.managerMapper = managerMapper;
    }

    // database operations here

    public ManagerDTO createGestionnaire(Manager manager) {
        return managerMapper.toDTO(managerRepository.save(manager));
    }

    public List<Manager> getAllGestionnaires() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getGestionnaireByID(Long id) {
        return managerRepository.findById(id);
    }

    public Manager updateGestionnaire(Long id, Manager updatedManager) {
        Optional<Manager> existingGestionnaire = managerRepository.findById(id);
        if(existingGestionnaire.isPresent()) {
            Manager manager = existingGestionnaire.get();

            manager.setFirstName(updatedManager.getFirstName());
            manager.setLastName(updatedManager.getLastName());
            manager.setEmail(updatedManager.getEmail());
            manager.setPassword(updatedManager.getPassword());

            return managerRepository.save(manager);
        } else {
            throw new IllegalArgumentException("Manager with ID " + id + " does not exist.");
        }
    }

    public void deleteGestionnaire(Long id) {
        managerRepository.deleteById(id);
    }

}
