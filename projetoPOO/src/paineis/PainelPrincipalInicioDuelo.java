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
import enums.Resultado;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import paineis.PainelPrincipalInicioSolo.OuvinteBotaoDarPalpite;
import paineis.PainelPrincipalInicioSolo.OuvinteBotaoDica;
import paineis.PainelPrincipalInicioSolo.OuvinteLimitarCaracteres;

public class PainelPrincipalInicioDuelo extends BarraLateral {
	
	private JLabel fundoLetrasErradas;
	private JLabel textoLetrasErradas;
	
	private JLabel quadroPontosJogador1;
	private JLabel quadroPontosJogador2;
	
	private JLabel fotoUsuario1;
	private JLabel fotoUsuario2;
	private JLabel textoNomeUsuario1;
	private JLabel textoNomeUsuario2;
	private String nomeUsuario1;
	private String nomeUsuario2;
	
	private JLabel botaoDarPalpite;
	
	private String palavraEscolhida = "cavalo";
	
	private ArrayList<Character> arrayLetrasPalavraEscolhida = new ArrayList<Character>();

	private ArrayList<Character> letrasErradas = new ArrayList<Character>();
	
	private int errosJogador1;

	private int errosJogador2;

	private ArrayList<Character> letrasEncontradas = new ArrayList<Character>();
	
	private List<JLabel> labelsPalavra = new ArrayList<>();
	
	private JLabel labelForca1;
	private JLabel labelForca2;
	
	private JLabel labelLetraErrada;
	
	private int pontosJogador1;
	private int pontosJogador2;
	
	private JLabel jogadorAtual;
	private boolean jogador1Jogando = true;
	
	private JTextField inserirLetra;
	
	private JLabel labelResultado;
	
	private JLabel botaoDica;
	
	private JLabel labelDica;
	
	private String textoDica = "máquina usada para programar e navegar na internet";
	
	private boolean dicaAtiva = false;
	
	public PainelPrincipalInicioDuelo(String nomeUsuario1, String nomeUsuario2) {
		super("Painel Principal - Menu Solo");
		this.nomeUsuario1 = nomeUsuario1;
		this.nomeUsuario2 = nomeUsuario2;
			
		atualizarJogadorAtual();
		
		adicionarBotaoDica();
		
		adicionarLabelsPalavra();
		
		adicionarPontosUsuario();
		
		adicionarCampoEscolherLetra();
		
		adicionarBotaoDarPalpite();
		
		adicionarIconeForca();
	
		criarLetrasErradas();

	}
	
	public void adicionarIconeForca() {
		
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

		if (labelForca1 != null) {
			remove(labelForca1);
		}

		labelForca1 = new JLabel(redimensionado);
		labelForca1.setBounds(370, 400, 250, 250);
		add(labelForca1);
			
		imagem = original2.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		redimensionado = new ImageIcon(imagem);
		
		if (labelForca2 != null) {
			remove(labelForca2);
		}
		
		labelForca2 = new JLabel(redimensionado);
		labelForca2.setBounds(780, 400, 250, 250);
		add(labelForca2);
		
	}
	
	public void adicionarBotaoDarPalpite() {
		botaoDarPalpite = new BotaoGrande("DAR PALPITE", 590, 540, 240, 60);
		botaoDarPalpite.addMouseListener(new OuvinteBotaoDarPalpite());		
		add(botaoDarPalpite);
	}
	
	public void atualizarJogadorAtual() {
		if (jogadorAtual != null) {
			remove(jogadorAtual);
		}
		
		if (jogador1Jogando == true) {
			jogadorAtual = new LabelTexto(Fontes.FONTE_GRANDE, "Vez do jogador: " + nomeUsuario1, 160, 50, 500, 100);
		} else {
			jogadorAtual = new LabelTexto(Fontes.FONTE_GRANDE, "Vez do jogador: " + nomeUsuario2, 160, 50, 500, 100);
		}
		add(jogadorAtual);
	}
	
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
	        String digitado = inserirLetra.getText().trim();

	        if (digitado.isEmpty()) {
	            return;
	        }

	        char letra = Character.toUpperCase(digitado.charAt(0));

	        if (letrasErradas.contains(letra) || letrasEncontradas.contains(letra)) {
	        	inserirLetra.setText("");
	            return; 
	        }

	        if (palavraEscolhida.toUpperCase().indexOf(letra) >= 0) {
	        	if (jogador1Jogando) {	        		
	        		pontosJogador1 +=  690;
	        	} else {
	        		pontosJogador2 += 690;	        		
	        	}
	            letrasEncontradas.add(letra);
	            adicionarPontosUsuario();
	            adicionarLabelsPalavra();
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
	        adicionarIconeForca();
	        inserirLetra.setText("");
	        atualizarJogadorAtual();
	        revalidate();
	        repaint();
	    }
	}

	public void adicionarLabelsPalavra() {
		for (JLabel label : labelsPalavra) {
			remove(label);
		}
		labelsPalavra.clear();
		arrayLetrasPalavraEscolhida.clear();

		for (int i = 0; i < palavraEscolhida.length(); i++) {
			arrayLetrasPalavraEscolhida.add(palavraEscolhida.charAt(i));
		}

		int x = 240;
		int y = 300;

		for (Character letra : arrayLetrasPalavraEscolhida) {
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
	
	public void adicionarCampoEscolherLetra() {
		inserirLetra = new CampoPadrao("", 675, 430, 60, 70);
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
		if (quadroPontosJogador1 != null) {
			remove(quadroPontosJogador1);
			remove(quadroPontosJogador2);
		}
		
		quadroPontosJogador1 = new QuadroDePontos("Pontos Solo", pontosJogador1, Icones.ICONE_HISTORICO, 230, 560);
		add(quadroPontosJogador1, 0);
		
		fotoUsuario1 = new PerfilUsuario(Icones.ICONE_HISTORICO, this.nomeUsuario1, 260, 390, 140, 140);
		add(fotoUsuario1);
		
		textoNomeUsuario1 = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeUsuario1, 260, 475, 140, 140);
		add(textoNomeUsuario1, 0);
		
		quadroPontosJogador2 = new QuadroDePontos("Pontos Solo", pontosJogador2, Icones.ICONE_HISTORICO, 970, 560);
		add(quadroPontosJogador2, 0);
		
		fotoUsuario2 = new PerfilUsuario(Icones.ICONE_HISTORICO, this.nomeUsuario2, 1000, 390, 140, 140);
		add(fotoUsuario2);
		
		textoNomeUsuario2 = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeUsuario2, 1000, 475, 140, 140);
		add(textoNomeUsuario2, 0);
	}
	
	public void atualizarLetrasErradas() {
		int y = 125;
		int larg = 40;
		int alt = 40;
		int indexLetra = 0;

		while (indexLetra < letrasErradas.size()) {
			int x = 855;
			for (int j = 0; j < 4 && indexLetra < letrasErradas.size(); j++) {
				labelLetraErrada = new LabelTexto(Fontes.FONTE_LETRAS_ERRADAS, String.valueOf(letrasErradas.get(indexLetra)), x, y, larg, alt);
				add(labelLetraErrada, 1);
				x += 70;
				indexLetra++;
			}
			y += 60;
		}

		revalidate();
		repaint();
	}
	
	public void criarLetrasErradas() {
		fundoLetrasErradas = new JLabel();
		fundoLetrasErradas.setBackground(Cores.COR_FUNDO_ESCURO);
		fundoLetrasErradas.setBounds(835, 40, 325, 320);
		fundoLetrasErradas.setOpaque(true);
		
		textoLetrasErradas = new LabelTexto(Fontes.FONTE_MEDIA, "LETRAS ERRADAS", 855, 55, 285, 50);
		textoLetrasErradas.setBackground(Cores.COR_PRIMARIA);
		textoLetrasErradas.setOpaque(true);
		
		add(textoLetrasErradas, 0);
		add(fundoLetrasErradas, 1);

		atualizarLetrasErradas();
	}
	
	public static void main(String[] args) {
		new PainelPrincipalInicioDuelo("Wilokolo", "Raf3nix");
	}

}
