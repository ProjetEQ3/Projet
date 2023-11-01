package cal.projeteq3.glucose.exception.badRequestException;

public class UserNotFoundException extends BadRequestException{
        public UserNotFoundException() {
            super("userNotFound");

        }
}
