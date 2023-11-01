package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> findAllByStudentId(Long studentId);
    Optional<Contract> findByEmployerId(Long employerId);
    Optional<Contract> findByStudentIdAndEmployerId(Long studentId, Long employerId);
    Optional<Contract> findContractByEmployerSignatureId(Long employerSignatureId);
    Optional<Contract> findContractByStudentSignatureId(Long studentSignatureId);
    Optional<Contract> findContractByManagerSignatureId(Long managerSignatureId);
    List<Contract> findAllByJobOffer_Semester(Semester semester);
}
