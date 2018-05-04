package karaoke.music;
import karaoke.sound.*;

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
        return 0;
    }

    @Override
    public void play(SequencePlayer player, double startBeat) {
        player.play();
    }

    @Override
    public String getLyricText() {
        return null;
    }

}
