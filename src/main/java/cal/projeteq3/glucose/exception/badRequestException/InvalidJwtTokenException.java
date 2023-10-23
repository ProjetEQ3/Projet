package cal.projeteq3.glucose.exception.badRequestException;

public class InvalidJwtTokenException extends BadRequestException {
  public InvalidJwtTokenException(String message) {
    super(message);
  }
}
