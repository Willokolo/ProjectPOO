package projeto;

import enums.TipoDePartida;
import exceptions.RetornoVazioException;

public class PartidaDuelo extends Partida {
	private Jogador jogador1;
	private Jogador jogador2;
	
	public PartidaDuelo(Jogador jogador1, Jogador jogador2, Palavra palavra1, Palavra palavra2) {
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		
		rodadas.add(new JogoDaForca(palavra1, jogador1, jogador2));
		rodadas.add(new JogoDaForca(palavra2, jogador2, jogador1));
	}
	
	public TipoDePartida getTipo() {
		return TipoDePartida.DUELO;
	}
	public int getPontosRodada(Jogador jogador) {
		int total = 0;
		for (JogoDaForca j: rodadas) {
			total += j.getPontos(jogador);
		}
		return total;
	}
	public Jogador getVencedor() throws RetornoVazioException{
		float p1 = getPontosRodada(jogador1);
		float p2 = getPontosRodada(jogador2);
		
		if (p1 > p2) 
			return jogador1;
		else if (p2 > p1)
			return jogador2;
		throw new RetornoVazioException("Houve um empate.");
	}
	public String getResultado() {
		return jogador1 + ": " + getPontosDuelo(jogador1)
			+ "\n" + jogador2 + ": " + getPontosDuelo(jogador2);
	}
	public int getPontosDuelo(Jogador jogador) {
		Jogador vencedor;
		
		try {
			vencedor = getVencedor();
		} catch (RetornoVazioException e) {
			return 1;
		}
		
		if (vencedor.equals(jogador))
			return 3;
		
		return 0;
	}
}
