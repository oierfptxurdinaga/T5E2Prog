package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ErabiltzailePresi extends Erabiltzaile {

	public ErabiltzailePresi(String erabiltzaile, String pasahitza) {
		super(erabiltzaile, pasahitza);
	}

	// getters and setters
	public String getErabiltzaile() {
		return erabiltzaile;
	}
	public void setErabiltzaile(String erabiltzaile) {
		this.erabiltzaile = erabiltzaile;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	
	/**
	 * Denboraldi batean ligatik talde bat ateratzeko eta beste bat sartzeko bere lekuan
	 * 
	 * @param ateratzenDenTaldea	Ligatik atera nahi duzun taldea
	 * @param sartzenDenTaldea		Ligan sartu nahi duzun taldea
	 * @param ligakoTaldeak			Denboraldiko taldeen ArrayList-a
	 */
	public void aldatuTaldeak (Talde ateratzenDenTaldea, Talde sartzenDenTaldea, ArrayList<Talde> ligakoTaldeak, boolean denboraldiaHasiDa) {
		if (!denboraldiaHasiDa) {
			if (ligakoTaldeak.contains(ateratzenDenTaldea)) {
				if (!ligakoTaldeak.contains(sartzenDenTaldea)) {
					ligakoTaldeak.remove(ateratzenDenTaldea);
					ligakoTaldeak.add(sartzenDenTaldea);
				} else {
					JOptionPane.showMessageDialog(null, "Ligan sartu nahi duzun taldea denboraldi honetan ligan dago", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ligatik atera nahi duzun taldea ez dago denboraldi honetan ligan", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Ezin dituzu jokalariak aldatu denboraldia hasi delako", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
