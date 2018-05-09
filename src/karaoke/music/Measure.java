package karaoke.music;

import java.util.List;

import karaoke.sound.SequencePlayer;

/**
 * 
 * @author chessa, mattbev, sophias
 * 
 * Measure is an immutable variant of Karaoke
 *
 */
public class Measure implements Karaoke{
    
    private final List<Playable> components;

    /**
     * Create a measure of music
     * 
     * @param components the ordered playable components that make up this measure
     */
    public Measure(List<Playable> components) {
        this.components = components;
    }
    
    @Override
    public double duration() {
        return 0;
    }

    @Override
    public void play(SequencePlayer player, double startBeat) {
        
        double beginBeat = startBeat;
        for(Playable component: components) {
            component.play(player, beginBeat);
            beginBeat += component.getDuration();
        }
    }

   
    

}
