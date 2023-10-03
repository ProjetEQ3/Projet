package cal.projeteq3.glucose.model.jobOffer;

import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne(optional = false)
    private JobOffer jobOffer;

    private JobApplicationState jobApplicationState = JobApplicationState.SUBMITTED;

}