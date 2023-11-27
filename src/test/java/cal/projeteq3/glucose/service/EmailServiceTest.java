package cal.projeteq3.glucose.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    private MimeMessageHelper helper;
    private final MimeMessage message = mock(MimeMessage.class);

    private final String toEmail = "recipient@example.com";
    private final String subject = "Test Subject";
    private final String content = "Test Content";

    @Test
    void sendEmail_Success() {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        emailService.sendEmail(toEmail, subject, content);

        // Assert
        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    void sendEmail_MessagingException() {
        // Arrange
        doAnswer(invocation -> {
            throw new MessagingException("Simulated MessagingException");
        }).when(javaMailSender).createMimeMessage();

        // Act & Assert
        assertThrows(MessagingException.class, () -> emailService.sendEmail(toEmail, subject, content));
    }

    @Test
    void sendEmail_MailSendException_PrintErrorMessage() {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new MailSendException("Mail send error")).when(javaMailSender).send(mimeMessage);

        // Act
        emailService.sendEmail(toEmail, subject, content);

        // Assert
//        verify(System.out).println("Mail send error");
    }

    @Test
    void sendEmail_MailAuthenticationException_PrintErrorMessage() {
        // Arrange
        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(new MailAuthenticationException("Authentication error")).when(javaMailSender).send(mimeMessage);

        // Act
        emailService.sendEmail(toEmail, subject, content);

        // Assert
//        verify(System.out).println("Authentication error");
    }
}
