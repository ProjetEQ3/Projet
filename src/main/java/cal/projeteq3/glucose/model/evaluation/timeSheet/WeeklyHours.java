package cal.projeteq3.glucose.model.evaluation.timeSheet;

import cal.projeteq3.glucose.dto.evaluation.WeeklyHoursDTO;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeeklyHours {
	private LocalDateTime weekStartDate;
	private LocalDateTime weekEndDate;
	private Integer internRealWorkingHours;
	private Integer directSupervisionHours;

	public WeeklyHoursDTO toDTO() {
		return WeeklyHoursDTO.builder()
				.weekStartDate(this.getWeekStartDate())
				.weekEndDate(this.getWeekEndDate())
				.internRealWorkingHours(this.getInternRealWorkingHours())
				.directSupervisionHours(this.getDirectSupervisionHours())
				.build();
	}
}
