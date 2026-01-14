package bisuala;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import model.*;

public class PanelPresi extends JPanel {
	private static final long serialVersionUID = 1L;
	private final Color LIGHTRED = new Color(219, 175, 175);
    private final Color LIGHTGREEN = new Color(218, 245, 213);
    private APP aplikazioNagusia;

    // CAMBIO 1: El constructor ahora necesita la FEDERACIÓN (todos) y la TEMPORADA ACTUAL (los que juegan)
    public PanelPresi(Erabiltzaile erab, Federazioa federazioa, Denboraldia unekoDenboraldia, APP app) {
    	this.aplikazioNagusia = app;
        setLayout(new BorderLayout());

        // 1. Obtenemos la lista MAESTRA (Todos los equipos existentes, jueguen o no)
        ArrayList<Talde> taldeGuztiak = federazioa.getTaldeGuztiak();
        
        // 2. Obtenemos la lista de la TEMPORADA (Solo los que juegan este año)
        // Usamos una lista vacía si la temporada es null para evitar errores
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
            for (Talde t : taldeGuztiak) {
                
                // --- LOGICA CLAVE AQUI ---
                // Preguntamos: ¿Está este equipo (t) dentro de la lista de la temporada?
                boolean jokatzenAriDa = taldeJokatzen.contains(t);
                
                // Decidimos el color UNA VEZ para usarlo en todos los paneles
                Color kolorea = jokatzenAriDa ? LIGHTGREEN : LIGHTRED;
                
                // --- 1. TALDEAREN PANELA ---
                JPanel pnlTaldeaPresi = new JPanel(new BorderLayout(20, 0));
                pnlTaldeaPresi.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)),
                        new EmptyBorder(15, 10, 15, 10)));
                
                pnlTaldeaPresi.setBackground(kolorea); // <--- Usamos la variable
                pnlTaldeaPresi.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

                // --- A. IRUDIA ---
                JLabel lblEskutua = new JLabel();
                URL imgUrl = getClass().getResource(t.getEskutua());

                if (imgUrl != null) {
                    ImageIcon ikonoOriginala = new ImageIcon(imgUrl);
                    Image irudia = ikonoOriginala.getImage();
                    Image irudiaEskalatuta = irudia.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                    lblEskutua.setIcon(new ImageIcon(irudiaEskalatuta));
                } else {
                    lblEskutua.setText("Irudirik ez");
                    lblEskutua.setHorizontalAlignment(SwingConstants.CENTER);
                }

                JPanel pnlIrudia = new JPanel(new GridBagLayout());
                pnlIrudia.setBackground(kolorea); // <--- Usamos la variable
                pnlIrudia.setPreferredSize(new Dimension(100, 100));
                pnlIrudia.add(lblEskutua);
                pnlTaldeaPresi.add(pnlIrudia, BorderLayout.WEST);

                // --- B. DATUAK ---
                JPanel pnlDatuak = new JPanel();
                pnlDatuak.setLayout(new BoxLayout(pnlDatuak, BoxLayout.Y_AXIS));
                pnlDatuak.setBackground(kolorea); // <--- Usamos la variable

                JLabel lblIzena = new JLabel(t.getIzena().toUpperCase());
                lblIzena.setFont(new Font("Arial", Font.BOLD, 18));
                lblIzena.setForeground(new Color(135, 21, 33));
                lblIzena.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel lblInfo = new JLabel("Zelaia: " + t.getFutbolZelaia() + "  |  Herria: " + t.getHiria());
                lblInfo.setFont(new Font("Arial", Font.PLAIN, 12));
                lblInfo.setForeground(Color.GRAY);
                lblInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
                
                // Etiqueta extra para clarificar estado (Opcional)
                String egoeraTestua = jokatzenAriDa ? "(Ligan Inskribatuta)" : "(Ez du jokatzen denboraldi honetan)";
                JLabel lblEgoera = new JLabel(egoeraTestua);
                lblEgoera.setFont(new Font("Arial", Font.ITALIC, 10));
                lblEgoera.setAlignmentX(Component.LEFT_ALIGNMENT);

                pnlDatuak.add(lblIzena);
                pnlDatuak.add(Box.createRigidArea(new Dimension(0, 4)));
                pnlDatuak.add(lblInfo);
                pnlDatuak.add(lblEgoera); // Añadido opcional
                pnlDatuak.add(Box.createRigidArea(new Dimension(0, 12)));

                // --- JOKALARIAK ---
                JPanel pnlJokalariak = new JPanel(new GridLayout(0, 2, 10, 5));
                pnlJokalariak.setBackground(kolorea); // <--- Usamos la variable
                pnlJokalariak.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel lblJokIzenburua = new JLabel("JOKALARIAK:");
                lblJokIzenburua.setFont(new Font("Arial", Font.BOLD, 11));
                lblJokIzenburua.setAlignmentX(Component.LEFT_ALIGNMENT);

                pnlDatuak.add(lblJokIzenburua);
                pnlDatuak.add(Box.createRigidArea(new Dimension(0, 5)));

                if (t.getJokalariak() != null && !t.getJokalariak().isEmpty()) {
                    for (Jokalari j : t.getJokalariak()) {
                        String testua = "• " + j.getDortsala() + " - " + j.getIzena() + " (" + j.getPosizio() +")"; 
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
            add(new JScrollPane(pnlZerrenda), BorderLayout.CENTER);
        }

        // --- BOTONES ---
        JPanel pnlBotoiak = new JPanel();
        JButton btnHasi = new JButton("Denboraldia hasi");
        btnHasi.addActionListener(e -> {
            // 1. Leihoa sortu eta ireki
            LeihoaDenboraldiBerria leihoa = new LeihoaDenboraldiBerria(federazioa);
            leihoa.setVisible(true); // Hemen gelditzen da leihoa itxi arte

            // 2. GAKOA: Egiaztatu ea ondo sortu den itxi aurretik
            if (leihoa.isOndoSortuDa()) {
                
                // Soilik ondo sortu bada freskatzen dugu
                aplikazioNagusia.interfazeaFreskatu();
                
                // Eta mezua hemen (aukerakoa, zeren LeihoaDenboraldiBerria-k jada eman du mezua)
                // JOptionPane.showMessageDialog(this, "Datuak kargatu dira.");
                
            } else {
                // "X" edo "Utzi" sakatu badu, ez dugu ezer egiten.
                System.out.println("Ez da denboraldirik sortu.");
            }
        });
        
        pnlBotoiak.add(btnHasi);
        add(pnlBotoiak, BorderLayout.SOUTH);
    }
}