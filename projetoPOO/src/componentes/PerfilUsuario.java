package componentes;

import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaces.Cores;
import interfaces.Fontes;

public class PerfilUsuario extends JLabel {

	private LabelTexto textoNome;

	public PerfilUsuario(ImageIcon icone, String nome, int x, int y, int largura, int altura) {
		Image imagem = icone.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);

		setIcon(new ImageIcon(imagem));
		setBounds(x, y, largura, altura);
		setBackground(Cores.COR_PRIMARIA);
		setOpaque(true);
	}
}
