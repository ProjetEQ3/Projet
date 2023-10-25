package cal.projeteq3.glucose.model.contract;

import cal.projeteq3.glucose.exception.unauthorizedException.SignaturePrerequisitNotMet;
import cal.projeteq3.glucose.model.jobOffer.JobOffer;
import cal.projeteq3.glucose.model.user.Employer;
import cal.projeteq3.glucose.model.user.Student;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.*;
import lombok.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

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

        try{
            PdfWriter.getInstance(document, new FileOutputStream("Contract.pdf"));
            document.open();

            document.addTitle("Contract");
            document.addSubject("Contract");
            document.addKeywords("Contract");
            document.addAuthor("Glucose");
            document.addCreator("Glucose");
            document.add(new Paragraph("Contract paragraphe test"));
            document.close();

            return Files.readAllBytes(Paths.get("Contract.pdf"));
        }catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
