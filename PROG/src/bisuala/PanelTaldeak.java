package bisuala;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import model.*;

public class PanelTaldeak extends JPanel {

    public PanelTaldeak(ArrayList<Talde> taldeak) {
        setLayout(new BorderLayout());

        // Panel nagusia (zerrenda)
        JPanel pnlTaldeZerrenda = new JPanel();
        pnlTaldeZerrenda.setLayout(new BoxLayout(pnlTaldeZerrenda, BoxLayout.Y_AXIS));
        pnlTaldeZerrenda.setBorder(new EmptyBorder(10, 10, 10, 10)); 

        for (Talde t : taldeak) {
            // 1. TALDEAREN PANELA
            JPanel pnlTaldea = new JPanel(new BorderLayout(20, 0)); 
            pnlTaldea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)), 
                new EmptyBorder(15, 10, 15, 10) 
            ));
            pnlTaldea.setBackground(Color.WHITE);
            // Tamaina maximoa finkatu horizontalki zabaltzeko baina bertikalki ez gehiegi
            pnlTaldea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

            // ---------------------------------------------------------
            // A. IRUDIA (EZKUTUA)
            // ---------------------------------------------------------
            JLabel lblEskutua = new JLabel();
            String irudiBidea = t.getEskutua(); 
            URL imgUrl = getClass().getResource(irudiBidea);

            if (imgUrl != null) {
                ImageIcon ikonoOriginala = new ImageIcon(imgUrl);
                Image irudia = ikonoOriginala.getImage();
                int dim = 90; 
                Image irudiaEskalatuta = irudia.getScaledInstance(dim, dim, Image.SCALE_SMOOTH); 
                lblEskutua.setIcon(new ImageIcon(irudiaEskalatuta));
            } else {
                lblEskutua.setText("Irudirik ez");
                lblEskutua.setHorizontalAlignment(SwingConstants.CENTER);
            }

            JPanel pnlIrudia = new JPanel(new GridBagLayout()); 
            pnlIrudia.setBackground(Color.WHITE);
            pnlIrudia.setPreferredSize(new Dimension(100, 100)); 
            
            // GridBagLayout-ek automatikoki zentratzen du osagaia add() egitean
            pnlIrudia.add(lblEskutua);
            
            pnlTaldea.add(pnlIrudia, BorderLayout.WEST);

            // ---------------------------------------------------------
            // B. DATUAK (EZKERREAN LERROKATUTA)
            // ---------------------------------------------------------
            JPanel pnlDatuak = new JPanel();
            pnlDatuak.setLayout(new BoxLayout(pnlDatuak, BoxLayout.Y_AXIS));
            pnlDatuak.setBackground(Color.WHITE);

            // --- IZENBURUA ---
            JLabel lblIzena = new JLabel(t.getIzena().toUpperCase());
            lblIzena.setFont(new Font("Arial", Font.BOLD, 18));
			lblIzena.setForeground(new Color(135, 21, 33));
            // GAKOA: Hau da lerrokatzen duena
            lblIzena.setAlignmentX(Component.LEFT_ALIGNMENT); 
            
            // --- INFO (Estadioa / Herria) ---
            JLabel lblInfo = new JLabel("Zelaia: " + t.getFutbolZelaia() + "  |  Herria: " + t.getHiria());
            lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
            lblInfo.setForeground(Color.GRAY);
            // GAKOA: Hau da lerrokatzen duena
            lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Gehitu panelera
            pnlDatuak.add(lblIzena);
            pnlDatuak.add(Box.createRigidArea(new Dimension(0, 4))); // Tarte txikia
            pnlDatuak.add(lblInfo);
            pnlDatuak.add(Box.createRigidArea(new Dimension(0, 12))); // Tarte handiagoa

            // --- JOKALARIEN GRID-A ---
            JPanel pnlJokalariak = new JPanel(new GridLayout(0, 2, 10, 5)); // 2 Zutabe
            pnlJokalariak.setBackground(Color.WHITE);
            // GAKOA: Panela bera ere ezkerrera lerrokatu
            pnlJokalariak.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Jokalarien izenburua
            JLabel lblJokIzenburua = new JLabel("JOKALARIAK:");
            lblJokIzenburua.setFont(new Font("Arial", Font.BOLD, 11));
            lblJokIzenburua.setAlignmentX(Component.LEFT_ALIGNMENT); // GAKOA
            
            pnlDatuak.add(lblJokIzenburua);
            pnlDatuak.add(Box.createRigidArea(new Dimension(0, 5)));

            if (t.getJokalariak() != null && !t.getJokalariak().isEmpty()) {
                for (Jokalari j : t.getJokalariak()) {
                    String testua = "• " + j.getDortsala() +" - "+ j.getIzena() + " " + j.getAbizena() + " (" + j.getPosizio() + ")";
                    JLabel lblJokalari = new JLabel(testua);
                    lblJokalari.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                    // Grid barruan daudenez, hauek automatikoki lerrokatzen dira, 
                    // baina ziurtasunagatik ezkerrera behartu dezakegu:
                    lblJokalari.setHorizontalAlignment(SwingConstants.LEFT);
                    pnlJokalariak.add(lblJokalari);
                }
            } else {
                JLabel lblHutsik = new JLabel("(Ez dago jokalaririk)");
                lblHutsik.setForeground(Color.RED);
                pnlJokalariak.add(lblHutsik);
            }

            pnlDatuak.add(pnlJokalariak);

            // Datuen panela taldera gehitu
            pnlTaldea.add(pnlDatuak, BorderLayout.CENTER);

            // ---------------------------------------------------------
            // C. ZERRENDA NAGUSIRA GEHITU
            // ---------------------------------------------------------
            pnlTaldeZerrenda.add(pnlTaldea);
        }

        JScrollPane scrollPane = new JScrollPane(pnlTaldeZerrenda);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
}