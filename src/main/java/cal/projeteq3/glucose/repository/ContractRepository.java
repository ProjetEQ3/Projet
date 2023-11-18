package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAllByStudentId(Long studentId);
    List<Contract> findAllByJobOffer_Semester(Semester semester);
    void deleteAllByIdNotAndJobOfferSemester(Long id, Semester jobOffer_semester);
}
