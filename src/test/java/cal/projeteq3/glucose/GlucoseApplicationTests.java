package cal.projeteq3.glucose;

import cal.projeteq3.glucose.repository.EmployerRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.service.EmployerService;
import cal.projeteq3.glucose.service.ManagerService;
import cal.projeteq3.glucose.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GlucoseApplicationTests {
	@Mock
	EmployerRepository employerRepository;
	@Mock
	StudentRepository studentRepository;
	@Mock
	ManagerRepository managerRepository;
	@Mock
	EmployerService employerService;
	@Mock
	StudentService studentService;
	@Mock
	ManagerService managerService;
	@Mock
	PasswordEncoder passwordEncoder;

	@InjectMocks
	GlucoseApplication glucoseApplication;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldCreateDatabase() {
		glucoseApplication.createDatabase();

		verify(employerRepository, times(1)).saveAll(anyList());
		verify(studentRepository, times(1)).saveAll(anyList());
		verify(managerRepository, times(1)).saveAll(anyList());
	}

	@Test
	void shouldCreateApplication() {
		glucoseApplication.createApplication();

		verify(studentService, times(2)).addCv(anyLong(), any());
		verify(managerService, times(1)).updateCvState(anyLong(), any(), any());
		verify(studentService, times(2)).applyJobOffer(anyLong(), anyLong(), anyString());
		verify(employerService, times(1)).addAppointmentByJobApplicationId(anyLong(), anySet());
	}

	@Test
	void shouldRunApplication() {
		glucoseApplication.run();

		verify(employerRepository, times(1)).saveAll(anyList());
		verify(studentRepository, times(1)).saveAll(anyList());
		verify(managerRepository, times(1)).saveAll(anyList());
		verify(studentService, times(2)).addCv(anyLong(), any());
		verify(managerService, times(1)).updateCvState(anyLong(), any(), any());
		verify(studentService, times(2)).applyJobOffer(anyLong(), anyLong(), anyString());
		verify(employerService, times(1)).addAppointmentByJobApplicationId(anyLong(), anySet());
	}

	@Test
	void contextLoads() {
	}

}
