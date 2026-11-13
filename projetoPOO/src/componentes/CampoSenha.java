package componentes;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interfaces.Cores;
import interfaces.Icones;

public class CampoSenha extends JPasswordField {
	
	 public CampoSenha(String nome, int x, int y, int largura, int altura, JFrame frame) {

	        super(nome);
	        
	        setEchoChar((char) 0);
	        
			setBackground(Cores.COR_FUNDO);
			setForeground(Cores.COR_LETRAS);
			setMargin(new Insets(0, 15, 0, 15));

			setBounds(x, y, largura, altura);

	        final JLabel labelImagem = new JLabel(Icones.ICONE_OLHO_SENHA_ABERTO);
	        labelImagem.setBounds(largura - 45, (altura - 30) / 2, 30, 30);
	        labelImagem.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        
	        labelImagem.addMouseListener(new MouseAdapter() {
	        	private boolean visivel = true;
	        	
	        	public void mouseClicked(MouseEvent e) {
	        		visivel = !visivel;
	        		
	        		if (visivel) {
	        			setEchoChar((char) 0);
	        			labelImagem.setIcon(Icones.ICONE_OLHO_SENHA_ABERTO);
	        			
	        		} else if (!visivel) {
	        			setEchoChar('*');
	        			labelImagem.setIcon(Icones.ICONE_OLHO_SENHA_FECHADO);
	        			
	        		}
	        	}
	        });

	        this.add(labelImagem);
	 }
}
