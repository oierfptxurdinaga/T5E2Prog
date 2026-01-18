package bisuala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.*;
import utils.DatuKarga;

public class APP extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<Denboraldia> cbDenboraldiak;
    private JTabbedPane tabs;
    private Erabiltzaile erabAktiboa;
    private Federazioa federazioa;
    private boolean aldaketakDauden = false;

    public APP(Erabiltzaile erab, Federazioa federazioa) {
        // ... (Todo el constructor se queda IGUAL que lo tenías) ...
        this.erabAktiboa = erab;
        this.federazioa = federazioa;
        ArrayList<Denboraldia> denboraldiak = federazioa.getDenboraldiak();

        setTitle("FNS Kudeaketa - " + erab.getErabiltzaile());
        setBounds(100, 100, 950, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new ItziKonfirmazioa(this, federazioa));

        tabs = new JTabbedPane();
        JPanel pnlGoikoa = new JPanel(new BorderLayout());
        pnlGoikoa.setBackground(new Color(230, 230, 230));

        JPanel pnlEzkerra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlEzkerra.setOpaque(false);
        JLabel labelDenboraldia = new JLabel("Denboraldia:");
        pnlEzkerra.add(labelDenboraldia);

        cbDenboraldiak = new JComboBox<>();
        if (denboraldiak != null) {
            for (Denboraldia d : denboraldiak) {
                cbDenboraldiak.addItem(d);
            }
            if (!denboraldiak.isEmpty()) {
                cbDenboraldiak.setSelectedIndex(denboraldiak.size() - 1);
            }
        }
        pnlEzkerra.add(cbDenboraldiak);

        JPanel pnlEskubia = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlEskubia.setOpaque(false);
        JButton btnLogout = new JButton("Saioa Itxi");
        btnLogout.setBackground(new Color(135, 21, 33));
        btnLogout.setForeground(Color.WHITE);
        pnlEskubia.add(btnLogout);

        pnlGoikoa.add(pnlEzkerra, BorderLayout.WEST);
        pnlGoikoa.add(pnlEskubia, BorderLayout.EAST);

        getContentPane().add(pnlGoikoa, BorderLayout.NORTH);
        getContentPane().add(tabs, BorderLayout.CENTER);

        tabs.addChangeListener(e -> {
            Component panelAktiboa = tabs.getSelectedComponent();
            if (panelAktiboa instanceof PanelPresi) {
                labelDenboraldia.setVisible(false);
                cbDenboraldiak.setVisible(false);
            } else {
                labelDenboraldia.setVisible(true);
                cbDenboraldiak.setVisible(true);
            }
        });

        cbDenboraldiak.addActionListener(e -> tabsEguneratu());
        tabsEguneratu();
        btnLogout.addActionListener(e -> kudeatuIrteera(true));
    }

    private void tabsEguneratu() {
        tabs.removeAll();
        Denboraldia aukeratutakoa = (Denboraldia) cbDenboraldiak.getSelectedItem();

        if (aukeratutakoa != null) {
            // 1. Sailkapena
            ArrayList<DenboraldiTalde> sailkapena = aukeratutakoa.getSailkapena(); // Ziurtatu metodo hau existitzen dela edo kalkulatzen duzula
            tabs.addTab("Sailkapena", new PanelSailkapena(sailkapena, aukeratutakoa.getUrtea()));

            // 2. Taldeak
            tabs.addTab("Taldeak", new PanelTaldeak(aukeratutakoa.getLigakoTaldeak(), aukeratutakoa.getUrtea()));
            
            // 3. JARDUNALDIAK (Hau da falta zena) [GEHITU LERRO HAU]
            tabs.addTab("Jardunaldiak", new PanelJardunaldiak(aukeratutakoa));

            // 4. Erabiltzailearen araberako panelak
            if (erabAktiboa instanceof ErabiltzaileAdministraria) {
                tabs.addTab("Admin - Kudeaketa", new PanelAdmin(erabAktiboa, aukeratutakoa.getLigakoTaldeak()));

            } else if (erabAktiboa instanceof ErabiltzaileEpaile) {
                tabs.addTab("Epailea - Emaitzak", new PanelEpailea(erabAktiboa, aukeratutakoa.getLigakoTaldeak(),
                        aukeratutakoa.getLigakoJardunaldi()));

            } else if (erabAktiboa instanceof ErabiltzailePresi) {
                tabs.addTab("Presidentea - Taldea",
                        new PanelPresi(erabAktiboa, this.federazioa, federazioa.getUnekoDenboraldia(), this));
            }
        }

        tabs.revalidate();
        tabs.repaint();
    }
    // ... (El resto de métodos: interfazeaFreskatu, kudeatuIrteera, etc. se quedan IGUAL) ...
    public void interfazeaFreskatu() {
        this.aldaketakDauden = true;
        ActionListener[] listeners = cbDenboraldiak.getActionListeners();
        for (ActionListener al : listeners) { cbDenboraldiak.removeActionListener(al); }
        cbDenboraldiak.removeAllItems();
        ArrayList<Denboraldia> denboraldiak = federazioa.getDenboraldiak();
        if (denboraldiak != null) {
            for (Denboraldia d : denboraldiak) { cbDenboraldiak.addItem(d); }
            cbDenboraldiak.setSelectedIndex(denboraldiak.size() - 1);
        }
        for (ActionListener al : listeners) { cbDenboraldiak.addActionListener(al); }
        tabsEguneratu();
    }

    public boolean isAldaketakDauden() { return aldaketakDauden; }
    public void setAldaketakDauden(boolean aldaketakDauden) { this.aldaketakDauden = aldaketakDauden; }

    public void kudeatuIrteera(boolean isLogout) {
        if (aldaketakDauden) {
            int aukera = JOptionPane.showConfirmDialog(this, "Aldaketak egin dituzu. Gorde nahi dituzu irten aurretik?",
                    "Gorde aldaketak", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (aukera == JOptionPane.YES_OPTION) {
                DatuKarga.gordeFederazioa(federazioa);
                aldaketakDauden = false;
                exekutatuIrteera(isLogout);
            } else if (aukera == JOptionPane.NO_OPTION) {
                exekutatuIrteera(isLogout);
            }
        } else {
            int aukera = JOptionPane.showConfirmDialog(this,
                    isLogout ? "Ziur zaude saioa itxi nahi duzula?" : "Ziur zaude programa itxi nahi duzula?",
                    "Konfirmatu", JOptionPane.YES_NO_OPTION);
            if (aukera == JOptionPane.YES_OPTION) {
                exekutatuIrteera(isLogout);
            }
        }
    }

    private void exekutatuIrteera(boolean isLogout) {
        if (isLogout) {
            new Login().setVisible(true);
            dispose();
        } else {
            System.exit(0);
        }
    }
}