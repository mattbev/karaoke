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
        this.notes = notes; // not correct, we will want to make defensive copies of the lists
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
        return this.notes; // not correct as this is not defensive, just placeholder
    }
    
    /**
     * Return the instruments played in this chord
     * 
     * @return the list of instruments in this chord
     */
    public List<Instrument> getInstruments() {
        return null;
    }
    
    @Override
    public String getLyricText() {
        return null;
    }
}

