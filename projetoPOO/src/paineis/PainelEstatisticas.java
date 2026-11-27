package paineis;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

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

public class PainelEstatisticas extends BarraLateral {

private JLabel fundoEscuro;
	
	private JLabel textoTitulo;
	
	private JScrollPane scroll;
	
	private JPanel lista;
	
	private ArrayList<Partida> partidas;
	
	private JLabel labelHistorico;
	
	public PainelEstatisticas() {
		super("Painel Estatisticas");
		
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
		
		
		add(scroll, 0); 
		
		revalidate();
		repaint();
		
		add(fundoEscuro, 2);
		add(textoTitulo, 1);
	}
	
	

	
	public static void main(String[] args) {
		new PainelEstatisticas();
	}
	
}
