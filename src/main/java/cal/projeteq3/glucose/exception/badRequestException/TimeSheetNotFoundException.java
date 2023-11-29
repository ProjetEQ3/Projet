package cal.projeteq3.glucose.exception.badRequestException;

public class TimeSheetNotFoundException extends BadRequestException {
    public TimeSheetNotFoundException() {
        super("timeSheetNotFound");
    }
}
