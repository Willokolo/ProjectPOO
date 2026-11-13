package paineis;

import componentes.Painel;

public abstract class PainelInicial extends Painel {
	
	public PainelInicial(String nome) {
		super(nome);
		
		criarTextos();
		criarCamposTexto();
		criarBotoes();
		
		labelFundoEscuroCentral();
		
		setVisible(true);
	}
	
	protected abstract void criarTextos();
	protected abstract void criarCamposTexto();
	protected abstract void criarBotoes();
	protected abstract void labelFundoEscuroCentral();
}
