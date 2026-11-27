package projeto;

import java.util.ArrayList;

import enums.Dificuldade;
import exceptions.NaoHaPalavrasCompativeisException;

public class SorteadorDePalavras {
	// SORTEIA UMA PALAVRA
	public static Palavra sorteiaPalavra(ArrayList<Palavra> palavras, Dificuldade dificuldade, Jogador jogador) throws NaoHaPalavrasCompativeisException {
		ArrayList<Palavra> palavrasDificuldade = new ArrayList<Palavra>();
		
		for (Palavra p: palavras) {
			if (p.getDificuldade() == dificuldade && !jogador.checarPalavra(p)) {
				palavrasDificuldade.add(p);
			}
		}
		
		if(palavrasDificuldade.isEmpty()) 
			throw new NaoHaPalavrasCompativeisException();
		
		int sorteio = (int)(Math.random() * palavrasDificuldade.size());
		
		return palavrasDificuldade.get(sorteio);
	}
	
	public static ArrayList<Palavra> sorteiaPalavra(ArrayList<Palavra> palavras, Dificuldade dificuldade, Jogador jogador1, Jogador jogador2) throws NaoHaPalavrasCompativeisException {
		ArrayList<Palavra> palavrasDificuldade = new ArrayList<Palavra>();
		ArrayList<Palavra> palavrasFinal = new ArrayList<Palavra>();
		
		for (Palavra p: palavras) {
			if (p.getDificuldade() == dificuldade && !jogador1.checarPalavra(p) && !jogador2.checarPalavra(p)) {
				palavrasDificuldade.add(p);
			}
		}
		
		if(palavrasDificuldade.isEmpty()) 
			throw new NaoHaPalavrasCompativeisException();
		
		palavrasFinal = sortear(palavrasDificuldade);
		
		return palavrasFinal;
	}
	private static ArrayList<Palavra> sortear(ArrayList<Palavra> candidatas) throws NaoHaPalavrasCompativeisException{
		ArrayList<Palavra> tamanho = new ArrayList<Palavra>();
		ArrayList<Palavra> restantes = new ArrayList<Palavra>();
		
		while (!candidatas.isEmpty()) {
			tamanho  = new ArrayList<Palavra>();
			int sorteio1 = (int)(Math.random() * candidatas.size());
			
			Palavra palavra = candidatas.remove(sorteio1);
			
			for (Palavra p: candidatas) {
				if (p.getPalavra().length() == palavra.getPalavra().length())
					tamanho.add(p);
			}
			
			if (tamanho.size() >= 1) {
				int sorteio3 = (int)(Math.random() * tamanho.size());
				
				restantes.add(tamanho.get(sorteio3));
				restantes.add(palavra);
				
				return restantes;
			}
		}
		
		throw new NaoHaPalavrasCompativeisException("Não há duas palavras com a mesma dificuldade e tamanho.");
	}
}
