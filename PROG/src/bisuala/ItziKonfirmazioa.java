package bisuala;

import javax.swing.*;
import java.awt.event.*;
import model.Federazioa;
import utils.DatuKarga;

public class ItziKonfirmazioa extends WindowAdapter {

    private APP app; // Necesitamos referencia a APP, no solo JFrame genérico
    private Federazioa federazioa;

    public ItziKonfirmazioa(APP app, Federazioa federazioa) {
        this.app = app;
        this.federazioa = federazioa;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        // Preguntamos a la APP si hay cambios (usando el boolean que creamos antes)
        if (app.isAldaketakDauden()) {
            
            // CASO A: HAY CAMBIOS PENDIENTES
            int aukera = JOptionPane.showConfirmDialog(app, 
                    "Aldaketak egin dituzu. Gorde nahi dituzu itxi aurretik?", 
                    "Gorde aldaketak",
                    JOptionPane.YES_NO_CANCEL_OPTION, 
                    JOptionPane.WARNING_MESSAGE);

            if (aukera == JOptionPane.YES_OPTION) {
                // Guardar y Salir
                System.out.println("Aldaketak gordetzen...");
                DatuKarga.gordeFederazioa(federazioa);
                app.dispose();
                System.exit(0);
            } else if (aukera == JOptionPane.NO_OPTION) {
                // Salir sin guardar
                System.out.println("Ez dira aldaketak gorde.");
                app.dispose();
                System.exit(0);
            }
            // Si es CANCEL, no hacemos nada y la ventana sigue abierta

        } else {
            
            // CASO B: NO HAY CAMBIOS (Mensaje simple)
            int aukera = JOptionPane.showConfirmDialog(app, 
                    "Ziur zaude programa itxi nahi duzula?", 
                    "Irten",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (aukera == JOptionPane.YES_OPTION) {
                app.dispose();
                System.exit(0);
            }
        }
    }
}