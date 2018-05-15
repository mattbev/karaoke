package karaoke.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.Chord;
import karaoke.Concat;
import karaoke.Karaoke;
import karaoke.Lyric;
import karaoke.LyricLine;
import karaoke.Measure;
import karaoke.Note;
import karaoke.Playable;
import karaoke.Rest;
import karaoke.Tuplet;

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
     */
    private static Karaoke makeAbstractSyntaxTree(final ParseTree<ABCGrammar> parseTree) {
        switch (parseTree.name()) {
        /* highest level */
        case ABC_TUNE: //abc_tune ::= abc_header abc_body
            {
               
                
                return karaoke;
        }
            
        /* header */
        case ABC_HEADER: //abc_header ::= field_number comment* field_title other_fields* field_key
            {
                //TODO
             }
        case FIELD_NUMBER: //field_number ::= "X:" digit+ end_of_line
            {
                //TODO
            }
        case FIELD_TITLE: //field_title ::= "T:" text end_of_line
            {
                //TODO
            }
        case OTHER_FIELDS: //other_fields ::= field_composer | field_default_length | field_meter | field_tempo | field_voice | comment
            {
               //TODO 
            }
        case FIELD_COMPOSER: //field_composer ::= "C:" text end_of_line
            {
               //TODO
            }
        case FIELD_DEFAULT_LENGTH: //field_default_length ::= "L:" note_length end_of_line
            {
                //TODO 
            }
        case FIELD_METER: //field_meter ::= "M:" meter end_of_line
            {
                //TODO 
            }
        case FIELD_TEMPO: //field-tempo ::= "Q:" tempo end_of_line
            {
                //TODO 
            }
        case FIELD_VOICE: //field_voice ::= "V:" text end_of_line
            {
                //TODO 
            }
        case FIELD_KEY: //field_key ::= "K:" key end_of_line
            {
                //TODO 
            }
        case KEY: //key ::= keynote "m"?
            {
                //TODO 
            }
        case KEYNOTE: //keynote ::= basenote ("#" | "b")?
            {
                //TODO 
            }
        case METER: //meter ::= "C" | "C|" | meter_fraction
            {
                //TODO 
            }
        case METER_FRACTION: //meter_fraction ::= digit+ "/" digit+
            {
                //TODO 
            }
        case TEMPO: //tempo ::= meter_fraction "=" digit+
            {
                //TODO 
            }
            
        /* body */
        case ABC_BODY: //abc_body ::= abc_line+
            {
                //TODO 
            }
        case ABC_LINE: //abc_line ::= element+ end_of_line (lyric end_of_line)?  | middle_of_body_field | comment
            {
                //TODO 
            }
        case ELEMENT: //element ::= note_element | rest_element | tuplet_element | barline | nth_repeat | space_or_tab 
            {
                //TODO 
            }
        case NOTE_ELEMENT: //note_element ::= note | chord
            {
                //TODO 
            }
        case NOTE: //note ::= pitch note_length?
            {
//                final ParseTree<ABCGrammar> pitch = parseTree.children().get(0);
//                try {
//                    final ParseTree<ABCGrammar> noteLength = parseTree.children().get(1);
//                } catch (IndexOutOfBoundsException e){
//                    final 
//                }
                
            }
        case PITCH: //pitch ::= accidental? basenote octave?
            {
                //TODO 
            }
        case NOTE_LENGTH: //note_length ::= (digit+)? ("/" (digit+)?)?
            {
                //TODO 
            }
        case NOTE_LENGTH_STRICT: //note_length_strict ::= digit+ "/" digit+
            {
                final List<ParseTree<ABCGrammar>> children = parseTree.children();
                final double numerator = (double) Integer.parseInt(children.get(0).text());
                final double denominator = (double) Integer.parseInt(children.get(1).text());
//                return numerator/denominator;
            }
        case REST_ELEMENT: //rest_element ::= "z" note_length
            {
                final List<ParseTree<ABCGrammar>> children = parseTree.children();
                final int noteLength = Integer.parseInt(children.get(1).text());
                return new Rest(noteLength);
            }
        case TUPLET_ELEMENT: //tuplet_element ::= tuplet_spec note_element+
            {
                final List<ParseTree<ABCGrammar>> children = parseTree.children();
                List<Chord> chords = new ArrayList<>();
                for (int i=1; i<children.size(); i++) {
                    Karaoke karaoke = makeAbstractSyntaxTree(children.get(i));
                    chords.add((Chord) karaoke.getMusic().get(0).getComponents().get(0));
                }
                return new Tuplet(chords);
            }
        case TUPLET_SPEC: //tuplet_spec ::= "(" digit 
            {
                //TODO 
            }
        case CHORD: //chord ::= "[" note+ "]"
            {
                final List<ParseTree<ABCGrammar>> children = parseTree.children();
                final List<Note> notes = new ArrayList<>();
                final String lyrics = ""; //TODO
                for (int i=1; i<children.size()-1; i++) {
                    Karaoke karaoke = makeAbstractSyntaxTree(children.get(i));
                    Measure measure = karaoke.getMusic().get(0);
                    Playable playable = measure.getComponents().get(0);
                    notes.add((Note) playable);
                }
                return new Chord(notes, null);
            }
        case MIDDLE_OF_BODY_FIELD: //middle_of_body_field ::= voice
            {
                //TODO 
            }
        case LYRIC: //lyric ::= "w:" lyrical_element*
            {
                final List<ParseTree<ABCGrammar>> children = parseTree.children();
                Karaoke karaoke = makeAbstractSyntaxTree(children.get(0));
                for (int i=1; i<children.size(); i++) {
                    karaoke = new Concat(karaoke, makeAbstractSyntaxTree(children.get(i)));
                }
                return karaoke;
            }
//        case LYRICAL_ELEMENT: //lyrical_element ::= " "+ | "-" | "_" | "*" | "~" | backslash_hyphen | "|" | lyric_text
//            {
//                //TODO 
//            }
        
        /* general */ 
        case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
            {
                //TODO 
            }
//        case COMMENT_TEXT: //comment_text ::= [ ^(newline)]*
//            {
//                //TODO 
//            }
        case END_OF_LINE: //end_of_line ::= comment | newline
            {
//                final ParseTree<ABCGrammar> child = parseTree.children().get(0);
//                // check which alternative (comment or newline) was actually matched
//                switch(child.name()) {
//                case COMMENT:
//                    return makeAbstractSyntaxTree(child);
//                case NEWLINE:
//                    
//                }
            }
            
        default:
            throw new AssertionError("should never get here");
        }
    }
}
