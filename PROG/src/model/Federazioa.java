package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Federazioa implements Serializable {
    private static final long serialVersionUID = 1L;

    // 1. TALDE GUZTIAK (Masterra: Hemen 10ak egongo dira)
    private ArrayList<Talde> taldeGuztiak; 

    // 2. DENBORALDIAK (Historiala)
    private ArrayList<Denboraldia> denboraldiak;
    
    public Federazioa() {
        this.taldeGuztiak = new ArrayList<>();
        this.denboraldiak = new ArrayList<>();
    }

    // --- KUDEAKETA METODOAK ---
    
    public void gehituTaldea(Talde t) {
        if (!taldeGuztiak.contains(t)) {
            taldeGuztiak.add(t);
        }
    }

    public void gehituDenboraldia(Denboraldia d) {
        this.denboraldiak.add(d);
    }

    // --- GETTERS ETA SETTERS ---

    public ArrayList<Talde> getTaldeGuztiak() {
        return taldeGuztiak;
    }

    public ArrayList<Denboraldia> getDenboraldiak() {
        return denboraldiak;
    }
    
    /**
     * Azken denboraldia lortzeko metodo laguntzailea
     */
    public Denboraldia getUnekoDenboraldia() {
        if (denboraldiak != null && !denboraldiak.isEmpty()) {
            return denboraldiak.get(denboraldiak.size() - 1);
        }
        return null;
    }
}