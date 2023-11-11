// generated with ast extension for cup
// version 0.8
// 26/7/2022 16:39:26


package rs.ac.bg.etf.pp1.ast;

public class BinaryExpr extends Expr {

    private OptionalMinus OptionalMinus;
    private Expresion Expresion;
    private BinaryOp BinaryOp;
    private OptionalMinus OptionalMinus1;
    private Expresion Expresion2;

    public BinaryExpr (OptionalMinus OptionalMinus, Expresion Expresion, BinaryOp BinaryOp, OptionalMinus OptionalMinus1, Expresion Expresion2) {
        this.OptionalMinus=OptionalMinus;
        if(OptionalMinus!=null) OptionalMinus.setParent(this);
        this.Expresion=Expresion;
        if(Expresion!=null) Expresion.setParent(this);
        this.BinaryOp=BinaryOp;
        if(BinaryOp!=null) BinaryOp.setParent(this);
        this.OptionalMinus1=OptionalMinus1;
        if(OptionalMinus1!=null) OptionalMinus1.setParent(this);
        this.Expresion2=Expresion2;
        if(Expresion2!=null) Expresion2.setParent(this);
    }

    public OptionalMinus getOptionalMinus() {
        return OptionalMinus;
    }

    public void setOptionalMinus(OptionalMinus OptionalMinus) {
        this.OptionalMinus=OptionalMinus;
    }

    public Expresion getExpresion() {
        return Expresion;
    }

    public void setExpresion(Expresion Expresion) {
        this.Expresion=Expresion;
    }

    public BinaryOp getBinaryOp() {
        return BinaryOp;
    }

    public void setBinaryOp(BinaryOp BinaryOp) {
        this.BinaryOp=BinaryOp;
    }

    public OptionalMinus getOptionalMinus1() {
        return OptionalMinus1;
    }

    public void setOptionalMinus1(OptionalMinus OptionalMinus1) {
        this.OptionalMinus1=OptionalMinus1;
    }

    public Expresion getExpresion2() {
        return Expresion2;
    }

    public void setExpresion2(Expresion Expresion2) {
        this.Expresion2=Expresion2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptionalMinus!=null) OptionalMinus.accept(visitor);
        if(Expresion!=null) Expresion.accept(visitor);
        if(BinaryOp!=null) BinaryOp.accept(visitor);
        if(OptionalMinus1!=null) OptionalMinus1.accept(visitor);
        if(Expresion2!=null) Expresion2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptionalMinus!=null) OptionalMinus.traverseTopDown(visitor);
        if(Expresion!=null) Expresion.traverseTopDown(visitor);
        if(BinaryOp!=null) BinaryOp.traverseTopDown(visitor);
        if(OptionalMinus1!=null) OptionalMinus1.traverseTopDown(visitor);
        if(Expresion2!=null) Expresion2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptionalMinus!=null) OptionalMinus.traverseBottomUp(visitor);
        if(Expresion!=null) Expresion.traverseBottomUp(visitor);
        if(BinaryOp!=null) BinaryOp.traverseBottomUp(visitor);
        if(OptionalMinus1!=null) OptionalMinus1.traverseBottomUp(visitor);
        if(Expresion2!=null) Expresion2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("BinaryExpr(\n");

        if(OptionalMinus!=null)
            buffer.append(OptionalMinus.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expresion!=null)
            buffer.append(Expresion.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BinaryOp!=null)
            buffer.append(BinaryOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptionalMinus1!=null)
            buffer.append(OptionalMinus1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expresion2!=null)
            buffer.append(Expresion2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [BinaryExpr]");
        return buffer.toString();
    }
}
