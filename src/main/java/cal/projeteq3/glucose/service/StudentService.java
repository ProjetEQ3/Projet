package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.badRequestException.*;
import cal.projeteq3.glucose.exception.unauthorizedException.*;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.Semester;
import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.contract.Signature;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobApplicationState;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService{

	private final StudentRepository studentRepository;
	private final CvFileRepository cvFileRepository;
	private final JobOfferRepository jobOfferRepository;
	private final JobApplicationRepository jobApplicationRepository;
	private final AppointmentRepository appointmentRepository;
	private final PasswordEncoder passwordEncoder;
	private final ManagerRepository managerRepository;
	private final ContractRepository contractRepository;
	private final SignatureRepository signatureRepository;

	// database operations here

	public StudentDTO createStudent(RegisterStudentDTO registerStudentDTO){
		Student student = Student.builder().email(registerStudentDTO.getRegisterDTO().getEmail()).password(
			                         passwordEncoder.encode(registerStudentDTO.getRegisterDTO().getPassword())).firstName(
			                         registerStudentDTO.getStudentDTO().getFirstName()).lastName(registerStudentDTO.getStudentDTO().getLastName())
		                         .department(String.valueOf(registerStudentDTO.getStudentDTO().getDepartment())).matricule(
				registerStudentDTO.getStudentDTO().getMatricule()).build();
		return new StudentDTO(studentRepository.save(student));
	}

	public List<StudentDTO> getAllStudents(){
		List<Student> students = studentRepository.findAll();
		return students.stream().map(StudentDTO::new).collect(Collectors.toList());
	}

	public StudentDTO getStudentByID(Long id){
		Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
		return new StudentDTO(student);
	}

	public StudentDTO updateStudent(Long id, StudentDTO updatedStudent){
		Optional<Student> existingStudent = studentRepository.findById(id);
		if(existingStudent.isPresent()){
			Student student = existingStudent.get();

			student.setId(updatedStudent.getId());
			student.setFirstName(updatedStudent.getFirstName());
			student.setLastName(updatedStudent.getLastName());
			student.setEmail(updatedStudent.getEmail());
			student.setMatricule(updatedStudent.getMatricule());
			student.setDepartment(updatedStudent.getDepartment());

			return new StudentDTO(studentRepository.save(student));
		}

		throw new StudentNotFoundException(id);
	}

	public void deleteStudent(Long id){
		studentRepository.deleteById(id);
	}

	public CvFileDTO getCv(Long studentId){
		Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
		CvFile cvFile = student.getCvFile();
		if(cvFile == null) throw new StudentCvNotFoundException();
		return new CvFileDTO(cvFile);
	}

	public CvFileDTO addCv(Long studentId, CvFileDTO cvFile){
		Student student;
		Optional<Student> studentOptional = studentRepository.findById(studentId);
		if(studentOptional.isEmpty()) throw new StudentNotFoundException(studentId);
		student = studentOptional.get();
		CvFile cvExiste = student.getCvFile();
		if(cvExiste != null) throw new StudentHasAlreadyCVException();
		CvFile cv = CvFile.builder().fileName(cvFile.getFileName()).fileData(cvFile.getFileData()).cvState(
			cvFile.getCvState()).build();
		student.setCvFile(cv);
		return new CvFileDTO(studentRepository.save(student).getCvFile());
	}

	public void deleteCv(Long studentId){
		Optional<Student> studentOptional = studentRepository.findById(studentId);
		if(studentOptional.isEmpty()) throw new StudentNotFoundException(studentId);
		Student student = studentOptional.get();
		CvFile cvExiste = student.getCvFile();
		student.deleteCv();
		cvFileRepository.delete(cvExiste);
	}

	public List<JobOfferDTO> getJobOffersByDepartment(Department department, Semester semester){
		return jobOfferRepository.findJobOffersByDepartmentAndSemester(department, semester).stream().map(JobOfferDTO::new)
		                         .collect(Collectors.toList());
	}

	public List<JobOfferDTO> getOpenJobOffersByDepartment(Department department, Semester semester){
		return jobOfferRepository.findJobOffersByDepartmentAndJobOfferStateAndSemester(
			department, JobOfferState.OPEN, semester).stream().map(JobOfferDTO::new).collect(Collectors.toList());
	}

	@Transactional
	public JobOfferDTO applyJobOffer(Long jobOfferId, Long studentId){
		JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElseThrow(JobOfferNotFoundException::new);
		Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);

		if(jobOffer.hasApplied(studentId)) throw new StudentHasAlreadyAppliedException();
		if(!student.hasApprovedCv()) throw new CvNotApprovedException();
		if(jobOffer.getJobOfferState() != JobOfferState.OPEN) throw new JobOfferNotOpenException();

		JobApplication jobApplication = new JobApplication();
		jobApplication.setStudent(student);
		jobApplication.setJobOffer(jobOffer);
		jobApplication.setSemester(jobOffer.getSemester());
		jobOffer.getJobApplications().add(jobApplication);

		jobApplicationRepository.save(jobOffer.getJobApplications().get(jobOffer.getJobApplications().size() - 1));
		return new JobOfferDTO(jobOfferRepository.save(jobOffer));
	}

	public List<JobOfferDTO> getAppliedJobOfferByStudentId(long studentId, Semester semester){
		Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
		return jobOfferRepository.findAllByJobApplications_Student_IdAndSemester(student.getId(), semester).stream().map(JobOfferDTO::new)
		                         .collect(Collectors.toList());
	}

	public List<AppointmentDTO> getAppointmentsByJobApplicationId(Long id){
		List<Appointment> appointments = jobApplicationRepository.findAppointmentsByJobApplicationId(id);
		return appointments.stream().map(AppointmentDTO::new).collect(Collectors.toList());
	}

	public List<AppointmentDTO> findAllAppointmentsForJobOfferAndStudent(Long jobOfferId, Long studentId){
		JobApplication jobApplication = jobApplicationRepository.findByJobOfferIdAndStudentId(jobOfferId, studentId).orElseThrow(
			JobApplicationNotFoundException::new);
		List<Appointment> appointments = new ArrayList<>(jobApplication.getAppointments());
		return appointments.stream().map(AppointmentDTO::new).collect(Collectors.toList());
	}

	public AppointmentDTO setAppointmentToChosen(Long id){
		Appointment appointment = appointmentRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);
		Long jobApplicationId = appointment.getJobApplication().getId();
		List<Appointment> appointments = jobApplicationRepository.findAppointmentsByJobApplicationId(jobApplicationId);
		for(Appointment siblingAppointment : appointments){
			if((long) siblingAppointment.getId() != appointment.getId()){
				appointmentRepository.deleteById(siblingAppointment.getId());
			}
		}
		appointment.setChosen(true);
		JobApplication jobApplication = appointment.getJobApplication();
		jobApplication.setJobApplicationState(JobApplicationState.WAITING_APPOINTMENT);
		return new AppointmentDTO(appointmentRepository.save(appointment));
	}

	public List<ContractDTO> getContractsByStudentId(Long studentId, Semester semester){
		Manager manager = managerRepository.findAll().get(0);
		return contractRepository.findAllByStudentId(studentId).stream().filter(
			contract -> contract.getJobOffer().getSemester().equals(semester)).map(
			(contract -> new ContractDTO(contract, manager))).toList();
	}

	public ContractDTO signContract(Long contractId, Long studentId){
		Student student = studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new);
		Contract contract = contractRepository.findById(contractId).orElseThrow(ContractNotFoundException::new);
		if(!contract.isReadyToSign()) throw new ContractNotReadyToSignException();
		if(!contract.getStudent().getId().equals(studentId)) throw new UnauthorizedContractToSignException();
		if(contract.getStudentSignature() != null) throw new ContractAlreadySignedException();
		if(student.getFirstName() == null || student.getLastName() == null) throw new StudentNotCompleteException();
		Signature signature = signatureRepository.save(Signature
				.builder()
				.firstName(student.getFirstName())
				.lastName(student.getLastName())
				.signatureDate(java.time.LocalDate.now())
				.contract(contract)
				.build());
		contract.setStudentSignature(signature);
//		TODO: get what manager ?
		return new ContractDTO(contractRepository.save(contract), managerRepository.findAll().get(0));
	}

	public JobOfferDTO markJobOfferAsViewed(Long id, Long jobOfferId) {
		Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
		if(student.getViewedJobOfferIds().contains(jobOfferId)) return null;
		student.getViewedJobOfferIds().add(jobOfferId);
		studentRepository.save(student);
		return new JobOfferDTO(jobOfferRepository.findById(jobOfferId).orElseThrow(JobOfferNotFoundException::new));
	}

	public List<Long> getViewedJobOffersByStudentId(Long studentId) {
		return studentRepository.findById(studentId).orElseThrow(StudentNotFoundException::new).getViewedJobOfferIds();
	}
}
