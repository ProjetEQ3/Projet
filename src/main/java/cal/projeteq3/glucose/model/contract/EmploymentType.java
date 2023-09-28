package cal.projeteq3.glucose.model.contract;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum EmploymentType {
    FULL_TIME,
    PART_TIME,
    INTERNSHIP,
    APPRENTICESHIP,
    FIXED_TERM_CONTRACT,
    PERMANENT_CONTRACT,
    FREELANCE,
    OTHER
    ;

    private String other;
}
