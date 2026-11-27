package paineis;

import java.awt.event.MouseEvent;

import javax.mail.MessagingException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import componentes.BotaoGrande;
import componentes.BotaoPequeno;
import componentes.CampoPadrao;
import componentes.LabelFundoEscuroCentralizado;
import componentes.LabelTexto;
import exceptions.JogadorInativoException;
import exceptions.LoginSenhaException;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import projeto.CentralDeInformacoes;
import projeto.CodigoDeConfirmacao;
import projeto.Jogador;
import projeto.Persistencia;

public class PainelInserirEmailRedefinirSenha extends PainelInicial {
	
	private class OuvinteEnviar extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String email = tfEmail.getText();
			
			try {
				Jogador jogador = central.recuperarJogador(email);
				JOptionPane.showMessageDialog(null, "Enviando código...", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				
				CodigoDeConfirmacao codigo = new CodigoDeConfirmacao(email);
				
				setVisible(false);
				new PainelInserirCodigoRedefinirSenha(central, jogador, codigo, persistencia, janelaAnterior);
				
			} catch (LoginSenhaException | JogadorInativoException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			} catch (MessagingException e2) {
				JOptionPane.showMessageDialog(null, "Erro durante o envio do código", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// ATRIBUTOS FUNCIONAIS
	private CentralDeInformacoes central;
	private JFrame janelaAnterior;
	private Persistencia persistencia;
	
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
		
	
	public PainelInserirEmailRedefinirSenha(CentralDeInformacoes central, Persistencia persistencia, JFrame janelaAnterior) {
		super("Painel Redefinir Senha - Inserir código");
		
		this.central = central;
		this.janelaAnterior = janelaAnterior;
		this.persistencia = persistencia;
	}
	
	@Override
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "REDEFINIR SENHA", 450, 185, 300, 50);
		add(lbTitulo);
		
		lbinformarEmail1 = new LabelTexto(Fontes.FONTE_PEQUENA, "Informe o Email que será utilizado para o envio do", 405, 235, 400, 20);
		add(lbinformarEmail1);
		
		lbinformarEmail2 = new LabelTexto(Fontes.FONTE_PEQUENA, "código de recuperação", 403, 255, 400, 20);
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
		btEnviar.addMouseListener(new OuvinteEnviar());
		add(btEnviar);
		
		btVoltar = new BotaoPequeno("< Voltar", 378, 135, 50, 20); // MUDAR COR DO FUNDO DEPOIS
		btVoltar.addMouseListener(new OuvinteBotaoSemFundo() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				janelaAnterior.setVisible(true);
			}
		});
		add(btVoltar);
	}

	@Override
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 450, 400);
		add(lbFundoPainelLogin);
	}
}
