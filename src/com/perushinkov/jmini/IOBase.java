package com.perushinkov.jmini;

import java.io.*;
import java.util.Scanner;

/**
 * Created by perushinkov on 10/25/14.
 */
public class IOBase {
    private FileReader in;

    private int row;
    private int col;

    public IOBase(File in) throws java.io.FileNotFoundException {
        this.in = new FileReader(in);

        row = 0;
        col = 0;
    }

    /**
     *
     * @return null for <EOF>
     * @throws Exception
     */
    public Character nextChar() throws Exception {
        int ch = in.read();
        if(ch == -1) {
            return null;
        }
        row = (ch == '\n') ? row + 1 : row;
        col = (ch == '\n') ? 0 : col + 1;
        return (char)ch;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

}
