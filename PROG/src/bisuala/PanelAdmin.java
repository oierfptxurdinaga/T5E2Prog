package bisuala;

import javax.swing.*;
import java.util.ArrayList;
import model.*;

public class PanelAdmin extends JPanel {
	private static final long serialVersionUID = 1L;
	
    public PanelAdmin(Erabiltzaile erab, ArrayList<Talde> taldeak) {
        add(new JLabel("Administratzaile Panela: " + erab.getErabiltzaile()));
    }

    public void aldatuJokalariak(Jokalari jokalari, Talde tZaharra, Talde tBerria, boolean denboraldiaHasiDa) {
        if (!denboraldiaHasiDa) {
            if (tZaharra.getJokalariak().contains(jokalari)) {
                tZaharra.getJokalariak().remove(jokalari);
                tBerria.getJokalariak().add(jokalari);
                JOptionPane.showMessageDialog(null, "Aldaketa eginda");
            } else {
                JOptionPane.showMessageDialog(null, "Jokalari hori ez dago talde horretan", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ezin dituzu jokalariak aldatu denboraldia hasi delako", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}