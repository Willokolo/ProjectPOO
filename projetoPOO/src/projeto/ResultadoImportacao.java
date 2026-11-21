package projeto;

import java.util.ArrayList;

public class ResultadoImportacao {
	// ATRIBUTOS
	private int cadastradas;
	private int editadas;
	private int excluidas;
	private ArrayList<String> rejeitadas = new ArrayList<String>();
	
	
	// ADDS
	public void addCadastradas() {
		cadastradas++;
	}
	public void addEditadas() {
		editadas++;
	}
	public void addExcluidas() {
		excluidas++;
	}
	
	// GETS
	public int getCadastradas() {
		return cadastradas;
	}
	public int getExcluidas() {
		return excluidas;
	}
	public int getEditadas() {
		return editadas;
	}
	public ArrayList<String> getRejeitadas(){
		return rejeitadas;
	}
	
	// CADASTRA UMA LINHA REJEITADA
	public void rejeitar(String linha, String motivo) {
		rejeitadas.add(linha + ", " + motivo);
	}
}
