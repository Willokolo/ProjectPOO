package POOproject;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class PainelLogin extends JFrame {
	
	JTextField tfEmail;
	JTextField tfSsenha;
	JLabel lbEmail;
	JLabel lbSenha;
	
	public PainelLogin() {
		janelaPainelLogin();
	}
	
	public void janelaPainelLogin() {
		setSize(1200, 675);
		setVisible(true);
		setLocationRelativeTo(null);
		setLayout(null);
		setBackground();
		
	}
	
	public void adiconarEmailSenha() {
		lbEmail = new JLabel("Email");
		lbEmail.setBounds();	
		tfEmail = new Inputs.tfCampoPadrao("Email", 100, 100, 20, 20);
		add(tfEmail);
		
		tfSsenha = new Inputs.tfCampoPadrao("Senha", 200, 200, 20, 20);
		add(tfSsenha);
	}
	
}
