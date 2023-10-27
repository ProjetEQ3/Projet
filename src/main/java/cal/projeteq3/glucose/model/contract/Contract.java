package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.exception.unauthorizedException.SignaturePrerequisitNotMet;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
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

    public byte[] generateContractPDF() {

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try{
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.addTitle("Contract");

            BaseFont arialFont = BaseFont.createFont("src/main/resources/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font documentTitleFont = new Font(arialFont, 18, Font.BOLD, BaseColor.BLACK);
            Font normalTextFont = new Font(arialFont, 10, Font.NORMAL, BaseColor.BLACK);
            Font boldTextFont = new Font(arialFont, 10, Font.BOLD, BaseColor.BLACK);

            Paragraph documentTitle = new Paragraph("Entente Intervenue entre les parties suivantes", documentTitleFont);
            documentTitle.setAlignment(Paragraph.ALIGN_CENTER);

            Paragraph intro = new Paragraph();
            intro.add(new Phrase("Dans le cadre de la formule Alternance travail-études du programme de ", normalTextFont));
            intro.add(new Chunk("Techniques de l’informatique", boldTextFont));
            intro.add(new Phrase(", les parties citées ci-dessous :", normalTextFont));
            intro.setAlignment(Paragraph.ALIGN_CENTER);
            intro.setSpacingBefore(20f);

            document.add(documentTitle);
            document.add(intro);
            document.close();

            byte[] bytes = outputStream.toByteArray();

            return Base64.getEncoder().encode(bytes);

        }catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
