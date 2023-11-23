package cal.projeteq3.glucose;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class GlucoseApplication implements CommandLineRunner {
	@Autowired
	private EmployerRepository employerRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ManagerRepository managerRepository;
	@Autowired
	private EmployerService employerService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(GlucoseApplication.class, args);
		System.out.println("Hello World!");
	}

	@Override
	public void run(String... args) throws Exception {
		studentRepository.save(Student.builder()
				.firstName("Louis")
				.lastName("Étudiant")
				.email("bonjourmichel44@gmail.com")
				.password(passwordEncoder.encode("Ose12345"))
				.matricule("0000003")
				.department("_420B0")
				.build());
		employerRepository.save(Employer.builder()
				.firstName("Louis")
				.lastName("Employeur")
				.email("glucose.professionnel@gmail.com")
				.password(passwordEncoder.encode("Ose12345"))
				.organisationName("Fritz")
				.organisationPhone("123-456-7890")
				.build());
		managerRepository.save(Manager.builder()
				.firstName("Emailer")
				.lastName("Tester")
				.email("glucose.pro@gmail.com")
				.password(passwordEncoder.encode("Ose12345"))
				.matricule("0000111")
				.phoneNumber("123-456-7890")
				.department(Department._420B0)
				.build());

		studentService.addCv(1L, createFakePDF("cv_louis_michaud.pdf"));

//		createDatabase();
//		createApplication();
	}

	private void createDatabase(){
		employerRepository.saveAll(createEmployer());
		studentRepository.saveAll(createStudent());
		managerRepository.saveAll(createManager());
		createJobOffers(employerService);
	}

	//Ajouter CV propre
	private void createApplication(){
		studentService.addCv(10L, createFakePDF("cv_chawki_michaud.pdf"));
		studentService.addCv(9L, createFakePDF("cv_louis_michaud.pdf"));

		managerService.updateCvState(1L, CvState.ACCEPTED, null);
		studentService.applyJobOffer(9L, 10L, "CoverLetter");
		studentService.applyJobOffer(10L, 10L, "CoverLetter");

		employerService.addAppointmentByJobApplicationId(1L, new HashSet<>(
				Set.of(LocalDateTime.now().plusDays(1),
						LocalDateTime.now().plusDays(2),
						LocalDateTime.now().plusDays(3))));
	}

	private List<Employer> createEmployer(){
		return List.of(
				Employer.builder()
						.firstName("Gabriel")
						.lastName("Employeur")
						.email("gabriel@professionnel.com")
						.password(passwordEncoder.encode("Ose12345"))
						.organisationName("Fritz")
						.organisationPhone("123-456-7890")
						.build(),
				Employer.builder()
						.firstName("Chawki")
						.lastName("Employeur")
						.email("chawki@professionnel.com")
						.password(passwordEncoder.encode("Ose12345"))
						.organisationName("Fritz")
						.organisationPhone("123-456-7890")
						.build(),
				Employer.builder()
						.firstName("Zakaria")
						.lastName("Employeur")
						.email("e@zaka.se")
						.password(passwordEncoder.encode("aaaAAA111"))
						.organisationName("Fritz")
						.organisationPhone("123-456-7890")
						.build(),
				Employer.builder()
						.firstName("Samuel")
						.lastName("Employeur")
						.email("samuel@professionnel.com")
						.password(passwordEncoder.encode("Ose12345"))
						.organisationName("Fritz")
						.organisationPhone("123-456-7890")
						.build(),
				Employer.builder()
						.firstName("Louis")
						.lastName("Employeur")
						.email("louis@professionnel.com")
						.password(passwordEncoder.encode("Ose12345"))
						.organisationName("Fritz")
						.organisationPhone("123-456-7890")
						.build(),
				Employer.builder()
						.firstName("Jean")
						.lastName("Employeur")
						.email("terss@professionnel.com")
						.password(passwordEncoder.encode("Ose12345"))
						.organisationName("Fritz")
						.organisationPhone("123-456-7890")
						.build()
		);
	}

	private List<Student> createStudent(){
		return List.of(
				Student.builder()
						.firstName("Jean")
						.lastName("Étudiant")
						.email("jean@michaud.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000001")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Joe")
						.lastName("Étudiant")
						.email("joe@michaud.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000002")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Louis")
						.lastName("Étudiant")
						.email("bonjourmichel44@gmail.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000003")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Chawki")
						.lastName("Étudiant")
						.email("chawki@michaud.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000004")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Zakaria")
						.lastName("Gn")
						.email("s@zaka.se")
						.password(passwordEncoder.encode("aaaAAA111"))
						.matricule("0000005")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Gabriel")
						.lastName("Étudiant")
						.email("gabriel@michaud.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000006")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Samuel")
						.lastName("Étudiant")
						.email("samuel@michaud.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000007")
						.department("_420B0")
						.build(),
				Student.builder()
						.firstName("Karim")
						.lastName("Étudiant")
						.email("karim@michaud.com")
						.password(passwordEncoder.encode("Ose12345"))
						.matricule("0000008")
						.department("_420B0")
						.build());
	}

	private List<Manager> createManager(){
		return
			List.of(
					Manager.builder()
							.firstName("Édouard")
							.lastName("Langlois-Légaré")
							.email("edouard.langlois@claurendeau.qc.ca")
							.password(passwordEncoder.encode("Ose12345"))
							.matricule("0000000")
							.phoneNumber("111-111-1111")
							.department(Department._420B0)
							.build(),
					Manager.builder()
							.firstName("Zaka")
							.lastName("Gn")
							.email("m@zaka.se")
							.password(passwordEncoder.encode("aaaAAA111"))
							.matricule("0000001")
							.phoneNumber("123-456-7890")
							.department(Department._310A0)
							.build(),
					Manager.builder()
							.firstName("Michel")
							.lastName("Michaud")
							.email("michel@michaud.com")
							.password(passwordEncoder.encode("Ose12345"))
							.matricule("0000002")
							.phoneNumber("123-456-7890")
							.department(Department._420B0)
							.build(),
					Manager.builder()
							.firstName("Emailer")
							.lastName("Tester")
							.email("patatepoilu876@gmail.com")
							.password(passwordEncoder.encode("Ose12345"))
							.matricule("0000111")
							.phoneNumber("123-456-7890")
							.department(Department._420B0)
							.build()
		);
	}


	private void createJobOffers(EmployerService employerService) {
		List<JobOffer> jobOffers = new ArrayList<>(List.of(
				JobOffer.builder()
						.title("Front-end developer")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._420B0)
						.location("Montréal")
						.description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
								"l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
								"expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
								"collaboration avec notre équipe de développement pour contribuer au développement, à la " +
								"maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(1))
						.hoursPerWeek(15)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Back-end developer")
						.jobOfferState(JobOfferState.TAKEN)
						.department(Department._420B0)
						.location("Montréal")
						.description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
								"l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
								"expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
								"collaboration avec notre équipe de développement pour contribuer au développement, à la " +
								"maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(5))
						.hoursPerWeek(13)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Développement de Jeux Vidéo")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"de jeux vidéo pour PC et consoles.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(40)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Gestion de feu de circulation")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._310A0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la gestion de la circulation " +
								"et la coordination des interventions d'urgence.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(40)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Techniques de santé animale")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._145A0)
						.location("Montréal")
						.description("Prendre soin des animaux et les aider à se rétablir " +
								"de leurs maladies ou blessures.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(40)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Front-end developer")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._420B0)
						.location("Montréal")
						.description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
								"l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
								"expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
								"collaboration avec notre équipe de développement pour contribuer au développement, à la " +
								"maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(1))
						.hoursPerWeek(15)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Back-end developer")
						.jobOfferState(JobOfferState.TAKEN)
						.department(Department._420B0)
						.location("Montréal")
						.description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
								"l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
								"expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
								"collaboration avec notre équipe de développement pour contribuer au développement, à la " +
								"maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(5))
						.hoursPerWeek(13)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Java developer")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._144A1)
						.location("Montréal")
						.description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
								"l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
								"expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
								"collaboration avec notre équipe de développement pour contribuer au développement, à la " +
								"maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(5))
						.hoursPerWeek(1)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Développement Web Full Stack")
						.jobOfferState(JobOfferState.OPEN)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"d'applications web interactives en utilisant diverses " +
								"technologies front-end et back-end.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(20)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Sécurité Informatique")
						.jobOfferState(JobOfferState.OPEN)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait se concentrer sur l'analyse de la sécurité " +
								"des systèmes informatiques, la détection des vulnérabilités, et la mise en place de mesures de sécurité.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(23)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Intelligence Artificielle et Apprentissage Automatique")
						.jobOfferState(JobOfferState.OPEN)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la création de modèles " +
								"d'apprentissage automatique, l'analyse de données, " +
								"et le développement d'applications basées sur l'IA.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(25)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Développement Mobile")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"d'applications mobiles pour Android et iOS.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(30)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Développement de Jeux Vidéo")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"de jeux vidéo pour PC et consoles.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(40)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Gestion de feu de circulation")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._310A0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la gestion de la circulation " +
								"et la coordination des interventions d'urgence.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(40)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Techniques de santé animale")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._145A0)
						.location("Montréal")
						.description("Prendre soin des animaux et les aider à se rétablir " +
								"de leurs maladies ou blessures.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(40)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),

//				Louis@professionnel
				JobOffer.builder()
						.title("Full-stack developer")
						.jobOfferState(JobOfferState.OPEN)
						.department(Department._420B0)
						.location("Montréal")
						.description("En tant que stagiaire en développement logiciel au sein de notre entreprise, vous aurez " +
								"l'opportunité de participer à des projets passionnants et innovants tout en acquérant une " +
								"expérience précieuse dans le domaine du développement logiciel. Vous travaillerez en étroite " +
								"collaboration avec notre équipe de développement pour contribuer au développement, à la " +
								"maintenance et à l'amélioration de nos produits logiciels.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(5))
						.hoursPerWeek(1)
						.nbOfCandidates(1)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build(),
				JobOffer.builder()
						.title("Développement Web Full Stack")
						.jobOfferState(JobOfferState.SUBMITTED)
						.department(Department._420B0)
						.location("Montréal")
						.description("Ce stage pourrait impliquer la conception et le développement " +
								"d'applications web interactives en utilisant diverses " +
								"technologies front-end et back-end.")
						.salary(20.0f)
						.startDate(LocalDate.of(2024, 2, 10))
						.duration(7)
						.expirationDate(LocalDate.now().plusDays(30))
						.hoursPerWeek(20)
						.nbOfCandidates(2)
						.semester(new Semester(LocalDate.of(2024, 2, 10)))
						.build()
				));
		EmployerDTO employer = employerService.getEmployerByEmail("chawki@professionnel.com");
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(0)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(1)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(2)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(3)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(4)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(5)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(6)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(7)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(8)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(9)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(10)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(11)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(12)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(13)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(14)), employer.getId());

		employer = employerService.getEmployerByEmail("louis@professionnel.com");

		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(15)), employer.getId());
		employerService.createJobOffer(new JobOfferDTO(jobOffers.get(16)), employer.getId());
	}

	private CvFileDTO createFakePDF(String fileName) {
		CvFileDTO hardCodedPDF = new CvFileDTO();
		hardCodedPDF.setFileName(fileName);// Decode the Base64 string into a byte array
		String base64Data = "JVBERi0xLjcNCiW1tbW1DQoxIDAgb2JqDQo8PC9UeXBlL0NhdGFsb2cvUGFnZXMgMiAwIFIvTGFuZyhmcikgL1N0cnVjdFRyZWVSb290IDE1IDAgUi9NYXJrSW5mbzw8L01hcmtlZCB0cnVlPj4vTWV0YWRhdGEgMjcgMCBSL1ZpZXdlclByZWZlcmVuY2VzIDI4IDAgUj4+DQplbmRvYmoNCjIgMCBvYmoNCjw8L1R5cGUvUGFnZXMvQ291bnQgMS9LaWRzWyAzIDAgUl0gPj4NCmVuZG9iag0KMyAwIG9iag0KPDwvVHlwZS9QYWdlL1BhcmVudCAyIDAgUi9SZXNvdXJjZXM8PC9Gb250PDwvRjEgNSAwIFIvRjIgMTIgMCBSPj4vRXh0R1N0YXRlPDwvR1MxMCAxMCAwIFIvR1MxMSAxMSAwIFI+Pi9Qcm9jU2V0Wy9QREYvVGV4dC9JbWFnZUIvSW1hZ2VDL0ltYWdlSV0gPj4vTWVkaWFCb3hbIDAgMCA2MTIgNzkyXSAvQ29udGVudHMgNCAwIFIvR3JvdXA8PC9UeXBlL0dyb3VwL1MvVHJhbnNwYXJlbmN5L0NTL0RldmljZVJHQj4+L1RhYnMvUy9TdHJ1Y3RQYXJlbnRzIDA+Pg0KZW5kb2JqDQo0IDAgb2JqDQo8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvTGVuZ3RoIDE3OT4+DQpzdHJlYW0NCnicjY5BC4JAEIXvC/sf3lGDdmdEUkGEXE2KOoQLHaSDRHpKyv4/tFkXoUNzGHjMY74Pur63A9JUH8y2AOl9O/TwunFp1n6WIS8MHlKQovckHICwcjtKAoxXKU4LDFLkVgq9YTArCmE7Kdj1CIyIVByEiJhU5C4316tqJvRP9xT9J/I3VlLAkZgY9tKkxEzEhrIz7E6K0kGOUvxrE/ywiUmFc5vJYSI3HvwZCOXB4AWHSzi4DQplbmRzdHJlYW0NCmVuZG9iag0KNSAwIG9iag0KPDwvVHlwZS9Gb250L1N1YnR5cGUvVHlwZTAvQmFzZUZvbnQvQkNERUVFK0NhbGlicmkvRW5jb2RpbmcvSWRlbnRpdHktSC9EZXNjZW5kYW50Rm9udHMgNiAwIFIvVG9Vbmljb2RlIDIzIDAgUj4+DQplbmRvYmoNCjYgMCBvYmoNClsgNyAwIFJdIA0KZW5kb2JqDQo3IDAgb2JqDQo8PC9CYXNlRm9udC9CQ0RFRUUrQ2FsaWJyaS9TdWJ0eXBlL0NJREZvbnRUeXBlMi9UeXBlL0ZvbnQvQ0lEVG9HSURNYXAvSWRlbnRpdHkvRFcgMTAwMC9DSURTeXN0ZW1JbmZvIDggMCBSL0ZvbnREZXNjcmlwdG9yIDkgMCBSL1cgMjUgMCBSPj4NCmVuZG9iag0KOCAwIG9iag0KPDwvT3JkZXJpbmcoSWRlbnRpdHkpIC9SZWdpc3RyeShBZG9iZSkgL1N1cHBsZW1lbnQgMD4+DQplbmRvYmoNCjkgMCBvYmoNCjw8L1R5cGUvRm9udERlc2NyaXB0b3IvRm9udE5hbWUvQkNERUVFK0NhbGlicmkvRmxhZ3MgMzIvSXRhbGljQW5nbGUgMC9Bc2NlbnQgNzUwL0Rlc2NlbnQgLTI1MC9DYXBIZWlnaHQgNzUwL0F2Z1dpZHRoIDUyMS9NYXhXaWR0aCAxNzQzL0ZvbnRXZWlnaHQgNDAwL1hIZWlnaHQgMjUwL1N0ZW1WIDUyL0ZvbnRCQm94WyAtNTAzIC0yNTAgMTI0MCA3NTBdIC9Gb250RmlsZTIgMjQgMCBSPj4NCmVuZG9iag0KMTAgMCBvYmoNCjw8L1R5cGUvRXh0R1N0YXRlL0JNL05vcm1hbC9jYSAxPj4NCmVuZG9iag0KMTEgMCBvYmoNCjw8L1R5cGUvRXh0R1N0YXRlL0JNL05vcm1hbC9DQSAxPj4NCmVuZG9iag0KMTIgMCBvYmoNCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1RydWVUeXBlL05hbWUvRjIvQmFzZUZvbnQvQkNERkVFK0NhbGlicmkvRW5jb2RpbmcvV2luQW5zaUVuY29kaW5nL0ZvbnREZXNjcmlwdG9yIDEzIDAgUi9GaXJzdENoYXIgMzIvTGFzdENoYXIgMzIvV2lkdGhzIDI2IDAgUj4+DQplbmRvYmoNCjEzIDAgb2JqDQo8PC9UeXBlL0ZvbnREZXNjcmlwdG9yL0ZvbnROYW1lL0JDREZFRStDYWxpYnJpL0ZsYWdzIDMyL0l0YWxpY0FuZ2xlIDAvQXNjZW50IDc1MC9EZXNjZW50IC0yNTAvQ2FwSGVpZ2h0IDc1MC9BdmdXaWR0aCA1MjEvTWF4V2lkdGggMTc0My9G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OmNyZWF0b3I+PHJkZjpTZXE+PHJkZjpsaT5DaGF3a2kgQmVuZGFobWFuZTwvcmRmOmxpPjwvcmRmOlNlcT48L2RjOmNyZWF0b3I+PC9yZGY6RGVzY3JpcHRpb24+CjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiICB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iPgo8eG1wOkNyZWF0b3JUb29sPk1pY3Jvc29mdMKuIFdvcmQgcG91ciBNaWNyb3NvZnTCoDM2NTwveG1wOkNyZWF0b3JUb29sPjx4bXA6Q3JlYXRlRGF0ZT4yMDIzLTEwLTI0VDE0OjQ4OjUzLTA0OjAwPC94bXA6Q3JlYXRlRGF0ZT48eG1wOk1vZGlmeURhdGU+MjAyMy0xMC0yNFQxNDo0ODo1My0wNDowMDwveG1wOk1vZGlmeURhdGU+PC9yZGY6RGVzY3JpcHRpb24+CjxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiICB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyI+Cjx4bXBNTTpEb2N1bWVudElEPnV1aWQ6MjJEQ0FGNkUtMEJERS00QzU2LTgwNkUtRjNEOUUxQzk5ODUyPC94bXBNTTpEb2N1bWVudElEPjx4bXBNTTpJbnN0YW5jZUlEPnV1aWQ6MjJEQ0FGNkUtMEJERS00QzU2LTgwNkUtRjNEOUUxQzk5ODUyPC94bXBNTTpJbnN0YW5jZUlEPjwvcmRmOkRlc2NyaXB0aW9uPgogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIAogICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgCiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAKPC9yZGY6UkRGPjwveDp4bXBtZXRhPjw/eHBhY2tldCBlbmQ9InciPz4NCmVuZHN0cmVhbQ0KZW5kb2JqDQoyOCAwIG9iag0KPDwvRGlzcGxheURvY1RpdGxlIHRydWU+Pg0KZW5kb2JqDQoyOSAwIG9iag0KPDwvVHlwZS9YUmVmL1NpemUgMjkvV1sgMSA0IDJdIC9Sb290IDEgMCBSL0luZm8gMTQgMCBSL0lEWzw2RUFGREMyMkRFMEI1NjRDODA2RUYzRDlFMUM5OTg1Mj48NkVBRkRDMjJERTBCNTY0QzgwNkVGM0Q5RTFDOTk4NTI+XSAvRmlsdGVyL0ZsYXRlRGVjb2RlL0xlbmd0aCAxMTE+Pg0Kc3RyZWFtDQp4nGNgAIL//xmBpCADA4haDKFugynGj2CK6R2YYi6AUD1gikUTQhWBKdZECDUdQp0FU2xlYIo9DWgE0EwxBiYIxQyhWCAUK4RihFBQlWxAfRytYO1c4mCK2wtMxeyCUD/BVKwImMpsYGAAAK7TD2wNCmVuZHN0cmVhbQ0KZW5kb2JqDQp4cmVmDQowIDMwDQowMDAwMDAwMDE1IDY1NTM1IGYNCjAwMDAwMDAwMTcgMDAwMDAgbg0KMDAwMDAwMDE2MyAwMDAwMCBuDQowMDAwMDAwMjE5IDAwMDAwIG4NCjAwMDAwMDA0OTcgMDAwMDAgbg0KMDAwMDAwMDc1MCAwMDAwMCBuDQowMDAwMDAwODgwIDAwMDAwIG4NCjAwMDAwMDA5MDggMDAwMDAgbg0KMDAwMDAwMTA2NSAwMDAwMCBuDQowMDAwMDAxMTM4IDAwMDAwIG4NCjAwMDAwMDEzNzcgMDAwMDAgbg0KMDAwMDAwMTQzMSAwMDAwMCBuDQowMDAwMDAxNDg1IDAwMDAwIG4NCjAwMDAwMDE2NTQgMDAwMDAgbg0KMDAwMDAwMTg5NCAwMDAwMCBuDQowMDAwMDAwMDE2IDY1NTM1IGYNCjAwMDAwMDAwMTcgNjU1MzUgZg0KMDAwMDAwMDAxOCA2NTUzNSBmDQowMDAwMDAwMDE5IDY1NTM1IGYNCjAwMDAwMDAwMjAgNjU1MzUgZg0KMDAwMDAwMDAyMSA2NTUzNSBmDQowMDAwMDAwMDIyIDY1NTM1IGYNCjAwMDAwMDAwMDAgNjU1MzUgZg0KMDAwMDAwMjU4MyAwMDAwMCBuDQowMDAwMDAyODkwIDAwMDAwIG4NCjAwMDAwMjM3MzggMDAwMDAgbg0KMDAwMDAyMzgwMSAwMDAwMCBuDQowMDAwMDIzODI4IDAwMDAwIG4NCjAwMDAwMjcwMDggMDAwMDAgbg0KMDAwMDAyNzA1MyAwMDAwMCBuDQp0cmFpbGVyDQo8PC9TaXplIDMwL1Jvb3QgMSAwIFIvSW5mbyAxNCAwIFIvSURbPDZFQUZEQzIyREUwQjU2NEM4MDZFRjNEOUUxQzk5ODUyPjw2RUFGREMyMkRFMEI1NjRDODA2RUYzRDlFMUM5OTg1Mj5dID4+DQpzdGFydHhyZWYNCjI3MzY1DQolJUVPRg0KeHJlZg0KMCAwDQp0cmFpbGVyDQo8PC9TaXplIDMwL1Jvb3QgMSAwIFIvSW5mbyAxNCAwIFIvSURbPDZFQUZEQzIyREUwQjU2NEM4MDZFRjNEOUUxQzk5ODUyPjw2RUFGREMyMkRFMEI1NjRDODA2RUYzRDlFMUM5OTg1Mj5dIC9QcmV2IDI3MzY1L1hSZWZTdG0gMjcwNTM+Pg0Kc3RhcnR4cmVmDQoyODEyMg0KJSVFT0Y=";
		byte[] binaryData = Base64.getDecoder().decode(base64Data);
		hardCodedPDF.setFileData(binaryData);
		return hardCodedPDF;
	}
}