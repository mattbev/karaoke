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
import karaoke.*;

public class BodyParser {
    
    private static enum BodyGrammar {
        // body
        ABC_BODY, ABC_LINE, MUSIC, VOICE_MUSIC, ELEMENT, COMMENT, // spaces and tabs
        NOTE_ELEMENT, NOTE, PITCH, NOTE_LENGTH, NOTE_LENGTH_STRICT, // notes
        REST_ELEMENT, // rests
        TUPLET_ELEMENT, TUPLET_SPEC, // tuplets
        CHORD, // chords
         
        FIELD_VOICE, LYRIC, LYRICAL_ELEMENT, // voice
    }
    
    private static Parser<BodyGrammar> parser = makeParser();
    
    /**
     * Compile the grammar into a parser.
     * 
     * @return parser for the grammar
     * @throws RuntimeException if grammar file can't be read or has syntax errors
     */
    private static Parser<BodyGrammar> makeParser() {
        try {
            // read the grammar as a file, relative to the project root.
            final File grammarFile = new File("src/karaoke.parser/Abc.g");
            return Parser.compile(grammarFile, BodyGrammar.ABC_BODY);
        } catch (IOException e) {
            throw new RuntimeException("can't read the grammar file", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException("the grammar has a syntax error", e);
        }
    }
    
    /**
     * Parse a string into a body.
     * @param string string to parse
     * @return Body parsed from the string
     * @throws UnableToParseException if the string doesn't match the Body grammar
     */
    public static Body parse(final String string) throws UnableToParseException {
        // parse the string into a parse tree
        final ParseTree<BodyGrammar> parseTree = parser.parse(string);
        
        //for visuals
//        System.out.println("parse tree " + parseTree);
//        Visualizer.showInBrowser(parseTree);
        
        //make AST from parse tree
        final Body body = makeAbstractSyntaxTree(parseTree);
        return body;
    }
    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
     * @param parseTree constructed according to the body grammar in ABC.g
     * @return abstract syntax tree corresponding to parseTree
     */
    private static Body makeAbstractSyntaxTree(final ParseTree<BodyGrammar> parseTree) {
        
 
        final List<ParseTree<BodyGrammar>> children = parseTree.children();
        Map<String, LyricLine> voicesLyrics = new HashMap<>();
        Map<String, Karaoke> voicesKaraoke = new HashMap<>();
        
        // iterate through abc_lines
        
        for (int i = 0; i < children.size(); i ++) {
            
            BodyGrammar name = children.get(i).children().get(0).name();
            if (name.equals(FIELD_VOICE)) {
                String voice = children.get(i).text();
                
            } else if (name.equals(MUSIC)) {
                String voice = "main";
            } 
                int j = 0;
                Karaoke karaoke = makeAbstractSyntaxTree(children.get(1).children().get(j));
                if (children.get(1).children().size() > 1) {
                    while (children.get(1).children().get(j).name() != ABCGrammar.END_OF_LINE) {
                        karaoke = Karaoke.createConcat(karaoke, makeAbstractSyntaxTree(children.get(1).children().get(j)));
                        j += 1;
                    }
                }
                
                if (voicesKaraoke.containsKey(children.get(0).text())) {
                    voicesKaraoke.put(children.get(0).text(), Karaoke.createConcat(voicesKaraoke.get(children.get(0).text()), karaoke));
                } else {
                    voicesKaraoke.put(children.get(0).text(), karaoke);
                }
            case MUSIC:
                break;
            case ABC_LINE:
                break;
            case CHORD:
                break;
            case ELEMENT:
                break;
            case LYRIC:
                break;
            case LYRICAL_ELEMENT:
                break;
            case MIDDLE_OF_BODY_FIELD:
                break;
            case NOTE:
                break;
            case NOTE_ELEMENT:
                break;
            case NOTE_LENGTH:
                break;
            case NOTE_LENGTH_STRICT:
                break;
            case PITCH:
                break;
            case REST_ELEMENT:
                break;
            case TUPLET_ELEMENT:
                break;
            case TUPLET_SPEC:
                break;
            default:
                break;   
            }
    }
}

/**
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
//    final ParseTree<ABCGrammar> pitch = parseTree.children().get(0);
//    try {
//        final ParseTree<ABCGrammar> noteLength = parseTree.children().get(1);
//    } catch (IndexOutOfBoundsException e){
//        final 
//    }
    
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
//    return numerator/denominator;
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
//case LYRICAL_ELEMENT: //lyrical_element ::= " "+ | "-" | "_" | "*" | "~" | backslash_hyphen | "|" | lyric_text
//{
//    //TODO 
//}

/* general */ 
case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
{
    //TODO 
}
//case COMMENT_TEXT: //comment_text ::= [ ^(newline)]*
//{
//    //TODO 
//}
case END_OF_LINE: //end_of_line ::= comment | newline
{
//    final ParseTree<ABCGrammar> child = parseTree.children().get(0);
//    // check which alternative (comment or newline) was actually matched
//    switch(child.name()) {
//    case COMMENT:
//        return makeAbstractSyntaxTree(child);
//    case NEWLINE:
//        
//    }
}
*/
}