import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

import java.util.ArrayList;

class ErabiltzaieTest {
	
	private Erabiltzaile erabiltzaile;
	
	
	@BeforeEach
	void setUp() throws Exception {
		erabiltzaile = new ErabiltzaileEpaile("kaixo","kaixo123");
	}
	
	@Test
	void getErabiltzaileTest(){
		String x=erabiltzaile.getErabiltzaile();
		assertEquals(x,"kaixo");
	}
	
	@Test
	void getPasahitzaTest(){
		String x=erabiltzaile.getPasahitza();
		assertEquals(x,"kaixo123");
	}
}


