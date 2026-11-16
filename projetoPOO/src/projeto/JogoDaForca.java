package projeto;

public class JogoDaForca {
	private Palavra palavra;
	private String gabarito;
	
	public JogoDaForca(Palavra palavra) {
		this.palavra = palavra;
		gabarito = "";
		
		String nome = this.palavra.getPalavra();
		for(int i = 0; i < nome.length(); i++) {
			gabarito += "?";
		}
		
	}
	
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
		return contem;
	}
	
	public boolean acabou() {
		for (int i = 0; i < gabarito.length(); i++) {
			if (gabarito.charAt(i) == '?')
				return false;
		}
		return true;
	}
}
