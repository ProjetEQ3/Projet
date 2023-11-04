package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @ToString.Exclude
    private JobApplication jobApplication;

    private LocalDateTime appointmentDate;

    private boolean isChosen;

    public Appointment(){
        this.isChosen = false;
    }
}
