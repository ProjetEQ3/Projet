package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.model.contract.Contract;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Manager;
import cal.projeteq3.glucose.model.user.Student;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public final class ContractPDFGenerator{
	private ContractPDFGenerator(){}

	public static String generatePDF(Contract contract, Manager manager){
		Document document = new Document();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try{
			PdfWriter.getInstance(document, outputStream);
			document.open();
			document.addTitle("Contract");
			JobOffer jobOffer = contract.getJobOffer();
			Employer employer = contract.getEmployer();
			Student student = contract.getStudent();

			BaseFont arialFont = BaseFont.createFont("src/main/resources/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font normalTextFont = new Font(arialFont, 10, Font.NORMAL, BaseColor.BLACK);
			Font boldTextFont = new Font(arialFont, 10, Font.BOLD, BaseColor.BLACK);
			Font titleTextFont = new Font(arialFont, 20, Font.BOLD, BaseColor.BLACK);

			// Centered title
			Paragraph title = new Paragraph("Entente Intervenue entre les parties suivantes", titleTextFont);
			title.setAlignment(Element.ALIGN_CENTER);
			document.add(title);

			// Introduction
			Paragraph intro = new Paragraph();
			intro.add(new Phrase("Dans le cadre de la formule Alternance travail-études du programme de ", normalTextFont));
			intro.add(new Chunk(jobOffer.getDepartment().getDepartment(), boldTextFont));
			intro.add(new Phrase(", les parties citées ci-dessous :", normalTextFont));
			intro.setAlignment(Paragraph.ALIGN_CENTER);
			intro.setSpacingBefore(20f);
			document.add(intro);

			// Presentation of Education Center
			Paragraph presentationEducationCenter = new Paragraph(
				"Le Cégep André-Laurendeau, corporation légalement constituée, situé au 1111, rue Lapierre, Lasalle, Québec, " + "H8N 2J4",
				normalTextFont
			);
			presentationEducationCenter.setAlignment(Element.ALIGN_CENTER);
			document.add(presentationEducationCenter);

			// Presentation of Director
			Paragraph presentationDirector = new Paragraph(
				"ici représenté par " + manager.getFirstName() + " " + manager.getLastName() + " ci-après désigné \"Le " +
					"Collège\".",
				normalTextFont
			);
			presentationDirector.setAlignment(Element.ALIGN_CENTER);
			document.add(presentationDirector);

			Paragraph et = new Paragraph("et", boldTextFont);
			et.setAlignment(Element.ALIGN_CENTER);
			document.add(et);

			Paragraph presentationEmployer = new Paragraph(
				"L'entreprise " + employer.getOrganisationName() + " ayant sa place d'affaire au : " + jobOffer.getLocation(),
				normalTextFont
			);
			presentationEmployer.setAlignment(Element.ALIGN_CENTER);
			document.add(presentationEmployer);

			Paragraph studentParagraph = new Paragraph();
			studentParagraph.add(new Phrase("L’élève, ", normalTextFont));
			studentParagraph.add(new Chunk(student.getFirstName() + ", " + student.getLastName(), boldTextFont));
			studentParagraph.add(new Phrase(",\nConviennent des conditions de stage suivantes :", normalTextFont));
			studentParagraph.setAlignment(Paragraph.ALIGN_CENTER);
			studentParagraph.setSpacingBefore(20f);
			document.add(studentParagraph);

			Paragraph internshipLocation = new Paragraph();
			internshipLocation.add(new Phrase("Lieu du stage ", titleTextFont));
			internshipLocation.add(new Phrase("\nService ou département :\n", normalTextFont));
			internshipLocation.add(new Chunk(employer.getOrganisationName(), boldTextFont));
			internshipLocation.add(new Phrase("\nAdresse :\n", normalTextFont));
			internshipLocation.add(new Chunk(jobOffer.getLocation(), boldTextFont));
			internshipLocation.setAlignment(Paragraph.ALIGN_LEFT);
			internshipLocation.setSpacingBefore(15f);
			document.add(internshipLocation);

			Paragraph supervisorInfo = new Paragraph("Superviseur du stage", titleTextFont);
			supervisorInfo.setAlignment(Paragraph.ALIGN_LEFT);
			supervisorInfo.setSpacingBefore(20f);
			document.add(supervisorInfo);

			Paragraph supervisorDetails = new Paragraph();
			supervisorDetails.add(new Phrase("Nom :\n", normalTextFont));
			supervisorDetails.add(new Chunk(employer.getLastName(), boldTextFont));
			supervisorDetails.add(new Phrase("\nPrénom :\n", normalTextFont));
			supervisorDetails.add(new Chunk(employer.getFirstName(), boldTextFont));
			supervisorDetails.add(new Phrase("\nCourriel :\n", normalTextFont));
			supervisorDetails.add(new Chunk(employer.getEmail(), boldTextFont));
			//            TODO: Add job title to employer
			//            supervisorDetails.add(new Phrase("\nFonction :\n", normalTextFont));
			//            supervisorDetails.add(new Chunk(employer.getJobTitle, boldTextFont));
			supervisorDetails.add(new Phrase("\nTéléphone :\n", normalTextFont));
			supervisorDetails.add(new Chunk(employer.getOrganisationPhone(), boldTextFont));
			supervisorDetails.setAlignment(Paragraph.ALIGN_LEFT);
			supervisorDetails.setSpacingBefore(10f);
			document.add(supervisorDetails);

			Paragraph internshipDuration = new Paragraph(
				"Durée de stage (durée minimale de 8 semaines et de 224 heures)", titleTextFont);
			internshipDuration.setAlignment(Paragraph.ALIGN_LEFT);
			internshipDuration.setSpacingBefore(20f);
			document.add(internshipDuration);

			Paragraph internshipDetails = new Paragraph();
			internshipDetails.add(new Phrase("Date de début :\n", normalTextFont));
			internshipDetails.add(new Chunk(String.valueOf(jobOffer.getStartDate()), boldTextFont));
			internshipDetails.add(new Phrase("\nDate de fin :\n", normalTextFont));
			internshipDetails.add(
				new Chunk(String.valueOf(jobOffer.getStartDate().plusWeeks(jobOffer.getDuration())), boldTextFont));
			internshipDetails.add(new Phrase("\nNombre total de semaines :\n", normalTextFont));
			internshipDetails.add(new Chunk(jobOffer.getDuration() + " Semaines", boldTextFont));
			internshipDetails.setAlignment(Paragraph.ALIGN_LEFT);
			internshipDetails.setSpacingBefore(10f);
			document.add(internshipDetails);

			Paragraph internshipSchedule = new Paragraph("Horaire de travail", titleTextFont);
			internshipSchedule.setAlignment(Paragraph.ALIGN_LEFT);
			internshipSchedule.setSpacingBefore(20f);
			document.add(internshipSchedule);

			Paragraph scheduleDetails = new Paragraph();
			scheduleDetails.add(new Phrase("Heure de début :\n", normalTextFont));
			scheduleDetails.add(new Chunk("9h00", boldTextFont));
			scheduleDetails.add(new Phrase("\nHeure de fin :\n", normalTextFont));
			scheduleDetails.add(new Chunk("17h00", boldTextFont));
			scheduleDetails.add(new Phrase("\nNombre total d’heures par semaine :\n", normalTextFont));
			scheduleDetails.add(new Chunk(jobOffer.getHoursPerWeek() + " h/Semaine", boldTextFont));
			scheduleDetails.setAlignment(Paragraph.ALIGN_LEFT);
			scheduleDetails.setSpacingBefore(10f);
			document.add(scheduleDetails);

			Paragraph workDays = new Paragraph("Jours de travail", titleTextFont);
			workDays.setAlignment(Paragraph.ALIGN_LEFT);
			workDays.setSpacingBefore(20f);
			document.add(workDays);

			Paragraph workDaysDetails = new Paragraph();
			workDaysDetails.add(new Phrase("Lundi, Mardi, Mercredi, Jeudi, Vendredi\n", normalTextFont));
			workDaysDetails.setAlignment(Paragraph.ALIGN_LEFT);
			workDaysDetails.setSpacingBefore(10f);
			document.add(workDaysDetails);

			Paragraph salaryInfo = new Paragraph("\nSalaire", titleTextFont);
			salaryInfo.setAlignment(Paragraph.ALIGN_LEFT);
			salaryInfo.setSpacingBefore(20f);
			document.add(salaryInfo);

			Paragraph salaryDetails = new Paragraph();
			salaryDetails.add(new Phrase("Salaire horaire :\n", normalTextFont));
			salaryDetails.add(new Chunk(jobOffer.getSalary() + "0 $/heure\n", boldTextFont));
			salaryDetails.setAlignment(Paragraph.ALIGN_LEFT);
			salaryDetails.setSpacingBefore(10f);
			document.add(salaryDetails);

			Paragraph objectivesInfo = new Paragraph(
				"Conformément aux objectifs visés pour ce stage, les tâches et les mandats suivants seront confiés à l’élève " + "stagiaire :",
				boldTextFont
			);
			objectivesInfo.setAlignment(Paragraph.ALIGN_LEFT);
			objectivesInfo.setSpacingBefore(20f);
			document.add(objectivesInfo);

			Paragraph responsibilities = new Paragraph();
			responsibilities.add(new Phrase("Tâches et responsabilités du stagiaire\n", normalTextFont));
			//            TODO: Changer pour responsabilités?
			responsibilities.add(new Chunk(jobOffer.getDescription(), boldTextFont));
			responsibilities.setAlignment(Paragraph.ALIGN_LEFT);
			responsibilities.setSpacingBefore(10f);
			document.add(responsibilities);

			Paragraph responsibilitiesSection = new Paragraph("Responsabilités", titleTextFont);
			responsibilitiesSection.setAlignment(Paragraph.ALIGN_LEFT);
			responsibilitiesSection.setSpacingBefore(20f);
			document.add(responsibilitiesSection);

			// Add the responsibilities text
			Paragraph responsibilitiesText = new Paragraph();
			responsibilitiesText.add(new Chunk("\nLe Collège s’engage à :\n", boldTextFont));
			responsibilitiesText.add(new Phrase(
				"• fournir à l’entreprise tous les renseignements concernant les conditions spécifiques du " + "programme " +
					"d’études et du programme d’alternance travail-études;\n",
				normalTextFont
			));
			responsibilitiesText.add(
				new Phrase("• collaborer, au besoin, à la définition du plan de stage;\n", normalTextFont));
			responsibilitiesText.add(
				new Phrase("• effectuer un suivi de l’élève stagiaire pendant la durée du stage;\n", normalTextFont));
			responsibilitiesText.add(
				new Phrase("• fournir à l’entreprise les documents nécessaires à l’évaluation de l’élève stagiaire;\n",
				           normalTextFont
				));
			responsibilitiesText.add(new Phrase(
				"• collaborer avec l’entreprise pour résoudre des problèmes qui pourraient survenir en cours de " + "stage, " +
					"le" + " cas échéant;\n",
				normalTextFont
			));
			responsibilitiesText.add(
				new Phrase("• conserver tous les dossiers de stage et les rapports des élèves;\n", normalTextFont));
			responsibilitiesText.add(new Phrase(
				"• fournir à l’entreprise le formulaire d’attestation de participation à un stage de formation " + "admissible"
					+ " après réception du formulaire « Déclaration relative au crédit d’impôt remboursable pour " + "les stages" +
					" »" + ".\n",
				normalTextFont
			));

			responsibilitiesText.add(new Chunk("\nL’entreprise s’engage à :\n", boldTextFont));
			responsibilitiesText.add(
				new Phrase("• embaucher l’élève stagiaire aux conditions précisées dans la présente entente;\n",
				           normalTextFont
				));
			responsibilitiesText.add(new Phrase(
				"• désigner un superviseur de stage qui assurera l’encadrement de l’élève stagiaire pour toute " + "la durée " + "du stage;\n",
				normalTextFont
			));
			responsibilitiesText.add(new Phrase(
				"• mettre en place des mesures d’accueil, d’intégration et d’encadrement de l’élève stagiaire;\n",
				normalTextFont
			));
			responsibilitiesText.add(new Phrase("• procéder à l’évaluation de l’élève stagiaire.\n", normalTextFont));
			responsibilitiesText.add(
				new Phrase("• retourner le formulaire « Déclaration des heures travaillées » dûment rempli.\n",
				           normalTextFont
				));
			responsibilitiesText.add(new Phrase(
				"• respecter les mesures de sécurités sanitaires provinciales en contexte de la pandémie Covid-19\n",
				normalTextFont
			));

			responsibilitiesText.add(new Chunk("\nL’élève s’engage:\n", boldTextFont));
			responsibilitiesText.add(
				new Phrase("• assumer de façon responsable et sécuritaire, les tâches qui lui sont confiées;\n",
				           normalTextFont
				));
			responsibilitiesText.add(new Phrase(
				"• respecter les politiques, règles et procédures de l’entreprise ainsi que l’horaire de travail au même " +
					"titre" + " qu’un employé;\n",
				normalTextFont
			));
			responsibilitiesText.add(new Phrase("• respecter les dates de début et de fin de stage;\n", normalTextFont));
			responsibilitiesText.add(new Phrase(
				"• référer rapidement au responsable des stages au cégep toute situation problématique affectant le bon " + "d" +
					"éroulement du stage;\n",
				normalTextFont
			));
			responsibilitiesText.add(
				new Phrase("• rédiger un rapport de stage et le soumettre au responsable des stages au cégep.\n",
				           normalTextFont
				));

			responsibilitiesText.setAlignment(Paragraph.ALIGN_LEFT);
			responsibilitiesText.setSpacingBefore(10f);
			document.add(responsibilitiesText);

			Paragraph signatures = new Paragraph("\nSignatures.", titleTextFont);
			signatures.setAlignment(Paragraph.ALIGN_CENTER);
			signatures.setSpacingBefore(20f);
			document.add(signatures);

			// Add the signature details
			Paragraph signatureDetails = new Paragraph(
				"\n\nLes parties s’engagent à respecter cette entente de stage", boldTextFont);
			signatureDetails.setAlignment(Paragraph.ALIGN_CENTER);
			signatureDetails.setSpacingBefore(10f);
			document.add(signatureDetails);

			// Add the signature lines
			Paragraph studentSignature = new Paragraph(
				"L'élève :\n" + contract.getStudentSignatureContract(), normalTextFont);
			studentSignature.setAlignment(Paragraph.ALIGN_LEFT);
			studentSignature.setSpacingBefore(20f);
			document.add(studentSignature);

			Paragraph employerSignature = new Paragraph(
				"Pour l’entreprise :\n" + contract.getEmployerSignatureContract(), normalTextFont);
			employerSignature.setAlignment(Paragraph.ALIGN_LEFT);
			employerSignature.setSpacingBefore(20f);
			document.add(employerSignature);

			Paragraph directorSignature = new Paragraph(
				"Pour le Collège :\n" + contract.getManagerSignatureContract(), normalTextFont);
			directorSignature.setAlignment(Paragraph.ALIGN_LEFT);
			directorSignature.setSpacingBefore(20f);
			document.add(directorSignature);

			Paragraph finalParagraph = getElements(normalTextFont);
			document.add(finalParagraph);

			Anchor link = new Anchor(
				"Contactez votre personne-ressources pour plus d’information ou consultez: https://claurendeau.qc.ca/",
				normalTextFont
			);
			link.setReference("https://claurendeau.qc.ca/");

			document.add(link);

			document.close();
			byte[] bytes = outputStream.toByteArray();
			return Base64.getEncoder().encodeToString(bytes);

		}catch(DocumentException | IOException e){
			System.out.println(e.getMessage());
		}
		return null;
	}

	private static Paragraph getElements(Font normalTextFont){
		Paragraph finalParagraph = new Paragraph(
			"Toute personne morale ou physique faisant affaire avec le Cégep André-Laurendeau doit prendre " +
				"connaissance des politiques et procédures internes la concernant. En cas de manquements à une " +
				"politique ou à la Loi de la part d’une personne issue du milieu de stage, ce dernier se verrait exclu " +
				"de la liste des milieux approuvés.", normalTextFont
		);
		finalParagraph.setAlignment(Paragraph.ALIGN_LEFT);
		finalParagraph.setSpacingBefore(20f);
		return finalParagraph;
	}

}
