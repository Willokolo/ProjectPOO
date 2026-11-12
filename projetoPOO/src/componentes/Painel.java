package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import projeto.Utils;

public class Painel extends JFrame{

<<<<<<< HEAD
	public Painel() {
		// o tamanho real da janela é 1200 x 675, mas por causa da barra de título e das bordas, é preciso aumentar o tamanho total dela
		// a barra de título tem 31 pixels e as outras bordas tem 8 pixels cada
		setSize(1216, 714);
=======
	private int largura = 1200;
	private int altura  = 675;
	
	public Painel(String title) {
		setSize(this.largura, this.altura);
>>>>>>> 84d0743 (Adicionei organização de pastas, para enums e painéis, furutamente desejando utilizar a pasta projeto apenas para as mains de teste e principal, também adicionei a classe LabelTexto que cria textos de forma padronizada)
		setLocationRelativeTo(null);
		setLayout(null);
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Utils.COR_FUNDO);
	}
}
