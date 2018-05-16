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

public interface Playable {
    

    // Datatype definition:
    //      Playable = Chord(notes: List<Note>, lyric: List<Lyric>)
    //                 + Rest(duration: double)
    //
    //
    
    /**
     * Create a new playable chord with lyric lyric
     * 
     * @param notes the notes to be in this chord
     * @param l the line of lyrics, with the current one bolded, corresponding to this chord
     * @return a new chord 
     */
    public static Chord createChord(List<Note> notes, LyricLine l) {
        return new Chord(notes, l);
    }
    
    /**
     * Create a new music rest with lyric lyric of duration duration
     * 
     * @param duration the length of this rest
     * @param lyricLine the line of lyrics, with nothing bolded, corresponding to this rest
     * @return a new rest in music with length duration and lyric lyric
     */
    public static Rest createRest(double duration,LyricLine lyricLine) {
        return new Rest(duration, lyricLine);
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
     * @param l lyric to add to the playable
     * @return new playable with lyric l 
     */
    public Playable copyWithNewLyric(LyricLine l);
    
    /**
     * Make a new playable which consists of the same contents and lyrics, but has a different duration depending on the Tuplet type t
     * 
     * @param duration  the new duration
     * @param t the tuplet type
     * @return a new Chord with a different duration depending on the tuplet
     */
    public Playable copyPlayableNewDuration(double duration, Tuplet.Type t);
    

    /**
     * Return the lyric and rest of the line of this playable
     * 
     * @return the line of lyrics, with the current one bolded, corresponding to this playable
     */
    public LyricLine getLyricLine();

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
