package paineis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import componentes.BarraLateral;
import componentes.BotaoGrande;
import componentes.CampoPadrao;
import componentes.LabelTexto;
import componentes.PerfilUsuario;
import componentes.QuadroDePontos;
import enums.Dificuldade;
import enums.Resultado;
import exceptions.NaoHaPalavrasCompativeisException;
import exceptions.RetornoVazioException;
import exceptions.TentativaException;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.JogoDaForca;
import projeto.Palavra;
import projeto.PartidaDuelo;
import projeto.Persistencia;
import projeto.Tentativa;

public class PainelPrincipalInicioDuelo extends BarraLateral {

	// ==================== DADOS DA PARTIDA ====================
	
	private PartidaDuelo partida;
	private JogoDaForca rodadaAtual;
	private int indiceRodada = 0;
	private String palavraEscolhida;
	private String textoDica;

	// ==================== COMPONENTES DOS JOGADORES ====================
	private Jogador jogador2;
	
	private JLabel textoJogadorAtual;

	private JLabel fotoJogador1;
	private JLabel fotoJogador2;

	private JLabel textoNomeJogador1;
	private JLabel textoNomeJogador2;

	private JLabel quadroPontosJogador1;
	private JLabel quadroPontosJogador2;

	private JLabel forcaJogador1;
	private JLabel forcaJogador2;

	// ==================== COMPONENTES DO JOGO ====================

	private List<JLabel> labelsPalavra = new ArrayList<>();
	private List<JLabel> labelsLetrasErradas = new ArrayList<>();
	
	private JLabel labelDificuldade;
	private JLabel labelRodada;
	
	private JTextField campoLetra;
	private JLabel botaoDarPalpite;

	private JLabel botaoDica;
	private JLabel labelDica;

	private JLabel fundoLetrasErradas;
	private JLabel tituloLetrasErradas;

	// ==================== CONSTRUTOR ====================

	public PainelPrincipalInicioDuelo(CentralDeInformacoes central, Persistencia persistencia, Jogador jogador1, Jogador jogador2, Palavra palavra1, Palavra palavra2) throws NaoHaPalavrasCompativeisException {
		super("Painel Principal - Menu Solo", central, persistencia, jogador1);
		this.jogador2 = jogador2;
		
		iniciarPartida(palavra1, palavra2);
		adicionarBotaoDica();
		atualizarTela();
		adicionarCampoLetra();
		adicionarBotaoDarPalpite();
		adicionarQuadroLetrasErradas();
	}
	
	private void iniciarPartida(Palavra palavra1, Palavra palavra2) {
		partida = new PartidaDuelo(jogador, jogador2, palavra1, palavra2);
		jogador.addPalavraVista(palavra1);
		jogador2.addPalavraVista(palavra1);
		jogador.addPalavraVista(palavra2);
		jogador2.addPalavraVista(palavra2);
		
		Dificuldade dificuldade = palavra1.getDificuldade();
		String texto = "";
		
		switch (dificuldade) {
		case FACIL:
			texto = "FÁCIL";
			break;
			
		case NORMAL:
			texto = "NORMAL";
			break;
			
		case DIFICIL:
			texto = "DIFÍCIL";
			break;
		}
		
		labelDificuldade = new LabelTexto(Fontes.FONTE_GRANDE, "Dificuldade: " + texto, 500, 600, 400, 50);
		add(labelDificuldade);
		
		central.addPartida(partida);
		persistencia.salvarCentral(central);
		
		iniciarRodada();
	}
	
	private void iniciarRodada() {
		rodadaAtual = partida.getRodadas().get(indiceRodada);
		palavraEscolhida = rodadaAtual.getPalavra().getPalavra();
		textoDica = rodadaAtual.getDica();
		
		if (labelRodada != null)
			remove(labelRodada);
		
		labelRodada = new LabelTexto(Fontes.FONTE_GRANDE, "Rodada " + (indiceRodada + 1), 250, 25, 300, 50);
		labelRodada.setHorizontalAlignment(JLabel.LEFT);
		add(labelRodada);
		
		atualizarTela();
	}
	
	// ==================== CRIAÇÃO DOS COMPONENTES ====================

	public void adicionarBotaoDica() {
		ImageIcon original = Icones.ICONE_HISTORICO;

		Image imagem = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon redimensionado = new ImageIcon(imagem);
		
		botaoDica = new JLabel(redimensionado);
		botaoDica.setBounds(780, 50, 40, 40);
		botaoDica.addMouseListener(new OuvinteBotaoDica());
		add(botaoDica);
		
		labelDica = new LabelTexto(Fontes.FONTE_MEDIA, "Dica", 775, 95, 50, 15);
		add(labelDica);
	}

	public void adicionarCampoLetra() {
		campoLetra = new CampoPadrao("", 665, 430, 60, 70);
		campoLetra.addKeyListener(new OuvinteLimitarCaracteres(campoLetra, 1));
		campoLetra.setFont(Fontes.FONTE_GRANDE);
		campoLetra.setBackground(Cores.COR_FUNDO_ESCURO);
		campoLetra.setHorizontalAlignment(JTextField.CENTER);
		add(campoLetra);
	}

	public void adicionarBotaoDarPalpite() {
		botaoDarPalpite = new BotaoGrande("DAR PALPITE", 580, 540, 240, 60);
		botaoDarPalpite.addMouseListener(new OuvinteBotaoDarPalpite());
		add(botaoDarPalpite);
	}

	public void adicionarQuadroLetrasErradas() {
		fundoLetrasErradas = new JLabel();
		fundoLetrasErradas.setBackground(Cores.COR_FUNDO_ESCURO);
		fundoLetrasErradas.setBounds(835, 40, 325, 320);
		fundoLetrasErradas.setOpaque(true);

		tituloLetrasErradas = new LabelTexto(Fontes.FONTE_MEDIA, "LETRAS ERRADAS", 855, 55, 285, 50);
		tituloLetrasErradas.setBackground(Cores.COR_PRIMARIA);
		tituloLetrasErradas.setOpaque(true);

		add(tituloLetrasErradas, 0);
		add(fundoLetrasErradas, 1);

		atualizarTela();
	}

	// ==================== ATUALIZAÇÃO DA TELA ====================

	public void atualizarJogadorAtual() {
		if (textoJogadorAtual != null) {
			remove(textoJogadorAtual);
		}
		
		textoJogadorAtual = new LabelTexto(Fontes.FONTE_GRANDE, "Vez do jogador: " + rodadaAtual.getJogadorDaVez().getNome(), 250, 75, 500, 50);
		textoJogadorAtual.setHorizontalAlignment(JLabel.LEFT);
		add(textoJogadorAtual);
	}

	public void atualizarPalavra() {
		for (JLabel label : labelsPalavra)
			remove(label);
		labelsPalavra.clear();
		
		String gabarito = rodadaAtual.getGabarito();
		int x = 240;
		int y = 300;

		for (int i = 0; i < palavraEscolhida.length(); i++) {
			char letra = palavraEscolhida.charAt(i);
			
			if (letra == '-') {
				JLabel traco = new JLabel();
				traco.setBackground(Cores.COR_LETRAS);
				traco.setBounds(x + 7, y - 18, 15, 6);
				traco.setOpaque(true);
				add(traco, 0);
				labelsPalavra.add(traco);
				x += 36;
				
				continue;
			}
			
			if (gabarito.charAt(i) != '?') {
				JLabel letraCorreta = new LabelTexto(Fontes.FONTE_GRANDE, String.valueOf(Character.toUpperCase(letra)), x + 2, y - 35, 30, 30);
				add(letraCorreta, 0);
				labelsPalavra.add(letraCorreta);
			}
			
			JLabel sublinhado = new JLabel();
			sublinhado.setBackground(Cores.COR_LETRAS);
			sublinhado.setBounds(x, y, 30, 6);
			sublinhado.setOpaque(true);
			add(sublinhado, 0);
			labelsPalavra.add(sublinhado);
			x += 36;
		}
	}

	public void atualizarLetrasErradas() {
		for (JLabel l: labelsLetrasErradas)
			remove(l);
		labelsLetrasErradas.clear();
		
		ArrayList<Character> erradas = letrasErradas();
		int y = 125;
		int indice = 0;
		
		while (indice < erradas.size()) {
			int x = 855;
			for (int i = 0; i < 4 && indice < erradas.size(); i++, indice++) {
				JLabel letra = new LabelTexto(Fontes.FONTE_LETRAS_ERRADAS, String.valueOf(erradas.get(indice)), x, y, 40, 40);
				add(letra, 1);
				
				labelsLetrasErradas.add(letra);
				x += 70;
			}
			y += 60;
		}
		
		revalidate();
		repaint();

	}

	public void atualizarPlacar() {
		if (quadroPontosJogador1 != null) {
			remove(quadroPontosJogador1);
			remove(quadroPontosJogador2);
		}
		
		String nomeJogador1 = jogador.getNome();
		String nomeJogador2 = jogador2.getNome();
		
		// Jogador 1
		quadroPontosJogador1 = new QuadroDePontos("Pontos Duelo", jogador.getPontosDuelo(), Icones.ICONE_HISTORICO, 230, 560);
		add(quadroPontosJogador1, 0);

		fotoJogador1 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeJogador1, 260, 390, 140, 140);
		add(fotoJogador1);

		textoNomeJogador1 = new LabelTexto(Fontes.FONTE_MEDIA, nomeJogador1, 260, 475, 140, 140);
		add(textoNomeJogador1, 0);

		// Jogador 2
		quadroPontosJogador2 = new QuadroDePontos("Pontos Duelo", jogador2.getPontosDuelo(), Icones.ICONE_HISTORICO, 970, 560);
		add(quadroPontosJogador2, 0);

		fotoJogador2 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeJogador2, 1000, 390, 140, 140);
		add(fotoJogador2);

		textoNomeJogador2 = new LabelTexto(Fontes.FONTE_MEDIA, nomeJogador2, 1000, 475, 140, 140);
		add(textoNomeJogador2, 0);
	}

	public void atualizarForcas() {

		ImageIcon original1 = Icones.ICONE_FORCA_ESTADO_0;
		ImageIcon original2 = Icones.ICONE_FORCA_ESTADO_0;
		
		switch(rodadaAtual.getNumErros(jogador)) {
		case 1:
			original1 = Icones.ICONE_FORCA_ESTADO_1;
			break;
			
		case 2:
			original1 = Icones.ICONE_FORCA_ESTADO_2;
			break;
			
		case 3:
			original1 = Icones.ICONE_FORCA_ESTADO_3;
			break;
			
		case 4:
			original1 = Icones.ICONE_FORCA_ESTADO_4;
			break;
			
		case 5:
			original1 = Icones.ICONE_FORCA_ESTADO_5;
			break;
			
		case 6:
			original1 = Icones.ICONE_FORCA_ESTADO_6;
			break;
		}
		
		switch(rodadaAtual.getNumErros(jogador2)) {
		case 1:
			original2 = Icones.ICONE_FORCA_ESTADO_1;
			break;
			
		case 2:
			original2 = Icones.ICONE_FORCA_ESTADO_2;
			break;
			
		case 3:
			original2 = Icones.ICONE_FORCA_ESTADO_3;
			break;
			
		case 4:
			original2 = Icones.ICONE_FORCA_ESTADO_4;
			break;
			
		case 5:
			original2 = Icones.ICONE_FORCA_ESTADO_5;
			break;
			
		case 6:
			original2 = Icones.ICONE_FORCA_ESTADO_6;
			break;
		}

		Image imagem;
		ImageIcon redimensionado;

		// Forca do jogador 1
		imagem = original1.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		redimensionado = new ImageIcon(imagem);   // garante que a imagem terminou de carregar

		// espelhar na horizontal
		int largura = redimensionado.getIconWidth();
		int altura = redimensionado.getIconHeight();

		BufferedImage espelhada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = espelhada.createGraphics();
		g.drawImage(imagem, largura, 0, -largura, altura, null);
		g.dispose();

		redimensionado = new ImageIcon(espelhada);

		if (forcaJogador1 != null) {
			remove(forcaJogador1);
		}

		forcaJogador1 = new JLabel(redimensionado);
		forcaJogador1.setBounds(370, 400, 250, 250);
		add(forcaJogador1);

		// Forca do jogador 2
		imagem = original2.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		redimensionado = new ImageIcon(imagem);

		if (forcaJogador2 != null) {
			remove(forcaJogador2);
		}

		forcaJogador2 = new JLabel(redimensionado);
		forcaJogador2.setBounds(780, 400, 250, 250);
		add(forcaJogador2);
	}
	
	private ArrayList<Character> letrasErradas(){
		ArrayList<Character> erradas = new ArrayList<Character>();
		for (Tentativa t: rodadaAtual.getTentativas()) {
			if(!t.getAcertou()) {
				erradas.add(Character.toUpperCase(t.getLetra()));
			}
		}
		return erradas;
	}
	
	private void encerrarRodada() {
		JOptionPane.showMessageDialog(null, textoFimRodada(), "Fim da rodada", JOptionPane.INFORMATION_MESSAGE);
		
		if (indiceRodada == 0) {
			indiceRodada = 1;
			iniciarRodada();
		} else
			encerrarPartida();
	}
	
	private void encerrarPartida() {
		JOptionPane.showMessageDialog(null, textoFimPartida(), "Fim da partida", JOptionPane.INFORMATION_MESSAGE);
		
		Resultado resultado = Resultado.DERROTA;
		try {
			Jogador vencedor = partida.getVencedor();
			
			if (vencedor.equals(jogador))
				resultado = Resultado.VITORIA;
		} catch (RetornoVazioException e) {
			resultado = Resultado.EMPATE;
		} finally {
			for (Jogador j : partida.getJogadores())
				j.addPontosDuelo(partida.getPontos(j));
			persistencia.salvarCentral(central);
		}
		
		dispose();
		new ResultadoPartida(resultado, central, persistencia, jogador, partida.getDificuldade(), PainelPrincipalInicioDuelo.this);
	}
	
	private String textoFimPartida() {
		try {
			return partida.getVencedor() + " venceu a partida!";
		} catch (RetornoVazioException e) {
			return e.getMessage();
		}
	}
	
	private String textoFimRodada() {
		String msg = "";
		
		try {
			msg = rodadaAtual.getVencedor() + " venceu a rodada!";
		} catch (RetornoVazioException e) {
			msg = e.getMessage();
		}
		
		return msg + "\nA palavra era: " + palavraEscolhida;
	}
	
	private void atualizarTela() {
		atualizarJogadorAtual();
		atualizarPalavra();
		atualizarPlacar();
		atualizarForcas();
		atualizarLetrasErradas();
		
		repaint();
	}
	
	// ==================== OUVINTES ====================

	public class OuvinteBotaoDica extends OuvinteBotaoSemFundo {
		@Override
		public void mouseClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(null, textoDica, "Dica", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public class OuvinteBotaoDarPalpite extends OuvinteBotaoFundoVerde {
		@Override
		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String digitado = campoLetra.getText().trim();

			if (digitado.isEmpty()) {
				return;
			}

			char letra = Character.toUpperCase(digitado.charAt(0));
			Jogador jogadorDaVez = rodadaAtual.getJogadorDaVez();
			Tentativa tentativa = new Tentativa(jogadorDaVez, letra);
			
			try {
				rodadaAtual.testarLetra(tentativa);

			} catch (TentativaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			campoLetra.setText("");
			atualizarTela();
			
			if (rodadaAtual.terminou()) 
				encerrarRodada();
			
			j.setBackground(Cores.COR_BOTAO_HOVER);
		}
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
}