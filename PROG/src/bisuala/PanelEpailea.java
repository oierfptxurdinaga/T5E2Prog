package bisuala;
import javax.swing.*;
import java.util.ArrayList;
import model.*;

public class PanelEpailea extends JPanel {
    // KONPONBIDEA: Parametroak APP-ko deiarekin bat etorri behar dira
    public PanelEpailea(Erabiltzaile erab, ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldiak) {
        add(new JLabel("Epaile Panela: " + erab.getErabiltzaile()));
        // Hemen emaitzak sartzeko formularioa...
    }
}