package parser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.junit.Test;

import edu.mit.eecs.parserlib.UnableToParseException;
import karaoke.Karaoke;
import karaoke.sound.MidiSequencePlayer;
import karaoke.sound.SequencePlayer;

/**
 * Tests for the 
 * 
 * @author chessa
 *
 */
public class MusicParserTest {

    // TESTING STRATEGY FOR PARSING ABC FILE ACCORDING TO GRAMMAR:
    //
    //      valid file paths, parsable files: 
    //      (OUTPUT: NO PARSE ERRORS, MANUALLY EXAMINE PARSETREE IS CORRECT)
//     //              header:
    //                  only contains required fields
//     //                  contains additional fields
    //                  > 1 voices
    //                  default values used when no M, L, Q, C field present:
    //                      meter = 4/4 
    //                      note length = 1/8, 1/16
    //                      tempo = 100 beats/min, test with both default and specified note lengths
//     //                      composer = "Unknown"
    //                  M: C, C|
//     //              key signature:
//     //                  just key, no extra signature
    //                  signature = sharp, flat, major, minor
    //                  2 signature characters (flat minor, sharp major etc)
//     //              notes:
//*    //                  octave deviation: 0, +1, +2, -1, -2
//*    //              note lengths:
//     //                  no mult. factor = default
//     //                  mult. factor = complete frac, frac w/ default denominator, frac w/ default numerator, whole number
    //                  mult. factor with apostrophe, with comma (octave deviation)
    //              accidentals:
//*    //                  accidental = sharp, flat, natural
    //                  2 sharps, flats
//     //              rests:
//*    //                  length = default length, mult. factor
//     //              chords:
//*    //                  chord size = 1, >1 
//     //                  chord contains note w/ accidental, mult. factor
//     //              tuplets:
//*    //                  type = duplet, triplet, quadruplet
//*    //                  tuplet contains notes, chords, both
//*    //                  tuplet contains notes w/ same lengths, different lengths
//     //              repeats:
    //                  beginning bar not included
    //                  alternate endings of same length, differnt length
//     //              lyrics:
//*    //                  lyrics contain: -, _, mutliple underscores in row, *, ~, \-, |, -_
    //                  double hyphen = empty syllable
    //                  fewer lyrics than notes (extra notes have no lyrics)
    //                  fewer notes than lyrics (extra lyrics ignored)
    //                  multiple voices have lyrics
    //                  repeated bar has lyrics
    //              multiple voices:
    //                  interleaved, not interleaved

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //covers: header contains additional fields
    //        composer is default unknown
    //        note: octave deviation 0, +1
    //        tuplet: duplet, contains notes
    //        note lengths: mult. factor is whole #
    //        rests with mult. factor
    //        lyrics contain -, _, multiple underscores
    @Test
    public void testCorrectPiece3() {
        try {
            File f = new File("samples/piece3.abc");
            List<String> s = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            String contents = String.join("\n", s);
            Karaoke karaoke = KaraokeParser.parse(contents);
        } catch (Exception e) {
            assert false: "file should have parsed correctly";
        }
    }
    
    //covers: header contains additional fields
    //        composer is default unknown
    //        note: octave deviation 0, +1
    //        note length is default
    //        tuplet: triplet, contains notes
    //        note lengths: mult. factor is whole #, frac w/ no denom, frac w/ no numerator, complete frac
    @Test
    public void testCorrectPiece1() {
        try {
            File f = new File("samples/piece1.abc");
            List<String> s = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            String contents = String.join("\n", s);
            Karaoke karaoke = KaraokeParser.parse(contents);
        } catch (Exception e) {
            assert false: "file should have parsed correctly";
        }
    }
    
    //covers: header contains additional fields
    //        composer is default unknown
    //        note: octave deviation 0, +1
    //        note length is default
    //        chord.size > 1
    //        chords contain notes with accidentals, mult factors
    //        tuplet: triplet, contains notes
    //        note lengths: mult. factor is whole #, frac w/ no denom, frac w/ no numerator, complete frac
    @Test
    public void testCorrectPiece2() {
        try {
            File f = new File("samples/piece2.abc");
            List<String> s = Files.readAllLines(f.toPath(), StandardCharsets.UTF_8);
            String contents = String.join("\n", s);
            Karaoke karaoke = KaraokeParser.parse(contents);
        } catch (Exception e) {
            assert false: "file should have parsed correctly";
        }
    }
    
}
