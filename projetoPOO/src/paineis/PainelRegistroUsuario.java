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

public class PainelRegistroUsuario extends Painel {

	private JTextField tfEmail;
	private JTextField tfSsenha;
	private JTextField tfNome;
	private JLabel lbNome;
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	private JLabel lbSenha;
	private JLabel lbFundoPainelLogin;
	
	public PainelRegistroUsuario() {
		super("Painel Login Usuário");
		
		adiconarEmailSenha();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	
	}
	
	public void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 480, 550);
		add(lbFundoPainelLogin);
		
	}
	
	public void adiconarEmailSenha() {
		lbTitulo = new LabelTexto(TipoTexto.TITULO, "NOVO JOGADOR", getWidth() / 2 - 128, 75, 300, 50);
		add(lbTitulo);
		
		lbBemVindo = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Seja Bem-vindo!", getWidth() / 2 - 55, 115, 200, 30);
		add(lbBemVindo);
		
		lbNome = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Nome", getWidth() / 2 - 22, 155, 200, 30);
		add(lbNome);
		
		tfNome = new CampoPadrao("Nome", getWidth() / 2 - 190, 185, 380, 50);
		add(tfNome);
		
		lbEmail = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Email", getWidth() / 2 - 22, 260, 200, 30);
		add(lbEmail);
		
		tfEmail = new CampoPadrao("Email", getWidth() / 2 - 190, 290, 380, 50);
		add(tfEmail);
		
		lbSenha = new LabelTexto(TipoTexto.TEXTO_PEQUENO, "Senha", getWidth() / 2 - 22, 365, 200, 30);
		add(lbSenha);
		
		tfSsenha = new CompoSenha("Senha", getWidth() / 2 - 190, 395, 380, 50, this);
		add(tfSsenha);
		
		ImageIcon imagem = new ImageIcon("OlhoSenha.png");
		JLabel labelIamgem = new JLabel(imagem);
		
		labelIamgem.setBounds(getWidth() + (240 - 60), 4000 + 440 /2, 50, 50 );
		add(labelIamgem);
		
		BotaoGrande botao = new BotaoGrande("CADASTRAR", getWidth() / 2 - 190, 470, 380, 70);
		add(botao);
		
		lbSenha = new LabelTexto(TipoTexto.TEXTO_PEQUENO_BOLD, "-- Já tem conta? Clique aqui e comece a Jogar! --", getWidth() / 2 - 178, 550, 400, 30);
		lbSenha.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				new PainelLoginUsuario();
				
			}
		});
		add(lbSenha);
		
	}
	
	public static void main(String[] args) {
		new PainelRegistroUsuario();
		
	}
}
