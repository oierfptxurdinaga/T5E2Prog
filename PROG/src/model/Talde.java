package model;

import java.util.ArrayList;

public class Talde {
	
	private String izena;
	private String eskutua;
	private String futbolZelaia;
	private ArrayList<Jokalari> jokalariak;
	private String hiria;
	private boolean aktiboaDago;
	
	public Talde(String izena, String eskutua, String futbolZelaia, ArrayList<Jokalari> jokalariak, String hiria, boolean aktiboaDago) {
		this.izena = izena;
		this.eskutua = eskutua;
		this.futbolZelaia = futbolZelaia;
		this.jokalariak = jokalariak;
		this.hiria = hiria;
		this.aktiboaDago = aktiboaDago;
	}
	
	//getters and setters
	public void set(Talde taldea) {
		this.izena = taldea.izena;
		this.eskutua = taldea.eskutua;
		this.futbolZelaia = taldea.futbolZelaia;
		this.jokalariak = taldea.jokalariak;
		this.hiria = taldea.hiria;
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

	public String getSHiria() {
		return hiria;
	}

	public void setHiria(String hiria) {
		this.hiria = hiria;
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
