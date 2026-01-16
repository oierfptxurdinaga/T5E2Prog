package bisuala;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections; // <--- HAU BEHARREZKOA DA
import java.util.Comparator;  // <--- HAU ERE BAI
import model.*;

public class PanelPresi extends JPanel {
    private static final long serialVersionUID = 1L;
    private final Color LIGHTRED = new Color(219, 175, 175);
    private final Color LIGHTGREEN = new Color(218, 245, 213);
    private APP aplikazioNagusia;

    public PanelPresi(Erabiltzaile erab, Federazioa federazioa, Denboraldia unekoDenboraldia, APP app) {
        this.aplikazioNagusia = app;
        setLayout(new BorderLayout());

        // 1. Zerrenda nagusia lortu
        ArrayList<Talde> taldeGuztiak = federazioa.getTaldeGuztiak();
        
        // 2. Denboraldiko zerrenda lortu
        ArrayList<Talde> taldeJokatzen = (unekoDenboraldia != null) ? unekoDenboraldia.getLigakoTaldeak() : new ArrayList<>();

        // Izenburua
        JLabel lblIzenburua = new JLabel("Federazioko Presidentea: " + erab.getErabiltzaile());
        lblIzenburua.setHorizontalAlignment(SwingConstants.CENTER);
        lblIzenburua.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblIzenburua, BorderLayout.NORTH);

        // Zerrenda Panela
        JPanel pnlZerrenda = new JPanel();
        pnlZerrenda.setLayout(new BoxLayout(pnlZerrenda, BoxLayout.Y_AXIS));

        if (taldeGuztiak != null && !taldeGuztiak.isEmpty()) {
            
            // --- ORDENAZIOA: HEMEN DAGO GAKOA ---
            
            // 1. Zerrendaren kopia bat egiten dugu jatorrizkoa ez nahasteko
            ArrayList<Talde> taldeOrdenatuak = new ArrayList<>(taldeGuztiak);
            
            // 2. Ordenatu egiten dugu: Jokatzen dutenak LEHENAGO
            Collections.sort(taldeOrdenatuak, new Comparator<Talde>() {
                @Override
                public int compare(Talde t1, Talde t2) {
                    boolean t1Jokatzen = taldeJokatzen.contains(t1);
                    boolean t2Jokatzen = taldeJokatzen.contains(t2);
                    
                    if (t1Jokatzen && !t2Jokatzen) {
                        return -1; // t1 goian (Jokatzen du)
                    } else if (!t1Jokatzen && t2Jokatzen) {
                        return 1;  // t2 goian (Jokatzen du)
                    } else {
                        // Biak egoera berean badaude, izenaren arabera ordenatu
                        return t1.getIzena().compareToIgnoreCase(t2.getIzena());
                    }
                }
            });
            // -------------------------------------

            // ORAIN 'taldeOrdenatuak' ERABILTZEN DUGU BEGIZTAN
            for (Talde t : taldeOrdenatuak) {
                
                boolean jokatzenAriDa = taldeJokatzen.contains(t);
                Color kolorea = jokatzenAriDa ? LIGHTGREEN : LIGHTRED;
                
                // --- 1. TALDEAREN PANELA ---
                JPanel pnlTaldeaPresi = new JPanel(new BorderLayout(20, 0));
                pnlTaldeaPresi.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                        new EmptyBorder(15, 10, 15, 10)));
                
                pnlTaldeaPresi.setBackground(kolorea);
                pnlTaldeaPresi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

                // --- A. IRUDIA ---
                JLabel lblEskutua = new JLabel();
                // Kontuz hemen: t.getEskutua() erabiltzen dugu, zuk esan bezala
                String path = t.getEskutua(); 
                
                if (path != null) {
                    URL imgUrl = getClass().getResource(path);
                    if (imgUrl != null) {
                        ImageIcon ikonoOriginala = new ImageIcon(imgUrl);
                        Image irudia = ikonoOriginala.getImage();
                        Image irudiaEskalatuta = irudia.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                        lblEskutua.setIcon(new ImageIcon(irudiaEskalatuta));
                    } else {
                        lblEskutua.setText("Ez dago");
                    }
                } else {
                    lblEskutua.setText("Irudirik ez");
                }
                lblEskutua.setHorizontalAlignment(SwingConstants.CENTER);

                JPanel pnlIrudia = new JPanel(new GridBagLayout());
                pnlIrudia.setBackground(kolorea);
                pnlIrudia.setPreferredSize(new Dimension(100, 100));
                pnlIrudia.add(lblEskutua);
                pnlTaldeaPresi.add(pnlIrudia, BorderLayout.WEST);

                // --- B. DATUAK ---
                JPanel pnlDatuak = new JPanel();
                pnlDatuak.setLayout(new BoxLayout(pnlDatuak, BoxLayout.Y_AXIS));
                pnlDatuak.setBackground(kolorea);

                JLabel lblIzena = new JLabel(t.getIzena().toUpperCase());
                lblIzena.setFont(new Font("Arial", Font.BOLD, 18));
                lblIzena.setForeground(new Color(135, 21, 33));
                lblIzena.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel lblInfo = new JLabel("Zelaia: " + t.getFutbolZelaia() + "  |  Herria: " + t.getHiria());
                lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
                lblInfo.setForeground(Color.GRAY);
                lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                String egoeraTestua = jokatzenAriDa ? "(Ligan Inskribatuta)" : "(Ez du jokatzen denboraldi honetan)";
                JLabel lblEgoera = new JLabel(egoeraTestua);
                lblEgoera.setFont(new Font("Arial", Font.ITALIC, 10));
                lblEgoera.setAlignmentX(Component.LEFT_ALIGNMENT);

                pnlDatuak.add(lblIzena);
                pnlDatuak.add(Box.createRigidArea(new Dimension(0, 4)));
                pnlDatuak.add(lblInfo);
                pnlDatuak.add(lblEgoera);
                pnlDatuak.add(Box.createRigidArea(new Dimension(0, 12)));

                // --- JOKALARIAK ---
                JPanel pnlJokalariak = new JPanel(new GridLayout(0, 2, 10, 5));
                pnlJokalariak.setBackground(kolorea);
                pnlJokalariak.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel lblJokIzenburua = new JLabel("JOKALARIAK:");
                lblJokIzenburua.setFont(new Font("Arial", Font.BOLD, 11));
                lblJokIzenburua.setAlignmentX(Component.LEFT_ALIGNMENT);

                pnlDatuak.add(lblJokIzenburua);
                pnlDatuak.add(Box.createRigidArea(new Dimension(0, 5)));

                if (t.getJokalariak() != null && !t.getJokalariak().isEmpty()) {
                    for (Jokalari j : t.getJokalariak()) {
                        String testua = "• " + j.getDortsala() + " - " + j.getIzena() + " (" + j.getPosizio() + ")"; 
                        JLabel lblJokalari = new JLabel(testua);
                        lblJokalari.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        lblJokalari.setHorizontalAlignment(SwingConstants.LEFT);
                        pnlJokalariak.add(lblJokalari);
                    }
                } else {
                    JLabel lblHutsik = new JLabel("Ez dago jokalaririk");
                    lblHutsik.setForeground(Color.RED);
                    pnlJokalariak.add(lblHutsik);
                }
                pnlDatuak.add(pnlJokalariak);
                pnlTaldeaPresi.add(pnlDatuak, BorderLayout.CENTER);
                pnlZerrenda.add(pnlTaldeaPresi);
            }
            
            //SCROLL AZKARRA EGITEKO
            JScrollPane scroll = new JScrollPane(pnlZerrenda);
            scroll.getVerticalScrollBar().setUnitIncrement(20);
            add(scroll, BorderLayout.CENTER);
        }

        // --- BOTOIAK ---
        JPanel pnlBotoiak = new JPanel();
        JButton btnHasi = new JButton("Denboraldia hasi");
        btnHasi.addActionListener(e -> {
            LeihoaDenboraldiBerria leihoa = new LeihoaDenboraldiBerria(federazioa);
            leihoa.setVisible(true);

            if (leihoa.isOndoSortuDa()) {
                aplikazioNagusia.interfazeaFreskatu();
            } else {
                System.out.println("Ez da denboraldirik sortu.");
            }
        });
        
        pnlBotoiak.add(btnHasi);
        add(pnlBotoiak, BorderLayout.SOUTH);
    }
}