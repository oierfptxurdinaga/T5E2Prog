package bisuala;

import javax.swing.*;
import java.util.List;
import model.*;

public class PanelEpailea extends JPanel {

    public PanelEpailea(Erabiltzaile erab, List<TaldeTemporada> taldeak, List<Jardunaldi> jardunaldiak) {
        add(new JLabel("Epaile Panela: " + erab.getErabiltzaile()));
    }
}