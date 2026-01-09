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

	    // 1. OSO GARRANTZITSUA: JTabbedPane-a hasieratu
	    tabs = new JTabbedPane();

	    // Goiko panela (Iparra)
	    JPanel pnlGoikoa = new JPanel(new BorderLayout());
	    pnlGoikoa.setBackground(new Color(230, 230, 230));

	    // Ezkerreko aldea: Denboraldia
	    JPanel pnlEzkerra = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pnlEzkerra.setOpaque(false);
	    pnlEzkerra.add(new JLabel(" Denboraldia: "));
	    cbDenboraldiak = new JComboBox<>(new DefaultComboBoxModel<>(denboraldiak.toArray(new Denboraldia[0])));
	    pnlEzkerra.add(cbDenboraldiak);

	    // Eskubiko aldea: Logout botoia
	    JPanel pnlEskubia = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    pnlEskubia.setOpaque(false);
	    JButton btnLogout = new JButton("Saioa Itxi");
	    btnLogout.setBackground(new Color(255, 100, 100));
	    btnLogout.setForeground(Color.WHITE);
	    pnlEskubia.add(btnLogout);

	    // Panelak batu
	    pnlGoikoa.add(pnlEzkerra, BorderLayout.WEST);
	    pnlGoikoa.add(pnlEskubia, BorderLayout.EAST);

	    // 2. Osagaiak Frame-ari gehitu
	    getContentPane().add(pnlGoikoa, BorderLayout.NORTH);
	    getContentPane().add(tabs, BorderLayout.CENTER); // Fitxak erdian jarri

	    // 3. Denboraldia aldatzean fitxak eguneratzeko listener-a
	    cbDenboraldiak.addActionListener(e -> tabsEguneratu());

	    // 4. HASIERAKO KARGA: Fitxak lehen aldiz erakutsi daitezen
	    tabsEguneratu();

	    // --- LOGOUT LOGIKA ---
	    btnLogout.addActionListener(e -> {
	        int aukera = JOptionPane.showConfirmDialog(this, "Ziur zaude saioa itxi nahi duzula?", "Saioa Itxi",
	                JOptionPane.YES_NO_OPTION);

	        if (aukera == JOptionPane.YES_OPTION) {
	            new Login().setVisible(true);
	            dispose();
	        }
	    });
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
			} else if (erabAktiboa instanceof ErabiltzaileEpaile) {
				tabs.addTab("Epailea - Emaitzak",
						new PanelEpailea(erabAktiboa, d.getLigakoTaldeak(), d.getLigakoJardunaldi()));
			} else if (erabAktiboa instanceof ErabiltzailePresi) {
				tabs.addTab("Presidentea - Taldea", new PanelPresi(erabAktiboa, d.getLigakoTaldeak()));
			}
		}
		tabs.revalidate();
		tabs.repaint();
	}
}