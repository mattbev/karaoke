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
    
    private final Lyric lyric;
    private final double duration;
    
    /**
     * Make a component to represent a pause in a piece of music
     * 
     * @param lyric the lyric to be streamed during this pause
     * @param duration the duration of the pause
     */
    public Rest(Lyric lyric, double duration) { 
        this.lyric = lyric;
        this.duration = duration;
    }

    @Override
    public double getDuration() {
        return this.duration;
    }

    
    /**
     * Provide a rest in the karaoke
     */
    @Override
    public void play(SequencePlayer player, double startBeat) {
        return;
    }

}
