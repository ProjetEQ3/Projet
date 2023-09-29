package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.model.Address;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

import static java.time.DayOfWeek.*;

@Data
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
    private Employer supervisor;

    @ManyToOne
    @Column(nullable = false)
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
    private EmploymentType employmentType = EmploymentType.FULL_TIME;

    @Enumerated(EnumType.STRING)
    Set<DayOfWeek> workDays = Set.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);

    @Column(nullable = false)
    private double hourlyRate;

    @OneToOne
    private Signature employerSignature;
    @OneToOne
    private Signature studentSignature;
    @OneToOne
    private Signature directorSignature;


}
