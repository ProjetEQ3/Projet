package cal.projeteq3.glucose.dto.contract;

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
}
