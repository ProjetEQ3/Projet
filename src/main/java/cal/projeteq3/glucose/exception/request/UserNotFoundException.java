package cal.projeteq3.glucose.exception.request;

public class UserNotFoundException extends BadRequestException{
        public UserNotFoundException(Long id) {
            super("User with ID " + id + " does not exist.");
        }

        public UserNotFoundException(String email) {
            super("User with email " + email + " does not exist.");
        }
}
