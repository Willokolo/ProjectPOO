package paineis;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componentes.BotaoGrande;
import componentes.BotaoPequeno;
import componentes.CampoPadrao;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import interfaces.Fontes;

public class PainelInserirEmailRedefinirSenha extends PainelInicial {

	// CAMPOS DE TEXTO
	private JTextField tfEmail;
		
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbinformarEmail1;
	private JLabel lbinformarEmail2;
	private JLabel lbEmail;
	
	// BOTÕES
	private JLabel btVoltar;	
	private JLabel btEnviar;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
		
	
	public PainelInserirEmailRedefinirSenha() {
		super("Painel Redefinir Senha - Inserir código");
	}
	
	@Override
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "REDEFINIR SENHA", 450, 185, 300, 50);
		add(lbTitulo);
		
		lbinformarEmail1 = new LabelTexto(Fontes.FONTE_PEQUENA, "Informe o Email que será utilizado para o envio do", 405, 235, 400, 20);
		add(lbinformarEmail1);
		
		lbinformarEmail2 = new LabelTexto(Fontes.FONTE_PEQUENA, "código de recurperação", 403, 255, 400, 20);
		add(lbinformarEmail2);
		
		lbEmail = new LabelTexto(Fontes.FONTE_PEQUENA, "Email", 500, 295, 200, 30);
		add(lbEmail);
	}

	@Override
	protected void criarCamposTexto() {
		tfEmail = new CampoPadrao("Email", 410, 325, 380, 50);
		add(tfEmail);
	}

	@Override
	protected void criarBotoes() {
		btEnviar = new BotaoGrande("ENVIAR", 410, 410, 380, 100);
		add(btEnviar);
		
		btVoltar = new BotaoPequeno("< Voltar", 378, 135, 50, 20);
		add(btVoltar);
	}

	@Override
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 450, 400);
		add(lbFundoPainelLogin);
	}
	
	public static void main(String[] args) {
		new PainelInserirEmailRedefinirSenha();
	}
}
