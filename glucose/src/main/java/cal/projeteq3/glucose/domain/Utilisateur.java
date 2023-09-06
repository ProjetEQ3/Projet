package cal.projeteq3.glucose.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long utilisateurID;

    private String nom;
    private String prenom;
    private String adresseCourriel;
    private String motDePasse;

}
