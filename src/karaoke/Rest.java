/**
 * 
 */
package karaoke;

import java.util.Arrays;
import java.util.List;

import karaoke.sound.SequencePlayer;

/**
 * @author chessa, mattbev, sophias
 * 
 * Rest represents a pause in a piece of music, a variant of playable.
 *
 */



public class Rest implements Playable {
    
    private static final double ONE_HUNDRED = 100;
    private final double duration;
    
    // AF(duration): a pause in the music of length duration
    //
    // RI: 
    //      duration > 0
    //
    // Safety from rep exposure:
    //      all fields private final, and never mutated after contructor
    //      all return types are immutable, rep is never exposed
    // Thread safety argument:
    //    This class is threadsafe because it's immutable:
    //    - duration is private final and an immutable type
    //    
    
    /**
     * Make a component to represent a pause in a piece of music
     * 
     * @param duration the duration of the pause
     */
    public Rest(double duration) { 
        this.duration = duration;
    }

    @Override
    public double duration() {
        return this.duration;
    }

    
    /**
     * Provide a rest in the karaoke
     */
    @Override
    public void play(SequencePlayer player, double startBeat) {
        return;
    }
    
    @Override
    public boolean equals(Object that) {
        if (that instanceof Rest) {
            if (this.duration() == ((Rest) that).duration()) {
                return true;
            }
        }
        return false;
    }
    
    @Override 
    public int hashCode() {
        return (int) (this.duration*ONE_HUNDRED);
    }
    
    @Override
    public String toString() {
        return "z" + this.duration;
    }
    
    @Override
    public List<Measure> getMusic() {
        return Arrays.asList(new Measure(Arrays.asList(this)));
    }

    public List<Double> getDurationList() {
        // TODO Auto-generated method stub
        return null;
    }

}
