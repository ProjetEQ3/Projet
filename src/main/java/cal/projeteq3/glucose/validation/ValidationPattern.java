package cal.projeteq3.glucose.validation;

public enum ValidationPattern{
	SANITIZED_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$"),
	EMAIL_PATTERN("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
	PASSWORD_PATTERN("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$"),
	NAME_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,50}$"),
	STREET_PATTERN("^[A-Za-z0-9À-ÖØ-öø-ÿ\\-' ]{2,100}$"),
	CITY_PATTERN("^[A-Za-zÀ-ÖØ-öø-ÿ\\- ]{2,50}$"),
	PROVINCE_PATTERN("^[A-Za-z]{2,}$"),
	POSTAL_CODE_PATTERN("^[A-Za-z0-9 ]{5,}$"),
	COUNTRY_PATTERN("^[A-Za-zÀ-ÖØ-öø-ÿ\\- ]{2,50}$"),
	PHONE_NUMBER_PATTERN("^(\\d{1,4}[\\s\\-()]{0,3}){1,3}\\d{1,4}$"),
	WEBSITE_PATTERN("^([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$"),
	CV_FILE_NAME_PATTERN("^[\\p{L}0-9-_\\s]{2,100}\\.pdf$"),
	SERIAL_PATTERN("^[a-zA-Z0-9_-]{3,100}$"),
    ORGANISATION_NAME_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$"),
	MATRICULE_PATTERN("^[0-9]{7}$"),
	TITLE_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$"),
	DEPARTMENT_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$"),
	LOCATION_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$"),
	DESCRIPTION_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$"),
	SALARY_PATTERN("^[0-9]{1,}$"),
	START_DATE_PATTERN("^[0-9]{1,}$"),
	DURATION_PATTERN("^[0-9]{1,}$"),
	HOURS_PER_WEEK_PATTERN("^[0-9]{1,}$"),
	EXPIRATION_DATE_PATTERN("^[0-9]{1,}$"),
	JOB_OFFER_STATE_PATTERN("^[a-zA-Z0-9À-ÖØ-öø-ÿ_ -,.]{2,100}$");

	private final String string;

	ValidationPattern(String string){
		this.string = string;
	}

	@Override
	public String toString(){
		return string;
	}

}
