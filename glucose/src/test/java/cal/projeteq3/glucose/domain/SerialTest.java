package cal.projeteq3.glucose.domain;

import static org.junit.jupiter.api.Assertions.*;
import cal.projeteq3.glucose.exception.badRequestException.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SerialTest{
	private Serial serial;

	@BeforeEach
	public void setup(){
		serial = new Serial();
	}

	@Test
	public void testSetValidSerial(){
		// Arrange
		String validSerial = "prefix_value";

		// Act
		serial.set(validSerial);

		// Assert
		assertEquals(validSerial, serial.get());
	}

	@Test
	public void testSetInvalidSerial(){
		// Arrange
		String invalidSerial = "invalid serial";

		// Act & Assert
		assertThrows(ValidationException.class, () -> {
			serial.set(invalidSerial);
		});
	}

	@Test
	public void testGenerateSerialWithPrefix(){
		// Arrange
		String prefix = "test";
		serial.generateSerial(prefix);

		// Assert
		assertTrue(serial.get().startsWith(prefix));
	}

	@Test
	public void testAddPrefix(){
		// Arrange
		String prefix = "test";

		// Act
		serial.addPrefix(prefix);

		// Assert
		assertTrue(serial.get().startsWith(prefix));
	}

}

