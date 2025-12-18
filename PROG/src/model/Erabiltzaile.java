package model;

public abstract class Erabiltzaile {
	
	protected String erabiltzaile;
	
	public Erabiltzaile(String erabiltzaile) {
		this.erabiltzaile = erabiltzaile;
	}

	//getters and setters
	public String getErabiltzaile() {
		return erabiltzaile;
	}

	public void setErabiltzaile(String erabiltzaile) {
		this.erabiltzaile = erabiltzaile;
	}
}
