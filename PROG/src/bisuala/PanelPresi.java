package bisuala;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.*;

public class PanelPresi extends JPanel {

    public PanelPresi(Erabiltzaile erab, ArrayList<Talde> taldeak) {
        setLayout(new BorderLayout());

        // Izenburua (Título)
        JLabel lblIzenburua = new JLabel("Federazioko Presidentea: " + erab.getErabiltzaile());
        lblIzenburua.setHorizontalAlignment(SwingConstants.CENTER);
        lblIzenburua.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblIzenburua, BorderLayout.NORTH);

        // Taldeen zerrenda erakusteko panela (Panel para mostrar la lista)
        JPanel pnlZerrenda = new JPanel();
        pnlZerrenda.setLayout(new BoxLayout(pnlZerrenda, BoxLayout.Y_AXIS));

        // Liga osoa kudeatzen duenez, talde GUZTIAK erakusten dizkiogu
        if (taldeak != null && !taldeak.isEmpty()) {
            for (Talde t : taldeak) {
                JTextArea txtTaldea = new JTextArea();
                txtTaldea.setEditable(false);
                txtTaldea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                
                // Taldearen oinarrizko informazioa (Info básica del equipo)
                txtTaldea.setText("Taldea: " + t.getIzena() + " (" + t.getHiria() + ")\n");
                txtTaldea.append("Estadioa: " + t.getFutbolZelaia() + "\n");
                
                pnlZerrenda.add(txtTaldea);
                // Tarte txiki bat utzi taldeen artean (Espacio entre equipos)
                pnlZerrenda.add(Box.createRigidArea(new Dimension(0, 5))); 
            }
        } else {
            pnlZerrenda.add(new JLabel("Ez dago talderik ligan."));
        }

        // Scroll-a gehitu zerrenda luzea bada (Añadir scroll)
        add(new JScrollPane(pnlZerrenda), BorderLayout.CENTER);
        
        // Kudeaketa botoiak (Botones de gestión - Ejemplo visual)
        JPanel pnlBotoiak = new JPanel();
        pnlBotoiak.add(new JButton("Talde Berria Sortu")); 
        pnlBotoiak.add(new JButton("Liga Konfiguratu"));
        add(pnlBotoiak, BorderLayout.SOUTH);
    }
}