package bisuala;

import javax.swing.*;
import java.awt.event.*;

public class ConfirmarCierre extends WindowAdapter {

	private JFrame frame;

	public ConfirmarCierre(JFrame frame) {
		this.frame = frame;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		int opcion = JOptionPane.showConfirmDialog(frame, "¿Seguro que quieres cerrar el programa?", "Confirmar salida",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

		if (opcion == JOptionPane.YES_OPTION) {
			frame.dispose();
			System.exit(0);
		}
	}
}