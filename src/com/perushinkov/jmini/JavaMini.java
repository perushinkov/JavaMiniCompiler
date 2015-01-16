package com.perushinkov.jmini;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by perushinkov on 10/25/14.
 */
public class JavaMini {

    IOBase io;
    JMLexer lexer;
    JMParser parser;

    public JavaMini(File file) {
        try {
            io = new IOBase(file);
            lexer = new JMLexer(io);
            parser = new JMParser(lexer.lex());
            parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IOBase getIo() {
        return io;
    }

    public JMLexer getLexer() {
        return lexer;
    }

    public JMParser getParser() {
        return parser;
    }

    public static void main(String[] args) {
        new JavaMini(new File("resources/Factorial.java"));
        new JavaMini(new File("resources/BinarySearch.java"));
        new JavaMini(new File("resources/BinaryTree.java"));
        JavaMini example = new JavaMini(new File("resources/BubbleSort.java"));
        new JavaMini(new File("resources/LinearSearch.java"));
        new JavaMini(new File("resources/LinkedList.java"));
        new JavaMini(new File("resources/QuickSort.java"));
        new JavaMini(new File("resources/TreeVisitor.java"));


        ArrayList<Token> alt = example.getLexer().getTokens();
        TreeMap<ErrorCode, TokenPosition> tm = example.getLexer().getErrorMessages();

        for(ErrorCode err: tm.keySet()) {
            System.out.println(err.toString() + ":" + tm.get(err).toString());
        }

        for(Token t: alt) {
            System.out.println(t.toString());
        }

        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
    }
}
