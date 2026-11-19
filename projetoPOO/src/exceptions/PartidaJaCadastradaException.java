package exceptions;

public class PartidaJaCadastradaException extends Exception {
	public PartidaJaCadastradaException() {
		super("Essa partida já está cadastrada.");
	}
}
