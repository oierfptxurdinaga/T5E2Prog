package utils;

import java.io.*;
import java.util.*;
import model.*;

public class DatuKargatzailea {

    public static void main(String[] args) {
        System.out.println("Sistemaren datuak hasieratzen (6 Taldeko Liga)...");

        // 1. DENBORALDIA SORTU
        Denboraldia denboraldia = new Denboraldia(2024);

        // 2. TALDEAK SORTU (Kargatu map-a)
        Map<Integer, Talde> taldeakMap = sortuTaldeak();

        // 3. JOKALARIAK KARGATU
        kargatuJokalariak(taldeakMap);

        // 4. LIGAKO INSKRIPZIOA
        // Lehenengo 6 taldeak bakarrik inskribatu
        for (int i = 1; i <= 6; i++) {
            denboraldia.gehituTaldea(taldeakMap.get(i));
        }

        // 5. JARDUNALDIAK ETA PARTIDUAK KARGATU
        kargatuPartiduak(denboraldia, taldeakMap);

        // 6. ERABILTZAILEAK SORTU
        List<Erabiltzaile> erabiltzaileak = new ArrayList<>();
        erabiltzaileak.add(new ErabiltzaileAdministraria("admin", "admin")); 
        erabiltzaileak.add(new ErabiltzaileEpaile("epaile", "epaile"));
        erabiltzaileak.add(new ErabiltzailePresi("presi", "presi"));

        // ---------------------------------------------------------
        // ALDAKETA GARRANTZITSUA HEMEN
        // ---------------------------------------------------------
        // Denboraldia zuzenean gorde beharrean, ArrayList batean sartu behar dugu,
        // DatuKarga.java klaseak ArrayList bat espero duelako.
        
        ArrayList<Denboraldia> denboraldiZerrenda = new ArrayList<>();
        denboraldiZerrenda.add(denboraldia);

        // 7. GORDE (.SER fitxategiak)
        // Orain 'denboraldiZerrenda' gordetzen dugu, ez 'denboraldia' solte
        gordeObjektua(denboraldiZerrenda, "src/data/ligaren_datuak.ser");
        gordeObjektua(erabiltzaileak, "src/data/erabiltzaileak.ser");
        
        System.out.println("Datuak ondo sortu eta gorde dira.");
    }

    // ----------------------------------------------------------------------
    // METODOAK (Berdin jarraitzen dute)
    // ----------------------------------------------------------------------

    private static Map<Integer, Talde> sortuTaldeak() {
        Map<Integer, Talde> map = new HashMap<>();
        Object[][] datuak = {
            {1, "Barça Futsal", "/resources/images/barcelona.png", "Palau Blaugrana", new ArrayList<Jokalari>(), "Barcelona", true},
            {2, "ElPozo Murcia", "/resources/images/elpozo.png", "Palacio de Deportes de Murcia", new ArrayList<Jokalari>(),"Murcia", true},
            {3, "Inter Movistar", "/resources/images/inter.png", "Jorge Garbajosa", new ArrayList<Jokalari>(),"Torrejon de Ardoz",true},
            {4, "Palma Futsal", "/resources/images/baleares.png", "Son Moix", new ArrayList<Jokalari>(), "Palma", true},
            {5, "Jaén Paraíso", "/resources/images/jaen.png", "Olivo Arena", new ArrayList<Jokalari>(), "Jaen", true},
            {6, "Xota FS", "/resources/images/xota.png", "Anaitasuna", new ArrayList<Jokalari>(), "Pamplona", true},
            {7, "Jimbee Cartagena", "/resources/images/cartagena.png", "Cartagena", new ArrayList<Jokalari>(), "Cartagena", false},
            {8, "Peñíscola", "/resources/images/peñiscola.png", "Juan Vizcarro", new ArrayList<Jokalari>(), "Peñiscola", false},
            {9, "Burela FS", "/resources/images/burela.png", "Vista Alegre", new ArrayList<Jokalari>(), "Burela", false},
            {10, "Córdoba Pat.", "/resources/images/cordoba.png", "Vista Alegre", new ArrayList<Jokalari>(), "Cordoba", false}
        };

        for (Object[] d : datuak) {
            Talde t = new Talde(
                (String)d[1], 
                (String)d[2], 
                (String)d[3], 
                (ArrayList<Jokalari>)d[4], 
                (String)d[5], 
                (boolean)d[6]
            );
            map.put((Integer)d[0], t);
        }
        return map;
    }

    private static void kargatuJokalariak(Map<Integer, Talde> taldeakMap) {
        // Zure jokalari zerrenda hemen...
        String[][] jokalariDatuak = {
        		// Barça (1)
                {"Miquel", "Feixas", "1997-09-04"}, {"Didac", "Plana", "1990-05-22"}, {"Antonio", "Pérez", "2000-10-10"}, {"André", "Coelho", "1993-10-30"},
                {"Sergio", "Lozano", "1988-11-09"}, {"Dyego", "Zuffo", "1989-08-05"}, {"Adolfo", "Fernández", "1993-05-19"}, {"Catela", "Juanjo", "1995-04-14"},
                {"Matheus", "Rodrigues", "1996-10-03"}, {"Erick", "Mendonça", "1995-07-21"}, {"Pito", "Guisel", "1991-11-06"}, {"Alex", "Yepes", "1989-03-12"},
                // ElPozo (2)
                {"Juanjo", "Angosto", "1985-08-19"}, {"Edu", "Sousa", "1996-08-15"}, {"Felipe", "Valerio", "1993-07-08"}, {"Marlon", "Oliveira", "1987-12-28"},
                {"Marcel", "Marques", "1996-07-26"}, {"Gadeia", "Fabricio", "1988-06-14"}, {"David", "Álvarez", "1998-02-14"}, {"Ricardo", "Mayor", "2000-02-21"},
                {"Esteban", "Guerrero", "1995-04-14"}, {"Rafa", "Santos", "1990-09-21"}, {"Bruno", "Taffy", "1990-03-30"}, {"Eric", "Pérez", "1997-02-10"},
                // Inter (3)
                {"Jesús", "Herrero", "1986-11-04"}, {"Jesús", "García", "1999-02-05"}, {"Raya", "José Javier", "1997-01-01"}, {"Humberto", "De Araujo", "1986-11-24"},
                {"Cecilio", "Morales", "1992-07-06"}, {"Rubi", "Lemos", "1987-11-13"}, {"Terry", "Prestjord", "1993-01-01"}, {"Kaito", "Eto", "1998-01-01"},
                {"Sepe", "García", "1990-11-04"}, {"Drahovsky", "Tomas", "1992-10-07"}, {"Raúl", "Gómez", "1995-10-25"}, {"Fits", "Rafael", "1992-05-23"},
                // Palma (4)
                {"Luan", "Muller", "1993-03-17"}, {"Carlos", "Barrón", "1987-10-01"}, {"Rómulo", "Alves", "1986-09-28"}, {"Chaguinha", "Bruno", "1988-07-25"},
                {"Moslem", "Oladghobad", "1995-11-29"}, {"Cleber", "Gomes", "1997-01-06"}, {"Rivillos", "Mario", "1989-12-13"}, {"Neguinho", "Joao", "2000-07-12"},
                {"Bruno", "Gomes", "1996-01-01"}, {"Gordillo", "Jesús", "2001-02-08"}, {"Tayebi", "Hossein", "1988-09-29"}, {"Fabinho", "Gomes", "2001-11-29"},
                // Jaén (5)
                {"Espindola", "Carlos", "1993-07-12"}, {"Dudu", "Eduardo", "1996-02-15"}, {"Taborda", "Pablo", "1986-09-02"}, {"Menzeguez", "Gerardo", "1993-04-23"},
                {"Alan", "Brandi", "1987-11-24"}, {"Mati", "Rosa", "1995-09-18"}, {"Michel", "Moya", "1997-02-02"}, {"Cesar", "Velasco", "1998-05-30"},
                {"Chino", "Javier", "1991-11-26"}, {"Renato", "Lopes", "1997-12-15"}, {"Helder", "Goncalves", "2000-03-25"}, {"Nem", "Everson", "1995-06-12"},
                // Xota (6)
                {"Asier", "Llamas", "1993-05-15"}, {"Oihan", "Sanchez", "2001-01-20"}, {"Tony", "Escribano", "1998-04-14"}, {"Juninho", "Roberto", "1995-06-12"},
                {"Linhares", "Fabinho", "1996-08-22"}, {"Roberto", "Martil", "1986-02-21"}, {"Geraghty", "Braulio", "1994-11-12"}, {"Dani", "Zurdo", "2000-08-08"},
                {"Pachu", "Alberto", "1992-04-12"}, {"Ion", "Cerviño", "2002-11-20"}, {"Iosu", "Mendell", "2003-01-30"}, {"Josu", "Mendive", "2001-07-15"},
                // Cartagena (7), Peñiscola (8), Burela (9), Córdoba (10)...
                {"Chemi", "Oliver", "1996-02-20"}, {"Chispi", "Molina", "1999-05-12"}, {"Bebe", "Rafael", "1990-06-12"}, {"Mellado", "Miguel", "1999-07-23"},
                {"Jesus", "Izquierdo", "1991-10-10"}, {"Tomaz", "Braga", "1990-09-12"}, {"Lucao", "Vinicius", "1996-03-12"}, {"Waltinho", "Walter", "1991-11-27"},
                {"Juanan", "Moraleja", "1998-06-25"}, {"Javi", "Minguez", "1996-07-17"}, {"Pablo", "Ramirez", "2001-02-25"}, {"Motta", "Felipe", "1999-12-11"},
                {"Gus", "Lopez", "1989-01-20"}, {"Mati", "Starna", "1999-02-15"}, {"Plaza", "David", "2000-03-12"}, {"Juanqui", "Fernandez", "1988-06-20"},
                {"Pani", "Francisco", "1997-04-12"}, {"Aicardo", "Jesus", "1988-12-04"}, {"Quintela", "Diego", "1991-08-06"}, {"Saladié", "Carles", "1995-10-12"},
                {"Elías", "Beltran", "2001-05-15"}, {"Luciano", "Gaudio", "1998-09-22"}, {"Sancho", "Victor", "2002-01-11"}, {"Jose", "Mario", "1999-08-08"},
                {"Michal", "Kaluza", "1998-05-20"}, {"Bruno", "García", "1999-01-01"}, {"Lucho", "González", "1995-01-01"}, {"Isma", "Vázquez", "1996-01-01"},
                {"David", "Pazos", "1993-01-01"}, {"Alex", "Diz", "1995-01-01"}, {"Antón", "Arnejo", "2001-01-01"}, {"Nito", "García", "2002-01-01"},
                {"Rikelme", "Da Silva", "2000-01-01"}, {"Malaguti", "Alberto", "1997-01-01"}, {"Pitero", "Luis", "1999-01-01"}, {"Charly", "López", "1998-01-01"},
                {"Fabio", "Alvira", "1990-01-01"}, {"Víctor", "Cano", "2000-01-01"}, {"Mykytiuk", "Mykola", "1996-01-01"}, {"Mareco", "Damián", "1994-01-01"},
                {"Zequi", "Méndez", "1992-01-01"}, {"Pulinho", "Da Silva", "1998-01-01"}, {"Perin", "Lucas", "1997-01-01"}, {"Antoniazzi", "Tiago", "1999-01-01"},
                {"Kenji", "Shimizu", "1997-01-01"}, {"Muhammad", "Osamanmusa", "1998-01-01"}, {"Kaué", "Da Silva", "2000-01-01"}, {"Guilherme", "Santos", "2001-01-01"}
        };

        String[] posizioPosibleak = {"Atezaina", "Itxiera", "Hegala", "Pibota"};
        java.util.Random random = new java.util.Random();
        int jokalariIndizea = 0;
        
        for (int taldeId = 1; taldeId <= 10; taldeId++) {
            Talde unekoTaldea = taldeakMap.get(taldeId);
            for (int k = 0; k < 12; k++) {
                if (jokalariIndizea < jokalariDatuak.length) {
                    String[] d = jokalariDatuak[jokalariIndizea];
                    int jaiotzeUrtea = Integer.parseInt(d[2].split("-")[0]);
                    int dortsala = k + 1; 
                    String posizioa = posizioPosibleak[random.nextInt(posizioPosibleak.length)];
                    
                    Jokalari j = new Jokalari(d[0], d[1], jaiotzeUrtea, dortsala, posizioa, true);
                    
                    // Ziurtatu metodo hau Talde klasean existitzen dela
                    unekoTaldea.sartuJokalaria(j); 
                    
                    jokalariIndizea++;
                }
            }
        }
    }

    private static void kargatuPartiduak(Denboraldia d, Map<Integer, Talde> tMap) {
        Jardunaldi j1 = new Jardunaldi(1);
        j1.addPartidua(sortuPartidua(tMap, 1, 2, 4, 3)); 
        j1.addPartidua(sortuPartidua(tMap, 3, 4, 2, 2)); 
        j1.addPartidua(sortuPartidua(tMap, 5, 6, 2, 2)); 
        d.addJardunaldia(j1);

        Jardunaldi j2 = new Jardunaldi(2);
        j2.addPartidua(sortuPartidua(tMap, 2, 3, 1, 5)); 
        j2.addPartidua(sortuPartidua(tMap, 4, 5, 3, 3)); 
        j2.addPartidua(sortuPartidua(tMap, 6, 1, 1, 4)); 
        d.addJardunaldia(j2);

        Jardunaldi j3 = new Jardunaldi(3);
        j3.addPartidua(sortuPartidua(tMap, 5, 1, 0, 4)); 
        j3.addPartidua(sortuPartidua(tMap, 3, 4, 1, 2)); 
        j3.addPartidua(sortuPartidua(tMap, 2, 6, 3, 1)); 
        d.addJardunaldia(j3);

        Jardunaldi j4 = new Jardunaldi(4);
        j4.addPartidua(sortuPartidua(tMap, 1, 4, 6, 6)); 
        j4.addPartidua(sortuPartidua(tMap, 2, 5, 2, 1)); 
        j4.addPartidua(sortuPartidua(tMap, 6, 3, 2, 4)); 
        d.addJardunaldia(j4);

        Jardunaldi j5 = new Jardunaldi(5);
        j5.addPartidua(sortuPartidua(tMap, 3, 1, 3, 2)); 
        j5.addPartidua(sortuPartidua(tMap, 5, 3, 4, 5)); 
        j5.addPartidua(sortuPartidua(tMap, 4, 6, 3, 0)); 
        d.addJardunaldia(j5);
    }

    private static Partidua sortuPartidua(Map<Integer, Talde> map, int idL, int idV, int gL, int gV) {
        Talde local = map.get(idL);
        Talde visit = map.get(idV);
        return new Partidua(local, visit, gL, gV);
    }

    private static void gordeObjektua(Object obj, String path) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(obj);
            System.out.println("Gordeta: " + path);
        } catch (IOException e) { e.printStackTrace(); }
    }
}