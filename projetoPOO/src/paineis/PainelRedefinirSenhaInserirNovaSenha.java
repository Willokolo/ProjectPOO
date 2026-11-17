package paineis;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.BotaoGrande;
import componentes.BotaoPequeno;
import componentes.CampoPadrao;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import componentes.Painel;
import interfaces.Fontes;

public class PainelRedefinirSenhaInserirNovaSenha extends PainelInicial {

	// CAMPOS DE TEXTO
	private JTextField tfEmail;
		
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbSenha;
	
	// BOTÕES
	private JLabel btVoltar;	
	private JLabel btEnviar;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
	
	public PainelRedefinirSenhaInserirNovaSenha() {
		super("Painel Redefinir Senha - Inserir código");
	}

	@Override
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "REDEFINIR SENHA", 450, 210, 300, 50);
		add(lbTitulo);
		
		lbSenha = new LabelTexto(Fontes.FONTE_PEQUENA, "Senha", 396, 300, 400, 20);
		add(lbSenha);
	}

	@Override
	protected void criarCamposTexto() {
		tfEmail = new CampoPadrao("Inserir uma nova senha", 410, 325, 380, 50);
		add(tfEmail);
	}

	@Override
	protected void criarBotoes() {
		btEnviar = new BotaoGrande("REDEFINIR", 410, 410, 380, 70);
		add(btEnviar);	
		
		btVoltar = new BotaoPequeno("< Voltar", 378, 170, 50, 20);
		add(btVoltar);
	}

	@Override
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 450, 330);
		add(lbFundoPainelLogin);
	}
	
	public static void main(String[] args) {
		new PainelRedefinirSenhaInserirNovaSenha();
	}	
}
