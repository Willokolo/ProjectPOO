package projeto;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GeradorDeRelatorios {
	public static void gerarRelatorio(CentralDeInformacoes central) {
		Paragraph p;
		
		try {
			Document doc = new Document(PageSize.A4);
			OutputStream os = new FileOutputStream("relatorio.pdf");
			PdfWriter.getInstance(doc, os);
			doc.open();
			
			p = new Paragraph("Jogadores:");
			doc.add(p);
			if (!central.getTodosOsJogadores().isEmpty()) {
				for (Jogador jogador: central.getTodosOsJogadores()) {
					p = new Paragraph(jogador.toString());
					doc.add(p);
				}
			}
			else {
				p = new Paragraph("Sem jogadores registrados.");
				doc.add(p);
			}
			
			p = new Paragraph("Palavras:");
			doc.add(p);
			if (!central.getPalavras().isEmpty()) {
				for (Palavra palavra: central.getPalavras()) {
					p = new Paragraph(palavra.toString());
					doc.add(p);
				}
			}
			else {
				p = new Paragraph("Sem palavras registradas.");
				doc.add(p);
			}
			
			doc.close();
			
		} catch (DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
