package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.user.Supervisor;
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

    public SupervisorDTO(Supervisor supervisor){
        super(supervisor.getFirstName(), supervisor.getLastName(), "SUPERVISOR");
        this.id = supervisor.getId();
        this.organisationName = supervisor.getOrganisationName();
        this.organisationPhone = supervisor.getOrganisationPhone();
        this.jobTitle = supervisor.getJobTitle();
    }

    public Supervisor toEntity(){
        return Supervisor.builder()
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .credentials(Credentials.builder()
                        .email(this.getEmail())
                        .build())
                .organisationName(this.organisationName)
                .organisationPhone(this.organisationPhone)
                .jobTitle(this.jobTitle)
                .build();
    }



}
