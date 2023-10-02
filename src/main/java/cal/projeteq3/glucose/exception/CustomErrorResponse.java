package cal.projeteq3.glucose.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomErrorResponse {

    private String timestamp;
    private int status;
    private String error;
    private String path;
}
