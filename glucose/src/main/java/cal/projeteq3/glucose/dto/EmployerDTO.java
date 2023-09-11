package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployerDTO extends UserDTO {
    private String organisationName;
    private String organisationPhone;
}
