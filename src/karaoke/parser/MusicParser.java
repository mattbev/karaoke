package karaoke.parser;

import java.io.File;
import java.io.IOException;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.music.Karaoke;

/**
 * 
 * @author mattj
 *
 */
public class MusicParser {
    /**
     * Main method. Parses and then reprints an example expression.
     * 
     * @param args command line arguments, not used
     * @throws UnableToParseException if example expression can't be parsed
     */
    public static void main(final String[] args) throws UnableToParseException {
//        final String input1 = "img/black.png --- img/tech3.png";
//        System.out.println(input1);
//        final Expression expression1 = ExpressionParser.parse(input1);
//        System.out.println(expression1);
        
    }
    
    // the nonterminals of the grammar
    private static enum ABCGrammar {
        COMPOSER,
//        EXPRESSION, RESIZE, PRIMITIVE, TOPTOBOTTOMOPERATOR, FILENAME, NUMBER, WHITESPACE, HORIZONTAL, BOTTOMOVERLAY, TOPOVERLAY, CAPTION,
    }
    
    private static Parser<ABCGrammar> parser = makeParser();
    
    /**
     * Compile the grammar into a parser.
     * 
     * @return parser for the grammar
     * @throws RuntimeException if grammar file can't be read or has syntax errors
     */
    private static Parser<ABCGrammar> makeParser() {
        try {
            // read the grammar as a file, relative to the project root.
            final File grammarFile = new File("src/karaoke.parser/ABC.g");
            return Parser.compile(grammarFile, ABCGrammar.COMPOSER);
        } catch (IOException e) {
            throw new RuntimeException("can't read the grammar file", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException("the grammar has a syntax error", e);
        }
    }
    
    /**
     * Parse a string into an expression.
     * @param string string to parse
     * @return Karaoke parsed from the string
     * @throws UnableToParseException if the string doesn't match the ABC grammar
     */
    public static Karaoke parse(final String string) throws UnableToParseException {
        // parse the example into a parse tree
        final ParseTree<ABCGrammar> parseTree = parser.parse(string);
        
        //for visuals
//        System.out.println("parse tree " + parseTree);
//        Visualizer.showInBrowser(parseTree);
        
        //make AST from parse tree
        final Karaoke karaoke = makeAbstractSyntaxTree(parseTree);
        return karaoke;
    }
    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
     * @param parseTree constructed according to the grammar in ABC.g
     * @return abstract syntax tree corresponding to parseTree
     */
    private static Karaoke makeAbstractSyntaxTree(final ParseTree<ABCGrammar> parseTree) {
        switch (parseTree.name()) {
        case COMPOSER: // composer ::= "C:" ' '* first (' ' middle)? ' ' last;
            {
                System.out.println("composer babyyyyyyy");
                return Karaoke.createMeasure(null);
            }
        default:
            throw new AssertionError("should never get here");
        }
    }
}
