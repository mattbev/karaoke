package karaoke.music;


import karaoke.sound.*;

/**
 * @author chessa, mattbev, sophias
 * 
 * A immutable ADT to represent a note of music played by an instrument
 *
 */
public class Note {

    private final Instrument instrument;
    private final double magnitude;
    private final Pitch pitch;
    private final String accidental;
    
    /**
     * 
     * @param i the instrument that plays this note
     * @param magnitude the magnitude of this note (i.e. 1/4 = quarter, 1 = whole, etc)
     * @param p the pitch of this note
     * @param accidental the accidental of the note
     */
    public Note(Instrument i, double magnitude, Pitch p, String accidental) {
        this.instrument = i;
        this.magnitude = magnitude;
        this.pitch = p;
        this.accidental = accidental;
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
     * Return the accidental of this note
     * 
     * @return the accidental of the note
     */
    public String getAccidental() {
        return this.accidental;
    }
    
    /**
     * Return the instrument this note is played by
     * 
     * @return this note's instrument
     */
    public Instrument getInstrument() {
        return this.instrument;
    }
    
    
    /**
     * Play this note
     * @param player player producing the note
     * @param startBeat beat at which the note should play
     */
    public void play(SequencePlayer player, double startBeat) {
        player.addNote(instrument, pitch, startBeat, magnitude);
        
    }
    
}
