package com.perushinkov.jmini;

/**
 * Created by perushinkov on 10/25/14.
 */
public class Token {
    TokenPosition pos;
    TokenType type;

    String value;

    public Token(TokenType type, TokenPosition pos) {
        this.type = type;
        this.pos = pos;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Token{" +
                "pos=" + pos +
                ", type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
