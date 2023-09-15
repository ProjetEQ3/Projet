package cal.projeteq3.glucose.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public final class Manager extends User<Manager> {
    private String matricule;
    private String phoneNumber;

    @Builder
    public Manager(long id, String firstName, String lastName, String email, String password, String matricule, String phoneNumber) {
        super(id, firstName, lastName, email, password);
        this.matricule = matricule;
        this.phoneNumber = phoneNumber;
    }

}
