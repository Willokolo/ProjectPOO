package componentes;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import projeto.Utils;

public class BotaoPequeno extends Botao {

	public BotaoPequeno(String nome, int x, int y, int largura, int altura) {
		super(nome, x, y, largura, altura);
		configurar();
	}

	private void configurar() {
		setFont(Utils.FONTE_PEQUENA);
		setBackground(Utils.COR_FUNDO);
		setHorizontalAlignment(JLabel.CENTER);
	}
	
}
