package com.perushinkov.jmini;

import java.util.ArrayList;
import java.util.List;

import static com.perushinkov.jmini.NonTerminals.*;
import static com.perushinkov.jmini.TokenType.*;
/**
 * // http://cs.fit.edu/~ryan/cse4251/mini_java_grammar.html
 * 
 * Original expression nonterminal at http://cs.fit.edu/~ryan/cse4251/mini_java_grammar.html is:
 *        Expression = Expression , ( "&&" | "<" | "+" | "-" | "*" ) , Expression
 *                | Expression, "[", Expression, "]"
 *                | Expression, ".", "length"
 *                | Expression, ".", Identifier, "(", [ Expression { ",", Expression } ], ")"
 *                | IntegerLiteral
 *                | "true"
 *                | "false"
 *                | Identifier
 *                | "this"
 *                | "new", "int", "[", Expression, "]"
 *                | "new", Identifier ,"(" ,")"
 *                | "!", Expression
 *                | "(", Expression, ")"
 *                ;
 *
 *  However this is not parsable by LL(1) parser. Furthermore it does not reflect the priority
 *  of certain operations over others. So I have analyzed it and transformed it so:
 *
 *  Expression = TermL1, ( "<"  , TermL1 )*
 *  TermL1 = TermL2, ( "&&" , TermL2)*
 *  TermL2 = TermL3, (( "+" | "-" ) , TermL3)*
 *  TermL3 = ExprL1, ( "*" , ExprL1)*
 *  ExprL1 = ExprL2, [".", "length"]
 *  ExprL2 = ExprL3, [
 *          "[", Expression, "]"
 *              |
 *          ".", Identifier, "(", [ Expression { ",", Expression } ], ")"
 *                   ]
 *  ExprL3 = IntegerLiteral
 *          | "true"
 *          | "false"
 *          | Identifier
 *          | "this"
 *          | "new", "int", "[", Expression, "]"
 *          | "new", Identifier ,"(" ,")"
 *          | "!", Expression
 *          | "(", Expression, ")"
 *          ;
 *
 *  I have also modified the Method declaration rule so that the variables do not
 *  need to be declared before all the statements
 *  Created by perushinkov on 10/25/14.
 */
public class JMParser {
    private List<Token> tokens;
    private int position;

    private List<String> errors;

    public JMParser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.position = -1;
        this.errors = new ArrayList<String>();
    }
    private void error(TokenType[] expected) {
        if (position == tokens.size()) return;
        // Error message
        TokenPosition errLocation = tokens.get(position).getPosition();
        TokenType errType = tokens.get(position).getType();
        StringBuffer sb = new StringBuffer();
        sb.append("Mismatch at row " + (errLocation.getRow() + 1));
        sb.append(" and col " + (errLocation.getCol() + 1) + ". ");
        sb.append("Got " + errType.name() + ". ");
        sb.append("Expected instead one of");
        for (TokenType type: expected) {
            sb.append(" " + type.name() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(".\n");
        errors.add(sb.toString());

        // Basic error recovery

        int j = 0;
        OUTER_LOOP:
        for (int i = position; i < tokens.size(); i++) {
            for (j = 0; j < expected.length; j++) {
                if (tokens.get(i).getType() == expected[j]) {
                    position = i;
                    break OUTER_LOOP;
                }
            }
        }
        if (tokens.get(position).getType() != expected[j]) {
            position = tokens.size();
        }
    }
    private void accept(TokenType tokenType) {
        Token token = nextToken();
        if (tokenType != token.getType()) {
            error(new TokenType[] {tokenType});
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

    public List<String> getErrors() {
        return errors;
    }
    /**
     * NONTERMINAL matchers start here:
     */
    public void parse() {
        goal();
    }

    /**
     * Goal = MainClass, { ClassDeclaration }, EOF;
     */
    public void goal() {
        mainClass();
        while (follows(T_CLASS)) {
            classDeclaration();
        }
        eof();
    }

    /**
     * MainClass = "class", Identifier, "{",
     *                  "public", "static", "void", "main", "(", "String", "[", "]", Identifier, ")", "{",
     *                      Statement,
     *                   "}",
     *               "}";
     */
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
    /**
     * ClassDeclaration	= "class", Identifier, [ "extends", Identifier ],
     *                    "{", { [VarDeclaration | MethodDeclaration] } "}";
     */
    public void classDeclaration() {
        accept(T_CLASS);

        identifier();

        if (follows(T_EXTENDS)) {
            accept(T_EXTENDS);
            identifier();
        }

        accept(T_LEFT_BRACE);
            while (!follows(T_RIGHT_BRACE)) {
                if (follows(T_PUBLIC)) {
                    methodDeclaration();
                } else if (follows(T_INT_TYPE) || follows(T_BOOLEAN_TYPE) || follows(T_ID)){
                    varDeclaration();
                } else break; // ONLY DURING ERROR RECOVERY
            }
        accept(T_RIGHT_BRACE);
    }
    /**
     * VarDeclaration = Type, Identifier, ";";
     */
    public void varDeclaration() {
        type();
        identifier();
        accept(T_SEMICOLON);
    }

    /**
     * MethodDeclaration = "public", Type, Identifier, "(",
     *                     [ Type, Identifier, { ",", Type, Identifier }, ],
     *                     ")", "{", { [ VarDeclaration ] | [ Statement ] } , "return", Expression, ";", "}";
     */
    public void methodDeclaration() {
        accept(T_PUBLIC);
        type();
        identifier();
        accept(T_LEFT_PARENTHESIS);
            if (!follows(T_RIGHT_PARENTHESIS)) {
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
            while (!follows(T_RETURN)) {
                // To see if we have a var declaration, or a statement
                // we must look ahead not one but two symbols just this time
                // Another option would be to do this at a much later stage
                // when the semantic tables will be ready
                if (follows(T_INT_TYPE) || follows(T_BOOLEAN_TYPE) ||
                        (follows(T_ID)
                                && tokens.get(position + 2).getType() == T_ID)) {
                    varDeclaration();
                } else if (follows(T_IF) || follows(T_WHILE) || follows(T_SYSOUT) ||
                        follows(T_LEFT_BRACE) || follows(T_ID)) {
                    statement();
                } else break; // This happens only during error recovery
            }
            accept(T_RETURN);
            expression();
            accept(T_SEMICOLON);
        accept(T_RIGHT_BRACE);
    }

    /**
     * Type = "int", "[", "]"
     *        |"boolean"
     *        | "int"
     *        | Identifier ;
     */
    public void type() {
        if (follows(T_BOOLEAN_TYPE)) {
            accept(T_BOOLEAN_TYPE);
        } else if (follows(T_ID)) {
            identifier();
        } else if (follows(T_INT_TYPE)) {
            accept(T_INT_TYPE);
            if (follows(T_LEFT_BRACKET)) {
                accept(T_LEFT_BRACKET);
                accept(T_RIGHT_BRACKET);
            }
        }
    }

    /**
	 * Statement	=	"{", { Statement }, "}"
	 * | "if", "(", Expression, ")", Statement, "else", Statement
	 * | "while", "(", Expression, ")", Statement
	 * | "System.out.println", "(" , Expression, ")", ";"
	 * | Identifier, [ "[", Expression, "]" ] , "=", Expression, ";"
	 * ;
     */
    public void statement() {
        if (follows(T_LEFT_BRACE)) { // "{", { Statement }, "}"
            accept(T_LEFT_BRACE);
            while (follows(T_LEFT_BRACE) || follows(T_IF) || follows(T_WHILE) || follows(T_SYSOUT) || follows(T_ID)) {
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
        } else if (follows(T_ID)) {
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

    /**
     *  Expression = TermL1, ( "<"  , TermL1 )*
     */
    public void expression() {
        termL1();
        while (follows(T_LESS_THAN)) {
            accept(T_LESS_THAN);
            termL1();
        }
    }

    /**
     *  TermL1 = TermL2, ( "&&" , TermL2)*
     */
    public void termL1() {
        termL2();
        while (follows(T_AND)) {
            accept(T_AND);
            termL2();
        }
    }
    /**
     *  TermL2 = TermL3, (( "+" | "-" ) , TermL3)*
     */
    public void termL2() {
        termL3();
        while (follows(T_PLUS) || follows(T_MINUS)) {
            nextToken();
            termL3();
        }
    }
    /**
     *  TermL3 = ExprL1, ( "*" , ExprL1)*
     */
    public void termL3() {
        exprL1();
        while (follows(T_MULT)) {
            nextToken();
            exprL1();
        }
    }

    /**
     * ExprL1 = ExprL2, [".", "length"]
     */
    public void exprL1() {
        exprL2();
        if (follows(T_DOT) && tokens.get(position + 2).getType() == T_LENGTH) {
            accept(T_DOT);
            accept(T_LENGTH);
        }
    }

    /**
     *  ExprL2 = ExprL3,
     *          [
     *              "[", Expression, "]"
     *              |
     *              ".", Identifier, "(", [ Expression { ",", Expression } ], ")"
     *          ]
     */
    public void exprL2() {
        exprL3();
        if (follows(T_LEFT_BRACKET)) {
            accept(T_LEFT_BRACKET);
            expression();
            accept(T_RIGHT_BRACKET);
        } else if (follows(T_DOT) && tokens.get(position + 2).getType() != T_LENGTH) {
            accept(T_DOT);
            identifier();
            accept(T_LEFT_PARENTHESIS);
            if (!follows(T_RIGHT_PARENTHESIS)) {
                expression();
                while (follows(T_COMMA)) {
                    accept(T_COMMA);
                    expression();
                }
            }
            accept(T_RIGHT_PARENTHESIS);

        }
    }

    /**  ExprL3 = IntegerLiteral
     *          | "true"
     *          | "false"
     *          | Identifier
     *          | "this"
     *          | "new", "int", "[", Expression, "]"
     *          | "new", Identifier ,"(" ,")"
     *          | "!", Expression
     *          | "(", Expression, ")"
     *          ;
     */
    public void exprL3() {
        if (follows(T_TRUE) || follows(T_FALSE) ||
                 follows(T_THIS)) {
            nextToken();
        } else if (follows(T_INT_CONST)) {
            integerLiteral();
        } else if (follows(T_ID)) {
            identifier();
        } else if (follows(T_NEW)) {
            accept(T_NEW);
            if (follows(T_INT_TYPE)) {
                accept(T_INT_TYPE);
                accept(T_LEFT_BRACKET);
                expression();
                accept(T_RIGHT_BRACKET);
            } else if (follows(T_ID)) {
                accept(T_ID);
                accept(T_LEFT_PARENTHESIS);
                accept(T_RIGHT_PARENTHESIS);
            } else {
                error(new TokenType[]{T_INT_TYPE, T_ID});
            }
        } else if (follows(T_NOT)) {
            accept(T_NOT);
            expression();
        } else if (follows(T_LEFT_PARENTHESIS)) {
            accept(T_LEFT_PARENTHESIS);
            expression();
            accept(T_RIGHT_PARENTHESIS);
        } else {
            error(new TokenType[]{T_TRUE, T_FALSE, T_INT_CONST, T_ID, T_NEW, T_NOT, T_LEFT_PARENTHESIS});
        }
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
