package POOproject;

import javax.swing.JTextField;

public class Inputs {

	public static JTextField tfCampoPadrao(String cNome) {
		JTextField tfCampoPadrao = new JTextField(cNome);
		return tfCampoPadrao;
	}
	
	public static class tfCampoP extends JTextField {
		
		public tfCampoP() {
			
		}
	}
	
}
