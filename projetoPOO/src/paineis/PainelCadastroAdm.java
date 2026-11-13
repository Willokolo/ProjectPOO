package paineis;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.BotaoGrande;
import componentes.CampoPadrao;
import componentes.CampoSenha;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import interfaces.Fontes;

public class PainelCadastroAdm extends PainelInicial {

	// CAMPOS DE TEXTO
	private JTextField tfEmail;
	private JTextField tfSsenha;
	
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	private JLabel lbSenha;
	
	// BOTÕES
	private JLabel btCadastrar;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	public PainelCadastroAdm() {
		super("Painel Cadastro Adminstrador");
		
		criarTextos();
		criarCamposTexto();
		criarBotoes();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	
	}

	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 480, 480);
		
		add(lbFundoPainelLogin);
	}
	
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "ADMINISTRADOR", 450, 120, 300, 50);
		add(lbTitulo);
		
		lbBemVindo = new LabelTexto(Fontes.FONTE_PEQUENA, "Seja Bem-vindo!", 500, 165, 200, 30);
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
		btCadastrar = new BotaoGrande("CADASTRAR", 410, 450, 380, 70);
		add(btCadastrar);
	}
	
	public static void main(String[] args) {
		new PainelCadastroAdm();
		
	}
}
