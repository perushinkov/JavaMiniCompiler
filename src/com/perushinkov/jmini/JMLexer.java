package com.perushinkov.jmini;

import java.util.ArrayList;
import java.util.TreeMap;

import static com.perushinkov.jmini.TokenType.*;
/**
 * Created by perushinkov on 10/25/14.
 */
public class JMLexer {
    private IOBase io;

    private TreeMap<ErrorCode, TokenPosition> errorMessages;
    private ArrayList<Token> tokens;

    private Character ch;

    public JMLexer(IOBase io) {
        this.io = io;
        errorMessages = new TreeMap<ErrorCode, TokenPosition>();
        tokens = new ArrayList<Token>();
    }

    /**
     * Parses
     * @return a stream of tokens
     * @throws Exception
     */
    public ArrayList<Token> lex() throws Exception {
        ch = io.nextChar();

        Token current = null;
        if(ch != null) {
            do {
                current = nextToken();
                tokens.add(current);
            } while (current.getType() != EOF);
        }

        return tokens;
    }

    private Token nextToken() throws Exception {
        Token current;
        do {
            if (ch == null) {
                return newPiece(EOF);
            }
            switch (ch) {
                case '{':
                    ch = io.nextChar();
                    return newPiece(LEFT_BRACE);
                case '}':
                    ch = io.nextChar();
                    return newPiece(RIGHT_BRACE);
                case '[':
                    ch = io.nextChar();
                    return newPiece(LEFT_BRACKET);
                case ']':
                    ch = io.nextChar();
                    return newPiece(RIGHT_BRACKET);
                case '(':
                    ch = io.nextChar();
                    return newPiece(LEFT_PARENTHESIS);
                case ')':
                    ch = io.nextChar();
                    return newPiece(RIGHT_PARENTHESIS);
                case ';':
                    ch = io.nextChar();
                    return newPiece(SEMICOLON);
                case ',':
                    ch = io.nextChar();
                    return newPiece(COMMA);
                case '=':
                    ch = io.nextChar();
                    return newPiece(EQUAL);
                case '<':
                    ch = io.nextChar();
                    return newPiece(LESS_THAN);
                case '+':
                    ch = io.nextChar();
                    return newPiece(PLUS);
                case '-':
                    ch = io.nextChar();
                    return newPiece(MINUS);
                case '*':
                    ch = io.nextChar();
                    return newPiece(MULT);
                case '.':
                    ch = io.nextChar();
                    return newPiece(DOT);
                case '!':
                    ch = io.nextChar();
                    return newPiece(NOT);
                case '&':
                    ch = io.nextChar();
                    if (ch == '&') {
                        ch = io.nextChar();
                        return newPiece(AND);
                    } else {
                        errorMessages.put(ErrorCode.TOKEN_MISMATCH, new TokenPosition(io.getRow(), io.getCol(), tokens.size()));
                        return newPiece(EOF);
                    }
                default:
                    //case NUMBER
                    if(ch <= '9' && ch >= '0') {
                        StringBuffer sb = new StringBuffer();
                        do {
                            sb.append(ch);
                            ch = io.nextChar();
                        } while(ch >= '0' && ch <= '9');
                        current = newPiece(INT_CONST);
                        current.setValue(sb.toString());
                        return current;
                    }
                    //case IDENTIFIER
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                        StringBuffer sb = new StringBuffer();
                        do {
                            sb.append(ch);
                            ch = io.nextChar();
                        } while ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'));
                        String identifier = sb.toString();
                        for(TokenType tt: TokenType.values()) {
                            if(tt.getTerminalValue() == null) continue;
                            if(tt.getTerminalValue().equals(identifier)) {
                                return newPiece(tt);
                            }
                        }
                        current = newPiece(ID);
                        current.setValue(identifier);
                        return current;
                    }
                    ch = io.nextChar();
            }
        } while(true);
    }

    private Token newPiece(TokenType tt){
        return new Token(tt, new TokenPosition(io.getRow(),io.getCol(),tokens.size()));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public TreeMap<ErrorCode, TokenPosition> getErrorMessages() {
        return errorMessages;
    }
}
