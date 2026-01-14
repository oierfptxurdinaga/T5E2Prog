package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Denboraldia implements Serializable{

    private int urtea;
    private ArrayList<Talde> ligakoTaldeak;           // Todos los equipos posibles
    private ArrayList<Jardunaldi> ligakoJardunaldi;   // Jornadas
    private Sailkapena ligakoSailkapena;             // Clasificación
    private boolean denboraldiaHasiDa;

    public Denboraldia(int urtea, ArrayList<Talde> ligakoTaldeak) {
        this.urtea = urtea;
        this.ligakoTaldeak = ligakoTaldeak;
        this.ligakoJardunaldi = new ArrayList<>();
        this.ligakoSailkapena = new Sailkapena();
        this.denboraldiaHasiDa = false;
    }

    public Denboraldia(int urtea) {
    	this.urtea = urtea;
    	this.ligakoTaldeak = new ArrayList<Talde>();
    	this.ligakoSailkapena = new Sailkapena();
        this.ligakoJardunaldi = new ArrayList<>(); 
        this.denboraldiaHasiDa = false;
    }

    // Getters y setters
    public int getUrtea() { return urtea; }
    public void setUrtea(int urtea) { this.urtea = urtea; }

    public ArrayList<Talde> getLigakoTaldeak() { return ligakoTaldeak; }
    public void setLigakoTaldeak(ArrayList<Talde> ligakoTaldeak) { this.ligakoTaldeak = ligakoTaldeak; }

    public ArrayList<Jardunaldi> getLigakoJardunaldi() { return ligakoJardunaldi; }
    public void setLigakoJardunaldi(ArrayList<Jardunaldi> ligakoJardunaldi) { this.ligakoJardunaldi = ligakoJardunaldi; }

    public Sailkapena getLigakoSailkapena() { return ligakoSailkapena; }
    public void setLigakoSailkapena(Sailkapena ligakoSailkapena) { this.ligakoSailkapena = ligakoSailkapena; }

    public boolean isDenboraldiaHasiDa() { return denboraldiaHasiDa; }
    public void setDenboraldiaHasiDa(boolean denboraldiaHasiDa) { this.denboraldiaHasiDa = denboraldiaHasiDa; }

    @Override
    public String toString() {
        return String.valueOf(urtea);
    }
    
    public void gehituTaldea(Talde taldea) {
        if (taldea != null) {
            if (!this.ligakoTaldeak.contains(taldea)) {
                this.ligakoTaldeak.add(taldea);

                if (this.ligakoSailkapena != null) {
                    this.ligakoSailkapena.gehituTaldea(taldea);
                }
            }
        }
    }
    public void addJardunaldia(Jardunaldi j) {
        if (this.ligakoJardunaldi == null) {
            this.ligakoJardunaldi = new ArrayList<>();
        }
        

        this.ligakoJardunaldi.add(j);
    }
}