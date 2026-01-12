package bisuala;

import javax.swing.*;
import java.util.List;
import model.*;

public class PanelAdmin extends JPanel {
    public PanelAdmin(Erabiltzaile erab, List<Talde> taldeak) {
        add(new JLabel("Administratzaile Panela: " + erab.getErabiltzaile()));
        // Hemen zure zerrendak eta botoiak...
    }
	public void aldatuJokalariak(Jokalari jokalari, Talde taldeZaharra, Talde taldeBerria) {
		if(taldeZaharra.getJokalariak().contains(jokalari)) {
			taldeZaharra.getJokalariak().remove(jokalari);
			taldeBerria.getJokalariak().add(jokalari);
		}else {
			JOptionPane.showMessageDialog(null, "Jokalari hori ez dago talde horretan", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}