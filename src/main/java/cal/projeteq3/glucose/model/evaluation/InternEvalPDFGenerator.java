package cal.projeteq3.glucose.model.evaluation;

import cal.projeteq3.glucose.model.contract.Signature;
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

//            Productivity
            Paragraph productivity = new Paragraph();
            productivity.add(new Chunk("Productivité ", titleTextFont));
            productivity.add(new Chunk("Capacité d’optimiser son rendement au travail", boldTextFont));
            productivity.add(new Chunk("\n\n"));
            productivity.add(new Chunk("planifier et organiser son travail de façon efficace: ", normalTextFont));
            productivity.add(new Chunk(internEvaluation.getProductivity().getPlan_and_organize_work().toString(), normalTextFont));
            productivity.add(new Chunk("\n"));
            productivity.add(new Chunk("comprendre rapidement les directives relatives à son travail: ", normalTextFont));
            productivity.add(new Chunk(internEvaluation.getProductivity().getUnderstand_directives().toString(), normalTextFont));
            productivity.add(new Chunk("\n"));
            productivity.add(new Chunk("maintenir un rythme de travail soutenu: ", normalTextFont));
            productivity.add(new Chunk(internEvaluation.getProductivity().getMaintain_sustained_work_rhythm().toString(), normalTextFont));
            productivity.add(new Chunk("\n"));
            productivity.add(new Chunk("établir ses priorités: ", normalTextFont));
            productivity.add(new Chunk(internEvaluation.getProductivity().getEstablish_priorities().toString(), normalTextFont));
            productivity.add(new Chunk("\n"));
            productivity.add(new Chunk("respecter ses échéanciers: ", normalTextFont));
            productivity.add(new Chunk(internEvaluation.getProductivity().getRespect_deadlines().toString(), normalTextFont));
            productivity.add(new Chunk("\n"));
            productivity.add(new Chunk("Commentaire: ", normalTextFont));
            productivity.add(new Chunk(internEvaluation.getProductivity().getComment(), normalTextFont));
            document.add(productivity);

//            QualityOfWork
            Paragraph qualityOfWork = new Paragraph();
            qualityOfWork.add(new Chunk("Qualité du travail ", titleTextFont));
            qualityOfWork.add(new Chunk("Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant personnellement des normes de qualité", boldTextFont));
            qualityOfWork.add(new Chunk("\n\n"));
            qualityOfWork.add(new Chunk("respecter les mandats qui lui ont été confiés: ", normalTextFont));
            qualityOfWork.add(new Chunk(internEvaluation.getQualityOfWork().getRespect_mandates().toString(), normalTextFont));
            qualityOfWork.add(new Chunk("\n"));
            qualityOfWork.add(new Chunk("porter attention aux détails dans la réalisation de ses tâche: ", normalTextFont));
            qualityOfWork.add(new Chunk(internEvaluation.getQualityOfWork().getAttention_to_details().toString(), normalTextFont));
            qualityOfWork.add(new Chunk("\n"));
            qualityOfWork.add(new Chunk("vérifier son travail, s’assurer que rien n’a été oublié: ", normalTextFont));
            qualityOfWork.add(new Chunk(internEvaluation.getQualityOfWork().getVerify_work().toString(), normalTextFont));
            qualityOfWork.add(new Chunk("\n"));
            qualityOfWork.add(new Chunk("rechercher des occasions de se perfectionner: ", normalTextFont));
            qualityOfWork.add(new Chunk(internEvaluation.getQualityOfWork().getLook_for_opportunities_to_improve().toString(), normalTextFont));
            qualityOfWork.add(new Chunk("\n"));
            qualityOfWork.add(new Chunk("faire une bonne analyse des problèmes rencontrés: ", normalTextFont));
            qualityOfWork.add(new Chunk(internEvaluation.getQualityOfWork().getGood_analysis_of_problems().toString(), normalTextFont));
            qualityOfWork.add(new Chunk("\n"));
            qualityOfWork.add(new Chunk("Commentaire: ", normalTextFont));
            qualityOfWork.add(new Chunk(internEvaluation.getQualityOfWork().getComment(), normalTextFont));
            document.add(qualityOfWork);

//            InterpersonalSkills
            Paragraph interpersonalSkills = new Paragraph();
            interpersonalSkills.add(new Chunk("Compétences interpersonnelles ", titleTextFont));
            interpersonalSkills.add(new Chunk("Capacité d’établir des interrelations harmonieuses dans son milieu de travail", boldTextFont));
            interpersonalSkills.add(new Chunk("\n\n"));
            interpersonalSkills.add(new Chunk("établir facilement des contacts avec les gens: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getEstablish_contacts().toString(), normalTextFont));
            interpersonalSkills.add(new Chunk("\n"));
            interpersonalSkills.add(new Chunk("contribuer activement au travail d’équipe: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getContribute_to_teamwork().toString(), normalTextFont));
            interpersonalSkills.add(new Chunk("\n"));
            interpersonalSkills.add(new Chunk("s’adapter facilement à la culture de l’entreprise: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getAdapt_to_company_culture().toString(), normalTextFont));
            interpersonalSkills.add(new Chunk("\n"));
            interpersonalSkills.add(new Chunk("accepter les critiques constructives: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getAccept_constructive_criticism().toString(), normalTextFont));
            interpersonalSkills.add(new Chunk("\n"));
            interpersonalSkills.add(new Chunk("être respectueux envers les gens: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getBe_respectful().toString(), normalTextFont));
            interpersonalSkills.add(new Chunk("\n"));
            interpersonalSkills.add(new Chunk("faire preuve d’écoute active en essayant de comprendre le point de vue de l’autre: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getBe_a_good_listener().toString(), normalTextFont));
            interpersonalSkills.add(new Chunk("\n"));
            interpersonalSkills.add(new Chunk("Commentaire: ", normalTextFont));
            interpersonalSkills.add(new Chunk(internEvaluation.getInterpersonalSkills().getComment(), normalTextFont));
            document.add(interpersonalSkills);

//            PersonalSkills
            Paragraph personalSkills = new Paragraph();
            personalSkills.add(new Chunk("Compétences personnelles ", titleTextFont));
            personalSkills.add(new Chunk("Capacité de faire preuve d’attitudes ou de comportements matures et responsables", boldTextFont));
            personalSkills.add(new Chunk("\n\n"));
            personalSkills.add(new Chunk("démontrer de l’intérêt et de la motivation au travail: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getShow_interest_and_motivation().toString(), normalTextFont));
            personalSkills.add(new Chunk("\n"));
            personalSkills.add(new Chunk("exprimer clairement ses idées: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getExpress_ideas_clearly().toString(), normalTextFont));
            personalSkills.add(new Chunk("\n"));
            personalSkills.add(new Chunk("faire preuve d’initiative: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getShow_initiative().toString(), normalTextFont));
            personalSkills.add(new Chunk("\n"));
            personalSkills.add(new Chunk("travailler de façon sécuritaire: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getWork_safely().toString(), normalTextFont));
            personalSkills.add(new Chunk("\n"));
            personalSkills.add(new Chunk("démontrer un bon sens des responsabilités ne requérant qu’un minimum de supervision: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getShow_good_sense_of_responsibility().toString(), normalTextFont));
            personalSkills.add(new Chunk("\n"));
            personalSkills.add(new Chunk("être ponctuel et assidu à son travail: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getBe_punctual_and_attentive_to_work().toString(), normalTextFont));
            personalSkills.add(new Chunk("\n"));
            personalSkills.add(new Chunk("Commentaire: ", normalTextFont));
            personalSkills.add(new Chunk(internEvaluation.getPersonalSkills().getComment(), normalTextFont));
            document.add(personalSkills);

//            GlobalAppreciation
            Paragraph globalAppreciation = new Paragraph();
            globalAppreciation.add(new Chunk("Appréciation globale ", titleTextFont));
            globalAppreciation.add(new Chunk("\n\n"));
            globalAppreciation.add(new Chunk("Capacité de s’acquitter des tâches sous sa responsabilité en s’imposant personnellement des normes de qualité: ", normalTextFont));
            globalAppreciation.add(new Chunk(internEvaluation.getGlobalAppreciation().getSpecificAppraisal(), normalTextFont));
            globalAppreciation.add(new Chunk("Commentaire: ", normalTextFont));
            globalAppreciation.add(new Chunk(internEvaluation.getGlobalAppreciation().getComment(), normalTextFont));
            document.add(globalAppreciation);

//            ReAdmit
            Paragraph reAdmit = new Paragraph();
            reAdmit.add(new Chunk("Réadmission ", titleTextFont));
            reAdmit.add(new Chunk("\n\n"));
            reAdmit.add(new Chunk("Le stagiaire est-il réadmissible? ", normalTextFont));
            reAdmit.add(new Chunk(internEvaluation.getReAdmit().getWouldWelcomeInternBack().toString(), normalTextFont));
            reAdmit.add(new Chunk("\n"));
            reAdmit.add(new Chunk("Commentaire: ", normalTextFont));
            reAdmit.add(new Chunk(internEvaluation.getReAdmit().getTechnicalFormationSufficient(), normalTextFont));
            document.add(reAdmit);

//            Signature
            Paragraph signature = new Paragraph();
            signature.add(new Chunk("Signature du superviseur: ", normalTextFont));
            signature.add(new Chunk("\n\n"));
            signature.add(new Chunk(internEvaluation.getSignature().toString() , normalTextFont));
            document.add(signature);

            Paragraph conclusion = new Paragraph();
            conclusion.add(new Chunk("Conclusion ", titleTextFont));
            conclusion.add(new Chunk("\n\n"));
            conclusion.add(new Chunk("Merci d'utiliser notre application et d'appuyer les étudiants! ", boldTextFont));
            document.add(conclusion);

            document.close();
            byte[] bytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);

        }catch(DocumentException | IOException e){
            System.out.println(e.getMessage());
        }
        return null;

    }
}
