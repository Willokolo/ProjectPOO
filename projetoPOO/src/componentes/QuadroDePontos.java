package componentes;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import interfaces.Cores;
import interfaces.Fontes;

public class QuadroDePontos extends JLabel {
	public QuadroDePontos(String nome, int pontuacao, ImageIcon imagem, int x, int y) {
		super(nome, imagem, JLabel.LEFT);
		setFont(Fontes.FONTE_MEDIA);
		setBackground(Cores.COR_FUNDO_ESCURO);
		setOpaque(true);
		setBounds(x, y, 200, 100);
		setForeground(Cores.COR_LETRAS);
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setHorizontalAlignment(SwingConstants.LEFT);
		setVerticalAlignment(SwingConstants.TOP);

		JLabel labelPontuacao = new JLabel();
		labelPontuacao.setText(String.valueOf(pontuacao));;
		labelPontuacao.setBackground(Cores.COR_PRIMARIA);
		labelPontuacao.setForeground(Cores.COR_LETRAS);
		labelPontuacao.setFont(Fontes.FONTE_MEDIA);
		labelPontuacao.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		labelPontuacao.setOpaque(true);
		labelPontuacao.setBounds(10, 50, 180, 40);
		labelPontuacao.setHorizontalAlignment(SwingConstants.CENTER);
		labelPontuacao.setVerticalAlignment(SwingConstants.CENTER);
		add(labelPontuacao, 0);
	}
}
