package karaoke;
import java.util.List;

import karaoke.server.WebServer;
import karaoke.sound.*;


/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Music is an immutable ADT representing an piece of music being played on request, with respective lyrics shown as it plays.
 *
 */

public interface Music {
    
    // Datatype definition:
    //    Music = Measure(components: List<Playable>)
    //              + Concat(firstMusic Music, secondMusic: Music)
    //
    
    /**
     * Create a new Music measure consisting of the given playable components
     * 
     * @param components the ordered components of this measure
     * @return a new measure of karaoke
     */
    public static Music createLine(List<Playable> components) {
        return new Line(components);
    }
    
    /**
     * Create a new Karaoke which is the concatenation of two karaokes
     * 
     * @param firstMusic the first Music piece
     * @param secondMusic the second Music piece
     * @return a new Music piece which is the concatenation of firstMusic then secondMusic
     */
    public static Music createConcat(Music firstMusic, Music secondMusic) {
        return new Concat(firstMusic, secondMusic);
    }
    
    /**
     * gets the components of the music
     * @return the playables in the music in order
     */
    public List<Playable> getComponents();
    
    /**
     * @return the total duration of this piece in beats
     */
    public double duration();
    
    
    /**
     * Play this piece.
     * @param player player to play on
     * @param startBeat when to play
     * @param server the server to play on
     * @param i the instrument to play the line on
     */
    public void play(SequencePlayer player, double startBeat, WebServer server, Instrument i);
    
   
    
}
