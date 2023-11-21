package cal.projeteq3.glucose.aspect;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.jobOffer.JobApplication;
import cal.projeteq3.glucose.model.jobOffer.JobOfferState;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import cal.projeteq3.glucose.model.user.User;
import cal.projeteq3.glucose.repository.*;
import cal.projeteq3.glucose.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Aspect
@Component
@RequiredArgsConstructor
public class EmailNotificationAspect {
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final EmployerRepository employerRepository;
    private final ManagerRepository managerRepository;
    private final JobApplicationRepository jobApplicationRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE 'le' dd MMMM 'à' HH'h'mm", Locale.FRENCH);

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.update*(..))",
            returning = "result")
    public void managerUpdatesNotification(JoinPoint joinPoint, Object result) {
        if (result instanceof ManagerDTO updatedManager) {
            String emailBody = "Le gestionnaire " + updatedManager.getFirstName() + " " + updatedManager.getLastName() + " a été mis à jour.";
            User manager = userRepository.findById(updatedManager.getId()).orElseThrow();
            sendEmail(manager, "Gestionnaire mis à jour", emailBody);
        } else if (result instanceof CvFileDTO cvFileDTO) {
            sendCvEmail(cvFileDTO);
        } else if (result instanceof JobOfferDTO jobOfferDTO) {
            sendJobOfferEmail(jobOfferDTO);
        }
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.signContract(..))",
            returning = "contractDTO")
    public void managerSignature(JoinPoint joinPoint, ContractDTO contractDTO) {
        String body = "Votre contrat est maintenant complet. Vous pouvez le télécharger.";

        Student student = studentRepository.findFirstByFirstNameAndLastName(
                contractDTO.getStudentSignature().getFirstName(),
                contractDTO.getStudentSignature().getLastName()).orElseThrow();
        Employer employer = employerRepository.findFirstByFirstNameAndLastName(
                contractDTO.getEmployerSignature().getFirstName(),
                contractDTO.getEmployerSignature().getLastName()).orElseThrow();

        sendEmail(student, "Votre contrat est maintenant complet", body);
        sendEmail(employer, "Votre contrat est maintenant complet", body);
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.addAppointmentByJobApplicationId(..))",
            returning = "jobApplicationDTO")
    public void employerAppointment(JoinPoint joinPoint, JobApplicationDTO jobApplicationDTO) {
        JobApplication jobApplication = jobApplicationRepository.findById(jobApplicationDTO.getId()).orElseThrow();
        String body = getAppointmentHtml(jobApplication);

        Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());

        sendEmail(student, "Vous avez un nouvel entretien", body);
    }

    private void sendJobOfferEmail(JobOfferDTO result) {
        switch (JobOfferState.valueOf(result.getJobOfferState().toString())) {
            case OPEN:
                final String openBody = "Une nouvelle offre de stage est disponible dans votre département." + getJobOfferHtml(result);
                List<Student> temp = studentRepository.findAllByDepartment(result.getDepartment())
                        .stream().peek((User u) -> sendEmail(u, "Nouvelle offre de stage disponible", openBody)).toList();
                System.out.println("STUDENTS: " + temp.size());
                break;
            case REFUSED:
                final String refusedBody = "Veuillez modifier votre offre de stage et la soumettre à nouveau pour qu'elle soit acceptée" +
                        "<p><strong>Raison du refus: </strong>" + result.getRefusReason() + "</p>";
                Employer employer = employerRepository.findByJobOffers_id(result.getId()).orElseThrow();
                sendEmail(employer, "Votre offre de stage a été rejetée", refusedBody);
                break;
            case EXPIRED:
                final String expiredBody = "Votre offre de stage a expirée. Vous pouvez la modifier et la soumettre à nouveau pour qu'elle soit acceptée";
                Employer employer1 = employerRepository.findByJobOffers_id(result.getId()).orElseThrow();
                sendEmail(employer1, "Votre offre de stage a expirée", expiredBody);
                break;
        }
    }

    private void sendCvEmail(CvFileDTO cvFileDTO) {
        switch (CvState.valueOf(cvFileDTO.getCvState().toString())) {
            case ACCEPTED:
//                TODO: Décommenter quand le command line runner sera retiré
                final String acceptedBody = "Votre CV a été mis à jour et est maintenant accepté. Vous pouvez maintenant postuler à des offres de stage.";
                User acceptedStudent = userRepository.findById(cvFileDTO.getStudentId()).orElseThrow();
                sendEmail(acceptedStudent, "Votre CV a été accepté", acceptedBody);
                break;
            case REFUSED:
                final String refusedBody = "Veuillez modifier votre CV et le soumettre à nouveau pour qu'il soit accepté" +
                        "<p><strong>Raison du refus: </strong>" + cvFileDTO.getRefusReason() + "</p>";
                User refusedStudent = userRepository.findById(cvFileDTO.getStudentId()).orElseThrow();
                sendEmail(refusedStudent, "Votre CV a été rejeté", refusedBody);
                break;
            case SUBMITTED:
                final String submittedBody = "Un nouveau CV est à approuver dans votre département.";
                Student submittedStudent = studentRepository.findById(cvFileDTO.getStudentId()).orElseThrow();
                List<Manager> manager = managerRepository.findAllByDepartment(submittedStudent.getDepartment()).stream()
                        .peek((User u) -> sendEmail(u, "Un nouveau cv est disponible", submittedBody)).toList();
                System.out.println("MANAGERS: " + manager.size());
                break;
        }
    }

    private void sendEmail(User user, String subject, String content) {
        emailService.sendEmail(user.getEmail(), user.getFirstName() + " " + user.getLastName(), subject, content);
    }

    private String getAppointmentHtml(JobApplication jobApplication) {
        StringBuilder appointmentsHtml = new StringBuilder();
        for (Appointment appointment : jobApplication.getAppointments()) {
            appointmentsHtml.append("<p><strong>Date:</strong> ")
                    .append(appointment.getAppointmentDate().format(formatter)).append("</p>");
        }

        return "<h1>Détails de l'application</h1>" +
                "<p><strong>Offre de stage:</strong> " + jobApplication.getJobOffer().getTitle() + "</p>" +
                "<h2>Rendez-vous</h2>" + appointmentsHtml;
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

