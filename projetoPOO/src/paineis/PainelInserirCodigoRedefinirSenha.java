
package paineis;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
import componentes.Painel;
import exceptions.CodigoException;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import projeto.CentralDeInformacoes;
import projeto.CodigoDeConfirmacao;
import projeto.Jogador;
import projeto.Persistencia;

public class PainelInserirCodigoRedefinirSenha extends PainelInicial {
	
	private class OuvinteEnviar extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String c = tfNumero1.getText() + tfNumero2.getText() + tfNumero3.getText() + tfNumero4.getText();
			
			try {
				codigo.confirmarCodigo(c);
				
				setVisible(false);
				new PainelRedefinirSenhaInserirNovaSenha(central, jogador, persistencia, janelaOriginal, PainelInserirCodigoRedefinirSenha.this);
			} catch (CodigoException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	// ATRIBUTOS FUNCIONAIS
	private CentralDeInformacoes central;
	private Jogador jogador;
	private CodigoDeConfirmacao codigo;
	private JFrame janelaOriginal;
	private Persistencia persistencia;
	
	// CAMPOS DE TEXTO
	private JTextField tfNumero1;
	private JTextField tfNumero2;
	private JTextField tfNumero3;
	private JTextField tfNumero4;
		
	// TEXTOS
	private JLabel lbTitulo;
	private JLabel lbTexto1;
	private JLabel lbTexto2;
	
	// BOTÕES
	private JLabel btVoltar;	
	private JLabel btEnviar;
	private JLabel btReenviarCodigo;
	
	// PAINEL DE FUNDO
	private JLabel lbFundoPainelLogin;
		
	
	public PainelInserirCodigoRedefinirSenha(CentralDeInformacoes central, Jogador jogador, CodigoDeConfirmacao codigo, Persistencia persistencia, JFrame janelaOriginal) {
		super("Painel Redefinir Senha");
		
		this.central = central;
		this.jogador = jogador;
		this.codigo = codigo;
		this.janelaOriginal = janelaOriginal;
		this.persistencia = persistencia;
	}

	@Override
	protected void criarTextos() {
		lbTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "REDEFINIR SENHA", 450, 185, 300, 50);
		add(lbTitulo);
		
		lbTexto1 = new LabelTexto(Fontes.FONTE_PEQUENA, "O código está sendo enviado ao Email informado,", 405, 235, 400, 20);
		add(lbTexto1);
		
		lbTexto2 = new LabelTexto(Fontes.FONTE_PEQUENA, "insira-o no espaço abaixo", 403, 255, 400, 20);
		add(lbTexto2);
		
	}

	@Override
	protected void criarCamposTexto() {
		tfNumero1 = new CampoPadrao("", 410, 325, 70, 100);
		tfNumero1.addKeyListener(new OuvinteLimitarCaracteres(tfNumero1, 1));
		tfNumero1.setFont(Fontes.FONTE_GRANDE);
		tfNumero1.setHorizontalAlignment(JTextField.CENTER);
		add(tfNumero1);
		
		tfNumero2 = new CampoPadrao("", 513, 325, 70, 100);
		tfNumero2.addKeyListener(new OuvinteLimitarCaracteres(tfNumero2, 1));
		tfNumero2.setFont(Fontes.FONTE_GRANDE);
		tfNumero2.setHorizontalAlignment(JTextField.CENTER);
		add(tfNumero2);
		
		tfNumero3 = new CampoPadrao("", 616, 325, 70, 100);
		tfNumero3.addKeyListener(new OuvinteLimitarCaracteres(tfNumero3, 1));
		tfNumero3.setFont(Fontes.FONTE_GRANDE);
		tfNumero3.setHorizontalAlignment(JTextField.CENTER);
		add(tfNumero3);
		
		tfNumero4 = new CampoPadrao("", 718, 325, 70, 100);
		tfNumero4.addKeyListener(new OuvinteLimitarCaracteres(tfNumero4, 1));
		tfNumero4.setFont(Fontes.FONTE_GRANDE);
		tfNumero4.setHorizontalAlignment(JTextField.CENTER);
		add(tfNumero4);		
	}

	@Override
	protected void criarBotoes() {
		btVoltar = new BotaoPequeno("< Voltar", 378, 135, 50, 20);
		btVoltar.addMouseListener(new OuvinteBotaoSemFundo() {
			
			public void mouseClicked(MouseEvent e) {
				dispose();
				
				new PainelInserirEmailRedefinirSenha(central, persistencia, janelaOriginal);
			}
		});
		add(btVoltar);
		
		btEnviar = new BotaoGrande("ENVIAR", 410, 450, 380, 70);
		btEnviar.addMouseListener(new OuvinteEnviar());
		add(btEnviar);
		
		btReenviarCodigo = new BotaoPequeno("--Reenviar código--", 500, 485, 200, 100);
		add(btReenviarCodigo);
	}

	@Override
	protected void labelFundoEscuroCentral() {
		lbFundoPainelLogin = new LabelFundoEscuroCentralizado(this, 450, 400);
		add(lbFundoPainelLogin);
		
	}

	public class OuvinteLimitarCaracteres extends KeyAdapter {

	    private JTextField campo;
	    private int qtdChar;

	    public OuvinteLimitarCaracteres(JTextField campo, int qtdChar) {
	        this.campo = campo;
	        this.qtdChar = qtdChar;
	    }

	    @Override
	    public void keyTyped(KeyEvent e) {

	        char c = e.getKeyChar();

	        if (!Character.isDigit(c)) {
	            e.consume();
	        }

	        if (campo.getText().length() >= qtdChar) {
	            e.consume();
	        }
	    }
	}
}
