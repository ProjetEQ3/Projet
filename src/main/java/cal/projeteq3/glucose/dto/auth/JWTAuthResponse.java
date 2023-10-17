package cal.projeteq3.glucose.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
    private final String tokenType = "BEARER";
    private String accessToken;
}