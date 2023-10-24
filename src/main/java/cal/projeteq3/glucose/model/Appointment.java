package cal.projeteq3.glucose.model;

import cal.projeteq3.glucose.exception.badRequestException.JobApplicationNotFoundException;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private JobApplication jobApplication;

    private LocalDateTime appointmentDate;

    private boolean isChosen;

    public Appointment(){
        this.isChosen = false;
    }
}
