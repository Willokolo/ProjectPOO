package exceptions;

public class TentativaException extends Exception {
	public TentativaException(String msg, char letra) {
		super(msg + ": " + letra);
	}
}
