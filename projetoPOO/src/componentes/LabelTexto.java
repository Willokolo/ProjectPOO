package componentes;

import javax.swing.JLabel;

import projeto.TipoTexto;
import projeto.Utils;

public class LabelTexto extends JLabel {
	
	public LabelTexto(TipoTexto tipoTexto, String text, int x, int y, int larg, int alt) {
		switch (tipoTexto) {
			case TipoTexto.TITULO: 
				setText(text);
				setBounds(x, y, larg, alt);	
				setForeground(Utils.COR_LETRAS);
				setOpaque(true);
				setBackground(Utils.COR_FUNDO_ESCURO);
				setFont(Utils.FONTE_GRANDE);
				
			case TipoTexto.TEXTO_NORMAL: 
				setText(text);
				setBounds(x, y, larg, alt);	
				setForeground(Utils.COR_LETRAS);
				setOpaque(true);
				setBackground(Utils.COR_FUNDO_ESCURO);
				setFont(Utils.FONTE_MEDIA);
				
		}
	}
}
