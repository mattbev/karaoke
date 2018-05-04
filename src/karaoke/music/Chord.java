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
    private final Lyric lyric;
    
    
    /**
     * Make a chord, which is a combination of one or more notes
     * 
     * @param notes the list of notes that make up this chord
     * @param lyric the lyric to be streamed during this chord
     */
    public Chord(List<Note> notes, Lyric lyric) {
        this.notes = notes; // not correct, we will want to make a defensive copy of the list
        this.lyric = lyric;
    }
    @Override
    public double getDuration() {
        return 0; // not correct, we will probably want to return the max duration of all notes
    }


    @Override
    public Lyric getLyric() {
        return lyric;
    }
    
    /**
     * Get this chord's notes
     * 
     * @return the notes that make up this chord
     */
    public List<Note> getNotes() {
        return this.notes; // obviously not correct as this is not defensive, just placeholder
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
        return this.lyric.getText();
    }
}

