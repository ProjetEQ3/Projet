package cal.projeteq3.glucose.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Etudiant extends Utilisateur{

    private String matricule;
    private Departement departement;

    public Etudiant(String nom, String prenom, String email, String password, String matricule, String departement) {
        super(nom, prenom, email, password);
        this.matricule = matricule;
        this.departement = Departement.valueOf(departement);
    }
}
