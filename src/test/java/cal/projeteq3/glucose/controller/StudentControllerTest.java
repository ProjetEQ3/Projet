package cal.projeteq3.glucose.controller;

import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.exception.APIException;
import cal.projeteq3.glucose.exception.unauthorisedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitConfig(classes = {StudentController.class, StudentService.class})
@WebMvcTest(StudentController.class)
public class StudentControllerTest{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService studentService;

	private final Long validStudentId = 1L;

	private final Long validJobOfferId = 1L;

	@Test
	public void GetJobOffersByDepartment_Valid() throws Exception{
		//        Arrange
		List<JobOffer> jobOffers_420B0 = new ArrayList<>(List.of(JobOffer.builder().title("JobOffer1").description(
			                                                                 "Description1").location("Location1").department(Department._420B0).jobOfferState(JobOfferState.OPEN).duration(6)
		                                                                 .hoursPerWeek(40).salary(20.0f).startDate(
				LocalDateTime.now()).expirationDate(LocalDateTime.now().plusDays(30)).build(), JobOffer.builder().title(
			"JobOffer2").description("Description2").location("Location1").department(Department._420B0).jobOfferState(
			JobOfferState.SUBMITTED).duration(6).hoursPerWeek(40).salary(20.0f).startDate(LocalDateTime.now()).expirationDate(
			LocalDateTime.now().plusDays(30)).build(), JobOffer.builder().title("JobOffer3").description("Description3")
		                                                     .location("Location1").department(Department._420B0)
		                                                     .jobOfferState(JobOfferState.EXPIRED).duration(6).hoursPerWeek(
				40).salary(20.0f).startDate(LocalDateTime.now().minusDays(60)).expirationDate(LocalDateTime.now().minusDays(30))
		                                                     .build()));
		when(studentService.getJobOffersByDepartment(Department._420B0)).thenReturn(
			jobOffers_420B0.stream().map(JobOfferDTO::new).collect(Collectors.toList()));

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/{department}", "_420B0")).andExpect(
			MockMvcResultMatchers.status().isAccepted()).andExpect(
			MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
			MockMvcResultMatchers.jsonPath("$.length()").value(3)).andExpect(
			MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
	}

	@Test
	public void GetJobOffersByDepartment_InvalidDep() throws Exception{
		//        Arrange
		//        Rien à arranger

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/{department}", "_420B1")).andExpect(
			       MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.header().exists("X-Errors"))
		       .andExpect(MockMvcResultMatchers.header().string("X-Errors", "Invalide operation"));
	}

	@Test
	public void GetOpenJobOffersByDepartment_Valid() throws Exception{
		//        Arrange
		List<JobOffer> jobOffers_420B0 = new ArrayList<>(List.of(JobOffer.builder().title("JobOffer1").description(
			                                                                 "Description1").location("Location1").department(Department._420B0).jobOfferState(JobOfferState.OPEN).duration(6)
		                                                                 .hoursPerWeek(40).salary(20.0f).startDate(
				LocalDateTime.now()).expirationDate(LocalDateTime.now().plusDays(30)).build(), JobOffer.builder().title(
			"JobOffer2").description("Description2").location("Location1").department(Department._420B0).jobOfferState(
			JobOfferState.SUBMITTED).duration(6).hoursPerWeek(40).salary(20.0f).startDate(LocalDateTime.now()).expirationDate(
			LocalDateTime.now().plusDays(30)).build(), JobOffer.builder().title("JobOffer3").description("Description3")
		                                                     .location("Location1").department(Department._420B0)
		                                                     .jobOfferState(JobOfferState.EXPIRED).duration(6).hoursPerWeek(
				40).salary(20.0f).startDate(LocalDateTime.now().minusDays(60)).expirationDate(LocalDateTime.now().minusDays(30))
		                                                     .build()));

		when(studentService.getOpenJobOffersByDepartment(Department._420B0)).thenReturn(jobOffers_420B0.stream().map(
			JobOfferDTO::new).filter(jobOfferDTO -> {
			return jobOfferDTO.getJobOfferState().equals(JobOfferState.OPEN);
		}).collect(Collectors.toList()));

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/open/{department}", "_420B0")).andExpect(
			MockMvcResultMatchers.status().isAccepted()).andExpect(
			MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)).andExpect(
			MockMvcResultMatchers.jsonPath("$.length()").value(1)).andExpect(
			MockMvcResultMatchers.jsonPath("$[0].title").value("JobOffer1"));
	}

	@Test
	public void GetOpenJobOffersByDepartment_InvalidDep() throws Exception{
		//        Arrange
		//        Rien à arranger

		//        Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/student/jobOffers/open/{department}", "_420B1")).andExpect(
			       MockMvcResultMatchers.status().is5xxServerError()).andExpect(MockMvcResultMatchers.header().exists("X-Errors"))
		       .andExpect(MockMvcResultMatchers.header().string("X-Errors", "Invalide operation"));
	}

	@Test
	void testApplyJobOfferSuccess() throws Exception{
		JobOfferDTO mockDto = new JobOfferDTO();
		when(studentService.applyJobOffer(validJobOfferId, validStudentId)).thenReturn(mockDto);

		mockMvc
			.perform(post("/student/applyJobOffer/" + validStudentId + "/" + validJobOfferId).contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isAccepted());
	}

	@Test
	void testApplyJobOfferApiException() throws Exception{
		APIException mockException = new JobOfferNotOpenException();

		when(studentService.applyJobOffer(validJobOfferId, validStudentId)).thenThrow(mockException);

		mockMvc
			.perform(post("/student/applyJobOffer/" + validStudentId + "/" + validJobOfferId).contentType(MediaType.APPLICATION_JSON))
		  .andExpect(status().isUnauthorized()).andExpect(header().string("X-Errors", "L'offre d'emploi n'est pas ouverte"));
	}

	@Test
	void testApplyJobOfferGenericException() throws Exception{
		when(studentService.applyJobOffer(validJobOfferId, validStudentId)).thenThrow(new RuntimeException());

		mockMvc.perform(
			       post("/student/applyJobOffer/" + validStudentId + "/" + validJobOfferId).contentType(MediaType.APPLICATION_JSON))
		       .andExpect(status().isInternalServerError()).andExpect(header().string("X-Errors", "Opération invalide"));
	}

}
