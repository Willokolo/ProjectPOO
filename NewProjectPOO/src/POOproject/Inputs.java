package POOproject;

import java.awt.Color;

import javax.swing.JTextField;

public class Inputs {

	public static JTextField tfCampoPadrao(String cNome) {
		JTextField tfCampoPadrao = new JTextField(cNome);
		return tfCampoPadrao;
	}
	
	public static class tfCampoPadrao extends JTextField {
		
		public tfCampoPadrao(String nome, int x, int y, int largura, int altura) {
			setText(nome);
			setBackground(new Color(255, 0 , 0));
			//setBorder();
			setBounds(x, y, largura, altura);
		}
	}
	
}
