package projeto;

import java.io.IOException;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import enums.Dificuldade;
import exceptions.PalavraJaExisteException;
import exceptions.PalavraNaoExisteException;

import com.itextpdf.text.pdf.PdfReader;

public class ExtratorPalavrasCSV {

	public static ResultadoImportacao lerArquivo(String csv, CentralDeInformacoes central) throws IOException{
		ResultadoImportacao resultado = new ResultadoImportacao();
		PdfReader leitor = new PdfReader(csv);
		
		for (int i = 1; i <= leitor.getNumberOfPages(); i++) {
			String texto = PdfTextExtractor.getTextFromPage(leitor, i);
			
			for (String linha: texto.split("\\R")) {
				if(linha.isBlank())
					continue;
				
				linha = linha.trim();
				String motivo = processarLinha(linha, central, resultado);
				
				if (motivo != null)
					resultado.rejeitar(linha, motivo);
				
			}
		}
		leitor.close();
		
		return resultado;
	}
	
	private static String processarLinha(String linha, CentralDeInformacoes central, ResultadoImportacao resultado) {
		String[] l = linha.split(", ");
		
		if (l.length != 4) {
			return "Quantidade de campos diferente de 4";
		}
		
		for (String s: l) {
			if (s.isEmpty())
				return "Campo vazio";
		}
			
		String operacao = l[0].trim();
		String palavra = l[1].trim();
		String dif = l[2].trim();
		String dica = l[3].trim();
		
		if (palavra.length() > 19)
			return "Palavra excede o limite de letras";
		
		Dificuldade dificuldade;
		switch(dif) {
		case "0":
			dificuldade = Dificuldade.FACIL;
			break;
			
		case "1":
			dificuldade = Dificuldade.NORMAL;
			break;
		
		case "2":
			dificuldade = Dificuldade.DIFICIL;
			break;
		
		default:
			return "Dificuldade inválida";
		}
		
		String msg = "";
		Palavra p = null;
		try {
			p = central.recuperarPalavra(palavra);
		} catch (PalavraNaoExisteException e) {
			msg = "Palavra não cadastrada";
		}
		switch (operacao) {
		case "0":
			try {
				central.addPalavra(new Palavra(palavra, dica, dificuldade));
				resultado.addCadastradas();
			} catch (PalavraJaExisteException e) {
				return "Palavra já cadastrada";
			}
			break;
			
		case "1":
			if (p == null)
				return msg;
			if (p.getDificuldade() != dificuldade)
				return "Não é possível alterar a dificuldade";
			
			p.setDica(dica);
			resultado.addEditadas();
			break;
			
		case "2":
			if (p == null)
				return msg;
			
			central.getPalavras().remove(p);
			resultado.addExcluidas();
			break;
			
		default:
			return "Operação inválida";
		}
		
		return null;
	}
}
