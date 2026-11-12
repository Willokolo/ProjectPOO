package componentes;

import javax.swing.JLabel;

import enums.Utils;

public class BotoesGrandes extends JLabel {
	
	public BotoesGrandes(String nome, int x, int y, int largura, int altura) {
		setText(nome);
		setBounds(x, y, largura, altura);
		setBackground(Utils.COR_PRIMARIA);
		setOpaque(true);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(Utils.FONTE_GRANDE);
		setForeground(Utils.COR_LETRAS);
	}
}
