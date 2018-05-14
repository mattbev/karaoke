package karaoke.music;

import static org.junit.Assert.assertEquals;


import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import java.util.ArrayList;

import org.junit.Test;

import karaoke.Chord;
import karaoke.Concat;
import karaoke.Karaoke;
import karaoke.Lyric;
import karaoke.Measure;
import karaoke.Note;
import karaoke.Playable;
import karaoke.Rest;
import karaoke.sound.Instrument;
import karaoke.sound.MidiSequencePlayer;
import karaoke.sound.Pitch;
import karaoke.sound.SequencePlayer;



/**
 * Tests for instance methods of Karaoke
 *
 */
public class KaraokeTest {
    /**
     * Testing Strategy for createMeasure():
     * 
     * Partition the input as follows:
     * Components: size = 1, > 1
     * Partitioning: Cover each part
     * 
     * Testing Strategy for createConcat():
     * 
     * Partition the input as follows:
     * firstKaraoke: measure with components size = 1, > 1
     * secondKaraoke: measure with components size = 1, > 1
     * Partitioning: Cover each part
     * 
     * Testing Strategy for duration():
     * 
     * Partition the output as follows:
     * duration = 1, > 1
     * Partitioning: Cover each part
     * 
     * Testing Strategy for play():
     * manual testing:
     * -listen for a chord of low F# and high E eighth notes
     * -listen for a triplet of low G, high E, high G quarter notes
     * 
     * 
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    //TEST createMeasure()


    
    //Covers: 
    //Components size = 1
    
    @Test
    public void testCreateMeasureComponentsSizeOne() {
        
        //create components list
        List<Playable> components = new ArrayList<Playable>();
        
        //create new note to add to a chord to add to components
        Note componentNote = new Note(Instrument.BRIGHT_PIANO, 4.0, Pitch.MIDDLE_C, "=");
        List<Note> notes = new ArrayList<Note>();
        notes.add(componentNote);
        List<Lyric> lyrics = new ArrayList<Lyric>();   //no lyrics
        Chord testChord = new Chord(notes, lyrics);
        
        components.add(testChord);
        
        Measure newMeasure = Karaoke.createMeasure(components);
        double expectedDuration = 4.0;
        assertEquals("expect Measure duration to be one",expectedDuration, newMeasure.duration(), .0001);
    }
    
    //Covers: 
    //Components size > 1
    
    @Test
    public void testCreateMeasureComponentsSizeGreaterThanOne() {
        
        //create components list
        List<Playable> components = new ArrayList<Playable>();
        
        //create new note to add to a chord to add to components
        Note componentNote = new Note(Instrument.BRIGHT_PIANO, 4.0, Pitch.MIDDLE_C, "=");
        List<Note> notes = new ArrayList<Note>();
        notes.add(componentNote);
        List<Lyric> lyrics = new ArrayList<Lyric>();   //no lyrics
        Chord testChord = new Chord(notes, lyrics);
        
        Rest componentRest = new Rest(2.0);
        
        components.add(testChord);
        components.add(componentRest);
        
        Measure newMeasure = Karaoke.createMeasure(components);
        double expectedDuration = 6.0;
        assertEquals("expect Measure duration to be one",expectedDuration, newMeasure.duration(), .0001);
    }
    
    
    //TEST duration()
    
    
    //Covers:
    //duration = 1
    
    @Test
    public void testDurationEqualsOne() {
        
        //create components list
        List<Playable> components = new ArrayList<Playable>();
        
        //create new note to add to a chord to add to components
        Note componentNote = new Note(Instrument.PIANO, 1.0, Pitch.MIDDLE_C, "=");
        List<Note> notes = new ArrayList<Note>();
        notes.add(componentNote);
        List<Lyric> lyrics = new ArrayList<Lyric>();   //no lyrics
        Chord testChord = new Chord(notes, lyrics);
        
        components.add(testChord);
        
        Measure newMeasure = Karaoke.createMeasure(components);
        double expectedDuration = 1.0;
        assertEquals("expect duration to be one",expectedDuration, newMeasure.duration(), .0001);
    }
    
    
    //Covers:
    //Duration > 1
    @Test
    public void testDurationGreaterThanOne() {
        
        //create components list
        List<Playable> components = new ArrayList<Playable>();
        
        //create new note to add to a chord to add to components
        Note componentNote = new Note(Instrument.BRIGHT_PIANO, 2.0, Pitch.MIDDLE_C, "=");
        List<Note> notes = new ArrayList<Note>();
        notes.add(componentNote);
        List<Lyric> lyrics = new ArrayList<Lyric>();   //no lyrics
        Chord testChord = new Chord(notes, lyrics);
        
        Rest componentRest = new Rest(2.0);
        
        components.add(testChord);
        components.add(componentRest);
        
        Measure newMeasure = Karaoke.createMeasure(components);
        double expectedDuration = 4.0;
        assertEquals("expect duration to be four",expectedDuration, newMeasure.duration(), .0001);
    }
    
    
    
    //TEST play()
    
    
    //Covers:
    //Manual test: listen for a chord of low F# and high E eighth notes
    @Test
    
    public void testPlayManual1() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 200; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
      
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, .5);
        player.addNote(piano, new Pitch('F').transpose(1), startBeat, .5);
    }
    
    
    //Covers:
    //Manual test: listen for a triplet of low G, high E, high G quarter notes
    @Test
    public void testPlayManual2() throws MidiUnavailableException, InvalidMidiDataException {
        Instrument piano = Instrument.PIANO;

        // create a new player
        final int beatsPerMinute = 200; // a beat is a quarter note, so this is 120 quarter notes per minute
        final int ticksPerBeat = 64; // allows up to 1/64-beat notes to be played with fidelity
        SequencePlayer player = new MidiSequencePlayer(beatsPerMinute, ticksPerBeat);
        
        // addNote(instr, pitch, startBeat, numBeats) schedules a note with pitch value 'pitch'
        // played by 'instr' starting at 'startBeat' to be played for 'numBeats' beats.
        
        double startBeat = 0;
        
        player.addNote(piano, new Pitch('G'), startBeat, 2.0/3);       
        startBeat += 2.0/3;
        player.addNote(piano, new Pitch('E').transpose(Pitch.OCTAVE), startBeat, 2.0/3);        
        startBeat += 2.0/3;
        player.addNote(piano, new Pitch('G').transpose(Pitch.OCTAVE), startBeat, 2.0/3);        
        startBeat += 2.0/3;
    }
    
    //TEST createConcat()


    
    //Covers: 
    //firstKaraoke: measure with components size = 1
    //secondKaraoke: measure with components size > 1
    
    @Test
    public void testConcatComponentsOneComponentsGreaterThanOne() {
      //create components list
        List<Playable> components1 = new ArrayList<Playable>();
        
        //create new note to add to a chord to add to components
        Note componentNote1 = new Note(Instrument.BRIGHT_PIANO, 2.0, Pitch.MIDDLE_C, "=");
        List<Note> notes1 = new ArrayList<Note>();
        notes1.add(componentNote1);
        List<Lyric> lyrics1 = new ArrayList<Lyric>();   //no lyrics
        Chord testChord1 = new Chord(notes1, lyrics1);
        
        Rest componentRest = new Rest(2.0);
        
        components1.add(testChord1);
        components1.add(componentRest);
        
        Measure newMeasure1 = Karaoke.createMeasure(components1);
        
      //create components list
        List<Playable> components2 = new ArrayList<Playable>();
        
        //create new note to add to a chord to add to components
        Note componentNote2 = new Note(Instrument.PIANO, 1.0, Pitch.MIDDLE_C, "=");
        List<Note> notes2 = new ArrayList<Note>();
        notes2.add(componentNote2);
        List<Lyric> lyrics2 = new ArrayList<Lyric>();   //no lyrics
        Chord testChord2 = new Chord(notes2, lyrics2);
        
        components2.add(testChord2);
        
        Measure newMeasure2 = Karaoke.createMeasure(components2);
        
        Concat concatTest = new Concat(newMeasure1, newMeasure2);
        
        double expectedDuration = 5;
        assertEquals("expect concat duration to be five",expectedDuration, concatTest.duration(), .0001);
    }
    
   
    
   

}
