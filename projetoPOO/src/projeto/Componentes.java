package projeto;

import javax.swing.JLabel;

public class Componentes {
	
	public static class Botoes extends JLabel {
		
		public Botoes(String nome, int x, int y, int largura, int altura) {
			setText(nome);
			setBounds(x, y, largura, altura);
			setBackground(Utils.COR_PRIMARIA);
			setOpaque(true);
			setHorizontalAlignment(JLabel.CENTER);
			setFont(Utils.FONTE_GRANDE);
			setForeground(Utils.COR_LETRAS);
		}
	}
}
