package cal.projeteq3.glucose.exception;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class CustomErrorResponse {

    private String timestamp;
    private int status;
    private String message;
    private String path;
}
