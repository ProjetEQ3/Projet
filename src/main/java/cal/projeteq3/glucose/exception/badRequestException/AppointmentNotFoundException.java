package cal.projeteq3.glucose.exception.badRequestException;

public class AppointmentNotFoundException extends BadRequestException{

    public AppointmentNotFoundException(Long id){
        super("Appointment with ID " + id + " does not exist.");
    }

    public AppointmentNotFoundException(){
        super("Appointment does not exist.");
    }

}