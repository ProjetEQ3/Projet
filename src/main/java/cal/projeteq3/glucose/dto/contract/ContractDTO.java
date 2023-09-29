package cal.projeteq3.glucose.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.sql.Time;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDTO {
    private Long id;
    private Long employerId;
    private Long supervisorId;
    private Long workAddressId;
    private Long studentId;
    private String jobTitle;
    private String responsibilities;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private float hoursPerWeek;
    private Time startShiftTime;
    private Time endShiftTime;
    private float hoursPerDay;
    private String employmentType;
    private Set<DayOfWeek> workDays;
    private double hourlyRate;
}
