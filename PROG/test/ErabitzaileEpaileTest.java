import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

import java.util.ArrayList;

class ErabiltzaieEpaileTest {
	
	private Erabiltzaile e;
	
	private Talde T1;
	private Talde T2;
	private Denboraldia d;
	private Jardunaldi j;
	private Partidua p;
	private ArrayList<Jardunaldi> J;
	
	
	@BeforeEach
	void setUp() throws Exception {
		e = new ErabiltzaileEpaile("kaixo","kaixo123");

		T1 = new Talde();
		T2 = new Talde();
		p = new Partidua(T1,T2);
		j = new Jardunaldi(1, new ArrayList<Partidua>());
		j.addPartidua(p);
		d = new Denboraldia(2025);
		J = new ArrayList<>();
		J.add(j);
		d.setLigakoJardunaldi(J);
	}
	
	@Test
	void ErabiltzaileTest() {
		e.setErabiltzaile("agur");
		assertEquals(e.getErabiltzaile(),"agur");
	}
	
	@Test
	void PasahitzaTest() {
		e.setPasahitza("agur123");
		assertEquals(e.getPasahitza(),"agur123");
	}
	
	@Test
	void SartuEmaitzaTest() {
		((ErabiltzaileEpaile) e).sartuEmaitza(d,T1,T2,1,2);
		assertEquals(p.getEtxekoGolak(),1);
		assertEquals(p.getKanpokoGolak(),2);
	}
}


