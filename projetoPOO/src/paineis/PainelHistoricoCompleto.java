package paineis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;

import componentes.BarraLateral;
import componentes.LabelTexto;
import enums.Dificuldade;
import enums.Resultado;
import enums.TipoDePartida;
import exceptions.RetornoVazioException;
import interfaces.Cores;
import interfaces.Fontes;
import projeto.Jogador;
import projeto.JogoDaForca;
import projeto.Palavra;
import projeto.Partida;
import projeto.PartidaDuelo;
import projeto.PartidaSolo;
import projeto.Tentativa;

public class PainelHistoricoCompleto extends BarraLateral {

	private JLabel fundoEscuro;
	
	private JLabel textoTitulo;
	
	private JScrollPane scroll;
	
	private JPanel lista;
	
	private ArrayList<Partida> partidas;
	
	private JLabel labelHistorico;
	
	public PainelHistoricoCompleto() {
		super("Painel Histórico Completo");
		
		configurar();
	}

	public void configurar() {
		fundoEscuro = new JLabel();
		fundoEscuro.setBackground(Cores.COR_FUNDO_ESCURO);
		fundoEscuro.setBounds(250, 40, 900, 600);
		fundoEscuro.setOpaque(true);
		
		textoTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "Histórico de Partidas", 280, 45, 840, 50);
		
		lista = new JPanel();
		lista.setBackground(Cores.COR_PRIMARIA);
		lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));

		scroll = new JScrollPane(lista);
		scroll.setBounds(280, 100, 840, 510);
		scroll.setBorder(BorderFactory.createLineBorder(Cores.COR_PRIMARIA, 6));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JScrollBar barra = scroll.getVerticalScrollBar();
		barra.setPreferredSize(new Dimension(10	, 0)); // largura da barra
		barra.setUnitIncrement(16);                  // velocidade da rolagem

		// troca a aparência padrão da barra por uma personalizada
		
		barra.setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {

		    // desenha o trilho (o fundo por onde o controle anda)
		    protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
		        g.setColor(Cores.COR_PRIMARIA);
		        g.fillRoundRect(r.x, r.y, r.width, r.height, 8, 8); 
		    }
		    
		    // desenha o controle (a parte que você arrasta)
		    protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		        g.setColor(new Color(220, 220, 220));         
		        g.fillRoundRect(r.x, r.y, r.width, r.height, 8, 8);
		    }

		    // botão da seta de cima (na barra vertical)
		    @Override
		    protected JButton createDecreaseButton(int o) {
		        return semBotao();
		    }

		    // botão da seta de baixo
		    @Override
		    protected JButton createIncreaseButton(int o) {
		        return semBotao();
		    }

		    // cria um botão de tamanho zero, então a seta "some"
		    private JButton semBotao() {
		        JButton b = new JButton();
		        b.setPreferredSize(new Dimension(0, 0));
		        return b;
		    }
		});
	
		criarHistorico();
		
		add(scroll, 0); 
		
		revalidate();
		repaint();
		
		add(fundoEscuro, 2);
		add(textoTitulo, 1);
	}
	
	
	public void criarHistorico() {
		Partida partidaDuelo = new PartidaDuelo(
				new Jogador("Wilokolo", "a", "b"), 
				new Jogador("Raf3nix", "c", "d"), 
				new Palavra("Cavalo", "Andar a ", Dificuldade.FACIL),
				new Palavra("Mesa", "de comer veia de 4 pé", Dificuldade.FACIL)
				);
		
		Partida partidaSolo = new PartidaSolo(new Jogador("Wilokolo", "a", "b"), new Palavra("Cavalo", "Andar a ", Dificuldade.FACIL));
		
		partidas = new ArrayList<Partida>();
		
		partidas.add(partidaSolo);
		partidas.add(partidaDuelo);
		
		for (Partida partida : partidas) {
			boolean primeiro = false;
			if (partida.equals(partidas.get(1))) {
				primeiro = true;
			}
			if (partida != null && partida.getClass() == PartidaSolo.class) {
				criarAbaHistorico(TipoDePartida.SOLO, partida, primeiro);
			} else if (partida != null && partida.getClass() == PartidaDuelo.class) {
				criarAbaHistorico(TipoDePartida.DUELO, partida, primeiro);
			}
		}			
	}
	
	
	public void criarAbaHistorico(TipoDePartida tipo, Partida partida, boolean primeiro) {
		JPanel cardEspacador = new JPanel();
		if (primeiro != false) {
			Dimension tamanhoEspacador = new Dimension(818, 20);
			cardEspacador.setPreferredSize(tamanhoEspacador);
			cardEspacador.setMinimumSize(tamanhoEspacador);
			cardEspacador.setMaximumSize(tamanhoEspacador);
			cardEspacador.setBackground(Cores.COR_PRIMARIA);
			cardEspacador.setAlignmentX(Component.LEFT_ALIGNMENT);
			lista.add(cardEspacador);	
		}
		
		JPanel card = new JPanel();
		String texto = null;
		Dimension tamanho = null;
		Color corResultado = null; 
		
		if (tipo == TipoDePartida.DUELO) {
			texto = "DUELO";
			tamanho = new Dimension(818, 500);
			
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "VS",  330, 50, 150, 50));
			
			try {
				
				Jogador vencedor = partida.getVencedor();
				Jogador perdedor = null;
				
				for (Jogador jogador : partida.getJogadores()) {
					if (!jogador.equals(vencedor)) {
						perdedor = jogador;
					}
				}
				
				//card.add(new LabelTexto(Fontes.FONTE_GRANDE, "Wilokolo",105, 50, 200, 50));
				//card.add(new LabelTexto(Fontes.FONTE_GRANDE, "Raf3nix", 458, 50, 200, 50));
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, vencedor.getNome(), 6, 50, 200, 50, Cores.COR_PRIMARIA));
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, perdedor.getNome(), 578, 50, 200, 50, Color.RED));
				
			} catch (RetornoVazioException e) {
				//System.out.println("Oxe");
			}
				
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "+3", 46, 66, 50, 50, Cores.COR_PRIMARIA));
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "0", 728, 66, 50, 50, Color.RED));
			
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "GANHOU", 105, 80, 200, 50, Cores.COR_PRIMARIA));
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "PERDEU", 458, 80, 200, 50, Color.RED));

			
			
		} else if (tipo == TipoDePartida.SOLO) {
			texto = "SOLO";
			tamanho = new Dimension(818, 300);

			if (partida.getResultado() == Resultado.VITORIA) {
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, "VITÓRIA", 330, 50, 150, 50, Cores.COR_PRIMARIA));
				
			} else if (partida.getResultado() == Resultado.DERROTA) {				
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, "DERROTA", 330, 50, 150, 50, Color.RED)); 
				
			}
		}
		
		card.setBackground(Cores.COR_FUNDO);
		card.setOpaque(true);

		card.setLayout(null);
		 
		card.setPreferredSize(tamanho);
		card.setMinimumSize(tamanho);
		card.setMaximumSize(tamanho);
		card.setAlignmentX(Component.LEFT_ALIGNMENT);

		JLabel labelTipo = new LabelTexto(Fontes.FONTE_GRANDE, texto, 0, 0, 120, 50);
		JLabel labelDificuldade = new LabelTexto(Fontes.FONTE_GRANDE, String.valueOf(partida.getDificuldade()), 680, 0, 150, 50);
		labelDificuldade.setAlignmentX(RIGHT_ALIGNMENT);
		
		JLabel dataPartida = new LabelTexto(Fontes.FONTE_GRANDE, String.valueOf(partida.getDataHoraFormatada()), 210, 0, 400, 50);
		dataPartida.setAlignmentX(CENTER_ALIGNMENT);
		
		
		card.add(dataPartida);
		card.add(labelDificuldade);
		card.add(labelTipo);
		
		criarLabelResultado(card, partida);

		lista.add(card);
		lista.revalidate();
		lista.repaint();
	}
	
	JPanel painelFundoVerde;
	
	public void criarLabelResultado(JPanel card, Partida partida) {
		int yPainel = 93;
		int index = 0;
		ArrayList<JogoDaForca> jogos = partida.getRodadas();
		
		for (JogoDaForca jogo : jogos) {

		painelFundoVerde = new JPanel();
		painelFundoVerde.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
		painelFundoVerde.setOpaque(true);
		painelFundoVerde.setLayout(null);
		painelFundoVerde.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		card.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, String.valueOf(partida.getPalavras().get(0)), 330, yPainel, 150, 50), 0);
		painelFundoVerde.setBounds(12, yPainel + 57, 794, 138);
		
		painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "JOGADOR", 6, 6, 160, 35));
		painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "ERROS", 216, 6, 160, 35));
		painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "ACERTOS", 422, 6, 160, 35));
		painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "PONTOS", 628, 6, 160, 35));
		
		int y = 55;
		
		for (Jogador jogador: partida.getJogadores()) {				
			
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, jogador.getNome(), 6, y, 160, 35));
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, String.valueOf(jogo.getErros(jogador)).replace("[", "").replace("]", ""), 
					216, y, 160, 35));
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, String.valueOf(jogo.getAcertos(jogador)).replace("[", "").replace("]", ""), 
					216, y, 160, 35));
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, String.valueOf(jogo.getPontos(jogador)),
					216, y, 160, 35));
			
				y += 35;
			
		}
			card.add(painelFundoVerde);
			yPainel += 200;
			index++;
		}
	}
	
	public static void main(String[] args) {
		new PainelHistoricoCompleto();
	}
	
}
