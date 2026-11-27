package paineis;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.BotaoGrande;
import componentes.BotaoPequeno;
import componentes.CampoPadrao;
import componentes.CampoSenha;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import exceptions.JogadorJaExisteException;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Persistencia;

public class PainelCadastroNovoJogador extends PainelInicial {
	
	private class OuvinteCadastrar extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String nome = tfNome.getText();
			String email = tfEmail.getText();
			String senha = tfSenha.getText();
			
			try {
				Jogador jogador = new Jogador(nome, email, senha);
				central.addJogador(jogador);
				persistencia.salvarCentral(central);
				
				dispose();
				
				new PainelPrincipalMenuSolo(central, persistencia, jogador);
			} catch (JogadorJaExisteException e1) {
				System.out.println(e1.getMessage());
			}
		}
		
	}
	
	// ATRIBUTOS FUNCIONAIS
	private CentralDeInformacoes central;
	private Persistencia persistencia;
	
	// CAMPOS DE TEXTO
	private JTextField tfEmail;
	private JTextField tfSenha;
	private JTextField tfNome;
	
	// TEXTOS
	private JLabel lbNome;
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	private JLabel lbSenha;
	
	// BOTÕES
	private JLabel btCadastrar;
	private JLabel btLogin;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	
	// CONSTRUTOR
	public PainelCadastroNovoJogador(CentralDeInformacoes central, Persistencia persistencia) {
		super("Painel Cadastro Novo Jogador");
		
		this.central = central;
		this.persistencia = persistencia;
	}
	
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 480, 570);
		add(lbFundoPainelLogin);
		
	}
	
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "NOVO JOGADOR", 450, 75, 300, 50);
		add(lbTitulo);
		
		lbBemVindo = new LabelTexto(Fontes.FONTE_PEQUENA, "Seja Bem-vindo!", 500, 115, 200, 30);
		add(lbBemVindo);
		
		lbNome = new LabelTexto(Fontes.FONTE_PEQUENA, "Nome", 500, 155, 200, 30);
		add(lbNome);
		
		lbSenha = new LabelTexto(Fontes.FONTE_PEQUENA, "Senha", 500, 365, 200, 30);
		add(lbSenha);
		
		lbEmail = new LabelTexto(Fontes.FONTE_PEQUENA, "Email", 500, 260, 200, 30);
		add(lbEmail);
	}
	
	protected void criarCamposTexto() {
		tfEmail = new CampoPadrao("Email", 410, 290, 380, 50);
		add(tfEmail);
		
		tfSenha = new CampoSenha("Senha", 410, 395, 380, 50);
		add(tfSenha);
		
		tfNome = new CampoPadrao("Nome", 410, 185, 380, 50);
		add(tfNome);
	}
	
	protected void criarBotoes() {
		btCadastrar = new BotaoGrande("CADASTRAR", 410, 470, 380, 70);
		btCadastrar.addMouseListener(new OuvinteCadastrar());
		add(btCadastrar);
		
		btLogin = new BotaoPequeno("-- Já tem conta? Clique aqui e comece a Jogar! --", 400, 550, 400, 30);
		btLogin.setFont(Fontes.FONTE_PEQUENA_BOLD);
		btLogin.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new PainelLoginUsuario(central, persistencia);
			}
			
			public void mouseEntered(MouseEvent e) {
				JComponent c = (JComponent) e.getSource();
				c.setOpaque(true);
				c.setCursor(new Cursor(Cursor.HAND_CURSOR));
				repaint();
			}
			
			public void mouseExited(MouseEvent e) {
				JComponent c = (JComponent) e.getSource();
				c.setOpaque(false);
				repaint();
			}
		});
		add(btLogin);
	}
}
