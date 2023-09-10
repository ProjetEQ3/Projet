package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferDTO{
	private Long id;
	private Long employerId;
	private String state;
	private String title;
	private String description;
	private String location;
	private String department;
}
