package cal.projeteq3.glucose.model;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Student extends User {

    private String matricule;
    private Department department;

    public Student(String nom, String prenom, String email, String password, String matricule, String departement) {
        super(nom, prenom, email, password);
        this.matricule = matricule;
        this.department = Department.valueOf(departement);
    }
}
