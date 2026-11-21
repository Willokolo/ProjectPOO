package projeto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorDeRelatorios {
	public static void gerarRelatorioPalavras(ArrayList<Palavra> palavras) throws FileNotFoundException, DocumentException {
		Paragraph p;
		
		Document doc = new Document(PageSize.A4);
		OutputStream os = new FileOutputStream("relatorioPalavras.pdf");
		PdfWriter.getInstance(doc, os);
		doc.open();
		
		p = new Paragraph("Palavras:");
		doc.add(p);
		if (!palavras.isEmpty()) {
			for (Palavra palavra: palavras) {
				p = new Paragraph(palavra.getPalavra() + ", Acertos: " + palavra.getAcertos() + ", Erros: " + palavra.getErros());
				doc.add(p);
			}
		}
		else {
			p = new Paragraph("Sem palavras registradas.");
			doc.add(p);
		}
		
		doc.close();
	}
}
