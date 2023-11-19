package cal.projeteq3.glucose.model.evaluation.section;

import cal.projeteq3.glucose.model.evaluation.TimeRange;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeneralObservations{
	private Boolean isFirstInternshipPreferred;
	private Boolean isSecondInternshipPreferred;
	private Integer numberOfInternsOpenTo;
	private Boolean wouldHostSameInternAgain;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "startTime", column = @Column(name = "first_shift_start")),
		@AttributeOverride(name = "endTime", column = @Column(name = "first_shift_end"))
	})
	private TimeRange firstVariableWorkShifts;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "startTime", column = @Column(name = "second_shift_start")),
		@AttributeOverride(name = "endTime", column = @Column(name = "second_shift_end"))
	})
	private TimeRange secondVariableWorkShifts;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "startTime", column = @Column(name = "third_shift_start")),
		@AttributeOverride(name = "endTime", column = @Column(name = "third_shift_end"))
	})
	private TimeRange thirdVariableWorkShifts;
}
