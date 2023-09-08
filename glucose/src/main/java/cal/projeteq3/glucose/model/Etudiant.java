package cal.projeteq3.glucose.model;

import jakarta.persistence.Entity;
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
