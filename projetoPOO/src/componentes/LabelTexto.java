package componentes;

import javax.swing.JLabel;

import enums.TipoTexto;
import interfaces.*;
public class LabelTexto extends Texto {
	
	public LabelTexto(TipoTexto tipoTexto, String text, int x, int y, int larg, int alt) {
		switch (tipoTexto) {
			case TipoTexto.TITULO: 
				setText(text);
				setBounds(x, y, larg, alt);	
				setFont(FontSize.FONTE_GRANDE);
				break;
				
			case TipoTexto.TEXTO_NORMAL: 
				setText(text);
				setBounds(x, y, larg, alt);	
				setFont(FontSize.FONTE_MEDIA);
				break;
				
			case TipoTexto.TEXTO_PEQUENO: 
				setText(text);
				setBounds(x, y, larg, alt);	
				setFont(FontSize.FONTE_PEQUENA);
				break;
				
			case TipoTexto.TEXTO_PEQUENO_BOLD: 
				setText(text);
				setBounds(x, y, larg, alt);	
				setFont(FontSize.FONTE_PEQUENA_BOLD);
				break;

		}
	}
}
