package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Sailkapena implements Serializable{

	/** Objektuaren bertsioa serializazioan kontrolatzeko identifikatzailea */
	private static final long serialVersionUID = 1L;
	private ArrayList<Talde> taldeak;
    private ArrayList<Integer> puntuak;

	public Sailkapena(ArrayList<Talde> taldeak, ArrayList<Integer> puntuak) {
		super();
		this.taldeak = taldeak;
		this.puntuak = puntuak;
	}
	public Sailkapena() {
	    this.taldeak = new ArrayList<>();
	    this.puntuak = new ArrayList<>();
	}
	
    public ArrayList<Talde> getTaldeak() {
		return taldeak;
	}

	public void setTaldeak(ArrayList<Talde> taldeak) {
		this.taldeak = taldeak;
	}

	public ArrayList<Integer> getPuntuak() {
		return puntuak;
	}

	public void setPuntuak(ArrayList<Integer> puntuak) {
		this.puntuak = puntuak;
	}

	public void gehituTaldea(Talde t) {
        if (this.taldeak == null) this.taldeak = new ArrayList<>();
        this.taldeak.add(t);

        if (this.puntuak == null) this.puntuak = new ArrayList<>();
        this.puntuak.add(0); 
    }
    
    public void SailkapenaOrdenatu(Sailkapena s) {
        ArrayList<Talde> t = s.getTaldeak();
        ArrayList<Integer> p = s.getPuntuak();

        // Método de la Burbuja (Bubble Sort)
        for (int i = 0; i < p.size() - 1; i++) {
            for (int j = 0; j < p.size() - i - 1; j++) {
                
                // Si el de abajo tiene más puntos que el de arriba...
                if (p.get(j) < p.get(j + 1)) {
                    
                    // 1. Intercambiamos los PUNTOS
                    int tempPuntos = p.get(j);
                    p.set(j, p.get(j + 1));
                    p.set(j + 1, tempPuntos);

                    // 2. ¡OJO! OBLIGATORIO intercambiar también los EQUIPOS
                    Talde tempTalde = t.get(j);
                    t.set(j, t.get(j + 1));
                    t.set(j + 1, tempTalde);
                }
            }
        }
    }

}