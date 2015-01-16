package com.perushinkov.jmini;

/**
 * Created by perushinkov on 10/25/14.
 */
public class TokenPosition {
    private int row;
    private int col;
    private int tokenNumber;

    public TokenPosition(int row, int col, int tokenNumber) {
        this.row = row;
        this.col = col;
        this.tokenNumber = tokenNumber;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getTokenNumber() {
        return tokenNumber;
    }

    @Override
    public String toString() {
        return "<row=" + row +
                " col=" + col +
                " tokenNumber=" + tokenNumber +
                '>';
    }
}
