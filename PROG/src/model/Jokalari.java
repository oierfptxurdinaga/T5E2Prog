package model;

import java.io.Serializable;

public class Jokalari implements Serializable{

	private static final long serialVersionUID = 1L;
	private String izena;
	private String abizena;
	private int jaiotzeUrtea;
	private int dortsala;
	private String posizio;
	private boolean aktiboaDago;
	
	public Jokalari(String izena, String abizena, int jaiotseUrtea, int dortsala, String posizio, boolean aktiboaDago) {
		this.izena = izena;
		this.abizena = abizena;
		this.jaiotzeUrtea = jaiotseUrtea;
		this.dortsala = dortsala;
		this.posizio = posizio;
		this.aktiboaDago = aktiboaDago;
	}
	
	public Jokalari() {
		super();
	}

	//getters and setters
	public String getIzena() {
		return izena;
	}
	public void setIzena(String izena) {
		this.izena = izena;
	}
	public String getAbizena() {
		return abizena;
	}
	public void setAbizena(String abizena) {
		this.abizena = abizena;
	}
	public int getJaiotzeUrtea() {
		return jaiotzeUrtea;
	}
	public void setJaiotzeUrtea(int adina) {
		this.jaiotzeUrtea = adina;
	}
	public int getDortsala() {
		return dortsala;
	}
	public void setDortsala(int dortsala) {
		this.dortsala = dortsala;
	}
	public String getPosizio() {
		return posizio;
	}
	public void setPosizio(String posizio) {
		this.posizio = posizio;
	}
	public boolean isAktiboaDago() {
		return aktiboaDago;
	}
	public void setAktiboaDago(boolean aktiboaDago) {
		this.aktiboaDago = aktiboaDago;
	}
	public Jokalari kopiatu() {
	    return new Jokalari(
	        this.izena, 
	        this.abizena, 
	        this.jaiotzeUrtea, 
	        this.dortsala, 
	        this.posizio, 
	        this.aktiboaDago
	    );
	}
	public String getIrudiaUrl(int urtea) {
	     String seed = this.izena.replaceAll(" ", "") + urtea;
	     return "https://api.dicebear.com/7.x/avataaars/png?seed=" + seed;
	}
}
