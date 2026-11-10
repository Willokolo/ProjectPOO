package projeto;

import java.util.ArrayList;

public class CentralDeInformacoes {
	
	//atributos
	private ArrayList<Jogador> todosOsJogadores = new ArrayList<Jogador>();
	private ArrayList<Palavra> palavras = new ArrayList<Palavra>();
	
	
	//sets
	public void setTodosOsJogadores(ArrayList<Jogador> todosOsJogadores) {
		this.todosOsJogadores = todosOsJogadores;
	}
	
	
	//gets
	public ArrayList<Jogador> getTodosOsJogadores() {
		return this.todosOsJogadores;
	}
	public ArrayList<Palavra> getPalavras(){
		return this.palavras;
	}
	
	
	//metodos
	
	//checa se o CPF ou o Email são iguais, respectivamente
	private boolean CPFIgual(Jogador jogador, String CPF) {
		return jogador.getCPF().equals(CPF);
	}
	private boolean emailIgual(Jogador jogador, String email) {
		return jogador.getEmail().equals(email);
	}
	
	//adiciona um jogador e testa se existe algum com CPF ou email igual
	public boolean adicionarJogador(Jogador jogador) {
		for (Jogador j: todosOsJogadores) {
			if (CPFIgual(j, jogador.getCPF()) || emailIgual(j, jogador.getEmail())) {
				return false;
			}
		}
		todosOsJogadores.add(jogador);
		return true;
	}
	
	//metodo sobrecarregado
	public boolean adicionarJogador(String nome, Sexo sexo, String CPF, String email) {
		Jogador jogador = new Jogador(nome, sexo, CPF, email);
		
		return adicionarJogador(jogador);
	}
	
	//recupera o jogador pelo CPF ou pelo Email, respectivamente
	public Jogador recuperarJogadorPorCPF(String CPF) {
		for (Jogador j: todosOsJogadores) {
			if (CPFIgual(j, CPF)) {
				return j;
			}
		}
		return null;
	}
	
	public Jogador recuperarJogadorPorEmail(String email) {
		for (Jogador j: todosOsJogadores) {
			if (emailIgual(j, email)) {
				return j;
			}
		}
		return null;
	}
	
	
	//lista todas as informações de um jogador
	public String listarInformacoesJogador(Jogador j) {
		return j + ", Sexo: " + j.getSexo() + ", CPF: " + j.getCPF() + ", Email: " + j.getEmail();
	}
	
	
	
	public boolean adicionarPalavra(Palavra palavra) {
		for (Palavra p: palavras) {
			if (p.equals(palavra)) {
				return false;
			}
		}
		palavras.add(palavra);
		return true;
	}
	
	public boolean adicionarPalavra(String palavra, String dica, Dificuldade dificuldade) {
		Palavra p = new Palavra(palavra, dica, dificuldade);
		
		return adicionarPalavra(p);
	}
	
	public Palavra recuperarPalavra(String palavra) {
		for (Palavra p: palavras) {
			if (p.equals(palavra)) {
				return p;
			}
		}
		return null;
	}
}
