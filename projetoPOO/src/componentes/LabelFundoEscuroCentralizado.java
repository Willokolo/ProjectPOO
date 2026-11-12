package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;

import enums.Utils;

public class LabelFundoEscuroCentralizado extends JLabel{
	
	public LabelFundoEscuroCentralizado(JFrame frame, int largura, int altura) {
		
		setBackground(Utils.COR_FUNDO_ESCURO);
		
		setLayout(null);
		
		setBounds((frame.getWidth() / 2) - (largura / 2), (frame.getHeight() / 2) - (altura / 2), largura, altura);
		
		setOpaque(true);
	}
}
