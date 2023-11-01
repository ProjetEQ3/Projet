package cal.projeteq3.glucose.exception.badRequestException;

public class UserNotFoundException extends BadRequestException{
//    TODO: add a message to the exception
        public UserNotFoundException(Long id) {
            super("userNotFound");
        }

        public UserNotFoundException(String email) {
            super("userNotFound");
        }

        public UserNotFoundException() {
            super("userNotFound");

        }
}
