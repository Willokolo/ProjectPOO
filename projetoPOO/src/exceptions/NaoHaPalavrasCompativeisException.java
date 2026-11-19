package exceptions;

public class NaoHaPalavrasCompativeisException extends Exception {
	public NaoHaPalavrasCompativeisException() {
		super("Não há palavras cadastradas com essa dificuldade, ou o jogador já viu as palavras cadastradas.");
	}
	
	public NaoHaPalavrasCompativeisException(String msg) {
		super(msg);
	}
}
