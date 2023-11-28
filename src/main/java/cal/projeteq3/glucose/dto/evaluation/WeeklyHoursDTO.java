package cal.projeteq3.glucose.dto.evaluation;

import cal.projeteq3.glucose.model.evaluation.timeSheet.WeeklyHours;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WeeklyHoursDTO {
    private LocalDateTime weekStartDate;
    private LocalDateTime weekEndDate;
    private Integer internRealWorkingHours;
    private Integer directSupervisionHours;

    public WeeklyHoursDTO(LocalDateTime weekStartDate, LocalDateTime weekEndDate, Integer internRealWorkingHours, Integer directSupervisionHours) {
        this.weekStartDate = weekStartDate;
        this.weekEndDate = weekEndDate;
        this.internRealWorkingHours = internRealWorkingHours;
        this.directSupervisionHours = directSupervisionHours;
    }
}
