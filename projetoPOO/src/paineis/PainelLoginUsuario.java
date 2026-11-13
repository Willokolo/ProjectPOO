package paineis;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

public class PainelLoginUsuario extends Painel {

	private JTextField tfEmail;
	private JTextField tfSsenha;
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	private JLabel lbSenha;
	private JLabel lbFundoPainelLogin;
	
	public PainelLoginUsuario() {
		super("Painel Login Usuário");
		
		adiconarEmailSenha();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	
	}
	
	public void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 480, 500);
		
		add(lbFundoPainelLogin);
	}
	
	public void adiconarEmailSenha() {
		lbTitulo = new LabelTexto(TipoTexto.TITULO, "LOGIN", getWidth() / 2 - 50, 120, 200, 50);
		add(lbTitulo);
		
		lbBemVindo = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Bem-vindo de volta!", getWidth() / 2 - 68, 165, 200, 30);
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
		
		BotaoGrande botao = new BotaoGrande("CADASTRAR", getWidth() / 2 - 190, 440, 380, 70);
		add(botao);
		
		lbSenha = new LabelTexto(TipoTexto.TEXTO_PEQUENO_BOLD, "-- Não tem conta? Clique aqui e comece a Jogar! --", getWidth() / 2 - 178, 530, 400, 30);
		lbSenha.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new PainelRegistroUsuario();
				
			}
		});
		add(lbSenha);
		
	}
	
	public static void main(String[] args) {
		new PainelLoginUsuario();
		
	}
}
