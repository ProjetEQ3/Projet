package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.Address;
import cal.projeteq3.glucose.model.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByStudentId(Long studentId);
    Optional<Contract> findByEmployerId(Long employerId);
    Optional<Contract> findByStudentIdAndEmployerId(Long studentId, Long employerId);
    Optional<Contract> findContractByEmployerSignatureId(Long employerSignatureId);
    Optional<Contract> findContractByStudentSignatureId(Long studentSignatureId);
    Optional<Contract> findContractByManagerSignatureId(Long managerSignatureId);
}
