package cal.projeteq3.glucose.validation;

import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.exception.request.ValidationException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.model.Student;

import java.time.LocalDate;
import static cal.projeteq3.glucose.validation.ValidationPattern.*;

public final class Validation{
	private static final int MIN_AGE = 18;
	private static final int MAX_AGE = 100;

	public static void validateEmail(String email){
		if(email.matches(EMAIL_PATTERN.toString()))
			return;
		exception(ValidationMessage.EMAIL_MESSAGE.toString());
	}

	public static void validatePassword(String password){
		if(password.matches(PASSWORD_PATTERN.toString()))
			return;
		exception(ValidationMessage.PASSWORD_MESSAGE.toString());
	}

	public static void validateName(String name){
		if(name.matches(NAME_PATTERN.toString()))
			return;
		exception(ValidationMessage.NAME_MESSAGE.toString());
	}

	public static void validateBirthDate(LocalDate birthDate){
		if(
			birthDate.isBefore(LocalDate.now().minusYears(MAX_AGE)) &&
			birthDate.isAfter(LocalDate.now().minusYears(MIN_AGE))
		)
			return;
		exception(ValidationMessage.AGE_MESSAGE.toString());
	}

	public static void validateStreet(String street){
		if(street.matches(STREET_PATTERN.toString()))
			return;
		exception(ValidationMessage.STREET_MESSAGE.toString());
	}

	public static void validateCity(String city){
		if(city.matches(CITY_PATTERN.toString()))
			return;
		exception(ValidationMessage.CITY_MESSAGE.toString());
	}

	public static void validateProvince(String province){
		if(province.matches(PROVINCE_PATTERN.toString()))
			return;
		exception(ValidationMessage.PROVINCE_MESSAGE.toString());
	}

	public static void validatePostalCode(String postalCode){
		if(postalCode.matches(POSTAL_CODE_PATTERN.toString()))
			return;
		exception(ValidationMessage.POSTAL_CODE_MESSAGE.toString());
	}

	public static void validateCountry(String country){
		if(country.matches(COUNTRY_PATTERN.toString()))
			return;
		exception(ValidationMessage.COUNTRY_MESSAGE.toString());
	}

	public static void validatePhoneNumber(String phoneNumber){
		if(phoneNumber.matches(PHONE_NUMBER_PATTERN.toString()))
			return;
		exception(ValidationMessage.PHONE_NUMBER_MESSAGE.toString());
	}

	public static void validateUrl(String website){
		if(website.matches(WEBSITE_PATTERN.toString()))
			return;
        exception(ValidationMessage.WEBSITE_MESSAGE.toString());
	}

	public static void validateCvFileName(String fileName){
		if(fileName.matches(CV_FILE_NAME_PATTERN.toString()))
			return;
		exception(ValidationMessage.CV_FILE_NAME_MESSAGE.toString());
	}

	public static void validateSerial(String serial){
		if(!serial.matches(SERIAL_PATTERN.toString()))
			exception(ValidationMessage.SERIAL_MESSAGE.toString());
	}

	private static void exception(String message){
		throw new ValidationException(message);
	}

	public static void validateEmploye(Employer employer) {
		validateName(employer.getFirstName());
		validateName(employer.getLastName());
		validateEmail(employer.getEmail());
		validatePassword(employer.getPassword());
		validateOrgName(employer.getOrganisationName());
		validatePhoneNumber(employer.getOrganisationPhone());
	}

	private static void validateOrgName(String organisationName) {
		if(organisationName.matches(ORGANISATION_NAME_PATTERN.toString()))
			return;
		exception(ValidationMessage.ORGANISATION_NAME_MESSAGE.toString());
	}

	public static void validateStudent(Student student) {
		validateName(student.getFirstName());
		validateName(student.getLastName());
		validateEmail(student.getEmail());
		validatePassword(student.getPassword());
		validateMatricule(student.getMatricule());
	}

	private static void validateMatricule(String matricule) {
		if(matricule.matches(MATRICULE_PATTERN.toString()))
			return;
		exception(ValidationMessage.MATRICULE_MESSAGE.toString());
	}
}
