package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;

import interfaces.*;

public class LabelFundoEscuroCentralizado extends JLabel{
	
	public LabelFundoEscuroCentralizado(JFrame frame, int largura, int altura) {
		
		setBackground(Cores.COR_FUNDO_ESCURO);
		
		setLayout(null);
		
		// -16 POR CAUSA DAS BORDAS
		setBounds(((frame.getWidth() - 16 ) / 2) - (largura / 2), (frame.getHeight() / 2) - (altura / 2), largura, altura);
		
		setOpaque(true);
	}
}
