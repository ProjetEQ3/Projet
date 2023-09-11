package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Employer extends User {
    private String organisationName;
    private String organisationPhone;
    @OneToMany(mappedBy = "employer")
    private List<JobOffer> jobOffer;

    public Employer(String firstName, String lastName, String email, String password, String organisationName, String organisationPhone, List<JobOffer> jobOffer) {
        super(firstName, lastName, email, password);
        this.organisationName = organisationName;
        this.organisationPhone = organisationPhone;
        this.jobOffer = jobOffer;
    }
}
