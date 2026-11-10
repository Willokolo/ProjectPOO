package projeto;

import java.util.Date;

public class Palavra {
	
	//atributos
	private String palavra;
	private String dica;
	private Date dataDeCadastro;
	private Dificuldade dificuldade;
	
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
	public Date getDataDeCadastro() {
		return this.dataDeCadastro;
	}
	public Dificuldade getDificuldade() {
		return this.dificuldade;
	}
	
	//sets
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
	public void setDica(String dica) {
		this.dica = dica;
	}
	public void setDataDeCadastro(Date dataDeCadastro) {
		this.dataDeCadastro = dataDeCadastro;
	}
	public void setDificuldade(Dificuldade dificuldade) {
		this.dificuldade = dificuldade;
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
		return palavra + ": " + dica;
	}
}
