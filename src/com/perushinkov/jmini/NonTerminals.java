package com.perushinkov.jmini;

import java.util.List;

/**
 * Each nonTerminal here must implement follows().
 *
 * Created by perushinkov on 1/2/15.
 */
public enum NonTerminals {
    NT_GOAL {
        @Override
        boolean follows(List<Token> tokens, int position) {
            // NOT USED;
            return true;
        }
    },
    NT_MAINCLASS {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_CLASSDECLARATION {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_VARDECLARATION {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_METHODDECLARATION {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_TYPE {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_STATEMENT {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_EXPRESSION {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_IDENTIFIER {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_INTEGER {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_INTEGERLITERAL {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    },
    NT_EOF {
        @Override
        boolean follows(List<Token> tokens, int position) {
            return false;
        }
    };

    /**
     * Tells if current NonTerminal follows.
     * Params required are a list of tokens and current position.
     * In the case of LL(1) parser, we would just need the following token.
     *
     * @return if nonterminal rule follows.
     *      NOTE: If this nonterminal's follow() returns true, it should be
     *            taken for granted that all its alternatives do not.
     */
    abstract boolean follows(List<Token> tokens, int position);

}
