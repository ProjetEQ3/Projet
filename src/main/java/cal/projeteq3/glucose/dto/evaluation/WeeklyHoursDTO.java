package cal.projeteq3.glucose.dto.evaluation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class WeeklyHoursDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate weekStartDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate weekEndDate;
    private Integer internRealWorkingHours;
    private Integer directSupervisionHours;

    public WeeklyHoursDTO(LocalDate weekStartDate, LocalDate weekEndDate, Integer internRealWorkingHours, Integer directSupervisionHours) {
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.internRealWorkingHours = internRealWorkingHours;
        this.directSupervisionHours = directSupervisionHours;
    }
}
