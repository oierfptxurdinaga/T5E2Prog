package utils;

import java.io.*;
import java.util.ArrayList;
import model.*;

public class DatuKarga {

    // Fitxategien bideak (Rutas de los archivos)
    private static final String ERABILTZAILE_PATH = "src/data/erabiltzaileak.ser";
    private static final String DENBORALDI_PATH = "src/data/ligaren_datuak.ser";

    /**
     * Erabiltzaileak .ser fitxategitik kargatzen ditu.
     * Fitxategia ez bada existitzen, zerrenda huts bat itzultzen du.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Erabiltzaile> kargatuErabiltzaileak() {
        ArrayList<Erabiltzaile> zerrenda = new ArrayList<>();
        File fitxategia = new File(ERABILTZAILE_PATH);

        if (fitxategia.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxategia))) {
                zerrenda = (ArrayList<Erabiltzaile>) ois.readObject();
                System.out.println("Erabiltzaileak zuzen kargatu dira.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Errorea erabiltzaileak kargatzean: " + e.getMessage());
            }
        } else {
            System.out.println("Ez da 'erabiltzaileak.ser' aurkitu. Zerrenda berria sortuko da.");
        }
        return zerrenda;
    }

    /**
     * Denboraldiak .ser fitxategitik kargatzen ditu.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Denboraldia> kargatuDenboraldiak() {
        ArrayList<Denboraldia> zerrenda = new ArrayList<>();
        File fitxategia = new File(DENBORALDI_PATH);

        if (fitxategia.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fitxategia))) {
                zerrenda = (ArrayList<Denboraldia>) ois.readObject();
                System.out.println("Denboraldiak zuzen kargatu dira.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Errorea denboraldiak kargatzean: " + e.getMessage());
            }
        } else {
            System.out.println("Ez da 'denboraldiak.ser' aurkitu. Zerrenda berria sortuko da.");
        }
        return zerrenda;
    }
}