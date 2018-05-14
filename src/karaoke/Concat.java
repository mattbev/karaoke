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
public class Concat implements Karaoke {

    private final Karaoke firstKaraoke;
    private final Karaoke secondKaraoke;
    
    // AF(firstKaraoke, secondKaraoke): A Karaoke of duration firstKaraoke.duration() + secondKaraoke.duration()
    //                                  in which firstKaraoke plays first and secondKaraoke follows after, with 
    //                                  lyrics firstKaraoke.lyrics+secondKaraoke.lyrics streaming with the music.
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
    //    - firstKaraoke, and secondKaraoke are final
    //    - neither Karaoke objects are every exposed to the client
    

    
    /**
     * 
     * @param firstKaraoke the first Karaoke piece in the sequence
     * @param secondKaraoke the second Karaoke piece in the sequence
     */
    public Concat(Karaoke firstKaraoke, Karaoke secondKaraoke) {
        this.firstKaraoke = firstKaraoke;
        this.secondKaraoke = secondKaraoke;
    }

    @Override
    public double duration() {
        return firstKaraoke.duration() + secondKaraoke.duration();
    }

    @Override
    public void play(SequencePlayer player, double startBeat) {
        firstKaraoke.play(player,startBeat);
        secondKaraoke.play(player, startBeat + firstKaraoke.duration());
    }

    @Override
    public List<Measure> getMusic() {
        List<Measure> measures = new ArrayList<>();
        measures.addAll(this.firstKaraoke.getMusic());
        measures.addAll(this.secondKaraoke.getMusic());
        return measures;
    }

   

}
