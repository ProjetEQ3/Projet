package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public final class EmailDetailsDTO{
	private String to;
	private String subject;
	private String text;

}
