package cal.projeteq3.glucose.service;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

@Service
public class EmailService{
	private final String apiKey = "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";
	private final String uri = "https://api.sendinblue.com/v3/smtp/email";
	private final String fromEmail = "glucose.pro@gmail.com";
    private final String fromName = "GlucOSE Notification";
	private final HttpClient client = HttpClient.newHttpClient();

	public void sendEmail(String toEmail, String toName, String subject, String content){
		HttpRequest request = HttpRequest
			.newBuilder()
			.uri(URI.create(uri))
			.header("Accept", "application/json")
			.header("Content-Type", "application/json")
			.header("api-key", apiKey)
			.POST(HttpRequest.BodyPublishers.ofString(getRequestBody(toEmail, toName, subject, content)))
			.build();
		try{
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			System.out.println("Response status code: " + response.statusCode());
		}catch(Exception e){
			System.out.println("Exception: " + e.getMessage());
		}
	}

	private String getRequestBody(String toEmail, String toName, String subject, String content){
		String htmlContent = getString(subject, content);
		return String.format(
			"{" +
				"\"sender\":{\"name\":\"%s\",\"email\":\"%s\"}," +
				"\"to\":[{\"email\":\"%s\",\"name\":\"%s\"}]," +
				"\"subject\":\"%s\"," +
				"\"htmlContent\":\"%s\"," +
				"\"textContent\":\"%s\"}",
				fromName, fromEmail, toEmail, toName, subject, htmlContent, content
		);
	}

	private static String getString(String subject, String content) {
		return "<html>" +
				"<head>" +
				"<title>Email Notification</title>" +
				"</head>" +
				"<body>" +
				"<h1>GlucOSE</h1>" +
				"<h2>" + subject +"</h2>" +
				"<div>" +
				"<p>Bonjour,</p>" +
				"<p>"+ content +"</p>" +
				"Vous pouvez toujours consulter sur l'application GlucOSE." +
				"<p>Merci d'utiliser notre application!</p>" +
				"</div>" +
				"<footer>" +
				"<p>&copy; 2023 GlucOSE Tous droits réservés.</p>" +
				"</footer>" +
				"</body>" +
				"</html>";
	}
}
