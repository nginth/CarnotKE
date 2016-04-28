// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g 2016-04-28 11:41:08

package org.python.antlr;

import org.antlr.runtime.CommonToken;

import org.python.antlr.ParseException;
import org.python.antlr.PythonTree;
import org.python.antlr.ast.alias;
import org.python.antlr.ast.arguments;
import org.python.antlr.ast.Assert;
import org.python.antlr.ast.Assign;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.AugAssign;
import org.python.antlr.ast.BinOp;
import org.python.antlr.ast.BoolOp;
import org.python.antlr.ast.boolopType;
import org.python.antlr.ast.Break;
import org.python.antlr.ast.Batch;
import org.python.antlr.ast.VisitorBase;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.ClassDef;
import org.python.antlr.ast.cmpopType;
import org.python.antlr.ast.Compare;
import org.python.antlr.ast.comprehension;
import org.python.antlr.ast.Context;
import org.python.antlr.ast.Continue;
import org.python.antlr.ast.Delete;
import org.python.antlr.ast.Dict;
import org.python.antlr.ast.DictComp;
import org.python.antlr.ast.Ellipsis;
import org.python.antlr.ast.ErrorMod;
import org.python.antlr.ast.ExceptHandler;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.Expression;
import org.python.antlr.ast.expr_contextType;
import org.python.antlr.ast.ExtSlice;
import org.python.antlr.ast.For;
import org.python.antlr.ast.GeneratorExp;
import org.python.antlr.ast.Global;
import org.python.antlr.ast.If;
import org.python.antlr.ast.IfExp;
import org.python.antlr.ast.Import;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Index;
import org.python.antlr.ast.Interactive;
import org.python.antlr.ast.keyword;
import org.python.antlr.ast.ListComp;
import org.python.antlr.ast.Lambda;
import org.python.antlr.ast.Module;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.operatorType;
import org.python.antlr.ast.Pass;
import org.python.antlr.ast.Print;
import org.python.antlr.ast.Raise;
import org.python.antlr.ast.Repr;
import org.python.antlr.ast.Return;
import org.python.antlr.ast.Set;
import org.python.antlr.ast.SetComp;
import org.python.antlr.ast.Slice;
import org.python.antlr.ast.Str;
import org.python.antlr.ast.Subscript;
import org.python.antlr.ast.TryExcept;
import org.python.antlr.ast.TryFinally;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.unaryopType;
import org.python.antlr.ast.UnaryOp;
import org.python.antlr.ast.While;
import org.python.antlr.ast.With;
import org.python.antlr.ast.Yield;
import org.python.antlr.ast.RelConnection; 
import org.python.antlr.ast.RelInsert; 
import org.python.antlr.ast.RelCommit;
import org.python.antlr.base.excepthandler;
import org.python.antlr.base.expr;
import org.python.antlr.base.mod;
import org.python.antlr.base.slice;
import org.python.antlr.base.stmt;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

/** Python 2.3.3 Grammar
 *
 *  Terence Parr and Loring Craymer
 *  February 2004
 *
 *  Converted to ANTLR v3 November 2005 by Terence Parr.
 *
 *  This grammar was derived automatically from the Python 2.3.3
 *  parser grammar to get a syntactically correct ANTLR grammar
 *  for Python.  Then Terence hand tweaked it to be semantically
 *  correct; i.e., removed lookahead issues etc...  It is LL(1)
 *  except for the (sometimes optional) trailing commas and semi-colons.
 *  It needs two symbols of lookahead in this case.
 *
 *  Starting with Loring's preliminary lexer for Python, I modified it
 *  to do my version of the whole nasty INDENT/DEDENT issue just so I
 *  could understand the problem better.  This grammar requires
 *  PythonTokenStream.java to work.  Also I used some rules from the
 *  semi-formal grammar on the web for Python (automatically
 *  translated to ANTLR format by an ANTLR grammar, naturally <grin>).
 *  The lexical rules for python are particularly nasty and it took me
 *  a long time to get it 'right'; i.e., think about it in the proper
 *  way.  Resist changing the lexer unless you've used ANTLR a lot. ;)
 *
 *  I (Terence) tested this by running it on the jython-2.1/Lib
 *  directory of 40k lines of Python.
 *
 *  REQUIRES ANTLR v3
 *
 *
 *  Updated the original parser for Python 2.5 features. The parser has been
 *  altered to produce an AST - the AST work started from tne newcompiler
 *  grammar from Jim Baker.  The current parsing and compiling strategy looks
 *  like this:
 *
 *  Python source->Python.g->AST (org/python/parser/ast/*)->CodeCompiler(ASM)->.class
 */
public class PythonParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "INDENT", "DEDENT", "TRAILBACKSLASH", "NEWLINE", "LEADING_WS", "NAME", "DOT", "PRINT", "AND", "AS", "ASSERT", "BREAK", "CLASS", "CONTINUE", "DEF", "DELETE", "ELIF", "EXCEPT", "EXEC", "FINALLY", "FROM", "FOR", "GLOBAL", "IF", "IMPORT", "IN", "IS", "LAMBDA", "NOT", "OR", "ORELSE", "PASS", "RAISE", "RETURN", "TRY", "WHILE", "WITH", "YIELD", "BATCH", "AT", "LPAREN", "RPAREN", "COLON", "ASSIGN", "COMMA", "STAR", "DOUBLESTAR", "SEMI", "PLUSEQUAL", "MINUSEQUAL", "STAREQUAL", "SLASHEQUAL", "PERCENTEQUAL", "AMPEREQUAL", "VBAREQUAL", "CIRCUMFLEXEQUAL", "LEFTSHIFTEQUAL", "RIGHTSHIFTEQUAL", "DOUBLESTAREQUAL", "DOUBLESLASHEQUAL", "RIGHTSHIFT", "LESS", "GREATER", "EQUAL", "GREATEREQUAL", "LESSEQUAL", "ALT_NOTEQUAL", "NOTEQUAL", "VBAR", "CIRCUMFLEX", "AMPER", "LEFTSHIFT", "PLUS", "MINUS", "SLASH", "PERCENT", "DOUBLESLASH", "TILDE", "LBRACK", "RBRACK", "LCURLY", "RCURLY", "BACKQUOTE", "INT", "LONGINT", "FLOAT", "COMPLEX", "STRING", "OORELCOMMIT", "OORELINSERT", "SQL", "SIM", "Neo4j", "JAPI", "RDF", "SPARQL", "CONNECTTO", "NODEBUG", "PERSISTIT", "DIGITS", "Exponent", "TRIAPOS", "TRIQUOTE", "ESC", "COMMENT", "CONTINUED_LINE", "WS"
    };
    public static final int PRINT=11;
    public static final int VBAREQUAL=58;
    public static final int MINUS=77;
    public static final int TRAILBACKSLASH=6;
    public static final int OORELCOMMIT=92;
    public static final int SLASHEQUAL=55;
    public static final int BREAK=15;
    public static final int IF=27;
    public static final int LESSEQUAL=69;
    public static final int ELIF=20;
    public static final int IN=29;
    public static final int DOT=10;
    public static final int LPAREN=44;
    public static final int IS=30;
    public static final int AS=13;
    public static final int AT=43;
    public static final int PASS=35;
    public static final int LBRACK=82;
    public static final int LEADING_WS=8;
    public static final int LONGINT=88;
    public static final int SEMI=51;
    public static final int ASSIGN=47;
    public static final int CIRCUMFLEXEQUAL=59;
    public static final int DOUBLESTAREQUAL=62;
    public static final int COMMENT=108;
    public static final int INDENT=4;
    public static final int IMPORT=28;
    public static final int DELETE=19;
    public static final int JAPI=97;
    public static final int Neo4j=96;
    public static final int ESC=107;
    public static final int RDF=98;
    public static final int CONNECTTO=100;
    public static final int ALT_NOTEQUAL=70;
    public static final int RCURLY=85;
    public static final int COMMA=48;
    public static final int TRIQUOTE=106;
    public static final int YIELD=41;
    public static final int STAREQUAL=54;
    public static final int GREATER=66;
    public static final int LCURLY=84;
    public static final int DOUBLESLASHEQUAL=63;
    public static final int RAISE=36;
    public static final int CONTINUE=17;
    public static final int LEFTSHIFTEQUAL=60;
    public static final int STAR=49;
    public static final int PERCENT=79;
    public static final int STRING=91;
    public static final int BACKQUOTE=86;
    public static final int PERSISTIT=102;
    public static final int CLASS=16;
    public static final int FROM=24;
    public static final int FINALLY=23;
    public static final int RIGHTSHIFTEQUAL=61;
    public static final int TRY=38;
    public static final int OORELINSERT=93;
    public static final int NEWLINE=7;
    public static final int FOR=25;
    public static final int RPAREN=45;
    public static final int EXCEPT=21;
    public static final int RIGHTSHIFT=64;
    public static final int NAME=9;
    public static final int LAMBDA=31;
    public static final int SQL=94;
    public static final int NOTEQUAL=71;
    public static final int EXEC=22;
    public static final int NOT=32;
    public static final int RBRACK=83;
    public static final int SIM=95;
    public static final int AND=12;
    public static final int PERCENTEQUAL=56;
    public static final int LESS=65;
    public static final int LEFTSHIFT=75;
    public static final int PLUS=76;
    public static final int DOUBLESTAR=50;
    public static final int SPARQL=99;
    public static final int NODEBUG=101;
    public static final int FLOAT=89;
    public static final int TRIAPOS=105;
    public static final int Exponent=104;
    public static final int DIGITS=103;
    public static final int INT=87;
    public static final int DOUBLESLASH=80;
    public static final int RETURN=37;
    public static final int GLOBAL=26;
    public static final int BATCH=42;
    public static final int CONTINUED_LINE=109;
    public static final int WS=110;
    public static final int EOF=-1;
    public static final int CIRCUMFLEX=73;
    public static final int COMPLEX=90;
    public static final int OR=33;
    public static final int DEF=18;
    public static final int ASSERT=14;
    public static final int AMPEREQUAL=57;
    public static final int EQUAL=67;
    public static final int SLASH=78;
    public static final int AMPER=74;
    public static final int COLON=46;
    public static final int ORELSE=34;
    public static final int WITH=40;
    public static final int VBAR=72;
    public static final int PLUSEQUAL=52;
    public static final int MINUSEQUAL=53;
    public static final int GREATEREQUAL=68;
    public static final int WHILE=39;
    public static final int TILDE=81;
    public static final int DEDENT=5;

    // delegates
    // delegators


        public PythonParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public PythonParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return PythonParser.tokenNames; }
    public String getGrammarFileName() { return "/Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g"; }





        private ErrorHandler errorHandler;

        private GrammarActions actions = new GrammarActions();

        private String encoding;

        private boolean printFunction = false;
        private boolean unicodeLiterals = false;

        public void setErrorHandler(ErrorHandler eh) {
            this.errorHandler = eh;
            actions.setErrorHandler(eh);
        }

        protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
            throws RecognitionException {

            Object o = errorHandler.recoverFromMismatchedToken(this, input, ttype, follow);
            if (o != null) {
                return o;
            }
            return super.recoverFromMismatchedToken(input, ttype, follow);
        }

        public PythonParser(TokenStream input, String encoding) {
            this(input);
            this.encoding = encoding;
        }

        @Override
        public void reportError(RecognitionException e) {
          // Update syntax error count and output error.
          super.reportError(e);
          errorHandler.reportError(this, e);
        }

        @Override
        public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            //Do nothing. We will handle error display elsewhere.
        }


    public static class single_input_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "single_input"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:303:1: single_input : ( ( NEWLINE )* EOF | simple_stmt ( NEWLINE )* EOF | compound_stmt ( NEWLINE )+ EOF );
    public final PythonParser.single_input_return single_input() throws RecognitionException {
        PythonParser.single_input_return retval = new PythonParser.single_input_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NEWLINE1=null;
        Token EOF2=null;
        Token NEWLINE4=null;
        Token EOF5=null;
        Token NEWLINE7=null;
        Token EOF8=null;
        PythonParser.simple_stmt_return simple_stmt3 = null;

        PythonParser.compound_stmt_return compound_stmt6 = null;


        PythonTree NEWLINE1_tree=null;
        PythonTree EOF2_tree=null;
        PythonTree NEWLINE4_tree=null;
        PythonTree EOF5_tree=null;
        PythonTree NEWLINE7_tree=null;
        PythonTree EOF8_tree=null;


            mod mtype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:310:5: ( ( NEWLINE )* EOF | simple_stmt ( NEWLINE )* EOF | compound_stmt ( NEWLINE )+ EOF )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==EOF||LA4_0==NEWLINE) ) {
                alt4=1;
            }
            else if ( (LA4_0==NAME||LA4_0==NOT||LA4_0==LPAREN||(LA4_0>=PLUS && LA4_0<=MINUS)||(LA4_0>=TILDE && LA4_0<=LBRACK)||LA4_0==LCURLY||LA4_0==BACKQUOTE) ) {
                alt4=2;
            }
            else if ( (LA4_0==PRINT) && (((printFunction)||(!printFunction)))) {
                alt4=2;
            }
            else if ( ((LA4_0>=ASSERT && LA4_0<=BREAK)||LA4_0==CONTINUE||LA4_0==DELETE||LA4_0==EXEC||LA4_0==FROM||LA4_0==GLOBAL||LA4_0==IMPORT||LA4_0==LAMBDA||(LA4_0>=PASS && LA4_0<=RETURN)||LA4_0==YIELD||(LA4_0>=INT && LA4_0<=CONNECTTO)) ) {
                alt4=2;
            }
            else if ( (LA4_0==CLASS||LA4_0==DEF||LA4_0==FOR||LA4_0==IF||(LA4_0>=TRY && LA4_0<=WITH)||(LA4_0>=BATCH && LA4_0<=AT)||LA4_0==PERSISTIT) ) {
                alt4=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:310:7: ( NEWLINE )* EOF
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:310:7: ( NEWLINE )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( (LA1_0==NEWLINE) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:310:7: NEWLINE
                    	    {
                    	    NEWLINE1=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_single_input118); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NEWLINE1_tree = (PythonTree)adaptor.create(NEWLINE1);
                    	    adaptor.addChild(root_0, NEWLINE1_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
                        }
                    } while (true);

                    EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_single_input121); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EOF2_tree = (PythonTree)adaptor.create(EOF2);
                    adaptor.addChild(root_0, EOF2_tree);
                    }
                    if ( state.backtracking==0 ) {

                              mtype = new Interactive(((Token)retval.start), new ArrayList<stmt>());
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:314:7: simple_stmt ( NEWLINE )* EOF
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_simple_stmt_in_single_input137);
                    simple_stmt3=simple_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simple_stmt3.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:314:19: ( NEWLINE )*
                    loop2:
                    do {
                        int alt2=2;
                        int LA2_0 = input.LA(1);

                        if ( (LA2_0==NEWLINE) ) {
                            alt2=1;
                        }


                        switch (alt2) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:314:19: NEWLINE
                    	    {
                    	    NEWLINE4=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_single_input139); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NEWLINE4_tree = (PythonTree)adaptor.create(NEWLINE4);
                    	    adaptor.addChild(root_0, NEWLINE4_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop2;
                        }
                    } while (true);

                    EOF5=(Token)match(input,EOF,FOLLOW_EOF_in_single_input142); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EOF5_tree = (PythonTree)adaptor.create(EOF5);
                    adaptor.addChild(root_0, EOF5_tree);
                    }
                    if ( state.backtracking==0 ) {

                              mtype = new Interactive(((Token)retval.start), actions.castStmts((simple_stmt3!=null?simple_stmt3.stypes:null)));
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:318:7: compound_stmt ( NEWLINE )+ EOF
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_compound_stmt_in_single_input158);
                    compound_stmt6=compound_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, compound_stmt6.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:318:21: ( NEWLINE )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( (LA3_0==NEWLINE) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:318:21: NEWLINE
                    	    {
                    	    NEWLINE7=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_single_input160); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NEWLINE7_tree = (PythonTree)adaptor.create(NEWLINE7);
                    	    adaptor.addChild(root_0, NEWLINE7_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt3 >= 1 ) break loop3;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(3, input);
                                throw eee;
                        }
                        cnt3++;
                    } while (true);

                    EOF8=(Token)match(input,EOF,FOLLOW_EOF_in_single_input163); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EOF8_tree = (PythonTree)adaptor.create(EOF8);
                    adaptor.addChild(root_0, EOF8_tree);
                    }
                    if ( state.backtracking==0 ) {

                              mtype = new Interactive(((Token)retval.start), actions.castStmts((compound_stmt6!=null?((PythonTree)compound_stmt6.tree):null)));
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = mtype;

            }
        }
        catch (RecognitionException re) {

                    reportError(re);
                    errorHandler.recover(this, input,re);
                    PythonTree badNode = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
                    retval.tree = new ErrorMod(badNode);
                
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "single_input"

    public static class file_input_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "file_input"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:332:1: file_input : ( NEWLINE | stmt )* EOF ;
    public final PythonParser.file_input_return file_input() throws RecognitionException {
        PythonParser.file_input_return retval = new PythonParser.file_input_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NEWLINE9=null;
        Token EOF11=null;
        PythonParser.stmt_return stmt10 = null;


        PythonTree NEWLINE9_tree=null;
        PythonTree EOF11_tree=null;


            mod mtype = null;
            List stypes = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:350:5: ( ( NEWLINE | stmt )* EOF )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:350:7: ( NEWLINE | stmt )* EOF
            {
            root_0 = (PythonTree)adaptor.nil();

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:350:7: ( NEWLINE | stmt )*
            loop5:
            do {
                int alt5=3;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==NEWLINE) ) {
                    alt5=1;
                }
                else if ( (LA5_0==NAME||LA5_0==NOT||LA5_0==LPAREN||(LA5_0>=PLUS && LA5_0<=MINUS)||(LA5_0>=TILDE && LA5_0<=LBRACK)||LA5_0==LCURLY||LA5_0==BACKQUOTE) ) {
                    alt5=2;
                }
                else if ( (LA5_0==PRINT) && (((printFunction)||(!printFunction)))) {
                    alt5=2;
                }
                else if ( ((LA5_0>=ASSERT && LA5_0<=DELETE)||LA5_0==EXEC||(LA5_0>=FROM && LA5_0<=IMPORT)||LA5_0==LAMBDA||(LA5_0>=PASS && LA5_0<=AT)||(LA5_0>=INT && LA5_0<=CONNECTTO)||LA5_0==PERSISTIT) ) {
                    alt5=2;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:350:8: NEWLINE
            	    {
            	    NEWLINE9=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_file_input215); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NEWLINE9_tree = (PythonTree)adaptor.create(NEWLINE9);
            	    adaptor.addChild(root_0, NEWLINE9_tree);
            	    }

            	    }
            	    break;
            	case 2 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:351:9: stmt
            	    {
            	    pushFollow(FOLLOW_stmt_in_file_input225);
            	    stmt10=stmt();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, stmt10.getTree());
            	    if ( state.backtracking==0 ) {

            	                if ((stmt10!=null?stmt10.stypes:null) != null) {
            	                    stypes.addAll((stmt10!=null?stmt10.stypes:null));
            	                }
            	            
            	    }

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            EOF11=(Token)match(input,EOF,FOLLOW_EOF_in_file_input244); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF11_tree = (PythonTree)adaptor.create(EOF11);
            adaptor.addChild(root_0, EOF11_tree);
            }
            if ( state.backtracking==0 ) {

                           mtype = new Module(((Token)retval.start), actions.castStmts(stypes));
                       
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (!stypes.isEmpty()) {
                      //The EOF token messes up the end offsets, so set them manually.
                      //XXX: this may no longer be true now that PythonTokenSource is
                      //     adjusting EOF offsets -- but needs testing before I remove
                      //     this.
                      PythonTree stop = (PythonTree)stypes.get(stypes.size() -1);
                      mtype.setCharStopIndex(stop.getCharStopIndex());
                      mtype.setTokenStopIndex(stop.getTokenStopIndex());
                  }

                  retval.tree = mtype;

            }
        }
        catch (RecognitionException re) {

                    reportError(re);
                    errorHandler.recover(this, input,re);
                    PythonTree badNode = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
                    retval.tree = new ErrorMod(badNode);
                
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "file_input"

    public static class eval_input_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eval_input"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:371:1: eval_input : ( LEADING_WS )? ( NEWLINE )* testlist[expr_contextType.Load] ( NEWLINE )* EOF ;
    public final PythonParser.eval_input_return eval_input() throws RecognitionException {
        PythonParser.eval_input_return retval = new PythonParser.eval_input_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token LEADING_WS12=null;
        Token NEWLINE13=null;
        Token NEWLINE15=null;
        Token EOF16=null;
        PythonParser.testlist_return testlist14 = null;


        PythonTree LEADING_WS12_tree=null;
        PythonTree NEWLINE13_tree=null;
        PythonTree NEWLINE15_tree=null;
        PythonTree EOF16_tree=null;


            mod mtype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:5: ( ( LEADING_WS )? ( NEWLINE )* testlist[expr_contextType.Load] ( NEWLINE )* EOF )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:7: ( LEADING_WS )? ( NEWLINE )* testlist[expr_contextType.Load] ( NEWLINE )* EOF
            {
            root_0 = (PythonTree)adaptor.nil();

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:7: ( LEADING_WS )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==LEADING_WS) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:7: LEADING_WS
                    {
                    LEADING_WS12=(Token)match(input,LEADING_WS,FOLLOW_LEADING_WS_in_eval_input298); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEADING_WS12_tree = (PythonTree)adaptor.create(LEADING_WS12);
                    adaptor.addChild(root_0, LEADING_WS12_tree);
                    }

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:19: ( NEWLINE )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==NEWLINE) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:20: NEWLINE
            	    {
            	    NEWLINE13=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_eval_input302); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NEWLINE13_tree = (PythonTree)adaptor.create(NEWLINE13);
            	    adaptor.addChild(root_0, NEWLINE13_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            pushFollow(FOLLOW_testlist_in_eval_input306);
            testlist14=testlist(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist14.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:62: ( NEWLINE )*
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==NEWLINE) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:378:63: NEWLINE
            	    {
            	    NEWLINE15=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_eval_input310); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NEWLINE15_tree = (PythonTree)adaptor.create(NEWLINE15);
            	    adaptor.addChild(root_0, NEWLINE15_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            EOF16=(Token)match(input,EOF,FOLLOW_EOF_in_eval_input314); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EOF16_tree = (PythonTree)adaptor.create(EOF16);
            adaptor.addChild(root_0, EOF16_tree);
            }
            if ( state.backtracking==0 ) {

                      mtype = new Expression(((Token)retval.start), actions.castExpr((testlist14!=null?((PythonTree)testlist14.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = mtype;

            }
        }
        catch (RecognitionException re) {

                    reportError(re);
                    errorHandler.recover(this, input,re);
                    PythonTree badNode = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
                    retval.tree = new ErrorMod(badNode);
                
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "eval_input"

    public static class dotted_attr_return extends ParserRuleReturnScope {
        public expr etype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dotted_attr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:393:1: dotted_attr returns [expr etype] : n1= NAME ( ( DOT n2+= NAME )+ | ) ;
    public final PythonParser.dotted_attr_return dotted_attr() throws RecognitionException {
        PythonParser.dotted_attr_return retval = new PythonParser.dotted_attr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token n1=null;
        Token DOT17=null;
        Token n2=null;
        List list_n2=null;

        PythonTree n1_tree=null;
        PythonTree DOT17_tree=null;
        PythonTree n2_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:395:5: (n1= NAME ( ( DOT n2+= NAME )+ | ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:395:7: n1= NAME ( ( DOT n2+= NAME )+ | )
            {
            root_0 = (PythonTree)adaptor.nil();

            n1=(Token)match(input,NAME,FOLLOW_NAME_in_dotted_attr366); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n1_tree = (PythonTree)adaptor.create(n1);
            adaptor.addChild(root_0, n1_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:396:7: ( ( DOT n2+= NAME )+ | )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==DOT) ) {
                alt10=1;
            }
            else if ( (LA10_0==NEWLINE||LA10_0==LPAREN) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:396:9: ( DOT n2+= NAME )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:396:9: ( DOT n2+= NAME )+
                    int cnt9=0;
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==DOT) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:396:10: DOT n2+= NAME
                    	    {
                    	    DOT17=(Token)match(input,DOT,FOLLOW_DOT_in_dotted_attr377); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    DOT17_tree = (PythonTree)adaptor.create(DOT17);
                    	    adaptor.addChild(root_0, DOT17_tree);
                    	    }
                    	    n2=(Token)match(input,NAME,FOLLOW_NAME_in_dotted_attr381); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    n2_tree = (PythonTree)adaptor.create(n2);
                    	    adaptor.addChild(root_0, n2_tree);
                    	    }
                    	    if (list_n2==null) list_n2=new ArrayList();
                    	    list_n2.add(n2);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt9 >= 1 ) break loop9;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(9, input);
                                throw eee;
                        }
                        cnt9++;
                    } while (true);

                    if ( state.backtracking==0 ) {

                                  retval.etype = actions.makeDottedAttr(n1, list_n2);
                              
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:401:9: 
                    {
                    if ( state.backtracking==0 ) {

                                  retval.etype = actions.makeNameNode(n1);
                              
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dotted_attr"

    public static class name_or_print_return extends ParserRuleReturnScope {
        public Token tok;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "name_or_print"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:409:1: name_or_print returns [Token tok] : ( NAME | {...}? => PRINT );
    public final PythonParser.name_or_print_return name_or_print() throws RecognitionException {
        PythonParser.name_or_print_return retval = new PythonParser.name_or_print_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NAME18=null;
        Token PRINT19=null;

        PythonTree NAME18_tree=null;
        PythonTree PRINT19_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:411:5: ( NAME | {...}? => PRINT )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==NAME) ) {
                alt11=1;
            }
            else if ( (LA11_0==PRINT) && ((printFunction))) {
                alt11=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:411:7: NAME
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    NAME18=(Token)match(input,NAME,FOLLOW_NAME_in_name_or_print446); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME18_tree = (PythonTree)adaptor.create(NAME18);
                    adaptor.addChild(root_0, NAME18_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.tok = ((Token)retval.start);
                          
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:414:7: {...}? => PRINT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    if ( !((printFunction)) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "name_or_print", "printFunction");
                    }
                    PRINT19=(Token)match(input,PRINT,FOLLOW_PRINT_in_name_or_print460); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PRINT19_tree = (PythonTree)adaptor.create(PRINT19);
                    adaptor.addChild(root_0, PRINT19_tree);
                    }
                    if ( state.backtracking==0 ) {

                              retval.tok = ((Token)retval.start);
                          
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "name_or_print"

    public static class attr_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "attr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:423:1: attr : ( NAME | AND | AS | ASSERT | BREAK | CLASS | CONTINUE | DEF | DELETE | ELIF | EXCEPT | EXEC | FINALLY | FROM | FOR | GLOBAL | IF | IMPORT | IN | IS | LAMBDA | NOT | OR | ORELSE | PASS | PRINT | RAISE | RETURN | TRY | WHILE | WITH | YIELD | BATCH );
    public final PythonParser.attr_return attr() throws RecognitionException {
        PythonParser.attr_return retval = new PythonParser.attr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token set20=null;

        PythonTree set20_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:424:5: ( NAME | AND | AS | ASSERT | BREAK | CLASS | CONTINUE | DEF | DELETE | ELIF | EXCEPT | EXEC | FINALLY | FROM | FOR | GLOBAL | IF | IMPORT | IN | IS | LAMBDA | NOT | OR | ORELSE | PASS | PRINT | RAISE | RETURN | TRY | WHILE | WITH | YIELD | BATCH )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
            {
            root_0 = (PythonTree)adaptor.nil();

            set20=(Token)input.LT(1);
            if ( input.LA(1)==NAME||(input.LA(1)>=PRINT && input.LA(1)<=BATCH) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (PythonTree)adaptor.create(set20));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "attr"

    public static class decorator_return extends ParserRuleReturnScope {
        public expr etype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "decorator"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:460:1: decorator returns [expr etype] : AT dotted_attr ( LPAREN ( arglist | ) RPAREN | ) NEWLINE ;
    public final PythonParser.decorator_return decorator() throws RecognitionException {
        PythonParser.decorator_return retval = new PythonParser.decorator_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token AT21=null;
        Token LPAREN23=null;
        Token RPAREN25=null;
        Token NEWLINE26=null;
        PythonParser.dotted_attr_return dotted_attr22 = null;

        PythonParser.arglist_return arglist24 = null;


        PythonTree AT21_tree=null;
        PythonTree LPAREN23_tree=null;
        PythonTree RPAREN25_tree=null;
        PythonTree NEWLINE26_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:465:5: ( AT dotted_attr ( LPAREN ( arglist | ) RPAREN | ) NEWLINE )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:465:7: AT dotted_attr ( LPAREN ( arglist | ) RPAREN | ) NEWLINE
            {
            root_0 = (PythonTree)adaptor.nil();

            AT21=(Token)match(input,AT,FOLLOW_AT_in_decorator770); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            AT21_tree = (PythonTree)adaptor.create(AT21);
            adaptor.addChild(root_0, AT21_tree);
            }
            pushFollow(FOLLOW_dotted_attr_in_decorator772);
            dotted_attr22=dotted_attr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dotted_attr22.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:466:5: ( LPAREN ( arglist | ) RPAREN | )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==LPAREN) ) {
                alt13=1;
            }
            else if ( (LA13_0==NEWLINE) ) {
                alt13=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }
            switch (alt13) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:466:7: LPAREN ( arglist | ) RPAREN
                    {
                    LPAREN23=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_decorator780); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN23_tree = (PythonTree)adaptor.create(LPAREN23);
                    adaptor.addChild(root_0, LPAREN23_tree);
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:467:7: ( arglist | )
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==NAME||LA12_0==NOT||LA12_0==LPAREN||(LA12_0>=PLUS && LA12_0<=MINUS)||(LA12_0>=TILDE && LA12_0<=LBRACK)||LA12_0==LCURLY||LA12_0==BACKQUOTE) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==PRINT) && ((printFunction))) {
                        alt12=1;
                    }
                    else if ( (LA12_0==LAMBDA||(LA12_0>=STAR && LA12_0<=DOUBLESTAR)||(LA12_0>=INT && LA12_0<=CONNECTTO)) ) {
                        alt12=1;
                    }
                    else if ( (LA12_0==RPAREN) ) {
                        alt12=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 12, 0, input);

                        throw nvae;
                    }
                    switch (alt12) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:467:9: arglist
                            {
                            pushFollow(FOLLOW_arglist_in_decorator790);
                            arglist24=arglist();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, arglist24.getTree());
                            if ( state.backtracking==0 ) {

                                          retval.etype = actions.makeCall(LPAREN23, (dotted_attr22!=null?dotted_attr22.etype:null), (arglist24!=null?arglist24.args:null), (arglist24!=null?arglist24.keywords:null),
                                                   (arglist24!=null?arglist24.starargs:null), (arglist24!=null?arglist24.kwargs:null));
                                      
                            }

                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:473:9: 
                            {
                            if ( state.backtracking==0 ) {

                                          retval.etype = actions.makeCall(LPAREN23, (dotted_attr22!=null?dotted_attr22.etype:null));
                                      
                            }

                            }
                            break;

                    }

                    RPAREN25=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_decorator834); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN25_tree = (PythonTree)adaptor.create(RPAREN25);
                    adaptor.addChild(root_0, RPAREN25_tree);
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:479:7: 
                    {
                    if ( state.backtracking==0 ) {

                                retval.etype = (dotted_attr22!=null?dotted_attr22.etype:null);
                            
                    }

                    }
                    break;

            }

            NEWLINE26=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_decorator856); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NEWLINE26_tree = (PythonTree)adaptor.create(NEWLINE26);
            adaptor.addChild(root_0, NEWLINE26_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = retval.etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "decorator"

    public static class decorators_return extends ParserRuleReturnScope {
        public List etypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "decorators"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:486:1: decorators returns [List etypes] : (d+= decorator )+ ;
    public final PythonParser.decorators_return decorators() throws RecognitionException {
        PythonParser.decorators_return retval = new PythonParser.decorators_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        List list_d=null;
        PythonParser.decorator_return d = null;
         d = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:488:5: ( (d+= decorator )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:488:7: (d+= decorator )+
            {
            root_0 = (PythonTree)adaptor.nil();

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:488:8: (d+= decorator )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==AT) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:488:8: d+= decorator
            	    {
            	    pushFollow(FOLLOW_decorator_in_decorators884);
            	    d=decorator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
            	    if (list_d==null) list_d=new ArrayList();
            	    list_d.add(d.getTree());


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);

            if ( state.backtracking==0 ) {

                        retval.etypes = list_d;
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "decorators"

    public static class funcdef_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "funcdef"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:495:1: funcdef : ( decorators )? DEF name_or_print parameters COLON suite[false] ;
    public final PythonParser.funcdef_return funcdef() throws RecognitionException {
        PythonParser.funcdef_return retval = new PythonParser.funcdef_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token DEF28=null;
        Token COLON31=null;
        PythonParser.decorators_return decorators27 = null;

        PythonParser.name_or_print_return name_or_print29 = null;

        PythonParser.parameters_return parameters30 = null;

        PythonParser.suite_return suite32 = null;


        PythonTree DEF28_tree=null;
        PythonTree COLON31_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:503:5: ( ( decorators )? DEF name_or_print parameters COLON suite[false] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:503:7: ( decorators )? DEF name_or_print parameters COLON suite[false]
            {
            root_0 = (PythonTree)adaptor.nil();

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:503:7: ( decorators )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==AT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:503:7: decorators
                    {
                    pushFollow(FOLLOW_decorators_in_funcdef922);
                    decorators27=decorators();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, decorators27.getTree());

                    }
                    break;

            }

            DEF28=(Token)match(input,DEF,FOLLOW_DEF_in_funcdef925); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DEF28_tree = (PythonTree)adaptor.create(DEF28);
            adaptor.addChild(root_0, DEF28_tree);
            }
            pushFollow(FOLLOW_name_or_print_in_funcdef927);
            name_or_print29=name_or_print();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, name_or_print29.getTree());
            pushFollow(FOLLOW_parameters_in_funcdef929);
            parameters30=parameters();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, parameters30.getTree());
            COLON31=(Token)match(input,COLON,FOLLOW_COLON_in_funcdef931); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON31_tree = (PythonTree)adaptor.create(COLON31);
            adaptor.addChild(root_0, COLON31_tree);
            }
            pushFollow(FOLLOW_suite_in_funcdef933);
            suite32=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, suite32.getTree());
            if ( state.backtracking==0 ) {

                      Token t = DEF28;
                      if ((decorators27!=null?((Token)decorators27.start):null) != null) {
                          t = (decorators27!=null?((Token)decorators27.start):null);
                      }
                      stype = actions.makeFuncdef(t, (name_or_print29!=null?((Token)name_or_print29.start):null), (parameters30!=null?parameters30.args:null), (suite32!=null?suite32.stypes:null), (decorators27!=null?decorators27.etypes:null));
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "funcdef"

    public static class parameters_return extends ParserRuleReturnScope {
        public arguments args;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "parameters"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:514:1: parameters returns [arguments args] : LPAREN ( varargslist | ) RPAREN ;
    public final PythonParser.parameters_return parameters() throws RecognitionException {
        PythonParser.parameters_return retval = new PythonParser.parameters_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token LPAREN33=null;
        Token RPAREN35=null;
        PythonParser.varargslist_return varargslist34 = null;


        PythonTree LPAREN33_tree=null;
        PythonTree RPAREN35_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:516:5: ( LPAREN ( varargslist | ) RPAREN )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:516:7: LPAREN ( varargslist | ) RPAREN
            {
            root_0 = (PythonTree)adaptor.nil();

            LPAREN33=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_parameters966); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LPAREN33_tree = (PythonTree)adaptor.create(LPAREN33);
            adaptor.addChild(root_0, LPAREN33_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:517:7: ( varargslist | )
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==NAME||LA16_0==LPAREN||(LA16_0>=STAR && LA16_0<=DOUBLESTAR)) ) {
                alt16=1;
            }
            else if ( (LA16_0==RPAREN) ) {
                alt16=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }
            switch (alt16) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:517:8: varargslist
                    {
                    pushFollow(FOLLOW_varargslist_in_parameters975);
                    varargslist34=varargslist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, varargslist34.getTree());
                    if ( state.backtracking==0 ) {

                                    retval.args = (varargslist34!=null?varargslist34.args:null);
                              
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:522:9: 
                    {
                    if ( state.backtracking==0 ) {

                                  retval.args = new arguments(((Token)retval.start), new ArrayList<expr>(), (Name)null, null, new ArrayList<expr>());
                              
                    }

                    }
                    break;

            }

            RPAREN35=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_parameters1019); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RPAREN35_tree = (PythonTree)adaptor.create(RPAREN35);
            adaptor.addChild(root_0, RPAREN35_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "parameters"

    public static class defparameter_return extends ParserRuleReturnScope {
        public expr etype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "defparameter"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:530:1: defparameter[List defaults] returns [expr etype] : fpdef[expr_contextType.Param] ( ASSIGN test[expr_contextType.Load] )? ;
    public final PythonParser.defparameter_return defparameter(List defaults) throws RecognitionException {
        PythonParser.defparameter_return retval = new PythonParser.defparameter_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token ASSIGN37=null;
        PythonParser.fpdef_return fpdef36 = null;

        PythonParser.test_return test38 = null;


        PythonTree ASSIGN37_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:535:5: ( fpdef[expr_contextType.Param] ( ASSIGN test[expr_contextType.Load] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:535:7: fpdef[expr_contextType.Param] ( ASSIGN test[expr_contextType.Load] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_fpdef_in_defparameter1052);
            fpdef36=fpdef(expr_contextType.Param);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, fpdef36.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:535:37: ( ASSIGN test[expr_contextType.Load] )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==ASSIGN) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:535:38: ASSIGN test[expr_contextType.Load]
                    {
                    ASSIGN37=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_defparameter1056); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN37_tree = (PythonTree)adaptor.create(ASSIGN37);
                    adaptor.addChild(root_0, ASSIGN37_tree);
                    }
                    pushFollow(FOLLOW_test_in_defparameter1058);
                    test38=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, test38.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        retval.etype = actions.castExpr((fpdef36!=null?((PythonTree)fpdef36.tree):null));
                        if (ASSIGN37 != null) {
                            defaults.add((test38!=null?((PythonTree)test38.tree):null));
                        } else if (!defaults.isEmpty()) {
                            throw new ParseException("non-default argument follows default argument", (fpdef36!=null?((PythonTree)fpdef36.tree):null));
                        }
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = retval.etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "defparameter"

    public static class varargslist_return extends ParserRuleReturnScope {
        public arguments args;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "varargslist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:549:1: varargslist returns [arguments args] : (d+= defparameter[defaults] ( options {greedy=true; } : COMMA d+= defparameter[defaults] )* ( COMMA ( STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )? )? | STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME );
    public final PythonParser.varargslist_return varargslist() throws RecognitionException {
        PythonParser.varargslist_return retval = new PythonParser.varargslist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token starargs=null;
        Token kwargs=null;
        Token COMMA39=null;
        Token COMMA40=null;
        Token STAR41=null;
        Token COMMA42=null;
        Token DOUBLESTAR43=null;
        Token DOUBLESTAR44=null;
        Token STAR45=null;
        Token COMMA46=null;
        Token DOUBLESTAR47=null;
        Token DOUBLESTAR48=null;
        List list_d=null;
        PythonParser.defparameter_return d = null;
         d = null;
        PythonTree starargs_tree=null;
        PythonTree kwargs_tree=null;
        PythonTree COMMA39_tree=null;
        PythonTree COMMA40_tree=null;
        PythonTree STAR41_tree=null;
        PythonTree COMMA42_tree=null;
        PythonTree DOUBLESTAR43_tree=null;
        PythonTree DOUBLESTAR44_tree=null;
        PythonTree STAR45_tree=null;
        PythonTree COMMA46_tree=null;
        PythonTree DOUBLESTAR47_tree=null;
        PythonTree DOUBLESTAR48_tree=null;


            List defaults = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:554:5: (d+= defparameter[defaults] ( options {greedy=true; } : COMMA d+= defparameter[defaults] )* ( COMMA ( STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )? )? | STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )
            int alt23=3;
            switch ( input.LA(1) ) {
            case NAME:
            case LPAREN:
                {
                alt23=1;
                }
                break;
            case STAR:
                {
                alt23=2;
                }
                break;
            case DOUBLESTAR:
                {
                alt23=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 23, 0, input);

                throw nvae;
            }

            switch (alt23) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:554:7: d+= defparameter[defaults] ( options {greedy=true; } : COMMA d+= defparameter[defaults] )* ( COMMA ( STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )? )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_defparameter_in_varargslist1104);
                    d=defparameter(defaults);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
                    if (list_d==null) list_d=new ArrayList();
                    list_d.add(d.getTree());

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:554:33: ( options {greedy=true; } : COMMA d+= defparameter[defaults] )*
                    loop18:
                    do {
                        int alt18=2;
                        int LA18_0 = input.LA(1);

                        if ( (LA18_0==COMMA) ) {
                            int LA18_1 = input.LA(2);

                            if ( (LA18_1==NAME||LA18_1==LPAREN) ) {
                                alt18=1;
                            }


                        }


                        switch (alt18) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:554:57: COMMA d+= defparameter[defaults]
                    	    {
                    	    COMMA39=(Token)match(input,COMMA,FOLLOW_COMMA_in_varargslist1115); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA39_tree = (PythonTree)adaptor.create(COMMA39);
                    	    adaptor.addChild(root_0, COMMA39_tree);
                    	    }
                    	    pushFollow(FOLLOW_defparameter_in_varargslist1119);
                    	    d=defparameter(defaults);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
                    	    if (list_d==null) list_d=new ArrayList();
                    	    list_d.add(d.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop18;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:555:7: ( COMMA ( STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )? )?
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==COMMA) ) {
                        alt21=1;
                    }
                    switch (alt21) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:555:8: COMMA ( STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )?
                            {
                            COMMA40=(Token)match(input,COMMA,FOLLOW_COMMA_in_varargslist1131); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA40_tree = (PythonTree)adaptor.create(COMMA40);
                            adaptor.addChild(root_0, COMMA40_tree);
                            }
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:556:11: ( STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )? | DOUBLESTAR kwargs= NAME )?
                            int alt20=3;
                            int LA20_0 = input.LA(1);

                            if ( (LA20_0==STAR) ) {
                                alt20=1;
                            }
                            else if ( (LA20_0==DOUBLESTAR) ) {
                                alt20=2;
                            }
                            switch (alt20) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:556:12: STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )?
                                    {
                                    STAR41=(Token)match(input,STAR,FOLLOW_STAR_in_varargslist1144); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    STAR41_tree = (PythonTree)adaptor.create(STAR41);
                                    adaptor.addChild(root_0, STAR41_tree);
                                    }
                                    starargs=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist1148); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    starargs_tree = (PythonTree)adaptor.create(starargs);
                                    adaptor.addChild(root_0, starargs_tree);
                                    }
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:556:31: ( COMMA DOUBLESTAR kwargs= NAME )?
                                    int alt19=2;
                                    int LA19_0 = input.LA(1);

                                    if ( (LA19_0==COMMA) ) {
                                        alt19=1;
                                    }
                                    switch (alt19) {
                                        case 1 :
                                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:556:32: COMMA DOUBLESTAR kwargs= NAME
                                            {
                                            COMMA42=(Token)match(input,COMMA,FOLLOW_COMMA_in_varargslist1151); if (state.failed) return retval;
                                            if ( state.backtracking==0 ) {
                                            COMMA42_tree = (PythonTree)adaptor.create(COMMA42);
                                            adaptor.addChild(root_0, COMMA42_tree);
                                            }
                                            DOUBLESTAR43=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist1153); if (state.failed) return retval;
                                            if ( state.backtracking==0 ) {
                                            DOUBLESTAR43_tree = (PythonTree)adaptor.create(DOUBLESTAR43);
                                            adaptor.addChild(root_0, DOUBLESTAR43_tree);
                                            }
                                            kwargs=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist1157); if (state.failed) return retval;
                                            if ( state.backtracking==0 ) {
                                            kwargs_tree = (PythonTree)adaptor.create(kwargs);
                                            adaptor.addChild(root_0, kwargs_tree);
                                            }

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:557:13: DOUBLESTAR kwargs= NAME
                                    {
                                    DOUBLESTAR44=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist1173); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    DOUBLESTAR44_tree = (PythonTree)adaptor.create(DOUBLESTAR44);
                                    adaptor.addChild(root_0, DOUBLESTAR44_tree);
                                    }
                                    kwargs=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist1177); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    kwargs_tree = (PythonTree)adaptor.create(kwargs);
                                    adaptor.addChild(root_0, kwargs_tree);
                                    }

                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                retval.args = actions.makeArgumentsType(((Token)retval.start), list_d, starargs, kwargs, defaults);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:563:7: STAR starargs= NAME ( COMMA DOUBLESTAR kwargs= NAME )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    STAR45=(Token)match(input,STAR,FOLLOW_STAR_in_varargslist1215); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STAR45_tree = (PythonTree)adaptor.create(STAR45);
                    adaptor.addChild(root_0, STAR45_tree);
                    }
                    starargs=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist1219); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    starargs_tree = (PythonTree)adaptor.create(starargs);
                    adaptor.addChild(root_0, starargs_tree);
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:563:26: ( COMMA DOUBLESTAR kwargs= NAME )?
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0==COMMA) ) {
                        alt22=1;
                    }
                    switch (alt22) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:563:27: COMMA DOUBLESTAR kwargs= NAME
                            {
                            COMMA46=(Token)match(input,COMMA,FOLLOW_COMMA_in_varargslist1222); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA46_tree = (PythonTree)adaptor.create(COMMA46);
                            adaptor.addChild(root_0, COMMA46_tree);
                            }
                            DOUBLESTAR47=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist1224); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            DOUBLESTAR47_tree = (PythonTree)adaptor.create(DOUBLESTAR47);
                            adaptor.addChild(root_0, DOUBLESTAR47_tree);
                            }
                            kwargs=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist1228); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            kwargs_tree = (PythonTree)adaptor.create(kwargs);
                            adaptor.addChild(root_0, kwargs_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                retval.args = actions.makeArgumentsType(((Token)retval.start), list_d, starargs, kwargs, defaults);
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:567:7: DOUBLESTAR kwargs= NAME
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    DOUBLESTAR48=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_varargslist1246); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOUBLESTAR48_tree = (PythonTree)adaptor.create(DOUBLESTAR48);
                    adaptor.addChild(root_0, DOUBLESTAR48_tree);
                    }
                    kwargs=(Token)match(input,NAME,FOLLOW_NAME_in_varargslist1250); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    kwargs_tree = (PythonTree)adaptor.create(kwargs);
                    adaptor.addChild(root_0, kwargs_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.args = actions.makeArgumentsType(((Token)retval.start), list_d, null, kwargs, defaults);
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "varargslist"

    public static class fpdef_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fpdef"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:574:1: fpdef[expr_contextType ctype] : ( NAME | ( LPAREN fpdef[null] COMMA )=> LPAREN fplist RPAREN | LPAREN fplist RPAREN );
    public final PythonParser.fpdef_return fpdef(expr_contextType ctype) throws RecognitionException {
        PythonParser.fpdef_return retval = new PythonParser.fpdef_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NAME49=null;
        Token LPAREN50=null;
        Token RPAREN52=null;
        Token LPAREN53=null;
        Token RPAREN55=null;
        PythonParser.fplist_return fplist51 = null;

        PythonParser.fplist_return fplist54 = null;


        PythonTree NAME49_tree=null;
        PythonTree LPAREN50_tree=null;
        PythonTree RPAREN52_tree=null;
        PythonTree LPAREN53_tree=null;
        PythonTree RPAREN55_tree=null;


            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:584:5: ( NAME | ( LPAREN fpdef[null] COMMA )=> LPAREN fplist RPAREN | LPAREN fplist RPAREN )
            int alt24=3;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==NAME) ) {
                alt24=1;
            }
            else if ( (LA24_0==LPAREN) ) {
                int LA24_2 = input.LA(2);

                if ( (synpred1_Python()) ) {
                    alt24=2;
                }
                else if ( (true) ) {
                    alt24=3;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 24, 2, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:584:7: NAME
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    NAME49=(Token)match(input,NAME,FOLLOW_NAME_in_fpdef1287); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME49_tree = (PythonTree)adaptor.create(NAME49);
                    adaptor.addChild(root_0, NAME49_tree);
                    }
                    if ( state.backtracking==0 ) {

                                etype = new Name(NAME49, (NAME49!=null?NAME49.getText():null), ctype);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:588:7: ( LPAREN fpdef[null] COMMA )=> LPAREN fplist RPAREN
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LPAREN50=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_fpdef1314); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN50_tree = (PythonTree)adaptor.create(LPAREN50);
                    adaptor.addChild(root_0, LPAREN50_tree);
                    }
                    pushFollow(FOLLOW_fplist_in_fpdef1316);
                    fplist51=fplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fplist51.getTree());
                    RPAREN52=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_fpdef1318); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN52_tree = (PythonTree)adaptor.create(RPAREN52);
                    adaptor.addChild(root_0, RPAREN52_tree);
                    }
                    if ( state.backtracking==0 ) {

                                etype = new Tuple((fplist51!=null?((Token)fplist51.start):null), actions.castExprs((fplist51!=null?fplist51.etypes:null)), expr_contextType.Store);
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:592:7: LPAREN fplist RPAREN
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LPAREN53=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_fpdef1334); if (state.failed) return retval;
                    pushFollow(FOLLOW_fplist_in_fpdef1337);
                    fplist54=fplist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fplist54.getTree());
                    RPAREN55=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_fpdef1339); if (state.failed) return retval;

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (etype != null) {
                      retval.tree = etype;
                  }
                  actions.checkAssign(actions.castExpr(((PythonTree)retval.tree)));

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fpdef"

    public static class fplist_return extends ParserRuleReturnScope {
        public List etypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "fplist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:596:1: fplist returns [List etypes] : f+= fpdef[expr_contextType.Store] ( options {greedy=true; } : COMMA f+= fpdef[expr_contextType.Store] )* ( COMMA )? ;
    public final PythonParser.fplist_return fplist() throws RecognitionException {
        PythonParser.fplist_return retval = new PythonParser.fplist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA56=null;
        Token COMMA57=null;
        List list_f=null;
        PythonParser.fpdef_return f = null;
         f = null;
        PythonTree COMMA56_tree=null;
        PythonTree COMMA57_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:598:5: (f+= fpdef[expr_contextType.Store] ( options {greedy=true; } : COMMA f+= fpdef[expr_contextType.Store] )* ( COMMA )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:598:7: f+= fpdef[expr_contextType.Store] ( options {greedy=true; } : COMMA f+= fpdef[expr_contextType.Store] )* ( COMMA )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_fpdef_in_fplist1368);
            f=fpdef(expr_contextType.Store);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
            if (list_f==null) list_f=new ArrayList();
            list_f.add(f.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:599:7: ( options {greedy=true; } : COMMA f+= fpdef[expr_contextType.Store] )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==COMMA) ) {
                    int LA25_1 = input.LA(2);

                    if ( (LA25_1==NAME||LA25_1==LPAREN) ) {
                        alt25=1;
                    }


                }


                switch (alt25) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:599:31: COMMA f+= fpdef[expr_contextType.Store]
            	    {
            	    COMMA56=(Token)match(input,COMMA,FOLLOW_COMMA_in_fplist1385); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA56_tree = (PythonTree)adaptor.create(COMMA56);
            	    adaptor.addChild(root_0, COMMA56_tree);
            	    }
            	    pushFollow(FOLLOW_fpdef_in_fplist1389);
            	    f=fpdef(expr_contextType.Store);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
            	    if (list_f==null) list_f=new ArrayList();
            	    list_f.add(f.getTree());


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:599:72: ( COMMA )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==COMMA) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:599:73: COMMA
                    {
                    COMMA57=(Token)match(input,COMMA,FOLLOW_COMMA_in_fplist1395); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA57_tree = (PythonTree)adaptor.create(COMMA57);
                    adaptor.addChild(root_0, COMMA57_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        retval.etypes = list_f;
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "fplist"

    public static class stmt_return extends ParserRuleReturnScope {
        public List stypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:606:1: stmt returns [List stypes] : ( simple_stmt | compound_stmt );
    public final PythonParser.stmt_return stmt() throws RecognitionException {
        PythonParser.stmt_return retval = new PythonParser.stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.simple_stmt_return simple_stmt58 = null;

        PythonParser.compound_stmt_return compound_stmt59 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:608:5: ( simple_stmt | compound_stmt )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==NAME||LA27_0==NOT||LA27_0==LPAREN||(LA27_0>=PLUS && LA27_0<=MINUS)||(LA27_0>=TILDE && LA27_0<=LBRACK)||LA27_0==LCURLY||LA27_0==BACKQUOTE) ) {
                alt27=1;
            }
            else if ( (LA27_0==PRINT) && (((printFunction)||(!printFunction)))) {
                alt27=1;
            }
            else if ( ((LA27_0>=ASSERT && LA27_0<=BREAK)||LA27_0==CONTINUE||LA27_0==DELETE||LA27_0==EXEC||LA27_0==FROM||LA27_0==GLOBAL||LA27_0==IMPORT||LA27_0==LAMBDA||(LA27_0>=PASS && LA27_0<=RETURN)||LA27_0==YIELD||(LA27_0>=INT && LA27_0<=CONNECTTO)) ) {
                alt27=1;
            }
            else if ( (LA27_0==CLASS||LA27_0==DEF||LA27_0==FOR||LA27_0==IF||(LA27_0>=TRY && LA27_0<=WITH)||(LA27_0>=BATCH && LA27_0<=AT)||LA27_0==PERSISTIT) ) {
                alt27=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:608:7: simple_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_simple_stmt_in_stmt1431);
                    simple_stmt58=simple_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simple_stmt58.getTree());
                    if ( state.backtracking==0 ) {

                                retval.stypes = (simple_stmt58!=null?simple_stmt58.stypes:null);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:612:7: compound_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_compound_stmt_in_stmt1447);
                    compound_stmt59=compound_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, compound_stmt59.getTree());
                    if ( state.backtracking==0 ) {

                                retval.stypes = new ArrayList();
                                retval.stypes.add((compound_stmt59!=null?((PythonTree)compound_stmt59.tree):null));
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "stmt"

    public static class simple_stmt_return extends ParserRuleReturnScope {
        public List stypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "simple_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:620:1: simple_stmt returns [List stypes] : s+= small_stmt ( options {greedy=true; } : SEMI s+= small_stmt )* ( SEMI )? NEWLINE ;
    public final PythonParser.simple_stmt_return simple_stmt() throws RecognitionException {
        PythonParser.simple_stmt_return retval = new PythonParser.simple_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token SEMI60=null;
        Token SEMI61=null;
        Token NEWLINE62=null;
        List list_s=null;
        PythonParser.small_stmt_return s = null;
         s = null;
        PythonTree SEMI60_tree=null;
        PythonTree SEMI61_tree=null;
        PythonTree NEWLINE62_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:622:5: (s+= small_stmt ( options {greedy=true; } : SEMI s+= small_stmt )* ( SEMI )? NEWLINE )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:622:7: s+= small_stmt ( options {greedy=true; } : SEMI s+= small_stmt )* ( SEMI )? NEWLINE
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_small_stmt_in_simple_stmt1483);
            s=small_stmt();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            if (list_s==null) list_s=new ArrayList();
            list_s.add(s.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:622:21: ( options {greedy=true; } : SEMI s+= small_stmt )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==SEMI) ) {
                    int LA28_1 = input.LA(2);

                    if ( (LA28_1==NAME||LA28_1==PRINT||(LA28_1>=ASSERT && LA28_1<=BREAK)||LA28_1==CONTINUE||LA28_1==DELETE||LA28_1==EXEC||LA28_1==FROM||LA28_1==GLOBAL||LA28_1==IMPORT||(LA28_1>=LAMBDA && LA28_1<=NOT)||(LA28_1>=PASS && LA28_1<=RETURN)||LA28_1==YIELD||LA28_1==LPAREN||(LA28_1>=PLUS && LA28_1<=MINUS)||(LA28_1>=TILDE && LA28_1<=LBRACK)||LA28_1==LCURLY||(LA28_1>=BACKQUOTE && LA28_1<=CONNECTTO)) ) {
                        alt28=1;
                    }


                }


                switch (alt28) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:622:45: SEMI s+= small_stmt
            	    {
            	    SEMI60=(Token)match(input,SEMI,FOLLOW_SEMI_in_simple_stmt1493); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMI60_tree = (PythonTree)adaptor.create(SEMI60);
            	    adaptor.addChild(root_0, SEMI60_tree);
            	    }
            	    pushFollow(FOLLOW_small_stmt_in_simple_stmt1497);
            	    s=small_stmt();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s.getTree());


            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:622:66: ( SEMI )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==SEMI) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:622:67: SEMI
                    {
                    SEMI61=(Token)match(input,SEMI,FOLLOW_SEMI_in_simple_stmt1502); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SEMI61_tree = (PythonTree)adaptor.create(SEMI61);
                    adaptor.addChild(root_0, SEMI61_tree);
                    }

                    }
                    break;

            }

            NEWLINE62=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_simple_stmt1506); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NEWLINE62_tree = (PythonTree)adaptor.create(NEWLINE62);
            adaptor.addChild(root_0, NEWLINE62_tree);
            }
            if ( state.backtracking==0 ) {

                        retval.stypes = list_s;
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "simple_stmt"

    public static class small_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "small_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:630:1: small_stmt : ( expr_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt | {...}? => print_stmt );
    public final PythonParser.small_stmt_return small_stmt() throws RecognitionException {
        PythonParser.small_stmt_return retval = new PythonParser.small_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.expr_stmt_return expr_stmt63 = null;

        PythonParser.del_stmt_return del_stmt64 = null;

        PythonParser.pass_stmt_return pass_stmt65 = null;

        PythonParser.flow_stmt_return flow_stmt66 = null;

        PythonParser.import_stmt_return import_stmt67 = null;

        PythonParser.global_stmt_return global_stmt68 = null;

        PythonParser.exec_stmt_return exec_stmt69 = null;

        PythonParser.assert_stmt_return assert_stmt70 = null;

        PythonParser.print_stmt_return print_stmt71 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:630:12: ( expr_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt | {...}? => print_stmt )
            int alt30=9;
            alt30 = dfa30.predict(input);
            switch (alt30) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:630:14: expr_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_expr_stmt_in_small_stmt1529);
                    expr_stmt63=expr_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr_stmt63.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:631:14: del_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_del_stmt_in_small_stmt1544);
                    del_stmt64=del_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, del_stmt64.getTree());

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:632:14: pass_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_pass_stmt_in_small_stmt1559);
                    pass_stmt65=pass_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pass_stmt65.getTree());

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:633:14: flow_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_flow_stmt_in_small_stmt1574);
                    flow_stmt66=flow_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, flow_stmt66.getTree());

                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:634:14: import_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_import_stmt_in_small_stmt1589);
                    import_stmt67=import_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, import_stmt67.getTree());

                    }
                    break;
                case 6 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:635:14: global_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_global_stmt_in_small_stmt1604);
                    global_stmt68=global_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, global_stmt68.getTree());

                    }
                    break;
                case 7 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:636:14: exec_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_exec_stmt_in_small_stmt1619);
                    exec_stmt69=exec_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, exec_stmt69.getTree());

                    }
                    break;
                case 8 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:637:14: assert_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_assert_stmt_in_small_stmt1634);
                    assert_stmt70=assert_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assert_stmt70.getTree());

                    }
                    break;
                case 9 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:638:14: {...}? => print_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    if ( !((!printFunction)) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "small_stmt", "!printFunction");
                    }
                    pushFollow(FOLLOW_print_stmt_in_small_stmt1653);
                    print_stmt71=print_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, print_stmt71.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "small_stmt"

    public static class expr_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:645:1: expr_stmt : ( ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) ) | ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) ) | lhs= testlist[expr_contextType.Load] ) ;
    public final PythonParser.expr_stmt_return expr_stmt() throws RecognitionException {
        PythonParser.expr_stmt_return retval = new PythonParser.expr_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token at=null;
        Token ay=null;
        List list_t=null;
        List list_y2=null;
        PythonParser.testlist_return lhs = null;

        PythonParser.augassign_return aay = null;

        PythonParser.yield_expr_return y1 = null;

        PythonParser.augassign_return aat = null;

        PythonParser.testlist_return rhs = null;

        PythonParser.testlist_return t = null;
         t = null;
        PythonParser.yield_expr_return y2 = null;
         y2 = null;
        PythonTree at_tree=null;
        PythonTree ay_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:654:5: ( ( ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) ) | ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) ) | lhs= testlist[expr_contextType.Load] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:654:7: ( ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) ) | ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) ) | lhs= testlist[expr_contextType.Load] )
            {
            root_0 = (PythonTree)adaptor.nil();

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:654:7: ( ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) ) | ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) ) | lhs= testlist[expr_contextType.Load] )
            int alt35=3;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:654:8: ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) )
                    {
                    pushFollow(FOLLOW_testlist_in_expr_stmt1709);
                    lhs=testlist(expr_contextType.AugStore);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lhs.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:655:9: ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) )
                    int alt31=2;
                    alt31 = dfa31.predict(input);
                    switch (alt31) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:655:11: (aay= augassign y1= yield_expr )
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:655:11: (aay= augassign y1= yield_expr )
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:655:12: aay= augassign y1= yield_expr
                            {
                            pushFollow(FOLLOW_augassign_in_expr_stmt1725);
                            aay=augassign();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, aay.getTree());
                            pushFollow(FOLLOW_yield_expr_in_expr_stmt1729);
                            y1=yield_expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, y1.getTree());
                            if ( state.backtracking==0 ) {

                                             actions.checkAugAssign(actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)));
                                             stype = new AugAssign((lhs!=null?((PythonTree)lhs.tree):null), actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)), (aay!=null?aay.op:null), actions.castExpr((y1!=null?y1.etype:null)));
                                         
                            }

                            }


                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:661:11: (aat= augassign rhs= testlist[expr_contextType.Load] )
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:661:11: (aat= augassign rhs= testlist[expr_contextType.Load] )
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:661:12: aat= augassign rhs= testlist[expr_contextType.Load]
                            {
                            pushFollow(FOLLOW_augassign_in_expr_stmt1769);
                            aat=augassign();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, aat.getTree());
                            pushFollow(FOLLOW_testlist_in_expr_stmt1773);
                            rhs=testlist(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, rhs.getTree());
                            if ( state.backtracking==0 ) {

                                             actions.checkAugAssign(actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)));
                                             stype = new AugAssign((lhs!=null?((PythonTree)lhs.tree):null), actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)), (aat!=null?aat.op:null), actions.castExpr((rhs!=null?((PythonTree)rhs.tree):null)));
                                         
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:668:7: ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) )
                    {
                    pushFollow(FOLLOW_testlist_in_expr_stmt1828);
                    lhs=testlist(expr_contextType.Store);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lhs.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:669:9: ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) )
                    int alt34=3;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==NEWLINE||LA34_0==SEMI) ) {
                        alt34=1;
                    }
                    else if ( (LA34_0==ASSIGN) ) {
                        int LA34_2 = input.LA(2);

                        if ( (LA34_2==YIELD) ) {
                            alt34=3;
                        }
                        else if ( (LA34_2==NAME||LA34_2==PRINT||(LA34_2>=LAMBDA && LA34_2<=NOT)||LA34_2==LPAREN||(LA34_2>=PLUS && LA34_2<=MINUS)||(LA34_2>=TILDE && LA34_2<=LBRACK)||LA34_2==LCURLY||(LA34_2>=BACKQUOTE && LA34_2<=CONNECTTO)) ) {
                            alt34=2;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 34, 2, input);

                            throw nvae;
                        }
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 34, 0, input);

                        throw nvae;
                    }
                    switch (alt34) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:670:9: 
                            {
                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:670:11: ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ )
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:670:11: ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ )
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:670:12: (at= ASSIGN t+= testlist[expr_contextType.Store] )+
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:670:12: (at= ASSIGN t+= testlist[expr_contextType.Store] )+
                            int cnt32=0;
                            loop32:
                            do {
                                int alt32=2;
                                int LA32_0 = input.LA(1);

                                if ( (LA32_0==ASSIGN) ) {
                                    alt32=1;
                                }


                                switch (alt32) {
                            	case 1 :
                            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:670:13: at= ASSIGN t+= testlist[expr_contextType.Store]
                            	    {
                            	    at=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_expr_stmt1855); if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	    at_tree = (PythonTree)adaptor.create(at);
                            	    adaptor.addChild(root_0, at_tree);
                            	    }
                            	    pushFollow(FOLLOW_testlist_in_expr_stmt1859);
                            	    t=testlist(expr_contextType.Store);

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                            	    if (list_t==null) list_t=new ArrayList();
                            	    list_t.add(t.getTree());


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt32 >= 1 ) break loop32;
                            	    if (state.backtracking>0) {state.failed=true; return retval;}
                                        EarlyExitException eee =
                                            new EarlyExitException(32, input);
                                        throw eee;
                                }
                                cnt32++;
                            } while (true);

                            if ( state.backtracking==0 ) {

                                              stype = new Assign((lhs!=null?((PythonTree)lhs.tree):null), actions.makeAssignTargets(
                                                  actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)), list_t), actions.makeAssignValue(list_t));
                                          
                            }

                            }


                            }
                            break;
                        case 3 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:676:11: ( (ay= ASSIGN y2+= yield_expr )+ )
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:676:11: ( (ay= ASSIGN y2+= yield_expr )+ )
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:676:12: (ay= ASSIGN y2+= yield_expr )+
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:676:12: (ay= ASSIGN y2+= yield_expr )+
                            int cnt33=0;
                            loop33:
                            do {
                                int alt33=2;
                                int LA33_0 = input.LA(1);

                                if ( (LA33_0==ASSIGN) ) {
                                    alt33=1;
                                }


                                switch (alt33) {
                            	case 1 :
                            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:676:13: ay= ASSIGN y2+= yield_expr
                            	    {
                            	    ay=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_expr_stmt1904); if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	    ay_tree = (PythonTree)adaptor.create(ay);
                            	    adaptor.addChild(root_0, ay_tree);
                            	    }
                            	    pushFollow(FOLLOW_yield_expr_in_expr_stmt1908);
                            	    y2=yield_expr();

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, y2.getTree());
                            	    if (list_y2==null) list_y2=new ArrayList();
                            	    list_y2.add(y2.getTree());


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt33 >= 1 ) break loop33;
                            	    if (state.backtracking>0) {state.failed=true; return retval;}
                                        EarlyExitException eee =
                                            new EarlyExitException(33, input);
                                        throw eee;
                                }
                                cnt33++;
                            } while (true);

                            if ( state.backtracking==0 ) {

                                              stype = new Assign((lhs!=null?((Token)lhs.start):null), actions.makeAssignTargets(
                                                  actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)), list_y2), actions.makeAssignValue(list_y2));
                                          
                            }

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:683:7: lhs= testlist[expr_contextType.Load]
                    {
                    pushFollow(FOLLOW_testlist_in_expr_stmt1956);
                    lhs=testlist(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lhs.getTree());
                    if ( state.backtracking==0 ) {

                                stype = new Expr((lhs!=null?((Token)lhs.start):null), actions.castExpr((lhs!=null?((PythonTree)lhs.tree):null)));
                            
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (stype != null) {
                      retval.tree = stype;
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "expr_stmt"

    public static class augassign_return extends ParserRuleReturnScope {
        public operatorType op;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "augassign"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:692:1: augassign returns [operatorType op] : ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL );
    public final PythonParser.augassign_return augassign() throws RecognitionException {
        PythonParser.augassign_return retval = new PythonParser.augassign_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token PLUSEQUAL72=null;
        Token MINUSEQUAL73=null;
        Token STAREQUAL74=null;
        Token SLASHEQUAL75=null;
        Token PERCENTEQUAL76=null;
        Token AMPEREQUAL77=null;
        Token VBAREQUAL78=null;
        Token CIRCUMFLEXEQUAL79=null;
        Token LEFTSHIFTEQUAL80=null;
        Token RIGHTSHIFTEQUAL81=null;
        Token DOUBLESTAREQUAL82=null;
        Token DOUBLESLASHEQUAL83=null;

        PythonTree PLUSEQUAL72_tree=null;
        PythonTree MINUSEQUAL73_tree=null;
        PythonTree STAREQUAL74_tree=null;
        PythonTree SLASHEQUAL75_tree=null;
        PythonTree PERCENTEQUAL76_tree=null;
        PythonTree AMPEREQUAL77_tree=null;
        PythonTree VBAREQUAL78_tree=null;
        PythonTree CIRCUMFLEXEQUAL79_tree=null;
        PythonTree LEFTSHIFTEQUAL80_tree=null;
        PythonTree RIGHTSHIFTEQUAL81_tree=null;
        PythonTree DOUBLESTAREQUAL82_tree=null;
        PythonTree DOUBLESLASHEQUAL83_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:694:5: ( PLUSEQUAL | MINUSEQUAL | STAREQUAL | SLASHEQUAL | PERCENTEQUAL | AMPEREQUAL | VBAREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL )
            int alt36=12;
            switch ( input.LA(1) ) {
            case PLUSEQUAL:
                {
                alt36=1;
                }
                break;
            case MINUSEQUAL:
                {
                alt36=2;
                }
                break;
            case STAREQUAL:
                {
                alt36=3;
                }
                break;
            case SLASHEQUAL:
                {
                alt36=4;
                }
                break;
            case PERCENTEQUAL:
                {
                alt36=5;
                }
                break;
            case AMPEREQUAL:
                {
                alt36=6;
                }
                break;
            case VBAREQUAL:
                {
                alt36=7;
                }
                break;
            case CIRCUMFLEXEQUAL:
                {
                alt36=8;
                }
                break;
            case LEFTSHIFTEQUAL:
                {
                alt36=9;
                }
                break;
            case RIGHTSHIFTEQUAL:
                {
                alt36=10;
                }
                break;
            case DOUBLESTAREQUAL:
                {
                alt36=11;
                }
                break;
            case DOUBLESLASHEQUAL:
                {
                alt36=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }

            switch (alt36) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:694:7: PLUSEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    PLUSEQUAL72=(Token)match(input,PLUSEQUAL,FOLLOW_PLUSEQUAL_in_augassign1998); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUSEQUAL72_tree = (PythonTree)adaptor.create(PLUSEQUAL72);
                    adaptor.addChild(root_0, PLUSEQUAL72_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.Add;
                              
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:698:7: MINUSEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    MINUSEQUAL73=(Token)match(input,MINUSEQUAL,FOLLOW_MINUSEQUAL_in_augassign2016); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUSEQUAL73_tree = (PythonTree)adaptor.create(MINUSEQUAL73);
                    adaptor.addChild(root_0, MINUSEQUAL73_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.Sub;
                              
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:702:7: STAREQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    STAREQUAL74=(Token)match(input,STAREQUAL,FOLLOW_STAREQUAL_in_augassign2034); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STAREQUAL74_tree = (PythonTree)adaptor.create(STAREQUAL74);
                    adaptor.addChild(root_0, STAREQUAL74_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.Mult;
                              
                    }

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:706:7: SLASHEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    SLASHEQUAL75=(Token)match(input,SLASHEQUAL,FOLLOW_SLASHEQUAL_in_augassign2052); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SLASHEQUAL75_tree = (PythonTree)adaptor.create(SLASHEQUAL75);
                    adaptor.addChild(root_0, SLASHEQUAL75_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.Div;
                              
                    }

                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:710:7: PERCENTEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    PERCENTEQUAL76=(Token)match(input,PERCENTEQUAL,FOLLOW_PERCENTEQUAL_in_augassign2070); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERCENTEQUAL76_tree = (PythonTree)adaptor.create(PERCENTEQUAL76);
                    adaptor.addChild(root_0, PERCENTEQUAL76_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.Mod;
                              
                    }

                    }
                    break;
                case 6 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:714:7: AMPEREQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    AMPEREQUAL77=(Token)match(input,AMPEREQUAL,FOLLOW_AMPEREQUAL_in_augassign2088); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    AMPEREQUAL77_tree = (PythonTree)adaptor.create(AMPEREQUAL77);
                    adaptor.addChild(root_0, AMPEREQUAL77_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.BitAnd;
                              
                    }

                    }
                    break;
                case 7 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:718:7: VBAREQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    VBAREQUAL78=(Token)match(input,VBAREQUAL,FOLLOW_VBAREQUAL_in_augassign2106); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    VBAREQUAL78_tree = (PythonTree)adaptor.create(VBAREQUAL78);
                    adaptor.addChild(root_0, VBAREQUAL78_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.BitOr;
                              
                    }

                    }
                    break;
                case 8 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:722:7: CIRCUMFLEXEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    CIRCUMFLEXEQUAL79=(Token)match(input,CIRCUMFLEXEQUAL,FOLLOW_CIRCUMFLEXEQUAL_in_augassign2124); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    CIRCUMFLEXEQUAL79_tree = (PythonTree)adaptor.create(CIRCUMFLEXEQUAL79);
                    adaptor.addChild(root_0, CIRCUMFLEXEQUAL79_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.BitXor;
                              
                    }

                    }
                    break;
                case 9 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:726:7: LEFTSHIFTEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LEFTSHIFTEQUAL80=(Token)match(input,LEFTSHIFTEQUAL,FOLLOW_LEFTSHIFTEQUAL_in_augassign2142); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSHIFTEQUAL80_tree = (PythonTree)adaptor.create(LEFTSHIFTEQUAL80);
                    adaptor.addChild(root_0, LEFTSHIFTEQUAL80_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.LShift;
                              
                    }

                    }
                    break;
                case 10 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:730:7: RIGHTSHIFTEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    RIGHTSHIFTEQUAL81=(Token)match(input,RIGHTSHIFTEQUAL,FOLLOW_RIGHTSHIFTEQUAL_in_augassign2160); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSHIFTEQUAL81_tree = (PythonTree)adaptor.create(RIGHTSHIFTEQUAL81);
                    adaptor.addChild(root_0, RIGHTSHIFTEQUAL81_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.RShift;
                              
                    }

                    }
                    break;
                case 11 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:734:7: DOUBLESTAREQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    DOUBLESTAREQUAL82=(Token)match(input,DOUBLESTAREQUAL,FOLLOW_DOUBLESTAREQUAL_in_augassign2178); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOUBLESTAREQUAL82_tree = (PythonTree)adaptor.create(DOUBLESTAREQUAL82);
                    adaptor.addChild(root_0, DOUBLESTAREQUAL82_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.Pow;
                              
                    }

                    }
                    break;
                case 12 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:738:7: DOUBLESLASHEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    DOUBLESLASHEQUAL83=(Token)match(input,DOUBLESLASHEQUAL,FOLLOW_DOUBLESLASHEQUAL_in_augassign2196); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOUBLESLASHEQUAL83_tree = (PythonTree)adaptor.create(DOUBLESLASHEQUAL83);
                    adaptor.addChild(root_0, DOUBLESLASHEQUAL83_tree);
                    }
                    if ( state.backtracking==0 ) {

                                  retval.op = operatorType.FloorDiv;
                              
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "augassign"

    public static class print_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "print_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:746:1: print_stmt : PRINT (t1= printlist | RIGHTSHIFT t2= printlist2 | ) ;
    public final PythonParser.print_stmt_return print_stmt() throws RecognitionException {
        PythonParser.print_stmt_return retval = new PythonParser.print_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token PRINT84=null;
        Token RIGHTSHIFT85=null;
        PythonParser.printlist_return t1 = null;

        PythonParser.printlist2_return t2 = null;


        PythonTree PRINT84_tree=null;
        PythonTree RIGHTSHIFT85_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:754:5: ( PRINT (t1= printlist | RIGHTSHIFT t2= printlist2 | ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:754:7: PRINT (t1= printlist | RIGHTSHIFT t2= printlist2 | )
            {
            root_0 = (PythonTree)adaptor.nil();

            PRINT84=(Token)match(input,PRINT,FOLLOW_PRINT_in_print_stmt2236); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            PRINT84_tree = (PythonTree)adaptor.create(PRINT84);
            adaptor.addChild(root_0, PRINT84_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:755:7: (t1= printlist | RIGHTSHIFT t2= printlist2 | )
            int alt37=3;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==NAME||LA37_0==NOT||LA37_0==LPAREN||(LA37_0>=PLUS && LA37_0<=MINUS)||(LA37_0>=TILDE && LA37_0<=LBRACK)||LA37_0==LCURLY||LA37_0==BACKQUOTE) ) {
                alt37=1;
            }
            else if ( (LA37_0==PRINT) && ((printFunction))) {
                alt37=1;
            }
            else if ( (LA37_0==LAMBDA||(LA37_0>=INT && LA37_0<=CONNECTTO)) ) {
                alt37=1;
            }
            else if ( (LA37_0==RIGHTSHIFT) ) {
                alt37=2;
            }
            else if ( (LA37_0==NEWLINE||LA37_0==SEMI) ) {
                alt37=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 37, 0, input);

                throw nvae;
            }
            switch (alt37) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:755:8: t1= printlist
                    {
                    pushFollow(FOLLOW_printlist_in_print_stmt2247);
                    t1=printlist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t1.getTree());
                    if ( state.backtracking==0 ) {

                                 stype = new Print(PRINT84, null, actions.castExprs((t1!=null?t1.elts:null)), (t1!=null?t1.newline:false));
                             
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:759:9: RIGHTSHIFT t2= printlist2
                    {
                    RIGHTSHIFT85=(Token)match(input,RIGHTSHIFT,FOLLOW_RIGHTSHIFT_in_print_stmt2266); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSHIFT85_tree = (PythonTree)adaptor.create(RIGHTSHIFT85);
                    adaptor.addChild(root_0, RIGHTSHIFT85_tree);
                    }
                    pushFollow(FOLLOW_printlist2_in_print_stmt2270);
                    t2=printlist2();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t2.getTree());
                    if ( state.backtracking==0 ) {

                                 stype = new Print(PRINT84, actions.castExpr((t2!=null?t2.elts:null).get(0)), actions.castExprs((t2!=null?t2.elts:null), 1), (t2!=null?t2.newline:false));
                             
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:764:8: 
                    {
                    if ( state.backtracking==0 ) {

                                 stype = new Print(PRINT84, null, new ArrayList<expr>(), true);
                             
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "print_stmt"

    public static class printlist_return extends ParserRuleReturnScope {
        public boolean newline;
        public List elts;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "printlist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:771:1: printlist returns [boolean newline, List elts] : ( ( test[null] COMMA )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] );
    public final PythonParser.printlist_return printlist() throws RecognitionException {
        PythonParser.printlist_return retval = new PythonParser.printlist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token trailcomma=null;
        Token COMMA86=null;
        List list_t=null;
        PythonParser.test_return t = null;
         t = null;
        PythonTree trailcomma_tree=null;
        PythonTree COMMA86_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:773:5: ( ( test[null] COMMA )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] )
            int alt40=2;
            alt40 = dfa40.predict(input);
            switch (alt40) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:773:7: ( test[null] COMMA )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_printlist2350);
                    t=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if (list_t==null) list_t=new ArrayList();
                    list_t.add(t.getTree());

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:774:39: ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )*
                    loop38:
                    do {
                        int alt38=2;
                        alt38 = dfa38.predict(input);
                        switch (alt38) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:774:56: COMMA t+= test[expr_contextType.Load]
                    	    {
                    	    COMMA86=(Token)match(input,COMMA,FOLLOW_COMMA_in_printlist2362); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA86_tree = (PythonTree)adaptor.create(COMMA86);
                    	    adaptor.addChild(root_0, COMMA86_tree);
                    	    }
                    	    pushFollow(FOLLOW_test_in_printlist2366);
                    	    t=test(expr_contextType.Load);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    	    if (list_t==null) list_t=new ArrayList();
                    	    list_t.add(t.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:774:95: (trailcomma= COMMA )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==COMMA) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:774:96: trailcomma= COMMA
                            {
                            trailcomma=(Token)match(input,COMMA,FOLLOW_COMMA_in_printlist2374); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            trailcomma_tree = (PythonTree)adaptor.create(trailcomma);
                            adaptor.addChild(root_0, trailcomma_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                 retval.elts =list_t;
                                 if (trailcomma == null) {
                                     retval.newline = true;
                                 } else {
                                     retval.newline = false;
                                 }
                             
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:783:7: t+= test[expr_contextType.Load]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_printlist2395);
                    t=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if (list_t==null) list_t=new ArrayList();
                    list_t.add(t.getTree());

                    if ( state.backtracking==0 ) {

                                retval.elts =list_t;
                                retval.newline = true;
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "printlist"

    public static class printlist2_return extends ParserRuleReturnScope {
        public boolean newline;
        public List elts;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "printlist2"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:792:1: printlist2 returns [boolean newline, List elts] : ( ( test[null] COMMA test[null] )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] );
    public final PythonParser.printlist2_return printlist2() throws RecognitionException {
        PythonParser.printlist2_return retval = new PythonParser.printlist2_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token trailcomma=null;
        Token COMMA87=null;
        List list_t=null;
        PythonParser.test_return t = null;
         t = null;
        PythonTree trailcomma_tree=null;
        PythonTree COMMA87_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:794:5: ( ( test[null] COMMA test[null] )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] )
            int alt43=2;
            alt43 = dfa43.predict(input);
            switch (alt43) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:794:7: ( test[null] COMMA test[null] )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_printlist22452);
                    t=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if (list_t==null) list_t=new ArrayList();
                    list_t.add(t.getTree());

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:795:39: ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )*
                    loop41:
                    do {
                        int alt41=2;
                        alt41 = dfa41.predict(input);
                        switch (alt41) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:795:56: COMMA t+= test[expr_contextType.Load]
                    	    {
                    	    COMMA87=(Token)match(input,COMMA,FOLLOW_COMMA_in_printlist22464); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA87_tree = (PythonTree)adaptor.create(COMMA87);
                    	    adaptor.addChild(root_0, COMMA87_tree);
                    	    }
                    	    pushFollow(FOLLOW_test_in_printlist22468);
                    	    t=test(expr_contextType.Load);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    	    if (list_t==null) list_t=new ArrayList();
                    	    list_t.add(t.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop41;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:795:95: (trailcomma= COMMA )?
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==COMMA) ) {
                        alt42=1;
                    }
                    switch (alt42) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:795:96: trailcomma= COMMA
                            {
                            trailcomma=(Token)match(input,COMMA,FOLLOW_COMMA_in_printlist22476); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            trailcomma_tree = (PythonTree)adaptor.create(trailcomma);
                            adaptor.addChild(root_0, trailcomma_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                       retval.elts =list_t;
                                 if (trailcomma == null) {
                                     retval.newline = true;
                                 } else {
                                     retval.newline = false;
                                 }
                             
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:803:7: t+= test[expr_contextType.Load]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_printlist22497);
                    t=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if (list_t==null) list_t=new ArrayList();
                    list_t.add(t.getTree());

                    if ( state.backtracking==0 ) {

                                retval.elts =list_t;
                                retval.newline = true;
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "printlist2"

    public static class del_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "del_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:811:1: del_stmt : DELETE del_list ;
    public final PythonParser.del_stmt_return del_stmt() throws RecognitionException {
        PythonParser.del_stmt_return retval = new PythonParser.del_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token DELETE88=null;
        PythonParser.del_list_return del_list89 = null;


        PythonTree DELETE88_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:818:5: ( DELETE del_list )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:818:7: DELETE del_list
            {
            root_0 = (PythonTree)adaptor.nil();

            DELETE88=(Token)match(input,DELETE,FOLLOW_DELETE_in_del_stmt2534); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DELETE88_tree = (PythonTree)adaptor.create(DELETE88);
            adaptor.addChild(root_0, DELETE88_tree);
            }
            pushFollow(FOLLOW_del_list_in_del_stmt2536);
            del_list89=del_list();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, del_list89.getTree());
            if ( state.backtracking==0 ) {

                        stype = new Delete(DELETE88, (del_list89!=null?del_list89.etypes:null));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "del_stmt"

    public static class pass_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "pass_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:825:1: pass_stmt : PASS ;
    public final PythonParser.pass_stmt_return pass_stmt() throws RecognitionException {
        PythonParser.pass_stmt_return retval = new PythonParser.pass_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token PASS90=null;

        PythonTree PASS90_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:832:5: ( PASS )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:832:7: PASS
            {
            root_0 = (PythonTree)adaptor.nil();

            PASS90=(Token)match(input,PASS,FOLLOW_PASS_in_pass_stmt2572); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            PASS90_tree = (PythonTree)adaptor.create(PASS90);
            adaptor.addChild(root_0, PASS90_tree);
            }
            if ( state.backtracking==0 ) {

                        stype = new Pass(PASS90);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "pass_stmt"

    public static class flow_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "flow_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:839:1: flow_stmt : ( break_stmt | continue_stmt | return_stmt | raise_stmt | yield_stmt );
    public final PythonParser.flow_stmt_return flow_stmt() throws RecognitionException {
        PythonParser.flow_stmt_return retval = new PythonParser.flow_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.break_stmt_return break_stmt91 = null;

        PythonParser.continue_stmt_return continue_stmt92 = null;

        PythonParser.return_stmt_return return_stmt93 = null;

        PythonParser.raise_stmt_return raise_stmt94 = null;

        PythonParser.yield_stmt_return yield_stmt95 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:840:5: ( break_stmt | continue_stmt | return_stmt | raise_stmt | yield_stmt )
            int alt44=5;
            switch ( input.LA(1) ) {
            case BREAK:
                {
                alt44=1;
                }
                break;
            case CONTINUE:
                {
                alt44=2;
                }
                break;
            case RETURN:
                {
                alt44=3;
                }
                break;
            case RAISE:
                {
                alt44=4;
                }
                break;
            case YIELD:
                {
                alt44=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }

            switch (alt44) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:840:7: break_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_break_stmt_in_flow_stmt2598);
                    break_stmt91=break_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, break_stmt91.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:841:7: continue_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_continue_stmt_in_flow_stmt2606);
                    continue_stmt92=continue_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, continue_stmt92.getTree());

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:842:7: return_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_return_stmt_in_flow_stmt2614);
                    return_stmt93=return_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, return_stmt93.getTree());

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:843:7: raise_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_raise_stmt_in_flow_stmt2622);
                    raise_stmt94=raise_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, raise_stmt94.getTree());

                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:844:7: yield_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_yield_stmt_in_flow_stmt2630);
                    yield_stmt95=yield_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, yield_stmt95.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "flow_stmt"

    public static class break_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "break_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:848:1: break_stmt : BREAK ;
    public final PythonParser.break_stmt_return break_stmt() throws RecognitionException {
        PythonParser.break_stmt_return retval = new PythonParser.break_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token BREAK96=null;

        PythonTree BREAK96_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:855:5: ( BREAK )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:855:7: BREAK
            {
            root_0 = (PythonTree)adaptor.nil();

            BREAK96=(Token)match(input,BREAK,FOLLOW_BREAK_in_break_stmt2658); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            BREAK96_tree = (PythonTree)adaptor.create(BREAK96);
            adaptor.addChild(root_0, BREAK96_tree);
            }
            if ( state.backtracking==0 ) {

                        stype = new Break(BREAK96);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "break_stmt"

    public static class continue_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "continue_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:862:1: continue_stmt : CONTINUE ;
    public final PythonParser.continue_stmt_return continue_stmt() throws RecognitionException {
        PythonParser.continue_stmt_return retval = new PythonParser.continue_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token CONTINUE97=null;

        PythonTree CONTINUE97_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:869:5: ( CONTINUE )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:869:7: CONTINUE
            {
            root_0 = (PythonTree)adaptor.nil();

            CONTINUE97=(Token)match(input,CONTINUE,FOLLOW_CONTINUE_in_continue_stmt2694); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            CONTINUE97_tree = (PythonTree)adaptor.create(CONTINUE97);
            adaptor.addChild(root_0, CONTINUE97_tree);
            }
            if ( state.backtracking==0 ) {

                        if (!suite_stack.isEmpty() && ((suite_scope)suite_stack.peek()).continueIllegal) {
                            errorHandler.error("'continue' not supported inside 'finally' clause", new PythonTree(((Token)retval.start)));
                        }
                        stype = new Continue(CONTINUE97);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "continue_stmt"

    public static class return_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "return_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:879:1: return_stmt : RETURN ( testlist[expr_contextType.Load] | ) ;
    public final PythonParser.return_stmt_return return_stmt() throws RecognitionException {
        PythonParser.return_stmt_return retval = new PythonParser.return_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token RETURN98=null;
        PythonParser.testlist_return testlist99 = null;


        PythonTree RETURN98_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:886:5: ( RETURN ( testlist[expr_contextType.Load] | ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:886:7: RETURN ( testlist[expr_contextType.Load] | )
            {
            root_0 = (PythonTree)adaptor.nil();

            RETURN98=(Token)match(input,RETURN,FOLLOW_RETURN_in_return_stmt2730); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RETURN98_tree = (PythonTree)adaptor.create(RETURN98);
            adaptor.addChild(root_0, RETURN98_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:887:7: ( testlist[expr_contextType.Load] | )
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==NAME||LA45_0==NOT||LA45_0==LPAREN||(LA45_0>=PLUS && LA45_0<=MINUS)||(LA45_0>=TILDE && LA45_0<=LBRACK)||LA45_0==LCURLY||LA45_0==BACKQUOTE) ) {
                alt45=1;
            }
            else if ( (LA45_0==PRINT) && ((printFunction))) {
                alt45=1;
            }
            else if ( (LA45_0==LAMBDA||(LA45_0>=INT && LA45_0<=CONNECTTO)) ) {
                alt45=1;
            }
            else if ( (LA45_0==NEWLINE||LA45_0==SEMI) ) {
                alt45=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }
            switch (alt45) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:887:8: testlist[expr_contextType.Load]
                    {
                    pushFollow(FOLLOW_testlist_in_return_stmt2739);
                    testlist99=testlist(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist99.getTree());
                    if ( state.backtracking==0 ) {

                                 stype = new Return(RETURN98, actions.castExpr((testlist99!=null?((PythonTree)testlist99.tree):null)));
                             
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:892:8: 
                    {
                    if ( state.backtracking==0 ) {

                                 stype = new Return(RETURN98, null);
                             
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "return_stmt"

    public static class yield_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "yield_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:899:1: yield_stmt : yield_expr ;
    public final PythonParser.yield_stmt_return yield_stmt() throws RecognitionException {
        PythonParser.yield_stmt_return retval = new PythonParser.yield_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.yield_expr_return yield_expr100 = null;




            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:906:5: ( yield_expr )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:906:7: yield_expr
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_yield_expr_in_yield_stmt2804);
            yield_expr100=yield_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, yield_expr100.getTree());
            if ( state.backtracking==0 ) {

                      stype = new Expr((yield_expr100!=null?((Token)yield_expr100.start):null), actions.castExpr((yield_expr100!=null?yield_expr100.etype:null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "yield_stmt"

    public static class raise_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "raise_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:913:1: raise_stmt : RAISE (t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )? )? )? ;
    public final PythonParser.raise_stmt_return raise_stmt() throws RecognitionException {
        PythonParser.raise_stmt_return retval = new PythonParser.raise_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token RAISE101=null;
        Token COMMA102=null;
        Token COMMA103=null;
        PythonParser.test_return t1 = null;

        PythonParser.test_return t2 = null;

        PythonParser.test_return t3 = null;


        PythonTree RAISE101_tree=null;
        PythonTree COMMA102_tree=null;
        PythonTree COMMA103_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:920:5: ( RAISE (t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )? )? )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:920:7: RAISE (t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )? )? )?
            {
            root_0 = (PythonTree)adaptor.nil();

            RAISE101=(Token)match(input,RAISE,FOLLOW_RAISE_in_raise_stmt2840); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RAISE101_tree = (PythonTree)adaptor.create(RAISE101);
            adaptor.addChild(root_0, RAISE101_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:920:13: (t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )? )? )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==NAME||LA48_0==NOT||LA48_0==LPAREN||(LA48_0>=PLUS && LA48_0<=MINUS)||(LA48_0>=TILDE && LA48_0<=LBRACK)||LA48_0==LCURLY||LA48_0==BACKQUOTE) ) {
                alt48=1;
            }
            else if ( (LA48_0==PRINT) && ((printFunction))) {
                alt48=1;
            }
            else if ( (LA48_0==LAMBDA||(LA48_0>=INT && LA48_0<=CONNECTTO)) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:920:14: t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )? )?
                    {
                    pushFollow(FOLLOW_test_in_raise_stmt2845);
                    t1=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t1.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:920:45: ( COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )? )?
                    int alt47=2;
                    int LA47_0 = input.LA(1);

                    if ( (LA47_0==COMMA) ) {
                        alt47=1;
                    }
                    switch (alt47) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:920:46: COMMA t2= test[expr_contextType.Load] ( COMMA t3= test[expr_contextType.Load] )?
                            {
                            COMMA102=(Token)match(input,COMMA,FOLLOW_COMMA_in_raise_stmt2849); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA102_tree = (PythonTree)adaptor.create(COMMA102);
                            adaptor.addChild(root_0, COMMA102_tree);
                            }
                            pushFollow(FOLLOW_test_in_raise_stmt2853);
                            t2=test(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, t2.getTree());
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:921:9: ( COMMA t3= test[expr_contextType.Load] )?
                            int alt46=2;
                            int LA46_0 = input.LA(1);

                            if ( (LA46_0==COMMA) ) {
                                alt46=1;
                            }
                            switch (alt46) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:921:10: COMMA t3= test[expr_contextType.Load]
                                    {
                                    COMMA103=(Token)match(input,COMMA,FOLLOW_COMMA_in_raise_stmt2865); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    COMMA103_tree = (PythonTree)adaptor.create(COMMA103);
                                    adaptor.addChild(root_0, COMMA103_tree);
                                    }
                                    pushFollow(FOLLOW_test_in_raise_stmt2869);
                                    t3=test(expr_contextType.Load);

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t3.getTree());

                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        stype = new Raise(RAISE101, actions.castExpr((t1!=null?((PythonTree)t1.tree):null)), actions.castExpr((t2!=null?((PythonTree)t2.tree):null)), actions.castExpr((t3!=null?((PythonTree)t3.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "raise_stmt"

    public static class import_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "import_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:928:1: import_stmt : ( import_name | import_from );
    public final PythonParser.import_stmt_return import_stmt() throws RecognitionException {
        PythonParser.import_stmt_return retval = new PythonParser.import_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.import_name_return import_name104 = null;

        PythonParser.import_from_return import_from105 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:929:5: ( import_name | import_from )
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==IMPORT) ) {
                alt49=1;
            }
            else if ( (LA49_0==FROM) ) {
                alt49=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 49, 0, input);

                throw nvae;
            }
            switch (alt49) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:929:7: import_name
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_import_name_in_import_stmt2902);
                    import_name104=import_name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, import_name104.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:930:7: import_from
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_import_from_in_import_stmt2910);
                    import_from105=import_from();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, import_from105.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "import_stmt"

    public static class import_name_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "import_name"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:934:1: import_name : IMPORT dotted_as_names ;
    public final PythonParser.import_name_return import_name() throws RecognitionException {
        PythonParser.import_name_return retval = new PythonParser.import_name_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token IMPORT106=null;
        PythonParser.dotted_as_names_return dotted_as_names107 = null;


        PythonTree IMPORT106_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:941:5: ( IMPORT dotted_as_names )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:941:7: IMPORT dotted_as_names
            {
            root_0 = (PythonTree)adaptor.nil();

            IMPORT106=(Token)match(input,IMPORT,FOLLOW_IMPORT_in_import_name2938); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IMPORT106_tree = (PythonTree)adaptor.create(IMPORT106);
            adaptor.addChild(root_0, IMPORT106_tree);
            }
            pushFollow(FOLLOW_dotted_as_names_in_import_name2940);
            dotted_as_names107=dotted_as_names();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dotted_as_names107.getTree());
            if ( state.backtracking==0 ) {

                        stype = new Import(IMPORT106, (dotted_as_names107!=null?dotted_as_names107.atypes:null));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "import_name"

    public static class import_from_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "import_from"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:949:1: import_from : FROM ( (d+= DOT )* dotted_name | (d+= DOT )+ ) IMPORT ( STAR | i1= import_as_names | LPAREN i2= import_as_names ( COMMA )? RPAREN ) ;
    public final PythonParser.import_from_return import_from() throws RecognitionException {
        PythonParser.import_from_return retval = new PythonParser.import_from_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token FROM108=null;
        Token IMPORT110=null;
        Token STAR111=null;
        Token LPAREN112=null;
        Token COMMA113=null;
        Token RPAREN114=null;
        Token d=null;
        List list_d=null;
        PythonParser.import_as_names_return i1 = null;

        PythonParser.import_as_names_return i2 = null;

        PythonParser.dotted_name_return dotted_name109 = null;


        PythonTree FROM108_tree=null;
        PythonTree IMPORT110_tree=null;
        PythonTree STAR111_tree=null;
        PythonTree LPAREN112_tree=null;
        PythonTree COMMA113_tree=null;
        PythonTree RPAREN114_tree=null;
        PythonTree d_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:5: ( FROM ( (d+= DOT )* dotted_name | (d+= DOT )+ ) IMPORT ( STAR | i1= import_as_names | LPAREN i2= import_as_names ( COMMA )? RPAREN ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:7: FROM ( (d+= DOT )* dotted_name | (d+= DOT )+ ) IMPORT ( STAR | i1= import_as_names | LPAREN i2= import_as_names ( COMMA )? RPAREN )
            {
            root_0 = (PythonTree)adaptor.nil();

            FROM108=(Token)match(input,FROM,FOLLOW_FROM_in_import_from2977); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FROM108_tree = (PythonTree)adaptor.create(FROM108);
            adaptor.addChild(root_0, FROM108_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:12: ( (d+= DOT )* dotted_name | (d+= DOT )+ )
            int alt52=2;
            alt52 = dfa52.predict(input);
            switch (alt52) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:13: (d+= DOT )* dotted_name
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:14: (d+= DOT )*
                    loop50:
                    do {
                        int alt50=2;
                        int LA50_0 = input.LA(1);

                        if ( (LA50_0==DOT) ) {
                            alt50=1;
                        }


                        switch (alt50) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:14: d+= DOT
                    	    {
                    	    d=(Token)match(input,DOT,FOLLOW_DOT_in_import_from2982); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    d_tree = (PythonTree)adaptor.create(d);
                    	    adaptor.addChild(root_0, d_tree);
                    	    }
                    	    if (list_d==null) list_d=new ArrayList();
                    	    list_d.add(d);


                    	    }
                    	    break;

                    	default :
                    	    break loop50;
                        }
                    } while (true);

                    pushFollow(FOLLOW_dotted_name_in_import_from2985);
                    dotted_name109=dotted_name();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, dotted_name109.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:35: (d+= DOT )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:36: (d+= DOT )+
                    int cnt51=0;
                    loop51:
                    do {
                        int alt51=2;
                        int LA51_0 = input.LA(1);

                        if ( (LA51_0==DOT) ) {
                            alt51=1;
                        }


                        switch (alt51) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:956:36: d+= DOT
                    	    {
                    	    d=(Token)match(input,DOT,FOLLOW_DOT_in_import_from2991); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    d_tree = (PythonTree)adaptor.create(d);
                    	    adaptor.addChild(root_0, d_tree);
                    	    }
                    	    if (list_d==null) list_d=new ArrayList();
                    	    list_d.add(d);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt51 >= 1 ) break loop51;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(51, input);
                                throw eee;
                        }
                        cnt51++;
                    } while (true);


                    }
                    break;

            }

            IMPORT110=(Token)match(input,IMPORT,FOLLOW_IMPORT_in_import_from2995); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IMPORT110_tree = (PythonTree)adaptor.create(IMPORT110);
            adaptor.addChild(root_0, IMPORT110_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:957:9: ( STAR | i1= import_as_names | LPAREN i2= import_as_names ( COMMA )? RPAREN )
            int alt54=3;
            switch ( input.LA(1) ) {
            case STAR:
                {
                alt54=1;
                }
                break;
            case NAME:
                {
                alt54=2;
                }
                break;
            case LPAREN:
                {
                alt54=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }

            switch (alt54) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:957:10: STAR
                    {
                    STAR111=(Token)match(input,STAR,FOLLOW_STAR_in_import_from3006); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STAR111_tree = (PythonTree)adaptor.create(STAR111);
                    adaptor.addChild(root_0, STAR111_tree);
                    }
                    if ( state.backtracking==0 ) {

                                   stype = new ImportFrom(FROM108, actions.makeFromText(list_d, (dotted_name109!=null?dotted_name109.names:null)),
                                       actions.makeModuleNameNode(list_d, (dotted_name109!=null?dotted_name109.names:null)),
                                       actions.makeStarAlias(STAR111), actions.makeLevel(list_d));
                               
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:963:11: i1= import_as_names
                    {
                    pushFollow(FOLLOW_import_as_names_in_import_from3031);
                    i1=import_as_names();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, i1.getTree());
                    if ( state.backtracking==0 ) {

                                   String dottedText = (dotted_name109!=null?input.toString(dotted_name109.start,dotted_name109.stop):null);
                                   if (dottedText != null && dottedText.equals("__future__")) {
                                       List<alias> aliases = (i1!=null?i1.atypes:null);
                                       for(alias a: aliases) {
                                           if (a != null) {
                                               if (a.getInternalName().equals("print_function")) {
                                                   printFunction = true;
                                               } else if (a.getInternalName().equals("unicode_literals")) {
                                                   unicodeLiterals = true;
                                               }
                                           }
                                       }
                                   }
                                   stype = new ImportFrom(FROM108, actions.makeFromText(list_d, (dotted_name109!=null?dotted_name109.names:null)),
                                       actions.makeModuleNameNode(list_d, (dotted_name109!=null?dotted_name109.names:null)),
                                       actions.makeAliases((i1!=null?i1.atypes:null)), actions.makeLevel(list_d));
                               
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:982:11: LPAREN i2= import_as_names ( COMMA )? RPAREN
                    {
                    LPAREN112=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_import_from3054); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN112_tree = (PythonTree)adaptor.create(LPAREN112);
                    adaptor.addChild(root_0, LPAREN112_tree);
                    }
                    pushFollow(FOLLOW_import_as_names_in_import_from3058);
                    i2=import_as_names();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, i2.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:982:37: ( COMMA )?
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==COMMA) ) {
                        alt53=1;
                    }
                    switch (alt53) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:982:37: COMMA
                            {
                            COMMA113=(Token)match(input,COMMA,FOLLOW_COMMA_in_import_from3060); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA113_tree = (PythonTree)adaptor.create(COMMA113);
                            adaptor.addChild(root_0, COMMA113_tree);
                            }

                            }
                            break;

                    }

                    RPAREN114=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_import_from3063); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN114_tree = (PythonTree)adaptor.create(RPAREN114);
                    adaptor.addChild(root_0, RPAREN114_tree);
                    }
                    if ( state.backtracking==0 ) {

                                   //XXX: this is almost a complete C&P of the code above - is there some way
                                   //     to factor it out?
                                   String dottedText = (dotted_name109!=null?input.toString(dotted_name109.start,dotted_name109.stop):null);
                                   if (dottedText != null && dottedText.equals("__future__")) {
                                       List<alias> aliases = (i2!=null?i2.atypes:null);
                                       for(alias a: aliases) {
                                           if (a != null) {
                                               if (a.getInternalName().equals("print_function")) {
                                                   printFunction = true;
                                               } else if (a.getInternalName().equals("unicode_literals")) {
                                                   unicodeLiterals = true;
                                               }
                                           }
                                       }
                                   }
                                   stype = new ImportFrom(FROM108, actions.makeFromText(list_d, (dotted_name109!=null?dotted_name109.names:null)),
                                       actions.makeModuleNameNode(list_d, (dotted_name109!=null?dotted_name109.names:null)),
                                       actions.makeAliases((i2!=null?i2.atypes:null)), actions.makeLevel(list_d));
                               
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "import_from"

    public static class import_as_names_return extends ParserRuleReturnScope {
        public List<alias> atypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "import_as_names"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1007:1: import_as_names returns [List<alias> atypes] : n+= import_as_name ( COMMA n+= import_as_name )* ;
    public final PythonParser.import_as_names_return import_as_names() throws RecognitionException {
        PythonParser.import_as_names_return retval = new PythonParser.import_as_names_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA115=null;
        List list_n=null;
        PythonParser.import_as_name_return n = null;
         n = null;
        PythonTree COMMA115_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1009:5: (n+= import_as_name ( COMMA n+= import_as_name )* )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1009:7: n+= import_as_name ( COMMA n+= import_as_name )*
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_import_as_name_in_import_as_names3112);
            n=import_as_name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, n.getTree());
            if (list_n==null) list_n=new ArrayList();
            list_n.add(n.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1009:25: ( COMMA n+= import_as_name )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==COMMA) ) {
                    int LA55_2 = input.LA(2);

                    if ( (LA55_2==NAME) ) {
                        alt55=1;
                    }


                }


                switch (alt55) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1009:26: COMMA n+= import_as_name
            	    {
            	    COMMA115=(Token)match(input,COMMA,FOLLOW_COMMA_in_import_as_names3115); if (state.failed) return retval;
            	    pushFollow(FOLLOW_import_as_name_in_import_as_names3120);
            	    n=import_as_name();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, n.getTree());
            	    if (list_n==null) list_n=new ArrayList();
            	    list_n.add(n.getTree());


            	    }
            	    break;

            	default :
            	    break loop55;
                }
            } while (true);

            if ( state.backtracking==0 ) {

                      retval.atypes = list_n;
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "import_as_names"

    public static class import_as_name_return extends ParserRuleReturnScope {
        public alias atype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "import_as_name"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1016:1: import_as_name returns [alias atype] : name= NAME ( AS asname= NAME )? ;
    public final PythonParser.import_as_name_return import_as_name() throws RecognitionException {
        PythonParser.import_as_name_return retval = new PythonParser.import_as_name_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token asname=null;
        Token AS116=null;

        PythonTree name_tree=null;
        PythonTree asname_tree=null;
        PythonTree AS116_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1021:5: (name= NAME ( AS asname= NAME )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1021:7: name= NAME ( AS asname= NAME )?
            {
            root_0 = (PythonTree)adaptor.nil();

            name=(Token)match(input,NAME,FOLLOW_NAME_in_import_as_name3161); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            name_tree = (PythonTree)adaptor.create(name);
            adaptor.addChild(root_0, name_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1021:17: ( AS asname= NAME )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==AS) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1021:18: AS asname= NAME
                    {
                    AS116=(Token)match(input,AS,FOLLOW_AS_in_import_as_name3164); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    AS116_tree = (PythonTree)adaptor.create(AS116);
                    adaptor.addChild(root_0, AS116_tree);
                    }
                    asname=(Token)match(input,NAME,FOLLOW_NAME_in_import_as_name3168); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    asname_tree = (PythonTree)adaptor.create(asname);
                    adaptor.addChild(root_0, asname_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                      retval.atype = new alias(actions.makeNameNode(name), actions.makeNameNode(asname));
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = retval.atype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "import_as_name"

    public static class dotted_as_name_return extends ParserRuleReturnScope {
        public alias atype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dotted_as_name"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1029:1: dotted_as_name returns [alias atype] : dotted_name ( AS asname= NAME )? ;
    public final PythonParser.dotted_as_name_return dotted_as_name() throws RecognitionException {
        PythonParser.dotted_as_name_return retval = new PythonParser.dotted_as_name_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token asname=null;
        Token AS118=null;
        PythonParser.dotted_name_return dotted_name117 = null;


        PythonTree asname_tree=null;
        PythonTree AS118_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1034:5: ( dotted_name ( AS asname= NAME )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1034:7: dotted_name ( AS asname= NAME )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_dotted_name_in_dotted_as_name3208);
            dotted_name117=dotted_name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, dotted_name117.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1034:19: ( AS asname= NAME )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==AS) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1034:20: AS asname= NAME
                    {
                    AS118=(Token)match(input,AS,FOLLOW_AS_in_dotted_as_name3211); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    AS118_tree = (PythonTree)adaptor.create(AS118);
                    adaptor.addChild(root_0, AS118_tree);
                    }
                    asname=(Token)match(input,NAME,FOLLOW_NAME_in_dotted_as_name3215); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    asname_tree = (PythonTree)adaptor.create(asname);
                    adaptor.addChild(root_0, asname_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                      retval.atype = new alias((dotted_name117!=null?dotted_name117.names:null), actions.makeNameNode(asname));
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = retval.atype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dotted_as_name"

    public static class dotted_as_names_return extends ParserRuleReturnScope {
        public List<alias> atypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dotted_as_names"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1041:1: dotted_as_names returns [List<alias> atypes] : d+= dotted_as_name ( COMMA d+= dotted_as_name )* ;
    public final PythonParser.dotted_as_names_return dotted_as_names() throws RecognitionException {
        PythonParser.dotted_as_names_return retval = new PythonParser.dotted_as_names_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA119=null;
        List list_d=null;
        PythonParser.dotted_as_name_return d = null;
         d = null;
        PythonTree COMMA119_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1043:5: (d+= dotted_as_name ( COMMA d+= dotted_as_name )* )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1043:7: d+= dotted_as_name ( COMMA d+= dotted_as_name )*
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_dotted_as_name_in_dotted_as_names3251);
            d=dotted_as_name();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
            if (list_d==null) list_d=new ArrayList();
            list_d.add(d.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1043:25: ( COMMA d+= dotted_as_name )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==COMMA) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1043:26: COMMA d+= dotted_as_name
            	    {
            	    COMMA119=(Token)match(input,COMMA,FOLLOW_COMMA_in_dotted_as_names3254); if (state.failed) return retval;
            	    pushFollow(FOLLOW_dotted_as_name_in_dotted_as_names3259);
            	    d=dotted_as_name();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
            	    if (list_d==null) list_d=new ArrayList();
            	    list_d.add(d.getTree());


            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);

            if ( state.backtracking==0 ) {

                      retval.atypes = list_d;
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dotted_as_names"

    public static class dotted_name_return extends ParserRuleReturnScope {
        public List<Name> names;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dotted_name"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1050:1: dotted_name returns [List<Name> names] : NAME ( DOT dn+= attr )* ;
    public final PythonParser.dotted_name_return dotted_name() throws RecognitionException {
        PythonParser.dotted_name_return retval = new PythonParser.dotted_name_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NAME120=null;
        Token DOT121=null;
        List list_dn=null;
        PythonParser.attr_return dn = null;
         dn = null;
        PythonTree NAME120_tree=null;
        PythonTree DOT121_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1052:5: ( NAME ( DOT dn+= attr )* )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1052:7: NAME ( DOT dn+= attr )*
            {
            root_0 = (PythonTree)adaptor.nil();

            NAME120=(Token)match(input,NAME,FOLLOW_NAME_in_dotted_name3293); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME120_tree = (PythonTree)adaptor.create(NAME120);
            adaptor.addChild(root_0, NAME120_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1052:12: ( DOT dn+= attr )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==DOT) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1052:13: DOT dn+= attr
            	    {
            	    DOT121=(Token)match(input,DOT,FOLLOW_DOT_in_dotted_name3296); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    DOT121_tree = (PythonTree)adaptor.create(DOT121);
            	    adaptor.addChild(root_0, DOT121_tree);
            	    }
            	    pushFollow(FOLLOW_attr_in_dotted_name3300);
            	    dn=attr();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, dn.getTree());
            	    if (list_dn==null) list_dn=new ArrayList();
            	    list_dn.add(dn.getTree());


            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);

            if ( state.backtracking==0 ) {

                      retval.names = actions.makeDottedName(NAME120, list_dn);
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dotted_name"

    public static class global_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "global_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1059:1: global_stmt : GLOBAL n+= NAME ( COMMA n+= NAME )* ;
    public final PythonParser.global_stmt_return global_stmt() throws RecognitionException {
        PythonParser.global_stmt_return retval = new PythonParser.global_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token GLOBAL122=null;
        Token COMMA123=null;
        Token n=null;
        List list_n=null;

        PythonTree GLOBAL122_tree=null;
        PythonTree COMMA123_tree=null;
        PythonTree n_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1066:5: ( GLOBAL n+= NAME ( COMMA n+= NAME )* )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1066:7: GLOBAL n+= NAME ( COMMA n+= NAME )*
            {
            root_0 = (PythonTree)adaptor.nil();

            GLOBAL122=(Token)match(input,GLOBAL,FOLLOW_GLOBAL_in_global_stmt3336); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            GLOBAL122_tree = (PythonTree)adaptor.create(GLOBAL122);
            adaptor.addChild(root_0, GLOBAL122_tree);
            }
            n=(Token)match(input,NAME,FOLLOW_NAME_in_global_stmt3340); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (PythonTree)adaptor.create(n);
            adaptor.addChild(root_0, n_tree);
            }
            if (list_n==null) list_n=new ArrayList();
            list_n.add(n);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1066:22: ( COMMA n+= NAME )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==COMMA) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1066:23: COMMA n+= NAME
            	    {
            	    COMMA123=(Token)match(input,COMMA,FOLLOW_COMMA_in_global_stmt3343); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA123_tree = (PythonTree)adaptor.create(COMMA123);
            	    adaptor.addChild(root_0, COMMA123_tree);
            	    }
            	    n=(Token)match(input,NAME,FOLLOW_NAME_in_global_stmt3347); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    n_tree = (PythonTree)adaptor.create(n);
            	    adaptor.addChild(root_0, n_tree);
            	    }
            	    if (list_n==null) list_n=new ArrayList();
            	    list_n.add(n);


            	    }
            	    break;

            	default :
            	    break loop60;
                }
            } while (true);

            if ( state.backtracking==0 ) {

                        stype = new Global(GLOBAL122, actions.makeNames(list_n), actions.makeNameNodes(list_n));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "global_stmt"

    public static class exec_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exec_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1073:1: exec_stmt : EXEC expr[expr_contextType.Load] ( IN t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )? )? ;
    public final PythonParser.exec_stmt_return exec_stmt() throws RecognitionException {
        PythonParser.exec_stmt_return retval = new PythonParser.exec_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token EXEC124=null;
        Token IN126=null;
        Token COMMA127=null;
        PythonParser.test_return t1 = null;

        PythonParser.test_return t2 = null;

        PythonParser.expr_return expr125 = null;


        PythonTree EXEC124_tree=null;
        PythonTree IN126_tree=null;
        PythonTree COMMA127_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1080:5: ( EXEC expr[expr_contextType.Load] ( IN t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )? )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1080:7: EXEC expr[expr_contextType.Load] ( IN t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )? )?
            {
            root_0 = (PythonTree)adaptor.nil();

            EXEC124=(Token)match(input,EXEC,FOLLOW_EXEC_in_exec_stmt3385); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EXEC124_tree = (PythonTree)adaptor.create(EXEC124);
            adaptor.addChild(root_0, EXEC124_tree);
            }
            pushFollow(FOLLOW_expr_in_exec_stmt3387);
            expr125=expr(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expr125.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1080:40: ( IN t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )? )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==IN) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1080:41: IN t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )?
                    {
                    IN126=(Token)match(input,IN,FOLLOW_IN_in_exec_stmt3391); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IN126_tree = (PythonTree)adaptor.create(IN126);
                    adaptor.addChild(root_0, IN126_tree);
                    }
                    pushFollow(FOLLOW_test_in_exec_stmt3395);
                    t1=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t1.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1080:75: ( COMMA t2= test[expr_contextType.Load] )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==COMMA) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1080:76: COMMA t2= test[expr_contextType.Load]
                            {
                            COMMA127=(Token)match(input,COMMA,FOLLOW_COMMA_in_exec_stmt3399); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA127_tree = (PythonTree)adaptor.create(COMMA127);
                            adaptor.addChild(root_0, COMMA127_tree);
                            }
                            pushFollow(FOLLOW_test_in_exec_stmt3403);
                            t2=test(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, t2.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                       stype = new Exec(EXEC124, actions.castExpr((expr125!=null?((PythonTree)expr125.tree):null)), actions.castExpr((t1!=null?((PythonTree)t1.tree):null)), actions.castExpr((t2!=null?((PythonTree)t2.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "exec_stmt"

    public static class assert_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assert_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1087:1: assert_stmt : ASSERT t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )? ;
    public final PythonParser.assert_stmt_return assert_stmt() throws RecognitionException {
        PythonParser.assert_stmt_return retval = new PythonParser.assert_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token ASSERT128=null;
        Token COMMA129=null;
        PythonParser.test_return t1 = null;

        PythonParser.test_return t2 = null;


        PythonTree ASSERT128_tree=null;
        PythonTree COMMA129_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1094:5: ( ASSERT t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1094:7: ASSERT t1= test[expr_contextType.Load] ( COMMA t2= test[expr_contextType.Load] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            ASSERT128=(Token)match(input,ASSERT,FOLLOW_ASSERT_in_assert_stmt3444); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ASSERT128_tree = (PythonTree)adaptor.create(ASSERT128);
            adaptor.addChild(root_0, ASSERT128_tree);
            }
            pushFollow(FOLLOW_test_in_assert_stmt3448);
            t1=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t1.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1094:45: ( COMMA t2= test[expr_contextType.Load] )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==COMMA) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1094:46: COMMA t2= test[expr_contextType.Load]
                    {
                    COMMA129=(Token)match(input,COMMA,FOLLOW_COMMA_in_assert_stmt3452); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA129_tree = (PythonTree)adaptor.create(COMMA129);
                    adaptor.addChild(root_0, COMMA129_tree);
                    }
                    pushFollow(FOLLOW_test_in_assert_stmt3456);
                    t2=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t2.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        stype = new Assert(ASSERT128, actions.castExpr((t1!=null?((PythonTree)t1.tree):null)), actions.castExpr((t2!=null?((PythonTree)t2.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "assert_stmt"

    public static class compound_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "compound_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1101:1: compound_stmt : ( if_stmt | while_stmt | for_stmt | try_stmt | with_stmt | batch_stmt | ( ( decorators )? DEF )=> funcdef | classdef );
    public final PythonParser.compound_stmt_return compound_stmt() throws RecognitionException {
        PythonParser.compound_stmt_return retval = new PythonParser.compound_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.if_stmt_return if_stmt130 = null;

        PythonParser.while_stmt_return while_stmt131 = null;

        PythonParser.for_stmt_return for_stmt132 = null;

        PythonParser.try_stmt_return try_stmt133 = null;

        PythonParser.with_stmt_return with_stmt134 = null;

        PythonParser.batch_stmt_return batch_stmt135 = null;

        PythonParser.funcdef_return funcdef136 = null;

        PythonParser.classdef_return classdef137 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1102:5: ( if_stmt | while_stmt | for_stmt | try_stmt | with_stmt | batch_stmt | ( ( decorators )? DEF )=> funcdef | classdef )
            int alt64=8;
            alt64 = dfa64.predict(input);
            switch (alt64) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1102:7: if_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_if_stmt_in_compound_stmt3485);
                    if_stmt130=if_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_stmt130.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1103:7: while_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_while_stmt_in_compound_stmt3493);
                    while_stmt131=while_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, while_stmt131.getTree());

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1104:7: for_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_for_stmt_in_compound_stmt3501);
                    for_stmt132=for_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, for_stmt132.getTree());

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1105:7: try_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_try_stmt_in_compound_stmt3509);
                    try_stmt133=try_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, try_stmt133.getTree());

                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1106:7: with_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_with_stmt_in_compound_stmt3517);
                    with_stmt134=with_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, with_stmt134.getTree());

                    }
                    break;
                case 6 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1107:7: batch_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_batch_stmt_in_compound_stmt3525);
                    batch_stmt135=batch_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, batch_stmt135.getTree());

                    }
                    break;
                case 7 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1108:7: ( ( decorators )? DEF )=> funcdef
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_funcdef_in_compound_stmt3542);
                    funcdef136=funcdef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, funcdef136.getTree());

                    }
                    break;
                case 8 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1109:7: classdef
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_classdef_in_compound_stmt3550);
                    classdef137=classdef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, classdef137.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "compound_stmt"

    public static class if_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1113:1: if_stmt : IF test[expr_contextType.Load] COLON ifsuite= suite[false] ( elif_clause )? ;
    public final PythonParser.if_stmt_return if_stmt() throws RecognitionException {
        PythonParser.if_stmt_return retval = new PythonParser.if_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token IF138=null;
        Token COLON140=null;
        PythonParser.suite_return ifsuite = null;

        PythonParser.test_return test139 = null;

        PythonParser.elif_clause_return elif_clause141 = null;


        PythonTree IF138_tree=null;
        PythonTree COLON140_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1120:5: ( IF test[expr_contextType.Load] COLON ifsuite= suite[false] ( elif_clause )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1120:7: IF test[expr_contextType.Load] COLON ifsuite= suite[false] ( elif_clause )?
            {
            root_0 = (PythonTree)adaptor.nil();

            IF138=(Token)match(input,IF,FOLLOW_IF_in_if_stmt3578); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF138_tree = (PythonTree)adaptor.create(IF138);
            adaptor.addChild(root_0, IF138_tree);
            }
            pushFollow(FOLLOW_test_in_if_stmt3580);
            test139=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, test139.getTree());
            COLON140=(Token)match(input,COLON,FOLLOW_COLON_in_if_stmt3583); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON140_tree = (PythonTree)adaptor.create(COLON140);
            adaptor.addChild(root_0, COLON140_tree);
            }
            pushFollow(FOLLOW_suite_in_if_stmt3587);
            ifsuite=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ifsuite.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1120:65: ( elif_clause )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==ELIF||LA65_0==ORELSE) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1120:65: elif_clause
                    {
                    pushFollow(FOLLOW_elif_clause_in_if_stmt3590);
                    elif_clause141=elif_clause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, elif_clause141.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        stype = new If(IF138, actions.castExpr((test139!=null?((PythonTree)test139.tree):null)), actions.castStmts((ifsuite!=null?ifsuite.stypes:null)),
                            actions.makeElse((elif_clause141!=null?elif_clause141.stypes:null), (elif_clause141!=null?((PythonTree)elif_clause141.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "if_stmt"

    public static class elif_clause_return extends ParserRuleReturnScope {
        public List stypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "elif_clause"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1128:1: elif_clause returns [List stypes] : ( else_clause | ELIF test[expr_contextType.Load] COLON suite[false] (e2= elif_clause | ) );
    public final PythonParser.elif_clause_return elif_clause() throws RecognitionException {
        PythonParser.elif_clause_return retval = new PythonParser.elif_clause_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token ELIF143=null;
        Token COLON145=null;
        PythonParser.elif_clause_return e2 = null;

        PythonParser.else_clause_return else_clause142 = null;

        PythonParser.test_return test144 = null;

        PythonParser.suite_return suite146 = null;


        PythonTree ELIF143_tree=null;
        PythonTree COLON145_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1138:5: ( else_clause | ELIF test[expr_contextType.Load] COLON suite[false] (e2= elif_clause | ) )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==ORELSE) ) {
                alt67=1;
            }
            else if ( (LA67_0==ELIF) ) {
                alt67=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1138:7: else_clause
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_else_clause_in_elif_clause3635);
                    else_clause142=else_clause();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, else_clause142.getTree());
                    if ( state.backtracking==0 ) {

                                retval.stypes = (else_clause142!=null?else_clause142.stypes:null);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1142:7: ELIF test[expr_contextType.Load] COLON suite[false] (e2= elif_clause | )
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    ELIF143=(Token)match(input,ELIF,FOLLOW_ELIF_in_elif_clause3651); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ELIF143_tree = (PythonTree)adaptor.create(ELIF143);
                    adaptor.addChild(root_0, ELIF143_tree);
                    }
                    pushFollow(FOLLOW_test_in_elif_clause3653);
                    test144=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, test144.getTree());
                    COLON145=(Token)match(input,COLON,FOLLOW_COLON_in_elif_clause3656); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COLON145_tree = (PythonTree)adaptor.create(COLON145);
                    adaptor.addChild(root_0, COLON145_tree);
                    }
                    pushFollow(FOLLOW_suite_in_elif_clause3658);
                    suite146=suite(false);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, suite146.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1143:7: (e2= elif_clause | )
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==ELIF||LA66_0==ORELSE) ) {
                        alt66=1;
                    }
                    else if ( (LA66_0==EOF||LA66_0==DEDENT||LA66_0==NEWLINE||LA66_0==NAME||LA66_0==PRINT||(LA66_0>=ASSERT && LA66_0<=DELETE)||LA66_0==EXEC||(LA66_0>=FROM && LA66_0<=IMPORT)||(LA66_0>=LAMBDA && LA66_0<=NOT)||(LA66_0>=PASS && LA66_0<=LPAREN)||(LA66_0>=PLUS && LA66_0<=MINUS)||(LA66_0>=TILDE && LA66_0<=LBRACK)||LA66_0==LCURLY||(LA66_0>=BACKQUOTE && LA66_0<=CONNECTTO)||LA66_0==PERSISTIT) ) {
                        alt66=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 66, 0, input);

                        throw nvae;
                    }
                    switch (alt66) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1143:8: e2= elif_clause
                            {
                            pushFollow(FOLLOW_elif_clause_in_elif_clause3670);
                            e2=elif_clause();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                            if ( state.backtracking==0 ) {

                                         stype = new If((test144!=null?((Token)test144.start):null), actions.castExpr((test144!=null?((PythonTree)test144.tree):null)), actions.castStmts((suite146!=null?suite146.stypes:null)), actions.makeElse((e2!=null?e2.stypes:null), (e2!=null?((PythonTree)e2.tree):null)));
                                     
                            }

                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1148:8: 
                            {
                            if ( state.backtracking==0 ) {

                                         stype = new If((test144!=null?((Token)test144.start):null), actions.castExpr((test144!=null?((PythonTree)test144.tree):null)), actions.castStmts((suite146!=null?suite146.stypes:null)), new ArrayList<stmt>());
                                     
                            }

                            }
                            break;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 if (stype != null) {
                     retval.tree = stype;
                 }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "elif_clause"

    public static class else_clause_return extends ParserRuleReturnScope {
        public List stypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "else_clause"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1155:1: else_clause returns [List stypes] : ORELSE COLON elsesuite= suite[false] ;
    public final PythonParser.else_clause_return else_clause() throws RecognitionException {
        PythonParser.else_clause_return retval = new PythonParser.else_clause_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token ORELSE147=null;
        Token COLON148=null;
        PythonParser.suite_return elsesuite = null;


        PythonTree ORELSE147_tree=null;
        PythonTree COLON148_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1157:5: ( ORELSE COLON elsesuite= suite[false] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1157:7: ORELSE COLON elsesuite= suite[false]
            {
            root_0 = (PythonTree)adaptor.nil();

            ORELSE147=(Token)match(input,ORELSE,FOLLOW_ORELSE_in_else_clause3730); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ORELSE147_tree = (PythonTree)adaptor.create(ORELSE147);
            adaptor.addChild(root_0, ORELSE147_tree);
            }
            COLON148=(Token)match(input,COLON,FOLLOW_COLON_in_else_clause3732); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON148_tree = (PythonTree)adaptor.create(COLON148);
            adaptor.addChild(root_0, COLON148_tree);
            }
            pushFollow(FOLLOW_suite_in_else_clause3736);
            elsesuite=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, elsesuite.getTree());
            if ( state.backtracking==0 ) {

                        retval.stypes = (elsesuite!=null?elsesuite.stypes:null);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "else_clause"

    public static class while_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "while_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1164:1: while_stmt : WHILE test[expr_contextType.Load] COLON s1= suite[false] ( ORELSE COLON s2= suite[false] )? ;
    public final PythonParser.while_stmt_return while_stmt() throws RecognitionException {
        PythonParser.while_stmt_return retval = new PythonParser.while_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token WHILE149=null;
        Token COLON151=null;
        Token ORELSE152=null;
        Token COLON153=null;
        PythonParser.suite_return s1 = null;

        PythonParser.suite_return s2 = null;

        PythonParser.test_return test150 = null;


        PythonTree WHILE149_tree=null;
        PythonTree COLON151_tree=null;
        PythonTree ORELSE152_tree=null;
        PythonTree COLON153_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1171:5: ( WHILE test[expr_contextType.Load] COLON s1= suite[false] ( ORELSE COLON s2= suite[false] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1171:7: WHILE test[expr_contextType.Load] COLON s1= suite[false] ( ORELSE COLON s2= suite[false] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            WHILE149=(Token)match(input,WHILE,FOLLOW_WHILE_in_while_stmt3773); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WHILE149_tree = (PythonTree)adaptor.create(WHILE149);
            adaptor.addChild(root_0, WHILE149_tree);
            }
            pushFollow(FOLLOW_test_in_while_stmt3775);
            test150=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, test150.getTree());
            COLON151=(Token)match(input,COLON,FOLLOW_COLON_in_while_stmt3778); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON151_tree = (PythonTree)adaptor.create(COLON151);
            adaptor.addChild(root_0, COLON151_tree);
            }
            pushFollow(FOLLOW_suite_in_while_stmt3782);
            s1=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s1.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1171:63: ( ORELSE COLON s2= suite[false] )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==ORELSE) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1171:64: ORELSE COLON s2= suite[false]
                    {
                    ORELSE152=(Token)match(input,ORELSE,FOLLOW_ORELSE_in_while_stmt3786); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ORELSE152_tree = (PythonTree)adaptor.create(ORELSE152);
                    adaptor.addChild(root_0, ORELSE152_tree);
                    }
                    COLON153=(Token)match(input,COLON,FOLLOW_COLON_in_while_stmt3788); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COLON153_tree = (PythonTree)adaptor.create(COLON153);
                    adaptor.addChild(root_0, COLON153_tree);
                    }
                    pushFollow(FOLLOW_suite_in_while_stmt3792);
                    s2=suite(false);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s2.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        stype = actions.makeWhile(WHILE149, actions.castExpr((test150!=null?((PythonTree)test150.tree):null)), (s1!=null?s1.stypes:null), (s2!=null?s2.stypes:null));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "while_stmt"

    public static class for_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "for_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1178:1: for_stmt : FOR exprlist[expr_contextType.Store] IN testlist[expr_contextType.Load] COLON s1= suite[false] ( ORELSE COLON s2= suite[false] )? ;
    public final PythonParser.for_stmt_return for_stmt() throws RecognitionException {
        PythonParser.for_stmt_return retval = new PythonParser.for_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token FOR154=null;
        Token IN156=null;
        Token COLON158=null;
        Token ORELSE159=null;
        Token COLON160=null;
        PythonParser.suite_return s1 = null;

        PythonParser.suite_return s2 = null;

        PythonParser.exprlist_return exprlist155 = null;

        PythonParser.testlist_return testlist157 = null;


        PythonTree FOR154_tree=null;
        PythonTree IN156_tree=null;
        PythonTree COLON158_tree=null;
        PythonTree ORELSE159_tree=null;
        PythonTree COLON160_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1185:5: ( FOR exprlist[expr_contextType.Store] IN testlist[expr_contextType.Load] COLON s1= suite[false] ( ORELSE COLON s2= suite[false] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1185:7: FOR exprlist[expr_contextType.Store] IN testlist[expr_contextType.Load] COLON s1= suite[false] ( ORELSE COLON s2= suite[false] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            FOR154=(Token)match(input,FOR,FOLLOW_FOR_in_for_stmt3831); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FOR154_tree = (PythonTree)adaptor.create(FOR154);
            adaptor.addChild(root_0, FOR154_tree);
            }
            pushFollow(FOLLOW_exprlist_in_for_stmt3833);
            exprlist155=exprlist(expr_contextType.Store);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exprlist155.getTree());
            IN156=(Token)match(input,IN,FOLLOW_IN_in_for_stmt3836); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IN156_tree = (PythonTree)adaptor.create(IN156);
            adaptor.addChild(root_0, IN156_tree);
            }
            pushFollow(FOLLOW_testlist_in_for_stmt3838);
            testlist157=testlist(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist157.getTree());
            COLON158=(Token)match(input,COLON,FOLLOW_COLON_in_for_stmt3841); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON158_tree = (PythonTree)adaptor.create(COLON158);
            adaptor.addChild(root_0, COLON158_tree);
            }
            pushFollow(FOLLOW_suite_in_for_stmt3845);
            s1=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s1.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1186:9: ( ORELSE COLON s2= suite[false] )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==ORELSE) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1186:10: ORELSE COLON s2= suite[false]
                    {
                    ORELSE159=(Token)match(input,ORELSE,FOLLOW_ORELSE_in_for_stmt3857); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ORELSE159_tree = (PythonTree)adaptor.create(ORELSE159);
                    adaptor.addChild(root_0, ORELSE159_tree);
                    }
                    COLON160=(Token)match(input,COLON,FOLLOW_COLON_in_for_stmt3859); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COLON160_tree = (PythonTree)adaptor.create(COLON160);
                    adaptor.addChild(root_0, COLON160_tree);
                    }
                    pushFollow(FOLLOW_suite_in_for_stmt3863);
                    s2=suite(false);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s2.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        stype = actions.makeFor(FOR154, (exprlist155!=null?exprlist155.etype:null), actions.castExpr((testlist157!=null?((PythonTree)testlist157.tree):null)), (s1!=null?s1.stypes:null), (s2!=null?s2.stypes:null));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "for_stmt"

    public static class try_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "try_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1197:1: try_stmt : TRY COLON trysuite= suite[!$suite.isEmpty() && $suite::continueIllegal] ( (e+= except_clause )+ ( ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal] )? ( FINALLY COLON finalsuite= suite[true] )? | FINALLY COLON finalsuite= suite[true] ) ;
    public final PythonParser.try_stmt_return try_stmt() throws RecognitionException {
        PythonParser.try_stmt_return retval = new PythonParser.try_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token TRY161=null;
        Token COLON162=null;
        Token ORELSE163=null;
        Token COLON164=null;
        Token FINALLY165=null;
        Token COLON166=null;
        Token FINALLY167=null;
        Token COLON168=null;
        List list_e=null;
        PythonParser.suite_return trysuite = null;

        PythonParser.suite_return elsesuite = null;

        PythonParser.suite_return finalsuite = null;

        PythonParser.except_clause_return e = null;
         e = null;
        PythonTree TRY161_tree=null;
        PythonTree COLON162_tree=null;
        PythonTree ORELSE163_tree=null;
        PythonTree COLON164_tree=null;
        PythonTree FINALLY165_tree=null;
        PythonTree COLON166_tree=null;
        PythonTree FINALLY167_tree=null;
        PythonTree COLON168_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1204:5: ( TRY COLON trysuite= suite[!$suite.isEmpty() && $suite::continueIllegal] ( (e+= except_clause )+ ( ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal] )? ( FINALLY COLON finalsuite= suite[true] )? | FINALLY COLON finalsuite= suite[true] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1204:7: TRY COLON trysuite= suite[!$suite.isEmpty() && $suite::continueIllegal] ( (e+= except_clause )+ ( ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal] )? ( FINALLY COLON finalsuite= suite[true] )? | FINALLY COLON finalsuite= suite[true] )
            {
            root_0 = (PythonTree)adaptor.nil();

            TRY161=(Token)match(input,TRY,FOLLOW_TRY_in_try_stmt3906); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TRY161_tree = (PythonTree)adaptor.create(TRY161);
            adaptor.addChild(root_0, TRY161_tree);
            }
            COLON162=(Token)match(input,COLON,FOLLOW_COLON_in_try_stmt3908); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON162_tree = (PythonTree)adaptor.create(COLON162);
            adaptor.addChild(root_0, COLON162_tree);
            }
            pushFollow(FOLLOW_suite_in_try_stmt3912);
            trysuite=suite(!suite_stack.isEmpty() && ((suite_scope)suite_stack.peek()).continueIllegal);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, trysuite.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:7: ( (e+= except_clause )+ ( ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal] )? ( FINALLY COLON finalsuite= suite[true] )? | FINALLY COLON finalsuite= suite[true] )
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==EXCEPT) ) {
                alt73=1;
            }
            else if ( (LA73_0==FINALLY) ) {
                alt73=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }
            switch (alt73) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:9: (e+= except_clause )+ ( ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal] )? ( FINALLY COLON finalsuite= suite[true] )?
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:10: (e+= except_clause )+
                    int cnt70=0;
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==EXCEPT) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:10: e+= except_clause
                    	    {
                    	    pushFollow(FOLLOW_except_clause_in_try_stmt3925);
                    	    e=except_clause();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    	    if (list_e==null) list_e=new ArrayList();
                    	    list_e.add(e.getTree());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt70 >= 1 ) break loop70;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(70, input);
                                throw eee;
                        }
                        cnt70++;
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:27: ( ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal] )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==ORELSE) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:28: ORELSE COLON elsesuite= suite[!$suite.isEmpty() && $suite::continueIllegal]
                            {
                            ORELSE163=(Token)match(input,ORELSE,FOLLOW_ORELSE_in_try_stmt3929); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            ORELSE163_tree = (PythonTree)adaptor.create(ORELSE163);
                            adaptor.addChild(root_0, ORELSE163_tree);
                            }
                            COLON164=(Token)match(input,COLON,FOLLOW_COLON_in_try_stmt3931); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COLON164_tree = (PythonTree)adaptor.create(COLON164);
                            adaptor.addChild(root_0, COLON164_tree);
                            }
                            pushFollow(FOLLOW_suite_in_try_stmt3935);
                            elsesuite=suite(!suite_stack.isEmpty() && ((suite_scope)suite_stack.peek()).continueIllegal);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, elsesuite.getTree());

                            }
                            break;

                    }

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:105: ( FINALLY COLON finalsuite= suite[true] )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==FINALLY) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1205:106: FINALLY COLON finalsuite= suite[true]
                            {
                            FINALLY165=(Token)match(input,FINALLY,FOLLOW_FINALLY_in_try_stmt3941); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            FINALLY165_tree = (PythonTree)adaptor.create(FINALLY165);
                            adaptor.addChild(root_0, FINALLY165_tree);
                            }
                            COLON166=(Token)match(input,COLON,FOLLOW_COLON_in_try_stmt3943); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COLON166_tree = (PythonTree)adaptor.create(COLON166);
                            adaptor.addChild(root_0, COLON166_tree);
                            }
                            pushFollow(FOLLOW_suite_in_try_stmt3947);
                            finalsuite=suite(true);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, finalsuite.getTree());

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                  stype = actions.makeTryExcept(TRY161, (trysuite!=null?trysuite.stypes:null), list_e, (elsesuite!=null?elsesuite.stypes:null), (finalsuite!=null?finalsuite.stypes:null));
                              
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1209:9: FINALLY COLON finalsuite= suite[true]
                    {
                    FINALLY167=(Token)match(input,FINALLY,FOLLOW_FINALLY_in_try_stmt3970); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FINALLY167_tree = (PythonTree)adaptor.create(FINALLY167);
                    adaptor.addChild(root_0, FINALLY167_tree);
                    }
                    COLON168=(Token)match(input,COLON,FOLLOW_COLON_in_try_stmt3972); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COLON168_tree = (PythonTree)adaptor.create(COLON168);
                    adaptor.addChild(root_0, COLON168_tree);
                    }
                    pushFollow(FOLLOW_suite_in_try_stmt3976);
                    finalsuite=suite(true);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, finalsuite.getTree());
                    if ( state.backtracking==0 ) {

                                  stype = actions.makeTryFinally(TRY161, (trysuite!=null?trysuite.stypes:null), (finalsuite!=null?finalsuite.stypes:null));
                              
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "try_stmt"

    public static class with_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "with_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1217:1: with_stmt : WITH w+= with_item ( options {greedy=true; } : COMMA w+= with_item )* COLON suite[false] ;
    public final PythonParser.with_stmt_return with_stmt() throws RecognitionException {
        PythonParser.with_stmt_return retval = new PythonParser.with_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token WITH169=null;
        Token COMMA170=null;
        Token COLON171=null;
        List list_w=null;
        PythonParser.suite_return suite172 = null;

        PythonParser.with_item_return w = null;
         w = null;
        PythonTree WITH169_tree=null;
        PythonTree COMMA170_tree=null;
        PythonTree COLON171_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1224:5: ( WITH w+= with_item ( options {greedy=true; } : COMMA w+= with_item )* COLON suite[false] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1224:7: WITH w+= with_item ( options {greedy=true; } : COMMA w+= with_item )* COLON suite[false]
            {
            root_0 = (PythonTree)adaptor.nil();

            WITH169=(Token)match(input,WITH,FOLLOW_WITH_in_with_stmt4025); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WITH169_tree = (PythonTree)adaptor.create(WITH169);
            adaptor.addChild(root_0, WITH169_tree);
            }
            pushFollow(FOLLOW_with_item_in_with_stmt4029);
            w=with_item();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, w.getTree());
            if (list_w==null) list_w=new ArrayList();
            list_w.add(w.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1224:25: ( options {greedy=true; } : COMMA w+= with_item )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==COMMA) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1224:49: COMMA w+= with_item
            	    {
            	    COMMA170=(Token)match(input,COMMA,FOLLOW_COMMA_in_with_stmt4039); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA170_tree = (PythonTree)adaptor.create(COMMA170);
            	    adaptor.addChild(root_0, COMMA170_tree);
            	    }
            	    pushFollow(FOLLOW_with_item_in_with_stmt4043);
            	    w=with_item();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, w.getTree());
            	    if (list_w==null) list_w=new ArrayList();
            	    list_w.add(w.getTree());


            	    }
            	    break;

            	default :
            	    break loop74;
                }
            } while (true);

            COLON171=(Token)match(input,COLON,FOLLOW_COLON_in_with_stmt4047); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON171_tree = (PythonTree)adaptor.create(COLON171);
            adaptor.addChild(root_0, COLON171_tree);
            }
            pushFollow(FOLLOW_suite_in_with_stmt4049);
            suite172=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, suite172.getTree());
            if ( state.backtracking==0 ) {

                        stype = actions.makeWith(WITH169, list_w, (suite172!=null?suite172.stypes:null));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "with_stmt"

    public static class with_item_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "with_item"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1231:1: with_item : test[expr_contextType.Load] ( AS expr[expr_contextType.Store] )? ;
    public final PythonParser.with_item_return with_item() throws RecognitionException {
        PythonParser.with_item_return retval = new PythonParser.with_item_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token AS174=null;
        PythonParser.test_return test173 = null;

        PythonParser.expr_return expr175 = null;


        PythonTree AS174_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1238:5: ( test[expr_contextType.Load] ( AS expr[expr_contextType.Store] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1238:7: test[expr_contextType.Load] ( AS expr[expr_contextType.Store] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_test_in_with_item4086);
            test173=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, test173.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1238:35: ( AS expr[expr_contextType.Store] )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==AS) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1238:36: AS expr[expr_contextType.Store]
                    {
                    AS174=(Token)match(input,AS,FOLLOW_AS_in_with_item4090); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    AS174_tree = (PythonTree)adaptor.create(AS174);
                    adaptor.addChild(root_0, AS174_tree);
                    }
                    pushFollow(FOLLOW_expr_in_with_item4092);
                    expr175=expr(expr_contextType.Store);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr175.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        expr item = actions.castExpr((test173!=null?((PythonTree)test173.tree):null));
                        expr var = null;
                        if ((expr175!=null?((Token)expr175.start):null) != null) {
                            var = actions.castExpr((expr175!=null?((PythonTree)expr175.tree):null));
                            actions.checkAssign(var);
                        }
                        stype = new With((test173!=null?((Token)test173.start):null), item, var, null);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "with_item"

    public static class except_clause_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "except_clause"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1251:1: except_clause : EXCEPT (t1= test[expr_contextType.Load] ( ( COMMA | AS ) t2= test[expr_contextType.Store] )? )? COLON suite[!$suite.isEmpty() && $suite::continueIllegal] ;
    public final PythonParser.except_clause_return except_clause() throws RecognitionException {
        PythonParser.except_clause_return retval = new PythonParser.except_clause_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token EXCEPT176=null;
        Token set177=null;
        Token COLON178=null;
        PythonParser.test_return t1 = null;

        PythonParser.test_return t2 = null;

        PythonParser.suite_return suite179 = null;


        PythonTree EXCEPT176_tree=null;
        PythonTree set177_tree=null;
        PythonTree COLON178_tree=null;


            excepthandler extype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1258:5: ( EXCEPT (t1= test[expr_contextType.Load] ( ( COMMA | AS ) t2= test[expr_contextType.Store] )? )? COLON suite[!$suite.isEmpty() && $suite::continueIllegal] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1258:7: EXCEPT (t1= test[expr_contextType.Load] ( ( COMMA | AS ) t2= test[expr_contextType.Store] )? )? COLON suite[!$suite.isEmpty() && $suite::continueIllegal]
            {
            root_0 = (PythonTree)adaptor.nil();

            EXCEPT176=(Token)match(input,EXCEPT,FOLLOW_EXCEPT_in_except_clause4131); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EXCEPT176_tree = (PythonTree)adaptor.create(EXCEPT176);
            adaptor.addChild(root_0, EXCEPT176_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1258:14: (t1= test[expr_contextType.Load] ( ( COMMA | AS ) t2= test[expr_contextType.Store] )? )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==NAME||LA77_0==NOT||LA77_0==LPAREN||(LA77_0>=PLUS && LA77_0<=MINUS)||(LA77_0>=TILDE && LA77_0<=LBRACK)||LA77_0==LCURLY||LA77_0==BACKQUOTE) ) {
                alt77=1;
            }
            else if ( (LA77_0==PRINT) && ((printFunction))) {
                alt77=1;
            }
            else if ( (LA77_0==LAMBDA||(LA77_0>=INT && LA77_0<=CONNECTTO)) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1258:15: t1= test[expr_contextType.Load] ( ( COMMA | AS ) t2= test[expr_contextType.Store] )?
                    {
                    pushFollow(FOLLOW_test_in_except_clause4136);
                    t1=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t1.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1258:46: ( ( COMMA | AS ) t2= test[expr_contextType.Store] )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==AS||LA76_0==COMMA) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1258:47: ( COMMA | AS ) t2= test[expr_contextType.Store]
                            {
                            set177=(Token)input.LT(1);
                            if ( input.LA(1)==AS||input.LA(1)==COMMA ) {
                                input.consume();
                                if ( state.backtracking==0 ) adaptor.addChild(root_0, (PythonTree)adaptor.create(set177));
                                state.errorRecovery=false;state.failed=false;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }

                            pushFollow(FOLLOW_test_in_except_clause4150);
                            t2=test(expr_contextType.Store);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, t2.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }

            COLON178=(Token)match(input,COLON,FOLLOW_COLON_in_except_clause4157); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON178_tree = (PythonTree)adaptor.create(COLON178);
            adaptor.addChild(root_0, COLON178_tree);
            }
            pushFollow(FOLLOW_suite_in_except_clause4159);
            suite179=suite(!suite_stack.isEmpty() && ((suite_scope)suite_stack.peek()).continueIllegal);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, suite179.getTree());
            if ( state.backtracking==0 ) {

                        extype = new ExceptHandler(EXCEPT176, actions.castExpr((t1!=null?((PythonTree)t1.tree):null)), actions.castExpr((t2!=null?((PythonTree)t2.tree):null)),
                            actions.castStmts((suite179!=null?suite179.stypes:null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = extype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "except_clause"

    protected static class suite_scope {
        boolean continueIllegal;
    }
    protected Stack suite_stack = new Stack();

    public static class suite_return extends ParserRuleReturnScope {
        public List stypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "suite"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1266:1: suite[boolean fromFinally] returns [List stypes] : ( simple_stmt | NEWLINE INDENT ( stmt )+ DEDENT );
    public final PythonParser.suite_return suite(boolean fromFinally) throws RecognitionException {
        suite_stack.push(new suite_scope());
        PythonParser.suite_return retval = new PythonParser.suite_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NEWLINE181=null;
        Token INDENT182=null;
        Token DEDENT184=null;
        PythonParser.simple_stmt_return simple_stmt180 = null;

        PythonParser.stmt_return stmt183 = null;


        PythonTree NEWLINE181_tree=null;
        PythonTree INDENT182_tree=null;
        PythonTree DEDENT184_tree=null;


            if (((suite_scope)suite_stack.peek()).continueIllegal || fromFinally) {
                ((suite_scope)suite_stack.peek()).continueIllegal = true;
            } else {
                ((suite_scope)suite_stack.peek()).continueIllegal = false;
            }
            retval.stypes = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1279:5: ( simple_stmt | NEWLINE INDENT ( stmt )+ DEDENT )
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==NAME||LA79_0==NOT||LA79_0==LPAREN||(LA79_0>=PLUS && LA79_0<=MINUS)||(LA79_0>=TILDE && LA79_0<=LBRACK)||LA79_0==LCURLY||LA79_0==BACKQUOTE) ) {
                alt79=1;
            }
            else if ( (LA79_0==PRINT) && (((printFunction)||(!printFunction)))) {
                alt79=1;
            }
            else if ( ((LA79_0>=ASSERT && LA79_0<=BREAK)||LA79_0==CONTINUE||LA79_0==DELETE||LA79_0==EXEC||LA79_0==FROM||LA79_0==GLOBAL||LA79_0==IMPORT||LA79_0==LAMBDA||(LA79_0>=PASS && LA79_0<=RETURN)||LA79_0==YIELD||(LA79_0>=INT && LA79_0<=CONNECTTO)) ) {
                alt79=1;
            }
            else if ( (LA79_0==NEWLINE) ) {
                alt79=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }
            switch (alt79) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1279:7: simple_stmt
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_simple_stmt_in_suite4205);
                    simple_stmt180=simple_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simple_stmt180.getTree());
                    if ( state.backtracking==0 ) {

                                retval.stypes = (simple_stmt180!=null?simple_stmt180.stypes:null);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1283:7: NEWLINE INDENT ( stmt )+ DEDENT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    NEWLINE181=(Token)match(input,NEWLINE,FOLLOW_NEWLINE_in_suite4221); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEWLINE181_tree = (PythonTree)adaptor.create(NEWLINE181);
                    adaptor.addChild(root_0, NEWLINE181_tree);
                    }
                    INDENT182=(Token)match(input,INDENT,FOLLOW_INDENT_in_suite4223); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INDENT182_tree = (PythonTree)adaptor.create(INDENT182);
                    adaptor.addChild(root_0, INDENT182_tree);
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1284:7: ( stmt )+
                    int cnt78=0;
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==NAME||LA78_0==NOT||LA78_0==LPAREN||(LA78_0>=PLUS && LA78_0<=MINUS)||(LA78_0>=TILDE && LA78_0<=LBRACK)||LA78_0==LCURLY||LA78_0==BACKQUOTE) ) {
                            alt78=1;
                        }
                        else if ( (LA78_0==PRINT) && (((printFunction)||(!printFunction)))) {
                            alt78=1;
                        }
                        else if ( ((LA78_0>=ASSERT && LA78_0<=DELETE)||LA78_0==EXEC||(LA78_0>=FROM && LA78_0<=IMPORT)||LA78_0==LAMBDA||(LA78_0>=PASS && LA78_0<=AT)||(LA78_0>=INT && LA78_0<=CONNECTTO)||LA78_0==PERSISTIT) ) {
                            alt78=1;
                        }


                        switch (alt78) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1284:8: stmt
                    	    {
                    	    pushFollow(FOLLOW_stmt_in_suite4232);
                    	    stmt183=stmt();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, stmt183.getTree());
                    	    if ( state.backtracking==0 ) {

                    	                 if ((stmt183!=null?stmt183.stypes:null) != null) {
                    	                     retval.stypes.addAll((stmt183!=null?stmt183.stypes:null));
                    	                 }
                    	             
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt78 >= 1 ) break loop78;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(78, input);
                                throw eee;
                        }
                        cnt78++;
                    } while (true);

                    DEDENT184=(Token)match(input,DEDENT,FOLLOW_DEDENT_in_suite4252); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DEDENT184_tree = (PythonTree)adaptor.create(DEDENT184);
                    adaptor.addChild(root_0, DEDENT184_tree);
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
            suite_stack.pop();
        }
        return retval;
    }
    // $ANTLR end "suite"

    public static class test_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "test"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1294:1: test[expr_contextType ctype] : (o1= or_test[ctype] ( ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load] | -> or_test ) | lambdef );
    public final PythonParser.test_return test(expr_contextType ctype) throws RecognitionException {
        PythonParser.test_return retval = new PythonParser.test_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token IF185=null;
        Token ORELSE186=null;
        PythonParser.or_test_return o1 = null;

        PythonParser.or_test_return o2 = null;

        PythonParser.test_return e = null;

        PythonParser.lambdef_return lambdef187 = null;


        PythonTree IF185_tree=null;
        PythonTree ORELSE186_tree=null;
        RewriteRuleTokenStream stream_IF=new RewriteRuleTokenStream(adaptor,"token IF");
        RewriteRuleTokenStream stream_ORELSE=new RewriteRuleTokenStream(adaptor,"token ORELSE");
        RewriteRuleSubtreeStream stream_or_test=new RewriteRuleSubtreeStream(adaptor,"rule or_test");
        RewriteRuleSubtreeStream stream_test=new RewriteRuleSubtreeStream(adaptor,"rule test");

            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1303:5: (o1= or_test[ctype] ( ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load] | -> or_test ) | lambdef )
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==NAME||LA81_0==NOT||LA81_0==LPAREN||(LA81_0>=PLUS && LA81_0<=MINUS)||(LA81_0>=TILDE && LA81_0<=LBRACK)||LA81_0==LCURLY||LA81_0==BACKQUOTE) ) {
                alt81=1;
            }
            else if ( (LA81_0==PRINT) && ((printFunction))) {
                alt81=1;
            }
            else if ( ((LA81_0>=INT && LA81_0<=CONNECTTO)) ) {
                alt81=1;
            }
            else if ( (LA81_0==LAMBDA) ) {
                alt81=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }
            switch (alt81) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1303:6: o1= or_test[ctype] ( ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load] | -> or_test )
                    {
                    pushFollow(FOLLOW_or_test_in_test4282);
                    o1=or_test(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_or_test.add(o1.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1304:7: ( ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load] | -> or_test )
                    int alt80=2;
                    alt80 = dfa80.predict(input);
                    switch (alt80) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1304:9: ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load]
                            {
                            IF185=(Token)match(input,IF,FOLLOW_IF_in_test4304); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_IF.add(IF185);

                            pushFollow(FOLLOW_or_test_in_test4308);
                            o2=or_test(ctype);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_or_test.add(o2.getTree());
                            ORELSE186=(Token)match(input,ORELSE,FOLLOW_ORELSE_in_test4311); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_ORELSE.add(ORELSE186);

                            pushFollow(FOLLOW_test_in_test4315);
                            e=test(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_test.add(e.getTree());
                            if ( state.backtracking==0 ) {

                                           etype = new IfExp((o1!=null?((Token)o1.start):null), actions.castExpr((o2!=null?((PythonTree)o2.tree):null)), actions.castExpr((o1!=null?((PythonTree)o1.tree):null)), actions.castExpr((e!=null?((PythonTree)e.tree):null)));
                                       
                            }

                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1309:6: 
                            {

                            // AST REWRITE
                            // elements: or_test
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (PythonTree)adaptor.nil();
                            // 1309:6: -> or_test
                            {
                                adaptor.addChild(root_0, stream_or_test.nextTree());

                            }

                            retval.tree = root_0;}
                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1311:7: lambdef
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_lambdef_in_test4360);
                    lambdef187=lambdef();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lambdef187.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 if (etype != null) {
                     retval.tree = etype;
                 }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "test"

    public static class or_test_return extends ParserRuleReturnScope {
        public Token leftTok;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "or_test"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1315:1: or_test[expr_contextType ctype] returns [Token leftTok] : left= and_test[ctype] ( (or= OR right+= and_test[ctype] )+ | -> $left) ;
    public final PythonParser.or_test_return or_test(expr_contextType ctype) throws RecognitionException {
        PythonParser.or_test_return retval = new PythonParser.or_test_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token or=null;
        List list_right=null;
        PythonParser.and_test_return left = null;

        PythonParser.and_test_return right = null;
         right = null;
        PythonTree or_tree=null;
        RewriteRuleTokenStream stream_OR=new RewriteRuleTokenStream(adaptor,"token OR");
        RewriteRuleSubtreeStream stream_and_test=new RewriteRuleSubtreeStream(adaptor,"rule and_test");
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1326:5: (left= and_test[ctype] ( (or= OR right+= and_test[ctype] )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1326:7: left= and_test[ctype] ( (or= OR right+= and_test[ctype] )+ | -> $left)
            {
            pushFollow(FOLLOW_and_test_in_or_test4395);
            left=and_test(ctype);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_and_test.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1327:9: ( (or= OR right+= and_test[ctype] )+ | -> $left)
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==OR) ) {
                alt83=1;
            }
            else if ( (LA83_0==EOF||LA83_0==NEWLINE||LA83_0==AS||LA83_0==FOR||LA83_0==IF||LA83_0==ORELSE||(LA83_0>=RPAREN && LA83_0<=COMMA)||(LA83_0>=SEMI && LA83_0<=DOUBLESLASHEQUAL)||LA83_0==RBRACK||(LA83_0>=RCURLY && LA83_0<=BACKQUOTE)) ) {
                alt83=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }
            switch (alt83) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1327:11: (or= OR right+= and_test[ctype] )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1327:11: (or= OR right+= and_test[ctype] )+
                    int cnt82=0;
                    loop82:
                    do {
                        int alt82=2;
                        int LA82_0 = input.LA(1);

                        if ( (LA82_0==OR) ) {
                            alt82=1;
                        }


                        switch (alt82) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1327:12: or= OR right+= and_test[ctype]
                    	    {
                    	    or=(Token)match(input,OR,FOLLOW_OR_in_or_test4411); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_OR.add(or);

                    	    pushFollow(FOLLOW_and_test_in_or_test4415);
                    	    right=and_test(ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_and_test.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt82 >= 1 ) break loop82;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(82, input);
                                throw eee;
                        }
                        cnt82++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1330:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1330:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (or != null) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.leftTok:null) != null) {
                          tok = (left!=null?left.leftTok:null);
                      }
                      retval.tree = actions.makeBoolOp(tok, (left!=null?((PythonTree)left.tree):null), boolopType.Or, list_right);
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "or_test"

    public static class and_test_return extends ParserRuleReturnScope {
        public Token leftTok;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and_test"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1335:1: and_test[expr_contextType ctype] returns [Token leftTok] : left= not_test[ctype] ( (and= AND right+= not_test[ctype] )+ | -> $left) ;
    public final PythonParser.and_test_return and_test(expr_contextType ctype) throws RecognitionException {
        PythonParser.and_test_return retval = new PythonParser.and_test_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token and=null;
        List list_right=null;
        PythonParser.not_test_return left = null;

        PythonParser.not_test_return right = null;
         right = null;
        PythonTree and_tree=null;
        RewriteRuleTokenStream stream_AND=new RewriteRuleTokenStream(adaptor,"token AND");
        RewriteRuleSubtreeStream stream_not_test=new RewriteRuleSubtreeStream(adaptor,"rule not_test");
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1346:5: (left= not_test[ctype] ( (and= AND right+= not_test[ctype] )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1346:7: left= not_test[ctype] ( (and= AND right+= not_test[ctype] )+ | -> $left)
            {
            pushFollow(FOLLOW_not_test_in_and_test4496);
            left=not_test(ctype);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_not_test.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1347:9: ( (and= AND right+= not_test[ctype] )+ | -> $left)
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==AND) ) {
                alt85=1;
            }
            else if ( (LA85_0==EOF||LA85_0==NEWLINE||LA85_0==AS||LA85_0==FOR||LA85_0==IF||(LA85_0>=OR && LA85_0<=ORELSE)||(LA85_0>=RPAREN && LA85_0<=COMMA)||(LA85_0>=SEMI && LA85_0<=DOUBLESLASHEQUAL)||LA85_0==RBRACK||(LA85_0>=RCURLY && LA85_0<=BACKQUOTE)) ) {
                alt85=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 85, 0, input);

                throw nvae;
            }
            switch (alt85) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1347:11: (and= AND right+= not_test[ctype] )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1347:11: (and= AND right+= not_test[ctype] )+
                    int cnt84=0;
                    loop84:
                    do {
                        int alt84=2;
                        int LA84_0 = input.LA(1);

                        if ( (LA84_0==AND) ) {
                            alt84=1;
                        }


                        switch (alt84) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1347:12: and= AND right+= not_test[ctype]
                    	    {
                    	    and=(Token)match(input,AND,FOLLOW_AND_in_and_test4512); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_AND.add(and);

                    	    pushFollow(FOLLOW_not_test_in_and_test4516);
                    	    right=not_test(ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_not_test.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt84 >= 1 ) break loop84;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(84, input);
                                throw eee;
                        }
                        cnt84++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1350:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1350:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (and != null) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.leftTok:null) != null) {
                          tok = (left!=null?left.leftTok:null);
                      }
                      retval.tree = actions.makeBoolOp(tok, (left!=null?((PythonTree)left.tree):null), boolopType.And, list_right);
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "and_test"

    public static class not_test_return extends ParserRuleReturnScope {
        public Token leftTok;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "not_test"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1355:1: not_test[expr_contextType ctype] returns [Token leftTok] : ( NOT nt= not_test[ctype] | comparison[ctype] );
    public final PythonParser.not_test_return not_test(expr_contextType ctype) throws RecognitionException {
        PythonParser.not_test_return retval = new PythonParser.not_test_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token NOT188=null;
        PythonParser.not_test_return nt = null;

        PythonParser.comparison_return comparison189 = null;


        PythonTree NOT188_tree=null;


            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1365:5: ( NOT nt= not_test[ctype] | comparison[ctype] )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==NOT) ) {
                alt86=1;
            }
            else if ( (LA86_0==NAME||LA86_0==LPAREN||(LA86_0>=PLUS && LA86_0<=MINUS)||(LA86_0>=TILDE && LA86_0<=LBRACK)||LA86_0==LCURLY||LA86_0==BACKQUOTE) ) {
                alt86=2;
            }
            else if ( (LA86_0==PRINT) && ((printFunction))) {
                alt86=2;
            }
            else if ( ((LA86_0>=INT && LA86_0<=CONNECTTO)) ) {
                alt86=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1365:7: NOT nt= not_test[ctype]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    NOT188=(Token)match(input,NOT,FOLLOW_NOT_in_not_test4600); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT188_tree = (PythonTree)adaptor.create(NOT188);
                    adaptor.addChild(root_0, NOT188_tree);
                    }
                    pushFollow(FOLLOW_not_test_in_not_test4604);
                    nt=not_test(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, nt.getTree());
                    if ( state.backtracking==0 ) {

                                etype = new UnaryOp(NOT188, unaryopType.Not, actions.castExpr((nt!=null?((PythonTree)nt.tree):null)));
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1369:7: comparison[ctype]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_comparison_in_not_test4621);
                    comparison189=comparison(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comparison189.getTree());
                    if ( state.backtracking==0 ) {

                                retval.leftTok = (comparison189!=null?comparison189.leftTok:null);
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 if (etype != null) {
                     retval.tree = etype;
                 }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "not_test"

    public static class comparison_return extends ParserRuleReturnScope {
        public Token leftTok;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comparison"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1376:1: comparison[expr_contextType ctype] returns [Token leftTok] : left= expr[ctype] ( ( comp_op right+= expr[ctype] )+ | -> $left) ;
    public final PythonParser.comparison_return comparison(expr_contextType ctype) throws RecognitionException {
        PythonParser.comparison_return retval = new PythonParser.comparison_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        List list_right=null;
        PythonParser.expr_return left = null;

        PythonParser.comp_op_return comp_op190 = null;

        PythonParser.expr_return right = null;
         right = null;
        RewriteRuleSubtreeStream stream_comp_op=new RewriteRuleSubtreeStream(adaptor,"rule comp_op");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            List cmps = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1388:5: (left= expr[ctype] ( ( comp_op right+= expr[ctype] )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1388:7: left= expr[ctype] ( ( comp_op right+= expr[ctype] )+ | -> $left)
            {
            pushFollow(FOLLOW_expr_in_comparison4670);
            left=expr(ctype);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_expr.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1389:8: ( ( comp_op right+= expr[ctype] )+ | -> $left)
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( ((LA88_0>=IN && LA88_0<=IS)||LA88_0==NOT||(LA88_0>=LESS && LA88_0<=NOTEQUAL)) ) {
                alt88=1;
            }
            else if ( (LA88_0==EOF||LA88_0==NEWLINE||(LA88_0>=AND && LA88_0<=AS)||LA88_0==FOR||LA88_0==IF||(LA88_0>=OR && LA88_0<=ORELSE)||(LA88_0>=RPAREN && LA88_0<=COMMA)||(LA88_0>=SEMI && LA88_0<=DOUBLESLASHEQUAL)||LA88_0==RBRACK||(LA88_0>=RCURLY && LA88_0<=BACKQUOTE)) ) {
                alt88=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1389:10: ( comp_op right+= expr[ctype] )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1389:10: ( comp_op right+= expr[ctype] )+
                    int cnt87=0;
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( ((LA87_0>=IN && LA87_0<=IS)||LA87_0==NOT||(LA87_0>=LESS && LA87_0<=NOTEQUAL)) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1389:12: comp_op right+= expr[ctype]
                    	    {
                    	    pushFollow(FOLLOW_comp_op_in_comparison4684);
                    	    comp_op190=comp_op();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_comp_op.add(comp_op190.getTree());
                    	    pushFollow(FOLLOW_expr_in_comparison4688);
                    	    right=expr(ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_expr.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());

                    	    if ( state.backtracking==0 ) {

                    	                     cmps.add((comp_op190!=null?comp_op190.op:null));
                    	                 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt87 >= 1 ) break loop87;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(87, input);
                                throw eee;
                        }
                        cnt87++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1395:7: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1395:7: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.leftTok = (left!=null?left.leftTok:null);
                  if (!cmps.isEmpty()) {
                      retval.tree = new Compare((left!=null?((Token)left.start):null), actions.castExpr((left!=null?((PythonTree)left.tree):null)), actions.makeCmpOps(cmps),
                          actions.castExprs(list_right));
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comparison"

    public static class comp_op_return extends ParserRuleReturnScope {
        public cmpopType op;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comp_op"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1400:1: comp_op returns [cmpopType op] : ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | IN | NOT IN | IS | IS NOT );
    public final PythonParser.comp_op_return comp_op() throws RecognitionException {
        PythonParser.comp_op_return retval = new PythonParser.comp_op_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token LESS191=null;
        Token GREATER192=null;
        Token EQUAL193=null;
        Token GREATEREQUAL194=null;
        Token LESSEQUAL195=null;
        Token ALT_NOTEQUAL196=null;
        Token NOTEQUAL197=null;
        Token IN198=null;
        Token NOT199=null;
        Token IN200=null;
        Token IS201=null;
        Token IS202=null;
        Token NOT203=null;

        PythonTree LESS191_tree=null;
        PythonTree GREATER192_tree=null;
        PythonTree EQUAL193_tree=null;
        PythonTree GREATEREQUAL194_tree=null;
        PythonTree LESSEQUAL195_tree=null;
        PythonTree ALT_NOTEQUAL196_tree=null;
        PythonTree NOTEQUAL197_tree=null;
        PythonTree IN198_tree=null;
        PythonTree NOT199_tree=null;
        PythonTree IN200_tree=null;
        PythonTree IS201_tree=null;
        PythonTree IS202_tree=null;
        PythonTree NOT203_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1402:5: ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | IN | NOT IN | IS | IS NOT )
            int alt89=11;
            alt89 = dfa89.predict(input);
            switch (alt89) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1402:7: LESS
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LESS191=(Token)match(input,LESS,FOLLOW_LESS_in_comp_op4769); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS191_tree = (PythonTree)adaptor.create(LESS191);
                    adaptor.addChild(root_0, LESS191_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.Lt;
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1406:7: GREATER
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    GREATER192=(Token)match(input,GREATER,FOLLOW_GREATER_in_comp_op4785); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GREATER192_tree = (PythonTree)adaptor.create(GREATER192);
                    adaptor.addChild(root_0, GREATER192_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.Gt;
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1410:7: EQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    EQUAL193=(Token)match(input,EQUAL,FOLLOW_EQUAL_in_comp_op4801); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQUAL193_tree = (PythonTree)adaptor.create(EQUAL193);
                    adaptor.addChild(root_0, EQUAL193_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.Eq;
                            
                    }

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1414:7: GREATEREQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    GREATEREQUAL194=(Token)match(input,GREATEREQUAL,FOLLOW_GREATEREQUAL_in_comp_op4817); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GREATEREQUAL194_tree = (PythonTree)adaptor.create(GREATEREQUAL194);
                    adaptor.addChild(root_0, GREATEREQUAL194_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.GtE;
                            
                    }

                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1418:7: LESSEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LESSEQUAL195=(Token)match(input,LESSEQUAL,FOLLOW_LESSEQUAL_in_comp_op4833); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESSEQUAL195_tree = (PythonTree)adaptor.create(LESSEQUAL195);
                    adaptor.addChild(root_0, LESSEQUAL195_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.LtE;
                            
                    }

                    }
                    break;
                case 6 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1422:7: ALT_NOTEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    ALT_NOTEQUAL196=(Token)match(input,ALT_NOTEQUAL,FOLLOW_ALT_NOTEQUAL_in_comp_op4849); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ALT_NOTEQUAL196_tree = (PythonTree)adaptor.create(ALT_NOTEQUAL196);
                    adaptor.addChild(root_0, ALT_NOTEQUAL196_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.NotEq;
                            
                    }

                    }
                    break;
                case 7 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1426:7: NOTEQUAL
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    NOTEQUAL197=(Token)match(input,NOTEQUAL,FOLLOW_NOTEQUAL_in_comp_op4865); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOTEQUAL197_tree = (PythonTree)adaptor.create(NOTEQUAL197);
                    adaptor.addChild(root_0, NOTEQUAL197_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.NotEq;
                            
                    }

                    }
                    break;
                case 8 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1430:7: IN
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    IN198=(Token)match(input,IN,FOLLOW_IN_in_comp_op4881); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IN198_tree = (PythonTree)adaptor.create(IN198);
                    adaptor.addChild(root_0, IN198_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.In;
                            
                    }

                    }
                    break;
                case 9 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1434:7: NOT IN
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    NOT199=(Token)match(input,NOT,FOLLOW_NOT_in_comp_op4897); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT199_tree = (PythonTree)adaptor.create(NOT199);
                    adaptor.addChild(root_0, NOT199_tree);
                    }
                    IN200=(Token)match(input,IN,FOLLOW_IN_in_comp_op4899); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IN200_tree = (PythonTree)adaptor.create(IN200);
                    adaptor.addChild(root_0, IN200_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.NotIn;
                            
                    }

                    }
                    break;
                case 10 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1438:7: IS
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    IS201=(Token)match(input,IS,FOLLOW_IS_in_comp_op4915); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IS201_tree = (PythonTree)adaptor.create(IS201);
                    adaptor.addChild(root_0, IS201_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.Is;
                            
                    }

                    }
                    break;
                case 11 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1442:7: IS NOT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    IS202=(Token)match(input,IS,FOLLOW_IS_in_comp_op4931); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IS202_tree = (PythonTree)adaptor.create(IS202);
                    adaptor.addChild(root_0, IS202_tree);
                    }
                    NOT203=(Token)match(input,NOT,FOLLOW_NOT_in_comp_op4933); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT203_tree = (PythonTree)adaptor.create(NOT203);
                    adaptor.addChild(root_0, NOT203_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = cmpopType.IsNot;
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comp_op"

    protected static class expr_scope {
        expr_contextType ctype;
    }
    protected Stack expr_stack = new Stack();

    public static class expr_return extends ParserRuleReturnScope {
        public Token leftTok;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1449:1: expr[expr_contextType ect] returns [Token leftTok] : left= xor_expr ( (op= VBAR right+= xor_expr )+ | -> $left) ;
    public final PythonParser.expr_return expr(expr_contextType ect) throws RecognitionException {
        expr_stack.push(new expr_scope());
        PythonParser.expr_return retval = new PythonParser.expr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token op=null;
        List list_right=null;
        PythonParser.xor_expr_return left = null;

        PythonParser.xor_expr_return right = null;
         right = null;
        PythonTree op_tree=null;
        RewriteRuleTokenStream stream_VBAR=new RewriteRuleTokenStream(adaptor,"token VBAR");
        RewriteRuleSubtreeStream stream_xor_expr=new RewriteRuleSubtreeStream(adaptor,"rule xor_expr");

            ((expr_scope)expr_stack.peek()).ctype = ect;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1467:5: (left= xor_expr ( (op= VBAR right+= xor_expr )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1467:7: left= xor_expr ( (op= VBAR right+= xor_expr )+ | -> $left)
            {
            pushFollow(FOLLOW_xor_expr_in_expr4985);
            left=xor_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_xor_expr.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1468:9: ( (op= VBAR right+= xor_expr )+ | -> $left)
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==VBAR) ) {
                alt91=1;
            }
            else if ( (LA91_0==EOF||LA91_0==NEWLINE||LA91_0==DOT||(LA91_0>=AND && LA91_0<=AS)||LA91_0==FOR||LA91_0==IF||(LA91_0>=IN && LA91_0<=IS)||(LA91_0>=NOT && LA91_0<=ORELSE)||(LA91_0>=LPAREN && LA91_0<=NOTEQUAL)||(LA91_0>=CIRCUMFLEX && LA91_0<=DOUBLESLASH)||(LA91_0>=LBRACK && LA91_0<=RBRACK)||(LA91_0>=RCURLY && LA91_0<=BACKQUOTE)||LA91_0==STRING) ) {
                alt91=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }
            switch (alt91) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1468:11: (op= VBAR right+= xor_expr )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1468:11: (op= VBAR right+= xor_expr )+
                    int cnt90=0;
                    loop90:
                    do {
                        int alt90=2;
                        int LA90_0 = input.LA(1);

                        if ( (LA90_0==VBAR) ) {
                            alt90=1;
                        }


                        switch (alt90) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1468:12: op= VBAR right+= xor_expr
                    	    {
                    	    op=(Token)match(input,VBAR,FOLLOW_VBAR_in_expr5000); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_VBAR.add(op);

                    	    pushFollow(FOLLOW_xor_expr_in_expr5004);
                    	    right=xor_expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_xor_expr.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt90 >= 1 ) break loop90;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(90, input);
                                throw eee;
                        }
                        cnt90++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1471:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1471:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.leftTok = (left!=null?left.lparen:null);
                  if (op != null) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.lparen:null) != null) {
                          tok = (left!=null?left.lparen:null);
                      }
                      retval.tree = actions.makeBinOp(tok, (left!=null?((PythonTree)left.tree):null), operatorType.BitOr, list_right);
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
            expr_stack.pop();
        }
        return retval;
    }
    // $ANTLR end "expr"

    public static class xor_expr_return extends ParserRuleReturnScope {
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "xor_expr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1477:1: xor_expr returns [Token lparen = null] : left= and_expr ( (op= CIRCUMFLEX right+= and_expr )+ | -> $left) ;
    public final PythonParser.xor_expr_return xor_expr() throws RecognitionException {
        PythonParser.xor_expr_return retval = new PythonParser.xor_expr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token op=null;
        List list_right=null;
        PythonParser.and_expr_return left = null;

        PythonParser.and_expr_return right = null;
         right = null;
        PythonTree op_tree=null;
        RewriteRuleTokenStream stream_CIRCUMFLEX=new RewriteRuleTokenStream(adaptor,"token CIRCUMFLEX");
        RewriteRuleSubtreeStream stream_and_expr=new RewriteRuleSubtreeStream(adaptor,"rule and_expr");
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1489:5: (left= and_expr ( (op= CIRCUMFLEX right+= and_expr )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1489:7: left= and_expr ( (op= CIRCUMFLEX right+= and_expr )+ | -> $left)
            {
            pushFollow(FOLLOW_and_expr_in_xor_expr5083);
            left=and_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_and_expr.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1490:9: ( (op= CIRCUMFLEX right+= and_expr )+ | -> $left)
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==CIRCUMFLEX) ) {
                alt93=1;
            }
            else if ( (LA93_0==EOF||LA93_0==NEWLINE||LA93_0==DOT||(LA93_0>=AND && LA93_0<=AS)||LA93_0==FOR||LA93_0==IF||(LA93_0>=IN && LA93_0<=IS)||(LA93_0>=NOT && LA93_0<=ORELSE)||(LA93_0>=LPAREN && LA93_0<=VBAR)||(LA93_0>=AMPER && LA93_0<=DOUBLESLASH)||(LA93_0>=LBRACK && LA93_0<=RBRACK)||(LA93_0>=RCURLY && LA93_0<=BACKQUOTE)||LA93_0==STRING) ) {
                alt93=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }
            switch (alt93) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1490:11: (op= CIRCUMFLEX right+= and_expr )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1490:11: (op= CIRCUMFLEX right+= and_expr )+
                    int cnt92=0;
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( (LA92_0==CIRCUMFLEX) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1490:12: op= CIRCUMFLEX right+= and_expr
                    	    {
                    	    op=(Token)match(input,CIRCUMFLEX,FOLLOW_CIRCUMFLEX_in_xor_expr5098); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_CIRCUMFLEX.add(op);

                    	    pushFollow(FOLLOW_and_expr_in_xor_expr5102);
                    	    right=and_expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_and_expr.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt92 >= 1 ) break loop92;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(92, input);
                                throw eee;
                        }
                        cnt92++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1493:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1493:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (op != null) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.lparen:null) != null) {
                          tok = (left!=null?left.lparen:null);
                      }
                      retval.tree = actions.makeBinOp(tok, (left!=null?((PythonTree)left.tree):null), operatorType.BitXor, list_right);
                  }
                  retval.lparen = (left!=null?left.lparen:null);

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "xor_expr"

    public static class and_expr_return extends ParserRuleReturnScope {
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and_expr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1498:1: and_expr returns [Token lparen = null] : left= shift_expr ( (op= AMPER right+= shift_expr )+ | -> $left) ;
    public final PythonParser.and_expr_return and_expr() throws RecognitionException {
        PythonParser.and_expr_return retval = new PythonParser.and_expr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token op=null;
        List list_right=null;
        PythonParser.shift_expr_return left = null;

        PythonParser.shift_expr_return right = null;
         right = null;
        PythonTree op_tree=null;
        RewriteRuleTokenStream stream_AMPER=new RewriteRuleTokenStream(adaptor,"token AMPER");
        RewriteRuleSubtreeStream stream_shift_expr=new RewriteRuleSubtreeStream(adaptor,"rule shift_expr");
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1510:5: (left= shift_expr ( (op= AMPER right+= shift_expr )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1510:7: left= shift_expr ( (op= AMPER right+= shift_expr )+ | -> $left)
            {
            pushFollow(FOLLOW_shift_expr_in_and_expr5180);
            left=shift_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_shift_expr.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1511:9: ( (op= AMPER right+= shift_expr )+ | -> $left)
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==AMPER) ) {
                alt95=1;
            }
            else if ( (LA95_0==EOF||LA95_0==NEWLINE||LA95_0==DOT||(LA95_0>=AND && LA95_0<=AS)||LA95_0==FOR||LA95_0==IF||(LA95_0>=IN && LA95_0<=IS)||(LA95_0>=NOT && LA95_0<=ORELSE)||(LA95_0>=LPAREN && LA95_0<=CIRCUMFLEX)||(LA95_0>=LEFTSHIFT && LA95_0<=DOUBLESLASH)||(LA95_0>=LBRACK && LA95_0<=RBRACK)||(LA95_0>=RCURLY && LA95_0<=BACKQUOTE)||LA95_0==STRING) ) {
                alt95=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1511:11: (op= AMPER right+= shift_expr )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1511:11: (op= AMPER right+= shift_expr )+
                    int cnt94=0;
                    loop94:
                    do {
                        int alt94=2;
                        int LA94_0 = input.LA(1);

                        if ( (LA94_0==AMPER) ) {
                            alt94=1;
                        }


                        switch (alt94) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1511:12: op= AMPER right+= shift_expr
                    	    {
                    	    op=(Token)match(input,AMPER,FOLLOW_AMPER_in_and_expr5195); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_AMPER.add(op);

                    	    pushFollow(FOLLOW_shift_expr_in_and_expr5199);
                    	    right=shift_expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_shift_expr.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt94 >= 1 ) break loop94;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(94, input);
                                throw eee;
                        }
                        cnt94++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1514:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1514:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (op != null) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.lparen:null) != null) {
                          tok = (left!=null?left.lparen:null);
                      }
                      retval.tree = actions.makeBinOp(tok, (left!=null?((PythonTree)left.tree):null), operatorType.BitAnd, list_right);
                  }
                  retval.lparen = (left!=null?left.lparen:null);

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "and_expr"

    public static class shift_expr_return extends ParserRuleReturnScope {
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "shift_expr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1519:1: shift_expr returns [Token lparen = null] : left= arith_expr ( ( shift_op right+= arith_expr )+ | -> $left) ;
    public final PythonParser.shift_expr_return shift_expr() throws RecognitionException {
        PythonParser.shift_expr_return retval = new PythonParser.shift_expr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        List list_right=null;
        PythonParser.arith_expr_return left = null;

        PythonParser.shift_op_return shift_op204 = null;

        PythonParser.arith_expr_return right = null;
         right = null;
        RewriteRuleSubtreeStream stream_shift_op=new RewriteRuleSubtreeStream(adaptor,"rule shift_op");
        RewriteRuleSubtreeStream stream_arith_expr=new RewriteRuleSubtreeStream(adaptor,"rule arith_expr");

            List ops = new ArrayList();
            List toks = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1535:5: (left= arith_expr ( ( shift_op right+= arith_expr )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1535:7: left= arith_expr ( ( shift_op right+= arith_expr )+ | -> $left)
            {
            pushFollow(FOLLOW_arith_expr_in_shift_expr5282);
            left=arith_expr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_arith_expr.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1536:9: ( ( shift_op right+= arith_expr )+ | -> $left)
            int alt97=2;
            switch ( input.LA(1) ) {
            case LEFTSHIFT:
                {
                alt97=1;
                }
                break;
            case RIGHTSHIFT:
                {
                alt97=1;
                }
                break;
            case EOF:
            case NEWLINE:
            case DOT:
            case AND:
            case AS:
            case FOR:
            case IF:
            case IN:
            case IS:
            case NOT:
            case OR:
            case ORELSE:
            case LPAREN:
            case RPAREN:
            case COLON:
            case ASSIGN:
            case COMMA:
            case STAR:
            case DOUBLESTAR:
            case SEMI:
            case PLUSEQUAL:
            case MINUSEQUAL:
            case STAREQUAL:
            case SLASHEQUAL:
            case PERCENTEQUAL:
            case AMPEREQUAL:
            case VBAREQUAL:
            case CIRCUMFLEXEQUAL:
            case LEFTSHIFTEQUAL:
            case RIGHTSHIFTEQUAL:
            case DOUBLESTAREQUAL:
            case DOUBLESLASHEQUAL:
            case LESS:
            case GREATER:
            case EQUAL:
            case GREATEREQUAL:
            case LESSEQUAL:
            case ALT_NOTEQUAL:
            case NOTEQUAL:
            case VBAR:
            case CIRCUMFLEX:
            case AMPER:
            case PLUS:
            case MINUS:
            case SLASH:
            case PERCENT:
            case DOUBLESLASH:
            case LBRACK:
            case RBRACK:
            case RCURLY:
            case BACKQUOTE:
            case STRING:
                {
                alt97=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1536:11: ( shift_op right+= arith_expr )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1536:11: ( shift_op right+= arith_expr )+
                    int cnt96=0;
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==LEFTSHIFT) ) {
                            alt96=1;
                        }
                        else if ( (LA96_0==RIGHTSHIFT) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1536:13: shift_op right+= arith_expr
                    	    {
                    	    pushFollow(FOLLOW_shift_op_in_shift_expr5296);
                    	    shift_op204=shift_op();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_shift_op.add(shift_op204.getTree());
                    	    pushFollow(FOLLOW_arith_expr_in_shift_expr5300);
                    	    right=arith_expr();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_arith_expr.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());

                    	    if ( state.backtracking==0 ) {

                    	                      ops.add((shift_op204!=null?shift_op204.op:null));
                    	                      toks.add((shift_op204!=null?((Token)shift_op204.start):null));
                    	                  
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt96 >= 1 ) break loop96;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(96, input);
                                throw eee;
                        }
                        cnt96++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1543:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1543:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (!ops.isEmpty()) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.lparen:null) != null) {
                          tok = (left!=null?left.lparen:null);
                      }
                      retval.tree = actions.makeBinOp(tok, (left!=null?((PythonTree)left.tree):null), ops, list_right, toks);
                  }
                  retval.lparen = (left!=null?left.lparen:null);

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "shift_expr"

    public static class batch_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "batch_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1547:1: batch_stmt : BATCH n= NAME ( IN | FROM ) s= NAME COLON s1= suite[false] ;
    public final PythonParser.batch_stmt_return batch_stmt() throws RecognitionException {
        PythonParser.batch_stmt_return retval = new PythonParser.batch_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token n=null;
        Token s=null;
        Token BATCH205=null;
        Token set206=null;
        Token COLON207=null;
        PythonParser.suite_return s1 = null;


        PythonTree n_tree=null;
        PythonTree s_tree=null;
        PythonTree BATCH205_tree=null;
        PythonTree set206_tree=null;
        PythonTree COLON207_tree=null;


            stmt stype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1553:3: ( BATCH n= NAME ( IN | FROM ) s= NAME COLON s1= suite[false] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1553:5: BATCH n= NAME ( IN | FROM ) s= NAME COLON s1= suite[false]
            {
            root_0 = (PythonTree)adaptor.nil();

            BATCH205=(Token)match(input,BATCH,FOLLOW_BATCH_in_batch_stmt5382); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            BATCH205_tree = (PythonTree)adaptor.create(BATCH205);
            adaptor.addChild(root_0, BATCH205_tree);
            }
            n=(Token)match(input,NAME,FOLLOW_NAME_in_batch_stmt5386); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (PythonTree)adaptor.create(n);
            adaptor.addChild(root_0, n_tree);
            }
            set206=(Token)input.LT(1);
            if ( input.LA(1)==FROM||input.LA(1)==IN ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (PythonTree)adaptor.create(set206));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            s=(Token)match(input,NAME,FOLLOW_NAME_in_batch_stmt5396); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            s_tree = (PythonTree)adaptor.create(s);
            adaptor.addChild(root_0, s_tree);
            }
            COLON207=(Token)match(input,COLON,FOLLOW_COLON_in_batch_stmt5398); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON207_tree = (PythonTree)adaptor.create(COLON207);
            adaptor.addChild(root_0, COLON207_tree);
            }
            pushFollow(FOLLOW_suite_in_batch_stmt5402);
            s1=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, s1.getTree());
            if ( state.backtracking==0 ) {

                      stype = new Batch(BATCH205, n.getText(), s.getText(), (s1!=null?s1.stypes:null));
                  
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "batch_stmt"

    public static class shift_op_return extends ParserRuleReturnScope {
        public operatorType op;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "shift_op"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1573:1: shift_op returns [operatorType op] : ( LEFTSHIFT | RIGHTSHIFT );
    public final PythonParser.shift_op_return shift_op() throws RecognitionException {
        PythonParser.shift_op_return retval = new PythonParser.shift_op_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token LEFTSHIFT208=null;
        Token RIGHTSHIFT209=null;

        PythonTree LEFTSHIFT208_tree=null;
        PythonTree RIGHTSHIFT209_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1575:5: ( LEFTSHIFT | RIGHTSHIFT )
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==LEFTSHIFT) ) {
                alt98=1;
            }
            else if ( (LA98_0==RIGHTSHIFT) ) {
                alt98=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }
            switch (alt98) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1575:7: LEFTSHIFT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LEFTSHIFT208=(Token)match(input,LEFTSHIFT,FOLLOW_LEFTSHIFT_in_shift_op5435); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LEFTSHIFT208_tree = (PythonTree)adaptor.create(LEFTSHIFT208);
                    adaptor.addChild(root_0, LEFTSHIFT208_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.LShift;
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1579:7: RIGHTSHIFT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    RIGHTSHIFT209=(Token)match(input,RIGHTSHIFT,FOLLOW_RIGHTSHIFT_in_shift_op5451); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RIGHTSHIFT209_tree = (PythonTree)adaptor.create(RIGHTSHIFT209);
                    adaptor.addChild(root_0, RIGHTSHIFT209_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.RShift;
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "shift_op"

    public static class arith_expr_return extends ParserRuleReturnScope {
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arith_expr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1586:1: arith_expr returns [Token lparen = null] : left= term ( ( arith_op right+= term )+ | -> $left) ;
    public final PythonParser.arith_expr_return arith_expr() throws RecognitionException {
        PythonParser.arith_expr_return retval = new PythonParser.arith_expr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        List list_right=null;
        PythonParser.term_return left = null;

        PythonParser.arith_op_return arith_op210 = null;

        PythonParser.term_return right = null;
         right = null;
        RewriteRuleSubtreeStream stream_term=new RewriteRuleSubtreeStream(adaptor,"rule term");
        RewriteRuleSubtreeStream stream_arith_op=new RewriteRuleSubtreeStream(adaptor,"rule arith_op");

            List ops = new ArrayList();
            List toks = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1602:5: (left= term ( ( arith_op right+= term )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1602:7: left= term ( ( arith_op right+= term )+ | -> $left)
            {
            pushFollow(FOLLOW_term_in_arith_expr5497);
            left=term();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_term.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1603:9: ( ( arith_op right+= term )+ | -> $left)
            int alt100=2;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt100=1;
                }
                break;
            case MINUS:
                {
                alt100=1;
                }
                break;
            case EOF:
            case NEWLINE:
            case DOT:
            case AND:
            case AS:
            case FOR:
            case IF:
            case IN:
            case IS:
            case NOT:
            case OR:
            case ORELSE:
            case LPAREN:
            case RPAREN:
            case COLON:
            case ASSIGN:
            case COMMA:
            case STAR:
            case DOUBLESTAR:
            case SEMI:
            case PLUSEQUAL:
            case MINUSEQUAL:
            case STAREQUAL:
            case SLASHEQUAL:
            case PERCENTEQUAL:
            case AMPEREQUAL:
            case VBAREQUAL:
            case CIRCUMFLEXEQUAL:
            case LEFTSHIFTEQUAL:
            case RIGHTSHIFTEQUAL:
            case DOUBLESTAREQUAL:
            case DOUBLESLASHEQUAL:
            case RIGHTSHIFT:
            case LESS:
            case GREATER:
            case EQUAL:
            case GREATEREQUAL:
            case LESSEQUAL:
            case ALT_NOTEQUAL:
            case NOTEQUAL:
            case VBAR:
            case CIRCUMFLEX:
            case AMPER:
            case LEFTSHIFT:
            case SLASH:
            case PERCENT:
            case DOUBLESLASH:
            case LBRACK:
            case RBRACK:
            case RCURLY:
            case BACKQUOTE:
            case STRING:
                {
                alt100=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }

            switch (alt100) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1603:11: ( arith_op right+= term )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1603:11: ( arith_op right+= term )+
                    int cnt99=0;
                    loop99:
                    do {
                        int alt99=2;
                        int LA99_0 = input.LA(1);

                        if ( (LA99_0==PLUS) ) {
                            alt99=1;
                        }
                        else if ( (LA99_0==MINUS) ) {
                            alt99=1;
                        }


                        switch (alt99) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1603:12: arith_op right+= term
                    	    {
                    	    pushFollow(FOLLOW_arith_op_in_arith_expr5510);
                    	    arith_op210=arith_op();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_arith_op.add(arith_op210.getTree());
                    	    pushFollow(FOLLOW_term_in_arith_expr5514);
                    	    right=term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_term.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());

                    	    if ( state.backtracking==0 ) {

                    	                     ops.add((arith_op210!=null?arith_op210.op:null));
                    	                     toks.add((arith_op210!=null?((Token)arith_op210.start):null));
                    	                 
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt99 >= 1 ) break loop99;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(99, input);
                                throw eee;
                        }
                        cnt99++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1610:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1610:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (!ops.isEmpty()) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.lparen:null) != null) {
                          tok = (left!=null?left.lparen:null);
                      }
                      retval.tree = actions.makeBinOp(tok, (left!=null?((PythonTree)left.tree):null), ops, list_right, toks);
                  }
                  retval.lparen = (left!=null?left.lparen:null);

            }
        }
        catch (RewriteCardinalityException rce) {


                    System.out.println("Caught this parser error"); 
                    PythonTree badNode = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), null);
                    retval.tree = badNode;
                    errorHandler.error("Internal Parser Error", badNode);
                
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arith_expr"

    public static class arith_op_return extends ParserRuleReturnScope {
        public operatorType op;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arith_op"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1624:1: arith_op returns [operatorType op] : ( PLUS | MINUS );
    public final PythonParser.arith_op_return arith_op() throws RecognitionException {
        PythonParser.arith_op_return retval = new PythonParser.arith_op_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token PLUS211=null;
        Token MINUS212=null;

        PythonTree PLUS211_tree=null;
        PythonTree MINUS212_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1626:5: ( PLUS | MINUS )
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==PLUS) ) {
                alt101=1;
            }
            else if ( (LA101_0==MINUS) ) {
                alt101=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }
            switch (alt101) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1626:7: PLUS
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    PLUS211=(Token)match(input,PLUS,FOLLOW_PLUS_in_arith_op5622); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS211_tree = (PythonTree)adaptor.create(PLUS211);
                    adaptor.addChild(root_0, PLUS211_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.Add;
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1630:7: MINUS
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    MINUS212=(Token)match(input,MINUS,FOLLOW_MINUS_in_arith_op5638); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS212_tree = (PythonTree)adaptor.create(MINUS212);
                    adaptor.addChild(root_0, MINUS212_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.Sub;
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arith_op"

    public static class term_return extends ParserRuleReturnScope {
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1637:1: term returns [Token lparen = null] : left= factor ( ( term_op right+= factor )+ | -> $left) ;
    public final PythonParser.term_return term() throws RecognitionException {
        PythonParser.term_return retval = new PythonParser.term_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        List list_right=null;
        PythonParser.factor_return left = null;

        PythonParser.term_op_return term_op213 = null;

        PythonParser.factor_return right = null;
         right = null;
        RewriteRuleSubtreeStream stream_term_op=new RewriteRuleSubtreeStream(adaptor,"rule term_op");
        RewriteRuleSubtreeStream stream_factor=new RewriteRuleSubtreeStream(adaptor,"rule factor");

            List ops = new ArrayList();
            List toks = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1653:5: (left= factor ( ( term_op right+= factor )+ | -> $left) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1653:7: left= factor ( ( term_op right+= factor )+ | -> $left)
            {
            pushFollow(FOLLOW_factor_in_term5684);
            left=factor();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_factor.add(left.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1654:9: ( ( term_op right+= factor )+ | -> $left)
            int alt103=2;
            switch ( input.LA(1) ) {
            case STAR:
                {
                alt103=1;
                }
                break;
            case SLASH:
                {
                alt103=1;
                }
                break;
            case PERCENT:
                {
                alt103=1;
                }
                break;
            case DOUBLESLASH:
                {
                alt103=1;
                }
                break;
            case EOF:
            case NEWLINE:
            case DOT:
            case AND:
            case AS:
            case FOR:
            case IF:
            case IN:
            case IS:
            case NOT:
            case OR:
            case ORELSE:
            case LPAREN:
            case RPAREN:
            case COLON:
            case ASSIGN:
            case COMMA:
            case DOUBLESTAR:
            case SEMI:
            case PLUSEQUAL:
            case MINUSEQUAL:
            case STAREQUAL:
            case SLASHEQUAL:
            case PERCENTEQUAL:
            case AMPEREQUAL:
            case VBAREQUAL:
            case CIRCUMFLEXEQUAL:
            case LEFTSHIFTEQUAL:
            case RIGHTSHIFTEQUAL:
            case DOUBLESTAREQUAL:
            case DOUBLESLASHEQUAL:
            case RIGHTSHIFT:
            case LESS:
            case GREATER:
            case EQUAL:
            case GREATEREQUAL:
            case LESSEQUAL:
            case ALT_NOTEQUAL:
            case NOTEQUAL:
            case VBAR:
            case CIRCUMFLEX:
            case AMPER:
            case LEFTSHIFT:
            case PLUS:
            case MINUS:
            case LBRACK:
            case RBRACK:
            case RCURLY:
            case BACKQUOTE:
            case STRING:
                {
                alt103=2;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1654:11: ( term_op right+= factor )+
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1654:11: ( term_op right+= factor )+
                    int cnt102=0;
                    loop102:
                    do {
                        int alt102=2;
                        switch ( input.LA(1) ) {
                        case STAR:
                            {
                            alt102=1;
                            }
                            break;
                        case SLASH:
                            {
                            alt102=1;
                            }
                            break;
                        case PERCENT:
                            {
                            alt102=1;
                            }
                            break;
                        case DOUBLESLASH:
                            {
                            alt102=1;
                            }
                            break;

                        }

                        switch (alt102) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1654:12: term_op right+= factor
                    	    {
                    	    pushFollow(FOLLOW_term_op_in_term5697);
                    	    term_op213=term_op();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_term_op.add(term_op213.getTree());
                    	    pushFollow(FOLLOW_factor_in_term5701);
                    	    right=factor();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_factor.add(right.getTree());
                    	    if (list_right==null) list_right=new ArrayList();
                    	    list_right.add(right.getTree());

                    	    if ( state.backtracking==0 ) {

                    	                    ops.add((term_op213!=null?term_op213.op:null));
                    	                    toks.add((term_op213!=null?((Token)term_op213.start):null));
                    	                
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt102 >= 1 ) break loop102;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(102, input);
                                throw eee;
                        }
                        cnt102++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1661:8: 
                    {

                    // AST REWRITE
                    // elements: left
                    // token labels: 
                    // rule labels: left, retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.tree:null);
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1661:8: -> $left
                    {
                        adaptor.addChild(root_0, stream_left.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.lparen = (left!=null?left.lparen:null);
                  if (!ops.isEmpty()) {
                      Token tok = (left!=null?((Token)left.start):null);
                      if ((left!=null?left.lparen:null) != null) {
                          tok = (left!=null?left.lparen:null);
                      }
                      retval.tree = actions.makeBinOp(tok, (left!=null?((PythonTree)left.tree):null), ops, list_right, toks);
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "term"

    public static class term_op_return extends ParserRuleReturnScope {
        public operatorType op;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term_op"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1665:1: term_op returns [operatorType op] : ( STAR | SLASH | PERCENT | DOUBLESLASH );
    public final PythonParser.term_op_return term_op() throws RecognitionException {
        PythonParser.term_op_return retval = new PythonParser.term_op_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token STAR214=null;
        Token SLASH215=null;
        Token PERCENT216=null;
        Token DOUBLESLASH217=null;

        PythonTree STAR214_tree=null;
        PythonTree SLASH215_tree=null;
        PythonTree PERCENT216_tree=null;
        PythonTree DOUBLESLASH217_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1667:5: ( STAR | SLASH | PERCENT | DOUBLESLASH )
            int alt104=4;
            switch ( input.LA(1) ) {
            case STAR:
                {
                alt104=1;
                }
                break;
            case SLASH:
                {
                alt104=2;
                }
                break;
            case PERCENT:
                {
                alt104=3;
                }
                break;
            case DOUBLESLASH:
                {
                alt104=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1667:7: STAR
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    STAR214=(Token)match(input,STAR,FOLLOW_STAR_in_term_op5783); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STAR214_tree = (PythonTree)adaptor.create(STAR214);
                    adaptor.addChild(root_0, STAR214_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.Mult;
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1671:7: SLASH
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    SLASH215=(Token)match(input,SLASH,FOLLOW_SLASH_in_term_op5799); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    SLASH215_tree = (PythonTree)adaptor.create(SLASH215);
                    adaptor.addChild(root_0, SLASH215_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.Div;
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1675:7: PERCENT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    PERCENT216=(Token)match(input,PERCENT,FOLLOW_PERCENT_in_term_op5815); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERCENT216_tree = (PythonTree)adaptor.create(PERCENT216);
                    adaptor.addChild(root_0, PERCENT216_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.Mod;
                            
                    }

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1679:7: DOUBLESLASH
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    DOUBLESLASH217=(Token)match(input,DOUBLESLASH,FOLLOW_DOUBLESLASH_in_term_op5831); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOUBLESLASH217_tree = (PythonTree)adaptor.create(DOUBLESLASH217);
                    adaptor.addChild(root_0, DOUBLESLASH217_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.op = operatorType.FloorDiv;
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "term_op"

    public static class factor_return extends ParserRuleReturnScope {
        public expr etype;
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "factor"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1686:1: factor returns [expr etype, Token lparen = null] : ( PLUS p= factor | MINUS m= factor | TILDE t= factor | power );
    public final PythonParser.factor_return factor() throws RecognitionException {
        PythonParser.factor_return retval = new PythonParser.factor_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token PLUS218=null;
        Token MINUS219=null;
        Token TILDE220=null;
        PythonParser.factor_return p = null;

        PythonParser.factor_return m = null;

        PythonParser.factor_return t = null;

        PythonParser.power_return power221 = null;


        PythonTree PLUS218_tree=null;
        PythonTree MINUS219_tree=null;
        PythonTree TILDE220_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1691:5: ( PLUS p= factor | MINUS m= factor | TILDE t= factor | power )
            int alt105=4;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==PLUS) ) {
                alt105=1;
            }
            else if ( (LA105_0==MINUS) ) {
                alt105=2;
            }
            else if ( (LA105_0==TILDE) ) {
                alt105=3;
            }
            else if ( (LA105_0==NAME||LA105_0==LPAREN||LA105_0==LBRACK||LA105_0==LCURLY||LA105_0==BACKQUOTE) ) {
                alt105=4;
            }
            else if ( (LA105_0==PRINT) && ((printFunction))) {
                alt105=4;
            }
            else if ( ((LA105_0>=INT && LA105_0<=CONNECTTO)) ) {
                alt105=4;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }
            switch (alt105) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1691:7: PLUS p= factor
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    PLUS218=(Token)match(input,PLUS,FOLLOW_PLUS_in_factor5870); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS218_tree = (PythonTree)adaptor.create(PLUS218);
                    adaptor.addChild(root_0, PLUS218_tree);
                    }
                    pushFollow(FOLLOW_factor_in_factor5874);
                    p=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, p.getTree());
                    if ( state.backtracking==0 ) {

                                retval.etype = new UnaryOp(PLUS218, unaryopType.UAdd, (p!=null?p.etype:null));
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1695:7: MINUS m= factor
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    MINUS219=(Token)match(input,MINUS,FOLLOW_MINUS_in_factor5890); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS219_tree = (PythonTree)adaptor.create(MINUS219);
                    adaptor.addChild(root_0, MINUS219_tree);
                    }
                    pushFollow(FOLLOW_factor_in_factor5894);
                    m=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, m.getTree());
                    if ( state.backtracking==0 ) {

                                retval.etype = actions.negate(MINUS219, (m!=null?m.etype:null));
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1699:7: TILDE t= factor
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    TILDE220=(Token)match(input,TILDE,FOLLOW_TILDE_in_factor5910); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TILDE220_tree = (PythonTree)adaptor.create(TILDE220);
                    adaptor.addChild(root_0, TILDE220_tree);
                    }
                    pushFollow(FOLLOW_factor_in_factor5914);
                    t=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if ( state.backtracking==0 ) {

                                retval.etype = new UnaryOp(TILDE220, unaryopType.Invert, (t!=null?t.etype:null));
                            
                    }

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1703:7: power
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_power_in_factor5930);
                    power221=power();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, power221.getTree());
                    if ( state.backtracking==0 ) {

                                retval.etype = actions.castExpr((power221!=null?((PythonTree)power221.tree):null));
                                retval.lparen = (power221!=null?power221.lparen:null);
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = retval.etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "factor"

    public static class power_return extends ParserRuleReturnScope {
        public expr etype;
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "power"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1711:1: power returns [expr etype, Token lparen = null] : atom (t+= trailer[$atom.start, $atom.tree] )* ( options {greedy=true; } : d= DOUBLESTAR factor )? ;
    public final PythonParser.power_return power() throws RecognitionException {
        PythonParser.power_return retval = new PythonParser.power_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token d=null;
        List list_t=null;
        PythonParser.atom_return atom222 = null;

        PythonParser.factor_return factor223 = null;

        PythonParser.trailer_return t = null;
         t = null;
        PythonTree d_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1716:5: ( atom (t+= trailer[$atom.start, $atom.tree] )* ( options {greedy=true; } : d= DOUBLESTAR factor )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1716:7: atom (t+= trailer[$atom.start, $atom.tree] )* ( options {greedy=true; } : d= DOUBLESTAR factor )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_atom_in_power5969);
            atom222=atom();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, atom222.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1716:12: (t+= trailer[$atom.start, $atom.tree] )*
            loop106:
            do {
                int alt106=2;
                switch ( input.LA(1) ) {
                case LPAREN:
                    {
                    alt106=1;
                    }
                    break;
                case LBRACK:
                    {
                    alt106=1;
                    }
                    break;
                case DOT:
                    {
                    alt106=1;
                    }
                    break;

                }

                switch (alt106) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1716:13: t+= trailer[$atom.start, $atom.tree]
            	    {
            	    pushFollow(FOLLOW_trailer_in_power5974);
            	    t=trailer((atom222!=null?((Token)atom222.start):null), (atom222!=null?((PythonTree)atom222.tree):null));

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            	    if (list_t==null) list_t=new ArrayList();
            	    list_t.add(t.getTree());


            	    }
            	    break;

            	default :
            	    break loop106;
                }
            } while (true);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1716:51: ( options {greedy=true; } : d= DOUBLESTAR factor )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==DOUBLESTAR) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1716:75: d= DOUBLESTAR factor
                    {
                    d=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_power5989); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    d_tree = (PythonTree)adaptor.create(d);
                    adaptor.addChild(root_0, d_tree);
                    }
                    pushFollow(FOLLOW_factor_in_power5991);
                    factor223=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, factor223.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        retval.lparen = (atom222!=null?atom222.lparen:null);
                        //XXX: This could be better.
                        retval.etype = actions.castExpr((atom222!=null?((PythonTree)atom222.tree):null));
                        if (list_t != null) {
                            for(Object o : list_t) {
                                actions.recurseSetContext(retval.etype, expr_contextType.Load);
                                if (o instanceof Call) {
                                    Call c = (Call)o;
                                    c.setFunc((PyObject)retval.etype);
                                    retval.etype = c;
                                } else if (o instanceof Subscript) {
                                    Subscript c = (Subscript)o;
                                    c.setValue((PyObject)retval.etype);
                                    retval.etype = c;
                                } else if (o instanceof Attribute) {
                                    Attribute c = (Attribute)o;
                                    c.setCharStartIndex(retval.etype.getCharStartIndex());
                                    c.setValue((PyObject)retval.etype);
                                    retval.etype = c;
                                }
                            }
                        }
                        if (d != null) {
                            List right = new ArrayList();
                            right.add((factor223!=null?((PythonTree)factor223.tree):null));
                            retval.etype = actions.makeBinOp((atom222!=null?((Token)atom222.start):null), retval.etype, operatorType.Pow, right);
                        }
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = retval.etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "power"

    public static class atom_return extends ParserRuleReturnScope {
        public Token lparen = null;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "atom"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1753:1: atom returns [Token lparen = null] : ( LPAREN ( yield_expr | testlist_gexp -> testlist_gexp | ) RPAREN | LBRACK ( listmaker[$LBRACK] -> listmaker | ) RBRACK | LCURLY ( dictorsetmaker[$LCURLY] -> dictorsetmaker | ) RCURLY | lb= BACKQUOTE testlist[expr_contextType.Load] rb= BACKQUOTE | name_or_print | INT | LONGINT | FLOAT | COMPLEX | (S+= STRING )+ | sql_stmt -> sql_stmt | sim_stmt -> sim_stmt | neo4j_stmt -> neo4j_stmt | sparql_stmt -> sparql_stmt | rdf_stmt -> rdf_stmt | japi_stmt -> japi_stmt | conn_stmt -> conn_stmt | oorel_commit_stmt -> oorel_commit_stmt | oorel_insert_stmt -> oorel_insert_stmt );
    public final PythonParser.atom_return atom() throws RecognitionException {
        PythonParser.atom_return retval = new PythonParser.atom_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token lb=null;
        Token rb=null;
        Token LPAREN224=null;
        Token RPAREN227=null;
        Token LBRACK228=null;
        Token RBRACK230=null;
        Token LCURLY231=null;
        Token RCURLY233=null;
        Token INT236=null;
        Token LONGINT237=null;
        Token FLOAT238=null;
        Token COMPLEX239=null;
        Token S=null;
        List list_S=null;
        PythonParser.yield_expr_return yield_expr225 = null;

        PythonParser.testlist_gexp_return testlist_gexp226 = null;

        PythonParser.listmaker_return listmaker229 = null;

        PythonParser.dictorsetmaker_return dictorsetmaker232 = null;

        PythonParser.testlist_return testlist234 = null;

        PythonParser.name_or_print_return name_or_print235 = null;

        PythonParser.sql_stmt_return sql_stmt240 = null;

        PythonParser.sim_stmt_return sim_stmt241 = null;

        PythonParser.neo4j_stmt_return neo4j_stmt242 = null;

        PythonParser.sparql_stmt_return sparql_stmt243 = null;

        PythonParser.rdf_stmt_return rdf_stmt244 = null;

        PythonParser.japi_stmt_return japi_stmt245 = null;

        PythonParser.conn_stmt_return conn_stmt246 = null;

        PythonParser.oorel_commit_stmt_return oorel_commit_stmt247 = null;

        PythonParser.oorel_insert_stmt_return oorel_insert_stmt248 = null;


        PythonTree lb_tree=null;
        PythonTree rb_tree=null;
        PythonTree LPAREN224_tree=null;
        PythonTree RPAREN227_tree=null;
        PythonTree LBRACK228_tree=null;
        PythonTree RBRACK230_tree=null;
        PythonTree LCURLY231_tree=null;
        PythonTree RCURLY233_tree=null;
        PythonTree INT236_tree=null;
        PythonTree LONGINT237_tree=null;
        PythonTree FLOAT238_tree=null;
        PythonTree COMPLEX239_tree=null;
        PythonTree S_tree=null;
        RewriteRuleTokenStream stream_RBRACK=new RewriteRuleTokenStream(adaptor,"token RBRACK");
        RewriteRuleTokenStream stream_LBRACK=new RewriteRuleTokenStream(adaptor,"token LBRACK");
        RewriteRuleTokenStream stream_LPAREN=new RewriteRuleTokenStream(adaptor,"token LPAREN");
        RewriteRuleTokenStream stream_LCURLY=new RewriteRuleTokenStream(adaptor,"token LCURLY");
        RewriteRuleTokenStream stream_RPAREN=new RewriteRuleTokenStream(adaptor,"token RPAREN");
        RewriteRuleTokenStream stream_RCURLY=new RewriteRuleTokenStream(adaptor,"token RCURLY");
        RewriteRuleSubtreeStream stream_listmaker=new RewriteRuleSubtreeStream(adaptor,"rule listmaker");
        RewriteRuleSubtreeStream stream_oorel_insert_stmt=new RewriteRuleSubtreeStream(adaptor,"rule oorel_insert_stmt");
        RewriteRuleSubtreeStream stream_rdf_stmt=new RewriteRuleSubtreeStream(adaptor,"rule rdf_stmt");
        RewriteRuleSubtreeStream stream_oorel_commit_stmt=new RewriteRuleSubtreeStream(adaptor,"rule oorel_commit_stmt");
        RewriteRuleSubtreeStream stream_japi_stmt=new RewriteRuleSubtreeStream(adaptor,"rule japi_stmt");
        RewriteRuleSubtreeStream stream_yield_expr=new RewriteRuleSubtreeStream(adaptor,"rule yield_expr");
        RewriteRuleSubtreeStream stream_sparql_stmt=new RewriteRuleSubtreeStream(adaptor,"rule sparql_stmt");
        RewriteRuleSubtreeStream stream_sim_stmt=new RewriteRuleSubtreeStream(adaptor,"rule sim_stmt");
        RewriteRuleSubtreeStream stream_conn_stmt=new RewriteRuleSubtreeStream(adaptor,"rule conn_stmt");
        RewriteRuleSubtreeStream stream_testlist_gexp=new RewriteRuleSubtreeStream(adaptor,"rule testlist_gexp");
        RewriteRuleSubtreeStream stream_dictorsetmaker=new RewriteRuleSubtreeStream(adaptor,"rule dictorsetmaker");
        RewriteRuleSubtreeStream stream_sql_stmt=new RewriteRuleSubtreeStream(adaptor,"rule sql_stmt");
        RewriteRuleSubtreeStream stream_neo4j_stmt=new RewriteRuleSubtreeStream(adaptor,"rule neo4j_stmt");

            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1763:5: ( LPAREN ( yield_expr | testlist_gexp -> testlist_gexp | ) RPAREN | LBRACK ( listmaker[$LBRACK] -> listmaker | ) RBRACK | LCURLY ( dictorsetmaker[$LCURLY] -> dictorsetmaker | ) RCURLY | lb= BACKQUOTE testlist[expr_contextType.Load] rb= BACKQUOTE | name_or_print | INT | LONGINT | FLOAT | COMPLEX | (S+= STRING )+ | sql_stmt -> sql_stmt | sim_stmt -> sim_stmt | neo4j_stmt -> neo4j_stmt | sparql_stmt -> sparql_stmt | rdf_stmt -> rdf_stmt | japi_stmt -> japi_stmt | conn_stmt -> conn_stmt | oorel_commit_stmt -> oorel_commit_stmt | oorel_insert_stmt -> oorel_insert_stmt )
            int alt112=19;
            alt112 = dfa112.predict(input);
            switch (alt112) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1763:7: LPAREN ( yield_expr | testlist_gexp -> testlist_gexp | ) RPAREN
                    {
                    LPAREN224=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_atom6041); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LPAREN.add(LPAREN224);

                    if ( state.backtracking==0 ) {

                                retval.lparen = LPAREN224;
                            
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1767:7: ( yield_expr | testlist_gexp -> testlist_gexp | )
                    int alt108=3;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==YIELD) ) {
                        alt108=1;
                    }
                    else if ( (LA108_0==NAME||LA108_0==NOT||LA108_0==LPAREN||(LA108_0>=PLUS && LA108_0<=MINUS)||(LA108_0>=TILDE && LA108_0<=LBRACK)||LA108_0==LCURLY||LA108_0==BACKQUOTE) ) {
                        alt108=2;
                    }
                    else if ( (LA108_0==PRINT) && ((printFunction))) {
                        alt108=2;
                    }
                    else if ( (LA108_0==LAMBDA||(LA108_0>=INT && LA108_0<=CONNECTTO)) ) {
                        alt108=2;
                    }
                    else if ( (LA108_0==RPAREN) ) {
                        alt108=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 108, 0, input);

                        throw nvae;
                    }
                    switch (alt108) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1767:9: yield_expr
                            {
                            pushFollow(FOLLOW_yield_expr_in_atom6059);
                            yield_expr225=yield_expr();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_yield_expr.add(yield_expr225.getTree());
                            if ( state.backtracking==0 ) {

                                          etype = (yield_expr225!=null?yield_expr225.etype:null);
                                      
                            }

                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1771:9: testlist_gexp
                            {
                            pushFollow(FOLLOW_testlist_gexp_in_atom6079);
                            testlist_gexp226=testlist_gexp();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_testlist_gexp.add(testlist_gexp226.getTree());


                            // AST REWRITE
                            // elements: testlist_gexp
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (PythonTree)adaptor.nil();
                            // 1772:6: -> testlist_gexp
                            {
                                adaptor.addChild(root_0, stream_testlist_gexp.nextTree());

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 3 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1774:9: 
                            {
                            if ( state.backtracking==0 ) {

                                          etype = new Tuple(LPAREN224, new ArrayList<expr>(), ((expr_scope)expr_stack.peek()).ctype);
                                      
                            }

                            }
                            break;

                    }

                    RPAREN227=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_atom6122); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RPAREN.add(RPAREN227);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1779:7: LBRACK ( listmaker[$LBRACK] -> listmaker | ) RBRACK
                    {
                    LBRACK228=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_atom6130); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LBRACK.add(LBRACK228);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1780:7: ( listmaker[$LBRACK] -> listmaker | )
                    int alt109=2;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==NAME||LA109_0==NOT||LA109_0==LPAREN||(LA109_0>=PLUS && LA109_0<=MINUS)||(LA109_0>=TILDE && LA109_0<=LBRACK)||LA109_0==LCURLY||LA109_0==BACKQUOTE) ) {
                        alt109=1;
                    }
                    else if ( (LA109_0==PRINT) && ((printFunction))) {
                        alt109=1;
                    }
                    else if ( (LA109_0==LAMBDA||(LA109_0>=INT && LA109_0<=CONNECTTO)) ) {
                        alt109=1;
                    }
                    else if ( (LA109_0==RBRACK) ) {
                        alt109=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 109, 0, input);

                        throw nvae;
                    }
                    switch (alt109) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1780:8: listmaker[$LBRACK]
                            {
                            pushFollow(FOLLOW_listmaker_in_atom6139);
                            listmaker229=listmaker(LBRACK228);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_listmaker.add(listmaker229.getTree());


                            // AST REWRITE
                            // elements: listmaker
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (PythonTree)adaptor.nil();
                            // 1781:6: -> listmaker
                            {
                                adaptor.addChild(root_0, stream_listmaker.nextTree());

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1783:8: 
                            {
                            if ( state.backtracking==0 ) {

                                         etype = new org.python.antlr.ast.List(LBRACK228, new ArrayList<expr>(), ((expr_scope)expr_stack.peek()).ctype);
                                     
                            }

                            }
                            break;

                    }

                    RBRACK230=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_atom6182); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RBRACK.add(RBRACK230);


                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1788:7: LCURLY ( dictorsetmaker[$LCURLY] -> dictorsetmaker | ) RCURLY
                    {
                    LCURLY231=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_atom6190); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_LCURLY.add(LCURLY231);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1789:8: ( dictorsetmaker[$LCURLY] -> dictorsetmaker | )
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==NAME||LA110_0==NOT||LA110_0==LPAREN||(LA110_0>=PLUS && LA110_0<=MINUS)||(LA110_0>=TILDE && LA110_0<=LBRACK)||LA110_0==LCURLY||LA110_0==BACKQUOTE) ) {
                        alt110=1;
                    }
                    else if ( (LA110_0==PRINT) && ((printFunction))) {
                        alt110=1;
                    }
                    else if ( (LA110_0==LAMBDA||(LA110_0>=INT && LA110_0<=CONNECTTO)) ) {
                        alt110=1;
                    }
                    else if ( (LA110_0==RCURLY) ) {
                        alt110=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 110, 0, input);

                        throw nvae;
                    }
                    switch (alt110) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1789:9: dictorsetmaker[$LCURLY]
                            {
                            pushFollow(FOLLOW_dictorsetmaker_in_atom6200);
                            dictorsetmaker232=dictorsetmaker(LCURLY231);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) stream_dictorsetmaker.add(dictorsetmaker232.getTree());


                            // AST REWRITE
                            // elements: dictorsetmaker
                            // token labels: 
                            // rule labels: retval
                            // token list labels: 
                            // rule list labels: 
                            // wildcard labels: 
                            if ( state.backtracking==0 ) {
                            retval.tree = root_0;
                            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                            root_0 = (PythonTree)adaptor.nil();
                            // 1790:7: -> dictorsetmaker
                            {
                                adaptor.addChild(root_0, stream_dictorsetmaker.nextTree());

                            }

                            retval.tree = root_0;}
                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1792:9: 
                            {
                            if ( state.backtracking==0 ) {

                                          etype = new Dict(LCURLY231, new ArrayList<expr>(), new ArrayList<expr>());
                                      
                            }

                            }
                            break;

                    }

                    RCURLY233=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_atom6248); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_RCURLY.add(RCURLY233);


                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1797:8: lb= BACKQUOTE testlist[expr_contextType.Load] rb= BACKQUOTE
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    lb=(Token)match(input,BACKQUOTE,FOLLOW_BACKQUOTE_in_atom6259); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    lb_tree = (PythonTree)adaptor.create(lb);
                    adaptor.addChild(root_0, lb_tree);
                    }
                    pushFollow(FOLLOW_testlist_in_atom6261);
                    testlist234=testlist(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist234.getTree());
                    rb=(Token)match(input,BACKQUOTE,FOLLOW_BACKQUOTE_in_atom6266); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    rb_tree = (PythonTree)adaptor.create(rb);
                    adaptor.addChild(root_0, rb_tree);
                    }
                    if ( state.backtracking==0 ) {

                                 etype = new Repr(lb, actions.castExpr((testlist234!=null?((PythonTree)testlist234.tree):null)));
                             
                    }

                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1801:8: name_or_print
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_name_or_print_in_atom6284);
                    name_or_print235=name_or_print();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, name_or_print235.getTree());
                    if ( state.backtracking==0 ) {

                                 etype = new Name((name_or_print235!=null?((Token)name_or_print235.start):null), (name_or_print235!=null?input.toString(name_or_print235.start,name_or_print235.stop):null), ((expr_scope)expr_stack.peek()).ctype);
                             
                    }

                    }
                    break;
                case 6 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1805:8: INT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    INT236=(Token)match(input,INT,FOLLOW_INT_in_atom6302); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INT236_tree = (PythonTree)adaptor.create(INT236);
                    adaptor.addChild(root_0, INT236_tree);
                    }
                    if ( state.backtracking==0 ) {

                                 etype = new Num(INT236, actions.makeInt(INT236));
                             
                    }

                    }
                    break;
                case 7 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1809:8: LONGINT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LONGINT237=(Token)match(input,LONGINT,FOLLOW_LONGINT_in_atom6320); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LONGINT237_tree = (PythonTree)adaptor.create(LONGINT237);
                    adaptor.addChild(root_0, LONGINT237_tree);
                    }
                    if ( state.backtracking==0 ) {

                                 etype = new Num(LONGINT237, actions.makeInt(LONGINT237));
                             
                    }

                    }
                    break;
                case 8 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1813:8: FLOAT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    FLOAT238=(Token)match(input,FLOAT,FOLLOW_FLOAT_in_atom6338); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FLOAT238_tree = (PythonTree)adaptor.create(FLOAT238);
                    adaptor.addChild(root_0, FLOAT238_tree);
                    }
                    if ( state.backtracking==0 ) {

                                 etype = new Num(FLOAT238, actions.makeFloat(FLOAT238));
                             
                    }

                    }
                    break;
                case 9 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1817:8: COMPLEX
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    COMPLEX239=(Token)match(input,COMPLEX,FOLLOW_COMPLEX_in_atom6356); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMPLEX239_tree = (PythonTree)adaptor.create(COMPLEX239);
                    adaptor.addChild(root_0, COMPLEX239_tree);
                    }
                    if ( state.backtracking==0 ) {

                                 etype = new Num(COMPLEX239, actions.makeComplex(COMPLEX239));
                             
                    }

                    }
                    break;
                case 10 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1821:8: (S+= STRING )+
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1821:8: (S+= STRING )+
                    int cnt111=0;
                    loop111:
                    do {
                        int alt111=2;
                        int LA111_0 = input.LA(1);

                        if ( (LA111_0==STRING) ) {
                            alt111=1;
                        }


                        switch (alt111) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1821:9: S+= STRING
                    	    {
                    	    S=(Token)match(input,STRING,FOLLOW_STRING_in_atom6377); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    S_tree = (PythonTree)adaptor.create(S);
                    	    adaptor.addChild(root_0, S_tree);
                    	    }
                    	    if (list_S==null) list_S=new ArrayList();
                    	    list_S.add(S);


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt111 >= 1 ) break loop111;
                    	    if (state.backtracking>0) {state.failed=true; return retval;}
                                EarlyExitException eee =
                                    new EarlyExitException(111, input);
                                throw eee;
                        }
                        cnt111++;
                    } while (true);

                    if ( state.backtracking==0 ) {

                                 etype = new Str(actions.extractStringToken(list_S), actions.extractStrings(list_S, encoding, unicodeLiterals));
                             
                    }

                    }
                    break;
                case 11 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1825:8: sql_stmt
                    {
                    pushFollow(FOLLOW_sql_stmt_in_atom6397);
                    sql_stmt240=sql_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_sql_stmt.add(sql_stmt240.getTree());


                    // AST REWRITE
                    // elements: sql_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1826:6: -> sql_stmt
                    {
                        adaptor.addChild(root_0, stream_sql_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 12 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1827:8: sim_stmt
                    {
                    pushFollow(FOLLOW_sim_stmt_in_atom6414);
                    sim_stmt241=sim_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_sim_stmt.add(sim_stmt241.getTree());


                    // AST REWRITE
                    // elements: sim_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1828:6: -> sim_stmt
                    {
                        adaptor.addChild(root_0, stream_sim_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 13 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1829:8: neo4j_stmt
                    {
                    pushFollow(FOLLOW_neo4j_stmt_in_atom6431);
                    neo4j_stmt242=neo4j_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_neo4j_stmt.add(neo4j_stmt242.getTree());


                    // AST REWRITE
                    // elements: neo4j_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1830:6: -> neo4j_stmt
                    {
                        adaptor.addChild(root_0, stream_neo4j_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 14 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1831:8: sparql_stmt
                    {
                    pushFollow(FOLLOW_sparql_stmt_in_atom6448);
                    sparql_stmt243=sparql_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_sparql_stmt.add(sparql_stmt243.getTree());


                    // AST REWRITE
                    // elements: sparql_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1832:6: -> sparql_stmt
                    {
                        adaptor.addChild(root_0, stream_sparql_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 15 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1833:8: rdf_stmt
                    {
                    pushFollow(FOLLOW_rdf_stmt_in_atom6465);
                    rdf_stmt244=rdf_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_rdf_stmt.add(rdf_stmt244.getTree());


                    // AST REWRITE
                    // elements: rdf_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1834:6: -> rdf_stmt
                    {
                        adaptor.addChild(root_0, stream_rdf_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 16 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1835:8: japi_stmt
                    {
                    pushFollow(FOLLOW_japi_stmt_in_atom6482);
                    japi_stmt245=japi_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_japi_stmt.add(japi_stmt245.getTree());


                    // AST REWRITE
                    // elements: japi_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1836:6: -> japi_stmt
                    {
                        adaptor.addChild(root_0, stream_japi_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 17 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1837:8: conn_stmt
                    {
                    pushFollow(FOLLOW_conn_stmt_in_atom6499);
                    conn_stmt246=conn_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_conn_stmt.add(conn_stmt246.getTree());


                    // AST REWRITE
                    // elements: conn_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1838:6: -> conn_stmt
                    {
                        adaptor.addChild(root_0, stream_conn_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 18 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1839:8: oorel_commit_stmt
                    {
                    pushFollow(FOLLOW_oorel_commit_stmt_in_atom6517);
                    oorel_commit_stmt247=oorel_commit_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oorel_commit_stmt.add(oorel_commit_stmt247.getTree());


                    // AST REWRITE
                    // elements: oorel_commit_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1840:6: -> oorel_commit_stmt
                    {
                        adaptor.addChild(root_0, stream_oorel_commit_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 19 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1841:8: oorel_insert_stmt
                    {
                    pushFollow(FOLLOW_oorel_insert_stmt_in_atom6534);
                    oorel_insert_stmt248=oorel_insert_stmt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_oorel_insert_stmt.add(oorel_insert_stmt248.getTree());


                    // AST REWRITE
                    // elements: oorel_insert_stmt
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 1842:6: -> oorel_insert_stmt
                    {
                        adaptor.addChild(root_0, stream_oorel_insert_stmt.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 if (etype != null) {
                     retval.tree = etype;
                 }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "atom"

    public static class oorel_commit_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "oorel_commit_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1847:1: oorel_commit_stmt : OORELCOMMIT name= NAME -> ^( OORELCOMMIT[$oorel_commit_stmt.start, \n conn_name] ) ;
    public final PythonParser.oorel_commit_stmt_return oorel_commit_stmt() throws RecognitionException {
        PythonParser.oorel_commit_stmt_return retval = new PythonParser.oorel_commit_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token OORELCOMMIT249=null;

        PythonTree name_tree=null;
        PythonTree OORELCOMMIT249_tree=null;
        RewriteRuleTokenStream stream_OORELCOMMIT=new RewriteRuleTokenStream(adaptor,"token OORELCOMMIT");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");


            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1851:5: ( OORELCOMMIT name= NAME -> ^( OORELCOMMIT[$oorel_commit_stmt.start, \n conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1852:9: OORELCOMMIT name= NAME
            {
            OORELCOMMIT249=(Token)match(input,OORELCOMMIT,FOLLOW_OORELCOMMIT_in_oorel_commit_stmt6575); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_OORELCOMMIT.add(OORELCOMMIT249);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_oorel_commit_stmt6579); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }


            // AST REWRITE
            // elements: OORELCOMMIT
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1856:9: -> ^( OORELCOMMIT[$oorel_commit_stmt.start, \n conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1856:12: ^( OORELCOMMIT[$oorel_commit_stmt.start, \n conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new RelCommit(OORELCOMMIT, ((Token)retval.start), conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "oorel_commit_stmt"

    public static class oorel_insert_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "oorel_insert_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1862:1: oorel_insert_stmt : ( OORELINSERT name= NAME ) (obj_name= NAME ) -> ^( OORELINSERT[$oorel_insert_stmt.start, name_node, \n conn_name] ) ;
    public final PythonParser.oorel_insert_stmt_return oorel_insert_stmt() throws RecognitionException {
        PythonParser.oorel_insert_stmt_return retval = new PythonParser.oorel_insert_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token obj_name=null;
        Token OORELINSERT250=null;

        PythonTree name_tree=null;
        PythonTree obj_name_tree=null;
        PythonTree OORELINSERT250_tree=null;
        RewriteRuleTokenStream stream_OORELINSERT=new RewriteRuleTokenStream(adaptor,"token OORELINSERT");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");


            Name name_node = null;
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1867:5: ( ( OORELINSERT name= NAME ) (obj_name= NAME ) -> ^( OORELINSERT[$oorel_insert_stmt.start, name_node, \n conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1868:9: ( OORELINSERT name= NAME ) (obj_name= NAME )
            {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1868:9: ( OORELINSERT name= NAME )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1868:10: OORELINSERT name= NAME
            {
            OORELINSERT250=(Token)match(input,OORELINSERT,FOLLOW_OORELINSERT_in_oorel_insert_stmt6640); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_OORELINSERT.add(OORELINSERT250);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_oorel_insert_stmt6644); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);


            }

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1872:9: (obj_name= NAME )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1872:10: obj_name= NAME
            {
            obj_name=(Token)match(input,NAME,FOLLOW_NAME_in_oorel_insert_stmt6668); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(obj_name);


            }

            if ( state.backtracking==0 ) {

                          name_node = actions.makeNameNode(obj_name);
                      
            }


            // AST REWRITE
            // elements: OORELINSERT
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1876:9: -> ^( OORELINSERT[$oorel_insert_stmt.start, name_node, \n conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1876:12: ^( OORELINSERT[$oorel_insert_stmt.start, name_node, \n conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new RelInsert(OORELINSERT, ((Token)retval.start), name_node, conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "oorel_insert_stmt"

    public static class sql_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sql_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1882:1: sql_stmt : SQL name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( SQL[$sql_stmt.start, exprs, $expr::ctype, strings, \"SQL\", conn_name] ) ;
    public final PythonParser.sql_stmt_return sql_stmt() throws RecognitionException {
        PythonParser.sql_stmt_return retval = new PythonParser.sql_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token SQL251=null;
        Token s=null;
        List list_s=null;
        PythonParser.expr_return e = null;


        PythonTree name_tree=null;
        PythonTree SQL251_tree=null;
        PythonTree s_tree=null;
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_SQL=new RewriteRuleTokenStream(adaptor,"token SQL");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            ArrayList<String> strings = new ArrayList<String>();
        	ArrayList<expr> exprs   = new ArrayList<expr>();
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1888:5: ( SQL name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( SQL[$sql_stmt.start, exprs, $expr::ctype, strings, \"SQL\", conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1889:9: SQL name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+
            {
            SQL251=(Token)match(input,SQL,FOLLOW_SQL_in_sql_stmt6730); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SQL.add(SQL251);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_sql_stmt6734); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1892:11: (s+= STRING (e= expr[expr_contextType.Load] )? )+
            int cnt114=0;
            loop114:
            do {
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==STRING) ) {
                    alt114=1;
                }


                switch (alt114) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1892:12: s+= STRING (e= expr[expr_contextType.Load] )?
            	    {
            	    s=(Token)match(input,STRING,FOLLOW_STRING_in_sql_stmt6751); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_STRING.add(s);

            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s);

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1892:24: (e= expr[expr_contextType.Load] )?
            	    int alt113=2;
            	    alt113 = dfa113.predict(input);
            	    switch (alt113) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1892:25: e= expr[expr_contextType.Load]
            	            {
            	            pushFollow(FOLLOW_expr_in_sql_stmt6758);
            	            e=expr(expr_contextType.Load);

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_expr.add(e.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	       strings.add(actions.extractStrings(list_s, encoding, unicodeLiterals).toString().replaceAll(";", "")); list_s = null;
            	                       if(e != null) exprs.add(actions.castExpr((e!=null?((PythonTree)e.tree):null))); e = null;
            	                     
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt114 >= 1 ) break loop114;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(114, input);
                        throw eee;
                }
                cnt114++;
            } while (true);



            // AST REWRITE
            // elements: SQL
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1897:8: -> ^( SQL[$sql_stmt.start, exprs, $expr::ctype, strings, \"SQL\", conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1897:11: ^( SQL[$sql_stmt.start, exprs, $expr::ctype, strings, \"SQL\", conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new Tuple(SQL, ((Token)retval.start), exprs, ((expr_scope)expr_stack.peek()).ctype, strings, "SQL", conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sql_stmt"

    public static class sim_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sim_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1906:1: sim_stmt : SIM name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( SIM[$sim_stmt.start, exprs, $expr::ctype, strings, \"SIM\", conn_name] ) ;
    public final PythonParser.sim_stmt_return sim_stmt() throws RecognitionException {
        PythonParser.sim_stmt_return retval = new PythonParser.sim_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token SIM252=null;
        Token s=null;
        List list_s=null;
        PythonParser.expr_return e = null;


        PythonTree name_tree=null;
        PythonTree SIM252_tree=null;
        PythonTree s_tree=null;
        RewriteRuleTokenStream stream_SIM=new RewriteRuleTokenStream(adaptor,"token SIM");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            ArrayList<String> strings = new ArrayList<String>();
        	ArrayList<expr> exprs   = new ArrayList<expr>();
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1912:5: ( SIM name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( SIM[$sim_stmt.start, exprs, $expr::ctype, strings, \"SIM\", conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1913:9: SIM name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+
            {
            SIM252=(Token)match(input,SIM,FOLLOW_SIM_in_sim_stmt6871); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SIM.add(SIM252);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_sim_stmt6875); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1916:11: (s+= STRING (e= expr[expr_contextType.Load] )? )+
            int cnt116=0;
            loop116:
            do {
                int alt116=2;
                int LA116_0 = input.LA(1);

                if ( (LA116_0==STRING) ) {
                    alt116=1;
                }


                switch (alt116) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1916:12: s+= STRING (e= expr[expr_contextType.Load] )?
            	    {
            	    s=(Token)match(input,STRING,FOLLOW_STRING_in_sim_stmt6892); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_STRING.add(s);

            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s);

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1916:24: (e= expr[expr_contextType.Load] )?
            	    int alt115=2;
            	    alt115 = dfa115.predict(input);
            	    switch (alt115) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1916:25: e= expr[expr_contextType.Load]
            	            {
            	            pushFollow(FOLLOW_expr_in_sim_stmt6899);
            	            e=expr(expr_contextType.Load);

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_expr.add(e.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	       strings.add(actions.extractStrings(list_s, encoding, unicodeLiterals).toString().replaceAll(";", "_^_")); list_s = null;
            	                       if(e != null) exprs.add(actions.castExpr((e!=null?((PythonTree)e.tree):null))); e = null;
            	                     
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt116 >= 1 ) break loop116;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(116, input);
                        throw eee;
                }
                cnt116++;
            } while (true);



            // AST REWRITE
            // elements: SIM
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1921:8: -> ^( SIM[$sim_stmt.start, exprs, $expr::ctype, strings, \"SIM\", conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1921:11: ^( SIM[$sim_stmt.start, exprs, $expr::ctype, strings, \"SIM\", conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new Tuple(SIM, ((Token)retval.start), exprs, ((expr_scope)expr_stack.peek()).ctype, strings, "SIM", conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sim_stmt"

    public static class neo4j_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "neo4j_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1928:1: neo4j_stmt : Neo4j name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( Neo4j[$neo4j_stmt.start, exprs, $expr::ctype, strings, \"Neo4j\", conn_name] ) ;
    public final PythonParser.neo4j_stmt_return neo4j_stmt() throws RecognitionException {
        PythonParser.neo4j_stmt_return retval = new PythonParser.neo4j_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token Neo4j253=null;
        Token s=null;
        List list_s=null;
        PythonParser.expr_return e = null;


        PythonTree name_tree=null;
        PythonTree Neo4j253_tree=null;
        PythonTree s_tree=null;
        RewriteRuleTokenStream stream_Neo4j=new RewriteRuleTokenStream(adaptor,"token Neo4j");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            ArrayList<String> strings = new ArrayList<String>();
        	ArrayList<expr> exprs   = new ArrayList<expr>();
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1934:5: ( Neo4j name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( Neo4j[$neo4j_stmt.start, exprs, $expr::ctype, strings, \"Neo4j\", conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1935:9: Neo4j name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+
            {
            Neo4j253=(Token)match(input,Neo4j,FOLLOW_Neo4j_in_neo4j_stmt6984); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_Neo4j.add(Neo4j253);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_neo4j_stmt6988); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1938:11: (s+= STRING (e= expr[expr_contextType.Load] )? )+
            int cnt118=0;
            loop118:
            do {
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( (LA118_0==STRING) ) {
                    alt118=1;
                }


                switch (alt118) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1938:12: s+= STRING (e= expr[expr_contextType.Load] )?
            	    {
            	    s=(Token)match(input,STRING,FOLLOW_STRING_in_neo4j_stmt7005); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_STRING.add(s);

            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s);

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1938:24: (e= expr[expr_contextType.Load] )?
            	    int alt117=2;
            	    alt117 = dfa117.predict(input);
            	    switch (alt117) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1938:25: e= expr[expr_contextType.Load]
            	            {
            	            pushFollow(FOLLOW_expr_in_neo4j_stmt7012);
            	            e=expr(expr_contextType.Load);

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_expr.add(e.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	       strings.add(actions.extractStrings(list_s, encoding, unicodeLiterals).toString().replaceAll(";", "")); list_s = null;
            	                       if(e != null) exprs.add(actions.castExpr((e!=null?((PythonTree)e.tree):null))); e = null;
            	                     
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt118 >= 1 ) break loop118;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(118, input);
                        throw eee;
                }
                cnt118++;
            } while (true);



            // AST REWRITE
            // elements: Neo4j
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1943:8: -> ^( Neo4j[$neo4j_stmt.start, exprs, $expr::ctype, strings, \"Neo4j\", conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1943:11: ^( Neo4j[$neo4j_stmt.start, exprs, $expr::ctype, strings, \"Neo4j\", conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new Tuple(Neo4j, ((Token)retval.start), exprs, ((expr_scope)expr_stack.peek()).ctype, strings, "Neo4j", conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "neo4j_stmt"

    public static class japi_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "japi_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1949:1: japi_stmt : JAPI name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( JAPI[$japi_stmt.start, exprs, $expr::ctype, strings, \"JAPI\", conn_name] ) ;
    public final PythonParser.japi_stmt_return japi_stmt() throws RecognitionException {
        PythonParser.japi_stmt_return retval = new PythonParser.japi_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token JAPI254=null;
        Token s=null;
        List list_s=null;
        PythonParser.expr_return e = null;


        PythonTree name_tree=null;
        PythonTree JAPI254_tree=null;
        PythonTree s_tree=null;
        RewriteRuleTokenStream stream_JAPI=new RewriteRuleTokenStream(adaptor,"token JAPI");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            ArrayList<String> strings = new ArrayList<String>();
            ArrayList<expr> exprs   = new ArrayList<expr>();
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1955:5: ( JAPI name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( JAPI[$japi_stmt.start, exprs, $expr::ctype, strings, \"JAPI\", conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1956:9: JAPI name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+
            {
            JAPI254=(Token)match(input,JAPI,FOLLOW_JAPI_in_japi_stmt7096); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_JAPI.add(JAPI254);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_japi_stmt7100); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1959:11: (s+= STRING (e= expr[expr_contextType.Load] )? )+
            int cnt120=0;
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( (LA120_0==STRING) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1959:12: s+= STRING (e= expr[expr_contextType.Load] )?
            	    {
            	    s=(Token)match(input,STRING,FOLLOW_STRING_in_japi_stmt7117); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_STRING.add(s);

            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s);

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1959:24: (e= expr[expr_contextType.Load] )?
            	    int alt119=2;
            	    alt119 = dfa119.predict(input);
            	    switch (alt119) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1959:25: e= expr[expr_contextType.Load]
            	            {
            	            pushFollow(FOLLOW_expr_in_japi_stmt7124);
            	            e=expr(expr_contextType.Load);

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_expr.add(e.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	       strings.add(actions.extractStrings(list_s, encoding, unicodeLiterals).toString().replaceAll(";", "")); list_s = null;
            	                       if(e != null) exprs.add(actions.castExpr((e!=null?((PythonTree)e.tree):null))); e = null;
            	                     
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt120 >= 1 ) break loop120;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(120, input);
                        throw eee;
                }
                cnt120++;
            } while (true);



            // AST REWRITE
            // elements: JAPI
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1964:9: -> ^( JAPI[$japi_stmt.start, exprs, $expr::ctype, strings, \"JAPI\", conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1964:12: ^( JAPI[$japi_stmt.start, exprs, $expr::ctype, strings, \"JAPI\", conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new Tuple(JAPI, ((Token)retval.start), exprs, ((expr_scope)expr_stack.peek()).ctype, strings, "JAPI", conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "japi_stmt"

    public static class rdf_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rdf_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1971:1: rdf_stmt : RDF name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( RDF[$rdf_stmt.start, exprs, $expr::ctype, strings, \"RDF\", conn_name] ) ;
    public final PythonParser.rdf_stmt_return rdf_stmt() throws RecognitionException {
        PythonParser.rdf_stmt_return retval = new PythonParser.rdf_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token RDF255=null;
        Token s=null;
        List list_s=null;
        PythonParser.expr_return e = null;


        PythonTree name_tree=null;
        PythonTree RDF255_tree=null;
        PythonTree s_tree=null;
        RewriteRuleTokenStream stream_RDF=new RewriteRuleTokenStream(adaptor,"token RDF");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            ArrayList<String> strings = new ArrayList<String>();
            ArrayList<expr> exprs   = new ArrayList<expr>();
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1977:5: ( RDF name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( RDF[$rdf_stmt.start, exprs, $expr::ctype, strings, \"RDF\", conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1978:9: RDF name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+
            {
            RDF255=(Token)match(input,RDF,FOLLOW_RDF_in_rdf_stmt7210); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RDF.add(RDF255);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_rdf_stmt7214); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1981:11: (s+= STRING (e= expr[expr_contextType.Load] )? )+
            int cnt122=0;
            loop122:
            do {
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( (LA122_0==STRING) ) {
                    alt122=1;
                }


                switch (alt122) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1981:12: s+= STRING (e= expr[expr_contextType.Load] )?
            	    {
            	    s=(Token)match(input,STRING,FOLLOW_STRING_in_rdf_stmt7231); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_STRING.add(s);

            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s);

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1981:24: (e= expr[expr_contextType.Load] )?
            	    int alt121=2;
            	    alt121 = dfa121.predict(input);
            	    switch (alt121) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1981:25: e= expr[expr_contextType.Load]
            	            {
            	            pushFollow(FOLLOW_expr_in_rdf_stmt7238);
            	            e=expr(expr_contextType.Load);

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_expr.add(e.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	       strings.add(actions.extractStrings(list_s, encoding, unicodeLiterals).toString().replaceAll(";", "")); list_s = null;
            	                       if(e != null) exprs.add(actions.castExpr((e!=null?((PythonTree)e.tree):null))); e = null;
            	                     
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt122 >= 1 ) break loop122;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(122, input);
                        throw eee;
                }
                cnt122++;
            } while (true);



            // AST REWRITE
            // elements: RDF
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 1986:9: -> ^( RDF[$rdf_stmt.start, exprs, $expr::ctype, strings, \"RDF\", conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1986:12: ^( RDF[$rdf_stmt.start, exprs, $expr::ctype, strings, \"RDF\", conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new Tuple(RDF, ((Token)retval.start), exprs, ((expr_scope)expr_stack.peek()).ctype, strings, "RDF", conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "rdf_stmt"

    public static class sparql_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sparql_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1993:1: sparql_stmt : SPARQL name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( SPARQL[$sparql_stmt.start, exprs, $expr::ctype, strings, \"SPARQL\", conn_name] ) ;
    public final PythonParser.sparql_stmt_return sparql_stmt() throws RecognitionException {
        PythonParser.sparql_stmt_return retval = new PythonParser.sparql_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token name=null;
        Token SPARQL256=null;
        Token s=null;
        List list_s=null;
        PythonParser.expr_return e = null;


        PythonTree name_tree=null;
        PythonTree SPARQL256_tree=null;
        PythonTree s_tree=null;
        RewriteRuleTokenStream stream_SPARQL=new RewriteRuleTokenStream(adaptor,"token SPARQL");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

            ArrayList<String> strings = new ArrayList<String>();
            ArrayList<expr> exprs   = new ArrayList<expr>();
            Name conn_name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1999:5: ( SPARQL name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+ -> ^( SPARQL[$sparql_stmt.start, exprs, $expr::ctype, strings, \"SPARQL\", conn_name] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2000:9: SPARQL name= NAME (s+= STRING (e= expr[expr_contextType.Load] )? )+
            {
            SPARQL256=(Token)match(input,SPARQL,FOLLOW_SPARQL_in_sparql_stmt7324); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_SPARQL.add(SPARQL256);

            name=(Token)match(input,NAME,FOLLOW_NAME_in_sparql_stmt7328); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(name);

            if ( state.backtracking==0 ) {

                          conn_name = actions.makeNameNode(name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2003:11: (s+= STRING (e= expr[expr_contextType.Load] )? )+
            int cnt124=0;
            loop124:
            do {
                int alt124=2;
                int LA124_0 = input.LA(1);

                if ( (LA124_0==STRING) ) {
                    alt124=1;
                }


                switch (alt124) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2003:12: s+= STRING (e= expr[expr_contextType.Load] )?
            	    {
            	    s=(Token)match(input,STRING,FOLLOW_STRING_in_sparql_stmt7345); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_STRING.add(s);

            	    if (list_s==null) list_s=new ArrayList();
            	    list_s.add(s);

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2003:24: (e= expr[expr_contextType.Load] )?
            	    int alt123=2;
            	    alt123 = dfa123.predict(input);
            	    switch (alt123) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2003:25: e= expr[expr_contextType.Load]
            	            {
            	            pushFollow(FOLLOW_expr_in_sparql_stmt7352);
            	            e=expr(expr_contextType.Load);

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) stream_expr.add(e.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	       strings.add(actions.extractStrings(list_s, encoding, unicodeLiterals).toString().replaceAll(";", "")); list_s = null;
            	                       if(e != null) exprs.add(actions.castExpr((e!=null?((PythonTree)e.tree):null))); e = null;
            	                     
            	    }

            	    }
            	    break;

            	default :
            	    if ( cnt124 >= 1 ) break loop124;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(124, input);
                        throw eee;
                }
                cnt124++;
            } while (true);



            // AST REWRITE
            // elements: SPARQL
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 2008:9: -> ^( SPARQL[$sparql_stmt.start, exprs, $expr::ctype, strings, \"SPARQL\", conn_name] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2008:12: ^( SPARQL[$sparql_stmt.start, exprs, $expr::ctype, strings, \"SPARQL\", conn_name] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new Tuple(SPARQL, ((Token)retval.start), exprs, ((expr_scope)expr_stack.peek()).ctype, strings, "SPARQL", conn_name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sparql_stmt"

    public static class conn_stmt_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "conn_stmt"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2015:1: conn_stmt : CONNECTTO u+= STRING n+= STRING p+= STRING t+= STRING (m+= STRING )? (d+= NODEBUG )? -> ^( CONNECTTO[$conn_stmt.start, name, url, uname, pword, conntype, model, debug] ) ;
    public final PythonParser.conn_stmt_return conn_stmt() throws RecognitionException {
        PythonParser.conn_stmt_return retval = new PythonParser.conn_stmt_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token CONNECTTO257=null;
        Token u=null;
        Token n=null;
        Token p=null;
        Token t=null;
        Token m=null;
        Token d=null;
        List list_u=null;
        List list_n=null;
        List list_p=null;
        List list_t=null;
        List list_m=null;
        List list_d=null;

        PythonTree CONNECTTO257_tree=null;
        PythonTree u_tree=null;
        PythonTree n_tree=null;
        PythonTree p_tree=null;
        PythonTree t_tree=null;
        PythonTree m_tree=null;
        PythonTree d_tree=null;
        RewriteRuleTokenStream stream_NODEBUG=new RewriteRuleTokenStream(adaptor,"token NODEBUG");
        RewriteRuleTokenStream stream_CONNECTTO=new RewriteRuleTokenStream(adaptor,"token CONNECTTO");
        RewriteRuleTokenStream stream_STRING=new RewriteRuleTokenStream(adaptor,"token STRING");


            String url = null;
        	String uname = null;
        	String model = null;
        	String pword = null;
        	String conntype = null;
        	String debug = null;
            expr name = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2025:5: ( CONNECTTO u+= STRING n+= STRING p+= STRING t+= STRING (m+= STRING )? (d+= NODEBUG )? -> ^( CONNECTTO[$conn_stmt.start, name, url, uname, pword, conntype, model, debug] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2025:7: CONNECTTO u+= STRING n+= STRING p+= STRING t+= STRING (m+= STRING )? (d+= NODEBUG )?
            {
            CONNECTTO257=(Token)match(input,CONNECTTO,FOLLOW_CONNECTTO_in_conn_stmt7429); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_CONNECTTO.add(CONNECTTO257);

            u=(Token)match(input,STRING,FOLLOW_STRING_in_conn_stmt7435); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(u);

            if (list_u==null) list_u=new ArrayList();
            list_u.add(u);

            n=(Token)match(input,STRING,FOLLOW_STRING_in_conn_stmt7441); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(n);

            if (list_n==null) list_n=new ArrayList();
            list_n.add(n);

            p=(Token)match(input,STRING,FOLLOW_STRING_in_conn_stmt7447); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(p);

            if (list_p==null) list_p=new ArrayList();
            list_p.add(p);

            t=(Token)match(input,STRING,FOLLOW_STRING_in_conn_stmt7453); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_STRING.add(t);

            if (list_t==null) list_t=new ArrayList();
            list_t.add(t);

            if ( state.backtracking==0 ) {

                        name = new Name(CONNECTTO257, "RelConnection", expr_contextType.Store);
                        url = actions.extractStrings(list_u, encoding, unicodeLiterals).toString();
                        uname = actions.extractStrings(list_n, encoding, unicodeLiterals).toString();
                        pword = actions.extractStrings(list_p, encoding, unicodeLiterals).toString();
                        conntype = actions.extractStrings(list_t, encoding, unicodeLiterals).toString();
                        // We reset the model every connection. Since the user doesn't need one necessarily. 
                        model = ""; 
                        debug = "debug";
                    
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2036:7: (m+= STRING )?
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==STRING) ) {
                alt125=1;
            }
            switch (alt125) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2036:8: m+= STRING
                    {
                    m=(Token)match(input,STRING,FOLLOW_STRING_in_conn_stmt7475); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_STRING.add(m);

                    if (list_m==null) list_m=new ArrayList();
                    list_m.add(m);

                    if ( state.backtracking==0 ) {

                                model = actions.extractStrings(list_m, encoding, unicodeLiterals).toString();
                              
                    }

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2041:7: (d+= NODEBUG )?
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==NODEBUG) ) {
                alt126=1;
            }
            switch (alt126) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2041:8: d+= NODEBUG
                    {
                    d=(Token)match(input,NODEBUG,FOLLOW_NODEBUG_in_conn_stmt7507); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_NODEBUG.add(d);

                    if (list_d==null) list_d=new ArrayList();
                    list_d.add(d);

                    if ( state.backtracking==0 ) {

                                debug = "nodebug";
                              
                    }

                    }
                    break;

            }



            // AST REWRITE
            // elements: CONNECTTO
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (PythonTree)adaptor.nil();
            // 2048:7: -> ^( CONNECTTO[$conn_stmt.start, name, url, uname, pword, conntype, model, debug] )
            {
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2048:10: ^( CONNECTTO[$conn_stmt.start, name, url, uname, pword, conntype, model, debug] )
                {
                PythonTree root_1 = (PythonTree)adaptor.nil();
                root_1 = (PythonTree)adaptor.becomeRoot(new RelConnection(CONNECTTO, ((Token)retval.start), name, url, uname, pword, conntype, model, debug), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "conn_stmt"

    public static class listmaker_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "listmaker"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2058:1: listmaker[Token lbrack] : t+= test[$expr::ctype] ( list_for[gens] | ( options {greedy=true; } : COMMA t+= test[$expr::ctype] )* ) ( COMMA )? ;
    public final PythonParser.listmaker_return listmaker(Token lbrack) throws RecognitionException {
        PythonParser.listmaker_return retval = new PythonParser.listmaker_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA259=null;
        Token COMMA260=null;
        List list_t=null;
        PythonParser.list_for_return list_for258 = null;

        PythonParser.test_return t = null;
         t = null;
        PythonTree COMMA259_tree=null;
        PythonTree COMMA260_tree=null;


            List gens = new ArrayList();
            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2066:5: (t+= test[$expr::ctype] ( list_for[gens] | ( options {greedy=true; } : COMMA t+= test[$expr::ctype] )* ) ( COMMA )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2066:7: t+= test[$expr::ctype] ( list_for[gens] | ( options {greedy=true; } : COMMA t+= test[$expr::ctype] )* ) ( COMMA )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_test_in_listmaker7624);
            t=test(((expr_scope)expr_stack.peek()).ctype);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if (list_t==null) list_t=new ArrayList();
            list_t.add(t.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2067:9: ( list_for[gens] | ( options {greedy=true; } : COMMA t+= test[$expr::ctype] )* )
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( (LA128_0==FOR) ) {
                alt128=1;
            }
            else if ( (LA128_0==COMMA||LA128_0==RBRACK) ) {
                alt128=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 128, 0, input);

                throw nvae;
            }
            switch (alt128) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2067:10: list_for[gens]
                    {
                    pushFollow(FOLLOW_list_for_in_listmaker7636);
                    list_for258=list_for(gens);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, list_for258.getTree());
                    if ( state.backtracking==0 ) {

                                   Collections.reverse(gens);
                                   List<comprehension> c = gens;
                                   etype = new ListComp(((Token)retval.start), actions.castExpr(list_t.get(0)), c);
                               
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2073:11: ( options {greedy=true; } : COMMA t+= test[$expr::ctype] )*
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2073:11: ( options {greedy=true; } : COMMA t+= test[$expr::ctype] )*
                    loop127:
                    do {
                        int alt127=2;
                        int LA127_0 = input.LA(1);

                        if ( (LA127_0==COMMA) ) {
                            int LA127_1 = input.LA(2);

                            if ( (LA127_1==NAME||LA127_1==PRINT||(LA127_1>=LAMBDA && LA127_1<=NOT)||LA127_1==LPAREN||(LA127_1>=PLUS && LA127_1<=MINUS)||(LA127_1>=TILDE && LA127_1<=LBRACK)||LA127_1==LCURLY||(LA127_1>=BACKQUOTE && LA127_1<=CONNECTTO)) ) {
                                alt127=1;
                            }


                        }


                        switch (alt127) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2073:35: COMMA t+= test[$expr::ctype]
                    	    {
                    	    COMMA259=(Token)match(input,COMMA,FOLLOW_COMMA_in_listmaker7668); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA259_tree = (PythonTree)adaptor.create(COMMA259);
                    	    adaptor.addChild(root_0, COMMA259_tree);
                    	    }
                    	    pushFollow(FOLLOW_test_in_listmaker7672);
                    	    t=test(((expr_scope)expr_stack.peek()).ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    	    if (list_t==null) list_t=new ArrayList();
                    	    list_t.add(t.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop127;
                        }
                    } while (true);

                    if ( state.backtracking==0 ) {

                                     etype = new org.python.antlr.ast.List(lbrack, actions.castExprs(list_t), ((expr_scope)expr_stack.peek()).ctype);
                                 
                    }

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2077:11: ( COMMA )?
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( (LA129_0==COMMA) ) {
                alt129=1;
            }
            switch (alt129) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2077:12: COMMA
                    {
                    COMMA260=(Token)match(input,COMMA,FOLLOW_COMMA_in_listmaker7701); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA260_tree = (PythonTree)adaptor.create(COMMA260);
                    adaptor.addChild(root_0, COMMA260_tree);
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "listmaker"

    public static class testlist_gexp_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "testlist_gexp"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2081:1: testlist_gexp : t+= test[$expr::ctype] ( ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )* (c2= COMMA )? {...}? | -> test | ( comp_for[gens] ) ) ;
    public final PythonParser.testlist_gexp_return testlist_gexp() throws RecognitionException {
        PythonParser.testlist_gexp_return retval = new PythonParser.testlist_gexp_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token c1=null;
        Token c2=null;
        List list_t=null;
        PythonParser.comp_for_return comp_for261 = null;

        PythonParser.test_return t = null;
         t = null;
        PythonTree c1_tree=null;
        PythonTree c2_tree=null;
        RewriteRuleTokenStream stream_COMMA=new RewriteRuleTokenStream(adaptor,"token COMMA");
        RewriteRuleSubtreeStream stream_test=new RewriteRuleSubtreeStream(adaptor,"rule test");
        RewriteRuleSubtreeStream stream_comp_for=new RewriteRuleSubtreeStream(adaptor,"rule comp_for");

            expr etype = null;
            List gens = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2091:5: (t+= test[$expr::ctype] ( ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )* (c2= COMMA )? {...}? | -> test | ( comp_for[gens] ) ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2091:7: t+= test[$expr::ctype] ( ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )* (c2= COMMA )? {...}? | -> test | ( comp_for[gens] ) )
            {
            pushFollow(FOLLOW_test_in_testlist_gexp7733);
            t=test(((expr_scope)expr_stack.peek()).ctype);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_test.add(t.getTree());
            if (list_t==null) list_t=new ArrayList();
            list_t.add(t.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2092:9: ( ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )* (c2= COMMA )? {...}? | -> test | ( comp_for[gens] ) )
            int alt132=3;
            switch ( input.LA(1) ) {
            case COMMA:
                {
                alt132=1;
                }
                break;
            case RPAREN:
                {
                int LA132_2 = input.LA(2);

                if ( (( c1 != null || c2 != null )) ) {
                    alt132=1;
                }
                else if ( (true) ) {
                    alt132=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 132, 2, input);

                    throw nvae;
                }
                }
                break;
            case FOR:
                {
                alt132=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }

            switch (alt132) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2092:11: ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )* (c2= COMMA )? {...}?
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2092:11: ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )*
                    loop130:
                    do {
                        int alt130=2;
                        alt130 = dfa130.predict(input);
                        switch (alt130) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2092:28: c1= COMMA t+= test[$expr::ctype]
                    	    {
                    	    c1=(Token)match(input,COMMA,FOLLOW_COMMA_in_testlist_gexp7757); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_COMMA.add(c1);

                    	    pushFollow(FOLLOW_test_in_testlist_gexp7761);
                    	    t=test(((expr_scope)expr_stack.peek()).ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_test.add(t.getTree());
                    	    if (list_t==null) list_t=new ArrayList();
                    	    list_t.add(t.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop130;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2092:61: (c2= COMMA )?
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==COMMA) ) {
                        alt131=1;
                    }
                    switch (alt131) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2092:62: c2= COMMA
                            {
                            c2=(Token)match(input,COMMA,FOLLOW_COMMA_in_testlist_gexp7769); if (state.failed) return retval; 
                            if ( state.backtracking==0 ) stream_COMMA.add(c2);


                            }
                            break;

                    }

                    if ( !(( c1 != null || c2 != null )) ) {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        throw new FailedPredicateException(input, "testlist_gexp", " $c1 != null || $c2 != null ");
                    }
                    if ( state.backtracking==0 ) {

                                     etype = new Tuple(((Token)retval.start), actions.castExprs(list_t), ((expr_scope)expr_stack.peek()).ctype);
                                 
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2097:11: 
                    {

                    // AST REWRITE
                    // elements: test
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 2097:11: -> test
                    {
                        adaptor.addChild(root_0, stream_test.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2098:11: ( comp_for[gens] )
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2098:11: ( comp_for[gens] )
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2098:12: comp_for[gens]
                    {
                    pushFollow(FOLLOW_comp_for_in_testlist_gexp7823);
                    comp_for261=comp_for(gens);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_comp_for.add(comp_for261.getTree());
                    if ( state.backtracking==0 ) {

                                     Collections.reverse(gens);
                                     List<comprehension> c = gens;
                                     expr e = actions.castExpr(list_t.get(0));
                                     if (e instanceof Context) {
                                         ((Context)e).setContext(expr_contextType.Load);
                                     }
                                     etype = new GeneratorExp(((Token)retval.start), actions.castExpr(list_t.get(0)), c);
                                 
                    }

                    }


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (etype != null) {
                      retval.tree = etype;
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "testlist_gexp"

    public static class lambdef_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lambdef"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2113:1: lambdef : LAMBDA ( varargslist )? COLON test[expr_contextType.Load] ;
    public final PythonParser.lambdef_return lambdef() throws RecognitionException {
        PythonParser.lambdef_return retval = new PythonParser.lambdef_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token LAMBDA262=null;
        Token COLON264=null;
        PythonParser.varargslist_return varargslist263 = null;

        PythonParser.test_return test265 = null;


        PythonTree LAMBDA262_tree=null;
        PythonTree COLON264_tree=null;


            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2120:5: ( LAMBDA ( varargslist )? COLON test[expr_contextType.Load] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2120:7: LAMBDA ( varargslist )? COLON test[expr_contextType.Load]
            {
            root_0 = (PythonTree)adaptor.nil();

            LAMBDA262=(Token)match(input,LAMBDA,FOLLOW_LAMBDA_in_lambdef7887); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LAMBDA262_tree = (PythonTree)adaptor.create(LAMBDA262);
            adaptor.addChild(root_0, LAMBDA262_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2120:14: ( varargslist )?
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==NAME||LA133_0==LPAREN||(LA133_0>=STAR && LA133_0<=DOUBLESTAR)) ) {
                alt133=1;
            }
            switch (alt133) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2120:15: varargslist
                    {
                    pushFollow(FOLLOW_varargslist_in_lambdef7890);
                    varargslist263=varargslist();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, varargslist263.getTree());

                    }
                    break;

            }

            COLON264=(Token)match(input,COLON,FOLLOW_COLON_in_lambdef7894); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON264_tree = (PythonTree)adaptor.create(COLON264);
            adaptor.addChild(root_0, COLON264_tree);
            }
            pushFollow(FOLLOW_test_in_lambdef7896);
            test265=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, test265.getTree());
            if ( state.backtracking==0 ) {

                        arguments a = (varargslist263!=null?varargslist263.args:null);
                        if (a == null) {
                            a = new arguments(LAMBDA262, new ArrayList<expr>(), (Name)null, null, new ArrayList<expr>());
                        }
                        etype = new Lambda(LAMBDA262, a, actions.castExpr((test265!=null?((PythonTree)test265.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "lambdef"

    public static class trailer_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "trailer"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2131:1: trailer[Token begin, PythonTree ptree] : ( LPAREN ( arglist | ) RPAREN | LBRACK subscriptlist[$begin] RBRACK | DOT attr );
    public final PythonParser.trailer_return trailer(Token begin, PythonTree ptree) throws RecognitionException {
        PythonParser.trailer_return retval = new PythonParser.trailer_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token LPAREN266=null;
        Token RPAREN268=null;
        Token LBRACK269=null;
        Token RBRACK271=null;
        Token DOT272=null;
        PythonParser.arglist_return arglist267 = null;

        PythonParser.subscriptlist_return subscriptlist270 = null;

        PythonParser.attr_return attr273 = null;


        PythonTree LPAREN266_tree=null;
        PythonTree RPAREN268_tree=null;
        PythonTree LBRACK269_tree=null;
        PythonTree RBRACK271_tree=null;
        PythonTree DOT272_tree=null;


            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2140:5: ( LPAREN ( arglist | ) RPAREN | LBRACK subscriptlist[$begin] RBRACK | DOT attr )
            int alt135=3;
            switch ( input.LA(1) ) {
            case LPAREN:
                {
                alt135=1;
                }
                break;
            case LBRACK:
                {
                alt135=2;
                }
                break;
            case DOT:
                {
                alt135=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 135, 0, input);

                throw nvae;
            }

            switch (alt135) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2140:7: LPAREN ( arglist | ) RPAREN
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LPAREN266=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_trailer7935); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN266_tree = (PythonTree)adaptor.create(LPAREN266);
                    adaptor.addChild(root_0, LPAREN266_tree);
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2141:7: ( arglist | )
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( (LA134_0==NAME||LA134_0==NOT||LA134_0==LPAREN||(LA134_0>=PLUS && LA134_0<=MINUS)||(LA134_0>=TILDE && LA134_0<=LBRACK)||LA134_0==LCURLY||LA134_0==BACKQUOTE) ) {
                        alt134=1;
                    }
                    else if ( (LA134_0==PRINT) && ((printFunction))) {
                        alt134=1;
                    }
                    else if ( (LA134_0==LAMBDA||(LA134_0>=STAR && LA134_0<=DOUBLESTAR)||(LA134_0>=INT && LA134_0<=CONNECTTO)) ) {
                        alt134=1;
                    }
                    else if ( (LA134_0==RPAREN) ) {
                        alt134=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 134, 0, input);

                        throw nvae;
                    }
                    switch (alt134) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2141:8: arglist
                            {
                            pushFollow(FOLLOW_arglist_in_trailer7944);
                            arglist267=arglist();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, arglist267.getTree());
                            if ( state.backtracking==0 ) {

                                         etype = new Call(begin, actions.castExpr(ptree), actions.castExprs((arglist267!=null?arglist267.args:null)),
                                           actions.makeKeywords((arglist267!=null?arglist267.keywords:null)), (arglist267!=null?arglist267.starargs:null), (arglist267!=null?arglist267.kwargs:null));
                                     
                            }

                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2147:8: 
                            {
                            if ( state.backtracking==0 ) {

                                         etype = new Call(begin, actions.castExpr(ptree), new ArrayList<expr>(), new ArrayList<keyword>(), null, null);
                                     
                            }

                            }
                            break;

                    }

                    RPAREN268=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_trailer7986); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN268_tree = (PythonTree)adaptor.create(RPAREN268);
                    adaptor.addChild(root_0, RPAREN268_tree);
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2152:7: LBRACK subscriptlist[$begin] RBRACK
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    LBRACK269=(Token)match(input,LBRACK,FOLLOW_LBRACK_in_trailer7994); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LBRACK269_tree = (PythonTree)adaptor.create(LBRACK269);
                    adaptor.addChild(root_0, LBRACK269_tree);
                    }
                    pushFollow(FOLLOW_subscriptlist_in_trailer7996);
                    subscriptlist270=subscriptlist(begin);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, subscriptlist270.getTree());
                    RBRACK271=(Token)match(input,RBRACK,FOLLOW_RBRACK_in_trailer7999); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RBRACK271_tree = (PythonTree)adaptor.create(RBRACK271);
                    adaptor.addChild(root_0, RBRACK271_tree);
                    }
                    if ( state.backtracking==0 ) {

                                etype = new Subscript(begin, actions.castExpr(ptree), actions.castSlice((subscriptlist270!=null?((PythonTree)subscriptlist270.tree):null)), ((expr_scope)expr_stack.peek()).ctype);
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2156:7: DOT attr
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    DOT272=(Token)match(input,DOT,FOLLOW_DOT_in_trailer8015); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOT272_tree = (PythonTree)adaptor.create(DOT272);
                    adaptor.addChild(root_0, DOT272_tree);
                    }
                    pushFollow(FOLLOW_attr_in_trailer8017);
                    attr273=attr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, attr273.getTree());
                    if ( state.backtracking==0 ) {

                                etype = new Attribute(begin, actions.castExpr(ptree), new Name((attr273!=null?((PythonTree)attr273.tree):null), (attr273!=null?input.toString(attr273.start,attr273.stop):null), expr_contextType.Load), ((expr_scope)expr_stack.peek()).ctype);
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (etype != null) {
                      retval.tree = etype;
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "trailer"

    public static class subscriptlist_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "subscriptlist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2163:1: subscriptlist[Token begin] : sub+= subscript ( options {greedy=true; } : c1= COMMA sub+= subscript )* (c2= COMMA )? ;
    public final PythonParser.subscriptlist_return subscriptlist(Token begin) throws RecognitionException {
        PythonParser.subscriptlist_return retval = new PythonParser.subscriptlist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token c1=null;
        Token c2=null;
        List list_sub=null;
        PythonParser.subscript_return sub = null;
         sub = null;
        PythonTree c1_tree=null;
        PythonTree c2_tree=null;


            slice sltype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2170:5: (sub+= subscript ( options {greedy=true; } : c1= COMMA sub+= subscript )* (c2= COMMA )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2170:7: sub+= subscript ( options {greedy=true; } : c1= COMMA sub+= subscript )* (c2= COMMA )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_subscript_in_subscriptlist8056);
            sub=subscript();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, sub.getTree());
            if (list_sub==null) list_sub=new ArrayList();
            list_sub.add(sub.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2170:22: ( options {greedy=true; } : c1= COMMA sub+= subscript )*
            loop136:
            do {
                int alt136=2;
                int LA136_0 = input.LA(1);

                if ( (LA136_0==COMMA) ) {
                    int LA136_1 = input.LA(2);

                    if ( ((LA136_1>=NAME && LA136_1<=PRINT)||(LA136_1>=LAMBDA && LA136_1<=NOT)||LA136_1==LPAREN||LA136_1==COLON||(LA136_1>=PLUS && LA136_1<=MINUS)||(LA136_1>=TILDE && LA136_1<=LBRACK)||LA136_1==LCURLY||(LA136_1>=BACKQUOTE && LA136_1<=CONNECTTO)) ) {
                        alt136=1;
                    }


                }


                switch (alt136) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2170:46: c1= COMMA sub+= subscript
            	    {
            	    c1=(Token)match(input,COMMA,FOLLOW_COMMA_in_subscriptlist8068); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    c1_tree = (PythonTree)adaptor.create(c1);
            	    adaptor.addChild(root_0, c1_tree);
            	    }
            	    pushFollow(FOLLOW_subscript_in_subscriptlist8072);
            	    sub=subscript();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, sub.getTree());
            	    if (list_sub==null) list_sub=new ArrayList();
            	    list_sub.add(sub.getTree());


            	    }
            	    break;

            	default :
            	    break loop136;
                }
            } while (true);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2170:72: (c2= COMMA )?
            int alt137=2;
            int LA137_0 = input.LA(1);

            if ( (LA137_0==COMMA) ) {
                alt137=1;
            }
            switch (alt137) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2170:73: c2= COMMA
                    {
                    c2=(Token)match(input,COMMA,FOLLOW_COMMA_in_subscriptlist8079); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c2_tree = (PythonTree)adaptor.create(c2);
                    adaptor.addChild(root_0, c2_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        sltype = actions.makeSliceType(begin, c1, c2, list_sub);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = sltype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "subscriptlist"

    public static class subscript_return extends ParserRuleReturnScope {
        public slice sltype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "subscript"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2177:1: subscript returns [slice sltype] : (d1= DOT DOT DOT | ( test[null] COLON )=>lower= test[expr_contextType.Load] (c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )? )? | ( COLON )=>c2= COLON (upper2= test[expr_contextType.Load] )? ( sliceop )? | test[expr_contextType.Load] );
    public final PythonParser.subscript_return subscript() throws RecognitionException {
        PythonParser.subscript_return retval = new PythonParser.subscript_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token d1=null;
        Token c1=null;
        Token c2=null;
        Token DOT274=null;
        Token DOT275=null;
        PythonParser.test_return lower = null;

        PythonParser.test_return upper1 = null;

        PythonParser.test_return upper2 = null;

        PythonParser.sliceop_return sliceop276 = null;

        PythonParser.sliceop_return sliceop277 = null;

        PythonParser.test_return test278 = null;


        PythonTree d1_tree=null;
        PythonTree c1_tree=null;
        PythonTree c2_tree=null;
        PythonTree DOT274_tree=null;
        PythonTree DOT275_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2182:5: (d1= DOT DOT DOT | ( test[null] COLON )=>lower= test[expr_contextType.Load] (c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )? )? | ( COLON )=>c2= COLON (upper2= test[expr_contextType.Load] )? ( sliceop )? | test[expr_contextType.Load] )
            int alt143=4;
            alt143 = dfa143.predict(input);
            switch (alt143) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2182:7: d1= DOT DOT DOT
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    d1=(Token)match(input,DOT,FOLLOW_DOT_in_subscript8122); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    d1_tree = (PythonTree)adaptor.create(d1);
                    adaptor.addChild(root_0, d1_tree);
                    }
                    DOT274=(Token)match(input,DOT,FOLLOW_DOT_in_subscript8124); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOT274_tree = (PythonTree)adaptor.create(DOT274);
                    adaptor.addChild(root_0, DOT274_tree);
                    }
                    DOT275=(Token)match(input,DOT,FOLLOW_DOT_in_subscript8126); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOT275_tree = (PythonTree)adaptor.create(DOT275);
                    adaptor.addChild(root_0, DOT275_tree);
                    }
                    if ( state.backtracking==0 ) {

                                retval.sltype = new Ellipsis(d1);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2186:7: ( test[null] COLON )=>lower= test[expr_contextType.Load] (c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )? )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_subscript8156);
                    lower=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lower.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2187:41: (c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )? )?
                    int alt140=2;
                    int LA140_0 = input.LA(1);

                    if ( (LA140_0==COLON) ) {
                        alt140=1;
                    }
                    switch (alt140) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2187:42: c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )?
                            {
                            c1=(Token)match(input,COLON,FOLLOW_COLON_in_subscript8162); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            c1_tree = (PythonTree)adaptor.create(c1);
                            adaptor.addChild(root_0, c1_tree);
                            }
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2187:51: (upper1= test[expr_contextType.Load] )?
                            int alt138=2;
                            int LA138_0 = input.LA(1);

                            if ( (LA138_0==NAME||LA138_0==NOT||LA138_0==LPAREN||(LA138_0>=PLUS && LA138_0<=MINUS)||(LA138_0>=TILDE && LA138_0<=LBRACK)||LA138_0==LCURLY||LA138_0==BACKQUOTE) ) {
                                alt138=1;
                            }
                            else if ( (LA138_0==PRINT) && ((printFunction))) {
                                alt138=1;
                            }
                            else if ( (LA138_0==LAMBDA||(LA138_0>=INT && LA138_0<=CONNECTTO)) ) {
                                alt138=1;
                            }
                            switch (alt138) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2187:52: upper1= test[expr_contextType.Load]
                                    {
                                    pushFollow(FOLLOW_test_in_subscript8167);
                                    upper1=test(expr_contextType.Load);

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) adaptor.addChild(root_0, upper1.getTree());

                                    }
                                    break;

                            }

                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2187:89: ( sliceop )?
                            int alt139=2;
                            int LA139_0 = input.LA(1);

                            if ( (LA139_0==COLON) ) {
                                alt139=1;
                            }
                            switch (alt139) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2187:90: sliceop
                                    {
                                    pushFollow(FOLLOW_sliceop_in_subscript8173);
                                    sliceop276=sliceop();

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) adaptor.addChild(root_0, sliceop276.getTree());

                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                retval.sltype = actions.makeSubscript((lower!=null?((PythonTree)lower.tree):null), c1, (upper1!=null?((PythonTree)upper1.tree):null), (sliceop276!=null?((PythonTree)sliceop276.tree):null));
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2191:7: ( COLON )=>c2= COLON (upper2= test[expr_contextType.Load] )? ( sliceop )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    c2=(Token)match(input,COLON,FOLLOW_COLON_in_subscript8204); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c2_tree = (PythonTree)adaptor.create(c2);
                    adaptor.addChild(root_0, c2_tree);
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2192:16: (upper2= test[expr_contextType.Load] )?
                    int alt141=2;
                    int LA141_0 = input.LA(1);

                    if ( (LA141_0==NAME||LA141_0==NOT||LA141_0==LPAREN||(LA141_0>=PLUS && LA141_0<=MINUS)||(LA141_0>=TILDE && LA141_0<=LBRACK)||LA141_0==LCURLY||LA141_0==BACKQUOTE) ) {
                        alt141=1;
                    }
                    else if ( (LA141_0==PRINT) && ((printFunction))) {
                        alt141=1;
                    }
                    else if ( (LA141_0==LAMBDA||(LA141_0>=INT && LA141_0<=CONNECTTO)) ) {
                        alt141=1;
                    }
                    switch (alt141) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2192:17: upper2= test[expr_contextType.Load]
                            {
                            pushFollow(FOLLOW_test_in_subscript8209);
                            upper2=test(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, upper2.getTree());

                            }
                            break;

                    }

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2192:54: ( sliceop )?
                    int alt142=2;
                    int LA142_0 = input.LA(1);

                    if ( (LA142_0==COLON) ) {
                        alt142=1;
                    }
                    switch (alt142) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2192:55: sliceop
                            {
                            pushFollow(FOLLOW_sliceop_in_subscript8215);
                            sliceop277=sliceop();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, sliceop277.getTree());

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                retval.sltype = actions.makeSubscript(null, c2, (upper2!=null?((PythonTree)upper2.tree):null), (sliceop277!=null?((PythonTree)sliceop277.tree):null));
                            
                    }

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2196:7: test[expr_contextType.Load]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_subscript8233);
                    test278=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, test278.getTree());
                    if ( state.backtracking==0 ) {

                                retval.sltype = new Index((test278!=null?((Token)test278.start):null), actions.castExpr((test278!=null?((PythonTree)test278.tree):null)));
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  retval.tree = retval.sltype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "subscript"

    public static class sliceop_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "sliceop"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2203:1: sliceop : COLON ( test[expr_contextType.Load] -> test | ) ;
    public final PythonParser.sliceop_return sliceop() throws RecognitionException {
        PythonParser.sliceop_return retval = new PythonParser.sliceop_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COLON279=null;
        PythonParser.test_return test280 = null;


        PythonTree COLON279_tree=null;
        RewriteRuleTokenStream stream_COLON=new RewriteRuleTokenStream(adaptor,"token COLON");
        RewriteRuleSubtreeStream stream_test=new RewriteRuleSubtreeStream(adaptor,"rule test");

            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2212:5: ( COLON ( test[expr_contextType.Load] -> test | ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2212:7: COLON ( test[expr_contextType.Load] -> test | )
            {
            COLON279=(Token)match(input,COLON,FOLLOW_COLON_in_sliceop8270); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_COLON.add(COLON279);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2213:6: ( test[expr_contextType.Load] -> test | )
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( (LA144_0==NAME||LA144_0==NOT||LA144_0==LPAREN||(LA144_0>=PLUS && LA144_0<=MINUS)||(LA144_0>=TILDE && LA144_0<=LBRACK)||LA144_0==LCURLY||LA144_0==BACKQUOTE) ) {
                alt144=1;
            }
            else if ( (LA144_0==PRINT) && ((printFunction))) {
                alt144=1;
            }
            else if ( (LA144_0==LAMBDA||(LA144_0>=INT && LA144_0<=CONNECTTO)) ) {
                alt144=1;
            }
            else if ( (LA144_0==COMMA||LA144_0==RBRACK) ) {
                alt144=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }
            switch (alt144) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2213:7: test[expr_contextType.Load]
                    {
                    pushFollow(FOLLOW_test_in_sliceop8278);
                    test280=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_test.add(test280.getTree());


                    // AST REWRITE
                    // elements: test
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    if ( state.backtracking==0 ) {
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (PythonTree)adaptor.nil();
                    // 2214:5: -> test
                    {
                        adaptor.addChild(root_0, stream_test.nextTree());

                    }

                    retval.tree = root_0;}
                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2216:8: 
                    {
                    if ( state.backtracking==0 ) {

                                 etype = new Name(COLON279, "None", expr_contextType.Load);
                             
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (etype != null) {
                      retval.tree = etype;
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "sliceop"

    public static class exprlist_return extends ParserRuleReturnScope {
        public expr etype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "exprlist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2223:1: exprlist[expr_contextType ctype] returns [expr etype] : ( ( expr[null] COMMA )=>e+= expr[ctype] ( options {k=2; } : COMMA e+= expr[ctype] )* ( COMMA )? | expr[ctype] );
    public final PythonParser.exprlist_return exprlist(expr_contextType ctype) throws RecognitionException {
        PythonParser.exprlist_return retval = new PythonParser.exprlist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA281=null;
        Token COMMA282=null;
        List list_e=null;
        PythonParser.expr_return expr283 = null;

        PythonParser.expr_return e = null;
         e = null;
        PythonTree COMMA281_tree=null;
        PythonTree COMMA282_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:5: ( ( expr[null] COMMA )=>e+= expr[ctype] ( options {k=2; } : COMMA e+= expr[ctype] )* ( COMMA )? | expr[ctype] )
            int alt147=2;
            alt147 = dfa147.predict(input);
            switch (alt147) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:7: ( expr[null] COMMA )=>e+= expr[ctype] ( options {k=2; } : COMMA e+= expr[ctype] )* ( COMMA )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_expr_in_exprlist8349);
                    e=expr(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    if (list_e==null) list_e=new ArrayList();
                    list_e.add(e.getTree());

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:44: ( options {k=2; } : COMMA e+= expr[ctype] )*
                    loop145:
                    do {
                        int alt145=2;
                        alt145 = dfa145.predict(input);
                        switch (alt145) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:61: COMMA e+= expr[ctype]
                    	    {
                    	    COMMA281=(Token)match(input,COMMA,FOLLOW_COMMA_in_exprlist8361); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA281_tree = (PythonTree)adaptor.create(COMMA281);
                    	    adaptor.addChild(root_0, COMMA281_tree);
                    	    }
                    	    pushFollow(FOLLOW_expr_in_exprlist8365);
                    	    e=expr(ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    	    if (list_e==null) list_e=new ArrayList();
                    	    list_e.add(e.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop145;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:84: ( COMMA )?
                    int alt146=2;
                    int LA146_0 = input.LA(1);

                    if ( (LA146_0==COMMA) ) {
                        alt146=1;
                    }
                    switch (alt146) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:85: COMMA
                            {
                            COMMA282=(Token)match(input,COMMA,FOLLOW_COMMA_in_exprlist8371); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA282_tree = (PythonTree)adaptor.create(COMMA282);
                            adaptor.addChild(root_0, COMMA282_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                 retval.etype = new Tuple(((Token)retval.start), actions.castExprs(list_e), ctype);
                             
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2229:7: expr[ctype]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_expr_in_exprlist8390);
                    expr283=expr(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expr283.getTree());
                    if ( state.backtracking==0 ) {

                              retval.etype = actions.castExpr((expr283!=null?((PythonTree)expr283.tree):null));
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "exprlist"

    public static class del_list_return extends ParserRuleReturnScope {
        public List<expr> etypes;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "del_list"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2237:1: del_list returns [List<expr> etypes] : e+= expr[expr_contextType.Del] ( options {k=2; } : COMMA e+= expr[expr_contextType.Del] )* ( COMMA )? ;
    public final PythonParser.del_list_return del_list() throws RecognitionException {
        PythonParser.del_list_return retval = new PythonParser.del_list_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA284=null;
        Token COMMA285=null;
        List list_e=null;
        PythonParser.expr_return e = null;
         e = null;
        PythonTree COMMA284_tree=null;
        PythonTree COMMA285_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2239:5: (e+= expr[expr_contextType.Del] ( options {k=2; } : COMMA e+= expr[expr_contextType.Del] )* ( COMMA )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2239:7: e+= expr[expr_contextType.Del] ( options {k=2; } : COMMA e+= expr[expr_contextType.Del] )* ( COMMA )?
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_expr_in_del_list8428);
            e=expr(expr_contextType.Del);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if (list_e==null) list_e=new ArrayList();
            list_e.add(e.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2239:37: ( options {k=2; } : COMMA e+= expr[expr_contextType.Del] )*
            loop148:
            do {
                int alt148=2;
                alt148 = dfa148.predict(input);
                switch (alt148) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2239:54: COMMA e+= expr[expr_contextType.Del]
            	    {
            	    COMMA284=(Token)match(input,COMMA,FOLLOW_COMMA_in_del_list8440); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    COMMA284_tree = (PythonTree)adaptor.create(COMMA284);
            	    adaptor.addChild(root_0, COMMA284_tree);
            	    }
            	    pushFollow(FOLLOW_expr_in_del_list8444);
            	    e=expr(expr_contextType.Del);

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            	    if (list_e==null) list_e=new ArrayList();
            	    list_e.add(e.getTree());


            	    }
            	    break;

            	default :
            	    break loop148;
                }
            } while (true);

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2239:92: ( COMMA )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==COMMA) ) {
                alt149=1;
            }
            switch (alt149) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2239:93: COMMA
                    {
                    COMMA285=(Token)match(input,COMMA,FOLLOW_COMMA_in_del_list8450); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    COMMA285_tree = (PythonTree)adaptor.create(COMMA285);
                    adaptor.addChild(root_0, COMMA285_tree);
                    }

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        retval.etypes = actions.makeDeleteList(list_e);
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "del_list"

    public static class testlist_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "testlist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2246:1: testlist[expr_contextType ctype] : ( ( test[null] COMMA )=>t+= test[ctype] ( options {k=2; } : COMMA t+= test[ctype] )* ( COMMA )? | test[ctype] );
    public final PythonParser.testlist_return testlist(expr_contextType ctype) throws RecognitionException {
        PythonParser.testlist_return retval = new PythonParser.testlist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA286=null;
        Token COMMA287=null;
        List list_t=null;
        PythonParser.test_return test288 = null;

        PythonParser.test_return t = null;
         t = null;
        PythonTree COMMA286_tree=null;
        PythonTree COMMA287_tree=null;


            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2255:5: ( ( test[null] COMMA )=>t+= test[ctype] ( options {k=2; } : COMMA t+= test[ctype] )* ( COMMA )? | test[ctype] )
            int alt152=2;
            alt152 = dfa152.predict(input);
            switch (alt152) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2255:7: ( test[null] COMMA )=>t+= test[ctype] ( options {k=2; } : COMMA t+= test[ctype] )* ( COMMA )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_testlist8503);
                    t=test(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if (list_t==null) list_t=new ArrayList();
                    list_t.add(t.getTree());

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2256:22: ( options {k=2; } : COMMA t+= test[ctype] )*
                    loop150:
                    do {
                        int alt150=2;
                        alt150 = dfa150.predict(input);
                        switch (alt150) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2256:39: COMMA t+= test[ctype]
                    	    {
                    	    COMMA286=(Token)match(input,COMMA,FOLLOW_COMMA_in_testlist8515); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA286_tree = (PythonTree)adaptor.create(COMMA286);
                    	    adaptor.addChild(root_0, COMMA286_tree);
                    	    }
                    	    pushFollow(FOLLOW_test_in_testlist8519);
                    	    t=test(ctype);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    	    if (list_t==null) list_t=new ArrayList();
                    	    list_t.add(t.getTree());


                    	    }
                    	    break;

                    	default :
                    	    break loop150;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2256:62: ( COMMA )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==COMMA) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2256:63: COMMA
                            {
                            COMMA287=(Token)match(input,COMMA,FOLLOW_COMMA_in_testlist8525); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA287_tree = (PythonTree)adaptor.create(COMMA287);
                            adaptor.addChild(root_0, COMMA287_tree);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                etype = new Tuple(((Token)retval.start), actions.castExprs(list_t), ctype);
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2260:7: test[ctype]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_test_in_testlist8543);
                    test288=test(ctype);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, test288.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (etype != null) {
                      retval.tree = etype;
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "testlist"

    public static class dictorsetmaker_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "dictorsetmaker"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2267:1: dictorsetmaker[Token lcurly] : k+= test[expr_contextType.Load] ( ( COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* ) | ( COMMA k+= test[expr_contextType.Load] )* ) ( COMMA )? | comp_for[gens] ) ;
    public final PythonParser.dictorsetmaker_return dictorsetmaker(Token lcurly) throws RecognitionException {
        PythonParser.dictorsetmaker_return retval = new PythonParser.dictorsetmaker_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COLON289=null;
        Token COMMA291=null;
        Token COLON292=null;
        Token COMMA293=null;
        Token COMMA294=null;
        List list_k=null;
        List list_v=null;
        PythonParser.comp_for_return comp_for290 = null;

        PythonParser.comp_for_return comp_for295 = null;

        PythonParser.test_return k = null;
         k = null;
        PythonParser.test_return v = null;
         v = null;
        PythonTree COLON289_tree=null;
        PythonTree COMMA291_tree=null;
        PythonTree COLON292_tree=null;
        PythonTree COMMA293_tree=null;
        PythonTree COMMA294_tree=null;


            List gens = new ArrayList();
            expr etype = null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2277:5: (k+= test[expr_contextType.Load] ( ( COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* ) | ( COMMA k+= test[expr_contextType.Load] )* ) ( COMMA )? | comp_for[gens] ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2277:7: k+= test[expr_contextType.Load] ( ( COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* ) | ( COMMA k+= test[expr_contextType.Load] )* ) ( COMMA )? | comp_for[gens] )
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_test_in_dictorsetmaker8578);
            k=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());
            if (list_k==null) list_k=new ArrayList();
            list_k.add(k.getTree());

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2278:10: ( ( COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* ) | ( COMMA k+= test[expr_contextType.Load] )* ) ( COMMA )? | comp_for[gens] )
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==COLON||LA158_0==COMMA||LA158_0==RCURLY) ) {
                alt158=1;
            }
            else if ( (LA158_0==FOR) ) {
                alt158=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }
            switch (alt158) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2279:14: ( COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* ) | ( COMMA k+= test[expr_contextType.Load] )* ) ( COMMA )?
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2279:14: ( COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* ) | ( COMMA k+= test[expr_contextType.Load] )* )
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( (LA156_0==COLON) ) {
                        alt156=1;
                    }
                    else if ( (LA156_0==COMMA||LA156_0==RCURLY) ) {
                        alt156=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 156, 0, input);

                        throw nvae;
                    }
                    switch (alt156) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2279:15: COLON v+= test[expr_contextType.Load] ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* )
                            {
                            COLON289=(Token)match(input,COLON,FOLLOW_COLON_in_dictorsetmaker8606); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COLON289_tree = (PythonTree)adaptor.create(COLON289);
                            adaptor.addChild(root_0, COLON289_tree);
                            }
                            pushFollow(FOLLOW_test_in_dictorsetmaker8610);
                            v=test(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, v.getTree());
                            if (list_v==null) list_v=new ArrayList();
                            list_v.add(v.getTree());

                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2280:16: ( comp_for[gens] | ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )* )
                            int alt154=2;
                            int LA154_0 = input.LA(1);

                            if ( (LA154_0==FOR) ) {
                                alt154=1;
                            }
                            else if ( (LA154_0==COMMA||LA154_0==RCURLY) ) {
                                alt154=2;
                            }
                            else {
                                if (state.backtracking>0) {state.failed=true; return retval;}
                                NoViableAltException nvae =
                                    new NoViableAltException("", 154, 0, input);

                                throw nvae;
                            }
                            switch (alt154) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2280:18: comp_for[gens]
                                    {
                                    pushFollow(FOLLOW_comp_for_in_dictorsetmaker8630);
                                    comp_for290=comp_for(gens);

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_for290.getTree());
                                    if ( state.backtracking==0 ) {

                                                           Collections.reverse(gens);
                                                           List<comprehension> c = gens;
                                                           etype = new DictComp(((Token)retval.start), actions.castExpr(list_k.get(0)), actions.castExpr(list_v.get(0)), c);
                                                       
                                    }

                                    }
                                    break;
                                case 2 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2286:18: ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )*
                                    {
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2286:18: ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )*
                                    loop153:
                                    do {
                                        int alt153=2;
                                        alt153 = dfa153.predict(input);
                                        switch (alt153) {
                                    	case 1 :
                                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2286:34: COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load]
                                    	    {
                                    	    COMMA291=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictorsetmaker8677); if (state.failed) return retval;
                                    	    if ( state.backtracking==0 ) {
                                    	    COMMA291_tree = (PythonTree)adaptor.create(COMMA291);
                                    	    adaptor.addChild(root_0, COMMA291_tree);
                                    	    }
                                    	    pushFollow(FOLLOW_test_in_dictorsetmaker8681);
                                    	    k=test(expr_contextType.Load);

                                    	    state._fsp--;
                                    	    if (state.failed) return retval;
                                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());
                                    	    if (list_k==null) list_k=new ArrayList();
                                    	    list_k.add(k.getTree());

                                    	    COLON292=(Token)match(input,COLON,FOLLOW_COLON_in_dictorsetmaker8684); if (state.failed) return retval;
                                    	    if ( state.backtracking==0 ) {
                                    	    COLON292_tree = (PythonTree)adaptor.create(COLON292);
                                    	    adaptor.addChild(root_0, COLON292_tree);
                                    	    }
                                    	    pushFollow(FOLLOW_test_in_dictorsetmaker8688);
                                    	    v=test(expr_contextType.Load);

                                    	    state._fsp--;
                                    	    if (state.failed) return retval;
                                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, v.getTree());
                                    	    if (list_v==null) list_v=new ArrayList();
                                    	    list_v.add(v.getTree());


                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop153;
                                        }
                                    } while (true);

                                    if ( state.backtracking==0 ) {

                                                           etype = new Dict(lcurly, actions.castExprs(list_k), actions.castExprs(list_v));
                                                       
                                    }

                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2291:15: ( COMMA k+= test[expr_contextType.Load] )*
                            {
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2291:15: ( COMMA k+= test[expr_contextType.Load] )*
                            loop155:
                            do {
                                int alt155=2;
                                int LA155_0 = input.LA(1);

                                if ( (LA155_0==COMMA) ) {
                                    int LA155_1 = input.LA(2);

                                    if ( (LA155_1==NAME||LA155_1==PRINT||(LA155_1>=LAMBDA && LA155_1<=NOT)||LA155_1==LPAREN||(LA155_1>=PLUS && LA155_1<=MINUS)||(LA155_1>=TILDE && LA155_1<=LBRACK)||LA155_1==LCURLY||(LA155_1>=BACKQUOTE && LA155_1<=CONNECTTO)) ) {
                                        alt155=1;
                                    }


                                }


                                switch (alt155) {
                            	case 1 :
                            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2291:16: COMMA k+= test[expr_contextType.Load]
                            	    {
                            	    COMMA293=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictorsetmaker8744); if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) {
                            	    COMMA293_tree = (PythonTree)adaptor.create(COMMA293);
                            	    adaptor.addChild(root_0, COMMA293_tree);
                            	    }
                            	    pushFollow(FOLLOW_test_in_dictorsetmaker8748);
                            	    k=test(expr_contextType.Load);

                            	    state._fsp--;
                            	    if (state.failed) return retval;
                            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());
                            	    if (list_k==null) list_k=new ArrayList();
                            	    list_k.add(k.getTree());


                            	    }
                            	    break;

                            	default :
                            	    break loop155;
                                }
                            } while (true);

                            if ( state.backtracking==0 ) {

                                                etype = new Set(lcurly, actions.castExprs(list_k));
                                            
                            }

                            }
                            break;

                    }

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2296:14: ( COMMA )?
                    int alt157=2;
                    int LA157_0 = input.LA(1);

                    if ( (LA157_0==COMMA) ) {
                        alt157=1;
                    }
                    switch (alt157) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2296:15: COMMA
                            {
                            COMMA294=(Token)match(input,COMMA,FOLLOW_COMMA_in_dictorsetmaker8798); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA294_tree = (PythonTree)adaptor.create(COMMA294);
                            adaptor.addChild(root_0, COMMA294_tree);
                            }

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2297:12: comp_for[gens]
                    {
                    pushFollow(FOLLOW_comp_for_in_dictorsetmaker8813);
                    comp_for295=comp_for(gens);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_for295.getTree());
                    if ( state.backtracking==0 ) {

                                     Collections.reverse(gens);
                                     List<comprehension> c = gens;
                                     expr e = actions.castExpr(list_k.get(0));
                                     if (e instanceof Context) {
                                         ((Context)e).setContext(expr_contextType.Load);
                                     }
                                     etype = new SetComp(lcurly, actions.castExpr(list_k.get(0)), c);
                                 
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  if (etype != null) {
                      retval.tree = etype;
                  }

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "dictorsetmaker"

    public static class classdef_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "classdef"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2311:1: classdef : ( decorators )? ( PERSISTIT conn_name= NAME )? CLASS (class_name= NAME ) ( LPAREN ( testlist[expr_contextType.Load] )? RPAREN )? COLON suite[false] ;
    public final PythonParser.classdef_return classdef() throws RecognitionException {
        PythonParser.classdef_return retval = new PythonParser.classdef_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token conn_name=null;
        Token class_name=null;
        Token PERSISTIT297=null;
        Token CLASS298=null;
        Token LPAREN299=null;
        Token RPAREN301=null;
        Token COLON302=null;
        PythonParser.decorators_return decorators296 = null;

        PythonParser.testlist_return testlist300 = null;

        PythonParser.suite_return suite303 = null;


        PythonTree conn_name_tree=null;
        PythonTree class_name_tree=null;
        PythonTree PERSISTIT297_tree=null;
        PythonTree CLASS298_tree=null;
        PythonTree LPAREN299_tree=null;
        PythonTree RPAREN301_tree=null;
        PythonTree COLON302_tree=null;


            stmt stype = null;
            boolean persistant = false; 
            Name conn_name_node = null;
            Name class_name_node = null;
               

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2322:5: ( ( decorators )? ( PERSISTIT conn_name= NAME )? CLASS (class_name= NAME ) ( LPAREN ( testlist[expr_contextType.Load] )? RPAREN )? COLON suite[false] )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2322:7: ( decorators )? ( PERSISTIT conn_name= NAME )? CLASS (class_name= NAME ) ( LPAREN ( testlist[expr_contextType.Load] )? RPAREN )? COLON suite[false]
            {
            root_0 = (PythonTree)adaptor.nil();

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2322:7: ( decorators )?
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==AT) ) {
                alt159=1;
            }
            switch (alt159) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2322:7: decorators
                    {
                    pushFollow(FOLLOW_decorators_in_classdef8866);
                    decorators296=decorators();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, decorators296.getTree());

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2322:19: ( PERSISTIT conn_name= NAME )?
            int alt160=2;
            int LA160_0 = input.LA(1);

            if ( (LA160_0==PERSISTIT) ) {
                alt160=1;
            }
            switch (alt160) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2322:20: PERSISTIT conn_name= NAME
                    {
                    PERSISTIT297=(Token)match(input,PERSISTIT,FOLLOW_PERSISTIT_in_classdef8870); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PERSISTIT297_tree = (PythonTree)adaptor.create(PERSISTIT297);
                    adaptor.addChild(root_0, PERSISTIT297_tree);
                    }
                    conn_name=(Token)match(input,NAME,FOLLOW_NAME_in_classdef8874); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    conn_name_tree = (PythonTree)adaptor.create(conn_name);
                    adaptor.addChild(root_0, conn_name_tree);
                    }
                    if ( state.backtracking==0 ) {
                       
                                      persistant = true; 
                                      conn_name_node = actions.makeNameNode(conn_name);
                                  
                    }

                    }
                    break;

            }

            CLASS298=(Token)match(input,CLASS,FOLLOW_CLASS_in_classdef8892); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            CLASS298_tree = (PythonTree)adaptor.create(CLASS298);
            adaptor.addChild(root_0, CLASS298_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2326:23: (class_name= NAME )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2326:24: class_name= NAME
            {
            class_name=(Token)match(input,NAME,FOLLOW_NAME_in_classdef8897); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            class_name_tree = (PythonTree)adaptor.create(class_name);
            adaptor.addChild(root_0, class_name_tree);
            }

            }

            if ( state.backtracking==0 ) {

                          class_name_node = actions.cantBeNoneName(class_name);
                      
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2330:9: ( LPAREN ( testlist[expr_contextType.Load] )? RPAREN )?
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==LPAREN) ) {
                alt162=1;
            }
            switch (alt162) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2330:10: LPAREN ( testlist[expr_contextType.Load] )? RPAREN
                    {
                    LPAREN299=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_classdef8920); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN299_tree = (PythonTree)adaptor.create(LPAREN299);
                    adaptor.addChild(root_0, LPAREN299_tree);
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2330:17: ( testlist[expr_contextType.Load] )?
                    int alt161=2;
                    int LA161_0 = input.LA(1);

                    if ( (LA161_0==NAME||LA161_0==NOT||LA161_0==LPAREN||(LA161_0>=PLUS && LA161_0<=MINUS)||(LA161_0>=TILDE && LA161_0<=LBRACK)||LA161_0==LCURLY||LA161_0==BACKQUOTE) ) {
                        alt161=1;
                    }
                    else if ( (LA161_0==PRINT) && ((printFunction))) {
                        alt161=1;
                    }
                    else if ( (LA161_0==LAMBDA||(LA161_0>=INT && LA161_0<=CONNECTTO)) ) {
                        alt161=1;
                    }
                    switch (alt161) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2330:17: testlist[expr_contextType.Load]
                            {
                            pushFollow(FOLLOW_testlist_in_classdef8922);
                            testlist300=testlist(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist300.getTree());

                            }
                            break;

                    }

                    RPAREN301=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_classdef8926); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN301_tree = (PythonTree)adaptor.create(RPAREN301);
                    adaptor.addChild(root_0, RPAREN301_tree);
                    }

                    }
                    break;

            }

            COLON302=(Token)match(input,COLON,FOLLOW_COLON_in_classdef8930); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON302_tree = (PythonTree)adaptor.create(COLON302);
            adaptor.addChild(root_0, COLON302_tree);
            }
            pushFollow(FOLLOW_suite_in_classdef8932);
            suite303=suite(false);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, suite303.getTree());
            if ( state.backtracking==0 ) {

                        Token t = CLASS298;
                        if ((decorators296!=null?((Token)decorators296.start):null) != null) {
                            t = (decorators296!=null?((Token)decorators296.start):null);
                        }

                        stype = new ClassDef(t, class_name_node,
                            actions.makeBases(actions.castExpr((testlist300!=null?((PythonTree)testlist300.tree):null))),
                            actions.castStmts((suite303!=null?suite303.stypes:null)),
                            actions.castExprs((decorators296!=null?decorators296.etypes:null)), persistant, conn_name_node);
                      
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                 retval.tree = stype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "classdef"

    public static class arglist_return extends ParserRuleReturnScope {
        public List args;
        public List keywords;
        public expr starargs;
        public expr kwargs;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "arglist"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2349:1: arglist returns [List args, List keywords, expr starargs, expr kwargs] : ( argument[arguments, kws, gens, true, false] ( COMMA argument[arguments, kws, gens, false, false] )* ( COMMA ( STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )? )? | STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] );
    public final PythonParser.arglist_return arglist() throws RecognitionException {
        PythonParser.arglist_return retval = new PythonParser.arglist_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token COMMA305=null;
        Token COMMA307=null;
        Token STAR308=null;
        Token COMMA309=null;
        Token COMMA311=null;
        Token DOUBLESTAR312=null;
        Token DOUBLESTAR313=null;
        Token STAR314=null;
        Token COMMA315=null;
        Token COMMA317=null;
        Token DOUBLESTAR318=null;
        Token DOUBLESTAR319=null;
        PythonParser.test_return s = null;

        PythonParser.test_return k = null;

        PythonParser.argument_return argument304 = null;

        PythonParser.argument_return argument306 = null;

        PythonParser.argument_return argument310 = null;

        PythonParser.argument_return argument316 = null;


        PythonTree COMMA305_tree=null;
        PythonTree COMMA307_tree=null;
        PythonTree STAR308_tree=null;
        PythonTree COMMA309_tree=null;
        PythonTree COMMA311_tree=null;
        PythonTree DOUBLESTAR312_tree=null;
        PythonTree DOUBLESTAR313_tree=null;
        PythonTree STAR314_tree=null;
        PythonTree COMMA315_tree=null;
        PythonTree COMMA317_tree=null;
        PythonTree DOUBLESTAR318_tree=null;
        PythonTree DOUBLESTAR319_tree=null;


            List arguments = new ArrayList();
            List kws = new ArrayList();
            List gens = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2356:5: ( argument[arguments, kws, gens, true, false] ( COMMA argument[arguments, kws, gens, false, false] )* ( COMMA ( STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )? )? | STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )
            int alt170=3;
            int LA170_0 = input.LA(1);

            if ( (LA170_0==NAME||LA170_0==NOT||LA170_0==LPAREN||(LA170_0>=PLUS && LA170_0<=MINUS)||(LA170_0>=TILDE && LA170_0<=LBRACK)||LA170_0==LCURLY||LA170_0==BACKQUOTE) ) {
                alt170=1;
            }
            else if ( (LA170_0==PRINT) && ((printFunction))) {
                alt170=1;
            }
            else if ( (LA170_0==LAMBDA||(LA170_0>=INT && LA170_0<=CONNECTTO)) ) {
                alt170=1;
            }
            else if ( (LA170_0==STAR) ) {
                alt170=2;
            }
            else if ( (LA170_0==DOUBLESTAR) ) {
                alt170=3;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 170, 0, input);

                throw nvae;
            }
            switch (alt170) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2356:7: argument[arguments, kws, gens, true, false] ( COMMA argument[arguments, kws, gens, false, false] )* ( COMMA ( STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )? )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_argument_in_arglist8978);
                    argument304=argument(arguments, kws, gens, true, false);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, argument304.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2356:51: ( COMMA argument[arguments, kws, gens, false, false] )*
                    loop163:
                    do {
                        int alt163=2;
                        int LA163_0 = input.LA(1);

                        if ( (LA163_0==COMMA) ) {
                            int LA163_1 = input.LA(2);

                            if ( (LA163_1==NAME||LA163_1==PRINT||(LA163_1>=LAMBDA && LA163_1<=NOT)||LA163_1==LPAREN||(LA163_1>=PLUS && LA163_1<=MINUS)||(LA163_1>=TILDE && LA163_1<=LBRACK)||LA163_1==LCURLY||(LA163_1>=BACKQUOTE && LA163_1<=CONNECTTO)) ) {
                                alt163=1;
                            }


                        }


                        switch (alt163) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2356:52: COMMA argument[arguments, kws, gens, false, false]
                    	    {
                    	    COMMA305=(Token)match(input,COMMA,FOLLOW_COMMA_in_arglist8982); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA305_tree = (PythonTree)adaptor.create(COMMA305);
                    	    adaptor.addChild(root_0, COMMA305_tree);
                    	    }
                    	    pushFollow(FOLLOW_argument_in_arglist8984);
                    	    argument306=argument(arguments, kws, gens, false, false);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, argument306.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop163;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2357:11: ( COMMA ( STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )? )?
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( (LA167_0==COMMA) ) {
                        alt167=1;
                    }
                    switch (alt167) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2357:12: COMMA ( STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )?
                            {
                            COMMA307=(Token)match(input,COMMA,FOLLOW_COMMA_in_arglist9000); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA307_tree = (PythonTree)adaptor.create(COMMA307);
                            adaptor.addChild(root_0, COMMA307_tree);
                            }
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2358:15: ( STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )? | DOUBLESTAR k= test[expr_contextType.Load] )?
                            int alt166=3;
                            int LA166_0 = input.LA(1);

                            if ( (LA166_0==STAR) ) {
                                alt166=1;
                            }
                            else if ( (LA166_0==DOUBLESTAR) ) {
                                alt166=2;
                            }
                            switch (alt166) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2358:17: STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )?
                                    {
                                    STAR308=(Token)match(input,STAR,FOLLOW_STAR_in_arglist9018); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    STAR308_tree = (PythonTree)adaptor.create(STAR308);
                                    adaptor.addChild(root_0, STAR308_tree);
                                    }
                                    pushFollow(FOLLOW_test_in_arglist9022);
                                    s=test(expr_contextType.Load);

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2358:52: ( COMMA argument[arguments, kws, gens, false, true] )*
                                    loop164:
                                    do {
                                        int alt164=2;
                                        int LA164_0 = input.LA(1);

                                        if ( (LA164_0==COMMA) ) {
                                            int LA164_1 = input.LA(2);

                                            if ( (LA164_1==NAME||LA164_1==PRINT||(LA164_1>=LAMBDA && LA164_1<=NOT)||LA164_1==LPAREN||(LA164_1>=PLUS && LA164_1<=MINUS)||(LA164_1>=TILDE && LA164_1<=LBRACK)||LA164_1==LCURLY||(LA164_1>=BACKQUOTE && LA164_1<=CONNECTTO)) ) {
                                                alt164=1;
                                            }


                                        }


                                        switch (alt164) {
                                    	case 1 :
                                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2358:53: COMMA argument[arguments, kws, gens, false, true]
                                    	    {
                                    	    COMMA309=(Token)match(input,COMMA,FOLLOW_COMMA_in_arglist9026); if (state.failed) return retval;
                                    	    if ( state.backtracking==0 ) {
                                    	    COMMA309_tree = (PythonTree)adaptor.create(COMMA309);
                                    	    adaptor.addChild(root_0, COMMA309_tree);
                                    	    }
                                    	    pushFollow(FOLLOW_argument_in_arglist9028);
                                    	    argument310=argument(arguments, kws, gens, false, true);

                                    	    state._fsp--;
                                    	    if (state.failed) return retval;
                                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, argument310.getTree());

                                    	    }
                                    	    break;

                                    	default :
                                    	    break loop164;
                                        }
                                    } while (true);

                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2358:105: ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )?
                                    int alt165=2;
                                    int LA165_0 = input.LA(1);

                                    if ( (LA165_0==COMMA) ) {
                                        alt165=1;
                                    }
                                    switch (alt165) {
                                        case 1 :
                                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2358:106: COMMA DOUBLESTAR k= test[expr_contextType.Load]
                                            {
                                            COMMA311=(Token)match(input,COMMA,FOLLOW_COMMA_in_arglist9034); if (state.failed) return retval;
                                            if ( state.backtracking==0 ) {
                                            COMMA311_tree = (PythonTree)adaptor.create(COMMA311);
                                            adaptor.addChild(root_0, COMMA311_tree);
                                            }
                                            DOUBLESTAR312=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist9036); if (state.failed) return retval;
                                            if ( state.backtracking==0 ) {
                                            DOUBLESTAR312_tree = (PythonTree)adaptor.create(DOUBLESTAR312);
                                            adaptor.addChild(root_0, DOUBLESTAR312_tree);
                                            }
                                            pushFollow(FOLLOW_test_in_arglist9040);
                                            k=test(expr_contextType.Load);

                                            state._fsp--;
                                            if (state.failed) return retval;
                                            if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());

                                            }
                                            break;

                                    }


                                    }
                                    break;
                                case 2 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2359:17: DOUBLESTAR k= test[expr_contextType.Load]
                                    {
                                    DOUBLESTAR313=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist9061); if (state.failed) return retval;
                                    if ( state.backtracking==0 ) {
                                    DOUBLESTAR313_tree = (PythonTree)adaptor.create(DOUBLESTAR313);
                                    adaptor.addChild(root_0, DOUBLESTAR313_tree);
                                    }
                                    pushFollow(FOLLOW_test_in_arglist9065);
                                    k=test(expr_contextType.Load);

                                    state._fsp--;
                                    if (state.failed) return retval;
                                    if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());

                                    }
                                    break;

                            }


                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                if (arguments.size() > 1 && gens.size() > 0) {
                                    actions.errorGenExpNotSoleArg(new PythonTree(((Token)retval.start)));
                                }
                                retval.args =arguments;
                                retval.keywords =kws;
                                retval.starargs =actions.castExpr((s!=null?((PythonTree)s.tree):null));
                                retval.kwargs =actions.castExpr((k!=null?((PythonTree)k.tree):null));
                            
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2371:7: STAR s= test[expr_contextType.Load] ( COMMA argument[arguments, kws, gens, false, true] )* ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )?
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    STAR314=(Token)match(input,STAR,FOLLOW_STAR_in_arglist9112); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    STAR314_tree = (PythonTree)adaptor.create(STAR314);
                    adaptor.addChild(root_0, STAR314_tree);
                    }
                    pushFollow(FOLLOW_test_in_arglist9116);
                    s=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, s.getTree());
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2371:42: ( COMMA argument[arguments, kws, gens, false, true] )*
                    loop168:
                    do {
                        int alt168=2;
                        int LA168_0 = input.LA(1);

                        if ( (LA168_0==COMMA) ) {
                            int LA168_1 = input.LA(2);

                            if ( (LA168_1==NAME||LA168_1==PRINT||(LA168_1>=LAMBDA && LA168_1<=NOT)||LA168_1==LPAREN||(LA168_1>=PLUS && LA168_1<=MINUS)||(LA168_1>=TILDE && LA168_1<=LBRACK)||LA168_1==LCURLY||(LA168_1>=BACKQUOTE && LA168_1<=CONNECTTO)) ) {
                                alt168=1;
                            }


                        }


                        switch (alt168) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2371:43: COMMA argument[arguments, kws, gens, false, true]
                    	    {
                    	    COMMA315=(Token)match(input,COMMA,FOLLOW_COMMA_in_arglist9120); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    COMMA315_tree = (PythonTree)adaptor.create(COMMA315);
                    	    adaptor.addChild(root_0, COMMA315_tree);
                    	    }
                    	    pushFollow(FOLLOW_argument_in_arglist9122);
                    	    argument316=argument(arguments, kws, gens, false, true);

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, argument316.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop168;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2371:95: ( COMMA DOUBLESTAR k= test[expr_contextType.Load] )?
                    int alt169=2;
                    int LA169_0 = input.LA(1);

                    if ( (LA169_0==COMMA) ) {
                        alt169=1;
                    }
                    switch (alt169) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2371:96: COMMA DOUBLESTAR k= test[expr_contextType.Load]
                            {
                            COMMA317=(Token)match(input,COMMA,FOLLOW_COMMA_in_arglist9128); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            COMMA317_tree = (PythonTree)adaptor.create(COMMA317);
                            adaptor.addChild(root_0, COMMA317_tree);
                            }
                            DOUBLESTAR318=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist9130); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            DOUBLESTAR318_tree = (PythonTree)adaptor.create(DOUBLESTAR318);
                            adaptor.addChild(root_0, DOUBLESTAR318_tree);
                            }
                            pushFollow(FOLLOW_test_in_arglist9134);
                            k=test(expr_contextType.Load);

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {

                                retval.starargs =actions.castExpr((s!=null?((PythonTree)s.tree):null));
                                retval.keywords =kws;
                                retval.kwargs =actions.castExpr((k!=null?((PythonTree)k.tree):null));
                            
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2377:7: DOUBLESTAR k= test[expr_contextType.Load]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    DOUBLESTAR319=(Token)match(input,DOUBLESTAR,FOLLOW_DOUBLESTAR_in_arglist9153); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DOUBLESTAR319_tree = (PythonTree)adaptor.create(DOUBLESTAR319);
                    adaptor.addChild(root_0, DOUBLESTAR319_tree);
                    }
                    pushFollow(FOLLOW_test_in_arglist9157);
                    k=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, k.getTree());
                    if ( state.backtracking==0 ) {

                                retval.kwargs =actions.castExpr((k!=null?((PythonTree)k.tree):null));
                            
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "arglist"

    public static class argument_return extends ParserRuleReturnScope {
        public boolean genarg;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "argument"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2384:1: argument[List arguments, List kws, List gens, boolean first, boolean afterStar] returns [boolean genarg] : t1= test[expr_contextType.Load] ( ( ASSIGN t2= test[expr_contextType.Load] ) | comp_for[$gens] | ) ;
    public final PythonParser.argument_return argument(List arguments, List kws, List gens, boolean first, boolean afterStar) throws RecognitionException {
        PythonParser.argument_return retval = new PythonParser.argument_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token ASSIGN320=null;
        PythonParser.test_return t1 = null;

        PythonParser.test_return t2 = null;

        PythonParser.comp_for_return comp_for321 = null;


        PythonTree ASSIGN320_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2386:5: (t1= test[expr_contextType.Load] ( ( ASSIGN t2= test[expr_contextType.Load] ) | comp_for[$gens] | ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2386:7: t1= test[expr_contextType.Load] ( ( ASSIGN t2= test[expr_contextType.Load] ) | comp_for[$gens] | )
            {
            root_0 = (PythonTree)adaptor.nil();

            pushFollow(FOLLOW_test_in_argument9196);
            t1=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t1.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2387:9: ( ( ASSIGN t2= test[expr_contextType.Load] ) | comp_for[$gens] | )
            int alt171=3;
            switch ( input.LA(1) ) {
            case ASSIGN:
                {
                alt171=1;
                }
                break;
            case FOR:
                {
                alt171=2;
                }
                break;
            case RPAREN:
            case COMMA:
                {
                alt171=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 171, 0, input);

                throw nvae;
            }

            switch (alt171) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2387:10: ( ASSIGN t2= test[expr_contextType.Load] )
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2387:10: ( ASSIGN t2= test[expr_contextType.Load] )
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2387:11: ASSIGN t2= test[expr_contextType.Load]
                    {
                    ASSIGN320=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_argument9209); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    ASSIGN320_tree = (PythonTree)adaptor.create(ASSIGN320);
                    adaptor.addChild(root_0, ASSIGN320_tree);
                    }
                    pushFollow(FOLLOW_test_in_argument9213);
                    t2=test(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t2.getTree());

                    }

                    if ( state.backtracking==0 ) {

                                    expr newkey = actions.castExpr((t1!=null?((PythonTree)t1.tree):null));
                                    //Loop through all current keys and fail on duplicate.
                                    for(Object o: kws) {
                                        List list = (List)o;
                                        Object oldkey = list.get(0);
                                        if (oldkey instanceof Name && newkey instanceof Name) {
                                            if (((Name)oldkey).getId().equals(((Name)newkey).getId())) {
                                                errorHandler.error("keyword arguments repeated", (t1!=null?((PythonTree)t1.tree):null));
                                            }
                                        }
                                    }
                                    List<expr> exprs = new ArrayList<expr>();
                                    exprs.add(newkey);
                                    exprs.add(actions.castExpr((t2!=null?((PythonTree)t2.tree):null)));
                                    kws.add(exprs);
                                
                    }

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2405:11: comp_for[$gens]
                    {
                    pushFollow(FOLLOW_comp_for_in_argument9239);
                    comp_for321=comp_for(gens);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_for321.getTree());
                    if ( state.backtracking==0 ) {

                                    if (!first) {
                                        actions.errorGenExpNotSoleArg((comp_for321!=null?((PythonTree)comp_for321.tree):null));
                                    }
                                    retval.genarg = true;
                                    Collections.reverse(gens);
                                    List<comprehension> c = gens;
                                    arguments.add(new GeneratorExp((t1!=null?((Token)t1.start):null), actions.castExpr((t1!=null?((PythonTree)t1.tree):null)), c));
                                
                    }

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2416:11: 
                    {
                    if ( state.backtracking==0 ) {

                                    if (kws.size() > 0) {
                                        errorHandler.error("non-keyword arg after keyword arg", (t1!=null?((PythonTree)t1.tree):null));
                                    } else if (afterStar) {
                                        errorHandler.error("only named arguments may follow *expression", (t1!=null?((PythonTree)t1.tree):null));
                                    }
                                    arguments.add((t1!=null?((PythonTree)t1.tree):null));
                                
                    }

                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "argument"

    public static class list_iter_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "list_iter"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2428:1: list_iter[List gens, List ifs] : ( list_for[gens] | list_if[gens, ifs] );
    public final PythonParser.list_iter_return list_iter(List gens, List ifs) throws RecognitionException {
        PythonParser.list_iter_return retval = new PythonParser.list_iter_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.list_for_return list_for322 = null;

        PythonParser.list_if_return list_if323 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2429:5: ( list_for[gens] | list_if[gens, ifs] )
            int alt172=2;
            int LA172_0 = input.LA(1);

            if ( (LA172_0==FOR) ) {
                alt172=1;
            }
            else if ( (LA172_0==IF) ) {
                alt172=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 172, 0, input);

                throw nvae;
            }
            switch (alt172) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2429:7: list_for[gens]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_list_for_in_list_iter9304);
                    list_for322=list_for(gens);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, list_for322.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2430:7: list_if[gens, ifs]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_list_if_in_list_iter9313);
                    list_if323=list_if(gens, ifs);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, list_if323.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "list_iter"

    public static class list_for_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "list_for"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2434:1: list_for[List gens] : FOR exprlist[expr_contextType.Store] IN testlist[expr_contextType.Load] ( list_iter[gens, ifs] )? ;
    public final PythonParser.list_for_return list_for(List gens) throws RecognitionException {
        PythonParser.list_for_return retval = new PythonParser.list_for_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token FOR324=null;
        Token IN326=null;
        PythonParser.exprlist_return exprlist325 = null;

        PythonParser.testlist_return testlist327 = null;

        PythonParser.list_iter_return list_iter328 = null;


        PythonTree FOR324_tree=null;
        PythonTree IN326_tree=null;


            List ifs = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2438:5: ( FOR exprlist[expr_contextType.Store] IN testlist[expr_contextType.Load] ( list_iter[gens, ifs] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2438:7: FOR exprlist[expr_contextType.Store] IN testlist[expr_contextType.Load] ( list_iter[gens, ifs] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            FOR324=(Token)match(input,FOR,FOLLOW_FOR_in_list_for9339); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FOR324_tree = (PythonTree)adaptor.create(FOR324);
            adaptor.addChild(root_0, FOR324_tree);
            }
            pushFollow(FOLLOW_exprlist_in_list_for9341);
            exprlist325=exprlist(expr_contextType.Store);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exprlist325.getTree());
            IN326=(Token)match(input,IN,FOLLOW_IN_in_list_for9344); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IN326_tree = (PythonTree)adaptor.create(IN326);
            adaptor.addChild(root_0, IN326_tree);
            }
            pushFollow(FOLLOW_testlist_in_list_for9346);
            testlist327=testlist(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist327.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2438:79: ( list_iter[gens, ifs] )?
            int alt173=2;
            int LA173_0 = input.LA(1);

            if ( (LA173_0==FOR||LA173_0==IF) ) {
                alt173=1;
            }
            switch (alt173) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2438:80: list_iter[gens, ifs]
                    {
                    pushFollow(FOLLOW_list_iter_in_list_for9350);
                    list_iter328=list_iter(gens, ifs);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, list_iter328.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        Collections.reverse(ifs);
                        gens.add(new comprehension(FOR324, (exprlist325!=null?exprlist325.etype:null), actions.castExpr((testlist327!=null?((PythonTree)testlist327.tree):null)), ifs));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "list_for"

    public static class list_if_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "list_if"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2446:1: list_if[List gens, List ifs] : IF test[expr_contextType.Load] ( list_iter[gens, ifs] )? ;
    public final PythonParser.list_if_return list_if(List gens, List ifs) throws RecognitionException {
        PythonParser.list_if_return retval = new PythonParser.list_if_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token IF329=null;
        PythonParser.test_return test330 = null;

        PythonParser.list_iter_return list_iter331 = null;


        PythonTree IF329_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2447:5: ( IF test[expr_contextType.Load] ( list_iter[gens, ifs] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2447:7: IF test[expr_contextType.Load] ( list_iter[gens, ifs] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            IF329=(Token)match(input,IF,FOLLOW_IF_in_list_if9380); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF329_tree = (PythonTree)adaptor.create(IF329);
            adaptor.addChild(root_0, IF329_tree);
            }
            pushFollow(FOLLOW_test_in_list_if9382);
            test330=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, test330.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2447:38: ( list_iter[gens, ifs] )?
            int alt174=2;
            int LA174_0 = input.LA(1);

            if ( (LA174_0==FOR||LA174_0==IF) ) {
                alt174=1;
            }
            switch (alt174) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2447:39: list_iter[gens, ifs]
                    {
                    pushFollow(FOLLOW_list_iter_in_list_if9386);
                    list_iter331=list_iter(gens, ifs);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, list_iter331.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                      ifs.add(actions.castExpr((test330!=null?((PythonTree)test330.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "list_if"

    public static class comp_iter_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comp_iter"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2454:1: comp_iter[List gens, List ifs] : ( comp_for[gens] | comp_if[gens, ifs] );
    public final PythonParser.comp_iter_return comp_iter(List gens, List ifs) throws RecognitionException {
        PythonParser.comp_iter_return retval = new PythonParser.comp_iter_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        PythonParser.comp_for_return comp_for332 = null;

        PythonParser.comp_if_return comp_if333 = null;



        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2455:5: ( comp_for[gens] | comp_if[gens, ifs] )
            int alt175=2;
            int LA175_0 = input.LA(1);

            if ( (LA175_0==FOR) ) {
                alt175=1;
            }
            else if ( (LA175_0==IF) ) {
                alt175=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                throw nvae;
            }
            switch (alt175) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2455:7: comp_for[gens]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_comp_for_in_comp_iter9417);
                    comp_for332=comp_for(gens);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_for332.getTree());

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2456:7: comp_if[gens, ifs]
                    {
                    root_0 = (PythonTree)adaptor.nil();

                    pushFollow(FOLLOW_comp_if_in_comp_iter9426);
                    comp_if333=comp_if(gens, ifs);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_if333.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comp_iter"

    public static class comp_for_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comp_for"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2460:1: comp_for[List gens] : FOR exprlist[expr_contextType.Store] IN or_test[expr_contextType.Load] ( comp_iter[gens, ifs] )? ;
    public final PythonParser.comp_for_return comp_for(List gens) throws RecognitionException {
        PythonParser.comp_for_return retval = new PythonParser.comp_for_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token FOR334=null;
        Token IN336=null;
        PythonParser.exprlist_return exprlist335 = null;

        PythonParser.or_test_return or_test337 = null;

        PythonParser.comp_iter_return comp_iter338 = null;


        PythonTree FOR334_tree=null;
        PythonTree IN336_tree=null;


            List ifs = new ArrayList();

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2464:5: ( FOR exprlist[expr_contextType.Store] IN or_test[expr_contextType.Load] ( comp_iter[gens, ifs] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2464:7: FOR exprlist[expr_contextType.Store] IN or_test[expr_contextType.Load] ( comp_iter[gens, ifs] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            FOR334=(Token)match(input,FOR,FOLLOW_FOR_in_comp_for9452); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FOR334_tree = (PythonTree)adaptor.create(FOR334);
            adaptor.addChild(root_0, FOR334_tree);
            }
            pushFollow(FOLLOW_exprlist_in_comp_for9454);
            exprlist335=exprlist(expr_contextType.Store);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, exprlist335.getTree());
            IN336=(Token)match(input,IN,FOLLOW_IN_in_comp_for9457); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IN336_tree = (PythonTree)adaptor.create(IN336);
            adaptor.addChild(root_0, IN336_tree);
            }
            pushFollow(FOLLOW_or_test_in_comp_for9459);
            or_test337=or_test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, or_test337.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2464:78: ( comp_iter[gens, ifs] )?
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( (LA176_0==FOR||LA176_0==IF) ) {
                alt176=1;
            }
            switch (alt176) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2464:78: comp_iter[gens, ifs]
                    {
                    pushFollow(FOLLOW_comp_iter_in_comp_for9462);
                    comp_iter338=comp_iter(gens, ifs);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_iter338.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        Collections.reverse(ifs);
                        gens.add(new comprehension(FOR334, (exprlist335!=null?exprlist335.etype:null), actions.castExpr((or_test337!=null?((PythonTree)or_test337.tree):null)), ifs));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comp_for"

    public static class comp_if_return extends ParserRuleReturnScope {
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "comp_if"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2472:1: comp_if[List gens, List ifs] : IF test[expr_contextType.Load] ( comp_iter[gens, ifs] )? ;
    public final PythonParser.comp_if_return comp_if(List gens, List ifs) throws RecognitionException {
        PythonParser.comp_if_return retval = new PythonParser.comp_if_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token IF339=null;
        PythonParser.test_return test340 = null;

        PythonParser.comp_iter_return comp_iter341 = null;


        PythonTree IF339_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2473:5: ( IF test[expr_contextType.Load] ( comp_iter[gens, ifs] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2473:7: IF test[expr_contextType.Load] ( comp_iter[gens, ifs] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            IF339=(Token)match(input,IF,FOLLOW_IF_in_comp_if9491); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF339_tree = (PythonTree)adaptor.create(IF339);
            adaptor.addChild(root_0, IF339_tree);
            }
            pushFollow(FOLLOW_test_in_comp_if9493);
            test340=test(expr_contextType.Load);

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, test340.getTree());
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2473:38: ( comp_iter[gens, ifs] )?
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==FOR||LA177_0==IF) ) {
                alt177=1;
            }
            switch (alt177) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2473:38: comp_iter[gens, ifs]
                    {
                    pushFollow(FOLLOW_comp_iter_in_comp_if9496);
                    comp_iter341=comp_iter(gens, ifs);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, comp_iter341.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                      ifs.add(actions.castExpr((test340!=null?((PythonTree)test340.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "comp_if"

    public static class yield_expr_return extends ParserRuleReturnScope {
        public expr etype;
        PythonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "yield_expr"
    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2480:1: yield_expr returns [expr etype] : YIELD ( testlist[expr_contextType.Load] )? ;
    public final PythonParser.yield_expr_return yield_expr() throws RecognitionException {
        PythonParser.yield_expr_return retval = new PythonParser.yield_expr_return();
        retval.start = input.LT(1);

        PythonTree root_0 = null;

        Token YIELD342=null;
        PythonParser.testlist_return testlist343 = null;


        PythonTree YIELD342_tree=null;

        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2486:5: ( YIELD ( testlist[expr_contextType.Load] )? )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2486:7: YIELD ( testlist[expr_contextType.Load] )?
            {
            root_0 = (PythonTree)adaptor.nil();

            YIELD342=(Token)match(input,YIELD,FOLLOW_YIELD_in_yield_expr9537); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            YIELD342_tree = (PythonTree)adaptor.create(YIELD342);
            adaptor.addChild(root_0, YIELD342_tree);
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2486:13: ( testlist[expr_contextType.Load] )?
            int alt178=2;
            int LA178_0 = input.LA(1);

            if ( (LA178_0==NAME||LA178_0==NOT||LA178_0==LPAREN||(LA178_0>=PLUS && LA178_0<=MINUS)||(LA178_0>=TILDE && LA178_0<=LBRACK)||LA178_0==LCURLY||LA178_0==BACKQUOTE) ) {
                alt178=1;
            }
            else if ( (LA178_0==PRINT) && ((printFunction))) {
                alt178=1;
            }
            else if ( (LA178_0==LAMBDA||(LA178_0>=INT && LA178_0<=CONNECTTO)) ) {
                alt178=1;
            }
            switch (alt178) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2486:13: testlist[expr_contextType.Load]
                    {
                    pushFollow(FOLLOW_testlist_in_yield_expr9539);
                    testlist343=testlist(expr_contextType.Load);

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, testlist343.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {

                        retval.etype = new Yield(YIELD342, actions.castExpr((testlist343!=null?((PythonTree)testlist343.tree):null)));
                    
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (PythonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

                  //needed for y2+=yield_expr
                  retval.tree = retval.etype;

            }
        }

        catch (RecognitionException re) {
            reportError(re);
            errorHandler.recover(this, input,re);
            retval.tree = (PythonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
        }
        finally {
        }
        return retval;
    }
    // $ANTLR end "yield_expr"

    // $ANTLR start synpred1_Python
    public final void synpred1_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:588:7: ( LPAREN fpdef[null] COMMA )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:588:8: LPAREN fpdef[null] COMMA
        {
        match(input,LPAREN,FOLLOW_LPAREN_in_synpred1_Python1304); if (state.failed) return ;
        pushFollow(FOLLOW_fpdef_in_synpred1_Python1306);
        fpdef(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,COMMA,FOLLOW_COMMA_in_synpred1_Python1309); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_Python

    // $ANTLR start synpred2_Python
    public final void synpred2_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:654:8: ( testlist[null] augassign )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:654:9: testlist[null] augassign
        {
        pushFollow(FOLLOW_testlist_in_synpred2_Python1699);
        testlist(null);

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_augassign_in_synpred2_Python1702);
        augassign();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred2_Python

    // $ANTLR start synpred3_Python
    public final void synpred3_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:668:7: ( testlist[null] ASSIGN )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:668:8: testlist[null] ASSIGN
        {
        pushFollow(FOLLOW_testlist_in_synpred3_Python1818);
        testlist(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,ASSIGN,FOLLOW_ASSIGN_in_synpred3_Python1821); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred3_Python

    // $ANTLR start synpred4_Python
    public final void synpred4_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:773:7: ( test[null] COMMA )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:773:8: test[null] COMMA
        {
        pushFollow(FOLLOW_test_in_synpred4_Python2333);
        test(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,COMMA,FOLLOW_COMMA_in_synpred4_Python2336); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred4_Python

    // $ANTLR start synpred5_Python
    public final void synpred5_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:794:7: ( test[null] COMMA test[null] )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:794:8: test[null] COMMA test[null]
        {
        pushFollow(FOLLOW_test_in_synpred5_Python2432);
        test(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,COMMA,FOLLOW_COMMA_in_synpred5_Python2435); if (state.failed) return ;
        pushFollow(FOLLOW_test_in_synpred5_Python2437);
        test(null);

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred5_Python

    // $ANTLR start synpred6_Python
    public final void synpred6_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1108:7: ( ( decorators )? DEF )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1108:8: ( decorators )? DEF
        {
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1108:8: ( decorators )?
        int alt179=2;
        int LA179_0 = input.LA(1);

        if ( (LA179_0==AT) ) {
            alt179=1;
        }
        switch (alt179) {
            case 1 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1108:8: decorators
                {
                pushFollow(FOLLOW_decorators_in_synpred6_Python3534);
                decorators();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        match(input,DEF,FOLLOW_DEF_in_synpred6_Python3537); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred6_Python

    // $ANTLR start synpred7_Python
    public final void synpred7_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1304:9: ( IF or_test[null] ORELSE )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1304:10: IF or_test[null] ORELSE
        {
        match(input,IF,FOLLOW_IF_in_synpred7_Python4294); if (state.failed) return ;
        pushFollow(FOLLOW_or_test_in_synpred7_Python4296);
        or_test(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,ORELSE,FOLLOW_ORELSE_in_synpred7_Python4299); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred7_Python

    // $ANTLR start synpred8_Python
    public final void synpred8_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2186:7: ( test[null] COLON )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2186:8: test[null] COLON
        {
        pushFollow(FOLLOW_test_in_synpred8_Python8143);
        test(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,COLON,FOLLOW_COLON_in_synpred8_Python8146); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred8_Python

    // $ANTLR start synpred9_Python
    public final void synpred9_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2191:7: ( COLON )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2191:8: COLON
        {
        match(input,COLON,FOLLOW_COLON_in_synpred9_Python8194); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred9_Python

    // $ANTLR start synpred10_Python
    public final void synpred10_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:7: ( expr[null] COMMA )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2225:8: expr[null] COMMA
        {
        pushFollow(FOLLOW_expr_in_synpred10_Python8339);
        expr(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,COMMA,FOLLOW_COMMA_in_synpred10_Python8342); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred10_Python

    // $ANTLR start synpred11_Python
    public final void synpred11_Python_fragment() throws RecognitionException {   
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2255:7: ( test[null] COMMA )
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2255:8: test[null] COMMA
        {
        pushFollow(FOLLOW_test_in_synpred11_Python8490);
        test(null);

        state._fsp--;
        if (state.failed) return ;
        match(input,COMMA,FOLLOW_COMMA_in_synpred11_Python8493); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred11_Python

    // Delegated rules

    public final boolean synpred1_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred3_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred3_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred10_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred10_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred2_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred2_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred4_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred4_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred5_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred5_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred6_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred6_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred7_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred7_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred8_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred8_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_Python() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_Python_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA30 dfa30 = new DFA30(this);
    protected DFA35 dfa35 = new DFA35(this);
    protected DFA31 dfa31 = new DFA31(this);
    protected DFA40 dfa40 = new DFA40(this);
    protected DFA38 dfa38 = new DFA38(this);
    protected DFA43 dfa43 = new DFA43(this);
    protected DFA41 dfa41 = new DFA41(this);
    protected DFA52 dfa52 = new DFA52(this);
    protected DFA64 dfa64 = new DFA64(this);
    protected DFA80 dfa80 = new DFA80(this);
    protected DFA89 dfa89 = new DFA89(this);
    protected DFA112 dfa112 = new DFA112(this);
    protected DFA113 dfa113 = new DFA113(this);
    protected DFA115 dfa115 = new DFA115(this);
    protected DFA117 dfa117 = new DFA117(this);
    protected DFA119 dfa119 = new DFA119(this);
    protected DFA121 dfa121 = new DFA121(this);
    protected DFA123 dfa123 = new DFA123(this);
    protected DFA130 dfa130 = new DFA130(this);
    protected DFA143 dfa143 = new DFA143(this);
    protected DFA147 dfa147 = new DFA147(this);
    protected DFA145 dfa145 = new DFA145(this);
    protected DFA148 dfa148 = new DFA148(this);
    protected DFA152 dfa152 = new DFA152(this);
    protected DFA150 dfa150 = new DFA150(this);
    protected DFA153 dfa153 = new DFA153(this);
    static final String DFA30_eotS =
        "\13\uffff";
    static final String DFA30_eofS =
        "\13\uffff";
    static final String DFA30_minS =
        "\1\11\1\uffff\1\0\10\uffff";
    static final String DFA30_maxS =
        "\1\144\1\uffff\1\0\10\uffff";
    static final String DFA30_acceptS =
        "\1\uffff\1\1\1\uffff\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11";
    static final String DFA30_specialS =
        "\1\1\1\uffff\1\0\10\uffff}>";
    static final String[] DFA30_transitionS = {
            "\1\1\1\uffff\1\2\2\uffff\1\11\1\5\1\uffff\1\5\1\uffff\1\3\2"+
            "\uffff\1\10\1\uffff\1\6\1\uffff\1\7\1\uffff\1\6\2\uffff\2\1"+
            "\2\uffff\1\4\2\5\3\uffff\1\5\2\uffff\1\1\37\uffff\2\1\3\uffff"+
            "\2\1\1\uffff\1\1\1\uffff\17\1",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA30_eot = DFA.unpackEncodedString(DFA30_eotS);
    static final short[] DFA30_eof = DFA.unpackEncodedString(DFA30_eofS);
    static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars(DFA30_minS);
    static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars(DFA30_maxS);
    static final short[] DFA30_accept = DFA.unpackEncodedString(DFA30_acceptS);
    static final short[] DFA30_special = DFA.unpackEncodedString(DFA30_specialS);
    static final short[][] DFA30_transition;

    static {
        int numStates = DFA30_transitionS.length;
        DFA30_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
        }
    }

    class DFA30 extends DFA {

        public DFA30(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 30;
            this.eot = DFA30_eot;
            this.eof = DFA30_eof;
            this.min = DFA30_min;
            this.max = DFA30_max;
            this.accept = DFA30_accept;
            this.special = DFA30_special;
            this.transition = DFA30_transition;
        }
        public String getDescription() {
            return "630:1: small_stmt : ( expr_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt | {...}? => print_stmt );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA30_2 = input.LA(1);

                         
                        int index30_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((printFunction)) ) {s = 1;}

                        else if ( ((!printFunction)) ) {s = 10;}

                         
                        input.seek(index30_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA30_0 = input.LA(1);

                         
                        int index30_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA30_0==NAME||(LA30_0>=LAMBDA && LA30_0<=NOT)||LA30_0==LPAREN||(LA30_0>=PLUS && LA30_0<=MINUS)||(LA30_0>=TILDE && LA30_0<=LBRACK)||LA30_0==LCURLY||(LA30_0>=BACKQUOTE && LA30_0<=CONNECTTO)) ) {s = 1;}

                        else if ( (LA30_0==PRINT) && (((printFunction)||(!printFunction)))) {s = 2;}

                        else if ( (LA30_0==DELETE) ) {s = 3;}

                        else if ( (LA30_0==PASS) ) {s = 4;}

                        else if ( (LA30_0==BREAK||LA30_0==CONTINUE||(LA30_0>=RAISE && LA30_0<=RETURN)||LA30_0==YIELD) ) {s = 5;}

                        else if ( (LA30_0==FROM||LA30_0==IMPORT) ) {s = 6;}

                        else if ( (LA30_0==GLOBAL) ) {s = 7;}

                        else if ( (LA30_0==EXEC) ) {s = 8;}

                        else if ( (LA30_0==ASSERT) ) {s = 9;}

                         
                        input.seek(index30_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 30, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA35_eotS =
        "\35\uffff";
    static final String DFA35_eofS =
        "\35\uffff";
    static final String DFA35_minS =
        "\1\11\31\0\3\uffff";
    static final String DFA35_maxS =
        "\1\144\31\0\3\uffff";
    static final String DFA35_acceptS =
        "\32\uffff\1\1\1\2\1\3";
    static final String DFA35_specialS =
        "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1"+
        "\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\3\uffff}>";
    static final String[] DFA35_transitionS = {
            "\1\11\1\uffff\1\12\23\uffff\1\31\1\1\13\uffff\1\5\37\uffff\1"+
            "\2\1\3\3\uffff\1\4\1\6\1\uffff\1\7\1\uffff\1\10\1\13\1\14\1"+
            "\15\1\16\1\17\1\27\1\30\1\20\1\21\1\22\1\25\1\24\1\23\1\26",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA35_eot = DFA.unpackEncodedString(DFA35_eotS);
    static final short[] DFA35_eof = DFA.unpackEncodedString(DFA35_eofS);
    static final char[] DFA35_min = DFA.unpackEncodedStringToUnsignedChars(DFA35_minS);
    static final char[] DFA35_max = DFA.unpackEncodedStringToUnsignedChars(DFA35_maxS);
    static final short[] DFA35_accept = DFA.unpackEncodedString(DFA35_acceptS);
    static final short[] DFA35_special = DFA.unpackEncodedString(DFA35_specialS);
    static final short[][] DFA35_transition;

    static {
        int numStates = DFA35_transitionS.length;
        DFA35_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA35_transition[i] = DFA.unpackEncodedString(DFA35_transitionS[i]);
        }
    }

    class DFA35 extends DFA {

        public DFA35(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 35;
            this.eot = DFA35_eot;
            this.eof = DFA35_eof;
            this.min = DFA35_min;
            this.max = DFA35_max;
            this.accept = DFA35_accept;
            this.special = DFA35_special;
            this.transition = DFA35_transition;
        }
        public String getDescription() {
            return "654:7: ( ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) ) | ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) ) | lhs= testlist[expr_contextType.Load] )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA35_0 = input.LA(1);

                         
                        int index35_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA35_0==NOT) ) {s = 1;}

                        else if ( (LA35_0==PLUS) ) {s = 2;}

                        else if ( (LA35_0==MINUS) ) {s = 3;}

                        else if ( (LA35_0==TILDE) ) {s = 4;}

                        else if ( (LA35_0==LPAREN) ) {s = 5;}

                        else if ( (LA35_0==LBRACK) ) {s = 6;}

                        else if ( (LA35_0==LCURLY) ) {s = 7;}

                        else if ( (LA35_0==BACKQUOTE) ) {s = 8;}

                        else if ( (LA35_0==NAME) ) {s = 9;}

                        else if ( (LA35_0==PRINT) && ((printFunction))) {s = 10;}

                        else if ( (LA35_0==INT) ) {s = 11;}

                        else if ( (LA35_0==LONGINT) ) {s = 12;}

                        else if ( (LA35_0==FLOAT) ) {s = 13;}

                        else if ( (LA35_0==COMPLEX) ) {s = 14;}

                        else if ( (LA35_0==STRING) ) {s = 15;}

                        else if ( (LA35_0==SQL) ) {s = 16;}

                        else if ( (LA35_0==SIM) ) {s = 17;}

                        else if ( (LA35_0==Neo4j) ) {s = 18;}

                        else if ( (LA35_0==SPARQL) ) {s = 19;}

                        else if ( (LA35_0==RDF) ) {s = 20;}

                        else if ( (LA35_0==JAPI) ) {s = 21;}

                        else if ( (LA35_0==CONNECTTO) ) {s = 22;}

                        else if ( (LA35_0==OORELCOMMIT) ) {s = 23;}

                        else if ( (LA35_0==OORELINSERT) ) {s = 24;}

                        else if ( (LA35_0==LAMBDA) ) {s = 25;}

                         
                        input.seek(index35_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA35_1 = input.LA(1);

                         
                        int index35_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA35_2 = input.LA(1);

                         
                        int index35_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA35_3 = input.LA(1);

                         
                        int index35_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA35_4 = input.LA(1);

                         
                        int index35_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA35_5 = input.LA(1);

                         
                        int index35_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA35_6 = input.LA(1);

                         
                        int index35_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA35_7 = input.LA(1);

                         
                        int index35_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA35_8 = input.LA(1);

                         
                        int index35_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA35_9 = input.LA(1);

                         
                        int index35_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA35_10 = input.LA(1);

                         
                        int index35_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (((synpred2_Python()&&(printFunction))||(synpred2_Python()&&(printFunction)))) ) {s = 26;}

                        else if ( (((synpred3_Python()&&(printFunction))||(synpred3_Python()&&(printFunction)))) ) {s = 27;}

                        else if ( ((printFunction)) ) {s = 28;}

                         
                        input.seek(index35_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA35_11 = input.LA(1);

                         
                        int index35_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA35_12 = input.LA(1);

                         
                        int index35_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA35_13 = input.LA(1);

                         
                        int index35_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA35_14 = input.LA(1);

                         
                        int index35_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA35_15 = input.LA(1);

                         
                        int index35_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA35_16 = input.LA(1);

                         
                        int index35_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA35_17 = input.LA(1);

                         
                        int index35_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA35_18 = input.LA(1);

                         
                        int index35_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA35_19 = input.LA(1);

                         
                        int index35_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA35_20 = input.LA(1);

                         
                        int index35_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA35_21 = input.LA(1);

                         
                        int index35_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA35_22 = input.LA(1);

                         
                        int index35_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA35_23 = input.LA(1);

                         
                        int index35_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA35_24 = input.LA(1);

                         
                        int index35_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA35_25 = input.LA(1);

                         
                        int index35_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred2_Python()) ) {s = 26;}

                        else if ( (synpred3_Python()) ) {s = 27;}

                        else if ( (true) ) {s = 28;}

                         
                        input.seek(index35_25);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 35, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA31_eotS =
        "\17\uffff";
    static final String DFA31_eofS =
        "\17\uffff";
    static final String DFA31_minS =
        "\1\64\14\11\2\uffff";
    static final String DFA31_maxS =
        "\1\77\14\144\2\uffff";
    static final String DFA31_acceptS =
        "\15\uffff\1\1\1\2";
    static final String DFA31_specialS =
        "\17\uffff}>";
    static final String[] DFA31_transitionS = {
            "\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "\1\16\1\uffff\1\16\23\uffff\2\16\10\uffff\1\15\2\uffff\1\16"+
            "\37\uffff\2\16\3\uffff\2\16\1\uffff\1\16\1\uffff\17\16",
            "",
            ""
    };

    static final short[] DFA31_eot = DFA.unpackEncodedString(DFA31_eotS);
    static final short[] DFA31_eof = DFA.unpackEncodedString(DFA31_eofS);
    static final char[] DFA31_min = DFA.unpackEncodedStringToUnsignedChars(DFA31_minS);
    static final char[] DFA31_max = DFA.unpackEncodedStringToUnsignedChars(DFA31_maxS);
    static final short[] DFA31_accept = DFA.unpackEncodedString(DFA31_acceptS);
    static final short[] DFA31_special = DFA.unpackEncodedString(DFA31_specialS);
    static final short[][] DFA31_transition;

    static {
        int numStates = DFA31_transitionS.length;
        DFA31_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
        }
    }

    class DFA31 extends DFA {

        public DFA31(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 31;
            this.eot = DFA31_eot;
            this.eof = DFA31_eof;
            this.min = DFA31_min;
            this.max = DFA31_max;
            this.accept = DFA31_accept;
            this.special = DFA31_special;
            this.transition = DFA31_transition;
        }
        public String getDescription() {
            return "655:9: ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) )";
        }
    }
    static final String DFA40_eotS =
        "\34\uffff";
    static final String DFA40_eofS =
        "\34\uffff";
    static final String DFA40_minS =
        "\1\11\31\0\2\uffff";
    static final String DFA40_maxS =
        "\1\144\31\0\2\uffff";
    static final String DFA40_acceptS =
        "\32\uffff\1\1\1\2";
    static final String DFA40_specialS =
        "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1"+
        "\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\2\uffff}>";
    static final String[] DFA40_transitionS = {
            "\1\11\1\uffff\1\12\23\uffff\1\31\1\1\13\uffff\1\5\37\uffff\1"+
            "\2\1\3\3\uffff\1\4\1\6\1\uffff\1\7\1\uffff\1\10\1\13\1\14\1"+
            "\15\1\16\1\17\1\27\1\30\1\20\1\21\1\22\1\25\1\24\1\23\1\26",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA40_eot = DFA.unpackEncodedString(DFA40_eotS);
    static final short[] DFA40_eof = DFA.unpackEncodedString(DFA40_eofS);
    static final char[] DFA40_min = DFA.unpackEncodedStringToUnsignedChars(DFA40_minS);
    static final char[] DFA40_max = DFA.unpackEncodedStringToUnsignedChars(DFA40_maxS);
    static final short[] DFA40_accept = DFA.unpackEncodedString(DFA40_acceptS);
    static final short[] DFA40_special = DFA.unpackEncodedString(DFA40_specialS);
    static final short[][] DFA40_transition;

    static {
        int numStates = DFA40_transitionS.length;
        DFA40_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA40_transition[i] = DFA.unpackEncodedString(DFA40_transitionS[i]);
        }
    }

    class DFA40 extends DFA {

        public DFA40(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 40;
            this.eot = DFA40_eot;
            this.eof = DFA40_eof;
            this.min = DFA40_min;
            this.max = DFA40_max;
            this.accept = DFA40_accept;
            this.special = DFA40_special;
            this.transition = DFA40_transition;
        }
        public String getDescription() {
            return "771:1: printlist returns [boolean newline, List elts] : ( ( test[null] COMMA )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA40_0 = input.LA(1);

                         
                        int index40_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA40_0==NOT) ) {s = 1;}

                        else if ( (LA40_0==PLUS) ) {s = 2;}

                        else if ( (LA40_0==MINUS) ) {s = 3;}

                        else if ( (LA40_0==TILDE) ) {s = 4;}

                        else if ( (LA40_0==LPAREN) ) {s = 5;}

                        else if ( (LA40_0==LBRACK) ) {s = 6;}

                        else if ( (LA40_0==LCURLY) ) {s = 7;}

                        else if ( (LA40_0==BACKQUOTE) ) {s = 8;}

                        else if ( (LA40_0==NAME) ) {s = 9;}

                        else if ( (LA40_0==PRINT) && ((printFunction))) {s = 10;}

                        else if ( (LA40_0==INT) ) {s = 11;}

                        else if ( (LA40_0==LONGINT) ) {s = 12;}

                        else if ( (LA40_0==FLOAT) ) {s = 13;}

                        else if ( (LA40_0==COMPLEX) ) {s = 14;}

                        else if ( (LA40_0==STRING) ) {s = 15;}

                        else if ( (LA40_0==SQL) ) {s = 16;}

                        else if ( (LA40_0==SIM) ) {s = 17;}

                        else if ( (LA40_0==Neo4j) ) {s = 18;}

                        else if ( (LA40_0==SPARQL) ) {s = 19;}

                        else if ( (LA40_0==RDF) ) {s = 20;}

                        else if ( (LA40_0==JAPI) ) {s = 21;}

                        else if ( (LA40_0==CONNECTTO) ) {s = 22;}

                        else if ( (LA40_0==OORELCOMMIT) ) {s = 23;}

                        else if ( (LA40_0==OORELINSERT) ) {s = 24;}

                        else if ( (LA40_0==LAMBDA) ) {s = 25;}

                         
                        input.seek(index40_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA40_1 = input.LA(1);

                         
                        int index40_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA40_2 = input.LA(1);

                         
                        int index40_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA40_3 = input.LA(1);

                         
                        int index40_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA40_4 = input.LA(1);

                         
                        int index40_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA40_5 = input.LA(1);

                         
                        int index40_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA40_6 = input.LA(1);

                         
                        int index40_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA40_7 = input.LA(1);

                         
                        int index40_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA40_8 = input.LA(1);

                         
                        int index40_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA40_9 = input.LA(1);

                         
                        int index40_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA40_10 = input.LA(1);

                         
                        int index40_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((synpred4_Python()&&(printFunction))) ) {s = 26;}

                        else if ( ((printFunction)) ) {s = 27;}

                         
                        input.seek(index40_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA40_11 = input.LA(1);

                         
                        int index40_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA40_12 = input.LA(1);

                         
                        int index40_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA40_13 = input.LA(1);

                         
                        int index40_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA40_14 = input.LA(1);

                         
                        int index40_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA40_15 = input.LA(1);

                         
                        int index40_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA40_16 = input.LA(1);

                         
                        int index40_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA40_17 = input.LA(1);

                         
                        int index40_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA40_18 = input.LA(1);

                         
                        int index40_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA40_19 = input.LA(1);

                         
                        int index40_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA40_20 = input.LA(1);

                         
                        int index40_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA40_21 = input.LA(1);

                         
                        int index40_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA40_22 = input.LA(1);

                         
                        int index40_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA40_23 = input.LA(1);

                         
                        int index40_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA40_24 = input.LA(1);

                         
                        int index40_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA40_25 = input.LA(1);

                         
                        int index40_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred4_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index40_25);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 40, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA38_eotS =
        "\37\uffff";
    static final String DFA38_eofS =
        "\37\uffff";
    static final String DFA38_minS =
        "\2\7\35\uffff";
    static final String DFA38_maxS =
        "\1\63\1\144\35\uffff";
    static final String DFA38_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\32\uffff";
    static final String DFA38_specialS =
        "\37\uffff}>";
    static final String[] DFA38_transitionS = {
            "\1\2\50\uffff\1\1\2\uffff\1\2",
            "\1\2\1\uffff\1\4\1\uffff\1\4\23\uffff\2\4\13\uffff\1\4\6\uffff"+
            "\1\2\30\uffff\2\4\3\uffff\2\4\1\uffff\1\4\1\uffff\17\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA38_eot = DFA.unpackEncodedString(DFA38_eotS);
    static final short[] DFA38_eof = DFA.unpackEncodedString(DFA38_eofS);
    static final char[] DFA38_min = DFA.unpackEncodedStringToUnsignedChars(DFA38_minS);
    static final char[] DFA38_max = DFA.unpackEncodedStringToUnsignedChars(DFA38_maxS);
    static final short[] DFA38_accept = DFA.unpackEncodedString(DFA38_acceptS);
    static final short[] DFA38_special = DFA.unpackEncodedString(DFA38_specialS);
    static final short[][] DFA38_transition;

    static {
        int numStates = DFA38_transitionS.length;
        DFA38_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA38_transition[i] = DFA.unpackEncodedString(DFA38_transitionS[i]);
        }
    }

    class DFA38 extends DFA {

        public DFA38(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 38;
            this.eot = DFA38_eot;
            this.eof = DFA38_eof;
            this.min = DFA38_min;
            this.max = DFA38_max;
            this.accept = DFA38_accept;
            this.special = DFA38_special;
            this.transition = DFA38_transition;
        }
        public String getDescription() {
            return "()* loopback of 774:39: ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )*";
        }
    }
    static final String DFA43_eotS =
        "\34\uffff";
    static final String DFA43_eofS =
        "\34\uffff";
    static final String DFA43_minS =
        "\1\11\31\0\2\uffff";
    static final String DFA43_maxS =
        "\1\144\31\0\2\uffff";
    static final String DFA43_acceptS =
        "\32\uffff\1\1\1\2";
    static final String DFA43_specialS =
        "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1"+
        "\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\2\uffff}>";
    static final String[] DFA43_transitionS = {
            "\1\11\1\uffff\1\12\23\uffff\1\31\1\1\13\uffff\1\5\37\uffff\1"+
            "\2\1\3\3\uffff\1\4\1\6\1\uffff\1\7\1\uffff\1\10\1\13\1\14\1"+
            "\15\1\16\1\17\1\27\1\30\1\20\1\21\1\22\1\25\1\24\1\23\1\26",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA43_eot = DFA.unpackEncodedString(DFA43_eotS);
    static final short[] DFA43_eof = DFA.unpackEncodedString(DFA43_eofS);
    static final char[] DFA43_min = DFA.unpackEncodedStringToUnsignedChars(DFA43_minS);
    static final char[] DFA43_max = DFA.unpackEncodedStringToUnsignedChars(DFA43_maxS);
    static final short[] DFA43_accept = DFA.unpackEncodedString(DFA43_acceptS);
    static final short[] DFA43_special = DFA.unpackEncodedString(DFA43_specialS);
    static final short[][] DFA43_transition;

    static {
        int numStates = DFA43_transitionS.length;
        DFA43_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA43_transition[i] = DFA.unpackEncodedString(DFA43_transitionS[i]);
        }
    }

    class DFA43 extends DFA {

        public DFA43(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 43;
            this.eot = DFA43_eot;
            this.eof = DFA43_eof;
            this.min = DFA43_min;
            this.max = DFA43_max;
            this.accept = DFA43_accept;
            this.special = DFA43_special;
            this.transition = DFA43_transition;
        }
        public String getDescription() {
            return "792:1: printlist2 returns [boolean newline, List elts] : ( ( test[null] COMMA test[null] )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA43_0 = input.LA(1);

                         
                        int index43_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA43_0==NOT) ) {s = 1;}

                        else if ( (LA43_0==PLUS) ) {s = 2;}

                        else if ( (LA43_0==MINUS) ) {s = 3;}

                        else if ( (LA43_0==TILDE) ) {s = 4;}

                        else if ( (LA43_0==LPAREN) ) {s = 5;}

                        else if ( (LA43_0==LBRACK) ) {s = 6;}

                        else if ( (LA43_0==LCURLY) ) {s = 7;}

                        else if ( (LA43_0==BACKQUOTE) ) {s = 8;}

                        else if ( (LA43_0==NAME) ) {s = 9;}

                        else if ( (LA43_0==PRINT) && ((printFunction))) {s = 10;}

                        else if ( (LA43_0==INT) ) {s = 11;}

                        else if ( (LA43_0==LONGINT) ) {s = 12;}

                        else if ( (LA43_0==FLOAT) ) {s = 13;}

                        else if ( (LA43_0==COMPLEX) ) {s = 14;}

                        else if ( (LA43_0==STRING) ) {s = 15;}

                        else if ( (LA43_0==SQL) ) {s = 16;}

                        else if ( (LA43_0==SIM) ) {s = 17;}

                        else if ( (LA43_0==Neo4j) ) {s = 18;}

                        else if ( (LA43_0==SPARQL) ) {s = 19;}

                        else if ( (LA43_0==RDF) ) {s = 20;}

                        else if ( (LA43_0==JAPI) ) {s = 21;}

                        else if ( (LA43_0==CONNECTTO) ) {s = 22;}

                        else if ( (LA43_0==OORELCOMMIT) ) {s = 23;}

                        else if ( (LA43_0==OORELINSERT) ) {s = 24;}

                        else if ( (LA43_0==LAMBDA) ) {s = 25;}

                         
                        input.seek(index43_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA43_1 = input.LA(1);

                         
                        int index43_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA43_2 = input.LA(1);

                         
                        int index43_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA43_3 = input.LA(1);

                         
                        int index43_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA43_4 = input.LA(1);

                         
                        int index43_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA43_5 = input.LA(1);

                         
                        int index43_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA43_6 = input.LA(1);

                         
                        int index43_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA43_7 = input.LA(1);

                         
                        int index43_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA43_8 = input.LA(1);

                         
                        int index43_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA43_9 = input.LA(1);

                         
                        int index43_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA43_10 = input.LA(1);

                         
                        int index43_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((synpred5_Python()&&(printFunction))) ) {s = 26;}

                        else if ( ((printFunction)) ) {s = 27;}

                         
                        input.seek(index43_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA43_11 = input.LA(1);

                         
                        int index43_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA43_12 = input.LA(1);

                         
                        int index43_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA43_13 = input.LA(1);

                         
                        int index43_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA43_14 = input.LA(1);

                         
                        int index43_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA43_15 = input.LA(1);

                         
                        int index43_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA43_16 = input.LA(1);

                         
                        int index43_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA43_17 = input.LA(1);

                         
                        int index43_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA43_18 = input.LA(1);

                         
                        int index43_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA43_19 = input.LA(1);

                         
                        int index43_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA43_20 = input.LA(1);

                         
                        int index43_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA43_21 = input.LA(1);

                         
                        int index43_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA43_22 = input.LA(1);

                         
                        int index43_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA43_23 = input.LA(1);

                         
                        int index43_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA43_24 = input.LA(1);

                         
                        int index43_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA43_25 = input.LA(1);

                         
                        int index43_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred5_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index43_25);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 43, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA41_eotS =
        "\37\uffff";
    static final String DFA41_eofS =
        "\37\uffff";
    static final String DFA41_minS =
        "\2\7\35\uffff";
    static final String DFA41_maxS =
        "\1\63\1\144\35\uffff";
    static final String DFA41_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\32\uffff";
    static final String DFA41_specialS =
        "\37\uffff}>";
    static final String[] DFA41_transitionS = {
            "\1\2\50\uffff\1\1\2\uffff\1\2",
            "\1\2\1\uffff\1\4\1\uffff\1\4\23\uffff\2\4\13\uffff\1\4\6\uffff"+
            "\1\2\30\uffff\2\4\3\uffff\2\4\1\uffff\1\4\1\uffff\17\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA41_eot = DFA.unpackEncodedString(DFA41_eotS);
    static final short[] DFA41_eof = DFA.unpackEncodedString(DFA41_eofS);
    static final char[] DFA41_min = DFA.unpackEncodedStringToUnsignedChars(DFA41_minS);
    static final char[] DFA41_max = DFA.unpackEncodedStringToUnsignedChars(DFA41_maxS);
    static final short[] DFA41_accept = DFA.unpackEncodedString(DFA41_acceptS);
    static final short[] DFA41_special = DFA.unpackEncodedString(DFA41_specialS);
    static final short[][] DFA41_transition;

    static {
        int numStates = DFA41_transitionS.length;
        DFA41_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA41_transition[i] = DFA.unpackEncodedString(DFA41_transitionS[i]);
        }
    }

    class DFA41 extends DFA {

        public DFA41(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 41;
            this.eot = DFA41_eot;
            this.eof = DFA41_eof;
            this.min = DFA41_min;
            this.max = DFA41_max;
            this.accept = DFA41_accept;
            this.special = DFA41_special;
            this.transition = DFA41_transition;
        }
        public String getDescription() {
            return "()* loopback of 795:39: ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )*";
        }
    }
    static final String DFA52_eotS =
        "\4\uffff";
    static final String DFA52_eofS =
        "\4\uffff";
    static final String DFA52_minS =
        "\2\11\2\uffff";
    static final String DFA52_maxS =
        "\1\12\1\34\2\uffff";
    static final String DFA52_acceptS =
        "\2\uffff\1\1\1\2";
    static final String DFA52_specialS =
        "\4\uffff}>";
    static final String[] DFA52_transitionS = {
            "\1\2\1\1",
            "\1\2\1\1\21\uffff\1\3",
            "",
            ""
    };

    static final short[] DFA52_eot = DFA.unpackEncodedString(DFA52_eotS);
    static final short[] DFA52_eof = DFA.unpackEncodedString(DFA52_eofS);
    static final char[] DFA52_min = DFA.unpackEncodedStringToUnsignedChars(DFA52_minS);
    static final char[] DFA52_max = DFA.unpackEncodedStringToUnsignedChars(DFA52_maxS);
    static final short[] DFA52_accept = DFA.unpackEncodedString(DFA52_acceptS);
    static final short[] DFA52_special = DFA.unpackEncodedString(DFA52_specialS);
    static final short[][] DFA52_transition;

    static {
        int numStates = DFA52_transitionS.length;
        DFA52_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
        }
    }

    class DFA52 extends DFA {

        public DFA52(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 52;
            this.eot = DFA52_eot;
            this.eof = DFA52_eof;
            this.min = DFA52_min;
            this.max = DFA52_max;
            this.accept = DFA52_accept;
            this.special = DFA52_special;
            this.transition = DFA52_transition;
        }
        public String getDescription() {
            return "956:12: ( (d+= DOT )* dotted_name | (d+= DOT )+ )";
        }
    }
    static final String DFA64_eotS =
        "\13\uffff";
    static final String DFA64_eofS =
        "\13\uffff";
    static final String DFA64_minS =
        "\1\20\6\uffff\1\0\3\uffff";
    static final String DFA64_maxS =
        "\1\146\6\uffff\1\0\3\uffff";
    static final String DFA64_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\uffff\1\7\1\10\1\uffff";
    static final String DFA64_specialS =
        "\1\0\6\uffff\1\1\3\uffff}>";
    static final String[] DFA64_transitionS = {
            "\1\11\1\uffff\1\10\6\uffff\1\3\1\uffff\1\1\12\uffff\1\4\1\2"+
            "\1\5\1\uffff\1\6\1\7\72\uffff\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA64_eot = DFA.unpackEncodedString(DFA64_eotS);
    static final short[] DFA64_eof = DFA.unpackEncodedString(DFA64_eofS);
    static final char[] DFA64_min = DFA.unpackEncodedStringToUnsignedChars(DFA64_minS);
    static final char[] DFA64_max = DFA.unpackEncodedStringToUnsignedChars(DFA64_maxS);
    static final short[] DFA64_accept = DFA.unpackEncodedString(DFA64_acceptS);
    static final short[] DFA64_special = DFA.unpackEncodedString(DFA64_specialS);
    static final short[][] DFA64_transition;

    static {
        int numStates = DFA64_transitionS.length;
        DFA64_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA64_transition[i] = DFA.unpackEncodedString(DFA64_transitionS[i]);
        }
    }

    class DFA64 extends DFA {

        public DFA64(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 64;
            this.eot = DFA64_eot;
            this.eof = DFA64_eof;
            this.min = DFA64_min;
            this.max = DFA64_max;
            this.accept = DFA64_accept;
            this.special = DFA64_special;
            this.transition = DFA64_transition;
        }
        public String getDescription() {
            return "1101:1: compound_stmt : ( if_stmt | while_stmt | for_stmt | try_stmt | with_stmt | batch_stmt | ( ( decorators )? DEF )=> funcdef | classdef );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA64_0 = input.LA(1);

                         
                        int index64_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA64_0==IF) ) {s = 1;}

                        else if ( (LA64_0==WHILE) ) {s = 2;}

                        else if ( (LA64_0==FOR) ) {s = 3;}

                        else if ( (LA64_0==TRY) ) {s = 4;}

                        else if ( (LA64_0==WITH) ) {s = 5;}

                        else if ( (LA64_0==BATCH) ) {s = 6;}

                        else if ( (LA64_0==AT) ) {s = 7;}

                        else if ( (LA64_0==DEF) && (synpred6_Python())) {s = 8;}

                        else if ( (LA64_0==CLASS||LA64_0==PERSISTIT) ) {s = 9;}

                         
                        input.seek(index64_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA64_7 = input.LA(1);

                         
                        int index64_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred6_Python()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index64_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 64, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA80_eotS =
        "\33\uffff";
    static final String DFA80_eofS =
        "\1\2\32\uffff";
    static final String DFA80_minS =
        "\1\7\1\0\31\uffff";
    static final String DFA80_maxS =
        "\1\126\1\0\31\uffff";
    static final String DFA80_acceptS =
        "\2\uffff\1\2\27\uffff\1\1";
    static final String DFA80_specialS =
        "\1\uffff\1\0\31\uffff}>";
    static final String[] DFA80_transitionS = {
            "\1\2\5\uffff\1\2\13\uffff\1\2\1\uffff\1\1\21\uffff\4\2\2\uffff"+
            "\15\2\23\uffff\1\2\1\uffff\2\2",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA80_eot = DFA.unpackEncodedString(DFA80_eotS);
    static final short[] DFA80_eof = DFA.unpackEncodedString(DFA80_eofS);
    static final char[] DFA80_min = DFA.unpackEncodedStringToUnsignedChars(DFA80_minS);
    static final char[] DFA80_max = DFA.unpackEncodedStringToUnsignedChars(DFA80_maxS);
    static final short[] DFA80_accept = DFA.unpackEncodedString(DFA80_acceptS);
    static final short[] DFA80_special = DFA.unpackEncodedString(DFA80_specialS);
    static final short[][] DFA80_transition;

    static {
        int numStates = DFA80_transitionS.length;
        DFA80_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA80_transition[i] = DFA.unpackEncodedString(DFA80_transitionS[i]);
        }
    }

    class DFA80 extends DFA {

        public DFA80(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 80;
            this.eot = DFA80_eot;
            this.eof = DFA80_eof;
            this.min = DFA80_min;
            this.max = DFA80_max;
            this.accept = DFA80_accept;
            this.special = DFA80_special;
            this.transition = DFA80_transition;
        }
        public String getDescription() {
            return "1304:7: ( ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load] | -> or_test )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA80_1 = input.LA(1);

                         
                        int index80_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred7_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index80_1);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 80, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA89_eotS =
        "\15\uffff";
    static final String DFA89_eofS =
        "\15\uffff";
    static final String DFA89_minS =
        "\1\35\11\uffff\1\11\2\uffff";
    static final String DFA89_maxS =
        "\1\107\11\uffff\1\144\2\uffff";
    static final String DFA89_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\uffff\1\13\1\12";
    static final String DFA89_specialS =
        "\15\uffff}>";
    static final String[] DFA89_transitionS = {
            "\1\10\1\12\1\uffff\1\11\40\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\14\1\uffff\1\14\24\uffff\1\13\13\uffff\1\14\37\uffff\2\14"+
            "\3\uffff\2\14\1\uffff\1\14\1\uffff\17\14",
            "",
            ""
    };

    static final short[] DFA89_eot = DFA.unpackEncodedString(DFA89_eotS);
    static final short[] DFA89_eof = DFA.unpackEncodedString(DFA89_eofS);
    static final char[] DFA89_min = DFA.unpackEncodedStringToUnsignedChars(DFA89_minS);
    static final char[] DFA89_max = DFA.unpackEncodedStringToUnsignedChars(DFA89_maxS);
    static final short[] DFA89_accept = DFA.unpackEncodedString(DFA89_acceptS);
    static final short[] DFA89_special = DFA.unpackEncodedString(DFA89_specialS);
    static final short[][] DFA89_transition;

    static {
        int numStates = DFA89_transitionS.length;
        DFA89_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA89_transition[i] = DFA.unpackEncodedString(DFA89_transitionS[i]);
        }
    }

    class DFA89 extends DFA {

        public DFA89(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 89;
            this.eot = DFA89_eot;
            this.eof = DFA89_eof;
            this.min = DFA89_min;
            this.max = DFA89_max;
            this.accept = DFA89_accept;
            this.special = DFA89_special;
            this.transition = DFA89_transition;
        }
        public String getDescription() {
            return "1400:1: comp_op returns [cmpopType op] : ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | IN | NOT IN | IS | IS NOT );";
        }
    }
    static final String DFA112_eotS =
        "\25\uffff";
    static final String DFA112_eofS =
        "\25\uffff";
    static final String DFA112_minS =
        "\1\11\24\uffff";
    static final String DFA112_maxS =
        "\1\144\24\uffff";
    static final String DFA112_acceptS =
        "\1\uffff\1\1\1\2\1\3\1\4\2\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1"+
        "\15\1\16\1\17\1\20\1\21\1\22\1\23";
    static final String DFA112_specialS =
        "\1\0\24\uffff}>";
    static final String[] DFA112_transitionS = {
            "\1\5\1\uffff\1\6\40\uffff\1\1\45\uffff\1\2\1\uffff\1\3\1\uffff"+
            "\1\4\1\7\1\10\1\11\1\12\1\13\1\23\1\24\1\14\1\15\1\16\1\21\1"+
            "\20\1\17\1\22",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA112_eot = DFA.unpackEncodedString(DFA112_eotS);
    static final short[] DFA112_eof = DFA.unpackEncodedString(DFA112_eofS);
    static final char[] DFA112_min = DFA.unpackEncodedStringToUnsignedChars(DFA112_minS);
    static final char[] DFA112_max = DFA.unpackEncodedStringToUnsignedChars(DFA112_maxS);
    static final short[] DFA112_accept = DFA.unpackEncodedString(DFA112_acceptS);
    static final short[] DFA112_special = DFA.unpackEncodedString(DFA112_specialS);
    static final short[][] DFA112_transition;

    static {
        int numStates = DFA112_transitionS.length;
        DFA112_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA112_transition[i] = DFA.unpackEncodedString(DFA112_transitionS[i]);
        }
    }

    class DFA112 extends DFA {

        public DFA112(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 112;
            this.eot = DFA112_eot;
            this.eof = DFA112_eof;
            this.min = DFA112_min;
            this.max = DFA112_max;
            this.accept = DFA112_accept;
            this.special = DFA112_special;
            this.transition = DFA112_transition;
        }
        public String getDescription() {
            return "1753:1: atom returns [Token lparen = null] : ( LPAREN ( yield_expr | testlist_gexp -> testlist_gexp | ) RPAREN | LBRACK ( listmaker[$LBRACK] -> listmaker | ) RBRACK | LCURLY ( dictorsetmaker[$LCURLY] -> dictorsetmaker | ) RCURLY | lb= BACKQUOTE testlist[expr_contextType.Load] rb= BACKQUOTE | name_or_print | INT | LONGINT | FLOAT | COMPLEX | (S+= STRING )+ | sql_stmt -> sql_stmt | sim_stmt -> sim_stmt | neo4j_stmt -> neo4j_stmt | sparql_stmt -> sparql_stmt | rdf_stmt -> rdf_stmt | japi_stmt -> japi_stmt | conn_stmt -> conn_stmt | oorel_commit_stmt -> oorel_commit_stmt | oorel_insert_stmt -> oorel_insert_stmt );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA112_0 = input.LA(1);

                         
                        int index112_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA112_0==LPAREN) ) {s = 1;}

                        else if ( (LA112_0==LBRACK) ) {s = 2;}

                        else if ( (LA112_0==LCURLY) ) {s = 3;}

                        else if ( (LA112_0==BACKQUOTE) ) {s = 4;}

                        else if ( (LA112_0==NAME) ) {s = 5;}

                        else if ( (LA112_0==PRINT) && ((printFunction))) {s = 6;}

                        else if ( (LA112_0==INT) ) {s = 7;}

                        else if ( (LA112_0==LONGINT) ) {s = 8;}

                        else if ( (LA112_0==FLOAT) ) {s = 9;}

                        else if ( (LA112_0==COMPLEX) ) {s = 10;}

                        else if ( (LA112_0==STRING) ) {s = 11;}

                        else if ( (LA112_0==SQL) ) {s = 12;}

                        else if ( (LA112_0==SIM) ) {s = 13;}

                        else if ( (LA112_0==Neo4j) ) {s = 14;}

                        else if ( (LA112_0==SPARQL) ) {s = 15;}

                        else if ( (LA112_0==RDF) ) {s = 16;}

                        else if ( (LA112_0==JAPI) ) {s = 17;}

                        else if ( (LA112_0==CONNECTTO) ) {s = 18;}

                        else if ( (LA112_0==OORELCOMMIT) ) {s = 19;}

                        else if ( (LA112_0==OORELINSERT) ) {s = 20;}

                         
                        input.seek(index112_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 112, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA113_eotS =
        "\110\uffff";
    static final String DFA113_eofS =
        "\1\30\107\uffff";
    static final String DFA113_minS =
        "\1\7\107\uffff";
    static final String DFA113_maxS =
        "\1\144\107\uffff";
    static final String DFA113_acceptS =
        "\1\uffff\5\1\1\uffff\1\1\1\uffff\2\1\3\uffff\1\1\11\uffff\1\2\57"+
        "\uffff";
    static final String DFA113_specialS =
        "\1\0\107\uffff}>";
    static final String[] DFA113_transitionS = {
            "\1\30\1\uffff\1\3\1\30\1\11\2\30\13\uffff\1\30\1\uffff\1\30"+
            "\1\uffff\2\30\1\uffff\3\30\11\uffff\1\4\37\30\1\1\1\2\3\30\1"+
            "\3\1\5\1\30\1\3\1\30\1\7\4\12\12\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA113_eot = DFA.unpackEncodedString(DFA113_eotS);
    static final short[] DFA113_eof = DFA.unpackEncodedString(DFA113_eofS);
    static final char[] DFA113_min = DFA.unpackEncodedStringToUnsignedChars(DFA113_minS);
    static final char[] DFA113_max = DFA.unpackEncodedStringToUnsignedChars(DFA113_maxS);
    static final short[] DFA113_accept = DFA.unpackEncodedString(DFA113_acceptS);
    static final short[] DFA113_special = DFA.unpackEncodedString(DFA113_specialS);
    static final short[][] DFA113_transition;

    static {
        int numStates = DFA113_transitionS.length;
        DFA113_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA113_transition[i] = DFA.unpackEncodedString(DFA113_transitionS[i]);
        }
    }

    class DFA113 extends DFA {

        public DFA113(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 113;
            this.eot = DFA113_eot;
            this.eof = DFA113_eof;
            this.min = DFA113_min;
            this.max = DFA113_max;
            this.accept = DFA113_accept;
            this.special = DFA113_special;
            this.transition = DFA113_transition;
        }
        public String getDescription() {
            return "1892:24: (e= expr[expr_contextType.Load] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA113_0 = input.LA(1);

                         
                        int index113_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA113_0==PLUS) ) {s = 1;}

                        else if ( (LA113_0==MINUS) ) {s = 2;}

                        else if ( (LA113_0==NAME||LA113_0==TILDE||LA113_0==LCURLY) ) {s = 3;}

                        else if ( (LA113_0==LPAREN) ) {s = 4;}

                        else if ( (LA113_0==LBRACK) ) {s = 5;}

                        else if ( (LA113_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA113_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( ((LA113_0>=INT && LA113_0<=COMPLEX)) ) {s = 10;}

                        else if ( ((LA113_0>=STRING && LA113_0<=CONNECTTO)) ) {s = 14;}

                        else if ( (LA113_0==EOF||LA113_0==NEWLINE||LA113_0==DOT||(LA113_0>=AND && LA113_0<=AS)||LA113_0==FOR||LA113_0==IF||(LA113_0>=IN && LA113_0<=IS)||(LA113_0>=NOT && LA113_0<=ORELSE)||(LA113_0>=RPAREN && LA113_0<=LEFTSHIFT)||(LA113_0>=SLASH && LA113_0<=DOUBLESLASH)||LA113_0==RBRACK||LA113_0==RCURLY) ) {s = 24;}

                         
                        input.seek(index113_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 113, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA115_eotS =
        "\110\uffff";
    static final String DFA115_eofS =
        "\1\30\107\uffff";
    static final String DFA115_minS =
        "\1\7\107\uffff";
    static final String DFA115_maxS =
        "\1\144\107\uffff";
    static final String DFA115_acceptS =
        "\1\uffff\5\1\1\uffff\1\1\1\uffff\2\1\3\uffff\1\1\11\uffff\1\2\57"+
        "\uffff";
    static final String DFA115_specialS =
        "\1\0\107\uffff}>";
    static final String[] DFA115_transitionS = {
            "\1\30\1\uffff\1\3\1\30\1\11\2\30\13\uffff\1\30\1\uffff\1\30"+
            "\1\uffff\2\30\1\uffff\3\30\11\uffff\1\4\37\30\1\1\1\2\3\30\1"+
            "\3\1\5\1\30\1\3\1\30\1\7\4\12\12\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA115_eot = DFA.unpackEncodedString(DFA115_eotS);
    static final short[] DFA115_eof = DFA.unpackEncodedString(DFA115_eofS);
    static final char[] DFA115_min = DFA.unpackEncodedStringToUnsignedChars(DFA115_minS);
    static final char[] DFA115_max = DFA.unpackEncodedStringToUnsignedChars(DFA115_maxS);
    static final short[] DFA115_accept = DFA.unpackEncodedString(DFA115_acceptS);
    static final short[] DFA115_special = DFA.unpackEncodedString(DFA115_specialS);
    static final short[][] DFA115_transition;

    static {
        int numStates = DFA115_transitionS.length;
        DFA115_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA115_transition[i] = DFA.unpackEncodedString(DFA115_transitionS[i]);
        }
    }

    class DFA115 extends DFA {

        public DFA115(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 115;
            this.eot = DFA115_eot;
            this.eof = DFA115_eof;
            this.min = DFA115_min;
            this.max = DFA115_max;
            this.accept = DFA115_accept;
            this.special = DFA115_special;
            this.transition = DFA115_transition;
        }
        public String getDescription() {
            return "1916:24: (e= expr[expr_contextType.Load] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA115_0 = input.LA(1);

                         
                        int index115_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA115_0==PLUS) ) {s = 1;}

                        else if ( (LA115_0==MINUS) ) {s = 2;}

                        else if ( (LA115_0==NAME||LA115_0==TILDE||LA115_0==LCURLY) ) {s = 3;}

                        else if ( (LA115_0==LPAREN) ) {s = 4;}

                        else if ( (LA115_0==LBRACK) ) {s = 5;}

                        else if ( (LA115_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA115_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( ((LA115_0>=INT && LA115_0<=COMPLEX)) ) {s = 10;}

                        else if ( ((LA115_0>=STRING && LA115_0<=CONNECTTO)) ) {s = 14;}

                        else if ( (LA115_0==EOF||LA115_0==NEWLINE||LA115_0==DOT||(LA115_0>=AND && LA115_0<=AS)||LA115_0==FOR||LA115_0==IF||(LA115_0>=IN && LA115_0<=IS)||(LA115_0>=NOT && LA115_0<=ORELSE)||(LA115_0>=RPAREN && LA115_0<=LEFTSHIFT)||(LA115_0>=SLASH && LA115_0<=DOUBLESLASH)||LA115_0==RBRACK||LA115_0==RCURLY) ) {s = 24;}

                         
                        input.seek(index115_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 115, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA117_eotS =
        "\110\uffff";
    static final String DFA117_eofS =
        "\1\30\107\uffff";
    static final String DFA117_minS =
        "\1\7\107\uffff";
    static final String DFA117_maxS =
        "\1\144\107\uffff";
    static final String DFA117_acceptS =
        "\1\uffff\5\1\1\uffff\1\1\1\uffff\2\1\3\uffff\1\1\11\uffff\1\2\57"+
        "\uffff";
    static final String DFA117_specialS =
        "\1\0\107\uffff}>";
    static final String[] DFA117_transitionS = {
            "\1\30\1\uffff\1\3\1\30\1\11\2\30\13\uffff\1\30\1\uffff\1\30"+
            "\1\uffff\2\30\1\uffff\3\30\11\uffff\1\4\37\30\1\1\1\2\3\30\1"+
            "\3\1\5\1\30\1\3\1\30\1\7\4\12\12\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA117_eot = DFA.unpackEncodedString(DFA117_eotS);
    static final short[] DFA117_eof = DFA.unpackEncodedString(DFA117_eofS);
    static final char[] DFA117_min = DFA.unpackEncodedStringToUnsignedChars(DFA117_minS);
    static final char[] DFA117_max = DFA.unpackEncodedStringToUnsignedChars(DFA117_maxS);
    static final short[] DFA117_accept = DFA.unpackEncodedString(DFA117_acceptS);
    static final short[] DFA117_special = DFA.unpackEncodedString(DFA117_specialS);
    static final short[][] DFA117_transition;

    static {
        int numStates = DFA117_transitionS.length;
        DFA117_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA117_transition[i] = DFA.unpackEncodedString(DFA117_transitionS[i]);
        }
    }

    class DFA117 extends DFA {

        public DFA117(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 117;
            this.eot = DFA117_eot;
            this.eof = DFA117_eof;
            this.min = DFA117_min;
            this.max = DFA117_max;
            this.accept = DFA117_accept;
            this.special = DFA117_special;
            this.transition = DFA117_transition;
        }
        public String getDescription() {
            return "1938:24: (e= expr[expr_contextType.Load] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA117_0 = input.LA(1);

                         
                        int index117_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA117_0==PLUS) ) {s = 1;}

                        else if ( (LA117_0==MINUS) ) {s = 2;}

                        else if ( (LA117_0==NAME||LA117_0==TILDE||LA117_0==LCURLY) ) {s = 3;}

                        else if ( (LA117_0==LPAREN) ) {s = 4;}

                        else if ( (LA117_0==LBRACK) ) {s = 5;}

                        else if ( (LA117_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA117_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( ((LA117_0>=INT && LA117_0<=COMPLEX)) ) {s = 10;}

                        else if ( ((LA117_0>=STRING && LA117_0<=CONNECTTO)) ) {s = 14;}

                        else if ( (LA117_0==EOF||LA117_0==NEWLINE||LA117_0==DOT||(LA117_0>=AND && LA117_0<=AS)||LA117_0==FOR||LA117_0==IF||(LA117_0>=IN && LA117_0<=IS)||(LA117_0>=NOT && LA117_0<=ORELSE)||(LA117_0>=RPAREN && LA117_0<=LEFTSHIFT)||(LA117_0>=SLASH && LA117_0<=DOUBLESLASH)||LA117_0==RBRACK||LA117_0==RCURLY) ) {s = 24;}

                         
                        input.seek(index117_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 117, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA119_eotS =
        "\110\uffff";
    static final String DFA119_eofS =
        "\1\30\107\uffff";
    static final String DFA119_minS =
        "\1\7\107\uffff";
    static final String DFA119_maxS =
        "\1\144\107\uffff";
    static final String DFA119_acceptS =
        "\1\uffff\5\1\1\uffff\1\1\1\uffff\2\1\3\uffff\1\1\11\uffff\1\2\57"+
        "\uffff";
    static final String DFA119_specialS =
        "\1\0\107\uffff}>";
    static final String[] DFA119_transitionS = {
            "\1\30\1\uffff\1\3\1\30\1\11\2\30\13\uffff\1\30\1\uffff\1\30"+
            "\1\uffff\2\30\1\uffff\3\30\11\uffff\1\4\37\30\1\1\1\2\3\30\1"+
            "\3\1\5\1\30\1\3\1\30\1\7\4\12\12\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA119_eot = DFA.unpackEncodedString(DFA119_eotS);
    static final short[] DFA119_eof = DFA.unpackEncodedString(DFA119_eofS);
    static final char[] DFA119_min = DFA.unpackEncodedStringToUnsignedChars(DFA119_minS);
    static final char[] DFA119_max = DFA.unpackEncodedStringToUnsignedChars(DFA119_maxS);
    static final short[] DFA119_accept = DFA.unpackEncodedString(DFA119_acceptS);
    static final short[] DFA119_special = DFA.unpackEncodedString(DFA119_specialS);
    static final short[][] DFA119_transition;

    static {
        int numStates = DFA119_transitionS.length;
        DFA119_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA119_transition[i] = DFA.unpackEncodedString(DFA119_transitionS[i]);
        }
    }

    class DFA119 extends DFA {

        public DFA119(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 119;
            this.eot = DFA119_eot;
            this.eof = DFA119_eof;
            this.min = DFA119_min;
            this.max = DFA119_max;
            this.accept = DFA119_accept;
            this.special = DFA119_special;
            this.transition = DFA119_transition;
        }
        public String getDescription() {
            return "1959:24: (e= expr[expr_contextType.Load] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA119_0 = input.LA(1);

                         
                        int index119_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA119_0==PLUS) ) {s = 1;}

                        else if ( (LA119_0==MINUS) ) {s = 2;}

                        else if ( (LA119_0==NAME||LA119_0==TILDE||LA119_0==LCURLY) ) {s = 3;}

                        else if ( (LA119_0==LPAREN) ) {s = 4;}

                        else if ( (LA119_0==LBRACK) ) {s = 5;}

                        else if ( (LA119_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA119_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( ((LA119_0>=INT && LA119_0<=COMPLEX)) ) {s = 10;}

                        else if ( ((LA119_0>=STRING && LA119_0<=CONNECTTO)) ) {s = 14;}

                        else if ( (LA119_0==EOF||LA119_0==NEWLINE||LA119_0==DOT||(LA119_0>=AND && LA119_0<=AS)||LA119_0==FOR||LA119_0==IF||(LA119_0>=IN && LA119_0<=IS)||(LA119_0>=NOT && LA119_0<=ORELSE)||(LA119_0>=RPAREN && LA119_0<=LEFTSHIFT)||(LA119_0>=SLASH && LA119_0<=DOUBLESLASH)||LA119_0==RBRACK||LA119_0==RCURLY) ) {s = 24;}

                         
                        input.seek(index119_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 119, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA121_eotS =
        "\110\uffff";
    static final String DFA121_eofS =
        "\1\30\107\uffff";
    static final String DFA121_minS =
        "\1\7\107\uffff";
    static final String DFA121_maxS =
        "\1\144\107\uffff";
    static final String DFA121_acceptS =
        "\1\uffff\5\1\1\uffff\1\1\1\uffff\2\1\3\uffff\1\1\11\uffff\1\2\57"+
        "\uffff";
    static final String DFA121_specialS =
        "\1\0\107\uffff}>";
    static final String[] DFA121_transitionS = {
            "\1\30\1\uffff\1\3\1\30\1\11\2\30\13\uffff\1\30\1\uffff\1\30"+
            "\1\uffff\2\30\1\uffff\3\30\11\uffff\1\4\37\30\1\1\1\2\3\30\1"+
            "\3\1\5\1\30\1\3\1\30\1\7\4\12\12\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA121_eot = DFA.unpackEncodedString(DFA121_eotS);
    static final short[] DFA121_eof = DFA.unpackEncodedString(DFA121_eofS);
    static final char[] DFA121_min = DFA.unpackEncodedStringToUnsignedChars(DFA121_minS);
    static final char[] DFA121_max = DFA.unpackEncodedStringToUnsignedChars(DFA121_maxS);
    static final short[] DFA121_accept = DFA.unpackEncodedString(DFA121_acceptS);
    static final short[] DFA121_special = DFA.unpackEncodedString(DFA121_specialS);
    static final short[][] DFA121_transition;

    static {
        int numStates = DFA121_transitionS.length;
        DFA121_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA121_transition[i] = DFA.unpackEncodedString(DFA121_transitionS[i]);
        }
    }

    class DFA121 extends DFA {

        public DFA121(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 121;
            this.eot = DFA121_eot;
            this.eof = DFA121_eof;
            this.min = DFA121_min;
            this.max = DFA121_max;
            this.accept = DFA121_accept;
            this.special = DFA121_special;
            this.transition = DFA121_transition;
        }
        public String getDescription() {
            return "1981:24: (e= expr[expr_contextType.Load] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA121_0 = input.LA(1);

                         
                        int index121_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA121_0==PLUS) ) {s = 1;}

                        else if ( (LA121_0==MINUS) ) {s = 2;}

                        else if ( (LA121_0==NAME||LA121_0==TILDE||LA121_0==LCURLY) ) {s = 3;}

                        else if ( (LA121_0==LPAREN) ) {s = 4;}

                        else if ( (LA121_0==LBRACK) ) {s = 5;}

                        else if ( (LA121_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA121_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( ((LA121_0>=INT && LA121_0<=COMPLEX)) ) {s = 10;}

                        else if ( ((LA121_0>=STRING && LA121_0<=CONNECTTO)) ) {s = 14;}

                        else if ( (LA121_0==EOF||LA121_0==NEWLINE||LA121_0==DOT||(LA121_0>=AND && LA121_0<=AS)||LA121_0==FOR||LA121_0==IF||(LA121_0>=IN && LA121_0<=IS)||(LA121_0>=NOT && LA121_0<=ORELSE)||(LA121_0>=RPAREN && LA121_0<=LEFTSHIFT)||(LA121_0>=SLASH && LA121_0<=DOUBLESLASH)||LA121_0==RBRACK||LA121_0==RCURLY) ) {s = 24;}

                         
                        input.seek(index121_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 121, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA123_eotS =
        "\110\uffff";
    static final String DFA123_eofS =
        "\1\30\107\uffff";
    static final String DFA123_minS =
        "\1\7\107\uffff";
    static final String DFA123_maxS =
        "\1\144\107\uffff";
    static final String DFA123_acceptS =
        "\1\uffff\5\1\1\uffff\1\1\1\uffff\2\1\3\uffff\1\1\11\uffff\1\2\57"+
        "\uffff";
    static final String DFA123_specialS =
        "\1\0\107\uffff}>";
    static final String[] DFA123_transitionS = {
            "\1\30\1\uffff\1\3\1\30\1\11\2\30\13\uffff\1\30\1\uffff\1\30"+
            "\1\uffff\2\30\1\uffff\3\30\11\uffff\1\4\37\30\1\1\1\2\3\30\1"+
            "\3\1\5\1\30\1\3\1\30\1\7\4\12\12\16",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA123_eot = DFA.unpackEncodedString(DFA123_eotS);
    static final short[] DFA123_eof = DFA.unpackEncodedString(DFA123_eofS);
    static final char[] DFA123_min = DFA.unpackEncodedStringToUnsignedChars(DFA123_minS);
    static final char[] DFA123_max = DFA.unpackEncodedStringToUnsignedChars(DFA123_maxS);
    static final short[] DFA123_accept = DFA.unpackEncodedString(DFA123_acceptS);
    static final short[] DFA123_special = DFA.unpackEncodedString(DFA123_specialS);
    static final short[][] DFA123_transition;

    static {
        int numStates = DFA123_transitionS.length;
        DFA123_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA123_transition[i] = DFA.unpackEncodedString(DFA123_transitionS[i]);
        }
    }

    class DFA123 extends DFA {

        public DFA123(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 123;
            this.eot = DFA123_eot;
            this.eof = DFA123_eof;
            this.min = DFA123_min;
            this.max = DFA123_max;
            this.accept = DFA123_accept;
            this.special = DFA123_special;
            this.transition = DFA123_transition;
        }
        public String getDescription() {
            return "2003:24: (e= expr[expr_contextType.Load] )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA123_0 = input.LA(1);

                         
                        int index123_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA123_0==PLUS) ) {s = 1;}

                        else if ( (LA123_0==MINUS) ) {s = 2;}

                        else if ( (LA123_0==NAME||LA123_0==TILDE||LA123_0==LCURLY) ) {s = 3;}

                        else if ( (LA123_0==LPAREN) ) {s = 4;}

                        else if ( (LA123_0==LBRACK) ) {s = 5;}

                        else if ( (LA123_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA123_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( ((LA123_0>=INT && LA123_0<=COMPLEX)) ) {s = 10;}

                        else if ( ((LA123_0>=STRING && LA123_0<=CONNECTTO)) ) {s = 14;}

                        else if ( (LA123_0==EOF||LA123_0==NEWLINE||LA123_0==DOT||(LA123_0>=AND && LA123_0<=AS)||LA123_0==FOR||LA123_0==IF||(LA123_0>=IN && LA123_0<=IS)||(LA123_0>=NOT && LA123_0<=ORELSE)||(LA123_0>=RPAREN && LA123_0<=LEFTSHIFT)||(LA123_0>=SLASH && LA123_0<=DOUBLESLASH)||LA123_0==RBRACK||LA123_0==RCURLY) ) {s = 24;}

                         
                        input.seek(index123_0);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 123, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA130_eotS =
        "\35\uffff";
    static final String DFA130_eofS =
        "\35\uffff";
    static final String DFA130_minS =
        "\1\55\1\11\33\uffff";
    static final String DFA130_maxS =
        "\1\60\1\144\33\uffff";
    static final String DFA130_acceptS =
        "\2\uffff\1\2\1\uffff\1\1\30\uffff";
    static final String DFA130_specialS =
        "\35\uffff}>";
    static final String[] DFA130_transitionS = {
            "\1\2\2\uffff\1\1",
            "\1\4\1\uffff\1\4\23\uffff\2\4\13\uffff\1\4\1\2\36\uffff\2\4"+
            "\3\uffff\2\4\1\uffff\1\4\1\uffff\17\4",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA130_eot = DFA.unpackEncodedString(DFA130_eotS);
    static final short[] DFA130_eof = DFA.unpackEncodedString(DFA130_eofS);
    static final char[] DFA130_min = DFA.unpackEncodedStringToUnsignedChars(DFA130_minS);
    static final char[] DFA130_max = DFA.unpackEncodedStringToUnsignedChars(DFA130_maxS);
    static final short[] DFA130_accept = DFA.unpackEncodedString(DFA130_acceptS);
    static final short[] DFA130_special = DFA.unpackEncodedString(DFA130_specialS);
    static final short[][] DFA130_transition;

    static {
        int numStates = DFA130_transitionS.length;
        DFA130_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA130_transition[i] = DFA.unpackEncodedString(DFA130_transitionS[i]);
        }
    }

    class DFA130 extends DFA {

        public DFA130(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 130;
            this.eot = DFA130_eot;
            this.eof = DFA130_eof;
            this.min = DFA130_min;
            this.max = DFA130_max;
            this.accept = DFA130_accept;
            this.special = DFA130_special;
            this.transition = DFA130_transition;
        }
        public String getDescription() {
            return "()* loopback of 2092:11: ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )*";
        }
    }
    static final String DFA143_eotS =
        "\36\uffff";
    static final String DFA143_eofS =
        "\36\uffff";
    static final String DFA143_minS =
        "\1\11\1\uffff\31\0\3\uffff";
    static final String DFA143_maxS =
        "\1\144\1\uffff\31\0\3\uffff";
    static final String DFA143_acceptS =
        "\1\uffff\1\1\31\uffff\1\3\1\2\1\4";
    static final String DFA143_specialS =
        "\1\0\1\uffff\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14"+
        "\1\15\1\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31"+
        "\3\uffff}>";
    static final String[] DFA143_transitionS = {
            "\1\12\1\1\1\13\23\uffff\1\32\1\2\13\uffff\1\6\1\uffff\1\33\35"+
            "\uffff\1\3\1\4\3\uffff\1\5\1\7\1\uffff\1\10\1\uffff\1\11\1\14"+
            "\1\15\1\16\1\17\1\20\1\30\1\31\1\21\1\22\1\23\1\26\1\25\1\24"+
            "\1\27",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA143_eot = DFA.unpackEncodedString(DFA143_eotS);
    static final short[] DFA143_eof = DFA.unpackEncodedString(DFA143_eofS);
    static final char[] DFA143_min = DFA.unpackEncodedStringToUnsignedChars(DFA143_minS);
    static final char[] DFA143_max = DFA.unpackEncodedStringToUnsignedChars(DFA143_maxS);
    static final short[] DFA143_accept = DFA.unpackEncodedString(DFA143_acceptS);
    static final short[] DFA143_special = DFA.unpackEncodedString(DFA143_specialS);
    static final short[][] DFA143_transition;

    static {
        int numStates = DFA143_transitionS.length;
        DFA143_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA143_transition[i] = DFA.unpackEncodedString(DFA143_transitionS[i]);
        }
    }

    class DFA143 extends DFA {

        public DFA143(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 143;
            this.eot = DFA143_eot;
            this.eof = DFA143_eof;
            this.min = DFA143_min;
            this.max = DFA143_max;
            this.accept = DFA143_accept;
            this.special = DFA143_special;
            this.transition = DFA143_transition;
        }
        public String getDescription() {
            return "2177:1: subscript returns [slice sltype] : (d1= DOT DOT DOT | ( test[null] COLON )=>lower= test[expr_contextType.Load] (c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )? )? | ( COLON )=>c2= COLON (upper2= test[expr_contextType.Load] )? ( sliceop )? | test[expr_contextType.Load] );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA143_0 = input.LA(1);

                         
                        int index143_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA143_0==DOT) ) {s = 1;}

                        else if ( (LA143_0==NOT) ) {s = 2;}

                        else if ( (LA143_0==PLUS) ) {s = 3;}

                        else if ( (LA143_0==MINUS) ) {s = 4;}

                        else if ( (LA143_0==TILDE) ) {s = 5;}

                        else if ( (LA143_0==LPAREN) ) {s = 6;}

                        else if ( (LA143_0==LBRACK) ) {s = 7;}

                        else if ( (LA143_0==LCURLY) ) {s = 8;}

                        else if ( (LA143_0==BACKQUOTE) ) {s = 9;}

                        else if ( (LA143_0==NAME) ) {s = 10;}

                        else if ( (LA143_0==PRINT) && ((printFunction))) {s = 11;}

                        else if ( (LA143_0==INT) ) {s = 12;}

                        else if ( (LA143_0==LONGINT) ) {s = 13;}

                        else if ( (LA143_0==FLOAT) ) {s = 14;}

                        else if ( (LA143_0==COMPLEX) ) {s = 15;}

                        else if ( (LA143_0==STRING) ) {s = 16;}

                        else if ( (LA143_0==SQL) ) {s = 17;}

                        else if ( (LA143_0==SIM) ) {s = 18;}

                        else if ( (LA143_0==Neo4j) ) {s = 19;}

                        else if ( (LA143_0==SPARQL) ) {s = 20;}

                        else if ( (LA143_0==RDF) ) {s = 21;}

                        else if ( (LA143_0==JAPI) ) {s = 22;}

                        else if ( (LA143_0==CONNECTTO) ) {s = 23;}

                        else if ( (LA143_0==OORELCOMMIT) ) {s = 24;}

                        else if ( (LA143_0==OORELINSERT) ) {s = 25;}

                        else if ( (LA143_0==LAMBDA) ) {s = 26;}

                        else if ( (LA143_0==COLON) && (synpred9_Python())) {s = 27;}

                         
                        input.seek(index143_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA143_2 = input.LA(1);

                         
                        int index143_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA143_3 = input.LA(1);

                         
                        int index143_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA143_4 = input.LA(1);

                         
                        int index143_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA143_5 = input.LA(1);

                         
                        int index143_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA143_6 = input.LA(1);

                         
                        int index143_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA143_7 = input.LA(1);

                         
                        int index143_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA143_8 = input.LA(1);

                         
                        int index143_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA143_9 = input.LA(1);

                         
                        int index143_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA143_10 = input.LA(1);

                         
                        int index143_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA143_11 = input.LA(1);

                         
                        int index143_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((synpred8_Python()&&(printFunction))) ) {s = 28;}

                        else if ( ((printFunction)) ) {s = 29;}

                         
                        input.seek(index143_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA143_12 = input.LA(1);

                         
                        int index143_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_12);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA143_13 = input.LA(1);

                         
                        int index143_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_13);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA143_14 = input.LA(1);

                         
                        int index143_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_14);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA143_15 = input.LA(1);

                         
                        int index143_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_15);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA143_16 = input.LA(1);

                         
                        int index143_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_16);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA143_17 = input.LA(1);

                         
                        int index143_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_17);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA143_18 = input.LA(1);

                         
                        int index143_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_18);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA143_19 = input.LA(1);

                         
                        int index143_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_19);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA143_20 = input.LA(1);

                         
                        int index143_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_20);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA143_21 = input.LA(1);

                         
                        int index143_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_21);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA143_22 = input.LA(1);

                         
                        int index143_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_22);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA143_23 = input.LA(1);

                         
                        int index143_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_23);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA143_24 = input.LA(1);

                         
                        int index143_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_24);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA143_25 = input.LA(1);

                         
                        int index143_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_25);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA143_26 = input.LA(1);

                         
                        int index143_26 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred8_Python()) ) {s = 28;}

                        else if ( (true) ) {s = 29;}

                         
                        input.seek(index143_26);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 143, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA147_eotS =
        "\32\uffff";
    static final String DFA147_eofS =
        "\32\uffff";
    static final String DFA147_minS =
        "\1\11\27\0\2\uffff";
    static final String DFA147_maxS =
        "\1\144\27\0\2\uffff";
    static final String DFA147_acceptS =
        "\30\uffff\1\1\1\2";
    static final String DFA147_specialS =
        "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1"+
        "\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\2\uffff}>";
    static final String[] DFA147_transitionS = {
            "\1\10\1\uffff\1\11\40\uffff\1\4\37\uffff\1\1\1\2\3\uffff\1\3"+
            "\1\5\1\uffff\1\6\1\uffff\1\7\1\12\1\13\1\14\1\15\1\16\1\26\1"+
            "\27\1\17\1\20\1\21\1\24\1\23\1\22\1\25",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA147_eot = DFA.unpackEncodedString(DFA147_eotS);
    static final short[] DFA147_eof = DFA.unpackEncodedString(DFA147_eofS);
    static final char[] DFA147_min = DFA.unpackEncodedStringToUnsignedChars(DFA147_minS);
    static final char[] DFA147_max = DFA.unpackEncodedStringToUnsignedChars(DFA147_maxS);
    static final short[] DFA147_accept = DFA.unpackEncodedString(DFA147_acceptS);
    static final short[] DFA147_special = DFA.unpackEncodedString(DFA147_specialS);
    static final short[][] DFA147_transition;

    static {
        int numStates = DFA147_transitionS.length;
        DFA147_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA147_transition[i] = DFA.unpackEncodedString(DFA147_transitionS[i]);
        }
    }

    class DFA147 extends DFA {

        public DFA147(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 147;
            this.eot = DFA147_eot;
            this.eof = DFA147_eof;
            this.min = DFA147_min;
            this.max = DFA147_max;
            this.accept = DFA147_accept;
            this.special = DFA147_special;
            this.transition = DFA147_transition;
        }
        public String getDescription() {
            return "2223:1: exprlist[expr_contextType ctype] returns [expr etype] : ( ( expr[null] COMMA )=>e+= expr[ctype] ( options {k=2; } : COMMA e+= expr[ctype] )* ( COMMA )? | expr[ctype] );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA147_0 = input.LA(1);

                         
                        int index147_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA147_0==PLUS) ) {s = 1;}

                        else if ( (LA147_0==MINUS) ) {s = 2;}

                        else if ( (LA147_0==TILDE) ) {s = 3;}

                        else if ( (LA147_0==LPAREN) ) {s = 4;}

                        else if ( (LA147_0==LBRACK) ) {s = 5;}

                        else if ( (LA147_0==LCURLY) ) {s = 6;}

                        else if ( (LA147_0==BACKQUOTE) ) {s = 7;}

                        else if ( (LA147_0==NAME) ) {s = 8;}

                        else if ( (LA147_0==PRINT) && ((printFunction))) {s = 9;}

                        else if ( (LA147_0==INT) ) {s = 10;}

                        else if ( (LA147_0==LONGINT) ) {s = 11;}

                        else if ( (LA147_0==FLOAT) ) {s = 12;}

                        else if ( (LA147_0==COMPLEX) ) {s = 13;}

                        else if ( (LA147_0==STRING) ) {s = 14;}

                        else if ( (LA147_0==SQL) ) {s = 15;}

                        else if ( (LA147_0==SIM) ) {s = 16;}

                        else if ( (LA147_0==Neo4j) ) {s = 17;}

                        else if ( (LA147_0==SPARQL) ) {s = 18;}

                        else if ( (LA147_0==RDF) ) {s = 19;}

                        else if ( (LA147_0==JAPI) ) {s = 20;}

                        else if ( (LA147_0==CONNECTTO) ) {s = 21;}

                        else if ( (LA147_0==OORELCOMMIT) ) {s = 22;}

                        else if ( (LA147_0==OORELINSERT) ) {s = 23;}

                         
                        input.seek(index147_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA147_1 = input.LA(1);

                         
                        int index147_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA147_2 = input.LA(1);

                         
                        int index147_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA147_3 = input.LA(1);

                         
                        int index147_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA147_4 = input.LA(1);

                         
                        int index147_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA147_5 = input.LA(1);

                         
                        int index147_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA147_6 = input.LA(1);

                         
                        int index147_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA147_7 = input.LA(1);

                         
                        int index147_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA147_8 = input.LA(1);

                         
                        int index147_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA147_9 = input.LA(1);

                         
                        int index147_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((synpred10_Python()&&(printFunction))) ) {s = 24;}

                        else if ( ((printFunction)) ) {s = 25;}

                         
                        input.seek(index147_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA147_10 = input.LA(1);

                         
                        int index147_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA147_11 = input.LA(1);

                         
                        int index147_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA147_12 = input.LA(1);

                         
                        int index147_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA147_13 = input.LA(1);

                         
                        int index147_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA147_14 = input.LA(1);

                         
                        int index147_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA147_15 = input.LA(1);

                         
                        int index147_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA147_16 = input.LA(1);

                         
                        int index147_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA147_17 = input.LA(1);

                         
                        int index147_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA147_18 = input.LA(1);

                         
                        int index147_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA147_19 = input.LA(1);

                         
                        int index147_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA147_20 = input.LA(1);

                         
                        int index147_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA147_21 = input.LA(1);

                         
                        int index147_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA147_22 = input.LA(1);

                         
                        int index147_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA147_23 = input.LA(1);

                         
                        int index147_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred10_Python()) ) {s = 24;}

                        else if ( (true) ) {s = 25;}

                         
                        input.seek(index147_23);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 147, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA145_eotS =
        "\33\uffff";
    static final String DFA145_eofS =
        "\33\uffff";
    static final String DFA145_minS =
        "\1\35\1\11\31\uffff";
    static final String DFA145_maxS =
        "\1\60\1\144\31\uffff";
    static final String DFA145_acceptS =
        "\2\uffff\1\2\1\1\27\uffff";
    static final String DFA145_specialS =
        "\33\uffff}>";
    static final String[] DFA145_transitionS = {
            "\1\2\22\uffff\1\1",
            "\1\3\1\uffff\1\3\21\uffff\1\2\16\uffff\1\3\37\uffff\2\3\3\uffff"+
            "\2\3\1\uffff\1\3\1\uffff\17\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA145_eot = DFA.unpackEncodedString(DFA145_eotS);
    static final short[] DFA145_eof = DFA.unpackEncodedString(DFA145_eofS);
    static final char[] DFA145_min = DFA.unpackEncodedStringToUnsignedChars(DFA145_minS);
    static final char[] DFA145_max = DFA.unpackEncodedStringToUnsignedChars(DFA145_maxS);
    static final short[] DFA145_accept = DFA.unpackEncodedString(DFA145_acceptS);
    static final short[] DFA145_special = DFA.unpackEncodedString(DFA145_specialS);
    static final short[][] DFA145_transition;

    static {
        int numStates = DFA145_transitionS.length;
        DFA145_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA145_transition[i] = DFA.unpackEncodedString(DFA145_transitionS[i]);
        }
    }

    class DFA145 extends DFA {

        public DFA145(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 145;
            this.eot = DFA145_eot;
            this.eof = DFA145_eof;
            this.min = DFA145_min;
            this.max = DFA145_max;
            this.accept = DFA145_accept;
            this.special = DFA145_special;
            this.transition = DFA145_transition;
        }
        public String getDescription() {
            return "()* loopback of 2225:44: ( options {k=2; } : COMMA e+= expr[ctype] )*";
        }
    }
    static final String DFA148_eotS =
        "\35\uffff";
    static final String DFA148_eofS =
        "\35\uffff";
    static final String DFA148_minS =
        "\2\7\33\uffff";
    static final String DFA148_maxS =
        "\1\63\1\144\33\uffff";
    static final String DFA148_acceptS =
        "\2\uffff\1\2\3\uffff\1\1\26\uffff";
    static final String DFA148_specialS =
        "\35\uffff}>";
    static final String[] DFA148_transitionS = {
            "\1\2\50\uffff\1\1\2\uffff\1\2",
            "\1\2\1\uffff\1\6\1\uffff\1\6\40\uffff\1\6\6\uffff\1\2\30\uffff"+
            "\2\6\3\uffff\2\6\1\uffff\1\6\1\uffff\17\6",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA148_eot = DFA.unpackEncodedString(DFA148_eotS);
    static final short[] DFA148_eof = DFA.unpackEncodedString(DFA148_eofS);
    static final char[] DFA148_min = DFA.unpackEncodedStringToUnsignedChars(DFA148_minS);
    static final char[] DFA148_max = DFA.unpackEncodedStringToUnsignedChars(DFA148_maxS);
    static final short[] DFA148_accept = DFA.unpackEncodedString(DFA148_acceptS);
    static final short[] DFA148_special = DFA.unpackEncodedString(DFA148_specialS);
    static final short[][] DFA148_transition;

    static {
        int numStates = DFA148_transitionS.length;
        DFA148_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA148_transition[i] = DFA.unpackEncodedString(DFA148_transitionS[i]);
        }
    }

    class DFA148 extends DFA {

        public DFA148(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 148;
            this.eot = DFA148_eot;
            this.eof = DFA148_eof;
            this.min = DFA148_min;
            this.max = DFA148_max;
            this.accept = DFA148_accept;
            this.special = DFA148_special;
            this.transition = DFA148_transition;
        }
        public String getDescription() {
            return "()* loopback of 2239:37: ( options {k=2; } : COMMA e+= expr[expr_contextType.Del] )*";
        }
    }
    static final String DFA152_eotS =
        "\34\uffff";
    static final String DFA152_eofS =
        "\34\uffff";
    static final String DFA152_minS =
        "\1\11\31\0\2\uffff";
    static final String DFA152_maxS =
        "\1\144\31\0\2\uffff";
    static final String DFA152_acceptS =
        "\32\uffff\1\1\1\2";
    static final String DFA152_specialS =
        "\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1"+
        "\16\1\17\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27\1\30\1\31\2\uffff}>";
    static final String[] DFA152_transitionS = {
            "\1\11\1\uffff\1\12\23\uffff\1\31\1\1\13\uffff\1\5\37\uffff\1"+
            "\2\1\3\3\uffff\1\4\1\6\1\uffff\1\7\1\uffff\1\10\1\13\1\14\1"+
            "\15\1\16\1\17\1\27\1\30\1\20\1\21\1\22\1\25\1\24\1\23\1\26",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA152_eot = DFA.unpackEncodedString(DFA152_eotS);
    static final short[] DFA152_eof = DFA.unpackEncodedString(DFA152_eofS);
    static final char[] DFA152_min = DFA.unpackEncodedStringToUnsignedChars(DFA152_minS);
    static final char[] DFA152_max = DFA.unpackEncodedStringToUnsignedChars(DFA152_maxS);
    static final short[] DFA152_accept = DFA.unpackEncodedString(DFA152_acceptS);
    static final short[] DFA152_special = DFA.unpackEncodedString(DFA152_specialS);
    static final short[][] DFA152_transition;

    static {
        int numStates = DFA152_transitionS.length;
        DFA152_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA152_transition[i] = DFA.unpackEncodedString(DFA152_transitionS[i]);
        }
    }

    class DFA152 extends DFA {

        public DFA152(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 152;
            this.eot = DFA152_eot;
            this.eof = DFA152_eof;
            this.min = DFA152_min;
            this.max = DFA152_max;
            this.accept = DFA152_accept;
            this.special = DFA152_special;
            this.transition = DFA152_transition;
        }
        public String getDescription() {
            return "2246:1: testlist[expr_contextType ctype] : ( ( test[null] COMMA )=>t+= test[ctype] ( options {k=2; } : COMMA t+= test[ctype] )* ( COMMA )? | test[ctype] );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA152_0 = input.LA(1);

                         
                        int index152_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA152_0==NOT) ) {s = 1;}

                        else if ( (LA152_0==PLUS) ) {s = 2;}

                        else if ( (LA152_0==MINUS) ) {s = 3;}

                        else if ( (LA152_0==TILDE) ) {s = 4;}

                        else if ( (LA152_0==LPAREN) ) {s = 5;}

                        else if ( (LA152_0==LBRACK) ) {s = 6;}

                        else if ( (LA152_0==LCURLY) ) {s = 7;}

                        else if ( (LA152_0==BACKQUOTE) ) {s = 8;}

                        else if ( (LA152_0==NAME) ) {s = 9;}

                        else if ( (LA152_0==PRINT) && ((printFunction))) {s = 10;}

                        else if ( (LA152_0==INT) ) {s = 11;}

                        else if ( (LA152_0==LONGINT) ) {s = 12;}

                        else if ( (LA152_0==FLOAT) ) {s = 13;}

                        else if ( (LA152_0==COMPLEX) ) {s = 14;}

                        else if ( (LA152_0==STRING) ) {s = 15;}

                        else if ( (LA152_0==SQL) ) {s = 16;}

                        else if ( (LA152_0==SIM) ) {s = 17;}

                        else if ( (LA152_0==Neo4j) ) {s = 18;}

                        else if ( (LA152_0==SPARQL) ) {s = 19;}

                        else if ( (LA152_0==RDF) ) {s = 20;}

                        else if ( (LA152_0==JAPI) ) {s = 21;}

                        else if ( (LA152_0==CONNECTTO) ) {s = 22;}

                        else if ( (LA152_0==OORELCOMMIT) ) {s = 23;}

                        else if ( (LA152_0==OORELINSERT) ) {s = 24;}

                        else if ( (LA152_0==LAMBDA) ) {s = 25;}

                         
                        input.seek(index152_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA152_1 = input.LA(1);

                         
                        int index152_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA152_2 = input.LA(1);

                         
                        int index152_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA152_3 = input.LA(1);

                         
                        int index152_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_3);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA152_4 = input.LA(1);

                         
                        int index152_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_4);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA152_5 = input.LA(1);

                         
                        int index152_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_5);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA152_6 = input.LA(1);

                         
                        int index152_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_6);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA152_7 = input.LA(1);

                         
                        int index152_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_7);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA152_8 = input.LA(1);

                         
                        int index152_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_8);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA152_9 = input.LA(1);

                         
                        int index152_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_9);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA152_10 = input.LA(1);

                         
                        int index152_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((synpred11_Python()&&(printFunction))) ) {s = 26;}

                        else if ( ((printFunction)) ) {s = 27;}

                         
                        input.seek(index152_10);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA152_11 = input.LA(1);

                         
                        int index152_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_11);
                        if ( s>=0 ) return s;
                        break;
                    case 12 : 
                        int LA152_12 = input.LA(1);

                         
                        int index152_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_12);
                        if ( s>=0 ) return s;
                        break;
                    case 13 : 
                        int LA152_13 = input.LA(1);

                         
                        int index152_13 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_13);
                        if ( s>=0 ) return s;
                        break;
                    case 14 : 
                        int LA152_14 = input.LA(1);

                         
                        int index152_14 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_14);
                        if ( s>=0 ) return s;
                        break;
                    case 15 : 
                        int LA152_15 = input.LA(1);

                         
                        int index152_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_15);
                        if ( s>=0 ) return s;
                        break;
                    case 16 : 
                        int LA152_16 = input.LA(1);

                         
                        int index152_16 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_16);
                        if ( s>=0 ) return s;
                        break;
                    case 17 : 
                        int LA152_17 = input.LA(1);

                         
                        int index152_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_17);
                        if ( s>=0 ) return s;
                        break;
                    case 18 : 
                        int LA152_18 = input.LA(1);

                         
                        int index152_18 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_18);
                        if ( s>=0 ) return s;
                        break;
                    case 19 : 
                        int LA152_19 = input.LA(1);

                         
                        int index152_19 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_19);
                        if ( s>=0 ) return s;
                        break;
                    case 20 : 
                        int LA152_20 = input.LA(1);

                         
                        int index152_20 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_20);
                        if ( s>=0 ) return s;
                        break;
                    case 21 : 
                        int LA152_21 = input.LA(1);

                         
                        int index152_21 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_21);
                        if ( s>=0 ) return s;
                        break;
                    case 22 : 
                        int LA152_22 = input.LA(1);

                         
                        int index152_22 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_22);
                        if ( s>=0 ) return s;
                        break;
                    case 23 : 
                        int LA152_23 = input.LA(1);

                         
                        int index152_23 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_23);
                        if ( s>=0 ) return s;
                        break;
                    case 24 : 
                        int LA152_24 = input.LA(1);

                         
                        int index152_24 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_24);
                        if ( s>=0 ) return s;
                        break;
                    case 25 : 
                        int LA152_25 = input.LA(1);

                         
                        int index152_25 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_Python()) ) {s = 26;}

                        else if ( (true) ) {s = 27;}

                         
                        input.seek(index152_25);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 152, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA150_eotS =
        "\107\uffff";
    static final String DFA150_eofS =
        "\2\2\105\uffff";
    static final String DFA150_minS =
        "\2\7\105\uffff";
    static final String DFA150_maxS =
        "\1\126\1\144\105\uffff";
    static final String DFA150_acceptS =
        "\2\uffff\1\2\25\uffff\1\1\6\uffff\1\1\47\uffff";
    static final String DFA150_specialS =
        "\107\uffff}>";
    static final String[] DFA150_transitionS = {
            "\1\2\21\uffff\1\2\1\uffff\1\2\21\uffff\3\2\1\1\2\uffff\15\2"+
            "\23\uffff\1\2\2\uffff\1\2",
            "\1\2\1\uffff\1\30\1\uffff\1\30\15\uffff\1\2\1\uffff\1\2\3\uffff"+
            "\2\30\13\uffff\1\30\4\2\2\uffff\15\2\14\uffff\2\30\3\uffff\2"+
            "\30\1\2\1\30\1\uffff\1\37\16\30",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA150_eot = DFA.unpackEncodedString(DFA150_eotS);
    static final short[] DFA150_eof = DFA.unpackEncodedString(DFA150_eofS);
    static final char[] DFA150_min = DFA.unpackEncodedStringToUnsignedChars(DFA150_minS);
    static final char[] DFA150_max = DFA.unpackEncodedStringToUnsignedChars(DFA150_maxS);
    static final short[] DFA150_accept = DFA.unpackEncodedString(DFA150_acceptS);
    static final short[] DFA150_special = DFA.unpackEncodedString(DFA150_specialS);
    static final short[][] DFA150_transition;

    static {
        int numStates = DFA150_transitionS.length;
        DFA150_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA150_transition[i] = DFA.unpackEncodedString(DFA150_transitionS[i]);
        }
    }

    class DFA150 extends DFA {

        public DFA150(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 150;
            this.eot = DFA150_eot;
            this.eof = DFA150_eof;
            this.min = DFA150_min;
            this.max = DFA150_max;
            this.accept = DFA150_accept;
            this.special = DFA150_special;
            this.transition = DFA150_transition;
        }
        public String getDescription() {
            return "()* loopback of 2256:22: ( options {k=2; } : COMMA t+= test[ctype] )*";
        }
    }
    static final String DFA153_eotS =
        "\35\uffff";
    static final String DFA153_eofS =
        "\35\uffff";
    static final String DFA153_minS =
        "\1\60\1\11\33\uffff";
    static final String DFA153_maxS =
        "\1\125\1\144\33\uffff";
    static final String DFA153_acceptS =
        "\2\uffff\1\2\1\1\31\uffff";
    static final String DFA153_specialS =
        "\35\uffff}>";
    static final String[] DFA153_transitionS = {
            "\1\1\44\uffff\1\2",
            "\1\3\1\uffff\1\3\23\uffff\2\3\13\uffff\1\3\37\uffff\2\3\3\uffff"+
            "\2\3\1\uffff\1\3\1\2\17\3",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA153_eot = DFA.unpackEncodedString(DFA153_eotS);
    static final short[] DFA153_eof = DFA.unpackEncodedString(DFA153_eofS);
    static final char[] DFA153_min = DFA.unpackEncodedStringToUnsignedChars(DFA153_minS);
    static final char[] DFA153_max = DFA.unpackEncodedStringToUnsignedChars(DFA153_maxS);
    static final short[] DFA153_accept = DFA.unpackEncodedString(DFA153_acceptS);
    static final short[] DFA153_special = DFA.unpackEncodedString(DFA153_specialS);
    static final short[][] DFA153_transition;

    static {
        int numStates = DFA153_transitionS.length;
        DFA153_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA153_transition[i] = DFA.unpackEncodedString(DFA153_transitionS[i]);
        }
    }

    class DFA153 extends DFA {

        public DFA153(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 153;
            this.eot = DFA153_eot;
            this.eof = DFA153_eof;
            this.min = DFA153_min;
            this.max = DFA153_max;
            this.accept = DFA153_accept;
            this.special = DFA153_special;
            this.transition = DFA153_transition;
        }
        public String getDescription() {
            return "()* loopback of 2286:18: ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )*";
        }
    }
 

    public static final BitSet FOLLOW_NEWLINE_in_single_input118 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_EOF_in_single_input121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_stmt_in_single_input137 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NEWLINE_in_single_input139 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_EOF_in_single_input142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compound_stmt_in_single_input158 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NEWLINE_in_single_input160 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_EOF_in_single_input163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_file_input215 = new BitSet(new long[]{0x00001FF99F4FCA80L,0x0000005FFFD63000L});
    public static final BitSet FOLLOW_stmt_in_file_input225 = new BitSet(new long[]{0x00001FF99F4FCA80L,0x0000005FFFD63000L});
    public static final BitSet FOLLOW_EOF_in_file_input244 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEADING_WS_in_eval_input298 = new BitSet(new long[]{0x0000100180000A80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_NEWLINE_in_eval_input302 = new BitSet(new long[]{0x0000100180000A80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_eval_input306 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NEWLINE_in_eval_input310 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_EOF_in_eval_input314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_dotted_attr366 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_DOT_in_dotted_attr377 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_dotted_attr381 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_NAME_in_name_or_print446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRINT_in_name_or_print460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_attr0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AT_in_decorator770 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_dotted_attr_in_decorator772 = new BitSet(new long[]{0x0000100000000080L});
    public static final BitSet FOLLOW_LPAREN_in_decorator780 = new BitSet(new long[]{0x0006300180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_arglist_in_decorator790 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_decorator834 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NEWLINE_in_decorator856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decorator_in_decorators884 = new BitSet(new long[]{0x0000080000000002L});
    public static final BitSet FOLLOW_decorators_in_funcdef922 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DEF_in_funcdef925 = new BitSet(new long[]{0x0000000000000A00L});
    public static final BitSet FOLLOW_name_or_print_in_funcdef927 = new BitSet(new long[]{0x0000100000000000L});
    public static final BitSet FOLLOW_parameters_in_funcdef929 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_funcdef931 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_funcdef933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_parameters966 = new BitSet(new long[]{0x0006300000000200L});
    public static final BitSet FOLLOW_varargslist_in_parameters975 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_parameters1019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fpdef_in_defparameter1052 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_ASSIGN_in_defparameter1056 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_defparameter1058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_defparameter_in_varargslist1104 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist1115 = new BitSet(new long[]{0x0000100000000200L});
    public static final BitSet FOLLOW_defparameter_in_varargslist1119 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist1131 = new BitSet(new long[]{0x0006000000000002L});
    public static final BitSet FOLLOW_STAR_in_varargslist1144 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_varargslist1148 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist1151 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1153 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_varargslist1157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1173 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_varargslist1177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_varargslist1215 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_varargslist1219 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_varargslist1222 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1224 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_varargslist1228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1246 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_varargslist1250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_fpdef1287 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_fpdef1314 = new BitSet(new long[]{0x0000100000000200L});
    public static final BitSet FOLLOW_fplist_in_fpdef1316 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_fpdef1318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_fpdef1334 = new BitSet(new long[]{0x0000100000000200L});
    public static final BitSet FOLLOW_fplist_in_fpdef1337 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_fpdef1339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fpdef_in_fplist1368 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_fplist1385 = new BitSet(new long[]{0x0000100000000200L});
    public static final BitSet FOLLOW_fpdef_in_fplist1389 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_fplist1395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_stmt_in_stmt1431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_compound_stmt_in_stmt1447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_small_stmt_in_simple_stmt1483 = new BitSet(new long[]{0x0008000000000080L});
    public static final BitSet FOLLOW_SEMI_in_simple_stmt1493 = new BitSet(new long[]{0x00001239954ACA00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_small_stmt_in_simple_stmt1497 = new BitSet(new long[]{0x0008000000000080L});
    public static final BitSet FOLLOW_SEMI_in_simple_stmt1502 = new BitSet(new long[]{0x0000000000000080L});
    public static final BitSet FOLLOW_NEWLINE_in_simple_stmt1506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_stmt_in_small_stmt1529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_del_stmt_in_small_stmt1544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pass_stmt_in_small_stmt1559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_flow_stmt_in_small_stmt1574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_import_stmt_in_small_stmt1589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_global_stmt_in_small_stmt1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_exec_stmt_in_small_stmt1619 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assert_stmt_in_small_stmt1634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_print_stmt_in_small_stmt1653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1709 = new BitSet(new long[]{0xFFF0000000000000L});
    public static final BitSet FOLLOW_augassign_in_expr_stmt1725 = new BitSet(new long[]{0x0000023000028000L});
    public static final BitSet FOLLOW_yield_expr_in_expr_stmt1729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_augassign_in_expr_stmt1769 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1828 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1855 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1859 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1904 = new BitSet(new long[]{0x0000023000028000L});
    public static final BitSet FOLLOW_yield_expr_in_expr_stmt1908 = new BitSet(new long[]{0x0000800000000002L});
    public static final BitSet FOLLOW_testlist_in_expr_stmt1956 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUSEQUAL_in_augassign1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUSEQUAL_in_augassign2016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAREQUAL_in_augassign2034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SLASHEQUAL_in_augassign2052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERCENTEQUAL_in_augassign2070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AMPEREQUAL_in_augassign2088 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_VBAREQUAL_in_augassign2106 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CIRCUMFLEXEQUAL_in_augassign2124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFTSHIFTEQUAL_in_augassign2142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RIGHTSHIFTEQUAL_in_augassign2160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAREQUAL_in_augassign2178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESLASHEQUAL_in_augassign2196 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PRINT_in_print_stmt2236 = new BitSet(new long[]{0x0000100180000A02L,0x0000001FFFD63001L});
    public static final BitSet FOLLOW_printlist_in_print_stmt2247 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RIGHTSHIFT_in_print_stmt2266 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_printlist2_in_print_stmt2270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_printlist2350 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_printlist2362 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_printlist2366 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_printlist2374 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_printlist2395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_printlist22452 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_printlist22464 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_printlist22468 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_printlist22476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_printlist22497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DELETE_in_del_stmt2534 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_del_list_in_del_stmt2536 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PASS_in_pass_stmt2572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_break_stmt_in_flow_stmt2598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continue_stmt_in_flow_stmt2606 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_return_stmt_in_flow_stmt2614 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_raise_stmt_in_flow_stmt2622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_yield_stmt_in_flow_stmt2630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BREAK_in_break_stmt2658 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CONTINUE_in_continue_stmt2694 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RETURN_in_return_stmt2730 = new BitSet(new long[]{0x0000100180000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_return_stmt2739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_yield_expr_in_yield_stmt2804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RAISE_in_raise_stmt2840 = new BitSet(new long[]{0x0000100180000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_raise_stmt2845 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_raise_stmt2849 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_raise_stmt2853 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_raise_stmt2865 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_raise_stmt2869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_import_name_in_import_stmt2902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_import_from_in_import_stmt2910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IMPORT_in_import_name2938 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_dotted_as_names_in_import_name2940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FROM_in_import_from2977 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_DOT_in_import_from2982 = new BitSet(new long[]{0x0000000000000600L});
    public static final BitSet FOLLOW_dotted_name_in_import_from2985 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_DOT_in_import_from2991 = new BitSet(new long[]{0x0000000010000400L});
    public static final BitSet FOLLOW_IMPORT_in_import_from2995 = new BitSet(new long[]{0x0002100000000200L});
    public static final BitSet FOLLOW_STAR_in_import_from3006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_import_as_names_in_import_from3031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_import_from3054 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_import_as_names_in_import_from3058 = new BitSet(new long[]{0x0001200000000000L});
    public static final BitSet FOLLOW_COMMA_in_import_from3060 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_import_from3063 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_import_as_name_in_import_as_names3112 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_import_as_names3115 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_import_as_name_in_import_as_names3120 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_NAME_in_import_as_name3161 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_AS_in_import_as_name3164 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_import_as_name3168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotted_name_in_dotted_as_name3208 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_AS_in_dotted_as_name3211 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_dotted_as_name3215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotted_as_name_in_dotted_as_names3251 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_dotted_as_names3254 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_dotted_as_name_in_dotted_as_names3259 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_NAME_in_dotted_name3293 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_DOT_in_dotted_name3296 = new BitSet(new long[]{0x000007FFFFFFFA00L});
    public static final BitSet FOLLOW_attr_in_dotted_name3300 = new BitSet(new long[]{0x0000000000000402L});
    public static final BitSet FOLLOW_GLOBAL_in_global_stmt3336 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_global_stmt3340 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_global_stmt3343 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_global_stmt3347 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_EXEC_in_exec_stmt3385 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_exec_stmt3387 = new BitSet(new long[]{0x0000000020000002L});
    public static final BitSet FOLLOW_IN_in_exec_stmt3391 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_exec_stmt3395 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_exec_stmt3399 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_exec_stmt3403 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ASSERT_in_assert_stmt3444 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_assert_stmt3448 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_assert_stmt3452 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_assert_stmt3456 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_if_stmt_in_compound_stmt3485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_while_stmt_in_compound_stmt3493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_for_stmt_in_compound_stmt3501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_try_stmt_in_compound_stmt3509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_with_stmt_in_compound_stmt3517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_batch_stmt_in_compound_stmt3525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_funcdef_in_compound_stmt3542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_classdef_in_compound_stmt3550 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_stmt3578 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_if_stmt3580 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_if_stmt3583 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_if_stmt3587 = new BitSet(new long[]{0x0000000400100002L});
    public static final BitSet FOLLOW_elif_clause_in_if_stmt3590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_else_clause_in_elif_clause3635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ELIF_in_elif_clause3651 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_elif_clause3653 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_elif_clause3656 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_elif_clause3658 = new BitSet(new long[]{0x0000000400100002L});
    public static final BitSet FOLLOW_elif_clause_in_elif_clause3670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ORELSE_in_else_clause3730 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_else_clause3732 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_else_clause3736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WHILE_in_while_stmt3773 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_while_stmt3775 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_while_stmt3778 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_while_stmt3782 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ORELSE_in_while_stmt3786 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_while_stmt3788 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_while_stmt3792 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_for_stmt3831 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_exprlist_in_for_stmt3833 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_IN_in_for_stmt3836 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_for_stmt3838 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_for_stmt3841 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_for_stmt3845 = new BitSet(new long[]{0x0000000400000002L});
    public static final BitSet FOLLOW_ORELSE_in_for_stmt3857 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_for_stmt3859 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_for_stmt3863 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRY_in_try_stmt3906 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_try_stmt3908 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_try_stmt3912 = new BitSet(new long[]{0x0000000000A00000L});
    public static final BitSet FOLLOW_except_clause_in_try_stmt3925 = new BitSet(new long[]{0x0000000400A00002L});
    public static final BitSet FOLLOW_ORELSE_in_try_stmt3929 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_try_stmt3931 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_try_stmt3935 = new BitSet(new long[]{0x0000000000800002L});
    public static final BitSet FOLLOW_FINALLY_in_try_stmt3941 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_try_stmt3943 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_try_stmt3947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FINALLY_in_try_stmt3970 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_try_stmt3972 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_try_stmt3976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WITH_in_with_stmt4025 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_with_item_in_with_stmt4029 = new BitSet(new long[]{0x0001400000000000L});
    public static final BitSet FOLLOW_COMMA_in_with_stmt4039 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_with_item_in_with_stmt4043 = new BitSet(new long[]{0x0001400000000000L});
    public static final BitSet FOLLOW_COLON_in_with_stmt4047 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_with_stmt4049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_with_item4086 = new BitSet(new long[]{0x0000000000002002L});
    public static final BitSet FOLLOW_AS_in_with_item4090 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_with_item4092 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXCEPT_in_except_clause4131 = new BitSet(new long[]{0x0000500180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_except_clause4136 = new BitSet(new long[]{0x0001400000002000L});
    public static final BitSet FOLLOW_set_in_except_clause4140 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_except_clause4150 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_except_clause4157 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_except_clause4159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_simple_stmt_in_suite4205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEWLINE_in_suite4221 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_INDENT_in_suite4223 = new BitSet(new long[]{0x00001FF99F4FCA80L,0x0000005FFFD63000L});
    public static final BitSet FOLLOW_stmt_in_suite4232 = new BitSet(new long[]{0x00001FF99F4FCAA0L,0x0000005FFFD63000L});
    public static final BitSet FOLLOW_DEDENT_in_suite4252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_or_test_in_test4282 = new BitSet(new long[]{0x0000000008000002L});
    public static final BitSet FOLLOW_IF_in_test4304 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_or_test_in_test4308 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_ORELSE_in_test4311 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_test4315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lambdef_in_test4360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_and_test_in_or_test4395 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_OR_in_or_test4411 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_and_test_in_or_test4415 = new BitSet(new long[]{0x0000000200000002L});
    public static final BitSet FOLLOW_not_test_in_and_test4496 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_AND_in_and_test4512 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_not_test_in_and_test4516 = new BitSet(new long[]{0x0000000000001002L});
    public static final BitSet FOLLOW_NOT_in_not_test4600 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_not_test_in_not_test4604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comparison_in_not_test4621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_comparison4670 = new BitSet(new long[]{0x0000000160000002L,0x00000000000000FEL});
    public static final BitSet FOLLOW_comp_op_in_comparison4684 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_comparison4688 = new BitSet(new long[]{0x0000000160000002L,0x00000000000000FEL});
    public static final BitSet FOLLOW_LESS_in_comp_op4769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_in_comp_op4785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQUAL_in_comp_op4801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATEREQUAL_in_comp_op4817 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESSEQUAL_in_comp_op4833 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ALT_NOTEQUAL_in_comp_op4849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOTEQUAL_in_comp_op4865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IN_in_comp_op4881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NOT_in_comp_op4897 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_IN_in_comp_op4899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IS_in_comp_op4915 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IS_in_comp_op4931 = new BitSet(new long[]{0x0000000100000000L});
    public static final BitSet FOLLOW_NOT_in_comp_op4933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_xor_expr_in_expr4985 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_VBAR_in_expr5000 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_xor_expr_in_expr5004 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000100L});
    public static final BitSet FOLLOW_and_expr_in_xor_expr5083 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_CIRCUMFLEX_in_xor_expr5098 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_and_expr_in_xor_expr5102 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000200L});
    public static final BitSet FOLLOW_shift_expr_in_and_expr5180 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_AMPER_in_and_expr5195 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_shift_expr_in_and_expr5199 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000400L});
    public static final BitSet FOLLOW_arith_expr_in_shift_expr5282 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000801L});
    public static final BitSet FOLLOW_shift_op_in_shift_expr5296 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_arith_expr_in_shift_expr5300 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000801L});
    public static final BitSet FOLLOW_BATCH_in_batch_stmt5382 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_batch_stmt5386 = new BitSet(new long[]{0x0000000021000000L});
    public static final BitSet FOLLOW_set_in_batch_stmt5388 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_batch_stmt5396 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_batch_stmt5398 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_batch_stmt5402 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFTSHIFT_in_shift_op5435 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_RIGHTSHIFT_in_shift_op5451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_arith_expr5497 = new BitSet(new long[]{0x0000000000000002L,0x0000000000003000L});
    public static final BitSet FOLLOW_arith_op_in_arith_expr5510 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_term_in_arith_expr5514 = new BitSet(new long[]{0x0000000000000002L,0x0000000000003000L});
    public static final BitSet FOLLOW_PLUS_in_arith_op5622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_arith_op5638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factor_in_term5684 = new BitSet(new long[]{0x0002000000000002L,0x000000000001C000L});
    public static final BitSet FOLLOW_term_op_in_term5697 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_factor_in_term5701 = new BitSet(new long[]{0x0002000000000002L,0x000000000001C000L});
    public static final BitSet FOLLOW_STAR_in_term_op5783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SLASH_in_term_op5799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PERCENT_in_term_op5815 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESLASH_in_term_op5831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_factor5870 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_factor_in_factor5874 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_factor5890 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_factor_in_factor5894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TILDE_in_factor5910 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_factor_in_factor5914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_power_in_factor5930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atom_in_power5969 = new BitSet(new long[]{0x0004100000000402L,0x0000000000040000L});
    public static final BitSet FOLLOW_trailer_in_power5974 = new BitSet(new long[]{0x0004100000000402L,0x0000000000040000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_power5989 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_factor_in_power5991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_atom6041 = new BitSet(new long[]{0x0000323180028A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_yield_expr_in_atom6059 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_testlist_gexp_in_atom6079 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_atom6122 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_atom6130 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFDE3000L});
    public static final BitSet FOLLOW_listmaker_in_atom6139 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_RBRACK_in_atom6182 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LCURLY_in_atom6190 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFF63000L});
    public static final BitSet FOLLOW_dictorsetmaker_in_atom6200 = new BitSet(new long[]{0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_RCURLY_in_atom6248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BACKQUOTE_in_atom6259 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_atom6261 = new BitSet(new long[]{0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_BACKQUOTE_in_atom6266 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_name_or_print_in_atom6284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_in_atom6302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONGINT_in_atom6320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_in_atom6338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COMPLEX_in_atom6356 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_in_atom6377 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_sql_stmt_in_atom6397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sim_stmt_in_atom6414 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_neo4j_stmt_in_atom6431 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_sparql_stmt_in_atom6448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rdf_stmt_in_atom6465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_japi_stmt_in_atom6482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_conn_stmt_in_atom6499 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oorel_commit_stmt_in_atom6517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_oorel_insert_stmt_in_atom6534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OORELCOMMIT_in_oorel_commit_stmt6575 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_oorel_commit_stmt6579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OORELINSERT_in_oorel_insert_stmt6640 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_oorel_insert_stmt6644 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_oorel_insert_stmt6668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SQL_in_sql_stmt6730 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_sql_stmt6734 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_sql_stmt6751 = new BitSet(new long[]{0x0000100100000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_sql_stmt6758 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_SIM_in_sim_stmt6871 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_sim_stmt6875 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_sim_stmt6892 = new BitSet(new long[]{0x0000100100000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_sim_stmt6899 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_Neo4j_in_neo4j_stmt6984 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_neo4j_stmt6988 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_neo4j_stmt7005 = new BitSet(new long[]{0x0000100100000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_neo4j_stmt7012 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_JAPI_in_japi_stmt7096 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_japi_stmt7100 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_japi_stmt7117 = new BitSet(new long[]{0x0000100100000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_japi_stmt7124 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_RDF_in_rdf_stmt7210 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_rdf_stmt7214 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_rdf_stmt7231 = new BitSet(new long[]{0x0000100100000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_rdf_stmt7238 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_SPARQL_in_sparql_stmt7324 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_sparql_stmt7328 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_sparql_stmt7345 = new BitSet(new long[]{0x0000100100000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_sparql_stmt7352 = new BitSet(new long[]{0x0000000000000002L,0x0000000008000000L});
    public static final BitSet FOLLOW_CONNECTTO_in_conn_stmt7429 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_conn_stmt7435 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_conn_stmt7441 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_conn_stmt7447 = new BitSet(new long[]{0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_STRING_in_conn_stmt7453 = new BitSet(new long[]{0x0000000000000002L,0x0000002008000000L});
    public static final BitSet FOLLOW_STRING_in_conn_stmt7475 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_NODEBUG_in_conn_stmt7507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_listmaker7624 = new BitSet(new long[]{0x0001000002000002L});
    public static final BitSet FOLLOW_list_for_in_listmaker7636 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_listmaker7668 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_listmaker7672 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_listmaker7701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_testlist_gexp7733 = new BitSet(new long[]{0x0001000002000002L});
    public static final BitSet FOLLOW_COMMA_in_testlist_gexp7757 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_testlist_gexp7761 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_testlist_gexp7769 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comp_for_in_testlist_gexp7823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAMBDA_in_lambdef7887 = new BitSet(new long[]{0x0006500000000200L});
    public static final BitSet FOLLOW_varargslist_in_lambdef7890 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_lambdef7894 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_lambdef7896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_trailer7935 = new BitSet(new long[]{0x0006300180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_arglist_in_trailer7944 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_trailer7986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACK_in_trailer7994 = new BitSet(new long[]{0x0000500180000E00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_subscriptlist_in_trailer7996 = new BitSet(new long[]{0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_RBRACK_in_trailer7999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_trailer8015 = new BitSet(new long[]{0x000007FFFFFFFA00L});
    public static final BitSet FOLLOW_attr_in_trailer8017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subscript_in_subscriptlist8056 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_subscriptlist8068 = new BitSet(new long[]{0x0000500180000E00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_subscript_in_subscriptlist8072 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_subscriptlist8079 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOT_in_subscript8122 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_subscript8124 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_DOT_in_subscript8126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_subscript8156 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_COLON_in_subscript8162 = new BitSet(new long[]{0x0000500180000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_subscript8167 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_sliceop_in_subscript8173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_subscript8204 = new BitSet(new long[]{0x0000500180000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_subscript8209 = new BitSet(new long[]{0x0000400000000002L});
    public static final BitSet FOLLOW_sliceop_in_subscript8215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_subscript8233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_sliceop8270 = new BitSet(new long[]{0x0000100180000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_sliceop8278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_exprlist8349 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_exprlist8361 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_exprlist8365 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_exprlist8371 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_exprlist8390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_del_list8428 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_del_list8440 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_expr_in_del_list8444 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_del_list8450 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_testlist8503 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_testlist8515 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_testlist8519 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_testlist8525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_testlist8543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_dictorsetmaker8578 = new BitSet(new long[]{0x0001400002000002L});
    public static final BitSet FOLLOW_COLON_in_dictorsetmaker8606 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_dictorsetmaker8610 = new BitSet(new long[]{0x0001000002000000L});
    public static final BitSet FOLLOW_comp_for_in_dictorsetmaker8630 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_dictorsetmaker8677 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_dictorsetmaker8681 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_dictorsetmaker8684 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_dictorsetmaker8688 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_dictorsetmaker8744 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_dictorsetmaker8748 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_dictorsetmaker8798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comp_for_in_dictorsetmaker8813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decorators_in_classdef8866 = new BitSet(new long[]{0x0000000000010000L,0x0000004000000000L});
    public static final BitSet FOLLOW_PERSISTIT_in_classdef8870 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_classdef8874 = new BitSet(new long[]{0x0000000000010000L});
    public static final BitSet FOLLOW_CLASS_in_classdef8892 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_NAME_in_classdef8897 = new BitSet(new long[]{0x0000500000000000L});
    public static final BitSet FOLLOW_LPAREN_in_classdef8920 = new BitSet(new long[]{0x0000300180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_classdef8922 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_RPAREN_in_classdef8926 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_classdef8930 = new BitSet(new long[]{0x00001239954ACA80L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_suite_in_classdef8932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_argument_in_arglist8978 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_arglist8982 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_argument_in_arglist8984 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_arglist9000 = new BitSet(new long[]{0x0006000000000002L});
    public static final BitSet FOLLOW_STAR_in_arglist9018 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_arglist9022 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_arglist9026 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_argument_in_arglist9028 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_arglist9034 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist9036 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_arglist9040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist9061 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_arglist9065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STAR_in_arglist9112 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_arglist9116 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_arglist9120 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_argument_in_arglist9122 = new BitSet(new long[]{0x0001000000000002L});
    public static final BitSet FOLLOW_COMMA_in_arglist9128 = new BitSet(new long[]{0x0004000000000000L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist9130 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_arglist9134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLESTAR_in_arglist9153 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_arglist9157 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_argument9196 = new BitSet(new long[]{0x0001800002000000L});
    public static final BitSet FOLLOW_ASSIGN_in_argument9209 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_argument9213 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comp_for_in_argument9239 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_for_in_list_iter9304 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_list_if_in_list_iter9313 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_list_for9339 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_exprlist_in_list_for9341 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_IN_in_list_for9344 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_list_for9346 = new BitSet(new long[]{0x000000000A000002L});
    public static final BitSet FOLLOW_list_iter_in_list_for9350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_list_if9380 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_list_if9382 = new BitSet(new long[]{0x000000000A000002L});
    public static final BitSet FOLLOW_list_iter_in_list_if9386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comp_for_in_comp_iter9417 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_comp_if_in_comp_iter9426 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FOR_in_comp_for9452 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_exprlist_in_comp_for9454 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_IN_in_comp_for9457 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_or_test_in_comp_for9459 = new BitSet(new long[]{0x000100000A000002L});
    public static final BitSet FOLLOW_comp_iter_in_comp_for9462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_comp_if9491 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_comp_if9493 = new BitSet(new long[]{0x000100000A000002L});
    public static final BitSet FOLLOW_comp_iter_in_comp_if9496 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YIELD_in_yield_expr9537 = new BitSet(new long[]{0x0000100180000A02L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_testlist_in_yield_expr9539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_synpred1_Python1304 = new BitSet(new long[]{0x0000100000000200L});
    public static final BitSet FOLLOW_fpdef_in_synpred1_Python1306 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred1_Python1309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testlist_in_synpred2_Python1699 = new BitSet(new long[]{0xFFF0000000000000L});
    public static final BitSet FOLLOW_augassign_in_synpred2_Python1702 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_testlist_in_synpred3_Python1818 = new BitSet(new long[]{0x0000800000000000L});
    public static final BitSet FOLLOW_ASSIGN_in_synpred3_Python1821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_synpred4_Python2333 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred4_Python2336 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_synpred5_Python2432 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred5_Python2435 = new BitSet(new long[]{0x0000100180000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_test_in_synpred5_Python2437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_decorators_in_synpred6_Python3534 = new BitSet(new long[]{0x0000000000040000L});
    public static final BitSet FOLLOW_DEF_in_synpred6_Python3537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_synpred7_Python4294 = new BitSet(new long[]{0x0000100100000A00L,0x0000001FFFD63000L});
    public static final BitSet FOLLOW_or_test_in_synpred7_Python4296 = new BitSet(new long[]{0x0000000400000000L});
    public static final BitSet FOLLOW_ORELSE_in_synpred7_Python4299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_synpred8_Python8143 = new BitSet(new long[]{0x0000400000000000L});
    public static final BitSet FOLLOW_COLON_in_synpred8_Python8146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_COLON_in_synpred9_Python8194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_in_synpred10_Python8339 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred10_Python8342 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_test_in_synpred11_Python8490 = new BitSet(new long[]{0x0001000000000000L});
    public static final BitSet FOLLOW_COMMA_in_synpred11_Python8493 = new BitSet(new long[]{0x0000000000000002L});

}