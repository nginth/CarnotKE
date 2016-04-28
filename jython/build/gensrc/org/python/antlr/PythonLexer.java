// $ANTLR 3.1.3 Mar 17, 2009 19:23:44 /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g 2016-04-28 11:41:09

package org.python.antlr;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class PythonLexer extends Lexer {
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

    /** Handles context-sensitive lexing of implicit line joining such as
     *  the case where newline is ignored in cases like this:
     *  a = [3,
     *       4]
     */
    int implicitLineJoiningLevel = 0;
    int startPos=-1;

    //For use in partial parsing.
    public boolean eofWhileNested = false;
    public boolean partial = false;
    public boolean single = false;

    //If you want to use another error recovery mechanism change this
    //and the same one in the parser.
    private ErrorHandler errorHandler;

        public void setErrorHandler(ErrorHandler eh) {
            this.errorHandler = eh;
        }

        /**
         *  Taken directly from antlr's Lexer.java -- needs to be re-integrated every time
         *  we upgrade from Antlr (need to consider a Lexer subclass, though the issue would
         *  remain).
         */
        public Token nextToken() {
            startPos = getCharPositionInLine();
            while (true) {
                state.token = null;
                state.channel = Token.DEFAULT_CHANNEL;
                state.tokenStartCharIndex = input.index();
                state.tokenStartCharPositionInLine = input.getCharPositionInLine();
                state.tokenStartLine = input.getLine();
                state.text = null;
                if ( input.LA(1)==CharStream.EOF ) {
                    if (implicitLineJoiningLevel > 0) {
                        eofWhileNested = true;
                    }
                    return Token.EOF_TOKEN;
                }
                try {
                    mTokens();
                    if ( state.token==null ) {
                        emit();
                    }
                    else if ( state.token==Token.SKIP_TOKEN ) {
                        continue;
                    }
                    return state.token;
                } catch (NoViableAltException nva) {
                    reportError(nva);
                    errorHandler.recover(this, nva); // throw out current char and try again
                } catch (FailedPredicateException fp) {
                    //XXX: added this for failed STRINGPART -- the FailedPredicateException
                    //     hides a NoViableAltException.  This should be the only
                    //     FailedPredicateException that gets thrown by the lexer.
                    reportError(fp);
                    errorHandler.recover(this, fp); // throw out current char and try again
                } catch (RecognitionException re) {
                    reportError(re);
                    // match() routine has already called recover()
                }
            }
        }
        @Override
        public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
            //Do nothing. We will handle error display elsewhere.
        }



    // delegates
    // delegators

    public PythonLexer() {;} 
    public PythonLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public PythonLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "/Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g"; }

    // $ANTLR start "AS"
    public final void mAS() throws RecognitionException {
        try {
            int _type = AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2493:11: ( 'as' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2493:13: 'as'
            {
            match("as"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AS"

    // $ANTLR start "ASSERT"
    public final void mASSERT() throws RecognitionException {
        try {
            int _type = ASSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2494:11: ( 'assert' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2494:13: 'assert'
            {
            match("assert"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSERT"

    // $ANTLR start "BREAK"
    public final void mBREAK() throws RecognitionException {
        try {
            int _type = BREAK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2495:11: ( 'break' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2495:13: 'break'
            {
            match("break"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BREAK"

    // $ANTLR start "CLASS"
    public final void mCLASS() throws RecognitionException {
        try {
            int _type = CLASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2496:11: ( 'class' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2496:13: 'class'
            {
            match("class"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CLASS"

    // $ANTLR start "CONTINUE"
    public final void mCONTINUE() throws RecognitionException {
        try {
            int _type = CONTINUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2497:11: ( 'continue' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2497:13: 'continue'
            {
            match("continue"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTINUE"

    // $ANTLR start "DEF"
    public final void mDEF() throws RecognitionException {
        try {
            int _type = DEF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2498:11: ( 'def' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2498:13: 'def'
            {
            match("def"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DEF"

    // $ANTLR start "DELETE"
    public final void mDELETE() throws RecognitionException {
        try {
            int _type = DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2499:11: ( 'del' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2499:13: 'del'
            {
            match("del"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELETE"

    // $ANTLR start "ELIF"
    public final void mELIF() throws RecognitionException {
        try {
            int _type = ELIF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2500:11: ( 'elif' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2500:13: 'elif'
            {
            match("elif"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ELIF"

    // $ANTLR start "EXCEPT"
    public final void mEXCEPT() throws RecognitionException {
        try {
            int _type = EXCEPT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2501:11: ( 'except' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2501:13: 'except'
            {
            match("except"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXCEPT"

    // $ANTLR start "EXEC"
    public final void mEXEC() throws RecognitionException {
        try {
            int _type = EXEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2502:11: ( 'exec' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2502:13: 'exec'
            {
            match("exec"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EXEC"

    // $ANTLR start "FINALLY"
    public final void mFINALLY() throws RecognitionException {
        try {
            int _type = FINALLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2503:11: ( 'finally' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2503:13: 'finally'
            {
            match("finally"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FINALLY"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2504:11: ( 'from' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2504:13: 'from'
            {
            match("from"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "FOR"
    public final void mFOR() throws RecognitionException {
        try {
            int _type = FOR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2505:11: ( 'for' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2505:13: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FOR"

    // $ANTLR start "GLOBAL"
    public final void mGLOBAL() throws RecognitionException {
        try {
            int _type = GLOBAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2506:11: ( 'global' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2506:13: 'global'
            {
            match("global"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GLOBAL"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2507:11: ( 'if' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2507:13: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "IMPORT"
    public final void mIMPORT() throws RecognitionException {
        try {
            int _type = IMPORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2508:11: ( 'import' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2508:13: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IMPORT"

    // $ANTLR start "IN"
    public final void mIN() throws RecognitionException {
        try {
            int _type = IN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2509:11: ( 'in' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2509:13: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IN"

    // $ANTLR start "IS"
    public final void mIS() throws RecognitionException {
        try {
            int _type = IS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2510:11: ( 'is' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2510:13: 'is'
            {
            match("is"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IS"

    // $ANTLR start "LAMBDA"
    public final void mLAMBDA() throws RecognitionException {
        try {
            int _type = LAMBDA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2511:11: ( 'lambda' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2511:13: 'lambda'
            {
            match("lambda"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LAMBDA"

    // $ANTLR start "ORELSE"
    public final void mORELSE() throws RecognitionException {
        try {
            int _type = ORELSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2512:11: ( 'else' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2512:13: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ORELSE"

    // $ANTLR start "PASS"
    public final void mPASS() throws RecognitionException {
        try {
            int _type = PASS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2513:11: ( 'pass' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2513:13: 'pass'
            {
            match("pass"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PASS"

    // $ANTLR start "PRINT"
    public final void mPRINT() throws RecognitionException {
        try {
            int _type = PRINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2514:11: ( 'print' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2514:13: 'print'
            {
            match("print"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PRINT"

    // $ANTLR start "RAISE"
    public final void mRAISE() throws RecognitionException {
        try {
            int _type = RAISE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2515:11: ( 'raise' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2515:13: 'raise'
            {
            match("raise"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RAISE"

    // $ANTLR start "RETURN"
    public final void mRETURN() throws RecognitionException {
        try {
            int _type = RETURN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2516:11: ( 'return' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2516:13: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RETURN"

    // $ANTLR start "TRY"
    public final void mTRY() throws RecognitionException {
        try {
            int _type = TRY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2517:11: ( 'try' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2517:13: 'try'
            {
            match("try"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRY"

    // $ANTLR start "WHILE"
    public final void mWHILE() throws RecognitionException {
        try {
            int _type = WHILE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2518:11: ( 'while' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2518:13: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHILE"

    // $ANTLR start "WITH"
    public final void mWITH() throws RecognitionException {
        try {
            int _type = WITH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2519:11: ( 'with' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2519:13: 'with'
            {
            match("with"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WITH"

    // $ANTLR start "YIELD"
    public final void mYIELD() throws RecognitionException {
        try {
            int _type = YIELD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2520:11: ( 'yield' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2520:13: 'yield'
            {
            match("yield"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "YIELD"

    // $ANTLR start "BATCH"
    public final void mBATCH() throws RecognitionException {
        try {
            int _type = BATCH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2521:11: ( 'mybatches' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2521:13: 'mybatches'
            {
            match("mybatches"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BATCH"

    // $ANTLR start "PERSISTIT"
    public final void mPERSISTIT() throws RecognitionException {
        try {
            int _type = PERSISTIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2524:13: ( 'persist' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2524:15: 'persist' ( ' ' )+ 'on' ( ' ' )+
            {
            match("persist"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2524:25: ( ' ' )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==' ') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2524:26: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2524:37: ( ' ' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==' ') ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2524:38: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PERSISTIT"

    // $ANTLR start "SQL"
    public final void mSQL() throws RecognitionException {
        try {
            int _type = SQL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2525:9: ( 'SQL' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2525:11: 'SQL' ( ' ' )+ 'on' ( ' ' )+
            {
            match("SQL"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2525:17: ( ' ' )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==' ') ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2525:18: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2525:29: ( ' ' )+
            int cnt4=0;
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==' ') ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2525:30: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SQL"

    // $ANTLR start "SIM"
    public final void mSIM() throws RecognitionException {
        try {
            int _type = SIM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2526:9: ( 'SIM' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2526:11: 'SIM' ( ' ' )+ 'on' ( ' ' )+
            {
            match("SIM"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2526:17: ( ' ' )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==' ') ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2526:18: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2526:29: ( ' ' )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==' ') ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2526:30: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SIM"

    // $ANTLR start "Neo4j"
    public final void mNeo4j() throws RecognitionException {
        try {
            int _type = Neo4j;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2527:9: ( 'Neo4j' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2527:11: 'Neo4j' ( ' ' )+ 'on' ( ' ' )+
            {
            match("Neo4j"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2527:19: ( ' ' )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==' ') ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2527:20: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2527:31: ( ' ' )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( (LA8_0==' ') ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2527:32: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "Neo4j"

    // $ANTLR start "JAPI"
    public final void mJAPI() throws RecognitionException {
        try {
            int _type = JAPI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2528:9: ( 'JAPI' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2528:11: 'JAPI' ( ' ' )+ 'on' ( ' ' )+
            {
            match("JAPI"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2528:18: ( ' ' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( (LA9_0==' ') ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2528:19: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2528:30: ( ' ' )+
            int cnt10=0;
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==' ') ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2528:31: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt10 >= 1 ) break loop10;
                        EarlyExitException eee =
                            new EarlyExitException(10, input);
                        throw eee;
                }
                cnt10++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "JAPI"

    // $ANTLR start "RDF"
    public final void mRDF() throws RecognitionException {
        try {
            int _type = RDF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2529:9: ( 'RDF' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2529:11: 'RDF' ( ' ' )+ 'on' ( ' ' )+
            {
            match("RDF"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2529:17: ( ' ' )+
            int cnt11=0;
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==' ') ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2529:18: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt11 >= 1 ) break loop11;
                        EarlyExitException eee =
                            new EarlyExitException(11, input);
                        throw eee;
                }
                cnt11++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2529:29: ( ' ' )+
            int cnt12=0;
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( (LA12_0==' ') ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2529:30: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt12 >= 1 ) break loop12;
                        EarlyExitException eee =
                            new EarlyExitException(12, input);
                        throw eee;
                }
                cnt12++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RDF"

    // $ANTLR start "SPARQL"
    public final void mSPARQL() throws RecognitionException {
        try {
            int _type = SPARQL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2530:9: ( 'SPARQL' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2530:11: 'SPARQL' ( ' ' )+ 'on' ( ' ' )+
            {
            match("SPARQL"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2530:20: ( ' ' )+
            int cnt13=0;
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==' ') ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2530:21: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt13 >= 1 ) break loop13;
                        EarlyExitException eee =
                            new EarlyExitException(13, input);
                        throw eee;
                }
                cnt13++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2530:32: ( ' ' )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==' ') ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2530:33: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SPARQL"

    // $ANTLR start "OORELINSERT"
    public final void mOORELINSERT() throws RecognitionException {
        try {
            int _type = OORELINSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2531:12: ( 'relInsert' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2531:14: 'relInsert' ( ' ' )+ 'on' ( ' ' )+
            {
            match("relInsert"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2531:26: ( ' ' )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==' ') ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2531:27: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2531:38: ( ' ' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==' ') ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2531:39: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OORELINSERT"

    // $ANTLR start "OORELCOMMIT"
    public final void mOORELCOMMIT() throws RecognitionException {
        try {
            int _type = OORELCOMMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2532:12: ( 'relCommit' ( ' ' )+ 'on' ( ' ' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2532:14: 'relCommit' ( ' ' )+ 'on' ( ' ' )+
            {
            match("relCommit"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2532:26: ( ' ' )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==' ') ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2532:27: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);

            match("on"); 

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2532:38: ( ' ' )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==' ') ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2532:39: ' '
            	    {
            	    match(' '); 

            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OORELCOMMIT"

    // $ANTLR start "CONNECTTO"
    public final void mCONNECTTO() throws RecognitionException {
        try {
            int _type = CONNECTTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2533:10: ( 'connectTo' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2533:13: 'connectTo'
            {
            match("connectTo"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONNECTTO"

    // $ANTLR start "NODEBUG"
    public final void mNODEBUG() throws RecognitionException {
        try {
            int _type = NODEBUG;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2534:8: ( 'nodebug' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2534:11: 'nodebug'
            {
            match("nodebug"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NODEBUG"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2536:11: ( '(' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2536:13: '('
            {
            match('('); 
            implicitLineJoiningLevel++;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2538:11: ( ')' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2538:13: ')'
            {
            match(')'); 
            implicitLineJoiningLevel--;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LBRACK"
    public final void mLBRACK() throws RecognitionException {
        try {
            int _type = LBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2540:11: ( '[' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2540:13: '['
            {
            match('['); 
            implicitLineJoiningLevel++;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LBRACK"

    // $ANTLR start "RBRACK"
    public final void mRBRACK() throws RecognitionException {
        try {
            int _type = RBRACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2542:11: ( ']' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2542:13: ']'
            {
            match(']'); 
            implicitLineJoiningLevel--;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RBRACK"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2544:11: ( ':' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2544:13: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2546:10: ( ',' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2546:12: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2548:9: ( ';' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2548:11: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2550:9: ( '+' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2550:11: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2552:10: ( '-' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2552:12: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "STAR"
    public final void mSTAR() throws RecognitionException {
        try {
            int _type = STAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2554:9: ( '*' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2554:11: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAR"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2556:10: ( '/' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2556:12: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "VBAR"
    public final void mVBAR() throws RecognitionException {
        try {
            int _type = VBAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2558:9: ( '|' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2558:11: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VBAR"

    // $ANTLR start "AMPER"
    public final void mAMPER() throws RecognitionException {
        try {
            int _type = AMPER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2560:10: ( '&' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2560:12: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMPER"

    // $ANTLR start "LESS"
    public final void mLESS() throws RecognitionException {
        try {
            int _type = LESS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2562:9: ( '<' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2562:11: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS"

    // $ANTLR start "GREATER"
    public final void mGREATER() throws RecognitionException {
        try {
            int _type = GREATER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2564:12: ( '>' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2564:14: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER"

    // $ANTLR start "ASSIGN"
    public final void mASSIGN() throws RecognitionException {
        try {
            int _type = ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2566:11: ( '=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2566:13: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSIGN"

    // $ANTLR start "PERCENT"
    public final void mPERCENT() throws RecognitionException {
        try {
            int _type = PERCENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2568:12: ( '%' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2568:14: '%'
            {
            match('%'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PERCENT"

    // $ANTLR start "BACKQUOTE"
    public final void mBACKQUOTE() throws RecognitionException {
        try {
            int _type = BACKQUOTE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2570:14: ( '`' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2570:16: '`'
            {
            match('`'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BACKQUOTE"

    // $ANTLR start "LCURLY"
    public final void mLCURLY() throws RecognitionException {
        try {
            int _type = LCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2572:11: ( '{' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2572:13: '{'
            {
            match('{'); 
            implicitLineJoiningLevel++;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LCURLY"

    // $ANTLR start "RCURLY"
    public final void mRCURLY() throws RecognitionException {
        try {
            int _type = RCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2574:11: ( '}' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2574:13: '}'
            {
            match('}'); 
            implicitLineJoiningLevel--;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RCURLY"

    // $ANTLR start "CIRCUMFLEX"
    public final void mCIRCUMFLEX() throws RecognitionException {
        try {
            int _type = CIRCUMFLEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2576:15: ( '^' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2576:17: '^'
            {
            match('^'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CIRCUMFLEX"

    // $ANTLR start "TILDE"
    public final void mTILDE() throws RecognitionException {
        try {
            int _type = TILDE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2578:10: ( '~' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2578:12: '~'
            {
            match('~'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TILDE"

    // $ANTLR start "EQUAL"
    public final void mEQUAL() throws RecognitionException {
        try {
            int _type = EQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2580:10: ( '==' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2580:12: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL"

    // $ANTLR start "NOTEQUAL"
    public final void mNOTEQUAL() throws RecognitionException {
        try {
            int _type = NOTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2582:13: ( '!=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2582:15: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOTEQUAL"

    // $ANTLR start "ALT_NOTEQUAL"
    public final void mALT_NOTEQUAL() throws RecognitionException {
        try {
            int _type = ALT_NOTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2584:13: ( '<>' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2584:15: '<>'
            {
            match("<>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ALT_NOTEQUAL"

    // $ANTLR start "LESSEQUAL"
    public final void mLESSEQUAL() throws RecognitionException {
        try {
            int _type = LESSEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2586:14: ( '<=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2586:16: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESSEQUAL"

    // $ANTLR start "LEFTSHIFT"
    public final void mLEFTSHIFT() throws RecognitionException {
        try {
            int _type = LEFTSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2588:14: ( '<<' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2588:16: '<<'
            {
            match("<<"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFTSHIFT"

    // $ANTLR start "GREATEREQUAL"
    public final void mGREATEREQUAL() throws RecognitionException {
        try {
            int _type = GREATEREQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2590:17: ( '>=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2590:19: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATEREQUAL"

    // $ANTLR start "RIGHTSHIFT"
    public final void mRIGHTSHIFT() throws RecognitionException {
        try {
            int _type = RIGHTSHIFT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2592:15: ( '>>' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2592:17: '>>'
            {
            match(">>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHTSHIFT"

    // $ANTLR start "PLUSEQUAL"
    public final void mPLUSEQUAL() throws RecognitionException {
        try {
            int _type = PLUSEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2594:14: ( '+=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2594:16: '+='
            {
            match("+="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUSEQUAL"

    // $ANTLR start "MINUSEQUAL"
    public final void mMINUSEQUAL() throws RecognitionException {
        try {
            int _type = MINUSEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2596:15: ( '-=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2596:17: '-='
            {
            match("-="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUSEQUAL"

    // $ANTLR start "DOUBLESTAR"
    public final void mDOUBLESTAR() throws RecognitionException {
        try {
            int _type = DOUBLESTAR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2598:15: ( '**' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2598:17: '**'
            {
            match("**"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLESTAR"

    // $ANTLR start "STAREQUAL"
    public final void mSTAREQUAL() throws RecognitionException {
        try {
            int _type = STAREQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2600:14: ( '*=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2600:16: '*='
            {
            match("*="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STAREQUAL"

    // $ANTLR start "DOUBLESLASH"
    public final void mDOUBLESLASH() throws RecognitionException {
        try {
            int _type = DOUBLESLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2602:16: ( '//' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2602:18: '//'
            {
            match("//"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLESLASH"

    // $ANTLR start "SLASHEQUAL"
    public final void mSLASHEQUAL() throws RecognitionException {
        try {
            int _type = SLASHEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2604:15: ( '/=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2604:17: '/='
            {
            match("/="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASHEQUAL"

    // $ANTLR start "VBAREQUAL"
    public final void mVBAREQUAL() throws RecognitionException {
        try {
            int _type = VBAREQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2606:14: ( '|=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2606:16: '|='
            {
            match("|="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VBAREQUAL"

    // $ANTLR start "PERCENTEQUAL"
    public final void mPERCENTEQUAL() throws RecognitionException {
        try {
            int _type = PERCENTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2608:17: ( '%=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2608:19: '%='
            {
            match("%="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PERCENTEQUAL"

    // $ANTLR start "AMPEREQUAL"
    public final void mAMPEREQUAL() throws RecognitionException {
        try {
            int _type = AMPEREQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2610:15: ( '&=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2610:17: '&='
            {
            match("&="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AMPEREQUAL"

    // $ANTLR start "CIRCUMFLEXEQUAL"
    public final void mCIRCUMFLEXEQUAL() throws RecognitionException {
        try {
            int _type = CIRCUMFLEXEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2612:20: ( '^=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2612:22: '^='
            {
            match("^="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CIRCUMFLEXEQUAL"

    // $ANTLR start "LEFTSHIFTEQUAL"
    public final void mLEFTSHIFTEQUAL() throws RecognitionException {
        try {
            int _type = LEFTSHIFTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2614:19: ( '<<=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2614:21: '<<='
            {
            match("<<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEFTSHIFTEQUAL"

    // $ANTLR start "RIGHTSHIFTEQUAL"
    public final void mRIGHTSHIFTEQUAL() throws RecognitionException {
        try {
            int _type = RIGHTSHIFTEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2616:20: ( '>>=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2616:22: '>>='
            {
            match(">>="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RIGHTSHIFTEQUAL"

    // $ANTLR start "DOUBLESTAREQUAL"
    public final void mDOUBLESTAREQUAL() throws RecognitionException {
        try {
            int _type = DOUBLESTAREQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2618:20: ( '**=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2618:22: '**='
            {
            match("**="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLESTAREQUAL"

    // $ANTLR start "DOUBLESLASHEQUAL"
    public final void mDOUBLESLASHEQUAL() throws RecognitionException {
        try {
            int _type = DOUBLESLASHEQUAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2620:21: ( '//=' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2620:23: '//='
            {
            match("//="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOUBLESLASHEQUAL"

    // $ANTLR start "DOT"
    public final void mDOT() throws RecognitionException {
        try {
            int _type = DOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2622:5: ( '.' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2622:7: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DOT"

    // $ANTLR start "AT"
    public final void mAT() throws RecognitionException {
        try {
            int _type = AT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2624:4: ( '@' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2624:6: '@'
            {
            match('@'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AT"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2626:5: ( 'and' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2626:7: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2628:4: ( 'or' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2628:6: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2630:5: ( 'not' )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2630:7: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2633:5: ( '.' DIGITS ( Exponent )? | DIGITS '.' Exponent | DIGITS ( '.' ( DIGITS ( Exponent )? )? | Exponent ) )
            int alt23=3;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2633:9: '.' DIGITS ( Exponent )?
                    {
                    match('.'); 
                    mDIGITS(); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2633:20: ( Exponent )?
                    int alt19=2;
                    int LA19_0 = input.LA(1);

                    if ( (LA19_0=='E'||LA19_0=='e') ) {
                        alt19=1;
                    }
                    switch (alt19) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2633:21: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2634:9: DIGITS '.' Exponent
                    {
                    mDIGITS(); 
                    match('.'); 
                    mExponent(); 

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:9: DIGITS ( '.' ( DIGITS ( Exponent )? )? | Exponent )
                    {
                    mDIGITS(); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:16: ( '.' ( DIGITS ( Exponent )? )? | Exponent )
                    int alt22=2;
                    int LA22_0 = input.LA(1);

                    if ( (LA22_0=='.') ) {
                        alt22=1;
                    }
                    else if ( (LA22_0=='E'||LA22_0=='e') ) {
                        alt22=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 22, 0, input);

                        throw nvae;
                    }
                    switch (alt22) {
                        case 1 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:17: '.' ( DIGITS ( Exponent )? )?
                            {
                            match('.'); 
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:21: ( DIGITS ( Exponent )? )?
                            int alt21=2;
                            int LA21_0 = input.LA(1);

                            if ( ((LA21_0>='0' && LA21_0<='9')) ) {
                                alt21=1;
                            }
                            switch (alt21) {
                                case 1 :
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:22: DIGITS ( Exponent )?
                                    {
                                    mDIGITS(); 
                                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:29: ( Exponent )?
                                    int alt20=2;
                                    int LA20_0 = input.LA(1);

                                    if ( (LA20_0=='E'||LA20_0=='e') ) {
                                        alt20=1;
                                    }
                                    switch (alt20) {
                                        case 1 :
                                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:30: Exponent
                                            {
                                            mExponent(); 

                                            }
                                            break;

                                    }


                                    }
                                    break;

                            }


                            }
                            break;
                        case 2 :
                            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2635:45: Exponent
                            {
                            mExponent(); 

                            }
                            break;

                    }


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "LONGINT"
    public final void mLONGINT() throws RecognitionException {
        try {
            int _type = LONGINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2639:5: ( INT ( 'l' | 'L' ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2639:9: INT ( 'l' | 'L' )
            {
            mINT(); 
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LONGINT"

    // $ANTLR start "Exponent"
    public final void mExponent() throws RecognitionException {
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2644:5: ( ( 'e' | 'E' ) ( '+' | '-' )? DIGITS )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2644:10: ( 'e' | 'E' ) ( '+' | '-' )? DIGITS
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2644:22: ( '+' | '-' )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0=='+'||LA24_0=='-') ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            mDIGITS(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "Exponent"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2647:5: ( '0' ( 'x' | 'X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+ | '0' ( 'o' | 'O' ) ( '0' .. '7' )* | '0' ( '0' .. '7' )* | '0' ( 'b' | 'B' ) ( '0' .. '1' )* | '1' .. '9' ( DIGITS )* )
            int alt30=5;
            int LA30_0 = input.LA(1);

            if ( (LA30_0=='0') ) {
                switch ( input.LA(2) ) {
                case 'X':
                case 'x':
                    {
                    alt30=1;
                    }
                    break;
                case 'O':
                case 'o':
                    {
                    alt30=2;
                    }
                    break;
                case 'B':
                case 'b':
                    {
                    alt30=4;
                    }
                    break;
                default:
                    alt30=3;}

            }
            else if ( ((LA30_0>='1' && LA30_0<='9')) ) {
                alt30=5;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2648:9: '0' ( 'x' | 'X' ) ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+
                    {
                    match('0'); 
                    if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2648:25: ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' )+
                    int cnt25=0;
                    loop25:
                    do {
                        int alt25=2;
                        int LA25_0 = input.LA(1);

                        if ( ((LA25_0>='0' && LA25_0<='9')||(LA25_0>='A' && LA25_0<='F')||(LA25_0>='a' && LA25_0<='f')) ) {
                            alt25=1;
                        }


                        switch (alt25) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
                    	    {
                    	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='F')||(input.LA(1)>='a' && input.LA(1)<='f') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt25 >= 1 ) break loop25;
                                EarlyExitException eee =
                                    new EarlyExitException(25, input);
                                throw eee;
                        }
                        cnt25++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2650:9: '0' ( 'o' | 'O' ) ( '0' .. '7' )*
                    {
                    match('0'); 
                    if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2650:25: ( '0' .. '7' )*
                    loop26:
                    do {
                        int alt26=2;
                        int LA26_0 = input.LA(1);

                        if ( ((LA26_0>='0' && LA26_0<='7')) ) {
                            alt26=1;
                        }


                        switch (alt26) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2650:27: '0' .. '7'
                    	    {
                    	    matchRange('0','7'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop26;
                        }
                    } while (true);


                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2651:9: '0' ( '0' .. '7' )*
                    {
                    match('0'); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2651:14: ( '0' .. '7' )*
                    loop27:
                    do {
                        int alt27=2;
                        int LA27_0 = input.LA(1);

                        if ( ((LA27_0>='0' && LA27_0<='7')) ) {
                            alt27=1;
                        }


                        switch (alt27) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2651:16: '0' .. '7'
                    	    {
                    	    matchRange('0','7'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop27;
                        }
                    } while (true);


                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2653:9: '0' ( 'b' | 'B' ) ( '0' .. '1' )*
                    {
                    match('0'); 
                    if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2653:25: ( '0' .. '1' )*
                    loop28:
                    do {
                        int alt28=2;
                        int LA28_0 = input.LA(1);

                        if ( ((LA28_0>='0' && LA28_0<='1')) ) {
                            alt28=1;
                        }


                        switch (alt28) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2653:27: '0' .. '1'
                    	    {
                    	    matchRange('0','1'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop28;
                        }
                    } while (true);


                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2655:9: '1' .. '9' ( DIGITS )*
                    {
                    matchRange('1','9'); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2655:18: ( DIGITS )*
                    loop29:
                    do {
                        int alt29=2;
                        int LA29_0 = input.LA(1);

                        if ( ((LA29_0>='0' && LA29_0<='9')) ) {
                            alt29=1;
                        }


                        switch (alt29) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2655:18: DIGITS
                    	    {
                    	    mDIGITS(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop29;
                        }
                    } while (true);


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "COMPLEX"
    public final void mCOMPLEX() throws RecognitionException {
        try {
            int _type = COMPLEX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2659:5: ( ( DIGITS )+ ( 'j' | 'J' ) | FLOAT ( 'j' | 'J' ) )
            int alt32=2;
            alt32 = dfa32.predict(input);
            switch (alt32) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2659:9: ( DIGITS )+ ( 'j' | 'J' )
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2659:9: ( DIGITS )+
                    int cnt31=0;
                    loop31:
                    do {
                        int alt31=2;
                        int LA31_0 = input.LA(1);

                        if ( ((LA31_0>='0' && LA31_0<='9')) ) {
                            alt31=1;
                        }


                        switch (alt31) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2659:9: DIGITS
                    	    {
                    	    mDIGITS(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt31 >= 1 ) break loop31;
                                EarlyExitException eee =
                                    new EarlyExitException(31, input);
                                throw eee;
                        }
                        cnt31++;
                    } while (true);

                    if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2660:9: FLOAT ( 'j' | 'J' )
                    {
                    mFLOAT(); 
                    if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMPLEX"

    // $ANTLR start "DIGITS"
    public final void mDIGITS() throws RecognitionException {
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2664:8: ( ( '0' .. '9' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2664:10: ( '0' .. '9' )+
            {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2664:10: ( '0' .. '9' )+
            int cnt33=0;
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0>='0' && LA33_0<='9')) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2664:12: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt33 >= 1 ) break loop33;
                        EarlyExitException eee =
                            new EarlyExitException(33, input);
                        throw eee;
                }
                cnt33++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "DIGITS"

    // $ANTLR start "NAME"
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2666:5: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )* )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2666:10: ( 'a' .. 'z' | 'A' .. 'Z' | '_' ) ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2667:9: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '0' .. '9' )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>='0' && LA34_0<='9')||(LA34_0>='A' && LA34_0<='Z')||LA34_0=='_'||(LA34_0>='a' && LA34_0<='z')) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
            	    {
            	    if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop34;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NAME"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:5: ( ( 'r' | 'u' | 'b' | 'ur' | 'br' | 'R' | 'U' | 'B' | 'UR' | 'BR' | 'uR' | 'Ur' | 'Br' | 'bR' )? ( '\\'\\'\\'' ( options {greedy=false; } : TRIAPOS )* '\\'\\'\\'' | '\"\"\"' ( options {greedy=false; } : TRIQUOTE )* '\"\"\"' | '\"' ( ESC | ~ ( '\\\\' | '\\n' | '\"' ) )* '\"' | '\\'' ( ESC | ~ ( '\\\\' | '\\n' | '\\'' ) )* '\\'' ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:9: ( 'r' | 'u' | 'b' | 'ur' | 'br' | 'R' | 'U' | 'B' | 'UR' | 'BR' | 'uR' | 'Ur' | 'Br' | 'bR' )? ( '\\'\\'\\'' ( options {greedy=false; } : TRIAPOS )* '\\'\\'\\'' | '\"\"\"' ( options {greedy=false; } : TRIQUOTE )* '\"\"\"' | '\"' ( ESC | ~ ( '\\\\' | '\\n' | '\"' ) )* '\"' | '\\'' ( ESC | ~ ( '\\\\' | '\\n' | '\\'' ) )* '\\'' )
            {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:9: ( 'r' | 'u' | 'b' | 'ur' | 'br' | 'R' | 'U' | 'B' | 'UR' | 'BR' | 'uR' | 'Ur' | 'Br' | 'bR' )?
            int alt35=15;
            alt35 = dfa35.predict(input);
            switch (alt35) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:10: 'r'
                    {
                    match('r'); 

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:14: 'u'
                    {
                    match('u'); 

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:18: 'b'
                    {
                    match('b'); 

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:22: 'ur'
                    {
                    match("ur"); 


                    }
                    break;
                case 5 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:27: 'br'
                    {
                    match("br"); 


                    }
                    break;
                case 6 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:32: 'R'
                    {
                    match('R'); 

                    }
                    break;
                case 7 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:36: 'U'
                    {
                    match('U'); 

                    }
                    break;
                case 8 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:40: 'B'
                    {
                    match('B'); 

                    }
                    break;
                case 9 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:44: 'UR'
                    {
                    match("UR"); 


                    }
                    break;
                case 10 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:49: 'BR'
                    {
                    match("BR"); 


                    }
                    break;
                case 11 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:54: 'uR'
                    {
                    match("uR"); 


                    }
                    break;
                case 12 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:59: 'Ur'
                    {
                    match("Ur"); 


                    }
                    break;
                case 13 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:64: 'Br'
                    {
                    match("Br"); 


                    }
                    break;
                case 14 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2675:69: 'bR'
                    {
                    match("bR"); 


                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2676:9: ( '\\'\\'\\'' ( options {greedy=false; } : TRIAPOS )* '\\'\\'\\'' | '\"\"\"' ( options {greedy=false; } : TRIQUOTE )* '\"\"\"' | '\"' ( ESC | ~ ( '\\\\' | '\\n' | '\"' ) )* '\"' | '\\'' ( ESC | ~ ( '\\\\' | '\\n' | '\\'' ) )* '\\'' )
            int alt40=4;
            int LA40_0 = input.LA(1);

            if ( (LA40_0=='\'') ) {
                int LA40_1 = input.LA(2);

                if ( (LA40_1=='\'') ) {
                    int LA40_3 = input.LA(3);

                    if ( (LA40_3=='\'') ) {
                        alt40=1;
                    }
                    else {
                        alt40=4;}
                }
                else if ( ((LA40_1>='\u0000' && LA40_1<='\t')||(LA40_1>='\u000B' && LA40_1<='&')||(LA40_1>='(' && LA40_1<='\uFFFF')) ) {
                    alt40=4;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA40_0=='\"') ) {
                int LA40_2 = input.LA(2);

                if ( (LA40_2=='\"') ) {
                    int LA40_5 = input.LA(3);

                    if ( (LA40_5=='\"') ) {
                        alt40=2;
                    }
                    else {
                        alt40=3;}
                }
                else if ( ((LA40_2>='\u0000' && LA40_2<='\t')||(LA40_2>='\u000B' && LA40_2<='!')||(LA40_2>='#' && LA40_2<='\uFFFF')) ) {
                    alt40=3;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 40, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }
            switch (alt40) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2676:13: '\\'\\'\\'' ( options {greedy=false; } : TRIAPOS )* '\\'\\'\\''
                    {
                    match("'''"); 

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2676:22: ( options {greedy=false; } : TRIAPOS )*
                    loop36:
                    do {
                        int alt36=2;
                        int LA36_0 = input.LA(1);

                        if ( (LA36_0=='\'') ) {
                            int LA36_1 = input.LA(2);

                            if ( (LA36_1=='\'') ) {
                                int LA36_3 = input.LA(3);

                                if ( (LA36_3=='\'') ) {
                                    alt36=2;
                                }
                                else if ( ((LA36_3>='\u0000' && LA36_3<='&')||(LA36_3>='(' && LA36_3<='\uFFFF')) ) {
                                    alt36=1;
                                }


                            }
                            else if ( ((LA36_1>='\u0000' && LA36_1<='&')||(LA36_1>='(' && LA36_1<='\uFFFF')) ) {
                                alt36=1;
                            }


                        }
                        else if ( ((LA36_0>='\u0000' && LA36_0<='&')||(LA36_0>='(' && LA36_0<='\uFFFF')) ) {
                            alt36=1;
                        }


                        switch (alt36) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2676:47: TRIAPOS
                    	    {
                    	    mTRIAPOS(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop36;
                        }
                    } while (true);

                    match("'''"); 


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2677:13: '\"\"\"' ( options {greedy=false; } : TRIQUOTE )* '\"\"\"'
                    {
                    match("\"\"\""); 

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2677:19: ( options {greedy=false; } : TRIQUOTE )*
                    loop37:
                    do {
                        int alt37=2;
                        int LA37_0 = input.LA(1);

                        if ( (LA37_0=='\"') ) {
                            int LA37_1 = input.LA(2);

                            if ( (LA37_1=='\"') ) {
                                int LA37_3 = input.LA(3);

                                if ( (LA37_3=='\"') ) {
                                    alt37=2;
                                }
                                else if ( ((LA37_3>='\u0000' && LA37_3<='!')||(LA37_3>='#' && LA37_3<='\uFFFF')) ) {
                                    alt37=1;
                                }


                            }
                            else if ( ((LA37_1>='\u0000' && LA37_1<='!')||(LA37_1>='#' && LA37_1<='\uFFFF')) ) {
                                alt37=1;
                            }


                        }
                        else if ( ((LA37_0>='\u0000' && LA37_0<='!')||(LA37_0>='#' && LA37_0<='\uFFFF')) ) {
                            alt37=1;
                        }


                        switch (alt37) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2677:44: TRIQUOTE
                    	    {
                    	    mTRIQUOTE(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop37;
                        }
                    } while (true);

                    match("\"\"\""); 


                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2678:13: '\"' ( ESC | ~ ( '\\\\' | '\\n' | '\"' ) )* '\"'
                    {
                    match('\"'); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2678:17: ( ESC | ~ ( '\\\\' | '\\n' | '\"' ) )*
                    loop38:
                    do {
                        int alt38=3;
                        int LA38_0 = input.LA(1);

                        if ( (LA38_0=='\\') ) {
                            alt38=1;
                        }
                        else if ( ((LA38_0>='\u0000' && LA38_0<='\t')||(LA38_0>='\u000B' && LA38_0<='!')||(LA38_0>='#' && LA38_0<='[')||(LA38_0>=']' && LA38_0<='\uFFFF')) ) {
                            alt38=2;
                        }


                        switch (alt38) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2678:18: ESC
                    	    {
                    	    mESC(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2678:22: ~ ( '\\\\' | '\\n' | '\"' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop38;
                        }
                    } while (true);

                    match('\"'); 

                    }
                    break;
                case 4 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2679:13: '\\'' ( ESC | ~ ( '\\\\' | '\\n' | '\\'' ) )* '\\''
                    {
                    match('\''); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2679:18: ( ESC | ~ ( '\\\\' | '\\n' | '\\'' ) )*
                    loop39:
                    do {
                        int alt39=3;
                        int LA39_0 = input.LA(1);

                        if ( (LA39_0=='\\') ) {
                            alt39=1;
                        }
                        else if ( ((LA39_0>='\u0000' && LA39_0<='\t')||(LA39_0>='\u000B' && LA39_0<='&')||(LA39_0>='(' && LA39_0<='[')||(LA39_0>=']' && LA39_0<='\uFFFF')) ) {
                            alt39=2;
                        }


                        switch (alt39) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2679:19: ESC
                    	    {
                    	    mESC(); 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2679:23: ~ ( '\\\\' | '\\n' | '\\'' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop39;
                        }
                    } while (true);

                    match('\''); 

                    }
                    break;

            }


                       if (state.tokenStartLine != input.getLine()) {
                           state.tokenStartLine = input.getLine();
                           state.tokenStartCharPositionInLine = -2;
                       }
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "TRIQUOTE"
    public final void mTRIQUOTE() throws RecognitionException {
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:5: ( ( '\"' )? ( '\"' )? ( ESC | ~ ( '\\\\' | '\"' ) )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:7: ( '\"' )? ( '\"' )? ( ESC | ~ ( '\\\\' | '\"' ) )+
            {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:7: ( '\"' )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0=='\"') ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:7: '\"'
                    {
                    match('\"'); 

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:12: ( '\"' )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0=='\"') ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:12: '\"'
                    {
                    match('\"'); 

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:17: ( ESC | ~ ( '\\\\' | '\"' ) )+
            int cnt43=0;
            loop43:
            do {
                int alt43=3;
                int LA43_0 = input.LA(1);

                if ( (LA43_0=='\\') ) {
                    alt43=1;
                }
                else if ( ((LA43_0>='\u0000' && LA43_0<='!')||(LA43_0>='#' && LA43_0<='[')||(LA43_0>=']' && LA43_0<='\uFFFF')) ) {
                    alt43=2;
                }


                switch (alt43) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:18: ESC
            	    {
            	    mESC(); 

            	    }
            	    break;
            	case 2 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2691:22: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt43 >= 1 ) break loop43;
                        EarlyExitException eee =
                            new EarlyExitException(43, input);
                        throw eee;
                }
                cnt43++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "TRIQUOTE"

    // $ANTLR start "TRIAPOS"
    public final void mTRIAPOS() throws RecognitionException {
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:5: ( ( '\\'' )? ( '\\'' )? ( ESC | ~ ( '\\\\' | '\\'' ) )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:7: ( '\\'' )? ( '\\'' )? ( ESC | ~ ( '\\\\' | '\\'' ) )+
            {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:7: ( '\\'' )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0=='\'') ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:7: '\\''
                    {
                    match('\''); 

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:13: ( '\\'' )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0=='\'') ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:13: '\\''
                    {
                    match('\''); 

                    }
                    break;

            }

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:19: ( ESC | ~ ( '\\\\' | '\\'' ) )+
            int cnt46=0;
            loop46:
            do {
                int alt46=3;
                int LA46_0 = input.LA(1);

                if ( (LA46_0=='\\') ) {
                    alt46=1;
                }
                else if ( ((LA46_0>='\u0000' && LA46_0<='&')||(LA46_0>='(' && LA46_0<='[')||(LA46_0>=']' && LA46_0<='\uFFFF')) ) {
                    alt46=2;
                }


                switch (alt46) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:20: ESC
            	    {
            	    mESC(); 

            	    }
            	    break;
            	case 2 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2697:24: ~ ( '\\\\' | '\\'' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFF') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt46 >= 1 ) break loop46;
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "TRIAPOS"

    // $ANTLR start "ESC"
    public final void mESC() throws RecognitionException {
        try {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2702:5: ( '\\\\' . )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2702:10: '\\\\' .
            {
            match('\\'); 
            matchAny(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ESC"

    // $ANTLR start "CONTINUED_LINE"
    public final void mCONTINUED_LINE() throws RecognitionException {
        try {
            int _type = CONTINUED_LINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token nl=null;

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2710:5: ( '\\\\' ( '\\r' )? '\\n' ( ' ' | '\\t' )* ( COMMENT | nl= NEWLINE | ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2710:10: '\\\\' ( '\\r' )? '\\n' ( ' ' | '\\t' )* ( COMMENT | nl= NEWLINE | )
            {
            match('\\'); 
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2710:15: ( '\\r' )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0=='\r') ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2710:16: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2710:28: ( ' ' | '\\t' )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0=='\t'||LA48_0==' ') ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);

             _channel=HIDDEN; 
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2711:10: ( COMMENT | nl= NEWLINE | )
            int alt49=3;
            int LA49_0 = input.LA(1);

            if ( (LA49_0=='\t'||LA49_0==' ') && ((startPos==0))) {
                alt49=1;
            }
            else if ( (LA49_0=='#') ) {
                alt49=1;
            }
            else if ( (LA49_0=='\n'||(LA49_0>='\f' && LA49_0<='\r')) ) {
                alt49=2;
            }
            else {
                alt49=3;}
            switch (alt49) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2711:12: COMMENT
                    {
                    mCOMMENT(); 

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2712:12: nl= NEWLINE
                    {
                    int nlStart2044 = getCharIndex();
                    mNEWLINE(); 
                    nl = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, nlStart2044, getCharIndex()-1);

                                   emit(new CommonToken(NEWLINE,nl.getText()));
                               

                    }
                    break;
                case 3 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2717:10: 
                    {
                    }
                    break;

            }


                           if (input.LA(1) == -1) {
                               throw new ParseException("unexpected character after line continuation character");
                           }
                       

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "CONTINUED_LINE"

    // $ANTLR start "NEWLINE"
    public final void mNEWLINE() throws RecognitionException {
        try {
            int _type = NEWLINE;
            int _channel = DEFAULT_TOKEN_CHANNEL;

                int newlines = 0;

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:5: ( ( ( '\\u000C' )? ( '\\r' )? '\\n' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:9: ( ( '\\u000C' )? ( '\\r' )? '\\n' )+
            {
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:9: ( ( '\\u000C' )? ( '\\r' )? '\\n' )+
            int cnt52=0;
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0=='\n'||(LA52_0>='\f' && LA52_0<='\r')) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:10: ( '\\u000C' )? ( '\\r' )? '\\n'
            	    {
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:10: ( '\\u000C' )?
            	    int alt50=2;
            	    int LA50_0 = input.LA(1);

            	    if ( (LA50_0=='\f') ) {
            	        alt50=1;
            	    }
            	    switch (alt50) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:11: '\\u000C'
            	            {
            	            match('\f'); 

            	            }
            	            break;

            	    }

            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:21: ( '\\r' )?
            	    int alt51=2;
            	    int LA51_0 = input.LA(1);

            	    if ( (LA51_0=='\r') ) {
            	        alt51=1;
            	    }
            	    switch (alt51) {
            	        case 1 :
            	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2734:22: '\\r'
            	            {
            	            match('\r'); 

            	            }
            	            break;

            	    }

            	    match('\n'); 
            	    newlines++; 

            	    }
            	    break;

            	default :
            	    if ( cnt52 >= 1 ) break loop52;
                        EarlyExitException eee =
                            new EarlyExitException(52, input);
                        throw eee;
                }
                cnt52++;
            } while (true);


                     if ( startPos==0 || implicitLineJoiningLevel>0 )
                        _channel=HIDDEN;
                    

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEWLINE"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2740:5: ({...}? => ( ' ' | '\\t' | '\\u000C' )+ )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2740:10: {...}? => ( ' ' | '\\t' | '\\u000C' )+
            {
            if ( !((startPos>0)) ) {
                throw new FailedPredicateException(input, "WS", "startPos>0");
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2740:26: ( ' ' | '\\t' | '\\u000C' )+
            int cnt53=0;
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0=='\t'||LA53_0=='\f'||LA53_0==' ') ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
            	    {
            	    if ( input.LA(1)=='\t'||input.LA(1)=='\f'||input.LA(1)==' ' ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    if ( cnt53 >= 1 ) break loop53;
                        EarlyExitException eee =
                            new EarlyExitException(53, input);
                        throw eee;
                }
                cnt53++;
            } while (true);

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "LEADING_WS"
    public final void mLEADING_WS() throws RecognitionException {
        try {
            int _type = LEADING_WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;

                int spaces = 0;
                int newlines = 0;

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2754:5: ({...}? => ({...}? ( ' ' | '\\t' )+ | ( ' ' | '\\t' )+ ( ( '\\r' )? '\\n' )* ) )
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2754:9: {...}? => ({...}? ( ' ' | '\\t' )+ | ( ' ' | '\\t' )+ ( ( '\\r' )? '\\n' )* )
            {
            if ( !((startPos==0)) ) {
                throw new FailedPredicateException(input, "LEADING_WS", "startPos==0");
            }
            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2755:9: ({...}? ( ' ' | '\\t' )+ | ( ' ' | '\\t' )+ ( ( '\\r' )? '\\n' )* )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==' ') ) {
                int LA58_1 = input.LA(2);

                if ( ((implicitLineJoiningLevel>0)) ) {
                    alt58=1;
                }
                else if ( (true) ) {
                    alt58=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 58, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA58_0=='\t') ) {
                int LA58_2 = input.LA(2);

                if ( ((implicitLineJoiningLevel>0)) ) {
                    alt58=1;
                }
                else if ( (true) ) {
                    alt58=2;
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 58, 2, input);

                    throw nvae;
                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2755:13: {...}? ( ' ' | '\\t' )+
                    {
                    if ( !((implicitLineJoiningLevel>0)) ) {
                        throw new FailedPredicateException(input, "LEADING_WS", "implicitLineJoiningLevel>0");
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2755:43: ( ' ' | '\\t' )+
                    int cnt54=0;
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0=='\t'||LA54_0==' ') ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
                    	    {
                    	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt54 >= 1 ) break loop54;
                                EarlyExitException eee =
                                    new EarlyExitException(54, input);
                                throw eee;
                        }
                        cnt54++;
                    } while (true);

                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2756:14: ( ' ' | '\\t' )+ ( ( '\\r' )? '\\n' )*
                    {
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2756:14: ( ' ' | '\\t' )+
                    int cnt55=0;
                    loop55:
                    do {
                        int alt55=3;
                        int LA55_0 = input.LA(1);

                        if ( (LA55_0==' ') ) {
                            alt55=1;
                        }
                        else if ( (LA55_0=='\t') ) {
                            alt55=2;
                        }


                        switch (alt55) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2756:20: ' '
                    	    {
                    	    match(' '); 
                    	     spaces++; 

                    	    }
                    	    break;
                    	case 2 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2757:19: '\\t'
                    	    {
                    	    match('\t'); 
                    	     spaces += 8; spaces -= (spaces % 8); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt55 >= 1 ) break loop55;
                                EarlyExitException eee =
                                    new EarlyExitException(55, input);
                                throw eee;
                        }
                        cnt55++;
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2759:14: ( ( '\\r' )? '\\n' )*
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0=='\n'||LA57_0=='\r') ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2759:16: ( '\\r' )? '\\n'
                    	    {
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2759:16: ( '\\r' )?
                    	    int alt56=2;
                    	    int LA56_0 = input.LA(1);

                    	    if ( (LA56_0=='\r') ) {
                    	        alt56=1;
                    	    }
                    	    switch (alt56) {
                    	        case 1 :
                    	            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2759:17: '\\r'
                    	            {
                    	            match('\r'); 

                    	            }
                    	            break;

                    	    }

                    	    match('\n'); 
                    	    newlines++; 

                    	    }
                    	    break;

                    	default :
                    	    break loop57;
                        }
                    } while (true);


                                       if (input.LA(1) != -1 || newlines == 0) {
                                           // make a string of n spaces where n is column number - 1
                                           char[] indentation = new char[spaces];
                                           for (int i=0; i<spaces; i++) {
                                               indentation[i] = ' ';
                                           }
                                           CommonToken c = new CommonToken(LEADING_WS,new String(indentation));
                                           c.setLine(input.getLine());
                                           c.setCharPositionInLine(input.getCharPositionInLine());
                                           c.setStartIndex(input.index() - 1);
                                           c.setStopIndex(input.index() - 1);
                                           emit(c);
                                           // kill trailing newline if present and then ignore
                                           if (newlines != 0) {
                                               if (state.token!=null) {
                                                   state.token.setChannel(HIDDEN);
                                               } else {
                                                   _channel=HIDDEN;
                                               }
                                           }
                                       } else if (this.single && newlines == 1) {
                                           // This is here for this case in interactive mode:
                                           //
                                           // def foo():
                                           //   print 1
                                           //   <spaces but no code>
                                           //
                                           // The above would complete in interactive mode instead
                                           // of giving ... to wait for more input.
                                           //
                                           throw new ParseException("Trailing space in single mode.");
                                       } else {
                                           // make a string of n newlines
                                           char[] nls = new char[newlines];
                                           for (int i=0; i<newlines; i++) {
                                               nls[i] = '\n';
                                           }
                                           CommonToken c = new CommonToken(NEWLINE,new String(nls));
                                           c.setLine(input.getLine());
                                           c.setCharPositionInLine(input.getCharPositionInLine());
                                           c.setStartIndex(input.index() - 1);
                                           c.setStopIndex(input.index() - 1);
                                           emit(c);
                                       }
                                    

                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LEADING_WS"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;

                _channel=HIDDEN;

            // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:5: ({...}? => ( ' ' | '\\t' )* '#' (~ '\\n' )* ( '\\n' )+ | '#' (~ '\\n' )* )
            int alt63=2;
            alt63 = dfa63.predict(input);
            switch (alt63) {
                case 1 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:10: {...}? => ( ' ' | '\\t' )* '#' (~ '\\n' )* ( '\\n' )+
                    {
                    if ( !((startPos==0)) ) {
                        throw new FailedPredicateException(input, "COMMENT", "startPos==0");
                    }
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:27: ( ' ' | '\\t' )*
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0=='\t'||LA59_0==' ') ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:
                    	    {
                    	    if ( input.LA(1)=='\t'||input.LA(1)==' ' ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop59;
                        }
                    } while (true);

                    match('#'); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:43: (~ '\\n' )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( ((LA60_0>='\u0000' && LA60_0<='\t')||(LA60_0>='\u000B' && LA60_0<='\uFFFF')) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:44: ~ '\\n'
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);

                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:52: ( '\\n' )+
                    int cnt61=0;
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0=='\n') ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2830:52: '\\n'
                    	    {
                    	    match('\n'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt61 >= 1 ) break loop61;
                                EarlyExitException eee =
                                    new EarlyExitException(61, input);
                                throw eee;
                        }
                        cnt61++;
                    } while (true);


                    }
                    break;
                case 2 :
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2831:10: '#' (~ '\\n' )*
                    {
                    match('#'); 
                    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2831:14: (~ '\\n' )*
                    loop62:
                    do {
                        int alt62=2;
                        int LA62_0 = input.LA(1);

                        if ( ((LA62_0>='\u0000' && LA62_0<='\t')||(LA62_0>='\u000B' && LA62_0<='\uFFFF')) ) {
                            alt62=1;
                        }


                        switch (alt62) {
                    	case 1 :
                    	    // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:2831:15: ~ '\\n'
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop62;
                        }
                    } while (true);


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMENT"

    public void mTokens() throws RecognitionException {
        // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:8: ( AS | ASSERT | BREAK | CLASS | CONTINUE | DEF | DELETE | ELIF | EXCEPT | EXEC | FINALLY | FROM | FOR | GLOBAL | IF | IMPORT | IN | IS | LAMBDA | ORELSE | PASS | PRINT | RAISE | RETURN | TRY | WHILE | WITH | YIELD | BATCH | PERSISTIT | SQL | SIM | Neo4j | JAPI | RDF | SPARQL | OORELINSERT | OORELCOMMIT | CONNECTTO | NODEBUG | LPAREN | RPAREN | LBRACK | RBRACK | COLON | COMMA | SEMI | PLUS | MINUS | STAR | SLASH | VBAR | AMPER | LESS | GREATER | ASSIGN | PERCENT | BACKQUOTE | LCURLY | RCURLY | CIRCUMFLEX | TILDE | EQUAL | NOTEQUAL | ALT_NOTEQUAL | LESSEQUAL | LEFTSHIFT | GREATEREQUAL | RIGHTSHIFT | PLUSEQUAL | MINUSEQUAL | DOUBLESTAR | STAREQUAL | DOUBLESLASH | SLASHEQUAL | VBAREQUAL | PERCENTEQUAL | AMPEREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL | DOT | AT | AND | OR | NOT | FLOAT | LONGINT | INT | COMPLEX | NAME | STRING | CONTINUED_LINE | NEWLINE | WS | LEADING_WS | COMMENT )
        int alt64=99;
        alt64 = dfa64.predict(input);
        switch (alt64) {
            case 1 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:10: AS
                {
                mAS(); 

                }
                break;
            case 2 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:13: ASSERT
                {
                mASSERT(); 

                }
                break;
            case 3 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:20: BREAK
                {
                mBREAK(); 

                }
                break;
            case 4 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:26: CLASS
                {
                mCLASS(); 

                }
                break;
            case 5 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:32: CONTINUE
                {
                mCONTINUE(); 

                }
                break;
            case 6 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:41: DEF
                {
                mDEF(); 

                }
                break;
            case 7 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:45: DELETE
                {
                mDELETE(); 

                }
                break;
            case 8 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:52: ELIF
                {
                mELIF(); 

                }
                break;
            case 9 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:57: EXCEPT
                {
                mEXCEPT(); 

                }
                break;
            case 10 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:64: EXEC
                {
                mEXEC(); 

                }
                break;
            case 11 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:69: FINALLY
                {
                mFINALLY(); 

                }
                break;
            case 12 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:77: FROM
                {
                mFROM(); 

                }
                break;
            case 13 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:82: FOR
                {
                mFOR(); 

                }
                break;
            case 14 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:86: GLOBAL
                {
                mGLOBAL(); 

                }
                break;
            case 15 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:93: IF
                {
                mIF(); 

                }
                break;
            case 16 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:96: IMPORT
                {
                mIMPORT(); 

                }
                break;
            case 17 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:103: IN
                {
                mIN(); 

                }
                break;
            case 18 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:106: IS
                {
                mIS(); 

                }
                break;
            case 19 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:109: LAMBDA
                {
                mLAMBDA(); 

                }
                break;
            case 20 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:116: ORELSE
                {
                mORELSE(); 

                }
                break;
            case 21 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:123: PASS
                {
                mPASS(); 

                }
                break;
            case 22 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:128: PRINT
                {
                mPRINT(); 

                }
                break;
            case 23 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:134: RAISE
                {
                mRAISE(); 

                }
                break;
            case 24 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:140: RETURN
                {
                mRETURN(); 

                }
                break;
            case 25 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:147: TRY
                {
                mTRY(); 

                }
                break;
            case 26 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:151: WHILE
                {
                mWHILE(); 

                }
                break;
            case 27 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:157: WITH
                {
                mWITH(); 

                }
                break;
            case 28 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:162: YIELD
                {
                mYIELD(); 

                }
                break;
            case 29 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:168: BATCH
                {
                mBATCH(); 

                }
                break;
            case 30 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:174: PERSISTIT
                {
                mPERSISTIT(); 

                }
                break;
            case 31 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:184: SQL
                {
                mSQL(); 

                }
                break;
            case 32 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:188: SIM
                {
                mSIM(); 

                }
                break;
            case 33 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:192: Neo4j
                {
                mNeo4j(); 

                }
                break;
            case 34 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:198: JAPI
                {
                mJAPI(); 

                }
                break;
            case 35 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:203: RDF
                {
                mRDF(); 

                }
                break;
            case 36 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:207: SPARQL
                {
                mSPARQL(); 

                }
                break;
            case 37 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:214: OORELINSERT
                {
                mOORELINSERT(); 

                }
                break;
            case 38 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:226: OORELCOMMIT
                {
                mOORELCOMMIT(); 

                }
                break;
            case 39 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:238: CONNECTTO
                {
                mCONNECTTO(); 

                }
                break;
            case 40 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:248: NODEBUG
                {
                mNODEBUG(); 

                }
                break;
            case 41 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:256: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 42 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:263: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 43 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:270: LBRACK
                {
                mLBRACK(); 

                }
                break;
            case 44 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:277: RBRACK
                {
                mRBRACK(); 

                }
                break;
            case 45 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:284: COLON
                {
                mCOLON(); 

                }
                break;
            case 46 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:290: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 47 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:296: SEMI
                {
                mSEMI(); 

                }
                break;
            case 48 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:301: PLUS
                {
                mPLUS(); 

                }
                break;
            case 49 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:306: MINUS
                {
                mMINUS(); 

                }
                break;
            case 50 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:312: STAR
                {
                mSTAR(); 

                }
                break;
            case 51 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:317: SLASH
                {
                mSLASH(); 

                }
                break;
            case 52 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:323: VBAR
                {
                mVBAR(); 

                }
                break;
            case 53 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:328: AMPER
                {
                mAMPER(); 

                }
                break;
            case 54 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:334: LESS
                {
                mLESS(); 

                }
                break;
            case 55 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:339: GREATER
                {
                mGREATER(); 

                }
                break;
            case 56 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:347: ASSIGN
                {
                mASSIGN(); 

                }
                break;
            case 57 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:354: PERCENT
                {
                mPERCENT(); 

                }
                break;
            case 58 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:362: BACKQUOTE
                {
                mBACKQUOTE(); 

                }
                break;
            case 59 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:372: LCURLY
                {
                mLCURLY(); 

                }
                break;
            case 60 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:379: RCURLY
                {
                mRCURLY(); 

                }
                break;
            case 61 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:386: CIRCUMFLEX
                {
                mCIRCUMFLEX(); 

                }
                break;
            case 62 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:397: TILDE
                {
                mTILDE(); 

                }
                break;
            case 63 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:403: EQUAL
                {
                mEQUAL(); 

                }
                break;
            case 64 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:409: NOTEQUAL
                {
                mNOTEQUAL(); 

                }
                break;
            case 65 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:418: ALT_NOTEQUAL
                {
                mALT_NOTEQUAL(); 

                }
                break;
            case 66 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:431: LESSEQUAL
                {
                mLESSEQUAL(); 

                }
                break;
            case 67 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:441: LEFTSHIFT
                {
                mLEFTSHIFT(); 

                }
                break;
            case 68 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:451: GREATEREQUAL
                {
                mGREATEREQUAL(); 

                }
                break;
            case 69 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:464: RIGHTSHIFT
                {
                mRIGHTSHIFT(); 

                }
                break;
            case 70 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:475: PLUSEQUAL
                {
                mPLUSEQUAL(); 

                }
                break;
            case 71 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:485: MINUSEQUAL
                {
                mMINUSEQUAL(); 

                }
                break;
            case 72 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:496: DOUBLESTAR
                {
                mDOUBLESTAR(); 

                }
                break;
            case 73 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:507: STAREQUAL
                {
                mSTAREQUAL(); 

                }
                break;
            case 74 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:517: DOUBLESLASH
                {
                mDOUBLESLASH(); 

                }
                break;
            case 75 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:529: SLASHEQUAL
                {
                mSLASHEQUAL(); 

                }
                break;
            case 76 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:540: VBAREQUAL
                {
                mVBAREQUAL(); 

                }
                break;
            case 77 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:550: PERCENTEQUAL
                {
                mPERCENTEQUAL(); 

                }
                break;
            case 78 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:563: AMPEREQUAL
                {
                mAMPEREQUAL(); 

                }
                break;
            case 79 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:574: CIRCUMFLEXEQUAL
                {
                mCIRCUMFLEXEQUAL(); 

                }
                break;
            case 80 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:590: LEFTSHIFTEQUAL
                {
                mLEFTSHIFTEQUAL(); 

                }
                break;
            case 81 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:605: RIGHTSHIFTEQUAL
                {
                mRIGHTSHIFTEQUAL(); 

                }
                break;
            case 82 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:621: DOUBLESTAREQUAL
                {
                mDOUBLESTAREQUAL(); 

                }
                break;
            case 83 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:637: DOUBLESLASHEQUAL
                {
                mDOUBLESLASHEQUAL(); 

                }
                break;
            case 84 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:654: DOT
                {
                mDOT(); 

                }
                break;
            case 85 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:658: AT
                {
                mAT(); 

                }
                break;
            case 86 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:661: AND
                {
                mAND(); 

                }
                break;
            case 87 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:665: OR
                {
                mOR(); 

                }
                break;
            case 88 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:668: NOT
                {
                mNOT(); 

                }
                break;
            case 89 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:672: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 90 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:678: LONGINT
                {
                mLONGINT(); 

                }
                break;
            case 91 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:686: INT
                {
                mINT(); 

                }
                break;
            case 92 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:690: COMPLEX
                {
                mCOMPLEX(); 

                }
                break;
            case 93 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:698: NAME
                {
                mNAME(); 

                }
                break;
            case 94 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:703: STRING
                {
                mSTRING(); 

                }
                break;
            case 95 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:710: CONTINUED_LINE
                {
                mCONTINUED_LINE(); 

                }
                break;
            case 96 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:725: NEWLINE
                {
                mNEWLINE(); 

                }
                break;
            case 97 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:733: WS
                {
                mWS(); 

                }
                break;
            case 98 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:736: LEADING_WS
                {
                mLEADING_WS(); 

                }
                break;
            case 99 :
                // /Users/nginther/dm/inf/CarnotKE/jython/grammar/Python.g:1:747: COMMENT
                {
                mCOMMENT(); 

                }
                break;

        }

    }


    protected DFA23 dfa23 = new DFA23(this);
    protected DFA32 dfa32 = new DFA32(this);
    protected DFA35 dfa35 = new DFA35(this);
    protected DFA63 dfa63 = new DFA63(this);
    protected DFA64 dfa64 = new DFA64(this);
    static final String DFA23_eotS =
        "\3\uffff\1\4\2\uffff";
    static final String DFA23_eofS =
        "\6\uffff";
    static final String DFA23_minS =
        "\1\56\1\uffff\1\56\1\105\2\uffff";
    static final String DFA23_maxS =
        "\1\71\1\uffff\2\145\2\uffff";
    static final String DFA23_acceptS =
        "\1\uffff\1\1\2\uffff\1\3\1\2";
    static final String DFA23_specialS =
        "\6\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\1\1\uffff\12\2",
            "",
            "\1\3\1\uffff\12\2\13\uffff\1\4\37\uffff\1\4",
            "\1\5\37\uffff\1\5",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "2632:1: FLOAT : ( '.' DIGITS ( Exponent )? | DIGITS '.' Exponent | DIGITS ( '.' ( DIGITS ( Exponent )? )? | Exponent ) );";
        }
    }
    static final String DFA32_eotS =
        "\4\uffff";
    static final String DFA32_eofS =
        "\4\uffff";
    static final String DFA32_minS =
        "\2\56\2\uffff";
    static final String DFA32_maxS =
        "\1\71\1\152\2\uffff";
    static final String DFA32_acceptS =
        "\2\uffff\1\2\1\1";
    static final String DFA32_specialS =
        "\4\uffff}>";
    static final String[] DFA32_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\2\1\uffff\12\1\13\uffff\1\2\4\uffff\1\3\32\uffff\1\2\4\uffff"+
            "\1\3",
            "",
            ""
    };

    static final short[] DFA32_eot = DFA.unpackEncodedString(DFA32_eotS);
    static final short[] DFA32_eof = DFA.unpackEncodedString(DFA32_eofS);
    static final char[] DFA32_min = DFA.unpackEncodedStringToUnsignedChars(DFA32_minS);
    static final char[] DFA32_max = DFA.unpackEncodedStringToUnsignedChars(DFA32_maxS);
    static final short[] DFA32_accept = DFA.unpackEncodedString(DFA32_acceptS);
    static final short[] DFA32_special = DFA.unpackEncodedString(DFA32_specialS);
    static final short[][] DFA32_transition;

    static {
        int numStates = DFA32_transitionS.length;
        DFA32_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
        }
    }

    class DFA32 extends DFA {

        public DFA32(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 32;
            this.eot = DFA32_eot;
            this.eof = DFA32_eof;
            this.min = DFA32_min;
            this.max = DFA32_max;
            this.accept = DFA32_accept;
            this.special = DFA32_special;
            this.transition = DFA32_transition;
        }
        public String getDescription() {
            return "2658:1: COMPLEX : ( ( DIGITS )+ ( 'j' | 'J' ) | FLOAT ( 'j' | 'J' ) );";
        }
    }
    static final String DFA35_eotS =
        "\24\uffff";
    static final String DFA35_eofS =
        "\24\uffff";
    static final String DFA35_minS =
        "\1\42\1\uffff\2\42\1\uffff\2\42\15\uffff";
    static final String DFA35_maxS =
        "\1\165\1\uffff\2\162\1\uffff\2\162\15\uffff";
    static final String DFA35_acceptS =
        "\1\uffff\1\1\2\uffff\1\6\2\uffff\1\17\1\4\1\13\1\2\1\5\1\16\1\3"+
        "\1\11\1\14\1\7\1\12\1\15\1\10";
    static final String DFA35_specialS =
        "\24\uffff}>";
    static final String[] DFA35_transitionS = {
            "\1\7\4\uffff\1\7\32\uffff\1\6\17\uffff\1\4\2\uffff\1\5\14\uffff"+
            "\1\3\17\uffff\1\1\2\uffff\1\2",
            "",
            "\1\12\4\uffff\1\12\52\uffff\1\11\37\uffff\1\10",
            "\1\15\4\uffff\1\15\52\uffff\1\14\37\uffff\1\13",
            "",
            "\1\20\4\uffff\1\20\52\uffff\1\16\37\uffff\1\17",
            "\1\23\4\uffff\1\23\52\uffff\1\21\37\uffff\1\22",
            "",
            "",
            "",
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
            return "2675:9: ( 'r' | 'u' | 'b' | 'ur' | 'br' | 'R' | 'U' | 'B' | 'UR' | 'BR' | 'uR' | 'Ur' | 'Br' | 'bR' )?";
        }
    }
    static final String DFA63_eotS =
        "\2\uffff\2\4\1\uffff";
    static final String DFA63_eofS =
        "\5\uffff";
    static final String DFA63_minS =
        "\1\11\1\uffff\2\0\1\uffff";
    static final String DFA63_maxS =
        "\1\43\1\uffff\2\uffff\1\uffff";
    static final String DFA63_acceptS =
        "\1\uffff\1\1\2\uffff\1\2";
    static final String DFA63_specialS =
        "\1\2\1\uffff\1\0\1\1\1\uffff}>";
    static final String[] DFA63_transitionS = {
            "\1\1\26\uffff\1\1\2\uffff\1\2",
            "",
            "\12\3\1\1\ufff5\3",
            "\12\3\1\1\ufff5\3",
            ""
    };

    static final short[] DFA63_eot = DFA.unpackEncodedString(DFA63_eotS);
    static final short[] DFA63_eof = DFA.unpackEncodedString(DFA63_eofS);
    static final char[] DFA63_min = DFA.unpackEncodedStringToUnsignedChars(DFA63_minS);
    static final char[] DFA63_max = DFA.unpackEncodedStringToUnsignedChars(DFA63_maxS);
    static final short[] DFA63_accept = DFA.unpackEncodedString(DFA63_acceptS);
    static final short[] DFA63_special = DFA.unpackEncodedString(DFA63_specialS);
    static final short[][] DFA63_transition;

    static {
        int numStates = DFA63_transitionS.length;
        DFA63_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA63_transition[i] = DFA.unpackEncodedString(DFA63_transitionS[i]);
        }
    }

    class DFA63 extends DFA {

        public DFA63(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 63;
            this.eot = DFA63_eot;
            this.eof = DFA63_eof;
            this.min = DFA63_min;
            this.max = DFA63_max;
            this.accept = DFA63_accept;
            this.special = DFA63_special;
            this.transition = DFA63_transition;
        }
        public String getDescription() {
            return "2809:1: COMMENT : ({...}? => ( ' ' | '\\t' )* '#' (~ '\\n' )* ( '\\n' )+ | '#' (~ '\\n' )* );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA63_2 = input.LA(1);

                         
                        int index63_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA63_2>='\u0000' && LA63_2<='\t')||(LA63_2>='\u000B' && LA63_2<='\uFFFF')) ) {s = 3;}

                        else if ( (LA63_2=='\n') && ((startPos==0))) {s = 1;}

                        else s = 4;

                         
                        input.seek(index63_2);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA63_3 = input.LA(1);

                         
                        int index63_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((LA63_3>='\u0000' && LA63_3<='\t')||(LA63_3>='\u000B' && LA63_3<='\uFFFF')) ) {s = 3;}

                        else if ( (LA63_3=='\n') && ((startPos==0))) {s = 1;}

                        else s = 4;

                         
                        input.seek(index63_3);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA63_0 = input.LA(1);

                         
                        int index63_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA63_0=='\t'||LA63_0==' ') && ((startPos==0))) {s = 1;}

                        else if ( (LA63_0=='#') ) {s = 2;}

                         
                        input.seek(index63_0);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 63, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA64_eotS =
        "\1\uffff\24\64\7\uffff\1\140\1\142\1\145\1\150\1\152\1\154\1\160"+
        "\1\163\1\165\1\167\3\uffff\1\171\2\uffff\1\173\1\uffff\1\64\2\u0080"+
        "\3\64\3\uffff\1\u008e\1\uffff\1\u0090\1\u0092\1\uffff\1\u0094\14"+
        "\64\1\u00a3\1\64\1\u00a5\1\u00a6\22\64\4\uffff\1\u00bc\2\uffff\1"+
        "\u00be\10\uffff\1\u00c0\2\uffff\1\u00c2\7\uffff\1\u00c3\1\uffff"+
        "\1\u00c5\1\uffff\2\u0080\1\uffff\1\u00c3\1\u0080\4\uffff\1\u0080"+
        "\6\64\5\uffff\1\64\1\uffff\1\u00ce\3\64\1\u00d3\1\u00d4\6\64\1\u00db"+
        "\1\64\1\uffff\1\64\2\uffff\7\64\1\u00e6\13\64\1\u00f2\13\uffff\3"+
        "\u0080\1\u00c3\2\uffff\1\u00c3\1\64\1\uffff\4\64\2\uffff\1\u00fd"+
        "\1\u00fe\1\64\1\u0100\1\64\1\u0102\1\uffff\3\64\1\u0106\6\64\1\uffff"+
        "\1\64\1\u010e\2\64\2\uffff\3\64\1\uffff\1\64\2\uffff\1\u00c3\2\uffff"+
        "\1\u00c3\1\64\1\u0118\1\u0119\2\64\2\uffff\1\64\1\uffff\1\64\1\uffff"+
        "\3\64\1\uffff\1\u0121\1\64\1\u0123\3\64\1\u0127\1\uffff\1\u0128"+
        "\3\64\1\uffff\1\64\1\uffff\1\u00c3\1\u012d\2\uffff\2\64\1\u0130"+
        "\1\64\1\u0132\1\u0133\1\u0134\1\uffff\1\64\1\uffff\1\u0136\2\64"+
        "\2\uffff\2\64\1\uffff\1\64\1\uffff\2\64\1\uffff\1\u013e\3\uffff"+
        "\1\64\1\uffff\3\64\1\uffff\1\u0143\1\u0144\1\64\2\uffff\3\64\2\uffff"+
        "\1\u0149\2\64\1\u014c\4\uffff";
    static final String DFA64_eofS =
        "\u014d\uffff";
    static final String DFA64_minS =
        "\1\11\1\156\1\42\1\154\1\145\1\154\1\151\1\154\1\146\2\141\1\42"+
        "\1\162\1\150\1\151\1\171\1\111\1\145\1\101\1\42\1\157\7\uffff\2"+
        "\75\1\52\1\57\2\75\1\74\3\75\3\uffff\1\75\2\uffff\1\60\1\uffff\1"+
        "\162\2\56\3\42\3\uffff\1\12\1\uffff\2\11\1\uffff\1\60\1\144\2\42"+
        "\1\141\1\156\1\146\1\151\1\143\1\156\1\157\1\162\1\157\1\60\1\160"+
        "\2\60\1\155\1\163\1\151\1\162\1\151\1\154\1\171\1\151\1\164\1\145"+
        "\1\142\1\114\1\115\1\101\1\157\1\120\1\106\1\144\4\uffff\1\75\2"+
        "\uffff\1\75\10\uffff\1\75\2\uffff\1\75\7\uffff\1\60\1\uffff\4\60"+
        "\1\uffff\1\60\1\56\1\53\1\uffff\1\56\1\uffff\1\56\6\42\2\uffff\1"+
        "\0\1\uffff\1\0\1\145\1\uffff\1\60\1\141\1\163\1\156\2\60\1\146\2"+
        "\145\1\143\1\141\1\155\1\60\1\142\1\uffff\1\157\2\uffff\1\142\1"+
        "\163\1\156\2\163\1\165\1\103\1\60\1\154\1\150\1\154\1\141\2\40\1"+
        "\122\1\64\1\111\1\40\1\145\1\60\11\uffff\1\53\1\uffff\4\60\1\53"+
        "\2\60\1\162\1\uffff\1\153\1\163\1\151\1\145\2\uffff\2\60\1\160\1"+
        "\60\1\154\1\60\1\uffff\1\141\1\162\1\144\1\60\1\164\1\151\1\145"+
        "\1\162\1\156\1\157\1\uffff\1\145\1\60\1\144\1\164\2\uffff\1\121"+
        "\1\152\1\40\1\uffff\1\142\1\uffff\2\60\1\53\2\60\1\164\2\60\1\156"+
        "\1\143\2\uffff\1\164\1\uffff\1\154\1\uffff\1\154\1\164\1\141\1\uffff"+
        "\1\60\1\163\1\60\1\156\1\163\1\155\1\60\1\uffff\1\60\1\143\1\114"+
        "\1\40\1\uffff\1\165\3\60\2\uffff\1\165\1\164\1\60\1\171\3\60\1\uffff"+
        "\1\164\1\uffff\1\60\1\145\1\155\2\uffff\1\150\1\40\1\uffff\1\147"+
        "\1\uffff\1\145\1\124\1\uffff\1\60\3\uffff\1\40\1\uffff\1\162\1\151"+
        "\1\145\1\uffff\2\60\1\157\2\uffff\2\164\1\163\2\uffff\1\60\2\40"+
        "\1\60\4\uffff";
    static final String DFA64_maxS =
        "\1\176\1\163\1\162\1\157\1\145\1\170\1\162\1\154\1\163\1\141\1\162"+
        "\1\145\1\162\2\151\1\171\1\121\1\145\1\101\1\104\1\157\7\uffff\6"+
        "\75\2\76\2\75\3\uffff\1\75\2\uffff\1\71\1\uffff\1\162\1\170\1\154"+
        "\3\162\3\uffff\1\15\1\uffff\2\43\1\uffff\1\172\1\144\1\145\1\47"+
        "\1\141\1\156\1\154\1\163\1\145\1\156\1\157\1\162\1\157\1\172\1\160"+
        "\2\172\1\155\1\163\1\151\1\162\1\151\1\164\1\171\1\151\1\164\1\145"+
        "\1\142\1\114\1\115\1\101\1\157\1\120\1\106\1\164\4\uffff\1\75\2"+
        "\uffff\1\75\10\uffff\1\75\2\uffff\1\75\7\uffff\1\152\1\uffff\1\172"+
        "\1\146\2\154\1\uffff\1\152\1\154\1\71\1\uffff\1\152\1\uffff\1\154"+
        "\6\47\2\uffff\1\0\1\uffff\1\0\1\145\1\uffff\1\172\1\141\1\163\1"+
        "\164\2\172\1\146\2\145\1\143\1\141\1\155\1\172\1\142\1\uffff\1\157"+
        "\2\uffff\1\142\1\163\1\156\2\163\1\165\1\111\1\172\1\154\1\150\1"+
        "\154\1\141\2\40\1\122\1\64\1\111\1\40\1\145\1\172\11\uffff\1\71"+
        "\1\uffff\3\154\1\152\2\71\1\152\1\162\1\uffff\1\153\1\163\1\151"+
        "\1\145\2\uffff\2\172\1\160\1\172\1\154\1\172\1\uffff\1\141\1\162"+
        "\1\144\1\172\1\164\1\151\1\145\1\162\1\156\1\157\1\uffff\1\145\1"+
        "\172\1\144\1\164\2\uffff\1\121\1\152\1\40\1\uffff\1\142\1\uffff"+
        "\1\71\1\152\2\71\1\152\1\164\2\172\1\156\1\143\2\uffff\1\164\1\uffff"+
        "\1\154\1\uffff\1\154\1\164\1\141\1\uffff\1\172\1\163\1\172\1\156"+
        "\1\163\1\155\1\172\1\uffff\1\172\1\143\1\114\1\40\1\uffff\1\165"+
        "\1\71\1\152\1\172\2\uffff\1\165\1\164\1\172\1\171\3\172\1\uffff"+
        "\1\164\1\uffff\1\172\1\145\1\155\2\uffff\1\150\1\40\1\uffff\1\147"+
        "\1\uffff\1\145\1\124\1\uffff\1\172\3\uffff\1\40\1\uffff\1\162\1"+
        "\151\1\145\1\uffff\2\172\1\157\2\uffff\2\164\1\163\2\uffff\1\172"+
        "\2\40\1\172\4\uffff";
    static final String DFA64_acceptS =
        "\25\uffff\1\51\1\52\1\53\1\54\1\55\1\56\1\57\12\uffff\1\72\1\73"+
        "\1\74\1\uffff\1\76\1\100\1\uffff\1\125\6\uffff\1\135\1\136\1\137"+
        "\1\uffff\1\140\2\uffff\1\143\43\uffff\1\106\1\60\1\107\1\61\1\uffff"+
        "\1\111\1\62\1\uffff\1\113\1\63\1\114\1\64\1\116\1\65\1\101\1\102"+
        "\1\uffff\1\66\1\104\1\uffff\1\67\1\77\1\70\1\115\1\71\1\117\1\75"+
        "\1\uffff\1\124\4\uffff\1\133\3\uffff\1\134\1\uffff\1\132\7\uffff"+
        "\1\141\1\142\1\uffff\1\143\2\uffff\1\1\16\uffff\1\17\1\uffff\1\21"+
        "\1\22\24\uffff\1\122\1\110\1\123\1\112\1\120\1\103\1\121\1\105\1"+
        "\131\1\uffff\1\127\10\uffff\1\126\4\uffff\1\6\1\7\6\uffff\1\15\12"+
        "\uffff\1\31\4\uffff\1\37\1\40\3\uffff\1\43\1\uffff\1\130\12\uffff"+
        "\1\10\1\24\1\uffff\1\12\1\uffff\1\14\3\uffff\1\25\7\uffff\1\33\4"+
        "\uffff\1\42\4\uffff\1\3\1\4\7\uffff\1\26\1\uffff\1\27\3\uffff\1"+
        "\32\1\34\2\uffff\1\41\1\uffff\1\2\2\uffff\1\11\1\uffff\1\16\1\20"+
        "\1\23\1\uffff\1\30\3\uffff\1\44\3\uffff\1\13\1\36\3\uffff\1\50\1"+
        "\5\4\uffff\1\47\1\45\1\46\1\35";
    static final String DFA64_specialS =
        "\1\2\66\uffff\1\5\1\uffff\1\3\1\4\125\uffff\1\0\1\uffff\1\1\u00ba"+
        "\uffff}>";
    static final String[] DFA64_transitionS = {
            "\1\72\1\70\1\uffff\1\67\1\70\22\uffff\1\71\1\53\1\65\1\73\1"+
            "\uffff\1\45\1\41\1\65\1\25\1\26\1\36\1\34\1\32\1\35\1\54\1\37"+
            "\1\57\11\60\1\31\1\33\1\42\1\44\1\43\1\uffff\1\55\1\64\1\63"+
            "\7\64\1\22\3\64\1\21\3\64\1\23\1\20\1\64\1\62\5\64\1\27\1\66"+
            "\1\30\1\51\1\64\1\46\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\64\1\10\2"+
            "\64\1\11\1\17\1\24\1\56\1\12\1\64\1\13\1\64\1\14\1\61\1\64\1"+
            "\15\1\64\1\16\1\64\1\47\1\40\1\50\1\52",
            "\1\75\4\uffff\1\74",
            "\1\65\4\uffff\1\65\52\uffff\1\77\37\uffff\1\76",
            "\1\100\2\uffff\1\101",
            "\1\102",
            "\1\103\13\uffff\1\104",
            "\1\105\5\uffff\1\107\2\uffff\1\106",
            "\1\110",
            "\1\111\6\uffff\1\112\1\113\4\uffff\1\114",
            "\1\115",
            "\1\116\3\uffff\1\120\14\uffff\1\117",
            "\1\65\4\uffff\1\65\71\uffff\1\121\3\uffff\1\122",
            "\1\123",
            "\1\124\1\125",
            "\1\126",
            "\1\127",
            "\1\131\6\uffff\1\132\1\130",
            "\1\133",
            "\1\134",
            "\1\65\4\uffff\1\65\34\uffff\1\135",
            "\1\136",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\137",
            "\1\141",
            "\1\143\22\uffff\1\144",
            "\1\146\15\uffff\1\147",
            "\1\151",
            "\1\153",
            "\1\157\1\156\1\155",
            "\1\161\1\162",
            "\1\164",
            "\1\166",
            "",
            "",
            "",
            "\1\170",
            "",
            "",
            "\12\172",
            "",
            "\1\174",
            "\1\u0081\1\uffff\10\u0082\2\u0085\10\uffff\1\177\2\uffff\1"+
            "\u0083\4\uffff\1\u0084\1\uffff\1\u0086\2\uffff\1\176\10\uffff"+
            "\1\175\11\uffff\1\177\2\uffff\1\u0083\4\uffff\1\u0084\1\uffff"+
            "\1\u0086\2\uffff\1\176\10\uffff\1\175",
            "\1\u0081\1\uffff\12\u0087\13\uffff\1\u0083\4\uffff\1\u0084"+
            "\1\uffff\1\u0086\30\uffff\1\u0083\4\uffff\1\u0084\1\uffff\1"+
            "\u0086",
            "\1\65\4\uffff\1\65\52\uffff\1\u0089\37\uffff\1\u0088",
            "\1\65\4\uffff\1\65\52\uffff\1\u008a\37\uffff\1\u008b",
            "\1\65\4\uffff\1\65\52\uffff\1\u008c\37\uffff\1\u008d",
            "",
            "",
            "",
            "\1\70\2\uffff\1\70",
            "",
            "\1\72\1\u008f\1\uffff\1\u008e\1\u008f\22\uffff\1\71\2\uffff"+
            "\1\u0091",
            "\1\72\1\u008f\1\uffff\1\u008e\1\u008f\22\uffff\1\71\2\uffff"+
            "\1\u0091",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\22\64\1\u0093\7\64",
            "\1\u0095",
            "\1\65\4\uffff\1\65\75\uffff\1\u0096",
            "\1\65\4\uffff\1\65",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099\5\uffff\1\u009a",
            "\1\u009b\11\uffff\1\u009c",
            "\1\u009d\1\uffff\1\u009e",
            "\1\u009f",
            "\1\u00a0",
            "\1\u00a1",
            "\1\u00a2",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00a4",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00a7",
            "\1\u00a8",
            "\1\u00a9",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ad\7\uffff\1\u00ac",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "\1\u00b1",
            "\1\u00b2",
            "\1\u00b3",
            "\1\u00b4",
            "\1\u00b5",
            "\1\u00b6",
            "\1\u00b7",
            "\1\u00b8",
            "\1\u00b9\17\uffff\1\u00ba",
            "",
            "",
            "",
            "",
            "\1\u00bb",
            "",
            "",
            "\1\u00bd",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00bf",
            "",
            "",
            "\1\u00c1",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\12\172\13\uffff\1\u00c4\4\uffff\1\u0084\32\uffff\1\u00c4\4"+
            "\uffff\1\u0084",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\u00c6\7\uffff\6\u00c6\32\uffff\6\u00c6",
            "\10\u00c7\24\uffff\1\u0086\37\uffff\1\u0086",
            "\2\u00c8\32\uffff\1\u0086\37\uffff\1\u0086",
            "",
            "\12\u00c9\13\uffff\1\u00ca\4\uffff\1\u0084\32\uffff\1\u00ca"+
            "\4\uffff\1\u0084",
            "\1\u0081\1\uffff\10\u0082\2\u0085\13\uffff\1\u0083\4\uffff"+
            "\1\u0084\1\uffff\1\u0086\30\uffff\1\u0083\4\uffff\1\u0084\1"+
            "\uffff\1\u0086",
            "\1\u00cb\1\uffff\1\u00cb\2\uffff\12\u00cc",
            "",
            "\1\u0081\1\uffff\12\u0085\13\uffff\1\u0083\4\uffff\1\u0084"+
            "\32\uffff\1\u0083\4\uffff\1\u0084",
            "",
            "\1\u0081\1\uffff\12\u0087\13\uffff\1\u0083\4\uffff\1\u0084"+
            "\1\uffff\1\u0086\30\uffff\1\u0083\4\uffff\1\u0084\1\uffff\1"+
            "\u0086",
            "\1\65\4\uffff\1\65",
            "\1\65\4\uffff\1\65",
            "\1\65\4\uffff\1\65",
            "\1\65\4\uffff\1\65",
            "\1\65\4\uffff\1\65",
            "\1\65\4\uffff\1\65",
            "",
            "",
            "\1\uffff",
            "",
            "\1\uffff",
            "\1\u00cd",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00cf",
            "\1\u00d0",
            "\1\u00d2\5\uffff\1\u00d1",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00d5",
            "\1\u00d6",
            "\1\u00d7",
            "\1\u00d8",
            "\1\u00d9",
            "\1\u00da",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00dc",
            "",
            "\1\u00dd",
            "",
            "",
            "\1\u00de",
            "\1\u00df",
            "\1\u00e0",
            "\1\u00e1",
            "\1\u00e2",
            "\1\u00e3",
            "\1\u00e5\5\uffff\1\u00e4",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00e7",
            "\1\u00e8",
            "\1\u00e9",
            "\1\u00ea",
            "\1\u00eb",
            "\1\u00ec",
            "\1\u00ed",
            "\1\u00ee",
            "\1\u00ef",
            "\1\u00f0",
            "\1\u00f1",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\u00f3\1\uffff\1\u00f3\2\uffff\12\u00f4",
            "",
            "\12\u00c6\7\uffff\6\u00c6\5\uffff\1\u0086\24\uffff\6\u00c6"+
            "\5\uffff\1\u0086",
            "\10\u00c7\24\uffff\1\u0086\37\uffff\1\u0086",
            "\2\u00c8\32\uffff\1\u0086\37\uffff\1\u0086",
            "\12\u00c9\13\uffff\1\u00f5\4\uffff\1\u0084\32\uffff\1\u00f5"+
            "\4\uffff\1\u0084",
            "\1\u00f6\1\uffff\1\u00f6\2\uffff\12\u00f7",
            "\12\u00cc",
            "\12\u00cc\20\uffff\1\u0084\37\uffff\1\u0084",
            "\1\u00f8",
            "",
            "\1\u00f9",
            "\1\u00fa",
            "\1\u00fb",
            "\1\u00fc",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u00ff",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0101",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0103",
            "\1\u0104",
            "\1\u0105",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0107",
            "\1\u0108",
            "\1\u0109",
            "\1\u010a",
            "\1\u010b",
            "\1\u010c",
            "",
            "\1\u010d",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u010f",
            "\1\u0110",
            "",
            "",
            "\1\u0111",
            "\1\u0112",
            "\1\u0113",
            "",
            "\1\u0114",
            "",
            "\12\u00f4",
            "\12\u00f4\20\uffff\1\u0084\37\uffff\1\u0084",
            "\1\u0115\1\uffff\1\u0115\2\uffff\12\u0116",
            "\12\u00f7",
            "\12\u00f7\20\uffff\1\u0084\37\uffff\1\u0084",
            "\1\u0117",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u011a",
            "\1\u011b",
            "",
            "",
            "\1\u011c",
            "",
            "\1\u011d",
            "",
            "\1\u011e",
            "\1\u011f",
            "\1\u0120",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0122",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0124",
            "\1\u0125",
            "\1\u0126",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0129",
            "\1\u012a",
            "\1\u012b",
            "",
            "\1\u012c",
            "\12\u0116",
            "\12\u0116\20\uffff\1\u0084\37\uffff\1\u0084",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "\1\u012e",
            "\1\u012f",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0131",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "\1\u0135",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0137",
            "\1\u0138",
            "",
            "",
            "\1\u0139",
            "\1\u013a",
            "",
            "\1\u013b",
            "",
            "\1\u013c",
            "\1\u013d",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
            "",
            "",
            "\1\u013f",
            "",
            "\1\u0140",
            "\1\u0141",
            "\1\u0142",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u0145",
            "",
            "",
            "\1\u0146",
            "\1\u0147",
            "\1\u0148",
            "",
            "",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "\1\u014a",
            "\1\u014b",
            "\12\64\7\uffff\32\64\4\uffff\1\64\1\uffff\32\64",
            "",
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
            return "1:1: Tokens : ( AS | ASSERT | BREAK | CLASS | CONTINUE | DEF | DELETE | ELIF | EXCEPT | EXEC | FINALLY | FROM | FOR | GLOBAL | IF | IMPORT | IN | IS | LAMBDA | ORELSE | PASS | PRINT | RAISE | RETURN | TRY | WHILE | WITH | YIELD | BATCH | PERSISTIT | SQL | SIM | Neo4j | JAPI | RDF | SPARQL | OORELINSERT | OORELCOMMIT | CONNECTTO | NODEBUG | LPAREN | RPAREN | LBRACK | RBRACK | COLON | COMMA | SEMI | PLUS | MINUS | STAR | SLASH | VBAR | AMPER | LESS | GREATER | ASSIGN | PERCENT | BACKQUOTE | LCURLY | RCURLY | CIRCUMFLEX | TILDE | EQUAL | NOTEQUAL | ALT_NOTEQUAL | LESSEQUAL | LEFTSHIFT | GREATEREQUAL | RIGHTSHIFT | PLUSEQUAL | MINUSEQUAL | DOUBLESTAR | STAREQUAL | DOUBLESLASH | SLASHEQUAL | VBAREQUAL | PERCENTEQUAL | AMPEREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL | DOT | AT | AND | OR | NOT | FLOAT | LONGINT | INT | COMPLEX | NAME | STRING | CONTINUED_LINE | NEWLINE | WS | LEADING_WS | COMMENT );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA64_144 = input.LA(1);

                         
                        int index64_144 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((startPos>0)) ) {s = 142;}

                        else if ( (((startPos==0)||((startPos==0)&&(implicitLineJoiningLevel>0)))) ) {s = 143;}

                         
                        input.seek(index64_144);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA64_146 = input.LA(1);

                         
                        int index64_146 = input.index();
                        input.rewind();
                        s = -1;
                        if ( ((startPos>0)) ) {s = 142;}

                        else if ( (((startPos==0)||((startPos==0)&&(implicitLineJoiningLevel>0)))) ) {s = 143;}

                         
                        input.seek(index64_146);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA64_0 = input.LA(1);

                         
                        int index64_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA64_0=='a') ) {s = 1;}

                        else if ( (LA64_0=='b') ) {s = 2;}

                        else if ( (LA64_0=='c') ) {s = 3;}

                        else if ( (LA64_0=='d') ) {s = 4;}

                        else if ( (LA64_0=='e') ) {s = 5;}

                        else if ( (LA64_0=='f') ) {s = 6;}

                        else if ( (LA64_0=='g') ) {s = 7;}

                        else if ( (LA64_0=='i') ) {s = 8;}

                        else if ( (LA64_0=='l') ) {s = 9;}

                        else if ( (LA64_0=='p') ) {s = 10;}

                        else if ( (LA64_0=='r') ) {s = 11;}

                        else if ( (LA64_0=='t') ) {s = 12;}

                        else if ( (LA64_0=='w') ) {s = 13;}

                        else if ( (LA64_0=='y') ) {s = 14;}

                        else if ( (LA64_0=='m') ) {s = 15;}

                        else if ( (LA64_0=='S') ) {s = 16;}

                        else if ( (LA64_0=='N') ) {s = 17;}

                        else if ( (LA64_0=='J') ) {s = 18;}

                        else if ( (LA64_0=='R') ) {s = 19;}

                        else if ( (LA64_0=='n') ) {s = 20;}

                        else if ( (LA64_0=='(') ) {s = 21;}

                        else if ( (LA64_0==')') ) {s = 22;}

                        else if ( (LA64_0=='[') ) {s = 23;}

                        else if ( (LA64_0==']') ) {s = 24;}

                        else if ( (LA64_0==':') ) {s = 25;}

                        else if ( (LA64_0==',') ) {s = 26;}

                        else if ( (LA64_0==';') ) {s = 27;}

                        else if ( (LA64_0=='+') ) {s = 28;}

                        else if ( (LA64_0=='-') ) {s = 29;}

                        else if ( (LA64_0=='*') ) {s = 30;}

                        else if ( (LA64_0=='/') ) {s = 31;}

                        else if ( (LA64_0=='|') ) {s = 32;}

                        else if ( (LA64_0=='&') ) {s = 33;}

                        else if ( (LA64_0=='<') ) {s = 34;}

                        else if ( (LA64_0=='>') ) {s = 35;}

                        else if ( (LA64_0=='=') ) {s = 36;}

                        else if ( (LA64_0=='%') ) {s = 37;}

                        else if ( (LA64_0=='`') ) {s = 38;}

                        else if ( (LA64_0=='{') ) {s = 39;}

                        else if ( (LA64_0=='}') ) {s = 40;}

                        else if ( (LA64_0=='^') ) {s = 41;}

                        else if ( (LA64_0=='~') ) {s = 42;}

                        else if ( (LA64_0=='!') ) {s = 43;}

                        else if ( (LA64_0=='.') ) {s = 44;}

                        else if ( (LA64_0=='@') ) {s = 45;}

                        else if ( (LA64_0=='o') ) {s = 46;}

                        else if ( (LA64_0=='0') ) {s = 47;}

                        else if ( ((LA64_0>='1' && LA64_0<='9')) ) {s = 48;}

                        else if ( (LA64_0=='u') ) {s = 49;}

                        else if ( (LA64_0=='U') ) {s = 50;}

                        else if ( (LA64_0=='B') ) {s = 51;}

                        else if ( (LA64_0=='A'||(LA64_0>='C' && LA64_0<='I')||(LA64_0>='K' && LA64_0<='M')||(LA64_0>='O' && LA64_0<='Q')||LA64_0=='T'||(LA64_0>='V' && LA64_0<='Z')||LA64_0=='_'||LA64_0=='h'||(LA64_0>='j' && LA64_0<='k')||LA64_0=='q'||LA64_0=='s'||LA64_0=='v'||LA64_0=='x'||LA64_0=='z') ) {s = 52;}

                        else if ( (LA64_0=='\"'||LA64_0=='\'') ) {s = 53;}

                        else if ( (LA64_0=='\\') ) {s = 54;}

                        else if ( (LA64_0=='\f') ) {s = 55;}

                        else if ( (LA64_0=='\n'||LA64_0=='\r') ) {s = 56;}

                        else if ( (LA64_0==' ') && (((startPos==0)||(startPos>0)))) {s = 57;}

                        else if ( (LA64_0=='\t') && (((startPos==0)||(startPos>0)))) {s = 58;}

                        else if ( (LA64_0=='#') ) {s = 59;}

                         
                        input.seek(index64_0);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA64_57 = input.LA(1);

                         
                        int index64_57 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA64_57=='\n'||LA64_57=='\r') && ((startPos==0))) {s = 143;}

                        else if ( (LA64_57==' ') && (((startPos==0)||(startPos>0)))) {s = 57;}

                        else if ( (LA64_57=='\t') && (((startPos==0)||(startPos>0)))) {s = 58;}

                        else if ( (LA64_57=='#') && ((startPos==0))) {s = 145;}

                        else if ( (LA64_57=='\f') && ((startPos>0))) {s = 142;}

                        else s = 144;

                         
                        input.seek(index64_57);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA64_58 = input.LA(1);

                         
                        int index64_58 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA64_58=='\n'||LA64_58=='\r') && ((startPos==0))) {s = 143;}

                        else if ( (LA64_58==' ') && (((startPos==0)||(startPos>0)))) {s = 57;}

                        else if ( (LA64_58=='\t') && (((startPos==0)||(startPos>0)))) {s = 58;}

                        else if ( (LA64_58=='#') && ((startPos==0))) {s = 145;}

                        else if ( (LA64_58=='\f') && ((startPos>0))) {s = 142;}

                        else s = 146;

                         
                        input.seek(index64_58);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA64_55 = input.LA(1);

                         
                        int index64_55 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA64_55=='\n'||LA64_55=='\r') ) {s = 56;}

                        else s = 142;

                         
                        input.seek(index64_55);
                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 64, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}