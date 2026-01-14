package bisuala;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import model.*;

public class PanelSailkapena extends JPanel {

    private JTable taula;
    private DefaultTableModel modeloa;

    /**
     * ERAIKITZAILEA (CONSTRUCTOR)
     * Taldeak behar ditugu izenak jakiteko, eta Jornadak puntuak kalkulatzeko.
     */
    public PanelSailkapena(ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldi) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // 1. IZENBURUA
        JLabel lblIzenburua = new JLabel("SAILKAPENA");
        lblIzenburua.setFont(new Font("Arial", Font.BOLD, 24));
        lblIzenburua.setForeground(new Color(0, 51, 102));
        lblIzenburua.setHorizontalAlignment(SwingConstants.CENTER);
        lblIzenburua.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(lblIzenburua, BorderLayout.NORTH);

        // 2. KALKULAGAILUA
        // Metodo honek partidu guztiak irakurri eta estatistikak itzultzen ditu
        ArrayList<TaldeStats> estatistikak = kalkulatuEstatistikak(taldeak, jardunaldi);

        // 3. ORDENATU (Puntuak -> Golaverage -> Golak Alde)
        Collections.sort(estatistikak, new Comparator<TaldeStats>() {
            @Override
            public int compare(TaldeStats t1, TaldeStats t2) {
                // Lehenengo Puntuak (Handienetik txikira)
                int diffPuntuak = Integer.compare(t2.puntuak, t1.puntuak);
                if (diffPuntuak != 0) return diffPuntuak;

                // Berdinak badira, Golaverage Orokorra (Alde - Aurka)
                int diffAVG = (t2.gAlde - t2.gAurka) - (t1.gAlde - t1.gAurka);
                if (diffAVG != 0) return diffAVG;

                // Hori ere berdina bada, Golak Alde
                return Integer.compare(t2.gAlde, t1.gAlde);
            }
        });

        // 4. TAULA BISUALA SORTU
        String[] zutabeak = {"Pos", "Taldea", "PJ", "I", "B", "G", "GF", "GC", "AVG", "PTS"};
        Object[][] data = new Object[estatistikak.size()][10];

        for (int i = 0; i < estatistikak.size(); i++) {
            TaldeStats s = estatistikak.get(i);
            int average = s.gAlde - s.gAurka;

            data[i][0] = i + 1;                  // Posizioa
            data[i][1] = s.taldeIzena;           // Izena
            data[i][2] = s.jokatuak;             // PJ
            data[i][3] = s.irabaziak;            // I
            data[i][4] = s.berdinduak;           // B
            data[i][5] = s.galduak;              // G
            data[i][6] = s.gAlde;                // GF
            data[i][7] = s.gAurka;               // GC
            data[i][8] = average > 0 ? "+" + average : average; // AVG
            data[i][9] = s.puntuak;              // PUNTUAK
        }

        // Modelo ez-editagarria
        modeloa = new DefaultTableModel(data, zutabeak) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        taula = new JTable(modeloa);

        // --- DISEINUA (Aurreko berdina) ---
        konfiguratuDiseinua();

        JScrollPane scroll = new JScrollPane(taula);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200,200,200)));
        add(scroll, BorderLayout.CENTER);
    }

    /**
     * LOGIKA PURUA: Metodo honek ez du ezer margotzen.
     * Partiduak prozesatu eta zenbakiak kalkulatzen ditu memorian.
     */
    private ArrayList<TaldeStats> kalkulatuEstatistikak(ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldi) {
        Map<String, TaldeStats> mapaStats = new HashMap<>();

        // 1. Inicializar mapa
        if (taldeak != null) {
            for (Talde t : taldeak) {
                // .trim() erabiltzen dugu espazio hutsak kentzeko
                mapaStats.put(t.getIzena().trim(), new TaldeStats(t.getIzena()));
            }
        }

        // 2. Leer partidos
        if (jardunaldi != null) {
            for (Jardunaldi j : jardunaldi) {
                if (j.getPartiduak() != null) {
                    for (Partidua p : j.getPartiduak()) { // Ojo: Cambiado Partida -> Partidua
                        
                        // ERABILITAKO METODO BERRIA:
                        // -1 bada, continue egiten du (saltatu)
                        if (!p.jokatutaDago()) continue;

                        // Izenak lortu (.trim() segurtasunagatik)
                        String localNom = p.getEtxekoTaldea().getIzena().trim();
                        String visitNom = p.getKanpokoTaldea().getIzena().trim();

                        TaldeStats sLocal = mapaStats.get(localNom);
                        TaldeStats sVisit = mapaStats.get(visitNom);

                        // SEGURTASUNA: Talderen bat aurkitzen ez badu (izena ezberdina delako)
                        if (sLocal == null || sVisit == null) {
                            System.err.println("ERROREA: Ez da taldea aurkitu mapan: " + localNom + " vs " + visitNom);
                            continue;
                        }

                        int gL = p.getEtxekoGolak();
                        int gK = p.getKanpokoGolak();

                        // Sumar goles
                        sLocal.gAlde += gL; sLocal.gAurka+= gK;
                        sVisit.gAlde += gK; sVisit.gAurka += gL;
                        sLocal.jokatuak++; sVisit.jokatuak++;

                        // Repartir puntos
                        if (gL > gK) { 
                            sLocal.irabaziak++; sLocal.puntuak += 3;
                            sVisit.galduak++;
                        } else if (gK > gL) { 
                            sVisit.irabaziak++; sVisit.puntuak += 3;
                            sLocal.galduak++;
                        } else { 
                            sLocal.berdinduak++; sLocal.puntuak += 1;
                            sVisit.berdinduak++; sVisit.puntuak += 1;
                        }
                    }
                }
            }
        }
        return new ArrayList<>(mapaStats.values());
    }

    // --- DISEINU KONFIGURAZIOA ---
    private void konfiguratuDiseinua() {
        taula.setRowHeight(35);
        taula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taula.setGridColor(new Color(230, 230, 230));
        taula.setShowVerticalLines(false);
        
        JTableHeader header = taula.getTableHeader();
        header.setBackground(new Color(0, 51, 102));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 40));

        taula.getColumnModel().getColumn(0).setPreferredWidth(40);
        taula.getColumnModel().getColumn(1).setPreferredWidth(200);

        // Renderer-ak aplikatu
        for (int i = 0; i < taula.getColumnCount(); i++) {
            boolean zentratu = (i != 1);
            taula.getColumnModel().getColumn(i).setCellRenderer(new EstiloRenderer(zentratu));
        }
    }

    // --- KLASE INTERNOAK (Barne erabilerarako bakarrik) ---

    /**
     * DTO (Data Transfer Object): 
     * Klase honek BAKARRIK balio du RAM memorian datuak une batez gordetzeko.
     * Ez da datu basean gordetzen.
     */
    private class TaldeStats {
        String taldeIzena;
        int jokatuak = 0;
        int irabaziak = 0;
        int berdinduak = 0;
        int galduak = 0;
        int gAlde = 0;
        int gAurka = 0;
        int puntuak = 0;

        public TaldeStats(String izena) {
            this.taldeIzena = izena;
        }
    }

    /**
     * RENDERER: Zebra efektua eta loditasunak jartzeko
     */
    private class EstiloRenderer extends DefaultTableCellRenderer {
        private boolean zentratu;
        public EstiloRenderer(boolean zentratu) { this.zentratu = zentratu; }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (!isSelected) {
                // Zebra efektua: Bakoitiak zuri, Bikoitiak gris argi
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
            }
            // Posizioa eta Puntuak lodiz (Bold)
            if (column == 0 || column == 9) c.setFont(c.getFont().deriveFont(Font.BOLD));

            if (zentratu) setHorizontalAlignment(JLabel.CENTER);
            else {
                setHorizontalAlignment(JLabel.LEFT);
                setBorder(new EmptyBorder(0, 10, 0, 0));
            }
            return c;
        }
    }
}