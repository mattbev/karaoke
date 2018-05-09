/**
 * 
 */
package karaoke.music;

import karaoke.sound.SequencePlayer;

/**
 * @author chessa, mattbev, sophias
 * 
 * Rest represents a pause in a piece of music, a variant of playable.
 *
 */
public class Rest implements Playable {
    
    private static final double ONE_HUNDRED = 100;
    private final Lyric lyric = Lyric.emptyLyric();
    private final double duration;
    
    /**
     * Make a component to represent a pause in a piece of music
     * 
     * @param duration the duration of the pause
     */
    public Rest(double duration) { 
        this.duration = duration;
    }

    @Override
    public double getDuration() {
        return this.duration;
    }
    
    @Override
    public Lyric getLyric() {
        return new Lyric(this.lyric.getText());
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
        return (that instanceof Rest) && this.sameDuration((Rest) that);
    }
    
    private boolean sameDuration(Rest that) {
        return this.getDuration() == that.getDuration();
    }
    
    @Override 
    public int hashCode() {
        return (int) (this.duration*ONE_HUNDRED);
    }
    
    @Override
    public String toString() {
        return "z" + this.duration;
    }

}
