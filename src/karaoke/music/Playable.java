package karaoke.music;

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
    //      Playable = Chord(notes: List<Note>, lyric: Lyric)
    //                 + Rest(lyric: Lyric, duration: double)
    //
    //
    
    /**
     * Create a new playable chord with lyric lyric
     * 
     * @param notes the notes to be in this chord
     * @param lyrics the lyric associated with this chord
     * @return a new chord 
     */
    public static Chord createChord(List<Note> notes, List<Lyric> lyrics) {
        return new Chord(notes, lyrics);
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
    
    
    
    /*
    
     * Return the syllable lyric that should be streamed during this component
     * 
     * @return the syllable lyric tied to this component
     
    public Lyric getLyric();
    
    */
    
    /**
     * Create new chord consisting of the given notes and given lyric
     * 
     * @param notes the notes in this chord, beginning at the same time
     * @param lyrics the lyrics associated with this chord
     * @return a new chord of the given notes, to be sung with given lyrics
     */
    public static Chord createNewChord(List<Note> notes, List<Lyric> lyrics) {
        return new Chord(notes, lyrics);
    }

    /**
     * Play this playable
     * @param player player on which to play
     * @param startBeat when to play
     */
    public void play(SequencePlayer player, double startBeat) ;
        
    
}
