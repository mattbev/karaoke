/**
 * 
 */
package karaoke.music;
import karaoke.sound.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chessa, mattbev, sophias
 * 
 * Chord represents a chord, of one or more notes, played simultaneously by one or more instruments.
 * A variant of Playable interface.
 *
 */
public class Chord implements Playable{ 
    
    private List<Note> notes = new ArrayList<Note>();
    private  List<Lyric> lyrics = new ArrayList<Lyric>();
    
    // AF(notes, lyric): A chord where notes is a list of the notes of the chord (in the order they were in in the 
    //                   abc file) and lyrics is the list of lyric(s) to be streamed during this chord
    //
    // RI: notes.size >= 1
    //     
    // Safety from Rep Exposure:
    //
    // Thread Safety Argument:
    //
    
    /**
     * Make a chord, which is a combination of one or more notes
     * 
     * @param notes the list of notes that make up this chord
     * @param lyrics the lyric to be streamed during this chord
     */
    public Chord(List<Note> notes, List<Lyric> lyrics) {
        this.notes = new ArrayList<>(notes); 
        this.lyrics = lyrics;
    }
    
    
    @Override
    public double getDuration() {
        return this.notes.get(0).getDuration();
    }

    
    /**
     * Get this chord's notes
     * 
     * @return the notes that make up this chord
     */
    public List<Note> getNotes() {
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
    }
    
    /**
     * Make a new chord which consists of the same notes and lyrics, but has a different duration depending on the Tuplet type t
     * 
     * @param duration  the new duration
     * @param t the tuplet type
     * @return a new Chord with a different duration depending on the tuplet
     */
    public Chord copyChordNewDuration(double duration, Tuplet.Type t) {
        
        List<Lyric> lyricsCopy = new ArrayList<>();
        for (Lyric lyric : this.lyrics) {
            lyricsCopy.add(lyric.createLyricCopy());
        }
        
        List<Note> notesCopy = new ArrayList<>();
        for (Note note : this.notes) {
            int denom = getDenominator(t);
            notesCopy.add(Note.createNote(note.getInstrument(), duration/denom, note.getPitch(), note.getAccidental()));
        }
        
        return new Chord(notes, lyrics);
        
    }
    
    /**
     * 
     * @param t the tuplet type
     * @return the number to divide the total duration by
     */
    private static int getDenominator(Tuplet.Type t) {
        switch (t) {
        
        case DUPLET: {
            return 2;
        }
        case TRIPLET: {
            return 3;
        }
        case QUADRUPLET: {
            return 4;
        }
        default: throw new AssertionError("should never get here");
        }
    }
    
    /**
     * Create a copy of the lyrics list
     * 
     * @return a copy of the lyrics list, with copied lyrics as well
     */
    public List<Lyric> getLyrics() {
        List<Lyric> lyricsCopy = new ArrayList<>();
        for (Lyric l : this.lyrics) {
            lyricsCopy.add(l.createLyricCopy());
        } 
        return lyricsCopy;
    } 
    
    @Override
    public Lyric getLyric() {
        String lyric = "";
        for (Lyric l : this.lyrics) {
            lyric += l;
        } 
        return new Lyric(lyric);
    } 
}
