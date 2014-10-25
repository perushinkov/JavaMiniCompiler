package com.perushinkov.jmini;

/**
 * Created by perushinkov on 10/25/14.
 */
public enum TokenType {
    EOF,                // <EOF>
    LEFT_BRACE,         // '{'
    RIGHT_BRACE,        // '}'
    LEFT_BRACKET,       // '['
    RIGHT_BRACKET,      // ']'
    LEFT_PARENTHESIS,   // '('
    RIGHT_PARENTHESIS,  // ')'
    SEMICOLON,          // ';'
    COMMA,              // ','
    EQUAL,              // '='
    AND,                // '&&'
    LESS_THAN,          // '<'
    PLUS,               // '+'
    MINUS,              // '-'
    MULT,               // '*'
    DOT,                // '.'
    NOT,                // '!'
    ID,                 // <identifier>
    INT_CONST,          // <integer_literal>
    BOOLEAN_TYPE,       // 'boolean'
    CLASS,              // 'class'
    ELSE,               // 'else'
    EXTENDS,            // 'extends'
    FALSE,              // 'false'
    IF,                 // 'if'
    INT_TYPE,           // 'int'
    LENGTH,             // 'length'
    MAIN,               // 'main'
    NEW,                // 'new'
    PUBLIC,             // 'public'
    RETURN,             // 'return'
    STATIC,             // 'static'
    STRING,             // 'String'
    SYSOUT,             // 'System.out.println'
    THIS,               // 'this'
    TRUE,               // 'true'
    VOID,               // 'void'
    WHILE;              // 'while'

    private String terminalValue;

    static {
        EOF.terminalValue = null;
        LEFT_BRACE.terminalValue = "{";
        RIGHT_BRACE.terminalValue = "}";
        LEFT_BRACKET.terminalValue = "[";
        RIGHT_BRACKET.terminalValue = "]";
        LEFT_PARENTHESIS.terminalValue = "(";
        RIGHT_PARENTHESIS.terminalValue = ")";
        SEMICOLON.terminalValue = ";";
        COMMA.terminalValue = ",";
        EQUAL.terminalValue = "=";
        AND.terminalValue = "&&";
        LESS_THAN.terminalValue = "<";
        PLUS.terminalValue = "+";
        MINUS.terminalValue = "-";
        MULT.terminalValue = "*";
        DOT.terminalValue = ".";
        NOT.terminalValue = "!";
        ID.terminalValue = null;
        INT_CONST.terminalValue = null;
        BOOLEAN_TYPE.terminalValue = "boolean";
        CLASS.terminalValue = "class";
        ELSE.terminalValue = "else";
        EXTENDS.terminalValue = "extends";
        FALSE.terminalValue = "false";
        IF.terminalValue = "if";
        INT_TYPE.terminalValue = "int";
        LENGTH.terminalValue = "length";
        MAIN.terminalValue = "main";
        NEW.terminalValue = "new";
        PUBLIC.terminalValue = "public";
        RETURN.terminalValue = "return";
        STATIC.terminalValue = "static";
        STRING.terminalValue = "String";
        SYSOUT.terminalValue = "System.out.println";
        THIS.terminalValue = "this";
        TRUE.terminalValue = "true";
        VOID.terminalValue = "void";
        WHILE.terminalValue = "while";
    }

    public String getTerminalValue() {
        return terminalValue;
    }
}