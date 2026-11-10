package projeto;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BarraLateral extends JFrame{
	public BarraLateral() {
		configurar();
		criarBotoesCustomizados();
		criarTextos();
		criarBarra();
		setVisible(true);
	}
	
	public class OuvinteBotoesCustomizados implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBackground(Utils.COR_PRIMARIA_COMPLEMENTAR);
			JOptionPane.showMessageDialog(null, "Nada, por enquanto");
		}

		public void mousePressed(MouseEvent e) {
			
		}

		public void mouseReleased(MouseEvent e) {
			
		}

		public void mouseEntered(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBackground(Utils.COR_BOTAO_HOVER);
		}

		public void mouseExited(MouseEvent e) {
			JComponent c = (JComponent) e.getSource();
			c.setBackground(Utils.COR_PRIMARIA);
		}
		
	}
	
	private JLabel barra;
	private JLabel jogar;
	
	//botões customizados
	private JLabel jogarSolo;
	private JLabel jogarDuelo;
	
	private void configurar() {
		setSize(1200, 675);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Utils.COR_FUNDO);
	}
	
	private void criarBotoesCustomizados() {
		jogarSolo = new Componentes.Botoes("SOLO", 10, 320, 180, 70);
		jogarSolo.addMouseListener(new OuvinteBotoesCustomizados());
		//jogarSolo.setBorder(new RoundedBorder(10));
		add(jogarSolo);
		
		jogarDuelo = new Componentes.Botoes("DUELO", 10, 400, 180, 70);
		jogarDuelo.addMouseListener(new OuvinteBotoesCustomizados());
		add(jogarDuelo);
		
		JButton botao = new JButton("teste");
		botao.setContentAreaFilled(false);
		botao.setOpaque(true);
		botao.setBounds(400, 400, 180, 70);
		botao.setBackground(Utils.COR_PRIMARIA);
		botao.setBorderPainted(false);
		botao.addMouseListener(new OuvinteBotoesCustomizados());
		botao.setFont(Utils.FONTE_GRANDE);
		botao.setFocusPainted(false);
		add(botao);
	}
	
	private void criarTextos() {
		jogar = new JLabel("Jogar");
		jogar.setBounds(10, 275, 180, 40);
		jogar.setHorizontalAlignment(JLabel.CENTER);
		jogar.setFont(Utils.FONTE_GRANDE);
		jogar.setForeground(Utils.COR_LETRAS);
		add(jogar);
		
		JLabel teste = new JLabel();
		teste.setBounds(500, 200, 45, 45);
		teste.setBackground(Utils.COR_PRIMARIA);
		teste.setOpaque(true);
		add(teste);
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
