package karaoke;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.sun.net.httpserver.HttpServer;

import karaoke.server.WebServer;
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
    private final LyricLine lyricLine;

    
    // AF(newChords, type, duration, lyricLine): A tuplet of type <type> and time length <duration>, where newChords are the chords
    //                                           in the tuplet, played in the order they appear in the list, and the line of lyrics
    //                                           shown while the tuplet plays is lyricLine with the lyric corresponding to the tuplet
    //                                           bolded.
    //                       
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
     * @param lyricLine line of lyrics for the tuplet the line 
     *        falls on with the tuplet's lyric bolded
     */
    public Tuplet(List<Chord> chords, LyricLine lyricLine) {
        this.lyricLine = lyricLine;
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
    
    public List<Chord> getComponents() {
        return new ArrayList<>(this.newChords);
    }
    
    @Override
    public double duration() {
        return this.duration;
    }
    
    @Override
    public Playable copyWithNewLyric( LyricLine l) {
        List<Chord> chords = this.getChordListCopy();
        return new Tuplet(chords, l);
    }

    
    @Override
    public void play(SequencePlayer player, double startBeat, WebServer server) {
        
        double beginBeat = startBeat;
        for(Chord chord: newChords) {
            chord.play(player, beginBeat, server);
            beginBeat += chord.duration();
            Consumer<Double> c1 = i -> {
                try {
                    server.putInBlockingQueue(lyricLine);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            player.addEvent(startBeat , c1 );
            
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
    
    /**
     * gets the chords that make up the tuplet
     * @return a list of the chords in the tuplet
     */
    public List<Chord> getChordListCopy(){
        List<Chord> chordList = new ArrayList<Chord>();
        for(Chord chord: this.newChords) {
            chordList.add(chord);
        }
        return chordList;
    }

    @Override
    public LyricLine getLyricLine() {
        return this.lyricLine.copy();
    }

}
