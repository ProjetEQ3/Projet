package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.evaluation.WorkEnvironmentDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.*;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.auth.Role;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.Signature;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation.WorkEnvironmentEvaluation;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ManagerServiceTest{
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private StudentRepository studentRepository;
	@Mock
	private JobOfferRepository jobOfferRepository;
	@Mock
	private ContractRepository contractRepository;
	@Mock
	private JobApplicationRepository jobApplicationRepository;
	@Mock
	private WorkEnvironmentEvaluationRepository workEnvironmentEvaluationRepository;
	@Mock
	private CvFileRepository cvRepository;

	@InjectMocks
	private ManagerService managerService;
	@Mock
	private SignatureRepository signatureRepository;

	private Department testDepartment;
	private List<Student> testStudents;

	@BeforeEach
	void setUp(){
		testDepartment = Department._420B0;
		Student student1 = new Student();
		Student student2 = new Student();
		testStudents = Arrays.asList(student1, student2);
	}

	@Test
	void createManager_valid(){
		// Arrange
		ManagerDTO managerDTO = new ManagerDTO();
		managerDTO.setFirstName("John");
		managerDTO.setLastName("Doe");
		managerDTO.setEmail("john@example.com");
		managerDTO.setDepartment("_420B0");

		when(managerRepository.save(any())).thenReturn(managerDTO.toEntity());

		// Act
		ManagerDTO createdManagerDTO = managerService.createManager(managerDTO);

		// Assert
		assertNotNull(createdManagerDTO);
		assertEquals(managerDTO.getFirstName(), createdManagerDTO.getFirstName());
		assertEquals(managerDTO.getLastName(), createdManagerDTO.getLastName());
		assertEquals(managerDTO.getEmail(), createdManagerDTO.getEmail());
		verify(managerRepository, times(1)).save(any());
	}

	@Test
	void getAllManagers_valid(){
		// Arrange
		Manager manager = Manager.builder().department(testDepartment).firstName("John").lastName("Doe").email("manager@test.com").build();

		when(managerRepository.findAll()).thenReturn(Collections.singletonList(manager));

		// Act
		List<ManagerDTO> managerDTOs = managerService.getAllManagers();

		// Assert
		assertNotNull(managerDTOs);
		assertFalse(managerDTOs.isEmpty());
		ManagerDTO managerDTO = managerDTOs.get(0);
		assertEquals(manager.getFirstName(), managerDTO.getFirstName());
		assertEquals(manager.getLastName(), managerDTO.getLastName());
		assertEquals(manager.getEmail(), managerDTO.getEmail());
		verify(managerRepository, times(1)).findAll();
	}

	@Test
	void getManagerByID_managerNotFound(){
		// Arrange
		Long id = 1L;

		when(managerRepository.findById(id)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(UserNotFoundException.class, () -> managerService.getManagerByID(id));
		verify(managerRepository, times(1)).findById(id);
	}

	@Test
	void getManagerByID_valid(){
		// Arrange
		Long id = 1L;
		Manager manager = Manager.builder().department(testDepartment).firstName("John").lastName("Doe").email("Exemple@email.com").build();

		when(managerRepository.findById(id)).thenReturn(Optional.of(manager));

		// Act
		ManagerDTO managerDTO = managerService.getManagerByID(id);

		// Assert
		assertNotNull(managerDTO);
		assertEquals(manager.getId(), managerDTO.getId());
		assertEquals(manager.getFirstName(), managerDTO.getFirstName());
		assertEquals(manager.getLastName(), managerDTO.getLastName());
		assertEquals(manager.getEmail(), managerDTO.getEmail());
		verify(managerRepository, times(1)).findById(id);
	}

	@Test
	void updateManager_valid(){
		// Arrange
		Long id = 1L;
		ManagerDTO updatedManager = new ManagerDTO();
		updatedManager.setFirstName("UpdatedFirstName");
		updatedManager.setLastName("UpdatedLastName");
		updatedManager.setEmail("updated@example.com");

		Manager existingManager = Manager.builder().department(testDepartment).id(1L).firstName("John").lastName("Doe").email("john@example.com")
		                                 .build();

		when(managerRepository.findById(id)).thenReturn(Optional.of(existingManager));
		when(managerRepository.save(any(Manager.class))).thenReturn(existingManager);

		// Act
		ManagerDTO updatedDTO = managerService.updateManager(id, updatedManager);

		// Assert
		assertNotNull(updatedDTO);
		assertEquals(updatedManager.getFirstName(), updatedDTO.getFirstName());
		assertEquals(updatedManager.getLastName(), updatedDTO.getLastName());
		assertEquals(updatedManager.getEmail(), updatedDTO.getEmail());
		verify(managerRepository, times(1)).findById(id);
		verify(managerRepository, times(1)).save(any(Manager.class));
	}

	@Test
	void updateManager_managerNotFound(){
		// Arrange
		Long id = 1L;
		ManagerDTO updatedManager = new ManagerDTO();
		updatedManager.setFirstName("UpdatedFirstName");
		updatedManager.setLastName("UpdatedLastName");
		updatedManager.setEmail("updated@example.com");

		when(managerRepository.findById(id)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(ManagerNotFoundException.class, () -> managerService.updateManager(id, updatedManager));
		verify(managerRepository, times(1)).findById(id);
	}

	@Test
	void deleteManager_valid(){
		// Arrange
		Long id = 1L;

		// Act
		managerService.deleteManager(id);

		// Assert
		verify(managerRepository, times(1)).deleteById(id);
	}

	//    -------------- CV File --------------

	@Test
	void getCvById_valid(){
		// Arrange
		Long id = 1L;
		CvFile cvFile = createCvFile(id, "1234567");

		when(cvRepository.findById(id)).thenReturn(Optional.of(cvFile));

		// Act
		CvFileDTO cvFileDTO = managerService.getCvById(id);

		// Assert
		assertNotNull(cvFileDTO);
		assertEquals(cvFile.getId(), cvFileDTO.getId());
		assertEquals(cvFile.getFileName(), cvFileDTO.getFileName());

		verify(cvRepository, times(1)).findById(id);
	}

	@Test
	void getAllCvs_valid(){
		// Arrange
		List<CvFile> cvFiles = new ArrayList<>();
		cvFiles.add(createCvFile(1L, "1234567"));
		cvFiles.add(createCvFile(2L, "2345678"));

		when(cvRepository.findAll()).thenReturn(cvFiles);

		// Act
		List<CvFileDTO> cvFileDTOs = managerService.getAllCv(testDepartment);

		// Assert
		assertNotNull(cvFileDTOs);
		assertEquals(2, cvFileDTOs.size());
		verify(cvRepository, times(1)).findAll();
	}

	@Test
	void getAllSubmittedCv_valid(){
		Student student1 = Student.builder().department("_420B0").matricule("1234567").build();
		Student student2 = Student.builder().department("_420B0").matricule("1234561").build();
		Student student3 = Student.builder().department("_420B0").matricule("1234562").build();
		Student student4 = Student.builder().department("_420B0").matricule("1234563").build();
		//        Arrange
        List<CvFile> cvFiles = new ArrayList<>(List.of(
				CvFile.builder().student(student1).fileName("cv1.pdf").fileData(new byte[]{}).cvState(CvState.SUBMITTED)
                        .build(),
				CvFile.builder().student(student2).fileName("cv1.pdf").fileData(new byte[]{}).cvState(CvState.SUBMITTED)
						.build(),
				CvFile.builder().student(student3).fileName("cv1.pdf").fileData(new byte[]{}).cvState(CvState.REFUSED)
						.build(),
                CvFile.builder().student(student4).fileName("cv1.pdf").fileData(new byte[]{}).cvState(CvState.ACCEPTED)
						.build()));

		when(cvRepository.findAllByCvState(CvState.SUBMITTED)).thenReturn(
			cvFiles.stream().filter(cvFile -> cvFile.getCvState() == CvState.SUBMITTED).toList());

		//        Act
		List<CvFileDTO> cvFileDTOs = managerService.getSubmittedCv(testDepartment);

		//        Assert
		assertNotNull(cvFileDTOs);
		assertEquals(2, cvFileDTOs.size());
		verify(cvRepository, times(1)).findAllByCvState(CvState.SUBMITTED);
	}

	@Test
	void getAllCvFileByStudent_valid(){
		// Arrange
		Long studentId = 1L;

		List<CvFile> cvFiles = new ArrayList<>();
		cvFiles.add(createCvFile(1L, studentId));
		cvFiles.add(createCvFile(2L, studentId));

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(new Student()));
		when(cvRepository.findAllByStudent(any())).thenReturn(cvFiles);

		// Act
		List<CvFileDTO> cvFileDTOs = managerService.getAllCvFileByStudent(studentId);

		// Assert
		assertNotNull(cvFileDTOs);
		assertEquals(2, cvFileDTOs.size());
		verify(studentRepository, times(1)).findById(studentId);
		verify(cvRepository, times(1)).findAllByStudent(any());
	}

	@Test
	void getAllCvFileByStudentMatricule_valid(){
		// Arrange
		String matricule = "1234567";

		List<CvFile> cvFiles = new ArrayList<>();
		cvFiles.add(createCvFile(1L, matricule));
		cvFiles.add(createCvFile(2L, matricule));

		when(studentRepository.findByMatricule(matricule)).thenReturn(new Student());
		when(cvRepository.findAllByStudent(any())).thenReturn(cvFiles);

		// Act
		List<CvFileDTO> cvFileDTOs = managerService.getAllCvFileByStudentMatricule(matricule);

		// Assert
		assertNotNull(cvFileDTOs);
		assertEquals(2, cvFileDTOs.size());
		verify(studentRepository, times(1)).findByMatricule(matricule);
		verify(cvRepository, times(1)).findAllByStudent(any());
	}

	@Test
	void getCvFileByStudentAndFileName_valid(){
		// Arrange
		String matricule = "1234567";
		String fileName = "example_cv.pdf";

		CvFile cvFile = createCvFile(1L, matricule);
		cvFile.setFileName(fileName);

		when(studentRepository.findByMatricule(matricule)).thenReturn(new Student());
		when(cvRepository.findByStudentAndFileName(any(), any())).thenReturn(cvFile);

		// Act
		CvFileDTO cvFileDTO = managerService.getCvFileByStudentAndFileName(matricule, fileName);

		// Assert
		assertNotNull(cvFileDTO);
		assertEquals(fileName, cvFileDTO.getFileName());
		verify(studentRepository, times(1)).findByMatricule(matricule);
		verify(cvRepository, times(1)).findByStudentAndFileName(any(), any());
	}

	@Test
	void deleteCvFile_valid(){
		// Arrange
		Long cvFileId = 1L;

		// Act
		managerService.deleteCvFile(cvFileId);

		// Assert
		verify(cvRepository, times(1)).deleteById(cvFileId);
	}

	@Test
	void deleteAllCvFileByStudentMatricule_valid(){
		// Arrange
		String matricule = "1234567";

		// Act
		managerService.deleteAllCvFileByStudentMatricule(matricule);

		// Assert
		verify(cvRepository, times(1)).deleteAllByStudent(any());
	}

	@Test
	void getCvFilesWithState_valid(){
		// Arrange
		CvState state = CvState.SUBMITTED;

		List<CvFile> cvFiles = new ArrayList<>();
		cvFiles.add(createCvFile(1L, "1234567"));
		cvFiles.add(createCvFile(2L, "2345678"));

		when(cvRepository.findAllByCvState(state)).thenReturn(cvFiles);

		// Act
		List<CvFileDTO> cvFileDTOs = managerService.getCvFilesWithState(state, testDepartment);

		// Assert
		assertNotNull(cvFileDTOs);
		assertEquals(2, cvFileDTOs.size());
		verify(cvRepository, times(1)).findAllByCvState(state);
	}

	@Test
	void updateCvState_valid(){
		// Arrange
		Long id = 1L;
		CvState newState = CvState.ACCEPTED;

		CvFile cvFile = createCvFile(id, "1234567");

		when(cvRepository.findById(id)).thenReturn(Optional.of(cvFile));
		when(cvRepository.save(any())).thenReturn(cvFile);

		// Act
		CvFileDTO cvFileDTO = managerService.updateCvState(id, newState, null);

		// Assert
		assertNotNull(cvFileDTO);
		assertEquals(cvFile.getId(), cvFileDTO.getId());
		assertEquals(cvFile.getFileName(), cvFileDTO.getFileName());
		assertEquals(newState, cvFile.getCvState());
		assertNull(cvFile.getRefusReason());

		verify(cvRepository, times(1)).findById(id);
		verify(cvRepository, times(1)).save(any());
	}

	@Test
	void updateCvState_cvFileNotFound(){
		// Arrange
		Long id = 1L;
		CvState newState = CvState.ACCEPTED;
		String reason = "Approved";

		when(cvRepository.findById(id)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(CvFileNotFoundException.class, () -> managerService.updateCvState(id, newState, reason));
		verify(cvRepository, times(1)).findById(id);
	}

	//    -------------- Job Offer --------------

	@Test
	void getAllJobOffer_valid(){
		//        Arrange
		Semester semester = new Semester(LocalDate.now());
		List<JobOffer> jobOffers = new ArrayList<>();
		jobOffers.add(JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(JobOfferState.OPEN)
		                      .semester(semester).build());
		jobOffers.add(JobOffer.builder().department(testDepartment).title("Job Offer 2").description("Description 2").jobOfferState(JobOfferState.OPEN)
		                      .semester(semester).build());

		when(jobOfferRepository.findAllBySemester(semester)).thenReturn(jobOffers);

		//        Act
		List<JobOfferDTO> jobOfferDTOs = managerService.getAllJobOffer(semester, testDepartment);

		//        Assert
		assertNotNull(jobOfferDTOs);
		assertEquals(2, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findAllBySemester(semester);
	}

	@Test
	void getJobOfferByID_valid(){
		//        Arrange
		Long id = 1L;
		JobOffer jobOffer = JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
			JobOfferState.OPEN).build();

		when(jobOfferRepository.findById(id)).thenReturn(Optional.of(jobOffer));

		//        Act
		JobOfferDTO jobOfferDTO = managerService.getJobOfferByID(id);

		//        Assert
		assertNotNull(jobOfferDTO);
		assertEquals(jobOffer.getId(), jobOfferDTO.getId());
		assertEquals(jobOffer.getTitle(), jobOfferDTO.getTitle());
		assertEquals(jobOffer.getDescription(), jobOfferDTO.getDescription());
		assertEquals(jobOffer.getJobOfferState(), jobOfferDTO.getJobOfferState());
		verify(jobOfferRepository, times(1)).findById(id);
	}

	@Test
	void getJobOfferByID_jobOfferNotFound(){
		//        Arrange
		Long id = 1L;

		when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());

		//        Act and Assert
		assertThrows(JobOfferNotFoundException.class, () -> managerService.getJobOfferByID(id));
		verify(jobOfferRepository, times(1)).findById(id);
	}

	@Test
	void getJobOffersWithState_Submitted(){
		//        Arrange
		Semester semester = new Semester(LocalDate.now());
        List<JobOffer> jobOffers = new ArrayList<>(List.of(JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
                        JobOfferState.SUBMITTED).semester(semester).build(), JobOffer.builder().department(testDepartment).title("Job Offer 2").description(
                        "Description 2").jobOfferState(JobOfferState.REFUSED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title(
                        "Job Offer 3").description("Description 3").jobOfferState(JobOfferState.OPEN).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 4").description("Description 4").jobOfferState(
                        JobOfferState.PENDING).semester(semester).build(), JobOffer.builder().department(testDepartment).title(
                        "Job Offer 5").description("Description 5").jobOfferState(JobOfferState.EXPIRED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 6").description("Description 6").jobOfferState(
                        JobOfferState.TAKEN).semester(semester).build()
        ));

		when(jobOfferRepository.findJobOfferByJobOfferStateAndSemester(JobOfferState.SUBMITTED, semester)).thenReturn(
			jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.SUBMITTED).toList());

		//        Act

		List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.SUBMITTED, semester, testDepartment);
		//        Assert

		assertNotNull(jobOfferDTOs);
		assertEquals(1, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findJobOfferByJobOfferStateAndSemester(JobOfferState.SUBMITTED, semester);

	}

	@Test
	void getJobOffersWithState_Open(){
		//        Arrange

		Semester semester = new Semester(LocalDate.now());
        List<JobOffer> jobOffers = new ArrayList<>(List.of(JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
                        JobOfferState.SUBMITTED).semester(semester).build(), JobOffer.builder().department(testDepartment).title("Job Offer 2").description(
                        "Description 2").jobOfferState(JobOfferState.REFUSED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title(
                        "Job Offer 3").description("Description 3").jobOfferState(JobOfferState.OPEN).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 4").description("Description 4").jobOfferState(
                        JobOfferState.PENDING).semester(semester).build(), JobOffer.builder().department(testDepartment).title(
                        "Job Offer 5").description("Description 5").jobOfferState(JobOfferState.EXPIRED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 6").description("Description 6").jobOfferState(
                        JobOfferState.TAKEN).semester(semester).build()
        ));

		when(jobOfferRepository.findJobOfferByJobOfferStateAndSemester(JobOfferState.OPEN, semester)).thenReturn(
			jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.OPEN).toList());

		//        Act

		List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.OPEN, semester, testDepartment);
		//        Assert

		assertNotNull(jobOfferDTOs);
		assertEquals(1, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findJobOfferByJobOfferStateAndSemester(JobOfferState.OPEN, semester);

	}

	@Test
	void getJobOffersWithState_Pending(){
		//        Arrange
		Semester semester = new Semester(LocalDate.now());
        List<JobOffer> jobOffers = new ArrayList<>(List.of(JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
                        JobOfferState.SUBMITTED).semester(semester).build(), JobOffer.builder().department(testDepartment).title("Job Offer 2").description(
                        "Description 2").jobOfferState(JobOfferState.REFUSED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title(
                        "Job Offer 3").description("Description 3").jobOfferState(JobOfferState.OPEN).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 4").description("Description 4").jobOfferState(
                        JobOfferState.PENDING).semester(semester).build(), JobOffer.builder().department(testDepartment).title(
                        "Job Offer 5").description("Description 5").jobOfferState(JobOfferState.EXPIRED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 6").description("Description 6").jobOfferState(
                        JobOfferState.TAKEN).semester(semester).build()
        ));

		when(jobOfferRepository.findJobOfferByJobOfferStateAndSemester(JobOfferState.PENDING, semester)).thenReturn(
			jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.PENDING).toList());

		//        Act

		List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.PENDING, semester, testDepartment);
		//        Assert

		assertNotNull(jobOfferDTOs);
		assertEquals(1, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findJobOfferByJobOfferStateAndSemester(JobOfferState.PENDING, semester);

	}

	@Test
	void getJobOffersWithState_Expired(){
		//        Arrange
		Semester semester = new Semester(LocalDate.now());
        List<JobOffer> jobOffers = new ArrayList<>(List.of(JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
                        JobOfferState.SUBMITTED).semester(semester).build(), JobOffer.builder().department(testDepartment).title("Job Offer 2").description(
                        "Description 2").jobOfferState(JobOfferState.REFUSED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title(
                        "Job Offer 3").description("Description 3").jobOfferState(JobOfferState.OPEN).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 4").description("Description 4").jobOfferState(
                        JobOfferState.PENDING).semester(semester).build(), JobOffer.builder().department(testDepartment).title(
                        "Job Offer 5").description("Description 5").jobOfferState(JobOfferState.EXPIRED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 6").description("Description 6").jobOfferState(
                        JobOfferState.TAKEN).semester(semester).build()
        ));

		when(jobOfferRepository.findJobOfferByJobOfferStateAndSemester(JobOfferState.EXPIRED, semester)).thenReturn(
			jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.EXPIRED).toList());

		//        Act

		List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.EXPIRED, semester, testDepartment);
		//        Assert

		assertNotNull(jobOfferDTOs);
		assertEquals(1, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findJobOfferByJobOfferStateAndSemester(JobOfferState.EXPIRED, semester);

	}

	@Test
	void getJobOffersWithState_Taken(){
		//        Arrange

		Semester semester = new Semester(LocalDate.now());
		List<JobOffer> jobOffers = new ArrayList<>(List.of(JobOffer.builder().department(testDepartment).title("Job Offer 1").description(
			                                                   "Description 1").jobOfferState(JobOfferState.SUBMITTED).semester(semester).build(), JobOffer.builder().department(testDepartment).title(
			                                                   "Job Offer 2").description("Description 2").jobOfferState(JobOfferState.REFUSED).semester(semester).build(),
		                                                   JobOffer.builder().department(testDepartment).title("Job Offer 3").description(
			                                                           "Description 3").jobOfferState(JobOfferState.OPEN)
		                                                           .semester(semester).build(), JobOffer.builder().department(testDepartment).title(
				"Job Offer 4").description("Description 4").jobOfferState(JobOfferState.PENDING).semester(semester).build(),
		                                                   JobOffer.builder().department(testDepartment).title("Job Offer 5").description(
			                                                           "Description 5").jobOfferState(JobOfferState.EXPIRED)
		                                                           .semester(semester).build(), JobOffer.builder().department(testDepartment).title(
				"Job Offer 6").description("Description 6").jobOfferState(JobOfferState.TAKEN).semester(semester).build()
		));

		when(jobOfferRepository.findJobOfferByJobOfferStateAndSemester(JobOfferState.TAKEN, semester)).thenReturn(
			jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.TAKEN).toList());

		//        Act

		List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.TAKEN, semester, testDepartment);
		//        Assert

		assertNotNull(jobOfferDTOs);
		assertEquals(1, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findJobOfferByJobOfferStateAndSemester(JobOfferState.TAKEN, semester);

	}

	@Test
	void getJobOffersWithState_Refused(){
		//        Arrange

		Semester semester = new Semester(LocalDate.now());
        List<JobOffer> jobOffers = new ArrayList<>(List.of(JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
                        JobOfferState.SUBMITTED).semester(semester).build(), JobOffer.builder().department(testDepartment).title("Job Offer 2").description(
                        "Description 2").jobOfferState(JobOfferState.REFUSED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title(
                        "Job Offer 3").description("Description 3").jobOfferState(JobOfferState.OPEN).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 4").description("Description 4").jobOfferState(
                        JobOfferState.PENDING).semester(semester).build(), JobOffer.builder().department(testDepartment).title(
                        "Job Offer 5").description("Description 5").jobOfferState(JobOfferState.EXPIRED).semester(semester).build(),
                JobOffer.builder().department(testDepartment).title("Job Offer 6").description("Description 6").jobOfferState(
                        JobOfferState.TAKEN).semester(semester).build()
        ));

		when(jobOfferRepository.findJobOfferByJobOfferStateAndSemester(JobOfferState.REFUSED, semester)).thenReturn(
			jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.REFUSED).toList());

		//        Act

		List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.REFUSED, semester, testDepartment);
		//        Assert

		assertNotNull(jobOfferDTOs);
		assertEquals(1, jobOfferDTOs.size());
		verify(jobOfferRepository, times(1)).findJobOfferByJobOfferStateAndSemester(JobOfferState.REFUSED, semester);

	}

	@Test
	void updateJobOfferState_jobOfferFound(){
		// Arrange

		Long id = 1L;
		JobOfferState newState = JobOfferState.OPEN;

		JobOffer jobOffer = JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(
			JobOfferState.SUBMITTED).build();

		when(jobOfferRepository.findById(id)).thenReturn(Optional.of(jobOffer));
		when(jobOfferRepository.save(any())).thenReturn(jobOffer);

		// Act
		JobOfferDTO jobOfferDTO = managerService.updateJobOfferState(id, newState, null);

		// Assert

		assertNotNull(jobOfferDTO);
		assertEquals(jobOffer.getId(), jobOfferDTO.getId());
		assertEquals(jobOffer.getTitle(), jobOfferDTO.getTitle());
		assertEquals(jobOffer.getDescription(), jobOfferDTO.getDescription());
		assertEquals(newState, jobOffer.getJobOfferState());
		assertNull(jobOffer.getRefusReason());
		verify(jobOfferRepository, times(1)).findById(id);
	}

	@Test
	void updateJobOfferState_jobOfferNotFound(){
		// Arrange
		Long id = 1L;
		JobOfferState newState = JobOfferState.OPEN;
		String reason = "Approved";

		when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());

		// Act and Assert
		assertThrows(JobOfferNotFoundException.class, () -> managerService.updateJobOfferState(id, newState, reason));
		verify(jobOfferRepository, times(1)).findById(id);
	}

	@Test
	void deleteJobOffer_valid(){
		//        Arrange
		Long id = 1L;
		JobOffer.builder().department(testDepartment).title("Job Offer 1").description("Description 1").jobOfferState(JobOfferState.SUBMITTED).build();

		//        Act
		managerService.deleteJobOffer(id);

		//        Assert
		verify(jobOfferRepository, times(1)).deleteById(id);
	}

	//  ---------------------------------------------
	private CvFile createCvFile(Long id, Long studentId){
		CvFile cvFile = new CvFile();
		cvFile.setId(id);
		cvFile.setStudent(createStudent(studentId));
		return cvFile;
	}

	private CvFile createCvFile(Long id, String matricule){
		CvFile cvFile = new CvFile();
		cvFile.setId(id);
		cvFile.setStudent(createStudent(matricule));
		return cvFile;
	}

	private Student createStudent(Long id){
		Student student = new Student();
		student.setId(id);
		return student;
	}

	private Student createStudent(String matricule){
		Student student = new Student();
		student.setMatricule(matricule);
		student.setDepartment(testDepartment);
		return student;
	}

	@Test
	public void testSignContract(){
		LocalDateTime date = LocalDateTime.now();
		String formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		Student student = Student.builder().id(1L).firstName("StudentFirstName").lastName("StudentLastName").build();
		Manager manager = Manager.builder().department(testDepartment).id(1L).firstName("ManagerFirstName").lastName("ManagerLastName").build();
		Employer employer = Employer.builder().id(1L).email("asd@asd.com").firstName("EmployerFirstName").lastName(
			"EmployerLastName").organisationName(" asd").organisationPhone("111-111-1111").build();
		JobOffer jobOffer = JobOffer.builder().department(testDepartment).id(1L).title("JobOfferTitle").description("  asdads").location("Location1")
		                            .department(Department._145A0).jobOfferState(JobOfferState.OPEN).startDate(
				LocalDate.now()).expirationDate(LocalDate.now().plusDays(30)).semester(new Semester(LocalDate.now())).employer(
				employer).build();
		Contract contract = Contract.builder().id(1L).student(student).employer(employer).jobOffer(jobOffer).build();
		Contract savedContract = Contract.builder().id(1L).employer(employer).student(student).jobOffer(jobOffer)
		                                 .creationDate(date).lastModificationDate(date).isComplete(true).build();

		Signature studentSignature = Signature.builder().firstName("StudentFirstName").lastName("StudentLastName")
		                                      .signatureDate(LocalDate.now()).contract(savedContract).build();
		Signature employerSignature = Signature.builder().firstName("EmployerFirstName").lastName("EmployerLastName")
		                                       .signatureDate(LocalDate.now()).contract(savedContract).build();
		Signature managerSignature = Signature.builder().firstName("ManagerFirstName").lastName("ManagerLastName")
		                                      .signatureDate(LocalDate.now()).contract(savedContract).build();

		contract.setStudentSignature(studentSignature);
		contract.setEmployerSignature(employerSignature);

		savedContract.setStudentSignature(studentSignature);
		savedContract.setEmployerSignature(employerSignature);
		savedContract.setManagerSignature(managerSignature);

		when(signatureRepository.save(any(Signature.class))).thenReturn(studentSignature);
		when(signatureRepository.save(any(Signature.class))).thenReturn(employerSignature);
		when(signatureRepository.save(any(Signature.class))).thenReturn(managerSignature);
		when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
		when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
		when(contractRepository.save(any(Contract.class))).thenReturn(savedContract);
		when(managerRepository.findAll()).thenReturn(List.of(manager));

		ContractDTO result = managerService.signContract(1L, 1L);

		assertEquals("StudentFirstName", savedContract.getStudentSignature().getFirstName());
		assertEquals("StudentLastName", savedContract.getStudentSignature().getLastName());
		assertEquals(formattedDate, savedContract.getStudentSignature().getSignatureDate().toString());
		assertEquals("EmployerFirstName", savedContract.getEmployerSignature().getFirstName());
		assertEquals("EmployerLastName", savedContract.getEmployerSignature().getLastName());
		assertEquals(formattedDate, savedContract.getEmployerSignature().getSignatureDate().toString());
		assertEquals("ManagerFirstName", savedContract.getManagerSignature().getFirstName());
		assertEquals("ManagerLastName", savedContract.getManagerSignature().getLastName());
		assertEquals(formattedDate, savedContract.getManagerSignature().getSignatureDate().toString());
		assertEquals(date, savedContract.getCreationDate());
		verify(contractRepository, times(1)).save(contract);
		assertNotNull(result);
		assertEquals(1L, result.getId());
	}

	@Test
	public void testSignContractValidations(){
		Student student = Student.builder().id(1L).firstName("StudentFirstName").lastName("StudentLastName").build();
		Employer employer = Employer.builder().id(1L).firstName("EmployerFirstName").lastName("EmployerLastName").build();
		Manager manager = Manager.builder().department(testDepartment).id(1L).firstName("ManagerFirstName").lastName("ManagerLastName").build();
		JobOffer jobOffer = JobOffer.builder().department(testDepartment).id(1L).title("JobOfferTitle").employer(employer).jobOfferState(
			JobOfferState.OPEN).build();

		Signature studentSignature = Signature.builder().id(1L).firstName("StudentFirstName").lastName("StudentLastName")
		                                      .signatureDate(LocalDate.now()).build();
		Signature employerSignature = Signature.builder().id(1L).firstName("EmployerFirstName").lastName(
			"EmployerLastName")
		                                       .signatureDate(LocalDate.now()).build();
		Signature managerSignature = Signature.builder().id(1L).firstName("ManagerFirstName").lastName("ManagerLastName")
		                                      .signatureDate(LocalDate.now()).build();

		Contract contract = Contract.builder().id(1L).student(student).employer(employer).jobOffer(jobOffer).build();

		Manager manager2 = Manager.builder().department(testDepartment).id(2L).firstName("StudentFirstName2").lastName("StudentLastName2").build();
		Contract contract2 = Contract.builder().id(2L).student(student).employer(employer).jobOffer(jobOffer)
		                             .studentSignature(studentSignature).employerSignature(employerSignature)
		                             .managerSignature(managerSignature).build();

		Manager manager3 = Manager.builder().department(testDepartment).id(3L).firstName("StudentFirstName3").lastName("StudentLastName3").build();
		Contract contract3 = Contract.builder().id(3L).student(student).employer(employer).studentSignature(
			studentSignature).build();

		when(managerRepository.findById(1L)).thenReturn(Optional.of(manager));
		when(managerRepository.findById(2L)).thenReturn(Optional.of(manager2));
		when(managerRepository.findById(3L)).thenReturn(Optional.of(manager3));
		when(managerRepository.findById(100L)).thenReturn(Optional.empty());

		when(contractRepository.findById(1L)).thenReturn(Optional.of(contract));
		when(contractRepository.findById(2L)).thenReturn(Optional.of(contract2));
		when(contractRepository.findById(3L)).thenReturn(Optional.of(contract3));
		when(contractRepository.findById(100L)).thenReturn(Optional.empty());

		assertThrows(ManagerNotFoundException.class, () -> managerService.signContract(1L, 100L));
		assertThrows(ContractNotFoundException.class, () -> managerService.signContract(100L, 1L));
		assertThrows(ContractNotReadyToSignException.class, () -> managerService.signContract(3L, 3L));
		assertThrows(ContractNotSignedByStudentException.class, () -> managerService.signContract(1L, 2L));
		assertThrows(ContractAlreadySignedException.class, () -> managerService.signContract(2L, 2L));
	}

	@Test
	public void getContractBySession_valid(){
		// Arrange

		Semester semester = new Semester(LocalDate.now());

		Credentials credManager = new Credentials();
		credManager.setEmail("Michel@Michaud.com");
		credManager.setRole(Role.MANAGER);

		Credentials credEmployer = new Credentials();
		credEmployer.setEmail("Michel@Professionel.com");
		credEmployer.setRole(Role.EMPLOYER);

		Credentials credStudent = new Credentials();
		credStudent.setEmail("Michel@Student.com");
		credStudent.setRole(Role.STUDENT);

		Manager manager = new Manager();
		manager.setCredentials(credManager);
		manager.setId(1L);
		manager.setFirstName("Michel");
		manager.setLastName("Michaud");

		Employer employer = new Employer();
		employer.setCredentials(credEmployer);
		employer.setId(2L);
		employer.setFirstName("Michel");
		employer.setLastName("Professionel");
		employer.setOrganisationName("Professionel");
		employer.setOrganisationPhone("111-111-1111");

		Student student = new Student();
		student.setId(3L);
		student.setCredentials(credStudent);
		student.setFirstName("Michel");
		student.setLastName("Student");

		JobOffer jobOffer = new JobOffer();
		jobOffer.setId(4L);
		jobOffer.setEmployer(employer);
		jobOffer.setSemester(semester);
		jobOffer.setDepartment(testDepartment);
		jobOffer.setJobOfferState(JobOfferState.OPEN);
		jobOffer.setDuration(6);
		jobOffer.setHoursPerWeek(40);
		jobOffer.setSalary(20.0f);
		jobOffer.setStartDate(LocalDate.now());
		jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
		jobOffer.setLocation("Location1");
		jobOffer.setDescription("Description1");
		jobOffer.setTitle("JobOffer1");

		Contract contract = new Contract(employer, student, jobOffer);

		when(contractRepository.findAllByJobOffer_Semester(semester)).thenReturn(List.of(contract));
		when(managerRepository.findAll()).thenReturn(List.of(manager));

		// Act
		List<ContractDTO> contractsDTO = managerService.getContractsBySession(semester, testDepartment);

		// Assert
		assertNotNull(contractsDTO);
		assertEquals(contract.getId(), contractsDTO.get(0).getId());
		verify(contractRepository, times(1)).findAllByJobOffer_Semester(semester);
	}

	@Test
	public void getContractBySession_validWithMultipleContracts(){
		// Arrange

		Semester semester = new Semester(LocalDate.now());

		Credentials credManager = new Credentials();
		credManager.setEmail("Michel@Michaud.com");
		credManager.setRole(Role.MANAGER);

		Credentials credEmployer = new Credentials();
		credEmployer.setEmail("Michel@Professionel.com");
		credEmployer.setRole(Role.EMPLOYER);

		Credentials credStudent = new Credentials();
		credStudent.setEmail("Michel@Student.com");
		credStudent.setRole(Role.STUDENT);

		Manager manager = new Manager();
		manager.setCredentials(credManager);
		manager.setId(1L);
		manager.setFirstName("Michel");
		manager.setLastName("Michaud");

		Employer employer = new Employer();
		employer.setCredentials(credEmployer);
		employer.setId(2L);
		employer.setFirstName("Michel");
		employer.setLastName("Professionel");
		employer.setOrganisationName("Professionel");
		employer.setOrganisationPhone("111-111-1111");

		Employer employer1 = new Employer();
		employer1.setCredentials(credEmployer);
		employer1.setId(4L);
		employer1.setFirstName("Michel");
		employer1.setLastName("Professionel");
		employer1.setOrganisationName("Professionel");
		employer1.setOrganisationPhone("111-111-1111");

		Student student = new Student();
		student.setId(3L);
		student.setCredentials(credStudent);
		student.setFirstName("Michel");
		student.setLastName("Student");

		Student student1 = new Student();
		student1.setId(6L);
		student1.setCredentials(credStudent);
		student1.setFirstName("Michel");
		student1.setLastName("Student");

		JobOffer jobOffer = new JobOffer();
		jobOffer.setId(4L);
		jobOffer.setEmployer(employer);
		jobOffer.setSemester(semester);
		jobOffer.setDepartment(testDepartment);
		jobOffer.setJobOfferState(JobOfferState.OPEN);
		jobOffer.setDuration(6);
		jobOffer.setHoursPerWeek(40);
		jobOffer.setSalary(20.0f);
		jobOffer.setStartDate(LocalDate.now());
		jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
		jobOffer.setLocation("Location1");
		jobOffer.setDescription("Description1");
		jobOffer.setTitle("JobOffer1");

		JobOffer jobOffer1 = new JobOffer();
		jobOffer1.setId(8L);
		jobOffer1.setEmployer(employer);
		jobOffer1.setSemester(semester);
		jobOffer1.setDepartment(testDepartment);
		jobOffer1.setJobOfferState(JobOfferState.OPEN);
		jobOffer1.setDuration(6);
		jobOffer1.setHoursPerWeek(40);
		jobOffer1.setSalary(20.0f);
		jobOffer1.setStartDate(LocalDate.now());
		jobOffer1.setExpirationDate(LocalDate.now().plusDays(30));
		jobOffer1.setLocation("Location1");
		jobOffer1.setDescription("Description1");
		jobOffer1.setTitle("JobOffer1");

		Contract contract = new Contract(employer, student, jobOffer);
		Contract contract1 = new Contract(employer1, student1, jobOffer1);

		when(contractRepository.findAllByJobOffer_Semester(semester)).thenReturn(List.of(contract, contract1));
		when(managerRepository.findAll()).thenReturn(List.of(manager));

		// Act
		List<ContractDTO> contractsDTO = managerService.getContractsBySession(semester, testDepartment);

		// Assert
		assertEquals(contractsDTO.size(), 2);
		assertEquals(contract.getId(), contractsDTO.get(0).getId());
		verify(contractRepository, times(1)).findAllByJobOffer_Semester(semester);
	}

	@Test
	public void getContractBySession_validListEmpty(){
		// Arrange
		LocalDate date = LocalDate.now().plusMonths(-6);
		Semester semester = new Semester(date);

		Credentials credManager = new Credentials();
		credManager.setEmail("Michel@Michaud.com");
		credManager.setRole(Role.MANAGER);

		Credentials credEmployer = new Credentials();
		credEmployer.setEmail("Michel@Professionel.com");
		credEmployer.setRole(Role.EMPLOYER);

		Credentials credStudent = new Credentials();
		credStudent.setEmail("Michel@Student.com");
		credStudent.setRole(Role.STUDENT);

		Manager manager = new Manager();
		manager.setCredentials(credManager);
		manager.setId(1L);
		manager.setFirstName("Michel");
		manager.setLastName("Michaud");

		Employer employer = new Employer();
		employer.setCredentials(credEmployer);
		employer.setId(2L);
		employer.setFirstName("Michel");
		employer.setLastName("Professionel");
		employer.setOrganisationName("Professionel");
		employer.setOrganisationPhone("111-111-1111");

		Student student = new Student();
		student.setId(3L);
		student.setCredentials(credStudent);
		student.setFirstName("Michel");
		student.setLastName("Student");

		JobOffer jobOffer = new JobOffer();
		jobOffer.setId(4L);
		jobOffer.setEmployer(employer);
		jobOffer.setSemester(new Semester(LocalDate.now()));
		jobOffer.setDepartment(testDepartment);
		jobOffer.setJobOfferState(JobOfferState.OPEN);
		jobOffer.setDuration(6);
		jobOffer.setHoursPerWeek(40);
		jobOffer.setSalary(20.0f);
		jobOffer.setStartDate(LocalDate.now());
		jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
		jobOffer.setLocation("Location1");
		jobOffer.setDescription("Description1");
		jobOffer.setTitle("JobOffer1");

		when(contractRepository.findAllByJobOffer_Semester(semester)).thenReturn(List.of());
		when(managerRepository.findAll()).thenReturn(List.of(manager));

		// Act
		List<ContractDTO> contractsDTO = managerService.getContractsBySession(semester, testDepartment);

		// Assert
		assertNotNull(contractsDTO);
		assertEquals(0, contractsDTO.size());
		verify(contractRepository, times(1)).findAllByJobOffer_Semester(semester);
	}

	@Test
	public void getContractBySession_SemesterNull(){
		// Arrange

		Credentials credManager = new Credentials();
		credManager.setEmail("Michel@Michaud.com");
		credManager.setRole(Role.MANAGER);

		Credentials credEmployer = new Credentials();
		credEmployer.setEmail("Michel@Professionel.com");
		credEmployer.setRole(Role.EMPLOYER);

		Credentials credStudent = new Credentials();
		credStudent.setEmail("Michel@Student.com");
		credStudent.setRole(Role.STUDENT);

		Manager manager = new Manager();
		manager.setCredentials(credManager);
		manager.setId(1L);
		manager.setFirstName("Michel");
		manager.setLastName("Michaud");

		Employer employer = new Employer();
		employer.setCredentials(credEmployer);
		employer.setId(2L);
		employer.setFirstName("Michel");
		employer.setLastName("Professionel");
		employer.setOrganisationName("Professionel");
		employer.setOrganisationPhone("111-111-1111");

		Student student = new Student();
		student.setId(3L);
		student.setCredentials(credStudent);
		student.setFirstName("Michel");
		student.setLastName("Student");

		JobOffer jobOffer = new JobOffer();
		jobOffer.setId(4L);
		jobOffer.setEmployer(employer);
		jobOffer.setSemester(null);
		jobOffer.setDepartment(testDepartment);
		jobOffer.setJobOfferState(JobOfferState.OPEN);
		jobOffer.setDuration(6);
		jobOffer.setHoursPerWeek(40);
		jobOffer.setSalary(20.0f);
		jobOffer.setStartDate(LocalDate.now());
		jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
		jobOffer.setLocation("Location1");
		jobOffer.setDescription("Description1");
		jobOffer.setTitle("JobOffer1");

		when(contractRepository.findAllByJobOffer_Semester(null)).thenReturn(List.of());
		when(managerRepository.findAll()).thenReturn(List.of(manager));

		// Act
		List<ContractDTO> contractsDTO = managerService.getContractsBySession(null, testDepartment);

		// Assert
		assertNotNull(contractsDTO);
		assertEquals(0, contractsDTO.size());
		verify(contractRepository, times(1)).findAllByJobOffer_Semester(null);
	}

	@Test
	void getStudentsByDepartment_ShouldReturnStudents(){
		// Arrange
		Credentials studCreds = new Credentials();
		studCreds.setEmail("John@Doe.fr");
		studCreds.setRole(Role.STUDENT);
		Student student = new Student();
		student.setMatricule("1234567");
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setDepartment(testDepartment);
		student.setCredentials(studCreds);

		StudentDTO studentExpected = new StudentDTO();
		studentExpected.setMatricule("1234567");
		studentExpected.setFirstName("John");
		studentExpected.setLastName("Doe");
		studentExpected.setDepartment(testDepartment);
		when(studentRepository.findAllByDepartment(testDepartment)).thenReturn(List.of(student));

		// Act
		List<StudentDTO> students = managerService.getStudents(testDepartment);

		// Assert
		assertNotNull(students);
		assertEquals(1, students.size());
		assertEquals(studentExpected.getFirstName(), students.get(0).getFirstName());
		assertEquals(studentExpected.getLastName(), students.get(0).getLastName());
		assertEquals(studentExpected.getMatricule(), students.get(0).getMatricule());
		assertEquals(studentExpected.getDepartment(), students.get(0).getDepartment());
		verify(studentRepository).findAllByDepartment(testDepartment);
	}

	// public List<JobApplicationDTO> getJobApplicationsByStudentId(Long id){
	// 	return jobApplicationRepository.findAllByStudentId(id).stream().map(JobApplicationDTO::new).toList();
	// }
	@Test
	public void getJobApplicationsByStudentId_valid() {
		// Arrange
		Credentials studCreds = new Credentials();
		studCreds.setEmail("bob@bob.com");
		studCreds.setRole(Role.STUDENT);
		Student student = new Student();
		student.setMatricule("1234567");
		student.setFirstName("John");
		student.setLastName("Doe");
		student.setDepartment(testDepartment);
		student.setCredentials(studCreds);

		JobOffer jobOffer = new JobOffer();
		jobOffer.setId(1L);
		jobOffer.setDepartment(testDepartment);
		jobOffer.setJobOfferState(JobOfferState.OPEN);
		jobOffer.setSemester(new Semester(LocalDate.now()));
		jobOffer.setEmployer(new Employer());
		jobOffer.setDuration(6);
		jobOffer.setHoursPerWeek(40);
		jobOffer.setSalary(20.0f);
		jobOffer.setStartDate(LocalDate.now());
		jobOffer.setExpirationDate(LocalDate.now().plusDays(30));
		jobOffer.setLocation("Location1");
		jobOffer.setDescription("Description1");
		jobOffer.setTitle("JobOffer1");

		JobApplication jobApplication = new JobApplication();
		jobApplication.setId(1L);
		jobApplication.setStudent(student);
		jobApplication.setJobOffer(jobOffer);
		jobApplication.setJobApplicationState(JobApplicationState.SUBMITTED);
		jobApplication.setSemester(new Semester(LocalDate.now()));
		jobApplication.setStudent(student);
		jobApplication.setJobOffer(jobOffer);

		when(jobApplicationRepository.findAllByStudentId(1L)).thenReturn(List.of(jobApplication));

		// Act
		List<JobApplicationDTO> jobApplications = managerService.getJobApplicationsByStudentId(1L);

		// Assert
		assertNotNull(jobApplications);
		assertEquals(1, jobApplications.size());
		assertEquals(jobApplication.getId(), jobApplications.get(0).getId());
		assertEquals(jobApplication.getJobApplicationState(), jobApplications.get(0).getJobApplicationState());
		assertEquals(jobApplication.getJobOffer().getId(), jobApplications.get(0).getJobOffer().getId());
		assertEquals(jobApplication.getSemester(), jobApplications.get(0).getSemester());
		verify(jobApplicationRepository).findAllByStudentId(1L);
	}

	@Test
	public void saveWorkEnvironmentEvaluationWithJobApplicationId_ValidJobApplication() {
		// Arrange
		Long validJobApplicationId = 1L;
		WorkEnvironmentDTO workEnvironmentDTO = new WorkEnvironmentDTO();

		JobApplication jobApplication = new JobApplication();
		jobApplication.setId(validJobApplicationId);
		jobApplication.setCoverLetter("mock");

		Credentials credentials = new Credentials();
		credentials.setEmail("mock@email.com");

		Role role = Role.STUDENT;

		Student student = new Student();
		student.setCredentials(credentials);
		student.setRole(role);

		JobOffer jobOffer = new JobOffer();

		jobApplication.setStudent(student);
		jobApplication.setJobOffer(jobOffer);

		when(jobApplicationRepository.findById(validJobApplicationId)).thenReturn(Optional.of(jobApplication));
		when(workEnvironmentEvaluationRepository.existsByJobApplicationId(validJobApplicationId)).thenReturn(false);
		when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(jobApplication);

		// Act
		JobApplicationDTO result = managerService.saveWorkEnvironmentEvaluationWithJobApplicationId(validJobApplicationId, workEnvironmentDTO);

		// Assert
		assertNotNull(result);
		verify(workEnvironmentEvaluationRepository, times(1)).save(any(WorkEnvironmentEvaluation.class));
	}

	@Test
	public void saveWorkEnvironmentEvaluationWithJobApplicationId_InvalidJobApplication() {
		// Arrange
		Long invalidJobApplicationId = -1L;
		WorkEnvironmentDTO workEnvironmentDTO = new WorkEnvironmentDTO();

		when(jobApplicationRepository.findById(invalidJobApplicationId)).thenReturn(Optional.empty());

		assertThrows(JobApplicationNotFoundException.class, () -> {
			managerService.saveWorkEnvironmentEvaluationWithJobApplicationId(invalidJobApplicationId, workEnvironmentDTO);
		});

		verify(workEnvironmentEvaluationRepository, never()).save(any(WorkEnvironmentEvaluation.class));
	}

}

