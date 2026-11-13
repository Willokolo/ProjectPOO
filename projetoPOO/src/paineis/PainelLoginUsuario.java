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
import interfaces.Fontes;

public class PainelLoginUsuario extends PainelInicial {

	// CAMPOS DE TEXTO
	private JTextField tfEmail;
	private JTextField tfSsenha;
	
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	private JLabel lbSenha;
	
	// BOTÕES
	private JLabel btLogin;
	private JLabel btCadastrar;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	public PainelLoginUsuario() {
		super("Painel Login Usuário");
		
		criarTextos();
		criarCamposTexto();
		criarBotoes();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	
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
		
		lbEmail = new LabelTexto(Fontes.FONTE_PEQUENA, "Email", 500, 220, 200, 30);
		add(lbEmail);
		
		lbSenha = new LabelTexto(Fontes.FONTE_PEQUENA, "Senha", 500, 320, 200, 30);
		add(lbSenha);
	}
	
	protected void criarCamposTexto() {
		tfEmail = new CampoPadrao("Email", 410, 250, 380, 50);
		add(tfEmail);
		
		tfSsenha = new CampoSenha("Senha", 410, 350, 380, 50, this);
		add(tfSsenha);
	}
	
	protected void criarBotoes() {
		btLogin = new BotaoGrande("CADASTRAR", 410, 440, 380, 70);
		add(btLogin);
		
		btCadastrar = new BotaoPequeno("-- Não tem conta? Clique aqui e comece a Jogar! --", 400, 520, 400, 30);
		btCadastrar.setFont(Fontes.FONTE_PEQUENA_BOLD);
		btCadastrar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new PainelCadastroNovoJogador();
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
		add(btCadastrar);
	}
	
	public static void main(String[] args) {
		new PainelLoginUsuario();
		
	}
}
