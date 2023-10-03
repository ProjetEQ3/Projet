package cal.projeteq3.glucose.exception.request;

public class AddressNotFoundException extends BadRequestException{
    public AddressNotFoundException(String message) {
        super("Address not found: " + message);
    }
}
