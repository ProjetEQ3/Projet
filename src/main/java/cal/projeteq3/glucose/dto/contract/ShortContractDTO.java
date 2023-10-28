package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.Contract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortContractDTO {
    private Long id;
    private String studentName;
    private String jobOfferName;
    private String jobOfferCompany;
    private byte[] data;

    public ShortContractDTO(Contract contract) {
        this.id = contract.getId();
        this.studentName = contract.getStudent().getFirstName() + " " + contract.getStudent().getLastName();
        this.jobOfferName = contract.getJobOffer().getTitle();
        this.jobOfferCompany = contract.getJobOffer().getEmployer().getOrganisationName();
        this.data = contract.generateContractPDF();
    }
}
