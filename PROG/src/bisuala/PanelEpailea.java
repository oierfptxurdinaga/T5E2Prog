package bisuala;

import javax.swing.*;
import java.util.ArrayList; // ArrayList erabiltzen dugu
import model.*;

public class PanelEpailea extends JPanel {
	private static final long serialVersionUID = 1L;

	// ALDAKETA: Dena ArrayList<Talde> eta ArrayList<Jardunaldi>-ra
    public PanelEpailea(Erabiltzaile erab, ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldiak) {
        add(new JLabel("Epaile Panela: " + erab.getErabiltzaile()));
        // Hemen garatuko duzu gero emaitzak sartzeko logika
    }
}