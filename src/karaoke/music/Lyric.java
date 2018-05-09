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
     * Make a lyric object that represents a single syllable's lyric
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
    
    /**
     * Create a copy of this lyric with the same text
     * 
     * @return a new replicated lyric object
     */
    public Lyric createLyricCopy() {
        return new Lyric(this.getText());
    }
    
    /**
     * creates an empty instance of a lyric
     * @return a new Lyric instance representing the absence of a lyric
     */
    public static Lyric emptyLyric() {
        return new Lyric(" ");
    }
    

}
