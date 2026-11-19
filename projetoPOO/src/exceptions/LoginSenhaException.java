package exceptions;

public class LoginSenhaException extends Exception {

	public LoginSenhaException() {
		super("O login ou senha inserido está errado ou o jogador não existe.");
	}

}
