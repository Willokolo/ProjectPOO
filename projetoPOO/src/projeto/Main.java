package projeto;

import java.io.IOException;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import enums.Dificuldade;
import exceptions.FormatoNaoEsperadoException;
import exceptions.JogadorJaExisteException;
import exceptions.JogadorNaoExisteException;
import exceptions.NaoHaPalavrasNestaDificuladeException;
import exceptions.PalavraJaExisteException;

public class Main {

	public static void main(String[] args) {
		CentralDeInformacoes central;
		Persistencia p = new Persistencia();
		Scanner input = new Scanner(System.in);
		boolean cadastrado = false;
		String jogadorAtual = null;
		JogoDaForca jogo;
		
		if(!p.existe()) {
			central = new CentralDeInformacoes();
			p.salvarCentral(central);
		}
		
		boolean sair = false;
		while (!sair) {
			central = p.recuperarCentral();
			
			while(!cadastrado) {
				if(central.getTodosOsJogadores().isEmpty()) {
					// TELA CADASTRO ADM
					
					System.out.print("Cadastrar um admistrador\n"
							+ "Nome: ");
					String nome = input.nextLine();
					System.out.print("Email: ");
					String email = input.nextLine();
					System.out.print("Senha: ");
					String senha = input.nextLine();
					
					try {
						cadastrado = central.adicionarJogador(nome, email, senha);
						jogadorAtual = nome;
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
						cadastrado = central.fazerLogin(login, senha);
						jogadorAtual = login;
						p.salvarCentral(central);
					} catch (JogadorNaoExisteException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
			// BARRA LATERAL
			System.out.print("1- Cadastrar novo jogador\n"
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
			case "1":
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
						central.adicionarJogador(nome, email, senha);
						p.salvarCentral(central);
						System.out.println("Cadastro bem sucedido!");
						break;
					} catch (JogadorJaExisteException e) {
						System.out.println(e.getMessage());
					}
				}
				System.out.println("Voltando...");
				break;
				
			case "2":
				// TELA MENU SOLO
				
				if(central.getPalavras().isEmpty()) {
					System.out.println("Não há palavras salvas na base de vocábulos, não é possível jogar no momento.");
					break;
				}
				
				Dificuldade d;
				System.out.print("(digite -1 para voltar)\n"
						+ "1- Fácil\n"
						+ "2- Médio\n"
						+ "3- Difícil\n"
						+ "4- Aleatório\n"
						+ "Opção: ");
				String op = input.nextLine();
				
				if(op.equals("-1"))
					break;
				if (op.equals("4"))
					op = String.valueOf((int)(Math.random() * 3) + 1);
				
				if (op.equals("1"))
					d = Dificuldade.FACIL;
				else if (op.equals("2"))
					d = Dificuldade.NORMAL;
				else
					d = Dificuldade.DIFICIL;
				
				try {
					// TELA INICIO SOLO
					
					Palavra palavra = central.sorteiaPalavra(d);
					central.recuperarJogador(jogadorAtual).addPalavraVista(palavra);
					
					System.out.println("Iniciando partida " + d + "...");
					jogo = new JogoDaForca(palavra, d);
					while(!jogo.acabou() || jogo.getErros() >= 6) {
						System.out.println(jogo.getGabarito());
						
						System.out.print("Letra: ");
						String entrada = input.nextLine();
						
						if (entrada.length() == 1) {
							char letra = entrada.charAt(0);
							
							if (jogo.testarLetra(letra))
								System.out.println("A letra está presente na palavra!");
							else
								System.out.println("A letra não está presente na palavra!");
							
						} else 
							System.out.println("A entrada possui mais de um caractere. Por favor, insira apenas uma letra.");
					}
					if (jogo.getErros() >= 8)
						// ALGUMA TELA
						
						System.out.println("Você perdeu!");
					else
						// ALGUMA TELA
						
						System.out.println("Você venceu!");
					System.out.println("Pontos: " + jogo.getPontosTotais()
								+ "\n Erros: " + jogo.getErros());
					
				} catch (NaoHaPalavrasNestaDificuladeException | JogadorNaoExisteException e) {
					System.out.println(e.getMessage());
				}
				break;
				
			case "3":
				// INSERIR CÓDIGO
				break;
				
			case "4":
				// INSERIR CÓDIGO
				break;
				
			case "5":
				// INSERIR CÓDIGO
				break;
				
			case "6":
				// INSERIR CÓDIGO
				break;
				
			case "7":
				// INSERIR CÓDIGO
				break;
				
			case "8":
				while(true) {
					System.out.print("1- Atualizar base de vocábulos\n"
							+ "2- Listar partidas por filtro especial\n"
							+ "3- Acesso detalhado ao perfil de um jogador\n"
							+ "4- Enviar mensagens\n"
							+ "5- Exportar planilha com dados dos jogadores\n"
							+ "S- Sair\n"
							+ "Opção: ");
					String opt = input.nextLine();
					
					if (opt.equals("1")) {
						System.out.print("Nome do arquivo CSV: ");
						String arquivo = input.nextLine();
						
						try {
							for (Palavra pal: ExtratorPalavrasCSV.extrairPalavra(arquivo)) {
								central.adicionarPalavra(pal);
							}
							System.out.println("Palavras extraidas com sucesso!");
							p.salvarCentral(central);
						} catch (FormatoNaoEsperadoException | PalavraJaExisteException e) {
							System.out.println(e.getMessage());
						} catch (IOException e) {
							System.out.println("O arquivo não existe.");
						}
					} else if (opt.equals("2")) {
						// INSERIR CÓDIGO
					} else if (opt.equals("3")) {
						// INSERIR CÓDIGO
					} else if (opt.equals("4")) {
						System.out.print("Assunto da mensagem: ");
						String assunto = input.nextLine();
						System.out.print("Mensgagem: ");
						String mensagem = input.nextLine();
						
						System.out.println("Enviando mensagens...");
						try {
							for (Jogador jogador: central.getTodosOsJogadores()) {
								Mensageiro.enviarMensagem(jogador.getEmail(), assunto, mensagem);
								System.out.println("Mensagem envidada para: " + jogador.getNome());
							}
							System.out.println("Mensagens enviadas com sucesso!");
						} catch (MessagingException e) {
							System.out.println("Houve um erro durante o envio das mensagens.");
						}
					} else if (opt.equals("5")) {
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

}
