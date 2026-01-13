package model;

public class ErabiltzaileEpaile extends Erabiltzaile {

	public ErabiltzaileEpaile(String erabiltzaile, String pasahitza) {
		super(erabiltzaile, pasahitza);
	}

	// getters and setters
	public String getErabiltzaile() {
		return erabiltzaile;
	}
	public void setErabiltzaile(String erabiltzaile) {
		this.erabiltzaile = erabiltzaile;
	}
	public String getPasahitza() {
		return pasahitza;
	}
	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}

	/**
	 * Klase honekin nahi duzun partidoen emaitzak sartzen dira
	 * 
	 * @param denboraldia   zein da denboraldikoa da sartuko duzun partidoa
	 * @param etxekoTaldea  partidoan parte artsen duen etxeko taldea
	 * @param kanpokoTaldea partidoan parte artsen duen kanpoko taldea partida
	 *                      biltzeko
	 * @param etxekoGolak   zenbat gol sartu dituen partidoan etxeko taldeak emaitza
	 *                      sartzeko
	 * @param kanpokoGolak  zenbat gol sartu dituen partidoan kanpoko taldeak
	 *                      emaitza sartzeko
	 */
	public void sartuEmaitza(Denboraldia denboraldia, Talde etxekoTaldea, Talde kanpokoTaldea, int etxekoGolak,
			int kanpokoGolak) {
		for (int i = 0; i < denboraldia.getLigakoJardunaldi().size(); i++) {
			for (int j = 0; j < denboraldia.getLigakoJardunaldi().get(i).getPartiduak().size(); j++) {
				if (denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).getEtxekoTaldea()
						.equals(etxekoTaldea)
						&& denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).getKanpokoTaldea()
								.equals(kanpokoTaldea)) {
					denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).setEtxekoGolak(etxekoGolak);
					denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).setKanpokoGolak(kanpokoGolak);
					return;
				}
			}
		}
	}
}
