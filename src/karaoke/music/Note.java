package karaoke.music;


import karaoke.sound.*;

/**
 * @author chessa, mattbev, sophias
 * 
 * A mutable ADT to represent a note of music played by an instrument
 *
 */
public class Note {

    private final Instrument instrument;
    private final double magnitude;
    private final Pitch pitch;
    
    /**
     * 
     * @param i the instrument that plays this note
     * @param magnitude the magnitude of this note (i.e. 1/4 = quarter, 1 = whole, etc)
     * @param p the pitch of this note
     */
    public Note(Instrument i, double magnitude, Pitch p) {
        this.instrument = i;
        this.magnitude = magnitude;
        this.pitch = p;
    }
    
    
    /**
     * Get the duration of this note
     * 
     * @return the duration of this note
     */
    public double getDuration() {
        
        // not sure if correct for now, does duration == magnitude in music?
        return this.magnitude; 
    }
    
    /**
     * Return the pitch of this note
     * 
     * @return the pitch of the note
     */
    public Pitch getPitch() {
        return this.pitch;
    }
    
    /**
     * Return the instrument this note is played by
     * 
     * @return this note's instrument
     */
    public Instrument getInstrument() {
        return this.instrument;
    }
    
    
}