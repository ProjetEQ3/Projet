package cal.projeteq3.glucose.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private final static String FROM = "GlucoseMaster@outlook.com";

	public void sendEmail(String to, String subject, String content) {
		MimeMessage message = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(FROM);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(getString(subject, content), true);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (MailSendException | MailAuthenticationException e){
			System.out.println(e.getMessage());
		}
    }

	private static String getString(String subject, String content) {
		return "<html>" +
				"<head>" +
				"<title>glucose</title>" +
				"</head>" +
				"<body>" +
				"<h1>Glucose</h1>" +
				"<h2>" + subject + "</h2>" +
				"<div>" +
				"<p>Bonjour,</p>" +
				"<p>" + content + "</p>" +
				"Vous pouvez toujours voir plus de détail sur l'application GlucOSE." +
				"<p>Merci d'utiliser notre application!</p>" +
				"</div>" +
				"</body>" +
				"</html>";
	}

}
