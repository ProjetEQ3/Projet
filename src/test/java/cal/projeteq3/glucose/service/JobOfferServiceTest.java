package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.exception.unauthorisedException.CvNotApprovedException;
import cal.projeteq3.glucose.exception.unauthorisedException.JobOfferNotOpenException;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JobOfferTest {

	private JobOffer jobOffer;
	private Student student;

	@BeforeEach
	void setUp() {
		jobOffer = new JobOffer();
		student = mock(Student.class);
	}

	@Test
	void apply_withApprovedCVAndOpenJobOffer_returnsJobApplication() {
		when(student.hasApprovedCv()).thenReturn(true);
		jobOffer.setJobOfferState(JobOfferState.OPEN);

		JobApplication result = jobOffer.apply(student);

		assertNotNull(result);
		assertEquals(student, result.getStudent());
		assertEquals(jobOffer, result.getJobOffer());
	}

	@Test
	void apply_withNonApprovedCV_throwsCvNotApprovedException() {
		when(student.hasApprovedCv()).thenReturn(false);
		jobOffer.setJobOfferState(JobOfferState.OPEN);

		assertThrows(CvNotApprovedException.class, () -> jobOffer.apply(student));
	}

	@Test
	void apply_withClosedJobOffer_throwsJobOfferNotOpenException() {
		when(student.hasApprovedCv()).thenReturn(true);
		jobOffer.setJobOfferState(JobOfferState.SUBMITTED);  // Or any state other than OPEN

		assertThrows(JobOfferNotOpenException.class, () -> jobOffer.apply(student));
	}

	@Test
	void apply_withNonApprovedCVAndClosedJobOffer_throwsCvNotApprovedException() {
		// We're prioritizing the CV check first as per the `apply` method logic.
		when(student.hasApprovedCv()).thenReturn(false);
		jobOffer.setJobOfferState(JobOfferState.SUBMITTED);  // Or any state other than OPEN

		assertThrows(CvNotApprovedException.class, () -> jobOffer.apply(student));
	}

}