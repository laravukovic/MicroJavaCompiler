
package rs.ac.bg.etf.pp1;

import java.util.List;
import java.util.ArrayList;

import java_cup.runtime.Symbol;
import rs.ac.bg.etf.pp1.test.CompilerError;
import rs.ac.bg.etf.pp1.test.CompilerError.CompilerErrorType;

%%

%{
	
	List<CompilerError> compilerErrors = new ArrayList<CompilerError>();

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
	
	private void reportError(int line, String message) {
		CompilerError ce = new CompilerError(line, message, CompilerErrorType.LEXICAL_ERROR);
		compilerErrors.add(ce);
	}
	
	public List<CompilerError> getErrors() {
		return compilerErrors;
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" "    { }
"\b"   { }
"\t"   { }
"\r\n" { }
"\f"   { }

"program"  { return new_symbol(sym.PROG, yytext()); }
"break"    { return new_symbol(sym.BREAK, yytext()); }
"class"    { return new_symbol(sym.CLASS, yytext()); }
"enum"     { return new_symbol(sym.ENUM, yytext()); }
"else"     { return new_symbol(sym.ELSE, yytext()); }
"const"    { return new_symbol(sym.CONST, yytext()); }
"if"       { return new_symbol(sym.IF, yytext()); }
"do"       { return new_symbol(sym.DO, yytext()); }
"while"    { return new_symbol(sym.WHILE, yytext()); }
"new"      { return new_symbol(sym.NEW, yytext()); }
"print"    { return new_symbol(sym.PRINT, yytext()); }
"read"     { return new_symbol(sym.READ, yytext()); }
"return"   { return new_symbol(sym.RETURN, yytext()); }
"void"     { return new_symbol(sym.VOID, yytext()); }
"extends"  { return new_symbol(sym.EXTENDS, yytext()); }
"continue" { return new_symbol(sym.CONTINUE, yytext()); }
"this"     { return new_symbol(sym.THIS, yytext()); }
"super"	   { return new_symbol(sym.SUPER, yytext()); }
"goto"	   { return new_symbol(sym.GOTO, yytext()); }
"record"   { return new_symbol(sym.RECORD, yytext()); }

"+"       { return new_symbol(sym.ADD, yytext()); }
"-"       { return new_symbol(sym.SUB, yytext()); }
"*"       { return new_symbol(sym.MUL, yytext()); }
"/"       { return new_symbol(sym.DIV, yytext()); }
"%"       { return new_symbol(sym.MOD, yytext()); }
"=="      { return new_symbol(sym.EQUALITY, yytext()); }
"!="      { return new_symbol(sym.NOEQUALITY, yytext()); }
">"       { return new_symbol(sym.GREATER, yytext()); }
">="      { return new_symbol(sym.GREATEREQ, yytext()); }
"<"       { return new_symbol(sym.LESS, yytext()); }
"<="      { return new_symbol(sym.LESSEQ, yytext()); }
"&&"      { return new_symbol(sym.AND, yytext()); }
"||"      { return new_symbol(sym.OR, yytext()); }
"="       { return new_symbol(sym.EQUAL, yytext()); }
"++"      { return new_symbol(sym.INC, yytext()); }
"--"      { return new_symbol(sym.DEC, yytext()); }
";"       { return new_symbol(sym.SEMI, yytext()); }
":"       { return new_symbol(sym.DDOT, yytext()); }
","       { return new_symbol(sym.COMMA, yytext()); }
"."       { return new_symbol(sym.DOT, yytext()); }
"("       { return new_symbol(sym.LPAREN, yytext()); }
")"       { return new_symbol(sym.RPAREN, yytext()); }
"["       { return new_symbol(sym.LSQUARE, yytext()); }
"]"       { return new_symbol(sym.RSQUARE, yytext()); }
"{"       { return new_symbol(sym.LBRACE, yytext()); }
"}"       { return new_symbol(sym.RBRACE, yytext()); }
"??"       { return new_symbol(sym.BINARY, yytext()); }


"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

("true"|"false") { return new_symbol(sym.BOOLCONST, yytext()); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* {return new_symbol (sym.IDENT, yytext()); }
[0-9]+ { return new_symbol(sym.NUMBER, new Integer(yytext())); }
"'"."'" {  return new_symbol(sym.CHARCONST, new Character(yytext().charAt(1))); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1) + " kolona " + yycolumn);
	reportError(yyline + 1, "Simbol " + yytext() + " ne postoji u tabeli simbola ");  
}









