package bisuala;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import model.*;

public class Login extends JFrame {
    private JTextField txtUser;
    private JPasswordField txtPass;
    private ArrayList<Erabiltzaile> erabiltzaileak;
    private ArrayList<Denboraldia> denboraldiak;

    public Login() {
        // 1. Zerrendak hasieratu
        erabiltzaileak = new ArrayList<>();
        denboraldiak = new ArrayList<>();
        
        // 2. Proba datuak sortu (Denboraldia)
        // GOGORATU: model/Sailkapena.java-n 'public Sailkapena() {}' eraikitzailea gehitu behar duzula!
        Denboraldia d1 = new Denboraldia(2025, new ArrayList<>(), new ArrayList<>(), new Sailkapena(), false);
        denboraldiak.add(d1);
        
        // 3. Erabiltzaileak sortu
        erabiltzaileak.add(new ErabiltzaileAdministraria("admin", "admin123"));
        erabiltzaileak.add(new ErabiltzaileEpaile("epaile", "epaile123"));
        erabiltzaileak.add(new ErabiltzailePresi("presi", "presi123"));

        // 4. Interfazearen diseinua
        setTitle("Saioa Hasi");
        setLayout(null);
        setBounds(100, 100, 400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lblUser = new JLabel("Erabiltzailea:"); lblUser.setBounds(50, 50, 100, 30);
        txtUser = new JTextField(); txtUser.setBounds(150, 50, 150, 30);
        
        JLabel lblPass = new JLabel("Pasahitza:"); lblPass.setBounds(50, 100, 100, 30);
        txtPass = new JPasswordField(); txtPass.setBounds(150, 100, 150, 30);
        
        JButton btnLogin = new JButton("Sartu"); btnLogin.setBounds(150, 160, 100, 30);

        add(lblUser); add(txtUser);
        add(lblPass); add(txtPass);
        add(btnLogin);

        // 5. Botoiaren logika
        btnLogin.addActionListener(e -> {
            String u = txtUser.getText();
            String p = new String(txtPass.getPassword());

            for (Erabiltzaile user : erabiltzaileak) {
                if (user.getErabiltzaile().equals(u) && user.getPasahitza().equals(p)) {
                    // HEMEN irekitzen dugu APP leiho nagusia saioa ondo hastean
                    new APP(user, denboraldiak).setVisible(true); 
                    dispose(); // Login leihoa itxi
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "Datu okerrak, saiatu berriro.");
        });
    }
    
    public static void main(String[] args) {
        new Login().setVisible(true);
    }
}