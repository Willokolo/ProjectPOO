package exceptions;

public class NaoHaPalavrasNestaDificuladeException extends Exception {
	public NaoHaPalavrasNestaDificuladeException() {
		super("Não há palavras cadastradas com essa dificuldade.");
	}
}
