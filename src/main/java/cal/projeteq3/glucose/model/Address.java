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

    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String addressNumber; // numero: Appartement, Unité, Bureau

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
        BUILDING,
        HOUSE
        ;

        @Override
        public String toString() {
            return switch (this) {
                case APARTMENT -> "Apartment";
                case UNIT -> "Unit";
                case BUILDING -> "Building";
                default -> "";
            };
        }
    }

    @Override
    public String toString() {
        if (addressType == null) {

            return street + " " + "\n" +
                    city + ", " + state + ", " + zipCode + "\n" +
                    country;
        } else {

            return street + " " + addressType + ": " + addressNumber + "\n" +
                    city + ", " + state + ", " + zipCode + "\n" +
                    country;
        }
    }
}
