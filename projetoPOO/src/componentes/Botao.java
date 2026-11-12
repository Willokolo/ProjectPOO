package componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import interfaces.Colors;

public abstract class Botao extends JLabel{
	public Botao(String nome, int x, int y, int largura, int altura) {
		super(nome);
		setBounds(x, y, largura, altura);
		setForeground(Colors.COR_LETRAS);
	}
	
	public Botao(String nome, ImageIcon imagem, int x, int y, int largura, int altura) {
		super(nome, imagem, JLabel.LEFT);
		setBounds(x, y, largura, altura);
		setForeground(Colors.COR_LETRAS);
	}
}
