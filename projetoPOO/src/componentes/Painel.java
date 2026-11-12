package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import projeto.Utils;

public class Painel extends JFrame{

	public Painel() {
		// o tamanho real da janela é 1200 x 675, mas por causa da barra de título e das bordas, é preciso aumentar o tamanho total dela
		// a barra de título tem 31 pixels e as outras bordas tem 8 pixels cada
		setSize(1216, 714);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Utils.COR_FUNDO);
	}
	
}
