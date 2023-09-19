package cal.projeteq3.glucose.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import cal.projeteq3.glucose.model.cvFile.CvFile;
import cal.projeteq3.glucose.model.cvFile.CvState;
import cal.projeteq3.glucose.model.user.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CvFileTest {

	private CvFile cvFile;

	@BeforeEach
	public void setUp() {
		cvFile = new CvFile();
	}

	@Test
	public void testSetFileName() {
		String fileName = "example.pdf";
		cvFile.setFileName(fileName);
		assertEquals(fileName, cvFile.getFileName());
	}

	@Test
	public void testSetCvState() {
		CvState cvState = CvState.SUBMITTED;
		cvFile.setCvState(cvState);
		assertEquals(cvState, cvFile.getCvState());
	}

	@Test
	public void testSetStudent() {
		Student student = mock(Student.class);
		cvFile.setStudent(student);
		assertEquals(student, cvFile.getStudent());
	}

	@Test
	public void testIdGeneration() {
		Long id = 1L;
		cvFile.setId(id);
		assertEquals(id, cvFile.getId());
	}

	@Test
	public void testSetFileData() {
		byte[] fileData = new byte[]{1, 2, 3, 4, 5};
		cvFile.setFileData(fileData);
		assertArrayEquals(fileData, cvFile.getFileData());
	}

	@Test
	public void testDefaultCvState() {
		CvFile cvFile = new CvFile();
		assertEquals(CvState.SUBMITTED, cvFile.getCvState());
	}

	@Test
	public void testFileNameValidation() {
		String fileName = "example.pdf";
		CvFile cvFile = CvFile.builder().fileName(fileName).build();
		assertEquals(fileName, cvFile.getFileName());
	}

	@Test
	public void testStudentCvAssociation(){
		Student student = mock(Student.class);
		cvFile.setStudent(student);
		assertEquals(student, cvFile.getStudent());
	}

}
