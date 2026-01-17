package model;

public class DenboraldiTalde {

    private Talde talde;      // Talde originala
    private boolean aktiboa;   // Denboraldi honetan partizipatzen du
    private int JP;            // Jokatutako partidak
    private int I;             // Irabazitak
    private int B;             // Berdinketak
    private int G;             // Galdutak
    private int GA;            // Golak alde
    private int GK;            // Golak kontra
    private int GD;            // Gol diferentzia
    private int PT;           // Puntuak

    public DenboraldiTalde(Talde talde, boolean aktiboa) {
        this.talde = talde;
        this.aktiboa = aktiboa;
        this.JP = 0;
        this.I = 0;
        this.B = 0;
        this.G = 0;
        this.GA = 0;
        this.GK = 0;
        this.GD = 0;
        this.PT = 0;
    }

    public void emaitzakEguneratu(int golAlde, int golAurka) {
        this.JP++;
        this.GA += golAlde;
        this.GK += golAurka;
        this.GD = GA - GK;

        if (golAlde > golAurka) {
            this.I++;
            this.PT += 3;
        } else if (golAlde == golAurka) {
            this.B++;
            this.PT += 1;
        } else {
            this.G++;
        }
    }

    // Getters y setters
    public Talde getTalde() { return talde; }
    public boolean isAktiboa() { return aktiboa; }
    public void setAktiboa(boolean aktiboa) { this.aktiboa = aktiboa; }

    public int getPJ() { return JP; }
    public int getG() { return I; }
    public int getE() { return B; }
    public int getP() { return G; }
    public int getGF() { return GA; }
    public int getGC() { return GK; }
    public int getDG() { return GD; }
    public int getPts() { return PT; }
}