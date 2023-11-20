package cal.projeteq3.glucose.aspect;

import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.StudentRepository;
import cal.projeteq3.glucose.repository.UserRepository;
import cal.projeteq3.glucose.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailNotificationAspect {

    private EmailService emailService;
    private UserRepository userRepository;
    private StudentRepository studentRepository;

//    TODO: Change messages
    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.update*(..))",
            returning = "result")
    public void managerUpdatesNotification(JoinPoint joinPoint, Object result) {
        // Example: Send an email when an update is successful
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Method name: " + methodName);

        if (result instanceof ManagerDTO updatedManager) {
            String emailBody = "Manager " + updatedManager.getFirstName() + " " + updatedManager.getLastName() + " has been updated.";
            emailService.sendEmail("patatepoilu876@gmail.com", "Louis Philippe", emailBody, "");
        } else if (result instanceof CvFileDTO) {
            System.out.println("CV");
//            User student = userRepository.findById(((CvFileDTO) result).getStudentId()).orElseThrow();
//            emailService.sendEmail(student.getEmail(), student.getFirstName() + " " + student.getLastName(), "CV", "Votre CV a été mis à jour");
        } else if (result instanceof JobOfferDTO) {
            System.out.println("JOB offer");
            if (((JobOfferDTO) result).getJobOfferState().equals(JobOfferState.OPEN)) {
                List<Student> students = studentRepository.findAllByDepartment(((JobOfferDTO) result).getDepartment());
                for (User s : students) {
                    emailService.sendEmail(s.getEmail(), s.getFirstName() + " " + s.getLastName(), "Job offer", "Une nouvelle offre d'emploi est disponible");
                }
            }
        }
        // Add similar logic for other update methods
    }

}

