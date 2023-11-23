package cal.projeteq3.glucose.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class EmailService {

	private final JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private final static String FROM = "glucOSE.professionnel@outlook.com";

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
		} catch (MailSendException e){
			System.out.println(e.getMessage());
		}
	}

//	private final String apiKey = "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";
//	private final String uri = "https://api.sendinblue.com/v3/smtp/email";
//	private final String fromEmail = "1@zaka.se";
//	private final String fromName = "GlucOSE Notification";
//	private final HttpClient client = HttpClient.newHttpClient();
//
//	public void sendEmail(String toEmail, String toName, String subject, String content){
//		HttpRequest request = HttpRequest
//				.newBuilder()
//				.uri(URI.create(uri))
//				.header("Accept", "application/json")
//				.header("Content-Type", "application/json")
//				.header("api-key", apiKey)
//				.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(toEmail, toName, subject, content)))
//				.build();
//		try{
//			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//			System.out.println("CODE: " + response.statusCode() + " TO: " + toEmail);
//		}catch(Exception e){
//			System.out.println("Exception: " + e.getMessage());
//		}
//	}
//
//	private String getRequestBody(String toEmail, String toName, String subject, String content){
//		String htmlContent = getString(subject, content);
//		return String.format(
//				"{" +
//						"\"sender\":{\"name\":\"%s\",\"email\":\"%s\"}," +
//						"\"to\":[{\"email\":\"%s\",\"name\":\"%s\"}]," +
//						"\"subject\":\"%s\"," +
//						"\"htmlContent\":\"%s\"," +
//						"\"textContent\":\"%s\"}",
//				fromName, fromEmail, toEmail, toName, subject, htmlContent, content
//		);
//	}

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
