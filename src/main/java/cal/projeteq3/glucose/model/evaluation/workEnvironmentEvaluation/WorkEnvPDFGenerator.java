package cal.projeteq3.glucose.model.evaluation.workEnvironmentEvaluation;

import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class WorkEnvPDFGenerator {
    public static String generatePDF(WorkEnvironmentEvaluation workEnvironmentEvaluation) {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        JobOffer jobOffer = workEnvironmentEvaluation.getJobApplication().getJobOffer();
        Employer employer = jobOffer.getEmployer();
        Student student = workEnvironmentEvaluation.getJobApplication().getStudent();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.addTitle("Évaluation du stagière");

            BaseFont arialFont = BaseFont.createFont("src/main/resources/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleTextFont = new Font(arialFont, 20, Font.BOLD, BaseColor.BLACK);
            Font boldTextFont = new Font(arialFont, 10, Font.BOLD, BaseColor.BLACK);
            Font normalTextFont = new Font(arialFont, 10, Font.NORMAL, BaseColor.BLACK);

            document.add(new Paragraph("Évaluation du stagière", titleTextFont));

//            Entreprise
            Paragraph entreprise = new Paragraph("Identification de l'entreprise", titleTextFont);
            entreprise.add(new Paragraph("Nom de l'entreprise: " + employer.getOrganisationName(), normalTextFont));
            entreprise.add(new Paragraph("Personne contact: " + employer.getFirstName() + " " + employer.getLastName(), normalTextFont));
            entreprise.add(new Paragraph("Adresse: " + jobOffer.getLocation(), normalTextFont));
            entreprise.add(new Paragraph("Téléphone: " + employer.getOrganisationPhone(), normalTextFont));
            document.add(entreprise);

//            Stagiaire
            Paragraph stagiaire = new Paragraph("Identification du stagiaire", titleTextFont);
            stagiaire.add(new Paragraph("Nom du stagiaire: " + student.getFirstName() + " " + student.getLastName(), normalTextFont));
            stagiaire.add(new Paragraph("Date du stage: " + jobOffer.getStartDate(), normalTextFont));
            stagiaire.add(new Paragraph("Courriel: " + student.getEmail(), normalTextFont));
            document.add(stagiaire);

//            Évaluation
            Paragraph evaluation = new Paragraph("Évaluation", titleTextFont);
            evaluation.add(new Paragraph("Conformité des tâches: " + workEnvironmentEvaluation.getWorkEnvironment().getTaskConformity(), normalTextFont));
            evaluation.add(new Paragraph("Mesures d'accueil: " + workEnvironmentEvaluation.getWorkEnvironment().getWelcomeMeasures(), normalTextFont));
            evaluation.add(new Paragraph("Supervision suffisante: " + workEnvironmentEvaluation.getWorkEnvironment().getSufficientSupervision(), normalTextFont));
            evaluation.add(new Paragraph("Respect des normes de sécurité: " + workEnvironmentEvaluation.getWorkEnvironment().getSafetyCompliance(), normalTextFont));
            evaluation.add(new Paragraph("Atmosphère positive: " + workEnvironmentEvaluation.getWorkEnvironment().getPositiveAtmosphere(), normalTextFont));
            evaluation.add(new Paragraph("Salaire compétitif: " + workEnvironmentEvaluation.getWorkEnvironment().getCompetitiveSalary(), normalTextFont));
            evaluation.add(new Paragraph("Communication efficace: " + workEnvironmentEvaluation.getWorkEnvironment().getEffectiveCommunication(), normalTextFont));
            evaluation.add(new Paragraph("Équipement adéquat: " + workEnvironmentEvaluation.getWorkEnvironment().getAdequateEquipment(), normalTextFont));
            evaluation.add(new Paragraph("Charge de travail raisonnable: " + workEnvironmentEvaluation.getWorkEnvironment().getReasonableWorkload(), normalTextFont));
            evaluation.add(new Paragraph("Commentaire: " + workEnvironmentEvaluation.getComment(), normalTextFont));
            document.add(evaluation);

//            TODO: ajout signature et autres si jamais
//            Signature
//            Paragraph signature = new Paragraph("Signature", titleTextFont);
//            signature.add(new Paragraph("Signature de l'enseignant: ", normalTextFont));
//            signature.add(new Paragraph( workEnvironmentEvaluation.getSignature().toString, normalTextFont));
//            document.add(signature);

            document.close();
            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);

        }catch(DocumentException | IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
