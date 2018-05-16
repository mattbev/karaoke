package parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.Header;

public class HeaderParser {

    private static enum HeaderGrammar {
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
    
    private static Parser<HeaderGrammar> parser = makeParser();
    
    /**
     * Compile the grammar into a parser.
     * 
     * @return parser for the grammar
     * @throws RuntimeException if grammar file can't be read or has syntax errors
     */
    private static Parser<HeaderGrammar> makeParser() {
        try {
            // read the grammar as a file, relative to the project root.
            final File grammarFile = new File("src/parser/Abc.g");
            return Parser.compile(grammarFile, HeaderGrammar.ABC_HEADER);
        } catch (IOException e) {
            throw new RuntimeException("can't read the grammar file", e);
        } catch (UnableToParseException e) {
            throw new RuntimeException("the grammar has a syntax error", e);
        }
    }
    
    /**
     * Parse a string into an expression.
     * @param string string to parse
     * @return Header parsed from the string
     * @throws UnableToParseException if the string doesn't match the ABC grammar
     */
    public static Header parse(final String string) throws UnableToParseException {
        // parse the example into a parse tree
        final ParseTree<HeaderGrammar> parseTree = parser.parse(string);
        
        //for visuals
//        System.out.println("parse tree " + parseTree);
//        Visualizer.showInBrowser(parseTree);
        
        //make AST from parse tree
        final Header header = makeAbstractSyntaxTree(parseTree);
        return header;
    }
    
    /**
    * Convert a parse tree into an abstract syntax tree.
    * 
    * @param parseTree constructed according to the grammar in ABC.g
    * @return abstract syntax tree corresponding to parseTree
    */
   private static Header makeAbstractSyntaxTree(final ParseTree<HeaderGrammar> parseTree) {
       /* header */
       //abc_header ::= field_number comment* field_title other_fields* field_key
   
       final Map<Character,String> headerMap = new HashMap<>();
       for (ParseTree<HeaderGrammar> childParseTree : parseTree.children()) {
           switch(childParseTree.name()) {   
           case FIELD_NUMBER: //field_number ::= "X:" digit+ end_of_line
               {
                   final List<ParseTree<HeaderGrammar>> children = childParseTree.children();
                   String index = "";
                   for (int i=0; i<children.size()-1; i++) {
                       index += children.get(i).text();
                   }
                   headerMap.put('X', index);
                   continue;
               }
           case FIELD_TITLE: //field_title ::= "T:" text end_of_line
               {
                   final String title = childParseTree.children().get(0).text();
                   headerMap.put('T', title);
                   continue;
               }
           case FIELD_KEY: //field_key ::= "K:" key end_of_line
               {
                   final String key = childParseTree.children().get(0).text();
                   headerMap.put('K', key);
                   continue;
               }
           case OTHER_FIELDS: //other_fields ::= field_composer | field_default_length | field_meter | field_tempo | field_voice | comment
               {
                  ParseTree<HeaderGrammar> subParseTree = childParseTree.children().get(0);
                  switch(subParseTree.name()) {
                  case FIELD_COMPOSER: //field_composer ::= "C:" text end_of_line
                       {
                           final String composer = subParseTree.children().get(0).text();
                           headerMap.put('C', composer);
                           continue;
                       }
                  case FIELD_DEFAULT_LENGTH: //field_default_length ::= "L:" note_length end_of_line
                       {
                           final String noteLength = subParseTree.children().get(0).text();
                           headerMap.put('L', noteLength);
                           continue;
                       }    
                  case FIELD_METER: //field_meter ::= "M:" meter end_of_line
                      {
                          final String meter = subParseTree.children().get(0).text();
                          headerMap.put('M', meter);
                          continue;
                      }
                  case FIELD_TEMPO: //field_tempo ::= "Q:" tempo end_of_line
                      {
                          final String tempo = subParseTree.children().get(0).text(); 
                          headerMap.put('Q', tempo);
                          continue;
                      }
                  case FIELD_VOICE: //field_voice ::= "V:" text end_of_line
                      {
                          final String voices = subParseTree.children().get(0).text();
                          headerMap.put('V', voices);
                          continue;
                      }
                  default:
                      throw new AssertionError("should never get here");
                  }
               }
           
           default:
               throw new AssertionError("should never get here");
           }
       }
       return new Header(headerMap);
   }  
}
