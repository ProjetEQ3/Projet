package cal.projeteq3.glucose.domain.dto;

import cal.projeteq3.glucose.domain.Departement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EtudiantDTO extends UtilisateurDTO{
    private String matricule;
    private Departement departement;
}
