package paineis;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import interfaces.Cores;
import projeto.BarraLateral;

public class PainelPrincipalMenuSolo extends BarraLateral {

	private JLabel lbMenuFundoLateralDireito;
	private JLabel lbMenuLateralDireitoHistorico;
	private JLabel lbMenuLateralDireitoDificuldade;
	private boolean historicoAtivo = false;
	private boolean dificuldadeAtivo = true;
	
	public PainelPrincipalMenuSolo() {
		super("Painel Principal - Menu Solo");
		
		criarMenuLateral();
		
		setVisible(true);
	}
	
	public void criarMenuLateral() {
		lbMenuFundoLateralDireito = new JLabel();
		lbMenuFundoLateralDireito.setBackground(Cores.COR_FUNDO_ESCURO);
		lbMenuFundoLateralDireito.setBounds(900, 40, 255, 594);
		lbMenuFundoLateralDireito.setOpaque(true);

		add(lbMenuFundoLateralDireito, 2);
		
		if (historicoAtivo == false && dificuldadeAtivo == false 
				|| historicoAtivo == true && dificuldadeAtivo == true
				|| historicoAtivo == false && dificuldadeAtivo == true) {
			
			criarMenuLateralDificuldadeAtivo();
			
		} else if (historicoAtivo == false && dificuldadeAtivo == true) {
			
			criarMenuLateralHistoricoAtivo();
			
		} 
		
	}
	
	public void criarMenuLateralDificuldadeAtivo() {
		lbMenuLateralDireitoHistorico = new JLabel();
		lbMenuLateralDireitoHistorico.setBackground(Cores.MENU_DESATIVADO);
		lbMenuLateralDireitoHistorico.setBounds(1028, 40, 127, 96);
		lbMenuLateralDireitoHistorico.setOpaque(true);
		lbMenuLateralDireitoHistorico.addMouseListener(new OuvinteMenuLateralDireito());
		
		
		lbMenuLateralDireitoDificuldade = new JLabel();
		lbMenuLateralDireitoDificuldade.setBackground(Cores.COR_FUNDO_ESCURO);
		lbMenuLateralDireitoDificuldade.setBounds(900, 35, 128, 104);
		lbMenuLateralDireitoDificuldade.setOpaque(true);
		lbMenuLateralDireitoDificuldade.addMouseListener(new OuvinteMenuLateralDireito());
		
		add(lbMenuLateralDireitoHistorico, 1);
		add(lbMenuLateralDireitoDificuldade, 0);

	}
	
	public void criarMenuLateralHistoricoAtivo() {
		lbMenuLateralDireitoDificuldade = new JLabel();
		lbMenuLateralDireitoDificuldade.setBackground(Cores.MENU_DESATIVADO);
		lbMenuLateralDireitoDificuldade.setBounds(1028, 40, 127, 96);
		lbMenuLateralDireitoDificuldade.setOpaque(true);
		lbMenuLateralDireitoDificuldade.addMouseListener(new OuvinteMenuLateralDireito());
		
		
		lbMenuLateralDireitoHistorico = new JLabel();
		lbMenuLateralDireitoHistorico.setBackground(Cores.COR_FUNDO_ESCURO);
		lbMenuLateralDireitoHistorico.setBounds(900, 35, 128, 104);
		lbMenuLateralDireitoHistorico.setOpaque(true);
		lbMenuLateralDireitoHistorico.addMouseListener(new OuvinteMenuLateralDireito());
		
		add(lbMenuLateralDireitoHistorico, 1);
		add(lbMenuLateralDireitoDificuldade, 0);

	}
	
	private class OuvinteMenuLateralDireito extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (historicoAtivo == false && dificuldadeAtivo == false 
					|| historicoAtivo == true && dificuldadeAtivo == true
					|| historicoAtivo == false && dificuldadeAtivo == true) {
				
				dificuldadeAtivo = true;
				historicoAtivo = false;
				
			} else if (historicoAtivo == false && dificuldadeAtivo == true) {
				
				historicoAtivo = true;
				dificuldadeAtivo = false;
				
			} 
		}
	}
	
	public static void main(String[] args) {
		new PainelPrincipalMenuSolo();
	}

}
