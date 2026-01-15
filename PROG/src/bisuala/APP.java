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

	// ALDAKETA: Aldaketak dauden kontrolatzeko aldagaia
	private boolean aldaketakDauden = false;

	public APP(Erabiltzaile erab, Federazioa federazioa) {
		this.erabAktiboa = erab;
		this.federazioa = federazioa;

		// Denboraldiak kargatu Federaziotik
		ArrayList<Denboraldia> denboraldiak = federazioa.getDenboraldiak();

		setTitle("FNS Kudeaketa - " + erab.getErabiltzaile());
		setBounds(100, 100, 950, 700);

		// 1. LEIHOA IXTEKO KUDEAKETA (ConfirmarCierre erabiltzen dugu)
		// Leihoa ez ixtea automatikoki, guk kudeatzeko
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// Zure 'ConfirmarCierre' klasea gehitzen dugu entzule gisa
		// 'this' pasatzen diogu APP-aren metodoak erabili ahal izateko
		addWindowListener(new ItziKonfirmazioa(this, federazioa));

		// --- GOIKO PANELA ---
		tabs = new JTabbedPane();

		JPanel pnlGoikoa = new JPanel(new BorderLayout());
		pnlGoikoa.setBackground(new Color(230, 230, 230));

		// Ezkerrean: Denboraldi aukeraketa
		JPanel pnlEzkerra = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlEzkerra.setOpaque(false);
		JLabel labelDenboraldia = new JLabel("Denboraldia:");
		pnlEzkerra.add(labelDenboraldia);

		// ComboBox-a hasieratu
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

		// Eskubian: Saioa itxi botoia
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

		// --- ENTZULEAK (LISTENERS) ---

		// 1. Pestaina aldatzean ComboBox-a ezkutatu Presidentea bada
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

		// 2. ComboBox aldatzean pestainak eguneratu
		cbDenboraldiak.addActionListener(e -> tabsEguneratu());

		// Hasierako karga
		tabsEguneratu();

		// 3. LOGOUT BOTOIA: Orain irteera kudeatzen duen metodora deitzen du
		btnLogout.addActionListener(e -> kudeatuIrteera(true));
	}

	/**
	 * Pestainak eguneratzen ditu aukeratutako denboraldiaren arabera.
	 */
	private void tabsEguneratu() {
		tabs.removeAll();

		// Denboraldi hau COMBOBOX-etik dator (Sailkapenerako eta abar)
		Denboraldia aukeratutakoa = (Denboraldia) cbDenboraldiak.getSelectedItem();

		if (aukeratutakoa != null) {

			// 1. PESTAÑAS COMUNES (Erabiltzaileak aukeratutako denboraldia erakusten dute)
			tabs.addTab("Sailkapena", new PanelSailkapena(aukeratutakoa.getLigakoTaldeak(),
					aukeratutakoa.getLigakoJardunaldi(), aukeratutakoa.getUrtea()));

			tabs.addTab("Taldeak", new PanelTaldeak(aukeratutakoa.getLigakoTaldeak(), aukeratutakoa.getUrtea()));

			// 2. ROLES ESPECÍFICOS
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

	/**
	 * Metodo hau PanelPresi-tik deitzen da denboraldi berri bat sortzen denean. Ez
	 * du automatikoki gordetzen, baina aldaketak daudela markatzen du.
	 */
	public void interfazeaFreskatu() {
		// 1. Aldaketak daudela markatu (gordetzeko abisua ateratzeko gero)
		this.aldaketakDauden = true;

		// 2. ComboBox-a eguneratu listener-ak kenduz momentu batez
		ActionListener[] listeners = cbDenboraldiak.getActionListeners();
		for (ActionListener al : listeners) {
			cbDenboraldiak.removeActionListener(al);
		}

		cbDenboraldiak.removeAllItems();
		ArrayList<Denboraldia> denboraldiak = federazioa.getDenboraldiak();

		if (denboraldiak != null) {
			for (Denboraldia d : denboraldiak) {
				cbDenboraldiak.addItem(d);
			}
			// Azkena aukeratu (sortu berri duguna)
			cbDenboraldiak.setSelectedIndex(denboraldiak.size() - 1);
		}

		for (ActionListener al : listeners) {
			cbDenboraldiak.addActionListener(al);
		}

		// 3. Pestainak berritu
		tabsEguneratu();
	}

	// --- IRTEERA KUDEAKETA ETA GETTER/SETTER ---

	public boolean isAldaketakDauden() {
		return aldaketakDauden;
	}

	public void setAldaketakDauden(boolean aldaketakDauden) {
		this.aldaketakDauden = aldaketakDauden;
	}

	/**
	 * Irteera edo Logout egitean exekutatzen den logika bateratua.
	 * 
	 * @param isLogout Egia bada Login-era doa, Gezurra bada programa ixten du.
	 */
	public void kudeatuIrteera(boolean isLogout) {
		if (aldaketakDauden) {
			// Aldaketak badaude, galdetu
			int aukera = JOptionPane.showConfirmDialog(this, "Aldaketak egin dituzu. Gorde nahi dituzu irten aurretik?",
					"Gorde aldaketak", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

			if (aukera == JOptionPane.YES_OPTION) {
				// BAI -> Gorde eta jarraitu
				DatuKarga.gordeFederazioa(federazioa);
				aldaketakDauden = false;
				exekutatuIrteera(isLogout);

			} else if (aukera == JOptionPane.NO_OPTION) {
				// EZ -> Ez gorde eta jarraitu
				exekutatuIrteera(isLogout);
			}
			// CANCEL -> Ez egin ezer (leihoan geratu)

		} else {
			// Aldaketarik ez badago, konfirmazio sinplea
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