package cal.projeteq3.glucose.dto.user;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupervisorDTO extends UserDTO{
    private Long id;
    private String organisationName;
    private String organisationPhone;
    private String jobTitle;

}
