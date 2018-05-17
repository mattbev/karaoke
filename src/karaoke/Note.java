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
    
    // AF(duration, pitch, accidental): A musical note of pitch pitch, with accidental accidental, played
    //                          for the amount of time held in duration
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
     * @param header the header of the nmusic that the note is parsed from
     */
    public Note(String pitch, String noteLength, Header header) {
        
        Set<String> validNotes = new HashSet<>(Arrays.asList("C","D","E","F","G","A","B","c","d","e","f","g","a","b"));
        String basenote = "";
        for (String note : validNotes) {
            if (pitch.contains(note)) {
                basenote = note;
                break;
            }
        }
        String[] pitchParams = pitch.split(basenote);

        Pitch notePitch = new Pitch(basenote.toUpperCase().toCharArray()[0]);
        if (basenote.toLowerCase().equals(basenote)) {
            notePitch = notePitch.transpose(Pitch.OCTAVE);
        }
        String noteAccidental = "";
        if (pitchParams.length > 0 && (pitchParams[0].contains("^") || pitchParams[0].contains("_"))) {
            noteAccidental += pitchParams[0];
            if (noteAccidental.contains("^")) {
                notePitch = notePitch.transpose(noteAccidental.length());
            }
            else if (noteAccidental.contains("_")) {
                notePitch = notePitch.transpose(-noteAccidental.length());
            }
        }
        if (pitchParams.length == 2) {
            final String octave = pitchParams[1];            
            
            if (octave.contains("'")) {
                notePitch = notePitch.transpose(Pitch.OCTAVE*octave.length());
            }
            else if(octave.contains(",")) {
                notePitch = notePitch.transpose(-Pitch.OCTAVE*octave.length());
            }
        }        
        final double beatMultiplier = 4.;
        this.accidental = noteAccidental;
        this.duration = Note.parseLength(noteLength) * header.getDefaultLengthDouble() * beatMultiplier;
        this.pitch = notePitch;

    }
    
    /**
     * 
     * @param noteLength the raw length of the note
     * @return the parsed length of the note
     */
    public static double parseLength(String noteLength) {
        String numerator;
        String denominator;
        if (noteLength.length() == 0) {
            numerator = "1";
            denominator = "1";
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
        
        return ((double) numeratorInt) / ((double) denominatorInt);
    }
    
    
    /**
     * assert stated and implied RI
     */
    private void checkRep() {
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
     * Play this note
     * @param player player producing the note
     * @param startBeat beat at which the note should play
     * @param i the instrument to play the note on
     */
    public void play(SequencePlayer player, double startBeat, Instrument i) {
        player.addNote(i, pitch, startBeat, duration);
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
