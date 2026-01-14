package model;

import java.io.Serializable;

public class Partidua implements Serializable{

	private Talde etxekoTaldea;
	private Talde kanpokoTaldea;
	private int etxekoGolak;
	private int kanpokoGolak;
	
	
	public Partidua(Talde etxekoTaldea, Talde kanpokoTaldea, int etxekoGolak, int kanpokoGolak) {
		this.etxekoTaldea = etxekoTaldea;
		this.kanpokoTaldea = kanpokoTaldea;
		this.etxekoGolak=-1;
		this.kanpokoGolak=-1;
	}

	//getters and setters
	public Talde getEtxekoTaldea() {
		return etxekoTaldea;
	}

	public void setEtxekoTaldea(Talde etxekoTaldea) {
		this.etxekoTaldea = etxekoTaldea;
	}

	public Talde getKanpokoTaldea() {
		return kanpokoTaldea;
	}

	public void setKanpokoTaldea(Talde kanpokoTaldea) {
		this.kanpokoTaldea = kanpokoTaldea;
	}

	public int getEtxekoGolak() {
		return etxekoGolak;
	}

	public void setEtxekoGolak(int etxekoGolak) {
		this.etxekoGolak = etxekoGolak;
	}

	public int getKanpokoGolak() {
		return kanpokoGolak;
	}

	public void setKanpokoGolak(int kanpokoGolak) {
		this.kanpokoGolak = kanpokoGolak;
	}
	
	/**
	 * Aukeratutako partidotik nor irabasten duen estendu
	 * @return Talde modura itsultzen du irabaslea
	 */
	public Talde erakutziIrabazlea() {
		Talde irabazle=null;
		if(this.etxekoGolak<this.kanpokoGolak) {
			irabazle=this.kanpokoTaldea;
		}else if(this.etxekoGolak>this.kanpokoGolak) {
			irabazle=this.etxekoTaldea;
		}
		return irabazle;
	}
}
