package bisuala;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import model.*;

public class PanelTaldeak extends JPanel {

    public PanelTaldeak(ArrayList<Talde> taldeak) {
        setLayout(new BorderLayout());

        // Zerrenda nagusia edukiko duen panela (bertikalean antolatuta)
        JPanel pnlTaldeZerrenda = new JPanel();
        pnlTaldeZerrenda.setLayout(new BoxLayout(pnlTaldeZerrenda, BoxLayout.Y_AXIS));

        // Talde bakoitza zeharkatu
        for (Talde t : taldeak) {
            // 1. Talde bakoitzerako panel propio bat sortu
            // BorderLayout erabiltzen dugu: Ezkerrean irudia, erdian testua
            JPanel pnlTaldea = new JPanel(new BorderLayout(15, 15)); 
            pnlTaldea.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY)); // Banatzailea behean
            pnlTaldea.setBackground(Color.WHITE);
            pnlTaldea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180)); // Altuera maximoa mugatu

            // ---------------------------------------------------------
            // A. IRUDIA (ESKUTUA)
            // ---------------------------------------------------------
            JLabel lblEskutua = new JLabel();
            
            // Datu basetik datorren bidea (adibidez: "/resources/images/barca.jpg")
            String irudiBidea = t.getEskutua(); 
            
            // 'src' barruan baliabideak bilatzeko modu zuzena
            URL imgUrl = getClass().getResource(irudiBidea);

            if (imgUrl != null) {
                // Irudia kargatu eta tamaina egokitu (100x100 px)
                ImageIcon ikonoOriginala = new ImageIcon(imgUrl);
                Image irudiaEskalatuta = ikonoOriginala.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblEskutua.setIcon(new ImageIcon(irudiaEskalatuta));
            } else {
                // Irudia ez bada aurkitzen, testu bat erakutsi
                lblEskutua.setText("Irudirik ez");
                lblEskutua.setHorizontalAlignment(SwingConstants.CENTER);
                System.err.println("Errorea: Ez da irudia aurkitu bide honetan: " + irudiBidea);
            }
            
            // Irudia panel txiki batean sartu zentratuta egoteko
            JPanel pnlIrudia = new JPanel(new GridBagLayout());
            pnlIrudia.setBackground(Color.WHITE);
            pnlIrudia.setPreferredSize(new Dimension(120, 120));
            pnlIrudia.add(lblEskutua);
            
            // Irudia EZKERREAN gehitu
            pnlTaldea.add(pnlIrudia, BorderLayout.WEST);

            // ---------------------------------------------------------
            // B. INFORMAZIOA (TESTUA)
            // ---------------------------------------------------------
            JTextArea txtInfo = new JTextArea();
            txtInfo.setEditable(false);
            txtInfo.setOpaque(false); // Atzeko planoa gardena
            txtInfo.setFont(new Font("Arial", Font.PLAIN, 12));
            txtInfo.setMargin(new Insets(10, 0, 10, 10)); // Marjinak
            
            // Testua eraiki StringBuilder erabiliz
            StringBuilder sb = new StringBuilder();
            sb.append("TALDEA: ").append(t.getIzena().toUpperCase()).append("\n");
            sb.append("Estadioa: ").append(t.getFutbolZelaia()).append("\n");
            sb.append("Herria: ").append(t.getHiria()).append("\n");
            sb.append("--------------------------------------------------\n");
            sb.append("Jokalariak (").append(t.getJokalariak().size()).append("):\n");

            if (t.getJokalariak() != null) {
                for (Jokalari j : t.getJokalariak()) {
                    sb.append("   • ").append(j.getIzena()).append(" ").append(j.getAbizena())
                      .append("  [ZK: ").append(j.getDortsala()).append("] - ")
                      .append(j.getPosizio()).append("\n");
                }
            } else {
                sb.append("   (Ez dago jokalaririk erregistratuta)\n");
            }
            
            txtInfo.setText(sb.toString());
            
            // Testua ERDIAN gehitu (Scrollarekin testua luzea bada)
            pnlTaldea.add(new JScrollPane(txtInfo), BorderLayout.CENTER);

            // ---------------------------------------------------------
            // C. ZERRENDA NAGUSIRA GEHITU
            // ---------------------------------------------------------
            pnlTaldeZerrenda.add(pnlTaldea);
        }

        // Dena ScrollPane orokor batean sartu
        add(new JScrollPane(pnlTaldeZerrenda), BorderLayout.CENTER);
    }
}