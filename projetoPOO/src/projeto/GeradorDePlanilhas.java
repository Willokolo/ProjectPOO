package projeto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GeradorDePlanilhas {
	
	public static void gerarPlanilha(ArrayList<Jogador> jogadores) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		FileOutputStream fos = new FileOutputStream("dadosJogadores.xlsx");
		Sheet planilha = workbook.createSheet("Dados dos jogadores");
		int numLinha = 0;
		
		criarCabecalho(planilha, numLinha++);
		
		for (Jogador j: jogadores) {
			Row linha = planilha.createRow(numLinha++);
			String estado = j.isAtivo() ? "Ativo" : "Inativo";
			adicionarCelula(linha, 0, j.getNome());
			adicionarCelula(linha, 1, j.getEmail());
			adicionarCelula(linha, 2, j.getPontosSolo());
			adicionarCelula(linha, 3, j.getPontosDuelo());
			adicionarCelula(linha, 4, estado);
		}
		
		workbook.write(fos);
		workbook.close();
	}
	
	private static void criarCabecalho(Sheet planilha, int linha) {
		Row cabecalho = planilha.createRow(linha);
		
		adicionarCelula(cabecalho, 0, "Nome");
		adicionarCelula(cabecalho, 1, "Email");
		adicionarCelula(cabecalho, 2, "Pontos Solo");
		adicionarCelula(cabecalho, 3, "Pontos Duelo");
		adicionarCelula(cabecalho, 4, "Estado");
	}
	
	private static void adicionarCelula(Row linha, int coluna, String valor) {
		Cell celula = linha.createCell(coluna);
		celula.setCellValue(valor);
	}
	private static void adicionarCelula(Row linha, int coluna, int valor) {
		Cell celula = linha.createCell(coluna);
		celula.setCellValue(valor);
	}
}
