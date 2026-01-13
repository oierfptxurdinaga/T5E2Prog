package bisuala;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.*;

public class PanelTaldeak extends JPanel {

    public PanelTaldeak(List<TaldeTemporada> taldeakTemporada) {
        setLayout(new BorderLayout());

        JPanel pnlEquipos = new JPanel();
        pnlEquipos.setLayout(new BoxLayout(pnlEquipos, BoxLayout.Y_AXIS));

        for (TaldeTemporada tTemp : taldeakTemporada) {
            Talde t = tTemp.getTalde();

            JTextArea info = new JTextArea();
            info.setEditable(false);
            info.setText(
                    "Escudo: " + t.getEskutua() + "\n" +
                    "Nombre: " + t.getIzena() + "\n" +
                    "Estadio: " + t.getFutbolZelaia() + "\n" +
                    "Ciudad: " + t.getSHiria() + "\n" +
                    "En activo: " + (tTemp.isAktiboa() ? "Sí" : "No") + "\n\n" +
                    "Jugadores:\n"
            );

            for (Jokalari j : t.getJokalariak()) {
                info.append(
                        "- " + j.getIzena() + " " + j.getAbizena() +
                        ", Dorsal: " + j.getDortsala() +
                        ", Posición: " + j.getPosizio() +
                        ", Año Nac.: " + j.getJaiotseUrtea() +
                        "\n"
                );
            }

            info.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pnlEquipos.add(info);
        }

        JScrollPane scroll = new JScrollPane(pnlEquipos);
        add(scroll, BorderLayout.CENTER);
    }
}