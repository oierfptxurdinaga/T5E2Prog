package model;

import java.util.ArrayList;

public class Jardunaldi {
	
	private int jardunaldiZbk;
	private ArrayList<Partidua> partiduak;
	
	public Jardunaldi(int jardunaldiZbk, ArrayList<Partidua> partiduak) {
		this.jardunaldiZbk = jardunaldiZbk;
		this.partiduak = partiduak;
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

}
