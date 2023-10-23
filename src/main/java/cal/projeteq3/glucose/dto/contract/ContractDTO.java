package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDTO {
    private Long id;
    private Long employerId;
    private Long studentId;
    private Long jobOfferId;
    private SignatureDTO employerSignature;
    private SignatureDTO studentSignature;
    private SignatureDTO managerSignature;

    public ContractDTO(Contract contract){
        this.id = contract.getId();
    }

    public Contract toEntity(Employer employer, Student student){
        return Contract.builder()
                .employer(employer)
                .student(student)
                .build();
    }
}
