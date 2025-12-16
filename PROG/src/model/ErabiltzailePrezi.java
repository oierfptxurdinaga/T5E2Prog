package model;

import javax.swing.JOptionPane;

public class ErabiltzailePrezi extends Erabiltzaile{
	
	private String pasahitza;
	
	public ErabiltzailePrezi(String pasahitza, String usuario) {
		super();
		this.pasahitza = pasahitza;
	}

	//getters and setters
	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	
	public void aldatuJokalariak(Jokalari jokalari, Talde taldeZaharra, Talde taldeBerria) {
		if(taldeZaharra.getJokalariak().contains(jokalari)) {
			taldeZaharra.getJokalariak().remove(jokalari);
			taldeBerria.getJokalariak().add(jokalari);
		}else {
			JOptionPane.showMessageDialog(null, "Jokalari hori ez dago talde horretan", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}
