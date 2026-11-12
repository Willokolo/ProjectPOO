package projeto;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.CampoPadrao;
import componentes.Painel;

public class PainelLogin extends Painel {

	private JTextField tfEmail;
	private JTextField tfSsenha;
	private JLabel lbEmail;
	private JLabel lbSenha;
	
	public PainelLogin() {
		super();
		adicionarEmailSenha();
		
	    getContentPane().setBackground(Color.RED);
		
		setVisible(true);
		//Jesus
	}
	
	public void janelaPainelLogin() {
		
	}
	
	public void adicionarEmailSenha() {
		lbEmail = new JLabel("Email");
		//lbEmail.setBounds();	
		tfEmail = new CampoPadrao("Email", 100, 100, 20, 20);
		add(tfEmail);
		
		tfSsenha = new CampoPadrao("Senha", 200, 200, 20, 20);
		add(tfSsenha);
	}
	
}
