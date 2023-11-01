package cal.projeteq3.glucose.exception.badRequestException;

public class AppointmentNotFoundException extends BadRequestException{

    public AppointmentNotFoundException(){
        super("appointmentNotFound");
    }

}