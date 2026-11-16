package exceptions;

public class JogadorJaExisteException extends Exception {
	public JogadorJaExisteException() {
		super("O login inserido já é usado por outro jogador, tente novamente.");
	}
}
