package cal.projeteq3.glucose.dto;

import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.model.Appointment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class AppointmentDTO {
    private Long id;
    private JobApplicationDTO jobApplication;
    private LocalDateTime appointmentDate;
    private boolean isChosen;

    public AppointmentDTO(Appointment appointment){
        this.id = appointment.getId();
        this.jobApplication = new JobApplicationDTO(appointment.getJobApplication());
        this.appointmentDate = appointment.getAppointmentDate();
        this.isChosen = appointment.isChosen();
    }
}
