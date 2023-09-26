package cal.projeteq3.glucose.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.exception.request.CvFileNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.exception.request.ManagerNotFoundException;
import cal.projeteq3.glucose.exception.request.UserNotFoundException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.auth.Credentials;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.CvFileRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.ManagerRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cal.projeteq3.glucose.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ManagerServiceTest {
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private CvFileRepository cvRepository;

    @InjectMocks
    private ManagerService managerService;

    @Test
    public void createManager_valid() {
        // Arrange
        ManagerDTO managerDTO = new ManagerDTO();
        managerDTO.setFirstName("John");
        managerDTO.setLastName("Doe");
        managerDTO.setEmail("john@example.com");

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
    public void getAllManagers_valid() {
        // Arrange
        Manager manager = Manager.builder()
                .firstName("John")
                .lastName("Doe")
                .email("manager@test.com")
                .build();

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
    public void getManagerByID_managerNotFound() {
        // Arrange
        Long id = 1L;

        when(managerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> {
            managerService.getManagerByID(id);
        });
        verify(managerRepository, times(1)).findById(id);
    }

    @Test
    public void getManagerByID_valid() {
        // Arrange
        Long id = 1L;
        Manager manager = Manager.builder()
                .firstName("John")
                .lastName("Doe")
                .email("Exemple@email.com")
                .build();

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
    public void updateManager_valid() {
        // Arrange
        Long id = 1L;
        ManagerDTO updatedManager = new ManagerDTO();
        updatedManager.setFirstName("UpdatedFirstName");
        updatedManager.setLastName("UpdatedLastName");
        updatedManager.setEmail("updated@example.com");

        Manager existingManager = Manager.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
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
    public void updateManager_managerNotFound() {
        // Arrange
        Long id = 1L;
        ManagerDTO updatedManager = new ManagerDTO();
        updatedManager.setFirstName("UpdatedFirstName");
        updatedManager.setLastName("UpdatedLastName");
        updatedManager.setEmail("updated@example.com");

        when(managerRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ManagerNotFoundException.class, () -> {
            managerService.updateManager(id, updatedManager);
        });
        verify(managerRepository, times(1)).findById(id);
    }

    @Test
    public void deleteManager_valid() {
        // Arrange
        Long id = 1L;

        // Act
        managerService.deleteManager(id);

        // Assert
        verify(managerRepository, times(1)).deleteById(id);
    }

//    -------------- CV File --------------

    @Test
    public void getCvById_valid() {
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
    public void getAllCvs_valid(){
        // Arrange
        List<CvFile> cvFiles = new ArrayList<>();
        cvFiles.add(createCvFile(1L, "1234567"));
        cvFiles.add(createCvFile(2L, "2345678"));

        when(cvRepository.findAll()).thenReturn(cvFiles);

        // Act
        List<CvFileDTO> cvFileDTOs = managerService.getAllCv();

        // Assert
        assertNotNull(cvFileDTOs);
        assertEquals(2, cvFileDTOs.size());
        verify(cvRepository, times(1)).findAll();
    }

    @Test
    public void getAllSubmittedCv_valid(){
//        Arrange
        List<CvFile> cvFiles = new ArrayList<>();
        cvFiles.addAll(List.of(
                CvFile.builder()
                        .fileName("cv1.pdf")
                        .fileData(new byte[]{})
                        .cvState(CvState.SUBMITTED)
                        .build(),
                CvFile.builder()
                        .fileName("cv1.pdf")
                        .fileData(new byte[]{})
                        .cvState(CvState.SUBMITTED)
                        .build(),
                CvFile.builder()
                        .fileName("cv1.pdf")
                        .fileData(new byte[]{})
                        .cvState(CvState.REFUSED)
                        .build(),
                CvFile.builder()
                        .fileName("cv1.pdf")
                        .fileData(new byte[]{})
                        .cvState(CvState.ACCEPTED)
                        .build()
        ));

        when(cvRepository.findAllByCvState(CvState.SUBMITTED)).thenReturn(
                cvFiles.stream().filter(cvFile -> cvFile.getCvState() == CvState.SUBMITTED).toList());

//        Act
        List<CvFileDTO> cvFileDTOs = managerService.getSubmittedCv();

//        Assert
        assertNotNull(cvFileDTOs);
        assertEquals(2, cvFileDTOs.size());
        verify(cvRepository, times(1)).findAllByCvState(CvState.SUBMITTED);
    }

    @Test
    public void getAllCvFileByStudent_valid() {
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
    public void getAllCvFileByStudentMatricule_valid() {
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
    public void getCvFileByStudentAndFileName_valid() {
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
    public void deleteCvFile_valid() {
        // Arrange
        Long cvFileId = 1L;

        // Act
        managerService.deleteCvFile(cvFileId);

        // Assert
        verify(cvRepository, times(1)).deleteById(cvFileId);
    }

    @Test
    public void deleteAllCvFileByStudentMatricule_valid() {
        // Arrange
        String matricule = "1234567";

        // Act
        managerService.deleteAllCvFileByStudentMatricule(matricule);

        // Assert
        verify(cvRepository, times(1)).deleteAllByStudent(any());
    }

    @Test
    public void rejectCv_valid() {
        // Arrange
        Long cvFileId = 1L;
        String reason = "Rejected for some reason";

        CvFile cvFile = createCvFile(cvFileId, "1234567");

        when(cvRepository.findById(cvFileId)).thenReturn(Optional.of(cvFile));
        when(cvRepository.save(any())).thenReturn(cvFile);

        // Act
        CvFileDTO rejectedCvFileDTO = managerService.rejectCv(cvFileId, reason);

        // Assert
        assertNotNull(rejectedCvFileDTO);
        assertEquals(CvState.REFUSED, rejectedCvFileDTO.getCvState());
        assertEquals(reason, rejectedCvFileDTO.getRefusReason());
        verify(cvRepository, times(1)).findById(cvFileId);
        verify(cvRepository, times(1)).save(any());
    }

    @Test
    public void rejectCv_NotFound() {
        // Arrange
        Long cvFileId = 1L;

        when(cvRepository.findById(cvFileId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CvFileNotFoundException.class, () -> {
            managerService.rejectCv(cvFileId, "reason");
        });

        verify(cvRepository, times(1)).findById(cvFileId);
    }

    @Test
    public void getCvFilesWithState_valid() {
        // Arrange
        CvState state = CvState.SUBMITTED;

        List<CvFile> cvFiles = new ArrayList<>();
        cvFiles.add(createCvFile(1L, "1234567"));
        cvFiles.add(createCvFile(2L, "2345678"));

        when(cvRepository.findAllByCvState(state)).thenReturn(cvFiles);

        // Act
        List<CvFileDTO> cvFileDTOs = managerService.getCvFilesWithState(state);

        // Assert
        assertNotNull(cvFileDTOs);
        assertEquals(2, cvFileDTOs.size());
        verify(cvRepository, times(1)).findAllByCvState(state);
    }

    @Test
    public void updateCvState_valid() {
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
    public void updateCvState_cvFileNotFound() {
        // Arrange
        Long id = 1L;
        CvState newState = CvState.ACCEPTED;
        String reason = "Approved";

        when(cvRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(CvFileNotFoundException.class, () -> {
            managerService.updateCvState(id, newState, reason);
        });
        verify(cvRepository, times(1)).findById(id);
    }

//    -------------- Job Offer --------------

    @Test
    public void getAllJobOffer_valid(){
//        Arrange
        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.add(JobOffer.builder()
                .title("Job Offer 1")
                .description("Description 1")
                .jobOfferState(JobOfferState.OPEN)
                .build());
        jobOffers.add(JobOffer.builder()
                .title("Job Offer 2")
                .description("Description 2")
                .jobOfferState(JobOfferState.OPEN)
                .build());

        when(jobOfferRepository.findAll()).thenReturn(jobOffers);

//        Act
        List<JobOfferDTO> jobOfferDTOs = managerService.getAllJobOffer();

//        Assert
        assertNotNull(jobOfferDTOs);
        assertEquals(2, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findAll();
    }

    @Test
    public void getJobOfferByID_valid(){
//        Arrange
        Long id = 1L;
        JobOffer jobOffer = JobOffer.builder()
                .title("Job Offer 1")
                .description("Description 1")
                .jobOfferState(JobOfferState.OPEN)
                .build();

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
    public void getJobOfferByID_jobOfferNotFound(){
//        Arrange
        Long id = 1L;

        when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());

//        Act and Assert
        assertThrows(JobOffreNotFoundException.class, () -> {
            managerService.getJobOfferByID(id);
        });
        verify(jobOfferRepository, times(1)).findById(id);
    }

    @Test
    public void getJobOffersWithState_Submitted(){
//        Arrange

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.addAll(
                List.of(
                        JobOffer.builder()
                                .title("Job Offer 1")
                                .description("Description 1")
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 2")
                                .description("Description 2")
                                .jobOfferState(JobOfferState.REFUSED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 3")
                                .description("Description 3")
                                .jobOfferState(JobOfferState.OPEN)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 4")
                                .description("Description 4")
                                .jobOfferState(JobOfferState.PENDING)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 5")
                                .description("Description 5")
                                .jobOfferState(JobOfferState.EXPIRED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 6")
                                .description("Description 6")
                                .jobOfferState(JobOfferState.TAKEN)
                                .build()
                )
        );

            when(jobOfferRepository.findJobOfferByJobOfferState(JobOfferState.SUBMITTED)).thenReturn(
                    jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.SUBMITTED).toList());

//        Act

        List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.SUBMITTED);
//        Assert

        assertNotNull(jobOfferDTOs);
        assertEquals(1, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findJobOfferByJobOfferState(JobOfferState.SUBMITTED);

    }

    @Test
    public void getJobOffersWithState_Open(){
//        Arrange

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.addAll(
                List.of(
                        JobOffer.builder()
                                .title("Job Offer 1")
                                .description("Description 1")
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 2")
                                .description("Description 2")
                                .jobOfferState(JobOfferState.REFUSED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 3")
                                .description("Description 3")
                                .jobOfferState(JobOfferState.OPEN)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 4")
                                .description("Description 4")
                                .jobOfferState(JobOfferState.PENDING)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 5")
                                .description("Description 5")
                                .jobOfferState(JobOfferState.EXPIRED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 6")
                                .description("Description 6")
                                .jobOfferState(JobOfferState.TAKEN)
                                .build()
                )
        );

        when(jobOfferRepository.findJobOfferByJobOfferState(JobOfferState.OPEN)).thenReturn(
                jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.OPEN).toList());

//        Act

        List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.OPEN);
//        Assert

        assertNotNull(jobOfferDTOs);
        assertEquals(1, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findJobOfferByJobOfferState(JobOfferState.OPEN);

    }

    @Test
    public void getJobOffersWithState_Pending(){
//        Arrange

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.addAll(
                List.of(
                        JobOffer.builder()
                                .title("Job Offer 1")
                                .description("Description 1")
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 2")
                                .description("Description 2")
                                .jobOfferState(JobOfferState.REFUSED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 3")
                                .description("Description 3")
                                .jobOfferState(JobOfferState.OPEN)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 4")
                                .description("Description 4")
                                .jobOfferState(JobOfferState.PENDING)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 5")
                                .description("Description 5")
                                .jobOfferState(JobOfferState.EXPIRED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 6")
                                .description("Description 6")
                                .jobOfferState(JobOfferState.TAKEN)
                                .build()
                )
        );

        when(jobOfferRepository.findJobOfferByJobOfferState(JobOfferState.PENDING)).thenReturn(
                jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.PENDING).toList());

//        Act

        List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.PENDING);
//        Assert

        assertNotNull(jobOfferDTOs);
        assertEquals(1, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findJobOfferByJobOfferState(JobOfferState.PENDING);

    }

    @Test
    public void getJobOffersWithState_Expired(){
//        Arrange

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.addAll(
                List.of(
                        JobOffer.builder()
                                .title("Job Offer 1")
                                .description("Description 1")
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 2")
                                .description("Description 2")
                                .jobOfferState(JobOfferState.REFUSED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 3")
                                .description("Description 3")
                                .jobOfferState(JobOfferState.OPEN)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 4")
                                .description("Description 4")
                                .jobOfferState(JobOfferState.PENDING)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 5")
                                .description("Description 5")
                                .jobOfferState(JobOfferState.EXPIRED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 6")
                                .description("Description 6")
                                .jobOfferState(JobOfferState.TAKEN)
                                .build()
                )
        );

        when(jobOfferRepository.findJobOfferByJobOfferState(JobOfferState.EXPIRED)).thenReturn(
                jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.EXPIRED).toList());

//        Act

        List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.EXPIRED);
//        Assert

        assertNotNull(jobOfferDTOs);
        assertEquals(1, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findJobOfferByJobOfferState(JobOfferState.EXPIRED);

    }

    @Test
    public void getJobOffersWithState_Taken(){
//        Arrange

        List<JobOffer> jobOffers = new ArrayList<>(List.of(
                JobOffer.builder()
                        .title("Job Offer 1")
                        .description("Description 1")
                        .jobOfferState(JobOfferState.SUBMITTED)
                        .build(),
                JobOffer.builder()
                        .title("Job Offer 2")
                        .description("Description 2")
                        .jobOfferState(JobOfferState.REFUSED)
                        .build(),
                JobOffer.builder()
                        .title("Job Offer 3")
                        .description("Description 3")
                        .jobOfferState(JobOfferState.OPEN)
                        .build(),
                JobOffer.builder()
                        .title("Job Offer 4")
                        .description("Description 4")
                        .jobOfferState(JobOfferState.PENDING)
                        .build(),
                JobOffer.builder()
                        .title("Job Offer 5")
                        .description("Description 5")
                        .jobOfferState(JobOfferState.EXPIRED)
                        .build(),
                JobOffer.builder()
                        .title("Job Offer 6")
                        .description("Description 6")
                        .jobOfferState(JobOfferState.TAKEN)
                        .build()
        ));

        when(jobOfferRepository.findJobOfferByJobOfferState(JobOfferState.TAKEN)).thenReturn(
                jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.TAKEN).toList());

//        Act

        List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.TAKEN);
//        Assert

        assertNotNull(jobOfferDTOs);
        assertEquals(1, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findJobOfferByJobOfferState(JobOfferState.TAKEN);

    }

    @Test
    public void getJobOffersWithState_Refused(){
//        Arrange

        List<JobOffer> jobOffers = new ArrayList<>();
        jobOffers.addAll(
                List.of(
                        JobOffer.builder()
                                .title("Job Offer 1")
                                .description("Description 1")
                                .jobOfferState(JobOfferState.SUBMITTED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 2")
                                .description("Description 2")
                                .jobOfferState(JobOfferState.REFUSED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 3")
                                .description("Description 3")
                                .jobOfferState(JobOfferState.OPEN)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 4")
                                .description("Description 4")
                                .jobOfferState(JobOfferState.PENDING)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 5")
                                .description("Description 5")
                                .jobOfferState(JobOfferState.EXPIRED)
                                .build(),
                        JobOffer.builder()
                                .title("Job Offer 6")
                                .description("Description 6")
                                .jobOfferState(JobOfferState.TAKEN)
                                .build()
                )
        );

        when(jobOfferRepository.findJobOfferByJobOfferState(JobOfferState.REFUSED)).thenReturn(
                jobOffers.stream().filter(jobOffer -> jobOffer.getJobOfferState() == JobOfferState.REFUSED).toList());

//        Act

        List<JobOfferDTO> jobOfferDTOs = managerService.getJobOffersWithState(JobOfferState.REFUSED);
//        Assert

        assertNotNull(jobOfferDTOs);
        assertEquals(1, jobOfferDTOs.size());
        verify(jobOfferRepository, times(1)).findJobOfferByJobOfferState(JobOfferState.REFUSED);

    }

    @Test
    public void updateJobOfferState_jobOfferNotFound() {
        // Arrange
        Long id = 1L;
        JobOfferState newState = JobOfferState.OPEN;
        String reason = "Approved";

        when(jobOfferRepository.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(JobOffreNotFoundException.class, () -> {
            managerService.updateJobOfferState(id, newState, reason);
        });
        verify(jobOfferRepository, times(1)).findById(id);
    }


//  ---------------------------------------------
    private CvFile createCvFile(Long id, Long studentId) {
        CvFile cvFile = new CvFile();
        cvFile.setId(id);
        cvFile.setStudent(createStudent(studentId));
        return cvFile;
    }

    private CvFile createCvFile(Long id, String matricule) {
        CvFile cvFile = new CvFile();
        cvFile.setId(id);
        cvFile.setStudent(createStudent(matricule));
        return cvFile;
    }

    private Student createStudent(Long id) {
        Student student = new Student();
        student.setId(id);
        return student;
    }

    private Student createStudent(String matricule) {
        Student student = new Student();
        student.setMatricule(matricule);
        return student;
    }
}

