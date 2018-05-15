package karaoke.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mit.eecs.parserlib.ParseTree;
import karaoke.Karaoke;
import karaoke.LyricLine;
import karaoke.parser.KaraokeParser.ABCGrammar;

public class BodyParser {
    
    private static enum BodyGrammar {
        // body
        ABC_BODY, ABC_LINE, ELEMENT, // spaces and tabs
        NOTE_ELEMENT, NOTE, PITCH, NOTE_LENGTH, NOTE_LENGTH_STRICT, // notes
        REST_ELEMENT, // rests
        TUPLET_ELEMENT, TUPLET_SPEC, // tuplets
        CHORD, // chords
        MIDDLE_OF_BODY_FIELD, LYRIC, LYRICAL_ELEMENT, // voice
    }
    
    private static Body makeAbstractSyntaxTree(final ParseTree<ABCGrammar> parseTree) {
        
        
        final List<ParseTree<ABCGrammar>> children = parseTree.children();
        Map<String, LyricLine> voicesLyrics = new HashMap<>();
        Map<String, Karaoke> voicesKaraoke = new HashMap<>();
        case ABC_HEADER:
            
        Header header = HeaderParser.makeAbstractSyntaxTree(children.get(0));
            ABCGrammar nonterminal = children.get(0).name();
            switch(nonterminal) {
            case FIELD_VOICE:
                int j = 0;
                // first element 
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
            }
            if (nonterminal.equals(ABCGrammar.FIELD_VOICE)) {
                int j = 0;
                // first element 
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
                
                
            } else if (nonterminal.equals(ABCGrammar.LYRIC)) {
                
            }
    }
}
