package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Talde implements Serializable{
	private static final long serialVersionUID = 1L;
	private String izena;
	private String ezkutua;
	private String futbolZelaia;
	private ArrayList<Jokalari> jokalariak;
	private String hiria;
	private boolean aktiboaDago;
	
	public Talde(String izena, String eskutua, String futbolZelaia, ArrayList<Jokalari> jokalariak, String hiria, boolean aktiboaDago) {
		this.izena = izena;
		this.ezkutua = eskutua;
		this.futbolZelaia = futbolZelaia;
		this.jokalariak = jokalariak;
		this.hiria = hiria;
		this.aktiboaDago = aktiboaDago;
	}
	
	public Talde(Talde taldea) {
		this.izena = taldea.izena;
		this.ezkutua = taldea.ezkutua;
		this.futbolZelaia = taldea.futbolZelaia;
		this.jokalariak = taldea.jokalariak;
		this.hiria = taldea.hiria;
		this.aktiboaDago = taldea.aktiboaDago;
	}
	public Talde() {
		
	}
	//getters and setters
	
	public String getIzena() {
		return izena;
	}

	public void setIzena(String izena) {
		this.izena = izena;
	}

	public String getEskutua() {
		return ezkutua;
	}

	public void setEskutua(String eskutua) {
		this.ezkutua = eskutua;
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

	public String getHiria() {
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
		if(this.ezkutua!=Eskutua) {
			this.ezkutua=Eskutua;
		}
	}
	public void sartuJokalaria (Jokalari j) {
		if (this.jokalariak == null) {
            this.jokalariak = new ArrayList<>();
        }
        this.jokalariak.add(j);
	}
	@Override
	public String toString() {
	    return this.izena; 
	}
	@Override
	public int hashCode() {
		return Objects.hash(izena);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Talde other = (Talde) obj;
		return Objects.equals(izena, other.izena);
	}

	public Talde kopiatu() {
	    // 1. Sortu zerrenda berri eta huts bat jokalarientzat
	    ArrayList<Jokalari> jokalariKopiak = new ArrayList<>();
	    
	    // 2. Jatorrizko taldeko jokalari bakoitza kopiatu eta zerrenda berrira gehitu
	    if (this.jokalariak != null) { // Segurtasuna (null check)
	        for (Jokalari j : this.jokalariak) {
	            jokalariKopiak.add(j.kopiatu()); // HEMEN DAGO GAKOA
	        }
	    }
	    
	    // 3. Talde berria itzuli, jokalari zerrenda BERRIAREKIN
	    return new Talde(
	        this.izena,
	        this.ezkutua,
	        this.futbolZelaia,
	        jokalariKopiak,
	        this.hiria,
	        this.aktiboaDago
	    );
	}
}
