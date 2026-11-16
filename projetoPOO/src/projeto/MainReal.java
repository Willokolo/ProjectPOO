package projeto;

import java.util.Scanner;

import enums.Dificuldade;
import exceptions.JogadorJaExisteException;
import exceptions.JogadorNaoExisteException;
import exceptions.NaoHaPalavrasNestaDificuladeException;

public class MainReal {

	public static void main(String[] args) {
		CentralDeInformacoes central;
		Persistencia p = new Persistencia();
		Scanner input = new Scanner(System.in);
		boolean cadastrado = false;
		String jogadorAtual = null;
		
		if(!p.existe()) {
			central = new CentralDeInformacoes();
			p.salvarCentral(central);
		}
		
		while (true) {
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
					+ "S- Sair"
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
				// TELA INICIO JOGO SOLO
				
				if(central.getPalavras().isEmpty()) {
					System.out.println("Não há palavras salvas na base de vocábulos, não é possível jogar no momento.");
					break;
				}
				
				System.out.print("(digite -1 para voltar)"
						+ "1- Fácil\n"
						+ "2- Médio\n"
						+ "3- Difícil\n"
						+ "Opção: ");
				String op = input.nextLine();
				
				if(op.equals("-1"))
					break;
				
				Dificuldade d = op.equals("1") ? Dificuldade.fácil: op.equals("2") ? Dificuldade.médio: Dificuldade.difícil;
				
				try {
					Palavra palavra = central.sorteiaPalavra(d);
					central.recuperarJogador(jogadorAtual).addPalavraVista(palavra);
					
					
				} catch (NaoHaPalavrasNestaDificuladeException | JogadorNaoExisteException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

}
