package projeto;

import java.util.ArrayList;

import enums.Dificuldade;

public class JogoDaForca {
	// ATRIBUTOS
	private Palavra palavra;
	private String gabarito;
	private ArrayList<Character> letras;
	private int erros;
	private float pontos;
	private int bonusEficiencia;
	
	
	// CONSTRUTOR
	public JogoDaForca(Palavra palavra, Dificuldade dificuldade) {
		this.palavra = palavra;
		letras = new ArrayList<Character>();
		gabarito = "";
		erros = 0;
		bonusEficiencia = 100;
		
		
		// DEFINE OS PONTOS DE ACORDO COM O TAMANHO DA PALAVRA
		int tamanho = palavra.getPalavra().length();
		if (tamanho <= 4) 
			pontos = 100;
		else if (tamanho <= 6)
			pontos = 150;
		else if (tamanho <= 8)
			pontos = 200;
		else
			pontos = 300;
		// DEFINE O MULTIPLICADOR DE ACORDO COM A DIFICULDADE DA PALAVRA
		if (dificuldade == Dificuldade.NORMAL)
			pontos *= 1.5f;
		else if (dificuldade == Dificuldade.DIFICIL)
			pontos *= 2;
		
		// CRIA O GABARITO COM A QUANTIDADE DE LETRAS DA PALAVRA
		for(int i = 0; i < tamanho; i++) {
			gabarito += "?";
		}
		
	}
	
	
	// GETS
	public ArrayList<Character> getLetras(){
		return letras;
	}
	public int getErros() {
		return erros;
	}
	public String getGabarito() {
		return gabarito;
	}
	public float getPontosTotais() {
		return pontos + bonusEficiencia;
	}
	
	
	// TESTA SE O CARACTERE DE ENTRADA ESTÁ PRESENTE NA PALAVRA
	public boolean testarLetra(char letra) {
		String palavra = this.palavra.getPalavra();
		StringBuilder gabarito = new StringBuilder(this.gabarito);
		boolean contem = false;
		
		for (int i = 0; i < palavra.length(); i++) {
			if(palavra.charAt(i) == letra) {
				gabarito.setCharAt(i, letra);
				contem = true;
			}
		}
		this.gabarito = gabarito.toString();
		letras.add(letra);
		
		if (!contem)
			erros++;
		
		return contem;
	}
	
	
	// TESTA SE A PALAVRA JÁ FOI ENCONTRADA
	public boolean acabou() {
		for (int i = 0; i < gabarito.length(); i++) {
			if (gabarito.charAt(i) == '?')
				return false;
		}
		return true;
	}
}
