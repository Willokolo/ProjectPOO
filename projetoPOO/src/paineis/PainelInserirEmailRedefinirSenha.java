package paineis;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.Painel;

public class PainelInserirEmailRedefinirSenha extends Painel {

	public PainelInserirEmailRedefinirSenha() {
		super("Painel Redefinir Senha");

		camposTexto();
		
	}
	
	// CAMPOS DE TEXTO
	private JTextField tfEmail;
	
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbBemVindo;
	private JLabel lbEmail;
	
	// BOTÕES
	private JLabel btVoltar;
	private JLabel btEnviar;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	public void camposTexto() {
		
	}

}
