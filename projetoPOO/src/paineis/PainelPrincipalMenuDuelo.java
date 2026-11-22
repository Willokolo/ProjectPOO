package paineis;

import javax.swing.JLabel;

import componentes.LabelTexto;
import componentes.PerfilUsuario;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;

public class PainelPrincipalMenuDuelo extends PainelPrincipalMenuSolo {
	
	private JLabel labelFotoUsuario1;
	private JLabel labelFotoUsuario2;
	private JLabel labelNomeUsuario1;
	private JLabel labelNomeUsuario2;
	private String nomeUsuario1 = "Wilokolo";
	private String nomeUsuario2 = "Raf3nix";

	public PainelPrincipalMenuDuelo() {
		super("Painel Principal - Menu Duelo");
		
		criarLabelUsuario();
	}
	
	public void criarLabelUsuario() {
		labelFotoUsuario1 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario1, 325, 180, 150, 150);
	
		labelNomeUsuario1 = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeUsuario1, 325, 295, 150, 100);
		
		labelFotoUsuario2 = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario2, 625, 180, 150, 150);
		
		labelNomeUsuario2 = new LabelTexto(Fontes.FONTE_MEDIA, this.nomeUsuario2, 625, 295, 150, 100);

		add(labelFotoUsuario1, 0);
		add(labelNomeUsuario1, 0);
		add(labelFotoUsuario2, 0);
		add(labelNomeUsuario2, 0);
	}
	
	public static void main(String[] args) {
		new PainelPrincipalMenuDuelo();
	}
	
}
