package model;

public class ErabiltzaileEpaile extends Erabiltzaile{
	
	private String pasahitza;

	public ErabiltzaileEpaile(String erabiltzaile, String pasahitza) {
		super(erabiltzaile);
		this.pasahitza = pasahitza;
	}

	//getters and setters
	public String getPasahitza() {
		return pasahitza;
	}

	public void setPasahitza(String pasahitza) {
		this.pasahitza = pasahitza;
	}
	
	public void sartuEmaitza(Denboraldia denboraldia, Talde etxekoTaldea, Talde kanpokoTaldea, int etxekoGolak, int kanpokoGolak) {
		for(int i=0;i<denboraldia.getLigakoJardunaldi().size();i++){
			for(int j=0;j<denboraldia.getLigakoJardunaldi().get(i).getPartiduak().size();j++){
				if(denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).getEtxekoTaldea().equals(etxekoTaldea)&&
					denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).getKanpokoTaldea().equals(kanpokoTaldea)) {
					denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).setEtxekoGolak(etxekoGolak);
					denboraldia.getLigakoJardunaldi().get(i).getPartiduak().get(j).setKanpokoGolak(kanpokoGolak);
					break;
				}
			}
		}
	}
}
