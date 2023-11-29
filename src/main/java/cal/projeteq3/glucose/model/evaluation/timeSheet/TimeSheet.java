package cal.projeteq3.glucose.model.evaluation.timeSheet;

import cal.projeteq3.glucose.dto.evaluation.TimeSheetDTO;
import cal.projeteq3.glucose.dto.evaluation.WeeklyHoursDTO;
import cal.projeteq3.glucose.model.evaluation.Evaluation;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TimeSheet extends Evaluation {
	@ElementCollection
	@CollectionTable(
		name = "weekHours",
		joinColumns = @JoinColumn(name = "timeSheet_id")
	)
	private List<WeeklyHours> weeklyHours;
}
