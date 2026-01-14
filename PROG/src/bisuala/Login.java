package bisuala;

import javax.swing.*;
import java.awt.*; // EventQueue eta besteak erabiltzeko
import java.awt.event.*;
import java.util.ArrayList;
import model.*;
import utils.DatuKarga;

public class Login extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private ArrayList<Erabiltzaile> erabiltzaileak;
    private ArrayList<Denboraldia> denboraldiak;

    /**
     * Aplikazioaren sarrera puntua (METODO NAGUSIA)
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Login frame = new Login();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Eraikitzailea (Constructor)
     */
    public Login() {
        // 1. Datuak kargatu
        erabiltzaileak = DatuKarga.kargatuErabiltzaileak();
        denboraldiak = DatuKarga.kargatuDenboraldiak();

        datuakHasieratuBeharBada();

        // 2. Leihoaren konfigurazioa
        setTitle("Saioa Hasi");
        setLayout(null);
        setBounds(100, 100, 400, 300);

        // Leihoa ixteko logika
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int aukera = JOptionPane.showConfirmDialog(
                        Login.this, 
                        "Ziur zaude programa itxi nahi duzula?",
                        "Irten",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (aukera == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }
        });

        // Osagaiak (Componentes)
        JLabel lblUser = new JLabel("Erabiltzailea:"); 
        lblUser.setBounds(50, 50, 100, 30);
        txtUser = new JTextField(); 
        txtUser.setBounds(150, 50, 150, 30);

        JLabel lblPass = new JLabel("Pasahitza:"); 
        lblPass.setBounds(50, 100, 100, 30);
        txtPass = new JPasswordField(); 
        txtPass.setBounds(150, 100, 150, 30);

        JButton btnLogin = new JButton("Sartu"); 
        btnLogin.setBounds(150, 160, 100, 30);
        this.getRootPane().setDefaultButton(btnLogin);

        add(lblUser); add(txtUser);
        add(lblPass); add(txtPass);
        add(btnLogin);

        // Botoiaren logika
        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mesedez, sartu erabiltzailea eta pasahitza.", "Errorea", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean aurkitua = false;
            for (Erabiltzaile user : erabiltzaileak) {
                if (user.getErabiltzaile().equals(u) && user.getPasahitza().equals(p)) {
                    new APP(user, denboraldiak).setVisible(true);
                    dispose();
                    aurkitua = true;
                    break;
                }
            }

            if (!aurkitua) {
                JOptionPane.showMessageDialog(null, "Datu okerrak, saiatu berriro.", "Errorea", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void datuakHasieratuBeharBada() {
        if (erabiltzaileak.isEmpty()) {
            System.out.println("Admin lehenetsia sortzen...");
            erabiltzaileak.add(new ErabiltzaileAdministraria("admin", "admin123"));
            erabiltzaileak.add(new ErabiltzaileEpaile("epaile", "epaile123"));
            erabiltzaileak.add(new ErabiltzailePresi("presi", "presi123"));
        }
        if (denboraldiak.isEmpty()) {
            System.out.println("Denboraldi hutsa sortzen...");
            denboraldiak.add(new Denboraldia(2025));
        }
    }
}