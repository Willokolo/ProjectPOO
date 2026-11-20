package paineis;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import componentes.BarraLateral;
import componentes.BotaoGrande;
import componentes.BotaoMedio;
import componentes.LabelTexto;
import enums.Dificuldade;

public class PainelPrincipalMenuSolo extends BarraLateral {

	// ===================== Menu lateral: fundo e abas =====================
	private JLabel fundoMenuLateral;

	private JLabel abaDificuldade;
	private JLabel abaHistorico;

	private JLabel iconeAbaDificuldade;
	private JLabel iconeAbaHistorico;

	private JLabel textoAbaDificuldade;
	private JLabel textoAbaHistorico;

	// ===================== Corpo: dificuldade =====================
	private JLabel tituloDificuldade;
	private JLabel botaoFacil;
	private JLabel botaoMedio;
	private JLabel botaoDificil;
	private JLabel botaoAleatorio;

	private Dificuldade dificuldadeSelecionada = Dificuldade.ALEATORIO;

	// ===================== Corpo: histórico =====================
	private JLabel tituloHistorico;

	// ===================== Estado =====================
	private boolean abaDificuldadeAtiva = true;

	// ===================== Construtor =====================
	public PainelPrincipalMenuSolo() {
	    super("Painel Principal - Menu Solo");

	    criarMenuLateral();

	    setVisible(true);
	}

	// ===================== Criação do menu lateral =====================
	public void criarMenuLateral() {
	    OuvinteAbas ouvinteAbas = new OuvinteAbas();

	    fundoMenuLateral = new JLabel();
	    fundoMenuLateral.setBackground(Cores.COR_FUNDO_ESCURO);
	    fundoMenuLateral.setBounds(900, 40, 255, 594);
	    fundoMenuLateral.setOpaque(true);

	    abaDificuldade = new JLabel();
	    abaDificuldade.setOpaque(true);
	    abaDificuldade.setBounds(900, 40, 128, 96);
	    abaDificuldade.addMouseListener(ouvinteAbas);

	    abaHistorico = new JLabel();
	    abaHistorico.setOpaque(true);
	    abaHistorico.setBounds(1028, 40, 127, 96);
	    abaHistorico.addMouseListener(ouvinteAbas);

	    criarCorpoDificuldade();

	    criarIconesAbas();

	    criarTextosAbas();

	    add(abaDificuldade);
	    add(abaHistorico);

	    add(fundoMenuLateral);

	    atualizarCoresAbas();
	}

	public void criarIconesAbas() {
		iconeAbaDificuldade = new JLabel(Icones.ICONE_HISTORICO);
	    iconeAbaDificuldade.setBounds(905, 35, 118, 86);
	    iconeAbaDificuldade.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    iconeAbaDificuldade.setIcon(Icones.ICONE_HISTORICO);

	    iconeAbaHistorico = new JLabel(Icones.ICONE_HISTORICO);
	    iconeAbaHistorico.setBounds(1033, 35, 118, 86);
	    iconeAbaHistorico.setCursor(new Cursor(Cursor.HAND_CURSOR));
	    iconeAbaHistorico.setIcon(Icones.ICONE_HISTORICO);

	    add(iconeAbaDificuldade);
	    add(iconeAbaHistorico);
	}

	public void criarTextosAbas() {
		textoAbaDificuldade = new BotaoMedio("DIFICULDADE", 900, 60, 150, 118, true);

		textoAbaHistorico = new BotaoMedio("HISTÓRICO", 1034, 60, 150, 118, true);

		add(textoAbaDificuldade);
	    add(textoAbaHistorico);
	}

	// ===================== Criação dos corpos =====================
	public void criarCorpoDificuldade() {
		tituloDificuldade = new LabelTexto(Fontes.FONTE_MEDIA, "DIFICULDADE", 922, 160, 200, 50);
		add(tituloDificuldade, 0);

		botaoFacil = new BotaoGrande("FACIL", 925, 230, 200, 80);
		botaoFacil.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoFacil, 0);

		botaoMedio = new BotaoGrande("MEDIO", 925, 330, 200, 80);
		botaoMedio.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoMedio, 0);

		botaoDificil = new BotaoGrande("DIFICIL", 925, 430, 200, 80);
		botaoDificil.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoDificil, 0);

		botaoAleatorio = new BotaoGrande("ALEATORIO", 925, 530, 200, 80);
		botaoAleatorio.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoAleatorio, 0);
	}

	public void criarCorpoHistorico() {
		tituloHistorico = new LabelTexto(Fontes.FONTE_MEDIA, "HISTORICO", 922, 140, 200, 50);
		add(tituloHistorico, 0);
		
		removerCorpoDificuldade();
		
		JLabel textoNPartida;
		JLabel textoData;
		JLabel textoModo;
		JLabel textoJogadores;
		JLabel textoDificuldade;
		JLabel textoPalavra;
		JLabel textoVencedor;
		JLabel textoResultado;
		JLabel textoResultado1;
		JLabel textoResultado2;
		
		textoNPartida = new LabelTexto(Fontes.FONTE_PEQUENA_BOLD, "Partida N° " + "1", 905, 190, 100, 20);
		add(textoNPartida, 0);
		
		textoData = new LabelTexto(Fontes.FONTE_PEQUENA, "Data e Hora: " + "19/11/2026 22:13:22", 897, 210, 250, 20);
		add(textoData, 0);
		
		textoModo = new LabelTexto(Fontes.FONTE_PEQUENA, "Modo: " + "DUELO", 910, 230, 100, 20);
		add(textoModo, 0);
		
		textoJogadores = new LabelTexto(Fontes.FONTE_PEQUENA, "Jogadores: " + "Willokolo e Raf3nix", 886, 250, 250, 20);
		add(textoJogadores, 0);
		
		textoDificuldade = new LabelTexto(Fontes.FONTE_PEQUENA, "Dificuldade: " + "Brutal", 908, 270, 100, 20);
		add(textoDificuldade, 0);
		
		textoData = new LabelTexto(Fontes.FONTE_PEQUENA, "Data e Hora: " + "19/11/2026 22:13:22", 905, 210, 100, 20);
		add(textoData, 0);
		
//		textoData = new LabelTexto(Fontes.FONTE_PEQUENA, "Data e Hora: " + "19/11/2026 22:13:22", 905, 210, 100, 20);
//		add(textoData, 0);
//		
//		textoData = new LabelTexto(Fontes.FONTE_PEQUENA, "Data e Hora: " + "19/11/2026 22:13:22", 905, 210, 100, 20);
//		add(textoData, 0);
		
		
	}

	// ===================== Atualização visual =====================
	public void atualizarCoresAbas() {
	    JLabel abaAtiva   = abaDificuldadeAtiva ? abaDificuldade : abaHistorico;
	    JLabel abaInativa = abaDificuldadeAtiva ? abaHistorico   : abaDificuldade;

	    abaAtiva.setBackground(Cores.COR_FUNDO_ESCURO);

	    abaInativa.setBackground(Cores.MENU_DESATIVADO);

	    revalidate();
	    repaint();
	}

	private void restaurarCorBotoesDificuldade() {
		botaoFacil.setBackground(Cores.COR_PRIMARIA);
		botaoMedio.setBackground(Cores.COR_PRIMARIA);
		botaoDificil.setBackground(Cores.COR_PRIMARIA);
		botaoAleatorio.setBackground(Cores.COR_PRIMARIA);
	}

	// ===================== Remoção dos corpos =====================
	private void removerCorpoDificuldade() {
		remove(tituloDificuldade);
		remove(botaoFacil);
		remove(botaoDificil);
		remove(botaoMedio);
		remove(botaoAleatorio);
	}

//	private void removerCorpoHistorico() {
//		remove(textoNPartida);
//		remove(textoData);
//		remove(textoModo);
//		remove(textoJogadores);
//		remove(textoDificuldade);
//		remove(textoPalavra);
//		remove(textoVencedor);
//		remove(textoResultado);
//		remove(textoResultado1);
//		remove(textoResultado2);
//	}

	// ===================== Ouvintes =====================
	private class OuvinteBotoesDificuldade extends MouseAdapter {
	    @Override
	    public void mousePressed(MouseEvent e) {
	        if (e.getSource() == botaoFacil) {
	        	dificuldadeSelecionada = Dificuldade.FACIL;
	        	restaurarCorBotoesDificuldade();
	        	botaoFacil.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);

	        } else if (e.getSource() == botaoMedio) {
	        	dificuldadeSelecionada = Dificuldade.NORMAL;
	        	restaurarCorBotoesDificuldade();
	        	botaoMedio.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);

	        } else if (e.getSource() == botaoDificil) {
	        	dificuldadeSelecionada = Dificuldade.DIFICIL;
	        	restaurarCorBotoesDificuldade();
	        	botaoDificil.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);

	        } else if (e.getSource() == botaoAleatorio) {
	        	dificuldadeSelecionada = Dificuldade.ALEATORIO;
	        	restaurarCorBotoesDificuldade();
	        	botaoAleatorio.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
	        }
	    }
	}

	private class OuvinteAbas extends MouseAdapter {
	    @Override
	    public void mousePressed(MouseEvent e) {
	        if (e.getSource() == abaDificuldade) {
	            abaDificuldadeAtiva = true;
	            removerCorpoHistorico();
	            criarCorpoDificuldade();
	            revalidate();
	            repaint();

	        } else if (e.getSource() == abaHistorico) {
	            abaDificuldadeAtiva = false;
	            removerCorpoDificuldade();
	            criarCorpoHistorico();
	            revalidate();
	            repaint();

	        }
	        atualizarCoresAbas();
	    }
	}

	// ===================== Main =====================
	public static void main(String[] args) {
		new PainelPrincipalMenuSolo();
	}

}