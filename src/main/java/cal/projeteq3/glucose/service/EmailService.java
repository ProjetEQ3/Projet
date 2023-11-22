package cal.projeteq3.glucose.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private static String FROM = "glucOSE.professionnel@outlook.com";

	@SneakyThrows
	public void sendEmail(String to, String subject, String content) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom(FROM);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(getString(subject, content), true);
		javaMailSender.send(message);
	}

	private static String getString(String subject, String content) {
		return "<html>" +
				"<head>" +
				"<title>Notification GlucOSE</title>" +
				"</head>" +
				"<body>" +
				"<h1>Notification GlucOSE</h1>" +
				"<h2>" + subject + "</h2>" +
				"<div>" +
				"<p>Bonjour,</p>" +
				"<p>" + content + "</p>" +
				"Vous pouvez toujours voir plus de détail sur l'application GlucOSE." +
				"<p>Merci d'utiliser notre application!</p>" +
				"</div>" +
				"<footer>" +
				"<p>&copy; 2023 GlucOSE Tous droits réservés.</p>" +
				"</footer>" +
				"</body>" +
				"</html>";
	}

}
