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
	SERIAL_MESSAGE("Le numéro de série doit contenir entre 3 et 100 caractères et ne peut contenir que des lettres, des chiffres, des tirets bas, des espaces et des tirets.")
	;

	private final String string;

	ValidationMessage(String string){
		this.string = string;
	}

	@Override
	public String toString(){
		return string;
	}

}