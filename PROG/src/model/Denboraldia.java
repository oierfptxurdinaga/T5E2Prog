package model;

import java.util.ArrayList;

public class Denboraldia {

	private int urtea;
	private ArrayList<Talde> ligakoTaldeak;
	private ArrayList<Jardunaldi> ligakoJardunaldi;
	private Sailkapena ligakoSailkapena;
	private boolean denboraldiaHasiDa;
	
	public Denboraldia(int urtea, ArrayList<Talde> ligakoTaldeak, ArrayList<Jardunaldi> ligakoJardunaldi,
			Sailkapena ligakoSailkapena, boolean denboraldiaHasiDa) {
		this.urtea = urtea;
		this.ligakoTaldeak = ligakoTaldeak;
		this.ligakoJardunaldi = ligakoJardunaldi;
		this.ligakoSailkapena = ligakoSailkapena;
		this.denboraldiaHasiDa = denboraldiaHasiDa;
	}

	//getters and setters
	public int getUrtea() {
		return urtea;
	}

	public void setUrtea(int urtea) {
		this.urtea = urtea;
	}

	public ArrayList<Talde> getLigakoTaldeak() {
		return ligakoTaldeak;
	}

	public void setLigakoTaldeak(ArrayList<Talde> ligakoTaldeak) {
		this.ligakoTaldeak = ligakoTaldeak;
	}

	public ArrayList<Jardunaldi> getLigakoJardunaldi() {
		return ligakoJardunaldi;
	}

	public void setLigakoJardunaldi(ArrayList<Jardunaldi> ligakoJardunaldi) {
		this.ligakoJardunaldi = ligakoJardunaldi;
	}

	public Sailkapena getLigakoSailkapena() {
		return ligakoSailkapena;
	}

	public void setLigakoSailkapena(Sailkapena ligakoSailkapena) {
		this.ligakoSailkapena = ligakoSailkapena;
	}

	public boolean isDenboraldiaHasiDa() {
		return denboraldiaHasiDa;
	}

	public void setDenboraldiaHasiDa(boolean denboraldiaHasiDa) {
		this.denboraldiaHasiDa = denboraldiaHasiDa;
	}

	@Override
	public String toString() {
	    return String.valueOf(urtea); // Urtea testu gisa itzultzeko
	}
}
