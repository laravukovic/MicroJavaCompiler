// generated with ast extension for cup
// version 0.8
// 26/7/2022 16:39:26


package rs.ac.bg.etf.pp1.ast;

public class NoMethodDeclarations extends MethodDeclList {

    public NoMethodDeclarations () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoMethodDeclarations(\n");

        buffer.append(tab);
        buffer.append(") [NoMethodDeclarations]");
        return buffer.toString();
    }
}
