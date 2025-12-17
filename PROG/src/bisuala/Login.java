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
	private JTextField txtErabiltsaileIzena;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public Login() {

		ErabiltzaileArrunta arrunt1 = new ErabiltzaileArrunta("pepe");
		ErabiltzaileEpaile epaile1 = new ErabiltzaileEpaile("Jose", "kk");
		ErabiltzailePrezi prezi1 = new ErabiltzailePrezi("Juan", "12345");
		ArrayList<Erabiltzaile> erabiltzaileak = new ArrayList<>();
		erabiltzaileak.add(prezi1);
		erabiltzaileak.add(epaile1);
		erabiltzaileak.add(arrunt1);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtErabiltsaileIzena = new JTextField();
		txtErabiltsaileIzena.setBounds(203, 69, 160, 30);
		contentPane.add(txtErabiltsaileIzena);
		txtErabiltsaileIzena.setColumns(10);

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

				boolean error=true;
				for (int i = 0; i < erabiltzaileak.size(); i++) {
					if (erabiltzaileak.get(i).getErabiltzaile().equals(txtErabiltsaileIzena.getText())) {
						if (erabiltzaileak.get(i) instanceof ErabiltzaileEpaile) {
							ErabiltzaileEpaile epaile= (ErabiltzaileEpaile)erabiltzaileak.get(i);
							if (epaile.getPasahitza().equals(passwordField.getText())) {
								JOptionPane.showMessageDialog(null, "Pasahitza Zuzena", "Epaile Modura sartzen",
										JOptionPane.INFORMATION_MESSAGE);
								error=false;
							}
						} else if (erabiltzaileak.get(i) instanceof ErabiltzailePrezi) {
							ErabiltzailePrezi prezi= (ErabiltzailePrezi)erabiltzaileak.get(i);
							if (prezi.getPasahitza().equals(passwordField.getText())) {
								JOptionPane.showMessageDialog(null, "Pasahitza Zuzena", "Epaile modura sartzen",
										JOptionPane.INFORMATION_MESSAGE);
								error=false;
							}
						}
					}
				}
				if(error) {
					JOptionPane.showMessageDialog(null, "ERROREA", "Erabiltzailea edo Pashitza EZ da zuzena",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSaioaHasi.setBounds(179, 162, 105, 30);
		contentPane.add(btnSaioaHasi);

		JButton btnGonbidatu = new JButton("Sartu Gonbidatu Bezala");
		btnGonbidatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Sartzen...", "Gonbidatu modura sartzen",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnGonbidatu.setBounds(148, 202, 171, 30);
		contentPane.add(btnGonbidatu);

	}
}
