package model;

import java.util.ArrayList;

public class Talde {
	
	private String izena;
	private String eskutua;
	private String futbolZelaia;
	private ArrayList<Jokalari> jokalariak;
	private int sorreraUrtea;
	private int ligakoPuntuak;
	private int aldekoGolak;
	private int kontrakoGolak;
	private boolean aktiboaDago;
	
	public Talde(String izena, String eskutua, String futbolZelaia, ArrayList<Jokalari> jokalariak, int sorreraUrtea,
			int ligakoPuntuak, int aldekoGolak, int kontrakoGolak, boolean aktiboaDago) {
		this.izena = izena;
		this.eskutua = eskutua;
		this.futbolZelaia = futbolZelaia;
		this.jokalariak = jokalariak;
		this.sorreraUrtea = sorreraUrtea;
		this.ligakoPuntuak = ligakoPuntuak;
		this.aldekoGolak = aldekoGolak;
		this.kontrakoGolak = kontrakoGolak;
		this.aktiboaDago = aktiboaDago;
	}
	
	//getters and setters
	public void set(Talde taldea) {
		this.izena = taldea.izena;
		this.eskutua = taldea.eskutua;
		this.futbolZelaia = taldea.futbolZelaia;
		this.jokalariak = taldea.jokalariak;
		this.sorreraUrtea = taldea.sorreraUrtea;
		this.ligakoPuntuak = taldea.ligakoPuntuak;
		this.aldekoGolak = taldea.aldekoGolak;
		this.kontrakoGolak = taldea.kontrakoGolak;
		this.aktiboaDago = taldea.aktiboaDago;
	}
	
	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getEskutua() {
		return eskutua;
	}

	public void setEskutua(String eskutua) {
		this.eskutua = eskutua;
	}

	public String getFutbolZelaia() {
		return futbolZelaia;
	}

	public void setFutbolZelaia(String futbolZelaia) {
		this.futbolZelaia = futbolZelaia;
	}

	public ArrayList<Jokalari> getJokalariak() {
		return jokalariak;
	}

	public void setJokalariak(ArrayList<Jokalari> jokalariak) {
		this.jokalariak = jokalariak;
	}

	public int getSorreraUrtea() {
		return sorreraUrtea;
	}

	public void setSorreraUrtea(int sorreraUrtea) {
		this.sorreraUrtea = sorreraUrtea;
	}

	public int getLigakoPuntuak() {
		return ligakoPuntuak;
	}

	public void setLigakoPuntuak(int ligakoPuntuak) {
		this.ligakoPuntuak = ligakoPuntuak;
	}

	public int getAldekoGolak() {
		return aldekoGolak;
	}

	public void setAldekoGolak(int aldekoGolak) {
		this.aldekoGolak = aldekoGolak;
	}

	public int getKontrakoGolak() {
		return kontrakoGolak;
	}

	public void setKontrakoGolak(int kontrakoGolak) {
		this.kontrakoGolak = kontrakoGolak;
	}

	public boolean isAktiboaDago() {
		return aktiboaDago;
	}

	public void setAktiboaDago(boolean aktiboaDago) {
		this.aktiboaDago = aktiboaDago;
	}
	
	public void aldatuEskutua(String Eskutua) {
		if(this.eskutua!=Eskutua) {
			this.eskutua=Eskutua;
		}
	}
}
