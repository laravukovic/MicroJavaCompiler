package rs.ac.bg.etf.pp1;

import java.util.Stack;

import rs.ac.bg.etf.pp1.CounterVisitor.FormParamCounter;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;

public class CodeGenerator extends VisitorAdaptor {
	private int mainPc;
	
	public int getMainPc() {
		return mainPc;
	}
	
	/*public void visit(PrintStmt printStmt){
		if(printStmt.getExpr().struct == Tab.intType){
			Code.loadConst(5);
			Code.put(Code.print);
		}else{
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}*/
	
	/*public void visit(NumConst cnst){
		Obj con = Tab.insert(Obj.Con, "$", cnst.struct);
		con.setLevel(0);
		con.setAdr(cnst.getNumConst());
		
		Code.load(con);
	}*/
	
	//////METODE
	
	public void visit(MethodTypeName methodTypeName){
		
		if("main".equalsIgnoreCase(methodTypeName.getMethName())){
			mainPc = Code.pc;
		}
		methodTypeName.obj.setAdr(Code.pc);
		// Collect arguments and local variables
		SyntaxNode methodNode = methodTypeName.getParent();
	
		VarCounter varCnt = new VarCounter();
		methodNode.traverseTopDown(varCnt);
		
		FormParamCounter fpCnt = new FormParamCounter();
		methodNode.traverseTopDown(fpCnt);
		
		// Generate the entry
		Code.put(Code.enter);
		Code.put(fpCnt.getCount());
		Code.put(fpCnt.getCount() + varCnt.getCount());
	
	}

	public void visit(MethodDecl methodDecl){
		Code.put(Code.exit);
		Code.put(Code.return_);
	}
	
	/////DESIGNATOR
	
	public void visit(DesignatorArrayName designatorArrayName) {
		Code.load(designatorArrayName.obj);
	}
	
	////DESIGNATOR STATEMENT
	
	public void visit(DesAssign desAssign) {
		Code.store(desAssign.getDesignator().obj);
	}
	
	public void visit(VarInc varInc) {
		if(varInc.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(varInc.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(varInc.getDesignator().obj);
	}
	
	public void visit(VarDec varDec) {
		if(varDec.getDesignator().obj.getKind() == Obj.Elem)
			Code.put(Code.dup2);
		Code.load(varDec.getDesignator().obj);
		Code.loadConst(-1);
		Code.put(Code.add);
		Code.store(varDec.getDesignator().obj);
	}
	
	/////STATEMENT
	
	public void visit(ReadStmt readStmt) {
		if(readStmt.getDesignator().obj.getType().equals(Tab.charType))
			Code.put(Code.bread);
		else
			Code.put(Code.read);
			Code.store(readStmt.getDesignator().obj);
	}
	
	public void visit(PrintStmt printStmt) {
		Code.loadConst(0);
		if(printStmt.getExpr().struct.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
		
	}
	
	public void visit(PrintStmtwithNumber printStmtwithNumber) {
		Code.loadConst(printStmtwithNumber.getN2());
		if(printStmtwithNumber.getExpr().struct.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
		
	}
	
	///FACTOR
	
	public void visit(Var var) {
		Code.load(var.getDesignator().obj);
		
		if(minusHappend.peek() == true) {
			Code.put(Code.neg);
			minusHappend.pop();
			minusHappend.push(false);  
		}
	}
	
	public void visit(NumConst NumConst) {
		Code.loadConst(NumConst.getNumConst());
		
		if(minusHappend.peek() == true) {
			Code.put(Code.neg);
			minusHappend.pop();
			minusHappend.push(false);  
		}
	}
	
	public void visit(FactExpr factExpr) {
		if(minusHappend.peek() == true) {
			Code.put(Code.neg);
			minusHappend.pop();
			minusHappend.push(false);
		}
	}
	
	public void visit(CharConst CharConst) {
		Code.loadConst(CharConst.getCharConst());
	}
	
	public void visit(BoolConst BoolConst) {
		Code.loadConst(BoolConst.getBoolConst().equalsIgnoreCase("true") ? 1 : 0);
	}
	
	public void visit(NewArray newArray) {
		Code.put(Code.newarray);
		if(newArray.getType().struct == Tab.charType) {
			Code.put(0);
		}
		else {
			Code.put(1);
		}
	}
	
	///ADDOP MULOP
	
	public void visit(AddExpr addExpr) {
		if(addExpr.getAddop() instanceof AddOperator ) {
			Code.put(Code.add);
		}
		else {
			Code.put(Code.sub);
		}
	}
	
	public void visit(MulopTerm term) {
		if(term.getMulop() instanceof MulOperator) {
			Code.put(Code.mul);
		}
		else if(term.getMulop() instanceof DivOperator) {
			Code.put(Code.div);
		}
		else {
			Code.put(Code.rem);
		}
	}
	
	////EXPR
	
	Stack<Boolean> minusHappend = new Stack<Boolean>();
	Stack<Integer> jmpExprAdr = new Stack<Integer>();
	
	public void visit(Minus Minus) {
		minusHappend.push(true);
	}
	
	public void visit(NoMinus noMinus) {
		minusHappend.push(false);
	}
	
	public void visit(BasicExpr basicExpr) {
		minusHappend.pop();
	}
	
	public void visit(BinaryExpr binaryExpr) {
		Code.fixup(jmpExprAdr.pop());
	}
	
	public void visit(BinaryOperation binaryOp) {
		Code.put(Code.dup);
		Code.loadConst(0);
		Code.putFalseJump(Code.eq, 0);
		
		jmpExprAdr.push(Code.pc-2);
		Code.put(Code.pop);
	}
	
}
