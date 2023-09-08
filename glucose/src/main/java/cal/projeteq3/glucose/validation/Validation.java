package cal.projeteq3.glucose.validation;

import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import java.time.LocalDate;
import static cal.projeteq3.glucose.validation.ValidationPattern.*;

public final class Validation{
	private static final int MIN_AGE = 18;
	private static final int MAX_AGE = 100;

	public static void validateEmail(String email){
		if(!email.matches(EMAIL_PATTERN.toString()))
			exception(ValidationMessage.EMAIL_MESSAGE.toString());
	}

	public static void validatePassword(String password){
		if(!password.matches(PASSWORD_PATTERN.toString()))
			exception(ValidationMessage.PASSWORD_MESSAGE.toString());
	}

	public static void validateName(String name){
		if(!name.matches(NAME_PATTERN.toString()))
			exception(ValidationMessage.NAME_MESSAGE.toString());
	}

	public static void validateBirthDate(LocalDate birthDate){
		if(
			birthDate.isBefore(LocalDate.now().minusYears(MAX_AGE)) ||
				birthDate.isAfter(LocalDate.now().minusYears(MIN_AGE))
		)
			exception(ValidationMessage.AGE_MESSAGE.toString());
	}

	public static void validateStreet(String street){
		if(!street.matches(STREET_PATTERN.toString()))
			exception(ValidationMessage.STREET_MESSAGE.toString());
	}

	public static void validateCity(String city){
		if(!city.matches(CITY_PATTERN.toString()))
			exception(ValidationMessage.CITY_MESSAGE.toString());
	}

	public static void validateProvince(String province){
		if(!province.matches(PROVINCE_PATTERN.toString()))
			exception(ValidationMessage.PROVINCE_MESSAGE.toString());
	}

	public static void validatePostalCode(String postalCode){
		if(!postalCode.matches(POSTAL_CODE_PATTERN.toString()))
			exception(ValidationMessage.POSTAL_CODE_MESSAGE.toString());
	}

	public static void validateCountry(String country){
		if(!country.matches(COUNTRY_PATTERN.toString()))
			exception(ValidationMessage.COUNTRY_MESSAGE.toString());
	}

	public static void validatePhoneNumber(String phoneNumber){
		if(!phoneNumber.matches(PHONE_NUMBER_PATTERN.toString()))
			exception(ValidationMessage.PHONE_NUMBER_MESSAGE.toString());
	}

	public static void validateUrl(String website){
		if(!website.matches(WEBSITE_PATTERN.toString()))
			exception(ValidationMessage.WEBSITE_MESSAGE.toString());
	}

	private static void exception(String message){
		throw new ValidationException(message);
	}
}
