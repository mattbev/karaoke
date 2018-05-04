/**
 * 
 */
package karaoke.music;


/**
 * @author chessa, mattbev, sophias
 *
 * Lyric is an immutable ADT representing a single lyric, (i.e. perhaps only a syllable), during one playable component of music.
 */
public class Lyric {
    
    private final String lyric;
    
    /**
     * Mkae a lyric object that represents a single component's lyric
     * 
     * @param lyric the lyric text
     */
    public Lyric(String lyric) {
        this.lyric = lyric;
    }


    /**
     * Return the lyric text of this component
     * 
     * @return the lyric text
     */
    public String getText() {
        return this.lyric;
    }

}
