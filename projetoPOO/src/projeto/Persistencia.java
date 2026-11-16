package projeto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Persistencia {
	
	//atributos
	private XStream xstream;
	private File arquivo;
	
	
	//métodos
	
	//construtor que permite o xstream ler as classes
	public Persistencia() {
		xstream = new XStream(new DomDriver());
		xstream.allowTypesByWildcard(new String[] {"projeto.**"});
	}
	
	
	//checa que o arquivo existe
	public boolean existe() {
		arquivo = new File("central.xml");
		return arquivo.exists();
	}
	
	
	//salva um objeto CentralDeInformacoes em um arquivo .xml
	public void salvarCentral(CentralDeInformacoes c) {
		String xml = xstream.toXML(c);
		
		try {
			arquivo = new File("central.xml");
			arquivo.createNewFile();
			PrintWriter gravar = new PrintWriter(arquivo);
			gravar.print(xml);
			gravar.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//recupera um objeto CentralDeInformacoes de um arquivo .xml
	public CentralDeInformacoes recuperarCentral(){
		try {
			FileInputStream f = new FileInputStream("central.xml");
			return (CentralDeInformacoes) xstream.fromXML(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new CentralDeInformacoes();
	}
}
