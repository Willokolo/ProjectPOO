package projeto;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import enums.Dificuldade;
import exceptions.FormatoNaoEsperadoException;

import com.itextpdf.text.pdf.PdfReader;

public class ExtratorPalavrasCSV {

	public static ArrayList<Palavra> extrairPalavra(String csv) throws FormatoNaoEsperadoException, IOException{
		ArrayList<Palavra> palavras = new ArrayList<Palavra>();
		
		PdfReader leitor = new PdfReader(csv);
		
		for (int i = 1; i <= leitor.getNumberOfPages(); i++) {
			String texto = PdfTextExtractor.getTextFromPage(leitor, i);
			
			for (String linha: texto.split("\\R")) {
				String[] l = linha.split(", ");
				
				Dificuldade dificuldade;
				
				if (l.length != 3) {
					throw new FormatoNaoEsperadoException();
				}

				switch(l[1]) {
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
						throw new FormatoNaoEsperadoException();
					}
			
				palavras.add(new Palavra(l[0], l[2], dificuldade));
			}
		}
		
		return palavras;
	}
}
