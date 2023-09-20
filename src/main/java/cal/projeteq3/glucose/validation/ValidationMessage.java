package cal.projeteq3.glucose.validation;

public enum ValidationMessage{
	EMAIL_MESSAGE("L'adresse e-mail doit être une adresse e-mail valide."),
	PASSWORD_MESSAGE("Le mot de passe doit contenir au moins 8 caractères et inclure au moins une lettre majuscule, une lettre minuscule et un chiffre."),
	NAME_MESSAGE("Le nom doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des tirets et des apostrophes."),
	AGE_MESSAGE("L'âge doit être compris entre 18 et 100 ans."),
	STREET_MESSAGE("La rue doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	CITY_MESSAGE("La ville doit contenir entre 2 et 50 caractères et ne peut contenir que des lettres, des tirets et des espaces."),
	PROVINCE_MESSAGE("La province doit contenir 2 caractères et ne peut contenir que des lettres."),
	POSTAL_CODE_MESSAGE("Le code postal doit être un code postal valide. (par exemple, A1A 1A1)"),
	COUNTRY_MESSAGE("Le pays doit contenir entre 2 et 50 caractères et ne peut contenir que des lettres, des tirets et des espaces."),
	PHONE_NUMBER_MESSAGE("Le numéro de téléphone doit être un numéro de téléphone valide."),
	WEBSITE_MESSAGE("Le site web doit être une URL valide."),
	CV_FILE_NAME_MESSAGE("Le nom du fichier doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	SERIAL_MESSAGE("Le numéro de série doit contenir entre 3 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets bas, des espaces et des tirets."),
  ORGANISATION_NAME_MESSAGE("Le nom de l'organisation doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	MATRICULE_MESSAGE("Le matricule doit être de 7 chiffres."),
	ROLE_MESSAGE("Le rôle doit être un rôle valide."),
	TITLE_MESSAGE("Le titre doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	DEPARTMENT_MESSAGE("Le département doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	LOCATION_MESSAGE("L'emplacement doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	DESCRIPTION_MESSAGE("La description doit contenir entre 2 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets, des apostrophes et des espaces."),
	SALARY_MESSAGE("Le salaire doit être un nombre."),
	START_DATE_MESSAGE("La date de début doit être un nombre."),
	DURATION_MESSAGE("La durée doit être un nombre."),
	HOURS_PER_WEEK_MESSAGE("Les heures par semaine doivent être un nombre."),
	EXPIRATION_DATE_MESSAGE("La date d'expiration doit être un nombre."),
	JOB_OFFER_STATE_MESSAGE("L'état de l'offre d'emploi doit être un état valide.");

	private final String string;

	ValidationMessage(String string){
		this.string = string;
	}

	@Override
	public String toString(){
		return string;
	}

}