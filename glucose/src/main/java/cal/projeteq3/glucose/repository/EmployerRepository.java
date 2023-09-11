package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    // query methods here

}