package projeto;

import enums.Resultado;
import enums.TipoDePartida;
import exceptions.RetornoVazioException;

public class PartidaSolo extends Partida {
	private Jogador jogador;
	private Resultado resultado = Resultado.DERROTA;
	
	public PartidaSolo(Jogador jogador, Palavra palavra) {
		this.jogador = jogador;
		rodadas.add(new JogoDaForca(palavra, jogador));
	}
	
	public JogoDaForca getRodada() {
		return rodadas.get(0);
	}
	public TipoDePartida getTipo() {
		return TipoDePartida.SOLO;
	}
	public Resultado getResultado() {
		return resultado;
	}
	public int getPontos() {
		return rodadas.get(0).getPontos(jogador);
	}

	public Jogador getVencedor() throws RetornoVazioException {
		if (rodadas.get(0).estaEliminado(jogador)) {
			resultado = Resultado.DERROTA;
			throw new RetornoVazioException("O jogador perdeu.");
		}
		return jogador;
	}

}
