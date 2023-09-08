package cal.projeteq3.glucose.Dto.auth;

import cal.projeteq3.glucose.Dto.error.ErrorDto;
import lombok.Getter;
import java.util.List;

@Getter
public final class JWTAuthResponse extends ErrorDto{
	private final String tokenType = "ProjetEQ3";
	private String accessToken;

	public JWTAuthResponse(String accessToken){
		setAccessToken(accessToken);
		validate();
	}

	public JWTAuthResponse(List<String> errors){
		setAccessToken("");
		addErrors(errors);
	}

	public void setAccessToken(String accessToken){
		this.accessToken = accessToken;
	}

	@Override
	public void validate(){
		clearErrors();
		if(accessToken == null || accessToken.isEmpty()){
			addError("Access token is empty");
		}
	}

}
