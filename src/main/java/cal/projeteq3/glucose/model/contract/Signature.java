package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.model.auth.Credentials;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Signature {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Credentials credentials;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private LocalDate signatureDate;

    @JoinColumn(nullable = false)
    @OneToOne
    private Contract contract;

}
