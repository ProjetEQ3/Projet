package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.exception.unauthorizedException.SignaturePrerequisitNotMet;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Employer employer;

    @ManyToOne
    private Student student;

    @ManyToOne
    private JobOffer jobOffer;

    @OneToOne
    private Signature employerSignature;
    @OneToOne
    private Signature studentSignature;
    @OneToOne
    private Signature managerSignature;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModificationDate = LocalDateTime.now();


    public void setEmployerSignature(Signature employerSignature) {
        if (this.employerSignature != null)
            throw new IllegalStateException("Employer signature already set");

        this.employerSignature = employerSignature;
        lastModificationDate = LocalDateTime.now();
    }

    public void setStudentSignature(Signature studentSignature) {
        if (this.studentSignature != null)
            throw new IllegalStateException("Student signature already set");
        if (this.employerSignature == null)
            throw new SignaturePrerequisitNotMet();

        this.studentSignature = studentSignature;
        lastModificationDate = LocalDateTime.now();
    }

    public void setManagerSignature(Signature directorSignature) {
        if (this.managerSignature != null)
            throw new IllegalStateException("Director signature already set");
        if (this.employerSignature == null || this.studentSignature == null)
            throw new SignaturePrerequisitNotMet();

        this.managerSignature = directorSignature;
        lastModificationDate = LocalDateTime.now();
    }
}
