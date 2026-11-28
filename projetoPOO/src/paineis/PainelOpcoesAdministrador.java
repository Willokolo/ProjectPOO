package paineis;

import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.itextpdf.text.DocumentException;

import componentes.BarraLateral;
import componentes.BotaoMedio;
import componentes.LabelTexto;
import interfaces.Cores;
import interfaces.Fontes;
import ouvintes.OuvinteBotaoFundoVerde;
import projeto.Aviso;
import projeto.CentralDeInformacoes;
import projeto.ExtratorPalavrasCSV;
import projeto.GeradorDePlanilhas;
import projeto.GeradorDeRelatorios;
import projeto.Jogador;
import projeto.Partida;
import projeto.Persistencia;
import projeto.ResultadoImportacao;

public class PainelOpcoesAdministrador extends BarraLateral {
	
	private JLabel fundoEscuro;
	private JLabel labelTitulo;
	
	private JLabel btVocabulos;
	private JLabel btPartidas;
	private JLabel btPerfil;
	private JLabel btMensagem;
	private JLabel btRelatorio;
	private JLabel btDadosJogadores;
	
	public PainelOpcoesAdministrador(CentralDeInformacoes central, Persistencia persistencia, Jogador jogador) {
		super("Painel Opções Administrador", central, persistencia, jogador);
		
		criarTitulo();
		criarBotoes();
		criarFundoEscuro();
	}
	
	private void criarFundoEscuro() {
		fundoEscuro = new JLabel();
		fundoEscuro.setBackground(Cores.COR_FUNDO_ESCURO);
		fundoEscuro.setBounds(425, 50, 550, 575);
		fundoEscuro.setOpaque(true);
		add(fundoEscuro);
	}
	
	private void criarTitulo() {
		labelTitulo = new LabelTexto(Fontes.FONTE_GRANDE, "Opções de Administrador", 425, 70, 550, 50);
		add(labelTitulo);
	}
	
	private void criarBotoes() {
		btVocabulos = new BotaoMedio("Atualizar base de vocábulos", 435, 150, 530, 50, Cores.COR_PRIMARIA);
		btVocabulos.addMouseListener(new OuvinteVocabulos());
		add(btVocabulos);
		
		btPartidas = new BotaoMedio("Listar partidas por filtro especial", 435, 215, 530, 50, Cores.COR_PRIMARIA);
		btPartidas.addMouseListener(new OuvinteFiltrarPartidas());
		add(btPartidas);
		
		btPerfil = new BotaoMedio("Acesso detalhado ao perfil de um jogador", 435, 280, 530, 50, Cores.COR_PRIMARIA);
		btPerfil.addMouseListener(new OuvinteBotaoFundoVerde() {
			
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Nada por enquanto", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(btPerfil);
		
		btMensagem = new BotaoMedio("Enviar mensagem aos jogadores", 435, 345, 530, 50, Cores.COR_PRIMARIA);
		btMensagem.addMouseListener(new OuvinteMensagem());
		add(btMensagem);
		
		btRelatorio = new BotaoMedio("Gerar relatório das palavras", 435, 410, 530, 50, Cores.COR_PRIMARIA);
		btRelatorio.addMouseListener(new OuvinteBotaoFundoVerde() {
			
			public void mouseClicked(MouseEvent e) {
				try {
					GeradorDeRelatorios.gerarRelatorioPalavras(central.getPalavras());
					JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!\nNome do arquivo: relatorioPalavras.pdf",
														"Atualização", JOptionPane.INFORMATION_MESSAGE);
				} catch (FileNotFoundException | DocumentException e1) {
					JOptionPane.showMessageDialog(null, "Houve um erro durante a geração do relatório", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(btRelatorio);
		
		btDadosJogadores = new BotaoMedio("Exportar planilha com dados dos jogadores", 435, 475, 530, 50, Cores.COR_PRIMARIA);
		btDadosJogadores.addMouseListener(new OuvinteBotaoFundoVerde() {
			
			public void mouseClicked(MouseEvent e) {
				try {
					GeradorDePlanilhas.gerarPlanilha(central.getJogadores());
					JOptionPane.showMessageDialog(null, "Planilha gerada com sucesso!\nNome do arquivo: dadosJogadores.xlsx",
														"Atualização", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Houve um erro durante a geração da planilha", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(btDadosJogadores);
	}
	
	private class OuvinteVocabulos extends OuvinteBotaoFundoVerde{
		
		public void mouseClicked(MouseEvent e) {
			String arquivo = JOptionPane.showInputDialog(null, "Nome do arquivo", "Insira o nome do arquivo", JOptionPane.QUESTION_MESSAGE);
			
			try {
				ResultadoImportacao resultado = ExtratorPalavrasCSV.lerArquivo(arquivo, central);
				
				GeradorDeRelatorios.gerarRelatorioImportacao(resultado);
				
				JOptionPane.showMessageDialog(null, "Palavras extraídas e relatório gerado", "Atualização", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | DocumentException e1) {
				JOptionPane.showMessageDialog(null, "Houve um erro durante a geração do relatório", "Erro", JOptionPane.ERROR_MESSAGE);
				
			} catch (NullPointerException e2) {
				return;
				
			} finally {
				persistencia.salvarCentral(central);
			}
		}
	}
	
	private class OuvinteFiltrarPartidas extends OuvinteBotaoFundoVerde{
		
		public void mouseClicked(MouseEvent e) {
			String[] opcoes = {"Por data", "Por jogador", "Por data e jogador"};
			
			int escolha = JOptionPane.showOptionDialog(null, "Como quer filtrar as partidas?", "Opções", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
			
			try {
				MaskFormatter mascara = new MaskFormatter("##/##/#### ##:##");
				mascara.setPlaceholderCharacter('_');
				
				DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				JFormattedTextField campoInicio = new JFormattedTextField(mascara);
				JFormattedTextField campoFim = new JFormattedTextField(mascara);
				
				Jogador[] jogadores = central.getJogadoresAtivos().toArray(new Jogador[0]);
				Jogador selecionado;
				ArrayList<Partida> partidas = null;
				
				if (escolha == 0) {
					Object[] mensagem = {"Data e hora de início", campoInicio,
							"Data e hora final", campoFim};
					
					int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Filtro por data",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (opcao == JOptionPane.OK_OPTION) {
						LocalDateTime dataInicio = LocalDateTime.parse(campoInicio.getText(), formato);
						LocalDateTime dataFim = LocalDateTime.parse(campoFim.getText(), formato);
						
						partidas = central.getPartidas(dataInicio, dataFim);
					}
				} else if (escolha == 1) {
					
					selecionado = (Jogador) JOptionPane.showInputDialog(null, "Filtro por jogador", "Jogadores",
							JOptionPane.QUESTION_MESSAGE, null, jogadores, jogadores[0]);
					
					partidas = central.getPartidas(selecionado);
				} else if (escolha == 2) {
					
					selecionado = (Jogador) JOptionPane.showInputDialog(null, "Filtro por jogador", "Jogadores",
							JOptionPane.QUESTION_MESSAGE, null, jogadores, jogadores[0]);
					
					Object[] mensagem = {"Data e hora de início", campoInicio,
										"Data e hora final", campoFim};
					
					int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Filtro por data e jogador",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					
					if (opcao == JOptionPane.OK_OPTION) {
						LocalDateTime dataInicio = LocalDateTime.parse(campoInicio.getText(), formato);
						LocalDateTime dataFim = LocalDateTime.parse(campoFim.getText(), formato);
						
						partidas = central.getPartidas(selecionado, dataInicio, dataFim);
					}
				}
				
				dispose();
				new PainelHistoricoCompleto(central, persistencia, jogador, partidas);
			} catch (ParseException e1) {
				JOptionPane.showMessageDialog(null, "Erro de formatação", "Erro", JOptionPane.ERROR_MESSAGE);
			} catch (DateTimeParseException e2) {
				JOptionPane.showMessageDialog(null, "Preencha a data e o horário completamente", "Erro", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e3) {
				JOptionPane.showMessageDialog(null, "Você deixou um campo vazio", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class OuvinteMensagem extends OuvinteBotaoFundoVerde{
		
		public void mouseClicked(MouseEvent e) {
			String[] opcoes = {"Para um jogador", "Para todos os jogadores"};
			
			int escolha = JOptionPane.showOptionDialog(null, "Para quem quer enviar a mensagem?", "Opções", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
			
			JTextField campoAssunto = new JTextField();
			JTextField campoMensagem = new JTextField();
			Jogador selecionado = null;
			
			try {
				if (escolha == 0) {
					Jogador[] jogadores = central.getJogadoresAtivos().toArray(new Jogador[0]);
					
					selecionado = (Jogador) JOptionPane.showInputDialog(null, "Filtro por jogador", "Jogadores",
							JOptionPane.QUESTION_MESSAGE, null, jogadores, jogadores[0]);
				}
				
				Object[] campos = {"Assunto", campoAssunto,
									"Mensagem", campoMensagem};
				int opcao = 0;
				
				if (escolha != JOptionPane.CLOSED_OPTION) {
					 opcao = JOptionPane.showConfirmDialog(null, campos, "Mensagem",
							JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				}
				if (opcao == JOptionPane.OK_OPTION) {
					String assunto = campoAssunto.getText();
					String mensagem = campoMensagem.getText();
					
					
					
					if (escolha == 0) {
						central.addAviso(selecionado, new Aviso(assunto, mensagem, true));
					} else if (escolha == 1) {
						for (Jogador j: central.getJogadoresAtivos()) {
							central.addAviso(j, new Aviso(assunto, mensagem, false));
						}
					}
					
					JOptionPane.showMessageDialog(null, "Mensagens enviadas com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				}
				
			} catch (NullPointerException e2) {
				JOptionPane.showMessageDialog(null, "Você deixou um campo vazio", "Erro", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
