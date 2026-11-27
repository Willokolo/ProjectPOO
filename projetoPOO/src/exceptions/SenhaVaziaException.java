package exceptions;

public class SenhaVaziaException extends Exception {
	public SenhaVaziaException() {
		super("A senha não pode ficar em branco.");
	}
}
