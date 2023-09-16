package cal.projeteq3.glucose;

import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.model.Manager;
import cal.projeteq3.glucose.model.Student;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class GlucoseApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(GlucoseApplication.class, args);
	}

//	Creation of the original database
	@Override
	public void run(String... args) throws Exception {
		createDatabase();
	}

	@Autowired
	private EmployerService employerService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private ManagerService managerService;

	private void createDatabase(){
		for (Employer employer: createEmployer()) {
			//employerService.createEmployer(employer);
		}

		for (Student student : createStudent()){
			//studentService.createStudent(student);
		}

		managerService.createGestionnaire(createManager());
	}

	private static List<Employer> createEmployer(){
		return List.of(
				new Employer(
						"Gabriel",
						"Non",
						"Gabriel@professionnel.com",
						"Ose12345",
						"Fritz",
						"123-456-7890",
						null),
				new Employer(
						"Chawki",
						"Non",
						"Chawki@professionnel.com",
						"Ose12345",
						"Fritz",
						"123-456-7890",
						null),
				new Employer(
						"Zakaria",
						"Non",
						"Zakaria@professionnel.com",
						"Ose12345",
						"Fritz",
						"123-456-7890",
						null),
				new Employer(
						"Samuel",
						"Non",
						"Samuel@professionnel.com",
						"Ose12345",
						"Fritz",
						"123-456-7890",
						null),
				new Employer(
						"Louis",
						"Non",
						"Louis@professionnel.com",
						"Ose12345",
						"Fritz",
						"123-456-7890",
						null),
				new Employer(
						"Jean",
						"Non",
						"t@professionnel.com",
						"Ose12345",
						"Fritz",
						"123-456-7890",
						null)
				);
	}

	private static List<Student> createStudent(){
		return List.of(
				new Student(
						"Jean",
						"Michaud",
						"jean@michaud.com",
						"123-456-7890",
						"0000001",
						"_420B0",
						null
				),
				new Student(
						"Joe",
						"Michaud",
						"Joe@michaud.com",
						"123-456-7890",
						"0000002",
						"_420B0",
						null
				),
				new Student(
						"Louis",
						"Michaud",
						"Louis@michaud.com",
						"123-456-7890",
						"0000003",
						"_420B0",
						null
				),
				new Student(
						"Chawki",
						"Michaud",
						"Chawki@michaud.com",
						"123-456-7890",
						"0000004",
						"_420B0",
						null
				),
				new Student(
						"Zakaria",
						"Michaud",
						"Zakaria@michaud.com",
						"123-456-7890",
						"0000005",
						"_420B0",
						null
				),
				new Student(
						"Gabriel",
						"Michaud",
						"Gabriel@michaud.com",
						"123-456-7890",
						"0000006",
						"_420B0",
						null
				),
				new Student(
						"Samuel",
						"Michaud",
						"Samuel@michaud.com",
						"123-456-7890",
						"0000007",
						"_420B0",
						null
				),
				new Student(
						"Karim",
						"Michaud",
						"Karim@michaud.com",
						"123-456-7890",
						"0000008",
						"_420B0",
						null
				)
				);


	}

	private static Manager createManager(){
		return new Manager(
				"Michel",
				"Michaud",
				"michel@michaud.com",
				"Ose12345",
				"0000001",
				"123-456-7890"
		);
	}

}
