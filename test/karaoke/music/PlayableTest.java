package karaoke.music;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import karaoke.Lyric;
import karaoke.Note;
import karaoke.Playable;
import karaoke.sound.Instrument;
import karaoke.sound.Pitch;

/**
 * test suite for the playable interface
 * @author mattj
 */
public class PlayableTest {
    // testing strategy
    //
    // testing: createChord(List<Note> notes, List<Lyric> lyrics)
    // partition the inputs as follows:
    //   notes: size 1, size >1
    //   lyrics: size 1, size >1
    // partition the outputs as follows:
    //   number of notes in chord: 1, >1
    //   number of lyrics for chord: 1, >1
    //
    // testing: createRest(double duration)
    // partition the inputs as follows:
    //   duration = 0
    //   duration > 0
    // partition the outputs as follows:
    //   rest of 0 beats
    //   rest of > 0 beats
    //
    // testing: getDuration()
    // partition the outputs as follows:
    //   >0 seconds
    //
    // testing play(SequencePlayer player, double startBeat)
    // partition the inputs as follows:
    //   player that has: positive beatsPerMinute,
    //                    positive ticksPerBeat
    //   startBeat: 0, >0
    // partition the outputs as follows:
    //   manually listen for correct sound output
    
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //testing createChord...
    
    //tests that a correct chord was created for a single note chord and single lyric
    @Test
    public void testCreateChordOneNoteOneLyric() {
        List<Note> notes = Arrays.asList(new Note(Instrument.ACCORDION, .25, Pitch.MIDDLE_C, ""));
        List<Lyric> lyrics = Arrays.asList(new Lyric("hello"));
        Playable playable = Playable.createChord(notes, lyrics);
        assert playable.duration() == .25;
        assert playable.getLyric().equals(new Lyric("hello"));
    }
    
    //tests that a correct chord was created for a single note chord and multiple lyrics
    @Test
    public void testCreateChordOneNoteMultipleLyrics() {
        List<Note> notes = Arrays.asList(new Note(Instrument.ACCORDION, .25, Pitch.MIDDLE_C, ""));
        List<Lyric> lyrics = Arrays.asList(new Lyric("hello"), new Lyric("goodbye"));
        Playable playable = Playable.createChord(notes, lyrics);
        assert playable.duration() == .25;
        assert playable.getLyric().equals(new Lyric("hellogoodbye"));
    }
    
    //tests that a correct chord was created for a multiple note chord and single lyric
    @Test
    public void testCreateChordMultipleNotesOneLyric() {
        List<Note> notes = Arrays.asList(new Note(Instrument.ACCORDION, .50, Pitch.MIDDLE_C, ""),
                new Note(Instrument.ACCORDION, .25, Pitch.MIDDLE_C.transpose(Pitch.OCTAVE), ""));
        List<Lyric> lyrics = Arrays.asList(new Lyric("good bye"));
        Playable playable = Playable.createChord(notes, lyrics);
        assert playable.duration() == .50;
        assert playable.getLyric().equals(new Lyric("good bye"));
    }
    
    //tests that a correct chord was created for a multiple note chord and multiple lyrics
    @Test
    public void testCreateChordMultipleNotesMultipleLyrics() {
        List<Note> notes = Arrays.asList(new Note(Instrument.ACCORDION, .50, Pitch.MIDDLE_C, ""),
                new Note(Instrument.ACCORDION, .25, Pitch.MIDDLE_C.transpose(Pitch.OCTAVE), ""));
        List<Lyric> lyrics = Arrays.asList(new Lyric("hello"), new Lyric("goodbye"));
        Playable playable = Playable.createChord(notes, lyrics);
        assert playable.duration() == .50;
        assert playable.getLyric().equals(new Lyric("hellogoodbye"));
    }
    
    
    //testing createRest...
    
    //tests that a correct rest was made for a rest of duration 0
    @Test
    public void testCreateRestZeroDuration() {
        Playable playable = Playable.createRest(0);
        assert playable.duration() == 0;
        assert playable.getLyric().equals(Lyric.emptyLyric());
    }
    
    //tests that a correct rest was made for a rest of duration >0
    @Test
    public void testCreateRestNonZeroDuration() {
        Playable playable = Playable.createRest(.5);
        assert playable.duration() == .5;
        assert playable.getLyric().equals(Lyric.emptyLyric());
    }
    
    
    //testing getDuration...
 
    
    //tests that a duration of 0 was returned for a playable with non zero magnitude
    @Test
    public void testGetDurationNonZeroSeconds() {
        Playable playable = Playable.createRest(5);
        assert playable.duration() == 5;
    }
    
    
    //testing play...
    
//    //manually listening for correct playing of a playable at startBeat 0
//    @Test
//    public void testPlayStartZero() throws MidiUnavailableException, InvalidMidiDataException {
//        SequencePlayer player = new MidiSequencePlayer(140, 64);
//        List<Note> notes = Arrays.asList(new Note(Instrument.ALTO_SAX, 3, Pitch.MIDDLE_C, ""));
//        List<Lyric> lyrics = Arrays.asList(Lyric.emptyLyric());
//        Playable playable = Playable.createChord(notes, lyrics);
//        playable.play(player, 0);
//        //manual test, listen for a 3 beat middle C
//    }
    
//  //manually listening for correct playing of a playable at startBeat > 0
//    @Test
//    public void testPlayStartNonZero() throws MidiUnavailableException, InvalidMidiDataException {
//        SequencePlayer player = new MidiSequencePlayer(140, 64);
//        List<Note> notes = Arrays.asList(new Note(Instrument.ALTO_SAX, 3, Pitch.MIDDLE_C, ""));
//        List<Lyric> lyrics = Arrays.asList(Lyric.emptyLyric());
//        Playable playable = Playable.createChord(notes, lyrics);
//        playable.play(player, 2);
//        //manual test, listen for a 1 beat middle C
//    }
}
