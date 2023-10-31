package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.validation.Validation;
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
        this.employerId = contract.getEmployer().getId();
        this.studentId = contract.getStudent().getId();
        this.jobOfferId = contract.getJobOffer().getId();

        if (contract.getEmployerSignature() == null) {
            this.employerSignature = null;
        } else {
            this.employerSignature = new SignatureDTO(contract.getEmployerSignature());
        }

        if (contract.getStudentSignature() == null) {
            this.studentSignature = null;
        } else {
            this.studentSignature = new SignatureDTO(contract.getStudentSignature());
        }

        if (contract.getManagerSignature() == null) {
            this.managerSignature = null;
        } else {
            this.managerSignature = new SignatureDTO(contract.getManagerSignature());
        }
    }

    public Contract toEntity(Employer employer, Student student){
        return Contract.builder()
                .employer(employer)
                .student(student)
                .build();
    }

    public ContractDTO fromEntity(Contract contract) {
        return ContractDTO.builder()
                .id(contract.getId())
                .employerId(contract.getEmployer().getId())
                .studentId(contract.getStudent().getId())
                .jobOfferId(contract.getJobOffer().getId())
                .employerSignature(new SignatureDTO(contract.getEmployerSignature()))
                .studentSignature(new SignatureDTO(contract.getStudentSignature()))
                .managerSignature(new SignatureDTO(contract.getManagerSignature()))
                .build();
    }


}
