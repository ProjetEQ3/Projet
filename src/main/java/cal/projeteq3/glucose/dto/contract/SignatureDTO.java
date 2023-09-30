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
        this.email = signature.getCredentials().getEmail();
        this.firstName = signature.getFirstName();
        this.lastName = signature.getLastName();
        this.jobTitle = signature.getJobTitle();
        this.signatureDate = signature.getSignatureDate();
        this.contractId = signature.getContract().getId();
    }

    public Signature toEntity(LoginDTO loginDTO, Role role){
        return Signature.builder()
                .credentials(
                        Credentials.builder()
                                .email(loginDTO.getEmail())
                                .password(loginDTO.getPassword())
                                .role(role)
                                .build()
                )
                .firstName(this.firstName)
                .lastName(this.lastName)
                .jobTitle(this.jobTitle)
                .signatureDate(this.signatureDate)
                .build();
    }
}
