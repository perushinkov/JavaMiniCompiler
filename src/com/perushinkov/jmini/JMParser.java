package com.perushinkov.jmini;

import java.util.ArrayList;

/**
 * //http://cs.fit.edu/~ryan/cse4251/mini_java_grammar.html
 * 
 * Created by perushinkov on 10/25/14.
 */
public class JMParser {
    private ArrayList<Token> tokens;
    private int position;

    public JMParser(ArrayList<Token> tokens) {
        tokens = tokens;

    }

    public void parse() {
        goal();
    }

    // TODO: Goal = MainClass, { ClassDeclaration }, EOF;
    public void goal() {
        mainClass();
        classDeclaration();
        eof();
    }    

    // TODO: MainClass = "class", Identifier, "{", "public", "static", "void", "main", "(", "String", "[", "]", Identifier, ")", "{", Statement, "}", "}";
    public void mainClass() {
        //b
    }

    // TODO: ClassDeclaration	= "class", Identifier, [ "extends", Identifier ], "{", { VarDeclaration }, { MethodDeclaration } "}";
    public void classDeclaration() {
        //b
    }

    // TODO: VarDeclaration = Type, Identifier, ";";
    public void varDeclaration() {
        //b
    }

    // TODO: MethodDeclaration = "public", Type, Identifier, "(", 
    //                     [ Type, Identifier, { ",", Type, Identifier }, ], 
    //                     ")", "{", { VarDeclaration }, { Statement }, "return", Expression, ";", "}";
    public void methodDeclaration() {
        //b
    }

    // TODO: Type = "int", "[", "]" 
    //        |"boolean"
    //        | "int"
    //        | Identifier ;
    public void type() {
        //b
    }

	// TODO: Statement	=	"{", { Statement }, "}" 
	// | "if", "(", Expression, ")", Statement, "else", Statement 
	// | "while", "(", Expression, ")", Statement 
	// | "System.out.println", "(" , Expression, ")", ";" 
	// | Identifier, "=", Expression, ";" 
	// | Identifier, "[", Expression, "]", "=", Expression, ";" 
	// ;
    public void statement() {
        //b
    }

    // TODO: Expression = Expression , ( "&&" | "<" | "+" | "-" | "*" ) , Expression 
	//              | Expression, "[", Expression, "]" 
	//              | Expression, ".", "length" 
	//              | Expression, ".", Identifier, "(", [ Expression { ",", Expression } ], ")" 
	//              | IntegerLiteral 
	//              | "true"
	//              | "false"
	//              | Identifier
	//              | "this"
	//              | "new", "int", "[", Expression, "]" 
	//              | "new", Identifier ,"(" ,")"
	//              | "!", Expression 
	//              | "(", Expression, ")" 
	//              ;
    public void expression() {
        //b
    }

    public void identifier() {
        //b
    }

    public void integer() {
        //b
    }

    public void integerLiteral() {
        //b
    }

    public void eof() {
        //b
    }
}
