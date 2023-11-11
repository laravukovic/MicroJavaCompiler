package rs.ac.bg.etf.pp1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.test.Compiler;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;

public class MJParserTest implements Compiler {
	
	private List<CompilerError> compilerErrors = new ArrayList<CompilerError>();

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	@Override
	public List<CompilerError> compile(String sourceFilePath, String outputFilePath) {
		
		Logger log = Logger.getLogger(MJParserTest.class);
		Reader br = null;
		
		try {
			File sourceCode = new File(sourceFilePath);
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        Program prog = (Program)(s.value); 
	        CustomTab.init();
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticAnalyzer v = new SemanticAnalyzer();
			prog.traverseBottomUp(v);
	      
//			log.info(" Print count calls = " + v.printCallCount);

//			log.info(" Deklarisanih promenljivih ima = " + v.varDeclCounter);
			
			log.info("===================================");
			CustomTab.dump();
			
			if(!p.errorDetected && v.passed()) {
				File objFile = new File(outputFilePath);
				if(objFile.exists()) objFile.delete();
				
				CodeGenerator codeGenerator = new CodeGenerator();
				prog.traverseBottomUp(codeGenerator);
				Code.dataSize = v.nVars;
				Code.mainPc = codeGenerator.getMainPc();
				Code.write(new FileOutputStream(objFile));
				
				log.info("Parsiranje uspesno zavrseno!");
			}
			else {
				log.error("Parsiranje NIJE uspesno zavrseno!");
				
				compilerErrors.addAll(lexer.getErrors());
				compilerErrors.addAll(p.getErrors());
				compilerErrors.addAll(v.getErrors());
			}
			
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}
		
		return compilerErrors.size() > 0 ? compilerErrors : null;
	}
	

	public void writeOutputFile() {
		FileReader fr = null;
		FileWriter fw = null;
		
		try {
			fr = new FileReader("logs/mj-test.log");
			fw = new FileWriter("test/izlaz.out");
			
			String toCopy = "";
			
			int i;
			
			while((i = fr.read()) != -1) {
				toCopy += (char)i;
			}
			
			fw.write(toCopy);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fr != null) fr.close();
				if(fw != null) fw.close();
			}
			catch (IOException e) {
				System.err.println("Greska prilikom zatvaranja fajlova prilikom upisa standardnog izlaza");
			}
		}
	}
	
	public void writeErrorsToFile(List<CompilerError> errorList) {
		FileOutputStream fos = null;
		File file;
		
		try {
			file = new File("test/izlaz.err");
			
			fos = new FileOutputStream(file);
			
			if(file.exists()) file.createNewFile();
			
			byte[] bytesArray;
			for (CompilerError err : errorList) {
				bytesArray = (err.toString() + "\n").getBytes();
				fos.write(bytesArray);
				fos.flush();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(fos != null) {
					fos.close();
				}
			}
			catch (IOException e) {
				System.err.println("Greska prilikom zatvaranja error fajla");
			}
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Logger log = Logger.getLogger(MJParserTest.class);
		
		MJParserTest compiler = new MJParserTest();
		
		List<CompilerError> errors = compiler.compile(args[0], args[1]);
		
		if(errors != null) {
//			for (CompilerError err : errors) {
//				log.error(err.toString());
//			}
			
			if(args.length == 3 && args[2].equalsIgnoreCase("2>izlaz.err")) {
				compiler.writeErrorsToFile(errors);
			}
			else if(args.length == 4 && (args[2].equalsIgnoreCase("2>izlaz.err") || args[3].equalsIgnoreCase("2>izlaz.err"))) {
				compiler.writeErrorsToFile(errors);
			}
		}
		
		if(args.length == 3 && args[2].equalsIgnoreCase(">izlaz.out")) {
			compiler.writeOutputFile();
		}
		else if(args.length == 4 && (args[2].equalsIgnoreCase(">izlaz.out") || args[3].equalsIgnoreCase(">izlaz.out"))) {
			compiler.writeOutputFile();
		}
	}
}