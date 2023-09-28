package cal.projeteq3.glucose.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String street;

    private String addressNumber; // numero: Appartement, Unité, Bureau

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    public enum AddressType {
        APARTMENT,
        UNIT,
        BUILDING
    }
}
