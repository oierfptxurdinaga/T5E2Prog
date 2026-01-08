package model;

import javax.swing.JOptionPane;

public class ErabiltzailePresi extends Erabiltzaile{

	public ErabiltzailePresi(String erabiltzaile, String pasahitza) {
		super(erabiltzaile, pasahitza);
	}

	/**
	 * Jokalari bat talde batetik bestera pasatzeko balio du
	 * @param jokalari Zein jokalari aldatu nahi duzun taldez
	 * @param taldeZaharra jokalaria dagoen taldea
	 * @param taldeBerria jokalria eramango den taldea
	 */
	public void aldatuJokalariak(Jokalari jokalari, Talde taldeZaharra, Talde taldeBerria) {
		if(taldeZaharra.getJokalariak().contains(jokalari)) {
			taldeZaharra.getJokalariak().remove(jokalari);
			taldeBerria.getJokalariak().add(jokalari);
		}else {
			JOptionPane.showMessageDialog(null, "Jokalari hori ez dago talde horretan", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
