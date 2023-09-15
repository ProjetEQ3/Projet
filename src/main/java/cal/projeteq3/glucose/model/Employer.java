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
public final class Employer extends User<Employer> {
    private String organisationName;
    private String organisationPhone;

    @OneToMany(mappedBy = "employer")
    private List<JobOffer> jobOffer;

    @Builder
    public Employer(Long id, String firstName, String lastName, String email, String password, String organisationName, String organisationPhone, List<JobOffer> jobOffer) {
        super(id, firstName, lastName, email, password);
        this.organisationName = organisationName;
        this.organisationPhone = organisationPhone;
        this.jobOffer = jobOffer;
    }

}
