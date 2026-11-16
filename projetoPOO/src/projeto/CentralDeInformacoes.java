package projeto;

import java.util.ArrayList;

import enums.Dificuldade;
import enums.Sexo;
import exceptions.JogadorJaExisteException;
import exceptions.JogadorNaoExisteException;
import exceptions.NaoHaPalavrasNestaDificuladeException;
import exceptions.PalavraJaExisteException;
import exceptions.PalavraNaoExisteException;

public class CentralDeInformacoes {
	
	// ATRIBUTOS
	private ArrayList<Jogador> todosOsJogadores = new ArrayList<Jogador>();
	private ArrayList<Palavra> palavras = new ArrayList<Palavra>();
	
	
	// SETS
	public void setTodosOsJogadores(ArrayList<Jogador> todosOsJogadores) {
		this.todosOsJogadores = todosOsJogadores;
	}
	
	
	// GETS
	public ArrayList<Jogador> getTodosOsJogadores() {
		return this.todosOsJogadores;
	}
	public ArrayList<Palavra> getPalavras(){
		return this.palavras;
	}
	
	
	
	
	// MÉTODOS
	
	// CHECA SE O NOME OU O EMAIL SÃO IGUAIS
	private boolean loginIgual(Jogador jogador, String login) {
		return jogador.getEmail().equals(login) || jogador.getNome().equals(login);
	}
	
	
	// ADICIONA UM JOGADOR E TESTA SE JÁ EXISTE ALGUM COM AQUELE NOME OU EMAIL
	public boolean adicionarJogador(Jogador jogador) throws JogadorJaExisteException {
		for (Jogador j: todosOsJogadores) {
			if (loginIgual(j, jogador.getNome()) || loginIgual(j, jogador.getEmail())) {
				throw new JogadorJaExisteException();
			}
		}
		todosOsJogadores.add(jogador);
		return true;
	}
	
	public boolean adicionarJogador(String nome, String email, String senha) throws JogadorJaExisteException {
		Jogador jogador = new Jogador(nome, email, senha);
		
		return adicionarJogador(jogador);
	}
	
	
	// RECUPERA O JOGADOR PELO NOME OU EMAIL	
	public Jogador recuperarJogador(String login) throws JogadorNaoExisteException{
		for (Jogador j: todosOsJogadores) {
			if(loginIgual(j, login)) {
				return j;
			}
		}
		throw new JogadorNaoExisteException();
	}
	
	
	// FAZ O LOGIN DO USUARIO
	public boolean fazerLogin(String login, String senha) throws JogadorNaoExisteException {
		for (Jogador j: todosOsJogadores) {
			if(loginIgual(j, login) && j.checarSenha(senha)) {
				return true;
			}
		}
		throw new JogadorNaoExisteException();
	}
	
	
	// LISTA TODAS AS INFORMAÇÕES DE UM JOGADOR
	public String listarInformacoesJogador(Jogador j) {
		return j + ", Email: " + j.getEmail();
	}
	
	
	// ADICIONA UMA PALAVRA
	public boolean adicionarPalavra(Palavra palavra) throws PalavraJaExisteException{
		for (Palavra p: palavras) {
			if (p.equals(palavra)) {
				throw new PalavraJaExisteException();
			}
		}
		palavras.add(palavra);
		return true;
	}
	
	public boolean adicionarPalavra(String palavra, String dica, Dificuldade dificuldade) throws PalavraJaExisteException {
		Palavra p = new Palavra(palavra, dica, dificuldade);
		
		return adicionarPalavra(p);
	}
	
	
	// SORTEIA UMA PALAVRA
	public Palavra sorteiaPalavra(Dificuldade dificuldade) throws NaoHaPalavrasNestaDificuladeException {
		ArrayList<Palavra> correspondentes = new ArrayList<Palavra>();
		
		for (Palavra p: palavras) {
			if (p.getDificuldade() == dificuldade) {
				correspondentes.add(p);
			}
		}
		
		if(correspondentes.isEmpty()) 
			throw new NaoHaPalavrasNestaDificuladeException();
		
		int sorteio = (int)(Math.random() * correspondentes.size());
		
		return correspondentes.get(sorteio);
	}
	
	
	// RECUPERA UMA PALAVRA
	public Palavra recuperarPalavra(String palavra) throws PalavraNaoExisteException{
		for (Palavra p: palavras) {
			if (p.equals(palavra)) {
				return p;
			}
		}
		throw new PalavraNaoExisteException();
	}
}
