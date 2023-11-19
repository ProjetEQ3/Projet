package cal.projeteq3.glucose.model.evaluation.child;

import cal.projeteq3.glucose.model.evaluation.DeclarationHour;
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
public class ActualHoursDeclaration extends Evaluation{
	@ElementCollection
	@CollectionTable(
		name = "actual_hours_declaration_hours",
		joinColumns = @JoinColumn(name = "actual_hours_declaration_id")
	)
	private List<DeclarationHour> declarationHours;
	private String signatureFonction;
	private boolean isSigned;
}
