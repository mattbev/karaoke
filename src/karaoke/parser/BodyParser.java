package karaoke.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.Body;
import karaoke.Chord;
import karaoke.Header;
import karaoke.LyricLine;
import karaoke.Music;
import karaoke.Note;
import karaoke.Playable;
import karaoke.Rest;
import karaoke.Tuplet;

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
     * helper function for makeAbstractSyntaxTree
     * @param parseTree some tree representing when abc_line ::= element+ end_of_line (lyric end_of_line)?
     * @param header the header to the music piece
     * @param musicMap the map of voices to their musics
     * @param voice the current voice of the line
     * @return a list of playables to represent the line
     */
    private static Map<String,List<Music>> evaluateLine(ParseTree<BodyGrammar> parseTree, Header header, Map<String, List<Music>> currentMap, String voice) {
        
        final List<Playable> line = new ArrayList<>();
        final Map<String, List<Music>> musicMap = new HashMap<>(currentMap);
        
        for (ParseTree<BodyGrammar> child : parseTree.children()) {
            switch(child.name()) {
            case ELEMENT: //element ::= note_element | rest_element | tuplet_element | barline | nth_repeat | space_or_tab 
            {
                ParseTree<BodyGrammar> elementChild = child.children().get(0);
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
                        final Chord noteChord = Playable.createChord(Arrays.asList(note), LyricLine.emptyLyricLine());
                        line.add(noteChord);
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
                        final Chord chord = Playable.createChord(notes, LyricLine.emptyLyricLine());
                        line.add(chord);
                    }
                    default:
                        throw new AssertionError("should never get here");
                    }
                }
                case REST_ELEMENT: //rest_element ::= "z" note_length
                {
                    final List<ParseTree<BodyGrammar>> restChildren = child.children();
                    final int restLength = Integer.parseInt(restChildren.get(1).text());
                    final Rest rest = Playable.createRest(restLength, LyricLine.emptyLyricLine());
                    line.add(rest);
                }
                case TUPLET_ELEMENT: //tuplet_element ::= tuplet_spec note_element+
                {
                    final Map<String, List<Music>> newMap = evaluateLine(child, header, musicMap, voice);
                    final List<Playable> tupletPlayables = newMap.get(voice).get(-1).getComponents();
                    newMap.get(voice).remove(-1);
                    final List<Chord> tupletChords = new ArrayList<>();
                    for (Playable playable : tupletPlayables) {
                        tupletChords.add((Chord) playable);
                    }
                    final Tuplet tuplet = Playable.createTuplet(tupletChords, LyricLine.emptyLyricLine());
                    line.add(tuplet);
                    
                }
                case BARLINE: //barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:"
                {
                    continue;
                }
                case NTH_REPEAT: //nth_repeat ::= "[1" | "[2"
                {
                    continue;
                }
                case SPACE_OR_TAB: //space_or_tab ::= " " | "\t"
                {
                    continue;
                }
                default:
                    throw new AssertionError("should never get here");
        
                }
            }
            case END_OF_LINE: //end_of_line ::= comment | newline
            {
                ParseTree<BodyGrammar> endOfLineChild = child.children().get(0);
                switch(endOfLineChild.name()) {
                case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
                {
                    continue;
                }
                case NEWLINE: //newline ::= "\n" | "\r" "\n"?
                {
                    final List<Music> musicList = musicMap.get(voice);
                    musicList.add(Music.createLine(line));
                    
                }
                default:
                    throw new AssertionError("should never get here");
                }
            }
            case TUPLET_SPEC:
            {
                continue; //ignore the spec of the tuplet
            }
            case LYRIC: //lyric ::= "w:" lyrical_element*
            {
                final Music mostRecentLine = musicMap.get(voice).get(-1);
                final List<ParseTree<BodyGrammar>> lyricChildren = child.children();
                final List<String> lyricElements = new ArrayList<>();
                final List<Playable> elements = new ArrayList<>();
                
                for (int i=1; i < lyricChildren.size(); i++) {
                    if (mostRecentLine.getComponents().get(i-1) instanceof Rest) {
                        lyricElements.add("");
                    } else {
                        lyricElements.add(lyricChildren.get(i).text());
                    }
                }                
      
                for (int i=0; i < mostRecentLine.getComponents().size(); i++) {
                    
                    try {
                        final LyricLine lyricLine = new LyricLine(lyricElements, i, voice);
                        elements.add(mostRecentLine.getComponents().get(i).copyWithNewLyric(lyricLine));
                    } catch(IndexOutOfBoundsException e) {
                        elements.add(mostRecentLine.getComponents().get(i).copyWithNewLyric(LyricLine.emptyLyricLine()));
                    }
                }
                return elements;
            }
            default:
                throw new AssertionError("should never get here");                    
            }
            
        }
        return line; // if there is no new line at the end, return line after evaluating all children
            
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
//        Map<String, LyricLine> voicesLyrics = new HashMap<>();
        Map<String, List<Music>> voicesToMusic = new HashMap<>();
        String voice = "1"; //default voice of 1
        
        //case ABC_LINE: // abc_line ::= element+ end_of_line (lyric end_of_line)?  | middle_of_body_field | comment
        for (ParseTree<BodyGrammar> child : children) {   // for each line            
            switch(child.name()) {
                case MIDDLE_OF_BODY_FIELD: //middle_of_body_field ::= field_voice
                {
                    voice = child.children().get(0).text(); //should always be declared before hitting default case if there are voices in the abc file
                }
                case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
                {
                    continue; //do nothing with this
                }
                default: // when abc_line ::= element+ end_of_line (lyric end_of_line)?
                    try {
                        final Music line = Music.createLine(evaluateLine(child, header, voicesToMusic, voice));
                        if (voicesToMusic.containsKey(voice)) {
                            final Music currentMusic = voicesToMusic.get(voice);
                            final Music newMusic = Music.createConcat(currentMusic, line);
                            voicesToMusic.put(voice, newMusic);
                        } else {
                            voicesToMusic.put(voice, line);
                        }
                    } catch(AssertionError e) {
                        throw new AssertionError("should never get here");
                    }
            }
        }
        return new Body(voicesToMusic);
    }
}
