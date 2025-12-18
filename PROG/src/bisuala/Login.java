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

	public Login() {

		Erabiltzaile erabiltzailea = null;

		ErabiltzaileArrunta arrunt1 = new ErabiltzaileArrunta("Gonbidatua");
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

				final Erabiltzaile[] erabiltzaile = new Erabiltzaile[1];
				boolean error = true;
				for (int i = 0; i < erabiltzaileak.size(); i++) {
					if (erabiltzaileak.get(i).getErabiltzaile().equals(txtErabiltsaileIzena.getText())) {

						if (erabiltzaileak.get(i) instanceof ErabiltzaileEpaile) {
							ErabiltzaileEpaile epaile = (ErabiltzaileEpaile) erabiltzaileak.get(i);
							if (epaile.getPasahitza().equals(passwordField.getText())) {
								error = false;
								erabiltzaile[0] = erabiltzaileak.get(i);
							}
						} else if (erabiltzaileak.get(i) instanceof ErabiltzailePrezi) {
							ErabiltzailePrezi prezi = (ErabiltzailePrezi) erabiltzaileak.get(i);
							if (prezi.getPasahitza().equals(passwordField.getText())) {
								error = false;
								erabiltzaile[0] = erabiltzaileak.get(i);
							}
						}
					}
				}

				if (error) {
					JOptionPane.showMessageDialog(null, "ERROREA", "Erabiltzailea edo Pashitza EZ da zuzena",
							JOptionPane.ERROR_MESSAGE);
				} else {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								APP frame = new APP(erabiltzaile[0]);
								frame.setVisible(true);
								dispose();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});
		btnSaioaHasi.setBounds(179, 162, 105, 30);
		contentPane.add(btnSaioaHasi);

		JButton btnGonbidatu = new JButton("Sartu Gonbidatu Bezala");
		btnGonbidatu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final Erabiltzaile[] erabiltzaile = new Erabiltzaile[1];
				erabiltzaile[0] = arrunt1;
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							APP frame = new APP(erabiltzaile[0]);
							frame.setVisible(true);
							dispose();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				});

			}
		});
		btnGonbidatu.setBounds(148, 202, 171, 30);
		contentPane.add(btnGonbidatu);
	}
}
