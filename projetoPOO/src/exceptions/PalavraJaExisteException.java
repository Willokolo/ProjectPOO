package exceptions;

public class PalavraJaExisteException extends Exception {
	public PalavraJaExisteException() {
		super("Essa palavra já está salva na base de vocábulos.");
	}
}
