package cal.projeteq3.glucose.dto.contract;

import cal.projeteq3.glucose.dto.auth.LoginDTO;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.contract.Signature;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignatureDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private LocalDate signatureDate;
    private Long contractId;

    public SignatureDTO(Signature signature){
        this.id = signature.getId();
        this.firstName = signature.getFirstName();
        this.lastName = signature.getLastName();
        this.signatureDate = signature.getSignatureDate();
        this.contractId = signature.getContract().getId();
    }

    public Signature toEntity(){
        return Signature.builder()
                .firstName(this.firstName)
                .lastName(this.lastName)
                .signatureDate(this.signatureDate)
                .build();
    }
}