package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Denboraldia implements Serializable {

    private static final long serialVersionUID = 1L;
    private int urtea;
    private ArrayList<Talde> ligakoTaldeak;
    private ArrayList<Jardunaldi> ligakoJardunaldi;

    
    public Denboraldia(int urtea) {
        this.urtea = urtea;
        this.ligakoTaldeak = new ArrayList<>();
        this.ligakoJardunaldi = new ArrayList<>();
    }

    // --- GETTERS & SETTERS ---
    public int getUrtea() { return urtea; }
    public ArrayList<Talde> getLigakoTaldeak() { return ligakoTaldeak; }
    public void setLigakoTaldeak(ArrayList<Talde> ligakoTaldeak) { this.ligakoTaldeak = ligakoTaldeak; }
    public ArrayList<Jardunaldi> getLigakoJardunaldi() { return ligakoJardunaldi; }
    public void setLigakoJardunaldi(ArrayList<Jardunaldi> ligakoJardunaldi) { this.ligakoJardunaldi = ligakoJardunaldi; }
    
    public void addJardunaldia(Jardunaldi j) { this.ligakoJardunaldi.add(j); }
    public void gehituTaldea(Talde t) { this.ligakoTaldeak.add(t); }

    // -------------------------------------------------------------------------
    // EGOERA LOGIKA (ALDAKETA HEMEN)
    // -------------------------------------------------------------------------

    /**
     * Denboraldia HASITA dago gutxienez PARTIDU BAT (1) jokatuta badago.
     * Ez da itxaron behar jardunaldi osoa amaitu arte.
     */
    public boolean isHasiDa() {
        if (this.ligakoJardunaldi == null || this.ligakoJardunaldi.isEmpty()) {
            return false;
        }

        // Jardunaldi guztiak zeharkatu
        for (Jardunaldi j : this.ligakoJardunaldi) {
            if (j.getPartiduak() != null) {
                // Partidu guztiak zeharkatu
                for (Partidua p : j.getPartiduak()) {
                    // Jokatutako BAT BAKARRA aurkitzen badugu, TRUE itzultzen dugu berehala.
                    if (p.jokatutaDago()) {
                        return true;
                    }
                }
            }
        }
        // Egutegi osoa begiratu eta inork jokatu ez badu:
        return false;
    }

    /**
     * Denboraldia AMAITUTA dago partidu GUZTIAK (absolutuki denak) jokatu badira.
     */
    public boolean isAmaituta() {
        if (this.ligakoJardunaldi == null || this.ligakoJardunaldi.isEmpty()) {
            return false;
        }
        
        // Dena begiratu ea jokatu gabeko partidurik dagoen
        for (Jardunaldi j : this.ligakoJardunaldi) {
            if (j.getPartiduak() != null) {
                for (Partidua p : j.getPartiduak()) {
                    // Jokatu gabeko bat aurkitzen badugu, EZ da amaitu
                    if (!p.jokatutaDago()) {
                        return false;
                    }
                }
            }
        }
        // Hona iristen bada, denak jokatuta daude
        return true;
    }
    
    // PanelAdmin-ekin bateragarritasuna mantentzeko
    public boolean isDenboraldiaHasiDa() {
        return isHasiDa();
    }
    
    // Setter hutsa (kode zaharrak errorerik ez emateko)
    public void setDenboraldiaHasiDa(boolean b) {
        // Ez du ezer egiten, orain automatikoa da.
    }

    @Override
    public String toString() {
        return String.valueOf(urtea);
    }
}