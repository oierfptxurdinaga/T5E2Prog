package model;

import java.util.ArrayList;

public class Sailkapena {
	private ArrayList<Talde> sailkapena;

	public Sailkapena(ArrayList<Talde> sailkapena) {
		super();
		this.sailkapena = sailkapena;
	}

	// getters and setters
	public ArrayList<Talde> getSailkapena() {
		return sailkapena;
	}

	public void setSailkapena(ArrayList<Talde> sailkapena) {
		this.sailkapena = sailkapena;
	}

	public void eguneratuSailkapena() {
	Talde temp=null;	
	
		for(int i=0;i<sailkapena.size();i++){
			for(int j=1; j<(sailkapena.size()-i);j++) {
				if(sailkapena.get(j-1).getLigakoPuntuak()>sailkapena.get(j).getLigakoPuntuak()) {
					temp.set(sailkapena.get(j-1));;
					sailkapena.get(j-1).set(sailkapena.get(j));
					sailkapena.get(j).set(temp);
				}
			}
		}
	}
}
