package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.exception.request.JobOfferNotFoundException;
import cal.projeteq3.glucose.exception.request.StudentNotFoundException;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

// EQ3-13
@Service
public class JobOfferService{
	private final StudentRepository studentRepository;
	private final JobOfferRepository jobOfferRepository;

	public JobOfferService(StudentRepository studentRepository, JobOfferRepository jobOfferRepository){
		this.studentRepository = studentRepository;
		this.jobOfferRepository = jobOfferRepository;
	}

	// EQ3-13
	@Transactional
	public void apply(Long jobOfferId, Long studentId){
		JobOffer jobOffer = jobOfferRepository.findById(jobOfferId)
			.orElseThrow(JobOfferNotFoundException::new);
		Student student = studentRepository.findById(studentId)
			.orElseThrow(StudentNotFoundException::new);
		jobOffer.apply(student);
		jobOfferRepository.save(jobOffer);
	}

}
