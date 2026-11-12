package projeto;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.CampoPadrao;
import componentes.Painel;

public class PainelLogin extends Painel {

	JTextField tfEmail;
	JTextField tfSsenha;
	JLabel lbEmail;
	JLabel lbSenha;
	
	public PainelLogin() {
		super();
		adiconarEmailSenha();
		
	    getContentPane().setBackground(Color.RED);

		
		setVisible(true);
		//Jesus
	}
	
	public void janelaPainelLogin() {
		
	}
	
	public void adiconarEmailSenha() {
		lbEmail = new JLabel("Email");
		//lbEmail.setBounds();	
		tfEmail = new CampoPadrao("Email", 100, 100, 20, 20);
		add(tfEmail);
		
		tfSsenha = new CampoPadrao("Senha", 200, 200, 20, 20);
		add(tfSsenha);
	}
	
}
