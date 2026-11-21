package paineis;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import componentes.BarraLateral;
import componentes.CampoPadrao;
import componentes.LabelTexto;
import componentes.QuadroDePontos;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import paineis.PainelInserirCodigoRedefinirSenha.OuvinteLimitarCaracteres;

public class PainelPrincipalInicioSolo extends BarraLateral {

	private JLabel fundoLetrasErradas;
	private JLabel textoLetrasErradas;
	
	private JLabel quadroPontos;
	
	private JLabel fotoUsuario;
	private JLabel textoNomeUsuario;
	private String nomeUsuario;
	
	private ArrayList<Character> letrasErradas = new ArrayList<Character>();
	
	private int pontos;
	
	private JTextField inserirLetra;
	
	public PainelPrincipalInicioSolo(String nomeUsuario) {
		super("Painel Principal - Menu Solo");
		this.nomeUsuario = nomeUsuario;
		
		//adicionarLabelsPalavra();
		
		adicionarPontosUsuario();
		
		criarLetrasErradas();
		
		adicionarCampoEscolherLetra();
	
	}
	
	public void adicionarCampoEscolherLetra() {
		inserirLetra = new CampoPadrao("", 515, 350, 70, 100);
		inserirLetra.addKeyListener(new OuvinteLimitarCaracteres(inserirLetra, 1));
		inserirLetra.setFont(Fontes.FONTE_GRANDE);
		inserirLetra.setBackground(Cores.COR_FUNDO_ESCURO);		
		inserirLetra.setHorizontalAlignment(JTextField.CENTER);
		add(inserirLetra);
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

	        if (Character.isDigit(c)) {
	            e.consume();
	        }

	        if (campo.getText().length() >= qtdChar) {
	            e.consume();
	        }
	    }
	}
	
	public void adicionarPontosUsuario() {
		quadroPontos = new QuadroDePontos("Pontos Solo", pontos, Icones.ICONE_HISTORICO, 450, 40);
		add(quadroPontos, 0);
		
		fotoUsuario = new JLabel(Icones.ICONE_HISTORICO);
		fotoUsuario.setBounds(475, 160, 150, 150);
		fotoUsuario.setBackground(Cores.COR_PRIMARIA);
		fotoUsuario.setOpaque(true);

		textoNomeUsuario = new LabelTexto(Fontes.FONTE_MEDIA, nomeUsuario, 475, 275, 150, 100);

		add(textoNomeUsuario, 0);
		add(fotoUsuario, 0);
	}
	
	public void criarLetrasErradas() {
		fundoLetrasErradas = new JLabel();
		fundoLetrasErradas.setBackground(Cores.COR_FUNDO_ESCURO);
		fundoLetrasErradas.setBounds(900, 40, 260, 350);
		fundoLetrasErradas.setOpaque(true);
		
		textoLetrasErradas = new LabelTexto(Fontes.FONTE_MEDIA, "LETRAS ERRADAS", 915, 55, 230, 50);
		textoLetrasErradas.setBackground(Cores.COR_PRIMARIA);
		textoLetrasErradas.setOpaque(true);
		
		add(textoLetrasErradas);
		add(fundoLetrasErradas);
	}
	
	public static void main(String[] args) {
		new PainelPrincipalInicioSolo("Wilokolo");
	}
}
