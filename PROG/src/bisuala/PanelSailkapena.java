package bisuala;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import model.*;

public class PanelSailkapena extends JPanel {

    public PanelSailkapena(Sailkapena sailkapena) {
        setLayout(new BorderLayout());

        // Zutabeak definitu
        String[] zutabeak = {"Posizioa", "Taldea", "Puntuak"}; // Sinplifikatua hasteko
        DefaultTableModel modeloa = new DefaultTableModel(zutabeak, 0);

        // 1. Sailkapena objektutik ZERRENDA PARALELOAK lortu
        // Suposatzen dut zure Sailkapena klaseak getter hauek dituela
        ArrayList<Talde> listaTaldeak = sailkapena.getTaldeak(); 
        ArrayList<Integer> listaPuntuak = sailkapena.getPuntuak();

        // 2. Egiaztatu null ez direla eta tamaina bera dutela
        if (listaTaldeak != null && listaPuntuak != null && listaTaldeak.size() == listaPuntuak.size()) {
            
            // 'i' indizea erabiltzen dugu bi zerrendak batera irakurtzeko
            for (int i = 0; i < listaTaldeak.size(); i++) {
                Talde t = listaTaldeak.get(i);
                Integer p = listaPuntuak.get(i);
                
                // Errenkada gehitu taulara
                modeloa.addRow(new Object[]{
                        i + 1,              // Posizioa (1, 2, 3...)
                        t.getIzena(),       // Taldearen izena
                        p                   // Puntuak (puntuen zerrendatik)
                });
            }
        }

        // Taula panelera gehitu
        add(new JScrollPane(new JTable(modeloa)), BorderLayout.CENTER);
    }
}