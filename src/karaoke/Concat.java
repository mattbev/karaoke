package karaoke;
import java.util.ArrayList;
import java.util.List;

import karaoke.server.WebServer;
import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Concat is a variant of Karaoke which represents the concatenation of two pieces of Karaoke
 *
 */
public class Concat implements Music {

    private final Music firstMusic;
    private final Music secondMusic;
    
    // AF(firstMusic, secondMusic): A Music of duration firstMusic.duration() + secondMusic.duration()
    //                                  in which firstMusic plays first and secondMusic follows after, with 
    //                                  lyrics firstMusic.lyrics+secondMusic.lyrics streaming with the music.
    //
    // RI: 
    //      -True
    //
    // Safety from rep exposure:
    //      all fields private final, and never mutated after contructor
    //      all return types are immutable, rep is never exposed
    //
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - firstMusic, and secondMusic are final
    //    - neither Music objects are every exposed to the client
    

    
    /**
     * Combines two pieces of music, firstMusic and secondMusic
     * tail to head so that secondMusic plays right after firstMusic
     * @param firstMusic the first Music piece in the sequence
     * @param secondMusic the second Music piece in the sequence
     */
    public Concat(Music firstMusic, Music secondMusic) {
        this.firstMusic = firstMusic;
        this.secondMusic = secondMusic;
    }

    @Override
    public double duration() {
        return firstMusic.duration() + secondMusic.duration();
    }

    @Override
    public void play(SequencePlayer player, double startBeat, WebServer server) {
        firstMusic.play(player,startBeat, server);
        secondMusic.play(player, startBeat + firstMusic.duration(), server);
    }
    
    @Override
    public List<Playable> getComponents() {
        final List<Playable> firstComponents = new ArrayList<>(this.firstMusic.getComponents());
        final List<Playable> secondComponents = new ArrayList<>(this.secondMusic.getComponents());
        firstComponents.addAll(secondComponents);
        return new ArrayList<Playable>(firstComponents);
    }
    
    @Override
    public List<Double> getDurationList(){
        List<Double> durationList = new ArrayList<Double>();
        
        //extend list with firstMusic's duration list first,
        //then secondMusic's duration list
        durationList.addAll(firstMusic.getDurationList());
        durationList.addAll(secondMusic.getDurationList());
        
        return durationList;
    }

   

}
