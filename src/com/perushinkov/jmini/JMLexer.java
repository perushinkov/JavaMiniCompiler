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
            } while (current.getType() != T_EOF);
        }

        return tokens;
    }

    private Token nextToken() throws Exception {
        Token current;
        LEX_LOOP:
        do {
            if (ch == null) {
                return newPiece(T_EOF);
            }

            switch (ch) {
                case '/':
                    ch = io.nextChar();
                    if (ch == '/') {
                        while (io.nextChar() != '\n');
                        ch = io.nextChar();
                        continue;
                    } else if (ch == '*') {
                        do {
                            while (io.nextChar() != '*');
                            if (io.nextChar() == '/') {
                                ch = io.nextChar();
                                continue LEX_LOOP;
                            }
                        } while (true);
                    }
                case '{':
                    ch = io.nextChar();
                    return newPiece(T_LEFT_BRACE);
                case '}':
                    ch = io.nextChar();
                    return newPiece(T_RIGHT_BRACE);
                case '[':
                    ch = io.nextChar();
                    return newPiece(T_LEFT_BRACKET);
                case ']':
                    ch = io.nextChar();
                    return newPiece(T_RIGHT_BRACKET);
                case '(':
                    ch = io.nextChar();
                    return newPiece(T_LEFT_PARENTHESIS);
                case ')':
                    ch = io.nextChar();
                    return newPiece(T_RIGHT_PARENTHESIS);
                case ';':
                    ch = io.nextChar();
                    return newPiece(T_SEMICOLON);
                case ',':
                    ch = io.nextChar();
                    return newPiece(T_COMMA);
                case '=':
                    ch = io.nextChar();
                    return newPiece(T_EQUAL);
                case '<':
                    ch = io.nextChar();
                    return newPiece(T_LESS_THAN);
                case '+':
                    ch = io.nextChar();
                    return newPiece(T_PLUS);
                case '-':
                    ch = io.nextChar();
                    return newPiece(T_MINUS);
                case '*':
                    ch = io.nextChar();
                    return newPiece(T_MULT);
                case '.':
                    ch = io.nextChar();
                    return newPiece(T_DOT);
                case '!':
                    ch = io.nextChar();
                    return newPiece(T_NOT);
                case '&':
                    ch = io.nextChar();
                    if (ch == '&') {
                        ch = io.nextChar();
                        return newPiece(T_AND);
                    } else {
                        errorMessages.put(ErrorCode.TOKEN_MISMATCH, new TokenPosition(io.getRow(), io.getCol(), tokens.size()));
                        return newPiece(T_EOF);
                    }
                default:
                    //case NUMBER
                    if(ch <= '9' && ch >= '0') {
                        StringBuffer sb = new StringBuffer();
                        do {
                            sb.append(ch);
                            ch = io.nextChar();
                        } while(ch >= '0' && ch <= '9');
                        current = newPiece(T_INT_CONST);
                        current.setValue(sb.toString());
                        return current;
                    }
                    //case IDENTIFIER
                    if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_') {
                        StringBuffer sb = new StringBuffer();
                        do {
                            sb.append(ch);
                            ch = io.nextChar();
                        } while ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_' || (ch >= '0' && ch <= '9') );
                        String identifier = sb.toString();
                        if (identifier.equals("System")) {
                            String remaining = ".out.println";
                            for (int i = 1; i < remaining.length(); i++) {
                                ch = io.nextChar();
                                if (ch != remaining.charAt(i)) {
                                    throw new IllegalStateException();
                                }
                            }
                            ch = io.nextChar();
                            return newPiece(T_SYSOUT);
                        }
                        for(TokenType tt: TokenType.values()) {
                            if(tt.getTerminalValue() == null) continue;
                            if(tt.getTerminalValue().equals(identifier)) {
                                return newPiece(tt);
                            }
                        }
                        current = newPiece(T_ID);
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
