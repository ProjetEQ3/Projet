package cal.projeteq3.glucose.validation;

public enum ValidationMessage{
	EMAIL_MESSAGE("Email must be a valid email address."),
	PASSWORD_MESSAGE("Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number."),
	NAME_MESSAGE("Name must be between 2 and 100 characters long and contain only letters, hyphens and apostrophes."),
	AGE_MESSAGE("Age must be between 18 and 100 years old."),
	STREET_MESSAGE("Street must be between 2 and 100 characters long and contain only letters, numbers, hyphens, apostrophes, and spaces."),
	CITY_MESSAGE("City must be between 2 and 50 characters long and contain only letters, hyphens, and spaces."),
	PROVINCE_MESSAGE("Province must be 2 characters long and contain only letters."),
	POSTAL_CODE_MESSAGE("Postal code must be a valid postal code. (e.g. A1A 1A1)"),
	COUNTRY_MESSAGE("Country must be between 2 and 50 characters long and contain only letters, hyphens, and spaces."),
	PHONE_NUMBER_MESSAGE("Phone number must be a valid phone number."),
	WEBSITE_MESSAGE("Website must be a valid URL.");

	private final String string;

	ValidationMessage(String string){
		this.string = string;
	}

	@Override
	public String toString(){
		return string;
	}

}