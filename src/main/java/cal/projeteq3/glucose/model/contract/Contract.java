package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.exception.unauthorizedException.SignaturePrerequisitNotMet;
import cal.projeteq3.glucose.model.Address;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.Supervisor;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import static java.time.DayOfWeek.*;

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
    private Supervisor supervisor;

    @ManyToOne
    private Address workAddress;

    @ManyToOne
    private Student student;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false, length = 2000)
    private String responsibilities;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private float hoursPerWeek;

    @Column(nullable = false)
    private Time startShiftTime;

    @Column(nullable = false)
    private Time EndShiftTime;

    @Column(nullable = false)
    private float hoursPerDay;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmploymentType employmentType = EmploymentType.FULL_TIME;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Set<DayOfWeek> workDays = Set.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);

    @Column(nullable = false)
    private double hourlyRate;

    @OneToOne
    private Signature employerSignature;
    @OneToOne
    private Signature studentSignature;
    @OneToOne
    private Signature directorSignature;

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

    public void setDirectorSignature(Signature directorSignature) {
        if (this.directorSignature != null)
            throw new IllegalStateException("Director signature already set");
        if (this.employerSignature == null || this.studentSignature == null)
            throw new SignaturePrerequisitNotMet();

        this.directorSignature = directorSignature;
        lastModificationDate = LocalDateTime.now();
    }
}
