package bisuala;

import javax.swing.*;
import java.util.List;
import model.*;

public class PanelPresi extends JPanel {

    public PanelPresi(Erabiltzaile erab, List<TaldeTemporada> taldeak) {
        add(new JLabel("Presidente Panela: " + erab.getErabiltzaile()));
    }
}