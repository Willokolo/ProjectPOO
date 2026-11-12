package projeto;

import javax.swing.SwingUtilities;

public class MainLogin {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			 new PainelLogin();
		});
	
	}
}
