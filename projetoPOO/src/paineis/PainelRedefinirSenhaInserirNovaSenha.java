package paineis;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import componentes.BotaoGrande;
import componentes.BotaoPequeno;
import componentes.CampoPadrao;
import componentes.CampoSenha;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import exceptions.RetornoVazioException;
import exceptions.SenhaVaziaException;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Persistencia;

public class PainelRedefinirSenhaInserirNovaSenha extends PainelInicial {
	
	private class OuvinteRedefinir extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String senha = tfSenha.getText();
			
			try {
				jogador.redefinirSenha(senha);
				persistencia.salvarCentral(central);
				
				dispose();
				
				janelaOriginal.setVisible(true);
			} catch (SenhaVaziaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// ATRIBUTOS FUNCIONAIS
	private CentralDeInformacoes central;
	private Jogador jogador;
	private JFrame janelaOriginal;
	private JFrame janelaAnterior;
	private Persistencia persistencia;
	
	// CAMPOS DE TEXTO
	private JTextField tfSenha;
		
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbSenha;
	
	// BOTÕES
	private JLabel btVoltar;	
	private JLabel btRedefinir;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	public PainelRedefinirSenhaInserirNovaSenha(CentralDeInformacoes central, Jogador jogador, Persistencia persistencia, JFrame janelaOriginal, JFrame janelaAnterior) {
		super("Painel Redefinir Senha - Inserir código");
		
		this.central = central;
		this.jogador = jogador;
		this.janelaOriginal = janelaOriginal;
		this.janelaAnterior = janelaAnterior;
		this.persistencia = persistencia;
	}
	public PainelRedefinirSenhaInserirNovaSenha() {
		super("Painel Redefinir Senha - Inserir código");
	}

	@Override
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "REDEFINIR SENHA", 450, 210, 300, 50);
		add(lbTitulo);
		
		lbSenha = new LabelTexto(Fontes.FONTE_PEQUENA, "Senha", 396, 300, 400, 20);
		add(lbSenha);
	}

	@Override
	protected void criarCamposTexto() {
		tfSenha = new CampoSenha("Inserir uma nova senha", 410, 325, 380, 50);
		add(tfSenha);
	}

	@Override
	protected void criarBotoes() {
		btRedefinir = new BotaoGrande("REDEFINIR", 410, 410, 380, 70);
		btRedefinir.addMouseListener(new OuvinteRedefinir());
		add(btRedefinir);	
		
		btVoltar = new BotaoPequeno("< Voltar", 378, 170, 50, 20);
		btVoltar.addMouseListener(new OuvinteBotaoSemFundo() {
			
			public void mouseClicked(MouseEvent e) {
				dispose();
				janelaAnterior.setVisible(true);
			}
		});
		add(btVoltar);
	}

	@Override
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 450, 330);
		add(lbFundoPainelLogin);
	}
	
	public static void main(String[] args) {
		new PainelRedefinirSenhaInserirNovaSenha();
	}
}
