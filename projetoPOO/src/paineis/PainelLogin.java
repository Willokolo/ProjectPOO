package paineis;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.CampoPadrao;
import componentes.LabelFundoEscuroCentralizado;
import componentes.Painel;

public class PainelLogin extends Painel {

	JTextField tfEmail;
	JTextField tfSsenha;
	JLabel lbEmail;
	JLabel lbSenha;
	JLabel lbFundoPainelLogin;
	
	public PainelLogin() {
		super("Painel Login");
		
		adiconarEmailSenha();
		
		adicionarLinhaCentral();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	
	}
	
	public void adicionarLinhaCentral() {

	    int largura = 2;
	    int altura = 675;

	    int x = (getWidth() - largura) / 2;
	    int y = (getHeight() - altura) / 2;

	    JLabel lbLinha = new JLabel();

	    lbLinha.setBounds(x, y, largura, altura);
	    lbLinha.setOpaque(true);
	    lbLinha.setBackground(Color.WHITE);

	    add(lbLinha);
	}
	
	public void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 520, 530);
		
		add(lbFundoPainelLogin);
	}
	
	public void adiconarEmailSenha() {
		//lbEmail = new LabelTexto(Tipo);
		add(lbEmail);
		
		tfEmail = new CampoPadrao("Email", 100, 100);
		add(tfEmail);
		
		tfSsenha = new CampoPadrao("Senha", 200, 100);
		add(tfSsenha);
	}
	
	public static void main(String[] args) {
		new PainelLogin();
	}
	
}
