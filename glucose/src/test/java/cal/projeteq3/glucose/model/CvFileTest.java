package cal.projeteq3.glucose.model;

import static org.junit.jupiter.api.Assertions.*;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import org.junit.jupiter.api.Test;

public class CvFileTest{

	@Test
	public void testSetFileName_ValidFileName(){
		// Arrange
		CvFile cvFile = new CvFile();

		// Act
		cvFile.setFileName("valid_file.pdf");

		// Assert
		assertEquals("valid_file.pdf", cvFile.getFileName());
	}

	@Test
	public void testSetFileName_InvalidFileName(){
		// Arrange
		CvFile cvFile = new CvFile();

		//Act % Assert
		assertThrows(ValidationException.class, () -> cvFile.setFileName("###"));
	}

	@Test
	public void testSetFileData(){
		// Arrange
		CvFile cvFile = new CvFile();
		byte[] fileData = "Sample file data".getBytes();

		// Act
		cvFile.setFileData(fileData);

		// Assert
		assertArrayEquals(fileData, cvFile.getFileData());
	}

}
