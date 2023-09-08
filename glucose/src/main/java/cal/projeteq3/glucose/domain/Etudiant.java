package cal.projeteq3.glucose.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Etudiant extends Utilisateur{

    private String matricule;
    private Departement departement;
}
