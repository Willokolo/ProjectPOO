package componentes;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JTextField;

import interfaces.Colors;

public class CampoPadrao extends JTextField {

	private String texto;
		
	public CampoPadrao(String nome, int x, int y, int largura, int altura) {
		setText(nome);
		setBackground(Colors.COR_FUNDO);
		setForeground(Colors.COR_LETRAS);
		setMargin(new Insets(0, 15, 0, 15));

		setBounds(x, y, largura, altura);
	}
	
	public CampoPadrao(String nome, int x, int y) {
		setText(nome);
		setBackground(Colors.COR_FUNDO);
		setForeground(Colors.COR_LETRAS);
		setMargin(new Insets(0, 15, 0, 15));
		
		setBounds(x, y, 100, 30);
	}
}
