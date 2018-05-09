package karaoke.music;

import java.util.List;

import karaoke.sound.SequencePlayer;

public class Tuplet implements Playable {
    
    public static final int DUPLET_NOTES = 2;
    public static final int TRIPLET_NOTES = 3;
    public static final int QUADRUPLET_NOTES = 4;
    public static final int TIME_OF_NOTES = 3;
    
    enum Type{
        DUPLET, TRIPLET, QUADRUPLET
        
    }
    private final List<Chord> chords;
    private final List<Chord> newChords;
    private final Type type;
    private double duration;
    private String lyricText;
    
    // AF(chords, duration, lyricText): A tuplet where chords are the chords in the tuplet
    //
    //
    //
    //
    //
    //
    
    /**
     * creates an instance of a Tuplet object
     * @param chords the notes in the tuplet
     */
    public Tuplet(List<Chord> chords) {
        
        this.chords = chords;
        if(chords.size() == DUPLET_NOTES) {
            this.type = Type.DUPLET;
        }
        else if(chords.size() == TRIPLET_NOTES) {
            this.type = Type.TRIPLET;
        }
        else {
            type = Type.QUADRUPLET;
        }
        double noteDuration = chords.get(0).getDuration();
        
        if(type == Type.DUPLET) {
            this.duration = noteDuration * TIME_OF_NOTES;
        }
        if(type == Type.TRIPLET) {
            this.duration = noteDuration * 2;
        }
        if(type == Type.QUADRUPLET) {
            this.duration = noteDuration * TIME_OF_NOTES;
        }
        //List<Chord> newChords;
        for(Chord chord : chords) {
            //make chord copy here! <3
        }
        
        
        
        for (Chord chord : this.chords) {       //newChords
            this.lyricText += chord.getLyricText();
        }
    }
    
    @Override
    public double getDuration() {
        return this.duration;
    }
    
    @Override
    public String getLyricText() {
        return this.lyricText;
    }

    @Override
    public void play(SequencePlayer player, double startBeat) {
        
    }
}
