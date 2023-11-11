// generated with ast extension for cup
// version 0.8
// 26/7/2022 16:39:26


package rs.ac.bg.etf.pp1.ast;

public interface Visitor { 

    public void visit(Designator Designator);
    public void visit(OptFormPars OptFormPars);
    public void visit(MethodType MethodType);
    public void visit(Factor Factor);
    public void visit(Assignop Assignop);
    public void visit(Mulop Mulop);
    public void visit(DesignatorStatement DesignatorStatement);
    public void visit(VarDeclRest VarDeclRest);
    public void visit(ConstAssign ConstAssign);
    public void visit(Statements Statements);
    public void visit(Expresion Expresion);
    public void visit(Expr Expr);
    public void visit(BinaryOp BinaryOp);
    public void visit(FormPars FormPars);
    public void visit(OptSquare OptSquare);
    public void visit(VarDeclList VarDeclList);
    public void visit(SingleStatement SingleStatement);
    public void visit(VarDecl VarDecl);
    public void visit(OptionalMinus OptionalMinus);
    public void visit(ParamDecl ParamDecl);
    public void visit(Addop Addop);
    public void visit(MethodDeclList MethodDeclList);
    public void visit(Statement Statement);
    public void visit(Term Term);
    public void visit(FormParamDecl FormParamDecl);
    public void visit(StmtList StmtList);
    public void visit(MultiStatements MultiStatements);
    public void visit(StmNoLabel StmNoLabel);
    public void visit(SingleStmtLabelNoLabel SingleStmtLabelNoLabel);
    public void visit(SingleStmtLabel SingleStmtLabel);
    public void visit(DesStmt DesStmt);
    public void visit(DesignatorStatementDerived1 DesignatorStatementDerived1);
    public void visit(VarDec VarDec);
    public void visit(VarInc VarInc);
    public void visit(DesAssign DesAssign);
    public void visit(PrintStmtwithNumber PrintStmtwithNumber);
    public void visit(PrintStmt PrintStmt);
    public void visit(ReadStmt ReadStmt);
    public void visit(DesignatorArrayName DesignatorArrayName);
    public void visit(DesArray DesArray);
    public void visit(DesBasic DesBasic);
    public void visit(Var Var);
    public void visit(FactExpr FactExpr);
    public void visit(NewArray NewArray);
    public void visit(BoolConst BoolConst);
    public void visit(CharConst CharConst);
    public void visit(NumConst NumConst);
    public void visit(FactorTerm FactorTerm);
    public void visit(MulopTerm MulopTerm);
    public void visit(TermExpr TermExpr);
    public void visit(AddExpr AddExpr);
    public void visit(NoMinus NoMinus);
    public void visit(Minus Minus);
    public void visit(BinaryOperation BinaryOperation);
    public void visit(BinaryExpr BinaryExpr);
    public void visit(BasicExpr BasicExpr);
    public void visit(AssignOperator AssignOperator);
    public void visit(ModOperator ModOperator);
    public void visit(DivOperator DivOperator);
    public void visit(MulOperator MulOperator);
    public void visit(SubOperator SubOperator);
    public void visit(AddOperator AddOperator);
    public void visit(Label Label);
    public void visit(Type Type);
    public void visit(VoidRetType VoidRetType);
    public void visit(MethodRetType MethodRetType);
    public void visit(MethodTypeName MethodTypeName);
    public void visit(NoStmt NoStmt);
    public void visit(MultiStmtList MultiStmtList);
    public void visit(FormParamArray FormParamArray);
    public void visit(FormParam FormParam);
    public void visit(NoFormParams NoFormParams);
    public void visit(FormParams FormParams);
    public void visit(NoFormPars NoFormPars);
    public void visit(OptFormParams OptFormParams);
    public void visit(MethodDecl MethodDecl);
    public void visit(NoMethodDeclarations NoMethodDeclarations);
    public void visit(MethodDeclarations MethodDeclarations);
    public void visit(NoVarDecl NoVarDecl);
    public void visit(VarDeclarations VarDeclarations);
    public void visit(NoSquares NoSquares);
    public void visit(Squares Squares);
    public void visit(NoVarDeclMore NoVarDeclMore);
    public void visit(VarDeclArrayMore VarDeclArrayMore);
    public void visit(VarDeclMore VarDeclMore);
    public void visit(VarDeclDerived2 VarDeclDerived2);
    public void visit(VarDeclDerived1 VarDeclDerived1);
    public void visit(VarDeclarationArray VarDeclarationArray);
    public void visit(VarDeclaration VarDeclaration);
    public void visit(MultiBoolConst MultiBoolConst);
    public void visit(SingleBoolConst SingleBoolConst);
    public void visit(MultiCharConst MultiCharConst);
    public void visit(SingleCharConst SingleCharConst);
    public void visit(MultiNumberConst MultiNumberConst);
    public void visit(SingleNumberConst SingleNumberConst);
    public void visit(ConstDecl ConstDecl);
    public void visit(ParamDeclDerived1 ParamDeclDerived1);
    public void visit(VarParam VarParam);
    public void visit(ConstParam ConstParam);
    public void visit(ProgName ProgName);
    public void visit(Program Program);

}