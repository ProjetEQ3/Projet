package cal.projeteq3.glucose.model.user;

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
public class Supervisor extends User{
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String organisationName;

    @Column(nullable = false)
    private String organisationPhone;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    @JoinColumn(nullable = false, table = "employer")
    private Long employerId;

}
