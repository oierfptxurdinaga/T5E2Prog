package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Federazioa implements Serializable {
    private static final long serialVersionUID = 1L;

    // 1. TALDE GUZTIAK (Masterra: Hemen 12ak egongo dira)
    private ArrayList<Talde> taldeGuztiak; 

    // 2. DENBORALDIAK (Historiala)
    private ArrayList<Denboraldia> denboraldiak;
    private ArrayList <Erabiltzaile> erabiltzaileak;
    
    public Federazioa() {
        this.taldeGuztiak = new ArrayList<>();
        this.denboraldiak = new ArrayList<>();
        this.erabiltzaileak = new ArrayList<>();
    }

    
    public ArrayList<Erabiltzaile> getErabiltzaileak() {
        if (this.erabiltzaileak == null) {
            this.erabiltzaileak = new ArrayList<>();
        }
        return this.erabiltzaileak;
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
    public void setDenboraldiak(ArrayList<Denboraldia> denboraldiak) {
        this.denboraldiak = denboraldiak;
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