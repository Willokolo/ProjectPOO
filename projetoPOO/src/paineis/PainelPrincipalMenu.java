package paineis;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import componentes.BarraLateral;
import componentes.BotaoGrande;
import componentes.BotaoMedio;
import componentes.LabelTexto;
import componentes.QuadroDePontos;
import enums.Dificuldade;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Frases;
import interfaces.Icones;
import ouvintes.OuvinteBotaoFundoVerde;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Persistencia;

public abstract class PainelPrincipalMenu extends BarraLateral{
	// ===================== Pontuação =====================
	private JLabel quadroPontuacaoSolo;
	private JLabel quadroPontuacaoDuelo;
	private int pontuacaoSolo;
	private int pontuacaoDuelo;

	// ===================== Mostrador de dificuldade (centro) =====================
	private JLabel fundoMostradorDificuldade;
	private JLabel textoModoDificuldade;
	private JLabel fraseDificuldade;
	private JLabel textoMultiplicador;
	private double valorMultiplicador = 1;

	// ===================== Botão iniciar partida =====================
	protected JLabel botaoIniciarPartida;

	// ===================== Menu lateral: fundo e abas =====================
	private JLabel fundoMenuLateral;

	private JLabel abaDificuldade;
	private JLabel abaHistorico;

	private JLabel iconeAbaDificuldade;
	private JLabel iconeAbaHistorico;

	private JLabel textoAbaDificuldade;
	private JLabel textoAbaHistorico;

	// ===================== Menu lateral: corpo da dificuldade =====================
	private JLabel tituloDificuldade;
	private JLabel botaoFacil;
	private JLabel botaoMedio;
	private JLabel botaoDificil;
	private JLabel botaoAleatorio;

	protected Dificuldade dificuldadeSelecionada = Dificuldade.ALEATORIO;

	// ===================== Menu lateral: corpo do histórico =====================
	private JLabel tituloHistorico;

	private JLabel textoNPartida;
	private JLabel textoData;
	private JLabel textoModo;
	private JLabel textoJogadores;
	private JLabel textoDificuldade;
	private JLabel textoTentativasErros;
	private JLabel textoTentativasAcertos;
	private JLabel textoPalavra;
	private JLabel textoVencedor;
	private JLabel textoResultado;
	private JLabel textoResultado1;
	private JLabel textoResultado2;

	// ===================== Estado =====================
	private boolean abaDificuldadeAtiva = true;

	// ===================== Construtores =====================
	public PainelPrincipalMenu(String titulo, CentralDeInformacoes central, Persistencia persistencia, Jogador jogador) {
	    super(titulo, central, persistencia, jogador);
	    
	    pontuacaoSolo = jogador.getPontosSolo();
	    pontuacaoDuelo = jogador.getPontosDuelo();
	    
	    criarMostradorDificuldade();
	    criarQuadrosPontuacao();
	    criarBotaoIniciarPartida();
	    criarMenuLateral();

	    setVisible(true);
	}

	// ===================== Criação: área central =====================
	private void criarQuadrosPontuacao() {
		quadroPontuacaoSolo = new QuadroDePontos("Pontos Solo", pontuacaoSolo, Icones.ICONE_HISTORICO, 300, 40);
		add(quadroPontuacaoSolo, 0);

		quadroPontuacaoDuelo = new QuadroDePontos("Pontos Duelo", pontuacaoDuelo, Icones.ICONE_HISTORICO, 600, 40);
		add(quadroPontuacaoDuelo, 0);
	}

	private void criarMostradorDificuldade() {
		fundoMostradorDificuldade = new JLabel();
		fundoMostradorDificuldade.setBackground(Cores.COR_FUNDO_ESCURO);
		fundoMostradorDificuldade.setBounds(325, 370, 450, 145);
		fundoMostradorDificuldade.setOpaque(true);

		textoModoDificuldade = new LabelTexto(Fontes.FONTE_MEDIA, "", 350, 390, 400, 30);
		fraseDificuldade     = new LabelTexto(Fontes.FONTE_MEDIA_NO_BOLD, "", 325, 425, 450, 30);
		textoMultiplicador   = new LabelTexto(Fontes.FONTE_MEDIA, "", 350, 460, 400, 30);

		atualizarMostradorDificuldade();
		add(textoMultiplicador, 0);
		add(fraseDificuldade, 0);
		add(textoModoDificuldade, 0);
		add(fundoMostradorDificuldade);
	}

	private void criarBotaoIniciarPartida() {
		botaoIniciarPartida = new BotaoGrande("INICIAR PARTIDA", 400, 550, 300, 80);
		add(botaoIniciarPartida);
	}

	// ===================== Criação: menu lateral =====================
	private void criarMenuLateral() {
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

	private void criarIconesAbas() {
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

	private void criarTextosAbas() {
		textoAbaDificuldade = new BotaoMedio("DIFICULDADE", 900, 60, 150, 118, true);

		textoAbaHistorico = new BotaoMedio("HISTÓRICO", 1034, 60, 150, 118, true);

		add(textoAbaDificuldade);
	    add(textoAbaHistorico);
	}

	// ===================== Criação: corpos do menu lateral =====================
	private void criarCorpoDificuldade() {
		tituloDificuldade = new LabelTexto(Fontes.FONTE_MEDIA, "DIFICULDADE", 922, 160, 200, 50);
		add(tituloDificuldade, 0);

		botaoFacil = new BotaoGrande("FACIL", 925, 230, 200, 80);
		botaoFacil.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoFacil, 0);

		botaoMedio = new BotaoGrande("NORMAL", 925, 330, 200, 80);
		botaoMedio.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoMedio, 0);

		botaoDificil = new BotaoGrande("DIFICIL", 925, 430, 200, 80);
		botaoDificil.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoDificil, 0);

		botaoAleatorio = new BotaoGrande("ALEATORIO", 925, 530, 200, 80);
		botaoAleatorio.addMouseListener(new OuvinteBotoesDificuldade());
		add(botaoAleatorio, 0);
	}

 	private void criarCorpoHistorico() {
		final int X_HISTORICO = 910;
		final int LARGURA_HISTORICO = 235;

		tituloHistorico = new LabelTexto(Fontes.FONTE_MEDIA, "HISTORICO", 922, 130, 200, 50);
		add(tituloHistorico, 0);

		removerCorpoDificuldade();

		textoNPartida = new LabelTexto(Fontes.FONTE_PEQUENA_BOLD, "Partida N° " + "1", X_HISTORICO, 170, LARGURA_HISTORICO, 20);
		add(textoNPartida, 0);

		textoData = new LabelTexto(Fontes.FONTE_HISTORICO, "Data e Hora: " + "19/11/2026 22:13:22", X_HISTORICO, 185, LARGURA_HISTORICO, 20);
		add(textoData, 0);

		textoModo = new LabelTexto(Fontes.FONTE_HISTORICO, "Modo: " + "DUELO", X_HISTORICO, 200, LARGURA_HISTORICO, 20);
		add(textoModo, 0);

		textoJogadores = new LabelTexto(Fontes.FONTE_HISTORICO, "Jogadores: " + "Willokolo e Raf3nix", X_HISTORICO, 215, LARGURA_HISTORICO, 20);
		add(textoJogadores, 0);

		textoDificuldade = new LabelTexto(Fontes.FONTE_HISTORICO, "Dificuldade: " + "Brutal", X_HISTORICO, 230, LARGURA_HISTORICO, 20);
		add(textoDificuldade, 0);

		textoTentativasErros = new LabelTexto(Fontes.FONTE_HISTORICO, "Erros: " + "Z R B X", X_HISTORICO, 245, LARGURA_HISTORICO, 20);
		add(textoTentativasErros, 0);

		textoTentativasAcertos = new LabelTexto(Fontes.FONTE_HISTORICO, "Acertos: " + "A T I L S O C", X_HISTORICO, 260, LARGURA_HISTORICO, 20);
		add(textoTentativasAcertos, 0);

		textoVencedor = new LabelTexto(Fontes.FONTE_HISTORICO, "Vencedor: " + "Willokolo", X_HISTORICO, 275, LARGURA_HISTORICO, 20);
		add(textoVencedor, 0);

		textoResultado = new LabelTexto(Fontes.FONTE_HISTORICO, "Resultado: ", X_HISTORICO, 290, LARGURA_HISTORICO, 20);
		add(textoResultado, 0);

		textoResultado1 = new LabelTexto(Fontes.FONTE_HISTORICO, "Willokolo: 100000000", X_HISTORICO, 305, LARGURA_HISTORICO, 20);
		add(textoResultado1, 0);

		textoResultado2 = new LabelTexto(Fontes.FONTE_HISTORICO, "Raf3nix: 67", X_HISTORICO, 320, LARGURA_HISTORICO, 20);
		add(textoResultado2, 0);

		JLabel[] linhasHistorico = {
			textoNPartida, textoData, textoModo, textoJogadores, textoDificuldade,
			textoTentativasErros, textoTentativasAcertos, textoVencedor,
			textoResultado, textoResultado1, textoResultado2
		};

		for (JLabel linha : linhasHistorico) {
			linha.setHorizontalAlignment(SwingConstants.LEFT);
		}
	}

	// ===================== Atualização visual =====================
	private void atualizarCoresAbas() {
	    JLabel abaAtiva   = abaDificuldadeAtiva ? abaDificuldade : abaHistorico;
	    JLabel abaInativa = abaDificuldadeAtiva ? abaHistorico   : abaDificuldade;

	    abaAtiva.setBackground(Cores.COR_FUNDO_ESCURO);

	    abaInativa.setBackground(Cores.MENU_DESATIVADO);

	    revalidate();
	    repaint();
	}

	private void atualizarMostradorDificuldade() {
		if (dificuldadeSelecionada == Dificuldade.FACIL) {
			textoModoDificuldade.setText("MODO FACIL");
			fraseDificuldade.setText(Frases.FRASE_FACIL);
			valorMultiplicador = 1;
			textoMultiplicador.setText("MULTIPLICADOR: " + String.format("%.2f", valorMultiplicador) + "x");

		} else if (dificuldadeSelecionada == Dificuldade.NORMAL) {
			textoModoDificuldade.setText("MODO NORMAL");
			fraseDificuldade.setText(Frases.FRASE_MEDIO);
			valorMultiplicador = 1.5;
			textoMultiplicador.setText("MULTIPLICADOR: " + String.format("%.2f", valorMultiplicador) + "x");

		} else if (dificuldadeSelecionada == Dificuldade.DIFICIL) {
			textoModoDificuldade.setText("MODO DIFICIL");
			fraseDificuldade.setText(Frases.FRASE_DIFICIL);
			valorMultiplicador = 2;
			textoMultiplicador.setText("MULTIPLICADOR: " + String.format("%.2f", valorMultiplicador) + "x");

		} else if (dificuldadeSelecionada == Dificuldade.ALEATORIO) {
			textoModoDificuldade.setText("MODO ALEATORIO");
			fraseDificuldade.setText(Frases.FRASE_ALEATORIO);
			textoMultiplicador.setText("MULTIPLICADOR: ALEATORIO" );

		}
	}

	// ===================== Remoção dos corpos =====================
	private void removerSeExistir(JLabel label) {
		if (label != null) {
			remove(label);
		}
	}

	private void removerCorpoDificuldade() {
		removerSeExistir(tituloDificuldade);
		removerSeExistir(botaoFacil);
		removerSeExistir(botaoDificil);
		removerSeExistir(botaoMedio);
		removerSeExistir(botaoAleatorio);
	}

	private void removerCorpoHistorico() {
		removerSeExistir(tituloHistorico);
		removerSeExistir(textoNPartida);
		removerSeExistir(textoData);
		removerSeExistir(textoModo);
		removerSeExistir(textoJogadores);
		removerSeExistir(textoDificuldade);
		removerSeExistir(textoTentativasErros);
		removerSeExistir(textoTentativasAcertos);
		removerSeExistir(textoPalavra);
		removerSeExistir(textoVencedor);
		removerSeExistir(textoResultado);
		removerSeExistir(textoResultado1);
		removerSeExistir(textoResultado2);
	}

	// ===================== Ouvintes =====================
	private class OuvinteBotoesDificuldade extends OuvinteBotaoFundoVerde {
		
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == botaoFacil) {
	        	dificuldadeSelecionada = Dificuldade.FACIL;
	        	botaoFacil.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
	        	atualizarMostradorDificuldade();

	        } else if (e.getSource() == botaoMedio) {
	        	dificuldadeSelecionada = Dificuldade.NORMAL;
	        	botaoMedio.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
	        	atualizarMostradorDificuldade();

	        } else if (e.getSource() == botaoDificil) {
	        	dificuldadeSelecionada = Dificuldade.DIFICIL;
	        	botaoDificil.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
	        	atualizarMostradorDificuldade();

	        } else if (e.getSource() == botaoAleatorio) {
	        	dificuldadeSelecionada = Dificuldade.ALEATORIO;
	        	botaoAleatorio.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
	        	atualizarMostradorDificuldade();
	        }
			
		}
	}

	private class OuvinteAbas extends MouseAdapter {
	    @Override
	    public void mousePressed(MouseEvent e) {
	        if (e.getSource() == abaDificuldade && !abaDificuldadeAtiva) {
	            abaDificuldadeAtiva = true;
	            removerCorpoHistorico();
	            criarCorpoDificuldade();
	            revalidate();
	            repaint();

	        } else if (e.getSource() == abaHistorico && abaDificuldadeAtiva) {
	            abaDificuldadeAtiva = false;
	            removerCorpoDificuldade();
	            criarCorpoHistorico();
	            revalidate();
	            repaint();
	        }
	        atualizarCoresAbas();
	    }
	}
}

