package model;

import java.util.ArrayList;

public class Denboraldia {

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

    // Inicializar temporada con equipos activos
    public void iniciarTemporada(ArrayList<Talde> equiposActivos) {
        ArrayList<TaldeTemporada> listaSailkapena = new ArrayList<>();
        for (Talde t : equiposActivos) {
            listaSailkapena.add(new TaldeTemporada(t, true));
        }
        ligakoSailkapena.setSailkapenaTemporada(listaSailkapena);
        this.denboraldiaHasiDa = true;
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
}