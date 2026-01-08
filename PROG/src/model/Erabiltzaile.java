package model;

public abstract class Erabiltzaile {
    
    protected String erabiltzaile;
    protected String pasahitza;
    

	public Erabiltzaile(String erabiltzaile, String pasahitza) {
        this.erabiltzaile = erabiltzaile;
        this.pasahitza = pasahitza;
    }

    public String getErabiltzaile() {
        return erabiltzaile;
    }

    public String getPasahitza() {
        return pasahitza;
    }
    
    public void setErabiltzaile(String erabiltzaile) {
		this.erabiltzaile = erabiltzaile;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
}
