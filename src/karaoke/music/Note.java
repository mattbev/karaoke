package karaoke.music;


import karaoke.sound.*;

/**
 * @author chessa, mattbev, sophias
 * 
 * An immutable ADT to represent a note of music played by an instrument
 *
 */
public class Note {

    private final Instrument instrument;
    private final double duration;
    private final Pitch pitch;
    private final String accidental;
    
    // AF(instrument, duration, pitch, accidental): A musical note of pitch pitch, with accidental accidental, played on 
    //      instrument for the amoutn of time held in duration
    //
    // Rep invariant:
    //      duration > 0
    //
    // Safety from rep exposure:
    //      all fields private and final
    //      all return types immutable, rep never exposed
    // Thread Safety Argument:
    //      immutable class:
    //      all fields private final and immutable (so no beneficent mutation), all return types immutable
    
    /**
     * 
     * @param i the instrument that plays this note
     * @param duration the duration of this note (i.e. 1/4 = quarter, 1 = whole, etc)
     * @param p the pitch of this note
     * @param accidental the accidental of the note
     */
    public Note(Instrument i, double duration, Pitch p, String accidental) {
        this.instrument = i;
        this.duration = duration;
        this.pitch = p;
        this.accidental = accidental;
        checkRep();
    }
    
    /**
     * assert stated and implied RI
     */
    private void checkRep() {
        assert instrument != null : "instrument can't be null";
        assert pitch != null : "pitch can't be null";
        assert accidental != null : "accidental can't be null";
        assert duration > 0;
    }
    
    /**
     * Get the duration of this note
     * 
     * @return the duration of this note
     */
    public double getDuration() {
        checkRep();
        return this.duration; 
    }
    
    /**
     * Return the pitch of this note
     * 
     * @return the pitch of the note
     */
    public Pitch getPitch() {
        checkRep();
        return this.pitch;
    }
    
    /**
     * Return the accidental of this note
     * 
     * @return the accidental of the note
     */
    public String getAccidental() {
        checkRep();
        return this.accidental;
    }
    
    /**
     * Return the instrument this note is played by
     * 
     * @return this note's instrument
     */
    public Instrument getInstrument() {
        checkRep();
        return this.instrument;
    }
    
    
    /**
     * Play this note
     * @param player player producing the note
     * @param startBeat beat at which the note should play
     */
    public void play(SequencePlayer player, double startBeat) {
        player.addNote(instrument, pitch, startBeat, duration);
        checkRep();
        
    }
    
    /**
     * 
     * @param i the instrument the note is played by
     * @param magnitude the duration of the note
     * @param p the pitch of the note
     * @param accidental the accidental of the note
     * @return a new note of the given magnitude, pitch, and accidental played by the instrument i
     */
    public static Note createNote(Instrument i, double magnitude, Pitch p, String accidental) {
        return new Note(i, magnitude, p, accidental);
    }
    
    @Override
    public boolean equals(Object that) {
        checkRep();
        return (that instanceof Note) && this.sameNote((Note) that);
    }
    
    private boolean sameNote(Note that) {
        checkRep();
        return this.getAccidental().equals(that.getAccidental()) 
                && this.getPitch().equals(that.getPitch())
                && this.getDuration() == that.getDuration();
    }
    
    @Override 
    public int hashCode() {
        checkRep();
        return this.pitch.hashCode() + this.accidental.hashCode() + (int) this.getDuration();
    }
    
    @Override
    public String toString() {
        return "" + this.accidental + this.pitch + this.duration;
    }
}
