package projeto;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

<<<<<<< HEAD
import componentes.BotaoGrande;
import componentes.BotaoMedio;
import componentes.BotaoPequeno;
import componentes.Painel;
import enums.Utils;
import componentes.BotoesGrandes;
>>>>>>> 84d0743 (Adicionei organização de pastas, para enums e painéis, furutamente desejando utilizar a pasta projeto apenas para as mains de teste e principal, também adicionei a classe LabelTexto que cria textos de forma padronizada)

public class BarraLateral extends Painel{
	
	// LABELS
	private JLabel barra;
	private JLabel lbJogar;
	private JLabel lbJogadores;
	private JLabel lbNomeJogador;
	private JLabel lbIconeJogador;
	
	// CAMPOS DE TEXTO
	private JTextField tfJogadores;
	
	// BOTÕES CUSTOMIZADOS
	private JLabel btJogarSolo;
	private JLabel btJogarDuelo;
	private JLabel btLideranca;
	private JLabel btMensagens;
	private JLabel btEstatisticas;
	private JLabel btHistorico;
	private JLabel btAddJogador;
	
	public BarraLateral() {
		criarBotoesCustomizados();
		criarTextos();
		criarCamposTexto();
		criarBarra();
		setVisible(true);
	}
	
	public class OuvinteBotaoGrande implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBackground(Utils.COR_PRIMARIA_COMPLEMENTAR);
			JOptionPane.showMessageDialog(null, "Nada, por enquanto");
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			c.setBackground(Utils.COR_BOTAO_HOVER);
		}

		public void mouseExited(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBackground(Utils.COR_PRIMARIA);
		}
		
	}
	
	private class OuvinteBotaoMedio implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JOptionPane.showMessageDialog(null, "Nada, por enquanto");
		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			c.setOpaque(true);
			c.repaint();
		}

		public void mouseExited(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setOpaque(false);
			c.repaint();
		}
		
	}
	
	private void criarBotoesCustomizados() {
		
		// BOTÕES GRANDES
		btJogarSolo = new BotaoGrande("SOLO", Utils.ICONE_SOLO, 10, 370, 180, 70);
		btJogarSolo.addMouseListener(new OuvinteBotaoGrande());
		add(btJogarSolo);
		
		btJogarDuelo = new BotaoGrande("DUELO", Utils.ICONE_DUELO, 10, 450, 180, 70);
		btJogarDuelo.addMouseListener(new OuvinteBotaoGrande());
		add(btJogarDuelo);
		
		
		// BOTÕES MÉDIOS
		btLideranca = new BotaoMedio("Liderança", Utils.ICONE_LIDERANCA, 10, 250, 180, 30);
		btLideranca.addMouseListener(new OuvinteBotaoMedio());
		add(btLideranca);
		
		btMensagens = new BotaoMedio("Mensagens", Utils.ICONE_MENSAGENS, 10, 210, 180, 30);
		btMensagens.addMouseListener(new OuvinteBotaoMedio());
		add(btMensagens);
		
		btEstatisticas = new BotaoMedio("Estatísticas", Utils.ICONE_ESTATISTICAS, 10, 170, 180, 30);
		btEstatisticas.addMouseListener(new OuvinteBotaoMedio());
		add(btEstatisticas);
		
		btHistorico = new BotaoMedio("Histórico", Utils.ICONE_HISTORICO, 10, 130, 180, 30);
		btHistorico.addMouseListener(new OuvinteBotaoMedio());
		add(btHistorico);
		
		
		// BOTÕES PEQUENOS
		btAddJogador = new BotaoPequeno("+ Adicionar Novo Jogador", 10, 640, 180, 25);
		btAddJogador.addMouseListener(new OuvinteBotaoMedio());
		add(btAddJogador);
	}
	
	private void criarTextos() {
		lbJogar = new JLabel("Jogar");
		lbJogar.setBounds(10, 325, 180, 40);
		lbJogar.setHorizontalAlignment(JLabel.CENTER);
		lbJogar.setFont(Utils.FONTE_GRANDE);
		lbJogar.setForeground(Utils.COR_LETRAS);
		add(lbJogar);
		
		lbJogadores = new JLabel("Jogadores");
		lbJogadores.setBounds(10, 550, 180, 30);
		lbJogadores.setHorizontalAlignment(JLabel.CENTER);
		lbJogadores.setFont(Utils.FONTE_MEDIA);
		lbJogadores.setForeground(Utils.COR_LETRAS);
		add(lbJogadores);
		
		lbNomeJogador = new JLabel("RAFÊNIX");
		lbNomeJogador.setBounds(10, 70, 180, 30);
		lbNomeJogador.setHorizontalAlignment(JLabel.CENTER);
		lbNomeJogador.setFont(Utils.FONTE_MEDIA);
		lbNomeJogador.setForeground(Utils.COR_LETRAS);
		add(lbNomeJogador);
		
		lbIconeJogador = new JLabel("RF");
		lbIconeJogador.setBounds(75, 15, 50, 50);
		lbIconeJogador.setBackground(Utils.COR_PRIMARIA);
		lbIconeJogador.setOpaque(true);
		lbIconeJogador.setForeground(Utils.COR_FUNDO_ESCURO);
		lbIconeJogador.setFont(Utils.FONTE_GRANDE);
		lbIconeJogador.setHorizontalAlignment(JLabel.CENTER);
		add(lbIconeJogador);
	}
	
	private void criarCamposTexto() {
		tfJogadores = new JTextField();
		tfJogadores.setBounds(10, 590, 180, 40);
		tfJogadores.setFont(Utils.FONTE_PEQUENA);
		add(tfJogadores);
	}
	
	private void criarBarra() {
		barra = new JLabel();
		barra.setBounds(0, 0, 200, 675);
		barra.setOpaque(true);
		barra.setBackground(Utils.COR_FUNDO_ESCURO);
		add(barra);
	}
	
	public static void main(String[] args) {
		BarraLateral b = new BarraLateral();
	}
}
