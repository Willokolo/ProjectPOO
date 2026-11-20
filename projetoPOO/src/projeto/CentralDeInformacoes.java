package projeto;

import java.util.ArrayList;

import enums.Dificuldade;
import exceptions.JogadorJaExisteException;
import exceptions.LoginSenhaException;
import exceptions.NaoHaPalavrasCompativeisException;
import exceptions.PalavraJaExisteException;
import exceptions.PalavraNaoExisteException;
import exceptions.PartidaJaCadastradaException;

public class CentralDeInformacoes {
	
	// ATRIBUTOS
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Palavra> palavras = new ArrayList<Palavra>();
	private ArrayList<Partida> partidas = new ArrayList<Partida>();
	
	
	// GETS
	public ArrayList<Jogador> getJogadores() {
		return jogadores;
	}
	public ArrayList<Palavra> getPalavras(){
		return palavras;
	}
	public ArrayList<Partida> getPartidas(){
		return partidas;
	}
	
	
	
	
	// MÉTODOS
	
	// CHECA SE O NOME OU O EMAIL SÃO IGUAIS
	private boolean loginIgual(Jogador jogador, String login) {
		return jogador.getEmail().equals(login) || jogador.getNome().equals(login);
	}
	
	
	// ADICIONA UM JOGADOR E TESTA SE JÁ EXISTE ALGUM COM AQUELE NOME OU EMAIL
	public boolean addJogador(Jogador jogador) throws JogadorJaExisteException {
		for (Jogador j: jogadores) {
			if (loginIgual(j, jogador.getNome()) || loginIgual(j, jogador.getEmail())) {
				throw new JogadorJaExisteException();
			}
		}
		jogadores.add(jogador);
		return true;
	}
	
	public boolean addJogador(String nome, String email, String senha) throws JogadorJaExisteException {
		Jogador jogador = new Jogador(nome, email, senha);
		
		return addJogador(jogador);
	}
	
	
	// RECUPERA O JOGADOR PELO NOME OU EMAIL	
	public Jogador recuperarJogador(String login) throws LoginSenhaException{
		for (Jogador j: jogadores) {
			if(loginIgual(j, login)) {
				return j;
			}
		}
		throw new LoginSenhaException();
	}
	
	
	// FAZ O LOGIN DO USUARIO
	public Jogador fazerLogin(String login, String senha) throws LoginSenhaException {
		for (Jogador j: jogadores) {
			if(loginIgual(j, login) && j.checarSenha(senha)) {
				return j;
			}
		}
		throw new LoginSenhaException();
	}
	
	
	// LISTA AS INFORMAÇÕES BÁSICAS DE UM JOGADOR
	public String listarInformacoesJogador(Jogador j) {
		return "Nome: " + j + ", Email: " + j.getEmail();
	}
	
	// ADICIONA UM PARTIDA
	public boolean addPartida(Partida partida) throws PartidaJaCadastradaException {
		for (Partida p: partidas) {
			if (p.equals(partida))
				throw new PartidaJaCadastradaException();
		}
		partidas.add(partida);
		return true;
	}
	
	// ADICIONA UMA PALAVRA
	public boolean addPalavra(Palavra palavra) throws PalavraJaExisteException{
		for (Palavra p: palavras) {
			if (p.equals(palavra)) {
				throw new PalavraJaExisteException();
			}
		}
		palavras.add(palavra);
		return true;
	}
	
	public boolean addPalavra(String palavra, String dica, Dificuldade dificuldade) throws PalavraJaExisteException {
		Palavra p = new Palavra(palavra, dica, dificuldade);
		
		return addPalavra(p);
	}
	
	
	// SORTEIA UMA PALAVRA
	public Palavra sorteiaPalavra(Dificuldade dificuldade, Jogador jogador) throws NaoHaPalavrasCompativeisException {
		ArrayList<Palavra> palavrasDificuldade = new ArrayList<Palavra>();
		
		for (Palavra p: palavras) {
			if (p.getDificuldade() == dificuldade && !jogador.checarPalavra(p)) {
				palavrasDificuldade.add(p);
			}
		}
		
		if(palavrasDificuldade.isEmpty()) 
			throw new NaoHaPalavrasCompativeisException();
		
		int sorteio = (int)(Math.random() * palavrasDificuldade.size());
		
		return palavrasDificuldade.get(sorteio);
	}
	public ArrayList<Palavra> sorteiaPalavra(Dificuldade dificuldade, Jogador jogador1, Jogador jogador2) throws NaoHaPalavrasCompativeisException {
		ArrayList<Palavra> palavrasDificuldade = new ArrayList<Palavra>();
		ArrayList<Palavra> palavrasFinal = new ArrayList<Palavra>();
		
		for (Palavra p: palavras) {
			if (p.getDificuldade() == dificuldade && !jogador1.checarPalavra(p) && !jogador2.checarPalavra(p)) {
				palavrasDificuldade.add(p);
			}
		}
		
		if(palavrasDificuldade.isEmpty()) 
			throw new NaoHaPalavrasCompativeisException();
		
		palavrasFinal = sortear(palavrasDificuldade);
		
		return palavrasFinal;
	}
	private ArrayList<Palavra> sortear(ArrayList<Palavra> candidatas) throws NaoHaPalavrasCompativeisException{
		ArrayList<Palavra> tamanho = new ArrayList<Palavra>();
		ArrayList<Palavra> restantes = new ArrayList<Palavra>();
		
		while (!candidatas.isEmpty()) {
			tamanho  = new ArrayList<Palavra>();
			int sorteio1 = (int)(Math.random() * candidatas.size());
			
			Palavra palavra = candidatas.remove(sorteio1);
			
			for (Palavra p: candidatas) {
				if (p.getPalavra().length() == palavra.getPalavra().length())
					tamanho.add(p);
			}
			
			if (tamanho.size() >= 1) {
				int sorteio3 = (int)(Math.random() * tamanho.size());
				
				restantes.add(tamanho.get(sorteio3));
				restantes.add(palavra);
				
				return restantes;
			}
		}
		
		throw new NaoHaPalavrasCompativeisException("Não há duas palavras com a mesma dificuldade e tamanho.");
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
