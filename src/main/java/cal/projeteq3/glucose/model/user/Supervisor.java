package cal.projeteq3.glucose.model.user;

import cal.projeteq3.glucose.model.auth.Credentials;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supervisor extends User{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String organisationName;

    @Column(nullable = false)
    private String organisationPhone;

    @Column(nullable = false)
    private String jobTitle;

    @Builder
    public Supervisor(Long id, String firstName, String lastName, Credentials credentials, String organisationName, String organisationPhone, String jobTitle) {
        super(id, firstName, lastName, credentials);
        this.id = id;
        this.organisationName = organisationName;
        this.organisationPhone = organisationPhone;
        this.jobTitle = jobTitle;
    }
}
