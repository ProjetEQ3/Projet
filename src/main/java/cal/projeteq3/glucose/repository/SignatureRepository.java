package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.contract.Signature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SignatureRepository extends JpaRepository<Signature, Long> {
}
