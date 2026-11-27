package paineis;

import java.awt.event.KeyAdapter;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import componentes.BarraLateral;
import componentes.BotaoGrande;
import componentes.CampoPadrao;
import componentes.LabelTexto;
import componentes.PerfilUsuario;
import componentes.QuadroDePontos;
import enums.Resultado;
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
import projeto.PartidaSolo;
import projeto.Persistencia;
import projeto.Tentativa;

public class PainelPrincipalInicioSolo extends BarraLateral {
	// LETRAS ERRADAS
	private JLabel fundoLetrasErradas;
	private JLabel textoLetrasErradas;
	private JLabel labelLetraErrada;
	
	// PONTOS
	private JLabel quadroPontos;
	
	// USUARIO
	private JLabel fotoUsuario;
	private JLabel textoNomeUsuario;
	private String nomeUsuario;
	
	// BOTÕES
	private JLabel botaoDarPalpite;
	private JLabel botaoDica;
	
	// PARTIDA, PALAVRA E PALPITES
	private PartidaSolo partida;
	private String palavraEscolhida;
	private ArrayList<Character> arrayLetrasPalavraEscolhida = new ArrayList<Character>();
	private ArrayList<Character> letrasErradas = new ArrayList<Character>();
	private ArrayList<Character> letrasEncontradas = new ArrayList<Character>();
	private List<JLabel> labelsPalavra = new ArrayList<>();
		
	// FORCA
	private JLabel labelForca;
	
	// CAMPO DE TEXTO
	private JTextField inserirLetra;
	
	// DICA
	private String textoDica;
	private JLabel lbDica;
	
	public PainelPrincipalInicioSolo(CentralDeInformacoes central, Persistencia persistencia, Jogador jogador, Palavra palavra) {
		super("Painel Principal - Menu Solo", central, persistencia, jogador);
		
		iniciarPartida(palavra);
		adicionarBotaoDica();
		adicionarLabelsPalavra();
		adicionarPontosUsuario();
		adicionarCampoEscolherLetra();
		adicionarBotaoDarPalpite();
		criarLetrasErradas();
		adicionarIconeForca();
	}
	
	private void iniciarPartida(Palavra palavra) {
		partida = new PartidaSolo(jogador, palavra);
		jogador.addPalavraVista(palavra);
		central.addPartida(partida);
		persistencia.salvarCentral(central);
		
		palavraEscolhida = palavra.getPalavra();
		textoDica = palavra.getDica();
		nomeUsuario = jogador.getNome();
	}
	
	public void adicionarIconeForca() {
		
		ImageIcon original = Icones.ICONE_FORCA_ESTADO_0;
		
		switch(partida.getRodada().getErros(jogador)) {
		case 1:
			original = Icones.ICONE_FORCA_ESTADO_1;
			break;
			
		case 2:
			original = Icones.ICONE_FORCA_ESTADO_2;
			break;
			
		case 3:
			original = Icones.ICONE_FORCA_ESTADO_3;
			break;
			
		case 4:
			original = Icones.ICONE_FORCA_ESTADO_4;
			break;
			
		case 5:
			original = Icones.ICONE_FORCA_ESTADO_5;
			break;
			
		case 6:
			original = Icones.ICONE_FORCA_ESTADO_6;
			break;
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
	
	
	public void adicionarBotaoDarPalpite() {
		botaoDarPalpite = new BotaoGrande("DAR PALPITE", 430, 560, 240, 60);
		botaoDarPalpite.addMouseListener(new OuvinteBotaoDarPalpite());		
		add(botaoDarPalpite);
	}
	
	public void adicionarBotaoDica() {
		ImageIcon original = Icones.ICONE_HISTORICO;

		Image imagem = original.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon redimensionado = new ImageIcon(imagem);
		
		botaoDica = new JLabel(redimensionado);
		botaoDica.setBounds(840, 40, 40, 40);
		botaoDica.addMouseListener(new OuvinteBotaoDica());
		add(botaoDica);
		
		lbDica = new LabelTexto(Fontes.FONTE_MEDIA, "Dica", 835, 85, 50, 15);
		add(lbDica);
	}

	public void adicionarLabelsPalavra() {
		for (JLabel label : labelsPalavra)
			remove(label);
		labelsPalavra.clear();

		String gabarito = partida.getRodada().getGabarito();
		int x = 550 - ((palavraEscolhida.length() * 36) / 2);
		int y = 520;

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
		quadroPontos = new QuadroDePontos("Pontos Solo", jogador.getPontosSolo(), Icones.ICONE_HISTORICO, 450, 40);
		add(quadroPontos, 0);
		
		fotoUsuario = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario, 470, 160, 160, 160);
		add(fotoUsuario);
		
		textoNomeUsuario = new LabelTexto(Fontes.FONTE_MEDIA, nomeUsuario, 470, 255, 160, 160);
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
		
		add(textoLetrasErradas);
		add(fundoLetrasErradas);

		atualizarLetrasErradas();
	}
	
	private class OuvinteBotaoDica extends OuvinteBotaoSemFundo {
		@Override
		public void mouseClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(null, textoDica, "Dica", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private class OuvinteBotaoDarPalpite extends OuvinteBotaoFundoVerde {
		@Override
		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			String digitado = inserirLetra.getText().trim();
			
			if (digitado.isEmpty()) {
				return;
			}
			
			char letra = Character.toUpperCase(digitado.charAt(0));
			Tentativa tentativa = new Tentativa(jogador, letra);
			JogoDaForca rodada = partida.getRodada();
			
			try {
				if(rodada.testarLetra(tentativa))
					letrasEncontradas.add(letra);
				else
					letrasErradas.add(letra);
			} catch (TentativaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			if (rodada.terminou()) {
				JOptionPane.showMessageDialog(null, "A palavra era: " + palavraEscolhida, "Palavra", JOptionPane.INFORMATION_MESSAGE);
				
				try {
					rodada.getVencedor();
					dispose();
					
					new ResultadoPartida(Resultado.VITORIA, central, persistencia, jogador);
				} catch (RetornoVazioException e1) {
					dispose();
					new ResultadoPartida(Resultado.DERROTA, central, persistencia, jogador);
				} finally {
					jogador.addPontosSolo(partida.getPontos());
					persistencia.salvarCentral(central);
				}
				
			}
			
			adicionarLabelsPalavra();
			atualizarLetrasErradas();
			adicionarIconeForca();
			inserirLetra.setText("");
			
			j.setBackground(Cores.COR_BOTAO_HOVER);
		}
	}
}