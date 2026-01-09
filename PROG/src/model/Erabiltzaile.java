package model;

public abstract class Erabiltzaile {
    protected String erabiltzaile;
    protected String pasahitza;

    public Erabiltzaile(String erabiltzaile, String pasahitza) {
        this.erabiltzaile = erabiltzaile;
        this.pasahitza = pasahitza;
    }

    public String getErabiltzaile() { return this.erabiltzaile; }
    public String getPasahitza() { return this.pasahitza; }
}