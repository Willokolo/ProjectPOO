package paineis;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import componentes.LabelTexto;
import componentes.PerfilUsuario;
import enums.Dificuldade;
import exceptions.JogadorInativoException;
import exceptions.LoginSenhaException;
import exceptions.NaoHaPalavrasCompativeisException;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Palavra;
import projeto.Persistencia;
import projeto.SorteadorDePalavras;

public class PainelPrincipalMenuDuelo extends PainelPrincipalMenu {
	
	private JLabel labelFotoUsuario1;
	private JLabel labelFotoUsuario2;
	private JLabel labelNomeUsuario1;
	private JLabel labelNomeUsuario2;

	private Jogador jogador2;

	public PainelPrincipalMenuDuelo(CentralDeInformacoes central, Persistencia persistencia, Jogador jogador) {
		super("Painel Principal - Menu Duelo", central, persistencia, jogador);
		
		botaoIniciarPartida.addMouseListener(new OuvinteIniciar());
		criarLabelUsuario();
	}
	
	private void criarLabelUsuario() {
		String nomeUsuario = jogador.getNome();
		
		labelFotoUsuario1 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario, 325, 180, 150, 150);
		labelNomeUsuario1 = new LabelTexto(Fontes.FONTE_MEDIA, nomeUsuario, 325, 295, 150, 100);
		
		labelFotoUsuario2 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario, 625, 180, 150, 150);
		labelFotoUsuario2.addMouseListener(new OuvinteAdversario());
		labelNomeUsuario2 = new LabelTexto(Fontes.FONTE_MEDIA, "Escolher", 625, 295, 150, 100);

		add(labelFotoUsuario1);
		add(labelNomeUsuario1);
		add(labelFotoUsuario2);
		add(labelNomeUsuario2);
	}
	
	private void atualizarPerfilAdversario() {
		remove(labelFotoUsuario2);
		remove(labelNomeUsuario2);
		
		String nomeUsuario = jogador2.getNome();
		
		labelFotoUsuario2 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario, 625, 180, 150, 150);
		add(labelFotoUsuario2);
		
		labelNomeUsuario2 = new LabelTexto(Fontes.FONTE_MEDIA, nomeUsuario, 625, 295, 150, 100);
		add(labelNomeUsuario2);
		repaint();
	}
	
	private class OuvinteIniciar extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			if (!jogador.isAtivo()) {
				JOptionPane.showMessageDialog(null, "O jogador atual está inativo", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if (jogador2 == null) {
				JOptionPane.showMessageDialog(null, "Escolha um jogador para jogar contra", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				
				if (dificuldadeSelecionada == Dificuldade.ALEATORIO) {
					int aleatorio = (int)(Math.random() * 3) + 1;
					
					switch (aleatorio) {
					case 1:
						dificuldadeSelecionada = Dificuldade.FACIL;
						break;
						
					case 2:
						dificuldadeSelecionada = Dificuldade.FACIL;
						break;
						
					case 3:
						dificuldadeSelecionada = Dificuldade.FACIL;
						break;
					}
				}
				
				ArrayList<Palavra> palavras = SorteadorDePalavras.sorteiaPalavra(central.getPalavras(), dificuldadeSelecionada, jogador, jogador2);
				
				dispose();
								
				new PainelPrincipalInicioDuelo(central, persistencia, jogador, jogador2, palavras.get(0), palavras.get(1));
			} catch (NaoHaPalavrasCompativeisException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class OuvinteAdversario extends OuvinteBotaoSemFundo{
		public void mouseClicked(MouseEvent e) {
			if (central.getJogadores().size() < 2) {
				JOptionPane.showMessageDialog(null, "Não há jogadores cadastrados o suficiente", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String nome = JOptionPane.showInputDialog(null, "Nome do jogador", "Escolha", JOptionPane.QUESTION_MESSAGE);
			
			if (nome == null)
				return;
			else if (nome.equals(jogador.getNome())) {
				JOptionPane.showMessageDialog(null, "Você não pode jogar contra si mesmo", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				central.recuperarJogador(nome);
				
				String senha = JOptionPane.showInputDialog(null, "Senha do jogador", "Inserir senha", JOptionPane.QUESTION_MESSAGE);
				jogador2 = central.fazerLogin(nome, senha);
				
				atualizarPerfilAdversario();
			} catch (LoginSenhaException | JogadorInativoException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
