package componentes;

import java.awt.Color;

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
	
	public void adicionarLinhaCentral() {

	    int largura = 2;
	    int altura = 675;

	    int x = (getWidth() - largura) / 2;
	    int y = (getHeight() - altura) / 2;

	    JLabel lbLinha = new JLabel();

	    lbLinha.setBounds(x, y, largura, altura);
	    lbLinha.setOpaque(true);
	    lbLinha.setBackground(Color.WHITE);

	    add(lbLinha);
	}
}
