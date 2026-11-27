package paineis;

import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import ouvintes.OuvinteBotaoFundoVerde;
import projeto.CentralDeInformacoes;
import projeto.Jogador;
import projeto.Palavra;
import projeto.Persistencia;
import projeto.SorteadorDePalavras;
import componentes.LabelTexto;
import componentes.PerfilUsuario;
import enums.Dificuldade;
import exceptions.NaoHaPalavrasCompativeisException;

public class PainelPrincipalMenuSolo extends PainelPrincipalMenu {
	// ===================== Usuário =====================
	private JLabel fotoUsuario;
	private JLabel textoNomeUsuario;
	private String nomeUsuario;
	
	public PainelPrincipalMenuSolo(CentralDeInformacoes central, Persistencia persistencia, Jogador jogador) {
		super("Painel Principal - Menu Duelo", central, persistencia, jogador);
		
		criarUsuario();
		botaoIniciarPartida.addMouseListener(new OuvinteIniciar());
	}
	
	private void criarUsuario() {
		nomeUsuario = jogador.getNome();
		
		fotoUsuario = new PerfilUsuario(Icones.ICONE_HISTORICO, nomeUsuario, 475, 180, 150, 150);
		textoNomeUsuario = new LabelTexto(Fontes.FONTE_MEDIA, nomeUsuario, 475, 295, 150, 100);

		add(textoNomeUsuario);
		add(fotoUsuario);
	}
	
	private class OuvinteIniciar extends OuvinteBotaoFundoVerde{

		public void mouseClicked(MouseEvent e) {
			JComponent j = (JComponent) e.getSource();
			j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
			
			if (!jogador.isAtivo()) {
				JOptionPane.showMessageDialog(null, "O jogador atual está inativo", "Erro", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				
				if (dificuldadeSelecionada == Dificuldade.ALEATORIO) {
					int aleatorio = (int)(Math.random() * 3) + 1;
					
					switch (aleatorio) {
					case 1:
						dificuldadeSelecionada = Dificuldade.FACIL;
						break;
						
					case 2:
						dificuldadeSelecionada = Dificuldade.FACIL;
						break;
						
					case 3:
						dificuldadeSelecionada = Dificuldade.FACIL;
						break;
					}
				}
				
				Palavra palavra = SorteadorDePalavras.sorteiaPalavra(central.getPalavras(), dificuldadeSelecionada, jogador);
				dispose();
				
				new PainelPrincipalInicioSolo(central, persistencia, jogador, palavra);
			} catch (NaoHaPalavrasCompativeisException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}