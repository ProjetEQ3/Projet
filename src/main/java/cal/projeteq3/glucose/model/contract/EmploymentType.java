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

    public String toString() {
        return switch (this) {
            case FULL_TIME -> "Full time";
            case PART_TIME -> "Part time";
            case INTERNSHIP -> "Internship";
            case APPRENTICESHIP -> "Apprenticeship";
            case FIXED_TERM_CONTRACT -> "Fixed term contract";
            case PERMANENT_CONTRACT -> "Permanent contract";
            case FREELANCE -> "Freelance";
            case OTHER -> other;
            default -> "";
        };
    }
}
