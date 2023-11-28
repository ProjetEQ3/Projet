package cal.projeteq3.glucose.model.evaluation.timeSheet;

import cal.projeteq3.glucose.dto.evaluation.WeeklyHoursDTO;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class WeeklyHours {
	private LocalDate weekStartDate;
	private LocalDate weekEndDate;
	private Integer internRealWorkingHours;
	private Integer directSupervisionHours;
}
