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
    
    // AF(lyric): A song lyric of one syllable, where lyric is the lyric text to be sung for the one syllable 
    //
    // Rep Invariant:
    //      lyric.size() > 0
    //
    // Safety from rep exposure:
    //      field is private and final and immutable
    //      all return types are immutable
    
    /**
     * Make a lyric object that represents a single syllable's lyric
     * 
     * @param lyric the lyric text
     */
    public Lyric(String lyric) {
        this.lyric = lyric;
        checkRep();
    }


    /**
     * check the stated and implied rep invaraint
     */
    private void checkRep() {
        assert lyric.length() > 0 : "cannot have empty lyric text (empty lyric has one space)";
    }
    /**
     * Return the lyric text of this component
     * 
     * @return the lyric text
     */
    public String getText() {
        checkRep();
        return this.lyric;
    }
    
    /**
     * Create a copy of this lyric with the same text
     * 
     * @return a new replicated lyric object
     */
    public Lyric createLyricCopy() {
        checkRep();
        return new Lyric(this.getText());
    }
    
    /**
     * creates an empty instance of a lyric
     * @return a new Lyric instance representing the absence of a lyric
     */
    public static Lyric emptyLyric() {
        return new Lyric(" ");
    }
    

    @Override
    public boolean equals(Object that) {
        checkRep();
        return (that instanceof Lyric) && this.sameDuration((Lyric) that);
    }
    
    private boolean sameDuration(Lyric that) {
        checkRep();
        return this.getText().equals(that.getText());
    }
    
    @Override 
    public int hashCode() {
        checkRep();
        return this.getText().hashCode();
    }
}
