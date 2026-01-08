package bisuala;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.*;

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtErabiltzaileIzena;
	private JPasswordField passwordField;
	private Erabiltzaile hautatutakoErabiltzailea;

	public Login() {

		Erabiltzaile erabiltzailea = null;
		ErabiltzaileAdministraria administrari1 = new ErabiltzaileAdministraria("administrari", "administrari123");
		ErabiltzaileEpaile epaile1 = new ErabiltzaileEpaile("epaile", "epaile123");
		ErabiltzailePresi presi1 = new ErabiltzailePresi("presi", "presi123");
		ArrayList<Erabiltzaile> erabiltzaileak = new ArrayList<>();
		erabiltzaileak.add(presi1);
		erabiltzaileak.add(epaile1);
		erabiltzaileak.add(administrari1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtErabiltzaileIzena = new JTextField();
		txtErabiltzaileIzena.setBounds(203, 69, 160, 30);
		contentPane.add(txtErabiltzaileIzena);
		txtErabiltzaileIzena.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(203, 109, 160, 30);
		contentPane.add(passwordField);

		JLabel lblNewLabel = new JLabel("Erabiltzaile izena:");
		lblNewLabel.setBounds(65, 69, 128, 28);
		contentPane.add(lblNewLabel);

		JLabel lblPasahitza = new JLabel("Pasahitza:");
		lblPasahitza.setBounds(98, 109, 95, 28);
		contentPane.add(lblPasahitza);

		JButton btnSaioaHasi = new JButton("Saioa Hasi");
		btnSaioaHasi.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sartutakoUser = txtErabiltzaileIzena.getText();
                String sartutakoPass = new String(passwordField.getPassword());

                for (Erabiltzaile erab : erabiltzaileak) {
                    if (erab.getErabiltzaile().equals(sartutakoUser) && 
                        erab.getPasahitza().equals(sartutakoPass)) {
                        hautatutakoErabiltzailea = erab; 
                        saioaHasi();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(null, "Datuak okerrak dira");
            }
        });
		btnSaioaHasi.setBounds(179, 162, 105, 30);

		contentPane.add(btnSaioaHasi);
    }

    private void saioaHasi() {
        EventQueue.invokeLater(() -> {
            APP frame = new APP(hautatutakoErabiltzailea);
            frame.setVisible(true);
            dispose();
        });
    }
    
}

