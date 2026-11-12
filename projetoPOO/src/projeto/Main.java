package projeto;

import java.util.Scanner;

import enums.Sexo;

public class Main {
	public static void main(String[] args) {
		Persistencia p = new Persistencia();
		CentralDeInformacoes central;
		boolean rodar = true;
		Scanner input = new Scanner(System.in);
		
		if(!p.existe("central")) {
			central = new CentralDeInformacoes();
			p.salvarCentral(central, "central");
		}
		
		while(rodar) {
			central = p.recuperarCentral("central");
			
			System.out.print("1- Novo jogador\n"
					+ "2- Listar todos os jogadores\n"
					+ "3- Exibir informações de um jogador específico\n"
					+ "4- Salvar palavras a partir de um arquivo CSV\n"
					+ "5- Listar todas as palavras salvas na central\n"
					+ "6- Gerar um relatório em PDF\n"
					+ "7- Enviar uma mensagem para todos os jogadores\n"
					+ "S- Sair\n"
					+ "Opção: ");
			String opcao = input.nextLine();
			
			switch (opcao) {
			case "1":
				System.out.println("Nome:");
				String nome = input.nextLine();
				System.out.println("Sexo:");
				Sexo sexo = Sexo.valueOf(input.nextLine());
				System.out.println("CPF:");
				String CPF = input.nextLine();
				System.out.println("Email:");
				String email = input.nextLine();
				
				if (central.adicionarJogador(nome, sexo, CPF, email)) {
					System.out.println("O cadastro foi bem sucedido");
					p.salvarCentral(central, "central");
				} else {
					System.out.println("Houve um erro durante o cadastro");
				}
				break;
				
			case "2":
				if (central.getTodosOsJogadores().isEmpty()) {
					System.out.println("Não há jogadores salvos");
				} else
					for (Jogador j: central.getTodosOsJogadores()) {
						System.out.println(j);
					}
				break;
				
			case "3":
				System.out.print("1- Por Email\n"
						+ "2- Por CPF\n"
						+ "Opção: ");
				int op = Integer.valueOf(input.nextLine());
				
				Jogador j;
				switch (op) {
				case 1:
					System.out.println("Digite o Email:");
					String emailrec = input.nextLine();
					j = central.recuperarJogadorPorEmail(emailrec);
					System.out.println(central.listarInformacoesJogador(j));
					break;
					
				case 2:
					System.out.println("Digite o CPF:");
					String CPFrec = input.nextLine();
					j = central.recuperarJogadorPorCPF(CPFrec);
					System.out.println(central.listarInformacoesJogador(j));
					break;
				}
				break;
				
			case "4":
				System.out.println("Digite o nome do arquivo:");
				String arquivo = input.nextLine();
				for (Palavra palavra: ExtratorPalavrasCSV.extrairPalavra(arquivo)) {
					central.adicionarPalavra(palavra);
				}
				System.out.println("Palavras extraidas com sucesso!");
				p.salvarCentral(central, "central");
				break;
				
			case "5":
				for (Palavra palavra: central.getPalavras()) {
					System.out.println(palavra);
				}
				break;
				
			case "6":
				System.out.println("Gerando relatório...");
				GeradorDeRelatorios.gerarRelatorio(central);
				System.out.println("Relatório gerado!");
				break;
				
			case "7":
				System.out.println("Digite o assunto da mensagem:");
				String assunto = input.nextLine();
				System.out.println("Mensagem:");
				String mensagem = input.nextLine();
				
				System.out.println("Enviando mensagens...");
				for (Jogador jogador : central.getTodosOsJogadores()) {
					Mensageiro.enviarMensagem(jogador.getEmail(), assunto, mensagem);
					System.out.println("Mensagem enviada para: " + jogador.getNome());
				}
				System.out.println("Mensagens enviadas com sucesso!");
				break;
				
			case "S":
				System.out.println("Saindo...");
				rodar = false;
			}
		}
	}
}
