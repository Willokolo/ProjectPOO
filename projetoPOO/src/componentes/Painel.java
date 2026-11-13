package componentes;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import interfaces.Cores;

public class Painel extends JFrame{
	
	// O TAMANHO REAL É 1200 X 675, MAS ESTÁ COM ESSE VALOR POR CAUSA DAS BORDAS
	// FAÇA OS CÁLCULOS DE POSIÇÃO COM BASE NO TAMANHO REAL, NÃO COM ESSES NÚMEROS
	private final int largura = 1216; // NÃO MUDAR
	private final int altura  = 714; // NÃO MUDAR
	
	public Painel(String title) {
		super(title);
		setSize(this.largura, this.altura);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Cores.COR_FUNDO);
	}
	
	public void adicionarLinhaCentral() {

	    int largura = 2;
	    int altura = 675;

	    // -16 POR CAUSA DAS BORDAS
	    int x = (getWidth() - largura - 16) / 2;
	    int y = 0;

	    JLabel lbLinha = new JLabel();

	    lbLinha.setBounds(x, y, largura, altura);
	    lbLinha.setOpaque(true);
	    lbLinha.setBackground(Color.WHITE);

	    add(lbLinha);
	}
}
