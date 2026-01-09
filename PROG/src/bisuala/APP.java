package bisuala;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import model.*;

public class APP extends JFrame {
    private JComboBox<Denboraldia> cbDenboraldiak;
    private JTabbedPane tabs;
    private Erabiltzaile erabAktiboa;

    public APP(Erabiltzaile erab, ArrayList<Denboraldia> denboraldiak) {
        this.erabAktiboa = erab;
        setTitle("FNS Kudeaketa - " + erab.getErabiltzaile());
        setBounds(100, 100, 950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Goiko barra denboraldia aukeratzeko
        JPanel pnlGoikoa = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlGoikoa.setBackground(new Color(230, 230, 230));
        pnlGoikoa.add(new JLabel(" Aukeratutako Denboraldia: "));
        
        cbDenboraldiak = new JComboBox<>(new DefaultComboBoxModel<>(denboraldiak.toArray(new Denboraldia[0])));
        pnlGoikoa.add(cbDenboraldiak);
        
        getContentPane().add(pnlGoikoa, BorderLayout.NORTH);

        tabs = new JTabbedPane();
        tabsEguneratu(); // Hasierako karga
        
        getContentPane().add(tabs, BorderLayout.CENTER);

        // Denboraldia aldatzean, fitxa guztiak datu berriekin kargatu
        cbDenboraldiak.addActionListener(e -> tabsEguneratu());
    }

    private void tabsEguneratu() {
        tabs.removeAll();
        Denboraldia d = (Denboraldia) cbDenboraldiak.getSelectedItem();
        
        if (d != null) {
            // Zure metodoak erabiliz: getLigakoTaldeak() eta getLigakoJardunaldi()
            tabs.addTab("Sailkapena", new PanelSailkapena(d.getLigakoTaldeak(), d.getLigakoJardunaldi()));
            tabs.addTab("Taldeak", new PanelTaldeak(d.getLigakoTaldeak()));

            // Rolen araberako fitxak
            if (erabAktiboa instanceof ErabiltzaileAdministraria) {
                tabs.addTab("Admin - Kudeaketa", new PanelAdmin(erabAktiboa, d.getLigakoTaldeak()));
            } 
            else if (erabAktiboa instanceof ErabiltzaileEpaile) {
                tabs.addTab("Epailea - Emaitzak", new PanelEpailea(erabAktiboa, d.getLigakoTaldeak(), d.getLigakoJardunaldi()));
            }
            else if (erabAktiboa instanceof ErabiltzailePresi) {
                tabs.addTab("Presidentea - Taldea", new PanelPresi(erabAktiboa, d.getLigakoTaldeak()));
            }
        }
        tabs.revalidate();
        tabs.repaint();
    }
}