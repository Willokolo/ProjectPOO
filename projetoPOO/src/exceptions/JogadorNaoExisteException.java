package exceptions;

public class JogadorNaoExisteException extends Exception {

	public JogadorNaoExisteException() {
		super("O login ou senha inserido está errado ou o jogador não existe.");
	}

}
