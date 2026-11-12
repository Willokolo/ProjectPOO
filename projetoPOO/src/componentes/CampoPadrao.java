package componentes;

import java.awt.Color;

import javax.swing.JTextField;

public class CampoPadrao extends JTextField {

		
		public CampoPadrao(String nome, int x, int y, int largura, int altura) {
			setText(nome);
			setBackground(new Color(255, 0 , 0));
			//setBorder();
			setBounds(x, y, largura, altura);
		}
	
}
