package cal.projeteq3.glucose.domain;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Utilisateur {

    @Id
    private long utilisateurID;

    private String nom;
    private String prenom;
    private String adresseCourriel;
    private String motDePasse;

}
