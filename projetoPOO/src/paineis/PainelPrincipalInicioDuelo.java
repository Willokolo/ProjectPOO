package paineis;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componentes.BarraLateral;
import componentes.BotaoGrande;
import componentes.CampoPadrao;
import componentes.LabelTexto;
import componentes.PerfilUsuario;
import componentes.QuadroDePontos;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;

public class PainelPrincipalInicioDuelo extends BarraLateral {

	// ==================== DADOS DA PARTIDA ====================

	private String palavraEscolhida = "cavalo";
	private String textoDica = "máquina usada para programar e navegar na internet";

	private ArrayList<Character> letrasPalavra = new ArrayList<Character>();
	private ArrayList<Character> letrasEncontradas = new ArrayList<Character>();
	private ArrayList<Character> letrasErradas = new ArrayList<Character>();

	private boolean jogador1Jogando = true;
	private boolean dicaAtiva = false;

	// ==================== DADOS DOS JOGADORES ====================

	private String nomeJogador1;
	private String nomeJogador2;

	private int pontosJogador1;
	private int pontosJogador2;

	private int errosJogador1;
	private int errosJogador2;

	// ==================== COMPONENTES DOS JOGADORES ====================

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

	private JTextField campoLetra;
	private JLabel botaoDarPalpite;

	private JLabel botaoDica;
	private JLabel labelDica;

	private JLabel fundoLetrasErradas;
	private JLabel tituloLetrasErradas;
	private JLabel labelLetraErrada;

	private JLabel labelResultado;

	// ==================== CONSTRUTOR ====================

	public PainelPrincipalInicioDuelo(String nomeJogador1, String nomeJogador2) {
		super("Painel Principal - Menu Solo");
		this.nomeJogador1 = nomeJogador1;
		this.nomeJogador2 = nomeJogador2;

		atualizarJogadorAtual();
		adicionarBotaoDica();
		atualizarPalavra();
		atualizarPlacar();
		adicionarCampoLetra();
		adicionarBotaoDarPalpite();
		atualizarForcas();
		adicionarQuadroLetrasErradas();
	}

	// ==================== CRIAÇÃO DOS COMPONENTES ====================

	public void adicionarBotaoDica() {
		ImageIcon original = Icones.ICONE_HISTORICO;

		Image imagem = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon redimensionado = new ImageIcon(imagem);

		if (botaoDica != null) {
			remove(botaoDica);
		}

		JLabel botaoDica = new JLabel(redimensionado);
		botaoDica.setBounds(780, 40, 40, 40);
		botaoDica.addMouseListener(new OuvinteBotaoDica());
		add(botaoDica, 0);
	}

	public void adicionarCampoLetra() {
		campoLetra = new CampoPadrao("", 675, 430, 60, 70);
		campoLetra.addKeyListener(new OuvinteLimitarCaracteres(campoLetra, 1));
		campoLetra.setFont(Fontes.FONTE_GRANDE);
		campoLetra.setBackground(Cores.COR_FUNDO_ESCURO);
		campoLetra.setHorizontalAlignment(JTextField.CENTER);
		add(campoLetra);
	}

	public void adicionarBotaoDarPalpite() {
		botaoDarPalpite = new BotaoGrande("DAR PALPITE", 590, 540, 240, 60);
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

		atualizarLetrasErradas();
	}

	// ==================== ATUALIZAÇÃO DA TELA ====================

	public void atualizarJogadorAtual() {
		if (textoJogadorAtual != null) {
			remove(textoJogadorAtual);
		}

		if (jogador1Jogando == true) {
			textoJogadorAtual = new LabelTexto(Fontes.FONTE_GRANDE, "Vez do jogador: " + nomeJogador1, 160, 50, 500, 100);
		} else {
			textoJogadorAtual = new LabelTexto(Fontes.FONTE_GRANDE, "Vez do jogador: " + nomeJogador2, 160, 50, 500, 100);
		}
		add(textoJogadorAtual);
	}

	public void atualizarDica() {
		if (labelDica != null) {
			remove(labelDica);
		}

		adicionarBotaoDica();

		if (dicaAtiva == true) {
			labelDica = new JLabel();
			labelDica.setBounds(630, 40, 190, 100);
			labelDica.setBackground(Cores.COR_FUNDO_ESCURO);
			labelDica.setForeground(Cores.COR_LETRAS);
			labelDica.setFont(Fontes.FONTE_PEQUENA_BOLD);
			labelDica.setOpaque(true);
			labelDica.setVerticalAlignment(SwingConstants.TOP);

			labelDica.setText("<html><body style ='width: 155px'>" + textoDica + "</body></html>");
			add(labelDica, 1);
		}
		revalidate();
		repaint();
	}

	public void atualizarPalavra() {
		for (JLabel label : labelsPalavra) {
			remove(label);
		}
		labelsPalavra.clear();
		letrasPalavra.clear();

		for (int i = 0; i < palavraEscolhida.length(); i++) {
			letrasPalavra.add(palavraEscolhida.charAt(i));
		}

		int x = 240;
		int y = 300;

		for (Character letra : letrasPalavra) {
			if (letra.equals('-')) {
				JLabel traco = new JLabel();
				traco.setBackground(Cores.COR_LETRAS);
				traco.setBounds(x + 7, y - 18, 15, 6);
				traco.setOpaque(true);
				add(traco, 0);
				labelsPalavra.add(traco);
				x += 36;

			} else {
				if (letrasEncontradas.contains(Character.toUpperCase(letra))) {
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

		revalidate();
		repaint();
	}

	public void atualizarLetrasErradas() {
		int y = 125;
		int largura = 40;
		int altura = 40;
		int indiceLetra = 0;

		while (indiceLetra < letrasErradas.size()) {
			int x = 855;
			for (int j = 0; j < 4 && indiceLetra < letrasErradas.size(); j++) {
				labelLetraErrada = new LabelTexto(Fontes.FONTE_LETRAS_ERRADAS, String.valueOf(letrasErradas.get(indiceLetra)), x, y, largura, altura);
				add(labelLetraErrada, 1);
				x += 70;
				indiceLetra++;
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

		// Jogador 1
		quadroPontosJogador1 = new QuadroDePontos("Pontos Solo", pontosJogador1, Icones.ICONE_HISTORICO, 230, 560);
		add(quadroPontosJogador1, 0);

		fotoJogador1 = new PerfilUsuario(Icones.ICONE_HISTORICO, this.nomeJogador1, 260, 390, 140, 140);
		add(fotoJogador1);

		textoNomeJogador1 = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeJogador1, 260, 475, 140, 140);
		add(textoNomeJogador1, 0);

		// Jogador 2
		quadroPontosJogador2 = new QuadroDePontos("Pontos Solo", pontosJogador2, Icones.ICONE_HISTORICO, 970, 560);
		add(quadroPontosJogador2, 0);

		fotoJogador2 = new PerfilUsuario(Icones.ICONE_HISTORICO, this.nomeJogador2, 1000, 390, 140, 140);
		add(fotoJogador2);

		textoNomeJogador2 = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeJogador2, 1000, 475, 140, 140);
		add(textoNomeJogador2, 0);
	}

	public void atualizarForcas() {

		ImageIcon original1 = Icones.ICONE_FORCA_ESTADO_0;
		ImageIcon original2 = Icones.ICONE_FORCA_ESTADO_0;

		if (errosJogador1 == 0) {
			original1 = Icones.ICONE_FORCA_ESTADO_0;
		} else if (errosJogador1 == 1) {
			original1 = Icones.ICONE_FORCA_ESTADO_1;
		} else if (errosJogador1 == 2) {
			original1 = Icones.ICONE_FORCA_ESTADO_2;
		} else if (errosJogador1 == 3) {
			original1 = Icones.ICONE_FORCA_ESTADO_3;
		} else if (errosJogador1 == 4) {
			original1 = Icones.ICONE_FORCA_ESTADO_4;
		} else if (errosJogador1 == 5) {
			original1 = Icones.ICONE_FORCA_ESTADO_5;
		} else if (errosJogador1 == 6) {
			original1 = Icones.ICONE_FORCA_ESTADO_6;
		}

		if (errosJogador2 == 0) {
			original2 = Icones.ICONE_FORCA_ESTADO_0;
		} else if (errosJogador2 == 1) {
			original2 = Icones.ICONE_FORCA_ESTADO_1;
		} else if (errosJogador2 == 2) {
			original2 = Icones.ICONE_FORCA_ESTADO_2;
		} else if (errosJogador2 == 3) {
			original2 = Icones.ICONE_FORCA_ESTADO_3;
		} else if (errosJogador2 == 4) {
			original2 = Icones.ICONE_FORCA_ESTADO_4;
		} else if (errosJogador2 == 5) {
			original2 = Icones.ICONE_FORCA_ESTADO_5;
		} else if (errosJogador2 == 6) {
			original2 = Icones.ICONE_FORCA_ESTADO_6;
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

	// ==================== OUVINTES ====================

	public class OuvinteBotaoDica extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (dicaAtiva == false) {
				dicaAtiva = true;
			} else if (dicaAtiva == true) {
				dicaAtiva = false;
			}
			atualizarDica();
		}
	}

	public class OuvinteBotaoDarPalpite extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			String digitado = campoLetra.getText().trim();

			if (digitado.isEmpty()) {
				return;
			}

			char letra = Character.toUpperCase(digitado.charAt(0));

			if (letrasErradas.contains(letra) || letrasEncontradas.contains(letra)) {
				campoLetra.setText("");
				return;
			}

			if (palavraEscolhida.toUpperCase().indexOf(letra) >= 0) {
				if (jogador1Jogando) {
					pontosJogador1 += 690;
				} else {
					pontosJogador2 += 690;
				}
				letrasEncontradas.add(letra);
				atualizarPlacar();
				atualizarPalavra();
			} else {
				letrasErradas.add(letra);
				atualizarLetrasErradas();

				if (jogador1Jogando) {
					errosJogador1++;
				} else {
					errosJogador2++;
				}

				jogador1Jogando = !jogador1Jogando;
			}
			atualizarForcas();
			campoLetra.setText("");
			atualizarJogadorAtual();
			revalidate();
			repaint();
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

	// ==================== MAIN ====================

	public static void main(String[] args) {
		new PainelPrincipalInicioDuelo("Wilokolo", "Raf3nix");
	}

}