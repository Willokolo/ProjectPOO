package projeto;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Address;

public class Mensageiro {
	public static void enviarMensagem(String destinatario, String assunto, String mensagem) {
		Properties props = new Properties();
		String remetente = "mensageiroifpb2026@gmail.com";
		String senha = "pcgmfvupofwrtmck";
		
		
		props.put("mail.smtp.user", remetente); 
		props.put("mail.smtp.host", "smtp.gmail.com"); 
		props.put("mail.smtp.port", "25"); 
		props.put("mail.debug", "false"); 
		props.put("mail.smtp.auth", "true"); 
		props.put("mail.smtp.starttls.enable","true"); 
		props.put("mail.smtp.EnableSSL.enable","true");

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
		props.setProperty("mail.smtp.socketFactory.fallback", "false");   
		props.setProperty("mail.smtp.port", "465");   
		props.setProperty("mail.smtp.socketFactory.port", "465");
		
		Session sessao = Session.getDefaultInstance(props, 
				new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(remetente, senha);
					}
				});
		
		try {
			Message message = new MimeMessage(sessao);
			message.setFrom(new InternetAddress(remetente));
			
			Address[] toUser = InternetAddress.parse(destinatario);
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);
			message.setText(mensagem);
			
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
