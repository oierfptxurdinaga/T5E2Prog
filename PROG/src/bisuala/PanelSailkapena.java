package bisuala;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.net.URL; // IMPORTANTE: Necesario para cargar imágenes
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import model.*;

public class PanelSailkapena extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable taula;
	private DefaultTableModel modeloa;
	private int urtea;
	
	
	public PanelSailkapena(ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldi, int urtea) {
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);
		this.urtea = urtea;
		
		// 1. IZENBURUA
		JLabel lblIzenburua = new JLabel("SAILKAPENA");
		lblIzenburua.setFont(new Font("Arial", Font.BOLD, 24));
		lblIzenburua.setForeground(new Color(135, 21, 33));
		lblIzenburua.setHorizontalAlignment(SwingConstants.CENTER);
		lblIzenburua.setBorder(new EmptyBorder(0, 0, 20, 0));
		add(lblIzenburua, BorderLayout.NORTH);

		// 2. KALKULAGAILUA
		ArrayList<TaldeStats> estatistikak = kalkulatuEstatistikak(taldeak, jardunaldi);

		// 3. ORDENATU
		Collections.sort(estatistikak, new Comparator<TaldeStats>() {
			@Override
			public int compare(TaldeStats t1, TaldeStats t2) {
				int diffPuntuak = Integer.compare(t2.puntuak, t1.puntuak);
				if (diffPuntuak != 0)
					return diffPuntuak;
				int diffAVG = (t2.gAlde - t2.gAurka) - (t1.gAlde - t1.gAurka);
				if (diffAVG != 0)
					return diffAVG;
				return Integer.compare(t2.gAlde, t1.gAlde);
			}
		});

		// 4. TAULA BISUALA SORTU
		String[] zutabeak = { "Pos", "Taldea", "PJ", "I", "B", "G", "GF", "GC", "AVG", "PTS" };
		Object[][] data = new Object[estatistikak.size()][10];

		for (int i = 0; i < estatistikak.size(); i++) {
			TaldeStats s = estatistikak.get(i);
			int average = s.gAlde - s.gAurka;

			data[i][0] = i + 1;

			// --- CAMBIO IMPORTANTE ---
			// En lugar de pasar solo el nombre (String), pasamos el objeto 's' (TaldeStats)
			// El Renderer se encargará de sacar el nombre y la imagen de ahí.
			data[i][1] = s;

			data[i][2] = s.jokatuak;
			data[i][3] = s.irabaziak;
			data[i][4] = s.berdinduak;
			data[i][5] = s.galduak;
			data[i][6] = s.gAlde;
			data[i][7] = s.gAurka;
			data[i][8] = average > 0 ? "+" + average : average;
			data[i][9] = s.puntuak;
		}

		modeloa = new DefaultTableModel(data, zutabeak) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			// Esto asegura que la columna 1 se trate como objeto y no como String genérico
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				if (columnIndex == 1)
					return TaldeStats.class;
				return super.getColumnClass(columnIndex);
			}
		};

		taula = new JTable(modeloa);
		JButton btnPrint = new JButton("Inprimatu / PDF");
        btnPrint.addActionListener(e -> {
            try {
                java.text.MessageFormat header = new java.text.MessageFormat("Sailkapena - Denboraldia " + this.urtea);
                
                java.text.MessageFormat footer = new java.text.MessageFormat("Orrialdea {0,number,integer}");

                boolean complete = taula.print(JTable.PrintMode.FIT_WIDTH, header, footer);
                
                if (complete) {
                    JOptionPane.showMessageDialog(null, "Eginda!", "Inprimatzen", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (java.awt.print.PrinterException pe) {
                JOptionPane.showMessageDialog(null, "Errorea inprimatzean: " + pe.getMessage());
            }
        });

		// Gehitu botoia panelera
		this.add(btnPrint, BorderLayout.SOUTH);

		// --- DISEINUA ---
		konfiguratuDiseinua();

		JScrollPane scroll = new JScrollPane(taula);
		scroll.getViewport().setBackground(Color.WHITE);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
		add(scroll, BorderLayout.CENTER);
	}

	private ArrayList<TaldeStats> kalkulatuEstatistikak(ArrayList<Talde> taldeak, ArrayList<Jardunaldi> jardunaldi) {
		Map<String, TaldeStats> mapaStats = new HashMap<>();

		if (taldeak != null) {
			for (Talde t : taldeak) {
				// --- CAMBIO: Ahora guardamos también la ruta de la imagen (t.getEskutua())
				mapaStats.put(t.getIzena().trim(), new TaldeStats(t.getIzena(), t.getEskutua()));
			}
		}

		if (jardunaldi != null) {
			for (Jardunaldi j : jardunaldi) {
				if (j.getPartiduak() != null) {
					for (Partidua p : j.getPartiduak()) {
						if (!p.jokatutaDago())
							continue;

						String localNom = p.getEtxekoTaldea().getIzena().trim();
						String visitNom = p.getKanpokoTaldea().getIzena().trim();

						TaldeStats sLocal = mapaStats.get(localNom);
						TaldeStats sVisit = mapaStats.get(visitNom);

						if (sLocal == null || sVisit == null)
							continue;

						int gL = p.getEtxekoGolak();
						int gK = p.getKanpokoGolak();

						sLocal.gAlde += gL;
						sLocal.gAurka += gK;
						sVisit.gAlde += gK;
						sVisit.gAurka += gL;
						sLocal.jokatuak++;
						sVisit.jokatuak++;

						if (gL > gK) {
							sLocal.irabaziak++;
							sLocal.puntuak += 3;
							sVisit.galduak++;
						} else if (gK > gL) {
							sVisit.irabaziak++;
							sVisit.puntuak += 3;
							sLocal.galduak++;
						} else {
							sLocal.berdinduak++;
							sLocal.puntuak += 1;
							sVisit.berdinduak++;
							sVisit.puntuak += 1;
						}
					}
				}
			}
		}
		return new ArrayList<>(mapaStats.values());
	}

	private void konfiguratuDiseinua() {
		taula.setRowHeight(40); // Un poco más alto para que quepa el icono
		taula.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		taula.setGridColor(new Color(230, 230, 230));
		taula.setShowVerticalLines(false);

		JTableHeader header = taula.getTableHeader();
		header.setBackground(new Color(135, 21, 33));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 14));
		header.setPreferredSize(new Dimension(header.getWidth(), 40));

		taula.getColumnModel().getColumn(0).setPreferredWidth(40);
		taula.getColumnModel().getColumn(1).setPreferredWidth(250); // Más ancho para el nombre + icono

		for (int i = 0; i < taula.getColumnCount(); i++) {
			boolean zentratu = (i != 1);
			taula.getColumnModel().getColumn(i).setCellRenderer(new EstiloRenderer(zentratu));
		}
	}

	// --- CLASE INTERNA DE DATOS ---
	private class TaldeStats {
		String taldeIzena;
		String irudia; // Nuevo campo para la ruta de la imagen
		int jokatuak = 0;
		int irabaziak = 0;
		int berdinduak = 0;
		int galduak = 0;
		int gAlde = 0;
		int gAurka = 0;
		int puntuak = 0;

		public TaldeStats(String izena, String irudia) {
			this.taldeIzena = izena;
			this.irudia = irudia;
		}

		@Override
		public String toString() {
			return taldeIzena;
		}
	}

	// --- RENDERER PERSONALIZADO ---
	private class EstiloRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;
		private boolean zentratu;

		public EstiloRenderer(boolean zentratu) {
			this.zentratu = zentratu;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			// Llamamos a super para que configure colores, selección, etc.
			super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

			// Limpiamos el icono por defecto (para columnas que no son equipo)
			setIcon(null);

			// 1. COLUMNA DE EQUIPO (Índice 1)
			if (column == 1 && value instanceof TaldeStats) {
				TaldeStats stats = (TaldeStats) value;

				// Texto: el nombre del equipo
				setText(stats.taldeIzena);

				// Imagen: Cargar y escalar
				if (stats.irudia != null) {
					URL imgUrl = getClass().getResource(stats.irudia);
					if (imgUrl != null) {
						ImageIcon icon = new ImageIcon(imgUrl);
						Image img = icon.getImage();
						// Escalamos la imagen a un tamaño pequeño (ej. 30x30)
						Image newImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
						setIcon(new ImageIcon(newImg));
					}
				}

				setHorizontalAlignment(JLabel.LEFT);
				setBorder(new EmptyBorder(0, 10, 0, 0)); // Un poco de margen a la izquierda
			}
			// 2. RESTO DE COLUMNAS
			else {
				if (zentratu)
					setHorizontalAlignment(JLabel.CENTER);
				else {
					setHorizontalAlignment(JLabel.LEFT);
					setBorder(new EmptyBorder(0, 10, 0, 0));
				}
			}

			// Estilos generales (Zebra y Negritas)
			if (!isSelected) {
				setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 250));
			}
			if (column == 0 || column == 9) {
				setFont(getFont().deriveFont(Font.BOLD));
			}

			return this;
		}
	}
}