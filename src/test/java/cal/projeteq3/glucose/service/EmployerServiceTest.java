package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.EmployerDTO;
import cal.projeteq3.glucose.model.Employer;
import cal.projeteq3.glucose.repository.EmployerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

        Employer employer = new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null);
        when(employerRepository.save(employer)).thenReturn(new Employer("michel", "michaud", "test@test.com", "Ose12asd3", "Fritz", "111-111-1111", null));

        //Act

        employerService.createEmployer(employer);

        //Assert

        verify(employerRepository, times(1)).save(employer);

    }

    @Test
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

    }

    @Test
    public void getEmployerByIDTest() {

        //Arrange

        //Act

        //Assert

    }

    @Test
    public void updateEmployerTest() {

        //Arrange

        //Act

        //Assert

    }

    @Test
    public void deleteEmployerTest() {

        //Arrange

        //Act

        //Assert

    }

}
