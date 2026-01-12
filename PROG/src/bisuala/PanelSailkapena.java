package bisuala;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.util.ArrayList;
import model.*;

public class PanelSailkapena extends JPanel {
    // KONPONBIDEA: Taldeak eta Jardunaldiak onartu behar ditu
    public PanelSailkapena(ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldiak) {
        setLayout(new BorderLayout());
        String[] zutabeak = {"Posizioa", "Taldea", "JP", "I", "B", "G", "Puntuak"};
        DefaultTableModel modeloa = new DefaultTableModel(zutabeak, 0);
        
        for (Talde t : taldeak) {
            modeloa.addRow(new Object[]{1, t.getIzena(), 0, 0, 0, 0, 0});
        }
        
        add(new JScrollPane(new JTable(modeloa)), BorderLayout.CENTER);
    }
}