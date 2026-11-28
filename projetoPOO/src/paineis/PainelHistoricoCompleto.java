package paineis;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.*;
import java.awt.Rectangle;
import java.util.ArrayList;
import componentes.BarraLateral;
import componentes.LabelTexto;
import enums.Resultado;
import enums.TipoDePartida;
import exceptions.RetornoVazioException;
import interfaces.Cores;
import interfaces.Fontes;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.JogoDaForca;
import projeto.Partida;
import projeto.PartidaDuelo;
import projeto.PartidaSolo;
import projeto.Persistencia;

public class PainelHistoricoCompleto extends BarraLateral {

	private JLabel fundoEscuro;
	private JLabel textoTitulo;
	private JScrollPane scroll;
	private JPanel lista;
	private JPanel painelFundoVerde;
	private ArrayList<Partida> partidas;
	
	public PainelHistoricoCompleto(CentralDeInformacoes central, Persistencia persistencia, Jogador jogador, ArrayList<Partida> partidas) {
		super("Painel Histórico Completo", central, persistencia, jogador);
		
		this.partidas = partidas;
		configurar();
	}

	private void configurar() {
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
	
	
	private void criarHistorico() {
		if (partidas.isEmpty()) {
			JLabel lbSemPartidas = new LabelTexto(Fontes.FONTE_GRANDE, "Não há partidas cadastradas", 330, 50, 150, 50);
			lbSemPartidas.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			lbSemPartidas.setOpaque(true);
			lista.add(lbSemPartidas);
			lista.revalidate();
			lista.repaint();
			return;
		}
		
		for (Partida partida : partidas) {
			if (partida != null && partida.getClass() == PartidaSolo.class) {
				criarAbaHistorico(TipoDePartida.SOLO, partida);
			} else if (partida != null && partida.getClass() == PartidaDuelo.class) {
				criarAbaHistorico(TipoDePartida.DUELO, partida);
			}
		}			
	}
	
	
	private void criarAbaHistorico(TipoDePartida tipo, Partida partida) {
		JPanel cardEspacador = new JPanel();
		Dimension tamanhoEspacador = new Dimension(818, 20);
		cardEspacador.setPreferredSize(tamanhoEspacador);
		cardEspacador.setMinimumSize(tamanhoEspacador);
		cardEspacador.setMaximumSize(tamanhoEspacador);
		cardEspacador.setBackground(Cores.COR_PRIMARIA);
		cardEspacador.setAlignmentX(Component.LEFT_ALIGNMENT);
		lista.add(cardEspacador);	
		
		
		JPanel card = new JPanel();
		String texto = null;
		Dimension tamanho = null;
		
		if (tipo == TipoDePartida.DUELO) {
			texto = "DUELO";
			tamanho = new Dimension(818, 620);
			
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "VS",  330, 50, 150, 50));
			
			Jogador jogador1 = jogador;
			Jogador jogador2 = null;
			for (Jogador j: partida.getJogadores()) {
				if (!j.equals(jogador1))
					jogador2 = j;
			}
			
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, jogador1.getNome(), 152, 50, 200, 50));
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, jogador2.getNome(), 458, 50, 200, 50));
			String texto1;
			String texto2;
			Color cor1;
			Color cor2;
			
			try {
				if (partida.getVencedor().equals(jogador1)) {
					texto1 = "GANHOU";
					texto2 = "PERDEU";
					cor1 = Cores.COR_PRIMARIA;
					cor2 = Color.RED;
				} else {
					texto1 = "PERDEU";
					texto2 = "GANHOU";
					cor1 = Color.RED;
					cor2 = Cores.COR_PRIMARIA;
				}
				
				
			} catch (RetornoVazioException e) {
				texto1 = "EMPATOU";
				texto2 = texto1;
				cor1 = Color.YELLOW;
				cor2 = cor1;
			}
			
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "+" + partida.getPontos(jogador1), 46, 66, 50, 50, cor1));
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, "+" + partida.getPontos(jogador2), 728, 66, 50, 50, cor2));
			
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, texto1, 152, 80, 200, 50, cor1));
			card.add(new LabelTexto(Fontes.FONTE_GRANDE, texto2, 458, 80, 200, 50, cor2));
			
		} else if (tipo == TipoDePartida.SOLO) {
			texto = "SOLO";
			tamanho = new Dimension(818, 270);
			
			try {
				partida.getVencedor();
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, "VITÓRIA", 0, 50, 818, 50, Cores.COR_PRIMARIA));
			} catch (RetornoVazioException e) {
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, "DERROTA", 0, 50, 818, 50, Color.RED)); 
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
	
	
	private void criarLabelResultado(JPanel card, Partida partida) {
		int yPainel;
		int alturaFundoVerde;
		if (partida instanceof PartidaDuelo) {
			yPainel = 163;
			alturaFundoVerde = 138;
		} else {
			yPainel = 93;
			alturaFundoVerde = 100;
		}
		
		int indice = 0;
		ArrayList<JogoDaForca> jogos = partida.getRodadas();
		
		for (JogoDaForca jogo : jogos) {

			painelFundoVerde = new JPanel();
			painelFundoVerde.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			painelFundoVerde.setOpaque(true);
			painelFundoVerde.setLayout(null);
			painelFundoVerde.setAlignmentX(Component.LEFT_ALIGNMENT);
			
			if (partida instanceof PartidaDuelo)
				card.add(new LabelTexto(Fontes.FONTE_GRANDE, "Rodada " + (indice + 1), 0, (yPainel - 40), 818, 50));
			card.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, "Palavra: " + partida.getPalavras().get(indice).getPalavra(),0, yPainel, 818, 50), 0);
			painelFundoVerde.setBounds(12, yPainel + 57, 794, alturaFundoVerde);
			
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "JOGADOR", 6, 6, 160, 35));
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "ERROS", 186, 6, 160, 35));
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "ACERTOS", 422, 6, 160, 35));
			painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE, "PONTOS", 628, 6, 160, 35));
			
			int y = 50;
			
			for (Jogador jogador: partida.getJogadores()) {				
				String erros;
				String acertos = String.valueOf(jogo.getAcertos(jogador)).replace("[", "").replace("]", "");
				
				if (jogo.getErros(jogador).isEmpty())
					erros = "Sem erros";
				else
					erros = jogo.getErros(jogador).toString().replace("[", "").replace("]", "");
				
				if (jogo.getAcertos(jogador).isEmpty()) 
					acertos = "Sem acertos";
				else 
					acertos = jogo.getAcertos(jogador).toString().replace("[", "").replace("]", "");
				
				painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, jogador.getNome(), 6, y, 160, 35));
				painelFundoVerde.add(new LabelTexto(Fontes.FONTE_MEDIA, erros, 186, y, 160, 35));
				painelFundoVerde.add(new LabelTexto(Fontes.FONTE_MEDIA, acertos, 376, y, 252, 35));
				painelFundoVerde.add(new LabelTexto(Fontes.FONTE_GRANDE_NO_BOLD, String.valueOf(jogo.getPontos(jogador)), 628, y, 160, 35));
				
				y += 40;
			
			}
			card.add(painelFundoVerde);
			yPainel += 240;
			indice++;
		}
	}
}
