package karaoke;

import java.util.HashMap;
import java.util.List;

import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Overlay is a variant of Karaoke which represents the overlaying of two pieces of Karaoke
 * on top of each other so they are played simultaneously
 *
 */
public class Overlay implements Karaoke {
    private HashMap<String,Karaoke> overlayDict = new HashMap<String,Karaoke>();
    
    
    /**
     * 
     * @param firstKaraoke the first Karaoke piece in the overlay
     * @param secondKaraoke the second Karaoke piece in the sequence
     */
    public Overlay(HashMap<String,Karaoke> map) {
        this.overlayDict = map;
    }
    public double duration() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void play(SequencePlayer player, double startBeat) {
        // TODO Auto-generated method stub

    }

    public List<Measure> getMusic() {
        // TODO Auto-generated method stub
        return null;
    }

}
