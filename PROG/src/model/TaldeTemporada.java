package model;

public class TaldeTemporada {

    private Talde talde;      // Equipo original
    private boolean aktiboa;   // Participa en la temporada actual
    private int PJ;            // Partidos jugados
    private int G;             // Victorias
    private int E;             // Empates
    private int P;             // Derrotas
    private int GF;            // Goles a favor
    private int GC;            // Goles en contra
    private int DG;            // Diferencia de goles
    private int Pts;           // Puntos

    public TaldeTemporada(Talde talde, boolean aktiboa) {
        this.talde = talde;
        this.aktiboa = aktiboa;
        this.PJ = 0;
        this.G = 0;
        this.E = 0;
        this.P = 0;
        this.GF = 0;
        this.GC = 0;
        this.DG = 0;
        this.Pts = 0;
    }

    public void actualizarResultados(int golesAFavor, int golesEnContra) {
        this.PJ++;
        this.GF += golesAFavor;
        this.GC += golesEnContra;
        this.DG = GF - GC;

        if (golesAFavor > golesEnContra) {
            this.G++;
            this.Pts += 3;
        } else if (golesAFavor == golesEnContra) {
            this.E++;
            this.Pts += 1;
        } else {
            this.P++;
        }
    }

    // Getters y setters
    public Talde getTalde() { return talde; }
    public boolean isAktiboa() { return aktiboa; }
    public void setAktiboa(boolean aktiboa) { this.aktiboa = aktiboa; }

    public int getPJ() { return PJ; }
    public int getG() { return G; }
    public int getE() { return E; }
    public int getP() { return P; }
    public int getGF() { return GF; }
    public int getGC() { return GC; }
    public int getDG() { return DG; }
    public int getPts() { return Pts; }
}