package componentes;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import projeto.Utils;

public class Painel extends JFrame{

	public Painel() {
		setSize(1200, 675);
		setLocationRelativeTo(null);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Utils.COR_FUNDO);
		
	}
	
}
