package cal.projeteq3.glucose.domain.dto;

import cal.projeteq3.glucose.domain.Departement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantDTO {
    private String nom;
    private String prenom;
    private String adresseCourriel;
    private String codeEtudiant;
    private Departement departement;
}
