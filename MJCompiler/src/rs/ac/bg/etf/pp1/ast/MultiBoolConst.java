// generated with ast extension for cup
// version 0.8
// 26/7/2022 16:39:26


package rs.ac.bg.etf.pp1.ast;

public class MultiBoolConst extends ConstAssign {

    private ConstAssign ConstAssign;
    private String constName;
    private String constVal;

    public MultiBoolConst (ConstAssign ConstAssign, String constName, String constVal) {
        this.ConstAssign=ConstAssign;
        if(ConstAssign!=null) ConstAssign.setParent(this);
        this.constName=constName;
        this.constVal=constVal;
    }

    public ConstAssign getConstAssign() {
        return ConstAssign;
    }

    public void setConstAssign(ConstAssign ConstAssign) {
        this.ConstAssign=ConstAssign;
    }

    public String getConstName() {
        return constName;
    }

    public void setConstName(String constName) {
        this.constName=constName;
    }

    public String getConstVal() {
        return constVal;
    }

    public void setConstVal(String constVal) {
        this.constVal=constVal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstAssign!=null) ConstAssign.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstAssign!=null) ConstAssign.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstAssign!=null) ConstAssign.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultiBoolConst(\n");

        if(ConstAssign!=null)
            buffer.append(ConstAssign.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+constName);
        buffer.append("\n");

        buffer.append(" "+tab+constVal);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultiBoolConst]");
        return buffer.toString();
    }
}
