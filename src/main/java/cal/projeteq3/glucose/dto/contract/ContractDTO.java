package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.Address;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.EmploymentType;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.Supervisor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.sql.Time;
import java.util.List;
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
    private List<String> responsibilities;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private float hoursPerWeek;
    private Time startShiftTime;
    private Time endShiftTime;
    private float hoursPerDay;
    private EmploymentType employmentType;
    private Set<DayOfWeek> workDays;
    private double hourlyRate;

    public ContractDTO(Contract contract){
        this.id = contract.getId();
        this.employerId = contract.getEmployer().getId();
        this.supervisorId = contract.getSupervisor().getId();
        this.workAddressId = contract.getWorkAddress().getId();
        this.studentId = contract.getStudent().getId();
        this.jobTitle = contract.getJobTitle();
        this.responsibilities = contract.getResponsibilities();
        this.startDate = contract.getStartDate();
        this.endDate = contract.getEndDate();
        this.duration = contract.getDuration();
        this.hoursPerWeek = contract.getHoursPerWeek();
        this.startShiftTime = contract.getStartShiftTime();
        this.endShiftTime = contract.getEndShiftTime();
        this.hoursPerDay = contract.getHoursPerDay();
        this.employmentType = contract.getEmploymentType();
        this.workDays = contract.getWorkDays();
        this.hourlyRate = contract.getHourlyRate();
    }

    public Contract toEntity(Employer employer, Supervisor supervisor, Student student, Address workAddress){
        return Contract.builder()
                .employer(employer)
                .supervisor(supervisor)
                .workAddress(workAddress)
                .student(student)
                .jobTitle(this.jobTitle)
                .responsibilities(this.responsibilities)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .duration(this.duration)
                .hoursPerWeek(this.hoursPerWeek)
                .startShiftTime(this.startShiftTime)
                .EndShiftTime(this.endShiftTime)
                .hoursPerDay(this.hoursPerDay)
                .employmentType(this.employmentType)
                .workDays(this.workDays)
                .hourlyRate(this.hourlyRate)
                .build();
    }
}
