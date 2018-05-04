/**
 * 
 */
package karaoke.music;


/**
 * @author chessa, mattbev, sophias
 *
 * Lyric is an immutable ADT representing a lyric of one syllable, during one playable component of music.
 */
public class Lyric {
    
    private final String lyric;
    
    /**
     * Mkae a lyric object that represents a single syllable's lyric
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
