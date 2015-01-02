package com.perushinkov.jmini;

/**
 * Created by perushinkov on 10/25/14.
 */
public enum TokenType {
    T_EOF, // <EOF>
    T_LEFT_BRACE,         // '{'
    T_RIGHT_BRACE,        // '}'
    T_LEFT_BRACKET,       // '['
    T_RIGHT_BRACKET,      // ']'
    T_LEFT_PARENTHESIS,   // '('
    T_RIGHT_PARENTHESIS,  // ')'
    T_SEMICOLON,          // ';'
    T_COMMA,              // ','
    T_EQUAL,              // '='
    T_AND,                // '&&'
    T_LESS_THAN,          // '<'
    T_PLUS,               // '+'
    T_MINUS,              // '-'
    T_MULT,               // '*'
    T_DOT,                // '.'
    T_NOT,                // '!'
    T_ID,                 // <identifier>
    T_INT_CONST,          // <integer_literal>
    T_BOOLEAN_TYPE,       // 'boolean'
    T_CLASS,              // 'class'
    T_ELSE,               // 'else'
    T_EXTENDS,            // 'extends'
    T_FALSE,              // 'false'
    T_IF,                 // 'if'
    T_INT_TYPE,           // 'int'
    T_LENGTH,             // 'length'
    T_MAIN,               // 'main'
    T_NEW,                // 'new'
    T_PUBLIC,             // 'public'
    T_RETURN,             // 'return'
    T_STATIC,             // 'static'
    T_STRING,             // 'String'
    T_SYSOUT,             // 'System.out.println'
    T_THIS,               // 'this'
    T_TRUE,               // 'true'
    T_VOID,               // 'void'
    T_WHILE;              // 'while'

    private String terminalValue;

    static {
        T_EOF.terminalValue = null;
        T_LEFT_BRACE.terminalValue = "{";
        T_RIGHT_BRACE.terminalValue = "}";
        T_LEFT_BRACKET.terminalValue = "[";
        T_RIGHT_BRACKET.terminalValue = "]";
        T_LEFT_PARENTHESIS.terminalValue = "(";
        T_RIGHT_PARENTHESIS.terminalValue = ")";
        T_SEMICOLON.terminalValue = ";";
        T_COMMA.terminalValue = ",";
        T_EQUAL.terminalValue = "=";
        T_AND.terminalValue = "&&";
        T_LESS_THAN.terminalValue = "<";
        T_PLUS.terminalValue = "+";
        T_MINUS.terminalValue = "-";
        T_MULT.terminalValue = "*";
        T_DOT.terminalValue = ".";
        T_NOT.terminalValue = "!";
        T_ID.terminalValue = null;
        T_INT_CONST.terminalValue = null;
        T_BOOLEAN_TYPE.terminalValue = "boolean";
        T_CLASS.terminalValue = "class";
        T_ELSE.terminalValue = "else";
        T_EXTENDS.terminalValue = "extends";
        T_FALSE.terminalValue = "false";
        T_IF.terminalValue = "if";
        T_INT_TYPE.terminalValue = "int";
        T_LENGTH.terminalValue = "length";
        T_MAIN.terminalValue = "main";
        T_NEW.terminalValue = "new";
        T_PUBLIC.terminalValue = "public";
        T_RETURN.terminalValue = "return";
        T_STATIC.terminalValue = "static";
        T_STRING.terminalValue = "String";
        T_SYSOUT.terminalValue = "System.out.println";
        T_THIS.terminalValue = "this";
        T_TRUE.terminalValue = "true";
        T_VOID.terminalValue = "void";
        T_WHILE.terminalValue = "while";
    }

    public String getTerminalValue() {
        return terminalValue;
    }
}