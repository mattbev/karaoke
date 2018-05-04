/**
 * 
 */
package karaoke.music;


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

    @Override
    public Lyric getLyric() {
        return this.lyric;
    }

    @Override
    public String getLyricText() {
        return this.lyric.getText();
    }

}
