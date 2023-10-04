package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContractDTO {
    private Long jobOfferId;
    private Long workAddressId;
    private Long supervisorId;
    private List<String> responsibilities = new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private Time startShiftTime;
    private Time endShiftTime;
    private int hoursPerDay;
    private EmploymentType employmentType;
    private Set<DayOfWeek> workDays;

}
