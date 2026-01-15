import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

import java.util.ArrayList;

class TaldeTest {
	private Talde t;
	private Jokalari j;
	
	@BeforeEach
	void setUp() throws Exception {
		t = new Talde("Talde1", "", "", null, "Hiria1", true);
		j = new Jokalari("Markel", "Abascal", 2002, 13, "Delantero", true);
	}
	
	@Test
	void sartuJokalariaTest() {
		assertNull(t.getJokalariak());
		t.sartuJokalaria(j);
		assertEquals(1, t.getJokalariak().size());
		assertEquals(j, t.getJokalariak().get(0));
	}
	
	@Test
	void aldatuEskutua() {
		t.setEskutua("Beltza");
        assertEquals("Beltza", t.getEskutua());
        t.aldatuEskutua("Gorria");
        assertEquals("Gorria", t.getEskutua());
	}
}