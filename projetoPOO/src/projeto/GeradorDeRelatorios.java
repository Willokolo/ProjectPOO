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
	
	public static void gerarRelatorioImportacao(ResultadoImportacao resultado) throws FileNotFoundException, DocumentException {
		Paragraph p;
		
		Document doc = new Document(PageSize.A4);
		OutputStream os = new FileOutputStream("relatorioImportacao.pdf");
		PdfWriter.getInstance(doc, os);
		doc.open();
		
		p = new Paragraph("Cadastradas: " + resultado.getCadastradas());
		doc.add(p);
		p = new Paragraph("Editadas: " + resultado.getEditadas());
		doc.add(p);
		p = new Paragraph("Excluidas: " + resultado.getExcluidas());
		doc.add(p);
		
		p = new Paragraph("Rejeitadas:");
		doc.add(p);
		if (resultado.getRejeitadas().isEmpty()) {
			p = new Paragraph("Não houveram palavras rejeitadas.");
			doc.add(p);
		} else {
			for (String s: resultado.getRejeitadas()) {
				p = new Paragraph(s);
				doc.add(p);
			}
		}
		
		doc.close();
	}
}
