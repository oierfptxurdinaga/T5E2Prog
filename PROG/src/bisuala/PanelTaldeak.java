package bisuala;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.*;

public class PanelTaldeak extends JPanel {
    public PanelTaldeak(List<Talde> taldeak) {
        setLayout(new BorderLayout());
        if (taldeak != null && !taldeak.isEmpty()) {
            JComboBox<Talde> combo = new JComboBox<>(taldeak.toArray(new Talde[0]));
            add(combo, BorderLayout.NORTH);
        }
        add(new JTextArea("Hemen jokalarien zerrenda agertuko da..."), BorderLayout.CENTER);
    }
}