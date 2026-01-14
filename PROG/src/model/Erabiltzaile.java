package model;

import java.io.Serializable;

public abstract class Erabiltzaile implements Serializable {

	private static final long serialVersionUID = 1L;
	protected String erabiltzaile;
    protected String pasahitza;

    public Erabiltzaile(String erabiltzaile, String pasahitza) {
        this.erabiltzaile = erabiltzaile;
        this.pasahitza = pasahitza;
    }

    public String getErabiltzaile() { return this.erabiltzaile; }
    public String getPasahitza() { return this.pasahitza; }
}