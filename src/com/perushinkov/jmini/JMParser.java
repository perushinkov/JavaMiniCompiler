package com.perushinkov.jmini;

import java.util.ArrayList;

import static com.perushinkov.jmini.NonTerminals.*;
import static com.perushinkov.jmini.TokenType.*;
/**
 * // http://cs.fit.edu/~ryan/cse4251/mini_java_grammar.html
 * 
 * Created by perushinkov on 10/25/14.
 */
public class JMParser {
    private ArrayList<Token> tokens;
    private int position;

    public JMParser(ArrayList<Token> tokens) {
        tokens = tokens;

    }

    private void accept(TokenType tokenType) {
        Token token = nextToken();
        if (tokenType != token.getType()) {
            System.err.println("Unexpected token!");
            //TODO: How will errors be reported?
            //throw new MatchException("Unexpected token!");
        }
    }

    private boolean follows(TokenType tokenType) {
        if (position + 1 >= tokens.size()) {
            return false;
        }
        if (tokens.get(position + 1).getType() == tokenType) {
            return true;
        }
        return false;
    }

    private Token nextToken() {
        position++;
        if (position >= tokens.size()) {
            return null;
        }
        return tokens.get(position);
    }



    // NONTERMINAL matchers start here:
    public void parse() {
        goal();
    }

    // Goal = MainClass, { ClassDeclaration }, EOF;
    public void goal() {
        mainClass();
        if (NT_CLASSDECLARATION.follows(tokens, position)) {
            classDeclaration();
        }
        eof();
    }    

    // MainClass = "class", Identifier, "{",
    //                  "public", "static", "void", "main", "(", "String", "[", "]", Identifier, ")", "{",
    //                      Statement,
    //                  "}",
    //              "}";
    public void mainClass() {
        accept(T_CLASS);

        identifier();

        accept(T_LEFT_BRACE);
            accept(T_PUBLIC);
            accept(T_STATIC);
            accept(T_VOID);
            accept(T_MAIN);
            accept(T_LEFT_PARENTHESIS);
                accept(T_STRING);
                accept(T_LEFT_BRACKET);
                accept(T_RIGHT_BRACKET);

                identifier();

            accept(T_RIGHT_PARENTHESIS);
            accept(T_LEFT_BRACE);

            statement();

            accept(T_RIGHT_BRACE);
        accept(T_RIGHT_BRACE);
    }

    // ClassDeclaration	= "class", Identifier, [ "extends", Identifier ],
    //                    "{", { VarDeclaration }, { MethodDeclaration } "}";
    public void classDeclaration() {
        accept(T_CLASS);

        identifier();

        if (follows(T_EXTENDS)) {
            accept(T_EXTENDS);
            identifier();
        }

        accept(T_LEFT_BRACE);
            while (NT_VARDECLARATION.follows(tokens, position)) {
                varDeclaration();
            }
            while (NT_METHODDECLARATION.follows(tokens, position)) {
                methodDeclaration();
            }
        accept(T_RIGHT_BRACE);
    }

    // VarDeclaration = Type, Identifier, ";";
    public void varDeclaration() {
        type();
        identifier();
        accept(T_SEMICOLON);
    }

    // MethodDeclaration = "public", Type, Identifier, "(",
    //                     [ Type, Identifier, { ",", Type, Identifier }, ], 
    //                     ")", "{", { VarDeclaration }, { Statement }, "return", Expression, ";", "}";
    public void methodDeclaration() {
        accept(T_PUBLIC);
        type();
        identifier();
        accept(T_LEFT_PARENTHESIS);
            if (NT_TYPE.follows(tokens, position)) {
                type();
                identifier();
                while (follows(T_COMMA)) {
                    accept(T_COMMA);
                    type();
                    identifier();
                }
            }
        accept(T_RIGHT_PARENTHESIS);
        accept(T_LEFT_BRACE);
            while (NT_VARDECLARATION.follows(tokens, position)) {
                varDeclaration();
            }
            while (NT_STATEMENT.follows(tokens, position)) {
                statement();
            }
            accept(T_RETURN);
            expression();
            accept(T_SEMICOLON);
        accept(T_RIGHT_BRACE);
    }

    // Type = "int", "[", "]"
    //        |"boolean"
    //        | "int"
    //        | Identifier ;
    public void type() {
        if (follows(T_BOOLEAN_TYPE)) {
            accept(T_BOOLEAN_TYPE);
        } else if (NT_IDENTIFIER.follows(tokens, position)) {
            identifier();
        } else if (follows(T_INT_TYPE)) {
            accept(T_INT_TYPE);
            if (follows(T_LEFT_BRACKET)) {
                accept(T_LEFT_BRACKET);
                accept(T_RIGHT_BRACKET);
            }
        }
    }

	// Statement	=	"{", { Statement }, "}"
	// | "if", "(", Expression, ")", Statement, "else", Statement 
	// | "while", "(", Expression, ")", Statement 
	// | "System.out.println", "(" , Expression, ")", ";"
	// | Identifier, [ "[", Expression, "]" ] , "=", Expression, ";"
	// ;
    public void statement() {
        if (follows(T_LEFT_BRACE)) { // "{", { Statement }, "}"
            accept(T_LEFT_BRACE);
            if (NT_STATEMENT.follows(tokens, position)) {
                statement();
            }
            accept(T_RIGHT_BRACE);
        } else if (follows(T_IF)) { // "if", "(", Expression, ")", Statement, "else", Statement
            accept(T_IF);
            accept(T_LEFT_PARENTHESIS);
            expression();
            accept(T_RIGHT_PARENTHESIS);
            statement();
            accept(T_ELSE);
            statement();
        } else if (follows(T_WHILE)) { // "while", "(", Expression, ")", Statement
            accept(T_WHILE);
            accept(T_LEFT_PARENTHESIS);
            expression();
            accept(T_RIGHT_PARENTHESIS);
            statement();
        } else if (follows(T_SYSOUT)) { // "System.out.println", "(" , Expression, ")", ";"
            accept(T_SYSOUT);
            accept(T_LEFT_PARENTHESIS);
            expression();
            accept(T_RIGHT_PARENTHESIS);
            accept(T_SEMICOLON);
        } else if (NT_IDENTIFIER.follows(tokens, position)) {
            // Identifier, [ "[", Expression, "]" ] , "=", Expression, ";"
            identifier();
            if (follows(T_LEFT_BRACKET)) {
                accept(T_LEFT_BRACKET);
                expression();
                accept(T_RIGHT_BRACKET);
            }
            accept(T_EQUAL);
            expression();
            accept(T_SEMICOLON);
        }
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
        accept(T_ID);
    }

    public void integerLiteral() {
        accept(T_INT_CONST);
    }

    public void eof() {
        accept(T_EOF);
    }
}
