package componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import interfaces.*;

public class BotaoPequeno extends Botao {

	public BotaoPequeno(String nome, int x, int y, int largura, int altura) {
		super(nome, x, y, largura, altura);
		configurar();
	}

	private void configurar() {
		setFont(Fontes.FONTE_PEQUENA);
		setBackground(Cores.COR_FUNDO);
		setHorizontalAlignment(JLabel.CENTER);
	}
	
}
