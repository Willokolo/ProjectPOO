package projeto;

import enums.TipoDePartida;

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
	public float getPontosRodada(Jogador jogador) {
		float total = 0;
		for (JogoDaForca j: rodadas) {
			total += j.getPontos(jogador);
		}
		return total;
	}
	public Jogador getVencedor() {
		float p1 = getPontosRodada(jogador1);
		float p2 = getPontosRodada(jogador2);
		
		if (p1 > p2) 
			return jogador1;
		else if (p2 > p1)
			return jogador2;
		return null; // RETORNA NULO CASO EMPATE
	}
	public String getResultado() {
		Jogador vencedor = getVencedor();
		Jogador perdedor;
		if (vencedor == null) {
			return jogador1 + ": " + "1\n"
				+  jogador2 + ": " + "1";
		}
		
		if (vencedor.equals(jogador1))
			perdedor = jogador2;
		else
			perdedor = jogador1;
		
		return vencedor + ": " + "3\n"
			+  perdedor + ": " + "0";
	}
}
