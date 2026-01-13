package bisuala;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.*;

public class PanelSailkapena extends JPanel {

    public PanelSailkapena(Sailkapena sailkapena) {
        setLayout(new BorderLayout());

        String[] zutabeak = {"Posizioa", "Taldea", "PJ", "V", "E", "D", "GF", "GC", "Diferentzia", "Puntuak"};
        DefaultTableModel modeloa = new DefaultTableModel(zutabeak, 0);

        List<TaldeTemporada> taldeak = sailkapena.getSailkapena();
        sailkapena.eguneratuSailkapena();

        int pos = 1;
        for (TaldeTemporada tTemp : taldeak) {
            int diferentzia = tTemp.getGolesFavor() - tTemp.getGolesContra();
            modeloa.addRow(new Object[]{
                    pos++,
                    tTemp.getTalde().getIzena(),
                    tTemp.getPartidosJugados(),
                    tTemp.getVictorias(),
                    tTemp.getEmpates(),
                    tTemp.getDerrotas(),
                    tTemp.getGolesFavor(),
                    tTemp.getGolesContra(),
                    diferentzia,
                    tTemp.getLigakoPuntos()
            });
        }

        add(new JScrollPane(new JTable(modeloa)), BorderLayout.CENTER);
    }
}