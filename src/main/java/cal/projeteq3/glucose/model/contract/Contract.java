package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.exception.unauthorizedException.SignaturePrerequisitNotMet;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import jakarta.persistence.*;
import lombok.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contract {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Employer employer;

    @ManyToOne
    private Student student;

    @ManyToOne
    private JobOffer jobOffer;

    @OneToOne
    private Signature employerSignature;

    @OneToOne
    private Signature studentSignature;

    @OneToOne
    private Signature managerSignature;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creationDate = LocalDateTime.now();

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastModificationDate = LocalDateTime.now();

    public Contract(Employer employer, Student student, JobOffer jobOffer) {
        this.employer = employer;
        this.student = student;
        this.jobOffer = jobOffer;
    }

    public void setEmployerSignature(Signature employerSignature) {
        if (this.employerSignature != null)
            throw new IllegalStateException("Employer signature already set");

        this.employerSignature = employerSignature;
        lastModificationDate = LocalDateTime.now();
    }

    public void setStudentSignature(Signature studentSignature) {
        if (this.studentSignature != null)
            throw new IllegalStateException("Student signature already set");
        if (this.employerSignature == null)
            throw new SignaturePrerequisitNotMet();

        this.studentSignature = studentSignature;
        lastModificationDate = LocalDateTime.now();
    }

    public void setManagerSignature(Signature directorSignature) {
        if (this.managerSignature != null)
            throw new IllegalStateException("Director signature already set");
        if (this.employerSignature == null || this.studentSignature == null)
            throw new SignaturePrerequisitNotMet();

        this.managerSignature = directorSignature;
        lastModificationDate = LocalDateTime.now();
    }

    public String generateContractPDF(Manager manager) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.addTitle("Contract");

            BaseFont arialFont = BaseFont.createFont("src/main/resources/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font normalTextFont = new Font(arialFont, 10, Font.NORMAL, BaseColor.BLACK);
            Font boldTextFont = new Font(arialFont, 10, Font.BOLD, BaseColor.BLACK);

            // Centered title
            Paragraph title = new Paragraph("Entente Intervenue entre les parties suivantes", boldTextFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Introduction
            Paragraph intro = new Paragraph("Dans le cadre de la formule Alternance travail-études du programme de VAR :DEPARTMENT, les parties citées ci-dessous :", normalTextFont);
            intro.setAlignment(Element.ALIGN_CENTER);
            document.add(intro);

            // Presentation of Education Center
            Paragraph presentationEducationCenter = new Paragraph("Le Cégep André-Laurendeau, corporation légalement constituée, situé au 1111, rue Lapierre, Lasalle, Québec, H8N 2J4", normalTextFont);
            presentationEducationCenter.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationEducationCenter);

            // Presentation of Director
            Paragraph presentationDirector = new Paragraph("ici représenté par " + manager.getFirstName() + " " + manager.getLastName() + " ci-après désigné \"Le Collège\".", normalTextFont);
            presentationDirector.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationDirector);

            // "et" paragraph
            Paragraph et = new Paragraph("et", boldTextFont);
            et.setAlignment(Element.ALIGN_CENTER);
            document.add(et);

            // Presentation of Employer
            Paragraph presentationEmployer = new Paragraph("L'entreprise " + employer.getOrganisationName() + " ayant sa place d'affaire au :", normalTextFont);
            presentationEmployer.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationEmployer);

            // Presentation of Organisation
            Paragraph presentationOrganisation = new Paragraph("Entreprise\n" +
                    "Nom de l’entreprise\n" +
                    employer.getOrganisationName() + "\n" +
                    "Personne contact en entreprise\n" +
                    employer.getFirstName() + " " + employer.getLastName() + "\n" +
                    "Courriel de la personne contact en entreprise\n" +
                    employer.getEmail() + "\n" +
                    "Téléphone\n" +
                    employer.getOrganisationPhone(), normalTextFont);
            presentationOrganisation.setAlignment(Element.ALIGN_LEFT);
            document.add(presentationOrganisation);

            // Presentation of Student
            Paragraph presentationStudent = new Paragraph("L’élève, " + student.getFirstName() + " " + student.getLastName() + ",", normalTextFont);
            presentationStudent.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationStudent);

            // Presentation of Job Offer
            Paragraph presentationJobOffer = new Paragraph("Conviennent des conditions de stage suivantes :", normalTextFont);
            presentationJobOffer.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationJobOffer);

            // Presentation of Job Offer Location
            Paragraph presentationJobOfferLocation = new Paragraph("Lieu du stage\n" +
                    "Service ou département\n" +
                    employer.getOrganisationName() + "\n" +
                    "Adresse\n" +
                    jobOffer.getLocation(), normalTextFont);
            presentationJobOfferLocation.setAlignment(Element.ALIGN_LEFT);
            document.add(presentationJobOfferLocation);

            // Presentation of Supervisor
            Paragraph presentationSupervisor = new Paragraph("Superviseur du stage\n" +
                    "Nom\n" +
                    jobOffer.getEmployer().getLastName() + "\n" +
                    "Prénom\n" +
                    jobOffer.getEmployer().getFirstName() + "\n" +
                    "Courriel\n" +
                    jobOffer.getEmployer().getEmail() + "\n" +
                    "Téléphone\n" +
                    jobOffer.getEmployer().getOrganisationPhone(), normalTextFont);

            presentationSupervisor.setAlignment(Element.ALIGN_LEFT);
            document.add(presentationSupervisor);

            // Presentation of Internship Duration
            Paragraph presentationInternshipDuration = new Paragraph("Durée de stage (durée minimale de 8 semaines et de 224 heures)\n" +
                    "Date de début\n" +
                    jobOffer.getStartDate().toString() + "\n" +
                    "Date de fin\n" +
                    jobOffer.getStartDate().plusWeeks(jobOffer.getDuration()) + "\n" +
                    "Nombre total de semaines\n" +
                    jobOffer.getDuration(), normalTextFont);

            presentationInternshipDuration.setAlignment(Element.ALIGN_LEFT);
            document.add(presentationInternshipDuration);

            // Presentation of Internship Schedule
            Paragraph presentationInternshipSchedule = new Paragraph("Horaire de travail\n" +
                    "Heure de début\n" +
                    "8:00\n" +
                    "Heure de fin\n" +
                    "16:00\n" +
                    "Nombre total d’heures par semaine\n" +
                    jobOffer.getHoursPerWeek(), normalTextFont);

            presentationInternshipSchedule.setAlignment(Element.ALIGN_LEFT);
            document.add(presentationInternshipSchedule);

            // Presentation of Internship Work Days
            Paragraph presentationInternshipWorkDays = new Paragraph("Jours de travail\n" +
                    "Lundi\n" +
                    "Mardi\n" +
                    "Mercredi\n" +
                    "Jeudi\n" +
                    "Vendredi", normalTextFont);

            presentationInternshipWorkDays.setAlignment(Element.ALIGN_LEFT);
            document.add(presentationInternshipWorkDays);

            // Presentation of Internship Salary
            Paragraph presentationInternshipSalary = new Paragraph("Salaire\n" +
                    "Salaire horaire\n" +
                    jobOffer.getSalary() + "$/heure", normalTextFont);

            presentationInternshipSalary.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationInternshipSalary);

            // Presentation of Responsibilities
            Paragraph presentationResponsibilities = new Paragraph("Conformément aux objectifs visés pour ce stage, les tâches et les mandats suivants seront confiés à l’élève stagiaire :", normalTextFont);

            presentationResponsibilities.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationResponsibilities);


            // Presentation of Responsibilities
            Paragraph listResponsabilities = new Paragraph("Responsabilités\n" +
                    "Le Collège s’engage à :\n" +
                    "•\tfournir à l’entreprise tous les renseignements concernant les conditions spécifiques du programme d’études et du programme d’alternance travail-études;\n" +
                    "•\tcollaborer, au besoin, à la définition du plan de stage;\n" +
                    "•\teffectuer un suivi de l’élève stagiaire pendant la durée du stage;\n" +
                    "•\tfournir à l’entreprise les documents nécessaires à l’évaluation de l’élève stagiaire;\n" +
                    "•\tcollaborer avec l’entreprise pour résoudre des problèmes qui pourraient survenir en cours de stage, le cas échéant;\n" +
                    "•\tconserver tous les dossiers de stage et les rapports des élèves;\n" +
                    "•\tfournir à l’entreprise le formulaire d’attestation de participation à un stage de formation admissible après réception du formulaire « Déclaration relative au crédit d’impôt remboursable pour les stages ».\n" +
                    "L’entreprise s’engage à :\n" +
                    "•\tembaucher l’élève stagiaire aux conditions précisées dans la présente entente;\n" +
                    "•\tdésigner un superviseur de stage qui assurera l’encadrement de l’élève stagiaire pour toute la durée du stage;\n" +
                    "•\tmettre en place des mesures d’accueil, d’intégration et d’encadrement de l’élève stagiaire;\n" +
                    "•\tprocéder à l’évaluation de l’élève stagiaire.\n" +
                    "•\tretourner le formulaire « Déclaration des heures travaillées » dûment rempli.\n"
                    , normalTextFont);

            listResponsabilities.setAlignment(Element.ALIGN_LEFT);
            document.add(listResponsabilities);

            // Presentation of Signature
            Paragraph presentationSignature = new Paragraph("Signatures\n" +
                    "Les parties s’engagent à respecter cette entente de stage\n" +
                    "En foi de quoi les parties ont signé,", normalTextFont);

            presentationSignature.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationSignature);

            // Presentation of Student Signature
            String studentSignatureString = "L'èlève :\n" +
                    "VAR :STUDENT_SIGNATURE";
            if (studentSignature != null)
                studentSignatureString = studentSignatureString.replace("VAR :STUDENT_SIGNATURE", studentSignature.getFirstName() + " " + studentSignature.getLastName());
            else
                studentSignatureString = studentSignatureString.replace("VAR :STUDENT_SIGNATURE", "__________________________");
            Paragraph presentationStudentSignature = new Paragraph(studentSignatureString, normalTextFont);

            presentationStudentSignature.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationStudentSignature);

            // Presentation of Employer Signature
            String employerSignatureString = "Pour l’entreprise :\n" +
                    "VAR :EMPLOYER_SIGNATURE";
            if (employerSignature != null)
                employerSignatureString = employerSignatureString.replace("VAR :EMPLOYER_SIGNATURE", employerSignature.getFirstName() + " " + employerSignature.getLastName());
            else
                employerSignatureString = employerSignatureString.replace("VAR :EMPLOYER_SIGNATURE", "__________________________");

            Paragraph presentationEmployerSignature = new Paragraph(employerSignatureString, normalTextFont);

            presentationEmployerSignature.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationEmployerSignature);

            // Presentation of Director Signature
            String directorSignatureString = "Pour le Collège :\n" +
                    "VAR :DIRECTOR_SIGNATURE";

            if (managerSignature != null)
                directorSignatureString = directorSignatureString.replace("VAR :DIRECTOR_SIGNATURE", managerSignature.getFirstName() + " " + managerSignature.getLastName());
            else
                directorSignatureString = directorSignatureString.replace("VAR :DIRECTOR_SIGNATURE", "__________________________");

            Paragraph presentationDirectorSignature = new Paragraph(directorSignatureString, normalTextFont);

            presentationDirectorSignature.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationDirectorSignature);

            // Presentation of Footer
            Paragraph presentationFooter = new Paragraph("Toute personne morale ou physique faisant affaire avec le Cégep André-Laurendeau doit prendre connaissance des politiques et procédures internes la concernant. En cas de manquements à une politique ou à la Loi de la part d’une personne issue du milieu de stage, ce dernier se verrait exclu de la liste des milieux approuvés.\n" +
                    "Contactez votre personne-ressources pour plus d’information ou consultez : https://claurendeau.qc.ca/?s=ATÉ", normalTextFont);

            presentationFooter.setAlignment(Element.ALIGN_CENTER);
            document.add(presentationFooter);

            document.close();
            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);

        }catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
