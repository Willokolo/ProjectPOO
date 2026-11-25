package ouvintes;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import interfaces.Cores;

public abstract class OuvinteBotaoFundoVerde implements MouseListener{

	public abstract void mouseClicked(MouseEvent e);

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {
		JComponent j = (JComponent) e.getSource();
		j.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		j.setBackground(Cores.COR_BOTAO_HOVER);
	}

	public void mouseExited(MouseEvent e) {
		JComponent j = (JComponent) e.getSource();
		j.setBackground(Cores.COR_PRIMARIA);
	}

}
