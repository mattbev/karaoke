package karaoke;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import karaoke.sound.Instrument;
import karaoke.sound.Pitch;
import karaoke.sound.SequencePlayer;

/**
 * @author chessa, mattbev, sophias
 * 
 * An immutable ADT to represent a note of music played by an instrument
 *
 */
public class Note {

    private final double duration;
    private final Pitch pitch;
    private final String accidental;
    private final Instrument instrument = Instrument.PIANO;
    
    // AF(instrument, duration, pitch, accidental): A musical note of pitch pitch, with accidental accidental, played on 
    //      instrument for the amount of time held in duration
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
//     * @param instrument the instrument that plays this note
     * @param duration the duration of this note (i.e. 1/4 = quarter, 1 = whole, etc)
     * @param pitch the pitch of this note
     * @param accidental the accidental of the note
     */
    public Note(double duration, Pitch pitch, String accidental) {
        this.duration = duration;
        this.pitch = pitch;
        this.accidental = accidental;
        checkRep();
    }
    
    /**
     * construct a Note object from string input
     * @param pitch the pitch of the note
     * @param noteLength the duration of the note
     */
    public Note(String pitch, String noteLength) {
//        System.out.println(pitch);
//        System.out.println(noteLength);
        Set<String> validNotes = new HashSet<>(Arrays.asList("C","D","E","F","G","A","B","c","d","e","f","g","a","b"));
        String basenote = "";
        for (String note : validNotes) {
            if (pitch.contains(note)) {
                basenote = note;
                break;
            }
        }
        
        final Pitch notePitch = new Pitch(basenote.toUpperCase().toCharArray()[0]);
      
        final String[] pitchParams = pitch.split(basenote);
        String noteAccidental = "";
        if (pitchParams.length == 2) {
            noteAccidental = pitchParams[0];
            final String octave = pitchParams[1];
            
            if (basenote.toLowerCase().equals(basenote)) {
                notePitch.transpose(Pitch.OCTAVE);
            }
            if (octave.contains("'")) {
                notePitch.transpose(Pitch.OCTAVE*octave.length());
            }
            else if(octave.contains(",")) {
                notePitch.transpose(-Pitch.OCTAVE*octave.length());
            }
            if (noteAccidental.contains("^")) {
                notePitch.transpose(noteAccidental.length());
            }
            else if (noteAccidental.contains("_")) {
                notePitch.transpose(-noteAccidental.length());
            }
        }
        
        String numerator;
        String denominator;
        if (noteLength.length() == 0) {
            numerator = "0";
            denominator = "0";
        }
        else if (noteLength.length() == 1) {
            numerator = noteLength;
            denominator = "1";
        } else {
            final String[] lengthParams = noteLength.split("/");
            numerator = lengthParams[0];
            denominator = lengthParams[1];
        }
        int numeratorInt = 1;
        int denominatorInt = 1;
        if (numerator.length() > 0) {
            numeratorInt = Integer.parseInt(numerator);
        }
        if (denominator.length() > 0) {
            denominatorInt = Integer.parseInt(denominator);
        }
        final double noteDuration = ((double) numeratorInt) / ((double) denominatorInt);
        
        this.accidental = noteAccidental;
        this.duration = noteDuration;
        this.pitch = notePitch;
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

    
    @Override
    public boolean equals(Object that) {
        checkRep();
        if (that instanceof Note) {
            if (this.getAccidental().equals(((Note) that).getAccidental()) 
                && this.getPitch().equals(((Note) that).getPitch())
                && this.getDuration() == ((Note) that).getDuration()) {
                return true;
            }
        }
        return false;
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
