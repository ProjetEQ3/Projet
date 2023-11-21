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
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailNotificationAspect {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;


//    TODO: Change messages
    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.update*(..))",
            returning = "result")
    public void managerUpdatesNotification(JoinPoint joinPoint, Object result) {
        // Example: Send an email when an update is successful

        if (result instanceof ManagerDTO updatedManager) {
            String emailBody = "Manager " + updatedManager.getFirstName() + " " + updatedManager.getLastName() + " has been updated.";
            emailService.sendEmail("patatepoilu876@gmail.com", "Louis Philippe", emailBody, "");
        } else if (result instanceof CvFileDTO) {
            System.out.println("CV");
//            User student = userRepository.findById(((CvFileDTO) result).getStudentId()).orElseThrow();
//            emailService.sendEmail(student.getEmail(), student.getFirstName() + " " + student.getLastName(), "CV", "Votre CV a été mis à jour");
        } else if (result instanceof JobOfferDTO) {
            if (((JobOfferDTO) result).getJobOfferState().equals(JobOfferState.OPEN)) {
                List<Student> temp = studentRepository.findAllByDepartment(((JobOfferDTO) result).getDepartment())
                        .stream().peek((User u) -> sendEmail(u, "Nouvelle offre de stage disponible",
                                "Une nouvelle offre de stage est disponible dans votre département." + getJobOfferHtml((JobOfferDTO) result))).toList();
                System.out.println("STUDENTS: " + temp.size());
            }
        } else if (result instanceof User) {
            System.out.println("USER");
            sendEmail((User) result, "User", "Votre compte a été mis à jour");
        }
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.rejectCv(..))",
            returning = "result")
    public void managerRejectNotification(JoinPoint joinPoint, Object result) {
        System.out.println("CV REJECTED: " + result);
        User student = userRepository.findById(((CvFileDTO) result).getStudentId()).orElseThrow();
        sendEmail(student, "Votre CV a été rejeté", "Votre CV a été rejeté");
    }

    private void sendEmail(User user, String subject, String content){
        emailService.sendEmail(user.getEmail(), user.getFirstName() + " " + user.getLastName(), subject, content);
    }

    private static String getJobOfferHtml(JobOfferDTO result) {
        return "<h1>" + result.getTitle() + "</h1>" +
                "<p>" + result.getDescription() + "</p>" +
                "<h4>" + result.getLocation() + "</h4>" +
                "<p><strong>Salaire par heure:</strong>" + result.getSalary() + "</p>" +
                "<p><strong>Date de départ:</strong>" + result.getStartDate() + "</p>" +
                "<p><strong>Nombre de semaine:</strong>" + result.getDuration() + "</p>" +
                "<p><strong>Nombre d'heure par semaine:</strong>" + result.getHoursPerWeek() + "</p>";
    }

}

