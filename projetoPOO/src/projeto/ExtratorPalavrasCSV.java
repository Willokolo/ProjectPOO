package projeto;

import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.PdfReader;

public class ExtratorPalavrasCSV {

	public static ArrayList<Palavra> extrairPalavra(String csv){
		ArrayList<Palavra> palavras = new ArrayList<Palavra>();
		
		try {
			PdfReader leitor = new PdfReader(csv);
			
			for (int i = 1; i <= leitor.getNumberOfPages(); i++) {
				String texto = PdfTextExtractor.getTextFromPage(leitor, i);
				
				for (String linha: texto.split("\\R")) {
					String[] l = linha.split(",");
					
					if (l.length != 3) {
						return null;
					}
					
					Dificuldade dificuldade = l[1].equals("0") ? Dificuldade.fácil : 
											l[1].equals("1") ? Dificuldade.médio :
											Dificuldade.difícil;
					
					palavras.add(new Palavra(l[0], l[2], dificuldade));
				}
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return palavras;
	}
}
