package componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import interfaces.*;


public class LabelTexto extends JLabel {
	
	public LabelTexto(Font fonte, String texto, int x, int y, int largura, int altura) {
		super(texto);
		setForeground(Cores.COR_LETRAS);
		setBounds(x, y, largura, altura);	
		setHorizontalAlignment(JLabel.CENTER);
		setFont(fonte);
	}
	public LabelTexto(Font fonte, String texto, int x, int y, int largura, int altura, Color cor) {
		super(texto);
		setForeground(Cores.COR_LETRAS);
		setBounds(x, y, largura, altura);	
		setHorizontalAlignment(JLabel.CENTER);
		setFont(fonte);
		setForeground(cor);
	}
	
}
