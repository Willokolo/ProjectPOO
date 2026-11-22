package paineis;

import java.awt.event.KeyAdapter;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
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
import paineis.PainelInserirCodigoRedefinirSenha.OuvinteLimitarCaracteres;

public class PainelPrincipalInicioSolo extends BarraLateral {

	private JLabel fundoLetrasErradas;
	private JLabel textoLetrasErradas;
	
	private JLabel quadroPontos;
	
	private JLabel fotoUsuario;
	private JLabel textoNomeUsuario;
	private String nomeUsuario;
	
	private JLabel botaoDarPalpite;
	
	private String palavraEscolhida = "cavalo";
	
	private ArrayList<Character> arrayLetrasPalavraEscolhida = new ArrayList<Character>();

	private ArrayList<Character> letrasErradas = new ArrayList<Character>();

	private ArrayList<Character> letrasEncontradas = new ArrayList<Character>();
	
	private List<JLabel> labelsPalavra = new ArrayList<>();
	
	private JLabel labelForca;
	
	private JLabel labelLetraErrada;
	
	private int pontos;
	
	private JTextField inserirLetra;
	
	public PainelPrincipalInicioSolo(String nomeUsuario) {
		super("Painel Principal - Menu Solo");
		this.nomeUsuario = nomeUsuario;
		
		adicionarBotaoDica();
		
		adicionarLabelsPalavra();
		
		adicionarPontosUsuario();
		
		adicionarCampoEscolherLetra();
		
		adicionarBotaoDarPalpite();
		
		adicionarIconeForca();
		
		criarLetrasErradas();
	
	}
	
	public void adicionarIconeForca() {
		
		ImageIcon original = Icones.ICONE_FORCA_ESTADO_0;
		
		if (letrasErradas.size() == 0) {
			original = Icones.ICONE_FORCA_ESTADO_0;

		} else if (letrasErradas.size() == 1) {
			original = Icones.ICONE_FORCA_ESTADO_1;

		} else if (letrasErradas.size() == 2) {
			original = Icones.ICONE_FORCA_ESTADO_2;

		} else if (letrasErradas.size() == 3) {
			original = Icones.ICONE_FORCA_ESTADO_3;

		} else if (letrasErradas.size() == 4) {
			original = Icones.ICONE_FORCA_ESTADO_4;

		} else if (letrasErradas.size() == 5) {
			original = Icones.ICONE_FORCA_ESTADO_5;

		} else if (letrasErradas.size() == 6) {
			original = Icones.ICONE_FORCA_ESTADO_6;

		}

		Image imagem = original.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
		ImageIcon redimensionado = new ImageIcon(imagem);
		
		if (labelForca != null) {
			remove(labelForca);
		}
		
		labelForca = new JLabel(redimensionado);
		labelForca.setBounds(900, 400, 250, 250);
		add(labelForca);
		
	}
	
	private JLabel botaoDica;
	
	private JLabel labelDica;
	
	private String textoDica = "máquina usada para programar e navegar na internet";
	
	private boolean dicaAtiva = false;
	
	public void adicionarBotaoDarPalpite() {
		botaoDarPalpite = new BotaoGrande("DAR PALPITE", 430, 560, 240, 60);
		botaoDarPalpite.addMouseListener(new OuvinteBotaoDarPalpite());		
		add(botaoDarPalpite);
	}
	
	public void adicionarBotaoDica() {
		ImageIcon original = Icones.ICONE_HISTORICO;

		Image imagem = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon redimensionado = new ImageIcon(imagem);
		
		if (botaoDica != null) {
			remove(botaoDica);
		}
		
		JLabel botaoDica = new JLabel(redimensionado);
		botaoDica.setBounds(840, 40, 40, 40);
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
			labelDica.setBounds(690, 40, 190, 100);
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
	        	pontos += 690;
	            letrasEncontradas.add(letra);
	            adicionarPontosUsuario();
	            adicionarLabelsPalavra();
	        } else {
	            letrasErradas.add(letra);
	            atualizarLetrasErradas();
	        }
	        adicionarIconeForca();
	        inserirLetra.setText("");
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

		int x = 550 - ((palavraEscolhida.length() * 36) / 2);
		int y = 520;

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
		inserirLetra = new CampoPadrao("", 515, 370, 60, 70);
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
		if (quadroPontos != null) {
			remove(quadroPontos);
		}
		quadroPontos = new QuadroDePontos("Pontos Solo", pontos, Icones.ICONE_HISTORICO, 450, 40);
		add(quadroPontos, 0);
		
		fotoUsuario = new PerfilUsuario(Icones.ICONE_HISTORICO, this.nomeUsuario, 470, 160, 160, 160);
		add(fotoUsuario);
		
		textoNomeUsuario = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeUsuario, 470, 255, 160, 160);
		add(textoNomeUsuario);
	}
	
	public void atualizarLetrasErradas() {
		int y = 125;
		int larg = 40;
		int alt = 40;
		int indexLetra = 0;

		while (indexLetra < letrasErradas.size()) {
			int x = 935;
			for (int j = 0; j < 3 && indexLetra < letrasErradas.size(); j++) {
				labelLetraErrada = new LabelTexto(Fontes.FONTE_LETRAS_ERRADAS, String.valueOf(letrasErradas.get(indexLetra)), x, y, larg, alt);
				add(labelLetraErrada, 0);
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
		fundoLetrasErradas.setBounds(900, 40, 260, 350);
		fundoLetrasErradas.setOpaque(true);
		
		textoLetrasErradas = new LabelTexto(Fontes.FONTE_MEDIA, "LETRAS ERRADAS", 915, 55, 230, 50);
		textoLetrasErradas.setBackground(Cores.COR_PRIMARIA);
		textoLetrasErradas.setOpaque(true);
		
		add(textoLetrasErradas, 0);
		add(fundoLetrasErradas, 1);

		atualizarLetrasErradas();
	}
	
	public static void main(String[] args) {
		new PainelPrincipalInicioSolo("Wilokolo");
	}
}
