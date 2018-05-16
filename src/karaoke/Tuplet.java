package karaoke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import karaoke.sound.SequencePlayer;

public class Tuplet implements Playable {
    
    public static final int DUPLET_NOTES = 2;
    public static final double DUPLET_QUOTIENT = 3.0/2;
    public static final double TRIPLET_QUOTIENT = 2.0/3;
    public static final double QUADRUPLET_QUOTIENT = 3.0/4;
    public static final int TRIPLET_NOTES = 3;
    public static final int QUADRUPLET_NOTES = 4;
    public static final int TIME_OF_NOTES = 3;
    
    enum Type{
        DUPLET, TRIPLET, QUADRUPLET
        
    }
    private final List<Chord> newChords = new ArrayList<Chord>();
    private final Type type;
    private final double duration;
    
    // AF(newChords, type, duration): A tuplet of type type and time length duration, where newChords are the chords
    //        in the tuplet, played in the order they appear in the list
    //
    // RI: 
    //      newChords.size() = 2, 3, 4
    //      duration > 0
    //
    // Safety from rep exposure:
    //      all fields private final, and never mutated after contructor
    //      all return types are immutable, rep is never exposed    
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - newChord, type, and duration are final
    //    -The newChords array is never exposed to a client.
    //    -The mutable rep (newChords) is only mutated in the constructor
    //      which is a synchronized, threadsafe method by default through synchronized locking
    
    /**
     * creates an instance of a Tuplet object
     * @param chords the notes in the tuplet
     */
    public Tuplet(List<Chord> chords) {
        double dur = 0;
        double multiplier;
        //determine type of tuplet {duplet, triplet, quadruplet}
        if(chords.size() == DUPLET_NOTES) {
            this.type = Type.DUPLET;
        }
        else if(chords.size() == TRIPLET_NOTES) {
            this.type = Type.TRIPLET;
        }
        else {
            type = Type.QUADRUPLET;
        }
      
        //determine correct duration of tuplet based on type
        if(type == Type.DUPLET) {
            multiplier = DUPLET_QUOTIENT;
        }
        else if(type == Type.TRIPLET) {
            multiplier = TRIPLET_QUOTIENT;
        }
        else {
            multiplier = QUADRUPLET_QUOTIENT;
        }
        
        
        //add notes of correct length into chord list
        for(Chord chord : chords) {
            double newDuration = chord.duration() * multiplier;
            Chord newChord = chord.copyChordNewDuration(newDuration, this.type);
            dur += newDuration;
            newChords.add(newChord);
        }
        
        this.duration = dur;
    }
    
    @Override
    public double duration() {
        return this.duration;
    }

    
    @Override
    public void play(SequencePlayer player, double startBeat) {
        
        double beginBeat = startBeat;
        for(Chord chord: newChords) {
            chord.play(player, beginBeat);
            beginBeat += chord.duration();
        }
    }
    
    @Override
    public boolean equals(Object that) {
        if (that instanceof Tuplet) {
            for (int i = 0; i < this.newChords.size(); i++) {
                if (! this.newChords.get(i).equals(((Tuplet) that).newChords.get(i))) {
                    return false;
                }
            } 
            return true;
        }
        return false;
    }
    
    @Override 
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < this.newChords.size(); i ++) {
            hash += this.newChords.get(i).hashCode();
        }
        return hash;
    }
    
//    @Override
//    public List<Measure> getMusic() {
//        return Arrays.asList(new Measure(Arrays.asList(this)));
//    }
//
//    public List<Double> getDurationList() {
//        // TODO Auto-generated method stub
//        return null;
//    }

}
