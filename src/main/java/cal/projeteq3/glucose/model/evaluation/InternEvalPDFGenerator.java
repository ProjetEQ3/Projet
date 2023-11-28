package cal.projeteq3.glucose.model.evaluation;

import cal.projeteq3.glucose.model.evaluation.internEvaluation.InternEvaluation;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class InternEvalPDFGenerator {
    public static String generatePDF(InternEvaluation internEvaluation){
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Student student = internEvaluation.getJobApplication().getStudent();
        JobOffer jobOffer = internEvaluation.getJobApplication().getJobOffer();
        Employer employer = jobOffer.getEmployer();

        try{
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.addTitle("Évaluation du stagière");

            BaseFont arialFont = BaseFont.createFont("src/main/resources/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font normalTextFont = new Font(arialFont, 10, Font.NORMAL, BaseColor.BLACK);
            Font boldTextFont = new Font(arialFont, 10, Font.BOLD, BaseColor.BLACK);
            Font titleTextFont = new Font(arialFont, 20, Font.BOLD, BaseColor.BLACK);

            document.add(new Paragraph("Évaluation du stagière", titleTextFont));
            document.add(new Paragraph("Nom de l'élève: " + student.getFirstName() + " " + student.getLastName(), normalTextFont));
            document.add(new Paragraph("Programme d'études: " + student.getDepartment(), normalTextFont));
            document.add(new Paragraph("Nom de l'entreprise: " + employer.getOrganisationName(), normalTextFont));
            document.add(new Paragraph("Nom du superviseur: " + employer.getFirstName() + " " + employer.getLastName(), normalTextFont));
            document.add(new Paragraph("Titre du poste: " + jobOffer.getTitle(), normalTextFont));
            document.add(new Paragraph("Téléphone: " + employer.getOrganisationPhone(), normalTextFont));

            document.add(new Paragraph("Productivité", titleTextFont));
            document.add(new Paragraph("Productivité: " + internEvaluation.getProductivity().toString()));

            document.add(new Paragraph("Qualité du travail", titleTextFont));
            document.add(new Paragraph("Qualité du travail: " + internEvaluation.getQualityOfWork().toString()));

            document.add(new Paragraph("Compétences interpersonnelles", titleTextFont));
            document.add(new Paragraph("Compétences interpersonnelles: " + internEvaluation.getInterpersonalSkills().toString()));

            document.add(new Paragraph("Compétences personnelles", titleTextFont));
            document.add(new Paragraph("Compétences personnelles: " + internEvaluation.getPersonalSkills().toString()));

            document.add(new Paragraph("Appréciation globale", titleTextFont));
            document.add(new Paragraph("Appréciation globale: " + internEvaluation.getGlobalAppreciation().toString()));

            document.add(new Paragraph("Réadmission", titleTextFont));
            document.add(new Paragraph("Réadmission: " + internEvaluation.getReAdmit().toString()));

            document.close();
            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);

        }catch(DocumentException | IOException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
}
