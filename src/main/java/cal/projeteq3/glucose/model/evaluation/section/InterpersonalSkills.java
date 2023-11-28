package cal.projeteq3.glucose.model.evaluation.section;

import cal.projeteq3.glucose.model.evaluation.enums.AgreementLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public final class InterpersonalSkills{
	private static final String TITLE = "QUALITÉS DES RELATIONS INTERPERSONNELLES";
	private static final String SUBTITLE = "Capacité d’établir des interrelations harmonieuses dans son milieu de travail";
	private static final String[] INTERPERSONAL_SKILLS_QUESTIONS = {
		"établir facilement des contacts avec les gens",
		"contribuer activement au travail d’équipe",
		"s’adapter facilement à la culture de l’entreprise",
		"accepter les critiques constructives",
		"être respectueux envers les gens",
		"faire preuve d’écoute active en essayant de comprendre le point de vue de l’autre"
	};
	private AgreementLevel establish_contacts;
	private AgreementLevel contribute_to_teamwork;
	private AgreementLevel adapt_to_company_culture;
	private AgreementLevel accept_constructive_criticism;
	private AgreementLevel be_respectful;
	private AgreementLevel be_a_good_listener;
	@Column(name = "interpersonal_skills_comment")
	private String comment;
}
