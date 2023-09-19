package cal.projeteq3.glucose;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.service.EmployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class GlucoseApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(GlucoseApplication.class, args);
		System.out.println("Hello World!");
	}

	@Override
	public void run(String... args) throws Exception {
		createDatabase();
	}

	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private EmployerService employerService;

	private void createDatabase(){
		employerRepository.saveAll(createEmployer());
		studentRepository.saveAll(createStudent());
		managerRepository.save(createManager());
		createJobOffers(employerService);
	}

	private static List<Employer> createEmployer(){
		return List.of(
			Employer.builder()
				.firstName("Gabriel")
				.lastName("Non")
				.email("Gabriel@professionnel.com")
				.password("Ose12345")
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build(),
			Employer.builder()
				.firstName("Chawki")
				.lastName("Non")
				.email("Chawki@professionnel.com")
				.password("Ose12345")
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build(),
			Employer.builder()
				.firstName("Zakaria")
				.lastName("Non")
				.email("e@zaka.se")
				.password("aaaAAA111")
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build(),
			Employer.builder()
				.firstName("Samuel")
				.lastName("Non")
				.email("Samuel@professionnel.com")
				.password("Ose12345")
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build(),
			Employer.builder()
				.firstName("Louis")
				.lastName("Non")
				.email("Louis@professionnel.com")
				.password("Ose12345")
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build(),
			Employer.builder()
				.firstName("Jean")
				.lastName("Non")
				.email("terss@professionnel.com")
				.password("Ose12345")
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build()
				);
	}

	private static List<Student> createStudent(){
		return List.of(
			Student.builder()
				.firstName("Jean")
				.lastName("Michaud")
				.email("jean@michaud.com")
				.password("Ose12345")
				.matricule("0000001")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Joe")
				.lastName("Michaud")
				.email("Joe@michaud.com")
				.password("Ose12345")
				.matricule("0000002")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Louis")
				.lastName("Michaud")
				.email("Louis@michaud.com")
				.password("Ose12345")
				.matricule("0000003")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Chawki")
				.lastName("Michaud")
				.email("chawki@michaud.com")
				.password("Ose12345")
				.matricule("0000004")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Zakaria")
				.lastName("Michaud")
				.email("s@zaka.se")
				.password("aaaAAA111")
				.matricule("0000005")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Gabriel")
				.lastName("Michaud")
				.email("Gabriel@michaud.com")
				.password("Ose12345")
				.matricule("0000006")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Samuel")
				.lastName("Michaud")
				.email("Samuel@michaud.com")
				.password("Ose12345")
				.matricule("0000007")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Karim")
				.lastName("Michaud")
				.email("Karim@michaud.com")
				.password("Ose12345")
				.matricule("0000008")
				.department("_420B0")
				.build());
	}

	private static Manager createManager(){
		return
			Manager.builder()
				.firstName("Michel")
				.lastName("Michaud")
				.email("michel@michaud.com")
				.password("Ose12345")
				.matricule("0000001")
				.phoneNumber("123-456-7890")
				.build();
	}


	private static void createJobOffers(EmployerService employerService) {
        List<JobOffer> jobOffers = new ArrayList<>(List.of(
                JobOffer.builder()
                        .title("Front-end developer")
                        .jobOfferState(JobOfferState.SUBMITTED)
                        .department("_420B0")
                        .location("Montréal")
                        .description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
                                "l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
                                "expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
                                "collaboration avec notre équipe de développement pour contribuer au développement, à la " +
                                "maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
                        .startDate(LocalDateTime.now())
                        .duration(7)
                        .expirationDate(LocalDateTime.now().plusDays(1))
                        .hoursPerWeek(15)
                        .build(),
                JobOffer.builder()
                        .title("Back-end developer")
                        .jobOfferState(JobOfferState.TAKEN)
                        .department("_420B0")
                        .location("Montréal")
                        .description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
                                "l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
                                "expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
                                "collaboration avec notre équipe de développement pour contribuer au développement, à la " +
                                "maintenance et à l'amélioration de nos produits logiciels.")
                        .salary(20.0f)
                        .startDate(LocalDateTime.now())
                        .duration(7)
                        .expirationDate(LocalDateTime.now().plusDays(5))
                        .hoursPerWeek(13)
                        .build(),
                JobOffer.builder()
                        .title("Full-stack developer")
                        .jobOfferState(JobOfferState.OPEN)
                        .department("_420B0")
                        .location("Montréal")
                        .description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
                                "l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
                                "expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
                                "collaboration avec notre équipe de développement pour contribuer au développement, à la " +
                                "maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
                        .startDate(LocalDateTime.now())
                        .duration(7)
                        .expirationDate(LocalDateTime.now().plusDays(-5))
                        .hoursPerWeek(1)
                        .build(),
				JobOffer.builder()
						.title("Développement Web Full Stack")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department("_420B0")
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"d'applications web interactives en utilisant diverses " +
								"technologies front-end et back-end.")
						.salary(20.0f)
						.startDate(LocalDateTime.now())
						.duration(7)
						.expirationDate(LocalDateTime.now().plusDays(30))
						.hoursPerWeek(20)
						.build(),
				JobOffer.builder()
						.title("Sécurité Informatique")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department("_420B0")
						.location("Montréal")
						.description("Ce stage pourrait se concentrer sur l'analyse de la sécurité " +
								"des systèmes informatiques, la détection des vulnérabilités, et la mise en place de mesures de sécurité.")
						.salary(20.0f)
						.startDate(LocalDateTime.now())
						.duration(7)
						.expirationDate(LocalDateTime.now().plusDays(30))
						.hoursPerWeek(23)
						.build(),
				JobOffer.builder()
						.title("Intelligence Artificielle et Apprentissage Automatique")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department("_420B0")
						.location("Montréal")
						.description("Ce stage pourrait impliquer la création de modèles " +
								"d'apprentissage automatique, l'analyse de données, " +
								"et le développement d'applications basées sur l'IA.")
						.salary(20.0f)
						.startDate(LocalDateTime.now())
						.duration(7)
						.expirationDate(LocalDateTime.now().plusDays(30))
						.hoursPerWeek(25)
						.build(),
				JobOffer.builder()
						.title("Développement Mobile")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department("_420B0")
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"d'applications mobiles pour Android et iOS.")
						.salary(20.0f)
						.startDate(LocalDateTime.now())
						.duration(7)
						.expirationDate(LocalDateTime.now().plusDays(30))
						.hoursPerWeek(30)
						.build(),
				JobOffer.builder()
						.title("Développement de Jeux Vidéo")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department("_420B0")
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"de jeux vidéo pour PC et consoles.")
						.salary(20.0f)
						.startDate(LocalDateTime.now())
						.duration(7)
						.expirationDate(LocalDateTime.now().plusDays(30))
						.hoursPerWeek(40)
						.build()
        ));
		EmployerDTO employer = employerService.getEmployerByEmail("Chawki@professionnel.com");
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(0)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(1)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(2)), employer.getId());

		employer = employerService.getEmployerByEmail("Louis@professionnel.com");

		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(3)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(4)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(5)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(6)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(7)), employer.getId());}

}
