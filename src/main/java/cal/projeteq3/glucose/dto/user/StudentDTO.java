package cal.projeteq3.glucose.dto.user;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.model.Department;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.StudentSummary;
import cal.projeteq3.glucose.repository.JobApplicationRepository;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class StudentDTO extends UserDTO {
    private String matricule;
    private Department department;
    private CvFileDTO cvFile;
    private List<Long> jobApplications = new ArrayList<>();
    private StudentState studentState;

    @Builder
    public StudentDTO(
      Long id, String firstName, String lastName, String email, String role, String matricule, String department, StudentState studentState, List<Long> jobApplications
    ){
        super(id, firstName, lastName, email, role);
        this.matricule = matricule;
        this.department = Department.valueOf(department);
        this.studentState = studentState;
        this.jobApplications = jobApplications;
    }

    public StudentDTO(Student student){
        super(
          student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
          student.getRole().toString()
        );
        this.matricule = student.getMatricule();
        this.department = student.getDepartment();
        this.cvFile = student.getCvFile() == null ? null : new CvFileDTO(student.getCvFile());
    }

    public StudentDTO(Student student, Long jobApplicationId) {
        super(
          student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
          student.getRole().toString()
        );
        this.matricule = student.getMatricule();
        this.department = student.getDepartment();
        this.cvFile = student.getCvFile() == null ? null : new CvFileDTO(student.getCvFile());
        this.jobApplications.add(jobApplicationId);
    }

    public StudentDTO(String firstName, String lastName, String matricule, Department department){
        super(firstName, lastName, "STUDENT");
        this.matricule = matricule;
        this.department = department;
    }

    public void setStudentState(JobApplicationRepository jobApplicationRepository){
        if (this.getCvFile() == null) {
            studentState = StudentState.NO_CV;
        } else if (this.getJobApplications().isEmpty()) {
            studentState = StudentState.NO_JOB_APPLICATION;
        } else if (this.getJobApplications().stream().allMatch(jobApplicationId ->
                jobApplicationRepository.findById(jobApplicationId).orElseThrow().isNotChangeable())) {
            studentState = StudentState.NO_APPOINTMENT;
        } else if (this.getJobApplications().stream().noneMatch(jobApplicationId ->
                jobApplicationRepository.findById(jobApplicationId).orElseThrow().isAccepted())) {
            studentState = StudentState.NO_CONTRACT;
        } else {
            studentState = StudentState.COMPLETE;
        }
    }
}
