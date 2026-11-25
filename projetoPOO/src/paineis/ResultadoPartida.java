package paineis;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import componentes.BotaoGrande;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import enums.Resultado;
import interfaces.Cores;
import interfaces.Fontes;

public class ResultadoPartida extends Painel {

	private JLabel fundoEscuro;
	
	private JLabel textoPerdeu;
	
	private JLabel btJogarNovamente;
	
	private JLabel btVoltarMenu;
	
	public ResultadoPartida( Resultado resultado, String nomeJogador) {
		super("Painel Resultado");
			
		String texto = null;
		
		Color cor = null;
		
		if (resultado == Resultado.DERROTA) {
			cor = Color.RED;
			texto = nomeJogador + " VOCÊ PERDEU";
			
		} 
		
		if (resultado == Resultado.EMPATE) {
			cor = Cores.COR_LETRAS;
			texto = " EMPATOU";

		} 
		
		if (resultado == Resultado.VITORIA) {
			cor = Cores.COR_PRIMARIA;
			texto = nomeJogador + " VOCÊ GANHOU";

		} 

		configurar(texto, cor);
		
		btJogarNovamente = new BotaoGrande("JOGAR NOVAMENTE", this.getWidth() / 2 - 230, 310, 440, 60);
		add(btJogarNovamente, 0);

		btVoltarMenu = new BotaoGrande("VOLTAR PARA O MENU", this.getWidth() / 2 - 230, 390, 440, 60);
		btVoltarMenu.setForeground(Cores.COR_PRIMARIA);
		btVoltarMenu.setBackground(Cores.COR_LETRAS);
		add(btVoltarMenu, 0);
		
		setVisible(true);
		
	}
	
	public void configurar(String texto, Color cor) {
		fundoEscuro = new LabelFundoEscuroCentralizado(this, 500, 250);
		fundoEscuro.setBorder(BorderFactory.createLineBorder(cor, 10));

		textoPerdeu = new LabelTexto(Fontes.FONTE_GRANDE, texto, this.getWidth() / 2 - 250, 220, 500, 100);
				
		fundoEscuro.setOpaque(true);
		add(fundoEscuro, 0);
		add(textoPerdeu, 0);
	}
	
	public static void main(String[] args) {
		new ResultadoPartida(Resultado.VITORIA, "Wilokolo");
	}
	
}
