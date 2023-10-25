package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.EmploymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractCreationDTO {
    private Long id;
    private Long studentId;
    private Long jobOfferId;
}
