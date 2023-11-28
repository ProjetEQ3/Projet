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

    public TimeSheetDTO(List<WeeklyHoursDTO> weeklyHours, Long jobApplicationId, Long id) {
        this.weeklyHours = weeklyHours;
        this.jobApplicationId = jobApplicationId;
        this.id = id;
    }

    public TimeSheetDTO() {
    }
}
