package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    Optional<Contract> findByStudentId(Long studentId);
    Optional<Contract> findByEmployerId(Long employerId);
    Optional<Contract> findBySupervisorId(Long supervisorId);
    Optional<Contract> findByStudentIdAndEmployerId(Long studentId, Long employerId);
    Optional<Contract> findByStudentIdAndSupervisorId(Long studentId, Long supervisorId);
    Optional<Contract> findByEmployerIdAndSupervisorId(Long employerId, Long supervisorId);
    Optional<Contract> findByStudentIdAndEmployerIdAndSupervisorId(Long studentId, Long employerId, Long supervisorId);
    Optional<Contract> findBySignature(Signature signature);
}
