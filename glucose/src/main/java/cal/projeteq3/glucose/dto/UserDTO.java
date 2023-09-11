package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class UserDTO {
    private String nom;
    private String prenom;
    private String adresseCourriel;
    private String motDePasse;
}
