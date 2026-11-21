package projeto;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;

import enums.Dificuldade;
import exceptions.LetraJaTentadaException;
import exceptions.RetornoVazioException;

public class JogoDaForca {
	// ATRIBUTOS
	private static final int MAX_ERROS = 6;
	// PALAVRAS
	private Palavra palavra;
	private String gabarito;
	private ArrayList<Tentativa> tentativas;
	// JOGADORES
	private Jogador vencedor;
	private ArrayList<Jogador> jogadores;
	private int indiceDaVez;
	
	// GETS
	public Palavra getPalavra() {
		return palavra;
	}
	public ArrayList<Tentativa> getTentativas(){
		return tentativas;
	}
	public ArrayList<Tentativa> getTentativa(Jogador jogador) throws RetornoVazioException{
		
		if (!jogou(jogador))
			throw new RetornoVazioException("O jogador não jogou nessa rodada.");
		
		ArrayList<Tentativa> tentativasJogador = new ArrayList<Tentativa>();
		
		for (Tentativa t: tentativas) {
			if (t.getJogador().equals(jogador))
				tentativasJogador.add(t);
		}
		
		return tentativasJogador;
	}
	public String getGabarito() {
		return gabarito;
	}
	public String getDica() {
		return palavra.getDica();
	}
	public Dificuldade getDificuldade() {
		return palavra.getDificuldade();
	}
	public Jogador getVencedor() throws RetornoVazioException{
		
		if (vencedor == null) 
			throw new RetornoVazioException("Ninguém acertou a palavra.");
		
		return vencedor;
	}
	public Jogador getPerdedor() throws RetornoVazioException {
		for (Jogador j: jogadores) {
			if (!j.equals(vencedor))
				return j;
		}
		throw new RetornoVazioException("Ninguém acertou a palavra.");
	}
	public ArrayList<Jogador> getJogadores(){
		return jogadores;
	}
	public Jogador getJogadorDaVez() {
		return jogadores.get(indiceDaVez);
	}
	public int getErros(Jogador jogador) {
		int erros = 0;
		for (Tentativa t: tentativas) {
			if(!t.getAcertou() && t.getJogador().equals(jogador))
				erros++;
		}
		return erros;
	}
	public int getPontos(Jogador jogador) {
		int erros = getErros(jogador);
		
		if (estaEliminado(jogador))
			return -100;
		if (jogador.equals(vencedor))
			return pontosDaPalavra() + bonusEficiencia(erros);
		if (palavraCompleta() && !jogou(jogador))
			return bonusEficiencia(erros);
		return 0;
	}
	
	
	
	// CONSTRUTOR
	public JogoDaForca(Palavra palavra, Jogador... jogador){
		
		this.palavra = palavra;
		jogadores = new ArrayList<Jogador>(Arrays.asList(jogador));
		tentativas = new ArrayList<Tentativa>();
		gabarito = "";
		indiceDaVez = 0;
		
		// CRIA O GABARITO COM A QUANTIDADE DE LETRAS DA PALAVRA
		for(int i = 0; i < palavra.getPalavra().length(); i++) {
			gabarito += "?";
		}
		
	}
	
	
	// TESTA SE A TENTATIVA DE ENTRADA ESTÁ CORRETA
	public boolean testarLetra(Tentativa tentativa) throws LetraJaTentadaException{
		char letra = tentativa.getLetra();
		
		if (letraJaTentada(letra))
			throw new LetraJaTentadaException(letra);
		
		String palavra = this.palavra.getPalavra();
		StringBuilder gabarito = new StringBuilder(this.gabarito);
		boolean acertou = false;
		
		for (int i = 0; i < palavra.length(); i++) {
			if(normalizar(palavra.charAt(i)) == normalizar(letra)) {
				gabarito.setCharAt(i, palavra.charAt(i));
				acertou = true;
			}
		}
		this.gabarito = gabarito.toString();
		tentativa.setAcertou(acertou);
		tentativas.add(tentativa);
		
		if (palavraCompleta())
			vencedor = tentativa.getJogador();
		else if (!acertou)
			passarAVez();
		
		return acertou;
	}
	
	
	// TESTA SE A PALAVRA JÁ FOI COMPLETADA
	public boolean palavraCompleta() {
		return !gabarito.contains("?");
	}
	
	// TESTA SE O JOGADOR ESTÁ ELIMINADO
	public boolean estaEliminado(Jogador jogador) {
		return getErros(jogador) >= MAX_ERROS;
	}
	
	// TESTA SE O JOGO ACABOU
	public boolean terminou() {
		if (palavraCompleta()) {
			palavra.addAcerto();
			return true;
		}
		for (Jogador j: jogadores) {
			if (!estaEliminado(j))
				return false;
		}
		palavra.addErro();
		return true;
	}
	
	// TESTA SE A LETRA JÁ FOI USADA ANTES
	private boolean letraJaTentada(char letra) {
		for (Tentativa t: tentativas) {
			if (normalizar(t.getLetra()) == normalizar(letra))
				return true;
		}
		return false;
	}
	
	// NORMALIZA O CARACTERE
	private char normalizar(char letra) {
		String s = Normalizer.normalize(String.valueOf(letra), Normalizer.Form.NFD);
		return Character.toLowerCase(s.charAt(0));
	}
	
	private void passarAVez() {
		if (jogadores.size() == 1) {
			return;
		}
		
		int proximo = (indiceDaVez + 1) % jogadores.size();
		if (!estaEliminado(jogadores.get(proximo)))
			indiceDaVez = proximo;
	}
	
	private int bonusEficiencia(int erros) {
		if (erros == 0)
			return 100;
		else if (erros == 1)
			return 75;
		else if (erros == 2)
			return 50;
		else if (erros == 3)
			return 25;
		else if (erros <= 5)
			return 0;
		return -100;
	}
	
	private int pontosDaPalavra() {
		int tamanho = palavra.getPalavra().length();
		int pontos;
		if (tamanho <= 4)
			pontos = 100;
		else if (tamanho <= 6)
			pontos = 150;
		else if (tamanho <= 8)
			pontos = 200;
		else
			pontos = 300;
		
		Dificuldade d = palavra.getDificuldade();
		if (d == Dificuldade.NORMAL)
			pontos *= 1.5f;
		else if (d == Dificuldade.DIFICIL)
			pontos *= 2;
		
		return pontos;
	}
	
	private boolean jogou(Jogador jogador) {
		for (Tentativa t: tentativas) {
			if (t.getJogador().equals(jogador))
				return true;
		}
		return false;
	}
}
