package componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import enums.Utils;

public class BotaoGrande extends Botao {
	
	public BotaoGrande(String nome, int x, int y, int largura, int altura) {
		super(nome, x, y, largura, altura);
		configurar();
		setHorizontalAlignment(JLabel.CENTER);
	}
	
	public BotaoGrande(String nome, ImageIcon imagem, int x, int y, int largura, int altura) {
		super(nome, imagem, x, y, largura, altura);
		configurar();
		setBorder(new EmptyBorder(0, 10, 0, 0));
	}

	private void configurar() {
		setBackground(Utils.COR_PRIMARIA);
		setOpaque(true);
		setFont(Utils.FONTE_GRANDE);
	}
}
