package karaoke;

import java.util.List;

import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * An immutable ADT to represent one syllable component in a piece of music that has a lyric syllable
 *
 */
public interface Playable extends Music {
    
    // Datatype definition:
    //      Playable = Chord(notes: List<Note>, lyric: List<Lyric>)
    //                 + Rest(duration: double)
    //
    //
    
    /**
     * Create a new playable chord with lyric lyric
     * 
     * @param notes the notes to be in this chord
     * @return a new chord 
     */
    public static Chord createChord(List<Note> notes) {
        return new Chord(notes);
    }
    
    /**
     * Create a new music rest with lyric lyric of duration duration
     * 
     * @param duration the length of this rest
     * @return a new rest in music with length duration and lyric lyric
     */
    public static Rest createRest(double duration) {
        return new Rest(duration);
    }

    /**
     * Get the duration of this playable component
     * 
     * @return the duration in seconds of this component
     */
    public double duration();
    
    
    
    /**
     * Creates a copy of a playable with a new LyricLine
     * 
     * @param p playable to be copied with a lyric added
     * @param l lyric to add to the playable
     * @return new playable with lyric l 
     */
    public Playable copyWithNewLyric(LyricLine l);
        
    
        

    /**
     * Play this playable
     * @param player player on which to play
     * @param startBeat when to play
     */
    public void play(SequencePlayer player, double startBeat) ;
        
    @Override
    public boolean equals(Object that);
    
    @Override
    public int hashCode();
    
    @Override
    public String toString();
}
