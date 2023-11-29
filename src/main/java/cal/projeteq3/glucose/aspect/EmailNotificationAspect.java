package cal.projeteq3.glucose.aspect;

import cal.projeteq3.glucose.dto.AppointmentDTO;
import cal.projeteq3.glucose.dto.CvFileDTO;
import cal.projeteq3.glucose.dto.contract.ContractDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobApplicationDTO;
import cal.projeteq3.glucose.dto.jobOffer.JobOfferDTO;
import cal.projeteq3.glucose.dto.user.ManagerDTO;
import cal.projeteq3.glucose.model.Appointment;
import cal.projeteq3.glucose.model.Department;
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

//    All users Service
    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.*.signContract*(..))",
            returning = "signedContract")
    public void contractSignatureNotification(ContractDTO signedContract) {
        if (signedContract.getStudentSignature() != null && signedContract.getEmployerSignature() != null && signedContract.getManagerSignature() == null) {
            String body = "Vous avez un nouveau contrat qui est maintenant prêt à être signé." + getContractHtml(signedContract);

            Optional<Student> student = studentRepository.findFirstByFirstNameAndLastName(
                    signedContract.getStudentSignature().getFirstName(),
                    signedContract.getStudentSignature().getLastName());

            student.ifPresent(stud -> {
                Manager manager = managerRepository.findFirstByDepartment(stud.getDepartment());
                sendEmail(manager, "Contrat prêt à signer", body);
            });

        } else if (signedContract.getManagerSignature() != null) {
            String body = "Votre contrat est maintenant complet. Vous pouvez le télécharger.";
            String subject = "Contrat maintenant complet";

            assert signedContract.getStudentSignature() != null;
            Optional<Student> optStudent = studentRepository.findFirstByFirstNameAndLastName(
                    signedContract.getStudentSignature().getFirstName(),
                    signedContract.getStudentSignature().getLastName());
            optStudent.ifPresent(student -> sendEmail(student, subject, body));

            Optional<Employer> optEmployer = employerRepository.findFirstByFirstNameAndLastName(
                    signedContract.getEmployerSignature().getFirstName(),
                    signedContract.getEmployerSignature().getLastName());
            optEmployer.ifPresent(employer -> sendEmail(employer, subject, body));


        }
    }

    //    Manager Service
    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.ManagerService.update*(..))",
            returning = "result")
    public void managerUpdatesNotification(Object result) {
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

//    Employer Service
    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.update*(..))",
            returning = "result")
    public void employerUpdateNotification(Object result) {

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
    public void employerCreateNotification(JobOfferDTO jobOfferDTO) {
        String body = "Une nouvelle offre de stage dans votre département est disponible. " +
                "Vous pouvez la consulter et l'accepter ou la refuser." + getJobOfferHtml(jobOfferDTO);

        List<Manager> managers = managerRepository.findAllByDepartment(jobOfferDTO.getDepartment()).stream()
                .peek(manager -> sendEmail(manager, "Nouvelle offre de stage", body)).toList();
        System.out.println("MANAGERS: " + managers.size());
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.addAppointmentByJobApplicationId(..))",
            returning = "jobApplicationDTO")
    public void employerAppointment(JobApplicationDTO jobApplicationDTO) {
        jobApplicationRepository.findById(jobApplicationDTO.getId()).ifPresent(jobApplication -> {
            String body = getAppointmentHtml(jobApplication);
            Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());
            sendEmail(student, "Vous avez un nouvel entretien", body);
        });
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.acceptApplication(..))",
            returning = "jobApplicationDTO")
    public void employerAcceptNotification(JobApplicationDTO jobApplicationDTO) {
        String body = "Votre candidature a été acceptée. Vous pouvez maintenant signer votre contrat pour l'application suivante: " +
                getJobOfferHtml(jobApplicationDTO.getJobOffer());

        Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());

        sendEmail(student, "Candidature Accepté!", body);
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.EmployerService.refuseApplication(..))",
            returning = "jobApplicationDTO")
    public void employerRefuseNotification(JobApplicationDTO jobApplicationDTO) {
        String body = "Nous sommes désolés de vous informer que votre candidature a été refusée pour l'application suivante: " +
                getJobOfferHtml(jobApplicationDTO.getJobOffer());

        Student student = studentRepository.findByMatricule(jobApplicationDTO.getStudent().getMatricule());

        sendEmail(student, "Candidature Refusé", body);
    }

//    Student Service
    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.StudentService.addCv(..))",
            returning = "cvFileDTO")
    public void addCvNotification(CvFileDTO cvFileDTO) {
        sendCvEmail(cvFileDTO);
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.StudentService.applyJobOffer(..))",
            returning = "jobOfferDTO")
    public void applyJobOfferNotification(JobOfferDTO jobOfferDTO) {
        String body = "Vous avez une nouvelle candidature sur l'offre de stage suivante: " + getJobOfferHtml(jobOfferDTO);

        Optional<Employer> employer = employerRepository.findByJobOffers_id(jobOfferDTO.getId());
        employer.ifPresent(emp -> sendEmail(emp, "Nouvelle candidature", body));
    }

    @AfterReturning(
            pointcut = "execution(* cal.projeteq3.glucose.service.StudentService.setAppointmentToChosen(..))",
            returning = "appointmentDTO")
    public void chosenAppointmentNotification(AppointmentDTO appointmentDTO) {
        String body = "Vous avez un nouvel entretien. " +
                "<p><strong>Date: </strong> " + appointmentDTO.getAppointmentDate().format(formatter) + "</p>";

        Optional<Employer> employer = employerRepository.findByJobOffers_id(appointmentDTO.getJobApplication().getJobOffer().getId());
        employer.ifPresent(emp -> sendEmail(emp, "Nouvel entretien", body));
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
                employer.ifPresent(emp -> sendEmail(emp, "Offre de stage rejetée", refusedBody));
                break;
            case EXPIRED:
                final String expiredBody = "Votre offre de stage est expirée. Vous pouvez la modifier et la soumettre à nouveau pour qu'elle soit acceptée";
                Optional<Employer> employer1 = employerRepository.findByJobOffers_id(result.getId());
                employer1.ifPresent(emp -> sendEmail(emp, "Offre de stage expirée", expiredBody));
                break;
        }
    }

    private void sendCvEmail(CvFileDTO cvFileDTO) {
        switch (CvState.valueOf(cvFileDTO.getCvState().toString())) {
            case ACCEPTED:
                final String acceptedBody = "Votre CV a été mis à jour et est maintenant accepté. Vous pouvez maintenant postuler à des offres de stage.";
                Optional<User> acceptedStudent = userRepository.findById(cvFileDTO.getStudentId());
                acceptedStudent.ifPresent(student -> sendEmail(student, "CV accepté", acceptedBody));
                break;
            case REFUSED:
                final String refusedBody = "Veuillez modifier votre CV et le soumettre à nouveau pour qu'il soit accepté" +
                        "<p><strong>Raison du refus: </strong>" + cvFileDTO.getRefusReason() + "</p>";
                Optional<User> refusedStudent = userRepository.findById(cvFileDTO.getStudentId());
                refusedStudent.ifPresent(student -> sendEmail(student, "CV rejeté", refusedBody));
                break;
            case SUBMITTED:
                final String submittedBody = "Un nouveau CV est à approuver dans votre département.";
                Optional<Student> submittedStudent = studentRepository.findById(cvFileDTO.getStudentId());
                if (submittedStudent.isPresent()) {
                    List<Manager> manager = managerRepository.findAllByDepartment(submittedStudent.get().getDepartment()).stream()
                            .peek((User u) -> sendEmail(u, "Nouveau cv disponible", submittedBody)).toList();

                    System.out.println("MANAGERS: " + manager.size());
                } else {
                    System.out.println("Student not found");
                }
                break;
        }
    }

    private void sendEmail(User user, String subject, String content) {
        emailService.sendEmail(user.getEmail(), subject, content);
    }

    private String getAppointmentHtml(JobApplication jobApplication) {
        StringBuilder appointmentsHtml = new StringBuilder();
        for (Appointment appointment : jobApplication.getAppointments()) {
            appointmentsHtml.append("<p><strong>Date: </strong> ")
                    .append(appointment.getAppointmentDate().format(formatter)).append("</p>");
        }

        return "<h1>Détails de l'application</h1>" +
                "<p><strong>Offre de stage: </strong> " + jobApplication.getJobOffer().getTitle() + "</p>" +
                "<h2>Rendez-vous</h2>" + appointmentsHtml;
    }

    private static String getJobOfferHtml(JobOfferDTO result) {
        return "<h1>" + result.getTitle() + "</h1>" +
                "<p>" + result.getDescription() + "</p>" +
                "<h4>" + result.getLocation() + "</h4>" +
                "<p><strong>Salaire par heure: </strong>" + result.getSalary() + "</p>" +
                "<p><strong>Date de départ: </strong>" + result.getStartDate() + "</p>" +
                "<p><strong>Nombre de semaine: </strong>" + result.getDuration() + "</p>" +
                "<p><strong>Nombre d'heure par semaine: </strong>" + result.getHoursPerWeek() + "</p>";
    }

    private static String getContractHtml(ContractDTO signedContract) {
        return "<h1>Détails du contrat</h1>" +
                "<p><strong>Nom de l'étudiant: </strong> " + signedContract.getStudentName() + "</p>" +
                "<p><strong>Pour le stage: </strong> " + signedContract.getJobOfferName() + "</p>" +
                "<p><strong>Dans la compagnie: </strong> " + signedContract.getJobOfferCompany() + "</p>" +
                "<h2>Signatures</h2>" +
                "<p><strong>Signature de l'employer: </strong> " + (signedContract.getEmployerSignature() != null ? "Signed" : "Not Signed") + "</p>" +
                "<p><strong>Signature de l'étudiant: </strong> " + (signedContract.getStudentSignature() != null ? "Signed" : "Not Signed") + "</p>" +
                "<p><strong>Signature du gestionnaire: </strong> " + (signedContract.getManagerSignature() != null ? "Signed" : "Not Signed") + "</p>";
    }

}

