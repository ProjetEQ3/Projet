package cal.projeteq3.glucose.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long utilisateurID;

    private String nom;
    private String prenom;
    private String adresseCourriel;
    private String motDePasse;

    public Utilisateur(String nom, String prenom, String adresseCourriel, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresseCourriel = adresseCourriel;
        this.motDePasse = motDePasse;
    }
}
