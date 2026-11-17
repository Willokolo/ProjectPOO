package exceptions;

public class FormatoNaoEsperadoException extends Exception {
	public FormatoNaoEsperadoException() {
		super("Uma das palavras do arquivo CSV tinha um formato não esperado. ");
	}
}
