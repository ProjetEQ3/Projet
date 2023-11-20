package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.model.contract.Signature;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignatureDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String signatureDate;
    private Long contractId;

    public SignatureDTO(Signature signature) {
        this.id = signature.getId();
        this.firstName = signature.getFirstName();
        this.lastName = signature.getLastName();
        this.signatureDate = signature.getSignatureDate().toString();
        this.contractId = signature.getContract().getId();
    }
}
