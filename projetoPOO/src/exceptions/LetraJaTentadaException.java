package exceptions;

public class LetraJaTentadaException extends Exception {
	public LetraJaTentadaException(char letra ) {
		super("Essa letra já foi usada: " + letra);
	}
}
