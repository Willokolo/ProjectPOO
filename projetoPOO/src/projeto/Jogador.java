package projeto;

public class Jogador {
	
	//atributos
	private String nome;
	private Sexo sexo;
	private String CPF;
	private String email;
	
	
	//sets
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	
	//gets
	public String getNome() {
		return this.nome;
	}
	public Sexo getSexo() {
		return this.sexo;
	}
	public String getCPF() {
		return this.CPF;
	}
	public String getEmail() {
		return this.email;
	}
	
	
	//construtor
	public Jogador(String nome, Sexo sexo, String CPF, String email) {
		this.setNome(nome);
		this.setSexo(sexo);
		this.CPF = CPF;
		this.email = email;;
	}
	
	
	//to string
	public String toString() {
		return "Nome: " + nome;
	}
}
