package componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import interfaces.*;

public class BotaoMedio extends Botao {
	
	public BotaoMedio(String nome, int x, int y, int largura, int altura) {
		super(nome, x, y, largura, altura);
		configurar();
		setHorizontalAlignment(JLabel.CENTER);
	}
	
	public BotaoMedio(String nome, ImageIcon imagem, int x, int y, int largura, int altura) {
		super(nome, imagem, x, y, largura, altura);
		configurar();
		setBorder(new EmptyBorder(0, 10, 0, 0));
	}
	
	private void configurar() {
		setFont(Fontes.FONTE_MEDIA);
		setBackground(Cores.COR_FUNDO);
	}
}
