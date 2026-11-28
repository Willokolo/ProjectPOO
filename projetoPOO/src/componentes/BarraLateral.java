package componentes;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.itextpdf.text.DocumentException;

import interfaces.Cores;
import interfaces.Fontes;
import interfaces.Icones;
import ouvintes.OuvinteBotaoFundoVerde;
import ouvintes.OuvinteBotaoSemFundo;
import paineis.PainelCadastroNovoJogador;
import paineis.PainelHistoricoCompleto;
import paineis.PainelOpcoesAdministrador;
import paineis.PainelPrincipalMenuDuelo;
import paineis.PainelPrincipalMenuSolo;
import projeto.CentralDeInformacoes;
import projeto.ExtratorPalavrasCSV;
import projeto.GeradorDeRelatorios;
import projeto.Jogador;
import projeto.Partida;
import projeto.Persistencia;
import projeto.ResultadoImportacao;

public class BarraLateral extends Painel {
	// ATRIBUTOS FUNCIONAIS
	protected CentralDeInformacoes central;
	protected Persistencia persistencia;
	protected Jogador jogador;
	
	// LABELS
	private JLabel barra;
	private JLabel lbJogar;
	private JLabel lbJogadores;
	private JLabel lbNomeJogador;
	private JLabel lbIconeJogador;
	private JLabel lbAdministrador;
	
	// CAMPOS DE TEXTO
	private JTextField tfPesquisar;
	
	// BOTÕES CUSTOMIZADOS
	private JLabel btJogarSolo;
	private JLabel btJogarDuelo;
	private JLabel btLideranca;
	private JLabel btMensagens;
	private JLabel btEstatisticas;
	protected JLabel btHistorico;
	private JLabel btAddJogador;
	private JLabel btAdministrador;
	
	public BarraLateral(String title, CentralDeInformacoes central, Persistencia persistencia, Jogador jogador) {
		super(title);
		this.central = central;
		this.persistencia = persistencia;
		this.jogador = jogador;
		
		criarBotoesCustomizados();
		criarTextos();
		criarCamposTexto();
		criarBarra();
		setVisible(true);
		
	}
	
	public BarraLateral(String title) {
		super(title);
		
		criarBarra();
		setVisible(true);
	}
	
	private void criarBotoesCustomizados() {
		
		// BOTÕES GRANDES
		btJogarSolo = new BotaoGrande("SOLO", Icones.ICONE_SOLO, 10, 370, 180, 70);
		btJogarSolo.addMouseListener(new OuvinteBotaoFundoVerde() {
			
			public void mouseClicked(MouseEvent e) {
				JComponent j = (JComponent) e.getSource();
				j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
				
				dispose();
				
				new PainelPrincipalMenuSolo(central, persistencia, jogador);
			}
		});
		add(btJogarSolo);
		
		btJogarDuelo = new BotaoGrande("DUELO", Icones.ICONE_DUELO, 10, 450, 180, 70);
		btJogarDuelo.addMouseListener(new OuvinteBotaoFundoVerde() {
			
			public void mouseClicked(MouseEvent e) {
				JComponent j = (JComponent) e.getSource();
				j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
				
				dispose();
				
				new PainelPrincipalMenuDuelo(central, persistencia, jogador);
			}
		});
		add(btJogarDuelo);
		
		
		// BOTÕES MÉDIOS
		btHistorico = new BotaoMedio("Histórico", Icones.ICONE_HISTORICO, 10, 130, 180, 30);
		btHistorico.addMouseListener(new OuvinteBotaoSemFundo() {
			
			public void mouseClicked(MouseEvent e) {
				JComponent j = (JComponent) e.getSource();
				j.setBackground(Cores.COR_PRIMARIA_COMPLEMENTAR);
				
				dispose();
				ArrayList<Partida> partidas = central.getPartidas(jogador);
				new PainelHistoricoCompleto(central, persistencia, jogador, partidas);
			}
		});
		add(btHistorico);
		
		btEstatisticas = new BotaoMedio("Estatísticas", Icones.ICONE_ESTATISTICAS, 10, 170, 180, 30);
		btEstatisticas.addMouseListener(new OuvinteBotaoSemFundo() {
			
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Nada ainda", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btEstatisticas);
		
		btMensagens = new BotaoMedio("Mensagens", Icones.ICONE_MENSAGENS, 10, 210, 180, 30);
		btMensagens.addMouseListener(new OuvinteBotaoSemFundo() {
			
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Nada ainda", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btMensagens);
		
		btLideranca = new BotaoMedio("Liderança", Icones.ICONE_LIDERANCA, 10, 250, 180, 30);
		btLideranca.addMouseListener(new OuvinteBotaoSemFundo() {
			
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Nada ainda", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btLideranca);
		
		if (jogador.equals(central.getAdministrador())) {
			btAdministrador = new BotaoMedio("Administrador", Icones.ICONE_LIDERANCA, 10, 290, 180, 30);
			btAdministrador.addMouseListener(new OuvinteBotaoSemFundo() {
				
				public void mouseClicked(MouseEvent e) {
					dispose();
					new PainelOpcoesAdministrador(central, persistencia, jogador);
				}
			});
			add(btAdministrador);
		}
		
		
		// BOTÕES PEQUENOS
		btAddJogador = new BotaoPequeno("+ Adicionar Novo Jogador", 10, 640, 180, 25);
		btAddJogador.addMouseListener(new OuvinteBotaoSemFundo() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();
				
				new PainelCadastroNovoJogador(central, persistencia);
			}
		});
		add(btAddJogador);
	}
	
	private void criarTextos() {
		
		
		lbJogar = new LabelTexto(Fontes.FONTE_GRANDE, "Jogar", 10, 325, 180, 40);
		add(lbJogar);

		lbJogadores = new LabelTexto(Fontes.FONTE_MEDIA, "Jogadores", 10, 550, 180, 30);
		add(lbJogadores);

		lbNomeJogador = new LabelTexto(Fontes.FONTE_MEDIA, jogador.getNome(), 10, 70, 180, 30);
		add(lbNomeJogador);
		
		if(jogador.equals(central.getAdministrador())) {
			lbAdministrador = new LabelTexto(Fontes.FONTE_PEQUENA, "Administrador", 10, 90, 180, 30);
			add(lbAdministrador);
		}
		
		String icone = String.valueOf(jogador.getNome().charAt(0)) + jogador.getNome().charAt(3);
		lbIconeJogador = new LabelTexto(Fontes.FONTE_GRANDE, icone.toUpperCase(), 75, 15, 50, 50);
		lbIconeJogador.setBackground(Cores.COR_PRIMARIA);
		lbIconeJogador.setForeground(Cores.COR_FUNDO_ESCURO);
		lbIconeJogador.setOpaque(true);
		add(lbIconeJogador);
	}
	
	private void criarCamposTexto() {
		tfPesquisar = new CampoPadrao("Pesquisar", 10, 590, 180, 40);
		add(tfPesquisar);
	}
	
	private void criarBarra() {
		barra = new JLabel();
		barra.setBounds(0, 0, 200, 675);
		barra.setOpaque(true);
		barra.setBackground(Cores.COR_FUNDO_ESCURO);
		add(barra);
	}
}
