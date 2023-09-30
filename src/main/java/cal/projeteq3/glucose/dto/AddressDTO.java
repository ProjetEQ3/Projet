package cal.projeteq3.glucose.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private Long id;
    private String street;
    private String addressType;
    private String addressNumber;
    private String city;
    private String zipCode;
    private String state;
    private String country;
}
