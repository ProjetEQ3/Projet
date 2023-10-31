package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.util.ContractPDFGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortContractDTO {
    private Long id;
    private String studentName;
    private String jobOfferName;
    private String jobOfferCompany;
    private String data;

    public ShortContractDTO(Contract contract, Manager manager) {
        this.id = contract.getId();
        this.studentName = contract.getStudent().getFirstName() + " " + contract.getStudent().getLastName();
        this.jobOfferName = contract.getJobOffer().getTitle();
        this.jobOfferCompany = contract.getJobOffer().getEmployer().getOrganisationName();
        this.data = ContractPDFGenerator.generatePDF(contract, manager);
    }
}
