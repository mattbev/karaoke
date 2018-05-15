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
import karaoke.Body;
import karaoke.Chord;
import karaoke.Header;
import karaoke.Karaoke;
import karaoke.LyricLine;
import karaoke.Note;
import karaoke.Rest;

public class BodyParser {
    
    private static enum BodyGrammar {
        // body
        ABC_BODY, ABC_LINE, MUSIC, VOICE_MUSIC, ELEMENT, COMMENT, // spaces and tabs
        NOTE_ELEMENT, NOTE, PITCH, NOTE_LENGTH, NOTE_LENGTH_STRICT, // notes
        REST_ELEMENT, // rests
        TUPLET_ELEMENT, TUPLET_SPEC, // tuplets
        CHORD, BARLINE, NTH_REPEAT,// chords
         
        FIELD_VOICE, LYRIC, LYRICAL_ELEMENT, MIDDLE_OF_BODY_FIELD, // voice
        SPACE_OR_TAB, END_OF_LINE, NEWLINE, // general
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
     * @param header the header containing the information about the body
     * @return Body parsed from the string
     * @throws UnableToParseException if the string doesn't match the Body grammar
     */
    public static Body parse(final String string, Header header) throws UnableToParseException {
        // parse the string into a parse tree
        final ParseTree<BodyGrammar> parseTree = parser.parse(string);
        
        //for visuals
//        System.out.println("parse tree " + parseTree);
//        Visualizer.showInBrowser(parseTree);
        
        //make AST from parse tree
        final Body body = makeAbstractSyntaxTree(parseTree, header);
        return body;
    }
    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
     * @param parseTree constructed according to the body grammar in ABC.g
     * @return abstract syntax tree corresponding to parseTree
     */
    private static Body makeAbstractSyntaxTree(final ParseTree<BodyGrammar> parseTree, Header header) {
        /* body */
        //abc_body ::= abc_line+
 
        final List<ParseTree<BodyGrammar>> children = parseTree.children();
        Map<String, LyricLine> voicesLyrics = new HashMap<>();
        Map<String, Karaoke> voicesKaraoke = new HashMap<>();
        
        switch(parseTree.name()) {
        case ABC_LINE: //abc_line ::= element+ end_of_line (lyric end_of_line)?  | middle_of_body_field | comment
        {
            ParseTree<BodyGrammar> lineChild = parseTree.children().get(0);
            switch(lineChild.name()) {
            case MIDDLE_OF_BODY_FIELD: //middle_of_body_field ::= field_voice
            {
                final String voice = lineChild.children().get(0).text();
            }
            case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
            {
                final String comment = lineChild.children().get(0).text(); //do nothing with this
            }
            default: // when abc_line ::= element+ end_of_line (lyric end_of_line)?
                makeAbstractSyntaxTree(lineChild, header);
            }
        }
        case ELEMENT: //element ::= note_element | rest_element | tuplet_element | barline | nth_repeat | space_or_tab 
        {
            ParseTree<BodyGrammar> elementChild = parseTree.children().get(0);
            switch(elementChild.name()) {
            case NOTE_ELEMENT: //note_element ::= note | chord
            {
                ParseTree<BodyGrammar> noteChild = elementChild.children().get(0);
                switch(noteChild.name()) {
                case NOTE: //note ::= pitch note_length?
                {
                    final String pitch = noteChild.children().get(0).text();
                    String noteLength;
                    try {
                        noteLength = noteChild.children().get(1).text();
                    } catch (IndexOutOfBoundsException e) {
                        noteLength = header.getDefaultLength();
                    }
                    final Note note = new Note(pitch, noteLength);        
                    
                }
                case CHORD: //chord ::= "[" note+ "]"
                {
                    final List<Note> notes = new ArrayList<>();
                    List<ParseTree<BodyGrammar>> noteChildren = noteChild.children();
                    for (int i=1; i < noteChildren.size()-1; i++) {
                        ParseTree<BodyGrammar> noteTree = noteChildren.get(i);
                        final String pitch = noteTree.children().get(0).text();
                        String noteLength;
                        try {
                            noteLength = noteTree.children().get(1).text();
                        } catch (IndexOutOfBoundsException e) {
                            noteLength = header.getDefaultLength();
                        }
                        final Note note = new Note(pitch, noteLength);  
                        notes.add(note);
                    }   
                    final Chord chord = new Chord(notes); 
                }
                default:
                    break;
                }
            }
            case REST_ELEMENT: //rest_element ::= "z" note_length
            {
                final List<ParseTree<BodyGrammar>> restChildren = parseTree.children();
                final int restLength = Integer.parseInt(restChildren.get(1).text());
                final Rest rest = new Rest(restLength);
            }
            case TUPLET_ELEMENT: //tuplet_element ::= tuplet_spec note_element+
            {
                final List<ParseTree<BodyGrammar>> tupletChildren = parseTree.children();
                final List<Chord> chords = new ArrayList<>();
                for (int i=1; i < tupletChildren.size(); i++) {
                    ParseTree<BodyGrammar> subChild = tupletChildren.get(i);
                    
                }
                
            }
            case BARLINE: //barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:"
            {
                //TODO
            }
            case NTH_REPEAT: //nth_repeat ::= "[1" | "[2"
            {
                //TODO
            }
            case SPACE_OR_TAB: //space_or_tab ::= " " | "\t"
            {
                //TODO
            }
            default:
                break;

            }
        }
        case END_OF_LINE: //end_of_line ::= comment | newline
        {
            ParseTree<BodyGrammar> endOfLineChild = parseTree.children().get(0);
            switch(endOfLineChild.name()) {
            case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
            {
                final String comment = endOfLineChild.children().get(0).text(); //do nothing with this
            }
            case NEWLINE: //newline ::= "\n" | "\r" "\n"?
            {
                //TODO;
            }
            default:
                throw new AssertionError("should never get here");
            }
        }
        case LYRIC: //lyric ::= "w:" lyrical_element*
        {
            final List<ParseTree<BodyGrammar>> lyricChildren = parseTree.children();
            final List<String> lyricElements = new ArrayList<>();
            for (int i=1; i < lyricChildren.size(); i++) {
                lyricElements.add(lyricChildren.get(i).text());
            }
            final LyricLine lyricLine = new LyricLine(lyricElements, 0);
        }
        default:
            makeAbstractSyntaxTree(parseTree, header);                    
        }
    }
}
    
    
    
    
    
    
    
    
//    BodyGrammar name = children.get(i).children().get(0).name();
//    if (name.equals(FIELD_VOICE)) {
//        String voice = children.get(i).text();
//        
//    } else if (name.equals(MUSIC)) {
//        String voice = "main";
//    } 
//        int j = 0;
//        Karaoke karaoke = makeAbstractSyntaxTree(children.get(1).children().get(j));
//        if (children.get(1).children().size() > 1) {
//            while (children.get(1).children().get(j).name() != ABCGrammar.END_OF_LINE) {
//                karaoke = Karaoke.createConcat(karaoke, makeAbstractSyntaxTree(children.get(1).children().get(j)));
//                j += 1;
//            }
//        }
//        
//        if (voicesKaraoke.containsKey(children.get(0).text())) {
//            voicesKaraoke.put(children.get(0).text(), Karaoke.createConcat(voicesKaraoke.get(children.get(0).text()), karaoke));
//        } else {
//            voicesKaraoke.put(children.get(0).text(), karaoke);
//        }
//    case MUSIC:
//        break;
//    case ABC_LINE:
//        break;
//    case CHORD:
//        break;
//    case ELEMENT:
//        break;
//    case LYRIC:
//        break;
//    case LYRICAL_ELEMENT:
//        break;
//    case MIDDLE_OF_BODY_FIELD:
//        break;
//    case NOTE:
//        break;
//    case NOTE_ELEMENT:
//        break;
//    case NOTE_LENGTH:
//        break;
//    case NOTE_LENGTH_STRICT:
//        break;
//    case PITCH:
//        break;
//    case REST_ELEMENT:
//        break;
//    case TUPLET_ELEMENT:
//        break;
//    case TUPLET_SPEC:
//        break;
//    default:
//        break;   
//    }