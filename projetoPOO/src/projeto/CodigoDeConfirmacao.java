package projeto;

import javax.mail.MessagingException;

import exceptions.CodigoException;

public class CodigoDeConfirmacao {
	private String codigo;
	
	public CodigoDeConfirmacao(String email) throws MessagingException {
		gerarCodigo(email);
	}
	
	public String gerarCodigo(String email) throws MessagingException {
		codigo = String.valueOf((int)(Math.random() * 9000) + 1000);
		
		Mensageiro.enviarMensagem(email, "Código de confirmação", "Código de Confirmação: " + codigo);
		
		return codigo;
	}
	public void confirmarCodigo(String codigo) throws CodigoException {
		if (!codigo.equals(this.codigo))
			throw new CodigoException();
	}
}
