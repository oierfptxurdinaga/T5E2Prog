package bisuala;

import javax.swing.*;
import java.awt.*; 
import java.awt.event.*;
import java.io.*; // FileInputStream erabiltzeko
import java.util.ArrayList;
import model.*;
import utils.DatuKarga; 

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextField txtUser;
    private JPasswordField txtPass;
    
    private ArrayList<Erabiltzaile> erabiltzaileak;
    private Federazioa federazioa; // ALDAKETA: Objektu nagusia

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Login() {
        // 1. Datuak kargatu
        erabiltzaileak = DatuKarga.kargatuErabiltzaileak();
        
        // Federazioa kargatu (.ser fitxategitik)
        federazioa = kargatuFederazioa(); 

        datuakHasieratuBeharBada();

        // 2. Leihoaren konfigurazioa
        setTitle("Saioa Hasi");
        setLayout(null);
        setBounds(100, 100, 400, 300);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        // ... (WindowListener kodea berdin mantendu) ...
        addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int aukera = JOptionPane.showConfirmDialog(Login.this, "Ziur zaude programa itxi nahi duzula?", "Irten",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (aukera == JOptionPane.YES_OPTION) {
					dispose();
					System.exit(0);
				}
			}
		});

        // Osagaiak
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

        add(lblUser);
        add(txtUser);
        add(lblPass);
        add(txtPass);
        add(btnLogin);

        // Botoiaren logika
        btnLogin.addActionListener(e -> {
            String u = txtUser.getText().trim();
            String p = new String(txtPass.getPassword()).trim();

            if (u.isEmpty() || p.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mesedez, sartu erabiltzailea eta pasahitza.", "Errorea",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean aurkitua = false;
            for (Erabiltzaile user : erabiltzaileak) {
                if (user.getErabiltzaile().equals(u) && user.getPasahitza().equals(p)) {
                    // ALDAKETA: Orain 'federazioa' pasatzen diogu APP-ari
                    // GARRANTZITSUA: Joan APP.java-ra eta aldatu konstruktorea!
                    new APP(user, federazioa).setVisible(true);
                    dispose();
                    aurkitua = true;
                    break;
                }
            }

            if (!aurkitua) {
                JOptionPane.showMessageDialog(null, "Datu okerrak, saiatu berriro.", "Errorea",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Metodo laguntzailea Federazioa kargatzeko (DatuKarga-n egon beharko luke, baina hemen jarriko dugu orain)
    private Federazioa kargatuFederazioa() {
        File f = new File("src/data/federazioa.ser");
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                return (Federazioa) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void datuakHasieratuBeharBada() {
        if (erabiltzaileak.isEmpty()) {
            erabiltzaileak.add(new ErabiltzaileAdministraria("admin", "admin"));
        }
        // Federazioa null bada, berria sortu
        if (federazioa == null) {
            System.out.println("Federazioa hutsa sortzen...");
            federazioa = new Federazioa();
            // Hemen defektuzko zerbait sortu liteke...
        }
    }
}