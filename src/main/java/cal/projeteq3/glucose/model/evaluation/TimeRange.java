package cal.projeteq3.glucose.model.evaluation;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public final class TimeRange {
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
