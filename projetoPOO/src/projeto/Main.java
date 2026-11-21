package projeto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import javax.mail.MessagingException;

import com.itextpdf.text.DocumentException;

import enums.Dificuldade;
import enums.TipoDePartida;
import exceptions.JogadorInativoException;
import exceptions.JogadorJaExisteException;
import exceptions.LoginSenhaException;
import exceptions.LetraJaTentadaException;
import exceptions.NaoHaPalavrasCompativeisException;
import exceptions.OpcaoInvalidaException;
import exceptions.PartidaJaCadastradaException;
import exceptions.RetornoVazioException;

public class Main {	
	public static void main(String[] args) {
		CentralDeInformacoes central;
		Persistencia p = new Persistencia();
		Scanner input = new Scanner(System.in);
		boolean cadastrado = false;
		Jogador jogadorAtual = null;
		
		if(!p.existe()) {
			central = new CentralDeInformacoes();
			p.salvarCentral(central);
		}
		
		central = p.recuperarCentral();
		boolean sair = false;
		while (!sair) {
			while(!cadastrado) {
				if(central.getJogadores().isEmpty()) {
					// TELA CADASTRO ADM
					
					System.out.print("Cadastrar um admistrador\n"
							+ "Nome: ");
					String nome = input.nextLine();
					System.out.print("Email: ");
					String email = input.nextLine();
					System.out.print("Senha: ");
					String senha = input.nextLine();
					
					try {
						jogadorAtual = new Jogador(nome, email, senha);
						cadastrado = central.addJogador(jogadorAtual);
						p.salvarCentral(central);
					} catch (JogadorJaExisteException e) {
						System.out.println(e.getMessage());
					}
				} else {
					// TELA LOGIN JOGADOR
					
					System.out.print("Login\n"
							+ "Nome ou Email: ");
					String login = input.nextLine();
					System.out.print("Senha (digite -1 se esqueceu sua senha): ");
					String senha = input.nextLine();
					
					if (senha.equals("-1")) {
						try {
							redefinirSenha(input, central);
							p.salvarCentral(central);
						} catch (LoginSenhaException | JogadorInativoException | RetornoVazioException e) {
							System.out.println(e.getMessage());
						} catch (MessagingException e) {
							System.out.println("Houve um erro durante o envio do email.");
						}
					} else {
						try {
							jogadorAtual = central.fazerLogin(login, senha);
							cadastrado = true;
						} catch (LoginSenhaException | JogadorInativoException e) {
							System.out.println(e.getMessage());
						}
					}
				}
			}
			
			// BARRA LATERAL
			System.out.print("\n1- Cadastrar novo jogador\n"
					+ "2- Jogar partida solo\n"
					+ "3- Jogar partida duelo\n"
					+ "4- Ver ranking dos jogadores\n"
					+ "5- Ver histórico de partidas\n"
					+ "6- Pesquisar jogadores por nome\n"
					+ "7- Ver estatísticas\n"
					+ "8- Opções de administrador\n"
					+ "S- Sair\n"
					+ "Opção: ");
			String opcao = input.nextLine();
			
			switch(opcao) {
			case "1": {// CADASTRAR NOVO JOGADOR
				// TELA CADASTRO NOVO JOGADOR
				while(true) {
					System.out.print("Cadastro de novo jogador (digite -1 para voltar)\n"
							+ "Nome: ");
					String nome = input.nextLine();
					System.out.print("Email: ");
					String email = input.nextLine();
					System.out.print("Senha: ");
					String senha = input.nextLine();
					
					if (senha.equals("-1") || email.equals("-1") || nome.equals("-1"))
						break;
					
					try {
						central.addJogador(nome, email, senha);
						p.salvarCentral(central);
						System.out.println("Cadastro bem sucedido!");
						break;
					} catch (JogadorJaExisteException e) {
						System.out.println(e.getMessage());
					}
				}
				System.out.println("Voltando...");
				break;
				
			} case "2": {// JOGAR MODO SOLO
				// TELA MENU SOLO
				
				if(central.getPalavras().isEmpty()) {
					System.out.println("Não há palavras salvas na base de vocábulos, não é possível jogar no momento.");
					break;
				}
				
				Dificuldade d = null;
				try {
					d = escolherDificuldade(input);
				} catch (OpcaoInvalidaException e) {
					System.out.println(e.getMessage());
				}
				
				if (d == null)
					break;
				
				try {
					// TELA INICIO SOLO
					
					jogarSolo(central, d, input, jogadorAtual);
					
					p.salvarCentral(central);
					
				} catch (NaoHaPalavrasCompativeisException | PartidaJaCadastradaException | JogadorInativoException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			} case "3": {// JOGAR MODO DUELO
				// TELA INICIO DUELO
				
				if(central.getPalavras().isEmpty()) {
					System.out.println("\nNão há palavras salvas na base de vocábulos, não é possível jogar no momento.");
					break;
				}
				if (central.getJogadores().size() <= 1) {
					System.out.println("\nNão há jogadores cadastrados o suficiente.");
					break;
				}
				
				listarJogadores(central.getJogadores(), jogadorAtual);
				
				Jogador adversario = null;
				System.out.print("\nEscolha: ");
				String nome = input.nextLine();
				
				if (nome.equals(jogadorAtual.getNome())) {
					System.out.println("Você não pode jogar contra si mesmo.");
					break;
				}
				
				try {
					central.recuperarJogador(nome);
				} catch(LoginSenhaException | JogadorInativoException e) {
					System.out.println(e.getMessage());
					break;
				}
				
				while (true){
					System.out.print("Confirmar presença (digite -1 para voltar)\n"
								+ "Senha: ");
					String senha = input.nextLine();
					
					if (senha.equals("-1"))
						break;
					
					try {
						adversario = central.fazerLogin(nome, senha);
						break;
					} catch (LoginSenhaException | JogadorInativoException e) {
						System.out.println(e.getMessage());
					}
				}
				
				if (adversario == null)
					break;
				
				Dificuldade dif = null;
				try {
					dif = escolherDificuldade(input);
				} catch (OpcaoInvalidaException e) {
					System.out.println(e.getMessage());
				}
				
				if (dif == null)
					break;
				
				try {
					// TELA INICIO DUELO
					
					jogarDuelo(central, dif, input, jogadorAtual, adversario);
					
					p.salvarCentral(central);
					
				} catch (NaoHaPalavrasCompativeisException | PartidaJaCadastradaException | JogadorInativoException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			} case "4": {// VER RANKING DOS JOGADORES
				
				System.out.println("\nRanking dos Jogadores");
				
				while (true) {
					System.out.print("\nEscolha o Ranking que quer ver (Digite -1 para voltar)\n"
							+ "1- SOLO\n"
							+ "2- DUELO\n"
							+ "Opção: ");
					String opt = input.nextLine();
					
					if (opt.equals("-1"))
						break;
					
					if (opt.equals("1")) {
						for (int i = 0; i < central.getRankingSolo().size(); i++) {
							Jogador j = central.getRankingSolo().get(i);
							
							String ativo = j.isAtivo() ? "(Ativo)" : "(Inativo)";
							String posicao = j.getPontosSolo() != 0 ? String.valueOf(i + 1) : "Nunca jogou";
							
							System.out.println(posicao + ": " + j + " " + ativo + ", Pontos: " + j.getPontosSolo());
						}
						
					} else if (opt.equals("2")) {
						for (int i = 0; i < central.getRankingDuelo().size(); i++) {
							Jogador j = central.getRankingDuelo().get(i);
							
							String ativo = j.isAtivo() ? "(Ativo)" : "(Inativo)";
							String posicao = j.getPontosDuelo() != 0 ? String.valueOf(i + 1) : "Nunca jogou";
							
							System.out.println(posicao + ": " + j + ", " + ativo + ", Pontos: " + j.getPontosDuelo());
						}

					} else {
						System.out.println("Opção inválida.");
					}
				}
				
				break;
				
			} case "5": {// VER HISTÓRICO DE PARTIDAS
				// TELA HISTÓRICO
				
				System.out.println("\nHistórico de partidas");
				
				historico(central.getPartidas(), jogadorAtual);
				
				break;
				
			} case "6": {// PESQUISAR JOGADORES POR NOME
				
				System.out.println("\nJogadores cadastrados");
				
				for (Jogador j: central.getJogadores()) {
					int rankingSolo = central.getRankingSolo().indexOf(j);
					int rankingDuelo = central.getRankingDuelo().indexOf(j);
					
					String ativo = j.isAtivo() ? "Ativo" : "Inativo";
					String solo = j.getPontosSolo() != 0 ? String.valueOf(rankingSolo + 1) : "Nunca jogou";
					String duelo = j.getPontosDuelo() != 0 ? String.valueOf(rankingDuelo + 1) : "Nunca jogou";
					
					System.out.println(j + ", Situação: " + ativo + ", Ranking Solo: " + solo + ", Ranking Duelo: " + duelo);
				}
				
				while (true) {
					System.out.print("\nPesquisar um jogador (Digite -1 para voltar): ");
					String pesquisa = input.nextLine();
					
					if (pesquisa.equals("-1"))
						break;
					
					boolean tem = false;
					
					System.out.println("\nResultado");
					for (Jogador j: central.getJogadores()) {
						if (j.getNome().contains(pesquisa)) {
							int rankingSolo = central.getRankingSolo().indexOf(j);
							int rankingDuelo = central.getRankingDuelo().indexOf(j);
							
							String ativo = j.isAtivo() ? "Ativo" : "Inativo";
							String solo = j.getPontosSolo() != 0 ? String.valueOf(rankingSolo + 1) : "Nunca jogou";
							String duelo = j.getPontosDuelo() != 0 ? String.valueOf(rankingDuelo + 1) : "Nunca jogou";
							
							System.out.println(j + ", Situação: " + ativo + ", Ranking Solo: " + solo + ", Ranking Duelo: " + duelo);
							tem = true;
						}
					}
					
					if (!tem)
						System.out.println("Não há jogadorem cadastrados com esse nome.");
				}
				break;
				
			} case "7": {// VER ESTATÍSTICAS
				System.out.println("\nEstatísticas");
				
				for (char letra = 'a'; letra <= 'z'; letra++) {
					int tentativas = 0;
					int erros = 0;
					int acertos = 0;
					
					for (Tentativa t: jogadorAtual.getTentativas()) {
						if (t.getLetra() == letra) {
							tentativas++;
							
							if (t.getAcertou())
								acertos++;
							else
								erros++;
						}
					}
					
					System.out.println(letra + ": Tentativas: " + tentativas + ", Acertos: " + acertos + ", Erros: " + erros);
				}
				
				System.out.println("\nPontos Solo: " + jogadorAtual.getPontosSolo()
								+ "\nPontos Duelo: " + jogadorAtual.getPontosDuelo());
					
				break;
				
			} case "8": {// OPÇÕES DE ADM
				if (!jogadorAtual.equals(central.getAdministrador())) {
					System.out.println("\nApenas o administrador tem acesso a essa função");
					break;
				}
				
				while(true) {
					System.out.print("\n" + central.getAdministrador() + "\n"
							+ "1- Atualizar base de vocábulos\n"
							+ "2- Listar partidas por filtro especial\n"
							+ "3- Acesso detalhado ao perfil de um jogador\n"
							+ "4- Enviar mensagens\n"
							+ "5- Gerar relatório das palavras\n"
							+ "6- Exportar planilha com dados dos jogadores\n"
							+ "S- Sair\n"
							+ "Opção: ");
					String opt = input.nextLine();
					
					if (opt.equals("1")) { // ATUALIZAR BASE DE VOCÁBULOS
						System.out.print("Nome do arquivo CSV: ");
						String arquivo = input.nextLine();
						
						try {
							System.out.println("Lendo arquivo...");
							ResultadoImportacao resultado = ExtratorPalavrasCSV.lerArquivo(arquivo, central);
							p.salvarCentral(central);
							
							System.out.println("Cadastradas: " + resultado.getCadastradas()
											+ "\nEditadas: " + resultado.getEditadas()
											+ "\nExcluidas: " + resultado.getExcluidas());
							
							if (!resultado.getRejeitadas().isEmpty()) {
								System.out.println("Linhas rejeitadas:");
								for (String linha: resultado.getRejeitadas())
									System.out.println(linha);
							}
								
						} catch (IOException e) {
							System.out.println("O arquivo não existe.");
						}
					} else if (opt.equals("2")) { // LISTAR PARTIDAS POR FILTRO ESPECIAL
						System.out.print("(Digite -1 para voltar)\n"
								+ "1- Filtrar por data\n"
								+ "2- Filtrar por jogador\n"
								+ "3- Filtar por data e jogador\n"
								+ "Opção: ");
						String opc = input.nextLine();
						
						switch (opc) {
						case "1": {
							
							System.out.println("Inicio");
							System.out.print("Dia: ");
							int diaI = Integer.valueOf(input.nextLine());
							System.out.print("Mês: ");
							int mesI = Integer.valueOf(input.nextLine());
							System.out.print("Ano: ");
							int anoI = Integer.valueOf(input.nextLine());
							System.out.print("Hora: ");
							int horaI = Integer.valueOf(input.nextLine());
							System.out.print("Minuto: ");
							int minutoI = Integer.valueOf(input.nextLine());
							
							LocalDateTime inicio = LocalDateTime.of(anoI, mesI, diaI, horaI, minutoI);
							
							System.out.println("Fim");
							System.out.print("Dia: ");
							int diaF = Integer.valueOf(input.nextLine());
							System.out.print("Mês: ");
							int mesF = Integer.valueOf(input.nextLine());
							System.out.print("Ano: ");
							int anoF = Integer.valueOf(input.nextLine());
							System.out.print("Hora: ");
							int horaF = Integer.valueOf(input.nextLine());
							System.out.print("Minuto: ");
							int minutoF = Integer.valueOf(input.nextLine());
							
							LocalDateTime fim = LocalDateTime.of(anoF, mesF, diaF, horaF, minutoF);
							
							try {
								ArrayList<Partida> selecionadas = partidasNaJanela(central.getPartidas(), inicio, fim);
								
								historico(selecionadas, null);
							} catch (RetornoVazioException e) {
								System.out.println(e.getMessage());
							}
							break;
							
						} case "2": {
							listarJogadores(central.getJogadores(), null);
							System.out.print("Escolha: ");
							
							String nome = input.nextLine();
							
							try {
								Jogador jogador = central.recuperarJogador(nome);
								historico(central.getPartidas(), jogador);
							} catch (LoginSenhaException | JogadorInativoException e) {
								System.out.println(e.getMessage());
							}
							break;
							
						} case "3": {
							System.out.println("Inicio");
							System.out.print("Dia: ");
							int diaI = Integer.valueOf(input.nextLine());
							System.out.print("Mês: ");
							int mesI = Integer.valueOf(input.nextLine());
							System.out.print("Ano: ");
							int anoI = Integer.valueOf(input.nextLine());
							System.out.print("Hora: ");
							int horaI = Integer.valueOf(input.nextLine());
							System.out.print("Minuto: ");
							int minutoI = Integer.valueOf(input.nextLine());
							
							LocalDateTime inicio = LocalDateTime.of(anoI, mesI, diaI, horaI, minutoI);
							
							System.out.println("Fim");
							System.out.print("Dia: ");
							int diaF = Integer.valueOf(input.nextLine());
							System.out.print("Mês: ");
							int mesF = Integer.valueOf(input.nextLine());
							System.out.print("Ano: ");
							int anoF = Integer.valueOf(input.nextLine());
							System.out.print("Hora: ");
							int horaF = Integer.valueOf(input.nextLine());
							System.out.print("Minuto: ");
							int minutoF = Integer.valueOf(input.nextLine());
							
							LocalDateTime fim = LocalDateTime.of(anoF, mesF, diaF, horaF, minutoF);
							
							try {
								ArrayList<Partida> selecionadas = partidasNaJanela(central.getPartidas(), inicio, fim);
								
								listarJogadores(central.getJogadores(), null);
								System.out.println("Escolha:");
								
								String nome = input.nextLine();
								
								Jogador jogador = central.recuperarJogador(nome);
								historico(selecionadas, jogador);
							} catch (RetornoVazioException | LoginSenhaException | JogadorInativoException e) {
								System.out.println(e.getMessage());
							}
							break;
							
						} case "-1":
							break;
						}
						
					} else if (opt.equals("3")) { // ACESSO DETALHADO AO PERFIL DE UM JOGADOR
						listarJogadores(central.getJogadores(), null);
						System.out.print("Escolha: ");
						String nome = input.nextLine();
						
						try {
							Jogador jogador = central.recuperarJogador(nome);
							
							System.out.println("Nome: " + jogador
											+ "\nEmail: " + jogador.getEmail());
							System.out.print("\n(Digite -1 para voltar)\n"
									+ "1- Mudar nome\n"
									+ "2- Mudar Email\n"
									+ "3- Desativar conta\n"
									+ "Opção: ");
							String op = input.nextLine();
							
							switch(op) {
							case "-1":
								break;
								
							case "1":
								System.out.print("Novo nome: ");
								String novoNome = input.nextLine();
								
								central.editarJogador(jogador, novoNome, jogador.getEmail());
								break;
								
							case "2":
								System.out.print("Novo Email: ");
								String novoEmail = input.nextLine();
								
								central.editarJogador(jogador, jogador.getNome(), novoEmail);
								break;
								
							case "3":
								System.out.println("Desativando conta...");
								
								jogador.desativar();
								
								System.out.println("Conta desativada.");
							}
							p.salvarCentral(central);
						} catch (LoginSenhaException | JogadorInativoException | JogadorJaExisteException e) {
							System.out.println(e.getMessage());
						}
					} else if (opt.equals("4")) { // ENVIAR MENSAGENS
						System.out.print("(Digite -1 para voltar)\n"
								+ "1- Mensagem para um jogador\n"
								+ "2- Mensagem para todos os jogadores\n"
								+ "Opção: ");
						String op = input.nextLine();
						
						if (op.equals("-1"))
							break;
						else if(op.equals("1")) {
							listarJogadores(central.getJogadores(), jogadorAtual);
							System.out.print("Escolha (Digite -1 para voltar): ");
							String nomeDestinatario = input.nextLine();
							
							if (nomeDestinatario.equals("-1"))
								break;
							
							try {
								Jogador destinatario = central.recuperarJogador(nomeDestinatario);
								System.out.print("Assunto da mensagem: ");
								String assunto = input.nextLine();
								System.out.print("Mensagem: ");
								String mensagem = input.nextLine();
								
								System.out.println("Enviando mensagem...");
								
								Mensageiro.enviarMensagem(destinatario.getEmail(), assunto, mensagem);
							} catch (LoginSenhaException | JogadorInativoException | MessagingException e) {
								System.out.println(e.getMessage());
							}
							
						} else if (op.equals("2")) {
							
							System.out.print("Assunto da mensagem: ");
							String assunto = input.nextLine();
							System.out.print("Mensagem: ");
							String mensagem = input.nextLine();
							
							System.out.println("Enviando mensagens...");
							try {
								for (Jogador jogador: central.getJogadores()) {
									if (jogador.isAtivo()) {
										Mensageiro.enviarMensagem(jogador.getEmail(), assunto, mensagem);
										System.out.println("Mensagem envidada para: " + jogador.getNome());
									}
								}
								System.out.println("Mensagens enviadas com sucesso!");
							} catch (MessagingException e) {
								System.out.println("Houve um erro durante o envio das mensagens.");
							}
						} else {
							System.out.println("Opção inválida.");
							break;
						}
							
						
					} else if(opt.equals("5")) { // EXPORTAR RELATÓRIO DAS PALAVRAS
						try {
							System.out.println("Gerando relatório...");
							GeradorDeRelatorios.gerarRelatorioPalavras(central.getPalavras());
							System.out.println("Relatório gerado com sucesso!");
							
						} catch (FileNotFoundException | DocumentException e) {
							System.out.println("Houve um erro durante a geração do relatório.");
						}
					
					} else if (opt.equals("6")) { // EXPORTAR PLANILHA COM DADOS DOS JOGADORES
						System.out.println("Gerando planilha...");
						
						try {
							GeradorDePlanilhas.gerarPlanilha(central.getJogadores());
							System.out.println("Planilha gerada com sucesso!");
							
						} catch (IOException e) {
							System.out.println("Houve um erro durante a geração da planilha.");
						}
						
					} else {
						System.out.println("Saindo...");
						break;
					}
				}
				break;
				
			} case "S":
				System.out.println("Saindo...");
				sair = true;
				break;
			}
		}
	}
	
	
	private static Dificuldade escolherDificuldade(Scanner input) throws OpcaoInvalidaException{
		while (true) {
			System.out.print("(digite -1 para voltar)\n"
					+ "1- Fácil\n"
					+ "2- Médio\n"
					+ "3- Difícil\n"
					+ "4- Aleatório\n"
					+ "Opção: ");
			String opcao = input.nextLine();
			
			if (opcao.equals("4"))
				opcao = String.valueOf((int)(Math.random() * 3) + 1);
			
			switch (opcao) {
			case "-1":
				return null;
				
			case "1":
				return Dificuldade.FACIL;
				
			case "2":
				return Dificuldade.NORMAL;
				
			case "3":
				return Dificuldade.DIFICIL;
				
			default:
				throw new OpcaoInvalidaException();
			}
		}
	}
	
	private static void jogarSolo(CentralDeInformacoes central, Dificuldade dificuldade, Scanner input, Jogador jogador)
						throws NaoHaPalavrasCompativeisException, PartidaJaCadastradaException, JogadorInativoException {
		
		if (!jogador.isAtivo())
			throw new JogadorInativoException(jogador);
		
		Palavra palavra = SorteadorDePalavras.sorteiaPalavra(central.getPalavras(), dificuldade, jogador);
		
		System.out.println("Iniciando partida " + dificuldade + "...");
		PartidaSolo partida = new PartidaSolo(jogador, palavra);
		central.addPartida(partida);
		jogador.addPalavraVista(palavra);
		JogoDaForca rodada = partida.getRodada();
		while(!rodada.terminou()) {
			System.out.println("\n" + rodada.getGabarito() + " Dica: " + rodada.getDica());
			
			System.out.print("Letra: ");
			String entrada = input.nextLine();
			
			if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
				char letra = entrada.charAt(0);
				Tentativa tentativa = new Tentativa(jogador, letra);
				jogador.addTentativa(tentativa);
				try {
					
					if (rodada.testarLetra(tentativa))
						System.out.println("A letra está presente na palavra!");
					else
						System.out.println("A letra não está presente na palavra!");
					
				} catch (LetraJaTentadaException e) {
					System.out.println(e.getMessage());
				}
				
			} else 
				System.out.println("Digite apenas uma letra.");
		}
		if (rodada.estaEliminado(jogador)) {
			// ALGUMA TELA
			
			System.out.println("Você perdeu!");
		} else {
			// ALGUMA TELA
			
			System.out.println("Você venceu!");
		}
		System.out.println("A palavra era: " + rodada.getPalavra());
		System.out.println("Pontos: " + partida.getPontos()
					+ "\nErros: " + rodada.getErros(jogador));
		
		jogador.addPontosSolo(partida.getPontos());

	}
	
	
	private static void jogarDuelo(CentralDeInformacoes central, Dificuldade dificuldade, Scanner input, Jogador jogador1, Jogador jogador2)
											throws NaoHaPalavrasCompativeisException, PartidaJaCadastradaException, JogadorInativoException {
		
		if (!jogador1.isAtivo())
			throw new JogadorInativoException(jogador1);
		if (!jogador2.isAtivo())
			throw new JogadorInativoException(jogador2);
		
		ArrayList<Palavra> palavras = SorteadorDePalavras.sorteiaPalavra(central.getPalavras(), dificuldade, jogador1, jogador2);
		PartidaDuelo partida = new PartidaDuelo(jogador1, jogador2, palavras.get(0), palavras.get(1));
		central.addPartida(partida);
		
		System.out.println("Iniciando partida " + dificuldade + "...");
		
		for (JogoDaForca rodada: partida.getRodadas()) {
			
			jogador1.addPalavraVista(rodada.getPalavra());
			jogador2.addPalavraVista(rodada.getPalavra());
			
			while (!rodada.terminou()) {
				Jogador jogadorDaVez = rodada.getJogadorDaVez();
				int errosJogadorDaVez = rodada.getErros(jogadorDaVez);
				
				System.out.println("\nVez de " + jogadorDaVez + " (" + errosJogadorDaVez + " erros)");
				System.out.println(rodada.getGabarito() + " Dica: " + rodada.getDica());
				
				System.out.print("Letra: ");
				String entrada = input.nextLine();
				
				if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
					char letra = entrada.charAt(0);
					Tentativa tentativa = new Tentativa(jogadorDaVez, letra);
					jogadorDaVez.addTentativa(tentativa);
					try {
						
						if (rodada.testarLetra(tentativa))
							System.out.println("A letra está presente na palavra!");
						else
							System.out.println("A letra não está presente na palavra! Trocando a vez...");
						
					} catch (LetraJaTentadaException e) {
						System.out.println(e.getMessage());
					}
					
				} else 
					System.out.println("Digite apenas uma letra.");
			}

			try {
				System.out.println("\n" + rodada.getVencedor() + " venceu!");
				System.out.println(rodada.getPerdedor() + " perdeu a rodada!");
			} catch (RetornoVazioException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("A palavra era: " + rodada.getPalavra());
				
			for (Jogador j: rodada.getJogadores()) {
				System.out.println("\nEstatísticas da rodada ("+ j + ")"
						+ "\nPontos: " + partida.getPontosRodada(j)
						+ "\nErros: " + rodada.getErros(j));
			}
		}

		try {
			System.out.println(partida.getVencedor() + " venceu!");
		} catch (RetornoVazioException e) {
			System.out.println(e.getMessage());
		}
	
		System.out.println("\nEstatísticas da partida\n" + partida.getResultado());
		
		for (Jogador j: partida.getJogadores()) {
			j.addPontosDuelo(partida.getPontosDuelo(j));
		}
	}
	
	private static ArrayList<Partida> partidasNaJanela(ArrayList<Partida> partidas, LocalDateTime inicio, LocalDateTime fim) throws RetornoVazioException{
		ArrayList<Partida> restantes = new ArrayList<Partida>();
		
		for (Partida p: partidas) {
			LocalDateTime data = p.getDataHora();
			if (!data.isBefore(inicio) && !data.isAfter(fim))
				restantes.add(p);
		}
		
		if (restantes.isEmpty())
			throw new RetornoVazioException("Não há partidas nessa janela de tempo.");
		
		return restantes;
	}
	
	private static void historico(ArrayList<Partida> partidas, Jogador jogador) {
		int n = 0;
		boolean jogou = false;
		for (Partida p: partidas) {
			if (p.getJogadores().contains(jogador) || jogador == null) {
				jogou = true;
				
				System.out.println("\nPartida " + ++n + ":\n"
						+ "Data e Hora: " + p.getDataHoraFormatada());
				
				System.out.println("Modo: " + p.getTipo());
				
				System.out.print("Jogadores: ");
				for (int j = 0; j < p.getJogadores().size(); j++) {
					System.out.print(p.getJogadores().get(j));
					if (j < p.getJogadores().size() - 1)
						System.out.print(", ");
				}

				System.out.print("\nDificuldade: " + p.getDificuldade());
				
				if (p.getTipo() == TipoDePartida.DUELO) {
					int i = 0;
					for (JogoDaForca rodada: p.getRodadas()) {
						System.out.print("\nRodada " + ++i);
						for (Jogador j: p.getJogadores()) {
							System.out.println("\nTentativas de " + j + " (" + rodada.getPontos(j) + " pontos):");
							
							try {
								int k = 0;
								for (Tentativa t: rodada.getTentativa(j)) {
									String acertou = t.getAcertou() ? "acertou" : "errou";
									
									System.out.print(t.getLetra() + " (" + acertou + ")");
									
									if (k++ < rodada.getTentativa(j).size() - 1)
										System.out.print(", ");
								}
							} catch (RetornoVazioException e) {
								System.out.print(e.getMessage());
							}
						}
						try {
							System.out.print("\nVencedor: " + rodada.getVencedor());
						} catch (RetornoVazioException e) {
							System.out.print(e.getMessage());
						}
					}
				} else {
					System.out.print("\nTentativas: ");
					int k = 0;
					for (Tentativa t: p.getTentativas()) {
						String acertou = t.getAcertou() ? "acertou" : "errou";
						
						System.out.print(t.getLetra() + " (" + acertou + ")");
						
						if (k < p.getTentativas().size() - 1)
							System.out.print(", ");
						k++;
					}
				}
				
				System.out.print("\nPalavras: ");
				for (int j = 0; j < p.getPalavras().size(); j++) {
					System.out.print(p.getPalavras().get(j));
					if (j < p.getPalavras().size() - 1)
						System.out.print(", ");
				}
			
				try {
					System.out.println("\nVencedor: " + p.getVencedor());
				} catch (RetornoVazioException e) {
					System.out.println("\nVencedor: " + e.getMessage());
				}
				System.out.println("Resultado\n" + p.getResultado());
			}
		}
		if (!jogou)
			System.out.println("Você não possui partidas cadastradas.");
	}
	
	private static void listarJogadores(ArrayList<Jogador> jogadores, Jogador jogador) {
		ArrayList<String> nomes = new ArrayList<String>();
		if (jogador != null) {
			for (Jogador j: jogadores) {
				if (!j.equals(jogador) && j.isAtivo())
					nomes.add(j.getNome());
			}
		} else {
			for (Jogador j: jogadores) {
				nomes.add(j.getNome());
			}
		}
		System.out.println("Lista de jogadores cadastrados:");
		System.out.println(String.join(", ", nomes));
	}
	
	private static void redefinirSenha(Scanner input, CentralDeInformacoes central) 
			throws LoginSenhaException, JogadorInativoException, MessagingException, RetornoVazioException {
		System.out.print("Email cadastrado: ");
		String email = input.nextLine();
		
		Jogador jogador = central.recuperarJogador(email);
		
		System.out.println("Enviando código...");
		
		CodigoDeConfirmacao codigo = new CodigoDeConfirmacao(email);
		
		System.out.print("Digite o código recebido por email: ");
		String c = input.nextLine();
		
		if (!codigo.confirmarCodigo(c)) 
			throw new RetornoVazioException("Código incorreto.");
		
		System.out.print("Nova senha: ");
		String senha = input.nextLine();
		
		jogador.redefinirSenha(senha);
		System.out.println("Senha alterada com sucesso!");
	}
}
