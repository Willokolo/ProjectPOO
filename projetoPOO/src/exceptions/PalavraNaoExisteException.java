package exceptions;

public class PalavraNaoExisteException extends Exception {
	public PalavraNaoExisteException() {
		super("Essa palavra não está salva na base de vocábulos.");
	}
}
