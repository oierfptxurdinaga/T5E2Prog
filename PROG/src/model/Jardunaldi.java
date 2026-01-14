package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Jardunaldi implements Serializable{

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

	public void addPartidua(Partidua p) {
        if (this.partiduak == null) {
            this.partiduak = new ArrayList<>();
        }

        this.partiduak.add(p);
    }
}
