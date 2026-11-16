package projeto;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Dificuldade;
import enums.TipoDePartida;

public class Partida {
	private Palavra palavra;
	private TipoDePartida tipoDePartida;
	private Jogador jogador;
	private LocalDate data;
	private ArrayList<Character> letras;
	private int pontos;
	private boolean resultado;
	
	public Partida(Palavra palavra, TipoDePartida tipo, Jogador jogador, LocalDate data, ArrayList<Character> letras, int pontos, boolean resutado) {
		this.palavra = palavra;
		this.tipoDePartida = tipo;
		this.jogador = jogador;
		this.data = data;
		this.letras = letras;
		this.pontos = pontos;
		this.resultado = resutado;
	}
	
	private String palavra1;
	private String palavra2;
	private String gabarito = "";
	private int posSorteada;
	
	public Partida(TipoDePartida tipo, ArrayList<Palavra> palavras, Dificuldade dificuldade, Jogador jogador) {
		for (Palavra p: palavras) {
			
		}
	}
}
