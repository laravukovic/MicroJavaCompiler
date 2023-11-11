package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class CustomObj extends DumpSymbolTableVisitor {

	public CustomObj() {
		super();
	}

	@Override
	public void visitStructNode(Struct structToVisit) {
//		super.visitStructNode(structToVisit);

		switch (structToVisit.getKind()) {
		case Struct.Bool:
			output.append("bool");
			break;
		case Struct.Array:
			if (structToVisit.getElemType().getKind() == Struct.Int) {
				output.append("int");
			}
			if (structToVisit.getElemType().getKind() == Struct.Char) {
				output.append("char");
			}
			if (structToVisit.getElemType().getKind() == Struct.Bool) {
				output.append("bool");
			}
			output.append("[]");
			break;
		case Struct.None:
			output.append("void");
			break;
		default:
			super.visitStructNode(structToVisit);
			break;
		}
	}
	
	
}