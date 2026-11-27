package paineis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
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
import ouvintes.OuvinteBotaoFundoVerde;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Persistencia;

public class ResultadoPartida extends Painel {

	private CentralDeInformacoes central;
	private Persistencia persistencia;
	private Jogador jogador;
	
	private JLabel fundoEscuro;
	private JLabel textoPerdeu;
	private JLabel btJogarNovamente;
	private JLabel btVoltarMenu;
	
	public ResultadoPartida(Resultado resultado, CentralDeInformacoes central, Persistencia persistencia, Jogador jogador) {
		super("Painel Resultado");
		
		this.central = central;
		this.persistencia = persistencia;
		this.jogador = jogador;
		
		String nomeJogador = jogador.getNome();
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
		criarBotoes();
		
		setVisible(true);
		
	}
	
	private void configurar(String texto, Color cor) {
		fundoEscuro = new LabelFundoEscuroCentralizado(this, 500, 250);
		fundoEscuro.setBorder(BorderFactory.createLineBorder(cor, 10));

		textoPerdeu = new LabelTexto(Fontes.FONTE_GRANDE, texto, this.getWidth() / 2 - 250, 220, 500, 100);
				
		fundoEscuro.setOpaque(true);
		add(fundoEscuro, 0);
		add(textoPerdeu, 0);
	}
	
	private void criarBotoes() {
		btJogarNovamente = new BotaoGrande("JOGAR NOVAMENTE", this.getWidth() / 2 - 230, 310, 440, 60);
		add(btJogarNovamente, 0);

		btVoltarMenu = new BotaoGrande("VOLTAR PARA O MENU", this.getWidth() / 2 - 230, 390, 440, 60);
		btVoltarMenu.setForeground(Cores.COR_PRIMARIA);
		btVoltarMenu.setBackground(Cores.COR_LETRAS);
		btVoltarMenu.addMouseListener(new OuvinteBotaoFundoVerde() {
			public void mouseEntered(MouseEvent e) {
				JComponent j = (JComponent) e.getSource();
				j.setBackground(Cores.COR_BOTAO_HOVER);
				j.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			
			public void mouseExited(MouseEvent e) {
				JComponent j = (JComponent) e.getSource();
				j.setBackground(Cores.COR_LETRAS);
			}
			
			public void mouseClicked(MouseEvent e) {
				dispose();
				
				new PainelPrincipalMenuSolo(central, persistencia, jogador);
			}
		});
		add(btVoltarMenu, 0);
	}
}
