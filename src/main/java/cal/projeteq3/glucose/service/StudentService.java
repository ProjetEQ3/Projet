package cal.projeteq3.glucose.service;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.JobOfferDTO;
import cal.projeteq3.glucose.dto.auth.RegisterStudentDTO;
import cal.projeteq3.glucose.dto.user.StudentDTO;
import cal.projeteq3.glucose.exception.request.StudentNotFoundException;
import cal.projeteq3.glucose.exception.unauthorizedException.StudentHasAlreadyCVException;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.repository.CvFileRepository;
import cal.projeteq3.glucose.repository.JobOfferRepository;
import cal.projeteq3.glucose.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CvFileRepository cvFileRepository;
    private final JobOfferRepository jobOfferRepository;

    @Autowired
    public StudentService(
            StudentRepository studentRepository,
            CvFileRepository cvFileRepository,
            JobOfferRepository jobOfferRepository) {
        this.studentRepository = studentRepository;
        this.cvFileRepository = cvFileRepository;
        this.jobOfferRepository = jobOfferRepository;
    }

    // database operations here

    public StudentDTO createStudent(RegisterStudentDTO registerStudentDTO) {
        Student student = Student.builder()
                .email(registerStudentDTO.getRegisterDTO().getEmail())
                .password(registerStudentDTO.getRegisterDTO().getPassword())
                .firstName(registerStudentDTO.getStudentDTO().getFirstName())
                .lastName(registerStudentDTO.getStudentDTO().getLastName())
                .department(String.valueOf(registerStudentDTO.getStudentDTO().getDepartment()))
                .matricule(registerStudentDTO.getStudentDTO().getMatricule())
                .build();
        return new StudentDTO(studentRepository.save(student));
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentDTO::new).collect(Collectors.toList());
    }

    public StudentDTO getStudentByID(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        return new StudentDTO(student);
    }

    public StudentDTO updateStudent(Long id, StudentDTO updatedStudent) {
        Optional<Student> existingStudent = studentRepository.findById(id);
        if(existingStudent.isPresent()) {
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

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public CvFileDTO addCv(Long studentId, CvFileDTO cvFile){
        Student student;
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isEmpty()) throw new StudentNotFoundException(studentId);
        student = studentOptional.get();
        CvFile cvExiste = student.getCvFile();
        if(cvExiste != null) throw new StudentHasAlreadyCVException();
        CvFile cv = CvFile.builder()
					.fileName(cvFile.getFileName())
          .fileData(cvFile.getFileData())
          .cvState(cvFile.getCvState())
          .build();
        student.setCvFile(cv);
        return new CvFileDTO(studentRepository.save(student).getCvFile());
    }

    public boolean deleteCv(Long studentId){
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isEmpty()) throw new StudentNotFoundException(studentId);
        Student student = studentOptional.get();
        CvFile cvExiste = student.getCvFile();
        student.deleteCv();
        cvFileRepository.delete(cvExiste);
        return true;
    }

    public List<JobOfferDTO> getJobOffersByDepartment(Department department){
        return jobOfferRepository.findJobOffersByDepartment(department)
                .stream().map(JobOfferDTO::new)
                .collect(Collectors.toList());
    }

    public List<JobOfferDTO> getOpenJobOffersByDepartment(Department department){
        return jobOfferRepository.findJobOffersByDepartmentAndJobOfferState(department, JobOfferState.OPEN)
                .stream().map(JobOfferDTO::new)
                .collect(Collectors.toList());
    }

}
