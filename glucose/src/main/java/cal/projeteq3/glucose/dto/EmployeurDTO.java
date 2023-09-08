package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeurDTO extends UtilisateurDTO{
    private String nomOrganisme;
    private String numTelephone;
}
