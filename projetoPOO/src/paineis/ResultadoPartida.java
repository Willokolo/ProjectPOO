package paineis;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import componentes.BotaoGrande;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import enums.Dificuldade;
import enums.Resultado;
import exceptions.JogadorInativoException;
import exceptions.LoginSenhaException;
import exceptions.NaoHaPalavrasCompativeisException;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Palavra;
import projeto.Persistencia;
import projeto.SorteadorDePalavras;

public class ResultadoPartida extends Painel {

	private CentralDeInformacoes central;
	private Persistencia persistencia;
	private Jogador jogador;
	private JFrame janelaAnterior;
	private Dificuldade dificuldade;
	
	private JLabel fundoEscuro;
	private JLabel labelResultado;
	private JLabel btJogarNovamente;
	private JLabel btVoltarMenu;
	
	public ResultadoPartida(Resultado resultado, CentralDeInformacoes central, Persistencia persistencia, Jogador jogador, Dificuldade dificuldade, JFrame janelaAnterior) {
		super("Painel Resultado");
		
		this.central = central;
		this.persistencia = persistencia;
		this.jogador = jogador;
		this.janelaAnterior = janelaAnterior;
		this.dificuldade = dificuldade;
		
		String nomeJogador = jogador.getNome();
		String texto = null;
		Color cor = null;
		
		if (resultado == Resultado.DERROTA) {
			cor = Color.RED;
			texto = nomeJogador + " VOCÊ PERDEU";
			
		} 
		
		if (resultado == Resultado.EMPATE) {
			cor = Color.YELLOW;
			texto = "EMPATE";

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

		labelResultado = new LabelTexto(Fontes.FONTE_GRANDE, texto, this.getWidth() / 2 - 250, 220, 500, 100, cor);
				
		fundoEscuro.setOpaque(true);
		add(fundoEscuro, 0);
		add(labelResultado, 0);
	}
	
	private void criarBotoes() {
		btJogarNovamente = new BotaoGrande("JOGAR NOVAMENTE", this.getWidth() / 2 - 230, 310, 440, 60);
		btJogarNovamente.addMouseListener(new OuvinteJogarNovamente());
		add(btJogarNovamente, 0);

		btVoltarMenu = new BotaoGrande("VOLTAR PARA O MENU", this.getWidth() / 2 - 230, 390, 440, 60);
		btVoltarMenu.setForeground(Cores.COR_PRIMARIA);
		btVoltarMenu.setBackground(Cores.COR_LETRAS);
		btVoltarMenu.addMouseListener(new OuvinteVoltarMenu());
		add(btVoltarMenu, 0);
	}
	
	private class OuvinteJogarNovamente extends OuvinteBotaoFundoVerde {
		
		public void mouseClicked(MouseEvent e) {
			if (janelaAnterior instanceof PainelPrincipalInicioSolo) {
				try {
					Palavra palavra = SorteadorDePalavras.sorteiaPalavra(central.getPalavras(), dificuldade, jogador);
					dispose();
					new PainelPrincipalInicioSolo(central, persistencia, jogador, palavra);
				} catch (NaoHaPalavrasCompativeisException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			} else if (janelaAnterior instanceof PainelPrincipalInicioDuelo) {
				String nome = JOptionPane.showInputDialog(null, "Nome do jogador adversário", "Escolha", JOptionPane.QUESTION_MESSAGE);
				if (nome == null)
					return;
				
				try {
					central.recuperarJogador(nome);
					
					String senha = JOptionPane.showInputDialog(null, "Senha do jogador", "Senha", JOptionPane.QUESTION_MESSAGE);
					if (senha == null)
						return;
					
					Jogador adversario = central.fazerLogin(nome, senha);
					
					ArrayList<Palavra> palavras = SorteadorDePalavras.sorteiaPalavra(central.getPalavras(), dificuldade, jogador, adversario);
					
					dispose();
					new PainelPrincipalInicioDuelo(central, persistencia, jogador, adversario, palavras.get(0), palavras.get(1));
					
				} catch (LoginSenhaException | JogadorInativoException | NaoHaPalavrasCompativeisException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	private class OuvinteVoltarMenu extends OuvinteBotaoFundoVerde {
		
		public void mouseExited(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_LETRAS);
		}
		
		public void mouseClicked(MouseEvent e) {
			dispose();
			
			new PainelPrincipalMenuSolo(central, persistencia, jogador);
		}
	}
}
