package bisuala;

import javax.swing.*;
import java.util.List;
import model.*;

public class PanelPresi extends JPanel {
    public PanelPresi(Erabiltzaile erab, List<Talde> taldeak) {
        add(new JLabel("Presidente Panela: " + erab.getErabiltzaile()));
        // Hemen taldea kudeatzeko botoiak...
    }
	public void aldatuTaldeak(Talde taldeBerria, Talde taldeZaharra) {

		}
}