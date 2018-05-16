/**
 * 
 */
package karaoke;
import karaoke.sound.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chessa, mattbev, sophias
 * 
 * Chord represents a chord, of one or more notes, played simultaneously by one or more instruments.
 * A variant of Playable interface.
 *
 */
public class Chord implements Playable { 
    
    private static final int DUPLET_LENGTH = 2;
    private static final int TRIPLET_LENGTH = 3;
    private static final int QUADRUPLET_LENGTH = 4;
    
    private final List<Note> notes;
    
    // AF(notes): A chord where notes is a list of the notes of the chord (in the order they were in in the 
    //                   abc file)
    //
    // RI: notes.size > 0
    //     
    // Safety from Rep Exposure: 
    //      all fields private final
    //      immutable data type
    //      no mutators that directly expose the rep
    //      check rep checks that a chord always has 1+ note
    //
    // Thread Safety Argument:
    //      The class is threadsafe because it is immutable:
    //          notes is final 
    //          notes is mutable, but they are encapsulated in this object, not shared with any other object 
    //              or exposed to the client
    //          notes is composed of type Note, which is an immutable, threadsafe type
    
    /**
     * Make a chord, which is a combination of one or more notes
     * 
     * @param notes the list of notes that make up this chord
     */
    public Chord(List<Note> notes) {
        this.notes = new ArrayList<>(notes); 
        checkRep();
    }
    
    /**
     * checks that the rep is not broken
     */
    private void checkRep() {
        assert notes.size() > 0 : "a chord must contain at least one note";
        for (Note n : this.notes) {
            assert n != null : "Notes cannot be null";
        }
    }
    
    @Override
    public double duration() {
        checkRep();
        return this.notes.get(0).getDuration();
    }

    
    /**
     * Get this chord's notes
     * 
     * @return the notes that make up this chord
     */
    public List<Note> getNotes() {
        checkRep();
        return new ArrayList<>(this.notes); 
    }
    
    
    
    /**
     * Play this chord
     * @param player player producing the chord
     * @param startBeat beat at which the chord should play
     */
    @Override
    public void play(SequencePlayer player, double startBeat) {
        
        //iterate through every note in the chord and give it the same startBeat on which to play
        for(Note note: notes) {
            note.play(player, startBeat);
        }
        checkRep();
    }
    
    /**
     * Make a new chord which consists of the same notes and lyrics, but has a different duration depending on the Tuplet type t
     * 
     * @param duration  the new duration
     * @param t the tuplet type
     * @return a new Chord with a different duration depending on the tuplet
     */
    public Chord copyChordNewDuration(double duration, Tuplet.Type t) {        
        List<Note> notesCopy = new ArrayList<>();
        for (Note note : this.notes) {
            int denom = getDenominator(t);
            notesCopy.add(Note.createNote(note.getInstrument(), duration/denom, note.getPitch(), note.getAccidental()));
        }
        checkRep();
        return new Chord(notes);
        
    }
    
    /**
     * 
     * @param t the tuplet type
     * @return the number to divide the total duration by
     */
    private static int getDenominator(Tuplet.Type t) {
        switch (t) {
        
        case DUPLET: {
            return DUPLET_LENGTH;
        }
        case TRIPLET: {
            return TRIPLET_LENGTH;
        }
        case QUADRUPLET: {
            return QUADRUPLET_LENGTH;
        }
        default: throw new AssertionError("should never get here");
        }
    }
    
    @Override
    public boolean equals(Object that) {
        checkRep();
        return (that instanceof Chord) && this.sameChord((Chord) that);
    }
    
    private boolean sameChord(Chord that) {
        for (int i = 0; i < this.notes.size(); i++) {
            if (! this.notes.get(i).equals(that.getNotes().get(i))) {
                checkRep();
                return false;
            }
        } 
        checkRep();
        return true;
    }
    
    @Override 
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < this.notes.size(); i ++) {
            hash += this.notes.get(i).hashCode();
        }
        checkRep();
        return hash;
    }
    
    @Override
    public String toString() {
        if (this.notes.size() == 1) {
            return this.notes.get(0).toString();
        }
        String chord = "[";
        for (Note n : this.notes) {
            chord += n.toString();
        }
        return chord + "]";
    }

//    @Override
//    public List<Measure> getMusic() {
//        return Arrays.asList(new Measure(Arrays.asList(this)));
//    }

    public List<Double> getDurationList() {
        // TODO Auto-generated method stub
        return null;
    }

}
