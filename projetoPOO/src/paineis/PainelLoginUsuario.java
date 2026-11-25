package paineis;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import componentes.BotaoGrande;
import componentes.BotaoPequeno;
import componentes.CampoPadrao;
import componentes.CampoSenha;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import exceptions.JogadorInativoException;
import exceptions.JogadorJaExisteException;
import exceptions.LoginSenhaException;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Persistencia;

public class PainelLoginUsuario extends PainelInicial {

	private class OuvinteLogin extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String login = tfLogin.getText();
			String senha = tfSenha.getText();
			
			System.out.println("[" + login + "] [" + senha + "]");
			
			try {
				central.fazerLogin(login, senha);
				dispose();
				
				new PainelPrincipalMenuSolo();
			} catch (LoginSenhaException | JogadorInativoException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// ATRIBUTOS FUNCIONAIS
	private CentralDeInformacoes central;
	private Persistencia persistencia;
	
	// CAMPOS DE TEXTO
	private JTextField tfLogin;
	private JTextField tfSenha;
	
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbLogin;
	private JLabel lbSenha;
	
	// BOTÕES
	private JLabel btEsqueceuSenha;
	private JLabel btLogin;
	private JLabel btCadastrar;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	public PainelLoginUsuario(CentralDeInformacoes central, Persistencia persistencia) {
		super("Painel Login Usuário");
		
		this.central = central;
		this.persistencia = persistencia;
	}
	
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 480, 500);
		add(lbFundoPainelLogin);
	}
	
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "LOGIN", 500, 120, 200, 50);
		add(lbTitulo);
		
		lbBemVindo = new LabelTexto(Fontes.FONTE_PEQUENA, "Bem-vindo de volta!", 500, 165, 200, 30);
		add(lbBemVindo);
		
		lbLogin = new LabelTexto(Fontes.FONTE_PEQUENA, "Login", 500, 220, 200, 30);
		add(lbLogin);
		
		lbSenha = new LabelTexto(Fontes.FONTE_PEQUENA, "Senha", 500, 320, 200, 30);
		add(lbSenha);
	}
	
	protected void criarCamposTexto() {
		tfLogin = new CampoPadrao("Login", 410, 250, 380, 50);
		add(tfLogin);
		
		tfSenha = new CampoSenha("Senha", 410, 350, 380, 50);
		add(tfSenha);
	}
	
	protected void criarBotoes() {
		btLogin = new BotaoGrande("CADASTRAR", 410, 450, 380, 70);
		btLogin.addMouseListener(new OuvinteLogin());
		add(btLogin);
		
		btCadastrar = new BotaoPequeno("-- Não tem conta? Clique aqui e comece a Jogar! --", 400, 520, 400, 30);
		btCadastrar.setFont(Fontes.FONTE_PEQUENA_BOLD);
		btCadastrar.addMouseListener(new OuvinteBotaoSemFundo() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new PainelCadastroNovoJogador(central, persistencia);
			}
		});
		add(btCadastrar);
		
		btEsqueceuSenha = new BotaoPequeno("Esqueceu sua senha?", 620, 400, 200, 30);
		btEsqueceuSenha.addMouseListener(new OuvinteBotaoSemFundo() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				new PainelInserirEmailRedefinirSenha(central, persistencia, PainelLoginUsuario.this);
			}
		});
		add(btEsqueceuSenha);
	}
}
