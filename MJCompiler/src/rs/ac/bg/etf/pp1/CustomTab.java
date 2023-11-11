package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class CustomTab extends Tab {
	public static final Struct boolType = new Struct(Struct.Bool);

	private static CustomObj boolObj = new CustomObj();

	public static void init() {
		Tab.init();
		Obj tmp = Tab.insert(Obj.Type, "bool", CustomTab.boolType);
		tmp.setAdr(-1);
		tmp.setLevel(-1);
	}
	
	public static String printObj(Obj o) {
		StringBuilder sb = new StringBuilder();

		sb.append("Vrsta: ");
		switch (o.getKind()) {
		case Obj.Con:
			sb.append("Const");
			break;
		case Obj.Elem:
			sb.append("Elem");
			break;
		case Obj.Fld:
			sb.append("Fld");
			break;
		case Obj.Meth:
			sb.append("Meth");
			break;
		case Obj.Prog:
			sb.append("Prog");
			break;
		case Obj.Type:
			sb.append("Type");
			break;
		case Obj.Var:
			sb.append("Var");
			break;
		default:
			sb.append("NO_VALUE");
			break;
		}

		sb.append(", Ime: " + o.getName() + ", Tip: ");
		if (o.getType() == CustomTab.boolType) {
			sb.append("bool");
		} else if (o.getType() == CustomTab.charType) {
			sb.append("char");
		} else if (o.getType() == CustomTab.intType) {
			sb.append("int");
		} else if (o.getType() == CustomTab.noType) {
			sb.append("void");
		} else if (o.getType() == CustomTab.nullType) {
			sb.append("null");
		} 
		else if(o.getType().getKind() == 3) {
			switch (o.getType().getKind()) {
			case Struct.Bool:
				sb.append("bool");
				break;
			case Struct.Array:
				if (o.getType().getElemType().getKind() == Struct.Int) {
					sb.append("int");
				}
				if (o.getType().getElemType().getKind() == Struct.Char) {
					sb.append("char");
				}
				if (o.getType().getElemType().getKind() == Struct.Bool) {
					sb.append("bool");
				}
				sb.append("[]");
				break;
			default:
				break;
			}
		}
		
		sb.append(", Adr: " + o.getAdr());
		sb.append(", Level: " + o.getLevel());
		sb.append(", FpPos: " + o.getFpPos());

		return sb.toString();
	}
	
	public static void dump() {
		CustomTab.dump(boolObj);
	}
	

}
