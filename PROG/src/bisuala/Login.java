package bisuala;

import javax.swing.*;
import java.util.ArrayList;
import model.*;

public class Login extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private ArrayList<Erabiltzaile> erabiltzaileak;
    private ArrayList<Denboraldia> denboraldiak;

    public Login() {
        // 1. Inicializar listas
        erabiltzaileak = new ArrayList<>();
        denboraldiak = new ArrayList<>();

        // 2. Crear la primera temporada (2025) con lista vacía de equipos
        denboraldiak.add(new Denboraldia(2025, new ArrayList<>()));

        // 3. Crear usuarios de prueba
        erabiltzaileak.add(new ErabiltzaileAdministraria("admin", "admin123"));
        erabiltzaileak.add(new ErabiltzaileEpaile("epaile", "epaile123"));
        erabiltzaileak.add(new ErabiltzailePresi("presi", "presi123"));

        // 4. Interfaz gráfica
        setTitle("Saioa Hasi");
        setLayout(null);
        setBounds(100, 100, 400, 300);

        // Confirmar al cerrar ventana
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,
                        "Ziur zaude programa itxi nahi duzula?",
                        "Irten",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        JLabel lblUser = new JLabel("Erabiltzailea:"); lblUser.setBounds(50, 50, 100, 30);
        txtUser = new JTextField(); txtUser.setBounds(150, 50, 150, 30);

        JLabel lblPass = new JLabel("Pasahitza:"); lblPass.setBounds(50, 100, 100, 30);
        txtPass = new JPasswordField(); txtPass.setBounds(150, 100, 150, 30);

        JButton btnLogin = new JButton("Sartu"); btnLogin.setBounds(150, 160, 100, 30);
        this.getRootPane().setDefaultButton(btnLogin);

        add(lblUser); add(txtUser);
        add(lblPass); add(txtPass);
        add(btnLogin);

        // 5. Acción del botón login
        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();

            // Comprobar campos vacíos
            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "Mesedez, sartu erabiltzailea eta pasahitza.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Comprobar credenciales
            for (Erabiltzaile user : erabiltzaileak) {
                if (user.getErabiltzaile().equals(u) && user.getPasahitza().equals(p)) {
                    new APP(user, denboraldiak).setVisible(true);
                    dispose();
                    return;
                }
            }

            // Credenciales incorrectas
            JOptionPane.showMessageDialog(null,
                    "Datu okerrak, saiatu berriro.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        });
    }

    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}