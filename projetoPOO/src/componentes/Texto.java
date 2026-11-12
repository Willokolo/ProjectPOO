package componentes;

import javax.swing.JLabel;

import interfaces.Colors;

public class Texto extends JLabel {
	public Texto() {
		setForeground(Colors.COR_LETRAS);
		setOpaque(true);
		setBackground(Colors.COR_FUNDO_ESCURO);
	}
}
