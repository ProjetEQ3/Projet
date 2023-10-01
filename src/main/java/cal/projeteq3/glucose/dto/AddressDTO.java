package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.model.Address;
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
    private Address.AddressType addressType;
    private String addressNumber;
    private String city;
    private String zipCode;
    private String state;
    private String country;

    public AddressDTO(Address address){
        this.id = address.getId();
        this.street = address.getStreet();
        this.addressType = address.getAddressType();
        this.addressNumber = address.getAddressNumber();
        this.city = address.getCity();
        this.zipCode = address.getZipCode();
        this.state = address.getState();
        this.country = address.getCountry();
    }

    public Address toEntity(){
        return Address.builder()
                .id(this.id)
                .street(this.street)
                .addressType(this.addressType)
                .addressNumber(this.addressNumber)
                .city(this.city)
                .zipCode(this.zipCode)
                .state(this.state)
                .country(this.country)
                .build();
    }
}
