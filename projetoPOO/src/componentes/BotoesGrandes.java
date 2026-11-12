package componentes;

import javax.swing.JLabel;

import interfaces.*;

public class BotoesGrandes extends JLabel {
	
	public BotoesGrandes(String nome, int x, int y, int largura, int altura) {
		setText(nome);
		setBounds(x, y, largura, altura);
		setBackground(Colors.COR_PRIMARIA);
		setOpaque(true);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(FontSize.FONTE_GRANDE);
		setForeground(Colors.COR_LETRAS);
	}
}
