package projeto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import enums.StatusAviso;

public class Aviso {
	private String titulo;
	private String mensagem;
	private LocalDateTime dataEnvio;
	private LocalDateTime dataVisto;
	private StatusAviso status;
	private boolean isPrivado;
	
	public Aviso(String titulo, String mensagem, boolean isPrivado) {
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.isPrivado = isPrivado;
		dataEnvio = LocalDateTime.now();
		status = StatusAviso.ENVIADO;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public String getDataEnvioFormatada() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dataEnvio.format(formato);
	}
	public LocalDateTime getDataEnvio() {
		return dataEnvio;
	}
	public String getDataVistoFormatado() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dataVisto.format(formato);
	}
	public LocalDateTime getDataVisto() {
		return dataVisto;
	}
	public StatusAviso getStatus() {
		return status;
	}
	public boolean isPrivado() {
		return isPrivado;
	}
	
	public void lerAviso() {
		dataVisto = LocalDateTime.now();
		status = StatusAviso.LIDO;
	}
}
