package parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
     // end product
        ABC_TUNE, 
        
        // header
        ABC_HEADER, FIELD_NUMBER, FIELD_TITLE, OTHER_FIELDS, FIELD_COMPOSER, 
        FIELD_DEFAULT_LENGTH, FIELD_METER, FIELD_TEMPO, FIELD_VOICE, FIELD_KEY, 
        KEY, KEYNOTE, METER, METER_FRACTION, TEMPO, 
        
     // body
        ABC_BODY, ABC_LINE, ELEMENT, COMMENT, // spaces and tabs
        NOTE_ELEMENT, NOTE, PITCH, NOTE_LENGTH, NOTE_LENGTH_STRICT, ACCIDENTAL, BASENOTE, OCTAVE, // notes
        REST_ELEMENT, // rests
        TUPLET_ELEMENT, TUPLET_SPEC, // tuplets
        CHORD, BARLINE, NTH_REPEAT,// chords
         
        BACKSLASH_HYPHEN, LYRIC_TEXT, //lyric
        
        LYRIC, LYRICAL_ELEMENT, MIDDLE_OF_BODY_FIELD, // voice
        SPACE_OR_TAB, END_OF_LINE, NEWLINE, DIGIT, TEXT, // general   
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
            final File grammarFile = new File("src/parser/Abc.g");
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
                        if (noteChild.children().get(1).text().length() > 0) {
                            noteLength = noteChild.children().get(1).text();
                        } else {
                            noteLength = "1";
                        }
                        final Note note = new Note(pitch, noteLength, header);    
                        final Chord noteChord = Playable.createChord(Arrays.asList(note), LyricLine.emptyLyricLine());
                        line.add(noteChord);
                        continue;
                    }
                    case CHORD: //chord ::= "[" note+ "]"
                    {
                        final List<Note> notes = new ArrayList<>();
                        List<ParseTree<BodyGrammar>> noteChildren = noteChild.children();
                        for (int i=0; i < noteChildren.size(); i++) {
                            ParseTree<BodyGrammar> noteTree = noteChildren.get(i);
                            final String pitch = noteTree.children().get(0).text();
                            String noteLength;
                            if (noteChild.children().get(1).text().length() > 0) {
                                noteLength = noteTree.children().get(1).text();
                            } else {
                                noteLength = "1";
                            }
                            final Note note = new Note(pitch, noteLength, header);  
                            notes.add(note);
                        }   
                        final Chord chord = Playable.createChord(notes, LyricLine.emptyLyricLine());
                        line.add(chord);
                        continue;
                    }
                    default:
                        throw new AssertionError("should never get here6");
                    }
                }
                case REST_ELEMENT: //rest_element ::= "z" note_length
                {
                    final List<ParseTree<BodyGrammar>> restChildren = child.children();
                    String restLength = restChildren.get(0).children().get(0).text();
                    if (restLength.length() == 0) {
                        restLength = "1";
                    }
                    final Rest rest = Playable.createRestFromString(restLength, LyricLine.emptyLyricLine(), header);
                    line.add(rest);
                    continue;
                }
                case TUPLET_ELEMENT: //tuplet_element ::= tuplet_spec note_element+
                {
                    final List<ParseTree<BodyGrammar>> tupletElements = child.children().get(0).children();
                    List<Playable> tupletPlayables = new ArrayList<>();
                    for (int i=1; i < tupletElements.size(); i++) {
                        final ParseTree<BodyGrammar> element = tupletElements.get(i);
                        switch(element.name()) {
                        case NOTE_ELEMENT:
                        {
                            ParseTree<BodyGrammar> noteType = element.children().get(0);
                            switch(noteType.name()) {
                            case NOTE:
                            {
                                final String pitch = noteType.children().get(0).text();
                                String noteLength;
                                if (noteType.children().get(1).text().length() > 0) {
                                    noteLength = noteType.children().get(1).text();
                                } else {
                                    noteLength = "1";
                                }
                                final Note note = new Note(pitch, noteLength, header);    
                                final Chord noteChord = Playable.createChord(Arrays.asList(note), LyricLine.emptyLyricLine());
                                tupletPlayables.add(noteChord);
                                continue;
                            }
                            case CHORD:
                            {
                                final List<Note> notes = new ArrayList<>();
                                List<ParseTree<BodyGrammar>> noteChildren = noteType.children();
                                for (int j=0; j < noteChildren.size(); j++) {
                                    ParseTree<BodyGrammar> noteTree = noteChildren.get(j);
                                    final String pitch = noteTree.children().get(0).text();
                                    String noteLength;
                                    if (noteType.children().get(1).text().length() > 0) {
                                        noteLength = noteTree.children().get(1).text();
                                    } else {
                                        noteLength = "1";
                                    }
                                    final Note note = new Note(pitch, noteLength, header);  
                                    notes.add(note);
                                }   
                                final Chord chord = Playable.createChord(notes, LyricLine.emptyLyricLine());
                                tupletPlayables.add(chord);
                                continue;
                            }
                            default:
                                throw new AssertionError("should never get here5");
                            }
                        }
                        case TUPLET_SPEC:
                            continue;
                        default:
                            throw new AssertionError("should never get here4");
                        }
                    }
                    final List<Chord> tupletChords = new ArrayList<>();
                    for (Playable playable : tupletPlayables) {
                        tupletChords.add((Chord) playable);
                    }
                    final Tuplet tuplet = Playable.createTuplet(tupletChords, LyricLine.emptyLyricLine());
                    line.add(tuplet);
                    continue;                    
                }
                case BARLINE: //barline ::= "|" | "||" | "[|" | "|]" | ":|" | "|:"
                    //Check if it's first or second seen major bar line
                    //
                    // IF second seen major bar line
                    // ---> copy each of the notes kept in the repeat
                    //      list and concat line
                    //      -clear repeat list and first and second seen slots
                    //ELSE  list it as first major bar line
                    //--->  start keeping "repeat list" of notes that come after it
                    final String barline = child.children().get(0).text();
                    if (barline.equals(":|")) {
                        
                    }
                case NTH_REPEAT: //nth_repeat ::= "[1" | "[2"
                    continue;
                case SPACE_OR_TAB: //space_or_tab ::= " " | "\t"
                    continue;
                default:
                    throw new AssertionError("should never get here3");
                }
            }
            case END_OF_LINE: //end_of_line ::= comment | newline
            {
                ParseTree<BodyGrammar> endOfLineChild = child.children().get(0);
                switch(endOfLineChild.name()) {
                case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
                    continue;
                case NEWLINE: //newline ::= "\n" | "\r""\n"?
                {
//                    if (musicMap.containsKey(voice)) { // if there, add it to current list
//                        final List<Music> musicList = musicMap.get(voice);
//                        musicList.add(Music.createLine(line));
//                        musicMap.put(voice, musicList);
//                    } else { // if not there, create new entry
//                        musicMap.put(voice, Arrays.asList(Music.createLine(line)));
//                    }
//                    return musicMap;     
                    continue;
                }
                default:
                    throw new AssertionError("should never get here2");
                }
            }

            case LYRIC: //lyric ::= "w:" lyrical_element*
            {
                final List<ParseTree<BodyGrammar>> lyricChildren = child.children();
                final List<String> lyricStrings = getLyricStrings(lyricChildren);
                final List<Object> parsedLyricElements = asssignLyricsToPlayables(lyricStrings, line, lyricChildren);
                final Map<Integer, Integer> playableLyricIndexes = (Map<Integer, Integer>) parsedLyricElements.get(0);
                final List<String> parsedLyricStrings = (List<String>) parsedLyricElements.get(1);

                final List<Playable> modifiedLine = new ArrayList<>();
                for (int i=0; i<line.size(); i++) {
                    final Playable p = line.get(i);
                    final LyricLine lyricLine = new LyricLine(parsedLyricStrings, playableLyricIndexes.get(i), voice);
                    modifiedLine.add(p.copyWithNewLyric(lyricLine));
                } 
                if (musicMap.containsKey(voice)) { // if there, add it to current list
                    final List<Music> musicList = new ArrayList<>(musicMap.get(voice));
                    musicList.add(Music.createLine(modifiedLine));
                    musicMap.put(voice, musicList);
                } else { // if not there, create new entry
                    musicMap.put(voice, Arrays.asList(Music.createLine(modifiedLine)));
                }
                return musicMap;
            }
            case COMMENT: //comment ::= space_or_tab* "%" comment_text newline
                return musicMap; //make no changes to ADTs
            
            default:
                throw new AssertionError("should never get here");        
            }
            
        }
        if (musicMap.containsKey(voice)) { // if there, add it to current list
            final List<Music> musicList = new ArrayList<>(musicMap.get(voice));
            musicList.add(Music.createLine(line));
            
            musicMap.put(voice, musicList);
        } else { // if not there, create new entry
            musicMap.put(voice, Arrays.asList(Music.createLine(line)));
        }
        return musicMap;   // if there is no new line at the end, return line after evaluating all children
            
    }
    
    private static List<String> getLyricStrings(List<ParseTree<BodyGrammar>> lyricChildren) {
        final List<String> lyricList = new ArrayList<>();
        String currentString = "";
        for (int i=0; i < lyricChildren.size(); i++) {
            final ParseTree<BodyGrammar> child = lyricChildren.get(i);

            if (child.children().size() > 0 && child.children().get(0).name().equals(BodyGrammar.LYRIC_TEXT)) { //has text child
                currentString += child.children().get(0).text();
            }
            else if (child.text().equals("-")) {
                currentString += child.text();
                lyricList.add(currentString);
                currentString = "";
            }
            else if (child.text().equals("~")) {
                currentString += " ";
            }
            else if (child.text().equals("\\-")) {
                currentString += "-";
            }
            else if (child.text().equals(" ")) {
                lyricList.add(currentString);
                currentString = "";
            }            
        }
        lyricList.add(currentString);
        while (lyricList.contains("")) {
            lyricList.remove("");
        }
        return lyricList;        
    }
    
    private static List<Object> asssignLyricsToPlayables(List<String> lyricStrings, List<Playable> playableList, List<ParseTree<BodyGrammar>> treeChildren) {
        final Map<Integer, Integer> assignments = new HashMap<>();
        final List<Playable> playables = new ArrayList<>();
        for (Playable p : playableList) {
            if (!(p instanceof Rest)) {
                playables.add(p);
            }
        }
        
        int k = 0;
        do {
            ParseTree<BodyGrammar> c = treeChildren.get(k);
            if (c.text().equals(" ")) {
                k++;
            }
            else {
                break;
            }
        } while (true);
        
        final List<ParseTree<BodyGrammar>> lyricChildren = treeChildren.subList(k, treeChildren.size());
        int playableIndex = 0;
        int lyricIndex = 0;
        int i = 0;
        outerloop:
        while (i < lyricChildren.size()) {
            ParseTree<BodyGrammar> child = lyricChildren.get(i);
            //case 1: a text = assign to playable
            if (child.children().size() > 0 && child.children().get(0).name().equals(BodyGrammar.LYRIC_TEXT)) {
                assignments.put(playableIndex, lyricIndex);
                playableIndex++;
                if (! (playableIndex < playables.size())) {
                    break outerloop;
                }
            }
            //case 2: a hyphen
            if (child.text().equals("-")) {
                lyricIndex++;
            }
            
            //case 3: an underscore
            if (child.text().equals("_")) {
                assignments.put(playableIndex, lyricIndex);
                playableIndex++;
                if (! (playableIndex < playables.size())) {
                    break outerloop;
                }
            }
            
            //case 4: star
            else if (child.text().equals("*")) {
                int nextIndex = i;
                while (lyricChildren.get(nextIndex).text().equals("*")) {
                    assignments.put(nextIndex, lyricIndex);
                    playableIndex++;
                    if (! (playableIndex < playables.size())) {
                        break outerloop;
                    }
                    nextIndex++;
                }
                i = nextIndex;
                lyricIndex++;
            }
            //case 5: squiggle
            else if (child.text().equals("~")) {
                assignments.put(playableIndex, lyricIndex);
                playableIndex++;
                if (! (playableIndex < playables.size())) {
                    break outerloop;
                }
                lyricIndex++;
            }
            //case 6: backslash hyphen
            else if (child.text().equals("\\-")) {
                assignments.put(playableIndex, lyricIndex);
                playableIndex++;
                if (! (playableIndex < playables.size())) {
                   break outerloop;
                }                
                lyricIndex++;
            }
            //case 7: barline
            else if (child.text().equals("|")) {
                //TODO
            }
            //case 8: space
            else if (child.text().equals(" ")) {
                lyricIndex++;
            }
            i++;
        }
        
        final List<Integer> restIndices = new ArrayList<>();
        Map<Integer, Integer> newAssignments = new HashMap<>();
        
        for (int n=0; n < playableList.size(); n++) {
            Playable p = playableList.get(n);
            if (p instanceof Rest) {
                restIndices.add(n);
                try {
                    lyricStrings.add(n, "");
                } catch (IndexOutOfBoundsException e) {
                    lyricStrings.add("");
                }
            }

        
        }
        for (Integer key : assignments.keySet()) {
            int newKey = key;
            int newValue = assignments.get(key);
            for (int v : restIndices) {
                if (v <= newKey) {
                    newKey++;
                    newValue++;
                }
            }
            newAssignments.put(newKey, newValue);
        }

        for (Integer index : restIndices) {
            if (index == 0 || index == 1) {
                newAssignments.put(index, index);
            } else {
            newAssignments.put(index, newAssignments.get(index-1)+1);
            }
        }
        return Collections.unmodifiableList(Arrays.asList(newAssignments, lyricStrings));
        
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

        Map<String, List<Music>> voicesToLines = new HashMap<>();
        String voice = "1"; //default voice of 1
        
        //case ABC_LINE: // abc_line ::= element+ end_of_line (lyric end_of_line)?  | middle_of_body_field | comment
        for (ParseTree<BodyGrammar> child : children) {// for each line
            switch(child.children().get(0).name()) {                
                case MIDDLE_OF_BODY_FIELD: //middle_of_body_field ::= field_voice
                {
                    voice = child.children().get(0).children().get(0).children().get(0).text(); //should always be declared before hitting default case if there are voices in the abc file
                    continue;
                }
                case COMMENT: //comment ::= space_or_tab* "%" text newline
                    continue; //do nothing with this
                default: // when abc_line ::= element+ end_of_line (lyric end_of_line)?
                    try {
                        voicesToLines = evaluateLine(child, header, voicesToLines, voice);
                    } catch(AssertionError e) {
                        throw new AssertionError("should never get here1");
                    }
            }
        }
        
        final Map<String, Music> voicesToMusic = new HashMap<>(); 
        for (String v : voicesToLines.keySet()) {
            final List<Music> lines = voicesToLines.get(v);
            Music currentMusic = lines.get(0);
            for (int i=1; i < lines.size(); i++) {
                currentMusic = Music.createConcat(currentMusic, lines.get(i));
            }
            voicesToMusic.put(v, currentMusic);
        }
        return new Body(voicesToMusic);
    }
}
