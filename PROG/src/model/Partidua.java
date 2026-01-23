package model;

import java.io.Serializable;

/**
 * Liga edo denboraldi bateko partida bat irudikatzen duen klasea.
 * 
 * Partida batek etxeko taldea eta kanpoko taldea ditu,
 * eta bien artean lortutako gol kopuruak gordetzen ditu.
 * 
 * Gol kopuruaren balioen arabera, partida jokatu den ala ez
 * zehaztu daiteke.
 */
public class Partidua implements Serializable {

	/** Objektuaren bertsioa serializazioan kontrolatzeko identifikatzailea */
	private static final long serialVersionUID = 1L;
	private Talde etxekoTaldea;
	private Talde kanpokoTaldea;
	private int etxekoGolak;
	private int kanpokoGolak;

	/**
     * Golik gabeko partida berri bat sortzen du.
     * 
     * Hasieran golak {@code -1} balioarekin ezartzen dira,
     * partida oraindik jokatu ez dela adierazteko.
     * 
     * @param etxekoTaldea etxean jokatzen duen taldea
     * @param kanpokoTaldea kanpoan jokatzen duen taldea
     */
	public Partidua(Talde etxekoTaldea, Talde kanpokoTaldea) {
		this.etxekoTaldea = etxekoTaldea;
		this.kanpokoTaldea = kanpokoTaldea;
		this.etxekoGolak = -1;
		this.kanpokoGolak = -1;
	}

	/**
     * Emaitzarekin hasieratutako partida bat sortzen du.
     *
     * @param etxekoTaldea etxeko taldea
     * @param kanpokoTaldea kanpoko taldea
     * @param etxekoGolak etxeko taldeak sartutako gol kopurua
     * @param kanpokoGolak kanpoko taldeak sartutako gol kopurua
     */
	public Partidua(Talde etxekoTaldea, Talde kanpokoTaldea, int etxekoGolak, int kanpokoGolak) {
		this.etxekoTaldea = etxekoTaldea;
		this.kanpokoTaldea = kanpokoTaldea;
		this.etxekoGolak = etxekoGolak;
		this.kanpokoGolak = kanpokoGolak;
	}

	/**
     * Partida jokatu den ala ez adierazten du.
     * 
     * Bi taldeetako golak balioz ezarrita badaude,
     * partida jokatu dela ulertzen da.
     * 
     * @return {@code true} partida jokatu bada; bestela {@code false}
     */
	public boolean jokatutaDago() {
		return this.etxekoGolak != -1 && this.kanpokoGolak != -1;
	}

	// Getterrak eta setterrak
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
}
