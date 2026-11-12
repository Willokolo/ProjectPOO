package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import interfaces.Colors;

public class Painel extends JFrame{

	private int largura = 1200;
	private int altura  = 675;
	
	public Painel(String title) {
		setSize(this.largura, this.altura);
		setLocationRelativeTo(null);
		setLayout(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Colors.COR_FUNDO);
	}
}
