package projeto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import enums.Dificuldade;
import exceptions.JogadorInativoException;
import exceptions.JogadorJaExisteException;
import exceptions.LoginSenhaException;
import exceptions.PalavraJaExisteException;
import exceptions.PalavraNaoExisteException;
import exceptions.RetornoVazioException;

public class CentralDeInformacoes {
	
	// ATRIBUTOS
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private ArrayList<Palavra> palavras = new ArrayList<Palavra>();
	private ArrayList<Partida> partidas = new ArrayList<Partida>();
	private Jogador administrador;
	
	
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
	public Jogador getAdministrador() {
		return administrador;
	}
	public ArrayList<Partida> getPartidas(Jogador jogador){
		ArrayList<Partida> partidas = new ArrayList<Partida>();
		
		for (Partida p: this.partidas) {
			if (p.getJogadores().contains(jogador))
				partidas.add(p);
		}
		return new ArrayList<Partida>(partidas.reversed());
	}
	public ArrayList<Partida> getPartidas(LocalDateTime dataInicio, LocalDateTime dataFim){
		ArrayList<Partida> partidas = new ArrayList<Partida>();
		for (Partida p: this.partidas) {
			LocalDateTime data = p.getDataHora();
			if (!data.isBefore(dataInicio) && !data.isAfter(dataFim))
				partidas.add(p);
		}
		return new ArrayList<Partida>(partidas.reversed());
	}
	public ArrayList<Partida> getPartidas(Jogador jogador, LocalDateTime dataInicio, LocalDateTime dataFim) {
		ArrayList<Partida> partidasJogador = getPartidas(jogador);
		ArrayList<Partida> partidasData = getPartidas(dataInicio, dataFim);
		
		for (Partida p: partidasJogador) {
			if (!partidasData.contains(p))
				partidasData.add(p);
		}
		partidasData.sort(Comparator.comparing(Partida::getDataHora).reversed()); // COLOCA AS PARTIDAS EM ORDEM NOVAMENTE
		return partidasData;
	}
	public ArrayList<Jogador> getJogadores(Jogador filtro, boolean filtrarInativos) throws RetornoVazioException {
		ArrayList<Jogador> filtrados = new ArrayList<Jogador>();
		for (Jogador j: getJogadores()) {
			if (filtrarInativos) {
				if (!j.equals(filtro) && j.isAtivo())
					filtrados.add(j);
			} else {
				if (!j.equals(filtro))
					filtrados.add(j);
			}
		}
		
		if (filtrados.isEmpty())
			throw new RetornoVazioException("Não existem jogadores cadastrados para esse filtro");
		
		return filtrados;
	}
	public ArrayList<Jogador> getJogadoresAtivos(){
		ArrayList<Jogador> filtrados = new ArrayList<Jogador>();
		for (Jogador j: getJogadores()) {
			if (j.isAtivo())
				filtrados.add(j);
		}
		return filtrados;
	}
	
	
	
	// MÉTODOS
	
	public ArrayList<Jogador> getRankingSolo(){
		ArrayList<Jogador> ranking = new ArrayList<Jogador>(jogadores);
		ranking.sort(Comparator.comparing(Jogador::getPontosSolo).reversed());
		
		return ranking;
	}
	public ArrayList<Jogador> getRankingDuelo(){
		ArrayList<Jogador> ranking = new ArrayList<Jogador>(jogadores);
		ranking.sort(Comparator.comparing(Jogador::getPontosDuelo).reversed());
		
		return ranking;
	}
	public void editarJogador(Jogador jogador, String nome, String email) throws JogadorJaExisteException {
		for (Jogador j: jogadores) {
			if ((loginIgual(j, jogador.getNome()) || loginIgual(j, jogador.getEmail())) && j.isAtivo()) {
				throw new JogadorJaExisteException();
			}
		}
		
		jogador.setEmail(email);
		jogador.setNome(nome);
	}
	
	// CHECA SE O NOME OU O EMAIL SÃO IGUAIS
	private boolean loginIgual(Jogador jogador, String login) {
		return jogador.getEmail().equals(login) || jogador.getNome().equals(login);
	}
	
	
	// ADICIONA UM JOGADOR E TESTA SE JÁ EXISTE ALGUM COM AQUELE NOME OU EMAIL
	public boolean addJogador(Jogador jogador) throws JogadorJaExisteException {
		for (Jogador j: jogadores) {
			if ((loginIgual(j, jogador.getNome()) || loginIgual(j, jogador.getEmail())) && j.isAtivo()) {
				throw new JogadorJaExisteException();
			}
		}
		jogadores.add(jogador);
		setAdministrador(jogador);
		return true;
	}
	
	public boolean addJogador(String nome, String email, String senha) throws JogadorJaExisteException {
		Jogador jogador = new Jogador(nome, email, senha);
		
		return addJogador(jogador);
	}
	
	
	// RECUPERA O JOGADOR PELO NOME OU EMAIL	
	public Jogador recuperarJogador(String login) throws LoginSenhaException, JogadorInativoException{
		boolean ativo = true;
		Jogador jogador = null;
		
		for (Jogador j: jogadores) {
			if (!j.isAtivo() && loginIgual(j, login)) {
				jogador = j;
				ativo = false;
			} else if(loginIgual(j, login)) {
				return j;
			}
		}
		
		if (!ativo)
			throw new JogadorInativoException(jogador);
		
		throw new LoginSenhaException();
	}
	
	
	// FAZ O LOGIN DO USUARIO
	public Jogador fazerLogin(String login, String senha) throws LoginSenhaException, JogadorInativoException {
		boolean existeContaAtiva = false;
		Jogador inativo = null;
		
		for (Jogador j: jogadores) {
			if (!loginIgual(j, login))
				continue;
			
			if (j.isAtivo()) {
				if (j.checarSenha(senha))
					return j;
				existeContaAtiva = true;
			} else {
				inativo = j;
			}
		}
		
		if(!existeContaAtiva && inativo != null)
			throw new JogadorInativoException(inativo);
		
		throw new LoginSenhaException();
	}
	
	
	// LISTA AS INFORMAÇÕES BÁSICAS DE UM JOGADOR
	public String listarInformacoesJogador(Jogador j) {
		return "Nome: " + j + ", Email: " + j.getEmail();
	}
	
	// ADICIONA UM AVISO AO JOGADOR
	public void addAviso(Jogador jogador, Aviso aviso) {
		jogador.addAviso(aviso);
	}
	
	// ADICIONA UM PARTIDA
	public void addPartida(Partida partida) {
		partidas.add(partida);
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
	
	
	// RECUPERA UMA PALAVRA
	public Palavra recuperarPalavra(String palavra) throws PalavraNaoExisteException{
		for (Palavra p: palavras) {
			if (p.equals(palavra)) {
				return p;
			}
		}
		throw new PalavraNaoExisteException();
	}
	
	// DEFINE QUEM É O ADM
	private void setAdministrador(Jogador jogador) {
		if (administrador == null)
			administrador = jogador;
	}
}
