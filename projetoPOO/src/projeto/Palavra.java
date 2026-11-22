package projeto;

import enums.Dificuldade;

public class Palavra {
	
	//atributos
	private String palavra;
	private String dica;
	private Dificuldade dificuldade;
	private int erros;
	private int acertos;
	
	//construtor que inicializa os valores
	public Palavra(String palavra, String dica, Dificuldade dificuldade) {
		this.setPalavra(palavra.strip());
		this.setDica(dica.strip());
		this.setDificuldade(dificuldade);
	}
	
	//gets
	public String getPalavra() {
		return palavra;
	}
	public String getDica() {
		return dica;
	}
	public Dificuldade getDificuldade() {
		return this.dificuldade;
	}
	public int getErros() {
		return erros;
	}
	public int getAcertos() {
		return acertos;
	}
	
	//sets
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	public void setDica(String dica) {
		this.dica = dica;
	}
	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
	}
	public void addErro() {
		erros++;
	}
	public void addAcerto() {
		acertos++;
	}
	
	
	//equals sobrescrito e sobrecarregado
	public boolean equals(Palavra palavra) {
		return palavra.getPalavra().equals(this.palavra);
	}
	public boolean equals(String palavra) {
		return palavra.equals(this.palavra);
	}
	
	//to string sobrescrito
	public String toString() {
		return palavra;
	}
}
