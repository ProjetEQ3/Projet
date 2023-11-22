package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findFirstByDepartment(Department department);
    List<Manager> findAllByDepartment(Department department);
    Optional<Manager> findFirstByFirstNameAndLastName(String firstName, String lastName);
}
