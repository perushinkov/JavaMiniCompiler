package com.perushinkov.jmini;

import java.util.List;

/**
 * Some nonTerminals here implement follows().
 *
 * Created by perushinkov on 1/2/15.
 */
public enum NonTerminals {
    NT_GOAL,
    NT_MAINCLASS,
    NT_CLASSDECLARATION {
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_VARDECLARATION {
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_METHODDECLARATION {
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_TYPE {
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_STATEMENT {
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_EXPRESSION,
    NT_EXPRL1,
    NT_EXPRL2,
    NT_EXPRL3,
    NT_IDENTIFIER {
        boolean follows(List<Token> tokens, int position) { return false; }
    },
    NT_INTEGER,
    NT_INTEGERLITERAL,
    NT_EOF;
}
