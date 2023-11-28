package cal.projeteq3.glucose.dto.evaluation;

import cal.projeteq3.glucose.model.evaluation.timeSheet.TimeSheet;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TimeSheetDTO {
    private List<WeeklyHoursDTO> weeklyHours;
    private Long jobApplicationId;
    private Long id;

    public TimeSheetDTO(TimeSheet timeSheet) {
        this.weeklyHours = timeSheet.getWeeklyHours().stream().map(weeklyHour -> WeeklyHoursDTO.builder()
                .weekStartDate(weeklyHour.getWeekStartDate())
                .weekEndDate(weeklyHour.getWeekEndDate())
                .internRealWorkingHours(weeklyHour.getInternRealWorkingHours())
                .directSupervisionHours(weeklyHour.getDirectSupervisionHours())
                .build()).toList();
        this.jobApplicationId = timeSheet.getJobApplication().getId();
        this.id = timeSheet.getId();
    }
}
