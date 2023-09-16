package cal.projeteq3.glucose;

import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
	StudentRepository studentRepository;
	@Autowired
	ManagerRepository managerRepository;

	private void createDatabase(){
		employerRepository.saveAll(createEmployer());
		studentRepository.saveAll(createStudent());
		managerRepository.save(createManager());
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
				.password("123-456-7890")
				.matricule("0000001")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Joe")
				.lastName("Michaud")
				.email("Joe@michaud.com")
				.password("123-456-7890")
				.matricule("0000002")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Louis")
				.lastName("Michaud")
				.email("Louis@michaud.com")
				.password("123-456-7890")
				.matricule("0000003")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Chawki")
				.lastName("Michaud")
				.email("Chawki@michaud.com")
				.password("123-456-7890")
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
				.password("123-456-7890")
				.matricule("0000006")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Samuel")
				.lastName("Michaud")
				.email("Samuel@michaud.com")
				.password("123-456-7890")
				.matricule("0000007")
				.department("_420B0")
				.build(),
			Student.builder()
				.firstName("Karim")
				.lastName("Michaud")
				.email("Karim@michaud.com")
				.password("123-456-7890")
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

}
