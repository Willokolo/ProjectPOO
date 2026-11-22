package exceptions;

import projeto.Jogador;

public class JogadorInativoException extends Exception {

	public JogadorInativoException(Jogador jogador) {
		super("O jogador " + jogador + " está com a conta inativa.");
	}

}
