package projeto;

import java.util.ArrayList;

public class Jogador {
	// ATRIBUTOS
	private String nome;
	private String email;
	private String senha;
	private ArrayList<Palavra> palavrasVistas;
	
	
	// CONSTRUTOR
	public Jogador(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}
	
	
	// GETS APENAS PARA O EMAIL E NOME, POIS NÃO FAZ SENTIDO SER POSSIVEL VER A SENHA
	public String getEmail() {
		return email;
	}
	public String getNome() {
		return nome;
	}
	
	
	// CHECA SE A SENHA DE ENTRADA É A MESMA SENHA SALVA
	public boolean checarSenha(String senha) {
		return senha.equals(this.senha);
	}
	
	
	// ADICIONA UMA PALAVRA A LISTA DE PALAVRAS VISTAS
	public void addPalavraVista(Palavra palavra) {
		palavrasVistas.add(palavra);
	}
	
	
	// TO STRING
	public String toString() {
		return "Nome: " + nome;
	}
}
