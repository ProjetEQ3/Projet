package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.user.EmployerDTO;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.repository.EmployerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployerServiceTest {

    @Mock
    private EmployerRepository employerRepository;

    @InjectMocks
    private EmployerService employerService;

    @Test
    public void createEmployerTest() {

        //Arrange


        //Employer employer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        Employer employer = Employer.builder()
          .firstName("michel")
          .lastName("michaud")
          .email("test@test.com")
          .password("Ose12asd3")
          .organisationName("Fritz")
          .organisationPhone("123-456-7890")
          .build();
        when(employerRepository.save(employer)).thenReturn(
          Employer.builder()
            .firstName("michel")
            .lastName("michaud")
            .email("test@test.com")
            .password("Ose12asd3")
            .organisationName("Fritz")
            .organisationPhone("123-456-7890")
            .build()
        );

        //Act

        //employerService.createEmployer(employer);

        //Assert

        verify(employerRepository, times(1)).save(employer);

    }

    /*@Test
    public void getAllEmployersTest() {

        //Arrange

        List<Employer> employers = new ArrayList<>();
        Employer employer1 = new Employer("michel1", "michaud1", "test1@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        Employer employer2 = new Employer("michel2", "michaud2", "test2@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        employers.add(employer1);
        employers.add(employer2);

        when(employerRepository.findAll()).thenReturn(employers);

        //Act

        List<EmployerDTO> employerList = employerService.getAllEmployers();

        //Assert

        assertEquals(2, employerList.size());
        verify(employerRepository, times(1)).findAll();

    }*/

    /*@Test
    public void getEmployerByIDTest() {

        //Arrange

        Long id = 1L;
        Employer employer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        when(employerRepository.findById(id)).thenReturn(Optional.of(employer));

        //Act

        Optional<EmployerDTO> employerDTO = employerService.getEmployerByID(id);

        //Assert

        assertTrue(employerDTO.isPresent());
        verify(employerRepository, times(1)).findById(id);

    }*/

    @Test
    public void updateEmployerTest() {

        // Arrange

        Long employerId = 1L;
        EmployerDTO updatedEmployer = new EmployerDTO();
        updatedEmployer.setId(employerId);
        updatedEmployer.setFirstName("UpdatedFirstName");
        updatedEmployer.setLastName("UpdatedLastName");
        updatedEmployer.setEmail("updated@example.com");
        updatedEmployer.setOrganisationName("UpdatedOrgName");
        updatedEmployer.setOrganisationPhone("123-456-7890");

        Employer existingEmployer = new Employer();
        existingEmployer.setUserID(employerId);
        existingEmployer.setFirstName("OriginalFirstName");
        existingEmployer.setLastName("OriginalLastName");
        existingEmployer.setEmail("original@example.com");
        existingEmployer.setOrganisationName("OriginalOrgName");
        existingEmployer.setOrganisationPhone("987-654-3210");

        when(employerRepository.findById(employerId)).thenReturn(Optional.of(existingEmployer));
        when(employerRepository.save(any(Employer.class))).thenReturn(existingEmployer);

        // Act

        EmployerDTO updatedDTO = employerService.updateEmployer(employerId, updatedEmployer);

        // Assert

        assertNotNull(updatedDTO);
        assertEquals(updatedEmployer.getId(), updatedDTO.getId());
        assertEquals(updatedEmployer.getFirstName(), updatedDTO.getFirstName());
        assertEquals(updatedEmployer.getLastName(), updatedDTO.getLastName());
        assertEquals(updatedEmployer.getEmail(), updatedDTO.getEmail());
        assertEquals(updatedEmployer.getOrganisationName(), updatedDTO.getOrganisationName());
        assertEquals(updatedEmployer.getOrganisationPhone(), updatedDTO.getOrganisationPhone());

    }

    @Test
    public void deleteEmployerTest() {

        // Arrange

        Long employerId = 1L;
        doNothing().when(employerRepository).deleteById(employerId);

        // Act

        employerService.deleteEmployer(employerId);

        // Assert

        verify(employerRepository, times(1)).deleteById(employerId);

    }

}
