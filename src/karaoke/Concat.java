package karaoke;
import java.util.ArrayList;
import java.util.List;

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
     * 
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
    public void play(SequencePlayer player, double startBeat) {
        firstMusic.play(player,startBeat);
        secondMusic.play(player, startBeat + firstMusic.duration());
    }

    @Override
    public List<Measure> getMusic() {
        List<Measure> measures = new ArrayList<>();
        measures.addAll(this.firstMusic.getMusic());
        measures.addAll(this.secondMusic.getMusic());
        return measures;
    }

   

}
