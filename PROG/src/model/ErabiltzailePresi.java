package model;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ErabiltzailePresi extends Erabiltzaile {

	private static final long serialVersionUID = 1L;

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
}
