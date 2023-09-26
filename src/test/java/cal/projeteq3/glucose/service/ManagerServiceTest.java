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
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
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
}

