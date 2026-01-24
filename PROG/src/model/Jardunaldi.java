package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Liga edo denboraldi bateko jardunaldi bat irudikatzen duen klasea.
 * 
 * Jardunaldiak zenbaki baten bidez identifikatzen dira eta barnean
 * partida guztiak gordetzen ditu.
 * 
 * Partidak gehitzeko eta kudeatzeko metodoak eskaintzen ditu.
 */
public class Jardunaldi implements Serializable{

	/** Objektuaren bertsioa serializazioan kontrolatzeko identifikatzailea */
	private static final long serialVersionUID = 1L;
	private int jardunaldiZbk;
	private ArrayList<Partidua> partiduak;
	
	public Jardunaldi(int jardunaldiZbk, ArrayList<Partidua> partiduak) {
		this.jardunaldiZbk = jardunaldiZbk;
		this.partiduak = partiduak;
	}
	public Jardunaldi(int jardunaldiZbk) {
        this.jardunaldiZbk = jardunaldiZbk;
        this.partiduak = new ArrayList<>(); 
    }
	
	//getters and setters
	public int getJardunaldiZbk() {
		return jardunaldiZbk;
	}
	public void setJardunaldiZbk(int jardunaldiZbk) {
		this.jardunaldiZbk = jardunaldiZbk;
	}
	public ArrayList<Partidua> getPartiduak() {
		return partiduak;
	}
	public void setPartiduak(ArrayList<Partidua> partiduak) {
		this.partiduak = partiduak;
	}

	/**
     * Partida berri bat jardunaldian gehitzen du.
     * 
     * Barneko lista automatikoki sortzen da {@code null} bada.
     *
     * @param p gehitu nahi den partida
     */
	public void addPartidua(Partidua p) {
        if (this.partiduak == null) {
            this.partiduak = new ArrayList<>();
        }

        this.partiduak.add(p);
    }
}
