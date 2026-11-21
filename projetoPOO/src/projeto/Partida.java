package projeto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import enums.Dificuldade;
import enums.TipoDePartida;
import exceptions.RetornoVazioException;

public abstract class Partida {
	// ATRIBUTOS
	private LocalDateTime dataHora = LocalDateTime.now();
	protected ArrayList<JogoDaForca> rodadas = new ArrayList<JogoDaForca>();
	
	
	// GETS ABSTRATOS
	public abstract TipoDePartida getTipo();
	public abstract String getResultado();
	public abstract Jogador getVencedor() throws RetornoVazioException;
	
	
	// GETS CONCRETOS
	public ArrayList<JogoDaForca> getRodadas(){
		return rodadas;
	}
	
	public String getDataHoraFormatada() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		return dataHora.format(dtf);
	}
	
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	
	public ArrayList<Jogador> getJogadores() {
		return rodadas.get(0).getJogadores();
	}
	
	public ArrayList<Palavra> getPalavras() {
		ArrayList<Palavra> palavras = new ArrayList<Palavra>();
		
		for (JogoDaForca j: rodadas) 
			palavras.add(j.getPalavra());
		return palavras;
	}
	
	public Dificuldade getDificuldade() {
		return rodadas.get(0).getDificuldade();
	}
	
	public ArrayList<Tentativa> getTentativas() {
		ArrayList<Tentativa> tentativas = new ArrayList<Tentativa>();
		
		for (JogoDaForca j: rodadas) {
			for (Tentativa t: j.getTentativas()) {
				tentativas.add(t);
			}
		}
		
		return tentativas;
	}
	public ArrayList<Character> getLetras(){
		ArrayList<Character> letras = new ArrayList<Character>();
		
		for (JogoDaForca j: rodadas) {
			for (Tentativa t: j.getTentativas()) {
				letras.add(t.getLetra());
			}
		}
		
		return letras;
	}
}
