package karaoke.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.Body;
import karaoke.Header;
import karaoke.Karaoke;

/**
 * 
 * @author mattj
 *
 */

public class KaraokeParser {
    /**
     * Main method. Parses and then reprints an example karaoke.
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
       // end product
       ABC_TUNE, 
       
       // header
       ABC_HEADER, FIELD_NUMBER, FIELD_TITLE, OTHER_FIELDS, FIELD_COMPOSER, 
       FIELD_DEFAULT_LENGTH, FIELD_METER, FIELD_TEMPO, FIELD_VOICE, FIELD_KEY, 
       KEY, KEYNOTE, METER, METER_FRACTION, TEMPO, 
       
       // body
       ABC_BODY, ABC_LINE, ELEMENT, // spaces and tabs
       NOTE_ELEMENT, NOTE, PITCH, NOTE_LENGTH, NOTE_LENGTH_STRICT, // notes
       REST_ELEMENT, // rests
       TUPLET_ELEMENT, TUPLET_SPEC, // tuplets
       CHORD, // chords
       MIDDLE_OF_BODY_FIELD, LYRIC, LYRICAL_ELEMENT, // voice
       
       // general
       COMMENT, COMMENT_TEXT, END_OF_LINE,       
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
            final File grammarFile = new File("src/karaoke.parser/Abc.g");
            return Parser.compile(grammarFile, ABCGrammar.ABC_TUNE);
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
     * @throws UnableToParseException 
     */
    private static Karaoke makeAbstractSyntaxTree(final ParseTree<ABCGrammar> parseTree) throws UnableToParseException {
         
        /* highest level */
        final List<ParseTree<ABCGrammar>> children = parseTree.children();
        //abc_tune ::= abc_header abc_body

        Header header = HeaderParser.parse(children.get(0).text());
        Body body = BodyParser.parse(children.get(1).text(), header);
        
        return Karaoke.createKaraoke(header, body);
    }
}
