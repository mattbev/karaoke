package karaoke.music;
import java.util.List;

import karaoke.sound.*;


/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Karaoke is an immutable ADT representing an piece of music being played on request, with respective lyrics shown as it plays.
 *
 */

public interface Karaoke {
    
    // Datatype definition:
    //    Karaoke = Measure(components: List<Playable>)
    //              + Concat(firstKaraoke: Karaoke, secondKaraoke: Karaoke)
    //
    
    /**
     * Create a new karaoke measure consisting of the given playable components
     * 
     * @param components the ordered components of this measure
     * @return a new measure of karaoke
     */
    public static Measure createMeasure(List<Playable> components) {
        return new Measure(components);
    }
    
    /**
     * Create a new Karaoke which is the concatenation of two karaokes
     * 
     * @param firstKaraoke the first karaoke piece
     * @param secondKaraoke the second karaoke piece
     * @return a new karaoke piece which is the concatenation of firstKaraoke then secondKaraoke
     */
    public static Concat createConcat(Karaoke firstKaraoke, Karaoke secondKaraoke) {
        return new Concat(firstKaraoke, secondKaraoke);
    }
    
    /**
     * @return the total duration of this piece in beats
     */
    public double duration();
    
    /**
     * Play this piece.
     * @param player player to play on
     * @param startBeat when to play
     */
    public void play(SequencePlayer player, double startBeat);
    
   
    
}
