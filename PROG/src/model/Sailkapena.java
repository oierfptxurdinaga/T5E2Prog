package model;

import java.util.ArrayList;

public class Sailkapena {

    private ArrayList<TaldeTemporada> sailkapena; // Ahora con TaldeTemporada

    public Sailkapena() {
        this.sailkapena = new ArrayList<>();
    }

    public Sailkapena(ArrayList<TaldeTemporada> sailkapena) {
        this.sailkapena = sailkapena;
    }

    public ArrayList<TaldeTemporada> getSailkapenaTemporada() {
        return sailkapena;
    }

    public void setSailkapenaTemporada(ArrayList<TaldeTemporada> sailkapena) {
        this.sailkapena = sailkapena;
    }

    /**
     * Ordena la clasificación según puntos, diferencia de goles, goles a favor y nombre alfabético
     */
    public void eguneratuSailkapena() {
        sailkapena.sort((t1, t2) -> {
            int cmp = Integer.compare(t2.getPts(), t1.getPts());
            if (cmp != 0) return cmp;

            cmp = Integer.compare(t2.getDG(), t1.getDG());
            if (cmp != 0) return cmp;

            cmp = Integer.compare(t2.getGF(), t1.getGF());
            if (cmp != 0) return cmp;

            // Orden alfabético como último criterio
            return t1.getTalde().getIzena().compareToIgnoreCase(t2.getTalde().getIzena());
        });
    }
}