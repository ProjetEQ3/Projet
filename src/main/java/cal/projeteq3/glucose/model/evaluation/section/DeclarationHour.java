package cal.projeteq3.glucose.model.evaluation.section;

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
	private LocalDateTime semaineStartDate;
	private LocalDateTime semaineEndDate;
	private Integer internRealWorkingHours;
	private Integer directSupervisionHours;
}
