package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import enums.Utils;

public class Painel extends JFrame{

	public Painel() {

	private int largura = 1200;
	private int altura  = 675;
	
	public Painel(String title) {
		setSize(this.largura, this.altura);
		setLocationRelativeTo(null);
		setLayout(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Utils.COR_FUNDO);
	}
}
