import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Denboraldia;

class DenboraldiTest {

	@Test
	void testHasiDa() {
		Denboraldia d = new Denboraldia(1995);
		d.setDenboraldiaHasiDa(false);
		assertFalse(d.isDenboraldiaHasiDa());
	}

}
