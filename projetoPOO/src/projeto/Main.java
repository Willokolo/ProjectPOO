package projeto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.mail.MessagingException;
import enums.Dificuldade;
import enums.TipoDePartida;
import exceptions.FormatoNaoEsperadoException;
import exceptions.JogadorJaExisteException;
import exceptions.LoginSenhaException;
import exceptions.LetraJaTentadaException;
import exceptions.NaoHaPalavrasCompativeisException;
import exceptions.PalavraJaExisteException;
import exceptions.PartidaJaCadastradaException;

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
					
					try {
						jogadorAtual = central.fazerLogin(login, senha);
						cadastrado = true;
					} catch (LoginSenhaException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			// BARRA LATERAL
			System.out.print("\n1- Cadastrar novo jogador\n"
					+ "2- Jogar partida solo\n"
					+ "3- Jogar duelo\n"
					+ "4- Ver ranking dos jogadores\n"
					+ "5- Ver histórico de partidas\n"
					+ "6- Pesquisar jogadores por nome\n"
					+ "7- Ver estatísticas\n"
					+ "8- Opções de administrador\n"
					+ "S- Sair\n"
					+ "Opção: ");
			String opcao = input.nextLine();
			
			switch(opcao) {
			case "1": // CADASTRAR NOVO JOGADOR
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
				
			case "2": // JOGAR MODO SOLO
				// TELA MENU SOLO
				
				if(central.getPalavras().isEmpty()) {
					System.out.println("Não há palavras salvas na base de vocábulos, não é possível jogar no momento.");
					break;
				}
				
				Dificuldade d = escolherDificuldade(input);
				
				if (d == null)
					break;
				
				try {
					// TELA INICIO SOLO
					
					Palavra palavra = central.sorteiaPalavra(d, jogadorAtual);
					
					System.out.println("Iniciando partida " + d + "...");
					PartidaSolo partida = new PartidaSolo(jogadorAtual, palavra);
					central.addPartida(partida);
					jogadorAtual.addPalavraVista(palavra);
					JogoDaForca rodada = partida.getRodada();
					while(!rodada.terminou()) {
						System.out.println(rodada.getGabarito() + " Dica: " + rodada.getDica());
						
						System.out.print("Letra: ");
						String entrada = input.nextLine();
						
						if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
							char letra = entrada.charAt(0);
							Tentativa tentativa = new Tentativa(jogadorAtual, letra);
							
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
					if (rodada.estaEliminado(jogadorAtual)) {
						// ALGUMA TELA
						
						System.out.println("Você perdeu!");
					} else {
						// ALGUMA TELA
						
						System.out.println("Você venceu!");
					}
					System.out.println("A palavra era: " + rodada.getPalavra());
					System.out.println("Pontos: " + partida.getPontos()
								+ "\nErros: " + rodada.getErros(jogadorAtual));
					
					p.salvarCentral(central);
					
				} catch (NaoHaPalavrasCompativeisException | PartidaJaCadastradaException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "3": // JOGAR MODO DUELO
				// TELA INICIO DUELO
				
				if(central.getPalavras().isEmpty()) {
					System.out.println("Não há palavras salvas na base de vocábulos, não é possível jogar no momento.");
					break;
				}
				if (central.getJogadores().size() <= 1) {
					System.out.println("Não há jogadores cadastrados o suficiente.");
					break;
				}
				
				ArrayList<String> nomes = new ArrayList<String>();
				for (Jogador j: central.getJogadores()) {
					if (!j.equals(jogadorAtual))
						nomes.add(j.getNome());
				}
				
				System.out.println("Escolha um jogador para jogar contra:");
				System.out.println(String.join(", ", nomes));
				
				Jogador adversario = null;
				System.out.print("\nEscolha: ");
				String nome = input.nextLine();
				
				if (nome.equals(jogadorAtual.getNome())) {
					System.out.println("Você não pode jogar contra si mesmo.");
					break;
				}
				
				try {
					central.recuperarJogador(nome);
				} catch(LoginSenhaException e) {
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
					} catch (LoginSenhaException e) {
						System.out.println(e.getMessage());
					}
				}
				
				if (adversario == null)
					break;
				
				Dificuldade dif = escolherDificuldade(input);
				
				if (dif == null)
					break;
				
				try {
					// TELA INICIO DUELO
					
					ArrayList<Palavra> palavras = central.sorteiaPalavra(dif, jogadorAtual, adversario);
					PartidaDuelo partida = new PartidaDuelo(jogadorAtual, adversario, palavras.get(0), palavras.get(1));
					central.addPartida(partida);
					
					System.out.println("Iniciando partida " + dif + "...");
					
					for (JogoDaForca j: partida.getRodadas()) {
						
						jogadorAtual.addPalavraVista(j.getPalavra());
						adversario.addPalavraVista(j.getPalavra());
						
						while (!j.terminou()) {
							Jogador jogadorDaVez = j.getJogadorDaVez();
							int errosJogadorDaVez = j.getErros(jogadorDaVez);
							
							System.out.println("Vez de " + jogadorDaVez + " (" + errosJogadorDaVez + " erros)");
							System.out.println(j.getGabarito() + " Dica: " + j.getDica());
							
							System.out.print("Letra: ");
							String entrada = input.nextLine();
							
							if (entrada.length() == 1 && Character.isLetter(entrada.charAt(0))) {
								char letra = entrada.charAt(0);
								Tentativa tentativa = new Tentativa(jogadorDaVez, letra);
								
								try {
									
									if (j.testarLetra(tentativa))
										System.out.println("A letra está presente na palavra!");
									else
										System.out.println("A letra não está presente na palavra! Trocando a vez...");
									
								} catch (LetraJaTentadaException e) {
									System.out.println(e.getMessage());
								}
								
							} else 
								System.out.println("Digite apenas uma letra.");
						}
						if (j.getVencedor() == null)
							System.out.println("Ninguém acertou a palavra.");
						else {
							System.out.println(j.getVencedor() + " venceu!");
							System.out.println(j.getPerdedor() + " perdeu a rodada!");
						}
							
						for (Jogador jo: j.getJogadores()) {
							System.out.println("\nEstatísticas da rodada\n"
									+ jo + "\nPontos: " + partida.getPontosRodada(jo)
									+ "\nErros: " + j.getErros(jo));
						}
					}
					if (partida.getVencedor() == null)
						System.out.println("Houve um empate!");
					else
						System.out.println(partida.getVencedor() + " venceu!");
					
					System.out.println("\nEstatísticas da partida\n" + partida.getResultado());
					p.salvarCentral(central);
					
				} catch (NaoHaPalavrasCompativeisException | PartidaJaCadastradaException e) {
					System.out.println(e.getMessage());
				}
				break;
			
			case "4": // VER RANKING DOS JOGADORES
				// INSERIR CÓDIGO
				break;
				
			case "5": // VER HISTÓRICO DE PARTIDAS
				// TELA HISTÓRICO
				
				System.out.println("\nHistórico de partidas");
				int n = 0;
				boolean jogou = false;
				for (Partida par: central.getPartidas()) {
					if (par.getJogadores().contains(jogadorAtual)) {
						jogou = true;
						System.out.println("\nPartida " + ++n + ":\n"
								+ "Data e Hora: " + par.getDataHora());
						
						System.out.print("Jogadores: ");
						for (int j = 0; j < par.getJogadores().size(); j++) {
							System.out.print(par.getJogadores().get(j));
							if (j < par.getJogadores().size() - 1)
								System.out.print(", ");
						}
						
						System.out.println("\nModo: " + par.getTipo());
						System.out.print("Palavras: ");
						for (int j = 0; j < par.getPalavras().size(); j++) {
							System.out.print(par.getPalavras().get(j));
							if (j < par.getPalavras().size() - 1)
								System.out.print(", ");
						}
						
						System.out.println("\nDificuldade: " + par.getDificuldade());
						System.out.print("Letras tentadas: ");
						for (int j = 0; j < par.getLetras().size(); j++) {
							System.out.print(par.getLetras().get(j) + " (");
							System.out.print((par.getTentativas().get(j).getAcertou() ? "acertou" : "errou") + ")");
							if (j < par.getLetras().size() - 1)
								System.out.print(", ");
						}
						
						if (par.getVencedor() != null)
							System.out.println("\nVencedor: " + par.getVencedor().getNome());
						else if (par.getTipo() == TipoDePartida.DUELO)
							System.out.println("\nVencedor: Houve um empate");
						else
							System.out.println("\nVencedor: Não houve um vencedor");
						System.out.println("Resultado\n" + par.getResultado());
					}
				}
				if (!jogou)
					System.out.println("Você não possui partidas cadastradas.");
				break;
				
			case "6": // PESQUISAR JOGADORES POR NOME
				// INSERIR CÓDIGO
				break;
				
			case "7": // VER ESTATÍSTICAS
				// INSERIR CÓDIGO
				break;
				
			case "8": // OPÇÕES DE ADM
				while(true) {
					System.out.print("\n1- Atualizar base de vocábulos\n"
							+ "2- Listar partidas por filtro especial\n"
							+ "3- Acesso detalhado ao perfil de um jogador\n"
							+ "4- Enviar mensagens\n"
							+ "5- Exportar planilha com dados dos jogadores\n"
							+ "S- Sair\n"
							+ "Opção: ");
					String opt = input.nextLine();
					
					if (opt.equals("1")) { // ATUALIZAR BASE DE VOCÁBULOS
						System.out.print("Nome do arquivo CSV: ");
						String arquivo = input.nextLine();
						
						try {
							for (Palavra pal: ExtratorPalavrasCSV.extrairPalavra(arquivo)) {
								central.addPalavra(pal);
							}
							System.out.println("Palavras extraidas com sucesso!");
							p.salvarCentral(central);
						} catch (FormatoNaoEsperadoException | PalavraJaExisteException e) {
							System.out.println(e.getMessage());
						} catch (IOException e) {
							System.out.println("O arquivo não existe.");
						}
					} else if (opt.equals("2")) { // LISTAR PARTIDAS POR FILTRO ESPECIAL
						// INSERIR CÓDIGO
					} else if (opt.equals("3")) { // ACESSO DETALHADO AO PERFIL DE UM JOGADOR
						// INSERIR CÓDIGO
					} else if (opt.equals("4")) { // ENVIAR MENSAGENS
						System.out.print("Assunto da mensagem: ");
						String assunto = input.nextLine();
						System.out.print("Mensgagem: ");
						String mensagem = input.nextLine();
						
						System.out.println("Enviando mensagens...");
						try {
							for (Jogador jogador: central.getJogadores()) {
								Mensageiro.enviarMensagem(jogador.getEmail(), assunto, mensagem);
								System.out.println("Mensagem envidada para: " + jogador.getNome());
							}
							System.out.println("Mensagens enviadas com sucesso!");
						} catch (MessagingException e) {
							System.out.println("Houve um erro durante o envio das mensagens.");
						}
					} else if (opt.equals("5")) { // EXPORTAR PLANILHA COM DADOS DOS JOGADORES
						// INSERIR CÓDIGO
					} else {
						System.out.println("Saindo...");
						break;
					}
				}
				break;
				
			case "S":
				System.out.println("Saindo...");
				sair = true;
				break;
			}
		}
	}
	
	
	private static Dificuldade escolherDificuldade(Scanner input) {
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
				System.out.println("Opção inválida.");
			}
		}
	}

}
