package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	int printCallCount = 0;
	int varDeclCount = 0;
	Obj currentMethod = null;
	Struct lastType = null;
	int varDeclLine;
	boolean returnFound = false;
	boolean errorDetected = false;
	int nVars = 0;
	
	
	////////GRESKE
	
	List<CompilerError> compilerErrors = new ArrayList<CompilerError>();
	
	private void reportError(int line, String message) {
		CompilerError ce = new CompilerError(line, message, CompilerErrorType.SEMANTIC_ERROR);
		compilerErrors.add(ce);
	}
	
	public List<CompilerError> getErrors() {
		return compilerErrors;
	}
	
	Logger log = Logger.getLogger(getClass());
	
	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		
		if (line != 0) {
			msg.append (" na liniji ").append(line);
			reportError(info.getLine(), message);
		}
		else {
			reportError(0, message);
		}
		
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	////////OBRADA PROGRAMA
	
    public void visit(Program program) {
    	Obj mainMethod = Tab.find("main");
		
		if(mainMethod == Tab.noObj) {
			report_error("Greska: ne postoji main funkcija u programu", program);
		}
		else if(mainMethod.getType() != Tab.noType) {
			report_error("Greska : main funkcija mora biti void tipa!", program);
		}
		else {
			int fpCnt = 0;
			
			for(Obj obj : mainMethod.getLocalSymbols()) {
				fpCnt += obj.getFpPos();
			}
			
			if(fpCnt > 0) {
				report_error("Greska : main funkcija ne sme da ima parametre!", program);
			}
		}
    	
    	nVars = Tab.currentScope.getnVars();
    	Tab.chainLocalSymbols(program.getProgName().obj);
    	Tab.closeScope();
    }
    
    public void visit(ProgName progName){
    	progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
    	Tab.openScope();
    }
    
    ////KONSTANTE
    
    public void visit(SingleNumberConst numConst) {
    	Obj objNumConst = Tab.currentScope().findSymbol(numConst.getConstName());
    	
    	if(objNumConst == null) {
			Obj newNumConst = Tab.insert(Obj.Con, numConst.getConstName(), lastType);
			newNumConst.setAdr(numConst.getConstVal());
			report_info("Deklarisana je nova konstanta: " + CustomTab.printObj(newNumConst), numConst);
		}
		else {
			report_error("Greska: identifikator " + numConst.getConstName() + " je vec deklarisan!", numConst);
		}
		
		if(lastType != Tab.intType) {
			report_error("Greska: konstanta " + numConst.getConstName() + " nije odgovarajuceg tipa", numConst);
		}
    }
    
    public void visit(MultiNumberConst numConst) {
		Obj objNumConst = Tab.currentScope.findSymbol(numConst.getConstName());
		
		if(objNumConst == null) {
			Obj newNumConst = Tab.insert(Obj.Con, numConst.getConstName(), lastType);
			newNumConst.setAdr(numConst.getConstVal());
			report_info("Deklarisana je nova konstanta: " + CustomTab.printObj(newNumConst), numConst);
		}
		else {
			report_error("Greska: identifikator " + numConst.getConstName() + " je vec deklarisan!", numConst);
		}
		
		if(lastType != Tab.intType) {
			report_error("Greska: konstanta " + numConst.getConstName() + " nije odgovarajuceg tipa", numConst);
		}
	}
    
    public void visit(SingleCharConst charConst) {
		Obj objcharConst = Tab.currentScope.findSymbol(charConst.getConstName());
		
		if(objcharConst == null) {
			Obj newcharConst = Tab.insert(Obj.Con, charConst.getConstName(), lastType);
			newcharConst.setAdr(charConst.getConstVal());
			report_info("Deklarisana je nova konstanta: " + CustomTab.printObj(newcharConst), charConst);
		}
		else {
			report_error("Greska: identifikator " + charConst.getConstName() + " je vec deklarisan!", charConst);
		}
		
		if(lastType != Tab.charType) {
			report_error("Greska: konstanta " + charConst.getConstName() + " nije odgovarajuceg tipa", charConst);
		}
	}
    
    public void visit(MultiCharConst charConst) {
		Obj objcharConst = Tab.currentScope.findSymbol(charConst.getConstName());
		
		if(objcharConst == null) {
			Obj newcharConst = Tab.insert(Obj.Con, charConst.getConstName(), lastType);
			newcharConst.setAdr(charConst.getConstVal());
			report_info("Deklarisana je nova konstanta: " + CustomTab.printObj(newcharConst), charConst);
		}
		else {
			report_error("Greska: identifikator " + charConst.getConstName() + " je vec deklarisan!", charConst);
		}
		
		if(lastType != Tab.charType) {
			report_error("Greska: konstanta " + charConst.getConstName() + " nije odgovarajuceg int tipa", charConst);
		}
	}
    
    public void visit(SingleBoolConst boolConst) {
		Obj objboolConst = Tab.currentScope.findSymbol(boolConst.getConstName());
		
		if(objboolConst == null) {
			Obj newboolConst = Tab.insert(Obj.Con, boolConst.getConstName(), lastType);
			newboolConst.setAdr(boolConst.getConstVal().equals("true") ? 1 : 0);
			report_info("Deklarisana je nova konstanta: " + CustomTab.printObj(newboolConst), boolConst);
		}
		else {
			report_error("Greska: identifikator " + boolConst.getConstName() + " je vec deklarisan!", boolConst);
		}
		
		if(lastType != CustomTab.boolType) {
			report_error("Greska: konstanta " + boolConst.getConstName() + " nije odgovarajuceg tipa", boolConst);
		}
	}
	
	public void visit(MultiBoolConst boolConst) {
		Obj objboolConst = Tab.currentScope.findSymbol(boolConst.getConstName());
		
		if(objboolConst == null) {
			Obj newboolConst = Tab.insert(Obj.Con, boolConst.getConstName(), lastType);
			newboolConst.setAdr(boolConst.getConstVal().equals("true") ? 1 : 0);
			report_info("Deklarisana je nova konstanta: " + CustomTab.printObj(newboolConst), boolConst);
		}
		else {
			report_error("Greska: identifikator " + boolConst.getConstName() + " je vec deklarisan!", boolConst);
		}
		
		if(lastType != CustomTab.boolType) {
			report_error("Greska: konstanta " + boolConst.getConstName() + " nije odgovarajuceg int tipa", boolConst);
		}
	}
    
	//////PROMENJIVE
		
	public void visit(VarDeclaration varDeclaration) {
		if(Tab.currentScope.findSymbol(varDeclaration.getVarName()) != null) {
			report_error("Greska: Promenljiva " + varDeclaration.getVarName() + " je vec deklarisana", varDeclaration);
		}
		else {
			Obj varNode = Tab.insert(Obj.Var, varDeclaration.getVarName(), lastType);
			report_info("Deklarisana je nova promenljiva: " + CustomTab.printObj(varNode), varDeclaration);
		}
	}
	
	public void visit(VarDeclarationArray varDeclaration) {
		if(Tab.currentScope.findSymbol(varDeclaration.getVarName()) != null) {
			report_error("Greska: Promenljiva " + varDeclaration.getVarName() + " je vec deklarisana", varDeclaration);
		}
		else {
			Obj varNode = Tab.insert(Obj.Var, varDeclaration.getVarName(), new Struct(Struct.Array, lastType));
			report_info("Deklarisana je nova niz promenljiva: " + CustomTab.printObj(varNode), varDeclaration);
		}
	}
	
	public void visit(VarDeclMore varDeclaration) {
		if(Tab.currentScope.findSymbol(varDeclaration.getVarName()) != null) {
			report_error("Greska: Promenljiva " + varDeclaration.getVarName() + " je vec deklarisana", varDeclaration);
		}
		else {
			Obj varNode = Tab.insert(Obj.Var, varDeclaration.getVarName(), lastType);
			report_info("Deklarisana je nova promenljiva: " + CustomTab.printObj(varNode), varDeclaration);
		}
	}
	
	public void visit(VarDeclArrayMore varDeclaration) {
		if(Tab.currentScope.findSymbol(varDeclaration.getVarName()) != null) {
			report_error("Greska: Promenljiva " + varDeclaration.getVarName() + " je vec deklarisana", varDeclaration);
		}
		else {
			Obj varNode = Tab.insert(Obj.Var, varDeclaration.getVarName(), new Struct(Struct.Array, lastType));
			report_info("Deklarisana je nova niz promenljiva: " + CustomTab.printObj(varNode), varDeclaration);
		}
	}
	
	//////METODE
	
	public void visit(MethodTypeName methodTypeName) {
		if(Tab.currentScope.findSymbol(methodTypeName.getMethName()) != null) {
			report_error("Greska: Metoda sa imenom " + methodTypeName.getMethName() + " je vec deklarisana", methodTypeName);
		}
		else {
			currentMethod = Tab.insert(Obj.Meth, methodTypeName.getMethName(), methodTypeName.getMethodType().struct);
			methodTypeName.obj = currentMethod;
			report_info("Obradjuje se funkcija: " + CustomTab.printObj(methodTypeName.obj), methodTypeName);
			Tab.openScope();
		}
	
	}
	
	public void visit(MethodDecl methodDecl) {
		/*if(!returnFound && currentMethod.getType() != Tab.noType) {
			report_error("Greska : funkcija " + currentMethod.getName() + " nema return iskaz ", methodDecl);
		}*/
		
		Tab.chainLocalSymbols(currentMethod);
		Tab.closeScope();
		
		//returnFound = false;
		currentMethod = null;
	}
	
	public void visit(MethodRetType retType) {
		retType.struct = retType.getType().struct;
	}
	
	public void visit(VoidRetType retType) {
		retType.struct = Tab.noType;
	}
	
	public void visit(FormParam formParam) {
		Obj param = Tab.currentScope.findSymbol(formParam.getParamName());
		if(param != null) {
			report_error("Greska: Promenljiva sa imenom " + formParam.getParamName() + " je vec deklarisana", formParam);
		}
		else {
			Obj newParam = Tab.insert(Obj.Var, formParam.getParamName(), formParam.getType().struct);
			newParam.setFpPos(1);
			report_info("Deklarisan je novi parametar " + CustomTab.printObj(newParam) + " metode " + currentMethod.getName(), formParam);
		}
	}
	
	public void visit(FormParamArray formParam) {
		Obj param = Tab.currentScope.findSymbol(formParam.getParamName());
		if(param != null) {
			report_error("Greska: Promenljiva sa imenom " + formParam.getParamName() + " je vec deklarisana", formParam);
		}
		else {
			Obj newParam = Tab.insert(Obj.Var, formParam.getParamName(), new Struct(Struct.Array, formParam.getType().struct));
			newParam.setFpPos(1);
			report_info("Deklarisan je novi parametar " + CustomTab.printObj(newParam) + " metode " + currentMethod.getName(), formParam);
		}
	}
	
	//////DESIGNATOR
	
	public void visit(DesBasic designator) {
		Obj obj = Tab.find(designator.getVarName());
		
		if(obj == Tab.noObj) {
			report_error("Greska : ime " + designator.getVarName() + " nije deklarisano!", designator);
		}
		else if (obj.getKind() == Obj.Meth) {
			this.calledMethodsList.add(0, obj);
//			this.actualParams.add(0, new ArrayList<Struct>());
		}
		
		designator.obj = obj;
		report_info("Nadjena promenjiva: " + CustomTab.printObj(obj), designator);
	}
	
	public void visit(DesArray desArray) {
		if(desArray.getDesignatorArrayName().obj == Tab.noObj) {
			desArray.obj = Tab.noObj;
			return;
		}
		else if(desArray.getDesignatorArrayName().obj.getKind() == Obj.Meth) {
			this.calledMethodsList.add(0, desArray.getDesignatorArrayName().obj);
//			this.actualParams.add(0, new ArrayList<Struct>());
		}
		
		if(desArray.getExpr().struct != Tab.intType) {
			report_error("Greska : Indeks niza mora biti celobrojna vrednost! ", desArray);
			desArray.obj = Tab.noObj;
		}
		else {
			desArray.obj = new Obj(Obj.Elem, desArray.getDesignatorArrayName().getVarName(), desArray.getDesignatorArrayName().obj.getType().getElemType());
		}
	}
	
	public void visit(DesignatorArrayName arrayName) {
		Obj arrayType = Tab.find(arrayName.getVarName());
		
		if(arrayType == Tab.noObj) {
			report_error("Greska : nije deklarian niz ", arrayName);
			arrayName.obj=  Tab.noObj;
		}
		else if(Struct.Array != arrayType.getType().getKind()) {
			report_error("Tip simbola mora biti nizovnog tipa!", arrayName);
			arrayName.obj = Tab.noObj;
		}
		else {
			arrayName.obj = arrayType;
			report_info("Nadjena promenjiva: " + CustomTab.printObj(arrayType), arrayName);
		}
	}
	
	/////DESIGNATOR STATEMENT
	
	public void visit(DesAssign assign) {
		if(!(assign.getDesignator().obj.getKind() == Obj.Elem || assign.getDesignator().obj.getKind() == Obj.Var)) {
			report_error("Greska : simbol mora biti promenljiva, ili element niza", assign);
		}
		else if(!assign.getExpr().struct.assignableTo(assign.getDesignator().obj.getType())) {
			report_error("Greska : tipovi u dodeli vrednosti nisu kompatibilni", assign);
		}
		else {
			//report_info("Promenljivoj se dodeljuje vrednost: " + CustomTab.printObj(assign.getDesignator().obj), assign);
		}
	}
	
	public void visit(VarInc varInc) {
		if(varInc.getDesignator().obj.getKind() != Obj.Elem && varInc.getDesignator().obj.getType().getKind() != Obj.Var) {
			report_error("Greska : simbol nije promenljiva ili element niza!", varInc);
		}
		else if(varInc.getDesignator().obj.getType() != Tab.intType) {
			report_error("Greska : promenljiva mora biti tipa int", varInc);
		}
		else {
			//report_info("Promenljiva: " + CustomTab.printObj(varInc.getDesignator().obj) + " je inkrementirana ", varInc);
		}
	}
	
	public void visit(VarDec varDec) {
		if(varDec.getDesignator().obj.getKind() != Obj.Elem && varDec.getDesignator().obj.getType().getKind() != Obj.Var) {
			report_error("Greska : simbol nije promenljiva ili element niza!", varDec);
		}
		else if(varDec.getDesignator().obj.getType() != Tab.intType) {
			report_error("Greska : promenljiva mora biti tipa int", varDec);
		}
		else {
			//report_info("Promenljiva " + CustomTab.printObj(varDec.getDesignator().obj) + " je dekrementirana ", varDec);
		}
	}
	
	//////STATEMENT
	
	public void visit(PrintStmt stmt) {
		if(!(stmt.getExpr().struct.equals(Tab.intType) || stmt.getExpr().struct.equals(Tab.charType) || stmt.getExpr().struct.equals(CustomTab.boolType))) {
			report_error("Greska : parametar print funkcije nije validan tip (int, char, bool)", stmt);
		}	
	}
	
	public void visit(PrintStmtwithNumber stmt) {
		if(!(stmt.getExpr().struct.equals(Tab.intType) || stmt.getExpr().struct.equals(Tab.charType) || stmt.getExpr().struct.equals(CustomTab.boolType))) {
			report_error("Greska : parametar print funkcije nije validan tip (int, char, bool)", stmt);
		}
	}
	
	
	public void visit(ReadStmt stmt) {
		if(stmt.getDesignator().obj.getKind() != Obj.Elem && stmt.getDesignator().obj.getKind() != Obj.Var) {
			report_error("Greska : simbol nije promenljiva ili element niza!", stmt);	
		}
		else if(!(stmt.getDesignator().obj.getType() == Tab.intType || stmt.getDesignator().obj.getType() == Tab.charType || stmt.getDesignator().obj.getType() == CustomTab.boolType)) {
			report_error("Greska : parametar read funkcije nije validan tip (int, char, bool)", stmt);
		}
		
		report_info("Citanje promenjive: " + CustomTab.printObj(stmt.getDesignator().obj), stmt);
	}
	
	//////FACTOR
	
	public void visit(Var var) {
		var.struct = var.getDesignator().obj.getType();
	}
	
	public void visit(NumConst numConst) {
		numConst.struct = Tab.intType;
	}
	
	public void visit(CharConst charConst) {
		charConst.struct = Tab.charType;
	}
	
	public void visit(BoolConst boolConst) {
		boolConst.struct = CustomTab.boolType;
	}
	
	public void visit(NewArray newArray) {
		if(newArray.getExpr().struct != Tab.intType) {
			report_error("Greska : duzina niza mora biti celobrojna vrednost", newArray);
		}
		else {
			report_info("Formiran novi niz ", newArray);
		}
		newArray.struct = new Struct(Struct.Array, newArray.getType().struct);
	}
	
	public void visit(FactExpr expr) {
		expr.struct=  expr.getExpr().struct;
	}
	
	//////TERM
	
	public void visit(FactorTerm term) {
		term.struct = term.getFactor().struct;
	}
	
	public void visit(MulopTerm mulopTerm) {
		Struct term = mulopTerm.getTerm().struct;
		Struct factor = mulopTerm.getFactor().struct;
		
		if(term.equals(factor) && term == Tab.intType) {
			mulopTerm.struct = factor;
		}
		else {
			report_error("Greska : nekompatibilni tipovi u izrazu", mulopTerm);
			mulopTerm.struct = Tab.noType;
		}
	}
	
	//////EXPR
	
	public void visit(BasicExpr basicExpr) {
		if(basicExpr.getOptionalMinus() instanceof Minus && !basicExpr.getExpresion().struct.equals(Tab.intType)) {
			report_error("Greska : minus se moze javiti iskljucivo uz simbole tipa int ", basicExpr);
			basicExpr.struct = Tab.noType;
		}
		else{
			basicExpr.struct = basicExpr.getExpresion().struct;
		}
	}
	
	public void visit(BinaryExpr binaryExpr) {
		binaryExpr.struct = binaryExpr.getExpresion().struct;
	}
	
	/////EXPR1
	
	public void visit(AddExpr addExpr) {
		Struct term = addExpr.getExpresion().struct;
		Struct t = addExpr.getTerm().struct;
		
		if(term.equals(t) && term == Tab.intType) {
			addExpr.struct = term;
		} else {
			report_error("Greska : nekompatibilni tipovi u izrazu za sabiranje!", addExpr);
			addExpr.struct = Tab.noType;
		}
	}
	
	public void visit(TermExpr termExpr) {
		termExpr.struct = termExpr.getTerm().struct;
	}	
	
	
	/////POZIVANJE METODA
	List<Obj> calledMethodsList = new ArrayList<Obj>();
//	List<List<Struct>> actualParams = new ArrayList<List<Struct>>();
	boolean function_called = false;

	
	////TIPOVI
	public void visit(Type type){
    	Obj typeNode = Tab.find(type.getType());	
    	if(typeNode == Tab.noObj) {
    		report_error("Nije pronadjen tip " + type.getType() + " u tabeli simbola! ", null);
    		type.struct = Tab.noType;
    	} else {
    		if(Obj.Type == typeNode.getKind()) {
    			type.struct = typeNode.getType();
    			lastType = type.struct;
    			varDeclLine = type.getLine();
    		}else{
    			report_error("Greska: Ime " + type.getType() + " ne predstavlja tip!", type);
    			type.struct = Tab.noType;
    		}
    	}
    }
    
    public boolean passed(){
    	return !errorDetected;
    }
}
