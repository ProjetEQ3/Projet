package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterDTO;
import cal.projeteq3.glucose.dto.auth.RegisterEmployerDTO;
import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.exception.request.EmployerNotFoundException;
import cal.projeteq3.glucose.exception.request.JobOffreNotFoundException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.repository.EmployerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cal.projeteq3.glucose.repository.JobOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;


    @Test
    void createEmployer_valid(){
//        Arrange
        RegisterDTO registerDTO = new RegisterDTO(
                "Test@Test.com",
                "Testestest1",
                "EMPLOYER");
        EmployerDTO employerDTO = new EmployerDTO(
                "Test",
                "Test",
                null);

        RegisterEmployerDTO registerEmployerDTO = new RegisterEmployerDTO(
                registerDTO,
                employerDTO);

        Employer employer = Employer.builder()
                .email(registerEmployerDTO.getRegisterDTO().getEmail())
                .password(registerEmployerDTO.getRegisterDTO().getPassword())
                .firstName(registerEmployerDTO.getEmployerDTO().getFirstName())
                .lastName(registerEmployerDTO.getEmployerDTO().getLastName())
                .organisationName(registerEmployerDTO.getEmployerDTO().getOrganisationName())
                .organisationPhone(registerEmployerDTO.getEmployerDTO().getOrganisationPhone())
                .build();

        when(employerRepository.save(employer)).thenReturn(employer);

//        Act
        EmployerDTO employerDTOResult = employerService.createEmployer(registerEmployerDTO);

//        Assert
        assertEquals(employerDTOResult.getFirstName(), employer.getFirstName());
        assertEquals(employerDTOResult.getLastName(), employer.getLastName());
        assertEquals(employerDTOResult.getOrganisationName(), employer.getOrganisationName());
        assertEquals(employerDTOResult.getOrganisationPhone(), employer.getOrganisationPhone());
        assertEquals(employerDTOResult.getEmail(), employer.getEmail());
        verify(employerRepository, times(1)).save(employer);
    }

    @Test
    void getAllEmployers_valid(){
//        Arrange
        List<Employer> employers = new ArrayList<>(
                List.of(
                        Employer.builder()
                                .id(1L)
                                .email("test@test.com")
                                .password("Testestest1")
                                .firstName("Test")
                                .lastName("Test")
                                .organisationName("Test")
                                .organisationPhone("111-111-1111")
                                .build(),
                        Employer.builder()
                                .id(2L)
                                .email("test2@test.com")
                                .password("Testestest1")
                                .firstName("Test")
                                .lastName("Test")
                                .organisationName("Test")
                                .organisationPhone("111-111-1111")
                                .build(),
                        Employer.builder()
                                .id(3L)
                                .email("test3@test.com")
                                .password("Testestest1")
                                .firstName("Test")
                                .lastName("Test")
                                .organisationName("Test")
                                .organisationPhone("111-111-1111")
                                .build()
                )
        );

        when(employerRepository.findAll()).thenReturn(employers);

//        Act
        List<EmployerDTO> employerDTOS = employerService.getAllEmployers();

//        Assert
        assertEquals(employerDTOS.size(), employers.size());
        verify(employerRepository, times(1)).findAll();
    }

    @Test
    void getEmployerByEmail_valid(){
//        Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        when(employerRepository.findByCredentialsEmail(employer.getEmail())).thenReturn(Optional.of(employer));

//        Act
        EmployerDTO employerDTOResult = employerService.getEmployerByEmail(employer.getEmail());

//        Assert
        assertEquals(employerDTOResult.getFirstName(), employer.getFirstName());
        assertEquals(employerDTOResult.getLastName(), employer.getLastName());
        assertEquals(employerDTOResult.getOrganisationName(), employer.getOrganisationName());
        assertEquals(employerDTOResult.getOrganisationPhone(), employer.getOrganisationPhone());
        assertEquals(employerDTOResult.getEmail(), employer.getEmail());
        verify(employerRepository, times(1)).findByCredentialsEmail(employer.getEmail());
    }

    @Test
    void getEmployerByEmail_invalid(){
//        Arrange
        Employer employer = Employer.builder()
                .id(1L)
                .email("test@test.com")
                .password("Testestest1")
                .firstName("Test")
                .lastName("Test")
                .organisationName("Test")
                .organisationPhone("111-111-1111")
                .build();

        when(employerRepository.findByCredentialsEmail(employer.getEmail())).thenReturn(Optional.empty());

//        Act & Assert
        EmployerNotFoundException employerNotFoundException =
                assertThrows(EmployerNotFoundException.class, () ->
                        employerService.getEmployerByEmail(employer.getEmail()));

        verify(employerRepository, times(1)).findByCredentialsEmail(employer.getEmail());
    }

}
