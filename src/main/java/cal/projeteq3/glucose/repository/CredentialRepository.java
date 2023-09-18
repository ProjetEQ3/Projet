package cal.projeteq3.glucose.repository;

import cal.projeteq3.glucose.model.auth.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credentials, Long> {
    Optional<Credentials> findCredentialsByEmail(String email);
}
