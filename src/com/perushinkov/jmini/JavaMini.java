package com.perushinkov.jmini;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
        String[] inputFiles = new String[] {"Factorial", "BinarySearch", "BinaryTree",
                "BubbleSort", "LinearSearch", "LinkedList", "QuickSort", "TreeVisitor"};

        for (String filename: inputFiles) {
            JavaMini example = new JavaMini(new File("resources/" + filename + ".java"));
            TreeMap<ErrorCode, TokenPosition> tm = example.getLexer().getErrorMessages();
            for (ErrorCode err: tm.keySet()) {
                System.out.println(err.toString() + ":" + tm.get(err).toString());
            }
            List<String> errorList = example.getParser().getErrors();
            for (String error: errorList) {
                System.out.println(error);
            }
        }
    }
}
