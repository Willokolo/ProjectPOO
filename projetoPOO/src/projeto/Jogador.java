package projeto;

import java.util.ArrayList;
import java.util.Comparator;

import exceptions.RetornoVazioException;

public class Jogador {
	// ATRIBUTOS
	private String nome;
	private String email;
	private String senha;
	private ArrayList<Palavra> palavrasVistas;
	private ArrayList<Tentativa> tentativas;
	private int pontosDuelo;
	private int pontosSolo;
	private boolean ativo;
	
	
	// CONSTRUTOR
	public Jogador(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		pontosDuelo = 0;
		pontosSolo = 0;
		ativo = true;
		
		palavrasVistas = new ArrayList<Palavra>();
		tentativas = new ArrayList<Tentativa>();
	}
	
	
	// GETS
	public String getEmail() {
		return email;
	}
	public String getNome() {
		return nome;
	}
	public ArrayList<Palavra> getPalavrasVistas(){
		return palavrasVistas;
	}
	public int getPontosSolo() {
		return pontosSolo;
	}
	public int getPontosDuelo() {
		return pontosDuelo;
	}
	public ArrayList<Tentativa> getTentativas(){
		return tentativas;
	}
	public boolean isAtivo() {
		return ativo;
	}
	
	// ADICIONA PONTOS AO JOGADOR
	public void addPontosSolo(int pontos) {
		pontosSolo += pontos;
	}
	public void addPontosDuelo(int pontos) {
		pontosDuelo += pontos;
	}
	// ADICIONA UMA TENTATIVA A LISTA
	public void addTentativa(Tentativa tentativa) {
		tentativas.add(tentativa);
	}
	// ADICIONA UMA PALAVRA A LISTA DE PALAVRAS VISTAS
	public void addPalavraVista(Palavra palavra) {
		palavrasVistas.add(palavra);
	}
	
	// CHECA SE A SENHA DE ENTRADA É A MESMA SENHA SALVA
	public boolean checarSenha(String senha) {
		return senha.equals(this.senha);
	}
	// CHECA SE A PALAVRA ESTÁ NA LISTA DE PALAVRAS VISTAS
	public boolean checarPalavra(Palavra palavra) {
		return palavrasVistas.contains(palavra);
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void redefinirSenha(String senha) throws RetornoVazioException {
		if (senha == null || senha.isBlank())
			throw new RetornoVazioException("A senha não pode ficar em branco.");
		
		this.senha = senha;
	}
	public void desativar() {
		ativo = false;
	}
	
	// TO STRING
	public String toString() {
		return nome;
	}
}
