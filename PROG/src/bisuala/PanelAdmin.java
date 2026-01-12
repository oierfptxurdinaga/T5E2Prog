package bisuala;

import javax.swing.*;
import java.util.List;
import model.*;

public class PanelAdmin extends JPanel {
    public PanelAdmin(Erabiltzaile erab, List<Talde> taldeak) {
        add(new JLabel("Administratzaile Panela: " + erab.getErabiltzaile()));
        // Hemen zure zerrendak eta botoiak...
    }
}