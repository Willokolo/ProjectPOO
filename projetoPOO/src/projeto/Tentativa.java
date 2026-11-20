package projeto;

public class Tentativa {
	private Jogador jogador;
	private char letra;
	private boolean acertou;
	
	public Tentativa(Jogador jogador, char letra) {
		this.jogador = jogador;
		this.letra = letra;
	}

	public char getLetra() {
		return letra;
	}
	public Jogador getJogador() {
		return jogador;
	}
	public boolean getAcertou() {
		return acertou;
	}
	
	public void setAcertou(boolean acertou) {
		this.acertou = acertou;
	}
	
	public String toString() { // REMOVER 
		return jogador.getNome() + " " + letra + " " + acertou;
	}
}
