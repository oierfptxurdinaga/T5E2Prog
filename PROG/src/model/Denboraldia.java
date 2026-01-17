package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap; // <--- Hau gehitu
import java.util.Map;     // <--- Hau gehitu

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

    /**
     * Denboraldia HASITA dago gutxienez PARTIDU BAT (1) jokatuta badago.
     * Ez da itxaron behar jardunaldi osoa amaitu arte.
     */
    public boolean isHasiDa() {
        if (this.ligakoJardunaldi == null || this.ligakoJardunaldi.isEmpty()) {
            return false;
        }
        for (Jardunaldi j : this.ligakoJardunaldi) {
            if (j.getPartiduak() != null) {
                for (Partidua p : j.getPartiduak()) {
                    if (p.jokatutaDago()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Denboraldia AMAITUTA dago partidu GUZTIAK (absolutuki denak) jokatu badira.
     */
    public boolean isAmaituta() {
        if (this.ligakoJardunaldi == null || this.ligakoJardunaldi.isEmpty()) {
            return false;
        }
        for (Jardunaldi j : this.ligakoJardunaldi) {
            if (j.getPartiduak() != null) {
                for (Partidua p : j.getPartiduak()) {
                    if (!p.jokatutaDago()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // PanelAdmin-ekin bateragarritasuna mantentzeko
    public boolean isDenboraldiaHasiDa() {
        return isHasiDa();
    }

    @Override
    public String toString() {
        return String.valueOf(urtea);
    }

    // --- METODO BERRIA: SAILKAPENA KALKULATU ---
    
    /**
     * Sailkapena momentuan kalkulatzen du partiduetako emaitzetan oinarrituta.
     * @return DenboraldiTalde zerrenda estatistikekin eguneratuta.
     */
    public ArrayList<DenboraldiTalde> getSailkapena() {
        Map<String, DenboraldiTalde> statsMap = new HashMap<>();

        // 1. Taldeak hasieratu (0 puntu)
        if (this.ligakoTaldeak != null) {
            for (Talde t : this.ligakoTaldeak) {
                statsMap.put(t.getIzena().trim(), new DenboraldiTalde(t, true));
            }
        }

        // 2. Partiduak prozesatu eta puntuak batu
        if (this.ligakoJardunaldi != null) {
            for (Jardunaldi j : this.ligakoJardunaldi) {
                if (j.getPartiduak() != null) {
                    for (Partidua p : j.getPartiduak()) {
                        // Jokatu gabe badago, hurrengoa
                        if (!p.jokatutaDago()) continue;

                        String localNom = p.getEtxekoTaldea().getIzena().trim();
                        String visitNom = p.getKanpokoTaldea().getIzena().trim();

                        DenboraldiTalde sLocal = statsMap.get(localNom);
                        DenboraldiTalde sVisit = statsMap.get(visitNom);

                        if (sLocal != null && sVisit != null) {
                            // DenboraldiTalde klaseko metodoa erabili datuak eguneratzeko
                            sLocal.emaitzakEguneratu(p.getEtxekoGolak(), p.getKanpokoGolak());
                            sVisit.emaitzakEguneratu(p.getKanpokoGolak(), p.getEtxekoGolak());
                        }
                    }
                }
            }
        }
        // Zerrenda itzuli
        return new ArrayList<>(statsMap.values());
    }
}