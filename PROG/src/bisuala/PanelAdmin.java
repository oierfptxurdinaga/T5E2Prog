package bisuala;

import javax.swing.*;
import java.util.List;
import model.*;

public class PanelAdmin extends JPanel {

    public PanelAdmin(Erabiltzaile erab, List<TaldeTemporada> taldeakTemporada) {
        add(new JLabel("Administratzaile Panela: " + erab.getErabiltzaile()));
    }

    public void aldatuJokalariak(Jokalari jokalari, TaldeTemporada tZaharra, TaldeTemporada tBerria, boolean denboraldiaHasiDa) {
        if (!denboraldiaHasiDa) {
            if (tZaharra.getTalde().getJokalariak().contains(jokalari)) {
                tZaharra.getTalde().getJokalariak().remove(jokalari);
                tBerria.getTalde().getJokalariak().add(jokalari);
            } else {
                JOptionPane.showMessageDialog(null, "Jokalari hori ez dago talde horretan", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ezin dituzu jokalariak aldatu denboraldia hasi delako", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}