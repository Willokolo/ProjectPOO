package componentes;

import javax.swing.JLabel;

import interfaces.*;

public class BotoesGrandes extends JLabel {
	
	public BotoesGrandes(String nome, int x, int y, int largura, int altura) {
		setText(nome);
		setBounds(x, y, largura, altura);
		setBackground(Cores.COR_PRIMARIA);
		setOpaque(true);
		setHorizontalAlignment(JLabel.CENTER);
		setFont(Fontes.FONTE_GRANDE);
		setForeground(Cores.COR_LETRAS);
	}
}
