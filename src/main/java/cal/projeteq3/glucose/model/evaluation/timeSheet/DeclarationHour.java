package cal.projeteq3.glucose.model.evaluation.timeSheet;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DeclarationHour{
	private LocalDateTime weekStartDate;
	private LocalDateTime weekEndDate;
	private Integer internRealWorkingHours;
	private Integer directSupervisionHours;
}
