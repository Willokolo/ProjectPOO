package componentes;

import java.awt.Font;

import javax.swing.JLabel;

import interfaces.Cores;

public class LabelTexto extends JLabel {
	
	public LabelTexto(Font fonte, String texto, int x, int y, int largura, int altura) {
		super(texto);
		setForeground(Cores.COR_LETRAS);
		setBounds(x, y, largura, altura);	
		setHorizontalAlignment(JLabel.CENTER);
		setFont(fonte);
	}
}
