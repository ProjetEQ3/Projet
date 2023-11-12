package cal.projeteq3.glucose.service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class EmailService{
	private final String apiKey = "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";
	private final String uri = "https://api.sendinblue.com/v3/smtp/email";
	private final String fromEmail = "1@zaka.se";
	private final String fromName = "Zakaria Gannoun";
	private final HttpClient client = HttpClient.newHttpClient();
	private final String htmlContent = "<html><body><h1>h1 title</h1><p>HTML content</p></body></html>";

	public void sendEmail(String toEmail, String toName, String subject, String content){
		System.out.println("EmailService.sendEmail");
		System.out.println("apiKey: " + apiKey);
		System.out.println("uri: " + uri);
		System.out.println("from: " + fromEmail);
		StringBuilder requestBody = new StringBuilder(getRequestBody(toEmail, toName, subject, content));
		System.out.println("requestBody: " + requestBody.toString());
		HttpRequest request = HttpRequest
			.newBuilder()
			.uri(URI.create(uri))
			.header("Accept", "application/json")
			.header("Content-Type", "application/json")
			.header("api-key", apiKey)
			.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
			.build();
		System.out.println("request: " + request.toString());
		try{
			System.out.println("Sending request...");
			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			System.out.println("Response status code: " + response.statusCode());
			System.out.println("Response body: " + response.body());
		}catch(Exception e){
			System.out.println("Sending request exception... ");
			System.out.println("Exception: " + e.getMessage());
		}
	}

	private String getRequestBody(String toEmail, String toName, String subject, String content){
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

}
