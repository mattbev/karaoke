package karaoke.music;

import java.util.List;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * An immutable ADT to represent one syllable component in a piece of music that has a lyric syllable
 *
 */
public interface Playable {
    
    // Datatype definition:
    //      Playable = Chord(notes: List<Note>, lyric: Lyric)
    //                 + Rest(lyric: Lyric, duration: double)
    //
    //
    
    /**
     * Create a new playable chord with lyric lyric
     * 
     * @param notes the notes to be in this chord
     * @param lyric the lyric associated with this chord
     * @return a new chord 
     */
    public static Chord createChord(List<Note> notes, Lyric lyric) {
        return new Chord(notes, lyric);
    }
    
    /**
     * Create a new music rest with lyric lyric of duration duration
     * 
     * @param lyric the lyric associated with this pause
     * @param duration the length of this rest
     * @return a new rest in music with length duration and lyric lyric
     */
    public static Rest createRest(Lyric lyric, double duration) {
        return new Rest(lyric, duration);
    }

    /**
     * Get the duration of this playable component
     * 
     * @return the duration in seconds of this component
     */
    public double getDuration();
    
    /**
     * Return the syllable lyric that should be streamed during this component
     * 
     * @return the syllable lyric tied to this component
     */
    public Lyric getLyric();
    
    /**
     * Return the lyric text of this component of music
     * 
     * @return the lyric of this component as a string
     */
    public String getLyricText();
}
