package karaoke.parser;

/**
 * Tests for the 
 * 
 * @author chessa
 *
 */
public class MusicParserTest {

    // TESTING STRATEGY FOR PARSING ABC FILE ACCORDING TO GRAMMAR:
    //
    //      invalid file paths:
    //              file does not exist
    //              file is not of '.abc' format
    //              file otherwise has opening error
    //
    //      valid file paths, but unparsable contents:
    //              header errors:
    //                  index number is not first field
    //                  title is not second field
    //                  key is not last field
    //                  multiple fields on same line
    //              key signature errors:
    //                  invalid sharp/flat/etc character
    //                  > 2 signature characters
    //                  invalid pairings and orderings: minor-major, flat-sharp, major-flat
    //              note errors:
    //                  invalid note character & out-of-range letter
    //                  any apostrophes/commas don't precede multiplicative length factor
    //              accidental errors:
    //                  invalid accidental character
    //                  >1 natural accidental
    //                  >2 sharps, flats
    //              rest errors:
    //                  rest has an accidental (disallowed)
    //              chord errors:
    //                  uneven brackets
    //                  chord contains: rest, tuplet
    //                  empty chord
    //              tuplet errors:
    //                  invalid (or none) tuplet number
    //                  tuplet contains rest 
    //              repeat errors:
    //                  alternate endings have invalid numbers
    //                  no end repeat bar
    //              multiple voice errors:
    //                  reappeared voice field does not match those from header
    //              lyric errors:
    //                  lyrics not preceded by 'w'
    //                  invalid lyric characters
    //                  bar repeated but lyrics aren't
    //                  underscore is before hyphen
    //
    //      valid file paths, parsable contents: 
    //      (OUTPUT: NO PARSE ERRORS, MANUALLY EXAMINE PARSETREE IS CORRECT, MANUALLY LISTEN WITH KARAOKE.PLAY())
    //              header:
    //                  only contains required fields
    //                  contains additional fields
    //                  > 1 voices
    //                  default values used when no M, L, Q, C field present:
    //                      meter = 4/4 
    //                      note length = 1/8, 1/16
    //                      tempo = 100 beats/min, test with both default and specified note lengths
    //                      composer = "Unknown"
    //                  M: C, C|
    //              key signature:
    //                  signature = sharp, flat, major, minor
    //                  2 signature characters (flat minor, sharp major etc)
    //              notes:
    //                  octave deviation: 0, +1, +2, -1, -2
    //              note lengths:
    //                  no mult. factor = default
    //                  mult. factor = full frac, frac w/ default denominator, frac w/ default numerator, whole number
    //                  mult. factor with apostrophe, with comma (octave deviation)
    //              accidentals:
    //                  accidental = sharp, flat, natural
    //                  2 sharps, flats
    //                  accidental that overrides previous accidental
    //              rests:
    //                  default length
    //                  mult. factor
    //              chords:
    //                  chord size = 1, >1 
    //                  chord contains note w/ accidental, mult. factor
    //              tuplets:
    //                  type = duplet, triplet, quadruplet
    //                  tuplet contains notes, chords, both
    //                  tuplet contains notes w/ same lengths, different lengths
    //              repeats:
    //                  beginning bar not included
    //                  alternate endings of same length, differnt length
    //              lyrics:
    //                  lyrics contain: -, _, mutliple underscores in row, *, ~, \-, |
    //                  double hyphen = empty syllable
    //                  fewer lyrics than notes (extra notes have no lyrics)
    //                  fewer notes than lyrics (extra lyrics ignored)
    //                  multiple voices have lyrics
    //                  repeated bar has lyrics

}
