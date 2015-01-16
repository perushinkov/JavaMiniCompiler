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
        //String[] inputFiles = new String[] {"Factorial", "BinarySearch", "BinaryTree",
        //        "BubbleSort", "LinearSearch", "LinkedList", "QuickSort", "TreeVisitor"};

        for (String filename: args) {
            System.out.println("File: " + args[0] + "\n");
            //JavaMini example = new JavaMini(new File("resources/" + filename + ".java"));
            JavaMini example = new JavaMini(new File(filename));
            TreeMap<ErrorCode, TokenPosition> tm = example.getLexer().getErrorMessages();
            System.out.println("\tLex errors:");
            for (ErrorCode err: tm.keySet()) {
                System.out.println(err.toString() + ":" + tm.get(err).toString());
            }
            System.out.println();
            System.out.println("\tParse errors:");
            List<String> errorList = example.getParser().getErrors();
            for (String error: errorList) {
                System.out.println(error);
            }
            System.out.println();
            System.out.println("\tList of all tokens:");
            List<Token> tokens = example.getLexer().getTokens();
            int k = 0;
            for (Token token : tokens) {
                k++;
                if (k == 5) {
                    k = 0;
                    System.out.println();
                }
                System.out.print("\t<" + token.getType().name() + "> ");
                //System.out.println(token.toString());
            }

            System.out.println("\n\n\n");
        }
    }
}
