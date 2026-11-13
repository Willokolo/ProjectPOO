package paineis;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.BotaoGrande;
import componentes.CampoPadrao;
import componentes.CompoSenha;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import enums.TipoTexto;
import interfaces.Icones;

public class PainelLoginAdministrador extends Painel {

	private JTextField tfEmail;
	private JTextField tfSsenha;
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	private JLabel lbSenha;
	private JLabel lbFundoPainelLogin;
	
	public PainelLoginAdministrador() {
		super("Painel Login Adminstrador");
		
		adiconarEmailSenha();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	
	}
	
	public void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 480, 480);
		
		add(lbFundoPainelLogin);
	}
	
	public void adiconarEmailSenha() {
		lbTitulo = new LabelTexto(TipoTexto.TITULO, "ADMINISTRADOR", getWidth() / 2 - 125, 120, 300, 50);
		add(lbTitulo);
		
		lbBemVindo = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Seja Bem-vindo!", getWidth() / 2 - 55, 165, 200, 30);
		add(lbBemVindo);
		
		lbEmail = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Email", getWidth() / 2 - 22, 220, 200, 30);
		add(lbEmail);
		
		tfEmail = new CampoPadrao("Email", getWidth() / 2 - 190, 250, 380, 50);
		add(tfEmail);
		
		lbSenha = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Senha", getWidth() / 2 - 22, 320, 200, 30);
		add(lbSenha);
		
		tfSsenha = new CompoSenha("Senha", getWidth() / 2 - 190, 350, 380, 50, this);
		add(tfSsenha);
		
		ImageIcon imagem = new ImageIcon("OlhoSenha.png");
		JLabel labelIamgem = new JLabel(imagem);
		
		labelIamgem.setBounds(getWidth() + (240 - 60), 50 + 440 /2, 50, 50 );
		add(labelIamgem);
		
		BotaoGrande botao = new BotaoGrande("CADASTRAR", getWidth() / 2 - 190, 450, 380, 70);
		add(botao);
		
	}
	
	public static void main(String[] args) {
		new PainelLoginAdministrador();
		
	}
}
