package cal.projeteq3.glucose.aspect;

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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE 'le' dd MMMM 'à' HH'h'mm", Locale.FRENCH);

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.update*(..))",
            returning = "result")
    public void managerUpdatesNotification(JoinPoint joinPoint, Object result) {
        if (result instanceof ManagerDTO updatedManager) {
            String emailBody = "Le gestionnaire " + updatedManager.getFirstName() + " " + updatedManager.getLastName() + " a été mis à jour.";
            Optional<User> optManager = userRepository.findById(updatedManager.getId());
            optManager.ifPresent(manager -> sendEmail(manager, "Gestionnaire mis à jour", emailBody));
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

        Optional<Student> optStudent = studentRepository.findFirstByFirstNameAndLastName(
                contractDTO.getStudentSignature().getFirstName(),
                contractDTO.getStudentSignature().getLastName());
        optStudent.ifPresent(student -> sendEmail(student, "Votre contrat est maintenant complet", body));

        Optional<Employer> optEmployer = employerRepository.findFirstByFirstNameAndLastName(
                contractDTO.getEmployerSignature().getFirstName(),
                contractDTO.getEmployerSignature().getLastName());
        optEmployer.ifPresent(employer -> sendEmail(employer, "Votre contrat est maintenant complet", body));
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.update*(..))",
            returning = "result")
    public void employerUpdateNotification(JoinPoint joinPoint, Object result) {

        if (result instanceof Employer updatedEmployer) {
            String emailBody = "L'employeur " + updatedEmployer.getFirstName() + " " + updatedEmployer.getLastName() + " a été mis à jour.";
            Optional<User> optEmployer = userRepository.findById(updatedEmployer.getId());
            optEmployer.ifPresent(employer -> sendEmail(employer, "Employeur mis à jour", emailBody));
        } else if (result instanceof JobOfferDTO jobOfferDTO) {
            sendJobOfferEmail(jobOfferDTO);
        } else {
            System.out.println("Unhandled employer update notification");
        }
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.createJobOffer(..))",
            returning = "jobOfferDTO")
    public void employerCreateNotification(JoinPoint joinPoint, JobOfferDTO jobOfferDTO) {
        String body = "Une nouvelle offre de stage dans votre département est disponible. " +
                "Vous pouvez la consulter et l'accepter ou la refuser." + getJobOfferHtml(jobOfferDTO);

        List<Manager> managers = managerRepository.findAllByDepartment(jobOfferDTO.getDepartment()).stream()
                .peek(manager -> sendEmail(manager, "Nouvelle offre de stage", body)).toList();
        System.out.println("MANAGERS: " + managers.size());
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.addAppointmentByJobApplicationId(..))",
            returning = "jobApplicationDTO")
    public void employerAppointment(JoinPoint joinPoint, JobApplicationDTO jobApplicationDTO) {
        AtomicReference<String> body = new AtomicReference<>("");

        Optional<JobApplication> optApplication = jobApplicationRepository.findById(jobApplicationDTO.getId());
        optApplication.ifPresent((jobApplication) -> {
            body.set(getAppointmentHtml(jobApplication));

            Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());

            sendEmail(student, "Vous avez un nouvel entretien", body.get());
        });
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.acceptApplication(..))",
            returning = "jobApplicationDTO")
    public void employerAcceptNotification(JoinPoint joinPoint, JobApplicationDTO jobApplicationDTO) {
        String body = "Votre candidature a été acceptée. Vous pouvez maintenant signer votre contrat pour l'application suivante: " +
                getJobOfferHtml(jobApplicationDTO.getJobOffer());

        Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());

        sendEmail(student, "Candidature Accepté!", body);
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.refuseApplication(..))",
            returning = "jobApplicationDTO")
    public void employerRefuseNotification(JoinPoint joinPoint, JobApplicationDTO jobApplicationDTO) {
        String body = "Nous sommes désolés de vous informer que votre candidature a été refusée pour l'application suivante: " +
                getJobOfferHtml(jobApplicationDTO.getJobOffer());

        Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());

        sendEmail(student, "Candidature Refusé", body);
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
                Optional<Employer> employer = employerRepository.findByJobOffers_id(result.getId());
                employer.ifPresent(emp -> sendEmail(emp, "Votre offre de stage a été rejetée", refusedBody));
                break;
            case EXPIRED:
                final String expiredBody = "Votre offre de stage a expirée. Vous pouvez la modifier et la soumettre à nouveau pour qu'elle soit acceptée";
                Optional<Employer> employer1 = employerRepository.findByJobOffers_id(result.getId());
                employer1.ifPresent(emp -> sendEmail(emp, "Votre offre de stage a expirée", expiredBody));
                break;
        }
    }

    private void sendCvEmail(CvFileDTO cvFileDTO) {
        switch (CvState.valueOf(cvFileDTO.getCvState().toString())) {
            case ACCEPTED:
//                TODO: Décommenter quand le command line runner sera retiré
                final String acceptedBody = "Votre CV a été mis à jour et est maintenant accepté. Vous pouvez maintenant postuler à des offres de stage.";
                Optional<User> acceptedStudent = userRepository.findById(cvFileDTO.getStudentId());
                acceptedStudent.ifPresent(student -> sendEmail(student, "Votre CV a été accepté", acceptedBody));
                break;
            case REFUSED:
                final String refusedBody = "Veuillez modifier votre CV et le soumettre à nouveau pour qu'il soit accepté" +
                        "<p><strong>Raison du refus: </strong>" + cvFileDTO.getRefusReason() + "</p>";
                Optional<User> refusedStudent = userRepository.findById(cvFileDTO.getStudentId());
                refusedStudent.ifPresent(student -> sendEmail(student, "Votre CV a été rejeté", refusedBody));
                break;
            case SUBMITTED:
                final String submittedBody = "Un nouveau CV est à approuver dans votre département.";
                Optional<Student> submittedStudent = studentRepository.findById(cvFileDTO.getStudentId());
                if (submittedStudent.isPresent()) {
                    List<Manager> manager = managerRepository.findAllByDepartment(submittedStudent.get().getDepartment()).stream()
                            .peek((User u) -> sendEmail(u, "Un nouveau cv est disponible", submittedBody)).toList();

                    System.out.println("MANAGERS: " + manager.size());
                } else {
                    System.out.println("Student not found");
                }
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

