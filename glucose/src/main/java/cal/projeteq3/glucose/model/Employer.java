package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employer extends User {
    private String organisationName;
    private String organisationPhone;
    @OneToMany(mappedBy = "employer")
    private List<JobOffer> jobOffer;
}
